<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<br/><br/>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" >
				 主题　
				</td>
				<td class="content" colspan="3">
					${requestScope.materialLib.subject }
				</td>
			</tr>
		 <tr>
			<td class="label"> 
				概述 
			</td>
			<td class="content" colspan="3">
				${requestScope.materialLib.outline }
			</td>
		</tr>
			  <tr>
			 <td class="label" >
				 发布范围　
				</td>
				<td class="content"  colspan="3">
					<fieldset id="fieldset_org" >
						<legend>发布到下列组织机构 </legend> 
						<div id="fieldset_org_names"  class="viewer-list">${requestScope.materialLib.scopeNames }</div>		
					</fieldset>
				</td>
			</tr>  
			<tr>
				<td class="label" >
				 关键词　
				</td>
				<td class="content" colspan="3">
			  	   ${requestScope.materialLib.keyWord }
				</td>
			</tr>
			<tr>
				<td class="label" >
				 巡检专业
				</td>
				<td class="content" colspan="3">
				<eoms:id2nameDB id="${requestScope.materialLib.specialty}" beanId="ItawSystemDictTypeDao" />
				</td>
			</tr>
			<tr>
				<td class="label" >
				 附件
				</td>
				<td class="content" colspan="3" >
					<eoms:download ids="${requestScope.materialLib.file }"></eoms:download>
				</td>
			</tr>
		</table>
		<br/>
		<input type="button"  value="返回" class="btn"   onclick="javascript:history.go(-1);" />
<%@ include file="/common/footer_eoms.jsp"%>