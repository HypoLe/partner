package com.boco.eoms.netresource.point.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.netresource.line.model.Lines;
import com.boco.eoms.netresource.line.service.LinesService;
import com.boco.eoms.netresource.line.util.LinesImportResult;
import com.boco.eoms.netresource.point.mgr.IPointsMgr;
import com.boco.eoms.netresource.point.model.ImportPointsForm;
import com.boco.eoms.netresource.point.model.Points;
import com.boco.eoms.netresource.point.util.PointsConstants;
import com.boco.eoms.netresource.point.webapp.form.PointsForm;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

                                                                                                                                
/**
 * <p>
 * Title:标点信息管理
 * </p>
 * <p>
 * Description:标点信息管理
 * </p>
 * <p>
 * Thu Feb 16 18:22:16 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public final class PointsAction extends BaseAction {
 
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
     * 新增标点信息管理
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
                                                                        
    	//根据当前省ID，列出所有地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
                                                                                                                                                                                                
        return mapping.findForward("edit");
    }
    
    /**
     * 修改标点信息管理
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
        IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        // 通过id检索记录
        Points points = pointsMgr.getPoints(id);
        
        request.setAttribute("pointsForm", points);
        //PointsForm pointsForm = (PointsForm) convert(points);
        // 将检索到得model转换成form
        //updateFormBean(mapping, request, pointsForm);
        
    	//根据当前省ID，列出所有地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
                                                                                                                                                                                                        
        return mapping.findForward("edit");
    }
    
    /**
     * 保存标点信息管理
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
        IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
        TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        PointsForm pointsForm = (PointsForm) form;
        
        Points points = (Points) convert(pointsForm);
        
        if(null == points.getId() || "".equals(points.getId())){//新增记录
        	points.setIsdeleted("0");
        	points.setCreateTime(new Date());
            points.setCreator(sessionform.getUserid());
            
            //首次创建的时候,通过线路ID获取合作伙伴ID
            LinesService lineService = (LinesService)getBean("linesService");
            Lines line = lineService.getLineById(points.getLine());
            if(null != line && !"".equals(line)){
            	points.setPartner(line.getPartner());
            }
            
        }
        
        pointsMgr.savePoints(points);
        
        return mapping.findForward("success");
    }
    
    /**
     * 删除标点信息管理
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
        IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        // 通过id删除记录
        pointsMgr.removePoints(id);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
    	
        return list(mapping, form, request, response);
    }
    
    /**
     * 批量删除选择的标点信息管理
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
        IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
        String[] ids = request.getParameterValues("ids");
        // 删除多条记录
        pointsMgr.removePoints(ids);
        return list(mapping, form, request, response);
    }
    
    /**
     * 显示详细的标点信息管理
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
        IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
        String id = StaticMethod.null2String(request.getParameter("id"));
        
        Points points = pointsMgr.getPoints(id);
        //PointsForm pointsForm = (PointsForm) convert(points);
        request.setAttribute("pointsForm", points);
        //updateFormBean(mapping, request, pointsForm);
        return mapping.findForward("detail");
    }
    
    /**
     * 分页全检索标点信息管理列表
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
        
        PointsForm pointsForm = (PointsForm) form;
        
        String pageIndexName = new org.displaytag.util.ParamEncoder(
                PointsConstants.POINTS_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        
        IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
        
        //检索画面初期化无条件分页显示所有记录
        Map map = (Map) pointsMgr.getPointss(pageIndex, pageSize, "");
        List list = (List) map.get("result");
        request.setAttribute(PointsConstants.POINTS_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
        
        //根据当前省ID，列出所有地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	
    	//权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
        
        return mapping.findForward("list");
    }
    
    /**
     * 分页条件检索标点信息管理列表
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
        
        
        PointsForm pointsForm = (PointsForm) form;
        
        //拼接检索条件
        StringBuffer whereBuffer = new StringBuffer();
                        
        java.lang.String id = StaticMethod.null2String(pointsForm.getId());
        if (!"".equals(id)) {
            whereBuffer.append(" and id = '"+ id +"' ");
        }
                                
        java.lang.String pointName = StaticMethod.null2String(pointsForm.getPointName());
        if (!"".equals(pointName)) {
            whereBuffer.append(" and pointName like '%"+ pointName +"%' ");
        }
                                
        java.lang.String pointNo = StaticMethod.null2String(pointsForm.getPointNo());
        if (!"".equals(pointNo)) {
            whereBuffer.append(" and pointNo like '%"+ pointNo +"%' ");
        }
                                
        java.lang.String region = StaticMethod.null2String(pointsForm.getRegion());
        if (!"".equals(region)) {
            whereBuffer.append(" and region = '"+ region +"' ");
        }
        
        java.lang.String city = StaticMethod.null2String(pointsForm.getCity());
        if (!"".equals(city)) {
            whereBuffer.append(" and city = '"+ city +"' ");
        }
                                
        java.lang.String grid = StaticMethod.null2String(pointsForm.getGrid());
        if (!"".equals(grid)) {
            whereBuffer.append(" and grid = '"+ grid +"' ");
        }
                                
        java.lang.String line = StaticMethod.null2String(pointsForm.getLine());
        if (!"".equals(line)) {
            whereBuffer.append(" and line = '"+ line +"' ");
        }
                                
        java.lang.String specialtyType = StaticMethod.null2String(pointsForm.getSpecialtyType());
        if (!"".equals(specialtyType)) {
            whereBuffer.append(" and specialtyType = '"+ specialtyType +"' ");
        }
                                
        java.lang.String groupUser = StaticMethod.null2String(pointsForm.getGroupUser());
        if (!"".equals(groupUser)) {
            whereBuffer.append(" and groupUser like '%"+ groupUser +"%' ");
        }
                                
        java.lang.String groupUserNo = StaticMethod.null2String(pointsForm.getGroupUserNo());
        if (!"".equals(groupUserNo)) {
            whereBuffer.append(" and groupUserNo like '%"+ groupUserNo +"%' ");
        }
                                   
        String pageIndexName = new org.displaytag.util.ParamEncoder(
                PointsConstants.POINTS_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        
        IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
        
        //检索画面根据条件分页显示记录
        Map map = (Map) pointsMgr.getPointss(pageIndex, pageSize, whereBuffer.toString());
        List list = (List) map.get("result");
                request.setAttribute("exportCondition", URLEncoder.encode(whereBuffer.toString()));
                
        request.setAttribute(PointsConstants.POINTS_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
        
        //根据当前省ID，列出所有地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List cityList = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", cityList);
        
    	//权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
    	
        return mapping.findForward("list");
    }
    
    /**
     * 标点信息导入
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importPoints(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("importPoints");
    }
    
    /**
	 * Excel导入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		ImportPointsForm importLinesForm = (ImportPointsForm) form;
		FormFile formFile = importLinesForm.getImportFile();
		
		PrintWriter writer = null;
		try{
			IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			Map params = new HashMap();
			params.put("userId", sessionform.getUserid());
			
			writer = response.getWriter();
			LinesImportResult result = pointsMgr.importFromFile(formFile,params);
			if(result.getResultCode().equals("200")) {
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true")
								.put("msg", "ok")
								.put("infor", "【导入成功】：共计导入 "+result.getImportCount()+" 条记录。").build()));
			}
		}catch(Exception e){
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", e.getMessage()).build()));
		}finally{
			if(writer != null){
				writer.close();
			}
		}
		return null;
	}
	
	/**
	 * 下载模板文件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rootPath = request.getRealPath("/netresource/points");
		String fileName = "NetResouce_Points_Import_Model.xls";
		File file = new File(rootPath + File.separator + fileName);

		// 读到流中
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("GB2312");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("gbk"), "iso8859-1"));

		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();
	}
	
	/**
	 * 根据条件查询线路,返回线路名称与ID的JSON串
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ActionForward getPointsJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		
		IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
		List list = pointsMgr.getPointsByProperty(" and line = '"+request.getParameter("line")+"' ");
		
		StringBuffer l_list = new StringBuffer();
		l_list.append("," + "");
		l_list.append("," + "--请选择标点--");
		
		for (int i = 0; list != null && i < list.size(); i++) {
			Points points = (Points)list.get(i);
			l_list.append("," + points.getId());
			l_list.append("," + points.getPointName());
		}

		jitem.put("points", l_list.toString().substring(1));
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write( json.toString() );

		return null;
	}
	
	/**
	 * 判断 标点编号/名称 是否存在的ajax
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationPointInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String flag = request.getParameter("flag");
		IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
		List list = null;
		
		if(null != flag && !"".equals(flag) && "pointNo".equals(flag)){//标点编号查询
			list = pointsMgr.getPointsByProperty(" and pointNo = '"+  new String((request.getParameter("pointNo")).getBytes("ISO8859-1"),"UTF-8")  +"' ");
		}else if(null != flag && !"".equals(flag) && "pointName".equals(flag)){//标点名称查询
			list = pointsMgr.getPointsByProperty(" and pointName = '"+ new String((request.getParameter("pointName")).getBytes("ISO8859-1"),"UTF-8") +"' ");
		}
		
		//设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(null != list && list.size()>0){//已存在
			jitem.put("message", false);
		}else{
			jitem.put("message", true);
		}
		json.put(jitem);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
        
}