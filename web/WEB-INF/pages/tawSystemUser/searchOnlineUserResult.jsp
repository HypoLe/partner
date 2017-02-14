<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function(){
	colorRows('list-table');
})
</script>


<div id="form" class="tab-content">
<table cellspacing="0" cellpadding="0" border="1" id="list-table" class="formTable" >
	<tr height="30" class="header">
		<td width="20%" class="label">
			用户名
		</td>
		<td width="20%" class="label">
			账号
		</td>
		<td width="20%" class="label" >
			部门
		</td>
		<td width="20%" class="label" >
			分公司
		</td>
		<td width="20%" class="label" >
			电话
		</td>
		<td width="20%" class="label">
			登录IP
		</td>
		<td width="20%" class="label" >
			登录时间
		</td>
	</tr>
<logic:iterate id="result" name="result">
	<tr height="30">
		<td width="20%">
			<bean:write name="result" property="value.username"/>
		</td>
		<td width="20%">
			<bean:write name="result" property="value.userid"/>
		</td>
		<td width="20%">
			<bean:write name="result" property="value.deptname"/>
		</td>
		<td width="20%">
			<bean:write name="result" property="value.companyName"/>
		</td>
		<td width="20%">
			<bean:write name="result" property="value.contactMobile"/>
		</td>
		<td width="20%">
			<bean:write name="result" property="value.romteaddr"/>
		</td>
		<td width="20%">
			<bean:write name="result" property="value.loginTimeStr"/>
		</td>
	</tr>
</logic:iterate>
</table>
<br/>
</div>
<div align="center">
	<input type="button" name="refresh" value="刷新" class="btn" onclick="window.location.reload()" />
	<input type="button" name="close" value="关闭" class="btn" onclick="window.close()" />
</div>
<%@ include file="/common/footer_eoms.jsp"%>