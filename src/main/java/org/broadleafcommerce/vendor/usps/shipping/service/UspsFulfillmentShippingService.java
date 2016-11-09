/*
 * #%L
 * BroadleafCommerce USPS
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt).
 * 
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Broadleaf Commerce, LLC
 * The intellectual and technical concepts contained
 * herein are proprietary to Broadleaf Commerce, LLC
 * and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Broadleaf Commerce, LLC.
 * #L%
 */
package org.broadleafcommerce.vendor.usps.shipping.service;

import org.broadleafcommerce.common.order.domain.dto.FulfillmentOrderDTO;
import org.broadleafcommerce.core.shipping.service.FulfillmentShippingService;

public class UspsFulfillmentShippingService implements FulfillmentShippingService {
    
    @Override
    public FulfillmentOrderDTO trackFulfillmentOrder(FulfillmentOrderDTO fulfillmentOrderDTO) {
        //This method requires two details for the xml call
        //  USERID - this is an id we will use configuration to get, specifies a Web Tools ID
        //  TrackID - alphanumeric code used to track a package. You can provide multiple ones to
        //            receive info about multiple packages.
        
        //The Response wil give you:
        //  ID - this maps back to a TrackID
        //  Summary - a String summary of the state of the package.
        //  Detail - multiple entries of this element that say the events the package has gone through.
        return null;
    }

}
