package com.boco.eoms.netresource.asset.webapp.action;

import java.net.URLEncoder;
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
import com.boco.eoms.netresource.asset.mgr.IAssetMgr;
import com.boco.eoms.netresource.asset.model.Asset;
import com.boco.eoms.netresource.asset.util.AssetConstants;
import com.boco.eoms.netresource.asset.webapp.form.AssetForm;

                                                                                                
/**
 * <p>
 * Title:资产信息管理
 * </p>
 * <p>
 * Description:资产信息管理
 * </p>
 * <p>
 * Thu Mar 08 09:48:46 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */

public final class AssetAction extends BaseAction {
 
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
     * 新增资产信息管理
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
     * 修改资产信息管理
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
        IAssetMgr assetMgr = (IAssetMgr) getBean("assetMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        // 通过id检索记录
        Asset asset = assetMgr.getAsset(id);
        //AssetForm assetForm = (AssetForm) convert(asset);
         
        // 将检索到得model转换成form
        //updateFormBean(mapping, request, assetForm);
        request.setAttribute("assetForm", asset);
                                                                                                                                                                                                                
        return mapping.findForward("edit");
    }
    
    /**
     * 保存资产信息管理
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
    	
        IAssetMgr assetMgr = (IAssetMgr) getBean("assetMgr");
        AssetForm assetForm = (AssetForm) form;
        Asset asset = (Asset) convert(assetForm);
        
        //保存记录
        if(null != asset.getId() || !"".equals(asset.getId())) {//新增
        	TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        	asset.setCreateUser(sessionform.getUserid());
        	asset.setCreateTime(new Date());
        	asset.setIsDeleted(0);
            assetMgr.saveAsset(asset);
        }else{//编辑
            assetMgr.saveAsset(asset);
        }
        
        return mapping.findForward("success");
    }
    
    /**
     * 删除资产信息管理
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
        IAssetMgr assetMgr = (IAssetMgr) getBean("assetMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        // 通过id删除记录
        assetMgr.removeAsset(id);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
    	
        return list(mapping, form, request, response);
    }
    
    /**
     * 批量删除选择的资产信息管理
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
        IAssetMgr assetMgr = (IAssetMgr) getBean("assetMgr");
        String[] ids = request.getParameterValues("ids");
        // 删除多条记录
        assetMgr.removeAsset(ids);
        return list(mapping, form, request, response);
    }
    
    /**
     * 显示详细的资产信息管理
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
        IAssetMgr assetMgr = (IAssetMgr) getBean("assetMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        
        Asset asset = assetMgr.getAsset(id);
        //AssetForm assetForm = (AssetForm) convert(asset);
        
        request.setAttribute("assetForm", asset);
        //updateFormBean(mapping, request, assetForm);
        return mapping.findForward("detail");
    }
    
    /**
     * 分页全检索资产信息管理列表
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
        
        AssetForm assetForm = (AssetForm) form;
        
        String pageIndexName = new org.displaytag.util.ParamEncoder(
                AssetConstants.ASSET_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        IAssetMgr assetMgr = (IAssetMgr) getBean("assetMgr");
        // 检索画面初期化无条件分页显示所有记录
        Map map = (Map) assetMgr.getAssets(pageIndex, pageSize, "");
        List list = (List) map.get("result");
        request.setAttribute(AssetConstants.ASSET_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
    	
        return mapping.findForward("list");
    }
    
    /**
     * 分页条件检索资产信息管理列表
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
        
        AssetForm assetForm = (AssetForm) form;
        
        //  拼接检索条件
        StringBuffer whereBuffer = new StringBuffer();
                        
        /*java.lang.String createUser = StaticMethod.null2String(assetForm.getCreateUser());
        if (!"".equals(createUser)) {
            whereBuffer.append(" and createUser = '"+ createUser +"' ");
        }*/
                                
        java.lang.String assetName = StaticMethod.null2String(assetForm.getAssetName());
        if (null != assetName && !"".equals(assetName)) {
            whereBuffer.append(" and assetName like '%"+ assetName +"%' ");
        }
                                
        java.lang.String assetType = StaticMethod.null2String(assetForm.getAssetType());
        if (null != assetType && !"".equals(assetType)) {
            whereBuffer.append(" and assetType like '%"+ assetType +"%' ");
        }
                                
        java.lang.String assetModel = StaticMethod.null2String(assetForm.getAssetModel());
        if (null != assetModel && !"".equals(assetModel)) {
            whereBuffer.append(" and assetModel like '%"+ assetModel +"%' ");
        }
                                
        String assetUseTime = assetForm.getAssetUseTime();
        if (null != assetUseTime && !"".equals(assetUseTime)) {
            whereBuffer.append(" and assetUseTime = '"+ assetUseTime +"' ");
        }
                                
        java.lang.String assetBarCode = StaticMethod.null2String(assetForm.getAssetBarCode());
        if (null != assetBarCode && !"".equals(assetBarCode)) {
            whereBuffer.append(" and assetBarCode like '%"+ assetBarCode +"%' ");
        }
                                
        java.lang.String assetSitusTag = StaticMethod.null2String(assetForm.getAssetSitusTag());
        if (null != assetSitusTag && !"".equals(assetSitusTag)) {
            whereBuffer.append(" and assetSitusTag like '%"+ assetSitusTag +"%' ");
        }
                                
        java.lang.String assetLocation = StaticMethod.null2String(assetForm.getAssetLocation());
        if (null != assetLocation && !"".equals(assetLocation)) {
            whereBuffer.append(" and assetLocation like '%"+ assetLocation +"%' ");
        }
                                
        String pageIndexName = new org.displaytag.util.ParamEncoder(
                AssetConstants.ASSET_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        IAssetMgr assetMgr = (IAssetMgr) getBean("assetMgr");
        // 检索画面根据条件分页显示记录
        Map map = (Map) assetMgr.getAssets(pageIndex, pageSize, whereBuffer.toString());
        List list = (List) map.get("result");
                request.setAttribute("exportCondition", URLEncoder.encode(whereBuffer.toString()));
                
        request.setAttribute(AssetConstants.ASSET_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
        return mapping.findForward("list");
    }
    
        
}