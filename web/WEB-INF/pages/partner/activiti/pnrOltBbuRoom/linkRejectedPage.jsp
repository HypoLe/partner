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
		 
		 	//加载不同的下拉选项
		 	var signFlag="${signFlag}";
		 	if(signFlag=='under'){
		 		$("<option value='last'>上一环节</option>").appendTo("#rejectLinkSelect");
		 	}else if(signFlag=='above'){
		 		$("<option value='last'>上一环节</option>").appendTo("#rejectLinkSelect");
		 		$("<option value='cityManageExamine'>市运维主管审核</option>").appendTo("#rejectLinkSelect");
		 	}
		 	
	       	var marker = window.dialogArguments.document.getElementById("marker").value;
	        var taskId = window.dialogArguments.document.getElementById("taskId").value;
	        var processInstanceId = window.dialogArguments.document.getElementById("processInstanceId").value;
	        var returnPerson = window.dialogArguments.document.getElementById("returnPerson").value;
	        var titleId = window.dialogArguments.document.getElementById("titleId").value;
	        var title = window.dialogArguments.document.getElementById("title").value;
	        var rejectState = window.dialogArguments.document.getElementById("rejectState").value;
	        
	        $("#handle").val(marker); 
	        $("#taskId").val(taskId);
	        $("#processInstanceId").val(processInstanceId);
	        $("#returnPerson").val(returnPerson);
	        $("#themeId").val(titleId);
	        $("#theme").val(title);
	        
	        $("#submitButton").click(function() {
		        var rejectReason=$("#rejectReason").val();
		        var rejectLink=$("#rejectLinkSelect").val();
	        
	        	$.ajax({
	        		type:"POST",
	        		url:"${app}/activiti/pnrOltBbuRoom/pnrOltBbuRoom.do?method=linkRejected",
	        		data:{"handle":marker,"taskId":taskId,"processInstanceId":processInstanceId,"returnPerson":returnPerson,"themeId":titleId,"theme":title,"rejectReason":rejectReason,"rejectLink":rejectLink,"rejectState":rejectState},
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
			<html:form action="/pnrOltBbuRoom.do?method=rollback"
				styleId="theform">
				<table class="formTable">
					<tr>
						<td class="label">
							驳回到：
						</td>
						<td class="content" colspan="3">
							<select id="rejectLinkSelect" name="rejectLinkSelect" class="select"></select>
						</td>

					</tr>
					
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