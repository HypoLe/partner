<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function(){
    //Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
     var v = new eoms.form.Validation({form:'advertisementForm'});
         v.custom = function(){ 
          if(!numberCheck()){
	            alert("数量格式输入错误，请检查!");
	            return false;
	         }
	        else{
	           return true;
	            }
	  }
});
</script>
<!-- ---
<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div> 
</br>-->
<html:form action="advertisement.do?method=add" method="post"
	styleId="advertisementForm">
*号为必填内容
<table id="stylesheet" class="formTable">
		<caption>
		<div class="header center">墙体广告新增</div>
		</caption>
		<tr>
			<td class="label">项目名称*</td>
			<td class="content" colspan="3"><input class="text max"
				type="text" name="projectName" id="projectName" maxLength="100"
				class="text max" alt="allowBlank:false" /></td>
		</tr>
		<tr>
			<td class="label">墙体广告地点*</td>
			<%--	<td><input class="text" type="text" name="advertisementCity"
				id="advertisementCity" readonly="true" value=""
				alt="allowBlank:false" /> 
				<input type="button" name="areatree" id="areatree" value="选择地点" class="btn" /> 
				<input type="hidden" name="advertisementArea" id="advertisementArea" />
				--%>
			<td class="content"><input class="text" type="text"
				name="advertisementArea" id="advertisementArea" class="text"
				alt="allowBlank:false" /></td>
			<td class="label">墙体广告数量*</td>
			<td class="content"><input class="text" type="text"
				name="advertisementAmount" id="advertisementAmount"
				alt="allowBlank:false" onblur="javascript:numberCheck(this);"/>
			<div id="advertisementAmountDiv"
				class="ui-state-highlight ui-corner-all" style="width:150px">
			<span class="ui-icon ui-icon-circle-triangle-e"
				style="float: left; margin-right: .6em;"></span>
			<div>请输入非负整数 (单位为：份)</div>
			</div>
			</td>
		</tr>
		<tr>
			<td class="label">完成时间*</td>
			<td class="content"><input class="text" type="text"
				name="finishTime" id="finishTime"
				alt="allowBlank:false,vtext:'请输入完成时间期限...'"
				onclick="popUpCalendar(this, this,null,null,null,true,-1);"
				readonly="true" /></td>
			<td class="label">提交审核人*</td>
			<td class="content"><input type="text" class="text"
				name="approvalUserName" id="approvalUserName" value=""
				alt="allowBlank:false" readonly="readonly" /> 
				<input type="hidden"
				name="approvalUser" id="approvalUser" value="" />
				 <eoms:xbox
				id="approvalUserName" dataUrl="${app}/xtree.do?method=userFromDept"
				rootId="2" rootText="用户树" valueField="approvalUser"
				handler="approvalUserName" textField="approvalUserName"
				checktype="user" single="true" /></td>
			</td>
		</tr>
		<tr>
			<td class="label">墙体广告内容*</td>
			<td class="content" colspan="3">
			<textarea class="textarea max" name="advertisementContent" id="advertisementContent" alt="allowBlank:false"></textarea>
			<%---<textarea class="text max"
				type="text" name="advertisementContent" id="advertisementContent"
				maxLength="500" class="text max" alt="allowBlank:false"></textarea>
				---%>
				</td>
		</tr>
		<tr>
			</td>
			<td class="label">未完成原因</td>
			<td class="content" colspan="3">
			<textarea class="textarea max" name="incompleteCause" id="incompleteCause" alt="allowBlank:false"></textarea><%--
			<textarea class="text max"
				type="text" name="incompleteCause" id="incompleteCause"
				maxLength="500" class="text max" alt="allowBlank:false"></textarea>---%></td>
		</tr>
		<tr>
			<td class="label">备注</td>
			<td class="content" colspan="3">
			<textarea class="textarea max" name="remark" id="remark" alt="allowBlank:false"></textarea>
			<%---<textarea class="text max"
				type="text" name="remark" id="remark" alt="allowBlank:true"></textarea>---%></td>
		</tr>
	</table>
	<br />
	<html:submit styleClass="btn" property="method.save"
		onclick="return advertisementSubmit()" styleId="method.save"
		value="提交审批" />
	<%--
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>---%>
</html:form>
<script type="text/javascript">
  function advertisementSubmit(){ 
  	if(confirm('您是否要提交该信息?')){		
       return true;
 	}else{
       return false;
     }
   }
  function numberCheck(obj){
         var price=/^\d+$/;
         var advertisementAmountValue=$('advertisementAmount').value; 
         var advertisementAmountDiv=$('advertisementAmountDiv');
      if(advertisementAmountValue.match(price) && ""!=advertisementAmountValue){
   			advertisementAmountDiv.innerHTML="格式正确";
   			advertisementAmountDiv.style.backgroundColor="#FFFF00";
   			return true;
      	}
     
      	else{
           	advertisementAmountDiv.innerHTML="输入不合法,请输入非负整数";
           	advertisementAmountDiv.style.backgroundColor="#FF0000";
           	return false;
      	}
    }
</script>
<%@ include file="/common/footer_eoms.jsp"%>
