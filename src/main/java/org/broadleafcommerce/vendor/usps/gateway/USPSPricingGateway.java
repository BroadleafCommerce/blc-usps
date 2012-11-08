package org.broadleafcommerce.vendor.usps.gateway;

import java.util.List;

import org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.vendor.usps.domain.USPSConfiguration;

import com.usps.webtools.rates.RateV4ResponseType;

/**
 * This interface uses APIs provided by USPS to interact, typically via web services calls, to obtain pricing information.
 * <p/>
 * User: Kelly Tisdell
 * Date: 11/07/12
 */
public interface USPSPricingGateway {

	public RateV4ResponseType retrieveDomesticRates(FulfillmentGroup fulfillmentGroup, List<FulfillmentGroupItem> fgItems, USPSConfiguration uspsConfiguration) throws FulfillmentPriceException;
	
}
