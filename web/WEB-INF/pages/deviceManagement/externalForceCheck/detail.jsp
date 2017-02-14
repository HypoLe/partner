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

</script>

<table class="formTable">
	<tr>
		<td colspan="4"><div class="ui-widget-header" >外力盯防现场管理信息</div>
		<input type="hidden" name="id" value="${forceModel.id}"  disabled="disable"/>
		</td>
	</tr>
	<tr>
		<td class="label">外力盯防现场地点</td>
		<td class="content">
		${forceModel.place}</td>
		<td class="label">外力盯防负责人</td>
		<td>
		<eoms:id2nameDB id="${forceModel.ownerUser}" beanId="tawSystemUserDao" />
		</td>
	</tr>
	<tr>
	<td class="label">外力盯防起始日期</td>
	<td>${forceModel.startDate}
					</td>
	<td class="label">外力盯防结束日期</td>
	<td>${forceModel.endDate}</td>
					
	</tr>
	<tr>
	<td class="label">外力盯防所属线路</td>
	<td>${forceModel.route}</td>
	<td class="label">外力盯防所属线路段</td>
	<td>${forceModel.routeStage}</td>
	</tr>
	<tr>
	<td class="label">外力盯防GPS终端设备</td>
	<td colspan="3">${forceModel.gpsFacility}</td>
	</tr>
	
</table>

<logic:present name="subModelList" scope="request">

 <c:forEach items="${subModelList}"  var="model" varStatus="i" >
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
		不安全</logic:equal>
		 <logic:equal value="1" name="model" property="safety">
		安全</logic:equal>
		</td>
	</tr>
	<tr>
	<td class="label">值班开始时间</td>
	<td>${model.dutyStartTime}
					</td>
	<td class="label">值班结束时间</td>
	<td>${model.dutyEndTime}</td>
					
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
<input type="button" value="返回" onclick="javascript:self.history.back();" class="clsbtn2">

<%@ include file="/common/footer_eoms.jsp"%>