<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<jsp:directive.page import="java.util.Map"/>
<%@ page import="com.boco.eoms.partner.eva.util.PnrEvaDateUtil"%>
<%@ page import="com.boco.eoms.partner.eva.model.PnrEvaTaskAudit"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'pnrEvaKpiInstanceForm'});
});
var inputStrSc='';
var inputStrMr='';
var inputStrWt='';
</script>
<%
Map minSorceMap = (Map)request.getAttribute("minSorceMap");
%>
<html:form action="/evaTasks.do?method=saveTaskDetail" styleId="pnrEvaKpiInstanceForm" method="post">
<table class="listTable" id="list-table">
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-合作伙伴(<eoms:id2nameDB id="${requestScope.partner}" beanId="areaDeptTreeDao" />)-周期(${requestScope.timeType})-时间(${requestScope.time}) 考核执行列表
		</div>
	</caption>
	<thead>
		<tr>
		<td colspan="2">
			
		</td>
		<td>
			评分部门：<eoms:id2nameDB beanId="tawSystemDeptDao" id="${sessionScope.sessionform.deptid}"/>
			<input type="hidden" id="deptId" name=""deptId"" value="${requestScope.deptid}"/>
		</td>
		<td>
			评分人：<eoms:id2nameDB id="${sessionScope.sessionform.userid}" beanId="tawSystemUserDao"/>
			<input type="hidden" id="userId" name="userId" value="${requestScope.userId}"/>
			
		</td>
		<td colspan="3">
			最终得分:<span id="lastScore"></span>
		</td>

	</tr>
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
		<bean:define id="auditFlag" name="taskDetail" property="auditFlag" />
		<bean:define id="state" name="taskDetail" property="state" />
		<%if (PnrEvaConstants.NODE_LEAF.equals(leaf)) { %>
		<script type="text/javascript">
		inputStrSc =inputStrSc + ',${taskDetail.nodeId}_${taskDetail.area}_sc';
		inputStrMr =inputStrMr + ',${taskDetail.nodeId}_${taskDetail.area}_mr';
		inputStrWt =inputStrWt + ',${taskDetail.nodeId}_${taskDetail.area}_wt';
		</script>
		<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_wt" 
			name="${taskDetail.nodeId}_${taskDetail.area}_wt" value="${taskDetail.weight}"/>	
			<%if(PnrEvaConstants.TASK_PUBLISHED.equals(isPublish)||!PnrEvaConstants.AUDIT_UNDO.equals(auditFlag)){%>
		<td>
			<eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" />  
		</td>
		<td>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_mr" 
			name="${taskDetail.nodeId}_${taskDetail.area}_mr" value="${taskDetail.maintenanceRatio}" readonly=""
			alt="maxLength:255"/>
			${taskDetail.maintenanceRatio}
		</td>
		<td>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_sc" 
			name="${taskDetail.nodeId}_${taskDetail.area}_sc" value="${taskDetail.realScore}" readonly=""
			alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
			${taskDetail.realScore}
		</td>
		<td>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_rm" 
			name="${taskDetail.nodeId}_${taskDetail.area}_rm" value="${taskDetail.remark}" readonly=""
			alt="maxLength:255"/>
			${taskDetail.remark}
		</td>
		</tr>
		<tr>
		<%}else if(algorithm.equals(PnrEvaConstants.SUMTYPE_MIN)){%>
		<td>
			<eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" />  
		</td>
		<%
		String minSorceIfo = StaticMethod.nullObject2String(minSorceMap.get(nodeId));
		if(minSorceIfo.equals(nodeAreaStr)){
			%>
			<td>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_mr" 
			name="${taskDetail.nodeId}_${taskDetail.area}_mr" value="100" readonly=""
			alt="allowBlank:false,vtext:'请输入比例',maxLength:32"/>
			100
		</td>
		<%}else{%>
		<td>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_mr" 
			name="${taskDetail.nodeId}_${taskDetail.area}_mr" value="${taskDetail.maintenanceRatio}" readonly=""
			alt="allowBlank:false,vtext:'请输入比例',maxLength:32"/>
			${taskDetail.maintenanceRatio}
		</td>
		<%} %>
		<td>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_sc" 
			name="${taskDetail.nodeId}_${taskDetail.area}_sc" value="${taskDetail.realScore}" readonly=""
			alt="maxLength:255"/>
			${taskDetail.realScore}
		</td>
		<td>
			<input type="text" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_rm" 
			name="${taskDetail.nodeId}_${taskDetail.area}_rm" value="${taskDetail.remark}"
			alt="maxLength:255"/>
		</td>
		</tr>
		<tr>
		<%}else if(algorithm.equals(PnrEvaConstants.SUMTYPE_INPUT)){%>
		<td>
		<eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" />  
	</td>
	<td>
		<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_mr" 
		name="${taskDetail.nodeId}_${taskDetail.area}_mr" value="100" readonly=""
		alt="allowBlank:false,vtext:'请输入比例',maxLength:32"/>
		100
	</td>
	<td>
		<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_sc" 
		name="${taskDetail.nodeId}_${taskDetail.area}_sc" value="${taskDetail.realScore}" readonly=""
		alt="maxLength:255"/>
		${taskDetail.realScore}
	</td>
	<td>
		<input type="text" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_rm" 
		name="${taskDetail.nodeId}_${taskDetail.area}_rm" value="${taskDetail.remark}"
		alt="maxLength:255"/>
	</td>
	</tr>
	<tr>
	<%}else{%>
		<td>
		<eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" />  
	</td>
	<td>
		<input type="text" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_mr" 
		name="${taskDetail.nodeId}_${taskDetail.area}_mr" value="${taskDetail.maintenanceRatio}"
		alt="allowBlank:false,vtext:'请输入比例',maxLength:32"/>
	</td>
	<td>
		<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_sc" 
		name="${taskDetail.nodeId}_${taskDetail.area}_sc" value="${taskDetail.realScore}" readonly=""
		alt="maxLength:255"/>
		${taskDetail.realScore}
	</td>
	<td>
		<input type="text" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_rm" 
		name="${taskDetail.nodeId}_${taskDetail.area}_rm" value="${taskDetail.remark}"
		alt="maxLength:255"/>
	</td>
	</tr>
	<tr>
	<%}
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
<table>
	<tr>
		<td>	
			<input type="submit" class="btn" value="保存草稿" onclick="save()" style="${requestScope.isPublishButton}"/>
			&nbsp;
			<input type="submit" class="btn" value="发布" onclick="commit()" style="${requestScope.isPublishButton}"/>	
			&nbsp;
			<input type="button" class="btn" value="计算分数" onclick="countScore()" style="${requestScope.isPublishButton}"/>	
		</td>
	</tr>
</table>
	<input type="hidden" id="taskId" name="taskId" value="${requestScope.taskId}"/>
	<input type="hidden" id="partner" name="partner" value="${requestScope.partner}"/>
	<input type="hidden" name="timeType" value="${requestScope.timeType}"/>
	<input type="hidden" name="time" value="${requestScope.time}"/>
	<input type="hidden" name="type" value="<%=PnrEvaConstants.NODETYPE_TEMPLATE %>"/>
</html:form>
<c:if test="${not empty taskAuditInfo}">
<div style="height: 400px">
	<a onclick="$('kpiContent').toggleClass('hide')"
		style="cursor: pointer">隐藏/显示模板任务审核信息</a>
<table  style="width:100%">
	<tr>
		<td >
	<caption>
		<div class="header center">
			考核审核信息
		</div>
	</caption>
	</td>
	</tr>
</table>
	<div id="kpiContent">
	<logic:present name="taskAuditInfo" scope="request">
	<logic:iterate id="auditInfo" name="taskAuditInfo" scope="request">
	<table class="formTable" id="tplForm" style="width:98%">
	<tr>
		<td class="label" >
		审核地域		</td>
		<td class="content" colspan="3">
		${auditInfo.areaName}		</td>
	</tr>
		<tr>
		<td class="label">
		审核角色	
		</td>
		<td class="content" colspan="3">
		<eoms:id2nameDB id="${auditInfo.auditOrg}" beanId="tawSystemSubRoleDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
		送审时间		</td>
		<td class="content">
		${auditInfo.auditCreateString}		</td>
		<td class="label">
		审核时间		</td>
		<td class="content">
		${auditInfo.auditDateString}		</td>
	</tr>
	<tr>
		<td class="label">
		审核人		</td>
		<td class="content">
		<eoms:id2nameDB id="${auditInfo.auditUser}" beanId="tawSystemUserDao" />		</td>

		<td class="label">
		审核结果		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="auditFlag" itemId="${auditInfo.auditResult}" beanId="id2nameXML" />		</td>
	</tr>
	<tr>
		<td class="label">
		审核说明		</td>
		<td class="content" colspan="3">
		${auditInfo.auditRemark}		</td>
	</tr>
</table>
<p>&nbsp;</p>
</logic:iterate>
</logic:present>
	</div>
	</div>
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
function  save()
    {
      document.forms[0].action="evaTasks.do?method=saveTaskDetail";
    };
function  commit()
    {
      document.forms[0].action="evaTasks.do?method=commitTaskDetail";
    };
function  runBack()
    {
   	  v.passing="true";
   	  document.getElementById("taskId").value = '' ;
   	  document.getElementById("partner").value = '' ;
      document.forms[0].action="evaTasks.do?method=xquery&searchType=1";
    };
function  queryBack()
    {
      v.passing="true";
      document.getElementById("taskId").value = '' ;
      document.forms[0].action="evaTasks.do?method=xquery&searchType=0";
    };
    function  countScore()
    {
      var lastScore = 0;
      var strSc=inputStrSc.split(",");
      var strMr=inputStrMr.split(",");
      var strWt=inputStrWt.split(",");
      for(var i=1;i<strSc.length;i++){
      //alert(document.getElementById(strWt[i]).value);
      //alert(document.getElementById(strMr[i]).value);
      //alert(document.getElementById(strSc[i]).value);
      lastScore = lastScore + document.getElementById(strSc[i]).value*document.getElementById(strMr[i]).value*document.getElementById(strWt[i]).value/10000; 
      }
       document.all.lastScore.innerHTML=lastScore; 
    };
</script>
