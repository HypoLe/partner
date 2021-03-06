package com.boco.eoms.base.webapp.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.boco.eoms.commons.system.session.bo.TawSystemSessionBo;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.workplan.mgr.ITawwpExecuteMgr;

/**
 * @author gongyufeng
 * @see 新增一个和门户的接口action
 * 
 */
public class InterfaceAction extends BaseAction {

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see 获取url，返回atom信息。 title
	 *      已处理的作业计划数，summary未处理的作业计划数，content是判断是否处于值班状态，返回url
	 */
	public ActionForward getInfor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 当前用户
		String userid = request.getParameter("userName");
		Hashtable hashtable = null;
		Hashtable hashtable2 = null;
		try {
			// atom 数据源
			Factory factory = Abdera.getNewFactory();
			Feed feed = factory.newFeed();
			Entry entry = feed.insertEntry();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();

			ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");
			// 根据id 得到对象
			TawSystemUser user = (TawSystemUser) userManager
					.getUserByuserid(userid);
			// 得到参数部门id
			String deptid = user.getDeptid();
			// 注入作业计划执行mgr
			ITawwpExecuteMgr tawwpExecuteMgr = (ITawwpExecuteMgr) getBean("tawwpExecuteMgr");
			// 取已执行的数目
			hashtable = tawwpExecuteMgr.countExecute(userid, deptid, "");
			// 取未执行的数据
			hashtable2 = tawwpExecuteMgr.countExecute2(userid, deptid, "");
			// 放在atom
			if (hashtable2 != null)
				entry.setSummary(String.valueOf(hashtable.size()));
			else
				entry.setSummary("0");
			// 放在atom
			if (hashtable != null)
				entry.setTitle(String.valueOf(hashtable2.size()));
			else
				entry.setTitle("0");
			// 值班状态
			/*List list = TawSystemSessionBo.getRoomInfo(userid);
			if (list.size() != 0) {
				entry.setContent("1");
				entry.setLanguage(basePath + "/main.jsp?id=30");// 值班URL
			} else {
				entry.setContent("0"); 	
				entry.setLanguage("");
			}*/
			entry.setBaseUri(basePath
					+ "/workplan/tawwpexecute/dailyexecutelist.do");// 已执行URL
			entry.setText(basePath
					+ "/workplan/tawwpexecute/dailyexecutelist.do");// 未执行URL

			OutputStream os = response.getOutputStream();
			PrintStream ps = new PrintStream(os);
			feed.getDocument().writeTo(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 值班信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getdutyInfor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = request.getParameter("userName");
		Factory factory = Abdera.getNewFactory();
		Feed feed = factory.newFeed();
		Entry entry = feed.insertEntry();
		String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
		// 验证session内是否有值班状态。如果有直接跳转
		if(sessionform.getWorkSerial()!="0"){
			entry.setContent("1");
			entry.setLanguage(basePath + "/main.jsp?id=30");// 值班URL
		}else{// 如果没有 则到数据库内去判断是否有志班的机房，如果有1个则直接跳装。用cas的filter去取一个机房的信息 如果有两个以上则url为选择机房的页面，如果没有则说明没有
			List list = TawSystemSessionBo.getRoomInfo(userid);
			//if (list.size() == 1) { // 一个的时候
				//entry.setContent("1");
				//entry.setLanguage(basePath + "/main.jsp?id=30");// 值班URL
			//} else
			if (list.size() == 0){ // 没有的时候
				entry.setContent("0"); 	
				entry.setLanguage("");
			}else {   
				entry.setContent("1"); 	// 一个以上的时候
				entry.setLanguage(basePath+"/roomSelectArea.do?method=roomSelectArea");
			}
		}
		OutputStream os = response.getOutputStream();
		PrintStream ps = new PrintStream(os);
		feed.getDocument().writeTo(ps);
		return null;
	}
}
