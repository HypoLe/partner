<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'partnerFeeInfoForm'});
});
</script>

<eoms:xbox id="tree" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="payUnit" handler="payUnitName"
		textField="payUnitName"
		checktype="dept" single="true"		
	  ></eoms:xbox>

<eoms:xbox id="tree" dataUrl="${app}/fee/partnerFeePlans.do?method=getPartnerTree" 
	  	rootId="1" 
	  	rootText='单位' 
	  	valueField="collectUnit" handler="collectUnitName"
		textField="collectUnitName"
		checktype="forums" single="true"		
	  ></eoms:xbox>
	  
<html:form action="/partnerFeeInfos.do?method=save" styleId="partnerFeeInfoForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">
<font color='red'>*</font>号为必填内容<br><br>
<table class="formTable">

	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.name" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" >
			<html:text property="name" styleId="name"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50" 
						 value="${partnerFeeInfoForm.name}" />
		</td>
		
		<td class="label">
			收款单位&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" >
			<input type="text"   id="collectUnitName" name="collectUnitName" class="text"  
				 alt="allowBlank:false,vtext:''"    value='<eoms:id2nameDB id="${partnerFeeInfoForm.collectUnit}"  
				beanId="partnerDeptDao" />'   readonly="true"/>
			<input type="hidden" id="collectUnit" name="collectUnit" value="${partnerFeeInfoForm.collectUnit}"/>
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.payUnit" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<input type="text"   id="payUnitName" name="payUnitName" class="text"  alt="allowBlank:false,vtext:''" 
				value='<eoms:id2nameDB id="${partnerFeeInfoForm.payUnit}"  
				beanId="partnerDeptDao" />'   readonly="true"/>
			<input type="hidden" id="payUnit" name="payUnit" value="${partnerFeeInfoForm.payUnit}"/>
		</td>
		
		<td class="label">
			<fmt:message key="partnerFeeInfo.payUser" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payUser" styleId="payUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" 
						 value="${partnerFeeInfoForm.payUser}" />
		</td>
		
	</tr>


	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.feeType" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="feeType" styleId="feeType"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" 
						 value="${partnerFeeInfoForm.feeType}" />
		</td>
	
		<td class="label">
			<fmt:message key="partnerFeeInfo.payWay" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payWay" styleId="payWay"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" 
						 value="${partnerFeeInfoForm.payWay}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.payNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payNo" styleId="payNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number',maxLength:32" 
						 value="${partnerFeeInfoForm.payNo}" />
		</td>
		
		<td class="label">
			费用金额(元)&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payFee" styleId="payFee"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number',maxLength:20" 
						 value="${partnerFeeInfoForm.payFee}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.payDate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payDate" styleId="payDate"
						styleClass="text" readonly="true"
			           	onclick="popUpCalendar(this, this);"
						alt="allowBlank:false,vtext:''" 
						 value="${partnerFeeInfoForm.payDate}" />
		</td>
		
		<td class="label">
			<fmt:message key="partnerFeeInfo.compactNo" />
		</td>
		<td class="content">
			<html:text property="compactNo" styleId="compactNo"
						styleClass="text medium"
						alt="allowBlank:true,vtext:'',maxLength:20" 
						 value="${partnerFeeInfoForm.compactNo}" />
		</td>
		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.remark" />
		</td>
		<td class="content" colspan="3">
			<textarea name="remark" id="remark" 
			          cols="50" class="textarea max" 
			          alt="allowBlank:true,vtext:'',maxLength:500" >${partnerFeeInfoForm.remark}</textarea>	
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.fileName" />
		</td>
		<td class="content" colspan="3">
			<eoms:attachment name="partnerFeeInfoForm" property="fileName"
						scope="request" idField="fileName" appCode="baseinfo" />
		</td>
		
	</tr>

</table>
 
</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty partnerFeeInfoForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url=eoms.appPath+'/partnerFeeInfo/partnerFeeInfos.do?method=remove&id=${partnerFeeInfoForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${partnerFeeInfoForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
