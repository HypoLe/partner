<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">


<script type="text/javascript">

Ext.onReady(function() {
	v = new eoms.form.Validation({form:'softupinfoForm'});
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
										var equipmentType = '${softupinfoForm.equipmentType}';
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
    var speciality = '${softupinfoForm.speciality}';
	if(speciality!=''){
 		document.getElementById("speciality").value = speciality;
		changeFacility(1);
	}
}

</script>

<div id="loadIndicator" class="loading-indicator">
	加载页面完毕...
</div>

<div id="info-page">
	<div id="base-info" >
		<div class="header center">
			<b>软件升级事件信息</b>
		</div>
	
		
		


<html:form action="/softupinfos.do?method=save" styleId="softupinfoForm" method="post">

    <table class="formTable"> 
<html:hidden property="takeCountOf" value="1" />
<html:hidden property="sheetId" value="${softupinfoForm.sheetId}" />
<html:hidden property="total" value="1" />


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
						alt="allowBlank:false,vtext:''" value="${softupinfoForm.sheetNum}" />
		</td>

		<td class="label">
			建单时间*
		</td>
		<td class="content">
			<html:text property="createTime" styleId="createTime"
						styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="vtype:'lessThen',link:'pigeonholeTime',vtext:'建单时间不能晚于归档时间',allowBlank:false"
						value="${softupinfoForm.createTime}"
						readonly="true" />
		</td>
	</tr>

	<tr>
		<td class="label">
			归档时间*
		</td>
		<td class="content">
			<html:text property="pigeonholeTime" styleId="pigeonholeTime"
						styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="vtype:'moreThen',link:'createTime',vtext:'归档时间不能早于建单时间',allowBlank:false"
						value="${softupinfoForm.pigeonholeTime}"
						readonly="true" />
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
						alt="allowBlank:false,vtext:''" value="${softupinfoForm.affairName}" />
		</td>
    
    <td class="label">
			级别*
		</td>
		<td class="content">
			<eoms:comboBox name="affairLevel" id="affairLevel" initDicId="1121501" defaultValue="${softupinfoForm.affairLevel}"
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
						alt="allowBlank:false,vtext:''" value="${softupinfoForm.province}" />
		</td>

		<td class="label">
			发生地市*
		</td>
		<td class="content">
			<html:text property="city" styleId="city"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${softupinfoForm.city}" />
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
			<eoms:comboBox name="factory" id="factory" initDicId="1121502" defaultValue="${softupinfoForm.factory}"
			    alt="allowBlank:false,vtext:'请选择厂家...'"/>			
		</td>

		<td class="label">
			专业*
		</td>
		<td class="content">
			<eoms:comboBox name="speciality" id="speciality" initDicId="11216" defaultValue="${softupinfoForm.speciality}"
			    alt="allowBlank:false,vtext:'请选择专业...'"  onchange="changeFacility(0);"/>			
		</td>
	</tr>

	<tr>
		<td class="label">
			设备类型*
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
		设备名称*
		</td>
		<td class="content">
			<html:text property="equipmentName" styleId="equipmentName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${softupinfoForm.equipmentName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			当前版本*
		</td>
		<td class="content">
			<html:text property="nonceEdition" styleId="nonceEdition"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${softupinfoForm.nonceEdition}" />
		</td>

		<td class="label">
			升级版本*
		</td>
		<td class="content">
			<html:text property="updateEdition" styleId="updateEdition"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${softupinfoForm.updateEdition}" />
		</td>
	</tr>

 <tr>
 <td colspan="4">
 <div class="ui-widget-header">统计信息</div>
 </td>
 </tr>
	<tr>
		<td class="label">
			第一次未成功的升级时间*
		</td>
		<td class="content">
			<html:text property="firstUnsucceeTime" styleId="firstUnsucceeTime"
						styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="vtype:'lessThen',link:'finalUpTime',vtext:'第一次未成功的升级时间不能晚于最终升级时间',allowBlank:false"
						value="${softupinfoForm.firstUnsucceeTime}"
						readonly="true" />
		</td>

		<td class="label">
			最终升级时间*
		</td>
		<td class="content">
			<html:text property="finalUpTime" styleId="finalUpTime"
						styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="vtype:'moreThen',link:'firstUnsucceeTime',vtext:'最终升级时间不能早于第一次未成功的升级时间',allowBlank:false"
						value="${softupinfoForm.finalUpTime}"
						readonly="true" />
		</td>
	</tr>

	<tr>
		
		<td class="label">
			最终结果*
		</td>
		<td class="content">
			<html:text property="finalResult" styleId="finalResult"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${softupinfoForm.finalResult}" />
		</td>	
	</tr>
        <tr>
			<td class="label">
			未成功的原因
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="unsucceeReason"
					id="unsucceeReason" alt="width:500,allowBlank:true">${softupinfoForm.unsucceeReason}</textarea>
			
			</td>
				</tr>
			<tr>
			<td class="label">
				后续升级方案
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="extendProject"
					id="extendProject" alt="allowBlank:true">${softupinfoForm.extendProject}</textarea>
			</td>
		</tr>
		
		<tr>
			<td class="label">
				附件
				
			</td>
			
			<td colspan="3">
		
				<eoms:attachment scope="request" idField="accessory" 
					name="accessory" appCode="charge" alt="allowBlank:true" />
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
							<input type="text" name="textReviewer" id="textReviewer"  value="<eoms:id2nameDB id="${softupinfoForm.deviceAssessApprove.approveUser}" beanId="tawSystemUserDao" />"
								class="text" readonly="readonly" alt="allowBlank:false" />
							<input type="button" name="approvalButton" id="approvalButton"
								value="请选择审核人" class="btn" alt="allowBlank:false" />
							<input type="hidden" name="approvalUser" id="approvalUser"  value="${softupinfoForm.deviceAssessApprove.approveUser}"/>

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
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)">
			<c:if test="${not empty softupinfoForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('是否删除?')){
						var url='${app}/partner/deviceAssess/softupinfos.do?method=remove&id=${softupinfoForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${softupinfoForm.id}" />
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