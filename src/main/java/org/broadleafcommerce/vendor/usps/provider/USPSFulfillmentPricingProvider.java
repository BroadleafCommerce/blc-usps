package org.broadleafcommerce.vendor.usps.provider;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentOption;
import org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentEstimationResponse;
import org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider;
import org.broadleafcommerce.vendor.usps.gateway.USPSPricingGateway;
import org.broadleafcommerce.vendor.usps.service.USPSConfigurationService;

public class USPSFulfillmentPricingProvider implements
		FulfillmentPricingProvider {
	
	private static final Log LOG = LogFactory.getLog(USPSFulfillmentPricingProvider.class);

	@Resource(name="blUSPSPricingGateway")
    protected USPSPricingGateway uspsPricingGateway;
    
	@Resource(name="blUSPSConfigurationService")
    protected USPSConfigurationService uspsConfigurationService;

	@Override
	public FulfillmentGroup calculateCostForFulfillmentGroup(
			FulfillmentGroup fulfillmentGroup) throws FulfillmentPriceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canCalculateCostForFulfillmentGroup(
			FulfillmentGroup fulfillmentGroup, FulfillmentOption option) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FulfillmentEstimationResponse estimateCostForFulfillmentGroup(
			FulfillmentGroup fulfillmentGroup, Set<FulfillmentOption> options)
			throws FulfillmentPriceException {
		// TODO Auto-generated method stub
		return null;
	}

}
