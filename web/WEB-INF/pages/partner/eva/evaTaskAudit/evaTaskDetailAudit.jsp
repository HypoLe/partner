<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	var readTree;
	v = new eoms.form.Validation({form:'pnrEvaTaskAuditForm'});
	var tabs = new Ext.TabPanel('info-page');
    var tplTab = tabs.addTab('task-audit', '指标任务考核 ');
	tabs.activate(0);
});
</script>
 <div id="info-page">
  <!-- 模板面板 -->
  <div id="task-audit" class="tabContent">
<html:form action="/evaTaskAudit.do?method=doAudit" styleId="pnrEvaTaskAuditForm" method="post">
<table class="listTable" id="list-table">
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-合作伙伴(<eoms:id2nameDB id="${requestScope.partner}" beanId="areaDeptTreeDao" />)-周期(${requestScope.timeType})-时间(${requestScope.time}) 考核执行列表
		</div>
	</caption>
	<thead>
		<tr>
		<td colspan="${requestScope.maxLevel}">
			
		</td>
		<td>
			评分部门：<eoms:id2nameDB id="${requestScope.assessDeptId}" beanId="tawSystemDeptDao"/>
		</td>
		<td>
			评分人：<eoms:id2nameDB id="${requestScope.assessUserId}" beanId="tawSystemUserDao"/>
		</td>
		<td colspan="2">
			最终得分:<span id="lastScore">${requestScope.allScore}</span>
		</td>

	</tr>
	<tr>
		<td colspan="${requestScope.maxLevel}">
			考核指标
		</td>
		<td>
			算法描述
		</td>
		<td>
			实际扣分
		</td>
		<td>
			扣分原因
		</td>
		<td>
			备注
		</td>
	</tr>
	</thead>
	<tbody>

	
	<logic:iterate id="rowList" name="tableList" type="java.util.List" indexId="index">
	<tr>
		<logic:iterate id="taskDetail" name="rowList" indexId="indexid">
		<bean:define id="leaf" name="taskDetail" property="leaf" />
		<td rowspan="${taskDetail.rowspan}" colspan="${taskDetail.colspan}" class="label" style="vertical-align:middle;text-align:left">
			<eoms:id2nameDB id="${taskDetail.kpiId}" beanId="pnrEvaKpiDao" />(${taskDetail.weight}分)
		</td>
		<%if (PnrEvaConstants.NODE_LEAF.equals(leaf)) { %>
			<bean:define id="isPublish" name="taskDetail" property="isPublish" />
		<td>
			${taskDetail.algorithm}
		</td>
			<%if(PnrEvaConstants.TASK_PUBLISHED.equals(isPublish)){%>
		<td>
			${taskDetail.realScore}
		</td>
		<td>
			${taskDetail.reduceReason}
		</td>
		<td>
			${taskDetail.remark}
		</td>
		<%}else{%>
		<td>
			${taskDetail.realScore}
		</td>
		<td>

			${taskDetail.reduceReason}
		</td>
		<td>
			${taskDetail.remark}
		</td>
		<%}
		}%>
		</logic:iterate>
	</tr>
	</logic:iterate>
	</tbody>
</table>
<table class="formTable" id="tplForm" width="60%">
<caption>
	<div class="header center">任务考核执行
	</div>

</caption>
	<tr>
		<td class="label">
		审核人	
		</td>
		<td class="content">
		${taskAudit.auditUser}
		</td>
	</tr>
		<tr>
		<td class="label">
		审核
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="auditResult" isQuery="false" 
			           defaultId="${taskAudit.auditResult}" selectId="auditResult" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择模板审核(字典)...'"/>	
		</td>
	</tr>
	<tr height="50">
		<td width="30%" class="label"> 
			审核说明 
		</td>
		<td width="100%" colspan="3">
			<html:textarea property="auditRemark" styleId="auditRemark" style="width:50%"
						styleClass="textarea" alt="maxLength:400" />
		</td>
</table>
<table>
	<tr>
		<td>	

			<input type="submit" class="btn" value="提交" onclick="commit()""/>	
		</td>
	</tr>
</table>
	<input type="hidden" id="id" name="id" value="${taskAudit.id}">
	<input type="hidden" id="taskId" name="taskId" value="${requestScope.taskId}"/>
	<input type="hidden" id="partner" name="partner" value="${requestScope.partner}"/>
	<input type="hidden" name="timeType" value="${requestScope.timeType}"/>
	<input type="hidden" name="time" value="${requestScope.time}"/>
	<input type="hidden" name="templateId" value="${requestScope.templateId}">
</html:form>
</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
function  commit()
    {
      document.forms[0].action="evaTaskAudit.do?method=doAudit";
    };
</script>
