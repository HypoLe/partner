<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm"%>


<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'pnrEvaKpiInstanceForm'});
})
var areaStrs = "";
</script>

<html:form action="/evaTasks.do?method=saveTaskDetail" styleId="pnrEvaKpiInstanceForm" method="post">
<table class="listTable" id="list-table">
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-合作伙伴(<eoms:id2nameDB id="${requestScope.partner}" beanId="areaDeptTreeDao" />)-周期(${requestScope.timeType})-时间(${requestScope.time}) 考核执行列表
		</div>
	</caption>

	
	<thead>
	<tr>
		<td colspan="${requestScope.titleColspan + 1}">
			
		</td>
		<td colspan="2">
			评分部门：<eoms:id2nameDB beanId="tawSystemDeptDao" id="${sessionScope.sessionform.deptid}"/>
			<input type="hidden" id="deptId" name=""deptId"" value="${requestScope.deptid}"/>
		</td>
		<td colspan="2">
			评分人：<eoms:id2nameDB id="${sessionScope.sessionform.userid}" beanId="tawSystemUserDao"/>
			<input type="hidden" id="userId" name="userId" value="${requestScope.userId}"/>
			
		</td>

	</tr>
	<tr>
		<td>
			地市
		</td>
		<td colspan="${requestScope.titleColspan}">
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
	<input type="hidden" id="partner" name="partner" value="${requestScope.partner}"/>
	<input type="hidden" name="timeType" value="${requestScope.timeType}"/>
	<input type="hidden" name="time" value="${requestScope.time}"/>
	<input type="hidden" name="templateId" value="${requestScope.templateId}">
	<input type="hidden" name="type" value="<%=PnrEvaConstants.NODETYPE_KPI %>"/>
	<%
	Map areaRowMap = (Map)request.getAttribute("areaRowMap");
	List areaList = new ArrayList();
	String areaTemp = "";
	String areaRow = "";
	%>
	<logic:iterate id="rowList" name="tableList" type="java.util.List" indexId="index">
	<tr>
		<logic:iterate id="taskDetail" name="rowList" indexId="indexid">
		<input type="hidden" id="taskId" name="taskId" value="${taskDetail.taskId}"/>
		<bean:define id="leaf" name="taskDetail" property="leaf" />
		<bean:define id="area" name="taskDetail" property="area" />
		<%if (!areaTemp.equals(area)) {
			areaRow = StaticMethod.nullObject2String(areaRowMap.get(area.toString()));
		%>
		<script type="text/javascript">
		var inputStr_${taskDetail.area}='';
        
		</script>
		<td rowspan="<%=areaRow %>" class="label" style="vertical-align:middle;text-align:left">
		<eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" />
		<input type="button" class="btn" value="计算分数" onclick="countScore(inputStr_${taskDetail.area},'lastScore_${taskDetail.area}')" style="${requestScope.isPublishButton}"/>	
		<br>
		<span id="lastScore_${taskDetail.area}" style=color:red ></span>
		</td>
		<%
		areaTemp = area.toString();
		}
		%>
		<td rowspan="${taskDetail.rowspan}" colspan="${taskDetail.colspan}" class="label" style="vertical-align:middle;text-align:left">
			<eoms:id2nameDB id="${taskDetail.kpiId}" beanId="pnrEvaKpiDao" /><br>(${taskDetail.weight}分)
		</td>
		<%if (PnrEvaConstants.NODE_LEAF.equals(leaf)) { %>
			<bean:define id="isPublish" name="taskDetail" property="isPublish" />
			<bean:define id="auditFlag" name="taskDetail" property="auditFlag" />
			
		<script type="text/javascript">
		inputStr_${taskDetail.area} =inputStr_${taskDetail.area} + ',${taskDetail.nodeId}_${taskDetail.area}_sc';
		</script>
		<td>
			${taskDetail.algorithm}
		</td>
			<%if(PnrEvaConstants.TASK_PUBLISHED.equals(isPublish)||!PnrEvaConstants.AUDIT_UNDO.equals(auditFlag)){%>
		<td>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_sc" 
			name="${taskDetail.nodeId}_${taskDetail.area}_sc" value="${taskDetail.realScore}" readonly=""
			alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
			${taskDetail.realScore}
		</td>
		<td>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_rs" 
			name="${taskDetail.nodeId}_${taskDetail.area}_rs" value="${taskDetail.reduceReason}" readonly=""
			alt="maxLength:255"/>
			${taskDetail.reduceReason}
		</td>
		<td>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_rm" 
			name="${taskDetail.nodeId}_${taskDetail.area}_rm" value="${taskDetail.remark}" readonly=""
			alt="maxLength:255"/>
			${taskDetail.remark}
		</td>
		<%}else{%>
		<td>
			<input type="text" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_sc" 
			name="${taskDetail.nodeId}_${taskDetail.area}_sc" value="${taskDetail.realScore}"
			alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
		</td>
		<td>
			<input type="text" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_rs" 
			name="${taskDetail.nodeId}_${taskDetail.area}_rs" value="${taskDetail.reduceReason}"
			alt="maxLength:255"/>
		</td>
		<td>
			<input type="text" class="text" id="${taskDetail.nodeId}_${taskDetail.area}_rm" 
			name="${taskDetail.nodeId}_${taskDetail.area}_rm" value="${taskDetail.remark}"
			alt="maxLength:255"/>
		</td>
		<%}
		}%>
		</logic:iterate>
	</tr>
	</logic:iterate>  
	</tbody>
</table>
<table>
	<tr>
		<td>	
			<input type="submit" class="btn" value="保存草稿" onclick="save()" style="${requestScope.isPublishButton}"/>
			&nbsp;
			<input type="submit" class="btn" value="发布" onclick="commit()" style="${requestScope.isPublishButton}"/>	
		</td>
	</tr>
</table>
</html:form>
<c:if test="${not empty taskAuditInfo}">
<div style="height: 400px">
	<a onclick="$('kpiContent').toggleClass('hide')"
		style="cursor: pointer">隐藏/显示模板任务审核信息</a>

</table>
	<div id="kpiContent">
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
function  countScore(inputStr,scoreSpan)
    {
      var score = 100;
      var strInput=inputStr.split(",");
      for(var i=1;i<strInput.length;i++){
       score = score - document.getElementById(strInput[i]).value; 
      }
       document.all(scoreSpan).innerHTML='总分：'+score; 
       
    };
    
</script>
