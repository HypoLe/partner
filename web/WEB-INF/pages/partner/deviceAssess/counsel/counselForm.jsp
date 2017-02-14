<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'counselForm'});
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
		
		    delAllOption("equipmentType");//地市选择更新后，重新刷新县区
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
										var equipmentType = '${counselForm.equipmentType}';
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
    var speciality = '${counselForm.speciality}';
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
	url : "${app}/partner/deviceAssess/insideDisposes.do?method=importData",
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
	<b>咨询服务事件信息表</b>
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
			<form action="counsels.do?method=importData" method="post"
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
							选择审批人*
						</td>
						<%-- <td class="content" colspan="3"><input type="text" class="text"
				name="approvalUserName" id="approvalUserName" value="<eoms:id2nameDB beanId="tawSystemUserDao" id="${counselForm.approveUser}"/>"
				alt="allowBlank:false" readonly="readonly" /> 
				<input type="hidden"
				name="approveUser" id="approveUser" value="${counselForm.approveUser}" /> 
				<eoms:xbox
				id="approvalUserName" dataUrl="${app}/xtree.do?method=userFromDept"
				rootId="2" rootText="用户树" valueField="approveUser"
				handler="approvalUserName" textField="approvalUserName"
				checktype="user" single="true"  handler="approvalButton" /></td>
			</td>--%>
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
		
		
<html:form action="/counsels.do?method=save" styleId="counselForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

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
			<fmt:message key="counsel.sheetNum" />
		</td>
		<td class="content">
			<html:text property="sheetNum" styleId="sheetNum"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${counselForm.sheetNum}" />
		</td>
		<td class="label">
			<fmt:message key="counsel.createTime" />
		</td>
		<td class="content">
			<html:text property="createTime" styleId="createTime" readonly="true"
						styleClass="text medium"  onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						value="${counselForm.createTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="counsel.changeTime" />
		</td>
		<td class="content">
			<html:text property="changeTime" styleId="changeTime" readonly="true"
						styleClass="text medium"  onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						value="${counselForm.changeTime}" />
		</td>
		<td class="label">
			<fmt:message key="counsel.pigeonholeTime" />
		</td>
		<td class="content">
			<html:text property="pigeonholeTime" styleId="pigeonholeTime" readonly="true"
						styleClass="text medium"  onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						value="${counselForm.pigeonholeTime}" />
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
			<fmt:message key="counsel.affairName" />
		</td>
		<td class="content">
			<html:text property="affairName" styleId="affairName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${counselForm.affairName}" />
		</td>
		<td class="label">
			<fmt:message key="counsel.affairLevel" />
		</td>
		<td class="content">
			<eoms:comboBox name="affairLevel" id="affairLevel" initDicId="1121501" defaultValue="${counselForm.affairLevel}"
			    alt="allowBlank:false,vtext:'请选择等级...'"/>		
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="counsel.province" />
		</td>
		<td class="content">
			<html:text property="province" styleId="province"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${counselForm.province}" />
		</td>
		<td class="label">
			<fmt:message key="counsel.city" />
		</td>
		<td class="content">
			<html:text property="city" styleId="city"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${counselForm.city}" />
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
			<fmt:message key="counsel.factory" />
		</td>
		<td class="content">
			<eoms:comboBox name="factory" id="factory" initDicId="1121502" defaultValue="${counselForm.factory}"
			    alt="allowBlank:false,vtext:'请选择厂家...'"/>		
		</td>
		<td class="label">
			<fmt:message key="counsel.speciality" />
		</td>
		<td class="content">
			<eoms:comboBox name="speciality" id="speciality" initDicId="11216" defaultValue="${counselForm.speciality}"
			    alt="allowBlank:false,vtext:'请选择专业...'"  onchange="changeFacility(0);"/>			
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="counsel.equipmentType" />
		</td>
		<td class="content">
			<select name="equipmentType" id="equipmentType" 
				alt="allowBlank:true,vtext:'请选择设备类型'" >
				<option value="">
					请选择设备类型
				</option>				
			</select>			
		</td>
		<td class="label">
			<fmt:message key="counsel.equipmentName" />
		</td>
		<td class="content">
			<html:text property="equipmentName" styleId="equipmentName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${counselForm.equipmentName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="counsel.equipmentVersion" />
		</td>
		<td class="content" colspan="3">
			<html:text property="equipmentVersion" styleId="equipmentVersion"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${counselForm.equipmentVersion}" />
		</td>
		
							<tr>
						<td colspan="4">
							<div class="ui-widget-header">
								统计信息
							</div>
						</td>
					</tr>
	<tr>
		<td class="label">
			<fmt:message key="counsel.finallyTime" />
		</td>
		<td class="content">
			<html:text property="finallyTime" styleId="finallyTime"  readonly="true"
						styleClass="text medium" onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						value="${counselForm.finallyTime}" />
		</td>
	<%--返回时长，在后台已自动计算，不用要在前台输入。   modify by:huhao  time:2011-11-2      并添加咨询提出时间，referTime     begin--%>
	<%--
		<td class="label">
			<fmt:message key="counsel.finallyLong" />
		</td>
		<td class="content">
			<html:text property="finallyLong" styleId="finallyLong"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${counselForm.finallyLong}" />
		</td>
		--%>
		<td class="label">
			咨询提出时间
		</td>
		<td class="content">
			<html:text property="referTime" styleId="referTime"  readonly="true"
						styleClass="text medium" alt="allowBlank:false" onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						value="${counselForm.referTime}" />
		</td>
		<%--返回时长，在后台已自动计算，不用要在前台输入。   modify by:huhao  time:2011-11-2    并添加咨询提出时间，referTime     end--%>
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="counsel.satisfaction" />
		</td>
		<td class="content" colspan="3">
			<html:text property="satisfaction" styleId="satisfaction"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${counselForm.satisfaction}" />
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
	      <td class="label">提交审核人*</td>
			<%--   <td class="content" colspan="3"><input type="text" class="text"
				name="approvalUserName" id="approvalUserName" value="<eoms:id2nameDB beanId="tawSystemUserDao" id="${counselForm.approveUser}"/>"
				alt="allowBlank:false" readonly="readonly" /> 
				<input type="hidden"
				name="approveUser" id="approveUser" value="${counselForm.approveUser}" /> <eoms:xbox
				id="approvalUserName" dataUrl="${app}/xtree.do?method=userFromDept"
				rootId="2" rootText="用户树" valueField="approveUser"
				handler="approvalUserName" textField="approvalUserName"
				checktype="user" single="true" /></td>
			</td>
			--%>
									<td class="content" colspan="3">
							<input type="text" name="textReviewer" id="textReviewer"
								class="text" readonly="readonly" alt="allowBlank:false" />
							<input type="button" name="approvalButton" id="approvalButton"
								value="请选择审核人" class="btn" alt="allowBlank:false" />
							<input type="hidden" name="approveUser" id="approveUser" />

							<eoms:xbox id="tree1"
								dataUrl="${app}/xtree.do?method=userFromDept" rootId="1"
								rootText='用户树' valueField="approveUser" handler="approvalButton"
								textField="textReviewer" checktype="user" single="true"></eoms:xbox>
						</td>
	</tr>

</table>
</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)">
			<c:if test="${not empty counselForm.id}">
			<c:if test="${counselForm.deviceAssessApprove.state=='0'}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确定删除该信息?')){
						var url='${app}/partner/deviceAssess/counsels.do?method=remove&id=${counselForm.id}';
						location.href=url}"	/>
			</c:if>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="amount" value="1" />
<html:hidden property="total" value="1" />
<html:hidden property="sheetId" value="${counselForm.sheetId}" />
<html:hidden property="id" value="${counselForm.id}" />
<html:hidden property="id" value="${deviceAssessApprove.assessId}" />

</html:form>
<!-- 查看审批信息 -->
	<div id="history-info" class="tabContent">
		<logic:notEmpty name="dacList" scope="request">
			<display:table name="dacList" cellspacing="0"
				cellpadding="0" class="table dacList"
				id="dacList" export="false" sort="list"
				partialList="true" size="${size}">

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