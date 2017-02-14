package com.boco.eoms.commons.statistic.base.mgr.impl;

import com.boco.eoms.commons.statistic.base.exception.Id2NameDAOException;
import com.boco.eoms.commons.statistic.base.exception.Id2NameStatException;
import com.boco.eoms.commons.statistic.base.mgr.IStatID2Name;
import com.boco.eoms.commons.statistic.base.reference.ApplicationContextHolder;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleManager;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;

/**
 * 
 * @author lizhenyou
 *
 */
public class StatID2NameV35Impl implements IStatID2Name {

	private String ID2NameV35BeanId;
	//= "tawSystemUserDao";
	
	public String id2Name(String id) throws Id2NameStatException {
		
		String reString="";
		try {
		//ID2NameDAO dao = (ID2NameDAO) ApplicationContextHolder.getInstance().getBean(ID2NameV35BeanId);
		if("ItawSystemDeptManager".equals(ID2NameV35BeanId)){
		    ITawSystemDeptManager dao=(ITawSystemDeptManager)ApplicationContextHolder.getInstance().getBean(ID2NameV35BeanId);
		    reString = dao.id2Name(id);
		}else if("itawSystemUserManager".equals(ID2NameV35BeanId)){
			ITawSystemUserManager dao=(ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean(ID2NameV35BeanId);
			reString = dao.id2Name(id);	
		}else if("ItawSystemDictTypeManager".equals(ID2NameV35BeanId)){
			ITawSystemDictTypeManager dao=(ITawSystemDictTypeManager)ApplicationContextHolder.getInstance().getBean(ID2NameV35BeanId);
			reString = dao.id2Name(id);	
		}else if("ItawSystemRoleManager".equals(ID2NameV35BeanId)){
			ITawSystemRoleManager dao=(ITawSystemRoleManager)ApplicationContextHolder.getInstance().getBean(ID2NameV35BeanId);
			reString = dao.id2Name(id);	
		}else if("ItawSystemSubRoleManager".equals(ID2NameV35BeanId)){
			ITawSystemSubRoleManager dao=(ITawSystemSubRoleManager)ApplicationContextHolder.getInstance().getBean(ID2NameV35BeanId);
			reString = dao.id2Name(id);	
		}
		else{
			ID2NameDAO dao = (ID2NameDAO) ApplicationContextHolder.getInstance().getBean(ID2NameV35BeanId);
			reString = dao.id2Name(id);
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reString;
		
	}
	
	public String idType2Name(String id, String type)
			throws Id2NameDAOException {
		return null;
	}

	public String getID2NameV35BeanId() {
		return ID2NameV35BeanId;
	}

	public void setID2NameV35BeanId(String nameV35BeanId) {
		ID2NameV35BeanId = nameV35BeanId;
	}

	

}
