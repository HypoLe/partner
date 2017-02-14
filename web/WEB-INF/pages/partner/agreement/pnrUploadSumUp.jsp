<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="com.boco.eoms.partner.agreement.model.PnrAgreementMain"%>
<%@ page import="java.text.SimpleDateFormat"%>
<script type="text/javascript">
    function showAuditTable(){
		works = document.getElementById("auditTable");  
		if(works.style.display=='block'){
			works.style.display="none";
		} else {
			works.style.display="block";
		}
    }
</script>
<html:form action="/pnrAgreementMains.do?method=doUploadSumUp" styleId="pnrAgreementMainForm" method="post"> 

<table class="formTable" >
	<caption>
		<div class="header center">工作总结上传页面</div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			服务协议名称
		</td>
		<td class="content" >
			${pnrAgreementMain.agreementName}
		</td>
		<td class="label" style="vertical-align:middle">
			服务协议编号
		</td>
		<td class="content" >
			${pnrAgreementMain.agreementNO}
		</td>	
	</tr>		


	<tr>
		<td class="label" style="vertical-align:middle">
			工作总结
		</td>
		<td class="content" colspan="3">
 			<eoms:attachment name="pnrAgreementMain" property="sumUpAccessories" scope="request" idField="sumUpAccessories" appCode="agreement" /> 
		</td>
	</tr>	

    
	<html:hidden property="id" value="${id}" />
	</table>
        <input type="submit" id="subButton" value="提交"  class="button" />
</html:form>
</br>
<c:if test="${pnrAgreementListSize!=0}">
	<table class="formTable">
		  	<caption>
					<a  onclick="javascript:showAuditTable();">工作总结驳回信息</a>
			</caption>
	</table>
	<table class="formTable" id="auditTable" style="display:none;" >
	
			<c:forEach items="${pnrAgreementList}" var="audit"  varStatus="stauts">
					<tr>
					<th colspan="4">${stauts.count}&nbsp;</th>
					</tr>
			
					<tr>
						<td class="label">
							驳回人
						</td>
						<td class="content">
							<c:if test="${audit.toOrgType=='user'}">
								<eoms:id2nameDB id="${audit.toOrgId}" beanId="tawSystemUserDao" />
							</c:if>
							<c:if test="${audit.toOrgType=='dept'}">
								<eoms:id2nameDB id="${audit.toOrgId}" beanId="tawSystemDeptDao" />	
							</c:if>			
						</td>	
		
						<td class="label">
							驳回时间
						</td>
						<td class="content">
							<c:if test="${audit.operateTime!=null}">
								${audit.operateTimeString}
							</c:if>						
						</td>
					</tr>
					<tr>
						<td class="label">
							驳回说明
						</td>
						<td class="content" colspan="3">
							${audit.remark}
						</td>
					</tr>
			</c:forEach>
			
	</table>
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>