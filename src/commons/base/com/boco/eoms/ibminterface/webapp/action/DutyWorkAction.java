package com.boco.eoms.ibminterface.webapp.action;

import java.io.OutputStream;
import java.io.PrintStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.cptroom.model.TawSystemCptroom;
import com.boco.eoms.commons.system.cptroom.service.ITawSystemCptroomManager;

import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.ibminterface.dao.DutyWorkDao;

import com.boco.eoms.ibminterface.webapp.form.DutyWork;
import com.boco.eoms.workbench.infopub.util.InfopubConstants;
import com.boco.eoms.workplan.mgr.ITawwpExecuteMgr;
import com.boco.eoms.workplan.model.TawwpMonthPlan;
import com.boco.eoms.workplan.vo.TawwpMonthPlanVO;
import com.boco.eoms.workplan.vo.TawwpStubUserVO;

/**
 * @author 李晓明，龚玉峰
 * 
 */
public final class DutyWorkAction extends BaseAction {

	// jdbc

	private com.boco.eoms.db.util.ConnectionPool ds = com.boco.eoms.db.util.ConnectionPool
			.getInstance();

	/**
	 * 获取机房信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getduty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*String contentPath = request.getScheme() + "://"
				+ request.getLocalAddr() + ":" + request.getLocalPort()
				+ request.getContextPath();*/
		String contentPath = UtilMgrLocator.getEOMSAttributes().getEomsUrl();

		TawSystemCptroom tawSystemCptroom = null;
		List list = null;
		List listtmp = null;
		DutyWorkDao dutyWorkDao = new DutyWorkDao();
		list = dutyWorkDao.getdutyWorkList(StaticMethod.getCurrentDateTime("yyyy-MM-dd"));
		Map map =dutyWorkDao.getdutyWorkMap(StaticMethod.getLocalString());
		// 创建ATOM源
		Factory factory = Abdera.getNewFactory();
		Feed dutyfeed = factory.newFeed();
		for (int i = 0; i < list.size(); i++) {
			tawSystemCptroom = (TawSystemCptroom) list.get(i);
			Entry entry = dutyfeed.insertEntry();
			entry.setTitle(tawSystemCptroom.getRoomname());
//			entry.setText(tawSystemCptroom.getDutyUserCout());
			entry.setRights(String.valueOf(tawSystemCptroom.getDutyUserCout()));//
			entry.setSummary(tawSystemCptroom.getId() + "");
			if(map!=null && map.size()>0){
				String test=(String) map.get(tawSystemCptroom.getId()+"");
				entry.setId(test);	
			}else{
				entry.setId("0");
			}
		
		
			entry.setContent(contentPath + "/duty.do?id="
					+ tawSystemCptroom.getId() + "&method=getdutyList&type=interface&interface=interface&userName=admin");
		}
		OutputStream os = response.getOutputStream();
		PrintStream ps = new PrintStream(os);
		dutyfeed.getDocument().writeTo(ps);
		// end
		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getdutyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获取机房id
		String id = StaticMethod.null2String(request.getParameter("id"));
		int roomId = 0;
		ITawSystemCptroomManager mgr = (ITawSystemCptroomManager) getBean("ItawSystemCptroomManager");
		if (!"".equals(id)) {
			roomId = Integer.parseInt(id);
		}
		// 当前时间
		String times = StaticMethod.getCurrentDateTime();
		List list = null;
		DutyWorkDao dao = new DutyWorkDao();
		list = dao.getdutyList(roomId, times);
		/*String contentPath = request.getScheme() + "://"
		+ request.getLocalAddr() + ":" + request.getLocalPort()
		+ request.getContextPath();*/
		String contentPath = UtilMgrLocator.getEOMSAttributes().getEomsUrl();
		// start

		// 创建ATOM源
		Factory factory = Abdera.getNewFactory();
		Feed dutyfeedList = factory.newFeed();
		// 分页
		for (int i = 0; i < list.size(); i++) {
			DutyWork dutyWork = new DutyWork();
			dutyWork = (DutyWork) list.get(i);
			Entry entry = dutyfeedList.insertEntry();
			entry.setAttributeValue("roomName",mgr.getTawSystemCptroomName(new java.lang.Integer(roomId)));//机房名
			entry.setTitle(dutyWork.getUsername()); // 用户名
			entry.setSummary(dutyWork.getStarttimeDefined()); // 开始时间
			entry.setContent(dutyWork.getEndtimeDefined()); // 结束时间
			entry.setId(dutyWork.getMobile());				// z值班长电话
			entry.setRights(dao.getDutymanStr(Integer
					.parseInt(dutyWork.getId())));  // 其他值班人员，用逗号隔开。
			//entry.setPublished(contentPath+"/duty.do?method=getNextDuty&roomId="+roomId+"&id="+dutyWork.getId()+"&startTime="+dutyWork.getStarttimeDefined()+"&nextFlag=-1&type=interface&interface=interface&userName=admin");// 上一班次url
			entry.setText(contentPath+"/duty.do?method=getNextDuty&roomId="+roomId+"&id="+dutyWork.getId()+"&endTime="+dutyWork.getEndtimeDefined()+"&nextFlag=1&type=interface&interface=interface&userName=admin");// 下一班次url
			entry.setLanguage(contentPath+"/duty.do?method=getNextDuty&roomId="+roomId+"&id="+dutyWork.getId()+"&startTime="+dutyWork.getStarttimeDefined()+"&nextFlag=-1&type=interface&interface=interface&userName=admin");// 上一班次url)
		} 
		System.out.println("/********/" + dutyfeedList.toString() + "/******/");
		OutputStream os = response.getOutputStream();
		PrintStream ps = new PrintStream(os);
		dutyfeedList.getDocument().writeTo(ps);

		return null;
	}

	/**
	 * 显示日常执行作业计划管理(列表)首页Action
	 * 
	 * @param mapping
	 *            ActionMapping 集合
	 * @param actionForm
	 *            ActionForm 数据模型
	 * @param request
	 *            HttpServletRequest 请求
	 * @param response
	 *            HttpServletResponse 应答
	 * @return ActionForward 转向对象
	 */
	private Hashtable performDailyExecuteList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		// 获取当前用户的session中的信息
		String userId = "";
		TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		userId = saveSessionBeanForm.getUserid();
		if(userId ==null || "".equals(userId)){
			userId  = request.getParameter("userName"); // 当前用户�?
		}
		ITawSystemUserManager user = (ITawSystemUserManager) ApplicationContextHolder
		.getInstance().getBean("itawSystemUserManager");
		String deptId = user.getTawSystemUserByuserid(userId).getDeptid();
		// List stubUserList = saveSessionBeanForm.getStubUserList(); //代理信息集合
		List stubUserList = saveSessionBeanForm.getStubUserList();
		// 初始化数�?
		ITawwpExecuteMgr tawwpExecuteMgr = (ITawwpExecuteMgr) getBean("tawwpExecuteMgr");
		Hashtable monthPlanVOHash = null;
		Hashtable tempHash = null;
		Enumeration tempkeys = null;
		String monthPlanId = "";
		TawwpMonthPlanVO tawwpMonthPlanVO = null;
		TawwpStubUserVO tawwpStubUserVO = null;
		List stubMonthPlanList = new ArrayList();
		List listKey = null;
		// 获取执行作业计划集合
		try {
			monthPlanVOHash = tawwpExecuteMgr.listExecutePlanNew(userId, String
					.valueOf(deptId));

			// 如果代理信息存在
			if (stubUserList != null) {

				for (int i = 0; i < stubUserList.size(); i++) {
					// 获取代理信息VO对象
					tawwpStubUserVO = (TawwpStubUserVO) stubUserList.get(i);
					// 获取代理申请人需要执行的月度作业计划
					tempHash = new Hashtable();
					tempHash = tawwpExecuteMgr.listExecutePlan(tawwpStubUserVO
							.getCruser(), String.valueOf(deptId));
					if (tempHash.size() != 0) {
						// 取出全部月度作业计划编号
						tempkeys = tempHash.keys();

						while (tempkeys.hasMoreElements()) {
							monthPlanId = (String) tempkeys.nextElement();
							// 获取月度作业计划VO对象,并修改代理标志位
							tawwpMonthPlanVO = (TawwpMonthPlanVO) (tempHash
									.get(monthPlanId));
							tawwpMonthPlanVO.setStubFlag("1");
							tawwpMonthPlanVO.setUserByStub(tawwpStubUserVO
									.getCruser()); // 修改被代理用户名
						}
						stubMonthPlanList.add(tempHash);
					}
				}
			}

			Enumeration hashKeys = null;

			listKey = new ArrayList();
			TawwpMonthPlan tawwpMonthPlan = null;
			if (monthPlanVOHash.size() != 0) {
				hashKeys = monthPlanVOHash.keys();
				while (hashKeys.hasMoreElements()) {
					tawwpMonthPlan = (TawwpMonthPlan) (hashKeys.nextElement());

					listKey.add(tawwpMonthPlan);
					tawwpMonthPlan = null;
				}
				Collections.sort(listKey);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monthPlanVOHash;
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getWorkPlanList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 版块id
		Hashtable monthPlanVOHash = performDailyExecuteList(mapping, form, request,
				response);
		Enumeration hashKeys = null;
		/*String contentPath = request.getScheme() + "://"
		+ request.getLocalAddr() + ":" + request.getLocalPort()
		+ request.getContextPath();*/
		String contentPath = UtilMgrLocator.getEOMSAttributes().getEomsUrl();
		List workPlanList = new ArrayList();
		TawwpMonthPlan tawwpMonthPlan = null;
		if (monthPlanVOHash.size() != 0) {
			hashKeys = monthPlanVOHash.keys();
			while (hashKeys.hasMoreElements()) {
				tawwpMonthPlan = (TawwpMonthPlan) (hashKeys.nextElement());
				workPlanList.add(tawwpMonthPlan);
				tawwpMonthPlan = null;
			}
			Collections.sort(workPlanList);
		}
		TawwpMonthPlanVO tawwpMonthPlanVO = null;
		// 加上时间段限制过滤(开始)
		Factory factory = Abdera.getNewFactory();
		Feed feed = factory.newFeed();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		TawwpMonthPlan tawwpMonthPlan2 = null;
		for(int i=0;i<workPlanList.size();i++){
				tawwpMonthPlan2 = (TawwpMonthPlan)workPlanList.get(i);
				tawwpMonthPlanVO = (TawwpMonthPlanVO)monthPlanVOHash.get(tawwpMonthPlan2);
				Entry entry = feed.insertEntry();
				entry.setTitle(tawwpMonthPlanVO.getName());
				String url =contentPath+"/workplan/tawwpexecute/executeadds.do?monthplanid=" + tawwpMonthPlanVO.getId()
				+ "&flag=daily";
				entry.setSummary(tawwpMonthPlanVO.getNetName()); //   网元名称
				entry.setText(tawwpMonthPlanVO.getPrincipal()); // 执行负责人名称
				entry.setContent(url);
			}
		feed.setTitle(contentPath + "/workplan/tawwpexecute/dailyexecutelist.do");
		System.out.println(feed.toString());
    	OutputStream os = response.getOutputStream();
		PrintStream ps = new PrintStream(os);
		feed.getDocument().writeTo(ps);

		return null;
	}

	/**
	 * 取上一班次或者下一班次
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getNextDuty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//TODO  乱
		String id = StaticMethod.null2String(request.getParameter("id")); // 班次id
		String roomid = StaticMethod
				.null2String(request.getParameter("roomId")); // 机房id
		ITawSystemCptroomManager mgr = (ITawSystemCptroomManager) getBean("ItawSystemCptroomManager");
		String roomName=mgr.getTawSystemCptroomName(new java.lang.Integer(StaticMethod.null2int(roomid)));
//		entry.setAttributeValue("roomName",roomName);
		String nextFlag = StaticMethod.null2String(request
				.getParameter("nextFlag")); // 标志。 如果为 -1 则为上一班次，0 为本班次，1为下一班次

		String startTime = StaticMethod.null2String(request
				.getParameter("startTime"));
		String endTime = StaticMethod.null2String(request
				.getParameter("endTime"));
		List list = new ArrayList();
		DutyWorkDao dao = new DutyWorkDao();
		Factory factory = Abdera.getNewFactory();
		Feed dutyfeedList = factory.newFeed();
		/*String contentPath = request.getScheme() + "://"
		+ request.getLocalAddr() + ":" + request.getLocalPort()
		+ request.getContextPath();*/
		String contentPath = UtilMgrLocator.getEOMSAttributes().getEomsUrl();
		// 当前时间
		String times = StaticMethod.getCurrentDateTime();
		// 如果显示的没有班次
		if (id == null || "".equals(id)) {
			if ("-1".equals(nextFlag)) {  //向上

				list = dao.getNextNowNullUpduty(roomid, times);
				if (list != null) {
					DutyWork dutyWork = new DutyWork();
					dutyWork = (DutyWork) list.get(0);
					Entry entry = dutyfeedList.insertEntry();
					entry.setTitle(dutyWork.getUsername()); // 用户名
					entry.setSummary(dutyWork.getStarttimeDefined()); // 开始时间
					entry.setContent(dutyWork.getEndtimeDefined()); // 结束时间
					entry.setId(dutyWork.getMobile());
					entry.setAttributeValue("roomName",roomName);
					entry.setRights(dao.getDutymanStr(Integer.parseInt(dutyWork
							.getId())));
					entry.setText(contentPath+"/duty.do?method=getNextDuty&roomId="+roomid+"&id="+dutyWork.getId()+"&endTime="+dutyWork.getEndtimeDefined()+"&nextFlag=1&type=interface&interface=interface&userName=admin");// 下一班次url
					entry.setLanguage(contentPath+"/duty.do?method=getNextDuty&roomId="+roomid+"&id="+dutyWork.getId()+"&startTime="+dutyWork.getStarttimeDefined()+"&nextFlag=-1&type=interface&interface=interface&userName=admin");// 上一班次url)
						
				}
			} else if ("1".equals(nextFlag)) { // 向下
				list = dao.getNextNowNullDownduty(roomid, times);
				if (list != null) {
					DutyWork dutyWork = new DutyWork();
					dutyWork = (DutyWork) list.get(0);
					Entry entry = dutyfeedList.insertEntry();
					entry.setTitle(dutyWork.getUsername()); // 用户名
					entry.setSummary(dutyWork.getStarttimeDefined()); // 开始时间
					entry.setContent(dutyWork.getEndtimeDefined()); // 结束时间
					entry.setId(dutyWork.getMobile());
					entry.setAttributeValue("roomName",roomName);
					entry.setRights(dao.getDutymanStr(Integer.parseInt(dutyWork
							.getId())));
					entry.setText(contentPath+"/duty.do?method=getNextDuty&roomId="+roomid+"&id="+dutyWork.getId()+"&endTime="+dutyWork.getEndtimeDefined()+"&nextFlag=1&type=interface&interface=interface&userName=admin");// 下一班次url
					entry.setLanguage(contentPath+"/duty.do?method=getNextDuty&roomId="+roomid+"&id="+dutyWork.getId()+"&startTime="+dutyWork.getStarttimeDefined()+"&nextFlag=-1&type=interface&interface=interface&userName=admin");// 上一班次url)
					
				}
			}
		} else {

			if (!"".equals(startTime) && "-1".equals(nextFlag)) { // 表明为点击上班次
				list = dao.getNextUpduty(roomid, startTime);
				for (int i = 0; i < list.size(); i++) {
					DutyWork dutyWork = new DutyWork();
					dutyWork = (DutyWork) list.get(i);
					Entry entry = dutyfeedList.insertEntry();
					entry.setTitle(dutyWork.getUsername()); // 用户名
					entry.setSummary(dutyWork.getStarttimeDefined()); // 开始时间
					entry.setContent(dutyWork.getEndtimeDefined()); // 结束时间
					entry.setId(dutyWork.getMobile());
					entry.setAttributeValue("roomName",roomName);
					entry.setRights(dao.getDutymanStr(Integer.parseInt(dutyWork
							.getId())));
					entry.setText(contentPath+"/duty.do?method=getNextDuty&roomId="+roomid+"&id="+dutyWork.getId()+"&endTime="+dutyWork.getEndtimeDefined()+"&nextFlag=1&type=interface&interface=interface&userName=admin");// 下一班次url
					entry.setLanguage(contentPath+"/duty.do?method=getNextDuty&roomId="+roomid+"&id="+dutyWork.getId()+"&startTime="+dutyWork.getStarttimeDefined()+"&nextFlag=-1&type=interface&interface=interface&userName=admin");// 上一班次url)
					
				}
			} else if (!"".equals(endTime) && "1".equals(nextFlag)) {
				list = dao.getNextDownduty(roomid, endTime);
				for (int i = 0; i < list.size(); i++) {
					DutyWork dutyWork = new DutyWork();
					dutyWork = (DutyWork) list.get(i);
					Entry entry = dutyfeedList.insertEntry();
					entry.setTitle(dutyWork.getUsername()); // 用户名
					entry.setSummary(dutyWork.getStarttimeDefined()); // 开始时间
					entry.setContent(dutyWork.getEndtimeDefined()); // 结束时间
					entry.setId(dutyWork.getMobile());
					entry.setAttributeValue("roomName",roomName);
					entry.setRights(dao.getDutymanStr(Integer.parseInt(dutyWork
							.getId())));
					entry.setText(contentPath+"/duty.do?method=getNextDuty&roomId="+roomid+"&id="+dutyWork.getId()+"&endTime="+dutyWork.getEndtimeDefined()+"&nextFlag=1&type=interface&interface=interface&userName=admin");// 下一班次url
					entry.setLanguage(contentPath+"/duty.do?method=getNextDuty&roomId="+roomid+"&id="+dutyWork.getId()+"&startTime="+dutyWork.getStarttimeDefined()+"&nextFlag=-1&type=interface&interface=interface&userName=admin");// 上一班次url)
					
				}
			}
		}
		OutputStream os = response.getOutputStream();
		PrintStream ps = new PrintStream(os);
		dutyfeedList.getDocument().writeTo(ps);
		return null;
	}

}
