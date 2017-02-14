package com.boco.eoms.deviceManagement.busi.protectline.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;

import com.boco.eoms.deviceManagement.busi.protectline.form.WarningBoardForm;
import com.boco.eoms.deviceManagement.busi.protectline.form.WarningBoardOperateForm;
import com.boco.eoms.deviceManagement.busi.protectline.model.WarningBoard;
import com.boco.eoms.deviceManagement.busi.protectline.model.WarningBoardOperate;
import com.boco.eoms.deviceManagement.busi.protectline.service.WarningBoardOperateService;
import com.boco.eoms.deviceManagement.busi.protectline.service.WarningBoardService;
import com.boco.eoms.deviceManagement.busi.protectline.util.TableHelper;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;



public class WarningBoardAction extends BaseAction{

	
	
	public WarningBoardService getProAgreBean() {
		String source = WarningBoardService.class.getSimpleName();
		return (WarningBoardService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}
	
	public WarningBoardOperateService getProAgreOperateBean() {
		String source = WarningBoardOperateService.class.getSimpleName();
		return (WarningBoardOperateService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}
	
	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}
	
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String areaId="";
		String greatTime = CommonUtils.toEomsStandardDate(new Date());
		
		PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");    
	    String where=" and dept_mag_id= "+ deptId;
	    List deptList= partnerDeptMgr.getPartnerDepts(where);
	    if(!(deptList.equals(null)||deptList.size()==0)){
	    PartnerDept parentDept=(PartnerDept) deptList.get(0);
	    areaId=parentDept.getAreaId();
	    }
	   
		WarningBoardForm warningBoardForm = (WarningBoardForm) form;
		WarningBoard warningBoard=new WarningBoard();
		BeanUtils.copyProperties(warningBoard, warningBoardForm);
		

		warningBoard.setPersonnelId(userId);
        warningBoard.setGreatTime(greatTime);
		warningBoard.setAreaId(areaId);
		warningBoard.setDeleted("0");
		
    	getProAgreBean().save(warningBoard);	
		return mapping.findForward("success");
	}

	public ActionForward goToEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id =  request.getParameter("id");
		
		WarningBoard warningBoard = this.getProAgreBean().find(id);	
		Search search = new Search();	
		search.addFilterEqual("warningBoardId", id);
		SearchResult<WarningBoardOperate> searchResult = this.getProAgreOperateBean().searchAndCount(search);		
		List<WarningBoardOperate> warningBoardOperateList = searchResult.getResult();	 
	
		request.setAttribute("warningBoard", warningBoard);
		request.setAttribute("warningBoardOperateList",warningBoardOperateList);
		return mapping.findForward("goToEdit");
	}	
	
	
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WarningBoardForm warningBoardForm = (WarningBoardForm) form;
		WarningBoard warningBoard=new WarningBoard();
		BeanUtils.copyProperties(warningBoard, warningBoardForm);
		
		this.getProAgreBean().save(warningBoard);
		return mapping.findForward("success");
	}
	
	
	public ActionForward goToDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id =  request.getParameter("id");
		String type =  request.getParameter("type");
		WarningBoard warningBoard = this.getProAgreBean().find(id);	
		Search search = new Search();	
		search.addFilterEqual("warningBoardId", id);
		SearchResult<WarningBoardOperate> searchResult = this.getProAgreOperateBean().searchAndCount(search);		
		List<WarningBoardOperate> warningBoardOperateList = searchResult.getResult();	 
	
		request.setAttribute("type",type);
		request.setAttribute("warningBoard", warningBoard);
		request.setAttribute("warningBoardOperateList",warningBoardOperateList);
		if("2".equals(type)){
			return mapping.findForward("goToDetailApprovel");
		}
		return mapping.findForward("goToDetail");
	}
	
	
	
		@SuppressWarnings("finally")
		public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			String id = request.getParameter("id");
			WarningBoard warningBoard = this.getProAgreBean().find(id);	
			warningBoard.setDeleted("1");	
			this.getProAgreBean().save(warningBoard);	
			
		} catch (RuntimeException e) {
			BocoLog.info(this, "删除出错！");
			e.printStackTrace();
			CommonUtils.printJsonSuccessMsg(response);
		} finally {
			return mapping.findForward("success");
		} 
	}
	
	
	
	
	
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {


		Search search = new Search();	
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		// Get userId and deptId to add privileges searching.
		TawSystemSessionForm sessionform = this.getUser(request);	
		String userId=sessionform.getUserid();
	
		String type = request.getParameter("type");
		String listType="list";	
		Map paramMap = request.getParameterMap();
			
		if(type.equals("0")){
		search.addFilterEqual("personnelId", userId);
		search.addFilterNotEqual("status", "4");
//		search.addFilterNotEqual("status", "3");
		}
		
		else
		{		
		search.addFilterEqual("status", type);	
		
		
		    if(type.equals("1")) { 
		    	search.addFilterEqual("personnelId", userId);
		    }
		    if(type.equals("2")) { 
		    	search.addFilterEqual("auditId", userId);
		    }
		}
		
		
		search.addFilterEqual("deleted", "0");
		String pageIndexString = request.getParameter((new org.displaytag.util.ParamEncoder(
				"warningBoardList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
                 search.setMaxResults(CommonConstants.PAGE_SIZE);
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
		                   .valueOf(pageIndexString).intValue() - 1;

        search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
        search.setMaxResults(CommonConstants.PAGE_SIZE);

        SearchResult<WarningBoard> searchResult = this.getProAgreBean().searchAndCount(search);


        List<WarningBoard> warningBoardList = searchResult.getResult();

        request.setAttribute("type",type);
        request.setAttribute("warningBoardList",warningBoardList);
        request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
        request.setAttribute("size", searchResult.getTotalCount());
		
        if("2".equals(type)) { 
        	return mapping.findForward("listTypeApprovel");
        }
        
        
		return mapping.findForward(listType);
	
}
	
	public ActionForward deal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		String warningBoardId = request.getParameter("id");
		String operateTime = CommonUtils.toEomsStandardDate(new Date());
		String operateUserId=request.getParameter("auditId");
		String operateTarget=request.getParameter("personnelId");
		String status=request.getParameter("status");
		String operateResult=request.getParameter("operateResult");	
		String operateStatus=operateResult;
		
		
		WarningBoardOperateForm warningBoardOperateForm = (WarningBoardOperateForm) form;
		WarningBoardOperate warningBoardOperate=new WarningBoardOperate();
		BeanUtils.copyProperties(warningBoardOperate, warningBoardOperateForm);
		
		warningBoardOperate.setWarningBoardId(warningBoardId);
		warningBoardOperate.setOperateTime(operateTime);
		warningBoardOperate.setOperateUserId(operateUserId);
		warningBoardOperate.setOperateTarget(operateTarget);
		warningBoardOperate.setOperateStatus(operateStatus);
		this.getProAgreOperateBean().save(warningBoardOperate);
		
		WarningBoard warningBoard=new WarningBoard();
		warningBoard=this.getProAgreBean().find(warningBoardId);
		warningBoard.setStatus(operateStatus);
	    this.getProAgreBean().save(warningBoard);
	
	
		return mapping.findForward("success");
	}
	
	
	
	/**
	 * @author huhao
	 * @time 2010-10 多为统计跳转
	 */
	public ActionForward goToWarningBoardStaff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("warningBoardStaff");
	}

	/**
	 * @time 2010-10 多维统计
	 */
	public ActionForward warningBoardStaff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		TawSystemSessionForm sessionform = this.getUser(request);	
		String userId=sessionform.getUserid();
		String ss = StaticMethod.nullObject2String(
				request.getParameter("deleteIdss"), "");		
//		ss+="de0finishtime;de0advertisementamount;de0incompletecause;";
		String rows[] = ss.split(";");
		String checkString = StaticMethod.nullObject2String(request
				.getParameter("checks"), "");
		String search = "";
		String group = "";
		for (int i = 0; i < rows.length; i++) {
			String row = "";
			if (rows[i].contains("TypeLikedict")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikedict".length());
				row = row.replace("0", ".");
			} else if (rows[i].contains("TypeLikeArea")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikeArea".length());
				row = row.replace("0", ".");
			} else if (rows[i].contains("TypeLikeUser")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikeUser".length());
				row = row.replace("0", ".");
			} else {
				row = rows[i].replace("0", ".");
			}

			if (i == rows.length - 1) {
				search += row + " as " + rows[i];
				group += row;
			} else {
				search += row + " as " + rows[i] + ",";
				group += row + ",";
			}
		}
//		search=search+",de.finishtime as de0finishtime,de.advertisementamount as de0advertisementamount,de.incompletecause as de0incompletecause";
		// get the text to where condition.
		String whereCondition = " ";
		String deAreaId = request.getParameter("de0areaIdT");
		String deItemName = request.getParameter("de0itemNameT");
		String deRepeaterSection = request
				.getParameter("de0repeaterSectionT");
		String deAuditId = request
				.getParameter("de0auditIdT");

		if (!"".equals(deAreaId)) {
			whereCondition += " and de.areaId='" + deAreaId + "'";
		}
		if (!"".equals(deItemName)) {
			whereCondition += " and de.itemName like '%" + deItemName + "%'";
		}
		if (!"".equals(deRepeaterSection)) {
			whereCondition += " and de.repeaterSection like '%"
					+ deRepeaterSection + "%'";
		}
		if (!"".equals(deAuditId)) {
			whereCondition += " and de.auditId = '"
					+ deAuditId + "'";
		}

		/**
		 * 原始sql select
		 * de.creatdept,de.projectname,de.advertisementcontent,de.advertisementarea,de.advertisementamount,de.finishtime,de.incompletecause,de.approvaluser,count(de.id)
		 * from dm_protectline_advertisement de where de.deleted='0' and
		 * de.approvaltype='2' group by
		 * de.creatdept,de.projectname,de.advertisementcontent,de.advertisementarea,de.advertisementamount,de.finishtime,de.incompletecause,de.approvaluser
		 * order by
		 * de.creatdept,de.projectname,de.advertisementcontent,de.advertisementarea,de.advertisementamount,de.finishtime,de.incompletecause,de.approvaluser
		 */
		String searchSql = "select "
				+ search
				+ " ,count(de.id) "
				+ "from dm_protectline_warningboard de "
				+ " where de.deleted='0' and (status='3'or status='4') and  personnelid='"
				+userId
				+"'"
				+ ""
				+ whereCondition
				+ ""
				+ "group by "
				+ " "
				+ group
				+ " "
				+ "  order by "
				+ " " + group;
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if ("de0areaIdTypeLikeArea".equals(rows[i])) {
				headList.add("所属地市");
			}
			if ("de0itemName".equals(rows[i])) {
				headList.add("项目名称");
			}
			if ("de0repeaterSection".equals(rows[i])) {
				headList.add("中继段名称");
			}
			if ("de0auditIdTypeLikeUser".equals(rows[i])) {
				headList.add("审批人");
			}

		}
		headList.add("总数");
		List tempList = TableHelper
				.verticalGrowp(rows, searchSql,
						"/deviceManagement/warningboard/warningboard.do?method=searchInto");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);

		return mapping.findForward("warningBoardStaff");
	}
/**
 * @author huhao
 * 墙体广告统计钻取
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	
	
	public ActionForward searchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 TawSystemSessionForm sessionform = this.getUser(request);
		 String userId = sessionform.getUserid();
		// String operateDeptId = sessionform.getDeptid();
		
		
		
		CommonSpringJdbcServiceImpl csjsi = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
		.getInstance().getBean("commonSpringJdbcService");
		String search ="";
		String sql="";
		String areaId = request.getParameter("de0areaidtypelikearea");
		if (areaId!=null) {
			areaId = new String(areaId.toString().trim().getBytes(
					"ISO-8859-1"), "utf-8");
			search=search+"and de.areaId="+"'"+areaId+"' ";
		}
		String itemName = request.getParameter("de0itemname");
		if (itemName!=null) {
			itemName = new String(itemName.toString().trim().getBytes("ISO-8859-1"),
					"utf-8");
			search=search+"and de.itemName="+"'"+itemName+"' ";
		}
		String repeaterSection = request.getParameter("de0repeaterSection");
		if (repeaterSection!=null) {
			repeaterSection = new String(repeaterSection.toString().trim().getBytes("ISO-8859-1"),
					"utf-8");
			search=search+"and de.repeaterSection="+"'"+repeaterSection+"' ";
		}
		String auditId = request.getParameter("de0auditIdTypeLikeUser");
		if (auditId!=null) {
			auditId = new String(auditId.toString().trim()
					.getBytes("ISO-8859-1"), "utf-8");
			search=search+"and de.auditId="+"'"+auditId+"' ";
		}

			sql = "select de.* from dm_protectline_warningboard de where de.deleted='0' and (status='3'or status='4') and  personnelid='"
				+userId
				+"'"
				+" "
				+
				search;
			
			List<ListOrderedMap> resultList  = csjsi.queryForList(sql);
			List list=new ArrayList();
			for (ListOrderedMap listOrderedMap : resultList) {
				WarningBoard staff= new WarningBoard();
				staff.setId((String)listOrderedMap.get("id"));
				staff.setWarningBoardNewNumber((String)listOrderedMap.get("warningBoardNewNumber"));
				staff.setWarningBoardOldNumber((String)listOrderedMap.get("warningBoardOldNumber"));
				staff.setAreaId((String)listOrderedMap.get("areaId"));
				staff.setAuditId((String)listOrderedMap.get("auditId"));
				staff.setGreatTime((String)listOrderedMap.get("greatTime"));
				staff.setItemName((String)listOrderedMap.get("itemName"));
				staff.setPersonnelId((String)listOrderedMap.get("personnelId"));
				staff.setRepeaterSection((String)listOrderedMap.get("repeaterSection"));
				staff.setRepeaterSectionMileage((String)listOrderedMap.get("repeaterSectionMileage"));
				staff.setAssessResult((String)listOrderedMap.get("assessResult"));
				staff.setActualCompletion((String)listOrderedMap.get("actualCompletion"));
				staff.setCompletionTime((String)listOrderedMap.get("completionTime"));
				staff.setStatus((String)listOrderedMap.get("status"));
				list.add(staff);
			}
			request.setAttribute("warningBoardList", list);
			request.setAttribute("pagesize", list.size());
			request.setAttribute("size", list.size());
		String next = "warningBoardStaffaSearchList";
		return mapping.findForward(next);
	}
	
	
	public ActionForward file(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
			String id = request.getParameter("id");
			WarningBoard warningBoard = this.getProAgreBean().find(id);		
			warningBoard.setStatus("4");
			this.getProAgreBean().save(warningBoard);
			return mapping.findForward("success");
		
	}
	
	
	
	
	
}
