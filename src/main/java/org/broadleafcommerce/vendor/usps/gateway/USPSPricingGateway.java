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

    public RateV4ResponseType retrieveDomesticRates(FulfillmentGroup fulfillmentGroup, List<FulfillmentGroupItem> fgItems, USPSConfiguration uspsConfiguration, boolean shop) throws FulfillmentPriceException;
    
}
