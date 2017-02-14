<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
var tr = true;
Ext.onReady(function() {  
});
</script>

<fmt:bundle basename="com/boco/eoms/partner/net/config/applicationResource-partner-serviceArea">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="site.form.heading"/></div>
	</caption>
	<input type="hidden" name="partnerid" id="partnerid" value="${siteForm.partnerid}"/>
	<tr>
		<td class="label">
			<fmt:message key="site.siteName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			 ${siteForm.siteName} 
		</td>
		<td class="label">
			站点编号&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			 ${siteForm.siteNo} 
		</td>		
	</tr>
	
	<tr>

		<td class="label">
			<fmt:message key="site.region" />&nbsp;<font color='red'>*</font>
		</td>
	
		<td class="content">						
			<!-- 地市 -->			
			 <eoms:id2nameDB id="${siteForm.region}" beanId="tawSystemAreaDao" /> 
			</select>
						
		</td>
		
		<td class="label">
			<fmt:message key="site.city" />&nbsp;<font color='red'>*</font>
		</td>
		
		<td class="content">
			<!-- 县区 -->			
			 <eoms:id2nameDB id="${siteForm.city}" beanId="tawSystemAreaDao" /> 
		</td>		
		
	</tr>

	<tr>
			
		<td class="label">
			<fmt:message key="site.grid" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 所属网格 -->
			 <eoms:id2nameDB id="${siteForm.gridNumber}" beanId="gridDao" /> 
		</td>
		<td class="label">
			<fmt:message key="site.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 合作伙伴 -->		
			${siteForm.partnername}
		</td>	
	</tr>
		
	<tr>
		<td class="label">
			站点类型&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			 <eoms:id2nameDB id="${siteForm.siteType}"  beanId="IItawSystemDictTypeDao" />
		</td>
		
		<td class="label">
			专业类型&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			 基站
		</td>
	</tr>	
	

	<tr>
		<td class="label">
			<fmt:message key="site.longitude" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			 ${siteForm.longitude} 
		</td>
		<td class="label">
			<fmt:message key="site.latitude" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			 ${siteForm.latitude}
		</td>
	

	</tr>

	<tr>
		<td class="label">
			站点客户经理
		</td>
		<td class="content">
	          ${siteForm.siteManager}		
		</td>
		<td class="label">
			站点客户经理电话
		</td>
		<td class="content">
	         ${siteForm.siteManagerTele}
		</td>
	</tr>
	<tr>
		<td class="label">
			站点联系人
		</td>
		<td class="content">
	        ${siteForm.siteLinkman} 
		</td>
		<td class="label">
			站点联系人电话
		</td>
		<td class="content">
	         ${siteForm.siteLinkmanTele}	
		</td>
	</tr>
 	<tr>
		<td class="label">
			站址信息
		</td>
		<td class="content" colspan="3">
			${siteForm.address}			
		</td>
		
	</tr>
</table>

</fmt:bundle>

<html:hidden property="addUser" value="${siteForm.addUser}" />
<html:hidden property="addTime" value="${siteForm.addTime}" />
<html:hidden property="updateUser" value="${siteForm.updateUser}" />
<html:hidden property="delTime" value="${siteForm.delTime}" />
<html:hidden property="delUser" value="${siteForm.delUser}" />
<html:hidden property="updateTime" value="${siteForm.updateTime}" />
<html:hidden property="id" styleId="id" value="${siteForm.id}" />
<html:hidden property="gridNumber" styleId="id" value="${siteForm.gridNumber}" />


<br/>
<div class="header center"><b>站点下设备列表</b></div>

<fmt:bundle basename="com/boco/eoms/partner/inspect/config/ApplicationResources-partner-inspect">
	<display:table name="pnrInspectDeviceList" cellspacing="0" cellpadding="0"
			id="pnrInspectDeviceList" pagesize="${pageSize}" class="table pnrInspectDeviceList"
			export="false"
			requestURI="${app}/partner/inspect/pnrInspectDevices.do?method=search"
			sort="list" partialList="true" size="resultSize">
	
			<display:column property="deviceName" sortable="true"
					headerClass="sortable" titleKey="pnrInspectDevice.deviceName" />
			<display:column property="deviceNum" sortable="true"
					headerClass="sortable" titleKey="pnrInspectDevice.deviceNum" />
			<display:column sortable="true" headerClass="sortable" titleKey="pnrInspectDevice.speciality" >
				${pnrInspectDeviceList.specialityName}
			</display:column>
			<display:column sortable="true" headerClass="sortable" titleKey="pnrInspectDevice.factory" >
				${pnrInspectDeviceList.factoryName}
			</display:column>
			<display:column sortable="true" headerClass="sortable" titleKey="pnrInspectDevice.deviceType" >
					${pnrInspectDeviceList.deviceTypeName}
			</display:column>
			<display:column sortable="true" headerClass="sortable" titleKey="pnrInspectDevice.deviceModel" >
					${pnrInspectDeviceList.deviceModelName}
			</display:column>	
			<display:column sortable="true" headerClass="sortable" titleKey="pnrInspectDevice.region" >
					<eoms:id2nameDB id="${pnrInspectDeviceList.region}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" titleKey="pnrInspectDevice.grid" >
					<eoms:id2nameDB id="${pnrInspectDeviceList.grid}" beanId="gridDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" titleKey="pnrInspectDevice.site" >
					<eoms:id2nameDB id="${pnrInspectDeviceList.site}" beanId="siteDao" />
			</display:column>
			<display:column title="查看" headerClass="imageColumn">
				    <a href="${app}/partner/inspect/pnrInspectDevices.do?method=edit&id=${pnrInspectDeviceList.id}" target="_blank">
				       <img src="${app}/images/icons/search.gif"/></a>	
			</display:column><eoms:id2nameDB id="${gridList.addUser}" beanId="tawSystemUserDao" />
	</display:table>
</fmt:bundle>
<input type="hidden" id="siteId" name="siteId" value="${siteForm.id}" />
<input type="hidden" id="gridNumber" name="gridNumber" value="${siteForm.gridNumber}" />
<input type="hidden" id="region" name="region" value="${siteForm.region}" />
<input type="hidden" id="city" name="city" value="${siteForm.city}" />
	<input type="button" class="btn"
						value="新增基站下设备"
						onclick="javascript:if(true){
						var url=eoms.appPath+'/partner/inspect/pnrInspectDevices.do?method=edit';
						window.open(url,'_blank');}" />
<%@ include file="/common/footer_eoms.jsp"%>