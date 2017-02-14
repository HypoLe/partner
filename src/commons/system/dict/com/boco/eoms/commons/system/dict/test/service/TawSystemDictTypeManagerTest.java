
package com.boco.eoms.commons.system.dict.test.service;

import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;

public class TawSystemDictTypeManagerTest extends ConsoleTestCase {

    public void testListDict()throws Exception{
    	ITawSystemDictTypeManager mgr=(ITawSystemDictTypeManager)getBean("ItawSystemDictTypeManager") ;
    	List list=mgr.listDict("10101040503", "245", "2");
    	for(Iterator it=list.iterator();it.hasNext();){
    		TawSystemDictType dict=(TawSystemDictType)it.next();
    		System.out.println(dict.getId());
    	}
    }

}
