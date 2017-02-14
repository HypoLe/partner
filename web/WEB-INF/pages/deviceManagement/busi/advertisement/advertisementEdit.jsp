<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function() {
	var v = new eoms.form.Validation( {
		form : 'advertisementForm'
	});
	v.custom = function() {
		if (numberCheck()) {

			return true;
			windowsClose();

		} else {

			alert("数量格式输入错误，请检查!");
			return false;
		}
	}
});
function openSelectAreas() {
	window
			.open(
					eoms.appPath + '/partner/baseinfo/areaDeptTrees.do?method=selectAreas',
					'newwindow',
					'height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
}
function returnBack() {
	window.history.back();
}
function windowsClose() {
	window.close();
}
function remove() {
	if (confirm('您是否要删除该信息?')) {
		var url = '${app}/deviceManagement/advertisement/advertisement.do?method=delete&id=${advertisementList.id}';
		location.href = url;
	}
}
</script>
<html:form action="advertisement.do?method=edit" method="post"
	styleId="advertisementForm">
	<!-- hidden area start -->
	<input type="hidden" name="id" value="${advertisementList.id}" />
	<input type="hidden" name="creatTime"
		value="${advertisementList.creatTime}" />
	<input type="hidden" name="creatUser"
		value="${advertisementList.creatUser}" />
	<input type="hidden" name="creatDept"
		value="${advertisementList.creatDept}" />
	<input type="hidden" name="finishTime"
		value="${advertisementList.finishTime}" />
	<input type="hidden" name="approvalType"
		value="${advertisementList.approvalType}" />
	<input type="hidden" name="deleted"
		value="${advertisementList.deleted}" />
	<!-- hidden area end -->
*号为必填内容
<table id="stylesheet" class="formTable">
		<content tag="heading">
		<c:out value="墙体广告修改页面" />
		</content>
		<br />
		<br />
		<tr>
			<td class="label">
				项目名称*
			</td>
			<td class="content" colspan="3">
				<input class="text max" type="text" name="projectName"
					id="projectName" maxLength="100" class="text max"
					alt="allowBlank:false" value="${advertisementList.projectName}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				墙体广告地点*
			</td>
			<td>
				<%-- <input class="text" type="text" name="advertisementCity"
			id="advertisementCity" readonly="true"
			value="<eoms:id2nameDB beanId="tawSystemAreaDao" id="${advertisementList.advertisementArea}"/>"
			alt="allowBlank:false" /> <input type="button" name="areatree"
			id="areatree" value="选择地点" class="btn" /> <input type="hidden"
			name="advertisementArea" id="advertisementArea"
			value="${advertisementList.advertisementArea}" />--%>
				<input type="text" name="advertisementArea" id="advertisementArea"
					value="${advertisementList.advertisementArea}" />
				<td class="label">
					墙体广告数量（份）*
				</td>
				<td class="content">
					<input class="text" type="text" name="advertisementAmount"
						id="advertisementAmount"
						value="${advertisementList.advertisementAmount}"
						alt="allowBlank:false" onblur="javascript:numberCheck(this);" />
					<div id="advertisementAmountDiv"
						class="ui-state-highlight ui-corner-all" style="width: 150px">
						<span class="ui-icon ui-icon-circle-triangle-e"
							style="float: left; margin-right: .6em;"></span>
						<div>
							请输入非负整数 (单位为：份)
						</div>
					</div>
				</td>
		</tr>
		<tr>
			<td class="label">
				完成时间*
			</td>
			<td class="content">
				<input class="text" type="text" name="finishTime" id="finishTime"
					value="${advertisementList.finishTime}"
					alt="allowBlank:false,vtext:'请输入完成时间期限...'"
					onclick="popUpCalendar(this, this,null,null,null,true,-1);"
					readonly="true" />
			</td>
			<td class="label">
				提交审核人*
			</td>
			<td class="content">

				<input type="text" class="text" name="approvalUserName"
					id="approvalUserName"
					value=" <eoms:id2nameDB beanId="tawSystemUserDao" id="${advertisementList.approvalUser}"/>"
					alt="allowBlank:false" readonly="readonly" />
				<input type="hidden" name="approvalUser" id="approvalUser" value="" />
				<eoms:xbox id="approvalUserName"
					dataUrl="${app}/xtree.do?method=userFromDept" rootId="2"
					rootText="用户树" valueField="approvalUser" handler="approvalUserName"
					textField="approvalUserName" checktype="user" single="true" />
			</td>
		</tr>

		<tr>

			<td class="label">
				墙体广告内容*
			</td>
			<td class="content" colspan="3">
				<textarea class="text max" type="text" name="advertisementContent"
					id="advertisementContent" maxLength="500" class="text max"
					alt="allowBlank:false">${advertisementList.advertisementContent}</textarea>
			</td>
		</tr>
		<tr>
			</td>
			<td class="label">
				未完成原因
			</td>
			<td class="content" colspan="3">
				<textarea class="text max" type="text" name="incompleteCause"
					id="incompleteCause" maxLength="500" class="text max"
					alt="allowBlank:false">${advertisementList.incompleteCause}</textarea>
			</td>
		</tr>
		<tr>
			<td class="label">
				备注
			</td>
			<td class="content" colspan="3">
				<textarea class="text max" type="text" name="remark" id="remark"
					alt="allowBlank:true">${advertisementList.remark}</textarea>
			</td>
		</tr>
	</table>
	<br />
	<table>
		<tr>
			<td>
				<%-- <input type="submit" class="btn" value="提交审批" onclick="return advertisementSubmit()"/>--%>
				<html:submit styleClass="btn" property="method.save"
					onclick="return advertisementSubmit()" styleId="method.save"
					value="提交申请"></html:submit>
			</td>
			<td>
				<!--<c:if test="${advertisementType[advertisementList.approvalType]=='驳回'}"> 
			<input type="button" class="btn" value="删除" onclick="remove();"/>&nbsp;	
		</c:if>-->
				<td>
					<input type="button" style="margin-right: 5px"
						onclick="returnBack();" value="返回" class="btn" />
				</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
function advertisementSubmit() {
	if (confirm('您是否要提交该信息?')) {
		return true;
	} else {
		return false;
	}
}function numberCheck(obj){
         var price=/^\d+$/;
         var advertisementAmountValue=$('advertisementAmount').value; 
         var advertisementAmountDiv=$('advertisementAmountDiv');
      if(advertisementAmountValue.match(price) && ""!=advertisementAmountValue){
   			advertisementAmountDiv.innerHTML="格式正确";
   			advertisementAmountDiv.style.backgroundColor="#FFFF00";
   			return true;
      	}
      	else{
           	advertisementAmountDiv.innerHTML="输入不合法,请输入非负整数";
           	advertisementAmountDiv.style.backgroundColor="#FF0000";
           	return false;
      	}
    }
 </script>
<%@ include file="/common/footer_eoms.jsp"%>
