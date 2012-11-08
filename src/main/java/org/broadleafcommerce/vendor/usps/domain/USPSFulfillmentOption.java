package org.broadleafcommerce.vendor.usps.domain;

import org.broadleafcommerce.vendor.usps.domain.type.USPSServiceType;
import org.broadleafcommerce.core.order.domain.FulfillmentOption;

/**
 * USPS-specific fulfillment option
 * <p/>
 * User: Kelly Tisdell
 * Date: 11/07/12
 */
public interface USPSFulfillmentOption extends FulfillmentOption{

    public USPSServiceType getService();

    public void setService(USPSServiceType service);


}
