<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

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
})
</script>
<%---
<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
 </br>

</br>--%>
<html:form action="mediaPublicity.do?method=add" method="post"
	styleId="mediaPublicityForm">
*号为必填内容
<table id="stylesheet" class="formTable">
		<caption>
		<div class="header center">媒体宣传新增</div>
		</caption>
		<tr>
			<td class="label">项目名称*</td>
			<td class="content" colspan="3"><input class="text max"
				type="text" name="mediaName" id="mediaName" maxLength="100"
				class="text max" alt="allowBlank:false" /></td>

		</tr>
		<tr>
			<td class="label">媒体宣传形式*</td>
			<td colspan="3"><input class="text max" type="text" name=mediaStyle
				id="mediaStyle" alt="allowBlank:false" />
		</tr>
		<tr>
			<td class="label">媒体宣传时间*</td>
			<td class="content"><input class="text" type="text"
				name="mediaTime" id="mediaTime"
				alt="allowBlank:false,vtext:'请输入完成时间期限...'"
				onclick="popUpCalendar(this, this,null,null,null,true,-1);"
				readonly="true" /></td>
			<td class="label">提交审核人*</td>
			<td class="content">
		<input type="text"  class="text"  name="approvalUserName" id="approvalUserName"  value="" alt="allowBlank:false" readonly="readonly"/>
		   <input type="hidden" name="approvalUser" id="approvalUser"  value=""/>
			<eoms:xbox id="approvalUserName" dataUrl="${app}/xtree.do?method=userFromDept"  
		rootId="2" rootText="用户树"  valueField="approvalUser" handler="approvalUserName" 
		textField="approvalUserName" checktype="user" single="true" />
		</td>
		</tr>
		<tr>

			<td class="label">媒体宣传内容*</td>
			<td class="content" colspan="3">
			<%--
			<textarea class="text max"
				type="text" name="mediaContent" id="mediaContent" maxLength="500"
				class="text max" alt="allowBlank:false"></textarea>---%>
				
				<textarea class="textarea max" name="mediaContent" id="mediaContent" alt="allowBlank:false"></textarea>
					<br>
				
				</td>
		</tr>
		<tr>
			</td>
			<td class="label">宣传效果评估*</td>
			<td class="content" colspan="3">
			<%---
			<textarea class="text max"
				type="text" name="mediaAssess" id="mediaAssess" maxLength="500"
				class="text max" alt="allowBlank:false"></textarea>--%>
				
					<textarea class="textarea max" name="mediaAssess" id="mediaAssess" alt="allowBlank:false"></textarea>
					<br>
				
				</td>
		</tr>
		

		<tr>

			<td class="label">备注</td>
			<td class="content" colspan="3">
			<%---
			<textarea class="text max"
				type="text" name="remark" id="remark" alt="allowBlank:true"></textarea>--%>
				
				<textarea class="textarea max" name="remark" id="remark" alt="allowBlank:false"></textarea>
					<br></td>
		</tr>
	</table>
	<br />
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="提交审批" />
	<%--<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>----%>
</html:form>

<script type="text/javascript">
 var v = new eoms.form.Validation({form:'mediaPublicityForm'});
  v.custom = function(){ 
  	if(confirm('您是否要提交该信息?')){		
       return true;
 	}else{
       return false;
     }
   }
</script>

<%@ include file="/common/footer_eoms.jsp"%>
