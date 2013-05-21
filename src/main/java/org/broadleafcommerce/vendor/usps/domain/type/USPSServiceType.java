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

package org.broadleafcommerce.vendor.usps.domain.type;

import org.broadleafcommerce.common.BroadleafEnumerationType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * An extendible enumeration of usps shipping method types.
 * 
 * @author jfischer
 */
public class USPSServiceType implements Serializable, BroadleafEnumerationType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, USPSServiceType> TYPES = new HashMap<String, USPSServiceType>();
    private static final Map<String, USPSServiceType> NAMES = new HashMap<String, USPSServiceType>();
    
    public static final USPSServiceType FIRST_CLASS  = new USPSServiceType("FIRST_CLASS", "FIRST CLASS", "First Class");
    public static final USPSServiceType FIRST_CLASS_COMMERCIAL  = new USPSServiceType("FIRST_CLASS_COMMERCIAL", "FIRST CLASS COMMERCIAL", "First Class Commercial");
    public static final USPSServiceType FIRST_CLASS_HFP_COMMERCIAL  = new USPSServiceType("FIRST_CLASS_HFP_COMMERCIAL", "FIRST CLASS HFP COMMERCIAL", "First Class HFP Commercial");
    public static final USPSServiceType PRIORITY  = new USPSServiceType("PRIORITY", "PRIORITY", "Priority");
    public static final USPSServiceType PRIORITY_COMMERCIAL  = new USPSServiceType("PRIORITY_COMMERCIAL", "PRIORITY COMMERCIAL", "Priority Commercial");
    public static final USPSServiceType PRIORITY_HFP_COMMERCIAL  = new USPSServiceType("PRIORITY_HFP_COMMERCIAL", "PRIORITY HFP COMMERCIAL", "Priority HFP Commercial");
    public static final USPSServiceType EXPRESS_MAIL = new USPSServiceType("EXPRESS_MAIL", "EXPRESS MAIL", "Express Mail");
    public static final USPSServiceType EXPRESS_COMMERCIAL  = new USPSServiceType("EXPRESS_COMMERCIAL", "EXPRESS COMMERCIAL", "Express Commercial");
    public static final USPSServiceType EXPRESS_SH  = new USPSServiceType("EXPRESS_SH", "EXPRESS SH", "Express SH");
    public static final USPSServiceType EXPRESS_SH_COMMERCIAL  = new USPSServiceType("EXPRESS_SH_COMMERCIAL", "EXPRESS SH COMMERCIAL", "Express SH Commercial");
    public static final USPSServiceType EXPRESS_HFP  = new USPSServiceType("EXPRESS_HFP", "EXPRESS HFP", "Express HFP");
    public static final USPSServiceType EXPRESS_HFP_COMMERCIAL  = new USPSServiceType("EXPRESS_HFP_COMMERCIAL", "EXPRESS HFP COMMERCIAL", "Express HFP Commercial");
    public static final USPSServiceType PARCEL_POST  = new USPSServiceType("PARCEL_POST", "PARCEL POST", "Parcel Post");
    public static final USPSServiceType MEDIA_MAIL  = new USPSServiceType("MEDIA_MAIL", "MEDIA MAIL", "Media Mail");
    public static final USPSServiceType LIBRARY_MAIL  = new USPSServiceType("LIBRARY_MAIL", "LIBRARY MAIL", "Library Mail");
    public static final USPSServiceType ONLINE  = new USPSServiceType("ONLINE", "ONLINE", "Online");
    
    public static USPSServiceType getInstance(final String type) {
        return TYPES.get(type);
    }
    
    private String type;
    private String friendlyType;
    private String name;

    public USPSServiceType() {
        //do nothing
    }

    public USPSServiceType(final String type, final String name, final String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
        setName(name);
    }

    @Override
    public String getType() {
        return this.type;
    }

    private void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }
    
    private void setName(final String name) {
        this.name = name;
        if (!NAMES.containsKey(name)) {
            NAMES.put(name, this);
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getFriendlyType() {
        return this.friendlyType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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