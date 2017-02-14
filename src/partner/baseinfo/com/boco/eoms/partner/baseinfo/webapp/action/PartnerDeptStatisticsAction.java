package com.boco.eoms.partner.baseinfo.webapp.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.util.PartnerDeptConstants;
import com.boco.eoms.partner.baseinfo.util.PnrDeptStatisticsTableHelper;
import com.boco.eoms.partner.home.util.HomeHelper;
import com.boco.eoms.partner.personnel.util.PageData;
import com.boco.eoms.partner.personnel.util.SearchUtil;
import com.boco.eoms.partner.property.util.StatisticsResultExport;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
import com.boco.eoms.partner.statistically.utils.TableHelper;
import com.informix.msg.isam_en_US;
public class PartnerDeptStatisticsAction extends BaseAction{
	/**
	 * 
	* @Title: goToStatisticsPage 
	* @Description: 根据不同参数进入不同的统计页面；
	* @param 
	* @Time:Dec 6, 2012-5:34:18 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward goToStatisticsPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		String whichPage=StaticMethod.null2String(request.getParameter("whichPage"));
		/**0 代维，1是自维*/
		String isPartner=StaticMethod.null2String(request.getParameter("isPartner"));
		request.getSession().setAttribute("isPartner", isPartner);
		/**
    	 * 根据当前省ID，列出所有地市,省份
    	 */
    	request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
    	request.setAttribute("city", StaticMethod.getUserCityAreaList());
		if ("pnrDeptCount".equals(whichPage)) {//进入组织数量统计页面
			return mapping.findForward("pnrDeptCountStatistics");
		}else if ("pnrDeptFund".equals(whichPage)) {//进入注册资本统计页面
			return mapping.findForward("pnrDeptFundStatistics");
		}else if ("pnrDeptSelectedLevel".equals(whichPage)) {//进入组织入围统计页面
			return mapping.findForward("pnrDeptSelectedLevelStatistics");
		}else if ("pnrDeptType".equals(whichPage)) {//进入企业性质统计页面
			return mapping.findForward("pnrDeptTypeStatistics");
		}else if ("pnrDept".equals(whichPage)) {//进入代维组织统计页面
			request.setAttribute("statistics", "1");
			return mapping.findForward("pnrDeptStatistics");
		}
		return mapping.findForward("fail");
	}
	/**
	 * 
	* @Title: pnrDetpCountStatistics 
	* @Description: 组织数量统计
	* @param 
	* @Time:Dec 6, 2012-5:36:18 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward pnrDetpStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		//数值转化为字符串，作为sql的search条件
		//获取where条件值
		String areaName=StaticMethod.null2String(request.getParameter("areaName"));
		String professional=StaticMethod.null2String(request.getParameter("professional"));
		String deptType=StaticMethod.null2String(request.getParameter("deptType"));
		String contactor=StaticMethod.null2String(request.getParameter("contactor"));
		String manager=StaticMethod.null2String(request.getParameter("manager"));
		String phone=StaticMethod.null2String(request.getParameter("phone"));
		String companyName=StaticMethod.null2String(request.getParameter("companyName"));
		String companyId=StaticMethod.null2String(request.getParameter("companyId"));
		String companyDeptId=StaticMethod.null2String(request.getParameter("companyDeptId"));
		String deptMagIdRe=StaticMethod.null2String(request.getParameter("deptMagId"));
		String areaId=StaticMethod.null2String(request.getParameter("areaid"));
		String nextDeptLevel=StaticMethod.null2String(request.getParameter("nextDeptLevel"));
		String exportFlag=StaticMethod.null2String(request.getParameter("exportFlag"));
		//0代维 1 自维
		String isPartner = StaticMethod.null2String(request.getParameter("isPartner"));
		
		boolean export=false;
		if ("2".equals(exportFlag)) {
			export=true;
		}
		boolean isArea=false;//是否显示代维区域标识
		boolean isUserNum=false;//是否显示代维人员数量标识
		boolean isProfessional=false;//是否显示代维专业标识
		boolean isBaseInfo=false;//是否显示代维组织基本信息标识
		boolean isQualify=false;//是否显示代维资质信息标识
		boolean isContact=false;//是否显示代维合同信息标识
		//判断哪些项目未被统计,未被统计的项目在统计页面将不显示
		List headList=new ArrayList();//导出标题，顺序不能乱
		headList.add("区域");
		headList.add("代维厂家名称");
		if (checkedValuesStr.contains("isUserNum")) {//
			headList.add("代维人员数量");
			headList.add("通过技能认证的人数");
			isUserNum=true;
		}
		if (checkedValuesStr.contains("isProfessional")) {//
			headList.add("基站设备及配套");
			headList.add("传输线路");
			headList.add("直放站室分");
			headList.add("铁塔及天馈");
			headList.add("集团客户专线");
			headList.add("WLAN");
			headList.add("家庭宽带");
			isProfessional=true;
		}
		if (checkedValuesStr.contains("isArea")) {//
			headList.add("代维区域");
			isArea=true;
		}
		if (checkedValuesStr.contains("isBaseInfo")) {//
			headList.add("工商注册时间");
			headList.add("工商注册地址");
			headList.add("注册资金");
			headList.add("企业性质");
			headList.add("法人代表");
			headList.add("联系人");
			headList.add("联系电话");
			isBaseInfo=true;
		}
		if (checkedValuesStr.contains("isQualify")) {//
			headList.add("资质名称和级别");
			headList.add("资质截止日期");
			isQualify=true;
		}
		if (checkedValuesStr.contains("isContact")) {//
			headList.add("合同签订日期");
			headList.add("合同起始日期");
			headList.add("合同截止日期");
			isContact=true;
		}
		//权限判断
		Map<String,String> map=PartnerPrivUtils.userIsPersonnel(request);
		String userId=map.get("isPersonnel").toString();
		String deptLevel="1";
		String whereQStr="";//代维资质的统计条件
		String whereStr="";
		if (!"".equals(professional)) {
			whereStr+=" and d.big_type like '%"+professional+"%' ";
		}
		if (!"".equals(deptType)) {
			whereStr+=" and d.type='"+deptType+"' ";
		}
		if (!"".equals(manager)) {
			whereStr+=" and d.manager like '%"+manager+"%' ";
		}
		if (!"".equals(contactor)) {
			whereStr+=" and d.contactor like '%"+contactor+"%' ";
		}
		if (!"".equals(phone)) {
			whereStr+=" and d.phone like '%"+phone+"%' ";
		}
		if (!"".equals(areaId)) {
			whereStr+=" and d.area_id like '"+areaId+"%' ";
		}
		if (!"".equals(companyId)) {
			whereStr+=" and d.id='"+companyId+"' ";
		}
		if (!"".equals(deptMagIdRe)) {
			whereStr+=" and d.dept_mag_id like '"+deptMagIdRe+"%' ";
		}
		if (!"".equals(nextDeptLevel)) {
			whereStr+=" and d.deptlevel='"+nextDeptLevel+"' ";
		}
		if (userId.equals("admin")) {
			if ("".equals(nextDeptLevel)) {
				deptLevel="1";
			}
			if ("".equals(whereStr)) {//不为空表示有筛选条件，此时不应该有deptlevel限制
				whereStr+=" and d.deptlevel='"+deptLevel+"' ";
			}
		}else if(userId.equals("y")){//代维人员
			String deptMagId=map.get("deptMagId").toString();
			deptLevel=PartnerPrivUtils.getPartnerDeptLevel(deptMagId)+"";
			if (deptMagId.length()==PartnerPrivUtils.getProvinceDeptLength()) {//省级
				deptLevel="1";
			}else if (deptMagId.length()==PartnerPrivUtils.getCityDeptLength()) {
				deptLevel="2";
			}else if (deptMagId.length()==PartnerPrivUtils.getCountyDeptLength()) {
				deptLevel="3";
			}else if (deptMagId.length()==PartnerPrivUtils.getGroupDeptLength()) {
				deptLevel="4";
			}
			if ("".equals(nextDeptLevel)) {
				deptLevel=deptLevel;
			}
			if (!"".equals(whereStr)) {//不为空表示有筛选条件，此时不应该有deptlevel限制,只有用户权限的控制
				whereStr+=" and d.dept_mag_id like '"+deptMagId+"%' ";
			}else {
				whereStr+=" and d.deptlevel='"+deptLevel+"' and d.dept_mag_id like '"+deptMagId+"%' ";
			}
		}else {//移动人员
			String areaId1=map.get("areaId").toString();
			int len=areaId1.length();
			if (len==PartnerPrivUtils.AreaId_length_Province) {
				deptLevel=PartnerPrivUtils.Level_provinceDept+"";
			}else if (len==PartnerPrivUtils.AreaId_length_City) {
				deptLevel=PartnerPrivUtils.Level_city+"";
			}else if(len==PartnerPrivUtils.AreaId_length_County){
				deptLevel=PartnerPrivUtils.Level_County+"";
			}
			if ("".equals(nextDeptLevel)) {
				deptLevel=deptLevel;
			}
			if (!"".equals(whereStr)) {//不为空表示有筛选条件，此时不应该有deptlevel限制,只有用户权限的控制
				whereStr+=" and d.area_id like '"+areaId1+"%' ";
			}else {
				whereStr+=" and d.deptlevel='"+deptLevel+"' and d.area_id like '"+areaId1+"%' ";
			}
		}
		//0代维 1 自维 
		if(!"".equals(isPartner)){
			   whereStr+=" and d.is_partner="+isPartner+" ";
		}
		/**/
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT * FROM ")
		.append("(SELECT ")
		.append("t2.* ") 
		.append("FROM ")
		.append("(	SELECT ")
		.append("t1.*,")
		.append("s.finalistendtime,s.finalisttime ")
		.append("FROM ")
		.append("(SELECT ")
		.append("COUNT(u.name) AS usernum,d.big_type,d.id,d.area_name,d.area_id,d.name,d.dept_mag_id,d.address,d.fund,d.type,d.contactor,d.phone,d.manager,d.registertime,d.deptlevel")
		.append(" FROM ")
		.append("pnr_dept d  ")
		.append("LEFT JOIN pnr_user u  ")
		.append("ON d.id=u.partnerid AND u.deleted='0'  ")
		.append("WHERE d.deleted='0' ")
		.append(whereStr)
		.append(" GROUP BY ")
		.append("d.id,d.name,d.dept_mag_id,d.area_id,d.area_name,d.address,d.fund,d.type,d.contactor,d.phone,d.manager,d.registertime,d.big_type,d.deptlevel ")
		.append("order BY ")
		.append("d.area_name ")
		.append(")  t1  ")
		.append("LEFT JOIN pnr_org_finalist_sheet s  ")
		.append("ON t1.id=s.orgid AND s.isdeleted='0')         t2)  t4  ")
		.append("LEFT JOIN (	SELECT ")
		.append("COUNT(workerid)as certnum,deptid  ")
		.append("FROM ")
		.append("(	SELECT ")
		.append("DISTINCT(workerid), deptid  ")
		.append("FROM ")
		.append("partner_certificate  C  ")
		.append("WHERE C.isdelete='0')  ")
		.append("GROUP BY deptid)  t3 ON  ")
		.append("t4.dept_mag_id=t3.deptid ");
		System.out.println("统计sql:"+sb.toString());
		List<ListOrderedMap> resultList=jdbcService.queryForList(sb.toString());
		
		List<List> lastResultList=new ArrayList();
		/*统计代维资质信息 begin*/
		List<ListOrderedMap> qualifyList= new ArrayList<ListOrderedMap>();
		if (isQualify) {
			StringBuffer sb1= new StringBuffer();
			sb1.append("select d.id,q.qualify_name,q.qualify_level,q.out_of_date ")
			.append("from pnr_dept d left join pnr_qualification q on ")
			.append("q.related_dept_id=d.id and q.isdeleted='0' ")
			.append("where d.deleted='0' ").append(whereStr);
			System.out.println("统计代维资质信息sql:"+sb1.toString());
			qualifyList=jdbcService.queryForList(sb1.toString());
		}
		/*统计代维资质信息 end*/
		
		/*统计代维区域信息 begin*/
		List<ListOrderedMap> areaList=new ArrayList<ListOrderedMap>();
		if (isArea) {
			StringBuffer sb2=new StringBuffer();
			sb2.append("select id,name,area_name as managearea,dept_mag_id,deptlevel ")
			.append("from pnr_dept ")
			.append(" where  deleted='0' ");
			System.out.println("统计代维区域sql:"+sb2.toString());
			areaList=jdbcService.queryForList(sb2.toString());
		}
		/*统计代维区域信息 end*/
		/*对结果进行处理*/
		ID2NameService service = (ID2NameService) ApplicationContextHolder	.getInstance().getBean("ID2NameGetServiceCatch");
		String provinceAreaName=this.getUser(request).getRootAreaName();
		List<List> exportListMap=new ArrayList<List>();//需要导出的list;
		for (ListOrderedMap r : resultList) {
			/*为结果添加资质信息 begin*/
			String uuid=r.get("id").toString();
			String deptId=r.get("dept_mag_id").toString();
			String qualifyString="";
			String outOfDate="";
			for (ListOrderedMap q : qualifyList) {
				String qualifyName="";
				String qualifyLevel="";
				if (uuid.equals(StaticMethod.nullObject2String(q.get("id")))) {
					if (!"".equals(qualifyString)) {
						qualifyString+="<br>";
					}
					if (!"".equals(outOfDate)) {
						outOfDate+="<br>";
					}
					qualifyName+=StaticMethod.nullObject2String(q.get("qualify_name"));
					qualifyLevel+=StaticMethod.nullObject2String(q.get("qualify_level"));
					if (!"".equals(qualifyName)&&!"".equals(qualifyLevel)) {
						qualifyString+=qualifyName+","+qualifyLevel;
					}
					outOfDate+=StaticMethod.nullObject2String(q.get("out_of_date"));
				}
			}
			r.put("qualify", qualifyString);
			r.put("outOfDate", outOfDate);
			/*为结果添加资质信息 end*/
			/*为结果添加代维区域信息 begin*/
			String manageArea="";
			for (ListOrderedMap q : areaList) {
				String deptid=StaticMethod.nullObject2String(q.get("dept_mag_id"));
				if (deptid.length()>=deptId.length()) {//取大于该地区的
					deptid=deptid.substring(0, deptId.length());//将下级的所有的deptMagId截取的长度和判断的部门长度相同
					if (deptId.equals(deptid)) {//获取所有本公司的代维区域
						String areaNameStr=StaticMethod.nullObject2String(q.get("managearea"));
						if (!"".equals(areaNameStr)&&!manageArea.contains(areaNameStr)&&!areaNameStr.equals(provinceAreaName)) {//如果包含了该区域就不再添加，如果没有包含就将该区域添加进去;
							if (!"".equals(manageArea)) {
								manageArea+=",";
							}
							manageArea+=StaticMethod.nullObject2String(q.get("managearea"));
						}
					}
				}
			}
			r.put("managearea", manageArea);
			/*为结果添加代维区域信息 end*/
			/*为结果处理代维专业的 begin*/
			String pro=StaticMethod.nullObject2String(r.get("big_type"));
			String[] professionals=pro.split(",");
			r.put("pro1", "");
			r.put("pro2", "");
			r.put("pro3", "");
			r.put("pro4", "");
			r.put("pro5", "");
			r.put("pro6", "");
			r.put("pro7", "");
			r.put("pro8", "");
			r.put("pro9", "");
			for (int i = 0; i < professionals.length; i++) {
				String p = professionals[i];
				if ("1122501".equals(p)) {
					r.put("pro1", "√");
				}else if ("1122503".equals(p)) {
					r.put("pro2", "√");
				}else if ("1122504".equals(p)) {
					r.put("pro3", "√");
				}else if ("1122505".equals(p)) {
					r.put("pro4", "√");
				}else if ("1122506".equals(p)) {
					r.put("pro5", "√");
				}else if ("1122507".equals(p)) {
					r.put("pro6", "√");
				}else if ("1122508".equals(p)) {
					r.put("pro7", "√");
				}else if ("1122509".equals(p)) {
					r.put("pro8", "√");
				}else if ("1122510".equals(p)) {
					r.put("pro9", "√");
				}
			}
			/*为结果处理代维专业的 end*/
			List list=new ArrayList();
			List exportSonList=new ArrayList();
			String areaName1=StaticMethod.nullObject2String(r.get("area_name"));//第1列
			String areaId1=StaticMethod.nullObject2String(r.get("area_id"));//第2列
			String deptUUid=StaticMethod.nullObject2String(r.get("id"));//第3列
			String deptMagId=StaticMethod.nullObject2String(r.get("dept_mag_id"));//第4列
			String deptName=StaticMethod.nullObject2String(r.get("name"));//第5列
			String userNum=StaticMethod.nullObject2String(r.get("usernum"),"0");//第6列
			String certNum=StaticMethod.nullObject2String(r.get("certnum"),"0");//第7列
			if ("".equals(certNum)) {
				certNum="0";
			}
			String pro1=StaticMethod.nullObject2String(r.get("pro1"));//第8列
			String pro2=StaticMethod.nullObject2String(r.get("pro2"));//第9列
			String pro3=StaticMethod.nullObject2String(r.get("pro3"));//第10列
			String pro4=StaticMethod.nullObject2String(r.get("pro4"));//第11列
			String pro5=StaticMethod.nullObject2String(r.get("pro5"));//第12列
			String pro6=StaticMethod.nullObject2String(r.get("pro6"));//第13列
			String pro7=StaticMethod.nullObject2String(r.get("pro7"));//第14列
			String pro8=StaticMethod.nullObject2String(r.get("pro8"));//第14列 后加的
			String pro9=StaticMethod.nullObject2String(r.get("pro9"));//第14列 后加的
			String manageArea1=StaticMethod.nullObject2String(r.get("managearea"));//第15列
			String registTime=StaticMethod.nullObject2String(r.get("registertime"));//第16列
			String registAddr=StaticMethod.nullObject2String(r.get("address"));//第17列
			String fund=StaticMethod.nullObject2String(r.get("fund"));//第18列
			String companyType=StaticMethod.nullObject2String(r.get("type"));//第19列
			companyType=service.id2Name(companyType, "tawSystemDictTypeDao");
			String manager1=StaticMethod.nullObject2String(r.get("manager"));//第20列
			String contactor1=StaticMethod.nullObject2String(r.get("contactor"));//第21列
			String phone1=StaticMethod.nullObject2String(r.get("phone"));//第22列
			String qualifyNameAndLevel=StaticMethod.nullObject2String(r.get("qualify"));//第23列
			String qualifyOutOfDate=StaticMethod.nullObject2String(r.get("outOfDate"));//第24列
			String signDate=StaticMethod.nullObject2String(r.get("registertime"));//第25列
			String startDate=StaticMethod.nullObject2String(r.get("finalisttime"));//第26列
			String endDate=StaticMethod.nullObject2String(r.get("finalistendtime"));//第27列
			String deptLevel2=StaticMethod.nullObject2String(r.get("deptlevel"));//第28列，用于钻取时下级的deptlevel
			String nextDeptLevel2=Integer.parseInt(deptLevel2)+1+"";//下级钻取的deptLevel级别
			if (!export) {
				list.add(areaName1);
				list.add(areaId1);
				list.add(deptUUid);
				list.add(deptMagId);
				list.add(deptName);
				list.add(userNum);
				list.add(certNum);
				list.add(pro1);
				list.add(pro2);
				list.add(pro3);
				list.add(pro4);
				list.add(pro5);
				list.add(pro6);
				list.add(pro7);
				list.add(pro8);
				list.add(pro9);
				list.add(manageArea1);
				list.add(registTime);
				list.add(registAddr);
				list.add(fund);
				list.add(companyType);
				list.add(manager1);
				list.add(contactor1);
				list.add(phone1);
				list.add(qualifyNameAndLevel);
				list.add(qualifyOutOfDate);
				list.add(signDate);
				list.add(startDate);
				list.add(endDate);
				list.add(nextDeptLevel2);
				lastResultList.add(list);
			}else {
				exportSonList.add(areaName1);
				exportSonList.add(deptName);
				if (isUserNum) {
					exportSonList.add(userNum);
					exportSonList.add(certNum);
				}
				if (isProfessional) {
					exportSonList.add(pro1);
					exportSonList.add(pro2);
					exportSonList.add(pro3);
					exportSonList.add(pro4);
					exportSonList.add(pro5);
					exportSonList.add(pro6);
					exportSonList.add(pro7);
					exportSonList.add(pro8);
					exportSonList.add(pro9);
				}
				if (isArea) {
					exportSonList.add(manageArea1);
				}
				if (isBaseInfo) {
					exportSonList.add(registTime);
					exportSonList.add(registAddr);
					exportSonList.add(fund);
					exportSonList.add(companyType);
					exportSonList.add(manager1);
					exportSonList.add(contactor1);
					exportSonList.add(phone1);
				}
				if (isQualify) {
					exportSonList.add(qualifyNameAndLevel.replace("<br>", "|"));
					exportSonList.add(qualifyOutOfDate.replace("<br>", "|"));
				}
				if (isContact) {
					exportSonList.add(signDate);
					exportSonList.add(startDate);
					exportSonList.add(endDate);
				}
				exportListMap.add(exportSonList);
			}
		}
		if (export) {
			String fileName="代维组织详情统计信息";
			List<List<TdObjModel>> exportList=HomeHelper.verticalGrowp(2, 0, 0,exportListMap);
			StatisticsResultExport.exportStatisticsResultToXLSFile(true,exportList, headList,fileName, response, request);
			return null;
		}else {
			//跳转到统计页面
			List<List<TdObjModel>> allMap=HomeHelper.verticalGrowp(2, 0, 0,lastResultList);
			request.setAttribute("isArea",isArea);
			request.setAttribute("isUserNum",isUserNum);
			request.setAttribute("isProfessional",isProfessional);
			request.setAttribute("isBaseInfo",isBaseInfo);
			request.setAttribute("isQualify",isQualify);
			request.setAttribute("isContact",isContact);
			request.setAttribute("tableList", allMap);
			request.setAttribute("deptLevel", deptLevel);
			request.setAttribute("checkedIdsStr", checkedIdsStr);
			request.setAttribute("areaName", areaName);
			request.setAttribute("professional", professional);
			request.setAttribute("deptType", deptType);
			request.setAttribute("contactor", contactor);
			request.setAttribute("manager", manager);
			request.setAttribute("phone", phone);
			request.setAttribute("companyName", companyName);
			request.setAttribute("companyId", companyId);
			request.setAttribute("areaId", areaId);
			//nextDeptLevel=Integer.parseInt(deptLevel)+1+"";//下级钻取的deptLevel级别
			request.setAttribute("nextDeptLevel", nextDeptLevel);
			request.setAttribute("statistics","2");
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("pnrDeptStatistics");
		}
	}
	/**
	 * 
	 * @Title: pnrDetpCountStatistics 
	 * @Description: 组织数量统计
	 * @param 
	 * @Time:Dec 6, 2012-5:36:18 PM
	 * @Author:fengguangping
	 * @return ActionForward    返回类型 
	 * @throws
	 */
	public ActionForward pnrDetpCountStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		//数值转化为字符串，作为sql的search条件
		String checkedValues[]=checkedValuesStr.split(";");
		String searchStr="";
		String group="";
		for (int i = 0; i < checkedIds.length; i++) {
			if(!checkedIds[i].contains("pro_")){
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
				if(!group.equals("")){
					group+=",";
				}
				group+=checkedValues[i];
			}else {
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
			}
			if (i!=checkedIds.length-1) {
				searchStr+=",";
			}
		}
		boolean NoProSelection=false;
		if (!searchStr.contains("pro_")) {
			searchStr+=",count(id) as count ";
			NoProSelection=true;
		}
		//获取where条件值
		String province=StaticMethod.null2String(request.getParameter("provinceid"));
		String city=StaticMethod.null2String(request.getParameter("cityid"));
		String county=StaticMethod.null2String(request.getParameter("countryid"));
		String exportFlag=StaticMethod.null2String(request.getParameter("exportFlag"));
		//0代维 1 自维
		String isPartner = StaticMethod.null2String(request.getParameter("isPartner"));
		String whereStr=" ";
		if(!"".equals(province)){
			whereStr+=" and province_id='"+province+"'";
		}
		if (!"".equals(city)) {
			whereStr+=" and city_id='"+city+"'";
		}
		if(!"".equals(county)){
			whereStr+=" and county_id='"+county+"'";
		}
		String otherConditions="  group by "+group+"  order by "+group;
		if (!searchStr.contains("TypeLike")) {
			otherConditions="";
		}
//		String deptLevel="0";
//		if (searchStr.contains("province_id")) {//如果包含区县字段则统计
//			deptLevel="1";
//		}
//		if (searchStr.contains("city_id")) {//如果包含区县字段则统计
//			deptLevel="2";
//		}
//		if (searchStr.contains("county_id")) {//如果包含区县字段则统计
//			deptLevel="3";
//		}
//		if (!"0".equals(deptLevel)) {
//			whereStr+=" and deptlevel='"+deptLevel+"'";
//		}
		if(!"".equals(isPartner)){
			whereStr+=" and is_partner="+isPartner+" ";
		}
		String statisticsSql="select "+searchStr+" from pnr_dept  where " +" deleted='0' "+whereStr+otherConditions;
		//String sql="select "+searchStr+" from pnr_electricity_bills_count  where " +"id  is not null "+whereStr+"  group by "+group+"  order by "+group;
		System.out.println("组织数量统计语句:"+statisticsSql);
		StringBuffer items=new StringBuffer();
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < checkedIds.length; i++) {
			if ("province_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("省份");
			}else if ("city_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("地市");
			}else if ("county_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("区县");
			}else if ("pro_1".equals(checkedIds[i])) {
				headList.add("基站设备及配套");
			}else if ("pro_2".equals(checkedIds[i])) {
				headList.add("传输线路");
			}else if ("pro_3".equals(checkedIds[i])) {
				headList.add("直放站室分");
			}else if ("pro_4".equals(checkedIds[i])) {
				headList.add("铁塔及天馈");
			}else if ("pro_5".equals(checkedIds[i])) {
				headList.add("集团客户专线");
			}else if ("pro_6".equals(checkedIds[i])) {
				headList.add("WLAN");
			}else if ("pro_7".equals(checkedIds[i])) {
				headList.add("家庭宽带");
			}
		}
		List<List<TdObjModel>> tempList =new ArrayList<List<TdObjModel>>();
		if (NoProSelection) {//没有专业选项时
			headList.add("总数");
			//tempList=TableHelper.verticalGrowp(checkedIds,statisticsSql,"/partner/baseinfo/pnrDeptStatistics.do?method=pnrDeptCountSearchInto&deptlevel="+deptLevel);
			tempList=TableHelper.verticalGrowp(checkedIds,statisticsSql,"/partner/baseinfo/pnrDeptStatistics.do?method=pnrDeptCountSearchInto");
		}else {
			//tempList=PnrDeptStatisticsTableHelper.verticalGrowp(checkedIds,statisticsSql,"/partner/baseinfo/pnrDeptStatistics.do?method=pnrDeptCountSearchInto&deptlevel="+deptLevel);
			tempList=PnrDeptStatisticsTableHelper.verticalGrowp(checkedIds,statisticsSql,"/partner/baseinfo/pnrDeptStatistics.do?method=pnrDeptCountSearchInto");
			//不需要数据钻取，去掉count字段；
			for (List<TdObjModel>  list : tempList) {
				list.remove(list.size()-1);
			}
		}
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedIdsStr", checkedIdsStr);
		request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
		request.setAttribute("provinceSearch", province);
    	request.setAttribute("city", StaticMethod.getUserCityAreaList());
		request.setAttribute("cityid", city);
		request.setAttribute("countryid", county);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="组织数量统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("pnrDeptCountStatistics");
		}
	}
	/**
	 * 
	* @Title: pnrDetpFundStatistics 
	* @Description: 组织资金统计
	* @param 
	* @Time:Dec 6, 2012-5:42:49 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward pnrDetpFundStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		//数值转化为字符串，作为sql的search条件
		String checkedValues[]=checkedValuesStr.split(";");
		String searchStr="";
		String group="";
		for (int i = 0; i < checkedIds.length; i++) {
			if(!checkedIds[i].contains("fund_")){
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
				if(!group.equals("")){
					group+=",";
				}
				group+=checkedValues[i];
			}else {
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
			}
			if (i!=checkedIds.length-1) {
				searchStr+=",";
			}
		}
		//获取where条件值
		String province=StaticMethod.null2String(request.getParameter("provinceid"));
		String city=StaticMethod.null2String(request.getParameter("cityid"));
		String county=StaticMethod.null2String(request.getParameter("countryid"));
		String exportFlag=StaticMethod.null2String(request.getParameter("exportFlag"));
		String whereStr=" ";
		if(!"".equals(province)){
			whereStr+=" and province_id='"+province+"'";
		}
		if (!"".equals(city)) {
			whereStr+=" and city_id='"+city+"'";
		}
		if(!"".equals(county)){
			whereStr+=" and county_id='"+county+"'";
		}
		String otherConditions="  group by "+group+"  order by "+group;
		if (!searchStr.contains("TypeLike")) {
			otherConditions="";
		}
//		String deptLevel="0";
//		if (searchStr.contains("province_id")) {//如果包含省份字段则统计
//			deptLevel="1";
//		}
//		if (searchStr.contains("city_id")) {//如果包含地市字段则统计
//			deptLevel="2";
//		}
//		if (searchStr.contains("county_id")) {//如果包含区县字段则统计
//			deptLevel="3";
//		}
//		if (!"0".equals(deptLevel)) {
//			whereStr+=" and deptlevel='"+deptLevel+"'";
//		}
		String statisticsSql="select "+searchStr+" from pnr_dept  where " +" deleted='0' "+whereStr+otherConditions;
		StringBuffer items=new StringBuffer();
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < checkedIds.length; i++) {
			if ("province_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("省份");
			}else if ("city_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("地市");
			}else if ("county_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("区县");
			}else if ("fund_1".equals(checkedIds[i])) {
				headList.add("100万以下");
			}else if ("fund_2".equals(checkedIds[i])) {
				headList.add("100-500万");
			}else if ("fund_3".equals(checkedIds[i])) {
				headList.add("500-1000万");
			}else if ("fund_4".equals(checkedIds[i])) {
				headList.add("1000万以上");
			}
		}
		List<List<TdObjModel>> tempList = PnrDeptStatisticsTableHelper.verticalGrowp(
				checkedIds,statisticsSql,"/partner/baseinfo/pnrDeptStatistics.do?method=pnrDeptFundSearchInto");
		//不需要数据钻取，去掉count字段；
		for (List<TdObjModel>  list : tempList) {
			list.remove(list.size()-1);
		}
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedIdsStr", checkedIdsStr);
		request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
		request.setAttribute("provinceSearch", province);
    	request.setAttribute("city", StaticMethod.getUserCityAreaList());
		request.setAttribute("cityid", city);
		request.setAttribute("countryid", county);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="组织注册资本统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("pnrDeptFundStatistics");
		}
	}
	/**
	 * 
	* @Title: pnrDetpSelectedLevelStatistics 
	* @Description: 组织入围级别统计
	* @param 
	* @Time:Dec 6, 2012-5:42:53 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward pnrDetpSelectedLevelStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		//数值转化为字符串，作为sql的search条件
		String checkedValues[]=checkedValuesStr.split(";");
		String searchStr="";
		String group="";
		for (int i = 0; i < checkedIds.length; i++) {
			if(!checkedIds[i].contains("sellevel_")){
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
				if(!group.equals("")){
					group+=",";
				}
				group+=checkedValues[i];
			}else {
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
			}
			if (i!=checkedIds.length-1) {
				searchStr+=",";
			}
		}
		//获取where条件值
		String province=StaticMethod.null2String(request.getParameter("provinceid"));
		String city=StaticMethod.null2String(request.getParameter("cityid"));
		String county=StaticMethod.null2String(request.getParameter("countryid"));
		String exportFlag=StaticMethod.null2String(request.getParameter("exportFlag"));
		String whereStr=" ";
		if(!"".equals(province)){
			whereStr+=" and province_id='"+province+"'";
		}
		if (!"".equals(city)) {
			whereStr+=" and city_id='"+city+"'";
		}
		if(!"".equals(county)){
			whereStr+=" and county_id='"+county+"'";
		}
		String otherConditions="  group by "+group+"  order by "+group;
		if (!searchStr.contains("TypeLike")) {
			otherConditions="";
		}
		String statisticsSql="select "+searchStr+" from pnr_dept  where " +" deleted='0' "+whereStr+otherConditions;
		//String sql="select "+searchStr+" from pnr_electricity_bills_count  where " +"id  is not null "+whereStr+"  group by "+group+"  order by "+group;
		StringBuffer items=new StringBuffer();
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < checkedIds.length; i++) {
			if ("province_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("省份");
			}else if ("city_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("地市");
			}else if ("county_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("区县");
			}else if ("sellevel_1".equals(checkedIds[i])) {
				headList.add("集团");
			}else if ("sellevel_2".equals(checkedIds[i])) {
				headList.add("省级");
			}else if ("sellevel_3".equals(checkedIds[i])) {
				headList.add("地市");
			}
		}
		List<List<TdObjModel>> tempList = PnrDeptStatisticsTableHelper.verticalGrowp(
				checkedIds,statisticsSql,"/partner/baseinfo/pnrDeptStatistics.do?method=pnrDeptSelectedLevelSearchInto");
		//不需要数据钻取，去掉count字段；
		for (List<TdObjModel>  list : tempList) {
			list.remove(list.size()-1);
		}
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedIdsStr", checkedIdsStr);
		request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
		request.setAttribute("provinceSearch", province);
    	request.setAttribute("city", StaticMethod.getUserCityAreaList());
		request.setAttribute("cityid", city);
		request.setAttribute("countryid", county);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="组织入围级别统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("pnrDeptSelectedLevelStatistics");
		}
	}
	/**
	 * 
	* @Title: pnrDetpTypeStatistics 
	* @Description: 企业性质统计
	* @param 
	* @Time:Dec 6, 2012-5:42:59 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward pnrDetpTypeStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		//数值转化为字符串，作为sql的search条件
		String checkedValues[]=checkedValuesStr.split(";");
		String searchStr="";
		String group="";
		for (int i = 0; i < checkedIds.length; i++) {
			if(!checkedIds[i].contains("comtype_")){
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
				if(!group.equals("")){
					group+=",";
				}
				group+=checkedValues[i];
			}else {
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
			}
			if (i!=checkedIds.length-1) {
				searchStr+=",";
			}
		}
		boolean NoTypeSelection=false;
		if (!searchStr.contains("comtype_")) {
			searchStr+=",count(id) as count ";
			NoTypeSelection=true;
		}
		//获取where条件值
		String province=StaticMethod.null2String(request.getParameter("provinceid"));
		String city=StaticMethod.null2String(request.getParameter("cityid"));
		String county=StaticMethod.null2String(request.getParameter("countryid"));
		String exportFlag=StaticMethod.null2String(request.getParameter("exportFlag"));
		String whereStr=" ";
		if(!"".equals(province)){
			whereStr+=" and province_id='"+province+"'";
		}
		if (!"".equals(city)) {
			whereStr+=" and city_id='"+city+"'";
		}
		if (NoTypeSelection) {
			whereStr+="and type is not null";
		}
		String otherConditions="  group by "+group+"  order by "+group;
		if (!searchStr.contains("TypeLike")) {
			otherConditions="";
		}
		String statisticsSql="select "+searchStr+" from pnr_dept  where " +" deleted='0' "+whereStr+otherConditions;
		//String sql="select "+searchStr+" from pnr_electricity_bills_count  where " +"id  is not null "+whereStr+"  group by "+group+"  order by "+group;
		StringBuffer items=new StringBuffer();
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < checkedIds.length; i++) {
			if ("province_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("省份");
			}else if ("city_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("地市");
			}else if ("county_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("区县");
			}else if ("comtype_1".equals(checkedIds[i])) {
				headList.add("国有企业");
			}else if ("comtype_2".equals(checkedIds[i])) {
				headList.add("集体企业");
			}else if ("comtype_3".equals(checkedIds[i])) {
				headList.add("联营企业");
			}else if ("comtype_4".equals(checkedIds[i])) {
				headList.add("股份合作制企业");
			}else if ("comtype_5".equals(checkedIds[i])) {
				headList.add("私营企业");
			}else if ("comtype_6".equals(checkedIds[i])) {
				headList.add("外商独资企业");
			}else if ("comtype_7".equals(checkedIds[i])) {
				headList.add("有限责任公司");
			}else if ("comtype_8".equals(checkedIds[i])) {
				headList.add("股份有限公司");
			}
		}
		List<List<TdObjModel>> tempList =new ArrayList<List<TdObjModel>>();
		if(NoTypeSelection){
			headList.add("总数");
			tempList=TableHelper.verticalGrowp(checkedIds,statisticsSql,"/partner/baseinfo/pnrDeptStatistics.do?method=pnrDeptTypeSearchInto");
		}else {
			tempList=PnrDeptStatisticsTableHelper.verticalGrowp(checkedIds,statisticsSql,"/partner/baseinfo/pnrDeptStatistics.do?method=pnrDeptTypeSearchInto");
			//不需要数据钻取，去掉count字段；
			for (List<TdObjModel>  list : tempList) {
				list.remove(list.size()-1);
			}
		}
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedIdsStr", checkedIdsStr);
		request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
		request.setAttribute("provinceSearch", province);
    	request.setAttribute("city", StaticMethod.getUserCityAreaList());
		request.setAttribute("cityid", city);
		request.setAttribute("countryid", county);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="组织企业性质统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("pnrDeptTypeStatistics");
		}
	}
	/**
	 * 
	 * @Title: pnrDetpCountStatistics 
	 * @Description: 组织数量统计数据钻取
	 * @param 
	 * @Time:Dec 6, 2012-5:36:18 PM
	 * @Author:fengguangping
	 * @return ActionForward    返回类型 
	 * @throws
	 */
	public ActionForward pnrDeptCountSearchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String whereStr="";
		String provinceId=StaticMethod.nullObject2String(request.getParameter("province_idtypelikearea"));
		String cityId=StaticMethod.nullObject2String(request.getParameter("city_idtypelikearea"));
		String countyId=StaticMethod.nullObject2String(request.getParameter("county_idtypelikearea"));
		String deptlevel=StaticMethod.nullObject2String(request.getParameter("deptlevel"));
		String bitType=StaticMethod.nullObject2String(request.getParameter("big_type"));
		if (!"".equals(provinceId)) {
			whereStr+="and province_id='"+provinceId+"'";
		}
		if (!"".equals(cityId)) {
			whereStr+="and city_id='"+cityId+"'";
		}
		if (!"".equals(countyId)) {
			whereStr+=" and county_id='"+countyId+"'";
		}
//		if (!"".equals(deptlevel)&&!"0".equals(deptlevel)) {
//			whereStr+="and deptlevel='"+deptlevel+"'";
//		}
		if (!"".equals(bitType)) {
			whereStr+="and big_type like '%"+bitType+"%'";
		}
		String sql=" select * from pnr_dept where deleted='0' "+whereStr;
		String pageIndexName = new org.displaytag.util.ParamEncoder(PartnerDeptConstants.PARTNERDEPT_LIST).
		encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
		: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String countSql="select count(*) as count from ( "+sql+")";
		SearchUtil<PartnerDept> search = new SearchUtil<PartnerDept>(PartnerDept.class,"partnerDept",sql,countSql);
		PageData<PartnerDept> pageData = search.getDataList(pageIndex);
		request.setAttribute("pageSize", pageData.getPageSize());
		request.setAttribute("resultSize", pageData.getTotalCount());
		request.setAttribute(PartnerDeptConstants.PARTNERDEPT_LIST, pageData.getList());
		return mapping.findForward("pnrDeptCountStatisticsList");
	}
	/**
	 * 
	* @Title: pnrDetpFundSearchInto 
	* @Description: 入围资本数据钻取
	* @param 
	* @Time:Dec 6, 2012-5:40:26 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward pnrDeptFundSearchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String whereStr="";
		String provinceId=StaticMethod.nullObject2String(request.getParameter("province_idtypelikearea"));
		String cityId=StaticMethod.nullObject2String(request.getParameter("city_idtypelikearea"));
		String countyId=StaticMethod.nullObject2String(request.getParameter("county_idtypelikearea"));
		String fund=StaticMethod.nullObject2String(request.getParameter("fund"));
		if (!"".equals(provinceId)) {
			whereStr+="and province_id='"+provinceId+"'";
		}
		if (!"".equals(cityId)) {
			whereStr+="and city_id='"+cityId+"'";
		}
		if (!"".equals(countyId)) {
			whereStr+="and county_id='"+countyId+"'";
		}
		if (!"".equals(fund)) {
			if("fund<100".equals(fund)){
				fund="(fund<100 or fund is null or fund='') ";
			}
			whereStr+="and "+fund;
		}
		String sql=" select * from pnr_dept where deleted='0' "+whereStr;
		String pageIndexName = new org.displaytag.util.ParamEncoder(PartnerDeptConstants.PARTNERDEPT_LIST).
		encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
		: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String countSql="select count(*) as count from ( "+sql+")";
		SearchUtil<PartnerDept> search = new SearchUtil<PartnerDept>(PartnerDept.class,"partnerDept",sql,countSql);
		PageData<PartnerDept> pageData = search.getDataList(pageIndex);
		request.setAttribute("pageSize", pageData.getPageSize());
		request.setAttribute("resultSize", pageData.getTotalCount());
		request.setAttribute(PartnerDeptConstants.PARTNERDEPT_LIST, pageData.getList());
		return mapping.findForward("pnrDeptFundStatisticsList");
	}
	/**
	 * 
	* @Title: pnrDetpSelectedLevelSearchInto 
	* @Description: 入围级别数据钻取
	* @param 
	* @Time:Dec 6, 2012-5:39:42 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward pnrDeptSelectedLevelSearchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String whereStr="";
		String provinceId=StaticMethod.nullObject2String(request.getParameter("province_idtypelikearea"));
		String cityId=StaticMethod.nullObject2String(request.getParameter("city_idtypelikearea"));
		String selectedLevel=StaticMethod.nullObject2String(request.getParameter("selectedlevel"));
		if (!"".equals(provinceId)) {
			whereStr+="and province_id='"+provinceId+"'";
		}
		if (!"".equals(cityId)) {
			whereStr+="and city_id='"+cityId+"'";
		}
		if (!"".equals(selectedLevel)) {
			whereStr+="and selectedLevel='"+selectedLevel+"'";
		}
		String sql=" select * from pnr_dept where deleted='0' "+whereStr;
		String pageIndexName = new org.displaytag.util.ParamEncoder(PartnerDeptConstants.PARTNERDEPT_LIST).
		encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
		: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String countSql="select count(*) as count from ( "+sql+")";
		SearchUtil<PartnerDept> search = new SearchUtil<PartnerDept>(PartnerDept.class,"partnerDept",sql,countSql);
		PageData<PartnerDept> pageData = search.getDataList(pageIndex);
		request.setAttribute("pageSize", pageData.getPageSize());
		request.setAttribute("resultSize", pageData.getTotalCount());
		request.setAttribute(PartnerDeptConstants.PARTNERDEPT_LIST, pageData.getList());
		return mapping.findForward("pnrDeptSelectedLevelStatisticsList");
	}
	/**
	 * 
	* @Title: pnrDetpTypeSearchInto 
	* @Description: 公司性质统计数据钻取
	* @param 
	* @Time:Dec 6, 2012-5:39:16 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward pnrDeptTypeSearchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String whereStr="";
		String provinceId=StaticMethod.nullObject2String(request.getParameter("province_idtypelikearea"));
		String cityId=StaticMethod.nullObject2String(request.getParameter("city_idtypelikearea"));
		String companyType=StaticMethod.nullObject2String(request.getParameter("type"));
		if (!"".equals(provinceId)) {
			whereStr+="and province_id='"+provinceId+"'";
		}
		if (!"".equals(cityId)) {
			whereStr+="and city_id='"+cityId+"'";
		}
		if (!"".equals(companyType)) {
			whereStr+="and type='"+companyType+"'";
		}else {
			whereStr+="and type is not null";
		}
		String sql=" select * from pnr_dept where deleted='0' "+whereStr;
		String pageIndexName = new org.displaytag.util.ParamEncoder(PartnerDeptConstants.PARTNERDEPT_LIST).
		encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
		: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String countSql="select count(*) as count from ( "+sql+")";
		SearchUtil<PartnerDept> search = new SearchUtil<PartnerDept>(PartnerDept.class,"partnerDept",sql,countSql);
		PageData<PartnerDept> pageData = search.getDataList(pageIndex);
		request.setAttribute("pageSize", pageData.getPageSize());
		request.setAttribute("resultSize", pageData.getTotalCount());
		request.setAttribute(PartnerDeptConstants.PARTNERDEPT_LIST, pageData.getList());
		return mapping.findForward("pnrDeptTypeStatisticsList");
	}

}
