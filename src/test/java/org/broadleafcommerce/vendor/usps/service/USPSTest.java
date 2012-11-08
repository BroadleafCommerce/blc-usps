package org.broadleafcommerce.vendor.usps.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

import javax.xml.bind.JAXBContext;

import org.broadleafcommerce.common.util.WeightUnitOfMeasureType;
import org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException;
import org.broadleafcommerce.vendor.usps.domain.USPSConfigurationImpl;
import org.broadleafcommerce.vendor.usps.gateway.USPSPricingGatewayImpl;
import org.broadleafcommerce.vendor.usps.provider.USPSFulfillmentPricingProvider;
import org.junit.Before;
import org.junit.Test;

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
		gateway.retrieveDomesticRates(null, null, configuration);
	}
	
	protected USPSPricingGatewayImpl createGateway() throws Exception {
		USPSPricingGatewayImpl uspsGateway = new USPSPricingGatewayImpl();
		uspsGateway.setJaxbContext(JAXBContext.newInstance("com.usps.webtools.rates"));
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
        configuration.setWeightUnitOfMeasure(WeightUnitOfMeasureType.POUNDS);
        configuration.setApiUrl(props.getProperty("usps.url"));
        configuration.setUserName(props.getProperty("usps.user.name"));
        configuration.setPassword(props.getProperty("usps.password"));
        configuration.setApi(props.getProperty("usps.api"));
        return configuration;
    }
}
