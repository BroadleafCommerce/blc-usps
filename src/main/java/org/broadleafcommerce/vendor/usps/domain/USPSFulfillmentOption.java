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
