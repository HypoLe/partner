<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" import="java.util.*;"%>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
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
			$form.attr("action","publish.do?method=toAudit&isToNextAudit=true");
			if($("#auditorid").val()==null||$("#auditorid").val()==""){
				alert("审批人不能为空！");
				return false;
			}
			else if($("#auditorid").val()==userid){
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
<div id="auditContent">
	<form id="form1" method="post">
		<table id="sheet" class="formTable">
		   <tr>
				<td class="label" >
				 主题<font color="red" >*</font>
				</td>
				<td class="content" colspan="3">
					${requestScope.publishInfo.subject }
				</td>
			</tr>			
			<tr>
				<td class="label">
					发布时间 <font color="red">*</font>
				</td>
				<td class="content">
					${fn:substring(requestScope.publishInfo.publishTime,0,19)}
				</td>
				<td class="label">
					有效期 <font color="red">*</font>
				</td>
				<td class="content" >
					 ${fn:substring(requestScope.publishInfo.valid, 0, 19)}
				</td>
			</tr>		
			<tr>
				<td class="label" >
				发布人 
				</td>
				<td class="content">
					 ${requestScope.publishInfo.publisherName }   
				</td>
				<td class="label" >
				发布人部门 
				</td>
				<td class="content">
					${requestScope.publishInfo.publisherDeptName }  
				</td>
			</tr>						
			<tr>
				<td class="label">
					内容 <font color="red">*</font>
				</td>
				<td class="content" colspan="3">
				  <pre>${requestScope.publishInfo.publishContent }</pre>
				</td>
			</tr>			
			<tr>
				<td class="label">
					发布范围
					<font color="red">*</font>
				</td>
				<td class="content" colspan="3">
					<fieldset id="fieldset_per">
						<legend>
							发布到下列组织机构或人员
						</legend>	 
						<div id="publishedRangeName_div" class="viewer-list">
							${publishInfo.publishedRangeName }
						</div>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td class="label">
				 附件 
				</td>
				<td class="content" colspan="3" height="100px">
					<eoms:download ids="${requestScope.publishInfo.file }"></eoms:download>
				</td>
		   <tr>
			
			<tr>
				<td class="label">
					审批意见
					<font color="red">*</font>
				</td>
				<td class="content" colspan="3">
					<textarea class="textarea max" name="taskOperateContent"
						id="taskOperateContent" alt="allowBlank:false,vtext:'内容不能为空！'"
						style="width: 99%"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center;">
					<fieldset>
						<legend>
							审批通过并移交下一审批人
						</legend>
						审批人：
						<eoms:xbox id="dutyManTree"
							dataUrl="${app}/xtree.do?method=userFromDept" rootId=""
							rootText='审批人' valueField="auditorid"
							handler="auditorname" textField="auditorname"
							checktype="user" single="true"></eoms:xbox>
						<input type='text' id="auditorname" name="auditorname" readonly class="text"/>
						<input type='hidden' id="auditorid" name="auditorid" />
						<input id="toNext" type="submit" class="btn" value="通过并移交下一审批人" />
					</fieldset>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center;">
					<input id="pass" type="submit" class="btn" value="通过" />
					<input id="reject" type="submit" class="btn" value="驳回" />
				</td>
			</tr>
		</table>
		<input type="hidden" id="id" name="id" value="${requestScope.publishInfo.id }" />
	</form>
</div>
<%@ include file="/common/footer_eoms.jsp"%>