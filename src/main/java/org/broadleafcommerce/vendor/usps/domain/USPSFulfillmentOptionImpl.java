
package org.broadleafcommerce.vendor.usps.domain;

import org.broadleafcommerce.vendor.usps.domain.type.USPSServiceType;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.core.order.domain.FulfillmentOptionImpl;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * JPA Domain representing USPS-specific fulfillment option
 * <p/>
 * User: Kelly Tisdell
 * Date: 11/07/12
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_USPS_FULFILLMENT_OPTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(friendlyName = "USPS Fulfillment Option")
public class USPSFulfillmentOptionImpl extends FulfillmentOptionImpl implements USPSFulfillmentOption {

    private static final long serialVersionUID = 1L;

    @Column(name="USPS_SERVICE_TYPE", nullable = false)
    @AdminPresentation(friendlyName = "USPS Service Type", fieldType= SupportedFieldType.BROADLEAF_ENUMERATION, broadleafEnumeration="com.broadleafcommerce.vendor.ups.domain.type.UPSServiceType")
    protected String service;

    public USPSServiceType getService() {
        return USPSServiceType.getInstance(service);
    }

    public void setService(USPSServiceType service) {
        this.service = service.getType();
    }
}
