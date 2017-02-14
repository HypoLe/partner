package com.boco.eoms.commons.system.role.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.accessories.util.AccessoriesMgrLocator;
import com.boco.eoms.commons.system.role.model.TawSystemRole;
import com.boco.eoms.commons.system.role.model.TawSystemRoleImport;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleImportManager;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleManager;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.role.webapp.form.TawSystemRoleImportForm;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.roleWorkflow.mgr.ITawSystemRoleWorkflowManager;
import com.boco.eoms.roleWorkflow.model.TawSystemRoleWorkflow;

/**
 * Action class to handle CRUD on a TawSystemRoleImport object
 * 
 * @struts.action name="tawSystemRoleImportForm" path="/tawSystemRoleImports"
 *                scope="request" validate="false" parameter="method"
 *                input="main"
 * @struts.action-set-property property="cancellable" value="true"
 * 
 * @struts.action-forward name="main"
 *                        path="/WEB-INF/pages/tawSystemRoleImport/tawSystemRoleImportTree.jsp"
 *                        contextRelative="true"
 * 
 * @struts.action-forward name="edit"
 *                        path="/WEB-INF/pages/tawSystemRoleImport/tawSystemRoleImportForm.jsp"
 *                        contextRelative="true"
 * 
 * @struts.action-forward name="list"
 *                        path="/WEB-INF/pages/tawSystemRoleImport/tawSystemRoleImportList.jsp"
 *                        contextRelative="true"
 */
public final class TawSystemRoleImportAction extends BaseAction {
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// return mapping.findForward("search");
		return null;
	}

	public ActionForward main(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("main");
	}

	/**
	 * 根据父节点的id得到�?有子节点的JSON数据
	 */
	public void xGetChildNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = request.getParameter("node");

		ITawSystemRoleImportManager mgr = (ITawSystemRoleImportManager) getBean("tawSystemRoleImportManager");
		JSONArray json = mgr.xGetChildNodes(nodeId);

		JSONUtil.print(response, json.toString());
	}

	/**
	 * 保存
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

		TawSystemRoleImportForm tawSystemRoleImportForm = (TawSystemRoleImportForm) form;
		if (null == tawSystemRoleImportForm.getAccessoriesId() || "".equals(tawSystemRoleImportForm.getAccessoriesId())) {
			request.setAttribute("noAccessories", "请上传需要导入的文件！");
			return edit(mapping, form, request, response);
		}
		else {
			request.setAttribute("noAccessories", "");
		}
		ITawSystemRoleImportManager mgr = (ITawSystemRoleImportManager) getBean("tawSystemRoleImportManager");
		TawSystemRoleImport tawSystemRoleImport = (TawSystemRoleImport) convert(tawSystemRoleImportForm);
		tawSystemRoleImport.setVersionAt(new Date());
		mgr.saveTawSystemRoleImport(tawSystemRoleImport);
		return forwardlist(mapping);
	}

	/**
	 * 转向历史列表
	 * 
	 * @param mapping
	 * @return
	 */
	public ActionForward forwardlist(ActionMapping mapping) {
		ActionForward forward = mapping.findForward("forwardlist");
		String path = forward.getPath();
		return new ActionForward(path, false);
	}

	/**
	 * 根据模块或功能的编码，删除该对象
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TawSystemRoleImportForm tawSystemRoleImportForm = (TawSystemRoleImportForm) form;

		ITawSystemRoleImportManager mgr = (ITawSystemRoleImportManager) getBean("tawSystemRoleImportManager");
		mgr.removeTawSystemRoleImport(tawSystemRoleImportForm.getId());
		return forwardlist(mapping);

	}

	/**
	 * ajax请求修改某节点的详细信息
	 */
	public String xedit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemRoleImportForm tawSystemRoleImportForm = (TawSystemRoleImportForm) form;

		if (tawSystemRoleImportForm.getId() != null) {
			ITawSystemRoleImportManager mgr = (ITawSystemRoleImportManager) getBean("tawSystemRoleImportManager");
			TawSystemRoleImport tawSystemRoleImport = (TawSystemRoleImport) convert(tawSystemRoleImportForm);

			mgr.saveTawSystemRoleImport(tawSystemRoleImport);
			// mgr.updateTawSystemRoleImport(tawSystemRoleImport);
		}

		return null;
	}

	/**
	 * ajax请求获取某节点的详细信息
	 */
	public void xget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String _strId = request.getParameter("id");
		ITawSystemRoleImportManager mgr = (ITawSystemRoleImportManager) getBean("tawSystemRoleImportManager");
		TawSystemRoleImport tawSystemRoleImport = mgr
				.getTawSystemRoleImport(_strId);

		JSONObject jsonRoot = new JSONObject();
		jsonRoot = JSONObject.fromObject(tawSystemRoleImport);

		JSONUtil.print(response, jsonRoot.toString());
	}

	/**
	 * 初使化页面
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

		return mapping.findForward("edit");
	}

	/**
	 * 显示列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 版块id
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"tawSystemRoleImportList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		ITawSystemRoleImportManager mgr = (ITawSystemRoleImportManager) getBean("tawSystemRoleImportManager");
		// 取某版块未阅读记录列表
		Map map = (Map) mgr.getTawSystemRoleImports(pageIndex, pageSize);

		List list = (List) map.get("result");
		request.setAttribute("tawSystemRoleImportList", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");

	}

	/**
	 * 导入版本
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importrole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer sheet = new Integer(0);
		ITawSystemRoleImportManager mgr = (ITawSystemRoleImportManager) getBean("tawSystemRoleImportManager");
		TawSystemRoleImport tawSystemRoleImport = mgr
				.getTawSystemRoleImport(request.getParameter("id"));

		String filePath = AccessoriesMgrLocator
				.getTawCommonsAccessoriesManagerCOS().getFilePath(
						RoleConstants.ROLE_ACCESSORIES_APP_ID);
		// 符件保存格式为'1234535.xls'，去掉单引号
		String fileName = tawSystemRoleImport.getAccessoriesId().replace("'",
				"");
		Map map = mgr.mappingRoleExcel(filePath + fileName);
		List list=(List)map.get(sheet);
		if (map == null || !map.containsKey(sheet) || list==null || list.isEmpty()) {
			// 导入失败
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"TawSystemRoleImportAction.import.error"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		}
		
		
//		mgr.importRole(list, tawSystemRoleImport);
		//------------2009-5-13 -----
		String info = mgr.importRole1(list, tawSystemRoleImport);
		if(!info.equals("")){
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"TawSystemRoleImportAction.import.existInfo",info));
			saveMessages(request.getSession(), messages);
			request.setAttribute("message", info);
			return mapping.findForward("failAsExist");
		}
		return mapping.findForward("success");
	}

	/**
	 * 恢复版本
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward restore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ITawSystemRoleImportManager mgr = (ITawSystemRoleImportManager) getBean("tawSystemRoleImportManager");
		TawSystemRoleImport tawSystemRoleImport = mgr
				.getTawSystemRoleImport(request.getParameter("id"));
		mgr.restoreRole(tawSystemRoleImport.getVersion());
		return mapping.findForward("success");
	}
	public ActionForward toExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("export");
	}
	public void getRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ITawSystemRoleWorkflowManager workflowService =  (ITawSystemRoleWorkflowManager) getBean("ITawSystemRoleWorkflowManager");
		ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleNoCacheManager");
		JSONArray jsonRoot = new JSONArray();
		String nodeId = request.getParameter("node");
		//String nodeType = request.getParameter("nodeType");
	    List workflows = workflowService.getTawSystemWorkflows();
	    ArrayList roleList = new ArrayList();
		if(nodeId.equals("-1"))
		{
    	for (Iterator it = workflows.iterator(); it.hasNext(); ){
    		TawSystemRoleWorkflow systemWorkflow = (TawSystemRoleWorkflow)it.next();
    		String workflowId = systemWorkflow.getFlowId();
    		String workflowName = systemWorkflow.getRemark();
    		JSONObject j = new JSONObject();
    		j.put(UIConstants.JSON_ID, workflowId);
    		j.put(UIConstants.JSON_TEXT, workflowName);
    		j.put(UIConstants.JSON_NODETYPE, "workflow");
    		jsonRoot.put(j);
    	}
    	
		}else{
			roleList =  (ArrayList) roleMgr.getFlwRolesByWorkflowFlag(Integer.parseInt(nodeId));
			for (int i = 0; i < roleList.size(); i++) {
				TawSystemRole role = (TawSystemRole) roleList.get(i);
				String roleName = String.valueOf(role.getRoleName());
				String note = StaticMethod.null2String(role.getNotes());
				// 获取角色的树图JSON对象
				JSONObject jitem = roleMgr.getJSON4TreeNode(role);
				jitem.put("qtip", "角色ID: " + role.getRoleId()
						+ ("".equals(note) ? "" : "<br/>备注:" + note));
				jitem.put("qtipTitle", roleName);
				//jitem.put("parentworkflow", nodeId);
				jsonRoot.put(jitem);
		}
			}
			JSONUtil.print(response, jsonRoot.toString());
	}
	public ActionForward exortExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        String roles = request.getParameter("id");
        try{
        ITawSystemSubRoleManager subRolemgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
        ITawSystemUserRefRoleManager usermgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
        ITawSystemUserManager ugr = (ITawSystemUserManager) getBean("itawSystemUserManager");
        ITawSystemRoleWorkflowManager workflowService =  (ITawSystemRoleWorkflowManager) getBean("ITawSystemRoleWorkflowManager");
       // TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr) getBean("tawSystemRoleteaseMgr");
        List rolelist = jsonOrg2trees(roles);
        response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("UTF-8");
		String fileName = URLEncoder.encode("tawSystemRoles.xls", "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));

        OutputStream os =  response.getOutputStream();
        jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
        jxl.write.WritableSheet ws = wwb.createSheet("tawSystemRoles", 0);
        ws.addCell(new jxl.write.Label(0, 0, "所属流程"));
        ws.addCell(new jxl.write.Label(1, 0, "大角色名称"));
        ws.addCell(new jxl.write.Label(2, 0, "细分角色名称"));
        ws.addCell(new jxl.write.Label(3, 0, "组长"));
        ws.addCell(new jxl.write.Label(4, 0, "人员"));
        int count=1;
        Integer workflowId =new Integer(0);
        for(int i=0;i<rolelist.size();i++){
        	List subRoleList = new ArrayList();
        	TawSystemRole  tawSystemRole =new TawSystemRole(); 
        	tawSystemRole = (TawSystemRole)rolelist.get(i);
        	workflowId = tawSystemRole.getWorkflowFlag();
        	TawSystemRoleWorkflow tawSystemRoleWorkflow=workflowService.getTawSystemWorkflowByFlowId(workflowId+"");
        	subRoleList=subRolemgr.getTawSystemSubRoles(tawSystemRole.getRoleId());
        	for(int j=0;j<subRoleList.size();j++){
        		TawSystemSubRole subrole = (TawSystemSubRole) subRoleList.get(j);
        		Map map = usermgr.getGroupUserbyroleid(subrole.getId());
            	Iterator it =map.keySet().iterator();
            	String leaderId = getLeader(map);
            	String leaderName="";
            	if(!"".equals(leaderId)){
            	TawSystemUser leader =ugr.getUserByuserid(leaderId);
            	leaderName = leader.getUsername();
            	}
            	if(!it.hasNext()){
            		ws.addCell(new jxl.write.Label(0, count, tawSystemRoleWorkflow.getRemark()));
            		ws.addCell(new jxl.write.Label(1, count, tawSystemRole.getRoleName()));
            		ws.addCell(new jxl.write.Label(2, count, subrole.getSubRoleName()));
            		ws.addCell(new jxl.write.Label(3, count, leaderName));
            		ws.addCell(new jxl.write.Label(4, count, ""));
            		count++;
            	}
            	while(it.hasNext()){
            		String userId=it.next().toString();
            		TawSystemUser user =ugr.getUserByuserid(userId); 
            		String userName = user.getUsername();
            		ws.addCell(new jxl.write.Label(0, count, tawSystemRoleWorkflow.getRemark()));
            		ws.addCell(new jxl.write.Label(1, count, tawSystemRole.getRoleName()));
            		ws.addCell(new jxl.write.Label(2, count, subrole.getSubRoleName()));
            		ws.addCell(new jxl.write.Label(3, count, leaderName));
            		ws.addCell(new jxl.write.Label(4, count, userName));
            		count++;
            	}
        	}
        }
//      写入Exel工作表
        wwb.write();
//        关闭Excel工作薄对象
        wwb.close();
      //  response.getOutputStream().close();
        }catch(Exception e){
        	e.printStackTrace();
        }   
        return null;
		//return mapping.findForward("export");
	}
	private List jsonOrg2trees(String orgs) {
		ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleNoCacheManager");
		JSONArray jsonOrgs = JSONArray.fromString(orgs);
		List roleList = new ArrayList();
		for (Iterator it = jsonOrgs.iterator(); it.hasNext();) {
			JSONObject org = (JSONObject) it.next();
			// 发布组织id
			String id = org.getString(UIConstants.JSON_ID);
			// 节点类型
			String nodeType = org.getString(UIConstants.JSON_NODETYPE);
			if(nodeType.equals("workflow")){
			List list =  (ArrayList) roleMgr.getFlwRolesByWorkflowFlag(Integer.parseInt(id));
			for(int i=0;i<list.size();i++){
				listDistinctAdd(roleList,(TawSystemRole)list.get(i));
			}
			}else{
				TawSystemRole tawSystemRole = roleMgr.getTawSystemRole(Long.parseLong(id));
				listDistinctAdd(roleList,tawSystemRole);
			}
			
		}
		return roleList;
	}
	private List listDistinctAdd(List list,TawSystemRole tawSystemRole){
		List rolelist = list;
		boolean flag = false;
		if(tawSystemRole!=null){
			long addId = tawSystemRole.getRoleId();
		if(rolelist!=null){
			for(int i=0;i<rolelist.size();i++){
				TawSystemRole role = (TawSystemRole)rolelist.get(i);
				if(role!=null){
					long id = role.getRoleId();
					if(addId==id){
						flag=true;
						break;
					}
				}
			}
			if(!flag){
				rolelist.add(tawSystemRole);
			}
		}else{
			 rolelist = new ArrayList();
			 rolelist.add(tawSystemRole);
		}
		}
		return rolelist;
	}
	private String getLeader(Map map){
		Iterator it = map.keySet().iterator();
        String leaderId ="";
    	while(it.hasNext()){
    		String userId=it.next().toString();
    		String groupType = (String)map.get(userId);
    		if(RoleConstants.groupType_leader.equals(groupType)){
    			leaderId = userId;
    			break;
    		}
    	}
		return leaderId;
	}
	public ActionForward outPutModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (saveSessionBeanForm == null) {
			return mapping.findForward("timeout");
		}
		
		try{
			String sysTemPath = request.getRealPath("/");
			String url = sysTemPath + "/roleModel/roleModel.xls";
			File file = new File(url);
			// 读到流中
			InputStream inStream = new FileInputStream(file);// 文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("application/x-msdownload;charset=GBK");
			response.setCharacterEncoding("UTF-8");
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "GBK"));

			// 循环取出流中的数据
			byte[] b = new byte[128];
			int len;
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			return mapping.findForward("fail");
		}
		
		return null;
	}
}
