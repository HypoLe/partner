<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="/pnrAgreementMains.do?method=savaReject" styleId="pnrAgreementMainForm" method="post"> 

<table class="formTable" >
	<caption>
		<div class="header center">工作总结驳回页面</div>
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
			<eoms:attachment name="pnrAgreementMain" property="sumUpAccessories" scope="request" idField="sumUpAccessories" appCode="agreement" viewFlag="Y"/>		
		</td>
	</tr>	

	<tr>
		<td class="label" style="vertical-align:middle">
			驳回说明
		</td>
		<td class="content" colspan="3">
			<textarea name="reject" maxLength="1000" rows="2" style="width:98%;" id="reject"></textarea>		
		</td>
	</tr>
	    
	<html:hidden property="agreementId" value="${agreementId}" />
	</table>
        <input type="submit" id="subButton" value="提交"  class="button" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>