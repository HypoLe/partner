package com.boco.eoms.message.webapp.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.bo.TawSystemTreeBo;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.message.mgr.ISmsApplyManager;
import com.boco.eoms.message.mgr.ISmsServiceManager;
import com.boco.eoms.message.model.SmsApply;
import com.boco.eoms.message.model.SmsService;
import com.boco.eoms.message.util.MsgConstants;
import com.boco.eoms.message.util.MsgHelp;
import com.boco.eoms.message.webapp.form.SmsApplyForm;
import com.boco.eoms.prm.service.IPojo2PojoService;

/**
 * Action class to handle CRUD on a SmsApply object
 * 
 * @struts.action name="smsApplyForm" path="/smsApplys" scope="request"
 *                validate="false" parameter="method" input="mainMenu"
 * @struts.action name="smsApplyForm" path="/editSmsApply" scope="request"
 *                validate="false" parameter="method" input="list"
 * @struts.action name="smsApplyForm" path="/saveSmsApply" scope="request"
 *                validate="true" parameter="method" input="edit"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="edit"
 *                        path="/WEB-INF/pages/smsApply/smsApplyForm.jsp"
 * @struts.action-forward name="list"
 *                        path="/WEB-INF/pages/smsApply/smsApplyList.jsp"
 * @struts.action-forward name="search" path="/smsApplys.html" redirect="true"
 */
public final class SmsApplyAction extends BaseAction {
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("search");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages messages = new ActionMessages();
		SmsApplyForm smsApplyForm = (SmsApplyForm) form;

		// Exceptions are caught by ActionExceptionHandler
		ISmsApplyManager mgr = (ISmsApplyManager) getBean("IsmsApplyManager");
		mgr.removeSmsApply(smsApplyForm.getId());

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"smsApply.deleted"));

		// save messages in session, so they'll survive the redirect
		saveMessages(request.getSession(), messages);

		return mapping.findForward("search");
	}

	/**
	 * 个性化服务-初始化服务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward xinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		com.boco.eoms.message.interfacesclient.MsgServiceImpl impl = new MsgServiceImplServiceLocator().getMsgService();
//		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Mms><serviceId>8aa0810022297d5d01222994f8ee0006</serviceId><buizId>8aa082a81ddc0473011ddc3e31460011</buizId><orgIds>13800138000,13800138001,13800138002</orgIds><dispatchTime>2009-3-12 12:00:00</dispatchTime><subject>彩信主题测试</subject><MmsContent><postion>1</postion><Content_Type>1</Content_Type><Content>测试彩信内容1</Content></MmsContent><MmsContent><postion>2</postion><Content_Type>3</Content_Type><Content>http://10.32.1.159:8089/eoms/images/复件.jpg</Content></MmsContent><MmsContent><postion>3</postion><Content_Type>1</Content_Type><Content>测试彩信内容2</Content></MmsContent><MmsContent><postion>4</postion><Content_Type>3</Content_Type><Content>http://10.32.1.159:8089/eoms/images/404.jpg</Content></MmsContent></Mms>";
//		impl.sendMmsImmediatelyByWeb(xmlString);
//		File file1 = new File("F:\\新建 Microsoft Word 文档.doc");
//		File file2 = new File("F:\\亿阳信通工作目录\\加班统计表(新).xls");
//		File file3 = new File("F:\\亿阳信通工作目录\\移动各地版本\\统一消息平台\\EOMS3.5贵州消息发送概要设计文档.doc");
//		String a = "http://10.32.1.161:8089/eoms/11.doc";
//		String b = "11.doc";
//		List fileList = new ArrayList();
//		fileList.add(file1);
//		fileList.add(file2);
//		fileList.add(file3);
//		impl.sendEmailByWeb("8aa081212168b6d7012168c24b2a0055", "主题", "发送内容", "8aa082a81e255a74011e2586b79b0021", "sunshengtai", "1,zhiban2#1,zhiban3#1,zhiban1", "2009-05-05 08:55:55", "http://10.32.1.161:8089/eoms/11.doc#11.doc,http://10.32.1.161:8089/eoms/html/abcde.xls#abcde.xls");
//		impl.test(a,b);
//		String xmlString = "<?xml version='1.0' encoding='UTF-8'?><service><status>1</status><serviceName>servicename</serviceName><createUserId>mengxianyu</createUserId><selStatus value='sadf'><userId>a,b,c</userId><deptId>254</deptId></selStatus></service>";
//		
//		MsgHelp.getDocByXmlString(xmlString);
//		String a = MsgHelp.getXmlValue("//service/createUserId");
//		System.out.println("====================="+a);
//		MsgServiceImpl   msgService = new MsgServiceImpl();
//		List mmsContentList = new ArrayList();
//		MmsContent mmsContent = new MmsContent();
//		MmsContent mmsContent1 = new MmsContent();
//		MmsContent mmsContent2 = new MmsContent();
//		MmsContent mmsContent3 = new MmsContent();
//		mmsContent1.setContent("测试彩信内容1");
//		mmsContent1.setContentType(MsgConstants.MMS_TYPE_TEXT);
//		mmsContent1.setPosition("1");
//		mmsContent1.setDeleted("0");
//		mmsContent.setContent("F:\\project\\mainVersion\\web\\images\\403.jpg");
//		mmsContent.setContentType(MsgConstants.MMS_TYPE_GIF);
//		mmsContent.setPosition("2");
//		mmsContent.setDeleted("0");
//		
//
//		mmsContent3.setContent("测试彩信内容2");
//		mmsContent3.setContentType(MsgConstants.MMS_TYPE_TEXT);
//		mmsContent3.setPosition("3");
//		mmsContent3.setDeleted("0");
//
//		mmsContent2.setContent("F:\\project\\mainVersion\\web\\images\\404.jpg");
//		mmsContent2.setContentType(MsgConstants.MMS_TYPE_GIF);
//		mmsContent2.setPosition("4");
//		mmsContent2.setDeleted("0");
//		mmsContentList.add(mmsContent);
//		mmsContentList.add(mmsContent1);
//		mmsContentList.add(mmsContent2);
//		mmsContentList.add(mmsContent3);
//		msgService.sendMms("8aa0810022297d5d01222994f8ee0006", 
//				"8aa082a81ddc0473011ddc3e31460011", 
//				"1,zhiban2#1,zhiban3#1,zhiban1", 
//				"彩信主题测试",
//				"2009-3-12 12:00:00",mmsContentList);
//		msgService.sendEmail("8aa08197219f86cf01219fdb3b950022", "关于测试邮件服务器主题问题","2008年11月26日完成测试邮件服务器功能，进一步完善了消息平台的功能，接下来还有彩信和语音部分需要完善。",
//				"00000000000000000000000000000001", "sunshengtai", "1,zhiban1#1,zhiban2#1,zhiban3","2008-11-27 10:05:00","F:\\readmail.txt");
//		msgService.sendMsg("8aa081a323990a0c01239948f86f0024", "2008年11月26日完成测试邮件服务器功能，进一步完善了消息平台的功能，接下来还有彩信和语音部分需要完善。", "00000000000000000000000000000000", "1,zhiban2#1,zhiban3#1,Gongyufeng", "2009-03-17 12:00:00");
//		msgService.sendVoice("8aa08197219f86cf01219fdb3b950022", "8aa08197219f86cf01219fdb3b950022", "2009-06-03 10:05:00", "2009-06-04 16:50:00", "2009-06-04 16:55:00", "测试语音平台", "sunshengtai", "1,zhiban2#1,zhiban3#1,zhiban1");
		TawSystemSessionForm sessionForm = this.getUser(request);
		ISmsApplyManager mgr = (ISmsApplyManager) getBean("IsmsApplyManager");
		//获取
		List applyList = mgr.getApplysByCondition(sessionForm.getUserid(), MsgConstants.DELETED);
		//将服务列表写入页面供显示
		request.setAttribute("applys", applyList);
		return mapping.findForward("selectApply");
	}
	/**
	 * 显示个性化页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward xinitApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SmsApplyForm smsApplyForm = (SmsApplyForm) form;
		TawSystemSessionForm sessionForm = this.getUser(request);
		ISmsApplyManager mgr = (ISmsApplyManager) getBean("IsmsApplyManager");
		String serviceId = request.getParameter("serviceId");
		//获取
		SmsApply smsApply = mgr.getApply(serviceId,sessionForm.getUserid(),MsgConstants.DIYED);
		smsApplyForm = (SmsApplyForm) convert(smsApply);
		//将服务列表写入页面供显示
		request.setAttribute("smsApplyForm", smsApplyForm);
		return mapping.findForward("diyApply");
	}
	/**
	 * 展现取消服务树
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardCacel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray jsonRoot = new JSONArray();
		TawSystemSessionForm sessionForm = this.getUser(request);
		ISmsApplyManager mgr = (ISmsApplyManager) getBean("IsmsApplyManager");
		ISmsServiceManager smgr = (ISmsServiceManager) getBean("IsmsServiceManager");
		SmsApply apply = null;
		SmsService service = null;
		String jsonroot = "";
		//获取
		//List applyList = mgr.getDeletedApplys(sessionForm.getUserid(), MsgConstants.DELETED);
		List applyList = mgr.getCancelApplys(sessionForm.getUserid());
		List serviceList = smgr.getCancelServices(sessionForm.getUserid());
		if(applyList != null && applyList.size() !=0) {			
			Iterator it = applyList.iterator();
			while(it.hasNext()) {
				JSONObject jitem = new JSONObject();
				apply = (SmsApply)it.next();				
				jitem.put(UIConstants.JSON_NODETYPE, "smsService");
				jitem.put("id", apply.getServiceId());
				jitem.put("name",apply.getName());
				jsonRoot.put(jitem);
			}
		} 
		if(serviceList != null && serviceList.size() !=0){
			Iterator sit = serviceList.iterator();
			while(sit.hasNext()) {
				JSONObject sjitem = new JSONObject();
				service = (SmsService)sit.next();				
				sjitem.put(UIConstants.JSON_NODETYPE, "smsService");
				sjitem.put("id", service.getId());
				sjitem.put("name",service.getName());
				jsonRoot.put(sjitem);
			}
		} 
		if(applyList == null && serviceList ==null){
			jsonroot = "[]";
		}
		jsonroot = jsonRoot.toString();
		request.setAttribute("jsonString", jsonroot);
		return mapping.findForward("cacleApply");
	}
	/**
	 * 展现订制服务树
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardCustom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		JSONArray jsonRoot = new JSONArray();
		TawSystemSessionForm sessionForm = this.getUser(request);
		ISmsApplyManager mgr = (ISmsApplyManager) getBean("IsmsApplyManager");
		ISmsServiceManager smgr = (ISmsServiceManager) getBean("IsmsServiceManager");
		SmsApply apply = null;
		SmsService service = null;
		String jsonroot = "";
		//获取
		//List applyList = mgr.getDeletedApplys(sessionForm.getUserid(), MsgConstants.DELETED);
		List applyList = mgr.getCustomApplys(sessionForm.getUserid());
		List serviceList = smgr.getCustomServices(sessionForm.getUserid());
		if(applyList != null && applyList.size() !=0) {			
			Iterator it = applyList.iterator();
			while(it.hasNext()) {
				JSONObject jitem = new JSONObject();
				apply = (SmsApply)it.next();				
				jitem.put(UIConstants.JSON_NODETYPE, "smsService");
				jitem.put("id", apply.getServiceId());
				jitem.put("name",apply.getName());
				jsonRoot.put(jitem);
			}
		} 
		if(serviceList != null && serviceList.size() !=0){
			Iterator sit = serviceList.iterator();
			while(sit.hasNext()) {
				JSONObject sjitem = new JSONObject();
				service = (SmsService)sit.next();				
				sjitem.put(UIConstants.JSON_NODETYPE, "smsService");
				sjitem.put("id", service.getId());
				sjitem.put("name",service.getName());
				jsonRoot.put(sjitem);
			}
		} 
		if(applyList == null && serviceList ==null){
			jsonroot = "[]";
		}
		jsonroot = jsonRoot.toString();
		request.setAttribute("jsonString", jsonroot);
		return mapping.findForward("customApply");
	}
	public String getNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = request.getParameter("node");

		TawSystemTreeBo treebo = TawSystemTreeBo.getInstance();
		JSONArray jsonRoot = treebo.getCheckServiceTree(nodeId, "");

		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().print(jsonRoot.toString());
		return null;
	}
	

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SmsApplyForm smsApplyForm = (SmsApplyForm) form;

		// if an id is passed in, look up the user - otherwise
		// don't do anything - user is doing an add
		if (smsApplyForm.getId() != null) {
			ISmsApplyManager mgr = (ISmsApplyManager) getBean("IsmsApplyManager");
			SmsApply smsApply = mgr.getSmsApply(smsApplyForm.getId());
			smsApplyForm = (SmsApplyForm) convert(smsApply);
			updateFormBean(mapping, request, smsApplyForm);
		}

		return mapping.findForward("edit");
	}

	public ActionForward xsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = this.getUser(request);
		String userId = sessionForm.getUserid();
		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		SmsApplyForm smsApplyForm = (SmsApplyForm) form;
		boolean isNew = ("".equals(smsApplyForm.getId()) || smsApplyForm
				.getId() == null);

		ISmsApplyManager mgr = (ISmsApplyManager) getBean("IsmsApplyManager");
		//SmsApply smsApply = mgr.getSmsApply(smsApplyForm.getId());
		SmsApply smsApply = new SmsApply();
		smsApply = (SmsApply) convert(smsApplyForm);
		smsApply.setDeleted(MsgConstants.DIYED);
		smsApply.setSelStatus("true");
		smsApply.setReceiverId(userId);
		smsApply.setUserId(userId);
		mgr.saveSmsApply(smsApply);

		// add success messages
		if (isNew) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"smsApply.added"));

			// save messages in session to survive a redirect
			saveMessages(request.getSession(), messages);

			return mapping.findForward("success");
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"smsApply.updated"));
			saveMessages(request, messages);

			return mapping.findForward("success");
		}
	}
	/**
	 * 保存取消服务
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward xsaveApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = this.getUser(request);
		String userId = sessionForm.getUserid();
		List applyList = null;
		SmsService sService = null;
		ISmsServiceManager mgr = (ISmsServiceManager) getBean("IsmsServiceManager");
		ISmsApplyManager applyMgr = (ISmsApplyManager) getBean("IsmsApplyManager");
		String servicesId = request.getParameter("servicesId");
		IPojo2PojoService pojo2pojo = (IPojo2PojoService) getBean("Service2Apply");
		if(servicesId != null && !servicesId.equals("")) {
			List servicesList = MsgHelp.getServiceDataFromJson(servicesId);
			//先删除所有之前取消的服务然后在新增开始，开始删除所有			
			//applyList = applyMgr.getDeletedApplys(userId, MsgConstants.DELETED);
			applyList = applyMgr.getCancelApplys(sessionForm.getUserid());
			
			Iterator it = applyList.iterator();
			while(it.hasNext()) {
				applyMgr.removeSmsApply(((SmsApply)it.next()).getId());
			}		
			//新增                 
			Iterator serviceIt = servicesList.iterator();
			while(serviceIt.hasNext()) {
				SmsApply smsApply = null;
				SmsService smsService = null;
				String serviceId = (String)serviceIt.next();
				sService = mgr.getSmsService(serviceId);
				smsApply = applyMgr.getApply(serviceId, userId, MsgConstants.DIYED);
				if(("true").equals(sService.getSelStatus())) {
					if(smsApply != null) {											
						applyMgr.removeSmsApply(smsApply.getId());
					}
				} else {					
					if(smsApply == null) {
						//将service对象复制到apply对象
						SmsApply apply = applyMgr.getApply(serviceId, userId, MsgConstants.DELETED);
						if (null == apply) {
							smsService = mgr.getSmsService(serviceId);
							smsApply = new SmsApply();
							pojo2pojo.p2p(smsService, smsApply);
							smsApply.setReceiverId(userId);
							smsApply.setDeleted(MsgConstants.DELETED);				
							applyMgr.saveSmsApply(smsApply);
						}
					} else {
						// modify by sunshengtai at 08-2-9，原有代码删除后此人又相当于订制了服务，应该是设置删除标志为删除
						smsApply.setDeleted(MsgConstants.DELETED);
						applyMgr.saveSmsApply(smsApply);
					}
				}
			}
		}
		return mapping.findForward("success");
	}
	/**
	 * 保存订制服务
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward xsaveCustomApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = this.getUser(request);
		String userId = sessionForm.getUserid();
		SmsService sService = null;
		String selStatus = "";
		ISmsServiceManager mgr = (ISmsServiceManager) getBean("IsmsServiceManager");
		ISmsApplyManager applyMgr = (ISmsApplyManager) getBean("IsmsApplyManager");
		//查询所有反选的服务
		List unpositiveServiceList = mgr.getServicesBySelStatus(MsgConstants.UNPOSITIVE);
		List allApplyServices = applyMgr.getApplysByReceiverId(userId);
		String servicesId = request.getParameter("servicesId");
		IPojo2PojoService pojo2pojo = (IPojo2PojoService) getBean("Service2Apply");
		if(servicesId != null && !servicesId.equals("")) {
			List servicesList = MsgHelp.getServiceDataFromJson(servicesId);
			//先删除该用户订制的所有服务（包括正选和反选）			
			Iterator it = allApplyServices.iterator();
			while(it.hasNext()) {
				applyMgr.removeSmsApply(((SmsApply)it.next()).getId());
			}		
			//然后根据页面传过来的serviceId新增    

			List toDelList = mgr.getToDelList(unpositiveServiceList, servicesList);
			Iterator toDelIt = toDelList.iterator();
			Iterator serviceIt = servicesList.iterator();
			while(serviceIt.hasNext()) {
				SmsApply smsApply = null;
				SmsService smsService = null;
				String serviceId = (String)serviceIt.next();
				sService = mgr.getSmsService(serviceId);
				selStatus = sService.getSelStatus();
				if(selStatus.equals("true")) {
					//如果该服务是正选，在sms_apply中新增一条deleted=2的记录，证明该人有这个正选服务
					smsApply = new SmsApply();
					pojo2pojo.p2p(sService, smsApply);
					smsApply.setReceiverId(userId);
					smsApply.setDeleted(MsgConstants.DIYED);				
					applyMgr.saveSmsApply(smsApply);
				} 
			}
			while(toDelIt.hasNext()) {
				SmsApply delApply = null;
				delApply = new SmsApply();
				SmsService delService = mgr.getSmsService((String)toDelIt.next());
				pojo2pojo.p2p(delService, delApply);
				delApply.setReceiverId(userId);
				delApply.setDeleted(MsgConstants.DELETED);				
				applyMgr.saveSmsApply(delApply);				
			}
		}
		return mapping.findForward("success");
	}
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"tawDemoMytableList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE); // ҳ��Ĳ����ￄ1�7,����"tawDemoMytableList"��ҳ����displayTag��id
		final Integer pageSize = new Integer(25); // ÿҳ��ʾ������
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1)); // ��ǰҳ��

		ISmsApplyManager mgr = (ISmsApplyManager) getBean("IsmsApplyManager");
		Map map = (Map) mgr.getSmsApplys(pageIndex, pageSize); // map����}��keyֵ��һ����"total",�����ܼ�¼������һ����"result"������Ҫ��ʾҳ���list
		List list = (List) map.get("result");
		request.setAttribute(MsgConstants.SMSAPPLY_LIST, list);
		request.setAttribute("resultSize", map.get("total"));

		return mapping.findForward("list");
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}
	
}
