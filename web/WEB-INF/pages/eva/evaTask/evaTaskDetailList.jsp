<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.eva.util.EvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style type="text/css">
#toAuditOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
<c:if test="${err=='beforeEvaTime'}">
<div class="failurePage">
	<h1 class="header">未到评分时间！</h1>
	<div class="content">
		
	</div>
</div>
</c:if>
<c:if test="${err=='evaSaved'}">
<div class="failurePage">
	<h1 class="header">本周期已评分！</h1>
	<div class="content">
		
	</div>
</div>
</c:if>
<c:if test="${err==null}">
<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'evaKpiInstanceForm'});
})
</script>

<html:form action="/evaTasks.do?method=saveTaskDetail" styleId="evaKpiInstanceForm" method="post">
<table class="listTable" id="list-table">
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-合作伙伴(<eoms:id2nameDB id="${requestScope.partner}" beanId="tawSystemDeptDao" />)-周期(<eoms:dict key="dict-eva" dictId="cycle" itemId="${requestScope.timeType}" beanId="id2nameXML" />)-时间(${requestScope.timeStr}) 考核执行列表
		</div>
	</caption>
	<thead>
	<tr>
		<td colspan="${requestScope.maxLevel}">
			考核指标
		</td>
		<td>
			算法描述
		</td>
		<td>
			实际得分
		</td>
		<td>
			增扣分原因
		</td>
		<td>
			备注
		</td>
	</tr>
	</thead>
	<tbody>
	<input type="hidden" id="reportId" name="reportId" value="${requestScope.reportId}"/>
	<input type="hidden" id="taskId" name="taskId" value="${requestScope.taskId}"/>
	<input type="hidden" id="partner" name="partner" value="${requestScope.partner}"/>
	<input type="hidden" id="partnerName" name="partnerName" value="<eoms:id2nameDB id="${requestScope.partner}" beanId="tawSystemDeptDao" />"/>
	<input type="hidden" name="timeType" value="${requestScope.timeType}"/>
	<input type="hidden" name="time" value="${requestScope.time}"/>
	
	<logic:iterate id="rowList" name="tableList" type="java.util.List" indexId="index">
	<tr>
		<logic:iterate id="taskDetail" name="rowList">
		<bean:define id="leaf" name="taskDetail" property="leaf" />
		<bean:define id="scope" name="taskDetail" property="scope" />
		<%if(!"0-0".equals(scope)){ %>
		<td rowspan="${taskDetail.rowspan}" colspan="${taskDetail.colspan}" class="label" style="vertical-align:middle;text-align:left">
			<eoms:id2nameDB id="${taskDetail.kpiId}" beanId="evaKpiDao" />(${taskDetail.weight}分)
		</td>
		<%if (EvaConstants.NODE_LEAF.equals(leaf)) { %>
		
			<bean:define id="isPublish" name="taskDetail" property="isPublish" />
		<td>
			${taskDetail.algorithm}
		</td>
			<%if(EvaConstants.TASK_PUBLISHED.equals(isPublish)){%>
		<td>
			<input type="text" class="text" id="${taskDetail.nodeId}_sc" 
			name="${taskDetail.nodeId}_sc" value="${taskDetail.realScore}" readonly=""
			alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
		</td>
		<td>
			<input type="text" class="text" id="${taskDetail.nodeId}_rs" 
			name="${taskDetail.nodeId}_rs" value="${taskDetail.reduceReason}" readonly=""
			alt="maxLength:255"/>
		</td>
		<td>
			<input type="text" class="text" id="${taskDetail.nodeId}_rm" 
			name="${taskDetail.nodeId}_rm" value="${taskDetail.remark}" readonly=""
			alt="maxLength:255"/>
		</td>
		<%}else{%>
		<td>
		<c:if test="${taskDetail.evaSource=='subjective'}">
			<input type="text" class="text" id="${taskDetail.nodeId}_sc" 
			name="${taskDetail.nodeId}_sc" value="${taskDetail.realScore}"
			alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
		</c:if>
		<c:if test="${taskDetail.evaSource!='subjective'}">
			<input type="text" class="text" id="${taskDetail.nodeId}_sc" 
			name="${taskDetail.nodeId}_sc" value="${taskDetail.realScore}" readonly=""
			alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
		</c:if>

		</td>
		<td>
			<input type="text" class="text" id="${taskDetail.nodeId}_rs" 
			name="${taskDetail.nodeId}_rs" value="${taskDetail.reduceReason}"
			alt="maxLength:255"/>
		</td>
		<td>
			<input type="text" class="text" id="${taskDetail.nodeId}_rm" 
			name="${taskDetail.nodeId}_rm" value="${taskDetail.remark}"
			alt="maxLength:255"/>
		</td>
		<%}
		}	
		}else{ %>
			<c:if test="${taskDetail.realScore!=null}">
			<input type="hidden" class="text" id="${taskDetail.nodeId}_sc" 
			name="${taskDetail.nodeId}_sc" value="${taskDetail.realScore}" />
			</c:if>
			<c:if test="${taskDetail.realScore==null}">
			<input type="hidden" class="text" id="${taskDetail.nodeId}_sc" 
			name="${taskDetail.nodeId}_sc" value="0" />
			</c:if>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_rs" 
			name="${taskDetail.nodeId}_rs" value="${taskDetail.reduceReason}" 
			alt="maxLength:255"/>
			<input type="hidden" class="text" id="${taskDetail.nodeId}_rm" 
			name="${taskDetail.nodeId}_rm" value="${taskDetail.remark}" 
			alt="maxLength:255"/>
		
		<%
		}
		%>
		<input type="hidden" class="text" id="${taskDetail.nodeId}_scope" name="${taskDetail.nodeId}_scope" value="${taskDetail.scope}" />
		</logic:iterate>
	</tr>
	</logic:iterate>
	<c:if test="${isPublish =='' ||isPublish =='0'||isPublish =='3'}">
	<tr>
		<td class="label">
			是否审核		
		</td>
		<td class="content" colspan="5">
	       <INPUT type="radio" name="toAudit" value="2" checked="true" onclick="changeAudit(this.value);">审核 
	       <INPUT type="radio" name="toAudit" value="1" onclick="changeAudit(this.value);">不审核
		</td>
	</tr>
<tr id='audit'>		
<td class="label">
	审核人:
</td>
	<td class="" colspan="5">
		<%
		String toAuditOrgPanels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept', rootId:'1'}]";
		%>	
		<eoms:chooser id="toAuditOrg" category="[{id:'toAuditOrg',text:'审核',allowBlank:false,limit:1,vtext:'请选择审核人'}]" panels="<%=toAuditOrgPanels%>"/>
	</td>
	</tr>
	</c:if>
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
function  agreementBack()
    {
      v.passing="true";
      document.forms[0].action="evaTasks.do?method=xqueryByTpid&templateId=${requestScope.templateId}";
    };
function changeAudit(auditFlag)
    {
    var auditTr = document.getElementById("audit");
    if(auditFlag=='2'){
      auditTr.style.display='';
       chooser_toAuditOrg.enable();
      }else{
      auditTr.style.display='none';
      chooser_toAuditOrg.disable();
      }
    };
</script>
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>