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

<div id="loadIndicator" class="loading-indicator">
	加载页面完毕...
</div>
<div id="info-page">
	<div id="base-info" class="tabContent">
	
<html:form action="/counsels.do?method=save" styleId="counselForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="counsel.form.heading"/></div>
	</caption>
	</table>

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
			${counselForm.sheetNum}
		</td>
		<td class="label">
			<fmt:message key="counsel.createTime" />
		</td>
		<td class="content">
          ${counselForm.createTime}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="counsel.changeTime" />
		</td>
		<td class="content">
             ${counselForm.changeTime}
		</td>
		<td class="label">
			<fmt:message key="counsel.pigeonholeTime" />
		</td>
		<td class="content">
			${counselForm.pigeonholeTime}
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
			${counselForm.affairName}
		</td>
		<td class="label">
			<fmt:message key="counsel.affairLevel" />
		</td>
		<td class="content">
			${counselForm.affairLevel}		
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="counsel.province" />
		</td>
		<td class="content">
			${counselForm.province}
		</td>
		<td class="label">
			<fmt:message key="counsel.city" />
		</td>
		<td class="content">
			${counselForm.city}
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
			${counselForm.factory}	
		</td>
		<td class="label">
			<fmt:message key="counsel.speciality" />
		</td>
		<td class="content">
			${counselForm.speciality}	
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="counsel.equipmentType" />
		</td>
		<td class="content">
					${counselForm.equipmentType}							
		</td>
		<td class="label">
			<fmt:message key="counsel.equipmentName" />
		</td>
		<td class="content">
			${counselForm.equipmentName}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="counsel.equipmentVersion" />
		</td>
		<td class="content" colspan="3">
			${counselForm.equipmentVersion}
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
			${counselForm.finallyTime}
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
			${counselForm.referTime}
		</td>
		<%--返回时长，在后台已自动计算，不用要在前台输入。   modify by:huhao  time:2011-11-2    并添加咨询提出时间，referTime     end--%>
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="counsel.satisfaction" />
		</td>
		<td class="content" colspan="3">
			${counselForm.satisfaction}
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
			<td class="content" colspan="3">
			<eoms:id2nameDB beanId="tawSystemUserDao" id="${counselForm.approveUser}"/>
			</td>
	</tr>

</table>
</fieldset>
</fmt:bundle>


<html:hidden property="amount" value="1" />
<html:hidden property="total" value="1" />
<html:hidden property="sheetId" value="${counselForm.sheetId}" />
<html:hidden property="id" value="${counselForm.id}" />
</html:form>
<table>
	<tr>
		<td>
					<input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)">
		</td>
	</tr>
</table>
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