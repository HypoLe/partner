<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List,com.boco.eoms.evaluation.model.*"%>

<script type="text/javascript"
	src="${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>

<c:if test="${not empty linkList}">
   <fieldset>
	    <legend>操作记录</legend>
		<display:table name="linkList" cellspacing="0" cellpadding="0" id="linkList" 
			class="table" export="false" requestURI="" size="${size}">			
			<display:column title="考核年/月">
		 	  ${linkList.year}<c:if test="${linkList.month!='0'}">-${linkList.month}</c:if>
		    </display:column>
		    <display:column title="被考核对象">
		 	  <eoms:id2nameDB id="${linkList.evaluationtarget}" beanId="partnerDeptDao"/>
		    </display:column>
		    <display:column property="phasename" title="操作环节" />
			<display:column title="操作者">
		 	  <eoms:id2nameDB id="${linkList.operateuserid}" beanId="tawSystemUserDao" />
		    </display:column>
			<display:column   title="操作时间">
			   <fmt:formatDate value="${linkList.operatetime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
			</display:column>
			<display:column title="备注">
		 	  <pre>${linkList.remark}</pre>
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
				打分被驳回！
				<table class="formTable">
					<input type="hidden" value="${evaluationEntityId}" name="evaluationEntityId" id="evaluationEntityId" />
					<tr>
						<td class="label">
							<font color='red'> * </font>备注
						</td>
						<td class="content">
							<textarea name="opinion" id="opinion" class="text medium" style="width:95%"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<input type="button" class="btn" value="作废" onclick="deletescore();" />
						</td>
						<td>
							<input type="button" class="btn" value="修改打分" onclick="rescore();" />							
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
				  url : "${app}/partner/evaluation/evaluationEntity.do?method=gotoRtnBackStart&id=${evaluationEntityTemp.id}" ,
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
	
 function deletescore(){ 
   if(confirm('确定将这次考核作废?作废后，请重新发起考核')){ 
     var opinion=document.getElementById("opinion");
     if(opinion.value.trim()==""||opinion.value.length>160)     
     {
		alert("'备注'不能为空且不能超过160个字");
		opinion.focus();
		return ;
	 }  
 	 jq('form#appForm').attr("action","${app}/partner/evaluation/evaluationEntity.do?method=dealRtnBackStart&id=${evaluationEntity.id}&operateType=4"); 
 	 jq('form#appForm').submit();
   }    
 }
 
function rescore(){
  if(confirm('确定修改原有打分?如是，请去打分列表重新打分')){ 
	 var opinion=document.getElementById("opinion");
     if(opinion.value.trim()==""||opinion.value.length>160)
	 {
		alert("'备注'不能为空且不能超过160个字");
		opinion.focus();
		return ;
	 } 
	jq('form#appForm').attr("action","${app}/partner/evaluation/evaluationEntity.do?method=dealRtnBackStart&id=${evaluationEntity.id}&operateType=5"); 
 	jq('form#appForm').submit();
  }
}
 
</script>		
<%@ include file="/common/footer_eoms.jsp"%>