package com.boco.eoms.partner.management.webapp.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.management.mgr.IManagementMgr;
import com.boco.eoms.partner.management.model.Management;
import com.boco.eoms.partner.management.util.ManagementConstants;
import com.boco.eoms.partner.management.webapp.form.ManagementForm;

                                                                
/**
 * <p>
 * Title:管理成本
 * </p>
 * <p>
 * Description:管理成本
 * </p>
 * <p>
 * Wed Mar 28 09:29:15 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public final class ManagementAction extends BaseAction {
 
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
     * 新增管理成本
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
        
    	//获取当前用户所属合作伙伴的ID
    	PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
    	TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
    	PartnerUser partnerUser = (PartnerUser)partnerUserMgr.getPartnerUserByUserId(sessionform.getUserid());
    	if(null != partnerUser){
    		request.setAttribute("partnerIdByUserID", partnerUser.getPartnerid());
    	}else{
    		request.setAttribute("partnerIdByUserID", "");
    	}
    	
    	//获取全部合作伙伴部门信息
    	PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
    	List partnerDeptList = partnerDeptMgr.getPartnerDepts();
    	request.setAttribute("partnerDeptList", partnerDeptList);
    	
    	//传递当前用户到页面
    	request.setAttribute("currentUserId", sessionform.getUserid());
    	
        return mapping.findForward("edit");
    }
    
    /**
     * 修改管理成本
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
        IManagementMgr managementMgr = (IManagementMgr) getBean("managementMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        // 通过id检索记录
        Management management = managementMgr.getManagement(id);
        request.setAttribute("managementForm", management);
        
        //ManagementForm managementForm = (ManagementForm) convert(management);
        // 将检索到得model转换成form
        //updateFormBean(mapping, request, managementForm);
        // 方法参数为画面上存储id的标签的property属性名和用来检索name的dao
        
        //获取当前用户所属合作伙伴的ID
    	PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
    	TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
    	PartnerUser partnerUser = (PartnerUser)partnerUserMgr.getPartnerUserByUserId(sessionform.getUserid());
    	if(null != partnerUser){
    		request.setAttribute("partnerIdByUserID", partnerUser.getPartnerid());
    	}else{
    		request.setAttribute("partnerIdByUserID", "");
    	}
    	
    	//获取全部合作伙伴部门信息
    	PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
    	List partnerDeptList = partnerDeptMgr.getPartnerDepts();
    	request.setAttribute("partnerDeptList", partnerDeptList);
    	
    	//传递当前用户到页面
    	request.setAttribute("currentUserId", sessionform.getUserid());
                                                                                                                                                
        return mapping.findForward("edit");
    }
    
    /**
     * 保存管理成本
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
        IManagementMgr managementMgr = (IManagementMgr) getBean("managementMgr");
        ManagementForm managementForm = (ManagementForm) form;
        // 判断是新建还是编辑画面
        boolean isNew = (null == managementForm.getId() || "".equalsIgnoreCase(managementForm.getId().trim()));
        Management management = (Management) convert(managementForm);
                // 保存记录
        if (isNew) {
        	TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        	management.setCreateUser(sessionform.getUserid());
        	management.setCreateTime(new Date());
        	management.setIsDeleted(0);
            managementMgr.saveManagement(management);
        } else {
            managementMgr.saveManagement(management);
        }
        return mapping.findForward("success");
    }
    
    /**
     * 删除管理成本
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
        IManagementMgr managementMgr = (IManagementMgr) getBean("managementMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        // 通过id删除记录
        managementMgr.removeManagement(id);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限
    	request.setAttribute("flag", request.getParameter("flag"));
    	
        return list(mapping, form, request, response);
    }
    
    /**
     * 批量删除选择的管理成本
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
        IManagementMgr managementMgr = (IManagementMgr) getBean("managementMgr");
        String[] ids = request.getParameterValues("ids");
        // 删除多条记录
        managementMgr.removeManagement(ids);
        return list(mapping, form, request, response);
    }
    
    /**
     * 显示详细的管理成本
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
        IManagementMgr managementMgr = (IManagementMgr) getBean("managementMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        
        Management management = managementMgr.getManagement(id);
        request.setAttribute("managementForm", management);
        
        //ManagementForm managementForm = (ManagementForm) convert(management);
        //updateFormBean(mapping, request, managementForm);
        
        return mapping.findForward("detail");
    }
    
    /**
     * 分页全检索管理成本列表
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
        
        ManagementForm managementForm = (ManagementForm) form;
        
        String pageIndexName = new org.displaytag.util.ParamEncoder(
                ManagementConstants.MANAGEMENT_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        IManagementMgr managementMgr = (IManagementMgr) getBean("managementMgr");
        
        // 检索画面初期化无条件分页显示所有记录
        Map map = (Map) managementMgr.getManagements(pageIndex, pageSize, "");
        List list = (List) map.get("result");
        request.setAttribute(ManagementConstants.MANAGEMENT_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
    	
    	//获取全部合作伙伴部门信息
    	PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
    	List partnerDeptList = partnerDeptMgr.getPartnerDepts();
    	request.setAttribute("partnerDeptList", partnerDeptList);
                                                                                                                                                                                                                                                                                                                        
        return mapping.findForward("list");
    }
    
    /**
     * 分页条件检索管理成本列表
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
        
        
        ManagementForm managementForm = (ManagementForm) form;
        
        //  拼接检索条件
        StringBuffer whereBuffer = new StringBuffer("");
                        
        /*java.lang.String id = StaticMethod.null2String(managementForm.getId());
        if (!"".equals(id)) {
            whereBuffer.append(" and id = '"+ id +"' ");
        }*/
                                
        java.lang.String partnerId = StaticMethod.null2String(managementForm.getPartnerId());
        if (!"".equals(partnerId)) {
            whereBuffer.append(" and partnerId = '"+ partnerId +"' ");
        }
                                
        java.lang.String partnerNum = StaticMethod.null2String(managementForm.getPartnerNum());
        if (!"".equals(partnerNum)) {
            whereBuffer.append(" and partnerNum = '"+ partnerNum +"' ");
        }
                                
        java.lang.String expenseCost = StaticMethod.null2String(managementForm.getExpenseCost());
        if (!"".equals(expenseCost)) {
            whereBuffer.append(" and expenseCost = '"+ expenseCost +"' ");
        }
                                
        java.lang.String beginCostTime = StaticMethod.null2String(managementForm.getBeginCostTime());
        if (!"".equals(beginCostTime)) {
            whereBuffer.append(" and beginCostTime >= '"+ beginCostTime +"' ");
        }
                                
        java.lang.String endCostTime = StaticMethod.null2String(managementForm.getEndCostTime());
        if (!"".equals(endCostTime)) {
            whereBuffer.append(" and endCostTime <= '"+ endCostTime +"' ");
        }
                                
        java.lang.String remark = StaticMethod.null2String(managementForm.getRemark());
        if (!"".equals(remark)) {
            whereBuffer.append(" and remark like '%"+ remark +"%' ");
        }
                        
        String pageIndexName = new org.displaytag.util.ParamEncoder(
                ManagementConstants.MANAGEMENT_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        IManagementMgr managementMgr = (IManagementMgr) getBean("managementMgr");
        
        // 检索画面根据条件分页显示记录
        Map map = (Map) managementMgr.getManagements(pageIndex, pageSize, whereBuffer.toString());
        List list = (List) map.get("result");
                
        request.setAttribute(ManagementConstants.MANAGEMENT_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限
    	request.setAttribute("flag", request.getParameter("flag"));
    	
    	//获取全部合作伙伴部门信息
    	PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
    	List partnerDeptList = partnerDeptMgr.getPartnerDepts();
    	request.setAttribute("partnerDeptList", partnerDeptList);
                                                                                                                                                                                                                                                                                                                        
        return mapping.findForward("list");
    }
    
        
}