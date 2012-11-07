
package org.broadleafcommerce.vendor.usps.domain.type;

import org.broadleafcommerce.common.BroadleafEnumerationType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration to represent the USPS Services
 * <p/>
 * User: Kelly Tisdell
 * Date: 11/07/12
 */
public class USPSServiceType implements Serializable, BroadleafEnumerationType{

    private static final long serialVersionUID = 1L;

    private static final Map<String, USPSServiceType> TYPES = new HashMap<String, USPSServiceType>();
    private static final Map<String, USPSServiceType> CODES = new HashMap<String, USPSServiceType>();

    //Valid for Domestic
    public static final USPSServiceType NEXT_DAY_AIR = new USPSServiceType("NEXT_DAY_AIR", "Next Day Air", "01");
    
    private final String type;
    private final String friendlyName;
    private final String code;
    
    protected USPSServiceType(String type, String friendlyName, String code) {
        this.type = type;
        this.friendlyName = friendlyName;
        this.code = code;
        TYPES.put(type, this);
        CODES.put(code, this);
    }

    public static USPSServiceType getInstance(final String type) {
        return TYPES.get(type);
    }

    public static USPSServiceType getInstanceByCode(final String code) {
        return CODES.get(code);
    }
    
    @Override
    public String getType() {
        return this.type;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getFriendlyType() {
        return friendlyName;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public String getCode() {
        return this.code;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * (result + ((type == null) ? 0 : type.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        USPSServiceType other = (USPSServiceType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
