<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }

window.onload = function(){
    var personCardNo = '${partnerUserForm.personCardNo}';
	var operType = '${operType}';
	if(personCardNo != '' && operType == 'save'){
	    parent.setPersonCardNo(personCardNo);
    }
}
function createRequest()
{
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}
	window.frameElement.height = 900;
	var isEdit = '${isEdit}';
	if(isEdit!='add'){
		document.body.style.overflow = "hidden";
	}       
</script>
<script language="javascript" for="document" event="onkeydown">
    if (event.keyCode == 13)
    {
        isEmail(document.getElementById('email').value);
    }
</script>

<html:form action="/partnerUsers.do?method=save" styleId="partnerUserForm" method="post" onsubmit=""> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<html:hidden property="treeNodeId"/>

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="partnerUser.form.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="partnerUser.name" />&nbsp;*
		</td>
		<td class="content">
			${partnerUserForm.name}
		</td>
	    <td  rowspan="4" valign="middle">
			<fmt:message key="partnerUser.photo" />
		</td>		
	    <td  rowspan="4" valign="bottom">
		   <html:hidden property="photo" styleId="photo" value="${partnerUserForm.photo}"/>
		   <html:hidden property="accessory" styleId="accessory" value="${partnerUserForm.accessory}"/>
		   <c:if test="${partnerUserForm.photo!=null}"><!-- 修改时 -->
			   <div id="imgdiv">
			      <img id="imgUser" name="imgUser" src="${partnerUserForm.photo}"  border="0" width="130" height="180">
			      <br>
			   </div>   
		      <iframe id="upframe" name="upframe" class="uploadframe" frameborder="0" style="display:none;height:50pt;width:100%" src="${app }/partner/baseinfo/partnerUsers.do?method=toUploadphotoPage" scrolling="no" ></iframe>
		   </c:if>
		   <c:if test="${partnerUserForm.photo==null}">
		   		暂无照片
		   </c:if>
		</td>
	</tr>
	<tr>	
		<td class="label">
			身份证号码&nbsp;*
		</td>
		<td class="content">
			${partnerUserForm.personCardNo}
		</td>	
	</tr>
	<tr>
	    <td class="label">
			所属地市&nbsp;*
		</td>
		<td class="content">
			${partnerUserForm.areaName}
			<html:hidden property="areaId" styleId="areaId"/>
		</td>
		</tr>
		<tr>
		<td class="label">
			所属公司&nbsp;*
		</td>
		<td class="content">
				${partnerUserForm.deptName}
				<html:hidden property="deptId"/>
		</td>
		</tr>
		<!-- 
		<tr>
		<td class="label">
			<fmt:message key="partnerUser.areaNames" />&nbsp;*
		</td>
		<td class="content">
			${partnerUserForm.areaNames}
			<input type="hidden" name="areaType" id="areaType" >
		</td>
		<td class="label">
			<fmt:message key="partnerUser.userId" />&nbsp;*
		</td>
		<td class="content">
			${partnerUserForm.userId}
		</td>
		</tr>
		<tr>
			<td class="label">
				<fmt:message key="partnerUser.serviceProfessional" />&nbsp;*
			</td>
			<td class="content">
				<eoms:id2nameDB id="${partnerUserForm.serviceProfessional}"  beanId="ItawSystemDictTypeDao" />	
			</td>
			<td class="label">
				<fmt:message key="partnerUser.workLicenseLevel" />&nbsp;*
			</td>
				<eoms:id2nameDB id="${partnerUserForm.workLicenseLevel}"  beanId="ItawSystemDictTypeDao" />	
			</td>
		</tr>

		<tr>
			<td class="label">
				<fmt:message key="partnerUser.workLicenseMajor" />&nbsp;*
			</td>
			<td class="content">
				<eoms:id2nameDB id="${partnerUserForm.workLicenseMajor}"  beanId="ItawSystemDictTypeDao" />
			</td>
		
			<td class="label">
				<fmt:message key="partnerUser.projectName" />&nbsp;*
			</td>
			<td class="content">
				${partnerUserForm.projectName}
			</td>
		</tr>

		<tr>
			<td class="label">
				<fmt:message key="partnerUser.maintainLevel" />&nbsp;*
			</td>
			<td class="content">
				<eoms:id2nameDB id="${partnerUserForm.maintainLevel}"  beanId="ItawSystemDictTypeDao" />			
			</td>
		
			<td class="label">
				<fmt:message key="partnerUser.projectProperty" />&nbsp;*
			</td>
			<td class="content">
				<eoms:id2nameDB id="${partnerUserForm.projectProperty}"  beanId="ItawSystemDictTypeDao" />	
			</td>
		</tr>


		<tr>
			<td class="label">
				dfasdf<fmt:message key="partnerUser.responsibility" />&nbsp;*
			</td>
			<td class="content">
				<%--<eoms:id2nameDB id="${partnerUserForm.responsibility}"  beanId="ItawSystemDictTypeDao" />
			--%>
			${partnerUserForm.responsibility }
			</td>
			
		
			<td class="label">
				<fmt:message key="partnerUser.isbackbone" />&nbsp;*
			</td>
			<td class="content">
				<eoms:dict key="dict-partner-baseinfo" dictId="isbackbone" itemId="${partnerUserForm.isbackbone}" beanId="id2nameXML" />				
			</td>
		</tr>

		<tr>
			<td class="label">
				<fmt:message key="partnerUser.mobilePhone" />&nbsp;*
			</td>
			<td class="content">
				${partnerUserForm.mobilePhone}
			</td>
			<td class="label">
				<fmt:message key="partnerUser.email" />&nbsp;*
			</td>
			<td class="content">
				${partnerUserForm.email}
			</td>		
		</tr>										
		<tr>
		<td class="label">
			<fmt:message key="partnerUser.sex" />&nbsp;*
		</td>
		<td class="content">
		    <eoms:id2nameDB id="${partnerUserForm.sex}"  beanId="ItawSystemDictTypeDao" />
		</td>
		<td class="label">
			<fmt:message key="partnerUser.birthdey" />&nbsp;*
		</td>
		<td class="content">
			${partnerUserForm.birthdey}
		</td>
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="partnerUser.diploma" />&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${partnerUserForm.diploma}"  beanId="ItawSystemDictTypeDao" />
		</td>

		<td class="label">
			<fmt:message key="partnerUser.workTime" />&nbsp;*
		</td>
		<td class="content">
			${partnerUserForm.workTime}
		</td>
       </tr>
       <tr>
		<td class="label">
			<fmt:message key="partnerUser.deptWorkTime" />&nbsp;*
		</td>
		<td class="content">
			${partnerUserForm.deptWorkTime}
		</td>

		<td class="label">
			<fmt:message key="partnerUser.licenseType" />&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${partnerUserForm.licenseType}"  beanId="ItawSystemDictTypeDao" />		
		</td>
		</tr>
		<tr>
			<td class="label">
				<fmt:message key="partnerUser.licenseNo" />&nbsp;*
			</td>
			<td class="content">
				${partnerUserForm.licenseNo}
			</td>
			<td class="label">
				<fmt:message key="partnerUser.post" />&nbsp;*
			</td>
			<td class="content">		
				<eoms:id2nameDB id="${partnerUserForm.post}"  beanId="ItawSystemDictTypeDao" />						
			</td>
		</tr>
		<tr>
		<td class="label">
			在岗状态<fmt:message key="partnerUser.postState" />&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${partnerUserForm.postState}"  beanId="ItawSystemDictTypeDao" />				
		</td>

		<td class="label">
			<fmt:message key="partnerUser.phone" />&nbsp;*
		</td>
		<td class="content">
			${partnerUserForm.phone}
		</td>
		</tr>
		<tr>
			<td class="label">
				<fmt:message key="partnerUser.remark" />
			</td>
			<td class="content" colspan="3">
				${partnerUserForm.remark}
			</td>
			-->

	</tr>

</table>

</fmt:bundle>


<html:hidden property="id" value="${partnerUserForm.id}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>