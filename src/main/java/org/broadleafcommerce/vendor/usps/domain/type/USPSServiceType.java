/*
 * #%L
 * BroadleafCommerce USPS
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.broadleafcommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Broadleaf in which case
 * the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt)
 * shall apply.
 * 
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
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

import org.apache.commons.lang.WordUtils;
import org.broadleafcommerce.common.BroadleafEnumerationType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * An extendible enumeration of usps shipping method types.
 * 
 * @author jfischer
 */
public class USPSServiceType implements Serializable, BroadleafEnumerationType {

    private static final long serialVersionUID = 1L;
    private static final String PM = "PRIORITY MAIL";
    private static final String PME = PM + " EXPRESS";
    private static final String PME1D = PME + " 1-DAY";
    private static final String PM1D = PM + " 1-DAY";
    private static final String PM2D = PM + " 2-DAY";

    public static final String HOLD_FOR_PICKUP = " HOLD FOR PICKUP";
    public static final String FLAT_RATE = " FLAT RATE";

    public static final String BOX = " BOX";
    public static final String ENV = " ENVELOPE";

    private static final Map<String, USPSServiceType> TYPES = new HashMap<String, USPSServiceType>();
    private static final Map<String, USPSServiceType> NAMES = new HashMap<String, USPSServiceType>();

    public static final USPSServiceType FIRST_CLASS  = new USPSServiceType("FIRST_CLASS", "FIRST CLASS", "First Class");
    public static final USPSServiceType FIRST_CLASS_COMMERCIAL  = new USPSServiceType("FIRST_CLASS_COMMERCIAL", "FIRST CLASS COMMERCIAL", "First Class Commercial");
    public static final USPSServiceType FIRST_CLASS_HFP_COMMERCIAL  = new USPSServiceType("FIRST_CLASS_HFP_COMMERCIAL", "FIRST CLASS HFP COMMERCIAL", "First Class HFP Commercial");
    public static final USPSServiceType PRIORITY  = new USPSServiceType("PRIORITY", "PRIORITY", "Priority", Pattern.compile("^PRIORITY MAIL \\d.*$"));
    public static final USPSServiceType PRIORITY_COMMERCIAL  = new USPSServiceType("PRIORITY_COMMERCIAL", "PRIORITY COMMERCIAL", "Priority Commercial");
    public static final USPSServiceType PRIORITY_HFP_COMMERCIAL  = new USPSServiceType("PRIORITY_HFP_COMMERCIAL", "PRIORITY HFP COMMERCIAL", "Priority HFP Commercial");
    public static final USPSServiceType EXPRESS = new USPSServiceType("EXPRESS", "EXPRESS", "Express", Pattern.compile("^PRIORITY MAIL EXPRESS \\d.*$"));
    public static final USPSServiceType EXPRESS_COMMERCIAL  = new USPSServiceType("EXPRESS_COMMERCIAL", "EXPRESS COMMERCIAL", "Express Commercial");
    public static final USPSServiceType EXPRESS_SH  = new USPSServiceType("EXPRESS_SH", "EXPRESS SH", "Express SH");
    public static final USPSServiceType EXPRESS_SH_COMMERCIAL  = new USPSServiceType("EXPRESS_SH_COMMERCIAL", "EXPRESS SH COMMERCIAL", "Express SH Commercial");
    public static final USPSServiceType EXPRESS_HFP  = new USPSServiceType("EXPRESS_HFP", "EXPRESS HFP", "Express HFP");
    public static final USPSServiceType EXPRESS_HFP_COMMERCIAL  = new USPSServiceType("EXPRESS_HFP_COMMERCIAL", "EXPRESS HFP COMMERCIAL", "Express HFP Commercial");
    public static final USPSServiceType PARCEL_POST  = new USPSServiceType("PARCEL_POST", "PARCEL POST", "Parcel Post");
    public static final USPSServiceType MEDIA_MAIL  = new USPSServiceType("MEDIA_MAIL", "MEDIA MAIL", "Media Mail", Pattern.compile("^MEDIA MAIL.*$"));
    public static final USPSServiceType LIBRARY_MAIL  = new USPSServiceType("LIBRARY_MAIL", "LIBRARY MAIL", "Library Mail", Pattern.compile("^LIBRARY MAIL.*$"));
    public static final USPSServiceType ONLINE  = new USPSServiceType("ONLINE", "ONLINE", "Online");
    // Priority Mail 1 Day
    public static final USPSServiceType PRIORITY_EXPRESS_1DAY = new USPSServiceType("PRIORITY_EXPRESS_1DAY", PME1D, WordUtils.capitalize(PME1D), Pattern.compile("^PRIORITY MAIL EXPRESS 1.*$"));
    public static final USPSServiceType PRIORITY_EXPRESS_1DAY_HOLD = new USPSServiceType("PRIORITY_EXPRESS_1DAY_HOLD", PME1D + HOLD_FOR_PICKUP, WordUtils.capitalize(PME1D + HOLD_FOR_PICKUP));
    public static final USPSServiceType PRIORITY_EXPRESS_1DAY_ENVELOPE = new USPSServiceType("PRIORITY_EXPRESS_1DAY_ENVELOPE", PME1D + FLAT_RATE + ENV, WordUtils.capitalize(PME1D + FLAT_RATE + ENV));
    public static final USPSServiceType PRIORITY_EXPRESS_1DAY_ENVELOPE_HOLD = new USPSServiceType("PRIORITY_EXPRESS_1DAY_ENVELOPE_HOLD", PME1D + FLAT_RATE + ENV + HOLD_FOR_PICKUP, WordUtils.capitalize(PME1D + FLAT_RATE + ENV  + HOLD_FOR_PICKUP));
    public static final USPSServiceType PRIORITY_EXPRESS_1DAY_LEGAL_ENVELOPE = new USPSServiceType("PRIORITY_EXPRESS_1DAY_LEGAL_ENVELOPE", PME1D + "LEGAL" + FLAT_RATE + ENV, WordUtils.capitalize(PME1D + "LEGAL" + FLAT_RATE + ENV));
    public static final USPSServiceType PRIORITY_EXPRESS_1DAY_LEGAL_ENVELOPE_HOLD = new USPSServiceType("PRIORITY_EXPRESS_1DAY_LEGAL_ENVELOPE_HOLD", PME1D + "LEGAL" + FLAT_RATE + ENV + HOLD_FOR_PICKUP, WordUtils.capitalize(PME1D + "LEGAL" + FLAT_RATE + ENV  + HOLD_FOR_PICKUP));
    public static final USPSServiceType PRIORITY_EXPRESS_1DAY_PADDED_ENVELOPE = new USPSServiceType("PRIORITY_EXPRESS_1DAY_PADDED_ENVELOPE", PME1D + "PADDED" + FLAT_RATE + ENV, WordUtils.capitalize(PME1D + "PADDED" + FLAT_RATE + ENV));
    public static final USPSServiceType PRIORITY_EXPRESS_1DAY_PADDED_ENVELOPE_HOLD = new USPSServiceType("PRIORITY_EXPRESS_1DAY_PADDED_ENVELOPE_HOLD", PME1D + "PADDED" + FLAT_RATE + ENV + HOLD_FOR_PICKUP, WordUtils.capitalize(PME1D + "PADDED" + FLAT_RATE + ENV  + HOLD_FOR_PICKUP));

    // Priority Mail 2 Day
    public static final USPSServiceType PRIORITY_2DAY = new USPSServiceType("PRIORITY_2DAY", PM2D, WordUtils.capitalize(PM2D), Pattern.compile("^PRIORITY MAIL 2.*$"));
    public static final USPSServiceType PRIORITY_2DAY_LARGE_BOX = new USPSServiceType("PRIORITY_2DAY_LARGE_BOX", PM2D + "LARGE" + FLAT_RATE + BOX, WordUtils.capitalize(PM2D + "LARGE" + FLAT_RATE + BOX));
    public static final USPSServiceType PRIORITY_2DAY_MEDIUM_BOX = new USPSServiceType("PRIORITY_2DAY_MEDIUM_BOX", PM2D + "MEDIUM" + FLAT_RATE + BOX, WordUtils.capitalize(PM2D + "MEDIUM" + FLAT_RATE + BOX));
    public static final USPSServiceType PRIORITY_2DAY_SMALL_BOX = new USPSServiceType("PRIORITY_2DAY_SMALL_BOX", PM2D + "SMALL" + FLAT_RATE + BOX, WordUtils.capitalize(PM2D + "SMALL" + FLAT_RATE + BOX));
    public static final USPSServiceType PRIORITY_2DAY_ENVELOPE = new USPSServiceType("PRIORITY_2DAY_ENVELOPE", PM2D + FLAT_RATE + ENV, WordUtils.capitalize(PM2D + FLAT_RATE + ENV));
    public static final USPSServiceType PRIORITY_2DAY_LEGAL_ENVELOPE = new USPSServiceType("PRIORITY_2DAY_LEGAL_ENVELOPE", PM2D + "LEGAL" + FLAT_RATE + ENV, WordUtils.capitalize(PM2D + "LEGAL" + FLAT_RATE + ENV));
    public static final USPSServiceType PRIORITY_2DAY_PADDED_ENVELOPE = new USPSServiceType("PRIORITY_2DAY_PADDED_ENVELOPE", PM2D + "PADDED" + FLAT_RATE + ENV, WordUtils.capitalize(PM2D + "PADDED" + FLAT_RATE + ENV));
    public static final USPSServiceType PRIORITY_2DAY_GIFT_CARD_ENVELOPE = new USPSServiceType("PRIORITY_2DAY_GIFT_CARD_ENVELOPE", PM2D + "GIFT CARD" + FLAT_RATE + ENV, WordUtils.capitalize(PM2D + "GIFT CARD" + FLAT_RATE + ENV));
    public static final USPSServiceType PRIORITY_2DAY_SMALL_ENVELOPE = new USPSServiceType("PRIORITY_2DAY_SMALL_ENVELOPE", PM2D + "SMALL" + FLAT_RATE + ENV, WordUtils.capitalize(PM2D + "SMALL" + FLAT_RATE + ENV));
    public static final USPSServiceType PRIORITY_2DAY_WINDOW_ENVELOPE = new USPSServiceType("PRIORITY_2DAY_WINDOW_ENVELOPE", PM2D + "WINDOW" + FLAT_RATE + ENV, WordUtils.capitalize(PM2D + "WINDOW" + FLAT_RATE + ENV));
    public static USPSServiceType getInstance(final String type) {
        return TYPES.get(type);
    }
    
    private String type;
    private String friendlyType;
    private String name;
    private Pattern pattern;

    public USPSServiceType() {
        //do nothing
    }

    public USPSServiceType(final String type, final String name, final String friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
        setName(name);
    }

    public USPSServiceType(String type, String friendlyType, String name, Pattern pattern) {
        this.friendlyType = friendlyType;
        setType(type);
        setName(name);
        this.pattern = pattern;
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

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
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
