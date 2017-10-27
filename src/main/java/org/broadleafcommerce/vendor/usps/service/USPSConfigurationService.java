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
package org.broadleafcommerce.vendor.usps.service;

import org.broadleafcommerce.vendor.usps.domain.USPSConfiguration;

/**
 * 
 * @author Kelly Tisdell
 *
 */
public interface USPSConfigurationService {

    /**
     * There should only be one USPS configuration record in the table. This returns the first one only.
     * @return
     */
    public USPSConfiguration findUSPSConfiguration();
    
    /**
     * Saves or updates USPS Configuration. There should only be one record in the table, so take care 
     * not to create multiple records.
     * @param config
     * @return
     */
    public USPSConfiguration saveUSPSConfiguration(USPSConfiguration config);
    
    /**
     * Deletes the given USPS Configuration.
     * @param config
     */
    public void deleteUSPSConfiguration(USPSConfiguration config);
    
    /**
     * Creates a new instance of USPS Configuration object.  This does not persist anything to the 
     * database.
     * @return
     */
    public USPSConfiguration createUSPSConfiguration();
}
