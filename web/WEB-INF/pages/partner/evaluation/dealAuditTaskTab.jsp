<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"  contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript"
	src="${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>

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

<form method="post" id="appForm">
		<div id="listQueryObject"
				style="border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff">
				<font style="font-weight:bold">审批操作</font>
				<input type="hidden" value="${evaluationEntity.id}" name="evaluationEntityId" id="evaluationEntityId" />
				<input type="hidden" value="${taskid}" name="taskid" id="taskid" />
				<table class="formTable">
					<tr>
						<td class="label">
							<font color='red'> * </font>备注:
						</td>
						<td class="content">
							<textarea name="remark" id="remark" class="text medium" style="width:95%"></textarea>
						</td>
					</tr>
					<tr>
						<td>
						    <input type="hidden" id="nextPos" name="nextPos" value="${nextPos}"/>
						    <br>
							<input type="button" class="btn" value="通过" onclick="auditPass();" />
						</td>
						<td>
						    驳回位置: 
						    <html:select property="rtnBackPos" styleId="rtnBackPos" value="${rtnBackPos}">
				     					<html:options collection="rtnBackPosList" property="value" labelProperty="label"/>
				   					</html:select>
						    <br>
							<input type="button" class="btn" value="驳回" onclick="auditNotPass();" />							
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
				  url : "${app}/partner/evaluation/evaluationEntity.do?method=gotoAuditTask&id=${evaluationEntityTemp.id}" ,
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
	
 function auditPass(){ 
   if(confirm('确定审批通过?')){ 
     var remark=document.getElementById("remark");
     if(remark.value.trim()==""||remark.value.length>999)     
     {
		alert("'备注'不能为空且不能超过1000个字");
		remark.focus();
		return ;
	 }  
 	 jq('form#appForm').attr("action","${app}/partner/evaluation/evaluationEntity.do?method=dealAuditTask&isPassed=true"); 
 	 jq('form#appForm').submit();
   }    
 }
 
function auditNotPass(){
   if(confirm('确定审批驳回?')){ 
     var remark=document.getElementById("remark");
     if(jq('#rtnBackPos').val()==''){
       alert("请选择驳回位置");
       return ;
     }
     if(remark.value.trim()==""||remark.value.length>999)     
     {
		alert("'备注'不能为空且不能超过1000个字");
		remark.focus();
		return ;
	 }  
 	 jq('form#appForm').attr("action","${app}/partner/evaluation/evaluationEntity.do?method=dealAuditTask&isPassed=false"); 
 	 jq('form#appForm').submit();
   }    
}
 
</script>		
<%@ include file="/common/footer_eoms.jsp"%>