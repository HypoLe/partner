<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<jsp:directive.page import="java.util.Map"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	var readTree;
	v = new eoms.form.Validation({form:'pnrEvaTemplateDetailForm'});
	var tabs = new Ext.TabPanel('info-page');
    var tplTab = tabs.addTab('task-audit', '模板任务考核 ');
	tabs.activate(0);
});
</script>
<%
Map minSorceMap = (Map)request.getAttribute("minSorceMap");
%>
 <div id="info-page">
  <!-- 模板面板 -->
  <div id="task-audit" class="tabContent">
<html:form action="/evaTaskAudit.do?method=doAudit" styleId="pnrEvaTemplateDetailForm" method="post">
<table class="listTable" id="list-table">
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-合作伙伴(${requestScope.partner})-周期(${requestScope.timeType})-时间(${requestScope.time}) 考核执行列表
		</div>
	</caption>
	<thead>
	<tr>
		<td>
			考核模板
		</td>
		<td>
			算法描述
		</td>
		<td>
			地域
		</td>
		<td>
			计算比例
		</td>
		<td>
			汇总分数
		</td>
		<td>
			备注
		</td>
	</tr>
	</thead>
	<tbody>	
	<tr>
	<logic:iterate id="rowList" name="tableList" type="java.util.List">

		<logic:iterate id="taskDetail" name="rowList">
		<bean:define id="leaf" name="taskDetail" property="leaf" />
		<bean:define id="isPublish" name="taskDetail" property="isPublish" />
		<bean:define id="rowspan" name="taskDetail" property="rowspan" />
		<bean:define id="algorithm" name="taskDetail" property="algorithm" />
		<bean:define id="nodeAreaStr" name="taskDetail" property="nodeAreaStr" />
		<bean:define id="nodeId" name="taskDetail" property="nodeId" />
		<%if (PnrEvaConstants.NODE_LEAF.equals(leaf)) { %>
		<td>
			<eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" />  
		</td>
		<td>
			${taskDetail.maintenanceRatio}
		</td>
		<td>
			${taskDetail.realScore}
		</td>
		<td>
			${taskDetail.remark}
		</td>
		</tr>
		<tr>
		<%
		}else{%>
		<td rowspan="${taskDetail.rowspan}" class="label" style="vertical-align:middle;text-align:left">
			<eoms:id2nameDB id="${taskDetail.templateId}" beanId="pnrEvaTemplateDao" />(${taskDetail.weight}%)
		</td>
		<td rowspan="${taskDetail.rowspan}"  style="vertical-align:middle;text-align:left">
			<eoms:dict key="dict-partner-eva" dictId="sumType" itemId="${taskDetail.algorithm}" beanId="id2nameXML" />
		</td>
		<%
		}
		%>
	</logic:iterate>
	</logic:iterate>
	</tr>
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
	<input type="hidden" id="taskId" name="taskId" value="${requestScope.taskId}"/>
	<input type="hidden" id="partner" name="partner" value="${requestScope.partner}"/>
	<input type="hidden" name="timeType" value="${requestScope.timeType}"/>
	<input type="hidden" name="time" value="${requestScope.time}"/>
	<input type="hidden" name="type" value="<%=PnrEvaConstants.NODETYPE_TEMPLATE %>"/>
	<input type="hidden" name="id" value="${taskAudit.id}"/>
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
