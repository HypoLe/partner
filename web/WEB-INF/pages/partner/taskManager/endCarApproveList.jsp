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
	<form action="${app}/partner/taskManager/carApprove.do?method=toEndCarApplyList" method="post">
		<table id="sheet" class="listTable">
			<tr>
				<td class="label">申请人</td>
				<td class="content">
					<input type="text"  class="text"  name="applyUserName" id="applyUserName"  
						value="" alt="allowBlank:false" readonly="readonly"/>
			   		<input type="hidden" name="checkStaff" id="checkStaff"  value=""/>
					<eoms:xbox id="applyUserName" dataUrl="${app}/xtree.do?method=userFromDept"  
					rootId="2" rootText="用户树"  valueField="checkStaff" handler="applyUserName" 
					textField="applyUserName" checktype="user" single="true" />
				</td>
				<td class="label">申请人所在部门</td>
				<td class="content">
					<input type="text" class="text" name="provName" id="provName"
							value="" alt="allowBlank:false" readonly="readonly" />
						<input name="prov" id="prov" value="" type="hidden" />
						<eoms:xbox id="provTree"
							dataUrl="${app}/xtree.do?method=userFromComp"
							rootId="" rootText="公司树" valueField="prov" handler="provName"
							textField="provName" checktype="dept" single="true" />
				</td>
			</tr>
			<tr>
				<td class="label">车牌号</td>
				<td class="content">
					<input type="text" type="text" class="text" name="carNum">
				</td>
				<td class="label">任务类型</td>
				<td class="content">
					<eoms:comboBox name="taskType" id="taskType" defaultValue="" styleClass="select" onchange="selectTaskType();"
						initDicId="11233" alt="allowBlank:false"/>
				</td>
			</tr>	
			<tr>
				<td class="label">任务名称</td>
				<td class="content">
					<input type="text" class="text" type="text" name="taskName">
				</td>
				<td class="label"></td>
				<td class="content"></td>
			</tr>
		</table>		
		<input type="submit" value="查询" class="btn">
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
		<display:column title="车牌号">
			<a href="${app}/partner/taskManager/carApprove.do?method=toCarApproveDetail&id=${list.id}"> 
				${list.car_num}<a>
		</display:column>
		<display:column title="申请人">
			<eoms:id2nameDB id="${list.apply_user}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column title="申请人所在部门">
			<eoms:id2nameDB id="${list.apply_user_dept}" beanId="tawSystemDeptDao" />
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
		<display:column title="处理环节">
			<c:if test="${list.approve_statue eq '-1'}">
				审批驳回
			</c:if>
			<c:if test="${list.approve_statue eq '0'}">
				待审批
			</c:if>
			<c:if test="${list.approve_statue eq '1'}">
				使用中
			</c:if>
			<c:if test="${list.approve_statue eq '2'}">
				已归还
			</c:if>
		</display:column>
		<display:column title="终止">
			<a href="${app}/partner/taskManager/carApprove.do?method=toEndCarApplyForm&id=${list.id}"> 
				<img src="${app }/images/icons/edit.gif"><a>
		</display:column>
		
		
	</display:table>
</logic:present>
	</br>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
