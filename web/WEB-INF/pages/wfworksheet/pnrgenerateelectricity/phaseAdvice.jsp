<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<style type="text/css">
.x-form-column{width:320px}
</style>

<script type="text/javascript">
function checkDeal(){
    var checkArray = document.getElementsByName('deal');
    var _performer = document.getElementsByName('performer');
    var _performerType = document.getElementsByName('performerType');
    var _performerLeader = document.getElementsByName('performerLeader');
    var _deal = ",";
    var _dealType = ",";
    var _dealLeader = ",";
    var i=0;
  	for (var c0 = 0; c0 < checkArray.length; c0++) {
 	     if(checkArray[c0].checked){
 	         i=1;
 	        _deal = _deal + _performer[c0].value+",";
 	        _dealType = _dealType + _performerType[c0].value+",";
 	        _dealLeader = _dealLeader + _performerLeader[c0].value+",";
 	     }
 	}
 	if(i==1){
 	   $('dealPerformer').value = _deal.substring(1,_deal.length-1);
 	   $('dealPerformerType').value = _dealType.substring(1,_dealType.length-1);
 	   $('dealPerformerLeader').value = _dealLeader.substring(1,_dealLeader.length-1);
 	}else{
 	   alert("请选择要通知的对象");
 	}
}
var v;

Ext.onReady(function(){	
   v = new eoms.form.Validation({form:'theform'});
});
</script>
<div id="sheetform">
<html:form action="/pnrgenerateelectricity.do?method=newPerformNonFlow" styleId="theform">
<%
 String taskName = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getAttribute("taskName"));
 String dealPerformer = (String)request.getAttribute("dealPerformer");
 String dealPerformerLeader = (String)request.getAttribute("dealPerformerLeader");
 String dealPerformerType = (String)request.getAttribute("dealPerformerType");
  %>

    <input type="hidden" name="beanId" value="iInstrumentStorageMainManager"/>
    <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.generateelectricity.model.PnrGenerateElectricityMain"/>		
    <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.generateelectricity.model.PnrGenerateElectricityLink"/> 
 	<input type="hidden" name="processTemplateName" id="processTemplateName" value="InstrumentStorage" />
    <input type="hidden" name="operateName" id="operateName" value="nonFlowOperate" />
     <input type="hidden" name="dealPerformer" value=""/> 
     <input type="hidden" name="dealPerformerLeader" value=""/> 
     <input type="hidden" name="dealPerformerType" value=""/> 
<%@ include file="/WEB-INF/pages/wfworksheet/pnrgenerateelectricity/baseinputlinkhtmlnew.jsp"%>
     <table  class="listTable">
      <logic:present name="toOperaterList" scope="request"> 
      <logic:iterate id="toTask" name="toOperaterList" type="com.boco.eoms.partner.sheet.generateelectricity.model.PnrGenerateElectricityTask">  
        <tr>
          <td>
              <input type="checkbox" name="deal" id="deal" value="${toTask.operateRoleId}" onclick="check()" >
              <input type="hidden" name="performer" id="performer" value="${toTask.operateRoleId}" />
              <input type="hidden" name="performerType" id="performerType" value="${toTask.operateType}" />
              <input type="hidden" name="performerLeader" id="performerLeader" value="${toTask.taskOwner}" />
          </td>
          <td >
              <eoms:id2nameDB id="${toTask.operateRoleId}" beanId="tawSystemSubRoleDao" /><eoms:id2nameDB id="${toTask.operateRoleId}" beanId="tawSystemUserDao" />&nbsp;
          </td>
           <td colspan="2">
              <eoms:id2nameDB id="${toTask.taskOwner}" beanId="tawSystemSubRoleDao" /><eoms:id2nameDB id="${toTask.taskOwner}" beanId="tawSystemUserDao" />&nbsp;
          </td>
          <td>
              <bean:write name="toTask" property="taskDisplayName" scope="page"/>
          </td>
        </tr>
      </logic:iterate> 
     </logic:present>
     <%System.out.println("page taskName:"+taskName); %>
      <%if(taskName.equals("advice")){%>
     <input type="hidden" name="operateType" value="-11"/> 
	     <tr>
	       <td class="label">
	        <bean:message bundle="sheet" key="linkForm.remark" />
		    </td>
			<td colspan="3"> 
		           <textarea name="remark" class="textarea max" id="remark" 
		               alt="allowBlank:false,width:500,maxLength:1000,vtext:'最多输入500汉字'" alt="width:'500px'">${sheetlink.remark}</textarea>
		  </td>
		</tr>
		</table>
		<%} else if(taskName.equals("reply")){%>
     <input type="hidden" name="operateType" value="9"/> 

    	<tr>
	       <td class="label">
	        <bean:message bundle="sheet" key="linkForm.remark" />
		    </td>
			<td colspan="3">
		           <textarea name="remark" class="textarea max" id="remark" 
		               alt="allowBlank:false,width:500,maxLength:1000,vtext:'最多输入500汉字'" alt="width:'500px'">${sheetlink.remark}</textarea>
		  </td>
		</tr>
		</table>
		<%} %>
  <!-- buttons -->
		<div class="x-form-item">
			<div class="form-btns">
    		<html:submit styleClass="btn" property="method.save" styleId="method.save" onclick="javascript:checkDeal();">
            	<fmt:message key="button.save"/>
        	</html:submit></div>
		</div>
</html:form>
</div>