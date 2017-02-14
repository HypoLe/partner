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
    //Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
})
</script>
<%----
<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
 </br>
---%>
</br>
<html:form action="publicityCatchline.do?method=add" method="post"
	styleId="publicityCatchlineForm">
*号为必填内容
<table id="stylesheet" class="formTable">
		<div align="center">媒体宣传标语新增</div>
		<tr>
			<td class="label">媒体宣传标语*</td>
			<td class="content" colspan="3"><textarea  rows="10" class="textarea max"
				type="text" name="publicityCatchline" id="publicityCatchline" maxLength="500"
			 alt="allowBlank:false"></textarea></td>

		</tr>
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
			<td class="label">备注</td>
			<td class="content" colspan="3"><textarea class="text max"
				type="text" name="remark" id="remark" alt="allowBlank:true"></textarea></td>
		</tr>
	</table>
	<br />
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="提交审批" />
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</html:form>

<script type="text/javascript">
 var v = new eoms.form.Validation({form:'publicityCatchlineForm'});
  v.custom = function(){ 
  	if(confirm('您是否要提交该信息?')){		
       return true;
 	}else{
       return false;
     }
   }
</script>

<%@ include file="/common/footer_eoms.jsp"%>
