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
					${requestScope.publishInfo.subject } 
				</td>
			</tr>
		 <tr>
			<td class="label"> 
				内容
			</td>
			<td class="content" colspan="3">
				${requestScope.publishInfo.publishContent }
			</td>
		</tr>
		 <tr>
			 <td class="label" >
				 发布范围
				</td>
				<td class="content" colspan="3">
					${requestScope.areaNames } 
				</td>
			</tr>
		  <tr>
			 <td class="label" >
				 有效期
				</td>
				<td class="content" colspan="3">
				${fn:substring(requestScope.publishInfo.valid, 0, 19)}
				</td>
			</tr>
		</table>
<%@ include file="/common/footer_eoms.jsp"%>