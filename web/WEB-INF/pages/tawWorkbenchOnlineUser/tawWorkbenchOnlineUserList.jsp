<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function(){
	colorRows('list-table');
})
</script>
<script type="text/javascript">
Ext.onReady(function(){
	var tabs = new Ext.TabPanel('tabs');
    var formTab = tabs.addTab('form', "${eoms:a2u('系统当前在线用户')}");
    
    formTab.on('activate',function(){
    	//location.href = "/interfaceMonitoringLog.do?method=newInterfaceMonitoring&id=ssssss"
    });
    
    tabs.activate('form');	
});
</script>

<%com.boco.eoms.base.webapp.listener.UserCounterListener.getOnlineUsers(request);%>
<div id="tabs">
<div id="form" class="tab-content">
<table cellspacing="0" cellpadding="0" border="1" id="list-table" class="formTable" >
	<tr height="30" class="header">
		<td width="20%" class="label">
			${eoms:a2u('用户名')}
		</td>
		<td width="20%" class="label" >
			${eoms:a2u('部门')}
		</td>
		<td width="20%" class="label" >
			${eoms:a2u('登录时间')}
		</td>
		<td width="20%" class="label" >
			${eoms:a2u('最近活动时间')}
		</td>
		<td width="20%" class="label">
			${eoms:a2u('登录IP')}
		</td>
	</tr>
<logic:iterate id="usersMap" name="usersMap">
	<tr height="30">
		<td width="20%">
			<bean:write name="usersMap" property="value.username"/>
		</td>
		<td width="20%">
			<bean:write name="usersMap" property="value.deptname"/>
		</td>
		<td width="20%">
			<bean:write name="usersMap" property="value.loginTime" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
		<td width="20%">
			<bean:write name="usersMap" property="value.activeTime" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
		<td width="20%">
			<bean:write name="usersMap" property="value.romteaddr"/>
		</td>
	</tr>
</logic:iterate>
</table>
</div>
 
<br/>
  </div>
<div align="center">
	<input type="button" name="refresh" value="${eoms:a2u('刷新')}" class="btn" onclick="window.location.reload()" />
	<input type="button" name="close" value="${eoms:a2u('关闭')}" class="btn" onclick="window.close()" />
</div>
<%@ include file="/common/footer_eoms.jsp"%>