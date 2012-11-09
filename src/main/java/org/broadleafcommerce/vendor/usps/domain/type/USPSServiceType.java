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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;
import org.broadleafcommerce.common.util.StringUtil;

/**
 * An extendible enumeration of usps shipping method types.
 * 
 * @author jfischer
 */
public class USPSServiceType implements Serializable, BroadleafEnumerationType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, USPSServiceType> TYPES = new HashMap<String, USPSServiceType>();
    private static final Map<String, USPSServiceType> NAMEDTYPES = new HashMap<String, USPSServiceType>();

    public static final USPSServiceType FIRSTCLASS  = new USPSServiceType("0", "FIRSTCLASS", "First Class");
    public static final USPSServiceType PRIORITYMAIL = new USPSServiceType("1", "PRIORITYMAIL", "Priority Mail");
    public static final USPSServiceType EXPRESSMAILHOLDFORPICKUP = new USPSServiceType("2", "EXPRESSMAILHOLDFORPICKUP", "Express Mail Hold for Pickup");
    public static final USPSServiceType EXPRESSMAILPOTOADDRESSEE = new USPSServiceType("3", "EXPRESSMAILPOTOADDRESSEE", "Express Mail PO to Addressee");
    public static final USPSServiceType PARCELPOST = new USPSServiceType("4", "PARCELPOST", "Parcel Post");
    public static final USPSServiceType BOUNDPRINTEDMATTER = new USPSServiceType("5", "BOUNDPRINTEDMATTER", "Bound Printed Matter");
    public static final USPSServiceType MEDIAMAIL = new USPSServiceType("6", "MEDIAMAIL", "Media Mail");
    public static final USPSServiceType LIBRARY = new USPSServiceType("7", "LIBRARY", "Library Mail");
    public static final USPSServiceType FIRSTCLASSPOSTCARDSTAMPED = new USPSServiceType("12", "FIRSTCLASSPOSTCARDSTAMPED", "First Class Postcard Stamped");
    public static final USPSServiceType EXPRESSMAILFLATRATEENVELOPE = new USPSServiceType("13", "EXPRESSMAILFLATRATEENVELOPE", "Express Mail Flat Rate Envelope");
    public static final USPSServiceType PRIORITYMAILFLATRATEENVELOPE = new USPSServiceType("16", "PRIORITYMAILFLATRATEENVELOPE", "Priority Mail Flat Rate Envelope");
    public static final USPSServiceType PRIORITYMAILFLATRATEBOX = new USPSServiceType("17", "PRIORITYMAILFLATRATEBOX", "Priority Mail Flat Rate Box");
    public static final USPSServiceType PRIORITYMAILKEYSANDIDS = new USPSServiceType("18", "PRIORITYMAILKEYSANDIDS", "Priority Mail Keys and IDs");
    public static final USPSServiceType FIRSTCLASSKEYSANDIDS = new USPSServiceType("19", "FIRSTCLASSKEYSANDIDS", "First Class Keys and IDs");
    public static final USPSServiceType PRIORITYMAILFLATRATELARGEBOX = new USPSServiceType("22", "PRIORITYMAILFLATRATELARGEBOX", "Priority Mail Flat Rate Large Box");
    public static final USPSServiceType EXPRESSMAILSUNDAYHOLIDAY = new USPSServiceType("23", "EXPRESSMAILSUNDAYHOLIDAY", "Express Mail Sunday/Holiday");
    public static final USPSServiceType EXPRESSMAILFLATRATEENVELOPESUNDAYHOLIDAY = new USPSServiceType("25", "EXPRESSMAILFLATRATEENVELOPESUNDAYHOLIDAY", "Express Mail Flat Rate Envelope Sunday/Holiday");
    public static final USPSServiceType EXPRESSMAILFLATRATEENVELOPEHOLDFORPICKUP = new USPSServiceType("27", "EXPRESSMAILFLATRATEENVELOPEHOLDFORPICKUP", "Express Mail Flat Rate Envelope Hold For Pickup");

    public static USPSServiceType getInstance(final String type) {
        return TYPES.get(type);
    }
    
    public static USPSServiceType getInstanceByName(final String name) {
        return NAMEDTYPES.get(name);
    }

    public static USPSServiceType getInstanceByDescription(final String description) {
        //remove any dimension callouts
        String lDescription = description.replaceAll("\\(.*?\\)", "");
        USPSServiceType closestMatch = null;
        Double closestChecksumDeviation = null;
        for (USPSServiceType type : TYPES.values()) {
            double deviation = StringUtil.determineSimilarity(lDescription, type.getDescription());
            if (
                    (closestChecksumDeviation == null && deviation <= 5000000.0) ||
                    (closestChecksumDeviation != null && deviation < closestChecksumDeviation)
            ){
                closestChecksumDeviation = deviation;
                closestMatch = type;
            }
        }
        return closestMatch;
    }

    private String type;
    private String description;
    private String name;

    public USPSServiceType() {
        //do nothing
    }

    public USPSServiceType(final String type, final String name, final String description) {
        this.description = description;
        setType(type);
        setName(name);
    }

    public String getType() {
        return type;
    }

    public String getFriendlyType() {
		return description;
	}

	private void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
        if (!NAMEDTYPES.containsKey(name)) {
        	NAMEDTYPES.put(name, this);
        }
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
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