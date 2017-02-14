<%@ page language="java" pageEncoding="UTF-8"
	import="java.util.List,com.boco.eoms.evaluation.model.IndicatorScore,com.boco.eoms.evaluation.model.web.TdObjectModel"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List,com.boco.eoms.evaluation.model.*"%>

<script type="text/javascript"
	src="${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>

<c:if test="${not empty evaluationConfirmList}">
   <fieldset>
	  <legend>操作记录</legend>
		<display:table name="evaluationConfirmList" cellspacing="0"
			cellpadding="0" id="evaluationConfirmList" pagesize="${pagesize}"
			class="table evaluationConfirmList" export="false" requestURI=""
			size="${size}">
			<display:column headerClass="sortable" title="考核发起人">
				<eoms:id2nameDB id="${evaluationConfirmList.submitPersonnel}"
					beanId="tawSystemUserDao" />
			</display:column>
			<display:column headerClass="sortable" title="确认人">
				<c:if
					test="${evaluationConfirmList.auditorType=='dyConfirmPersonnel'}">
					<eoms:id2nameDB id="${evaluationConfirmList.auditorId}"
						beanId="tawSystemUserDao" />
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
<br/>
<span class="ui-widget-header" style="height:20px;width:100%;line-height:150%;font-size:12px;color:#083772;">考核总得分：${evaluationEntity.score }</span>
<div id="info-page" style="width:100%;overflow-x:hidden;overflow-y:hidden;">
</div>

<form method="post" id="appForm">
			<div id="listQueryObject"
				style="border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff">
					<c:if test="${evaluationEntity.provConfirm=='1'}">打分被驳回！</c:if>
				<table class="formTable">
					<input type="hidden" value="${evaluationEntityId}"
						name="evaluationEntityId" id="evaluationEntityId" />
					<input type="hidden" value="${confirmPersonnel}"
						name="confirmPersonnel" id="confirmPersonnel" />
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
							<input type="button" class="btn" value="驳回代维意见" onclick="reject();" />
							(返回给代维人员:
							<font color="red"><eoms:id2nameDB id="${dyConfirmPersonnel}"
									beanId="tawSystemUserDao" />
							</font>)
						</td>
						<td>
							<input type="button" class="btn" value="同意代维意见"
								onclick="passThrough();" />							
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
 	 jq('form#appForm').attr("action","${app}/partner/evaluation/evaluationEntity.do?method=unPassAudit&id=${evaluationEntity.id}"); 
 	 jq('form#appForm').submit();
 }
 
 function passThrough(){
     var opinion=document.getElementById("opinion");
     if(opinion.value.trim()==""||opinion.value.length>160)
	 {
		alert("'意见'不能为空且不能超过160个字");
		opinion.focus();
		return ;
	 } 
	jq('form#appForm').attr("action","${app}/partner/evaluation/evaluationEntity.do?method=passAudit&id=${evaluationEntity.id}"); 
 	jq('form#appForm').submit();
 }
 
</script>		
<%@ include file="/common/footer_eoms.jsp"%>