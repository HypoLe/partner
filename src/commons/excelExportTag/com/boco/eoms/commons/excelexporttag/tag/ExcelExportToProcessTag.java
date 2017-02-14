	/**
 * <p>
 * Title: excel导出标签 顶级
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 *  2012-9-20 下午02:26:10
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */

package com.boco.eoms.commons.excelexporttag.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.excelexporttag.model.ExcelRowItem;

public class ExcelExportToProcessTag extends BodyTagSupport {
	private static final long serialVersionUID = -3182244672292128687L;
	
	/**
	 * 导出时 调用的serviceBeanId
	 */
	private String serviceBeanId;
	/**
	 * 导出时 实例对象（model）
	 */
	private String modelName;
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 区分查询出的是哪个列表
	 */
	private String queryFlag;
	
	/**
	 * 流程对应的流程id
	 */
	private String processKey;
	
	/**
	 * 因为待回复列表查询和待办列表查询有时候会公用同一个dao方法
	 */
	private String flag;
	
	/*
	 * 隐藏下面的导出，换成按钮导出时使用，hide代表隐藏，不隐藏可以不用写该属性
	 */
	private String hideFlag;
	
	/**
	 * 子标签属性
	 */
	List<ExcelRowItem> rowList ;
	public ExcelExportToProcessTag() {
		super();
		rowList =  new ArrayList<ExcelRowItem>();
	}
	/**
	 * 子标签 调用该方法 设置其在父标签的值
	 * @param tag
	 */
	public void addParams(ExcelRowItem row){
		rowList.add(row);
	}
	@Override
	public int doStartTag() throws JspException {
			if(!"".equals(StaticMethod.null2String(serviceBeanId))
					&&!"".equals(StaticMethod.null2String(modelName))&&!"".equals(StaticMethod.null2String(queryFlag))&&!"".equals(StaticMethod.null2String(processKey))){
				return EVAL_BODY_INCLUDE;
			}
			else
				throw new JspException("serviceBeanId值为空，或modelName值为空，或queryFlag值为空，或processKey值为空");
	}
	
	
	@Override
	public int doEndTag() throws JspException {
		@SuppressWarnings("unused")
		Class<?> tempModel;
		try {
			tempModel = Class.forName(modelName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			tempModel = null;
			throw new JspException(e.getMessage()+" 未找到.....");
		} finally{
			tempModel = null;
		}
		
		JspWriter out=pageContext.getOut();
		StringBuilder html = new StringBuilder();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		try {
				html.append("<link rel='stylesheet' type='text/css' href='").append(request.getContextPath())
					  .append("/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css'/>");
				html.append("<script type='text/javascript' src='") .append(request.getContextPath())
					  .append("/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js'></script>");
				html.append("<script type='text/javascript' src='") .append(request.getContextPath())
				  	  .append("/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js'></script>");
				html.append("<script type='text/javascript' src='") .append(request.getContextPath());
				if("hide".equals(hideFlag)){
					html.append("/scripts/excelExportHide.js'></script>");
				}else{
					html.append("/scripts/excelExport.js'></script>");
				}
				html.append("<div class='exportlinks'>导出到:");
				html.append("<a id='exportDialog' href=\"#\"><span class='export excel'>Excel </span></a>");
				html.append("</div>");
				html.append("<div id='eomsExcelExport' style='display:none;width:auto' >");
				html.append("<form id=\"tagExcelExportForm\" action=\"").append(request.getContextPath())
					  .append("/partner/baseinfo/tagExcelExport.do?method=excelExportToProcess\"  method=\"post\">");
				html.append("<table >");
					for (ExcelRowItem row : rowList) {
						if(row.isExport()){
							html.append("<tr><td>");
							html.append("<input type=\"checkbox\"  value=\"").append(row.getName()).append("|");
							if(!"".equals(row.getDictDaoName())&&row.getDictDaoName()!=null)
								html.append(row.getValue()).append("|").append(row.getDictDaoName());
							else
								html.append(row.getValue());
							html.append("\" name=\"rowname\"  />") .append(row.getName());
							html.append("</td></tr>");
						}
					}
		//		html.append("<tr><td>导出最大记录数：<input type=\"text\" name=\"maxSize\" value=\"200\" /></td></tr>");
				html.append("</table>");
				
				//页面参数，包括查询条件
				@SuppressWarnings("rawtypes")
				Map parameterMap = request.getParameterMap();
				String key,value;
				for (Object  obj : parameterMap.keySet()) {
					key = String.valueOf(obj);
					value = ((String[])parameterMap.get(key))[0];
					
					html.append("<input type=\"hidden\"  name=\"").append(key)
						   .append("\"   value=\"").append(value).append("\"/>");
					
				}
				//导出标签  默认参数
				html.append("<input type=\"hidden\"  name=\"modelName\"   value=\"").append(modelName).append("\"/>");
				html.append("<input type=\"hidden\"  name=\"serviceBeanId\"   value=\"").append(serviceBeanId).append("\"/>");
				html.append("<input type=\"hidden\"  name=\"excelExportTitle\"   value=\"").append(title).append("\"/>");
				html.append("<input type=\"hidden\"  name=\"queryFlag\"   value=\"").append(queryFlag).append("\"/>");
				html.append("<input type=\"hidden\"  name=\"processKey\"   value=\"").append(processKey).append("\"/>");
				html.append("<input type=\"hidden\"  name=\"flag\"   value=\"").append(flag).append("\"/>");
				html.append("</form>");
				rowList.clear();

				html.append("</div>");
				out.write(html.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return EVAL_PAGE;
	}
	
	
	
	
	
	
	
	
	
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) throws JspException {
		this.modelName = modelName;
	}
	public String getServiceBeanId() {
		return serviceBeanId;
	}
	public void setServiceBeanId(String serviceBeanId) {
		this.serviceBeanId = serviceBeanId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getHideFlag() {
		return hideFlag;
	}
	public void setHideFlag(String hideFlag) {
		this.hideFlag = hideFlag;
	}
}
