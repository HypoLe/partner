<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript">
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


<table class="formTable">
	<caption>
		<div class="header center">软件升级事件信息</div>
	</caption>
	 <tr>
        	<td colspan="4">
							<div class="ui-widget-header">
								工单信息
							</div>
						</td>
					</tr>
	<tr>
		<td class="label">
			工单号
		</td>
		<td class="content">
			${softupinfo.sheetNum}
		</td>

		<td class="label">
			建单时间
		</td>
		<td class="content">
			${softupinfo.createTime}					
		</td>
	</tr>

	<tr>
		<td class="label">
			归档时间
		</td>
		<td class="content">
		${softupinfo.pigeonholeTime}
						
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
			事件名称
		</td>
		<td class="content">
			${softupinfo.affairName}
		</td>
		<td class="label">
			级别
		</td>
		<td class="content">
		<eoms:id2nameDB beanId="IItawSystemDictTypeDao" id="${softupinfo.affairLevel}"/>	
		</td>
		
		
	</tr>

	<tr>
		
		<td class="label">
			发生省份
		</td>
		<td class="content">
		${softupinfo.province}
		</td>
		
		<td class="label">
			发生地市
		</td>
		<td class="content">
		${softupinfo.city}
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
		<eoms:id2nameDB beanId="IItawSystemDictTypeDao" id="${softupinfo.factory}"/>		
		</td>
		<td class="label">
			专业
		</td>
		<td class="content">
		<eoms:id2nameDB beanId="IItawSystemDictTypeDao" id="${softupinfo.speciality}"/>			
		</td>
	</tr>

	<tr>
		
		<td class="label">
			设备类型
		</td>
		<td class="content">
		<eoms:id2nameDB beanId="IItawSystemDictTypeDao" id="${softupinfo.equipmentType}"/>	
		</td>
		<td class="label">
			设备名称
		</td>
		<td class="content">
	         ${softupinfo.equipmentName}
		</td>
	</tr>

	<tr>
		
		<td class="label">
			当前版本
		</td>
		<td class="content">
			${softupinfo.nonceEdition}
		</td>
		<td class="label">
			升级版本
		</td>
		<td class="content">
			${softupinfo.updateEdition}
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
			第一次未成功的升级时间
		</td>
		<td class="content">
			${softupinfo.firstUnsucceeTime}
		</td>

		<td class="label">
			最终升级时间
		</td>
		<td class="content">
		${softupinfo.finalUpTime}			
		</td>
	</tr>

	<tr>
		<td class="label">
			最终结果
		</td>
		<td class="content">
		${softupinfo.finalResult}				
		</td>
	<td class="label">
				附件	
			</td>
			<td class="content" >		
	<c:if test="${empty softupinfo.accessory}">
					没有附件!
				</c:if>
				<c:if test="${not empty softupinfo.accessory}">
					<eoms:download ids="${softupinfo.accessory}"></eoms:download>
				</c:if>
				</td>
	</tr>
	
	<tr>
		<td class="label">
			未成功的原因
		</td>
		<td class="content" colspan="3" >
		${softupinfo.unsucceeReason}				
		</td>
	</tr>
	<tr>
		<td class="label">
			后续升级方案
		</td>
		<td class="content" colspan="3">
		${softupinfo.extendProject}				
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
							审批人
						</td>
						<td class="content" colspan="3">
							<eoms:id2nameDB id="${softupinfo.deviceAssessApprove.approveUser}" beanId="tawSystemUserDao" />
						</td>
					</tr>			
</table>
</div>


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
		<logic:empty name="dacList" scope="request">
			没有记录!
		</logic:empty>
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>