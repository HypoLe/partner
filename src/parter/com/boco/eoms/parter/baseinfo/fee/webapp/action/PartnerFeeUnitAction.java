package com.boco.eoms.parter.baseinfo.fee.webapp.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeeUnitMgr;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeUnit;
import com.boco.eoms.parter.baseinfo.fee.util.PartnerFeeUnitConstants;
import com.boco.eoms.parter.baseinfo.fee.webapp.form.PartnerFeeUnitForm;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.ui.util.UIConstants;

/**
 * <p>
 * Title:合作伙伴费用单位
 * </p>
 * <p>
 * Description:合作伙伴费用单位
 * </p>
 * <p>
 * Tue Sep 08 09:38:34 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() lvweihua
 * @moudle.getVersion() 3.5
 * 
 */
public final class PartnerFeeUnitAction extends BaseAction {
 
	/**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}
 	
 	/**
	 * 新增合作伙伴费用单位
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改合作伙伴费用单位
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeeUnitMgr partnerFeeUnitMgr = (PartnerFeeUnitMgr) getBean("partnerFeeUnitMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PartnerFeeUnit partnerFeeUnit = partnerFeeUnitMgr.getPartnerFeeUnit(id);
		PartnerFeeUnitForm partnerFeeUnitForm = (PartnerFeeUnitForm) convert(partnerFeeUnit);
		updateFormBean(mapping, request, partnerFeeUnitForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存合作伙伴费用单位
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeeUnitMgr partnerFeeUnitMgr = (PartnerFeeUnitMgr) getBean("partnerFeeUnitMgr");
		PartnerFeeUnitForm partnerFeeUnitForm = (PartnerFeeUnitForm) form;
		boolean isNew = (null == partnerFeeUnitForm.getId() || "".equals(partnerFeeUnitForm.getId()));
		PartnerFeeUnit partnerFeeUnit = (PartnerFeeUnit) convert(partnerFeeUnitForm);
		String user = this.getUser(request).getUserid();
		String dept = this.getUser(request).getDeptid();
		Date date = StaticMethod.getLocalTime();
		partnerFeeUnit.setCreateUser(user);
		partnerFeeUnit.setCreateDept(dept);
		partnerFeeUnit.setCreateDate(date);
		partnerFeeUnit.setIsDelete("0");
		if (isNew) {
			partnerFeeUnitMgr.savePartnerFeeUnit(partnerFeeUnit);
		} else {
			partnerFeeUnitMgr.savePartnerFeeUnit(partnerFeeUnit);
		}
		return mapping.findForward("success");
	}
	
	/**
	 * 删除合作伙伴费用单位
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeeUnitMgr partnerFeeUnitMgr = (PartnerFeeUnitMgr) getBean("partnerFeeUnitMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PartnerFeeUnit partnerFeeUnit = partnerFeeUnitMgr.getPartnerFeeUnit(id);
		partnerFeeUnit.setIsDelete("1");
		partnerFeeUnitMgr.savePartnerFeeUnit(partnerFeeUnit);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示合作伙伴费用单位列表
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
				PartnerFeeUnitConstants.PARTNERFEEUNIT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
		.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerFeeUnitMgr partnerFeeUnitMgr = (PartnerFeeUnitMgr) getBean("partnerFeeUnitMgr");
		String whereStr = " where partnerFeeUnit.isDelete = '0'";
		Map map = (Map) partnerFeeUnitMgr.getPartnerFeeUnits(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PartnerFeeUnitConstants.PARTNERFEEUNIT_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	/**
	 * 获得单位树(第一层)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getNodesRadioTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		JSONArray jsonRoot = new JSONArray();
		PartnerFeeUnitMgr partnerFeeUnitMgr = (PartnerFeeUnitMgr) getBean("partnerFeeUnitMgr");
		List list = (List)partnerFeeUnitMgr.getPartnerFeeUnits();
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			PartnerFeeUnit partnerFeeUnit =(PartnerFeeUnit) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", partnerFeeUnit.getId());
			// TODO 添加节点名称
			jitem.put("text",partnerFeeUnit.getName());
			jitem.put(UIConstants.JSON_NODETYPE, "folder");
			//jitem.put("iconCls", "folder");
			jitem.put("qtip", partnerFeeUnit.getName());
			
			// 设置是否为叶子节点
			boolean leafFlag = true;
		
			jitem.put("leaf", leafFlag);
			// TODO 设置鼠标悬浮提示
			//jitem.put("qtip", your tips here);
			jitem.put("checked", false);		
			jsonRoot.put(jitem);
		}
		//JSONUtil.print(response, jsonRoot.toString());		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(jsonRoot.toString());
	}
	
	/**
	 * 分页显示合作伙伴费用单位列表，支持Atom方式接入Portal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ActionForward search4Atom(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		try {
//			// --------------用于分页，得到当前页号-------------
//			final Integer pageIndex = new Integer(request
//					.getParameter("pageIndex"));
//			final Integer pageSize = new Integer(request
//					.getParameter("pageSize"));
//			PartnerFeeUnitMgr partnerFeeUnitMgr = (PartnerFeeUnitMgr) getBean("partnerFeeUnitMgr");
//			Map map = (Map) partnerFeeUnitMgr.getPartnerFeeUnits(pageIndex, pageSize, "");
//			List list = (List) map.get("result");
//			PartnerFeeUnit partnerFeeUnit = new PartnerFeeUnit();
//			
//			//创建ATOM源
//			Factory factory = Abdera.getNewFactory();
//			Feed feed = factory.newFeed();
//			
//			// 分页
//			for (int i = 0; i < list.size(); i++) {
//				partnerFeeUnit = (PartnerFeeUnit) list.get(i);
//				
//				// TODO 请按照下面的实例给entry赋值
//				Entry entry = feed.insertEntry();
//				entry.setTitle("<a href='" + request.getScheme() + "://"
//						+ request.getServerName() + ":"
//						+ request.getServerPort()
//						+ request.getContextPath()
//						+ "/partnerFeeUnit/partnerFeeUnits.do?method=edit&id="
//						+ partnerFeeUnit.getId() + "' target='_blank'>"
//						+ display name for list + "</a>");
//				entry.setSummary(summary);
//				entry.setContent(content);
//				entry.setLanguage(language);
//				entry.setText(text);
//				entry.setRights(tights);
//				
//				// 以下三项需要传入Date类型或String类型（yyyy-MM-dd）的参数
//				entry.setUpdated(new java.util.Date());
//				entry.setPublished(new java.util.Date());
//				entry.setEdited(new java.util.Date());
//				
//				// 为person的name属性赋值，entry.addAuthor可以随意赋值
//				Person person = entry.addAuthor(userId);
//				person.setName(userName);
//			}
//			
//			// 每页显示条数
//			feed.setText(map.get("total").toString());
//		    OutputStream os = response.getOutputStream();
//		    PrintStream ps = new PrintStream(os);
//		    feed.getDocument().writeTo(ps);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}