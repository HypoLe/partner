<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.*" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script type="text/javascript">
function addFeeApplication(){
       var aform=document.createElement("form");     
       aform.action="${app}/partner/feeManage/feeRule.do?method=gotoAdd";
       aform.method="post";
       document.body.appendChild(aform);
       aform.submit();
}
 
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
<form action="${app}/partner/feeManage/feeApplication.do?method=goToFeeApplicationList" 
	method="post" id="queryNormalConditionForm">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">申请单编号：</td>
				<td>
					<input type="text" id="aplBillCd" name="aplBillCd"  class="text"  value="${aplBillCd}" />
				</td>	
				<td class="label">状态：</td>
				<td>
				   <html:select property="statusCd" styleId="statusCd" value="${statusCd}">
				     <html:options collection="aplStatusCdOptions" property="value" labelProperty="label"/>
				   </html:select>
				</td>
			 </tr>
			 <tr>	
				<td class="label">申请时间：</td>
				<td colspan="3">
				   <input type="text" class="text" onclick="popUpCalendar(this, this,null,null,null,null,-1)" name="startDtTm" 
                    readonly="readonly" id="startDtTm"  value="${startDtTm}" /> 
                    &nbsp;&nbsp;到：&nbsp;&nbsp;
                   <input type="text" class="text" onclick="popUpCalendar(this, this,null,null,null,null,-1)" name="endDtTm" 
                    readonly="readonly" id="endDtTm"  value="${endDtTm}" />  
				</td>														
			</tr>					
	    </table>	    
		<input type="submit" styleClass="btn" value="查询" />&nbsp;&nbsp;&nbsp;	
		<input type="button" styleClass="btn" value="清空" onClick="clearForm1();"/>	
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
		export="false"  partialList="true" pagesize="${pagesize}" size="${size}" >
		<display:column property="aplBillCd"  title="申请单编号"  />		
		<display:column property="year"  title="年"  />
		<display:column property="month"  title="月"  />
		<display:column property="mnyAmt" title="金额"  />
		<display:column property="status" title="状态"  />				
		<display:column  title="申请时间">
		   ${fn:substring(listEL.crDtTm, 0, 19)}
		</display:column>
		<display:column property="remark" title="备注" />
						
		<!-- 查看、修改在新页面，删除后依旧返回列表 -->		
		<display:column  title="操作" >
		    <a style="float:left;" href="${app}/partner/feeManage/feeApplication.do?method=goToFeeApplicationView&feeApplicationId=${listEL.id}"
				target="view" shape="rect"> 
				<img src="${app}/images/icons/search.gif" style="cursor:hand;display:block" /> 
			</a>
			<!-- 保存或者驳回 -->
		    <c:if test="${listEL.statusCd=='1' or listEL.statusCd=='4'}">     
		      <div style="float:left;clear:right">&nbsp;&nbsp;|&nbsp;&nbsp;</div>
		      <a style="float:left;clear:right" href="${app}/partner/feeManage/feeApplication.do?method=goToFeeApplicationEdit&feeApplicationId=${listEL.id}"
				 shape="rect"> 
				<img src="${app}/images/icons/edit.gif" style="cursor:hand;display:block" /> 
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
  jq(':input', form).each(function() {//  jQueryjQuery(expression, [context]) expression:传递一个表达式（通常由 CSS 选择器组成）; context 可选:作为待查找的 DOM 元素集、文档或 jQuery 对象。 
    var type = this.type;
    var tag = this.tagName.toLowerCase(); // normalize case
    // it's ok to reset the value attr of text inputs,
    // password inputs, and textareas
    if (type == 'text' ||type == 'hidden'|| type == 'password' || tag == 'textarea')
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
  //clearForm(jq('#queryNormalConditionForm').get()); //均可以。  jQueryjQuery(expression, [context]) expression:传递一个表达式（通常由 CSS 选择器组成）; context 可选:作为待查找的 DOM 元素集、文档或 jQuery 对象。 
   clearForm(jq('#queryNormalConditionForm'));
}
   
   Ext.onReady(function(){
      var v = new eoms.form.Validation({form:'queryNormalConditionForm'});
	  v.custom = function(){
	     var startDtTm=jq('#startDtTm').val();
	     var endDtTm=jq('#endDtTm').val();
	     if(startDtTm>endDtTm){
	       alert('开始时间不能大于终止时间');
	       jq('#startDtTm').focus();
	       return false;
	     }
	     
	     return true;
	  }
   }); 
</script>
<%@ include file="/common/footer_eoms.jsp"%>
