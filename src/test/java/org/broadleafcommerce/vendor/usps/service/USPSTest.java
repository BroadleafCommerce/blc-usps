/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.broadleafcommerce.vendor.usps.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.bind.JAXBContext;

import org.broadleafcommerce.common.util.WeightUnitOfMeasureType;
import org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.Weight;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupImpl;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.pricing.service.fulfillment.FulfillmentLocationResolver;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.vendor.usps.domain.USPSConfigurationImpl;
import org.broadleafcommerce.vendor.usps.domain.USPSFulfillmentOption;
import org.broadleafcommerce.vendor.usps.domain.type.USPSServiceType;
import org.broadleafcommerce.vendor.usps.gateway.USPSPricingGatewayImpl;
import org.broadleafcommerce.vendor.usps.provider.USPSFulfillmentPricingProvider;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.usps.webtools.rates.RateV4ResponseType;

public class USPSTest {
	
	protected USPSFulfillmentPricingProvider provider;
	protected USPSPricingGatewayImpl gateway;
	protected USPSConfigurationImpl configuration;
	
	@Before
	public void setup() throws Exception {
		configuration = createConfiguration();
		gateway = createGateway();
		provider = createProvider();
	}

	@Test
	public void testBasicUSPSCall() throws FulfillmentPriceException {
		FulfillmentGroup fg = createBasicDomesticFulfillmentGroup();
		RateV4ResponseType response = gateway.retrieveDomesticRates(fg, fg.getFulfillmentGroupItems(), configuration, true);
		assertTrue(response.getPackage().size() > 0);
		assertTrue(response.getPackage().get(0).getError() == null);
		assertTrue(response.getPackage().get(0).getPostage().get(0).getRate() > 0);
	}
	
	/*
	 * Creates a FulfillmentGroup with a single FGItem.
	 */
	protected FulfillmentGroup createBasicDomesticFulfillmentGroup() {
		Sku sku = EasyMock.createMock(Sku.class);
		Weight weight = new Weight();
		weight.setWeight(new BigDecimal("2"));
		weight.setWeightUnitOfMeasure(WeightUnitOfMeasureType.POUNDS);
		EasyMock.expect(sku.getWeight()).andReturn(weight).anyTimes();
		EasyMock.replay(sku);
		
		DiscreteOrderItem oi = EasyMock.createMock(DiscreteOrderItem.class);
		EasyMock.expect(oi.getSku()).andReturn(sku).anyTimes();
		EasyMock.expect(oi.getQuantity()).andReturn(1).anyTimes();
		EasyMock.replay(oi);
		
		ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(oi);
		
		Order order = EasyMock.createMock(Order.class);
		EasyMock.expect(order.getId()).andReturn(1L).anyTimes();
		EasyMock.expect(order.getOrderItems()).andReturn(orderItems).anyTimes();
		EasyMock.replay(order);
		
		Country country = EasyMock.createMock(Country.class);
		EasyMock.expect(country.getAbbreviation()).andReturn("US").anyTimes();
		EasyMock.replay(country);
		assertEquals(country.getAbbreviation(), "US");
		
		Address address = EasyMock.createMock(Address.class);
		EasyMock.expect(address.getCountry()).andReturn(country).anyTimes();
		EasyMock.expect(address.getPostalCode()).andReturn("77549").anyTimes();
		EasyMock.replay(address);
		assertEquals(address.getCountry(), country);
		
		FulfillmentGroupItem fgItem = EasyMock.createMock(FulfillmentGroupItem.class);
		EasyMock.expect(fgItem.getQuantity()).andReturn(1).anyTimes();
		EasyMock.expect(fgItem.getOrderItem()).andReturn(oi).anyTimes();
		EasyMock.replay(fgItem);
		
		ArrayList<FulfillmentGroupItem> fgItems = new ArrayList<FulfillmentGroupItem>();
		fgItems.add(fgItem);
		
		USPSFulfillmentOption option = EasyMock.createMock(USPSFulfillmentOption.class);
		EasyMock.expect(option.getService()).andReturn(USPSServiceType.PARCELPOST).anyTimes();
		EasyMock.replay(option);
		
		FulfillmentGroup fg = EasyMock.createMock(FulfillmentGroup.class);
		EasyMock.expect(fg.getAddress()).andReturn(address).anyTimes();
		EasyMock.expect(fg.getOrder()).andReturn(order).anyTimes();
		EasyMock.expect(fg.getFulfillmentGroupItems()).andReturn(fgItems).anyTimes();
		EasyMock.expect(fg.getFulfillmentOption()).andReturn(option).anyTimes();
		EasyMock.replay(fg);
		
		return fg;
	}
	
	protected USPSPricingGatewayImpl createGateway() throws Exception {
		USPSPricingGatewayImpl uspsGateway = new USPSPricingGatewayImpl();
		uspsGateway.setJaxbContext(JAXBContext.newInstance("com.usps.webtools.rates"));
		
		Country country = EasyMock.createMock(Country.class);
		EasyMock.expect(country.getAbbreviation()).andReturn("US").anyTimes();
		EasyMock.replay(country);
		assertEquals(country.getAbbreviation(), "US");
		
		Address address = EasyMock.createMock(Address.class);
		EasyMock.expect(address.getCountry()).andReturn(country).anyTimes();
		EasyMock.expect(address.getPostalCode()).andReturn("78746").anyTimes();
		EasyMock.replay(address);
		assertEquals(address.getCountry(), country);
		
		FulfillmentLocationResolver resolver = EasyMock.createMock(FulfillmentLocationResolver.class);
		EasyMock.expect(resolver.resolveLocationForFulfillmentGroup(EasyMock.anyObject(FulfillmentGroup.class))).andReturn(address).anyTimes();
		EasyMock.replay(resolver);
		assertEquals(resolver.resolveLocationForFulfillmentGroup(new FulfillmentGroupImpl()).getCountry().getAbbreviation(), "US");
		
		uspsGateway.setFulfillmentLocationResolver(resolver);
		return uspsGateway;
	}
	
	protected USPSFulfillmentPricingProvider createProvider() {
		USPSFulfillmentPricingProvider uspsProv = new USPSFulfillmentPricingProvider();
		
		return uspsProv;
	}
	
	protected USPSConfigurationImpl createConfiguration() {
    	InputStream is = USPSTest.class.getResourceAsStream("/config/bc/override/development.properties");
    	Properties props = new Properties();
    	try {
	    	props.load(is);
    	} catch (IOException e){
    		throw new RuntimeException("Error occured loading the ThirdPartyModulesPrivateConfig", e);
    	} finally {
    		if (is != null) {
    			try {
    				is.close();
    			} catch (IOException e) {
    				//Ignore
    			}
    		}
    	}
    	
    	USPSConfigurationImpl configuration = new USPSConfigurationImpl();
        configuration.setMaximumWeightPerPackage(new BigDecimal("150"));
        configuration.setApiUrl(props.getProperty("usps.url"));
        configuration.setUserName(props.getProperty("usps.user.name"));
        configuration.setPassword(props.getProperty("usps.password"));
        configuration.setApi(props.getProperty("usps.api"));
        return configuration;
    }
}
