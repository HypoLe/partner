<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'theForm'});
	v.custom = function(){
		var x1 = parseFloat($('cableLength1').value);
		var x2 = parseFloat($('cableLength2').value);
		var x3 = parseFloat($('cableLength3').value);
		var x4 = parseFloat($('cableLength4').value);
		var x5 = parseFloat($('cableLength5').value);
		if(x1<0||x2<0||x3<0||x4<0||x5<0){
			alert("光缆长度输入整数或者小数");
			return false;
		}
		$('cableLength').value= x1+x2+x3+x4+x5;
		var extraHint="确认修改该条记录吗？";
		extraHint += "光缆长度计算值为:"+ (x1+x2+x3+x4+x5);
		if(confirm(extraHint)){
				return true;
		}else{ 
			return false;
		}	
	};	
});
</script>

<content tag="heading">
<c:out value="数据编辑页面" />
</content>
<br />
<br />

<c:set var="returnBtn">
	<input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回" />
</c:set>

<html:form action="circuitLength.do?method=edit" method="post"
	styleId="theForm">
	<fieldset>
	<table id="sheet" class="formTable">
		<!-- Set hidden area here. -->
		<input type="hidden" name="id" id="id" value="${circuit.id}" />
		<input type="hidden" name="sendUser" id="sendUser" value="${circuit.sendUser}" />
		<input type="hidden" name="status" id="status" value="${circuit.status}" />
		<input type="hidden" name="statusCnName" id="statusCnName" value="${circuit.statusCnName}" />
		<input type="text" name="cableLength" id="cableLength" value="${circuit.cableLength}" />
		<input type="hidden" name="currentTaskOwner" id="currentTaskOwner" value="${circuit.currentTaskOwner}" />
		<input type="hidden" name="maintainUser" id="maintainUser" value="${circuit.maintainUser}" />
		<input type="hidden" name="monitorCompany" id="monitorCompany" value="${circuit.monitorCompany}" />
		<input type="hidden" name="city" id="city" value="${circuit.city}" />
		<input type="hidden" name="country" id="country" value="${circuit.country}" />
		<input type="hidden" name="mainMonitorOption" id="mainMonitorOption" value="${circuit.mainMonitorOption}" />
		<input type="hidden" name="mainCityOption" id="mainCityOption" value="${circuit.mainCityOption}" />
		<input type="hidden" name="monthFlag" id="monthFlag" value="${circuit.monthFlag}" />
		<input type="hidden" name="yearFlag" id="yearFlag" value="${circuit.yearFlag}" />
		<!-- Set hidden area Over. -->

		<tr>
			<td class="label">分公司</td>
			<td><eoms:id2nameDB id="${circuit.city}" beanId="tawSystemAreaDao" /></td>
			<td class="label">归属县公司</td>
			<td><eoms:id2nameDB id="${circuit.country}" beanId="tawSystemAreaDao" /></td>
		</tr>
		<tr>
			<td class="label">代维公司*</td>
			<td><eoms:id2nameDB id="${circuit.monitorCompany}" beanId="tawSystemAreaDao" /></td>
		</tr>
		<tr>
			<td class="label">年</td>
			<td>${circuit.yearFlag }</td>
			<td class="label">月</td>
			<td>${circuit.monthFlag }</td>
		</tr>
		<tr>
			<td class="label">架空光缆（皮长公里）*</td>
			<td><input type="text" class="text" name="cableLength1"
				id="cableLength1" alt="allowBlank:false,maxLength:40" value="${circuit.cableLength1 }"/></td>
			<td class="label">增减*</td>
			<td><input type="text" class="text" name="cableLength1x"
				id="cableLength1x" alt="allowBlank:false,maxLength:40" value="${circuit.cableLength1x }" /></td>
		</tr>
		<tr>
			<td class="label">管道（直埋）光缆（皮长公里）*</td>
			<td><input type="text" class="text" name="cableLength2"
				id="cableLength2" alt="allowBlank:false,maxLength:40" value="${circuit.cableLength2 }" /></td>
			<td class="label">增减*</td>
			<td><input type="text" class="text" name="cableLength2x"
				id="cableLength2x" alt="allowBlank:false,maxLength:40" value="${circuit.cableLength2x }" /></td>
		</tr>
		<tr>
			<td class="label">同路由架空光缆（皮长公里）*</td>
			<td><input type="text" class="text" name="cableLength3"
				id="cableLength3" alt="allowBlank:false,maxLength:40" value="${circuit.cableLength3 }" /></td>
			<td class="label">增减*</td>
			<td><input type="text" class="text" name="cableLength3x"
				id="cableLength3x" alt="allowBlank:false,maxLength:40" value="${circuit.cableLength3x }" /></td>
		</tr>
		<tr>
			<td class="label">同路由管道（直埋）光缆（皮长公里）*</td>
			<td><input type="text" class="text" name="cableLength4"
				id="cableLength4" alt="allowBlank:false,maxLength:40" value="${circuit.cableLength4 }" /></td>
			<td class="label">增减*</td>
			<td><input type="text" class="text" name="cableLength4x"
				id="cableLength4x" alt="allowBlank:false,maxLength:40" value="${circuit.cableLength4x }" /></td>
		</tr>
		<tr>
			<td class="label">空闲管道（管程公里）*</td>
			<td><input type="text" class="text" name="cableLength5"
				id="cableLength5" alt="allowBlank:false,maxLength:40" value="${circuit.cableLength5 }" /></td>
			<td class="label">增减*</td>
			<td><input type="text" class="text" name="cableLength5x"
				id="cableLength5x" alt="allowBlank:false,maxLength:40" value="${circuit.cableLength5x }" /></td>
		</tr>
		<c:if test="${templateId!=null}">
			<tr>
				<td class="label">
					关联考核模板
				</td>
				<td id="templateLink" colspan="3">
					<a href="${app}/partner2/appraisal.do?method=showDetail&templateId=${templateId}" target="_blank">
						${templateName}
					</a>
				</td>
			</tr>
		</c:if>
	</table>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="修改记录"></html:submit>
		<input type="hidden" id="templateId" value="" />
	<input type="button" id="${circuit.id}" name="showAppraisals" class="btn" value="更改考核模板"/>
</html:form>

<script type="text/javascript">
var myJ = $.noConflict();
myJ(function() {
	myJ("input[name='showAppraisals']").click(function(event){
		//点击后防止多次提交
		myJ(this).attr('disabled','disabled');
		var _AppraisalsWindow = window.open("${app}/partner2/appraisal.do?method=list&operateType=edit",null,"left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes");
		//窗口打开后恢复按钮
		myJ(this).removeAttr('disabled');
	});
});
function returnBack(){
	window.history.back(-1);
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
