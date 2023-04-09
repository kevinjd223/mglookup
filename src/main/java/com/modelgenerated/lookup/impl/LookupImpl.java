/* LookupImpl.java
 *
*/

package com.modelgenerated.lookup.impl;

import com.modelgenerated.lookup.Lookup;
import com.modelgenerated.lookup.LookupData;
import com.modelgenerated.lookup.LookupDataList;
import com.modelgenerated.lookup.impl.gen.LookupBaseImpl;
import com.modelgenerated.foundation.debug.Displayable;
import java.io.Serializable;

public class LookupImpl extends LookupBaseImpl implements Lookup, Serializable, Displayable {
	private static final long serialVersionUID = 1L;

	@Override
	public LookupDataList getLookupDataList() {
        LookupDataList lookupDataList = super.getLookupDataList();
        lookupDataList.sort(LookupData.ATTRIB_SORTORDER);
        return lookupDataList;
    }
    
	/**
	 * Returns lookup data with the supplied code. Otherwise returns null.
	 */
	@Override
    public LookupData getLookupDataByCode(String code) {
    	if (code == null) {
    		return null;
    	}
    	
        LookupData lookupData = null;
        for (LookupData enumLookupData : this.getLookupDataList()) {
            String enumCode = enumLookupData.getCode();
            if (code.equalsIgnoreCase(enumCode)) {
                lookupData = enumLookupData;
            }
        }
        return lookupData;
    }
    
}
