package com.boco.eoms.netresource.line.action;

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
import com.boco.eoms.netresource.line.model.ImportLinesForm;
import com.boco.eoms.netresource.line.model.Lines;
import com.boco.eoms.netresource.line.model.LinesForm;
import com.boco.eoms.netresource.line.service.LinesService;
import com.boco.eoms.netresource.line.util.ExcelParse;
import com.boco.eoms.netresource.line.util.FileAttributes;
import com.boco.eoms.netresource.line.util.LinesConstants;
import com.boco.eoms.netresource.line.util.LinesImportResult;
import com.boco.eoms.netresource.point.mgr.IPointsMgr;
import com.boco.eoms.netresource.point.model.Points;
import com.boco.eoms.netresource.point.util.PointsConstants;
import com.boco.eoms.netresource.point.webapp.form.PointsForm;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * 网络资源管理 之 线路管理
* @Title: 
* 
* @Description: TODO
* 
* @author WangGuangping  
* 
* @date Feb 16, 2012 10:22:49 AM
* 
* @version V1.0   
*
 */

public final class LinesAction extends BaseAction {
	
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
        return list(mapping, form, request, response);
    }
	
    /**
     * 新增线路
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
    	
//    	//根据当前省ID，列出所有地市
//		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
//		String province = pnrBaseAreaIdList.getRootAreaId();
//    	List city = PartnerCityByUser.getCityByProvince(province);    	
//    	request.setAttribute("city", city);
//    	
//    	//列出维护级别
//    	IPnrInspectCycleDao pnrInspectCycleDao = (IPnrInspectCycleDao)getBean("pnrInspectCycleDao");
//    	List PnrInspectCycle = pnrInspectCycleDao.getCycles();
//    	request.setAttribute("PnrInspectCycle", PnrInspectCycle);
    	
        return mapping.findForward("add");
    }
    
    /**
     * 修改线路
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
//    	LinesService lineService = (LinesService)getBean("linesService");
//        String id = request.getParameter("id");
//        
//        // 通过id检索记录
//        Lines line = lineService.getLineById(id);
//        request.setAttribute("linesForm",line);
//        
//    	// 根据当前省ID，列出所有地市
//		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
//		String province = pnrBaseAreaIdList.getRootAreaId();
//    	List city = PartnerCityByUser.getCityByProvince(province);    	
//    	request.setAttribute("city", city);
//    	
//    	//列出维护级别
//    	IPnrInspectCycleDao pnrInspectCycleDao = (IPnrInspectCycleDao)getBean("pnrInspectCycleDao");
//    	List PnrInspectCycle = pnrInspectCycleDao.getCycles();
//    	request.setAttribute("PnrInspectCycle", PnrInspectCycle);
                                                                                                        
        return mapping.findForward("edit");
    }
    
	/**
	 * 保存线路
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		LinesService lineService = (LinesService)getBean("linesService");
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		LinesForm linesForm = (LinesForm) form;
        Lines line = (Lines) convert (linesForm);
        line.setPartner(request.getParameter("partnerId"));//获取合作伙伴ID
        
        if(null != line.getId() && !"".equals(line.getId())){ //添加修改人与修改时间
        	line.setMender(sessionform.getUserid());
        	line.setModifyTime(new Date());
        }else{//首次创建 添加创建时间
        	line.setCreateTime(new Date());
            line.setCreator(sessionform.getUserid());
            line.setIsdeleted("0");
        }
        
        lineService.saveOrUpdateLine(line);
        
        return mapping.findForward("success");
	}
	
	/**
     * 线路明细
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
    	
    	//查询到当前线路
    	LinesService lineService = (LinesService)getBean("linesService");
    	Lines lines = null;
    	
    	//通过URL传递id过来
    	if(null != request.getParameter("id") && !"".equals(request.getParameter("id"))){
    		lines = lineService.getLineById(request.getParameter("id"));
    	}
    	
    	request.setAttribute("linesForm", lines);
    	
    	//查询当前线路下的全部标点
    	String pageIndexName = new org.displaytag.util.ParamEncoder(
                PointsConstants.POINTS_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        
    	IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
    	
    	Map map = (Map) pointsMgr.getPointss(pageIndex, pageSize, " and line = '"+lines.getId()+"' ");
        List list = (List) map.get("result");
        request.setAttribute("exportCondition", URLEncoder.encode(" and line = '"+lines.getId()+"' "));
                
        request.setAttribute(PointsConstants.POINTS_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
    	
        return mapping.findForward("detail");
        
    }
    
	/**
     * 删除线路
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
    	LinesService lineService = (LinesService)getBean("linesService");
        String id = StaticMethod.null2String(request.getParameter("id"));
        
        // 通过id删除记录
        lineService.removeLine(id);
        
        //权限设置 flag = manager,具有管理权限，否则只有查看权限 
    	request.setAttribute("flag", request.getParameter("flag"));
    	
        return list(mapping, form, request, response);
    }
	
    /**
     * 批量删除 选择的线路
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
    	LinesService lineService = (LinesService)getBean("linesService");
        String[] ids = request.getParameterValues("ids");
        // 删除多条记录
        lineService.removeLine(ids);
        return list(mapping, form, request, response);
    }
    
    /**
     * 分页列表 线路信息
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
        
    	LinesForm lineForm = (LinesForm) form;
        
        String pageIndexName = new org.displaytag.util.ParamEncoder(
        		LinesConstants.LINES_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        LinesService lineService = (LinesService)getBean("linesService");
        // 检索画面初期化无条件分页显示所有记录
        Map map = (Map) lineService.searchLine(pageIndex, pageSize, "");
        List list = (List) map.get("result");
        
        request.setAttribute(LinesConstants.LINES_LIST, list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
        
        // 检索画面，用户树通过id检索名称显示
        //String jsonDriverNameTree = getNamesByIds(carManagerForm.getDriverName(), "tawSystemUserDao");
        request.setAttribute("jsonDriverNameTree", "");
        
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
     * 分页条件检索 线路信息管理列表
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
        
        LinesForm linesForm = (LinesForm) form;
        
        //  拼接检索条件
        StringBuffer whereBuffer = new StringBuffer();
                        
        java.lang.String id = StaticMethod.null2String(linesForm.getId());
        if (!"".equals(id)) {
            whereBuffer.append(" and id like '%"+ id +"%' ");
        }
                                
        java.lang.String lineNo = StaticMethod.null2String(linesForm.getLineNo());
        if (!"".equals(lineNo)) {
            whereBuffer.append(" and lineNo like '%"+ lineNo +"%' ");
        }
                                
        java.lang.String lineName = StaticMethod.null2String(linesForm.getLineName());
        if (!"".equals(lineName)) {
            whereBuffer.append(" and lineName like '%"+ lineName +"%' ");
        }
        
        java.lang.String region = StaticMethod.null2String(linesForm.getRegion());
        if (!"".equals(region)) {
            whereBuffer.append(" and region = '"+ region +"' ");
        }
        
        java.lang.String city = StaticMethod.null2String(linesForm.getCity());
        if (!"".equals(city)) {
            whereBuffer.append(" and city = '"+ city +"' ");
        }
        
        java.lang.String grid = StaticMethod.null2String(linesForm.getGrid());
        if (!"".equals(grid)) {
            whereBuffer.append(" and grid = '"+ grid +"' ");
        }
        
        java.lang.String remark = StaticMethod.null2String(linesForm.getRemark());
        if (!"".equals(remark)) {
            whereBuffer.append(" and remark like '%"+ remark +"%' ");
        }
        
        java.lang.String maintainType = StaticMethod.null2String(linesForm.getMaintainType());
        if (!"".equals(maintainType)) {
            whereBuffer.append(" and maintainType like '%"+ maintainType +"%' ");
        }
        
        java.lang.String level = StaticMethod.null2String(linesForm.getLevel());
        if (!"".equals(level)) {
            whereBuffer.append(" and level like '%"+ level +"%' ");
        }
          
        String pageIndexName = new org.displaytag.util.ParamEncoder(LinesConstants.LINES_LIST)
                .encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
        final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
                .getPageSize();
        final Integer pageIndex = new Integer(GenericValidator
                .isBlankOrNull(request.getParameter(pageIndexName)) ? 0
                : (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
        
        LinesService lineService = (LinesService)getBean("linesService");
        
        //检索画面根据条件分页显示记录
        Map map = (Map) lineService.searchLine(pageIndex, pageSize, whereBuffer.toString());
        List list = (List) map.get("result");
        request.setAttribute("exportCondition", URLEncoder.encode(whereBuffer.toString()));
                
        request.setAttribute(LinesConstants.LINES_LIST, list);
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
     * 线路信息导入
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importLines(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("importLines");
    }
    
    
    /**
     * 线路信息导入保存
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward importSaveLines(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	LinesService lineService = (LinesService)getBean("linesService");
    	FileAttributes fileAttributes = (FileAttributes)getBean("fileAttributes");
        //解析Excel
    	List lists = new ExcelParse().parseLines(fileAttributes.getNetDiskRootPath()+"/"+request.getParameter("accessoriesId").replace("'",""), request);
    	//保存入库
        Lines lines = null; 
        for(int i=0;i<lists.size();i++){
        	lines = (Lines)lists.get(i);
        	System.out.println(lines.getOpenTime());
        	//lineService.saveOrUpdateLine(lines);
        }
        
        return mapping.findForward("success");
    }
    
    /**
	 * 导入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		ImportLinesForm importLinesForm = (ImportLinesForm) form;
		FormFile formFile = importLinesForm.getImportFile();
		
		PrintWriter writer = null;
		try{
			LinesService lineService = (LinesService)getBean("linesService");
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			Map params = new HashMap();
			params.put("userId", sessionform.getUserid());
			writer = response.getWriter();
			LinesImportResult result = lineService.importFromFile(formFile,params);
			if(result.getResultCode().equals("200")) {
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true")
								.put("msg", "ok")
								.put("infor", "'导入成功,共计导入"+result.getImportCount()+"条记录'").build()));
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
		String rootPath = request.getRealPath("/netresource/lines");
		String fileName = "NetResouce_Lines_Import_Model.xls";
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
	public ActionForward getLinesJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		
		LinesService linesService = (LinesService)getBean("linesService");
		List list = linesService.searchLine(" and grid = '"+request.getParameter("grid")+"' ");
		
		//根据标点ID获取所在线路ID
		String lineNo = "";
		if(null != request.getParameter("point") && !"".equals(request.getParameter("point"))){
			IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
			List pointList = (List)pointsMgr.getPointsByProperty(" and id = '"+request.getParameter("point").trim()+"' ");
			if(null != pointList && pointList.size()>0){
				Points point = (Points)pointList.get(0);
				lineNo = point.getLine();
			}
		}
		
		//构建Json
		StringBuffer l_list = new StringBuffer();
		l_list.append("," + "");
		l_list.append("," + "--请选择线路--");
		
		for (int i = 0; list != null && i < list.size(); i++) {
			Lines lines = (Lines)list.get(i);
			l_list.append("," + lines.getId());
			l_list.append("," + lines.getLineName());
		}

		jitem.put("lines", l_list.toString().substring(1));
		jitem.put("lineNo", lineNo);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write( json.toString() );

		return null;
	}
	
	/**
	 * 判断 线路编号/名称 是否存在的ajax
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationLineInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String flag = request.getParameter("flag");
		LinesService lineService = (LinesService)getBean("linesService");
		Lines line = null;
		
		if(null != flag && !"".equals(flag) && "lineNo".equals(flag)){//线路编号查询
			line = lineService.getLinesByProperty(" and lineNo = '"+  new String((request.getParameter("lineNo")).getBytes("ISO8859-1"),"UTF-8")  +"' ");
		}else if(null != flag && !"".equals(flag) && "lineName".equals(flag)){//线路名称查询
			line = lineService.getLinesByProperty(" and lineName = '"+ new String((request.getParameter("lineName")).getBytes("ISO8859-1"),"UTF-8") +"' ");
		}
		
		//设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(null != line && null != line.getId()){//已存在
			jitem.put("message", false);
		}else{
			jitem.put("message", true);
		}
		json.put(jitem);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
    /**
     * 在指定线路下 新增标点 跳转方法
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addPointByLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	//查询到当前线路
    	LinesService lineService = (LinesService)getBean("linesService");
    	String id = request.getParameter("id");
    	if(null != id && !"".equals(id)){
    		Lines lines = lineService.getLineById(id);
        	request.setAttribute("linesForm", lines);
    	}
    	
        return mapping.findForward("addPointByLine");
    }
    
    /**
     * 在指定线路下 新增标点 保存方法
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward savePointByLine(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	//保存标点
    	IPointsMgr pointsMgr = (IPointsMgr) getBean("pointsMgr");
        TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        PointsForm pointsForm = (PointsForm) form;
        //form转换为Model对象
        Points points = (Points) convert(pointsForm);
        //判断是新增还是编辑
        if(null == points.getId() || "".equals(points.getId())){//新增记录
        	points.setIsdeleted("0");
        	points.setCreateTime(new Date());
            points.setCreator(sessionform.getUserid());
            //首次新增的时候,通过线路ID获取合作伙伴ID
            LinesService lineService = (LinesService)getBean("linesService");
            Lines line = lineService.getLineById(points.getLine());
            if(null != line && !"".equals(line)){
            	points.setPartner(line.getPartner());
            }
        }
        pointsMgr.savePoints(points);
    	
        //跳转当前线路列表下
        //request.setAttribute("id", points.getLine());
        //request.setAttribute("flag", "savePointByLine");
        
        //保存完成后,继续跳转到当前线路的明细及当前线路下的标点列表
        //detail(mapping, form, request, response);
        
        return mapping.findForward("success");
    }

}


