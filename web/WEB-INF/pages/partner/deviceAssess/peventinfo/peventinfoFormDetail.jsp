<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'peventinfoForm'});
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
    var speciality = '${peventinfoForm.speciality}';
	if(speciality!=''){
 		document.getElementById("speciality").value = speciality;
	}
}

</script>
<div id="loadIndicator" class="loading-indicator">
	加载页面完毕...
</div>
<div id="info-page">
	<div id="base-info" class="tabContent">
<html:form action="/peventinfos.do?method=save" styleId="peventinfoForm" method="post"> 
<html:hidden property="takeOf" value="1" />
<html:hidden property="proId" value="${peventinfoForm.proId}" />
<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<table class="formTable">
	<caption>
	<div class="header center">工程服务事件信息管理</div>
	</caption>
</table>
			<fieldset>
	<legend> <font color="#0072E3" ><b>事件信息</b></legend>
	<table class="formTable">
	<tr>
	<td class="label">
			省份
		</td>
		<td class="content">
			黑龙江
		</td>
		<td class="label">
			工程号
		</td>
		<td class="content">
         ${peventinfoForm.proNum}
		</td>
	</tr>
	<tr>
		<td class="label">
			工程名称
		</td>
		<td class="content">
			${peventinfoForm.proName}
		</td>

		<td class="label">
			级别
		</td>
		<td class="content">
			${peventinfoForm.proLevel}	
		</td>
</tr>
</table>
</fieldset>

			<fieldset>
	<legend> <font color="#0072E3" ><b>厂家信息</b></legend>
	<table class="formTable">
<tr>
		<td class="label">
		<fmt:message key="peventinfo.factory" />
		</td>
		<td class="content">
			${peventinfoForm.factory}	
		</td>

		<td class="label">
		<fmt:message key="peventinfo.speciality" />
		</td>
		<td class="content">
			${peventinfoForm.speciality}	
		</td>
			</tr>
			
			</table>
</fieldset>

			<fieldset>
	<legend> <font color="#0072E3" ><b>统计信息</b></legend>
	<table class="formTable">

	<tr>
		<td class="label">
			开始时间
		</td>
		<td class="content">
			${peventinfoForm.beginTime}
		</td>

		<td class="label">
			结束时间
		</td>
		<td class="content">
			${peventinfoForm.endTime}
		</td>
	</tr>

	<tr>
		<td class="label">
			满意度
		</td>
		<td class="content">
			${peventinfoForm.satisfaction}
		</td>
		
				<td class="label">
			满意度打分原因
		</td>
		<td class="content">
			${peventinfoForm.satisfactionCase}
		</td>
	</tr>

</table>
</fieldset>

</fmt:bundle>
<html:hidden property="total" value="1" />
<html:hidden property="id" value="${peventinfoForm.id}" />
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
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>