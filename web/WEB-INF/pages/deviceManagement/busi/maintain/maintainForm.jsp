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
		showChkFldId:'maintainPlacea',saveChkFldId:'maintainPlace',returnJSON:false
	});
			
})
function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}
</script>
<!--<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>-->
 </br>
<html:form action="maintain.do?method=add" method="post"
	styleId="maintainForm">
*号为必填内容
<table id="stylesheet" class="formTable">
		<caption>
		<div class="header center">新增割接信息</div>
		</caption>
		<tr>
			<td class="label">割接名称*</td>
			<td class="content" colspan="3"><input class="text max"
				type="text" name="maintainName" id="maintainName" maxLength="100"
				class="text max" alt="allowBlank:false" /></td>
		</tr>
		<tr>
			<td class="label">光缆线段*</td>
			<td><input class="text" type="text" name="fiberSection"
				id="fiberSection"  value=""
				alt="allowBlank:false" /> 
				<%--
				<input type="hidden" name="publicizeArticleArea" id="publicizeArticleArea" />
			--%></td>
			<td class="label">割接地点*</td>
			<td class="content">
			<input class="text" type="text" readonly="true"
				name="maintainPlacea" id="maintainPlacea" value=""
				alt="allowBlank:false" />
				<input type="button" name="areatree" id="areatree" value="选择地点" class="btn" /> 
				<input type="hidden" name="maintainPlace" id="maintainPlace" />
				<!--<eoms:comboBox name="publicizeArticleType" id="publicizeArticleType" initDicId="11219" 
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
			<input class="text" type="text"
				name="publicizeArticleType" id="publicizeArticleType"
				alt="allowBlank:false" />--></td>
		</tr>
		<tr>
				<td class="label">影响系统*</td>
			<td class="content">
			<input class="text" type="text"
				name="influenceSystem" id="influenceSystem"
				alt="allowBlank:false" />
			<!-- 
			<input class="text" type="text"
				name="finishTime" id="finishTime"
				alt="allowBlank:false,vtext:'请输入完成时间期限...'"
				onclick="popUpCalendar(this, this,null,null,null,true,-1);"
				readonly="true" /> --></td>
				
				<td class="label">原有衰耗*</td>
			<td class="content">
			<input class="text" type="text"
				name="attenuation" id="attenuation"
				alt="allowBlank:false" />
			</td>
				</tr>
				<tr>
			</td>
		</tr>
		<tr>
		<td class="label">预计用时*</td>
			<td class="content">
			<input class="text" type="text"
				name="expectationTimeConsuming" id="expectationTimeConsuming"
				alt="allowBlank:false,vtext:'请输入完成时间期限...'"
				onclick="popUpCalendar(this, this,null,null,null,true,-1);"
				readonly="true" />
			<td class="label">预计日期*</td>
			<td class="content" colspan="3">
			<input class="text" type="text"
				name="expectationDate" id="expectationDate"
				alt="allowBlank:false,vtext:'请输入预计日期...'"
				onclick="popUpCalendar(this, this,null,null,null,false,-1);"
				readonly="true" />
			
			<!-- 
			<input class="text" type="text"
				name="expectationDate" id="expectationDate" alt="allowBlank:false" />
			<textarea class="text max"
				type="text" name="remark" id="remark" alt="allowBlank:true"></textarea> --></td>
		</tr>
		<tr>
				<td class="label">提交审核人*  
				</td>
			<td class="content">
				<input type="text"  class="text"  name="approvalUserName" id="approvalUserName"  
					value="" alt="allowBlank:false" readonly="readonly"/>
		   		<input type="hidden" name="approvalUser" id="approvalUser"  value=""/>
			<%----<eoms:xbox id="approvalUser" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
				rootId="" rootText="公司树"  valueField="approvalUserName" handler="approvalUser" 
				textField="approvalUser" checktype="dept" single="true" />---%>
			<eoms:xbox id="approvalUser" dataUrl="${app}/xtree.do?method=userFromDept"  
				rootId="2" rootText="用户树"  valueField="approvalUser" handler="approvalUserName" 
				textField="approvalUserName" checktype="user" single="true" />
			<%----<eoms:xbox id="approvalUser" dataUrl="${app}/xtree.do?method=userFromDeptComp"  
				rootId="" rootText="用户树"  valueField="approvalUserName" handler="approvalUser" 
				textField="approvalUser" checktype="user" single="true" />
				</td>----%>
		</tr>
		<tr>
		<td class="label">割接原因*</td>
			<td class="content" colspan="3"><input class="text" type="text" style="width: 80%"
				name="maintainCause" id="maintainCause"
				alt="allowBlank:false" /></td>
				</tr>
		<tr>
			<td class="label">申请备注</td>
			<td class="content" colspan="3">
			<textarea class="text max"
				type="text" name="remark" id="remark" alt="allowBlank:true"></textarea>
		</tr>
		<tr><td class="label">
			附件
		</td>
		<td class="content" colspan="3">
		<eoms:attachment idList="" idField="accessory" appCode="partnerBaseinfo" 
			scope="request" name="maintainForm" property="accessory"/> 
		<%-- <eoms:attachment idList="" idField="accessory" appCode="partnerBaseinfo" 
			scope="request" name="partnerDeptForm" property="accessory"/> 	
			<eoms:attachment idList="" idField="accessories" appCode="worksheet" name="accessory"/>
		--%></td>	</tr>
	</table>
	<br />
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="提交" />
	<html:reset styleClass="btn" styleId="method.reset" value="取消"></html:reset>
</html:form>
<script type="text/javascript">
 var v = new eoms.form.Validation({form:'maintainForm'});
  v.custom = function(){ 
  	if(confirm('您是否要提交该信息?')){
       return true;
 	}else{
       return false;
     }
   }
</script>

<%@ include file="/common/footer_eoms.jsp"%>
