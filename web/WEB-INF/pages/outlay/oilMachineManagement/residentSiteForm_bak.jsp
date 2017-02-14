<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<eoms:xbox id="partnerTree" dataUrl="${app}/xtree.do?method=dept" rootId="1"
	rootText='代维公司' valueField="partnerNodeId" handler="partnerName" textField="partnerName"
	checktype="dept" single="true"></eoms:xbox>
<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=userFromDept" rootId="2"
	rootText='负责人' valueField="dutyMan" handler="dutyManName" textField="dutyManName"
	checktype="user" single="true"></eoms:xbox>
	<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=area" rootId="-1"
	rootText='地市' valueField="city" handler="cityName" textField="cityName"
	checktype="area" single="true"></eoms:xbox>
<html:form action="/oilResidentSites.do?method=save" styleId="residentSiteForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center">驻点信息</div>
	</caption>

	<tr>
		<td class="label">
			驻点名称&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
			<html:text property="residentSiteName" styleId="residentSiteName"
						styleClass="text max"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${residentSiteForm.residentSiteName}"/>
		</td>
	</tr>
	<tr>
	<td class="label">
			代维公司&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<input type='text' id='partnerName' readonly="true" value='<eoms:id2nameDB id="${residentSiteForm.partnerId}" beanId="partnerDeptDao" />'/>
			<input type='hidden' id="partnerNodeId"  value="${partnerNodeId}" />		
		</td>
	<td class="label">
			负责人&nbsp;
		</td>
		<td class="content">		
			<input type='text' id='dutyManName' name="dutyManName"  readonly="true" value='<eoms:id2nameDB id="${residentSiteForm.dutyMan}" beanId="tawSystemUserDao" />'/>			
		<input type='hidden' id="dutyMan" name="dutyMan"  />
		</td>
	</tr>

	<tr>
		<td class="label">
			地市&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		<input type='text' id='cityName' name="cityName" readonly="true" value='<eoms:id2nameDB id="${residentSiteForm.city}" beanId="tawSystemAreaDao" />'/>
		<input type='hidden' id="city" name="city"  />
		</td>
		
	</tr>

	<tr>
		<td class="label">
			人员数
		</td>
		<td class="content">
		<html:text property="personNum" styleId="personNum"
						styleClass="text medium"
						alt="allowBlank:true,vtext:'',vtype:'number'" value="${residentSiteForm.personNum}" />
			
		</td>
		<td class="label">
			车辆数&nbsp;
		</td>
		<td class="content">
		<html:text property="carNum" styleId="carNum"
						styleClass="text medium"
						alt="allowBlank:true,vtext:'',vtype:'number'" value="${residentSiteForm.carNum}" />
		</td>		
	</tr>

	<tr>
		<td class="label">
			值班固定电话&nbsp;
		</td>
		<td class="content">
		<html:text property="telNum" styleId="telNum"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${residentSiteForm.telNum}" />
			
		</td>
		<td class="label">
			值班移动电话&nbsp;
		</td>
		<td class="content">
		<html:text property="mobileTelNum" styleId="mobileTelNum"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${residentSiteForm.telNum}" />
		</td>		
	</tr>





	<tr>
		<td class="label">
			地址
		</td>
		<td class="content" colspan="3">
		<html:textarea property="address" styleId="address" 
				styleClass="text medium" style="width:80%" rows="3"
				alt="allowBlank:true,vtext:''" value="${residentSiteForm.address}" />
		</td>
		
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>"/>
		</td>
	</tr>
</table>
<html:hidden property="id" styleId="id" value="${residentSiteForm.id}" />
<html:hidden property="partnerId" styleId="partnerId" value="${residentSiteForm.partnerId}" />
<html:hidden property="dutyMan" styleId="dutyMan" value="${residentSiteForm.dutyMan}" />
<html:hidden property="addTime" styleId="addTime" value="${residentSiteForm.addTime}" />
<html:hidden property="addUser" styleId="addUser" value="${residentSiteForm.addUser}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>