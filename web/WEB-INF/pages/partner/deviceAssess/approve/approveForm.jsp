<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
	function deleteAssess(){
		if (confirm("是否确定作废记录？")==true){
	      	var form = document.getElementById('approveForm');
			form.action = "${app }/partner/deviceAssess/approve.do?method=delete&id=${approveId }";
			form.submit();
	    }
	}
</script>
 
<br/>

<iframe id="formPanel-page"  name="formPanel-page" frameborder="no" 
height="500" width="100%"
	src="${app }${approve.detailUrl }"></iframe>
	<br/>
<form id="approveForm" action="${app }/partner/deviceAssess/approve.do?method=approve" method="post">
	<input type="hidden" name="approveId" value="${approveId }">
	<input type="hidden" name="assessId" value="${assessId }">
	<table id="sheet" class="formTable">
	<tr>
			<td class="label">审批意见</td>
			<td class="content" colspan="3">
			<textarea class="textarea max" 
				type="text" name="content" id="content" alt="allowBlank:true"></textarea>
			</td> 
		</tr>
		<tr>
			<td class="label">
				备注
			</td>
			<td colspan="3" class="content">
			  <textarea type="text" class="textarea max" name="remark" id="executeRemark" ></textarea>
			</td> 
		</tr>
		<tr>
		<td class="label">
				操作
			</td>
			<td class="label">
				<select name="state">
					<option value="1">审批通过</option>
					<option value="0">审批驳回</option>
				</select>
			</td>
		</tr>
		</table>
<fieldset>
			<legend>
				操作
			</legend>
  <input type="submit" Class="btn" value="提交审批" /> 		
  <input type="button" style="margin-right: 5px" onclick="deleteAssess();"
		value="作废"  class="btn"/><br/><br/>
	</fieldset>	
	</form>	
	



<%@ include file="/common/footer_eoms.jsp"%>