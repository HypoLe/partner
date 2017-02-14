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
				function selectRes(){
						var handle1 = $("#marker").val(); //得到修改后各个文本框的值
				        var taskId1 = $("#taskId").val();
				        var processInstanceId1 =$("#processInstanceId").val();
				        var returnPerson1 = $("#returnPerson").val();
				        var themeId1 = $("#titleId").val();
				        var theme1 = $("#theme").val();
				        var rejectReason1=$("#dealPerformer2").val();
			        
			        	$.ajax({
			        		type:"POST",
			        		url:"${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=rollback",
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
				}
				
				
				function pass(){	
					        var taskId1=$("#taskId").val();
					        var processInstanceId1=$("#processInstanceId").val();
					        var returnPerson1=$("#returnPerson").val();
					        var marker1=$("#marker").val();
					        var userId1=$("#userId").val();
					        var theme1=$("#theme").val();
					        var title1=$("#title").val();
					        var titleId1=$("#titleId").val();
					        var workOrderState1=$("#workOrderState").val();
					        var step1=$("#step").val();
					        var city1=$("#city").val();
					        var cityName1=$("#cityName").val();
					        var nextPerson1=$("#nextPerson").val();
					        var linkName1=$("#linkName").val();
					        var themeId1=$("#themeId").val();
					        var handle1=$("#handle").val();
					        var condition1=$("#condition").val();
					        var assignee1=$("#assignee").val();
					        var dealPerformer1=$("#dealPerformer2").val();
					        var state1=$("#state").val();
					        var stateName1=$("#stateName").val();
					        var directList1=$("#directList").val();
					        var Pages1=$("#Pages").val();
					        if(step1=='6'){
					        	var urls="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=cityManageDirectorAudit&"+stateName1+"="+state1+"&"+cityName1+"="+city1;
					        }else if(step1=='7'){
					        	var urls="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=cityViceAudit&"+stateName1+"="+state1+"&"+cityName1+"="+city1;
					        }else if(step1=='11'){
					        	var urls="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=provinceManageViceAudit&"+stateName1+"="+state1+"&"+cityName1+"="+city1;
					        }else{
					        	var urls="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=transferProgram&"+stateName1+"="+state1+"&"+cityName1+"="+city1;
					        }
					        
				        	$.ajax({
				        		type:"POST",
				        		url:urls,
				        		data:{"Pages":Pages1,"directList":directList1,"taskId":taskId1,"processInstanceId":processInstanceId1,"returnPerson":returnPerson1,"marker":marker1,"userId":userId1,"theme":theme1,"title":title1,"titleId":titleId1,"step":step1,"nextPerson":nextPerson1,"linkName":linkName1,"themeId":themeId1,"handle":handle1,"condition":condition1,"assignee":assignee1,"dealPerformer2":dealPerformer1},
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
				}
		</script>
		<div id="sheetform">
			<html:form  action="/pnrTransferNewPrecheck.do?method=transferProgramDealWith"
				styleId="theform">
				<table class="formTable">
					<!--时间 -->
					<tr>
						<td class="label">
							审批人
						</td>
						<td class="content" >
						<input class="text" type="text" id="assignee" name="assignee" value="" alt="allowBlank:false,maxLength:500,vtext:'请填写审批人'"/>
				</td>	
					</tr>
					<tr>
						<td class="label">
							简明描述/驳回原因
						</td>
						<td class="content" colspan="3">
							<textarea rows="5" cols="80" id="dealPerformer2"
								name="dealPerformer2"></textarea>
						</td>

					</tr>
				</table>
	
            <input type="hidden" id="taskId" name="taskId" value="${taskId}"/><!-- 任务ID -->
            <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程ID -->
            <input type="hidden" id="returnPerson" name="returnPerson" value="${pnrTransferOffice.createWork}"><!-- 驳回人 -->
            <input type="hidden" id="marker" name="marker" value="${marker}" /><!-- 流程标识 marker和linkName隐藏域的值应该保持一致-->
            <input type="hidden" id="userId" name="userId" value="${userId}">
            <input type="hidden" id="theme" name="theme" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            <input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            <input type="hidden" id="titleId" name="titleId" value="${pnrTransferOffice.id}"><!-- 流程ID -->
            <input type="hidden" id="state" name="state" value="handle">
            <input type="hidden" id="workOrderState" name="workOrderState" value="handle">
            <input type="text" id="step" name="step" value="${step}">
			<input type="hidden" id="city" name="city" value="${pnrTransferOffice.cityLineCharge}">
			<input type="hidden" id="cityName" name="cityName" value="${cityname}">
			<input type="hidden" id="nextPerson" name="nextPerson" value="${pnrTransferOffice.cityLineCharge}">
			<!--  <input type="hidden" name="linkName" value="workOrderAudit">-->
			<input type="hidden" name="linkName" id="linkName" value="${marker}">
			<input type="hidden" id="handle" name="handle" value="${handle}" />
			<input type="hidden" id="themeId" name="themeId" value="${themeId}" />
			<input type="hidden" id="stateName" name="stateName" value="${state}" />
			<input type="hidden" id="Pages" name="Pages" value="true" />
			
			<input type="hidden" id="condition" name="condition" value="${condition}" />
			<input type="hidden" id="directList" name="directList" value="${directList}" /><!--跳转到待回复/待办列表标识-->	
				<!-- buttons -->
				<div class="form-btns">
<!--				<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">-->
<!--				通过-->
<!--			</html:submit>&nbsp;&nbsp;-->
				<html:button styleClass="btn" property="" onclick="pass()">通过</html:button>&nbsp;&nbsp;
				<html:button styleClass="btn" property="" onclick="selectRes()">驳回</html:button>
				</div>					
			</html:form>
		</div>

		<%@ include file="/common/footer_eoms.jsp"%>