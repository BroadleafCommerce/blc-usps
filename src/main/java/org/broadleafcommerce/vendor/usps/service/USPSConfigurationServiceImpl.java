package org.broadleafcommerce.vendor.usps.service;

import javax.annotation.Resource;

import org.broadleafcommerce.vendor.usps.dao.USPSConfigurationDao;
import org.broadleafcommerce.vendor.usps.domain.USPSConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Kelly Tisdell
 *
 */
@Transactional("blTransactionManager")
public class USPSConfigurationServiceImpl implements USPSConfigurationService {

	@Resource(name="blUSPSConfigurationDao")
	protected USPSConfigurationDao uspsConfigDao;
	
	@Override
	public USPSConfiguration findUSPSConfiguration() {
		return uspsConfigDao.findUSPSConfiguration();
	}

	@Override
	public USPSConfiguration saveUSPSConfiguration(USPSConfiguration config) {
		return uspsConfigDao.saveUSPSConfiguration(config);
	}

	@Override
	public void deleteUSPSConfiguration(USPSConfiguration config) {
		uspsConfigDao.deleteUSPSConfiguration(config);
	}
	
	@Override
	public USPSConfiguration createUSPSConfiguration() {
		return uspsConfigDao.createUSPSConfiguration();
	}

}
