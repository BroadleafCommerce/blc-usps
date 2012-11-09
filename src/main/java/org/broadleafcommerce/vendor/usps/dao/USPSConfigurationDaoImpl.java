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
package org.broadleafcommerce.vendor.usps.dao;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.vendor.usps.domain.USPSConfiguration;
import org.hibernate.ejb.QueryHints;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

/**
 * 
 * @author Kelly Tisdell
 *
 */
public class USPSConfigurationDaoImpl implements USPSConfigurationDao {

	@PersistenceContext(unitName="blPU")
	protected EntityManager em;
	
	@Resource(name="blEntityConfiguration")
	protected EntityConfiguration entityConfiguration;
	
	@Override
	@SuppressWarnings("unchecked")
	public USPSConfiguration findUSPSConfiguration() {
		StringBuffer queryString = new StringBuffer("select uspsConfig from ");
		queryString.append(entityConfiguration.lookupEntityClass(USPSConfiguration.class.getName()).getName()).append(" uspsConfig");
		Query query = em.createQuery(queryString.toString());
		query.setMaxResults(1);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		List<USPSConfiguration> configs = query.getResultList();
		if (configs != null && configs.size() > 0) {
			return configs.get(0);
		}
		return null;
	}

	@Override
	public USPSConfiguration saveUSPSConfiguration(USPSConfiguration config) {
		return em.merge(config);
	}

	@Override
	public void deleteUSPSConfiguration(USPSConfiguration config) {
		em.remove(config);
	}
	
	@Override
	public USPSConfiguration createUSPSConfiguration() {
		return (USPSConfiguration)entityConfiguration.createEntityInstance(USPSConfiguration.class.getName());
	}

}
