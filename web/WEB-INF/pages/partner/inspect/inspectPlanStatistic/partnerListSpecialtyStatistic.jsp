<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
</script>
  <logic:notPresent name="recordTotal">
<bean:define id="url" value="inspectPlanStatistic.do" />
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="table businessdesignMain"
		export="false" requestURI="inspectPlanStatistic.do"
		sort="list" size="total" partialList="true">
		
		<display:column sortable="true"
			headerClass="sortable" title="资源名称">
			${taskList.resName }
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="资源专业">
			${taskList.resSpecialty }
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="资源类型">
			${taskList.resType }
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="资源级别">
			${taskList.resLevel }
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="地市">
			${taskList.cityName }
			</display:column>
	    <display:column  sortable="true"
			headerClass="sortable" title="区县">
			${taskList.countryName }
			</display:column>

		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>

	</display:table>
</logic:notPresent>


 	<input type="button" class="btn" value="返回"  onclick="javascript:history.back();" /> 

<%@ include file="/common/footer_eoms.jsp"%>