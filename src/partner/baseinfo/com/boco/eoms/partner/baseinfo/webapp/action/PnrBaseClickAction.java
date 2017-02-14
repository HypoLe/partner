package com.boco.eoms.partner.baseinfo.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.mgr.TawApparatusMgr;
import com.boco.eoms.partner.baseinfo.mgr.TawPartnerCarMgr;
import com.boco.eoms.partner.baseinfo.mgr.TawPartnerOilMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PartnerDeptConstants;
import com.boco.eoms.partner.baseinfo.util.PartnerUserConstants;
import com.boco.eoms.partner.baseinfo.util.RoleIdList;
import com.boco.eoms.partner.baseinfo.util.TawApparatusConstants;
import com.boco.eoms.partner.baseinfo.util.TawPartnerCarConstants;
import com.boco.eoms.partner.baseinfo.util.TawPartnerOilConstants;
import com.boco.eoms.partner.baseinfo.webapp.form.PartnerDeptForm;
import com.boco.eoms.partner.baseinfo.webapp.form.PartnerUserForm;
import com.boco.eoms.partner.baseinfo.webapp.form.TawApparatusForm;
import com.boco.eoms.partner.baseinfo.webapp.form.TawPartnerCarForm;
import com.boco.eoms.partner.baseinfo.webapp.form.TawPartnerOilForm;

/**
 * <p>
 * Title:����
 * </p>
 * <p>
 * Description:����
 * </p>
 * <p>
 * Mon Feb 09 10:57:12 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() liujinlong
 * @moudle.getVersion() 3.5
 * 
 */
public final class PnrBaseClickAction extends BaseAction {
	TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
	public  static String defaultParentNodeId = "";//当save 或 remove 方法，调用 search方法时，利用这个变量，定位到相应人力信息的树节点下
 
	/**
	 * 合作伙伴树图点击事件（）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerDeptConstants.PARTNERDEPT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
	
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));//地市的nodeid
		String interfaceHeadId = StaticMethod.null2String(request.getParameter("interfaceHeadId"));//厂商总公司id
		String notShowAll = StaticMethod.null2String(request.getParameter("notShowAll"));//不查询总公司
//		查看合作伙伴信息页面
		if(nodeId.length()==7){
	    	return newExpert(mapping, form, request, response);
		}

		if(nodeId.length()==9){
			String typeNode = nodeId.substring(8);
			String isCity = "no";
			AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
			AreaDeptTree adt = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);
//			查看该合作伙伴所有人员
			if("1".equals(typeNode)){
				PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
				
				String in = StaticMethod.null2String(request.getParameter("in"));//此条件表示点省、地市、厂商节点的查询，调用search方法。
				request.setAttribute("nodeId",nodeId);
				String whereStr = " where 1=1";
				//当 in 参数为空时 
				if(request.getParameter("areaId")!=null&&!request.getParameter("areaId").equals("")){
					String areaName = areaDeptTreeMgr.getAreaDeptTreeByNodeId(request.getParameter("areaId")).getNodeName();
					whereStr += " and areaName like '%"+areaName+"%'";
				}
				if(!nodeId.equals("")){
					whereStr += " and treeNodeId  = '"+nodeId + "'";
					request.setAttribute("treeNodeId", nodeId);
					
					//----------------
					PartnerUserForm partnerUserForm = new PartnerUserForm();
					List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
					List listName = new ArrayList();
					List list_id=new ArrayList();
					for(int i=0;i<listId.size();i++){
						String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
						listName.add(areaDeptTreeMgr.id2Name(tempId));
						list_id.add(tempId);
					}
					request.setAttribute("partnerUserForm", partnerUserForm);
					request.setAttribute("listName", listName);
					request.setAttribute("listId", list_id);
			    	AreaDeptTree user = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);//人力信息节点
			    	AreaDeptTree factory = areaDeptTreeMgr.getAreaDeptTreeByNodeId(user.getParentNodeId());//人力信息父节点——厂商（部门），利用这个对象得到厂商名，给字段所属公司赋值
			    	AreaDeptTree area = areaDeptTreeMgr.getAreaDeptTreeByNodeId(factory.getParentNodeId());
			    	partnerUserForm.setDeptId(factory.getNodeId());
			    	partnerUserForm.setAreaId(area.getNodeId());
			    	//---------------------
			    	
				}
				//人力信息管理员才能批量删除 2009-7-29
				String hasRightForDel = "0";
				TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");	
				//2009-5-26 新建类RoleIdList，定义角色属性
				RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
				if(sessionForm.getUserid().equals("admin")){
					hasRightForDel = "1";
				}else{
					List roleList = sessionForm.getRolelist();
					for(int i=0;i<roleList.size();i++){
						TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
						//2009-5-26 登陆用户角色是合作伙伴人力信息管理员，才能删除
						if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){//PartnerUserConstants.PARTNER_USER_ROLEID
							hasRightForDel = "1";
						}
					}
				}
				
				Map map = (Map) partnerUserMgr.getPartnerUsers(pageIndex, pageSize, whereStr);
				List list = (List) map.get("result");
				request.setAttribute(PartnerUserConstants.PARTNERUSER_LIST, list);
				request.setAttribute("resultSize", map.get("total"));
				request.setAttribute("pageSize", pageSize);
				return mapping.findForward("listUser");	
			} else if ("2".equals(typeNode)){
//				仪器仪表查询页面
				TawApparatusForm tawApparatusForm = new TawApparatusForm();
				String type = adt.getNodeType();
				List listName = new ArrayList();
				List list_id=new ArrayList();
				if(type.equals("factory")){
					tawApparatusForm.setDept_id(nodeId);
					tawApparatusForm.setArea_id(adt.getParentNodeId());
					isCity="yes";
				}else if(type.equals("area")){
					tawApparatusForm.setArea_id(nodeId);
					tawApparatusForm.setDept_id("");
					
				}else if(type.equals("province")){
					listName.add(" ");
					list_id.add(" ");
				}else{
					isCity="yes";
					AreaDeptTree adt2 = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId.substring(0,7));
					tawApparatusForm.setDept_id(nodeId.substring(0,7));
					tawApparatusForm.setArea_id(adt2.getParentNodeId());
					nodeId=nodeId.substring(0,7);
				}
				//vvvvvvvvvvvvvvvv
				
				List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
				for(int i=0;i<listId.size();i++){
					String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
					listName.add(areaDeptTreeMgr.id2Name(tempId));
					list_id.add(tempId);
				}
				request.setAttribute("tawApparatusForm", tawApparatusForm);
				updateFormBean(mapping, request, tawApparatusForm);
				request.setAttribute("listName", listName);
				request.setAttribute("listId", list_id);
				//vvvvvvvvvvvv
				String pageIndexNameTawApparatus = new org.displaytag.util.ParamEncoder(
						TawApparatusConstants.TAWAPPARATUS_LIST)
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
				final Integer pageSizeTawApparatus = UtilMgrLocator.getEOMSAttributes()
						.getPageSize();
				final Integer pageIndexTawApparatus = new Integer(GenericValidator
						.isBlankOrNull(request.getParameter(pageIndexNameTawApparatus)) ? 0
						: (Integer.parseInt(request.getParameter(pageIndexNameTawApparatus)) - 1));
				String whereStr = " and dept_id like '"+nodeId+"%'";
				TawApparatusMgr tawApparatusMgr = (TawApparatusMgr) getBean("tawApparatusMgr");
				Map map = (Map) tawApparatusMgr.getTawApparatuss(pageIndexTawApparatus, pageSizeTawApparatus,
						whereStr);
				List list = (List) map.get("result");
				request.setAttribute(TawApparatusConstants.TAWAPPARATUS_LIST, list);
				request.setAttribute("resultSize", map.get("total"));
				request.setAttribute("pageSize", pageSize);
				request.setAttribute("nodeId", nodeId);
				request.setAttribute("isCity", isCity);
				return mapping.findForward("tawApparatusList");

			} else if ("3".equals(typeNode)){
//				车辆查询页面
				TawPartnerCarForm tawPartnerCarForm = new TawPartnerCarForm();
				String type = adt.getNodeType();
				List listName = new ArrayList();
				List list_id=new ArrayList();
				if(type.equals("factory")){
					tawPartnerCarForm.setDept_id(nodeId);
					tawPartnerCarForm.setArea_id(adt.getParentNodeId());
					isCity="yes";
				}else if(type.equals("area")){
					tawPartnerCarForm.setArea_id(nodeId);
					tawPartnerCarForm.setDept_id("");
					
				}else if(type.equals("province")){
					listName.add(" ");
					list_id.add(" ");
				}else{
					isCity="yes";
					AreaDeptTree adt2 = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId.substring(0,7));
					tawPartnerCarForm.setDept_id(nodeId.substring(0,7));
					tawPartnerCarForm.setArea_id(adt2.getParentNodeId());
					nodeId=nodeId.substring(0,7);
				}
//				vvvvvvvvvvvvvvvv
				List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
				for(int i=0;i<listId.size();i++){
					String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
					listName.add(areaDeptTreeMgr.id2Name(tempId));
					list_id.add(tempId);
				}
				updateFormBean(mapping, request, tawPartnerCarForm);
				request.setAttribute("tawPartnerCarForm", tawPartnerCarForm);
				request.setAttribute("listName", listName);
				request.setAttribute("listId", list_id);
				//vvvvvvvvvvvv
				String pageIndexNameTawPartnerCar = new org.displaytag.util.ParamEncoder(
						TawPartnerCarConstants.TAWPARTNERCAR_LIST)
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
				final Integer pageSizeTawPartnerCar = UtilMgrLocator.getEOMSAttributes()
						.getPageSize();
				final Integer pageIndexTawPartnerCar = new Integer(GenericValidator
						.isBlankOrNull(request.getParameter(pageIndexNameTawPartnerCar)) ? 0
						: (Integer.parseInt(request.getParameter(pageIndexNameTawPartnerCar)) - 1));
				String whereStr = " and dept_id like '"+nodeId+"%'";
				TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
				Map map = (Map) tawPartnerCarMgr.getTawPartnerCars(pageIndexTawPartnerCar, pageSizeTawPartnerCar, whereStr);
				List list = (List) map.get("result");
				request.setAttribute(TawPartnerCarConstants.TAWPARTNERCAR_LIST, list);
				request.setAttribute("resultSize", map.get("total"));
				request.setAttribute("pageSize", pageSize);
				request.setAttribute("nodeId", nodeId);
				request.setAttribute("isCity", isCity);
				return mapping.findForward("tawPartnerCarList");
			} else if ("4".equals(typeNode)){
//				油机查询页面
				TawPartnerOilForm tawPartnerOilForm = new TawPartnerOilForm();
				String type = adt.getNodeType();
				List listName = new ArrayList();
				List list_id=new ArrayList();
				if(type.equals("factory")){
					tawPartnerOilForm.setDept_id(nodeId);
					tawPartnerOilForm.setArea_id(adt.getParentNodeId());
					isCity="yes";
				}else if(type.equals("area")){
					tawPartnerOilForm.setArea_id(nodeId);
					tawPartnerOilForm.setDept_id("");
					
				}else if(type.equals("province")){
					listName.add(" ");
					list_id.add(" ");
				}else{
					isCity="yes";
					AreaDeptTree adt2 = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId.substring(0,7));
					tawPartnerOilForm.setDept_id(nodeId.substring(0,7));
					tawPartnerOilForm.setArea_id(adt2.getParentNodeId());
					nodeId=nodeId.substring(0,7);
				}
				//vvvvvvvvvvvvvvvv
				
				List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
				for(int i=0;i<listId.size();i++){
					String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
					listName.add(areaDeptTreeMgr.id2Name(tempId));
					list_id.add(tempId);
				}
				request.setAttribute("tawPartnerOilForm", tawPartnerOilForm);
				updateFormBean(mapping, request, tawPartnerOilForm);
				request.setAttribute("listName", listName);
				request.setAttribute("listId", list_id);
				//vvvvvvvvvvvv
				String pageIndexNameTawPartnerOil = new org.displaytag.util.ParamEncoder(
						TawPartnerOilConstants.TAWPARTNEROIL_LIST)
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
				final Integer pageSizeTawPartnerOil = UtilMgrLocator.getEOMSAttributes()
						.getPageSize();
				final Integer pageIndexTawPartnerOil = new Integer(GenericValidator
						.isBlankOrNull(request.getParameter(pageIndexNameTawPartnerOil)) ? 0
						: (Integer.parseInt(request.getParameter(pageIndexNameTawPartnerOil)) - 1));
				String whereStr = " and dept_id like '"+nodeId+"%'";
				TawPartnerOilMgr tawPartnerOilMgr = (TawPartnerOilMgr) getBean("tawPartnerOilMgr");
				Map map = (Map) tawPartnerOilMgr.getTawPartnerOils(pageIndexTawPartnerOil, pageSizeTawPartnerOil, whereStr);
				List list = (List) map.get("result");
				request.setAttribute(TawPartnerOilConstants.TAWPARTNEROIL_LIST, list);
				request.setAttribute("resultSize", map.get("total"));
				request.setAttribute("pageSize", pageSize);
				request.setAttribute("nodeId", nodeId);
				request.setAttribute("isCity", isCity);
				
				return mapping.findForward("tawPartnerOilList");
			}
		}
    	//加载当前用户所属地市
    	String userid = this.getUser(request).getUserid();
    	List city = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", city);
    	//查询条件：地市
    	String region = request.getParameter("city");
    	//查询条件：县区
    	String county = request.getParameter("county");
    	//查询条件：合作伙伴
    	String nameSearch = request.getParameter("nameSearch");
    	
    	
		String whereStr = " where 1=1";
		if(!nodeId.equals("")){
			whereStr += " and treeNodeId in (select nodeId from AreaDeptTree tree where tree.parentNodeId = "+nodeId+")";
			request.setAttribute("parentNodeId", nodeId);
		}
		else if(this.defaultParentNodeId!=null&&!this.defaultParentNodeId.equals("")){
			whereStr += " and treeNodeId in (select nodeId from AreaDeptTree tree where tree.parentNodeId = "+this.defaultParentNodeId+ ")";
			request.setAttribute("parentNodeId", this.defaultParentNodeId);
			this.defaultParentNodeId = "";
		}
		//组装查询条件
		PartnerDeptForm partnerDeptForm = (PartnerDeptForm) form;
//		if(request.getParameter("nameSearch")!=null&&!request.getParameter("nameSearch").equals("")){
//			whereStr += " and name like '%"+request.getParameter("nameSearch")+"%'";
//		}
		if(request.getParameter("managerSearch")!=null&&!request.getParameter("managerSearch").equals("")){
			whereStr += " and manager like '%"+request.getParameter("managerSearch")+"%'";
		}
		if(request.getParameter("phoneSearch")!=null&&!request.getParameter("phoneSearch").equals("")){
			whereStr += " and phone like '%"+request.getParameter("phoneSearch")+"%'";
		}
		if(region!=null && !region.equals("")){
			whereStr += " and area_id = '" + region + "'";
		}

		if(county!=null && !county.equals("")){
			whereStr += " and city = '"+ county +"'";
		}
		if(nameSearch!=null && !nameSearch.equals("")){
			whereStr += " and name = '"+ nameSearch +"'";
		}

		if(interfaceHeadId!=null && !interfaceHeadId.equals("")){
			whereStr += " and interfaceHeadId = '" + interfaceHeadId + "'";
		}
		if("1".equals(notShowAll)){
			whereStr += " and id <> '" + interfaceHeadId + "'";
		}
	
		Map map = (Map) partnerDeptMgr.getPartnerDepts(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PartnerDeptConstants.PARTNERDEPT_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		
		return mapping.findForward("list");
	}
	/*
	 * 新增合作伙伴
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward newExpert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	request.setAttribute("methodName", "detail");
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));//nodeId 是地市的nodeId
		String id = StaticMethod.null2String(request.getParameter("id"));
		request.setAttribute("nodeId", nodeId);
		if(nodeId.length()>=7){
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			PartnerDept partnerDept = partnerDeptMgr.getPartnerDeptByTreeNodeId(nodeId);
			request.setAttribute("proId1", partnerDept.getId());
		}
		if(!id.equals("")){
			request.setAttribute("proId1", id);
		}
		return mapping.findForward("newExpert");
	}	

    
}