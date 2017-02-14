<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">

  function repeaterSectionMileageValidate(obj){
    	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var repeaterSectionMileage=$('repeaterSectionMileage').value; //获得文本框输入的增补警示牌/宣传牌中继段里程（公里）
        var repeaterSectionMileageDiv=$('repeaterSectionMileageDiv');	//获得显示信息的div
   		if(repeaterSectionMileage.match(price) && ""!=repeaterSectionMileage){
   			repeaterSectionMileageDiv.innerHTML="格式正确";
   			repeaterSectionMileageDiv.style.backgroundColor="#FFFF00";
   			return true;
      	}else{
           	repeaterSectionMileageDiv.innerHTML="输入不合法,请输入正确的里程数 例：1000.00";
           	repeaterSectionMileageDiv.style.backgroundColor="#FF0000";
           	return false;
      	}
  }
  
     function warningBoardOldNumberCheck(obj){
         var price=/^\d+$/;
         var quantityValue=$('warningBoardOldNumber').value; 
         var quantityDiv=$('warningBoardOldNumberDiv');
      if(quantityValue.match(price) && ""!=quantityValue){
   			quantityDiv.innerHTML="格式正确";
   			quantityDiv.style.backgroundColor="#FFFF00";
   			return true;
      	}
     
      	else{
           	quantityDiv.innerHTML="输入不合法,请输入非负整数";
           	quantityDiv.style.backgroundColor="#FF0000";
           	return false;
      	}
    }
     function warningBoardNewNumberCheck(obj){
         var price=/^\d+$/;
         var quantityValue=$('warningBoardNewNumber').value; 
         var quantityDiv=$('warningBoardNewNumberDiv');
      if(quantityValue.match(price) && ""!=quantityValue){
   			quantityDiv.innerHTML="格式正确";
   			quantityDiv.style.backgroundColor="#FFFF00";
   			return true;
      	}
     
      	else{
           	quantityDiv.innerHTML="输入不合法,请输入非负整数";
           	quantityDiv.style.backgroundColor="#FF0000";
           	return false;
      	}
    }
 
      function actualCompletionCheck(obj){
         var price=/^\d+$/;
         var quantityValue=$('actualCompletion').value; 
         var quantityDiv=$('actualCompletionDiv');
      if(quantityValue.match(price) && ""!=quantityValue){
   			quantityDiv.innerHTML="格式正确";
   			quantityDiv.style.backgroundColor="#FFFF00";
   			return true;
      	}
      	else{
           	quantityDiv.innerHTML="输入不合法,请输入非负整数";
           	quantityDiv.style.backgroundColor="#FF0000";
           	return false;
      	}
    }  
    
	Ext.onReady(function(){
	            v = new eoms.form.Validation({form:'warningBoardForm'});
	            v.custom = function(){ 
	            	if(!warningBoardOldNumberCheck()) 
	            	 {
	            		alert("数量格式输入错误，请检查!");
	            		return false;
	            	}
	            	if(!repeaterSectionMileageValidate()) 
	            	 {
	            		alert("中继段里程格式输入错误，请检查!");
	            		return false;
	            	}
	            	if(!warningBoardNewNumberCheck()) 
	            	 {
	            		alert("数量格式输入错误，请检查!");
	            		return false;
	            	}
	            	if(!actualCompletionCheck()) 
	            	 {
	            		alert("数量格式输入错误，请检查!");
	            		return false;
	            	}else{
	            	return true;
	            	}
	            };  
   });
   
 
	function returnBack(){
		window.history.back();
	}
	
	 function feeAppliSubmit(){
			if(confirm("确定提交申请？")){
			document.getElementById("status").value='2'
			
			}else{
			return  false;
			}
}


 function deleteFeeAppli(){
			if(confirm("确定删除草稿？")){
			
			}else{
			return  false;
			}
}


function openSearch(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "查看处理详情";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭处理详情";
	}
}
</script>
	

 
<form action="warningboard.do?method=edit" method="post"
	id="warningBoardForm" name="warningBoardForm">
	
	<!-- hidden area start -->
		<input type="hidden" name="id" value="${warningBoard.id}" />
		<input type="hidden" name="personnelId" value="${warningBoard.personnelId}" />
		<input type="hidden" name="greatTime" value="${warningBoard.greatTime}" />
		<input type="hidden" name="areaId" value="${warningBoard.areaId}" />
		<input type="hidden" name="deleted" value="${warningBoard.deleted}" />
		<input type="hidden" name="status" id=status value="${warningBoard.status}"/>
		
	<!-- hidden area end -->
	
		<table id="sheet" class="formTable">
		<tr>
		<caption>
		<div class="header center">护线宣传协议信息</div>
	</caption>	
			
		</tr>
		<tr>
			<td class="label">
			 审核人&nbsp;* 
			</td>
			<td class="content">
			<input type="text"  class="text"  name="approvalUserName" id="approvalUserName" alt="allowBlank:false" 
			 value="<eoms:id2nameDB beanId="tawSystemUserDao" id="${warningBoard.auditId}"/>"
			 readonly="readonly" />				  
		    <input type="hidden" name="auditId" id="auditId"  value="${warningBoard.auditId}"/>
			<eoms:xbox id="approvalUserName" dataUrl="${app}/xtree.do?method=userFromDept"  
		rootId="2" rootText="用户树"  valueField="auditId" handler="approvalUserName" 
		textField="approvalUserName" checktype="user" single="true" />	
			</td>
			<td class="label">
				项目名称&nbsp;*
			</td>	
			<td class="content">
				<input class="text" type="text" name="itemName" value="${warningBoard.itemName}" 
					id="itemName" alt="allowBlank:false" />
			</td>
		</tr>	
	    <tr>	
	       <td class="label">
			增补警示牌/宣传牌中继段名称&nbsp;*
		  </td>
		   <td class="content">
			<input class="text" type="text" name="repeaterSection" value="${warningBoard.repeaterSection}" 
					id="repeaterSection" alt="allowBlank:false" />
  		</td>
  		  <td class="label">
			增补警示牌/宣传牌中继段里程&nbsp;*
		  </td>
		   <td class="content">
			<input class="text" type="text" name="repeaterSectionMileage" value="${warningBoard.repeaterSectionMileage}" 
					id="repeaterSectionMileage" alt="allowBlank:false"  onblur="javascript:repeaterSectionMileageValidate(this);"/>
  		    <div id="repeaterSectionMileageDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>例：1000.00 (单位为：公里)</div>
				</div>
  		</td>
	   </tr>
	   <tr>	
	
	       <td class="label">
			中继段原有警示牌/宣传牌数量&nbsp;*
		  </td>
		   <td class="content">
			<input class="text" type="text" name="warningBoardOldNumber" value="${warningBoard.warningBoardOldNumber}" 
					id="warningBoardOldNumber" alt="allowBlank:false"  onblur="javascript:warningBoardOldNumberCheck(this);" />
  		    <div id="warningBoardOldNumberDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>请输入非负整数 (单位为：块)</div>
				</div>	
  		</td>
  		  <td class="label">
			计划增补警示牌/宣传牌数量&nbsp;*
		  </td>
		   <td class="content">
			<input class="text" type="text" name="warningBoardNewNumber" value="${warningBoard.warningBoardNewNumber}" 
					id="warningBoardNewNumber" alt="allowBlank:false"  onblur="javascript:warningBoardNewNumberCheck(this);" />
  		    <div id="warningBoardNewNumberDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>请输入非负整数 (单位为：块)</div>
				</div>	           
  		</td>
	   </tr>
	
	    <tr>
			<td class="label">
			 实际完成情况&nbsp;* 
			</td>
			<td class="content">
				<input class="text" type="text" name="actualCompletion" value="${warningBoard.actualCompletion}" 
					id="actualCompletion" alt="allowBlank:false"  onblur="javascript:actualCompletionCheck(this);"/>
			     <div id="actualCompletionDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>请输入非负整数 (单位为：块)</div>
				</div>	
			</td>
			<td class="label">
				完成时间
			</td>	
			<td class="content">
				<input type="text" class="text" name="completionTime" value="${warningBoard.completionTime}" 
				 onclick="popUpCalendar(this, this,null,null,null,true,-1)" />
			</td>
		</tr>	
	
	
		<tr>
			<td class="label">
			 未完成原因
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="unfinishedReason" 
					id="unfinishedReason" alt="width:500,allowBlank:true">${warningBoard.unfinishedReason}</textarea>
			
			</td>
				</tr>
				<tr>
			<td class="label">
			新增补警示牌/宣传牌质量评估结果
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="assessResult" 
					id="assessResult" alt="width:500,allowBlank:true">${warningBoard.assessResult}</textarea>
			
			</td>
				</tr>
			<tr>
			<td class="label">
				备注
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="remarks" 
					id="remarks" alt="allowBlank:true">${warningBoard.remarks}</textarea>
			</td>
		</tr>
		
		</table>
     <%---- 
   <html:submit styleClass="btn" property="method.save" 
			styleId="method.save" value="保存修改" ></html:submit>--%> 
			
   <html:submit styleClass="btn" property="method.save"  onclick="return feeAppliSubmit()"
			styleId="method.save" value="提交申请" ></html:submit>		
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
	  <%---- <input type="button"  onclick="returnBack()"
		value="返回"  class="btn"/>--%>
</form>
<br></br>
<form action="warningboard.do?method=delete" method="post">
	<input type="hidden" name="id" value="${warningBoard.id}" />
<html:submit styleClass="btn"  property="method.save" 
	        styleId="method.save" value="删除"  onclick="return deleteFeeAppli()" ></html:submit>
</form>		


<%--

<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openSearch(this);">处理详情</span>
</div>	
		
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">	
				
		
		<logic:present name="warningBoardOperateList" scope="request">
	<display:table name="warningBoardOperateList" cellspacing="0" cellpadding="0"
		id="warningBoardOperateList" 
		class="table warningBoardOperateList" sort="list"
		 >
		
	
		
		
		<display:column headerClass="sortable" title="审核人">
		<eoms:id2nameDB beanId="tawSystemUserDao" id="${warningBoardOperateList.operateUserId}"/>
			
		</display:column>
		<display:column  headerClass="sortable" title="审核时间">
			${warningBoardOperateList.operateTime}
		</display:column>
		
		
		<display:column  headerClass="sortable" title="审核结果">
				<c:if test="${3==(warningBoardOperateList.operateResult)}" >审批通过</c:if>	
			<c:if test="${5==(warningBoardOperateList.operateResult)}" >审批不通过</c:if>
			
		</display:column>
		
		<display:column  headerClass="sortable" title="派往对象">
		<eoms:id2nameDB beanId="tawSystemUserDao" id="${warningBoardOperateList.operateTarget}"/>
		</display:column>
		<display:column  headerClass="sortable" title="审核意见">
			${warningBoardOperateList.operateOpinion}
		</display:column>
		<display:column  headerClass="sortable" title="备注">
			${warningBoardOperateList.operateRemark}
		</display:column>
		
	
	</display:table>
</logic:present>
	
</div>		
---%>
<%@ include file="/common/footer_eoms.jsp"%>