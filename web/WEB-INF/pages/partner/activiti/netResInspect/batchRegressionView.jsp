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

		<style type="text/css">
		
			#fullbg {
				background-color: Gray;
				left: 0px;
				opacity: 0.5;
				position: absolute;
				top: 0px;
				z-index: 3;
				filter: alpha(opacity = 50); /* IE6 */
				-moz-opacity: 0.5; /* Mozilla */
				-khtml-opacity: 0.5; /* Safari */
			}
			
		</style>


		<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
		
		<script type="text/javascript">
				
		 $(function() {
	       	var approveBoxs = window.dialogArguments.document.getElementsByName("Fruit");
	       	var selectedIDs="";
		    for(var i = 0; i < approveBoxs.length; i++) {
		    	if(approveBoxs[i].checked){
		    		selectedIDs+=approveBoxs[i].value+"#"; 
		    	}
			} 
			selectedIDs=selectedIDs.substr(0,selectedIDs.length-1);

	        $("#submitButton").click(function() {
		        var rejectReason1 = $("#rejectReason").val(); //审批人
	        
	        	$.ajax({
	        		type:"POST",
	        		url:"${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=doBatchRegression",
	        		data:{"rejectReason":rejectReason1,"selectedIDs":selectedIDs},
	        		dataType:"json",
	        		success:function(data){
	        			if(data.result=="success"){
	        				//alert("进入成功了吗？");
	        				//alert(data.content); //可以把这注销掉
	        				window.dialogArguments.refresh(); //调用父窗体中定义的刷新方法从而刷新父窗体
	                        window.close(); //关闭本页面
	        			}else{
	        				alert("进入异常了吗？");
	        				alert(data.content);
	        				window.dialogArguments.refresh(); //调用父窗体中定义的刷新方法从而刷新父窗体
	                        window.close(); //关闭本页面
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
			<html:form action="/pnrTransferNewPrecheck.do?method=doBatchApprove"
				styleId="theform">
				<table class="formTable">
					<tr>
						<td class="label">
							驳回原因
						</td>
						<td class="content" colspan="3">
							<textarea id="rejectReason" name="rejectReason" class="textarea max"></textarea>
						</td>
					</tr>
				</table>
				
		 	<!--    <input type="text" id="handle" name="handle"  value="" />
				<input type="text" id="taskId" name=taskId  value="" />
				<input type="text" id="processInstanceId" name="processInstanceId"  value="" />						
				<input type="text" id="returnPerson" name="returnPerson"  value="" />
				<input type="text" id="themeId" name="themeId"  value="" />
				<input type="text" id="theme" name="theme"  value="" />    -->
				
				<!-- buttons -->
				<div class="form-btns">
					<input type="button" id="submitButton" name="submitButton" value="提交" />
					<input type="button" id="closeButton" name="closeButton" value="关闭" />
				</div>					
			</html:form>
		</div>
<%@ include file="/common/footer_eoms.jsp"%>