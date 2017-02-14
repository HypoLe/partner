package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;

/**
 * <p>
 * Title:��Ա�������
 * </p>
 * <p>
 * Description:��Ա�������
 * </p>
 * <p>
 * Tue Mar 10 16:24:32 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() liujinlong
 * @moudle.getVersion() 3.5
 * 
 */
public final class AreaAction extends BaseAction {
	/**
	 * 地域树
	 * author:wangjunfeng
	 * 2010-4-2
	 */
	public ActionForward area(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		ArrayList list = new ArrayList();
		JSONArray root = new JSONArray();

		try {
			ITawSystemAreaManager mgr = (ITawSystemAreaManager) ApplicationContextHolder
			.getInstance().getBean("ItawSystemAreaManager");
			list = (ArrayList) mgr.getSonAreaByAreaId(node);

			//取出省公司的地域ID（spring 注入）
			PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
			String province = pnrBaseAreaIdList.getRootAreaId();
			Integer provinceLength = province.length();

			// 取下级节点
			if (null != list) {
 				for (int i =0 ;i<list.size();i++) {
					TawSystemArea tawSystemArea = (TawSystemArea) list.get(i);
					JSONObject item = new JSONObject();
					item.put("id", tawSystemArea.getAreaid()); // 添加地市ID
					item.put("text", tawSystemArea.getAreaname()); // 添加地市名称
					
					
					
					item.put(UIConstants.JSON_NODETYPE, "area");
					/*if(tawSystemArea.getAreaid().length() > provinceLength+2 ){
						item.put(UIConstants.JSON_NODETYPE, "area");
					}else{
						item.put(UIConstants.JSON_NODETYPE, "folder");
					}*/
					item.put("iconCls", "iconCls");
					item.put("areacode", "areacode");
					item.put("capital", "capital");
					
					//item.put("leaf", false);
					
					// 设置是否为叶子节点
					if (province.equals(tawSystemArea.getParentAreaid())) {
						item.put("leaf", false);
					} else {
						item.put("leaf", true);
					}

					root.put(item);
				}
			}			

		} catch (Exception ex) {
			BocoLog.error(this, "生成地域树图时报错：" + ex);
		}
		
		JSONUtil.print(response, root.toString());
		
		return null;
	}
}