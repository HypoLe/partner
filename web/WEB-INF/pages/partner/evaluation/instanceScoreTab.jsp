<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List,com.boco.eoms.evaluation.model.*"%>

<link rel="stylesheet" href="${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/demos.css">
<link rel="stylesheet" type="text/css" href="${app}/nop3/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>

<span style="font-size:12px;color:#083772;font-weight:bold;line-height:150%;">	
     ${evaluationEntity.year}<c:if test="${evaluationEntity.month!='0'}">-${evaluationEntity.month}</c:if>-<eoms:id2nameDB id="${evaluationEntity.evaluationTarget}" beanId="partnerDeptDao" /> 		
</span>
<input type="hidden" id="prt_templatename" name="prt_templatename" value="${templatename}"/>
<div id="info-page" style="width:100%;overflow-x:hidden;overflow-y:hidden;">
</div>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff">
<form method="post" id="fullConfimForm" name="fullConfimForm">  
  <input type="hidden" name="evaluationEntityId" value="${evaluationEntityId}"/> 
  <table class="formTable" width="75%">
	<tr>
		<td id="auditTd2">
		请选择代维公司确认人员*：<input type="text" name="dyConfirmPersonnelName" id="dyConfirmPersonnelName"  class="text" alt="allowBlank:false" readonly="readonly"/>
		</td>
		<input type="hidden" id="dyConfirmPersonnel" name="dyConfirmPersonnel" value=""/>
	<c:if test="${not empty tmpltyp}"> 
	<!--  
		<td id="auditTd3">
		请选择省公司确认人员*：<input type="text" name="provConfirmPersonnelName" id="provConfirmPersonnelName"  class="text" alt="allowBlank:false" readonly="readonly"/>
		</td>
		<input type="hidden" id="provConfirmPersonnel" name="provConfirmPersonnel" value=""/>
		-->
		<input type="hidden" id=tmpltyp name="tmpltyp" value="${tmpltyp}"/>
	</c:if>
	</tr>
	<tr>
		<td>	
			<input type="button" class="btn" value="整体确定" onclick="fullConfim();" />
		</td>		
	</tr>
  </table>
</form>
 
<eoms:xbox id="evaluationTargetTree" dataUrl="${app}/xtree.do?method=userFromDept"  
	rootId="${evaluationTargetTreeId}" rootText="${evaluationTargetName}" valueField="dyConfirmPersonnel" handler="dyConfirmPersonnelName" 
	textField="dyConfirmPersonnelName" checktype="user" single="true">
</eoms:xbox>

</div>
 
<script type="text/javascript">
   var items=[];
</script>
<c:forEach items="${evaluationEntityList}" var="evaluationEntityTemp" varStatus="status">
  <script type="text/javascript">
    items.push({
		          text : "${evaluationEntityTemp.usedTemplateName}",
				  url : "${app}/partner/evaluation/evaluationEntity.do?method=gotoScorePage&id=${evaluationEntityTemp.id}" ,
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
	
  function fullConfim(ths){
     var canSubmit='';
	 var iUser=jq('#dyConfirmPersonnel').val();
 	// var pUser=jq('#provConfirmPersonnel').val();
	 if(iUser==''){
 		    jq(this).focus();
 			alert('请选择代维公司确认人员！');
 			canSubmit='false';
 			return false;
 	 }
 	 
// 	 if(pUser==''){
 //		    jq(this).focus();
 //			alert('请选择省公司确认人员！');
 //			canSubmit='false';
 //			return false;
 //	 }
	
     Ext.Ajax.request({
         url: '${app}/partner/evaluation/evaluationEntity.do?method=fullConfimCheck',  
         params:{ 
       	  			evaluationEntityId:'${evaluationEntityId}'   				
       			}, 
       	 success:function(response,options){          				  
       			 var resulttext=response.responseText;
       			 var rslt=Ext.decode(resulttext);   
       					  
       			 if(rslt.pass==false){       					     					     
       				alert(rslt.msg);   
       				return false;
       			 }     						         					 
       			 else{     		
       			     if(window.confirm("整体确定？")){
       			        var form1=jq("#fullConfimForm");
                        form1.attr("action","${app}/partner/evaluation/evaluationEntity.do?method=fullConfim&operateType=3");
       					form1.submit();
       			     }			      					
       			 }	
      		}
      });
       
    }
</script>		
<%@ include file="/common/footer_eoms.jsp"%>	