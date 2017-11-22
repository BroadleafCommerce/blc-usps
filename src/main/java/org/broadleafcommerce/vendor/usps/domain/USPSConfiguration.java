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
package org.broadleafcommerce.vendor.usps.domain;

import java.math.BigDecimal;

/**
 * Represents USPS Security Data for making an API call.
 * <p/>
 * User: Kelly Tisdell
 * Date: 7/13/12
 */
public interface USPSConfiguration {
    
    public void setId(Long id);
    
    public Long getId();
    
    /**
     * The USPS user name.
     * @return
     */
    public String getUserName();
    
    public void setUserName(String userName);

    /**
     * The USPS password.
     * @return
     */
    public String getPassword();

    public void setPassword(String password);
    
    /**
     * This is the URL that should be used to call the web services (e.g. http://production.shippingapis.com/ShippingAPI.dll). 
     * 
     * Consult the USPS documentation for more information.
     * 
     * @param url
     */
    public void setApiUrl(String url);
    
    public String getApiUrl();
    
    /**
     * This is to set the API (e.g. RateV4)
     * @param api
     */
    public void setApi(String api);
    
    public String getApi();
    
    /**
     * This is the maximum weight that can be in a single package. Weight for USPS is in LBS
     * @return
     */
    public BigDecimal getMaximumWeightPerPackage();
    
    /**
     * Set the maximum weight for a single package. Units are in LBS.
     * 
     * @param weight
     */
    public void setMaximumWeightPerPackage(BigDecimal weight);
    
    /**
     * This will typically be a number greater than zero and less or equal to one. It cannot be 
     * less than or equal to zero.
     * This represents an additional charge that will be applied, as a percentage, 
     * above and beyond the USPS calculation. 
     * This is not meant as a surcharge, per se.  It is meant to allow the retailer to apply an additional 
     * percentage to the shipping calculation that will be directly applied to the result from USPS.
     * Since the calculation is weight-based, and since the 
     * actual packages that get sent out might be different than what is used to calculate shipping, 
     * we apply an optional surcharge on top of the calculation to ensure that the retailer is 
     * not necessarily losing money on USPS shipping calculations where the physical packaging 
     * may affect the cost in a disadvantageous way for the retailer.
     * 
     * @return
     */
    public BigDecimal getUpchargePercentage();
    
    public void setUpchargePercentage(BigDecimal percentage);

}
