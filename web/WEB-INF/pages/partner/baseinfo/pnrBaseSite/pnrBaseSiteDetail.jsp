<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrBaseSiteForm'});
});
</script>

<html:form action="/pnrBaseSites.do?method=save" styleId="pnrBaseSiteForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center">合作伙伴站址信息管理</div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.name" />
		</td>
		<td class="content" colspan="3">
			${pnrBaseSiteForm.name}
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.addTime" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.addTime}
		</td>
		<td class="label">
			<fmt:message key="pnrBaseSite.addUser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrBaseSiteForm.addUser}" beanId="tawSystemUserDao" />
		</td>
	</tr>
<!-- 
	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.updateTime" />
		</td>
		<td class="content">
			<html:text property="updateTime" styleId="updateTime"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.updateTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.updateUser" />
		</td>
		<td class="content">
			<html:text property="updateUser" styleId="updateUser"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.updateUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.delTime" />
		</td>
		<td class="content">
			<html:text property="delTime" styleId="delTime"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.delTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.delUser" />
		</td>
		<td class="content">
			<html:text property="delUser" styleId="delUser"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.delUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.isDel" />
		</td>
		<td class="content">
			<html:text property="isDel" styleId="isDel"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.isDel}" />
		</td>
	</tr>
 -->
	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.code" />
		</td>
		<td class="content" colspan="3">
			${pnrBaseSiteForm.code}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.state" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.state}
		</td>
		<td class="label">
			<fmt:message key="pnrBaseSite.softVersion" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.softVersion}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.hardwareFlat" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.hardwareFlat}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.hardwareVersion" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.hardwareVersion}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.siteType" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.siteType}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.siteNO" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.siteNO}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.siteUse" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.siteUse}
		</td>

		<td class="label">
			Vip标识
		</td>
		<td class="content">
			${pnrBaseSiteForm.vipNO}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.connectCode" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.connectCode}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.frequencyBand" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.frequencyBand}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.equipmentFactury" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.equipmentFactury}
		</td>
		<td class="label">
			<fmt:message key="pnrBaseSite.vendor" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.vendor}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.productDate" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.productDate}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.productNO" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.productNO}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.siteName" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.siteName}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.addressNO" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.addressNO}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.city" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrBaseSiteForm.city}" beanId="tawSystemAreaDao" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.town" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrBaseSiteForm.town}" beanId="tawSystemAreaDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.address" />
		</td>
		<td class="content" colspan="3">
			${pnrBaseSiteForm.address}
		</td>
	</tr>

	<tr>
		<td class="label">
			归属BSC
		</td>
		<td class="content">
			${pnrBaseSiteForm.bsc}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.siteSort" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.siteSort}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.longitude" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.longitude}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.latitude" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.latitude}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.altitude" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.altitude}
		</td>

		<td class="label">
			管理此网元的OMC
		</td>
		<td class="content">
			${pnrBaseSiteForm.omc}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.borderDescription" />
		</td>
		<td class="content" colspan="3">
			${pnrBaseSiteForm.borderDescription}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.coverArea" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.coverArea}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.coverType" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.coverType}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.courtCover" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.courtCover}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.geographyCharacter" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.geographyCharacter}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.areaType" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.areaType}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.isMonitor" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.isMonitor}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.courtNum" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.courtNum}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.carrierFrequencyNum" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.carrierFrequencyNum}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.networkDate" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.networkDate}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.timeForArrive" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.timeForArrive}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.powerProviderTel" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.powerProviderTel}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.acOrDc" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.acOrDc}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.transfersType" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.transfersType}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.togetherSite" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.togetherSite}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.togetherSiteInf" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.togetherSiteInf}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.noncommissionNO" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.noncommissionNO}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.setDate" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.setDate}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.maintainType" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.maintainType}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.provider" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.provider}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.powerState" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.powerState}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.lockState" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.lockState}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.dutyPerson" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.dutyPerson}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.dutyPersonTel" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.dutyPersonTel}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.inOrOut" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.inOrOut}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.keyKeeper" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.keyKeeper}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.keyLeave" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.keyLeave}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.roomRight" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.roomRight}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.houseFrame" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.houseFrame}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.roomType" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.roomType}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.houseArea" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.houseArea}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.rent" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.rent}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.owner" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.owner}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.ownerIDcard" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.ownerIDcard}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.ownerTel" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.ownerTel}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.roomNO" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.roomNO}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.groundCardNO" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.groundCardNO}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.tenancyNO" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.tenancyNO}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.transEquipmentType" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.transEquipmentType}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.transfersBelong" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.transfersBelong}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.transfersPort" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.transfersPort}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.fiberType" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.fiberType}
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.isTransNode" />
		</td>
		<td class="content">
			${pnrBaseSiteForm.isTransNode}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.description" />
		</td>
		<td class="content" colspan="3">
			${pnrBaseSiteForm.description}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.remark" />
		</td>
		<td class="content" colspan="3">
			${pnrBaseSiteForm.remark}
		</td>
	</tr>


</table>

</fmt:bundle>


<html:hidden property="id" value="${pnrBaseSiteForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>