<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.eva.util.EvaConstants"/>
<%@ page language="java" import="java.util.*"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
function query(){
	var taskId = document.getElementById("tempTaskName").value;
	var partnerDept = document.getElementById("partnerDept").value;
	if(""==taskId.trim()&&""==partnerDept.trim()){
		alert("请填写查询条件!");
		return false;
	}
		document.forms[0].submit();
};
</script>
	<eoms:xbox id="partnerDept" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="partnerDept" handler="partnerDeptName"
		textField="partnerDeptName"
		checktype="dept" single="true"		
	  ></eoms:xbox>
<html:form action="/evaTemplates.do?method=xsearch" styleId="evaTemplateForm" method="post"> 
<table class="formTable" id="form" name="form">
	<caption>
		<div class="header center">考核查询</div>
	</caption>
	<tr>
		<td class="label">
			考核模板名称
		</td>
		<td class="content">
			<html:text property="tempTaskName" styleId="tempTaskName" styleClass="text"  style="width:97%;" value="" />
		</td>
		<td class="label">
			被考核公司
		</td>
		<td class="content" colspan="3">
			<html:text property="partnerDeptName" styleId="partnerDeptName" styleClass="text"  style="width:97%;" value="" readonly="true"/>
			<html:hidden property="partnerDept" value=""/>
		</td>
	</tr>
</table>
<table id="submit-btn" align="left">
	<tr>
		<td>
			<input type="button" class="btn" value="查询" onclick="query();" />
		</td>
	</tr>
</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>