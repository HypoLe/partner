<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.List,com.boco.eoms.evaluation.model.*"%>

<script type="text/javascript"
	src="${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>

<!-- old操作记录 -->
<c:if test="${not empty evaluationConfirmList and not isOpenEvaluationSwitch}">
   <fieldset>
	  <legend>操作记录</legend>
		<display:table name="evaluationConfirmList" cellspacing="0"
			cellpadding="0" id="evaluationConfirmList" pagesize="${pagesize}"
			class="table evaluationConfirmList" export="false" requestURI=""
			size="${size}">
			<display:column headerClass="sortable" title="考核发起人">
				<eoms:id2nameDB id="${evaluationConfirmList.submitPersonnel}" beanId="tawSystemUserDao" />
			</display:column>
			<display:column headerClass="sortable" title="确认人">
				<c:if
					test="${evaluationConfirmList.auditorType=='dyConfirmPersonnel'}">
					<eoms:id2nameDB id="${evaluationConfirmList.auditorId}" beanId="tawSystemUserDao" />
				</c:if>
				<c:if
					test="${evaluationConfirmList.auditorType=='provConfirmPersonnel'}">
					<eoms:id2nameDB id="${evaluationConfirmList.auditorId}"
						beanId="tawSystemUserDao" />
				</c:if>

			</display:column>

			<display:column headerClass="sortable" title="结果">
				<c:if test="${evaluationConfirmList.auditorType=='dyConfirmPersonnel'}">
					<c:if test="${evaluationConfirmList.auditResult=='passAudit'}">通过考核</c:if>
					<c:if test="${evaluationConfirmList.auditResult=='unPassAudit'}">退回考核</c:if>
				</c:if>
				<c:if test="${evaluationConfirmList.auditorType=='provConfirmPersonnel'}">
					<c:if test="${evaluationConfirmList.auditResult=='passAudit'}">同意代维意见</c:if>
					<c:if test="${evaluationConfirmList.auditResult=='unPassAudit'}">驳回代维意见</c:if>
				</c:if>	
			</display:column>

			<display:column property="opinion" headerClass="sortable" title="意见" />

			<display:column  headerClass="sortable" title="确认时间">
			 <fmt:formatDate value="${evaluationConfirmList.confirmTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
			</display:column>
		</display:table>
		<br/>
	</fieldset>	
</c:if>
<!-- new 考核流程记录 -->
<c:if test="${not empty pnrEvaluEntityLinkList and isOpenEvaluationSwitch}">
   <fieldset>
	  <legend>操作记录</legend>
		<display:table name="pnrEvaluEntityLinkList" cellspacing="0"
			cellpadding="0" id="pnrEvaluEntityLinkList" 
			class="table " export="false" requestURI="" size="${fn:length(pnrEvaluEntityLinkList)}">
			<display:column headerClass="sortable" title="考核年/月">
				${pnrEvaluEntityLinkList.year}<c:if test="${'0'!=pnrEvaluEntityLinkList.month}">-${pnrEvaluEntityLinkList.month}</c:if>
			</display:column>
			<display:column headerClass="sortable" title="被考核对象">
				<eoms:id2nameDB id="${pnrEvaluEntityLinkList.evaluationtarget}" beanId="partnerDeptDao" />
			</display:column>
			<display:column headerClass="sortable" title="操作环节" property="phasename"/>
			<display:column headerClass="sortable" title="操作者">
				<eoms:id2nameDB id="${pnrEvaluEntityLinkList.operateuserid}" beanId="tawSystemUserDao" />
			</display:column>	
			<display:column headerClass="sortable" title="操作时间">
				<fmt:formatDate value="${pnrEvaluEntityLinkList.operatetime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
			</display:column>
			<display:column title="备注">
			    <pre>${pnrEvaluEntityLinkList.remark}</pre>
			</display:column>
		</display:table>
		<br/>
	</fieldset>	
</c:if>
<br/>
<span class="ui-widget-header" style="height:20px;width:100%;line-height:150%;font-size:12px;color:#083772;">考核总得分：${evaluationEntity.score }</span>
<div id="info-page" style="width:100%;overflow-x:hidden;overflow-y:hidden;">
</div>

<form action="${app}/partner/evaluation/evaluationEntity.do?method=saveEvaluationConfirm&flag=unPassAudit&operateType=11"
method="post" id="appForm">
			<div id="listQueryObject"
				style="border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff">
				<c:if test="${ProvConfirm=='2'}">意见被驳回！</c:if>
				<table class="formTable">
					<input type="hidden" value="${evaluationEntityId}" name="evaluationEntityId" id="evaluationEntityId" />
					<input type="hidden" value="${confirmPersonnel}" name="confirmPersonnel" id="confirmPersonnel" />
					<tr>
						<td class="label">
							<font color='red'> * </font>意见
						</td>
						<td class="content">
							<textarea name="opinion" id="opinion" class="text medium" style="width:95%"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" class="btn" value="通过考核" onclick="reject();" />

						</td>
						<td>
							<input type="button" class="btn" value="退回考核" onclick="reject2();" />
							(返回给提交者:
							<font color="red"><eoms:id2nameDB id="${executor}" beanId="tawSystemUserDao" />
							</font>)
						</td>
					</tr>
				</table>
			</div>
</form>


<script type="text/javascript">
   var items=[];
</script>
<c:forEach items="${evaluationEntityList}" var="evaluationEntityTemp" varStatus="status">
  <script type="text/javascript">
    items.push({
		          text : "${evaluationEntityTemp.usedTemplateName}",
				  url : "${app}/partner/evaluation/evaluationEntity.do?method=evaluationConfirmForm&id=${evaluationEntityTemp.id}" ,
				  isIframe : true 
		       });	
  </script>
</c:forEach>

<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function() {
	setTabs();
});

	function setTabs() {		 		 
		var tabConfig = {
		          items : items
	         };
	    var tabs = new eoms.TabPanel('info-page', tabConfig);		
	}  
	
 function reject(){
     var opinion=document.getElementById("opinion");
     if(opinion.value.trim()==""||opinion.value.length>160)     
     {
		alert("'意见'不能为空且不能超过160个字");
		opinion.focus();
		return ;
	 }  
 	 jq('form#appForm').attr("action","${app}/partner/evaluation/evaluationEntity.do?method=saveEvaluationConfirm&flag=passAudit&operateType=10");
 	 jq('form#appForm').submit();
 }
 
 function reject2(){
     var opinion=document.getElementById("opinion");
     if(opinion.value.trim()==""||opinion.value.length>160)
	 {
		alert("'意见'不能为空且不能超过160个字");
		opinion.focus();
		return ;
	 } 
	 jq('form#appForm').submit();
 }
 
</script>		
<%@ include file="/common/footer_eoms.jsp"%>