package com.boco.eoms.mobile.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.google.gson.Gson;

public class DeptUserTreeAction extends BaseAction{
	/**
	 * 查询部门或人员
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getDeptOrUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String getType = StaticMethod.nullObject2String(request.getParameter("getType"));//类型为dept  or user
		String name = StaticMethod.nullObject2String(request.getParameter("name"));//类型为dept  or user
		if("".equals(getType)){//如果为空值,则不进行查询,返回""
			response.getOutputStream().write("".getBytes("UTF-8"));
			return;
		}
		String returnJson = "";
		String getSonNoSelf = StaticMethod.nullObject2String(request.getParameter("getSonNoSelf"));
		if("dept".equals(getType)&&!"".equals(name)){
			ITawSystemDeptManager tawSystemDeptManager =  (ITawSystemDeptManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDeptSaveManagerFlush");
			List<TawSystemDept> returnList = null;
			if(!"".equals(getSonNoSelf)&&"true".equals(getSonNoSelf)){//如果是查询自己和自己下一级
				returnList = tawSystemDeptManager.getALLSondept(this.getUser(request).getDeptid(),"0");
//				returnList = tawSystemDeptManager.getALLSondept(this.getUser(request).getDeptid().substring(0, this.getUser(request).getDeptid().length()-4),"0");
			}else{
				returnList = tawSystemDeptManager.getDeptidByDeptName(name);
			}
			Gson gson = new Gson();
			if(null != returnList && !returnList.isEmpty()){
				returnJson = gson.toJson(returnList);
			}
//			System.out.println(returnJson);
			MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
			return;
		}
		if("user".equals(getType)&&(!"".equals(name)||!"".equals(getSonNoSelf)&&"true".equals(getSonNoSelf))){
			ITawSystemUserManager tawSystemUserManager =  (ITawSystemUserManager) ApplicationContextHolder.getInstance().getBean("ItawSystemUserSaveManagerFlush");
			List returnList  = null;
			if(!"".equals(getSonNoSelf)&&"true".equals(getSonNoSelf)){
				returnList  = tawSystemUserManager.getSubUserBydeptid(this.getUser(request).getDeptid(),this.getUserId(request),null,true);
//				returnList  = tawSystemUserManager.getUserBydeptidAndSubs(this.getUser(request).getDeptid());
			}else{
				returnList  =  tawSystemUserManager.getUsersByName(name);
			}
			
			Gson gson = new Gson();
			if(null != returnList && !returnList.isEmpty()){
				returnJson = gson.toJson(returnList);
			}
			MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
			return;
		}
		MobileCommonUtils.responseWrite(response, "", "UTF-8");
		return;

	}
}
