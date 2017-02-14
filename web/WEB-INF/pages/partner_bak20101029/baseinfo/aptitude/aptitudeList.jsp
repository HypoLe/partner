<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'aptitudeForm'});
});
</script>
<!--  
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/aptitudes.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>
-->
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<html:form action="/aptitudes.do?method=search" styleId="aptitudeForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="aptitude.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="aptitude.providerName" />
		</td>
		<td class="content">
			<html:text property="providerName" styleId="providerName"
						styleClass="text medium"
						alt="maxLength:32" value="${aptitudeForm.providerName}" />
		</td>
		<td class="label">
			<fmt:message key="aptitude.aptitudeName" />
		</td>
		<td class="content">
		
			<html:text property="aptitudeName" styleId="aptitudeName"
						styleClass="text medium"
						alt="maxLength:32" value="${aptitudeForm.aptitudeName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="aptitude.aptitudeStartTime" />
		</td>
		<td class="content">
			<input type="text" readonly="true" class="text" 
                name="asts" id="asts"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt=" vtype:'lessThen',link:'aste',vtext:'开始时间要小于结束时间'"
                value="" />
               至：
            <input type="text" readonly="true" class="text" 
                name="aste" id="aste"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt=" vtype:'moreThen',link:'asts',vtext:'开始时间要小于结束时间'"
                value="" />
		</td>
		<td class="label">
			<fmt:message key="aptitude.aptitudeLevle" />
		</td>
		<td class="content">
				
			<eoms:comboBox name="aptitudeLevle" id="aptitudeLevle" initDicId="1121308" defaultValue="${aptitudeForm.aptitudeLevle}"
			    />

				
		</td> 
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="aptitude.aptitudeEndTime" />
		</td>
		<td class="content">
			<input type="text" readonly="true" class="text" 
                name="aets" id="aets"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt=" vtype:'lessThen',link:'aete',vtext:'开始时间要小于结束时间'"
                value="" />
           	 至：
            <input type="text" readonly="true" class="text" 
                name="aete" id="aete"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt=" vtype:'moreThen',link:'aets',vtext:'开始时间要小于结束时间'"
                value="" />
		</td>
		<td class="label">
			<fmt:message key="aptitude.aptitudeDesc" />
		</td>
		<td class="content">
			<html:text property="aptitudeDesc" styleId="aptitudeDesc"
						styleClass="text medium" 
						alt="maxLength:32" value="${stationForm.remark}"/>
		</td>
	</tr>
</table>


<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>


	<display:table name="aptitudeList" cellspacing="0" cellpadding="0"
		id="aptitudeList" pagesize="${pageSize}" class="table aptitudeList"
		export="false"
		requestURI="${app}/partner/baseinfo/aptitudes.do?method=search"
		sort="list" partialList="true" size="resultSize">

<!-- 修改连接： href="${app}/partner/baseinfo/aptitudes.do?method=editNor" 
 -->
 
	 <display:column property="providerName" sortable="true"
			headerClass="sortable" titleKey="aptitude.providerName"  paramId="id" paramProperty="id"/>
 	
	<display:column property="aptitudeName" sortable="true"
			headerClass="sortable" titleKey="aptitude.aptitudeName"  paramId="id" paramProperty="id"/>

	<display:column  sortable="true" headerClass="sortable" titleKey="aptitude.aptitudeLevle" >
			
			<eoms:id2nameDB id="${aptitudeList.aptitudeLevle}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
 
	<display:column property="aptitudeStartTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}" 
			headerClass="sortable" titleKey="aptitude.aptitudeStartTime"  paramId="id" paramProperty="id"/>

	<display:column property="aptitudeEndTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}" 
			headerClass="sortable" titleKey="aptitude.aptitudeEndTime"  paramId="id" paramProperty="id"/>

	<display:column property="aptitudeAccessory" sortable="true"
			headerClass="sortable" titleKey="aptitude.aptitudeAccessory"  paramId="id" paramProperty="id"/>
			


		<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
			
			<c:choose>
			   <c:when test="${param.flag!='search'}">
					<a href="javascript:var id = '${aptitudeList.id }';
				                        var url=eoms.appPath+'/partner/baseinfo/aptitudes.do?method=detail';
				                        url=url + '&id=' + id;
				                        location.href=url">
						<img src="${app}/images/icons/search.gif" /> </a>
			       
			   </c:when>
			   <c:otherwise> 
					<a href="javascript:var id = '${aptitudeList.id }';
				                        var url=eoms.appPath+'/partner/baseinfo/aptitudes.do?method=detail&flag=search';
				                        url=url + '&id=' + id;
				                        location.href=url">
						<img src="${app}/images/icons/search.gif" /> </a>
			    
			   </c:otherwise>
			</c:choose>
			
		</display:column>
			

		<display:setProperty name="paging.banner.item_name" value="aptitude" />
		<display:setProperty name="paging.banner.items_name" value="aptitudes" />
	</display:table>
<!--  
	<c:out value="${buttons}" escapeXml="false" />
-->
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>