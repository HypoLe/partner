<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'facilityNumForm'});
});
</script>

<html:form action="/facilityNums.do?method=save" styleId="facilityNumForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="facilityNum.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.factory" />
		</td>
		<td class="content">
			<eoms:comboBox name="factory" id="factory" initDicId="1121502" defaultValue="${facilityNumForm.factory}"
			    alt="allowBlank:false,vtext:'请选择厂家...'"/>		
		</td>
		<td class="label">
			<fmt:message key="facilityNum.tmsc" />
		</td>
		<td class="content">
			<html:text property="tmsc" styleId="tmsc"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.tmsc}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.mscserver" />
		</td>
		<td class="content">
			<html:text property="mscserver" styleId="mscserver"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.mscserver}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.mgw" />
		</td>
		<td class="content">
			<html:text property="mgw" styleId="mgw"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.mgw}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.hlrvlr" />
		</td>
		<td class="content">
			<html:text property="hlrvlr" styleId="hlrvlr"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.hlrvlr}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.stp" />
		</td>
		<td class="content">
			<html:text property="stp" styleId="stp"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.stp}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.bsc" />
		</td>
		<td class="content">
			<html:text property="bsc" styleId="bsc"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.bsc}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.bts" />
		</td>
		<td class="content">
			<html:text property="bts" styleId="bts"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.bts}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.rnc" />
		</td>
		<td class="content">
			<html:text property="rnc" styleId="rnc"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.rnc}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.nodeB" />
		</td>
		<td class="content">
			<html:text property="nodeB" styleId="nodeB"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.nodeB}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.cr" />
		</td>
		<td class="content">
			<html:text property="cr" styleId="cr"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.cr}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.br" />
		</td>
		<td class="content">
			<html:text property="br" styleId="br"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.br}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.ar" />
		</td>
		<td class="content">
			<html:text property="ar" styleId="ar"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.ar}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.ce" />
		</td>
		<td class="content">
			<html:text property="ce" styleId="ce"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.ce}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.bb" />
		</td>
		<td class="content">
			<html:text property="bb" styleId="bb"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.bb}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.bc" />
		</td>
		<td class="content">
			<html:text property="bc" styleId="bc"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.bc}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.bi" />
		</td>
		<td class="content">
			<html:text property="bi" styleId="bi"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.bi}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.pb" />
		</td>
		<td class="content">
			<html:text property="pb" styleId="pb"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.pb}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.sw" />
		</td>
		<td class="content">
			<html:text property="sw" styleId="sw"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.sw}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.wdm" />
		</td>
		<td class="content">
			<html:text property="wdm" styleId="wdm"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.wdm}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.otnason" />
		</td>
		<td class="content">
			<html:text property="otnason" styleId="otnason"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.otnason}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.sdhmstp" />
		</td>
		<td class="content">
			<html:text property="sdhmstp" styleId="sdhmstp"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.sdhmstp}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.ptn" />
		</td>
		<td class="content">
			<html:text property="ptn" styleId="ptn"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.ptn}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.pon" />
		</td>
		<td class="content">
			<html:text property="pon" styleId="pon"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.pon}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.ggsn" />
		</td>
		<td class="content">
			<html:text property="ggsn" styleId="ggsn"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.ggsn}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.sgsn" />
		</td>
		<td class="content">
			<html:text property="sgsn" styleId="sgsn"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.sgsn}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.note" />
		</td>
		<td class="content">
			<html:text property="note" styleId="note"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.note}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.multimediaMes" />
		</td>
		<td class="content">
			<html:text property="multimediaMes" styleId="multimediaMes"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.multimediaMes}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.coloringRing" />
		</td>
		<td class="content">
			<html:text property="coloringRing" styleId="coloringRing"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.coloringRing}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.wap" />
		</td>
		<td class="content">
			<html:text property="wap" styleId="wap"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.wap}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.scp" />
		</td>
		<td class="content">
			<html:text property="scp" styleId="scp"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.scp}" />
		</td>
		<td class="label">
			<fmt:message key="facilityNum.smp" />
		</td>
		<td class="content">
			<html:text property="smp" styleId="smp"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.smp}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="facilityNum.vc" />
		</td>
		<td class="content">
			<html:text property="vc" styleId="vc"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${facilityNumForm.vc}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)">
			<c:if test="${not empty facilityNumForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确认删除')){
						var url='${app}/partner/deviceAssess/facilityNums.do?method=remove&id=${facilityNumForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${facilityNumForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>