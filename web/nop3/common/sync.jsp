<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>


<%@page import="utils.Nop3UtilsSyncHelper"%>
<div id="successPage" class="successPage" style="display: none;">
	<h1 class="header">操作成功</h1>
	<div class="content">您的操作已成功执行</div>
	<div class="content" style="margin-top: 15px">${msg}</div>
</div>

<script type="text/javascript">
var myJ = $.noConflict();
myJ(function() {
myJ('input#syncUserInfoAndDeptInfo').bind('click',function(event){
	myJ('div#loadIndicator').show();
	myJ('input#syncUserInfoAndDeptInfo').hide();
	myJ.ajax({
		  url: "${app}/nop3/common/syncUserInfoAndDeptInfo.jsp",
		  success: function(jsonMsg){
					myJ('div#loadIndicator').hide();
					myJ('div#successPage').show();}
   });});
 
myJ('input#syncTawSystemArea').bind('click',function(event){
	myJ('div#loadIndicator').show();
	myJ('input#syncTawSystemArea').hide();
	myJ.ajax({
		  url: "${app}/nop3/common/syncTawSystemArea.jsp",
		  success: function(jsonMsg){
					myJ('div#loadIndicator').hide();
					myJ('div#successPage').show();}
   });});  
 
});
</script>
<div id="loadIndicator" class="loading-indicator" style="display:none">正在同步数据，请耐心等候</div>
<!-- 
	<input id="syncUserInfoAndDeptInfo" type="button" value="同步用户和部门数据"/>
 -->
<input id="syncTawSystemArea" type="button" value="同步地域部门数据(合作伙伴)"/>
<%@ include file="/common/footer_eoms_innerpage.jsp"%>