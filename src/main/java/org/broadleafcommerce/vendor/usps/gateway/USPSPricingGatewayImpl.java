package org.broadleafcommerce.vendor.usps.gateway;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.vendor.usps.domain.USPSConfiguration;
import org.springframework.beans.factory.InitializingBean;

import com.usps.webtools.rates.ObjectFactory;
import com.usps.webtools.rates.RateV4RequestType;
import com.usps.webtools.rates.RequestPackageV4Type;
import com.usps.webtools.rates.ErrorV4Type;
import com.usps.webtools.rates.RateV4ResponseType;

public class USPSPricingGatewayImpl implements USPSPricingGateway, InitializingBean {
	
	protected JAXBContext jaxbContext;
	
	protected Integer timeout = 2000;
	
	protected String charSet = "UTF-8";
	
	@Override
	public RateV4ResponseType retrieveDomesticRates(FulfillmentGroup fulfillmentGroup, List<FulfillmentGroupItem> fgItems, USPSConfiguration uspsConfiguration) throws FulfillmentPriceException {
		
		RateV4RequestType request = new RateV4RequestType();
		request.setUSERID(uspsConfiguration.getUserName());
		request.setPASSWORD(uspsConfiguration.getPassword());
		request.setRevision(buildRevision(fulfillmentGroup, uspsConfiguration));
		
		List<RequestPackageV4Type> packages = buildPackages(fulfillmentGroup, fgItems, uspsConfiguration);
		for (RequestPackageV4Type pkg : packages) {
			request.getPackage().add(pkg);
		}
		
		RateV4ResponseType response = executeCall(request, uspsConfiguration);
		return response;
	}
	
	protected String buildRevision(FulfillmentGroup fulfillmentGroup, USPSConfiguration uspsConfiguration) {
		//Current values are null for basic and 2 for full.
		return "2";
	}
	
	protected List<RequestPackageV4Type> buildPackages(FulfillmentGroup fulfillmentGroup, List<FulfillmentGroupItem> fgItems, USPSConfiguration uspsConfiguration) {
		ArrayList<RequestPackageV4Type> packages = new ArrayList<RequestPackageV4Type>();
		
		RequestPackageV4Type pkg = new RequestPackageV4Type();
		pkg.setID("1");
		pkg.setZipOrigination(78746);
		pkg.setZipDestination(78633);
		pkg.setPounds(1);
		pkg.setOunces(0);
		pkg.setContainer("VARIABLE");
		pkg.setSize("REGULAR");
		pkg.setService("PARCEL");
		pkg.setMachinable(true);
		
		packages.add(pkg);
		
		return packages;
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
}
