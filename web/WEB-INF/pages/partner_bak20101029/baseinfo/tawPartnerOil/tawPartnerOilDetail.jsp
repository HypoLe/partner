<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">

</script>

<html:form action="/tawPartnerOils.do?method=save" styleId="tawPartnerOilForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawPartnerOil.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.oil_number" />&nbsp;*
		</td>
		<td class="content">
			${tawPartnerOilForm.oil_number}
		</td>
		<td class="label">
			<fmt:message key="tawPartnerOil.type" />&nbsp;*
		</td>
		<td class="content">
			${tawPartnerOilForm.type}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.area_id" />&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${tawPartnerOilForm.areaidtrue}" beanId="tawSystemAreaDao" />		
		</td>

		<td class="label">
			<fmt:message key="tawPartnerOil.serviceProfessional" />&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${tawPartnerOilForm.serviceProfessional}"  beanId="ItawSystemDictTypeDao" />	
		</td>
		<!-- 所属县区
		<td class="label">
			<fmt:message key="tawPartnerOil.city" />&nbsp;*
		</td>
		<td class="content">
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所在县区'" onchange="">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
		</td>
		-->
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.dept_id" />&nbsp;*
		</td>
		<td class="content">
				 <eoms:id2nameDB id="${tawPartnerOilForm.partnerid}" beanId="partnerDeptDao" />
		</td>
	
		<td class="label">
			<fmt:message key="tawPartnerOil.power" />&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${tawPartnerOilForm.power}"  beanId="ItawSystemDictTypeDao" />		
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.kind" />&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${tawPartnerOilForm.kind}"  beanId="ItawSystemDictTypeDao" />		
		</td>
		<td class="label">
			<fmt:message key="tawPartnerOil.state" />&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${tawPartnerOilForm.state}"  beanId="ItawSystemDictTypeDao" />	
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.factory" />&nbsp;*
		</td>
		<td class="content">
			${tawPartnerOilForm.factory}
		</td>		
		<td class="label">
			<fmt:message key="tawPartnerOil.storage" />&nbsp;*
		</td>
		<td class="content" >
			${tawPartnerOilForm.storage}
		</td>	
	</tr>
	
		<logic:notEmpty name="tawPartnerOilForm" property="addMan">
			<tr>
				<td class="label">
					添加人姓名
				</td>
				<td class="content">
					<eoms:id2nameDB id="${tawPartnerOilForm.addMan}"
						beanId="tawSystemUserDao" />&nbsp;&nbsp;
						(<bean:write name="tawPartnerOilForm" property="connectType" />)
				</td>
				<td class="label">
					添加时间
				</td>
				<td class="content">
					<bean:write name="tawPartnerOilForm" property="addTime" />
				</td>
			</tr>
		</logic:notEmpty>
	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.remark" />
		</td>
		<td class="content" colspan="3">
			${tawPartnerOilForm.remark}
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="编辑" onclick="var url='${app}/partner/baseinfo/tawPartnerOils.do?method=edit&id=${tawPartnerOilForm.id}';location.href=url"/>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${tawPartnerOilForm.id}" />
</html:form>
<script type="text/javascript">
  </script>
<%@ include file="/common/footer_eoms.jsp"%>