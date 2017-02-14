<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>


<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'lserveinfoForm'});
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
	
window.onload = function(){
    var speciality = '${lserveinfoForm.speciality}';
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
	url : "${app}/partner/deviceAssess/lserveinfos.do?method=importData",
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
			<b>现场服务事件信息</b>
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
			<form action="insideDisposes.do?method=importData" method="post"
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
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
		</div>
		<br />	
<html:form action="/lserveinfos.do?method=save" styleId="lserveinfoForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">
<html:hidden property="takeCountOf" value="1" />
<html:hidden property="total" value="1" />
<html:hidden property="sheetId" value="${lserveinfoForm.sheetId}" />
<table class="formTable">
	
<tr>
 <td colspan="4">
 <div class="ui-widget-header">工单信息</div>
 </td>
 </tr>

	<tr>
		<td class="label">
			工单号*
		</td>
		<td class="content">
			<html:text property="sheetNum" styleId="sheetNum"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${lserveinfoForm.sheetNum}" />
		</td>

		<td class="label">
			建单时间*
		</td>
		<td class="content">
			<html:text property="createTime" styleId="createTime"
						styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="vtype:'lessThen',link:'pigeonholeTime',vtext:'建单时间不能晚于归档时间',allowBlank:false"
						value="${lserveinfoForm.createTime}" readonly="true"/>
		</td>
	</tr>

	<tr>
		<td class="label">
		转派厂家时间*
		</td>
		<td class="content">
			<html:text property="turnFactoryTime" styleId="turnFactoryTime"
						styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="allowBlank:false,vtext:''" value="${lserveinfoForm.turnFactoryTime}"  readonly="true"/>
		</td>

		<td class="label">
		归档时间*
		</td>
		<td class="content">
			<html:text property="pigeonholeTime" styleId="pigeonholeTime"
						styleClass="text medium"	
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="vtype:'moreThen',link:'createTime',vtext:'归档时间不能早于建单时间',allowBlank:false"
						value="${lserveinfoForm.pigeonholeTime}" readonly="true" />
		</td>
	</tr>
<tr>
 <td colspan="4">
 <div class="ui-widget-header">事件信息</div>
 </td>
 </tr>
	<tr>
		<td class="label">
		事件名称*
		</td>
		<td class="content">
			<html:text property="affairName" styleId="affairName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${lserveinfoForm.affairName}" />
		</td>

		<td class="label">
			级别*
		</td>
		<td class="content">
			<eoms:comboBox name="affairLevel" id="affairLevel" initDicId="1121501" defaultValue="${lserveinfoForm.affairLevel}"
			    alt="allowBlank:false,vtext:'请选择等级...'"/>			
		</td>
	</tr>

	<tr>
		<td class="label">
			发生省份*
		</td>
		<td class="content">
			<html:text property="province" styleId="province"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${lserveinfoForm.province}" />
		</td>

		<td class="label">
		发生地市*
		</td>
		<td class="content">
			<html:text property="city" styleId="city"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${lserveinfoForm.city}" />
		</td>
	</tr>
<tr>
 <td colspan="4">
 <div class="ui-widget-header">厂家信息</div>
 </td>
 </tr>
	<tr>
		<td class="label">
			厂家*
		</td>
		<td class="content">
			<eoms:comboBox name="factory" id="factory" initDicId="1121502" defaultValue="${lserveinfoForm.factory}"
			    alt="allowBlank:false,vtext:'请选择厂家...'"/>		
		</td>

		<td class="label">
			专业*
		</td>
		<td class="content">
		
			<eoms:comboBox name="speciality" id="speciality" initDicId="11216" defaultValue="${lserveinfoForm.speciality}"
			 sub="deviceType" onchange="change()"  alt="allowBlank:false,vtext:'请选择专业...'"/>			
		</td>
	</tr>
	<tr>
	
		<td class="label">
			设备类型*
		</td>
		<td class="content">
			<eoms:comboBox name="deviceType" id="deviceType" initDicId="speciality" defaultValue="${lserveinfoForm.deviceType}"
			    alt="allowBlank:false,vtext:'请选择专业...'"/>			
		</td>
	</tr>
<tr>
 <td colspan="4">
 <div class="ui-widget-header">统计信息</div>
 </td>
 </tr>
	<tr>
		<td class="label">
			服务人天数
		</td>
		<td class="content">
			<html:text property="servePopu" styleId="servePopu"
						styleClass="text medium" 
						alt="vtype:'number',vtext:'格式不正确，人数只能为非负整数',allowBlank:false"
						value="${lserveinfoForm.servePopu}" />
		</td>

		<td class="label">
			满意度
		</td>
		<td class="content">
			<html:text property="satisfaction" styleId="satisfaction"
						styleClass="text medium"
						alt="re:/^([1]{1}[0]{1}[0]{1}?)$|^([(0-9)]{1,2}?)$/,re_vt:'请输入小于等于100的正整数',allowBlank:false,maxLength:5"
						value="${lserveinfoForm.satisfaction}" />
		</td>
	</tr>
</tr>
        <tr>
			<td class="label">
			满意度打分原因
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="satisfactionReason"
					id="satisfactionReason" alt="width:500,allowBlank:true">${lserveinfoForm.satisfactionReason}</textarea>
			
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
							<input type="text" name="textReviewer" id="textReviewer"
							value="<eoms:id2nameDB id="${lserveinfoForm.deviceAssessApprove.approveUser}" beanId="tawSystemUserDao" />"
								class="text" readonly="readonly" alt="allowBlank:false" />
							<input type="button" name="approvalButton" id="approvalButton"
								value="请选择审核人" class="btn" alt="allowBlank:false" />
							<input type="hidden" name="approvalUser" id="approvalUser" value="${lserveinfoForm.deviceAssessApprove.approveUser}"/>

							<eoms:xbox id="tree1"
								dataUrl="${app}/xtree.do?method=userFromDept" rootId="1"
								rootText='用户树' valueField="approvalUser" handler="approvalButton"
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
			<c:if test="${not empty lserveinfoForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('是否删除?')){
						var url='${app}/partner/deviceAssess/lserveinfos.do?method=remove&id=${lserveinfoForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${lserveinfoForm.id}" />
</html:form>
</div>
<div id="history-info" class="tabContent">
		<logic:notEmpty name="dacList" scope="request">
			<display:table name="dacList" cellspacing="0" cellpadding="0"
				class="table dacList" id="dacList" export="false" sort="list"
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
		<logic:empty name="dacList" scope="request">
			没有记录!
		</logic:empty>
	</div>
	</div>
<%@ include file="/common/footer_eoms.jsp"%>