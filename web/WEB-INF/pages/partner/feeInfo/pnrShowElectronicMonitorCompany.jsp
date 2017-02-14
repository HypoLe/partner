<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<form action="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=showMonitorCompany"
	 method="post">
<table class="formTable">
	<tr>
		<td>
			公司名称：
		</td>
		<td>
			<select id="deptNodeId">
				<c:forEach items="${partnerDeptsList}" var="dept">
					<option value="${dept.nodeId}">${dept.nodeName}</option>
				</c:forEach>
			</select>
		</td>
		<td>
			<input type="button" id="submitSearch" class="button" value="查询"/>
		</td>
	</tr>
</table>
</form>
<tr>

<display:table name="partnerUsersList" cellspacing="0" cellpadding="0"
	id="partnerUsersList" pagesize="${pageSize}"
	class="table partnerUsersList" export="false"
	requestURI="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=showMonitorCompany"
	sort="list" partialList="true" size="resultSize">
	
	<display:column media="html" title="选择">
		<input type="radio" name="userName" class="checkAble" value="${partnerUsersList.name}"/>
	</display:column>
	
	<display:column property="name" sortable="false" 
		headerClass="sortable" title="人员名称" paramId="id" paramProperty="id" />
</display:table>
<input type="button" id="ensure" class="button" value="确定"/>
<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function(){
	myJ('#deptNodeId').find('option').each(function(){
		if(myJ(this).val()=='${nodeId}'){
			myJ(this).attr('selected','selected');
			return false;
		}
	});
	myJ("input[name='userName']").eq(0).attr('checked',true);
	myJ('input#submitSearch').click(function(){
		var deptNodeId = myJ('#deptNodeId').val();
		document.forms[0].action="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=showMonitorCompany&nodeId="+deptNodeId;
		document.forms[0].submit();
	});
	
	myJ('input#ensure').click(function(){
		var oMyObject = window.dialogArguments;
		var deptName = '${deptName}';
   		var userName = myJ("input[name='userName']:checked").val();
   		//window.opener.document.getElementById('elecCompany').value = deptName;
   		oMyObject.faultElectricForm.item('elecCompany').value=deptName;
   		if(userName){	
   			//window.opener.document.getElementById('elecPerson').value = userName;
   			oMyObject.faultElectricForm.item('elecPerson').value=userName;
   		}
    	window.close();
	});
});

</script>
<%@ include file="/common/footer_eoms.jsp"%>