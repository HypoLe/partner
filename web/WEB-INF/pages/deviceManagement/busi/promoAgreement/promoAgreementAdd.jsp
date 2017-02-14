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
  
     function agreementOldNumberCheck(obj){
         var price=/^\d+$/;
         var quantityValue=$('agreementOldNumber').value; 
         var quantityDiv=$('agreementOldNumberDiv');
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
     function agreementNewNumberCheck(obj){
         var price=/^\d+$/;
         var quantityValue=$('agreementNewNumber').value; 
         var quantityDiv=$('agreementNewNumberDiv');
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
            v = new eoms.form.Validation({form:'promoAgreementForm'});
            v.custom = function(){ 
	            	if(!agreementOldNumberCheck()) 
	            	 {
	            		alert("数量格式输入错误，请检查!");
	            		return false;
	            	}
	            	if(!repeaterSectionMileageValidate()) 
	            	 {
	            		alert("中继段里程格式输入错误，请检查!");
	            		return false;
	            	}
	            	if(!agreementNewNumberCheck()) 
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
function promoAgrSubmit(){
			if(confirm("确定提交申请？")){
			document.getElementById("status").value='2'
			}else{
			return  false;
			}
}

 function promoSubmit(){
			document.getElementById("status").value='1'
     }
</script>
<form action="promoagreement.do?method=add" method="post" id="promoAgreementForm" name="promoAgreementForm" >
	*号为必填内容
	<table id="sheet" class="formTable">
		<tr>
		<caption>
		<div class="header center">护线宣传协议信息录入</div>
	</caption>	
		</tr>
		<tr>
		<td class="label">
				项目名称&nbsp;*
			</td>	
			<td class="content">
				<input class="text" type="text" name="itemName"
					id="itemName" alt="allowBlank:false" />
			</td>
			<td class="label">
			 审核人&nbsp;* 
			</td>
			<td class="content">
			<input type="text"  class="text"  name="approvalUserName" id="approvalUserName"   alt="allowBlank:false" readonly="readonly"/>
		   <input type="hidden" name="auditId" id="auditId"  />
			<eoms:xbox id="approvalUserName" dataUrl="${app}/xtree.do?method=userFromDept"  
		rootId="2" rootText="用户树"  valueField="auditId" handler="approvalUserName" 
		textField="approvalUserName" checktype="user" single="true" />	
			</td>
		</tr>	
	    <tr>	
	
	       <td class="label">
			中继段名称&nbsp;*
		  </td>
		   <td class="content">
			<input class="text" type="text" name="repeaterSection"
					id="repeaterSection" alt="allowBlank:false" />
  		</td>
  		  <td class="label">
			中继段里程&nbsp;*
		  </td>
		   <td class="content">
			<input class="text" type="text" name="repeaterSectionMileage"
					id="repeaterSectionMileage" alt="allowBlank:false"  onblur="javascript:repeaterSectionMileageValidate(this);" />
  		<div id="repeaterSectionMileageDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>例：1000.00 (单位为：公里)</div>
				</div>		
  		</td>
	   </tr>
	
	   <tr>	
	
	       <td class="label">
			中继段原有签订护线协议数量&nbsp;*
		  </td>
		   <td class="content">
			<input class="text" type="text" name="agreementOldNumber"
					id="agreementOldNumber" alt="allowBlank:false"  onblur="javascript:agreementOldNumberCheck(this);"/>
			<div id="agreementOldNumberDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>请输入非负整数 (单位为：份)</div>
				</div>		
  		</td>
  		  <td class="label">
			计划新签订护线协议数量（份）&nbsp;*
		  </td>
		   <td class="content">
			<input class="text" type="text" name="agreementNewNumber"
					id="agreementNewNumber" alt="allowBlank:false"  onblur="javascript:agreementNewNumberCheck(this);"/>
			<div id="agreementNewNumberDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>请输入非负整数 (单位为：份)</div>
				</div>			
  		</td>
	   </tr>
	
	    <tr>
			<td class="label">
			 实际完成情况&nbsp;* 
			</td>
			<td class="content">
				<input class="text" type="text" name="actualCompletion"
					id="actualCompletion" alt="allowBlank:false"  onblur="javascript:actualCompletionCheck(this);"/>
				  <div id="actualCompletionDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>请输入非负整数 (单位为：份)</div>
				</div>		
			</td>
			<td class="label">
				完成时间
			</td>	
			<td class="content">
				<input type="text" class="text" name="completionTime"  alt="allowBlank:false" readonly="readonly" onclick="popUpCalendar(this, this,null,null,null,true,-1)" />
			</td>
		</tr>	
	
	
		<tr>
			<td class="label">
			 未完成原因
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="unfinishedReason"
					id="unfinishedReason" alt="width:500,allowBlank:true"></textarea>
			
			</td>
				</tr>
			<tr>
			<td class="label">
				备注
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="remarks"
					id="remarks" alt="allowBlank:true"></textarea>
			</td>
		</tr>
		
		
		<input type="hidden" name="status"  id="status"  />
		</table>


	  <html:submit styleClass="btn"  property="method.save"  onclick="return promoAgrSubmit()"
	        styleId="method.save" value="提交审批"  ></html:submit>	  
			  	

</form>



<%@ include file="/common/footer_eoms.jsp"%>