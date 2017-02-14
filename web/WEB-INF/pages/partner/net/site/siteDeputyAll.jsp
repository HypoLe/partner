<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<c:set var="buttons">
		  
 <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
		
</c:set>
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<html:form action="/SiteNauticaDeputys.do?method=search" styleId="SiteNauticaDeputyForm" method="post"> 


<table class="formTable">
	<caption>
		<div class="header center">待处理经纬度任务查询</div>
	</caption>

	<tr>
		<td class="label">
			站点编号：
		</td>
		<td class="content">
			<html:text property="siteNo" styleId="siteNo"
						styleClass="text medium"
						value="${siteNo}"/>
		</td>
		<td class="label">
			站点名称：
		</td>
		<td class="content">
			<html:text property="siteName" styleId="siteName"
					    styleClass="text medium"
						value="${siteNameRel}"/>
		</td>		
	</tr>	
</table>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
			<input type="button"  class="btn" value="<fmt:message key="button.reset"/>" onclick="resetFrom()"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>

</html:form>

	<display:table name="siteNauticaDeputyList" cellspacing="0" cellpadding="0"
		id="siteNauticaDeputyList" pagesize="${pageSize}" class="table siteNauticaDeputyList"
		export="false"
		requestURI="${app}/partner/serviceArea/SiteNauticaDeputys.do?method=listAll"
		sort="list" partialList="true" size="resultSize">

	<display:column sortable="true" headerClass="sortable" title="申请人">
	
		${siteNauticaDeputyList.requestPerson}
	</display:column>
    
    <display:column sortable="true" headerClass="sortable" title="站点名称">
        <eoms:id2nameDB id="${siteNauticaDeputyList.siteName}" beanId="siteDao"/>
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" title="站点编号">
		    ${siteNauticaDeputyList.siteName}
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" title="网格名称">
	<eoms:id2nameDB id="${siteNauticaDeputyList.grid}" beanId="gridDao"/>
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" title="站点经度">
		${siteNauticaDeputyList.longitude}
	</display:column> 
	
	<display:column sortable="true" headerClass="sortable" title="站点维度">
		${siteNauticaDeputyList.latitude}
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" title="申请时间">
		${siteNauticaDeputyList.requestTime}
	</display:column>	
	
	<display:column title="审核" headerClass="imageColumn" paramId="id" paramProperty="id">
		<a href='${app}/partner/serviceArea/SiteNauticaDeputys.do?method=detail&id=${siteNauticaDeputyList.id}'>
			<img src="${app}/images/hljunicom/arrow_13.gif" /></a>
		</display:column>
	</display:table>  

</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
function resetFrom()
{
	document.getElementById("siteNo").value = "";
	document.getElementById("siteName").value = "";
}
</script>