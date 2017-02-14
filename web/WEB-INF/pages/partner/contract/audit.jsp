<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
<!--
	Ext.onReady(function() {
	    v = new eoms.form.Validation({form:'form1'});
	});
	
function openImport(handler){
	var el = Ext.get('infoContent');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开联系函具体信息界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭联系函具体信息界面";
	}
}
	
jQuery.noConflict(); 
jQuery(function($){ 
	var auditResult = -1;
	$("#submit_audit").click(function(){
			auditResult = $("input[name=tmp][checked]").val();
			if(auditResult==1){
				$("#input_result").val(auditResult);
				auditResult="通过";
			}
			else if(auditResult==0){
				$("#input_result").val(auditResult);
				auditResult="驳回";
			}
			else
				auditResult="未选择审批结果";
		
			var msg = "确定提交吗？\n审批结果："+auditResult;
		
		if(confirm(msg))
			 $("#form1").attr("action","contact.do?method=audit");
		else
			return false;
	})
});	
//-->
</script>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">联系函具体信息</span>
</div>

<div id="infoContent" 
		style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;"
	>
		<table id="sheet" class="formTable">
		 <tr>
				<td class="label" >
				 主题
				</td>
				<td class="content" colspan="3">
					${contactMain.subject }
				</td>
		 </tr>
		 <tr>
				<td class="label" >
				 编号
				</td>
				<td class="content" colspan="3">
					${contactMain.code }
				</td>
			</tr>
		 <tr>
				<td class="label"> 
					内容
				</td>
				<td class="content" colspan="3">
					${contactMain.content }			
				</td>
		</tr>
			<tr>	
			<td class="label" >
					发布人
					</td>
					<td class="content" >
					${contactMain.publisherName }
					</td>
			
					<td class="label" >
					 发布部门
					</td>
					<td class="content" >
						${contactMain.publisherDeptName}
					</td>
				
		</tr>
	    <tr>
			  	<td class="label" >
					 审核人
				</td>	
				<td class="content"  >	
				   <div id="fieldset_names"  class="viewer-list">${contactMain.approverName }	</div>		
				</td>
			    <td class="label" >
					 发布范围
				</td>
				<td class="content"  >
					<fieldset id="fieldset_per" >
						<legend>发布到下列组织机构或人员 </legend> 
						<div id="fieldset_names"  class="viewer-list">${contactMain.publishedRangeName }</div>		
					</fieldset>
				</td>
		</tr>  
		<tr>
			<td class="label" >
				 处理期限
				</td>
				<td class="content"  >
				 	${fn:substring(requestScope.contactMain.publishTime, 0, 19)}
				 </td>	
			<td class="label" >
				 处理期限
				</td>
				<td class="content"  >
				 	${fn:substring(requestScope.contactMain.deathTime, 0, 19)}
				 </td>	
		</tr>
		<tr>
				<td class="label">
				 证书附件 
				</td>
				<td class="content" colspan="3" height="100px">
					<eoms:download ids="${requestScope.contactMain.file }"></eoms:download>
				</td>
		<tr>
		<tr>
				<td class="label"> 是否短信 </td>
				<td class="content">
						<c:if test="${contactMain.isSendSMS==1 }">
							<c:out value="是"></c:out>
						</c:if>
						<c:if test="${contactMain.isSendSMS==0}">
								<c:out value="否"></c:out>
						</c:if>
				</td>
				<td class="label"> 是否紧急</td>
				<td class="content">
						<c:if test="${contactMain.isUrgent==1 }">
							<c:out value="是"></c:out>
						</c:if>
						<c:if test="${contactMain.isUrgent==0 }">
							<c:out value="否"></c:out>
						</c:if>
				</td>
			<tr>
		</table>
		
</div>	
  <br/><br/>		
    <a  href="contact.do?method=searchForLink&pageName=listLink&pageType=edit&mainId=${contactMain.id }" > 流转信息查看</a>

	<br/><br/>	
	<form method="post" id="form1" >
	
	<table id="sheet_form" class="formTable">
			<tr>
				<td class="label">
				 审批意见<font color="red" >*</font>
				</td>
				<td class="content" colspan="3">
				<textarea class="textarea max" name="auditContent"id="operationcontent"  	alt="allowBlank:false,vtext:'审批意见不能为空！'" >
				</textarea>
			</td>
			<tr>
			
			<tr>
				<td class="label">
				 审批结果<font color="red" >*</font>
				</td>
				<td class="content" colspan="3">
					<input type="radio" value="1"  name="tmp" />通过
					<input type="radio" value="0"  name="tmp" />驳回
					
					<input type ="hidden"  id="input_result"name="auditResult" alt="allowBlank:false,vtext:'审批结果不能为空！'" />
					
				</td>
			<tr>		
		
			<tr>
				<td  class="content" colspan="4" align="center">
					<input type="hidden" id="currentTaskId"  name="currentTaskId" value="${requestScope.currentTaskId }" >
					<input type="submit" id="submit_audit" value="提交"  class="btn" />
 				</td>
			</tr>		
	</table>
	<input type ="hidden"  id="mainId"name="mainId" value='${contactMain.id}'/>
	
	</form>
<%@ include file="/common/footer_eoms.jsp"%>