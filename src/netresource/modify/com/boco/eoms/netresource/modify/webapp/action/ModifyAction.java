package com.boco.eoms.netresource.modify.webapp.action;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.netresource.modify.mgr.IModifyMgr;
import com.boco.eoms.netresource.modify.model.Modify;
import com.boco.eoms.netresource.modify.util.ModifyConstants;
import com.boco.eoms.netresource.modify.util.ModifyId2Name;
import com.boco.eoms.netresource.modify.util.ModifyUtil;
import com.boco.eoms.netresource.modify.webapp.form.ModifyForm;

                                                                                                                        
/**
 * <p>
 * Title:资源变更管理
 * </p>
 * <p>
 * Description:资源变更管理
 * </p>
 * <p>
 * Tue Feb 21 11:40:02 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public final class ModifyAction extends BaseAction {
 
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
     * 新增资源变更管理
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
            
                                                                                                
        // 新建画面，用户树初始化无用户
        request.setAttribute("jsonApproveUserTree", "[]");
                                                                                                                                                
        return mapping.findForward("edit");
    }
    
    /**
     * 修改资源变更管理
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
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        // 通过id检索记录
        Modify modify = modifyMgr.getModify(id);
        //ModifyForm modifyForm = (ModifyForm) convert(modify);
        request.setAttribute("modifyForm", modify) ;
        // 将检索到得model转换成form
        //updateFormBean(mapping, request, modifyForm);
        
        // 方法参数为画面上存储id的标签的property属性名和用来检索name的dao
                                                                                                
        // 编辑画面，用户树通过id检索名称显示
        String jsonApproveUserTree = getNamesByIds(modify.getApproveUser(), "tawSystemUserDao");
        request.setAttribute("jsonApproveUserTree", jsonApproveUserTree);
                                                                                                                                                        
        return mapping.findForward("edit");
    }
    
    /**
     * 保存资源变更管理
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
    	TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        ModifyForm modifyForm = (ModifyForm) form;
        
        Modify modify = (Modify) convert(modifyForm);
        
        //除了新增，当资源类型为设备时，获取设备ID然后set为资源ID
        if(!"2".equals(modify.getModifyType()) && "4".equals(modify.getResourceType()) ){
        	modify.setResourceId(request.getParameter("inspectDeviceId"));
        }
        
        //新增的时候执行
        if("".equals(modify.getId()) || null == modify.getId()){
        	modify.setApplyTime(new Date());	//申请时间
            modify.setIsDeleted(0);	//0：未删除  1：删除
            modify.setApplyUser(sessionform.getUserid());	//申请人
            modify.setApplyDept(sessionform.getCompanyId());	//申请人所属代维公司
            modify.setApproveStatus("1");	//申请状态
        }
        
        modifyMgr.saveModify(modify);
        
        return mapping.findForward("success");
    }
    
    /**
     * 删除资源变更管理
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
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        // 通过id删除记录
        modifyMgr.removeModify(id);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
    	
        return list(mapping, form, request, response);
    }
    
    /**
     * 批量删除选择的资源变更管理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeSel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        String[] ids = request.getParameterValues("ids");
        // 删除多条记录
        modifyMgr.removeModify(ids);
        return list(mapping, form, request, response);
    }
    
    /**
     * 显示详细的资源变更管理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        // 检索要显示的数据
    	TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        
        Modify modifyForm = modifyMgr.getModify(id);
        //ModifyForm modifyForm = (ModifyForm) convert(modify);
        ModifyId2Name ModifyId2Name = new ModifyId2Name();
        modifyForm.setApproveStatus(ModifyId2Name.getModifyNameById(modifyForm.getApproveStatus(),"10"));
        modifyForm.setModifyType(ModifyId2Name.getModifyNameById(modifyForm.getModifyType(), "20"));
        modifyForm.setResourceType(ModifyId2Name.getModifyNameById(modifyForm.getResourceType(), "30"));
        
        /*ITawSystemUserManager userManager5 = (ITawSystemUserManager)getBean("itawSystemUserManager");
        // 通过用户id检索用户名称
        String userId5 = modifyForm.getApproveUser();
        String userName5 = "";
        if (null != userId5) {
            String[] userIdArr5 = userId5.split(",");
            if (userIdArr5 != null) {
                for (int i = 0; i < userIdArr5.length; i++) {
                    userName5 = userName5 + "," + userManager5.getUserByuserid(userIdArr5[i]).getUsername();
                }
            }
            if (userName5.length() > 0) {
                userName5 = userName5.substring(1);
            }
        }
        modifyForm.setApproveUser(userName5);*/
                
        //updateFormBean(mapping, request, modifyForm);
        request.setAttribute("userId", sessionform.getUserid());
        request.setAttribute("modifyForm" , modifyForm);
        return mapping.findForward("detail");
    }
    
    /**
     * 待处理申请列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward approveList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	
    	ModifyForm modifyForm = (ModifyForm) form;
        
        String pageIndexName = new org.displaytag.util.ParamEncoder(
                ModifyConstants.MODIFY_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String whereStr = " and approveUser = '"+sessionform.getUserid()+"' " ;

        Map map = (Map) modifyMgr.getModifys(pageIndex, pageSize, " and ( (approveStatus = '1') or (approveStatus = '2') ) " + whereStr);
        List list = (List) map.get("result");
        Modify modify = null;
        ModifyId2Name ModifyId2Name = new ModifyId2Name();
        for(int i=0;i<list.size();i++){
        	modify = (Modify)list.get(i);
        	modify.setApproveStatus(ModifyId2Name.getModifyNameById(modify.getApproveStatus(),"10"));
            modify.setModifyType(ModifyId2Name.getModifyNameById(modify.getModifyType(), "20"));
            modify.setResourceType(ModifyId2Name.getModifyNameById(modify.getResourceType(), "30"));
        }
        request.setAttribute(ModifyConstants.MODIFY_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
    	
    	return mapping.findForward("approvelist");
    }
    
    /**
     * 分页全检索资源变更管理列表
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
        
        ModifyForm modifyForm = (ModifyForm) form;
        
        String pageIndexName = new org.displaytag.util.ParamEncoder(
                ModifyConstants.MODIFY_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        //String whereStr = "admin".equals(sessionform.getUserid()) ? "" : " AND ( ( approveUser = '"+sessionform.getUserid()+"' ) OR ( applyUser = '"+sessionform.getUserid()+"' ) ) " ;

        Map map = (Map) modifyMgr.getModifys(pageIndex, pageSize, "");
        List list = (List) map.get("result");
        
        Modify modify = null;
        ModifyId2Name ModifyId2Name = new ModifyId2Name();
        for(int i=0;i<list.size();i++){
        	modify = (Modify)list.get(i);
        	modify.setApproveStatus(ModifyId2Name.getModifyNameById(modify.getApproveStatus(),"10"));
            modify.setModifyType(ModifyId2Name.getModifyNameById(modify.getModifyType(), "20"));
            modify.setResourceType(ModifyId2Name.getModifyNameById(modify.getResourceType(), "30"));
        }
        
        request.setAttribute(ModifyConstants.MODIFY_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
                                                                                                                                                                                                        
        //检索画面，用户树通过id检索名称显示
        String jsonApproveUserTree = getNamesByIds(modifyForm.getApproveUser(), "tawSystemUserDao");
        request.setAttribute("jsonApproveUserTree", jsonApproveUserTree);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
                                                                                                                                                                                                                                                                                                                                                                        
        return mapping.findForward("list");
    }
    
    /**
     * 分页条件检索资源变更管理列表
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
        
        
        ModifyForm modifyForm = (ModifyForm) form;
        TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        //String whereStr = "admin".equals(sessionform.getUserid()) ? "" : " AND ( ( approveUser = '"+sessionform.getUserid()+"' ) OR ( applyUser = '"+sessionform.getUserid()+"' ) ) " ;
        
        //  拼接检索条件
        StringBuffer whereBuffer = new StringBuffer("");
                        
        java.lang.String id = StaticMethod.null2String(modifyForm.getId());
        if (!"".equals(id)) {
            whereBuffer.append(" and id = '"+ id +"' ");
        }
                                
        java.lang.String applyUser = StaticMethod.null2String(modifyForm.getApplyUser());
        if (!"".equals(applyUser)) {
            whereBuffer.append(" and applyUser = '"+ applyUser +"' ");
        }
                                
        java.lang.String description = StaticMethod.null2String(modifyForm.getDescription());
        if (!"".equals(description)) {
            whereBuffer.append(" and description like '%"+ description +"%' ");
        }
                                
        java.lang.String approveUser = StaticMethod.null2String(modifyForm.getApproveUser());
        if (!"".equals(approveUser)) {
            whereBuffer.append(" and approveUser = '"+ approveUser +"' ");
        }
                                
        String approveStatus = modifyForm.getApproveStatus();
        if (null != approveStatus && !"".equals(approveStatus)) {
            whereBuffer.append(" and approveStatus = '"+ approveStatus +"' ");
        }
                                
        String modifyType = modifyForm.getModifyType();
        if (null != modifyType && !"".equals(modifyType)) {
            whereBuffer.append(" and modifyType = '"+ modifyType +"' ");
        }
                                   
        String resourceType = modifyForm.getResourceType();
        if (null != resourceType && !"".equals(resourceType)) {
            whereBuffer.append(" and resourceType = '"+ resourceType +"' ");
        }
                                   
        String pageIndexName = new org.displaytag.util.ParamEncoder(
                ModifyConstants.MODIFY_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        
        //检索画面根据条件分页显示记录
        Map map = (Map) modifyMgr.getModifys(pageIndex, pageSize, whereBuffer.toString());
        List list = (List) map.get("result");
        request.setAttribute("exportCondition", URLEncoder.encode(whereBuffer.toString()));
        
        Modify modify = null;
        ModifyId2Name ModifyId2Name = new ModifyId2Name();
        for(int i=0;i<list.size();i++){
        	modify = (Modify)list.get(i);
        	modify.setApproveStatus(ModifyId2Name.getModifyNameById(modify.getApproveStatus(),"10"));
            modify.setModifyType(ModifyId2Name.getModifyNameById(modify.getModifyType(), "20"));
            modify.setResourceType(ModifyId2Name.getModifyNameById(modify.getResourceType(), "30"));
        }
        
        request.setAttribute(ModifyConstants.MODIFY_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
                                                                                                                                                                                                        
        //检索画面，用户树通过id检索名称显示
        String jsonApproveUserTree = getNamesByIds(modifyForm.getApproveUser(), "tawSystemUserDao");
        request.setAttribute("jsonApproveUserTree", jsonApproveUserTree);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
                                                                                                                                                                                                                                                                                                                                                                        
        return mapping.findForward("list");
    }
    
    /**
     * 确认受理 资源变更申请
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward accept(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        String id = request.getParameter("id");
        Modify modify = modifyMgr.getModify(id);
        
        TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        if( sessionform.getUserid().equals(modify.getApproveUser()) || "admin".equals(sessionform.getUserid()) ){//有审批权限的人员
        	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	modify.setAcceptTime(new Date());  //---------时间格式化----------
            modify.setApproveStatus("2");
            modifyMgr.saveModify(modify);
        }
        
        ModifyId2Name ModifyId2Name = new ModifyId2Name();
        modify.setApproveStatus(ModifyId2Name.getModifyNameById(modify.getApproveStatus(),"10"));
        modify.setModifyType(ModifyId2Name.getModifyNameById(modify.getModifyType(), "20"));
        modify.setResourceType(ModifyId2Name.getModifyNameById(modify.getResourceType(), "30"));
        
        request.setAttribute("userId", sessionform.getUserid());
        request.setAttribute("modifyForm" , modify);
        
        return mapping.findForward("detail");
    }
    
    /**
     * 同意 资源变更申请
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward agree(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        String id = request.getParameter("id");
        Modify modify = modifyMgr.getModify(id);
        
        TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        if( sessionform.getUserid().equals(modify.getApproveUser()) || "admin".equals(sessionform.getUserid()) ){//有审批权限的人员
        	modify.setApproveTime(new Date());//审批时间
            modify.setApproveStatus("3");
            request.setAttribute("modifyForm" , modify);
            modifyMgr.saveModify(modify);
        }
        
        request.setAttribute("userId", sessionform.getUserid());
        request.setAttribute("modifyForm" , modify);
        
        return mapping.findForward("agree");
    }
    
    /**
     * 驳回 资源变更申请
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward refuse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        IModifyMgr modifyMgr = (IModifyMgr) getBean("modifyMgr");
        String id = request.getParameter("id");
        Modify modify = modifyMgr.getModify(id);
        
        TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        if( sessionform.getUserid().equals(modify.getApproveUser()) || "admin".equals(sessionform.getUserid()) ){//有审批权限的人员
        	modify.setApproveTime(new Date());//审批时间
            modify.setApproveStatus("4");
            request.setAttribute("modifyForm" , modify);
            modifyMgr.saveModify(modify);
        }
        
        //------跳转到待审批列表------
        ModifyForm modifyForm = (ModifyForm) form;
        
        String pageIndexName = new org.displaytag.util.ParamEncoder(
                ModifyConstants.MODIFY_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        
        String whereStr = "admin".equals(sessionform.getUserid()) ? "" : " and approveUser = '"+sessionform.getUserid()+"' " ;

        Map map = (Map) modifyMgr.getModifys(pageIndex, pageSize, " and ( (approveStatus = '1') or (approveStatus = '2') ) " + whereStr);
        List list = (List) map.get("result");
        
        modify = null;
        ModifyId2Name ModifyId2Name = new ModifyId2Name();
        for(int i=0;i<list.size();i++){
        	modify = (Modify)list.get(i);
        	modify.setApproveStatus(ModifyId2Name.getModifyNameById(modify.getApproveStatus(),"10"));
            modify.setModifyType(ModifyId2Name.getModifyNameById(modify.getModifyType(), "20"));
            modify.setResourceType(ModifyId2Name.getModifyNameById(modify.getResourceType(), "30"));
        }
        request.setAttribute(ModifyConstants.MODIFY_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
    	
    	return mapping.findForward("approvelist");
    }
    
    
    /**
     * 通过当前用户与所选择资源类型，级联当前用户所属合作伙伴负责的全部资源列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param resourceType: 1:基站、2:线路、3:标点、4:设备(返回站点/标点的Json) 5:通过站点、标点关联设备(返回设备Json)
     * @return
     * @throws Exception
     */
    public ActionForward changeResType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String resourceType = StaticMethod.null2String(request.getParameter("resourceType")); //1:基站、2:线路、3:标点、4:设备
		
		ModifyUtil modifyUtil = new ModifyUtil();
		
		String json = modifyUtil.getLinkJsonByResType( userId, resourceType , request.getParameter("siteId") );
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write( json );

		return null;
    }
    
        
    /**
     * 通过id获取名称列表
     * 
     * @param id
     * @param daoType
     * @return
     */
    private String getNamesByIds(String id, String daoType) {
        
        JSONArray jsonRoot = new JSONArray();
        if (id == null || "".equalsIgnoreCase(id.trim())) {
            return "[]";
        }
        
        String[] idArray = id.split(",");

        if (idArray != null) {
            for (int i = 0; i < idArray.length; i++) {
                JSONObject jitem = new JSONObject();
                String toId = idArray[i];

                jitem.put("id", toId);
                jitem.put("name", DictMgrLocator.getId2NameService().id2Name(toId, daoType));
                jsonRoot.put(jitem);
            }
            return jsonRoot.toString();
        } else {
            return "[]";
        }
    }
        
    
}