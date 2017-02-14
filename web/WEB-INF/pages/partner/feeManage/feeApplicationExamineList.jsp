<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.*" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
 function openImport(handler){
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
	}  
</script>

<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
<form action="${app}/partner/feeManage/feeApplication.do?method=goToFeeApplicationExamineList" 
	method="post" id="queryNormalConditionForm" >
	    <input type="hidden" name="classLevel" value="${classLevel}">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">申请单编号：</td>
				<td>
					<input type="text" id="aplBillCd" name="aplBillCd"  class="text"  value="${aplBillCd}" />
				</td>	
				<td class="label">状态：</td>
				<td>
				   <html:select property="statusCd" styleId="statusCd" value="${statusCd}">
				     <html:options collection="taskWkflStatusCdOptions" property="value" labelProperty="label"/>
				   </html:select>
				</td>
				<td class="label">申请人：</td>
				<td>
				   <input type="text" id="applicantname" name="applicantname" class="text" value="${applicantname }" readOnly="true"/>
			       <input type="hidden" id="applicant" name="applicant" class="text" value="${applicant }"/>	
			  	   <eoms:xbox id="userTree" dataUrl="${app}/xtree.do?method=userFromDept"
	            	rootId="" rootText='申请人' valueField="applicant"
	            	handler="applicantname" textField="applicantname" checktype="user" single="true">
                   </eoms:xbox>
				</td>
			 </tr>
			 <tr>	
				<td class="label">流转时间：</td>
				<td colspan="3">
				   <input type="text" class="text" onclick="popUpCalendar(this, this,null,null,null,null,-1)" name="startDtTm" 
                    readonly="readonly" id="startDtTm"  value="${startDtTm}" /> 
                    &nbsp;&nbsp;到：&nbsp;&nbsp;
                   <input type="text" class="text" onclick="popUpCalendar(this, this,null,null,null,null,-1)" name="endDtTm" 
                    readonly="readonly" id="endDtTm"  value="${endDtTm}" />  
				</td>														
			</tr>					
	    </table>
	    <input type="button" styleClass="btn" value="清空" onClick="clearForm1();"/>&nbsp;&nbsp;&nbsp;
		<input type="submit" styleClass="btn" value="查询" />		
</form>      
</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>
<br/>

<logic:present name="list" scope="request">
    <display:table name="list" cellspacing="0" cellpadding="0"
		id="listEL"  class="table list" 
		requestURI="${app}/partner/feeManage/feeApplication.do" 
		export="false"  partialList="true"  pagesize="${pagesize}" size="${size}" >
		<display:column property="aplBillCd"  title="申请单编号"  />	
		<display:column  title="申请人">
		     <eoms:id2nameDB id="${listEL.applicant}" beanId="tawSystemUserDao" />
		</display:column>		
		<display:column property="taskStatus" title="状态"  />	
		<display:column  title="流转时间">
		   ${fn:substring(listEL.maintaintDtTm, 0, 19)}
		</display:column>					
		<display:column  title="操作">
		    <a  href="${app}/partner/feeManage/feeApplication.do?method=goToFeeApplicationView&feeApplicationId=${listEL.feeAplId}"
				target="view" shape="rect"> 
				查看 
			</a>
			<!-- 1：待审批 -->
		    <c:if test="${listEL.taskStatusCd=='1'}">     
		      |<a  href="${app}/partner/feeManage/feeApplication.do?method=goToFeeApplicationExamine&feeTaskWkflId=${listEL.feeTaskWkflId}"
				shape="rect"> 
				审批 
			  </a>	  	
		    </c:if>  			
		</display:column>	
    </display:table>                
</logic:present>         

<br/>

<script type="text/javascript">
   var jq=jQuery.noConflict();
 
function clearForm(form) {
  // iterate over all of the inputs for the form
  // element that was passed in
  jq(':input[name!="classLevel"]', form).each(function() { 
    var type = this.type;
    var tag = this.tagName.toLowerCase(); // normalize case
    // it's ok to reset the value attr of text inputs,
    // password inputs, and textareas
    if (type == 'text' || type == 'hidden'||type == 'password' || tag == 'textarea')
      this.value = "";
    // checkboxes and radios need to have their checked state cleared
    // but should *not* have their 'value' changed
    else if (type == 'checkbox' || type == 'radio')
      this.checked = false;
    // select elements need to have their 'selectedIndex' property set to -1
    // (this works for both single and multiple select elements)
    else if (tag == 'select')
      this.selectedIndex = -1;
  });
};
   
function clearForm1(){
  // clearForm(document.getElementById('queryNormalConditionForm'));
   clearForm(jq('#queryNormalConditionForm'));
} 
   
   Ext.onReady(function(){
      var v = new eoms.form.Validation({form:'queryNormalConditionForm'});
	  v.custom = function(){
	     var startDtTm=jq('#startDtTm').val();
	     var endDtTm=jq('#endDtTm').val();
	     if(startDtTm!=""&&endDtTm!=""){
	     if(startDtTm>endDtTm){
	       alert('开始时间不能大于终止时间');
	       jq('#startDtTm').focus();
	       return false;
	     }
	     }
	     return true;
	  }
   }); 
</script>
<%@ include file="/common/footer_eoms.jsp"%>
