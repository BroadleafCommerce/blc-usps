package org.broadleafcommerce.vendor.usps.domain;

import org.broadleafcommerce.vendor.usps.domain.type.USPSServiceType;
import org.broadleafcommerce.core.order.domain.FulfillmentOption;

/**
 * UPS-specific fulfillment option
 * <p/>
 * User: Kelly Tisdell
 * Date: 7/12/12
 */
public interface USPSFulfillmentOption extends FulfillmentOption{

    public USPSServiceType getService();

    public void setService(USPSServiceType service);


}
