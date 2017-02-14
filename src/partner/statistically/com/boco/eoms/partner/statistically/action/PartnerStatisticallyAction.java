package com.boco.eoms.partner.statistically.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
import com.boco.eoms.partner.statistically.utils.TableHelper;

public class PartnerStatisticallyAction extends BaseAction {
	
	
	//go to the staff statistics page .
	public ActionForward goToStaffShowPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return   mapping.findForward("goToStaffShowPage");
	}
	
	//go to the Company statistics page .
	public ActionForward goToCompanyShowPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
		 * 定义取出专业集合，使用公共字典取得数据
		 */
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
		request.setAttribute("specialtyList", specialtyList);
		return   mapping.findForward("goToCompanyShowPage");
	}
	//go to the Apparatus statistics page .
	public ActionForward goToApparatusShowPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return   mapping.findForward("goToApparatusShowPage");
	}
	//go to the Car statistics page .
	public ActionForward goToCarShowPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return   mapping.findForward("goToCarShowPage");
	}
	
	// 人员统计的List显示方法
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rows[] = StaticMethod.nullObject2String(
				request.getParameter("deleteIds"), "").split(";");
		String checkString = StaticMethod.nullObject2String(
				request.getParameter("checks"), "");
		String search = "";
		String group="";
		//get the search column .change the '0' to '.'
		for (int i = 0; i < rows.length; i++) {
			String row="" ;
			if(rows[i].contains("TypeLikedict")){
				row=rows[i].substring(0,rows[i].length()-"TypeLikedict".length());
				row=row.replace("0", ".");
			}
			else{
			row = rows[i].replace("0", ".");
			}
			
			if (i == rows.length - 1) {
				search += row +" as "+rows[i] ;
				group+= row;
			} else {
				search += row + " as "+rows[i]+",";
				group+= row+",";
			}

		}
		//get the text to where condition.
		String whereCondition =" ";
		String dtname=request.getParameter("dt0nameT");
		String dareaname=request.getParameter("d0area_nameT");
		String dname=request.getParameter("d0nameT");
		String udiploma=request.getParameter("u0diplomaT");
		String usex=request.getParameter("u0sexT");
		if(!"".equals(dtname)){
			whereCondition+=" and dt.name = '"+dtname+"'";
		}
		if(!"".equals(dareaname)){
			whereCondition+=" and d.area_name = '"+dareaname+"'";
		}
		if(!"".equals(dname)){
			whereCondition+=" and d.name = '"+dname+"'";
		}
		if(!"".equals(udiploma)){
			whereCondition+=" and u.diploma = '"+udiploma+"'";
		}
		if(!"".equals(usex)){
			whereCondition+=" and u.sex = '"+usex+"'";
		}
		
		String searchSql = "select " +
				search +
				" ,count(u.name) from pnr_user u,pnr_dept d,(select name,id,deleted from pnr_dept where id = interface_head_id) as dt  " +
				" where  u.partnerid=d.id and dt.id=d.interface_head_id and dt.deleted='0' and d.deleted='0' and u.deleted='0' " +whereCondition+
				" group by " +
				group +
				" order by  " +
				group;
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if("dt0name".equals(rows[i])){
				headList.add("合作伙伴公司（省）");
			}
			else if("d0area_name".equals(rows[i])){
				headList.add("所属地市");
			}
			else if("d0name".equals(rows[i])){
				headList.add("合作伙伴（地市州）分公司");
			}
			else if("u0diplomaTypeLikedict".equals(rows[i])){
				headList.add("学历");
			}
			else if("u0sexTypeLikedict".equals(rows[i])){
				headList.add("性别");
			}
			
			
			
		}
		
		headList.add("人员总数");

		List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(rows, searchSql,"/partner/statistically/paternerStaffSearch.do?method=list");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);

		return mapping.findForward("paternerStaffList");
	}
	
	//公司统计的CompanyList方法
	public ActionForward companyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rows[] = StaticMethod.nullObject2String(
				request.getParameter("deleteIds"), "").split(";");
		String checkString = StaticMethod.nullObject2String(
				request.getParameter("checks"), "");
		String search = "";
		String group="";
		//get the search column .change the '0' to '.'
		for (int i = 0; i < rows.length; i++) {
			String row="" ;
			if(rows[i].contains("TypeLikedict")){
				row=rows[i].substring(0,rows[i].length()-"TypeLikedict".length());
				row=row.replace("0", ".");
			}
			else if(rows[i].contains("TypeFor")){
				row=rows[i].substring(0,rows[i].length()-"TypeFor".length());
				row=row.replace("0", ".");
			}
			else{
			row = rows[i].replace("0", ".");
			}
			
			if (i == rows.length - 1) {
				search += row +" as "+rows[i] ;
				group+= row;
			} else {
				search += row + " as "+rows[i]+",";
				group+= row+",";
			}

		}
		//get the text to where condition.
		String whereCondition =" ";
		String dtname=request.getParameter("dt0nameT");
		String dareaname=request.getParameter("d0area_nameT");
		// 设置专业
		String[] bigType = request.getParameterValues("d0big_typeT");
		String selectedLevel=request.getParameter("d0selectedLevelT");
		String fund=request.getParameter("d0fundT");
		String type=request.getParameter("d0typeT");
		
		
		if(bigType!=null){
		StringBuffer bigTypeString = new StringBuffer();
		for (int i = 0; i < bigType.length; i++) {
			bigTypeString.append(bigType[i]);
			bigTypeString.append(",");
		}
		String dtype= bigTypeString.toString();
		if(!"".equals(dtype)&&null!=dtype){
			whereCondition+=" and d.big_type like '%"+dtype+"%'";
		}
		}
		if(!"".equals(dtname)&&null!=dtname){
			whereCondition+=" and dt.name = '"+dtname+"'";
		}
		if(!"".equals(dareaname)&&null!=dareaname){
			whereCondition+=" and d.area_name = '"+dareaname+"'";
		}
		if(!"".equals(dareaname)&&null!=dareaname){
			whereCondition+=" and d.area_name = '"+dareaname+"'";
		}
		if(!"".equals(selectedLevel)&&null!=selectedLevel){
			whereCondition+=" and d.selectedLevel = '"+selectedLevel+"'";
		}
		if(!"".equals(type)&&null!=type){
			whereCondition+=" and d.type = '"+type+"'";
		}
		if(!"".equals(fund)&&null!=fund){
			if("1".equals(fund)){
			whereCondition+=" and d.fund <100 ";
			}
			if("2".equals(fund)){
				whereCondition+=" and d.fund >= 100 and d.fund <500 ";
				}
			if("3".equals(fund)){
				whereCondition+=" and d.fund >=500 and d.fund <1000 ";
				}
			if("4".equals(fund)){
				whereCondition+=" and d.fund >=1000  ";
				}
		}
		
		String searchSql = "select "+
		search +
		",count(d.id) from pnr_dept as d , (select name,id,deleted,ifcompany,organizationno from pnr_dept where id = interface_head_id and ifcompany='yes' and organizationno is not null) as dt where  d.deleted='0' and dt.deleted='0'  and   d.interface_head_id=dt.id and d.ifcompany=dt.ifcompany and d.selectedLevel is not null and d.fund is not null  " +whereCondition+
		" group by " +
		group +
		" order by  " +
		group;;
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if("dt0name".equals(rows[i])){
				headList.add("组织");
			}
			else if("d0area_name".equals(rows[i])){
				headList.add("所属地市");
			}
			else if("d0big_typeTypeFor".equals(rows[i])){
				headList.add("专业");
			}
			else if("d0selectedLevelTypeLikedict".equals(rows[i])){
				headList.add("入围级别");
			}
			else if("d0typeTypeLikedict".equals(rows[i])){
				headList.add("企业性质");
			}
			else if("d0fund".equals(rows[i])){
				headList.add("注册资金（万）");
			}
			
		}
		headList.add("公司总数");
		
		List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(rows, searchSql,"/partner/statistically/paternerCompanySearch.do?method=list");
		/**
		 * 定义取出专业集合，使用公共字典取得数据
		 */
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
		request.setAttribute("specialtyList", specialtyList);
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);
		return mapping.findForward("paternerCompanyList");
		
	}
	//仪器仪表统计方法
	public ActionForward apparatusList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rows[] = StaticMethod.nullObject2String(
				request.getParameter("deleteIds"), "").split(";");
		String checkString = StaticMethod.nullObject2String(
				request.getParameter("checks"), "");
		String search = "";
		String group="";
		//get the search column .change the '0' to '.'
		for (int i = 0; i < rows.length; i++) {
			String row="" ;
			if(rows[i].contains("TypeLikedict")){
				row=rows[i].substring(0,rows[i].length()-"TypeLikedict".length());
				row=row.replace("0", ".");
			}
			else{
			row = rows[i].replace("0", ".");
			}
			
			if (i == rows.length - 1) {
				search += row +" as "+rows[i] ;
				group+= row;
			} else {
				search += row + " as "+rows[i]+",";
				group+= row+",";
			}
		}
		//get the text to where condition.
		String whereCondition =" ";
		String dtname=request.getParameter("dt0nameT");
		String dareaname=request.getParameter("d0area_nameT");
		String dname=request.getParameter("d0nameT");
		String apptype=request.getParameter("app0typeT");
		if(!"".equals(dtname)&&null!=dtname){
			whereCondition+=" and dt.name like '%"+dtname+"%'";
		}
		if(!"".equals(dareaname)&&null!=dareaname){
			whereCondition+=" and d.area_name = '"+dareaname+"'";
		}
		if(!"".equals(dname)&&null!=dname){
			whereCondition+=" and d.name = '"+dname+"'";
		}if(!"".equals(apptype)&&null!=apptype){
			whereCondition+=" and app.type = '"+apptype+"'";
		}
		String searchSql = "select "+
		search +
		",count(app.id) from  pnr_dept as d, pnr_partner_apparatus app  , (select name,id,deleted from pnr_dept where id = interface_head_id) as dt where d.id =app.partnerid and  d.deleted='0' and dt.deleted='0'and  app.bigpartnerid=dt.id  " +whereCondition+
		" group by " +
		group +
		" order by  " +
		group;;
		
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if("dt0name".equals(rows[i])){
				headList.add("合作伙伴公司（省）");
			}
			else if("d0area_name".equals(rows[i])){
				headList.add("所属地市");
			}
			else if("d0name".equals(rows[i])){
				headList.add("合作伙伴（地市州）分公司");
			}
			else if("app0typeTypeLikedict".equals(rows[i])){
				headList.add("仪表类型");
			}
		}
		headList.add("统计数");
		
		List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(rows, searchSql,"/partner/statistically/paternerApparatusSearch.do?method=list");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);
		return mapping.findForward("paternerApparatusList");
	}
	//车辆信息统计
	public ActionForward carList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rows[] = StaticMethod.nullObject2String(
				request.getParameter("deleteIds"), "").split(";");
		String checkString = StaticMethod.nullObject2String(
				request.getParameter("checks"), "");
		String search = "";
		String group="";
		//get the search column .change the '0' to '.'
		for (int i = 0; i < rows.length; i++) {
			String row="" ;
			if(rows[i].contains("TypeLikedict")){
				row=rows[i].substring(0,rows[i].length()-"TypeLikedict".length());
				row=row.replace("0", ".");
			}
			else{
			row = rows[i].replace("0", ".");
			}
			
			if (i == rows.length - 1) {
				search += row +" as "+rows[i] ;
				group+= row;
			} else {
				search += row + " as "+rows[i]+",";
				group+= row+",";
			}
		}
		//get the text to where condition.
		String whereCondition ="";
		String dtname=request.getParameter("dt0nameT");
		String dareaname=request.getParameter("d0area_nameT");
		String dname=request.getParameter("d0nameT");
		String apptype=request.getParameter("app0typeT");
		if(!"".equals(dtname)&&null!=dtname){
			whereCondition+=" and dt.name = '"+dtname+"'";
		}
		if(!"".equals(dareaname)&&null!=dareaname){
			whereCondition+=" and d.area_name = '"+dareaname+"'";
		}
		if(!"".equals(dname)&&null!=dname){
			whereCondition+=" and d.name = '"+dname+"'";
		}if(!"".equals(apptype)&&null!=apptype){
			whereCondition+=" and app.type = '"+apptype+"'";
		}
		String searchSql="select " +search+
				"  ,count(c.id) from  pnr_dept as d, pnr_partner_car c  , (select name,id,deleted from pnr_dept where id = interface_head_id) as dt where d.id =c.partnerid and  d.deleted='0' and dt.deleted='0'and  c.bigpartnerid=dt.id " +whereCondition
				+" group by " +
				group +
				" order by  " +
				group;
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if("dt0name".equals(rows[i])){
				headList.add("合作伙伴公司（省）");
			}
			else if("d0area_name".equals(rows[i])){
				headList.add("所属地市");
			}
			else if("d0name".equals(rows[i])){
				headList.add("合作伙伴（地市州）分公司");
			}
			else if("c0useTypeLikedict".equals(rows[i])){
				headList.add("车辆用途");
			}
		}
		headList.add("统计数");
		List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(rows, searchSql,"/partner/statistically/PartnerCarSearch.do?method=list");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);
		return mapping.findForward("paternerCarList");

	}
	//构造部门树的方法
	public ActionForward dept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type=StaticMethod.null2String(request.getParameter("type"),"");
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		
		ArrayList list = new ArrayList();

		try {
			TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
			TawSystemDept sept = deptbo.getDeptinfobydeptid(node, "0");

			if (sept.getTmpdeptid() != null && !"".equals(sept.getTmpdeptid())) {
				list = (ArrayList) deptbo.getNextLevecDepts(
						sept.getTmpdeptid(), "0");
			} else {
				list = (ArrayList) deptbo.getNextLevecDepts(node, "0");
			}
		} catch (Exception ex) {
			BocoLog.error(this, "生成部门树图时报错：" + ex);
		}

		request.setAttribute("list", list);
		if(type.equals("parent")){
			return mapping.findForward("writeParentJson");
		}else  if(type.equals("all")){
			return mapping.findForward("writeAllParentJson");
		}
		else{
			return mapping.findForward("writeJson");
		}
	}
	/**
	 * 
	 *@Description 根据登录人员身份获取代维人员信息,以前版本没有添加权限本次修改新增了权限
	 *@date Apr 24, 2013 5:40:55 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param mapping
	 *@param form
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward user(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//0自维 1代维
		int isPartner = Integer.parseInt(request.getParameter("isPartner"));
		String node = StaticMethod.null2String(request.getParameter("node"),StaticVariable.ProvinceID + "");
		ArrayList<PartnerUser> userlist = new ArrayList<PartnerUser>();
		ArrayList<PartnerDept> deptlist = new ArrayList<PartnerDept>();
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		PartnerDept dept=partnerDeptMgr.getPartnerDeptByDeptMagId(node);//在人员树的最上级无法的node根据配置文件中的值来获取
		if (dept!= null &&dept.getId()!=null) {//如果在pnr_dept里面找到该值
			Map  map=PartnerPrivUtils.userIsPersonnel(request);
			String isPersonnel=map.get("isPersonnel").toString();//身份标识;
			if ("admin".equals(isPersonnel)) {//管理员身份;
				// deptlist = (ArrayList) deptbo.getNextLevecComp(dept.getId(), "0","",-1);
				deptlist = (ArrayList) deptbo.getNextLevecComp(dept.getId(), "0","",isPartner);
				userlist =  (ArrayList<PartnerUser>) partnerUserMgr.getPartnerUsers(" and dept_id = '"+node+"'");
			}else if ("y".equals(isPersonnel)) {//代维人员
				String deptid=map.get("deptMagId").toString();
			//	deptlist = (ArrayList) deptbo.getNextLevecComp(dept.getId(), "0",deptid,-1);
				deptlist = (ArrayList) deptbo.getNextLevecComp(dept.getId(), "0",deptid,isPartner);
				userlist =  (ArrayList<PartnerUser>) partnerUserMgr.getPartnerUsers(" and dept_id = '"+node+"' and dept_id like '"+deptid+"%' ");
			}else if ("n".equals(isPersonnel)) {//移动人员
				PartnerDeptMgr deptmag = (PartnerDeptMgr) ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
				String areaId=map.get("areaId").toString();
			//	deptlist =(ArrayList) deptbo.getNextLevecCompByAreaid(dept.getId(), "0",areaId,-1);
				deptlist =(ArrayList) deptbo.getNextLevecCompByAreaid(dept.getId(), "0",areaId,isPartner);
				userlist =  (ArrayList<PartnerUser>) partnerUserMgr.getPartnerUsers(" and dept_id = '"+node+"' and area_id like '"+areaId+"%'");
			}
		} else {
			TawSystemDept tawSystemDept=deptbo.getDeptinfobydeptid(node, "0");
			String parentDeptid="";
			if (tawSystemDept!=null&!"".equals(tawSystemDept.getParentDeptid())) {
				parentDeptid=tawSystemDept.getParentDeptid();//获取部门表里面的根目录的deptid一般都是"-1";
			}
			//deptlist = (ArrayList) deptbo.getNextLevecComp(parentDeptid, "0","",-1);
			deptlist = (ArrayList) deptbo.getNextLevecComp(parentDeptid, "0","",isPartner);
		}
		JSONArray json = new JSONArray();
		if (deptlist.size() > 0){
	    	for (int i = 0; i < deptlist.size(); i++) {
	    		PartnerDept subDept = (PartnerDept) deptlist.get(i);			
				JSONObject jitem = new JSONObject();
				jitem.put(UIConstants.JSON_ID, subDept.getDeptMagId());
				jitem.put(UIConstants.JSON_TEXT, subDept.getName());
				jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_DEPT);
				jitem.put("iconCls", "dept");
				json.put(jitem);				
			}
		}
		if (userlist.size() > 0) {
			for (int j = 0; j < userlist.size(); j++) {
				PartnerUser user = (PartnerUser) userlist.get(j);			
				JSONObject jitem = new JSONObject();
				jitem.put(UIConstants.JSON_ID, user.getUserId());
				jitem.put(UIConstants.JSON_TEXT, user.getName());
				jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_USER);
				jitem.put("leaf", 1);
				jitem.put("iconCls", "user");
				jitem.put("mobile",user.getMobilePhone());
				jitem.put("uuid",user.getId());
				json.put(jitem);
			}
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
		
	}
	
	
}
