<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'peventinfoForm'});
});
window.onload = function(){
    var speciality = '${peventinfoForm.speciality}';
	if(speciality!=''){
 		document.getElementById("speciality").value = speciality;
	}
}

</script>

<html:form action="/peventinfos.do?method=save" styleId="peventinfoForm" method="post"> 
<html:hidden property="takeOf" value="1" />
<html:hidden property="proId" value="${peventinfoForm.proId}" />
<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<table id="sheet" class="formTable">
	<div class="header center">
	<b>咨询服务事件信息表</b>
	</div>
		<br />
					<tr>
						<td colspan="4">
							<div class="ui-widget-header">
								工单信息
							</div>
						</td>
					</tr>
	<tr>
	<td class="label">
			省份
		</td>
		<td class="content">
			<html:text property="area" styleId="area"
						styleClass="text"
						alt="allowBlank:false,vtext:''" value="黑龙江" readonly="true" />
		</td>
		<td class="label">
			工程号
		</td>
		<td class="content">
			<html:text property="proNum" styleId="proNum"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${peventinfoForm.proNum}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			工程名称
		</td>
		<td class="content">
			<html:text property="proName" styleId="proName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${peventinfoForm.proName}" />
		</td>

		<td class="label">
			级别
		</td>
		<td class="content">
			<eoms:comboBox name="proLevel" id="proLevel" initDicId="1121501" defaultValue="${peventinfoForm.proLevel}"
			    alt="allowBlank:false,vtext:'请选择等级...'"/>		
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
		<fmt:message key="peventinfo.factory" />
		</td>
		<td class="content">
			<eoms:comboBox name="factory" id="factory" initDicId="1121502" defaultValue="${peventinfoForm.factory}"
			    alt="allowBlank:false,vtext:'请选择厂家...'"/>		
		</td>

		<td class="label">
		<fmt:message key="peventinfo.speciality" />
		</td>
		<td class="content">
			<eoms:comboBox name="speciality" id="speciality" initDicId="11216" defaultValue="${peventinfoForm.speciality}"
			    alt="allowBlank:false,vtext:'请选择专业...'" />			
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
			开始时间
		</td>
		<td class="content">
			<html:text property="beginTime" styleId="beginTime"
						styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="allowBlank:false,vtext:''" value="${peventinfoForm.beginTime}" readonly="true"/>
		</td>

		<td class="label">
			结束时间
		</td>
		<td class="content">
			<html:text property="endTime" styleId="endTime"
						styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="allowBlank:false,vtext:''" value="${peventinfoForm.endTime}"  readonly="true"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			满意度
		</td>
		<td class="content">
			<html:text property="satisfaction" styleId="satisfaction"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${peventinfoForm.satisfaction}" />
		</td>
		
				<td class="label">
			满意度打分原因
		</td>
		<td class="content">
			<html:text property="satisfactionCase" styleId="satisfactionCase"
						styleClass="text"
						alt="allowBlank:false" value="${peventinfoForm.satisfactionCase}" />
		</td>
	</tr>
	<tr>
						<td colspan="4">
							<div class="ui-widget-header">
								审批信息
							</div>
						</td>
					</tr>
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
</fieldset>

</fmt:bundle>

<table>
	<tr>
		<td>
				<input type="submit" class="btn"
					value="<fmt:message key="button.save"/>" />
					<input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)">
				<c:if test="${not empty peventinfoForm.id}">
				<c:if test="${peventinfoForm.deviceAssessApprove.state=='0'}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('是否删除?')){
						var url='${app}/partner/deviceAssess/peventinfos.do?method=remove&id=${peventinfoForm.id}';
						location.href=url}"	/>
			</c:if>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="total" value="1" />
<html:hidden property="id" value="${peventinfoForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>