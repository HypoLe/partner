<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style type="text/css">
	.trMouseOver {
		cursor:pointer;
	}
	tr.trMouseOver td {
		background-color:#CDE0FC;
	}
</style>
	
	<input type="button" value="返回" onclick="javascript:history.back();" />
 	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="http://127.0.0.1:8080/partner/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${rootAreaId}&ordercode=${ordercode}"
		sort="list" partialList="true" size="${resultSize}" 
		decorator="com.boco.eoms.partner.inspect.util.InspectPlanMainDetailByCheckUserDecorator"
	>
		<display:column media="html" sortable="false"  headerClass="sortable" title="资源名称">
			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanItemExceptionCountDetail&inspectPlanResId=${list.id}&exceptionFlag=${exceptionFlag}&handleFlag=${handleFlag}">${list.resName}</a>
		</display:column>
		<display:column title="资源专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="tawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源类型">
			<eoms:id2nameDB id="${list.resType}" beanId="tawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源级别">
			<eoms:id2nameDB id="${list.resLevel}" beanId="tawSystemDictTypeDao" />	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="地市">
			<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>			
		<display:column  sortable="true" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		
		<display:column  title="代维小组" >
		<eoms:id2nameDB id="${list.executeObject}" beanId="partnerDeptDao"/>
		</display:column>
		
		<display:column  title="处理情况" >
			<c:choose>
				<c:when test="${list.isHandle eq 1}">
					已处理
				</c:when>
				<c:when test="${list.isHandle eq 0}">
					未处理
				</c:when>
			</c:choose>
		</display:column>
	</display:table>
	
	
	
<%@ include file="/common/footer_eoms.jsp"%>