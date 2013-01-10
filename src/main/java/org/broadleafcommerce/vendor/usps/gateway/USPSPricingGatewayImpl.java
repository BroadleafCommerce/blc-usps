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
package org.broadleafcommerce.vendor.usps.gateway;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.broadleafcommerce.common.util.UnitOfMeasureUtil;
import org.broadleafcommerce.common.util.WeightUnitOfMeasureType;
import org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException;
import org.broadleafcommerce.core.catalog.domain.Weight;
import org.broadleafcommerce.core.order.domain.BundleOrderItem;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.pricing.service.fulfillment.FulfillmentLocationResolver;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.vendor.usps.domain.USPSConfiguration;
import org.broadleafcommerce.vendor.usps.domain.USPSFulfillmentOption;
import org.broadleafcommerce.vendor.usps.domain.type.USPSServiceType;
import org.springframework.beans.factory.InitializingBean;

import com.usps.webtools.rates.ObjectFactory;
import com.usps.webtools.rates.PostageV4Type;
import com.usps.webtools.rates.RateV4RequestType;
import com.usps.webtools.rates.RequestPackageV4Type;
import com.usps.webtools.rates.ErrorV4Type;
import com.usps.webtools.rates.RateV4ResponseType;
import com.usps.webtools.rates.ResponsePackageV4Type;

public class USPSPricingGatewayImpl implements USPSPricingGateway, InitializingBean {
    
    @Resource(name="blFulfillmentLocationResolver")
    protected FulfillmentLocationResolver fulfillmentLocationResolver;
    
    protected JAXBContext jaxbContext;
    
    protected Integer timeout = 2000;
    
    protected String charSet = "UTF-8";
    
    @Override
    public RateV4ResponseType retrieveDomesticRates(FulfillmentGroup fulfillmentGroup, List<FulfillmentGroupItem> fgItems, USPSConfiguration uspsConfiguration, boolean shop) throws FulfillmentPriceException {
        
        RateV4RequestType request = new RateV4RequestType();
        request.setUSERID(uspsConfiguration.getUserName());
        request.setPASSWORD(uspsConfiguration.getPassword());
        request.setRevision(buildRevision(fulfillmentGroup, uspsConfiguration));
        
        List<RequestPackageV4Type> packages = buildPackages(fulfillmentGroup, fgItems, uspsConfiguration, shop);
        for (RequestPackageV4Type pkg : packages) {
            request.getPackage().add(pkg);
        }
        
        RateV4ResponseType response = executeCall(request, uspsConfiguration);
        
        //USPS returns giberish in some of their fields. Looks like markup of some kind.  Remove it here...
        List<ResponsePackageV4Type> respPackages = response.getPackage();
        for (ResponsePackageV4Type pkg : respPackages) {
            List<PostageV4Type> postages = pkg.getPostage();
            for (PostageV4Type postage : postages) {
                postage.setMailService(postage.getMailService().replaceAll("&lt;sup&gt;&amp;reg;&lt;/sup&gt;", "").trim().toUpperCase());
            }
        }
        
        return response;
    }
    
    protected String buildRevision(FulfillmentGroup fulfillmentGroup, USPSConfiguration uspsConfiguration) {
        //Current values are null for basic and 2 for full.
        return "2";
    }
    
    protected List<RequestPackageV4Type> buildPackages(FulfillmentGroup fulfillmentGroup, List<FulfillmentGroupItem> fgItems, USPSConfiguration uspsConfiguration, boolean shop) 
        throws FulfillmentPriceException {
        ArrayList<RequestPackageV4Type> packages = new ArrayList<RequestPackageV4Type>();
        BigDecimal totalWeightLbs = BigDecimal.ZERO;
        BigDecimal maxWeightPerPackage = uspsConfiguration.getMaximumWeightPerPackage();
        
        for (FulfillmentGroupItem fgItem : fgItems) {
            BigDecimal skuWeight = null;
            OrderItem orderItem = fgItem.getOrderItem();
            Integer quantity = orderItem.getQuantity();
            if (orderItem instanceof DiscreteOrderItem) {
                DiscreteOrderItem discreteOrderItem = (DiscreteOrderItem)orderItem;
                Weight weight = discreteOrderItem.getSku().getWeight();
                WeightUnitOfMeasureType weightUnitOfMeasureType = weight.getWeightUnitOfMeasure();
                
                if (weightUnitOfMeasureType.equals(WeightUnitOfMeasureType.POUNDS)) {
                    skuWeight = weight.getWeight();
                } else if (WeightUnitOfMeasureType.KILOGRAMS.equals(weightUnitOfMeasureType)) {
                    //Convert kilograms to lbs
                    skuWeight = UnitOfMeasureUtil.convertKilogramsToPounds(weight.getWeight());
                } else {
                    throw new FulfillmentPriceException("Could not convert the UOM " + weightUnitOfMeasureType.getType() + " to LBS.");
                }
                
                if (skuWeight.floatValue() > maxWeightPerPackage.floatValue()) {
                    throw new FulfillmentPriceException("Sku " + discreteOrderItem.getSku().getId() + " exceeded the max package weight of " + maxWeightPerPackage.toString());
                }
                totalWeightLbs = totalWeightLbs.add(skuWeight.multiply(new BigDecimal(quantity)));
            } else if (orderItem instanceof BundleOrderItem) {
                List<DiscreteOrderItem> discreteOrderItems = ((BundleOrderItem)orderItem).getDiscreteOrderItems();
                for (DiscreteOrderItem discreteOrderItem : discreteOrderItems) {
                    Weight weight = discreteOrderItem.getSku().getWeight();
                    WeightUnitOfMeasureType weightUnitOfMeasureType = weight.getWeightUnitOfMeasure();
                    if (weightUnitOfMeasureType.equals(WeightUnitOfMeasureType.POUNDS)) {
                        skuWeight = weight.getWeight();
                    } else if (WeightUnitOfMeasureType.KILOGRAMS.equals(weightUnitOfMeasureType)) {
                        //Convert kilograms to lbs
                        skuWeight = UnitOfMeasureUtil.convertKilogramsToPounds(weight.getWeight());
                    } else {
                        throw new FulfillmentPriceException("Could not convert the UOM " + weightUnitOfMeasureType.getType() + " to LBS.");
                    }
                    
                    if (skuWeight.floatValue() > maxWeightPerPackage.floatValue()) {
                        throw new FulfillmentPriceException("Sku " + discreteOrderItem.getSku().getId() + " exceeded the max package weight of " + maxWeightPerPackage.toString());
                    }
                    totalWeightLbs = totalWeightLbs.add(skuWeight.multiply(new BigDecimal(quantity)));
                }
            }
        }
        //Find the address to be shipped from.
        Address fulfillmentAddress = fulfillmentLocationResolver.resolveLocationForFulfillmentGroup(fulfillmentGroup);
        
        //Calculate the number of packages and weight per package.
        BigDecimal numberOfPackages = totalWeightLbs.divide(maxWeightPerPackage, 0, RoundingMode.CEILING);
        BigDecimal weightPerPackage = totalWeightLbs.divide(numberOfPackages, 2, RoundingMode.HALF_UP);
        
        BigDecimal weightPoundsPerPackage = weightPerPackage.setScale(0, RoundingMode.DOWN);
        
        BigDecimal weightOuncesPerPackage;
        if (weightPerPackage.floatValue() >= 1F) {
            BigDecimal remainder = weightPerPackage.remainder(weightPoundsPerPackage);
            weightOuncesPerPackage = remainder.multiply(new BigDecimal(16)).setScale(2, RoundingMode.HALF_UP);
        } else {
            weightOuncesPerPackage = weightPerPackage.multiply(new BigDecimal(16)).setScale(2, RoundingMode.HALF_UP);
        }
        
        /*
         * Note that it is nearly impossible to guess how each client wants to build packages for the purpose of shipping. 
         * We are going to make this as generic as possible. In the future it might be good to provide an interface to build packages from fulfillment groups based on 
         * size, weight, and whether items are machinable.  
         * In the mean time, clients can use this basic algorithm, which assumes all items are machinable, and which uses regular sized packages.
         */
        for (int i = 0; i < numberOfPackages.intValue(); i++) {
            RequestPackageV4Type pkg = new RequestPackageV4Type();
            StringBuilder packageId = new StringBuilder(String.valueOf(fulfillmentGroup.getOrder().getId())).append('-').append(i);
            pkg.setID(packageId.toString());
            pkg.setZipOrigination(Integer.parseInt(fulfillmentAddress.getPostalCode()));
            pkg.setZipDestination(Integer.parseInt(fulfillmentGroup.getAddress().getPostalCode()));
            pkg.setPounds(weightPoundsPerPackage.intValue());
            pkg.setOunces(weightOuncesPerPackage.floatValue());
            
            //We allow clients to simply override this method.
            buildPackageVariables(pkg, fulfillmentGroup, uspsConfiguration, shop);
            packages.add(pkg);
        }
        
        return packages;
    }
    
    protected void buildPackageVariables(RequestPackageV4Type pkg, FulfillmentGroup fulfillmentGroup, USPSConfiguration uspsConfiguration, boolean shop) {
        
        /*
         * Size can be REGULAR or LARGE
         * Large is any package dimension over 12 inches
         */
        pkg.setSize("REGULAR");
        
        /*
         * Container can be one of:
         * FLAT RATE ENVELOPE
         * PADDED FLAT RATE ENVELOPE
         * LEGAL FLAT RATE ENVELOPE
         * SM FLAT RATE ENVELOPE
         * WINDOW FLAT RATE ENVELOPE
         * GIFT CARD FLAT RATE ENVELOPE
         * FLAT RATE BOX
         * SM FLAT RATE BOX
         * MD FLAT RATE BOX
         * LG FLAT RATE BOX
         * REGIONALRATEBOXA
         * REGIONALRATEBOXB
         * REGIONALRATEBOXC
         * RECTANGULAR
         * NONRECTANGULAR
         * 
         * If size is LARGE, container must be either RECTANGULAR or NONRECTANGULAR. We'll default it 
         * to VARIABLE.
         */
        pkg.setContainer("VARIABLE");
        
        //If someone is shopping for rates, we use ALL, otherwise, we use one of the services provided by the enum passed in.
        if (shop) {
            //Special case. Set the specific service to shop for all services.
            pkg.setService("ALL");
        } else {
            pkg.setService(((USPSFulfillmentOption)fulfillmentGroup.getFulfillmentOption()).getService().getName());
            
            /*
             * If Service is FIRST CLASS, FIRST CLASS COMMERCIAL, or FIRST CLASS HFP COMMERCIAL, then the 
             * firstClassMailType property must be set.
             */
            if (USPSServiceType.FIRST_CLASS.getName().equals(pkg.getService()) 
                    || USPSServiceType.FIRST_CLASS_COMMERCIAL.getName().equals(pkg.getService())
                    || USPSServiceType.FIRST_CLASS_HFP_COMMERCIAL.getName().equals(pkg.getService())) {
                
                /*
                 * Set it to parcel by default. Possibilities are:
                 * LETTER
                 * FLAT
                 * PARCEL
                 * POST CARD
                 * PAKCAGE SERVICE
                 */
                pkg.setFirstClassMailType("PARCEL");
            }
        }
        
        //Set machinable to true by default.
        pkg.setMachinable(true);
    }
    
    protected RateV4ResponseType executeCall(RateV4RequestType request, USPSConfiguration uspsConfiguration) throws FulfillmentPriceException {
        URL url;
        String urlString = new StringBuilder(uspsConfiguration.getApiUrl()).toString();
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("The URL: " + urlString + " is malformed.", e);
        }
        
        OutputStreamWriter osw = null;
        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(this.timeout);
            connection.setReadTimeout(this.timeout);
            
            osw = new OutputStreamWriter(connection.getOutputStream());
            BufferedInputStream is = null;
            InputStreamReader reader = null;
            StringWriter stringWriter = new StringWriter();
            try {
                osw.write("API=");
                osw.write(URLEncoder.encode(uspsConfiguration.getApi(), this.charSet));
                osw.write("&XML=");
                jaxbContext.createMarshaller().marshal(new ObjectFactory().createRateV4Request(request), stringWriter);
                osw.write(URLEncoder.encode(stringWriter.toString(), this.charSet));
                osw.flush();
                is = new BufferedInputStream(connection.getInputStream());
                reader = new InputStreamReader(is);
                
                JAXBElement<?> response = (JAXBElement<?>)jaxbContext.createUnmarshaller().unmarshal(reader);
                if (response.getValue() instanceof ErrorV4Type) {
                    ErrorV4Type error = (ErrorV4Type)response.getValue();
                    StringBuilder errorMsg = new StringBuilder("Error calling USPS: \n");
                    errorMsg.append("Code: ").append(error.getNumber()).append("\n");
                    errorMsg.append("Message: ").append(error.getDescription()).append("\n");
                    errorMsg.append("Source: ").append(error.getSource()).append("\n");
                    errorMsg.append("Help Context: ").append(error.getHelpContext()).append("\n");
                    errorMsg.append("Help File: ").append(error.getHelpFile());
                    throw new FulfillmentPriceException(errorMsg.toString());
                } else {
                    return (RateV4ResponseType)response.getValue();
                }
            } catch (JAXBException e) {
                throw new FulfillmentPriceException("Error occured making a call to USPS.", e);
            } finally {
                IOUtils.closeQuietly(osw);
                IOUtils.closeQuietly(stringWriter);
                IOUtils.closeQuietly(reader);
                IOUtils.closeQuietly(is);
            }
        } catch (IOException e) {
            throw new FulfillmentPriceException("Error occured executing call to USPS.", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jaxbContext = JAXBContext.newInstance("com.usps.webtools.rates");
    }
    
    public void setJaxbContext(JAXBContext context) {
        this.jaxbContext = context;
    }
    
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
    
    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }
    
    public void setFulfillmentLocationResolver(FulfillmentLocationResolver resolver) {
        this.fulfillmentLocationResolver = resolver;
    }
}
