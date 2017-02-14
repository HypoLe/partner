<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<%
request.setAttribute("province", "10");
%>
<script type="text/javascript">
var myjs=jQuery.noConflict();
Ext.onReady(function(){
	var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';
	new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
		treeRootId:'${province}',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
		showChkFldId:'maintainPlacea',saveChkFldId:'maintainPlace',returnJSON:false
	});
			
})
function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}
 

   
	function returnBack(){
		window.history.back();
	}
	
 function remove(){
		if(confirm('您是否要删除该信息?')){		
			var url='${app}/deviceManagement/publicizeArticle/publicizeArticle.do?method=delete&id=${publicizeArticle.id}';
			location.href=url;
		}
	}
	
function edit(){
       if(confirm('您是否要提交该信息?')){	
			var url='${app}/deviceManagement/publicizeArticle/publicizeArticle.do?method=list';
			location.href=url;
			}
	}
	
	 var v = new eoms.form.Validation({form:'maintainForm'});
  v.custom = function(){ 
  	if(confirm('您是否要提交该信息?')){
       return true;
 	}else{
       return false;
     }
   }
	
	
</script>
<html:form action="maintain.do?method=edit" method="post"
	styleId="maintainForm" >
	<!-- hidden area start -->
<input type="hidden" name="id" value="${maintain.id}" /> 
<input type="hidden" name="creatTime" value="${maintain.creatTime}" />
<input type="hidden" name="creatUser" value="${maintain.creatUser}" /> 
<input type="hidden" name="creatDept" value="${maintain.creatDept}" /> 
<input type="hidden" name="finishTime" value="${publicizeArticle.finishTime}" />
<input type="hidden" name="approvalType" value="${publicizeArticle.approvalType}" />
<input type="hidden" name="deleted" value="${maintain.deleted}" /> 
    <!-- hidden area end -->


*号为必填内容
<table id="stylesheet" class="formTable">
	<div align="center">割接修改页面</div>
	<br />

	<tr>
		<td class="label">项目名称*</td>
		<td class="content" colspan="3"><input class="text max"
			type="text" name="maintainName" id="maintainName" maxLength="100"
			class="text max" alt="allowBlank:false"
			value="${maintain.maintainName}" /></td>

	</tr>


	<tr>
	<td class="label">光缆线段*</td>
			<td>
			<input class="text max"
			type="text" name="fiberSection" id="fiberSection" maxLength="100"
			class="text max" alt="allowBlank:false"
			value="${maintain.fiberSection}" />
			<%--
			<input class="text" type="text" name="publicizeArticlePlace"
				id="publicizeArticlePlace" readonly="true" value="<eoms:id2nameDB beanId="tawSystemAreaDao" id="${publicizeArticle.publicizeArticlePlace}"/>"
				alt="allowBlank:false" /> 
				<input type="button" name="areatree" id="areatree" value="选择地点" class="btn" /> 
				<input type="hidden" name="publicizeArticleArea" id="publicizeArticleArea" value="${publicizeArticle.publicizeArticlePlace }"/>
				<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${baseStation.stationForm}
				<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${maintain.maintainPlace}"/>
			--%>
			<td class="label">割接地点*</td>
			<td class="content">
						<input class="text" type="text" readonly="true"
				name="maintainPlacea" id="maintainPlacea" value="<eoms:id2nameDB beanId="tawSystemAreaDao" id="${maintain.maintainPlace}"/>"
				alt="allowBlank:false" />
				<input type="button" name="areatree" id="areatree" value="选择地点" class="btn" /> 
				<input type="hidden" name="maintainPlace" id="maintainPlace"  value="${maintain.maintainPlace}"/>
			</td>
	</tr>
			<tr><td class="label">影响系统*</td>
			<td class="content"><input class="text" type="text"
				name="influenceSystem" id="influenceSystem"
				alt="allowBlank:false" 
				value="${maintain.influenceSystem}"
				/></td>
<td class="label">原有衰耗*</td>
			<td class="content">
			<input class="text" type="text"
				name="attenuation" id="attenuation"
				alt="allowBlank:false" 
				value="${maintain.attenuation}"
				/>
			<%--
			<eoms:comboBox name="publicizeArticleType" id="publicizeArticleType" initDicId="11219" 
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'" defaultValue="${publicizeArticle.publicizeArticleType}"/>
			    --%>
			</td></tr>
		<tr>
	
	
	<tr>
		<td class="label">预计用时*</td>
		<td class="content">
		<input class="text" type="text"
				name="expectationTimeConsuming" id="expectationTimeConsuming"
				alt="allowBlank:false" 
				value="${maintain.expectationTimeConsuming}"
				/>
		
		<%--
		<input class="text" type="text"
			name="expectationTimeConsuming" id="expectationTimeConsuming"
			value="${maintain.expectationTimeConsuming}"
			alt="allowBlank:false,vtext:'请输入完成时间期限...'"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);"
			readonly="true" />--%></td>
		<td class="label">预计日期*</td>
		<td class="content"><input class="text" type="text"
			name="expectationDate" id="expectationDate"
			value="${maintain.expectationDate}" alt="allowBlank:false" /></td>
	</tr>
	<tr>
		</td>
		<td class="label">提交审核人</td>
		<td class="content" colspan="3">
		<input class="text" type="text"
			name="approvalUser" id="approvalUser"
			value="${maintain.approvalUser}" alt="allowBlank:false" />
		<%---
		<textarea class="text max"
			type="text" name="incompleteCause" id="incompleteCause"
			maxLength="500" class="text max" alt="allowBlank:false">${publicizeArticle.incompleteCause}</textarea>
			--%>
			</td>
	</tr>
			<tr>
		<td class="label">割接原因*</td>
			<td class="content" colspan="3"><input class="text" type="text" style="width: 80%"
				name="maintainCause" id="maintainCause"
				alt="allowBlank:false"  value="${maintain.maintainCause}"/></td>
				</tr>
		<tr>
		<td class="label">申请备注</td>
			<td class="content" colspan="3">
			<textarea class="text max"
				type="text" name="remark" id="remark" alt="allowBlank:true">${maintain.remark}</textarea>
				</td>
		</tr>
	<%---<tr><td class="label">
			附件
		</td>
		<td class="content" colspan="3">
				<eoms:attachment idList="" idField="accessory" appCode="partnerBaseinfo" 
			scope="request" name="maintainForm" property="accessory"/> 
		</td></tr>---%>
</table>
<br />
<table>
	<tr>
		<td><input type="submit" class="btn" value="提交审批" onclick="edit();"/>
		</td>
		<!--  <td>${maintain.approvalType }:
		${maintainType[maintain.approvalType]}
		<c:if test="${maintainType[maintain.approvalType]=='驳回'}"> 
			<input type="button" class="btn" value="删除" onclick="remove();"/>&nbsp;	
		</c:if>-->
		
		<td>
		<input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/>
		</td>
	</tr>
</table>


</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
