<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript">
Ext.onReady(function() {
	v1 =new eoms.form.Validation({form:'operateForm'});
     });
</script>
<div class="header center">
	<b>变更申请操作
</div>
<br>
<div>
	<table class="formTable">
		<tr>
				<td colspan="4">
								<div class="ui-widget-header">
								<b>变更申请详情
								</div>
				</td>
		</tr>
		<tr>
				<td class="label">
					申请人员&nbsp;*
				</td>
				<td class="content">
					${ppMain.createUser}
				</td>
				<td class="label">
					申请时间&nbsp;*
				</td>
				<td class="content">
					${ppMain.startTime}
				</td>
		</tr>
		<tr>
				<td class="label">
					变更模块&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB id="${ppMain.referenceModel}" beanId="ItawSystemDictTypeDao" />
				</td>
				<td class="label">
					变更类型&nbsp;*
				</td>
				<td class="content">
				<eoms:id2nameDB id="${ppMain.changeType}" beanId="ItawSystemDictTypeDao" />
				</td>
		</tr>
		<tr>
				<td class="label">
					申请附件&nbsp;*
				</td>
				<td class="content" colspan="3"><%--
					${ppMain.changeAttachment} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					--%><input type="button" value="下载附件" id="dbtn" onclick="dow('${ppMain.id}')">
				</td>
		</tr>
	</table>
</div><br>
<div >
<form action="process.do?method=applyOperate" method="post" id="operateForm" name="operateForm">
	<table class="formTable">
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					<b>变更申请操作
				</div>
			</td>
		</tr>
  		<tr>
  			<td class="label">
					申请处理&nbsp;*
			</td>
			<td  colspan="3">
					<eoms:comboBox name="operateVal" id="operateVal" 	initDicId="1230503" styleClass="text medium" 
					alt="allowBlank:false" 	/>
			</td>
  		</tr>
  		<tr >
  			<td class="label">
					处理意见&nbsp;
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="reason"	id="reason" ></textarea>
			</td>
  		</tr>
	</table>
	<input type="hidden" name="idMain" value="${ppMain.id}">
	<input type="hidden" name="createUser" value="${ppMain.createUser}">
	<input type="submit" value="提交">
	<input type="reset" value="重置">
	<input type="button" value="返回" onclick="goBack()">
</form>
</div>
<script type="text/javascript">
	function goBack(){
		window.location.href="${app}/partner/process/process.do?method=goToApplyOperatePage";
	}
	function dow(id){
		window.location.href="${app}/partner/process/process.do?method=applyFileDownload&idMain="+id;
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
