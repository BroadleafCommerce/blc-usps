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
