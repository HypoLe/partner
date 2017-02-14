<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
 var myjs=jQuery.noConflict();
Ext.onReady(function(){
            v = new eoms.form.Validation({form:'externalForceFrom'});
            });
</script>
<eoms:xbox id="ownerUser" dataUrl="${app}/xtree.do?method=userFromDept"  
		rootId="2" rootText="用户树"  valueField="ownerUser" handler="ownerUserName" 
		textField="ownerUserName" checktype="user" data="${sendUserAndRoles }" single="true" />
<form action="externalForceCheck.do?method=save" method="post"  id="externalForceFrom" name="externalForceFrom">
<table class="formTable">
	<tr>
		<td colspan="4"><div class="ui-widget-header" >外力盯防现场管理信息录入</div></td>
	</tr>
	<tr>
		<td class="label">外力盯防现场地点</td>
		<td class="content">
		<input class="text" type="text" name="place"
					id="place" alt="allowBlank:false" /></td>
		<td class="label">外力盯防负责人</td>
		<td>
		<input type="text"  class="text"  name="ownerUserName" id="ownerUserName"  value="" readonly="readonly"/>
		<input type="hidden" name="ownerUser" id="ownerUser"  value=""/>
		</td>
	</tr>
	<tr>
	<td class="label">外力盯防起始日期</td>
	<td><input class="text" type="text" name="startDate"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'lessThen',link:'endDate',vtext:'开始时间不能晚于结束时间！'"
					 id="startDate" alt="allowBlank:false" readonly="readonly"/>
					</td>
	<td class="label">外力盯防结束日期</td>
	<td><input class="text" type="text" name="endDate"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'moreThen',link:'startDate',vtext:'结束时间不能早于开始时间！'"
					 id="endDate" alt="allowBlank:false" readonly="readonly"/></td>
					
	</tr>
	<tr>
	<td class="label">外力盯防所属线路</td>
	<td><input class="text" type="text" name="route"
					id="route" alt="allowBlank:false" /></td>
	<td class="label">外力盯防所属线路段</td>
	<td><input class="text" type="text" name="routeStage"
					id="routeStage" alt="allowBlank:false" /></td>
	</tr>
	<tr>
	<td class="label">外力盯防GPS终端设备</td>
	<td colspan="3"><input class="text" type="text" name="gpsFacility"
					id="gpsFacility" alt="allowBlank:false" /></td>
	</tr>
	
</table>
<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存"></html:submit>

<!--  
<input type="submit" value="保存"/>
<input type="button" value="关闭" onclick="javascript:window.close();" class="clsbtn2">
-->
</form>
<%@ include file="/common/footer_eoms.jsp"%>