<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
request.setAttribute("province", "10");
%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
	
<script type="text/javascript">
var myjs=jQuery.noConflict();
Ext.onReady(function(){
   // Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
	var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';

   
	new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
		treeRootId:'${province}',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
		showChkFldId:'publicizeArticlePlace',saveChkFldId:'publicizeArticleArea',returnJSON:false
	});
			
})
function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}

 function publicizeArticleCheck(obj){
         var price=/^\d+$/;
         var quantityValue=$('publicizeArticleActualAmount').value; 
         var quantityDiv=$('publicizeArticleActualNumberDiv');
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

 function publicizeArticleaCheck(obj){
         var price=/^\d+$/;
         var quantityValue=$('publicizeArticlePlanAmount').value; 
         var quantityDiv=$('publicizeArticleActualaNumberDiv');
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
</script>
<%--
<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>--%>
 </br>
<html:form action="publicizeArticle.do?method=add" method="post"
	styleId="publicizeArticleForm">
*号为必填内容
<table id="stylesheet" class="formTable">

		<caption>
		<div class="header center">新增</div>
		</caption>

		<tr>
			<td class="label">项目名称*</td>
			<td class="content" colspan="3"><input class="text max"
				type="text" name="projectName" id="projectName" maxLength="100"
				class="text max" alt="allowBlank:false" /></td>
		</tr>

		<tr>
			<td class="label">宣传单/宣传品发放地点*</td>
			<td><input class="text" type="text" name="publicizeArticlePlace"
				id="publicizeArticlePlace" readonly="true" value=""
				alt="allowBlank:false,vtext:'请选择地点...'"/> 
				<input type="button" name="areatree" id="areatree" value="选择地点" class="btn" /> 
				<input type="hidden" name="publicizeArticleArea" id="publicizeArticleArea" alt="allowBlank:false,vtext:'请选择地点...'" />
			
			
			<td class="label">计划发放宣传单/宣传品数量（份）*</td>
			<td class="content"><input class="text" type="text"
				name="publicizeArticlePlanAmount" id="publicizeArticlePlanAmount"  vtype:'number'
				alt="allowBlank:false" />
								<div id="publicizeArticleActualaNumberDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>请输入非负整数 (单位为：份)</div>
				</div>	
				</td>
		</tr>
		<tr><td class="label">实际发放宣传单/宣传品数量（份）*</td>
			<td class="content"><input class="text" type="text"
				name="publicizeArticleActualAmount" id="publicizeArticleActualAmount"
				alt="allowBlank:false" vtype:'number' onblur="javascript:publicizeArticleCheck(this);"/>
				<div id="publicizeArticleActualNumberDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>请输入非负整数 (单位为：份)</div>
				</div>	
				</td>
<td class="label">宣传品种类名称</td>
			<td class="content">
				<eoms:comboBox name="publicizeArticleType" id="publicizeArticleType" initDicId="11219" 
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
			<!--<input class="text" type="text"
				name="publicizeArticleType" id="publicizeArticleType"
				alt="allowBlank:false" />--></td></tr>
		<tr>
		
		
		
			<td class="label">完成时间*</td>
			<td class="content"><input class="text" type="text"
				name="finishTime" id="finishTime"
				alt="allowBlank:false,vtext:'请输入完成时间期限...'"
				onclick="popUpCalendar(this, this,null,null,null,true,-1);"
				readonly="true" /></td>
			<td class="label">提交审核人*</td>
			<td class="content">
			
			<input type="text" class="text"
				name="approvalUserName" id="approvalUserName" value=""
				alt="allowBlank:false" readonly="readonly" /> 
				<input type="hidden"
				name="approvalUser" id="approvalUser" value="" alt="allowBlank:false,vtext:'请选择审核人...'" /> 
				<eoms:xbox
				id="approvalUserName" dataUrl="${app}/xtree.do?method=userFromDept"
				rootId="2" rootText="用户树" valueField="approvalUser"
				handler="approvalUserName" textField="approvalUserName"
				checktype="user" single="true" />
			<!-- <input class="text" type="text"
				name="approvalUser" id="approvalUser" alt="allowBlank:false" />--></td>
		</tr>
		<tr>
			</td>
			<td class="label">提交原因</td>
			<td class="content" colspan="3"><textarea class="text max"
				type="text" name="incompleteCause" id="incompleteCause"
				maxLength="500" class="text max" alt="allowBlank:false"></textarea></td>
		</tr>
		<tr>
			<td class="label">备注</td>
			<td class="content" colspan="3"><textarea class="text max"
				type="text" name="remark" id="remark" alt="allowBlank:true"></textarea></td>
		</tr>
	</table>

	<br />
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="提交审批" />
	<%---<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>--%>
</html:form>
<script type="text/javascript">
 var v = new eoms.form.Validation({form:'publicizeArticleForm'});
  v.custom = function(){ 
  	if(confirm('您是否要提交该信息?')){		
       return true;
 	}else{
       return false;
     }
   }
</script>

<%@ include file="/common/footer_eoms.jsp"%>
