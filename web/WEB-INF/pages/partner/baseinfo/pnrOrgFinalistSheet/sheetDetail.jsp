<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<style>
	.redStar {
		color:red;
	}
</style>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<table class="formTable">
			<caption>
				<div class="header center">
					巡检组织入围资质表单详情页面
				</div>
			</caption>
</table>
<br>
<br/>

<table id="sheet" class="formTable">
	<tr>
		<td class="label">
		 	组织名称 
		</td>
		<td class="content" colspan="3">
			 ${pnrOrgFinalistSheet.orgName} 
		</td>
		</tr>
		<tr>
		<td class="label">
			巡检专业
		</td>
		<td class="content"  colspan="3">
			 ${pnrOrgFinalistSheet.speciality} 
		</td>
	</tr>
	
	<tr>
		<td class="label">
			入围时间
		</td>
		<td class="content">
				 ${pnrOrgFinalistSheet.finalistTime} 
			 
		</td>
		
		<td class="label">
			截止时间
		</td>
		<td class="content">
				 ${pnrOrgFinalistSheet.finalistEndTime} 
		</td>
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			 <pre>${pnrOrgFinalistSheet.remark}</pre>
		</td>
	</tr>
</table>


<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
});
</script>
<%@ include file="/common/footer_eoms.jsp"%>