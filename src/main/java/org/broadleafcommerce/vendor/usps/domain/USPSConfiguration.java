package org.broadleafcommerce.vendor.usps.domain;

import org.broadleafcommerce.common.util.WeightUnitOfMeasureType;

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
     * This is the URL that should be used to call the web services. Consult the USPS documentation 
     * for more information.
     * 
     * @param url
     */
    public void setApiUrl(String url);
    
    public String getApiUrl();
    
    /**
     * This is the unit of measure to be used. It should be compatible with the country of 
     * origin.
     * @return
     */
    public WeightUnitOfMeasureType getWeightUnitOfMeasure();

    /**
     * Currently only supports {@link WeightUnitOfMeasureType#POUNDS} and {@link WeightUnitOfMeasureType#KILOGRAMS}
     * @param uom
     */
    public void setWeightUnitOfMeasure(WeightUnitOfMeasureType uom);
    
    /**
     * This is the maximum weight that can be in a single package.
     * @return
     */
    public BigDecimal getMaximumWeightPerPackage();
    
    /**
     * Set the maximum weight for a single package. Note that USPS only supports packages up to 150 pounds
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
