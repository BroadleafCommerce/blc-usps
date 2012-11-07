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
