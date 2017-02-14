<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<form action="fiber.do?method=save&type=${type}&id=${id}" method="post" id="fiberInformationForm">
<c:if test="${type=='edit'||type=='add'}">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center">光缆信息</div>
	</caption>

	<tr>
		<td class="label">
			光缆类型&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="fiberType" styleId="fiberType"
			styleClass="text max"
			alt="allowBlank:false,vtext:'',maxLength:32" value="${fiberInformation.fiberType}"/>
		</td>
		<td class="label">
			光缆级别&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="fiberLevel" styleId="fiberLevel"
			styleClass="text max"
			alt="allowBlank:false,vtext:'',maxLength:32" value="${fiberInformation.fiberLevel}"/>
		</td>
	</tr>
	
	<tr>
		<td class="label">
			光缆结构&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="fiberStructure" styleId="fiberStructure"
			styleClass="text max"
			alt="allowBlank:false,vtext:'',maxLength:32" value="${fiberInformation.fiberStructure}"/>
		</td>
		<td class="label">
			光缆数量&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="fiberNumber" styleId="fiberNumber"
			styleClass="text max" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,'');}).call(this)"
			alt="allowBlank:false,vtext:'',maxLength:32" value="${fiberInformation.fiberNumber}"/>
		</td>
	</tr>
	
	<tr>
		<td class="label">
			纤芯数&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="fiberCoreNumber" styleId="fiberCoreNumber"
			styleClass="text max" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,'');}).call(this)"
			alt="allowBlank:false,vtext:'',maxLength:32" value="${fiberInformation.fiberCoreNumber}"/>
		</td>
		<td class="label">
			纤芯类型&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="fiberCoreType" styleId="fiberCoreType"
			styleClass="text max" 
			alt="allowBlank:false,vtext:'',maxLength:32" value="${fiberInformation.fiberCoreType}"/>
		</td>
	</tr>
	
	<tr>
		<td class="label">
			安装日期&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<input class="text" type="text" name="installTime"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="allowBlank:false,vtext:'',maxLength:32" value="${fiberInformation.installTime}"
					id="installTime" readonly="readonly"/>
		</td>
		<td class="label">
			段落纤芯号&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="paragraphNumber" styleId="paragraphNumber"
			styleClass="text max" 
			alt="allowBlank:false,vtext:'',maxLength:32" value="${fiberInformation.paragraphNumber}"/>
		</td>
	</tr>
	
	<tr>
		<td class="label">
			段落
		</td>
		<td class="content" colspan="3">
			<html:textarea property="paragraph" styleId="paragraph" 
				styleClass="text medium" style="width:80%" rows="3"
				alt="allowBlank:true,vtext:''" value="${fiberInformation.paragraph}" />
		</td>
	</tr>
</table>
	<input type="submit" class="btn" value="保存"/>
</c:if>
<c:if test="${type=='detail'}">
	<table class="formTable">
	<caption>
		<div class="header center">光缆信息</div>
	</caption>

	<tr>
		<td class="label">
			光缆类型：
		</td>
		<td class="content">
			${fiberInformation.fiberType}
		</td>
		<td class="label">
			光缆级别：
		</td>
		<td class="content">
			${fiberInformation.fiberLevel}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			光缆结构：
		</td>
		<td class="content">
			${fiberInformation.fiberStructure}
		</td>
		<td class="label">
			光缆数量：
		</td>
		<td class="content">
			${fiberInformation.fiberNumber}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			纤芯数：
		</td>
		<td class="content">
			${fiberInformation.fiberCoreNumber}
		</td>
		<td class="label">
			纤芯类型：
		</td>
		<td class="content">
			${fiberInformation.fiberCoreType}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			安装日期：
		</td>
		<td class="content">
			${fiberInformation.installTime}
		</td>
		<td class="label">
			段落纤芯号：
		</td>
		<td class="content">
			${fiberInformation.paragraphNumber}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			段落：
		</td>
		<td class="content" colspan="3">
			<textarea class="textarea max" alt="width:500" readonly="readonly">
				${fiberInformation.paragraph}
			</textarea>
		</td>
	</tr>
</table>
</c:if>
</form>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'fiberInformationForm'});
});
</script>
<%@ include file="/common/footer_eoms.jsp"%>