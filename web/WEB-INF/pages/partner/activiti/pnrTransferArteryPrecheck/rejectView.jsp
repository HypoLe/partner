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
	       	var marker = window.dialogArguments.document.getElementById("marker").value;
	        var taskId = window.dialogArguments.document.getElementById("taskId").value;
	        var processInstanceId = window.dialogArguments.document.getElementById("processInstanceId").value;
	        var returnPerson = window.dialogArguments.document.getElementById("returnPerson").value;
	        var titleId = window.dialogArguments.document.getElementById("titleId").value;
	        var title = window.dialogArguments.document.getElementById("title").value;
	        
	        $("#handle").val(marker); 
	        $("#taskId").val(taskId);
	        $("#processInstanceId").val(processInstanceId);
	        $("#returnPerson").val(returnPerson);
	        $("#themeId").val(titleId);
	        $("#theme").val(title);
	        
		    //提交 /pnrTransferPrecheck.do?method=rollback
	        $("#submitButton").click(function() {
		        var handle1 = $("#handle").val(); //得到修改后各个文本框的值
		        var taskId1 = $("#taskId").val();
		        var processInstanceId1 =$("#processInstanceId").val();
		        var returnPerson1 = $("#returnPerson").val();
		        var themeId1 = $("#themeId").val();
		        var theme1 = $("#theme").val();
		        var rejectReason1=$("#rejectReason").val();
	        
	        	$.ajax({
	        		type:"POST",
	        		url:"${app}/activiti/pnrTransferPrecheck/pnrTransferArteryPrecheck.do?method=rollback",
	        		data:{"handle":handle1,"taskId":taskId1,"processInstanceId":processInstanceId1,"returnPerson":returnPerson1,"themeId":themeId1,"theme":theme1,"rejectReason":rejectReason1},
	        		dataType:"text",
	        		success:function(data, textStatus){
	        	    	if(data.indexOf("true")>-1){ //判断返回的字符串中是否包含true
	                      //  window.opener.refresh(); //调用父窗体中定义的刷新方法从而刷新父窗体
	                        window.dialogArguments.refresh(); //调用父窗体中定义的刷新方法从而刷新父窗体
	                        window.close(); //关闭本页面
                        }else{
                         	alert("error");
                        }        			
	        		}
	        	});
	        });
	        
	        $("#closeButton").click(function() {
	        	window.close();
	        });
	        
	        
    	});
		
		</script>

		<div id="sheetform">
			<html:form action="/pnrTransferArteryPrecheck.do?method=rollback"
				styleId="theform">
				<table class="formTable">
					<!--时间 -->
					<tr>
						<td class="label">
							驳回原因
						</td>
						<td class="content" colspan="3">
							<textarea rows="5" cols="80" id="rejectReason"
								name="rejectReason"></textarea>
						</td>

					</tr>
				</table>
		
		 		<input type="hidden" id="handle" name="handle"  value="" />
				<input type="hidden" id="taskId" name=taskId  value="" />
				<input type="hidden" id="processInstanceId" name="processInstanceId"  value="" />						
				<input type="hidden" id="returnPerson" name="returnPerson"  value="" />
				<input type="hidden" id="themeId" name="themeId"  value="" />
				<input type="hidden" id="theme" name="theme"  value="" />  
				
				
				<!-- buttons -->
				<div class="form-btns">
					<input type="button" id="submitButton" name="submitButton" value="提交" />
					<input type="button" id="closeButton" name="closeButton" value="关闭" />
				</div>					
			</html:form>
		</div>

		<%@ include file="/common/footer_eoms.jsp"%>