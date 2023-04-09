/* LookupServiceBean.java
*/

package com.modelgenerated.lookup;

import com.modelgenerated.foundation.dataaccess.DataAccessLocator;
import com.modelgenerated.foundation.dataaccess.UserContext;
import com.modelgenerated.foundation.logging.Logger;
import com.modelgenerated.lookup.dao.LookupDAO;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;



@Stateless
@EJB(name = "java:global/LookupService", beanInterface = LookupService.class)
public class LookupServiceBean extends LookupServiceCrud implements LookupService {
    // crud methods for Lookup
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public Lookup findLookupByName(UserContext userContext, String name) {
        LookupDAO lookupDAO = (LookupDAO)DataAccessLocator.findDAO(Lookup.class.getName());
        
        Lookup lookup = (Lookup)lookupDAO.findByName(userContext, name);
        if (lookup != null) {
            Logger.debug(this, "findLookupByName() id=" + lookup.getId());
        } else {
            Logger.debug(this, "findLookupByName() not found name=" + name);

        }
        // force it to be loaded here
        if (lookup != null) {
		    lookup.getLookupDataList();
        }
        
        return lookup;        
        
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public LookupData findLookupData(UserContext userContext, String lookupName, String code) {
        Lookup lookup = findLookupByName(userContext, lookupName);
        
        return findLookupData(userContext, lookup, code);
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public LookupData findLookupData(UserContext userContext, Lookup lookup, String code) {
        LookupDataList lookupDataList = lookup.getLookupDataList();
        
        return findLookupData(userContext, lookupDataList, code);        
    }
    
    private LookupData findLookupData(UserContext userContext, LookupDataList lookupDataList, String code) {
        LookupData lookupData = null;
        Iterator i = lookupDataList.iterator();
        while (i.hasNext()) {
            LookupData enumLookupData = (LookupData)i.next();

            String enumCode = enumLookupData.getCode();
            if (valuesEqual(enumCode, code)) {
                lookupData = enumLookupData;
            }
        }

        // force jit loading. Hack/Workaround loading problem on jboss 7.1.3 and later.
        if (lookupData != null) {
            LookupRelationList lookupRelationList = lookupData.getLookupRelationships();
            Iterator j = lookupRelationList.iterator();
            while (j.hasNext()) {
                LookupRelation lookupRelation = (LookupRelation)j.next();
                LookupData childLookupData = lookupRelation.getChildLookupData();
            }
        }
        
        return lookupData;
    }
    
    private boolean valuesEqual(String value1, String value2) {
        if (value1 == null) {
            value1 = "";
        }
        if (value2 == null) {
            value2 = "";
        }
        return value1.equals(value2);        
    }   
    
    
}
