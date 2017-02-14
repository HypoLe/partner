<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>

<script type="text/javascript">
	var myjs=jQuery.noConflict();
	function openImport(handler){
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
	}

</script>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<html action="${app}/partner/taskManager/carApprove.do?method=toCarApproveList" method="post">
		<table id="sheet" class="listTable">
			
	</table>
	
		<html:submit styleClass="btn" property="method.approvalList"
			styleId="method.approvalList" value="查询"></html:submit>
	</form>
	</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>


<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="carApprove.do?method=toCarApproveList" 
		partialList="true" size="${size}">
		<display:column title="申请人">
			<eoms:id2nameDB id="${list.apply_user}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column title="申请人所在部门">
			<eoms:id2nameDB id="${list.apply_user_dept}" beanId="tawSystemDeptDao" />
		</display:column>
		<display:column title="车牌号">
			${list.car_num}
		</display:column>
		<display:column title="申请时间">
			<fmt:formatDate value="${list.apply_time}" type="both" dateStyle="medium"/>
		</display:column>
		<display:column title="任务类型">
			<eoms:id2nameDB id="${list.task_type}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column title="任务名称">
			${list.task_name}
		</display:column>
		<display:column title="审批">
			<a href="${app}/partner/taskManager/carApprove.do?method=toCarApproveForm&id=${list.id}"> 
				<img src="${app }/images/icons/edit.gif"><a>
		</display:column>
		
	</display:table>
</logic:present>
	</br>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
