<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js">
</script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js">
</script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js">
</script>
<script type="text/javascript">

var myjs = jQuery.noConflict();
Ext.onReady(function() {
	v = new eoms.form.Validation( {
		form : 'advertisementPlanForm'
	});
	v.custom = function() {
		return true;
	};
	//var userTreeAction = eoms.appPath + '/xtree.do?method=userFromDept';

	//new xbox( {
	//	btnId : 'areatree',
	//	treeDataUrl : '${app}/partner/baseinfo/xtree.do?method=area',
	//	treeRootId : '10',
	//	treeRootText : '黑龙江',
//	treeChkMode : '',
	//	treeChkType : 'area',
	//	single : true,
	//	showChkFldId : 'advertisementCity',
	//	saveChkFldId : 'advertisementArea',
	//	returnJSON : false
	//});
	Ext.get('loadIndicator').setOpacity(0.0, {
		duration : 2.0,
		callback : function() {
			this.hide();
		}});
	var tabs = new Ext.TabPanel('info-page');
	tabs.addTab('content-info', '内容信息 ');
	tabs.addTab('history-info', '审批信息 ');
	tabs.activate(0);

});

//function openImport(handler) {
//	var el = Ext.get('listQueryObject');
//	if (el.isVisible()) {
	//	el.slideOut('t', {
	//		useDisplay : true
	//	});
	//	handler.innerHTML = "打开导入界面";
//	} else {
	//	el.slideIn();
	//	handler.innerHTML = "关闭导入界面";
	//}
//}

function openSelectAreas() {
	window
			.open(
					eoms.appPath + '/partner/baseinfo/areaDeptTrees.do?method=selectAreas',
					'newwindow',
					'height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
}

//function save2Draft() {
//	var reviewer = document.getElementById("reviewer");
//	var advertisementArea = document.getElementById("advertisementArea").value;
	//var generalStone = document.getElementById("generalStone").value;
	//var detectStone = document.getElementById("detectStone").value;
	//var city = document.getElementById("city").value;
	//console.dir(reviewer);
//	alert(advertisementArea);
	//location.href='<html:rewrite page='/advertisementPlanAction.do?method=save'/>'
//}

//function saveImport() {
	//表单验证
	//if (!v1.check()) {
	//	return;
//	}

	//new Ext.form.BasicForm('importForm').submit( {
	//	method : 'post',
	//	url : "${app}/taskOrder/taskOrderAction.do?method=importRecord",
	//	waitTitle : '请稍后...',
	//	waitMsg : '正在导入数据,请稍后...',
	//	success : function(form, action) {
	////		alert(action.result.infor);
	//	},
	//	failure : function(form, action) {
		////	alert(action.result.infor);
	//	}
//	});

//}
</script>

<content tag="heading">
<c:out value="宣传牌增补计划信息" />
</content>
<br />
<br />

<div id="loadIndicator" class="loading-indicator">
	加载详细信息页面完毕...
</div>
<div id="info-page">
	<!-- 查看内容信息 -->
	<div id="content-info" class="tabContent">
		<form action="advertisementPlanAction.do?method=review" method="post"
			id="advertisementPlanForm" name="advertisementPlanForm">
			<input type="hidden" name="id" value="${advertisementPlan.id}" />
			<table id="checkSegmentTable" class="formTable">

				<tr>
					<td colspan="4">
						<div class="ui-widget-header">
							宣传牌增补计划信息
						</div>
					</td>
				</tr>

				<tr>
					<td class="label">
						地市名称
					</td>
					<td class="content">
						<eoms:id2nameDB id="${advertisementPlan.city}"
							beanId="tawSystemAreaDao" />
					</td>

					<td class="label">
						计划增加宣传牌数量
					</td>
					<td class="content">
						${advertisementPlan.advertisement}套
					</td>

				</tr>
				<tr>
					<td class="label">
						计划增补普通标石
					</td>
					<td class="content">
						${advertisementPlan.generalStone}块
					</td>
					<td class="label">
						计划增补检测标石
					</td>
					<td class="content">
						${advertisementPlan.detectStone}块
					</td>
				</tr>
				<tr>
					<td class="label">
						备注信息
					</td>
					<td colspan="3" class="content">
						${advertisementPlan.remarks}
					</td>
				</tr>
			</table>
			<table class="formTable">
				<tr>
					<td colspan="4">
						<div class="ui-widget-header">
							宣传牌增补计划审核
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">
						审核结果*
					</td>
					<td colspan="3" class="content">
						<select name="allow">
							<option value="1" selected="selected">
								通过审核
							</option>
							<option value="2">
								驳回
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">
						审核意见
					</td>
					<td colspan="3" class="content">
						<textarea class="textarea max" name="suggestion" id="suggestion"
							alt="allowBlank:true"></textarea>
					</td>
				</tr>
</table>
				<html:submit styleClass="btn" property="method.save"
					styleId="method.save" value="审核"></html:submit>
	
				<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
			
		</form>
	</div>
	<div id="history-info" class="tabContent">
		<logic:notEmpty name="operHis" scope="request">
			<display:table name="operHis" cellspacing="0" cellpadding="0"
				class="table operHis" id="operHis" export="false" sort="list"
				partialList="true" size="size">

				<display:column headerClass="sortable" title="审批人">
					<eoms:id2nameDB id="${operHis.actionSendUser}"
						beanId="tawSystemUserDao" />
				</display:column>

				<display:column headerClass="sortable" title="审批时间">
			${operHis.actionHappenTime}
        </display:column>
				<display:column headerClass="sortable" title="审批意见">
			${operHis.actionIdea}
        </display:column>

				<display:column headerClass="sortable" title="状态">
			${operHis.currentStatus}
        </display:column>
			</display:table>
		</logic:notEmpty>
		<logic:empty name="operHis" scope="request">没有记录</logic:empty>
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>