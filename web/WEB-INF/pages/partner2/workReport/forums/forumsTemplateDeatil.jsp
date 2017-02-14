<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="net.fckeditor.FCKeditor"%>
<%@ page import="com.boco.partner2.workReport.webapp.form.WorkReportForm"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<logic:notEmpty name="workReportFormTitle">
<div id="qucikNavRef" style="margin-bottom: 15px">
	<a class="linkDraft" href="${app }/partner2/workReport/thread.do?method=edit&forumsId=${workReportTemplate.forumsId}">
		返回报告专题<font color="red">${workReportFormTitle}</font>
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<span>"工作报告发布"</span>
</div>
</logic:notEmpty>
<logic:empty name="workReportFormTitle">
	<div id="qucikNavRef" style="margin-bottom: 15px">
	<a class="linkDraft" href="${app }/partner2/workReport/thread.do?method=threadTree">
		返回报告发布<font color="red">${workReportFormTitle}</font>
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<span>"使用模板新增报告"</span>
</div>

</logic:empty>
<tr>
	<td>当前使用模板：</td>
	<td><font color="red">${workReportTemplate.title}</font></td>
</tr>
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
				<td class="label">
					报告标题
					<html:errors property="title" />
				</td>
				<td colspan="3" class="content">
					<html:text property="title" styleId="title" value="${workReportTemplate.title}"
						styleClass="text medium" alt="allowBlank:false" style="width:98%" />
				</td>
			</tr>
			<tr>
				<td class="label">
					有效期：
					<html:errors property="threadTypeId" />
				</td>
				<td class="content">
					<input type="text" id="validityDate" name="validityDate"
						readonly="readonly" alt="allowBlank:false" value="${workReportTemplate.validityDate}"
						onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" />
					<INPUT type="checkbox" id="noValidity" name="noValidity"
						onclick="javascript:xvalidity(this)" value="1" />
					是否永久不过期
				</td>
				
				<td class="label">
					发布范围：
				</td>
				<td class="content">
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
					项目经理：
				</td>
				<td colspan="3" class="content">
					<input type="text" value="${workReportTemplate.workReportTemplateProManager}" readonly="readonly" name="projectManager" />
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
				<td class="label">
					附件管理
				</td>
				<td colspan="3" class="content">
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
				</td>
			</tr>
		</table>

</html:form>
<script type="text/javascript">
var myJ = jQuery.noConflict();
	var deptTree;
	
	function FCKeditor_OnComplete(editorInstance) {
		window.status = editorInstance.Description;
	}
	
	Ext.onReady(function(){
		v = new eoms.form.Validation({form:'workReportForm'});
		
		if('${workReportTemplate.noValidity}'=='1'){
			document.getElementById('noValidity').checked=true;
		}
		
		deptViewer = new Ext.JsonView("view",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				<!--emptyText : '<div>未选择</div>'-->
			}
		);
		var s= '${data}';
		deptViewer.jsonData = eoms.JSONDecode(s);
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
<%@ include file="/common/footer_eoms.jsp"%>
