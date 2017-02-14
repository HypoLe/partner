package com.boco.eoms.partner.home.webapp.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.boco.eoms.partner.home.mgr.PublishInfoMgr;
import com.boco.eoms.partner.home.mgr.PublishMgr;
import com.boco.eoms.partner.home.mgr.TaskMgr;
import com.boco.eoms.partner.home.model.PublishInfo;
import com.boco.eoms.partner.home.model.PublishTask;
import com.boco.eoms.partner.home.util.StateType;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * <p>
 * Title:公告基本信息s
 * </p>
 * <p>
 * Description:公告基本信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class PublishAction extends BaseAction {
	/**
	 * 列表类型 listType（草稿 1，待审批 2，待查看 3，历史公告4）
	 */
	public int listType = 0;
	/**
	 * 根据 参数 取得Jsp页面
	 * 手机需要传递的参数。type:android;id:公告的物理id;pageName:detail阅读公告的page name;isRead:0执行阅知,1只是查看.
	   例如:publish.do?method=getGsp&type=android&pageName=detail&isRead=0&id=${publishList.id }
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  getGsp(ActionMapping mapping, ActionForm form,
													HttpServletRequest request, HttpServletResponse response){
		String pageName = request.getParameter("pageName");
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		boolean mobileRequest = !"".equals(type)&&"android".equalsIgnoreCase(type);
		String publishId = StaticMethod.null2String(request.getParameter("id"));//公告物理id		
		PublishInfoMgr infoMgr = (PublishInfoMgr) getBean("refpublishInfoMgr");
		
		//编辑/查看详细 草稿页面
		PublishInfo publishInfo = infoMgr.find(publishId);				
		if(publishInfo==null){
			publishInfo=new PublishInfo();
			TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			publishInfo.setPublishTime(StaticMethod.getTimestamp());
			publishInfo.setPublisherDeptId(sessionInfo.getDeptid());
			publishInfo.setPublisherDeptName(sessionInfo.getDeptname());
			publishInfo.setPublisherId(sessionInfo.getUserid());
			publishInfo.setPublisherName(sessionInfo.getUsername());
		}
		request.setAttribute("publishInfo", publishInfo);
		
		if("draftsDetail".equals(pageName)){
			pageName = "detail";
		}		
		else if("editDrafts".equals(pageName)){
			pageName="draftsAdd_Edit";
		}		
		
		if("detail".equals(pageName)){
			int isRead=Integer.parseInt(request.getParameter("isRead"));//0执行阅知，1：只是查看
			request.setAttribute("isRead", isRead);
			/*与业务联系函类似
			 *search(){}  distinct main.* 还是有用的，因为当发布范围用用户A，转发时又有A，那么A就会 有2个task对应同一个联系函，
	    	   * 那么在A处理这个业务联系函时与此用户此业务联系函相关的task全部都应设置终止。 现在代码的实现是：不是一次处理2个task，
	    	   * 而是要处理两次 getJsp中
	    	    for (ContactTask contactTask : tasks) {
	  				if("1".equals(contactTask.getTaskState()+"")){//正在运行
	    				if(sessionInfo.getUserid().equals(contactTask.getTaskOwnerId()) ||sessionInfo.getDeptid().equals(contactTask.getTaskOwnerId()))
								currentTaskId = contactTask.getId();
	  				}
				}
	    	  */
			
			String taskId="";
			if(isRead==0){
				TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
				TaskMgr taskMgr=(TaskMgr)ApplicationContextHolder.getInstance().getBean("reftaskMgr");
				Search search=new Search();
				search.addFilterEqual("mainId", publishId);
				search.addFilterEqual("taskOwnerId", sessionInfo.getUserid());
				search.addFilterEqual("taskType",StateType.OPERATE_READ);
				search.addFilterEqual("taskState", StateType.TASK_RUNNING);
				PublishTask task=taskMgr.search(search).get(0);
				taskId= task.getId();
				request.setAttribute("taskId",taskId);
			}
			
			//add for mobile android.
			if(mobileRequest){
				JSONObject jsonObject = JSONObject.fromObject(publishInfo);
				if(isRead==0){
					jsonObject.accumulate("taskId", taskId);
				}
				jsonObject.remove("publishTime");
				jsonObject.put("publishTime", CommonUtils.toEomsStandardDate(publishInfo.getPublishTime()));
				jsonObject.remove("valid");
				jsonObject.put("valid", CommonUtils.toEomsStandardDate(publishInfo.getValid()));
				System.out.println(jsonObject.toString());
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain");
				try {
					response.getWriter().write(jsonObject.toString());
					response.getWriter().flush();
					response.getWriter().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			
		}
		
		
		return mapping.findForward(pageName);
	}
	/**
	 * 保存公告草稿
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  saveDrafts(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response){
		PublishMgr publishMgr = (PublishMgr) getBean("refpublishMgr");
		PublishInfo pInfo=publishMgr.saveDrafts(request,form);
		
		ActionForward aForward=new ActionForward();
		aForward.setPath("/publish.do?method=getGsp&pageName=draftsAdd_Edit&pageType=editDrafts&id="+pInfo.getId());
		request.setAttribute("saveSuccess", true);
		request.setAttribute("saveMessage", "保存成功");
		aForward.setRedirect(false);
		return aForward;
		//return mapping.findForward("success");
	}

	
	/**
	 * 公告作废
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  invalid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PublishMgr publishMgr = (PublishMgr) getBean("refpublishMgr");
		publishMgr.invalid(request,form);
		return mapping.findForward("success");
	}
	/**
	 * 不通过审批 直接发布公告
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  directlyPublish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PublishMgr publishMgr = (PublishMgr) getBean("refpublishMgr");
		publishMgr.directlyPublish(request,form);
		return mapping.findForward("success");
		}
	/**
	 * 送审
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  toAudit(ActionMapping mapping, ActionForm form,
	  HttpServletRequest request, HttpServletResponse response){
		PublishMgr publishMgr = (PublishMgr) getBean("refpublishMgr");
		publishMgr.toAudit(request,form);
		return mapping.findForward("success");
	}
//	/**
//	 * 新建草稿 直接送审
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	public ActionForward  directlyToAudit(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response){
//		PublishMgr publishMgr = (PublishMgr) getBean("refpublishMgr");
//		publishMgr.directlyToAudit(request,form);
//		return mapping.findForward("success");
//		}
	/**
	 * 审批通过
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  auditPass(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response){
		PublishMgr publishMgr = (PublishMgr) getBean("refpublishMgr");
		publishMgr.auditPass(request,form);
		return mapping.findForward("success");
		}
//	/**
//	 * 审批通过 移交下一审批人
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	public ActionForward  auditPassToNextAudit(ActionMapping mapping, ActionForm form,
//															HttpServletRequest request, HttpServletResponse response){
//		PublishMgr publishMgr = (PublishMgr) getBean("refpublishMgr");
//		publishMgr.auditPassToNextAudit(request,form);
//		return mapping.findForward("success");
//		}
	/**
	 * 驳回
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  auditReject(ActionMapping mapping, ActionForm form,
															HttpServletRequest request, HttpServletResponse response){
		PublishMgr publishMgr = (PublishMgr) getBean("refpublishMgr");
		publishMgr.auditReject(request,form);
		return mapping.findForward("success");
		}
	
	/**
	 * 执行 阅知公告：
	 * 需传递的参数 id:公告的物理id;taskId:执行这个操作的taskId;operateContent:(可选)阅知意见.
	 * 在getGsp()中，当是执行阅知待阅公告时，我传递了了一个参数taskId的，此处就是那个传递的taskId。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  read(ActionMapping mapping, ActionForm form,															HttpServletRequest request, HttpServletResponse response){
		PublishMgr publishMgr = (PublishMgr) getBean("refpublishMgr");
		publishMgr.read(request,form);
		return mapping.findForward("success");
	}
	
	 
	/**
	 * 公告列表（草稿，待审批，待查看，历史公告等）
	 * 需传递的参数listType=3或4,type=android,pageIndex=xxx,可选的subjectStringLike,publishTimeDateGreaterThan,publishTimeDateLessThan,validDateGreaterThan,validDateLessThan
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  getList(ActionMapping mapping, ActionForm form,
													HttpServletRequest request, HttpServletResponse response){
		PublishMgr publishMgr = (PublishMgr) getBean("refpublishMgr");
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		boolean mobileRequest= (!"".equals(type))&&"android".equalsIgnoreCase(type);
		String pageIndexName="";
		Integer pageIndex=0;
		Integer pageSize=15;
		if(mobileRequest){
//			 pageSize=CommonConstants.PAGE_SIZE;			
			 pageIndex= "".equals(Strings.nullToEmpty(request.getParameter("pageIndex")))?0: Integer.parseInt(Strings.nullToEmpty(request.getParameter("pageIndex")));
//			 System.out.println(pageIndex);
		}else{
			  pageIndexName = new org.displaytag.util.ParamEncoder("publishList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
			  pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
			  pageIndex = new Integer(GenericValidator
						.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
						: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		}
      
        
		System.out.println("pageSize:"+pageSize+",pageIndex:"+pageIndex);	
			
		listType = Integer.parseInt(request.getParameter("listType"));
		Search publishInfoSearch = new Search();
		publishInfoSearch = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), publishInfoSearch,"");	
	    SearchResult<PublishInfo> result = publishMgr.getList(request, listType, pageIndex, pageSize,publishInfoSearch);
	         
	    if(mobileRequest&&(listType==3||listType==4)){
	    	JSONObject jsonObject=new JSONObject();
	    	jsonObject.put("listType", listType);
	    	jsonObject.put("pageSize", pageSize);
	    	jsonObject.put("resultSize", result.getTotalCount());   	
	    	jsonObject.put("subjectStringLike", StaticMethod.null2String(request.getParameter("subjectStringLike")));
	    	jsonObject.put("publishTimeDateGreaterThan",  StaticMethod.null2String(request.getParameter("publishTimeDateGreaterThan")));
	    	jsonObject.put("publishTimeDateLessThan", StaticMethod.null2String(request.getParameter("publishTimeDateLessThan")));
	    	jsonObject.put("validDateGreaterThan", StaticMethod.null2String(request.getParameter("validDateGreaterThan")));
	    	jsonObject.put("validDateLessThan", StaticMethod.null2String(request.getParameter("validDateLessThan")));
	    	JSONArray jsonArray=new JSONArray();
	    	for(PublishInfo publishInfo:result.getResult()){
	    		JSONObject jObject=JSONObject.fromBean(publishInfo);
	    		jObject.remove("publishTime");
	    		jObject.put("publishTime", CommonUtils.toEomsStandardDate(publishInfo.getPublishTime()));
	    		jObject.remove("valid");
	    		jObject.put("valid", CommonUtils.toEomsStandardDate(publishInfo.getValid()));
	    		jsonArray.put(jObject);
	    	}
	    	jsonObject.put("publishList",jsonArray);    	
	    	System.out.println(jsonObject.toString());
	    	response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			try {
				response.getWriter().write(jsonObject.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	return null;
	    	
	    
	    }else{
	    	request.setAttribute("pageSize", pageSize);
	   		request.setAttribute("resultSize", result.getTotalCount());
	   		request.setAttribute("publishList", result.getResult());    
	   		request.setAttribute("subjectStringLike", StaticMethod.null2String(request.getParameter("subjectStringLike")));
	   		request.setAttribute("publishTimeDateGreaterThan", StaticMethod.null2String(request.getParameter("publishTimeDateGreaterThan")));
	   		request.setAttribute("publishTimeDateLessThan", StaticMethod.null2String(request.getParameter("publishTimeDateLessThan")));
	   		request.setAttribute("validDateGreaterThan", StaticMethod.null2String(request.getParameter("validDateGreaterThan")));
	   		request.setAttribute("validDateLessThan", StaticMethod.null2String(request.getParameter("validDateLessThan")));
	   		request.setAttribute("listType",listType);
	   		
	  		return mapping.findForward("list");
	    }
      
	}
}
