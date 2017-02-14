<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<base target="_self" />
		<%@ include file="/common/header_eoms_form.jsp"%>
		<script language="javaScript" type="text/javascript"
			src="${app}/scripts/module/partner/ajax.js"></script>
	
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>

		<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
		<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
		
		<script type="text/javascript">
				
		 $(function() {
	        var processInstanceId = window.dialogArguments.document.getElementById("processInstanceId").value;
	        var queryFlag = window.dialogArguments.document.getElementById("queryFlag").value;
	        var taskId = window.dialogArguments.document.getElementById("taskId").value;
	        
	        $("#processInstanceId").val(processInstanceId);
	        $("#queryFlag").val(queryFlag);
	        $("#taskId").val(taskId);
	     
	        $("#submitButton").click(function() {
	        	//var rejectReason=$("#rejectReason").val();
	        	var rejectReason = $('input[name="rejectReason"]:checked').val(); 
	        	//alert(rejectReason);
	        	$.ajax({
	        		type:"POST",
	        		url:"${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=transferRollback",
	        		data:{"processInstanceId":processInstanceId,"rejectReason":rejectReason,"taskId":taskId},
	        		dataType:"json",
	        		success:function(data){
	        	    	if(data.result=="success"){//提交成功
						   alert(data.content);
						  
						}else if(data.result=="error"){//提交失败
							 alert(data.content);
						}  
						window.dialogArguments.refresh(queryFlag); //调用父窗体中定义的刷新方法从而刷新父窗体
	                    window.close(); //关闭本页面         			
	        		}
	        	});
	        });
	        
	        $("#closeButton").click(function() {
	        	window.close();
	        });
	        
	        
    	});
		
		</script>

		<div id="sheetform">
			<html:form action="/pnrTransferOfficeMaleGuest.do?method=transferRollback"
				styleId="theform">
				<table class="formTable">
					<tr>
						<td class="label">
							驳回原因
						</td>
						<td class="content" colspan="3">
							<!-- <select id="rejectReason" name="rejectReason" class="select">
								<option value="2">非本区县原因</option>
								<option value="3">非线路原因障碍</option>
							</select>
							 -->
							<input name="rejectReason" type="radio" value="2" checked />非本区县原因
							<input name="rejectReason" type="radio" value="3" />非线路原因障碍
						</td>

					</tr>
				</table>
				
				<input type="hidden" id="processInstanceId" name="processInstanceId"  value="" />						
				<input type="hidden" id="queryFlag" name="queryFlag"  value="" />
				<input type="hidden" id="taskId" name="taskId"  value="" />
				
				<!-- buttons -->
				<div class="form-btns">
					<input type="button" id="submitButton" name="submitButton" value="提交" />
					<input type="button" id="closeButton" name="closeButton" value="关闭" />
				</div>					
			</html:form>
		</div>

		<%@ include file="/common/footer_eoms.jsp"%>