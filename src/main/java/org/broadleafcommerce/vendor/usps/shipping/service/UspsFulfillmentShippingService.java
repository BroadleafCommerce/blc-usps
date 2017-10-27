/*
 * #%L
 * BroadleafCommerce USPS
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.broadleafcommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Broadleaf in which case
 * the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt)
 * shall apply.
 * 
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
//package org.broadleafcommerce.vendor.usps.shipping.service;
//
//import org.broadleafcommerce.common.order.domain.dto.FulfillmentOrderDTO;
//import org.broadleafcommerce.core.shipping.service.FulfillmentShippingService;
//
//public class UspsFulfillmentShippingService implements FulfillmentShippingService {
//
//    @Override
//    public FulfillmentOrderDTO trackFulfillmentOrder(FulfillmentOrderDTO fulfillmentOrderDTO) {
//        //This method requires two details for the xml call
//        //  USERID - this is an id we will use configuration to get, specifies a Web Tools ID
//        //  TrackID - alphanumeric code used to track a package. You can provide multiple ones to
//        //            receive info about multiple packages.
//
//        //The Response wil give you:
//        //  ID - this maps back to a TrackID
//        //  Summary - a String summary of the state of the package.
//        //  Detail - multiple entries of this element that say the events the package has gone through.
//        return null;
//    }
//
//}
