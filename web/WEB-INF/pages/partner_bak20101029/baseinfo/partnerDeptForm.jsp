<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'partnerDeptForm'});
});
function checkNum(theInput,str){ 
  a=parseInt(theInput); 
    if (isNaN(a)) 
  { 
      alert(str+"请输入数字"); 
      return false;
  } 
  else 
  return true;
  }
function isEmail(strEmail) {
	if (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
	return true;
	else{
		document.forms[0].email.value = '';
		alert("请检查邮箱格式！");
	}
}
window.onload = function(){
    var proId = '${partnerDeptForm.id}';
	var operType = '${operType}';
	if(proId != '' && operType == 'save'){
	    parent.setproId(proId);
    }
}
	window.frameElement.height = 800;
	var isEdit = '${isEdit}';
	if(isEdit!='add'){
		document.body.style.overflow = "hidden";
	}     
    var subRetutn = false;
function sub() {    
	var bigTypes = document.getElementsByName("bigType");
	for(var i = 0 ;bigTypes.length>i ; i++){
		var bigTypeId = bigTypes[i].checked;
		if(bigTypes[i].checked){
			subRetutn = true;
		}
	}
	var id = '${partnerDeptForm.id}';
	var treeNodeId = '${partnerDeptForm.treeNodeId}';
	treeNodeId = treeNodeId.substring(0,5);
	if(subRetutn){
		if(v.check()){
	       $("partnerDeptForm").submit();
		} 
	}else {
		alert("单位类型为必选项");
		return false;	
	}
}

</script>
<html:form action="/partnerDepts.do?method=save" styleId="partnerDeptForm" method="post"> 

<input type="hidden" name="parentNodeId" value="${parentNodeId }">
<html:hidden property="treeNodeId"/>
<html:hidden property="treeId"/>

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="partnerDept.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="partnerDept.name" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="name" styleId="name"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerDeptForm.name}" /> 
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.type" />&nbsp;*
		</td>
		<td class="content">				
		    <eoms:comboBox name="type" id="type" initDicId="1120101"
					defaultValue='${partnerDeptForm.type}' alt="allowBlank:false,vtext:''" />
		</td>
		
	</tr>


	<tr>
		<td class="label">
			<fmt:message key="partnerDept.aptitude" />&nbsp;*
		</td>
		<td class="content">
		    <eoms:comboBox name="aptitude" id="aptitude" initDicId="1120102"
					defaultValue='${partnerDeptForm.aptitude}' alt="allowBlank:false,vtext:''" />						
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.deadline" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="deadline" styleId="deadline" readonly="true"
						styleClass="text medium" onclick="popUpCalendar(this, this,null,null,null,false,-1);"
						alt="allowBlank:false,vtext:''" value="${partnerDeptForm.deadline}" />
		</td>
	</tr>



	<tr>
		<td class="label">
			<fmt:message key="partnerDept.manager" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="manager" styleId="manager"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerDeptForm.manager}" />
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.areaName" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="areaName" styleId="areaName" readonly="true"
						styleClass="text medium" 
						alt="allowBlank:false,vtext:''" value="${partnerDeptForm.areaName}" />
		</td>
	</tr>



	<tr>
		<td class="label">
			<fmt:message key="partnerDept.fund" />
		</td>
		<td class="content">
			<html:text property="fund" styleId="fund" 
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:true,vtext:''" value="${partnerDeptForm.fund}" />
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.phone" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="phone" styleId="phone" 
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerDeptForm.phone}" />
		</td>
	</tr>



	<tr>
		<td class="label">
			<fmt:message key="partnerDept.address" />
		</td>
		<td class="content">
			<html:text property="address" styleId="address"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:true,vtext:''" value="${partnerDeptForm.address}" />
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.fax" />
		</td>
		<td class="content">
			<html:text property="fax" styleId="fax" 
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:true,vtext:''" value="${partnerDeptForm.fax}" />
		</td>
	</tr>

	<tr>
	    <td class="label">
			<fmt:message key="partnerDept.contactor" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="contactor" styleId="contactor"
						styleClass="text medium" 
						alt="allowBlank:false,vtext:'',maxLength:32" value="${partnerDeptForm.contactor}" />
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.zip" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="zip" styleId="zip" 
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${partnerDeptForm.zip}" />
		</td>
		

	</tr>

	<tr>
	    <td class="label">
			<fmt:message key="partnerDept.bank" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="bank" styleId="bank"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerDeptForm.bank}" />
		</td>
<!-- 开户名称 -->
	    <td class="label">
			开户名称
		</td>
		<td class="content">
			<html:text property="accName" styleId="accName"
						styleClass="text medium" 
						alt="allowBlank:true,vtext:'',maxLength:128" value="${partnerDeptForm.accName}" />
		</td>		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerDept.account" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="account" styleId="account" 
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerDeptForm.account}" />
		</td>
	    <td class="label">
			<fmt:message key="partnerDept.email" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="email" styleId="email"
						styleClass="text medium" onblur="isEmail(this.value);testCharSize(this,255);"
						alt="allowBlank:true,vtext:'',maxLength:64" value="${partnerDeptForm.email}" />
		</td>	

	</tr>
	
	<tr>
	
	    <td class="label">
			<fmt:message key="partnerDept.companyRegister" />
		</td>
		<td class="content">
			<html:text property="companyRegister" styleId="companyRegister"
						styleClass="text medium" 
						alt="allowBlank:true,vtext:'',maxLength:64" value="${partnerDeptForm.companyRegister}" />
		</td>
	    <td class="label">
			<fmt:message key="partnerDept.honestAgreement" />
		</td>
		<td class="content">
			<html:text property="honestAgreement" styleId="honestAgreement"
						styleClass="text medium" 
						alt="allowBlank:true,vtext:'',maxLength:64" value="${partnerDeptForm.honestAgreement}" />
		</td>	

	</tr>

	<tr>
<!-- 营业执照号 -->
	    <td class="label">
			营业执照号
		</td>
		<td class="content">
			<html:text property="licenceNum" styleId="licenceNum"
						styleClass="text medium" 
						alt="allowBlank:true,vtext:'',maxLength:128" value="${partnerDeptForm.licenceNum}" />
		</td>
	
<!-- 企业法人 -->
	    <td class="label">
			企业法人
		</td>
		<td class="content" colspan="3">
			<html:text property="managercop" styleId="managercop"
						styleClass="text medium" 
						alt="allowBlank:true,vtext:'',maxLength:128" value="${partnerDeptForm.managercop}" />
		</td>
	</tr>	
	<tr>
<!-- 单位人员总数 -->
	    <td class="label">
			单位人员总数
		</td>
		<td class="content">
			<html:text property="personCou" styleId="personCou"
						styleClass="text medium" 
						alt="allowBlank:true,vtext:'',maxLength:32" value="${partnerDeptForm.personCou}" />
		</td>
<!-- 是否入围-->		
	    <td class="label">
			是否入围
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-baseinfo" dictId="isbackbone" isQuery="false" alt="allowBlank:false"
			defaultId="${partnerDeptForm.selected}" selectId="selected" beanId="selectXML" />			
		</td>	

	</tr>
	<tr>
		<!-- 公司主页-->		
	    <td class="label">
			公司主页
		</td>
		<td class="content">
			<html:text property="homepage" styleId="homepage"
						styleClass="text medium" 
						alt="allowBlank:true,vtext:'',maxLength:128" value="${partnerDeptForm.homepage}" />
		</td>	
<!-- 税务登记号-->		
	    <td class="label">
			税务登记号
		</td>
		<td class="content">
			<html:text property="registNo" styleId="registNo"
						styleClass="text medium" 
						alt="allowBlank:true,vtext:'',maxLength:128" value="${partnerDeptForm.registNo}" />
		</td>	

	</tr>	
	<tr>
<!--大单位类型  -->		
	 <td class="label">
			单位类型 &nbsp;*
		</td>
		<td class="content" colspan="3">
		<c:forEach items="${tawSystemDictType}" var="dictBigType">
			<c:if test="${dictBigType.dictRemark=='isTrue'}">
				<input type="checkbox" name="bigType" value="${dictBigType.dictId}" checked='true'/>${dictBigType.dictName}
			</c:if>
			<c:if test="${dictBigType.dictRemark!='isTrue'}">
				<input type="checkbox" name="bigType" value="${dictBigType.dictId}"/>${dictBigType.dictName}
			</c:if>			
		</c:forEach>
		</td>	
	</tr>	
	<c:if test="${partnerList!=null}">	
		<tr>
			<td class="label" >
				所属公司&nbsp;*
			</td>
			<td class="content"  colspan="3">
				<select name="interfaceHeadId" id="interfaceHeadId" alt="allowBlank:false,vtext:'请选择所属公司'">
						<option value="">
							--请选择所属公司--
						</option>
						<logic:iterate id="partnerList" name="partnerList">
							<option value="${partnerList.interfaceHeadId}">
								${partnerList.nodeName}
							</option>
						</logic:iterate>
				</select>
				 
			</td>
		</tr>					
	</c:if>
<html:hidden property="interfaceHeadId" value="${partnerDeptForm.interfaceHeadId}" />
<html:hidden property="deptMagId" value="${partnerDeptForm.deptMagId}" /> 
<tr>
		<td class="label">
			<fmt:message key="partnerDept.accessory" />
		</td>
		<td class="content" colspan="3">
		<!-- <html:text property="accessory" styleId="accessory"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${partnerDeptForm.accessory}" />  -->	
						
			<eoms:attachment idList="" idField="accessory" appCode="partnerBaseinfo" scope="request" name="partnerDeptForm" property="accessory"/> 
		</td>	
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="partnerDept.remark" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" onblur="testCharSize(this,255);"
						styleClass="text medium" style="width:80%" rows="3"
						alt="allowBlank:true,vtext:''" value="${partnerDeptForm.remark}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="sub();"/>		
		</td>
	</tr>
</table>
<html:hidden property="id" value="${partnerDeptForm.id}" />  
</html:form>
<script type="text/javascript">
	var interfaceHeadIdseled = '${partnerDeptForm.interfaceHeadId}';
	var id = '${partnerDeptForm.id}';
	if(interfaceHeadIdseled!=''&&interfaceHeadIdseled!=id){
		document.getElementById("interfaceHeadId").value = interfaceHeadIdseled;
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>