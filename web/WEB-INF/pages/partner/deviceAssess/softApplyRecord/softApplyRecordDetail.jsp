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
</script>

<div id="loadIndicator" class="loading-indicator">
	加载页面完毕...
</div>

<div id="info-page">
	<div id="base-info" class="tabContent">
				<table id="sheet" class="formTable">
					<caption>
						<div class="header center">
							联通内部处理的设备故障事件信息
						</div>
					</caption>

					<tr>
						<td colspan="4">
							<div class="ui-widget-header">
								事件信息
							</div>
						</td>
					</tr>

					<tr>
						<td class="label">
							升级事件名称*
						</td>
						<td class="content">
						 ${softApplyRecord.affairName} 
						</td>
						<td class="label">
							升级申请时间*
						</td>
						<td class="content">
						  ${softApplyRecord.createTime} 
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
							<eoms:id2nameDB id="${softApplyRecord.factory}" beanId="IItawSystemDictTypeDao" /> 
						</td>
						<td class="label">
							专业*
						</td>
						<td class="content">
							<eoms:id2nameDB id="${softApplyRecord.speciality}" beanId="IItawSystemDictTypeDao" /> 
						</td>
					</tr>

					<tr>
						<td class="label">
							设备类型*
						</td>
						<td class="content" colspan="3">
							<eoms:id2nameDB id="${softApplyRecord.equipmentType}" beanId="IItawSystemDictTypeDao" /> 
						</td>
					</tr>
					
					<tr>
						<td colspan="4">
							<div class="ui-widget-header">
								其它信息
							</div>
						</td>
					</tr>
					
					<tr>
						<td class="label">
							许可证问题*
						</td>
						<td class="content" colspan="3">
								<textarea class="textarea max" readonly="readonly"	
										name="licenseProblem" id="licenseProblem" alt="allowBlank:false"
											value="${softApplyRecord.licenseProblem}" >
								 ${softApplyRecord.licenseProblem}
								</textarea>
						</td>
					</tr>
					
					<tr>
						<td class="label">
							方案问题*
						</td>
						<td class="content" colspan="3">
								<textarea class="textarea max" readonly="readonly"
											name="schemeProblem" id="schemeProblem" alt="allowBlank:false"
											value="${softApplyRecord.schemeProblem}" >
								${softApplyRecord.schemeProblem}
								</textarea>
						</td>
					</tr>
					
					<tr>
						<td class="label">
							软件或补丁功能描述错误*
						</td>
						<td class="content"  colspan="3">
								<textarea class="textarea max" readonly="readonly"
											name="describeErrors" id="describeErrors" alt="allowBlank:false"
											value="${softApplyRecord.describeErrors}" >
								${softApplyRecord.describeErrors}
								</textarea>
						</td>
					</tr>
					
					<tr>
						<td class="label">
							其他漏报项目*
						</td>
						<td class="content" colspan="3">
								<textarea class="textarea max" readonly="readonly"
											name="otherOmissionProject" id="otherOmissionProject" alt="allowBlank:false"
											value="${softApplyRecord.otherOmissionProject}" >
								${softApplyRecord.otherOmissionProject}
								</textarea>
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
							审批人*
						</td>
						<td class="content" colspan="3">
							<eoms:id2nameDB id="${softApplyRecord.deviceAssessApprove.approveUser}" beanId="tawSystemUserDao" />
						</td>
					</tr>
				</table>
	</div>

	<!-- 查看审批信息 -->
	<div id="history-info" class="tabContent">
		<logic:notEmpty name="dacList" scope="request">
			<display:table name="dacList" cellspacing="0"
				cellpadding="0" class="table dacList"
				id="dacList" export="false" sort="list"
				partialList="true" size="${size}">
	
				<display:column headerClass="sortable" title="事件名">
					<eoms:id2nameDB id="${dacList.assessId}"
						beanId="insideDisposeDao" />
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