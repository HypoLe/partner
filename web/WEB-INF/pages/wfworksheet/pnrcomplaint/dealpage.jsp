<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<%
String taskName = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getAttribute("taskName"));
 String operateType = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getParameter("operateType"));
 %>

<c:choose>
	  <c:when test="${taskName=='DraftTask'}">
	  	<c:set var="methodValue" value="showDraftPage" />
	  </c:when>
	    <c:when test="${taskName=='RejectTask'}">
	  	<c:if test="${operateType!='4' }">
	  	  	<c:set var="methodValue" value="showDraftPage" />
	  		<c:set var="operateType" value="54" />
	  	</c:if>
	  	<c:if test="${operateType=='4' }">
	  	  	<c:set var="methodValue" value="showInputDealPage" />
	  		<c:set var="operateType" value="4" />
	  	</c:if>
	  </c:when>
	   <c:when test="${taskName=='cc'}">
	  	<c:set var="methodValue" value="showInputDealPage" />
	  	<c:set var="operateType" value="-15"/>
	  </c:when>
	  <c:otherwise>
	  	<c:set var="methodValue" value="showInputDealPage" />
	  	<c:set var="operateType" value="<%=operateType %>" />
	  </c:otherwise>
</c:choose>

<c:url var="urlDeal" value="/sheet/pnrcomplaint/pnrcomplaint.do">
  <c:param name="method" value="${methodValue}"/>
  <c:param name="sheetKey" value="${sheetKey}"/>
  <c:param name="taskStatus" value="${taskStatus}"/> 
  <c:param name="piid" value="${piid}"/>
  <c:param name="taskId" value="${taskId}"/>
  <c:param name="taskName" value="${taskName}"/>
  <c:param name="operateRoleId" value="${operateRoleId}"/>
  <c:param name="TKID" value="${TKID}"/>
  <c:param name="preLinkId" value="${preLinkId}"/>
  <c:param name="operateType" value="${operateType}"/>
  <c:param name="dealTemplateId" value="${dealTemplateId}"/>  
  <c:param name="backFlag" value="${backFlag}"/>
</c:url> 

<script type="text/javascript">
var ifAccept;
var reviewResult;
var roleTree;
var v;
function initPage(){
	v = new eoms.form.Validation({form:'theform'});
	if("${taskName}"!="DraftTask"){
        $('btns').removeClass('hide');	
    }
}


Ext.onReady(function(){
	var dealTemplateId = "${dealTemplateId}";
	var strUrl = "${urlDeal}";
	var taskName = "${taskName}";
   	if (dealTemplateId != null && dealTemplateId != "" && taskName != "draft") {	
   		strUrl = '${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=showTemplateDealInputSheetPage&sheetKey=${sheetKey}&piid=${piid}&taskId=${taskId}&taskName=${taskName}&taskStatus=${taskStatus}&operateRoleId=${operateRoleId}&TKID=${TKID}&preLinkId=${preLinkId}&operateType=${operateType}&dealTemplateId='+dealTemplateId;
	}
	var config = {
		url:strUrl,
		callback : initPage
	}
	eoms.util.appendPage("idSpecial",config);
	
});


  function changeType3(){
	//$('phaseId').value = "TestTask";
	//$('operateType').value = "54";   	
  }
  function changeType2(){
   	$('phaseId').value = "DraftTask";
   	$('operateType').value = "22";  	
  }
   
   function saveDealInfo(){
    var form = document.forms[0];
   	var ajaxForm = Ext.getDom(form);
   	if(v.check()) {
	   	v.passing = false;
	    Ext.Ajax.request({
		   		form: ajaxForm,
				method:"post",
				url: "${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=performSaveInfo",
				success: function(){
		        	alert("保存成功！");
	 		}
	    });
	  }
   }
   
function ifisCopy() {
	try{ 
	 var ope= '${operateType}';
	 if(ope == '11'||ope == '55'||ope == '4') {
	  	$('hasNextTaskFlag').value = 'true';  
	 }	
	 var str=document.forms[0]['copyPerformer'].value;
	 var taskName=document.forms[0]['taskName'].value;
	if(taskName=="cc"||taskName=="reply"||taskName=="advice"){
	  if(str==null||str=="")
	  	$('hasNextTaskFlag').value = 'true';  
	 }
	} catch(e){}
} 

</script>

<div id="sheetform">
  <html:form action="/pnrcomplaint.do?method=performDeal" styleId="theform">

<!-- content -->
    <div id="idSpecial"></div>
    <input type="hidden" name="hasNextTaskFlag" id="hasNextTaskFlag"/> 
    
   <c:choose> 
    <c:when test="${operateType=='61'}">
		<div class="form-btns" id="btns">
		  <input type="submit" class="submit" onclick="this.form.action='${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=performClaimTask'" 
		     value="<bean:message bundle='sheet' key='common.accept'/>" />
		</div>
   	</c:when>

    <c:when test="${taskName=='DraftTask'}">
    	<input type="hidden" name="TKID" value="${taskId }"/> 
		<div class="form-btns" id="btns">
		<html:submit styleClass="btn" property="method.save"  styleId="submitone">
		 <bean:message bundle="sheet" key="button.send"/>
		</html:submit>
		  <html:button styleClass="btn" property="method.save" styleId="method.save"
		      onclick="v.passing=true;javascript:saveDealInfo();">
		          	<bean:message bundle='sheet' key='button.save'/>
		  </html:button>
	      <input type="submit" class="submit" onclick="v.passing=true;this.form.action='${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=performSaveInfoAndExit&draft=1'" value="<bean:message bundle='sheet' key='button.saveback'/>" />
	      <input type="submit" class="submit" onclick="v.passing=true;this.form.action='${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=showDraftList'" value="<bean:message bundle='sheet' key='button.back'/>" />
		</div>	 
   	</c:when>
   	<c:when test="${taskName=='RejectTask'}">    
		<div class="form-btns" id="btns">
		<html:submit styleClass="btn" property="method.save"  styleId="submitone" onclick="javascript:changeType3();">
		 <bean:message bundle="sheet" key="button.send"/>
		</html:submit>
		  <html:button styleClass="btn" property="method.save" styleId="method.save"
		      onclick="v.passing=true;javascript:saveDealInfo();">
		          	<bean:message bundle='sheet' key='button.save'/>
		  </html:button>
          <input type="submit" class="submit" onclick="v.passing=true;this.form.action='${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=performSaveInfoAndExit'" value="<bean:message bundle='sheet' key='button.saveback'/>" />
	      <input type="submit" class="submit" onclick="v.passing=true;this.form.action='${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=showListsendundo'" value="<bean:message bundle='sheet' key='button.back'/>" />
	    </div>	 
   	</c:when>
   	<c:when test="${taskName=='AuditTask'}">    
		<div class="form-btns" id="btns">
		<html:submit styleClass="btn" property="method.save"  styleId="submitone">
		提交
		</html:submit>
		<input type="submit" class="submit" onclick="v.passing=true;this.form.action='${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=showListsendundo'" value="<bean:message bundle='sheet' key='button.back'/>" />
		</div>	 
   	</c:when>
    <c:otherwise>  
	    <!-- buttons -->
	    <div class="form-btns hide" id="btns">
         <input type="submit" class="submit" name="method.save" id="method.save" value="<bean:message bundle='sheet' key='button.done'/>" >	      
	      <html:button styleClass="btn" property="method.save" styleId="method.save"  onclick="v.passing=true;javascript:saveDealInfo();">
		          	<bean:message bundle='sheet' key='button.save'/>
		   </html:button>
	      <input type="submit" class="submit" onclick="v.passing=true;this.form.action='${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=performSaveInfoAndExit'" value="<bean:message bundle='sheet' key='button.saveback'/>" />
	      <input type="submit" class="submit" onclick="v.passing=true;this.form.action='${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=showListsendundo'" value="<bean:message bundle='sheet' key='button.back'/>" />
	    </div>    
    </c:otherwise>    	   
   </c:choose>
</html:form>
</div>
