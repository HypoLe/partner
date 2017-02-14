<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript">

	function conf(){
		var checks = document.getElementsByName('checkSample');
		var len = checks.length;
		var siteNo ;
		var site="";

		for(var i =0; i<len; i++){
			if(checks[i].checked){
				siteNo  = checks[i].value;
				site += siteNo+",";
			}
		}
		window.opener.getRequire(site);
       window.close();        

       

   }
</script>


<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
<html:form  action="/sites.do?method=searchSample" styleId="siteForm" method="post"> 
	<caption>
		<div class="header center"><fmt:message key="site.form.query"/></div>
	</caption>
	<table align="center">
		<tr>	
			<td>
				<fmt:message key="site.siteName" />
				<html:text property="siteName" styleId="siteName"
							styleClass="text medium"
							alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.siteName}"/>
			</td>
			<td>	
				<fmt:message key="site.siteNo" />
				<html:text property="siteNo" styleId="siteNo"
							styleClass="text medium"
							alt="allowBlank:false,vtext:'',vtype:'number',maxLength:32" value="${siteForm.siteNo}" />
			</td>
		</tr>
	</table>
<table align="center">
    <tr>
	    <td width="200">
	    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>	
</html:form>


	<display:table name="siteList" cellspacing="0" cellpadding="0"
		id="siteList" pagesize="${pageSize}" class="table siteList"
		export="false"
		requestURI="${app}/partner/serviceArea/sites.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column sortable="true" headerClass="sortable" title="请选择">
			<input type="checkbox" indexed="true"   id="checkSample" name="checkSample" value="${siteList.id}"/>
	</display:column>	

	<display:column property="siteName" sortable="true"
			headerClass="sortable" titleKey="site.siteName" paramId="siteNo" paramProperty="siteNo"/>

	<display:column property="siteNo" sortable="true"
			headerClass="sortable" titleKey="site.siteNo"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="site.siteType">
		<eoms:id2nameDB id="${siteList.siteType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>

	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="site.provider"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="site.coverType" >
		<eoms:id2nameDB id="${siteList.coverType}"  beanId="ItawSystemDictTypeDao" />
		</display:column>

	<display:column property="longitude" sortable="true"
			headerClass="sortable" titleKey="site.longitude"  paramId="id" paramProperty="id"/>

	<display:column property="latitude" sortable="true"
			headerClass="sortable" titleKey="site.latitude"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="site.cellulType" >
		<eoms:id2nameDB id="${siteList.cellulType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.siteLevle"  paramId="id" paramProperty="id">
		<eoms:id2nameDB id="${siteList.siteLevle}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.roomType"  paramId="id" paramProperty="id">
	
		<eoms:id2nameDB id="${siteList.roomType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.maintainType"  paramId="id" paramProperty="id">
		<eoms:dict key="dict-partner-serviceArea" dictId="maintainType" itemId="${siteList.maintainType}" beanId="id2nameXML" />
	</display:column>
	<display:column property="region" sortable="true"
			headerClass="sortable" titleKey="site.region"  paramId="id" paramProperty="id"/>

	<display:column property="city" sortable="true"
			headerClass="sortable" titleKey="site.city"  paramId="id" paramProperty="id"/>

	<display:column property="grid" sortable="true"
			headerClass="sortable" titleKey="site.grid"  paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="site" />
		<display:setProperty name="paging.banner.items_name" value="sites" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
	
	<input type="button" style="margin-right: 5px" onclick="conf()" value="确定"/>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>