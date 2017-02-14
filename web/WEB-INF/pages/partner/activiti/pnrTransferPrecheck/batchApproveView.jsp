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
		 	var linkName="provinceManageExamine";
	       	var approveBoxs = window.dialogArguments.document.getElementsByName("approveBox");
	       	var selectedIDs="";
		    for(var i = 0; i < approveBoxs.length; i++) {
		    	if(approveBoxs[i].checked){
		    		selectedIDs+=approveBoxs[i].value+"#"; 
		    	}
			} 
			selectedIDs=selectedIDs.substr(0,selectedIDs.length-1);
		//	selectedIDs="167359,167321#166094,166020#165870,165693#164093,164055#163316,163078#167359,167321#166094,166020#165870,165693#164093,164055#163316,163078#167359,167321#166094,166020#165870,165693#164093,164055#163316,163078#167359,167321#166094,166020#165870,165693#164093,164055#163316,163078#";
		
		//	$("#handle").val(selectedIDs);

	        $("#submitButton").click(function() {
		        var teskAssignee1 = $("#teskAssignee").val(); //审批人
		        var companyName1 = $("#companyName").val();//单位
		        var telephone1 =$("#telephone").val();//电话
		        var dealPerformer1 = $("#dealPerformer").val();//简要描述
	        
	        	$.ajax({
	        		type:"POST",
	        		url:"${app}/activiti/pnrTransferPrecheck/pnrTransferPrecheck.do?method=batchApprove",
	        		data:{"linkName":linkName,"teskAssignee":teskAssignee1,"companyName":companyName1,"telephone":telephone1,"dealPerformer":dealPerformer1,"selectedIDs":selectedIDs},
	        		dataType:"text",
	        		beforeSend: function(){ 
						// Handle the beforeSend event 
						//$('#submitButton').attr('disabled','disabled');//按钮灰显不可用
					//	showBg();
					},
	        		success:function(data, textStatus){
	        	    	if(data.indexOf("true")>-1){ //判断返回的字符串中是否包含true
	                      //  window.opener.refresh(); //调用父窗体中定义的刷新方法从而刷新父窗体
	                        window.dialogArguments.refresh(); //调用父窗体中定义的刷新方法从而刷新父窗体
	                        window.close(); //关闭本页面
	                      //  closeBg();
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
		
		//显示灰色 jQuery 遮罩层
			function showBg() {
			    var bh = $("body").height();
			    var bw = $("body").width();
			    $("#fullbg").css({
			        height:bh,
			        width:bw,
			        display:"block"
			    });
			   
			}
		//关闭灰色 jQuery 遮罩
			function closeBg() {
			    $("#fullbg").hide();
			}
		
		
		</script>

		<div id="sheetform">
			<html:form action="/pnrTransferPrecheck.do?method=rollback"
				styleId="theform">
				<table class="formTable">
					<tr>
						<td class="label">
							审批人
						</td>
						<td class="content">
							<input class="text" type="text" id="teskAssignee" name="teskAssignee" />
						</td>
						<td class="label">
							单位
						</td>
						<td class="content">
							<input class="text" type="text" id="companyName" name="companyName" />
						</td>
					</tr>
					<tr>
						<td class="label">
							电话
						</td>
						<td class="content" colspan="3">
							<input class="text" type="text" id="telephone" name="telephone" />
						</td>
					</tr>
					<tr>
						<td class="label">
							简要描述
						</td>
						<td class="content" colspan="3">
							<textarea id="dealPerformer" name="dealPerformer" class="textarea max"></textarea>
						</td>
					</tr>
				</table>
		
	<!--  	 		<input type="hidden" id="handle" name="handle"  value="" />
				<input type="hidden" id="taskId" name=taskId  value="" />
				<input type="hidden" id="processInstanceId" name="processInstanceId"  value="" />						
				<input type="hidden" id="returnPerson" name="returnPerson"  value="" />
				<input type="hidden" id="themeId" name="themeId"  value="" />
				<input type="hidden" id="theme" name="theme"  value="" />  -->
				
		 	<!--    <input type="text" id="handle" name="handle"  value="" />
				<input type="text" id="taskId" name=taskId  value="" />
				<input type="text" id="processInstanceId" name="processInstanceId"  value="" />						
				<input type="text" id="returnPerson" name="returnPerson"  value="" />
				<input type="text" id="themeId" name="themeId"  value="" />
				<input type="text" id="theme" name="theme"  value="" />    -->
				
				<!-- buttons -->
				<div class="form-btns">
					<input type="button" id="submitButton" name="submitButton" value="通过" />
					<input type="button" id="closeButton" name="closeButton" value="关闭" />
				</div>					
			</html:form>
		</div>
		
		<div id="fullbg"></div><!-- jQuery 遮罩层 -->

		<%@ include file="/common/footer_eoms.jsp"%>