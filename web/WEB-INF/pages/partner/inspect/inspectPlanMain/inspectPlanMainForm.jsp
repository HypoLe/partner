<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">

var myjs=jQuery.noConflict();
Ext.onReady(function(){
    v = new eoms.form.Validation({form:'taskOrderForm'});
    v.custom = function(){ 
    	return true;
    };
    var id='${id}';
    if(id){
    	myjs("#specialty").attr("disabled","disabled");
    	myjs("#partnerDeptName").attr("disabled","disabled");
    }
});

function beforeSubmit(){
    myjs("#specialty").removeAttr('disabled');
    myjs("#partnerDeptName").removeAttr('disabled');
}
function deletePlan(){
	if (confirm("是否确认删除?")==true){
		var form = document.getElementById('taskOrderForm');
		form.action = '${app}/partner/inspect/inspectPlan.do?method=removeInspectPlanMain&id=${planMain.id}&sheet=${sheet}';
		form.submit();
	}
}
  
</script>
 
<br/>
	
    
<form action="${app}/partner/inspect/inspectPlan.do?method=saveInspectPlanMain" method="post" id="taskOrderForm" name="taskOrderForm" >
	<table id="taskOrderTable" class="formTable">
		<input type="hidden" name="id" value="${planMain.id }">
		<input type="hidden" name="sheet" value="${sheet}" />
		<tr>
			<td class="label">
			 巡检计划名称* 
			</td>
			<td class="content">
				<input class="text" type="text" name="planName"
					id="topic" alt="allowBlank:false" value="${planMain.planName }"/>
			</td>
			<td class="label">
			巡检专业*
			</td>
			<td class="content">
				<eoms:comboBox name="specialty" id="specialty" defaultValue="${planMain.specialty}"
					initDicId="11225" alt="allowBlank:false"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				巡检单位*
			</td>
			<td class="content">
				<input type="text"  class="text"  name="partnerDeptName" id="partnerDeptName"  
					value="<eoms:id2nameDB id="${planMain.partnerDeptId}" beanId="partnerDeptDao"/>" 
					alt="allowBlank:false" readonly="readonly"/>
				 <input name="partnerDeptId" id="partnerDeptId"  value="${planMain.partnerDeptId}" type="hidden" />
				 <eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true&checktype=excludeBigNodAndLeaf&showPartnerLevelType=3&selectType=inspect"  
						rootId="" rootText="公司树"  valueField="partnerDeptId" handler="partnerDeptName" 
						textField="partnerDeptName" checktype="dept" single="true" />	
			</td>
			<c:if test="${pnrInspect2SwitchConfig.sheetInspectSwitch eq true}" var="sheetInspect">
				<td class="label">
				</td>
				<td class="content">
				</td>
			</c:if>
			<c:if test="${!sheetInspect}">
				<td class="label">
					巡检日期 
				</td>
				<td class="content">
					${year}年${month}月
				</td>
			</c:if>
		</tr>
		<c:if test="${!sheetInspect}">
		<tr>
			<td class="label">
				是否下月复制*
			</td>
			<td class="content">
				<select class="select" alt="allowBlank:false" name="copyFlag">
					<option>请选择</option>
					<option value="1" <c:if test="${planMain.copyFlag eq 1 }">selected="selected"</c:if> >是</option>
					<option value="0" <c:if test="${planMain.copyFlag eq 0 }">selected="selected"</c:if> >否</option>
				</select>
			</td>
			<td class="label">
			</td>
			<td class="content" name="copyFlag">
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="label">
				计划描述
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="content"
					id="content">${planMain.content}</textarea>
			</td> 
		</tr>
</table>

	<c:if test="${ empty sheet }" var="sheetResult">
		<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存" onclick="beforeSubmit()"></html:submit>
	</c:if>
	<c:if test="${!sheetResult}">
		<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存草稿" onclick="beforeSubmit()"></html:submit>
	</c:if>
	
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
	<c:if test="${! empty planMain.id }" var="result">
		<input type="button" class="btn" value="删除" onclick="deletePlan()"/>&nbsp;&nbsp;
	</c:if>

</form>

<%@ include file="/common/footer_eoms.jsp"%>