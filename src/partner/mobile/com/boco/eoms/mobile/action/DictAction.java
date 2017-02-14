package com.boco.eoms.mobile.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.google.gson.Gson;

public class DictAction extends BaseAction {

	public void getDictByParentId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception, Exception {
		
		String parentDictId = StaticMethod.nullObject2String(request.getParameter("parentDictId"));
		ITawSystemDictTypeManager manager = (ITawSystemDictTypeManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDictTypeManager");
		List<TawSystemDictType> returnList =manager.getDictSonsByDictid(parentDictId);
		if(null != returnList && returnList.size()>0){
			Gson gson = new Gson();
			String returnJson = gson.toJson(returnList);
			MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
			return;
		}
		MobileCommonUtils.responseWrite(response, "", "UTF-8");
		return;
	}
}
