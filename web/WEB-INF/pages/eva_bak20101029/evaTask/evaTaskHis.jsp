<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.eva.util.EvaConstants"/>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.eva.util.DateTimeUtil"%>
<%@ page language="java" import="com.boco.eoms.eva.model.EvaTree"%>
<%@ page language="java" import="com.boco.eoms.eva.model.EvaKpiInstance"%>
<%@ page language="java" import="com.boco.eoms.base.util.StaticMethod"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('undo-info', '模板执行信息 ');
        	tabs.addTab('done-info', '已完成考核信息 ');
    		tabs.activate(0);
	});
</script>
<div id="info-page">
  <div id="undo-info" class="tabContent">
<table class="formTable" id="form">

	<tr>
		<td class="label" width="25%">
			模板名称
		</td>
		<td class="content" colspan="3" width="75%">
			${template.templateName}
		</td>
	</tr>
	<tr>
		<td class="label">
			创建人
		</td>
		<td class="content">
			<eoms:id2nameDB id="${template.creator}" beanId="tawSystemUserDao" />
		</td>
		
		<td class="label">
			创建时间
		</td>
		<td class="content">
			${template.createTime}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			模板状态
		</td>
		<td class="content">
			<eoms:dict key="dict-eva" dictId="activated" itemId="${template.activated}" beanId="id2nameXML" />
		</td>
		<td class="label">
			关联协议
		</td>
		<td class="content">
		<a href="${app}/partner/agreement/pnrAgreementMains.do?method=detail&id=${template.agreementId}" target="_blank">
		<eoms:id2nameDB id="${template.agreementId}" beanId="pnrAgreementMainDao" />
		</a>						
		</td>
	</tr>
	<tr>
		<td class="label">
			开始时间
		</td>
		<td class="content">
			${template.startTime}
		</td>
		
		<td class="label">
			结束时间
		</td>
		<td class="content">
			${template.endTime}
		</td>
	</tr>
	<tr>
		<td class="label" width="25%">
			所属专业
		</td>
		<td class="content" width="25%">
			${template.professional}
		</td>
		<td class="label">
			考核周期
		</td>
		<td>
		<eoms:dict key="dict-eva" dictId="cycle" itemId="${template.cycle}" beanId="id2nameXML" />
		</td>
	</tr>
	<tr>
		<td class="label">
			执行者
		</td>
		<td class="content">
			<eoms:id2nameDB id="${template.orgId}" beanId="tawSystemDeptDao"/>
		</td>
		<td class="label">
			被考核公司
		</td>
		<td class="content">
			<eoms:id2nameDB id="${template.partnerDept}" beanId="tawSystemDeptDao"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			${template.remark}
		</td>
	</tr>
	<c:if test="${template.activated!=2}">
	<tr>
		<td class="label">
			待考核任务
		</td>
		<td colspan="3">
			<c:forEach var="report" items="${reportList}" varStatus="stauts">	
			<c:if test="${report.isPublish=='0' }">
					<input type="button" name="excuteTask" id="excuteTask" value="${report.showTimeStr }" class="btn" onclick="excuteTask('${report.taskId }','${report.time }','${report.id }')" />
			</c:if>
			</c:forEach>
		</td>
	</tr>
	</c:if>
</table>
  </div>
  <div id="done-info" class="tabContent">

  <table class="formTable" id="form">

	<tr>
	<td class="label">
			已完成考核
		</td>
	<td colspan="3">
	<c:forEach var="report" items="${reportList}">
	
	<c:if test="${report.isPublish=='1' }">
			<input type="button" name="excuteTask" id="excuteTask" value="${report.showTimeStr }" class="btn" onclick="excuteTask('${report.taskId }','${report.time }','${report.id }')" disable='ture'/>
	</c:if>
	</c:forEach>
	</td>
	</tr>
	</table>
  </div>
<script type="text/javascript">
function excuteTask(taskId,time,reportId) {
		var url=eoms.appPath+'/eva/evaTasks.do?method=getTaskDetailList&taskId='+taskId+'&reportId='+reportId+'&timeStr='+time+'&partnerId=${template.partnerDept}';
		window.open(url);
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>