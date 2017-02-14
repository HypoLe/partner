<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'resumeForm'});
});
</script>
<!-- 
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/resumes.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>
 -->
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<html:form action="/resumes.do?method=search" styleId="resumeForm" method="post"> 
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="resume.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="resume.personnelName" />
		</td>
		<td class="content" colspan="3">
			<html:text property="personnelName" styleId="personnelName"
						styleClass="text medium"
						value="${resumeForm.personnelName}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="resume.idCardNo" />
		</td>
		<td class="content">
			<html:text property="idCardNo" styleId="idCardNo"
						styleClass="text medium"
						value="${resumeForm.idCardNo}" />
		</td>
		<td class="label">
			<fmt:message key="resume.post" />
		</td>
		<td class="content">
			<html:text property="post" styleId="post"
						styleClass="text medium"
						 value="${resumeForm.post}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.commencementDate" />
		</td>
		<td class="content">
			
			<input type="text" readonly="true" class="text" 
                name="ccds" id="ccds"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt=" vtype:'lessThen',link:'ccde',avtext:'开始时间要小于结束时间'"
                value="" />
              至：
            <input type="text" readonly="true" class="text" 
                name="ccde" id="ccde"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt=" vtype:'moreThen',link:'ccds',vtext:'开始时间要小于结束时间'"
                value="" />
		</td>
		<td class="label">
			<fmt:message key="resume.provider" />
		</td>
		<td class="content">
			<html:text property="provider" styleId="provider"
						styleClass="text medium"
						value="${resumeForm.provider}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.dimissionDate" />
		</td>
		<td class="content">
			
			<input type="text" readonly="true" class="text" 
              name="dds" id="dds"
              onclick="popUpCalendar(this,this,null,null,null,true,-1);"
              alt=" vtype:'lessThen',link:'dde',vtext:'开始时间要小于结束时间'"
              value="" />
              至：
            <input type="text" readonly="true" class="text" 
              name="dde" id="dde"
              onclick="popUpCalendar(this,this,null,null,null,true,-1);"
              alt=" vtype:'moreThen',link:'dds',vtext:'开始时间要小于结束时间'"
              value="" />
		</td>
		<td class="label">
			<fmt:message key="resume.state" />
		</td>
		<td class="content">
				<eoms:dict key="dict-partner-baseinfo" dictId="state" isQuery="false" alt=""
				defaultId="${resumeForm.state}" selectId="state" beanId="selectXML" />
		</td>
	</tr>

	<tr>

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


	<display:table name="resumeList" cellspacing="0" cellpadding="0"
		id="resumeList" pagesize="${pageSize}" class="table resumeList"
		export="false"
		requestURI="${app}/partner/baseinfo/resumes.do?method=search"
		sort="list" partialList="true" size="resultSize">


	<display:column property="personnelName" sortable="true"
			headerClass="sortable" titleKey="resume.personnelName"  paramId="id" paramProperty="id"/>

	<display:column property="idCardNo" sortable="true"
			headerClass="sortable" titleKey="resume.idCardNo"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="provider" titleKey="resume.provider" >
		<eoms:id2nameDB id="${resumeList.provider}" beanId="partnerDeptDao" />
	</display:column>

	<display:column property="post" sortable="true"
			headerClass="sortable" titleKey="resume.post"  paramId="id" paramProperty="id"/>

	<display:column property="commencementDate" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="resume.commencementDate"  paramId="id" paramProperty="id"/>

	<display:column property="dimissionDate" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="resume.dimissionDate"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="resume.state" >
			<eoms:dict key="dict-partner-baseinfo" dictId="state" itemId="${resumeList.state}" beanId="id2nameXML" />
	</display:column>

	<display:column property="remark" sortable="true"
			headerClass="sortable" titleKey="resume.remark"  paramId="id" paramProperty="id"/>
			
			
			
		<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
			
			<c:choose>
			   <c:when test="${param.flag!='search'}">
					<a href="javascript:var id = '${resumeList.id }';
				                        var url=eoms.appPath+'/partner/baseinfo/resumes.do?method=detail';
				                        url=url + '&id=' + id;
				                        location.href=url">
						<img src="${app}/images/icons/search.gif" /> </a>
			       
			   </c:when>
			   <c:otherwise> 
					<a href="javascript:var id = '${resumeList.id }';
				                        var url=eoms.appPath+'/partner/baseinfo/resumes.do?method=detail&flag=search';
				                        url=url + '&id=' + id;
				                        location.href=url">
						<img src="${app}/images/icons/search.gif" /> </a>
			    
			   </c:otherwise>
			</c:choose>
			
		</display:column>
		
		
			

	</display:table>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>