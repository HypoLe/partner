<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
window.onload = function(){
    var proId = '${partnerDeptForm.id}';
	var operType = '${operType}';
	if(proId != '' && operType == 'save'){
	    parent.setproId(proId);
    }
}
	window.frameElement.height = 900;
	var isEdit = '${isEdit}';
	if(isEdit!='add'){
		document.body.style.overflow = "hidden";
	}     
    var subRetutn = false;
function sub() {    
	if(subRetutn){
		if(v.check()){
	       $("partnerDeptForm").submit();
		} 
	}else {
		alert("大单位类型为必填项");
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
			<fmt:message key="partnerDept.name" />
		</td>
		<td class="content">
			${partnerDeptForm.name}
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.type" />
		</td>
		<td class="content">		
			<eoms:id2nameDB id="${partnerDeptForm.type}"  beanId="ItawSystemDictTypeDao" />				
		</td>
		
	</tr>


	<tr>
		<td class="label">
			<fmt:message key="partnerDept.aptitude" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${partnerDeptForm.aptitude}"  beanId="ItawSystemDictTypeDao" />			
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.deadline" />
		</td>
		<td class="content">
			${partnerDeptForm.deadline}
		</td>
	</tr>



	<tr>
		<td class="label">
			<fmt:message key="partnerDept.manager" />
		</td>
		<td class="content">
			${partnerDeptForm.manager}
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.areaName" />
		</td>
		<td class="content">
			${partnerDeptForm.areaName}
		</td>
	</tr>



	<tr>
		<td class="label">
			<fmt:message key="partnerDept.fund" />
		</td>
		<td class="content">
			${partnerDeptForm.fund}
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.phone" />
		</td>
		<td class="content">
			${partnerDeptForm.phone}
		</td>
	</tr>



	<tr>
		<td class="label">
			<fmt:message key="partnerDept.address" />
		</td>
		<td class="content">
			${partnerDeptForm.address}
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.fax" />
		</td>
		<td class="content">
			${partnerDeptForm.fax}
		</td>
	</tr>

	<tr>
	    <td class="label">
			<fmt:message key="partnerDept.contactor" />
		</td>
		<td class="content">
			${partnerDeptForm.contactor}
		</td>
		
		<td class="label">
			<fmt:message key="partnerDept.zip" />
		</td>
		<td class="content">
			${partnerDeptForm.zip}
		</td>
		

	</tr>

	<tr>
	    <td class="label">
			<fmt:message key="partnerDept.bank" />
		</td>
		<td class="content">
			${partnerDeptForm.bank}
		</td>
<!-- 开户名称 -->
	    <td class="label">
			开户名称
		</td>
		<td class="content">
			${partnerDeptForm.accName}
		</td>		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerDept.account" />
		</td>
		<td class="content">
			${partnerDeptForm.account}
		</td>
	    <td class="label">
			<fmt:message key="partnerDept.email" />
		</td>
		<td class="content">
			${partnerDeptForm.email}
		</td>	

	</tr>
<!-- 
	<tr>
	
	    <td class="label">
			<fmt:message key="partnerDept.companyRegister" />
		</td>
		<td class="content">
			${partnerDeptForm.companyRegister}
		</td>
	    <td class="label">
			<fmt:message key="partnerDept.honestAgreement" />
		</td>
		<td class="content">
			${partnerDeptForm.honestAgreement}
		</td>	

	</tr>

	<tr>
	    <td class="label">
			营业执照号
		</td>
		<td class="content">
			${partnerDeptForm.licenceNum}
		</td>
	
	    <td class="label">
			企业法人
		</td>
		<td class="content" colspan="3">
			${partnerDeptForm.managercop}
		</td>
	</tr>	
	<tr>
	    <td class="label">
			单位人员总数
		</td>
		<td class="content">
			${partnerDeptForm.personCou}
		</td>
	    <td class="label">
			是否入围
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-baseinfo" dictId="isbackbone" itemId="${partnerDeptForm.selected}" beanId="id2nameXML" />		
		</td>	

	</tr>
	<tr>
	    <td class="label">
			公司主页
		</td>
		<td class="content">
			${partnerDeptForm.homepage}
		</td>	
	    <td class="label">
			税务登记号
		</td>
		<td class="content">
			${partnerDeptForm.registNo}
		</td>	

	</tr>	
	<tr>
	
 -->	
 	<c:if test="${partnerList!=null}">
	<tr>
		<td class="label">
			专业
		</td>
		<td class="content" colspan="3">		
			<eoms:id2nameDB id="${partnerDeptForm.specialty}"  beanId="ItawSystemDictTypeDao" />				
		</td>			 
	</tr>
	</c:if>
<!--大单位类型  -->	
 	<tr>
	 <td class="label">
			单位类型 
		</td>
		<td class="content" colspan="3">
		<c:forEach items="${tawSystemDictType}" var="dictBigType">
			<c:if test="${dictBigType.dictRemark=='isTrue'}">
				${dictBigType.dictName};
			</c:if>
		</c:forEach>
		</td>	
	</tr>	
	<c:if test="${partnerList!=null}">	
		<tr>
			<td class="label" >
				所属公司
			</td>
			<td class="content"  colspan="3">
					<eoms:id2nameDB id="${partnerDeptForm.interfaceHeadId}" beanId="partnerDeptDao" />
			</td>
		</tr>					
	</c:if>
			<html:hidden property="deptMagId" value="${partnerDeptForm.deptMagId}" />  
			<html:hidden property="interfaceHeadId" value="${partnerDeptForm.interfaceHeadId}" /> 
<tr>
		<td class="label">
			<fmt:message key="partnerDept.accessory" />
		</td>
		<td class="content" colspan="3">
			<eoms:attachment name="partnerDeptForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="baseinfo" viewFlag="Y"/>		
		</td>	
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="partnerDept.remark" />
		</td>
		<td class="content" colspan="3">
			${partnerDeptForm.remark}
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="编辑" onclick="var url='${app}/partner/baseinfo/partnerDepts.do?method=edit&proId=${partnerDeptForm.id}';location.href=url"/>		
		</td>
	</tr>
</table>
<html:hidden property="areaId" value="${partnerDeptForm.areaId}" />
<html:hidden property="id" value="${partnerDeptForm.id}" />  
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>