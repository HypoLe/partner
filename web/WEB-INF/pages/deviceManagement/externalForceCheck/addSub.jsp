<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<style>
pre {
 white-space: pre-wrap;       /* css-3 */
 white-space: -moz-pre-wrap !important;  /* Mozilla, since 1999 */
 white-space: -pre-wrap;      /* Opera 4-6 */
 white-space: -o-pre-wrap;    /* Opera 7 */
 word-wrap: break-word;       /* Internet Explorer 5.5+ */
}
</style>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">

 var myjs=jQuery.noConflict();
Ext.onReady(function(){
            v = new eoms.form.Validation({form:'externalForceFrom'});
            });
            
   function final(){
   		
   		$("type").value="final";
   		var formObj = document.getElementById("externalForceFrom");
 			formObj.submit();
   	}         
            
</script>
<eoms:xbox id="executeUser" dataUrl="${app}/xtree.do?method=userFromDept"  
		rootId="2" rootText="用户树"  valueField="executeUser" handler="executeUserName" 
		textField="executeUserName" checktype="user" data="${sendUserAndRoles }" single="true" />

<form action="externalForceCheck.do?method=saveSub" method="post"  id="externalForceFrom" name="externalForceFrom">
<table class="formTable">
	<tr>
		<td colspan="4"><div class="ui-widget-header" >外力盯防现场执行情况录入</div></td>
	</tr>
	<tr>
		<td class="label">现场盯防人</td>
		<td class="content">
		<input type="text" class="text"  name="executeUserName" id="executeUserName"  value="" readonly="readonly"/>
		<input type="hidden" name="executeUser" id="executeUser"  value=""/></td>
		<td class="label">是否影响光缆安全</td>
		<td>
		<select name="safety" alt="allowBlank:false" id="safety">
		<option value="">请选择</option>
		<option value="0">是</option>
		<option value="1">否</option>
		</select>
			
		</td>
	</tr>
	<tr>
	<td class="label">值班开始时间</td>
	<td><input class="text" type="text" name="startDate"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="allowBlank:false,vtype:'lessThen',link:'dutyEndTime',vtext:'开始时间不能晚于结束时间！'"
					 id="dutyStartTime" alt="allowBlank:false" readonly="readonly"/>
					</td>
	<td class="label">值班结束时间</td>
	<td><input class="text" type="text" name="endDate"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="allowBlank:false,vtype:'moreThen',link:'dutyStartTime',vtext:'结束时间不能早于开始时间！'"
					 id="dutyEndTime" alt="allowBlank:false" readonly="readonly"/></td>
					
	</tr>

	<tr>
	<td class="label">值班日志</td>
	<td  colspan="3">
	<textarea  name="dutyDiary" id="dutyDiary" class="textarea max"></textarea>
	<input type="hidden" name="planId"  id="planId" value="${planId }">
	<input type="hidden" name="inOrder" id="inOrder" value="${size }">
	<input type="hidden" name="type"  id="type" value="11">
	</td>
	</tr>
	
</table>
<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存"></html:submit>
<html:button styleClass="btn" property="method.save" styleId="method.save" value="归档" onclick="final()"></html:button>
<!-- 
<input type="button" value="返回" onclick="javascript:self.history.back();" class="clsbtn2">
 -->
</form>

<logic:present name="modelList" scope="request">

 <c:forEach items="${modelList}"  var="model" varStatus="i" >
 <div class="ui-widget-header" >外力盯防现场值班信息</div>
 <fieldset>
			<legend>值班日志${model.inOrder}</legend>
<table class="formTable">
	<tr>
		<td class="label">现场盯防人</td>
		<td class="content">
	<eoms:id2nameDB id="${model.executeUser}" beanId="tawSystemUserDao" />
		</td>
		<td class="label">是否影响光缆安全</td>
		<td>
		<logic:equal value="0" name="model" property="safety">
		是</logic:equal>
		 <logic:equal value="1" name="model" property="safety">
		否</logic:equal>
		</td>
	</tr>
	<tr>
	<td class="label">值班开始时间</td>
	<td>${model.dutyStartTime}
					</td>
	<td class="label">值班结束时间</td>
	<td>${model.dutyEndTime}</td>
			<c:set var="finalDate" value="${model.dutyEndTime}"></c:set>
			
	</tr>
	<tr>
	<td class="label">值班时间间隔</td>
	<td>${model.dutySpace}
					</td>
    
			
	</tr>

	<tr>
	<td class="label">值班日志</td>
	
	<td  colspan="3">
	<pre><bean:write name="model" property="dutyDiary" scope="page"/></pre>
	</td>
	</tr>
	
</table>
</fieldset>
 </c:forEach>
</logic:present>

<%@ include file="/common/footer_eoms.jsp"%>