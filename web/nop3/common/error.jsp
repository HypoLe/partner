<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>


<div class="failurePage">
	<h1 class="header">操作失败</h1>
	<div class="content">您的操作已失败</div>
	<div class="content" style="margin-top: 15px">${msg}</div>
</div>

<script type="text/javascript">

var myJ = $.noConflict();
myJ(function() {

});
</script>
<%@ include file="/common/footer_eoms_innerpage.jsp"%>