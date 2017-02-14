<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">

	var deptTree;
	
	
	Ext.onReady(function(){
		v = new eoms.form.Validation({form:'workReportForm'});
		
		deptViewer = new Ext.JsonView("view",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				<!--emptyText : '<div>未选择</div>'-->
			}
		);
		//暂时屏蔽掉对部门人员的读取操作
		//var s= '${data}';
		//deptViewer.jsonData = eoms.JSONDecode(s);
		deptViewer.refresh();
		var	treeAction='${app}/xtree.do?method=userFromDept';
		deptTree = new xbox({
			btnId:'clkOrg',dlgId:'hello-dlg',
			treeDataUrl:treeAction,treeRootId:'-1',treeRootText:'部门',treeChkMode:'',treeChkType:'dept,user',
			showChkFldId:'showOrg',saveChkFldId:'org',viewer:deptViewer,returnJSON:true
		});
		
		
	});

	
	function changeAudit(checkbox){
	var el = Ext.get('isAsend'); 
		if(checkbox.checked){
		el.show();
       
		}else{
		 el.hide() ;
		}
	}	

function onSave(flag){
    //保存
  	if(flag == 0){
    	if(document.forms[0].title.value==""){
    		alert('请填写标题!');
    		return false; 
    	}
    	if(document.forms[0].showOrg.value == ""){
    		alert('请选择发布范围!');
    		return false;
    	}
    	document.forms[0].isSubmitAudit.value = "0";
    	document.forms[0].submit();
        return true;
  	}else if(flag == 1){
    	if(document.forms[0].title.value==""){
    		alert('请填写标题!');
    		return false; 
    	}
    	if(document.forms[0].showOrg.value == ""){
    		alert('请选择发布范围!');
    		return false;
    	}
    	document.forms[0].isSubmitAudit.value = "1";
    	document.forms[0].submit();
        return true;
  	}
  	else if(flag == 2){
    	if(document.forms[0].title.value==""){
    		alert('请填写标题!');
    		return false; 
    	}
        if(document.forms[0].auditUser.value.length>2){
        	document.forms[0].isSubmitAudit.value = "2";
        	//alert(document.forms[0].isSubmitAudit.value);
        	document.forms[0].submit();
        	return true;
        }else{
        	alert('请选择审核人!');
        	return false;
        }
  	}
}
function IsInteger(string ,sign) {  
    var integer; 
    if ((sign!=null) && (sign!='-') && (sign!='+')) { 
        alert('IsInter(string,sign)的参数出错： sign为null或"-"或"+"'); 
        return false; 
    } 
    integer = parseInt(string); 
    if (isNaN(integer)) { 
        return false; 
    } else if (integer.toString().length==string.length) {  
        if ((sign==null) || (sign=='-' && integer<0) || (sign=='+' && integer>0)) { 
            return true; 
        } else 
            return false;  
    } else 
        return false; 
} 

function xvalidity(checkbox){
	
		document.getElementById("validityDate").disabled=checkbox.checked;
		
	}
	
</script>
<div id="qucikNavRef" style="margin-bottom: 15px">
	<a class="linkDraft" href="${app }/partner2/workReport/thread.do?method=list&forumsId=${currentWorkReportForm.id}">
		返回报告专题<font color="red">${currentWorkReportForm.title }</font>
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<span>"工作报告发布"</span>
</div>
<logic:notEmpty name="threadMgrPriv">
<tr>
	<td>可使用的模板：</td>
	<c:forEach items="${allWorkReportTemplates}" var="awt">
		<td>
			<a href="${app}/partner2/workReport/template.do?method=showTemplateDeail&id=${awt.id}&workReportFormTitle=${currentWorkReportForm.title }"><font style="color: red">${awt.title}</font></a>
		</td>
	</c:forEach>
</tr>
</logic:notEmpty>
<logic:empty name="threadMgrPriv">
<tr>
	<td>可使用的模板：</td>
	<c:forEach items="${workReportTemplates}" var="wt">
		<td>
			<a href="${app}/partner2/workReport/template.do?method=showTemplateDeail&id=${wt.id}&workReportFormTitle=${currentWorkReportForm.title }"><font style="color: red">${wt.title}</font></a>
		</td>
	</c:forEach>
</tr>
</logic:empty>
<br/>
<br/>

<html:form action="/thread.do?method=save" method="post"
	styleId="workReportForm">

		<html:hidden property="id" />
		<html:hidden property="forumsId" />
		<html:hidden property="createrId" />
		<html:hidden property="createrName" />
		<html:hidden property="createTime" />
		<html:hidden property="threadCount" />
		<html:hidden property="isDel" />
		<html:hidden property="status" />
		<input type="hidden" id="org" name="org" />
		<input type="hidden" id="auditUser" name="auditUser" />
		<input type="hidden" id="isSubmitAudit" name="isSubmitAudit" />
		<input type="hidden" id="noValidity" name="noValidity" value="1" />

		<table class="formTable" width="75%">
			<tr>
				<td>
					报告标题
					<html:errors property="title" />
				</td>
				<td colspan="3">
					<html:text property="title" styleId="title"
						styleClass="text medium" alt="allowBlank:false" style="width:98%" />
				</td>
			</tr>
			<tr>
				<td>
					发布范围：
				</td>
				<td>
					<input type="hidden" readonly id="showOrg" name="showOrg" />
					<input type="button" id="clkOrg" name="clkOrg"
						value="选择范围" />
					<html:checkbox property="isIncludeSubDept"
						styleId="isIncludeSubDept" value="1">包括子部门</html:checkbox>
<!-- 					<html:checkbox property="isSend" styleId="isSend" value="1">发送短信到所选范围</html:checkbox> -->
					<div id="view" class="viewer-list"></div>
				</td>
			</tr>
				<tr>
				<td class="label">
					报告简述内容：
					<html:errors property="content" />
				</td>
				<td colspan="3" class="content">
					<textarea class="textarea max" alt="allowBlank:false" name="content">${workReportTemplate.content}</textarea>
				</td>
			</tr>
			<tr>
				<td>
					附件管理
				</td>
				<td colspan="3">
						<eoms:attachment idField="accessories" appCode="9" scope="request" name="workReportForm"  />
				</td>
			</tr>
			<tr>
				<td class="buttonBar bottom" colspan="3">
					<input type="button"
						value="发布报告"
						onClick="javascript:onSave(1);" class="button">
					<input type="button" value="保存草稿"
						onClick="javascript:onSave(0);" class="button">
					<logic:notEmpty name="workReportForm" property="id">
						<input type="button" value="删除"
							onclick="javascript:window.location='<html:rewrite page='/thread.do?method=delete&id=${workReportForm.id}&forumsId=${workReportForm.forumsId}'/>'"
							class="button" />
					</logic:notEmpty>

				</td>
			</tr>
		</table>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
