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

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import java.math.BigDecimal;

/**
 * Represents a reusable configuration for invoking the USPS API
 * <p/>
 * User: Kelly Tisdell
 * Date: 7/13/12
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_USPS_CONFIGURATION")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(friendlyName = "USPS Configuration Info")
public class USPSConfigurationImpl implements USPSConfiguration {

    @Id
    @GeneratedValue(generator= "USPSConfigurationId")
    @GenericGenerator(
            name="USPSConfigurationId",
            strategy="org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name="segment_value", value="USPSConfigurationImpl"),
                    @Parameter(name="entity_name", value="com.broadleafcommerce.vendor.usps.domain.USPSConfigurationImpl")
            }
    )
    @Column(name="USPS_CONFIGURATION_ID", nullable = false)
    protected Long id;

    @Column(name="USPS_USER_NAME", nullable = false)
    protected String userName;

    @Column(name="USPS_PASSWORD", nullable = false)
    protected String password;

    @Column(name="API_URL", nullable = false)
    protected String apiUrl;
    
    @Column(name="API", nullable = false)
    protected String api;
    
    @Column(name="MAX_WEIGHT_PER_PACKAGE", nullable = false)
    protected BigDecimal maximumWeightPerPackage;
    
    @Column(name="UPCHARGE_PERCENTAGE", nullable = true)
    protected BigDecimal upchargePercentage;
    
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setApiUrl(String url) {
        this.apiUrl = url;
    }

    @Override
    public String getApiUrl() {
        return this.apiUrl;
    }

	@Override
	public BigDecimal getMaximumWeightPerPackage() {
		return this.maximumWeightPerPackage;
	}

	@Override
	public void setMaximumWeightPerPackage(BigDecimal weight) {
		this.maximumWeightPerPackage = weight;
	}

	@Override
	public BigDecimal getUpchargePercentage() {
		return this.upchargePercentage;
	}

	@Override
	public void setUpchargePercentage(BigDecimal percentage) {
		if (percentage.doubleValue() <= 0D) {
			throw new IllegalArgumentException("USPS upcharge must be a positive number.");
		}
		this.upchargePercentage = percentage;
	}

	@Override
	public void setApi(String api) {
		this.api = api;
	}

	@Override
	public String getApi() {
		return this.api;
	}

}
