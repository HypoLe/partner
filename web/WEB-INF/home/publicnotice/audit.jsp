<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" 	import="java.util.*;"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
<!--
	Ext.onReady(function() {
	    v = new eoms.form.Validation({form:'form1'});
	});
	jQuery.noConflict(); 
	jQuery(function($){
		var $form = $("#form1");
		var userid = '${sessionScope.sessionform.userid}'
		$("#toNext").click(function(){
			$form.attr("action","publish.do?method=auditPassToNextAudit");
			if($("#taskOwnerIdId").val()==null||$("#taskOwnerIdId").val()==""){
				alert("审批人不能为空！");
				return false;
			}
			else if($("#taskOwnerIdId").val()==userid){
				alert("不能选择自己为下一审批人");
				return false;
			}
		})
		$("#pass").click(function(){
			$form.attr("action","publish.do?method=auditPass");
		})
		$("#reject").click(function(){
			$form.attr("action","publish.do?method=auditReject");
		})
		
	}); 
//-->
</script>
	<div  id="auditContent" >
			<form id="form1" method="post">
					<table id="sheet" class="formTable" >	
						<tr>
							<td class="label"> 
									公告内容
								</td>
								<td class="content" >
									 ${requestScope.publishInfo.publishContent }
								</td>
						</tr>
						 <tr>
							<td class="label"> 
								审批意见<font color="red" >*</font>
							</td>
							<td class="content" >
								<textarea class="textarea max" name="auditaDvice"id="auditaDvice"  
											alt="allowBlank:false,vtext:'内容不能为空！'" style="width: 99%"></textarea>
							</td>
						</tr>
						<tr>
								<td colspan="2"   style="text-align: right;">
									<fieldset>
										<legend>审批通过并移交下一审批人</legend>
											审批人：
											<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=userFromDept" rootId=""
													rootText='审批人' valueField="taskOwnerIdId" handler="taskOwnerNameId" textField="taskOwnerNameId"
													checktype="user" single="true"></eoms:xbox>
													<input type='text' id="taskOwnerNameId" name="taskOwnerName"  
														   readonly="true" value="${requestScope.auditName }"  />
													<input type='hidden' id="taskOwnerIdId" name="taskOwnerId"  value="${requestScope.auditId }" />
												<input id="toNext" type="submit" class="btn" value="通过并移交下一审批人" />	
									</fieldset>				
								</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: right;">
										<input id="pass" type="submit" class="btn" value="通过" />
										<input id="reject" type="submit" class="btn" value="驳回" />
							</td>
						</tr>
					</table>
					<input type="hidden"  id="id" name="id" value="${requestScope.publishId }">
			</form>
	</div>
<%@ include file="/common/footer_eoms.jsp"%>