<%@ page language="java" pageEncoding="UTF-8"%>
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
	v = new eoms.form.Validation({form:'facilityinfoForm'});
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
										var equipmentType = '${facilityinfoForm.equipmentType}';
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
    var speciality = '${facilityinfoForm.speciality}';
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
		<br/>
		<div class="header center">
			<b>厂家设备问题事件信息</b>
		</div>
		<br />
		<html:form action="/facilityinfos.do?method=save"
			styleId="facilityinfoForm" method="post">

			<fmt:bundle
				basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

				<table id="sheet" class="formTable">
					<tr>
						<td colspan="4">
							<div class="ui-widget-header">
								事件信息
							</div>
						</td>
					</tr>

					<tr>
						<td class="label">
							问题名称*
						</td>
						<td class="content">
							<html:text property="issueName" styleId="issueName"
								styleClass="text medium" alt="allowBlank:false,vtext:''"
								value="${facilityinfoForm.issueName}" />
						</td>
						<td class="label">
							问题类别*
						</td>
						<td class="content">
							<eoms:comboBox name="issueType" id="issueType"
								styleClass="select max" initDicId="1121501"
								defaultValue="${facilityinfoForm.issueType}"
								alt="allowBlank:false,vtext:'请选择等级...'" />
						</td>
					</tr>
					<tr>
						<td class="label">
							级别*
						</td>
						<td class="content" colspan="3"> 
							<eoms:comboBox name="facilityLevel" id="facilityLevel"
								styleClass="select max" initDicId="1121501"
								defaultValue="${facilityinfoForm.facilityLevel}"
								alt="allowBlank:false,vtext:'请选择等级...'" />
						</td>
					</tr>

					<tr>
						<td class="label">
							发生省份*
						</td>
						<td class="content">
							<html:text property="province" styleId="province"
								styleClass="text medium" alt="allowBlank:false,vtext:''"
								value="${facilityinfoForm.province}" />
						</td>
						<td class="label">
							发生地市*
						</td>
						<td class="content">
							<html:text property="city" styleId="city"
								styleClass="text medium" alt="allowBlank:false,vtext:''"
								value="${facilityinfoForm.city}" />
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
								defaultValue="${facilityinfoForm.factory}"
								alt="allowBlank:false,vtext:'请选择厂家...'" />
						</td>
						<td class="label">
							专业*
						</td>
						<td class="content">
							<eoms:comboBox name="speciality" id="speciality"
								styleClass="select max" initDicId="11216"
								defaultValue="${facilityinfoForm.speciality}"
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
								value="${facilityinfoForm.equipmentName}" />
						</td>
					</tr>

					<tr>
						<td class="label">
							设备版本*
						</td>
						<td class="content" colspan="3">
							<html:text property="equipmentVersion" styleId="equipmentVersion"
								styleClass="text medium" alt="allowBlank:false,vtext:''"
								value="${facilityinfoForm.equipmentVersion}" />
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
							发生时间*
						</td>
						<td class="content">
							<html:text property="occurTime" styleId="occurTime"
								readonly="true" styleClass="text medium"
								onclick="popUpCalendar(this,this,null,null,null,true,-1);"
								alt="allowBlank:false,vtext:''"
								value="${facilityinfoForm.occurTime}" />
						</td>
						<td class="label">
							状态*
						</td>
						<td class="content">
							<eoms:comboBox name="state" id="state"
								styleClass="select max" initDicId="112220101"
								defaultValue="${facilityinfoForm.state}"
								alt="allowBlank:false,vtext:'请选择...'"/>
						</td>
					</tr>

					<tr>
						<td class="label" >
							解决方案*
						</td>
						<td colspan="3" >
							<eoms:attachment scope="request" idField="accessory"
								name="facilityinfoForm" property="accessory"
								 appCode="assess" alt="allowBlank:true" />
						</td>
					</tr>
					<tr>
						<td class="label">
							解决时间*
						</td>
						<td class="content" colspan="3">
							<html:text property="solveTime" styleId="solveTime"
								readonly="true" styleClass="text medium"
								onclick="popUpCalendar(this,this,null,null,null,true,-1);"
								alt="allowBlank:false,vtext:''"
								value="${facilityinfoForm.solveTime}" />
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
							<input type="text" name="textReviewer" id="textReviewer" value="<eoms:id2nameDB id="${facilityinfoForm.deviceAssessApprove.approveUser }" beanId="tawSystemUserDao" />"
								class="text" readonly="readonly" alt="allowBlank:false" />
							<input type="button" name="approvalButton" id="approvalButton"
								value="请选择审核人" class="btn" alt="allowBlank:false" />
							<input type="hidden" name="approvalUser" id="approvalUser"  value="${facilityinfoForm.deviceAssessApprove.approveUser }"/>

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
						<input type="submit" class="btn" value="保存" />
						<input type="reset" class="btn" value="重置"
							onclick="javascript :history.back(-1)">
					</td>
				</tr>
			</table>
			<html:hidden property="id" value="${facilityinfoForm.id}" />
			<html:hidden property="total" value="1" />
		</html:form>
	</div>

	<!-- 查看审批信息 -->
	<div id="history-info" class="tabContent">
		<logic:notEmpty name="dacList" scope="request">
			<display:table name="dacList" cellspacing="0" cellpadding="0"
				class="table dacList" id="dacList" export="false" sort="list"
				partialList="true" size="${size}">

				<display:column headerClass="sortable" title="问题名称">
					<eoms:id2nameDB id="${dacList.assessId}" beanId="facilityinfoDao" />
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
		<logic:empty name="dacList" scope="request">
			没有记录!
		</logic:empty>
	</div>
</div>


<%@ include file="/common/footer_eoms.jsp"%>