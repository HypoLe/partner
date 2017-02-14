package com.boco.eoms.gz.util;

/** 
 * Description:  
 * Copyright:   Copyright (c)2011
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 11, 2011 10:03:56 AM 
 */
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dept.exception.TawSystemException;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.user.dao.hibernate.TawSystemUserDaoHibernate;
import com.boco.eoms.examonline.dao.ExamDAO;

public class SystemResource {
/**通过用户id取用户名
 * @param id
 * @return
 */
public static String getUserNameById(String id)
{
	// TawSystemUserDaoHibernate userdao=null;   	
	 try {
		return getUserDao().id2Name(id);
	} catch (DictDAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "";
	}
}
public static TawSystemUserDaoHibernate getUserDao()
{
	return (TawSystemUserDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemUserDao");
}
public static String getSpecialtySel(int... dictid)
{
	String sql=" from TawSystemDictType tdt where tdt.parentDictId='11801'";
//	String sql=" from TawSystemDictType tdt where tdt.parentDictId='11223'";
	 return getDictNameSel(sql,dictid);
}
public static String getManufacturerSel(int... dictid)
{
	
//	String sql=" from TawSystemDictType tdt where tdt.parentDictId='11802'";
	String sql=" from TawSystemDictType tdt where tdt.parentDictId='11203'";
	 return getDictNameSel(sql,dictid);
}
public static String deptId2Name(String deptid) throws TawSystemException {
	if (deptid != null && !deptid.equals("")) {
		return TawSystemDeptBo.getInstance().getDeptinfobydeptid(deptid, StaticVariable.UNSTRSELETED)
				.getDeptName();
	} else {
		return null;
	}

}
/**
 * 通过字典ID查询字典名称
 * @param dictid
 */
public static String getDictNameByDictid( String dictid ){
	String tmpstr=StaticMethod.null2String(dictid).trim();
	if(tmpstr.length()>0&&Integer.valueOf(tmpstr)>0)
	{
	ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager)ApplicationContextHolder
	.getInstance().getBean("ItawSystemDictTypeManager");
	return mgr.getDictByDictId(dictid).getDictName();
	}
	return "";
}
public static String getDictNameSel(String sql,int... dictid)
{
	StringBuffer sb=new StringBuffer();
	//String hql="from TawSystemDictType tdt where tdt.parentDictId='11801'";
	 ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
     List l=DAO.getHibernateTemplate().find(sql);
	for (int i = 0; i < l.size(); i++) {
		TawSystemDictType tdt=(TawSystemDictType)l.get(i);
		if(dictid.length>0&&String.valueOf(dictid[0]).equals(tdt.getDictId()))
			sb.append("<option value='").append(tdt.getDictId()).append("' selected>").append(tdt.getDictName()).append("</option>");
		else
		sb.append("<option value='").append(tdt.getDictId()).append("'>").append(tdt.getDictName()).append("</option>");
	}
	return sb.toString();
}
}
