package org.broadleafcommerce.vendor.usps.dao;

import org.broadleafcommerce.vendor.usps.domain.USPSConfiguration;

/**
 * 
 * @author Kelly Tisdell
 *
 */
public interface USPSConfigurationDao {

	public USPSConfiguration findUSPSConfiguration();
	
	public USPSConfiguration saveUSPSConfiguration(USPSConfiguration config);
	
	public void deleteUSPSConfiguration(USPSConfiguration config);
	
	public USPSConfiguration createUSPSConfiguration();
}
