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

<script type="text/javascript">
var myjs = jQuery.noConflict();
Ext.onReady(function() {
	new eoms.form.Validation( {
		form : 'mechaicalArmManagement'
	});
	Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
			var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '内容信息 ');
        	tabs.addTab('history-info', '审批信息 ');
    		tabs.activate(0);
    		
	
});
</script>


<div id="loadIndicator" class="loading-indicator">
	加载详细信息页面完毕...
</div>
<div id="info-page">
<div align="center">大型机械手管理审核页面</div>
	<div id="content-info" class="tabContent">
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					项目名称
				</td>

				<td class="content">
					${MechanicalArmList.projectName}
				</td>
				<td class="label">
					审核人
				</td>
				<td class="content">		
			<eoms:id2nameDB id="${MechanicalArmList.approvingPerson}"
							beanId="tawSystemUserDao" />
				</td>




			</tr>
			

			<tr>
				<td class="label">
					大型施工机械名称

				</td>
				<td class="content">
					${MechanicalArmList.constructionMachineryName}
				</td>

				<td class="label">
					大型施工机械施工地点
				</td>
				<td class="content">
					${MechanicalArmList.mechanicalArm_workyard}
				</td>
			</tr>
			<tr>
				<td class="label">
					大型施工机械手姓名
				</td>
				<td class="content">
					${MechanicalArmList.mechanicalArmName}
				</td>
				<td class="label">
					联系电话
				</td>
				<td class="content">
					${MechanicalArmList.contactNumber}
				</td>
			</tr>
<tr>
				<td class="label">
					填报人
				</td>
				<td class="content">
					<eoms:id2nameDB id="${MechanicalArmList.writePerson}"
						beanId="tawSystemUserDao" />
				</td>
				<td class="label">
					填报时间
				</td>

				<td class="content">
					${MechanicalArmList.writeTime}
				</td>
			<%-- 	<td class="label">
					所属地市
				</td>

				<td class="content">
					<eoms:id2nameDB id="${MechanicalArmList.belongTheLocal}"
				beanId="tawSystemAreaDao" />
				</td>--%>
			</tr>
			<tr>
				
				<td class="label">
					状态
				</td>
				<td class="content"  colspan="3">
					${MechanicalArmList.state}
				</td>
			</tr>
			<tr>
				<td class="label">
					走访情况
				</td>
				<td class="content" colspan="3">
					${MechanicalArmList.visitSituation}
				</td>
			</tr>
			<tr>
				<td class="label">
					备注
				</td>
				<td colspan="3" class="content">
					${MechanicalArmList.remarks}
				</td>
			</tr>
		</table>
		<form
		action="mechanicalArmManagement.do?method=approvingAdd&id=${MechanicalArmList.id}"
		method="post" id="mechaicalArmManagement"
		name="mechaicalArmManagement">
		<fieldset>
			<div class="ui-widget-header">信息审核</div>
				
			
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">
						审核结果*
					</td>

					<td class="content">
						<select name="approvingresult" id="approvingresult">
							<option value="1">
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
						建议
					</td>
					<td class="content">
						<textarea class="textarea max" name="suggest" id="suggest"></textarea>
					</td>
				</tr>
			</table>
		</fieldset>
		<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="确定"></html:submit>
		<html:reset styleClass="btn" property="method.reset"
			styleId="method.reset" value="取消"></html:reset>
	</form>
		
	</div>
	<div id="history-info" class="tabContent">
		<logic:notEmpty name="MechanicalArmList" scope="request">

			<table id="sheet" class="formTable">
				<tr>
					<td class="label">
						审核人
					</td>
					<td class="content">
						<eoms:id2nameDB id="${MechanicalArmList.approvingPerson}"
							beanId="tawSystemUserDao" />
					</td>
					<td class="label">
						审核结果
					</td>
					<td class="content">
						${MechanicalArmList.approvingresult}
					</td>
				</tr>
				<tr>
					<td class="label">
						审核意见
					</td>
					<td class="content" colspan="3">
						${MechanicalArmList.suggest}
					</td>
				</tr>
				<tr>
					<td class="label">
						审核时间
					</td>
					<td class="content" colspan="3">
						${MechanicalArmList.approvingtime}
					</td>
				</tr>

			</table>
		</logic:notEmpty>
		<logic:empty name="MechanicalArmList" scope="request">没有记录</logic:empty>
	</div>
	</div>
	
<%@ include file="/common/footer_eoms.jsp"%>