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
							厂家设备问题事件信息
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
							问题名称*
						</td>
						<td class="content">
							${facilityinfo.issueName} 
						</td>
						<td class="label">
							问题类别*
						</td>
						<td class="content">
							 <eoms:id2nameDB id="${facilityinfo.issueType}" beanId="IItawSystemDictTypeDao" /> 
						</td>
					</tr>
					<tr>
						<td class="label">
							级别*
						</td>
						<td class="content" colspan="3"> 
							 <eoms:id2nameDB id=" ${facilityinfo.facilityLevel}" beanId="IItawSystemDictTypeDao" /> 
						</td>
					</tr>

					<tr>
						<td class="label">
							发生省份*
						</td>
						<td class="content">
							${facilityinfo.province}
						</td>
						<td class="label">
							发生地市*
						</td>
						<td class="content">
							${facilityinfo.city}
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
							<eoms:id2nameDB id="${facilityinfo.factory}" beanId="IItawSystemDictTypeDao" />
						</td>
						<td class="label">
							专业*
						</td>
						<td class="content">
							<eoms:id2nameDB id="${facilityinfo.speciality}" beanId="IItawSystemDictTypeDao" />
						</td>
					</tr>

					<tr>
						<td class="label">
							设备类型*
						</td>
						<td class="content">
							<eoms:id2nameDB id="${facilityinfo.equipmentType}" beanId="IItawSystemDictTypeDao" />
						</td>
						<td class="label">
							设备名称*
						</td>
						<td class="content">
							${facilityinfo.equipmentName}
						</td>
					</tr>

					<tr>
						<td class="label">
							设备版本*
						</td>
						<td class="content" colspan="3">
							${facilityinfo.equipmentVersion}
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
							 ${facilityinfo.occurTime} 
						</td>
						<td class="label">
							状态*
						</td>
						<td class="content">
							<eoms:id2nameDB id="${facilityinfo.state}" beanId="IItawSystemDictTypeDao" />
						</td>
					</tr>

					<tr>
						<td class="label">
							解决方案*
						</td>
						<td class="content" colspan="3">
							<c:if test="${empty facilityinfo.accessory}">
								没有方案附件!
							</c:if>
							<c:if test="${not empty facilityinfo.accessory}">
								<eoms:download ids="${facilityinfo.accessory}"></eoms:download><br/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="label">
							解决时间*
						</td>
						<td class="content" colspan="3">
							 ${facilityinfo.solveTime} 
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
							<eoms:id2nameDB id="${facilityinfo.deviceAssessApprove.approveUser}" beanId="tawSystemUserDao" />
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
	
				<display:column headerClass="sortable" title="问题名称">
					<eoms:id2nameDB id="${dacList.assessId}"
						beanId="facilityinfoDao" />
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