<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>


<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'bigFaultForm'});
	setTabs();
});

	function setTabs() {
		var pageType = '${PAGE_TYPE}';
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.remove();}});
		var tabs = new Ext.TabPanel('info-page');
		tabs.addTab('base-info', '内容信息 ');
		if(pageType != null && pageType !="ADD_PAGE") {
	       	tabs.addTab('history-info', '审批信息 ');
		}
   		tabs.activate(0);
	}

	function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
	            }
    }
	//专业 联动 设备类型	
	function changeFacility(con){
		
		    delAllOption("equipmentType");
			var speciality = document.getElementById("speciality").value;
			var url="<%=request.getContextPath()%>/partner/deviceAssess/backEstimates.do?method=changeFacility&speciality="+speciality;
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									var json = eval(res);
									var equipmentType = "equipmentType";
									var arrOptions = json[0].facility.split(",");
									var obj=$(equipmentType);
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									if(con==1){
										var equipmentType = '${bigFaultForm.equipmentType}';
										if(equipmentType!=''){
											document.getElementById("equipmentType").value = equipmentType;
										}	
									
									}	
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
	}
window.onload = function(){
    var speciality = '${bigFaultForm.speciality}';
	if(speciality!=''){
 		document.getElementById("speciality").value = speciality;
		changeFacility(1);
	}
}
</script>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function() {
	v1 = new eoms.form.Validation({form:'importForm'});
	v1.custom = function() {
		var reg = "(.xls)$";
		var file = jq("#importFile").val();
		var right = file.match(reg);
		if(right == null) {
			alert("请选择Excel文件!");
			return false;
		} else {
			return true;
		}
	}
});
        
function openImport(handler){
	var el = Ext.get('listQueryObject'); 
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
}


  
function saveImport() {
	//表单验证
	if(!v1.check()) {
		return;
	}
	
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
	url : "${app}/partner/deviceAssess/bigFaults.do?method=importData",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		},
		failure : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		}
    });
	
}
</script>
<div id="loadIndicator" class="loading-indicator">
	加载页面完毕...
</div>

<div id="info-page">
	<div id="base-info" class="tabContent">
		<div class="header center">
			<b>厂家设备重大故障事件信息</b>
		</div>
		<br />
		<div style="border: 1px solid #98c0f4; padding: 5px;"
			class="x-layout-panel-hd">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"
				style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer"
				onclick="openImport(this);">从Excel导入</span>
		</div>

		<div id="listQueryObject"
			style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="bigFaults.do?method=importData" method="post"
				enctype="multipart/form-data" id="importForm" name="importForm">
				<fieldset>
					<legend>
						从Excel导入
					</legend>
					<table class="formTable">
						<tr>
							<td class="label">
								选择Excel文件
							</td>
							<td width="35%">
								<input id="importFile" type="file" name="importFile"
									class="file" alt="allowBlank:false" />
							</td>
						</tr>
						<tr>
							<td class="label">
								模板下载
							</td>
							<td>
								<input type="button" class="btn" value="下载导入文件模板" 
										onclick="javascript:location.href='${app}/partner/deviceAssess/bigFaults.do?method=download'"/>
							</td>
						</tr>
						<tr>
							<td class="label">
								选择审批人*
							</td>
							<td class="content" colspan="3">
								<input type="text" name="textApproval" id="textApproval"
									class="text" readonly="readonly" alt="allowBlank:false" />
								<input type="button" name="userButton" id="userButton"
									value="请选择审核人" class="btn" alt="allowBlank:false" />
								<input type="hidden" name="approvalPerson" id="reviewer" />
	
								<eoms:xbox id="tree1"
									dataUrl="${app}/xtree.do?method=userFromDept" rootId="1"
									rootText='用户树' valueField="reviewer" handler="userButton"
									textField="textApproval" checktype="user" single="true"></eoms:xbox>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
		</div>
		<br />
		<html:form action="/bigFaults.do?method=save" styleId="bigFaultForm" method="post"> 

			<table id="sheet" class="formTable">
				<tr>
					<td colspan="4">
						<div class="ui-widget-header">
							工单信息
						</div>
					</td>
				</tr>

				<tr>
					<td class="label">
						工单号*
					</td>
					<td class="content">
						<html:text property="sheetNum" styleId="sheetNum"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${bigFaultForm.sheetNum}" />
					</td>
					<td class="label">
						建单时间*
					</td>
					<td class="content">
						<html:text property="createTime" styleId="createTime"
							styleClass="text medium"
							onclick="popUpCalendar(this,this,null,null,null,true,-1);"
							alt="vtype:'lessThen',link:'pigeonholeTime',vtext:'建单时间不能晚于归档时间',allowBlank:false"
							value="${bigFaultForm.createTime}" readonly="true" />
					</td>
				</tr>

				<tr>
					<td class="label">
						归档时间*
					</td>
					<td class="content" colspan="3">
						<html:text property="pigeonholeTime" styleId="pigeonholeTime"
							styleClass="text medium"
							onclick="popUpCalendar(this,this,null,null,null,true,-1);"
							alt="vtype:'moreThen',link:'createTime',vtext:'建单时间不能晚于归档时间',allowBlank:false"
							value="${bigFaultForm.pigeonholeTime}" readonly="true" />
					</td>
				</tr>

				<tr>
					<td colspan="4">
						<div class="ui-widget-header">
							事件信息
						</div>
					</td>
				</tr>

				<tr>
					<td class="label">
						重大故障名称*
					</td>
					<td class="content">
						<html:text property="bigFaultName" styleId="bigFaultName"
							styleClass="text medium"
							alt="allowBlank:false,vtext:''" value="${bigFaultForm.bigFaultName}" />
					</td>
					<td class="label">
						重大故障定义编号*
					</td>
					<td class="content">
						<html:text property="bigFaultNo" styleId="bigFaultNo"
							styleClass="text medium"
							alt="allowBlank:false,vtext:''" value="${bigFaultForm.bigFaultNo}" />
					</td>
				</tr>

				<tr>
					<td class="label">
						发生省份*
					</td>
					<td class="content">
						<html:text property="province" styleId="province"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${bigFaultForm.province}" />
					</td>
					<td class="label">
						发生地市*
					</td>
					<td class="content">
						<html:text property="city" styleId="city"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${bigFaultForm.city}" />
					</td>
				</tr>

				<tr>
					<td colspan="4">
						<div class="ui-widget-header">
							厂家信息
						</div>
					</td>
				</tr>

				<tr>
					<td class="label">
						厂家*
					</td>
					<td class="content">
						<eoms:comboBox name="factory" id="factory" initDicId="1121502"
							styleClass="select max"
							defaultValue="${bigFaultForm.factory}"
							alt="allowBlank:false,vtext:'请选择厂家...'" />
					</td>
					<td class="label">
						专业*
					</td>
					<td class="content">
						<eoms:comboBox name="speciality" id="speciality"
							styleClass="select max" initDicId="11216"
							defaultValue="${bigFaultForm.speciality}"
							alt="allowBlank:false,vtext:'请选择专业...'"
							onchange="changeFacility(0);" />
					</td>
				</tr>

				<tr>
					<td class="label">
						设备类型*
					</td>
					<td class="content">
						<select name="equipmentType" id="equipmentType"
							class="select max" alt="allowBlank:false,vtext:'请选择设备类型'">
							<option value="">
								请选择设备类型
							</option>
						</select>
					</td>
					<td class="label">
						设备名称*
					</td>
					<td class="content">
						<html:text property="equipmentName" styleId="equipmentName"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${bigFaultForm.equipmentName}" />
					</td>
				</tr>

				<tr>
					<td class="label">
						设备版本*
					</td>
					<td class="content" colspan="3">
						<html:text property="equipmentVersion" styleId="equipmentVersion"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${bigFaultForm.equipmentVersion}" />
					</td>
				</tr>

				<tr>
					<td colspan="4">
						<div class="ui-widget-header">
							统计信息
						</div>
					</td>
				</tr>

				<tr>
					<td class="label">
						故障开始时*
					</td>
					<td class="content">
						<html:text property="beginTime" styleId="beginTime"
							readonly="true" styleClass="text medium"
							onclick="popUpCalendar(this,this,null,null,null,true,-1);"
							alt="vtype:'lessThen',link:'resumeTime,removeTime',vtext:'故障开始时不能晚于业务恢复时间或故障消除时间',allowBlank:false"
							value="${bigFaultForm.beginTime}" />
					</td>
					<td class="label">
						业务恢复时间*
					</td>
					<td class="content">
						<html:text property="resumeTime" styleId="resumeTime"
							readonly="true" styleClass="text medium"
							onclick="popUpCalendar(this,this,null,null,null,true,-1);"
							alt="vtype:'moreThen',link:'beginTime',vtext:'故障开始时不能晚于业务恢复时间或故障消除时间',allowBlank:false"
							value="${bigFaultForm.resumeTime}" />
					</td>
				</tr>

				<tr>
					<td class="label">
						故障消除时间*
					</td>
					<td class="content" colspan="3">
						<html:text property="removeTime" styleId="removeTime"
							readonly="true" styleClass="text medium"
							onclick="popUpCalendar(this,this,null,null,null,true,-1);"
							alt="vtype:'moreThen',link:'beginTime',vtext:'故障开始时不能晚于业务恢复时间或故障消除时间',allowBlank:false"
							value="${bigFaultForm.removeTime}" />
					</td>
				</tr>

				<tr>
					<td colspan="4">
						<div class="ui-widget-header">
							审批信息
						</div>
					</td>
				</tr>

				<tr>
					<td class="label">
						选择审批人*
					</td>
					<td class="content" colspan="3">
						<input type="text" name="textReviewer" id="textReviewer" value="<eoms:id2nameDB id="${bigFaultForm.deviceAssessApprove.approveUser }" beanId="tawSystemUserDao" />"
							class="text" readonly="readonly" alt="allowBlank:false" />
						<input type="button" name="approvalButton" id="approvalButton"
							value="请选择审核人" class="btn" alt="allowBlank:false" />
						<input type="hidden" name="approvalUser" id="approvalUser"  value="${bigFaultForm.deviceAssessApprove.approveUser }"/>

						<eoms:xbox id="tree1"
							dataUrl="${app}/xtree.do?method=userFromDept" rootId="1"
							rootText='用户树' valueField="approvalUser" handler="approvalButton"
							textField="textReviewer" checktype="user" single="true"></eoms:xbox>
					</td>
				</tr>
			</table>

			<table>
				<tr>
					<td>
						<input type="submit" class="btn" value="保存" />
						<input type="reset" class="btn" value="重置"
							onclick="javascript :history.back(-1)">
					</td>
				</tr>
			</table>
			<html:hidden property="faultLong"
				value="${bigFaultForm.faultLong}" />
			<html:hidden property="resumeLong"
				value="${bigFaultForm.resumeLong}" />
			<html:hidden property="amount" value="1" />
			<html:hidden property="id" value="${bigFaultForm.id}" />
			<html:hidden property="total" value="1" />
	</html:form>
	</div>
	<!-- 查看审批信息 -->
	<div id="history-info" class="tabContent">
		<logic:notEmpty name="dacList" scope="request">
			<display:table name="dacList" cellspacing="0" cellpadding="0"
				class="table dacList" id="dacList" export="false" sort="list"
				partialList="true" size="${size}">

				<display:column headerClass="sortable" title="重大故障名称">
					<eoms:id2nameDB id="${dacList.assessId}" beanId="bigFaultDao" />
				</display:column>
				<display:column headerClass="sortable" title="提交时间">
				${dacList.commitTime}
		       </display:column>
				<display:column headerClass="sortable" title="审批人">
					<eoms:id2nameDB id="${dacList.approveUser}"
						beanId="tawSystemUserDao" />
				</display:column>
				<display:column headerClass="sortable" title="意见">
				${dacList.content}
		       </display:column>
				<display:column headerClass="sortable" title="流转状态">
					<c:if test="${dacList.state=='0'}">
						驳回
					</c:if>
					<c:if test="${dacList.state=='1'}">
						同意
					</c:if>
				</display:column>
				<display:column headerClass="sortable" title="备注">
				${dacList.remark}
		       </display:column>

			</display:table>
		</logic:notEmpty>
		<logic:empty name="" scope="request">
			没有记录!
		</logic:empty>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>