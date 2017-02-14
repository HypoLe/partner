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
		showChkFldId:'publicizeArticlePlace',saveChkFldId:'publicizeArticleArea',returnJSON:false
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
</script>
<html:form action="publicizeArticle.do?method=edit" method="post"
	styleId="advertisementForm" >
	<!-- hidden area start -->
<input type="hidden" name="id" value="${publicizeArticle.id}" /> 
<input type="hidden" name="creatTime" value="${publicizeArticle.creatTime}" />
<input type="hidden" name="creatUser" value="${publicizeArticle.creatUser}" /> 
<input type="hidden" name="creatDept" value="${publicizeArticle.creatDept}" /> 
<input type="hidden" name="finishTime" value="${publicizeArticle.finishTime}" />
<input type="hidden" name="approvalType" value="${publicizeArticle.approvalType}" />
<input type="hidden" name="deleted" value="${publicizeArticle.deleted}" /> 
    <!-- hidden area end -->


*号为必填内容
<table id="stylesheet" class="formTable">

	<content tag="heading">
	<c:out value="宣传品修改页面" />
	</content>
	<br />
	<br />

	<tr>
		<td class="label">项目名称*</td>
		<td class="content" colspan="3"><input class="text max"
			type="text" name="projectName" id="projectName" maxLength="100"
			class="text max" alt="allowBlank:false"
			value="${publicizeArticle.projectName}" /></td>

	</tr>


	<tr>
	<td class="label">宣传单/宣传品发放地点*</td>
			<td><input class="text" type="text" name="publicizeArticlePlace"
				id="publicizeArticlePlace" readonly="true" value="<eoms:id2nameDB beanId="tawSystemAreaDao" id="${publicizeArticle.publicizeArticlePlace}"/>"
				alt="allowBlank:false" /> 
				<input type="button" name="areatree" id="areatree" value="选择地点" class="btn" /> 
				<input type="hidden" name="publicizeArticleArea" id="publicizeArticleArea" value="${publicizeArticle.publicizeArticlePlace }"/>
			
			<td class="label">计划发放宣传单/宣传品数量（份）*</td>
			<td class="content"><input class="text" type="text"
				name="publicizeArticlePlanAmount" id="publicizeArticlePlanAmount" value="${publicizeArticle.publicizeArticlePlanAmount}"
				alt="allowBlank:false" /></td>
	
	</tr>
			<tr><td class="label">实际发放宣传单/宣传品数量（份）*</td>
			<td class="content"><input class="text" type="text"
				name="publicizeArticleActualAmount" id="publicizeArticleActualAmount"
				alt="allowBlank:false" 
				value="${publicizeArticle.publicizeArticleActualAmount}"
				/></td>
<td class="label">宣传品种类名称（份）*</td>
			<td class="content">
			<eoms:comboBox name="publicizeArticleType" id="publicizeArticleType" initDicId="11219" 
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'" defaultValue="${publicizeArticle.publicizeArticleType}"/>
			    
			</td></tr>
		<tr>
	
	
	<tr>
		<td class="label">完成时间*</td>
		<td class="content"><input class="text" type="text"
			name="finishTime" id="finishTime"
			value="${publicizeArticle.finishTime}"
			alt="allowBlank:false,vtext:'请输入完成时间期限...'"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);"
			readonly="true" /></td>
		<td class="label">提交审核人*</td>
		<td class="content"><input class="text" type="text"
			name="approvalUser" id="approvalUser"
			value="${publicizeArticle.approvalUser}" alt="allowBlank:false" /></td>
	</tr>
	<tr>
		</td>
		<td class="label">未完成原因</td>
		<td class="content" colspan="3"><textarea class="text max"
			type="text" name="incompleteCause" id="incompleteCause"
			maxLength="500" class="text max" alt="allowBlank:false">${publicizeArticle.incompleteCause}</textarea></td>
	</tr>
	<tr>
		<td class="label">备注</td>
		<td class="content" colspan="3"><textarea class="text max"
			type="text" name="remark" id="remark" alt="allowBlank:true">${publicizeArticle.remark}</textarea></td>
	</tr>
</table>
<br />
<table>
	<tr>
		<td><input type="submit" class="btn" value="提交审批" onclick="edit();"/>
		</td>
		<td>${publicizeArticle.approvalType }:
		${publicizeArticleType[publicizeArticle.approvalType]}
		<c:if test="${publicizeArticleType[publicizeArticle.approvalType]=='驳回'}"> 
			<input type="button" class="btn" value="删除" onclick="remove();"/>&nbsp;	
		</c:if>
		
		<td>
		<input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/>
		</td>
	</tr>
</table>


</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
