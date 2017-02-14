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

<script type="text/javascript">
var myjs=jQuery.noConflict();

	function returnBack(){
		window.history.back();
	}
	
 function remove(){
		if(confirm('您是否要删除该信息?')){		
			var url='${app}/deviceManagement/mediaPublicity/mediaPublicity.do?method=delete&id=${mediaPublicityList.id}';
			location.href=url;
		}
	}
	
function edit(){
       if(confirm('您是否要提交该信息?')){	
			var url='${app}/deviceManagement/mediaPublicity/mediaPublicity.do?method=edit';
			location.href=url;
			}
	}
 

</script>
<html:form action="mediaPublicity.do?method=edit" method="post"
	styleId="mediaPublicityForm" >
	<!-- hidden area start -->
<input type="hidden" name="id" value="${mediaPublicityList.id}" /> 
<input type="hidden" name="creatTime" value="${mediaPublicityList.creatTime}" />
<input type="hidden" name="creatUser" value="${mediaPublicityList.creatUser}" /> 
<input type="hidden" name="creatDept" value="${mediaPublicityList.creatDept}" /> 
<input type="hidden" name="deleted" value="${mediaPublicityList.deleted}" /> 
    <!-- hidden area end -->


*号为必填内容
<table id="stylesheet" class="formTable">

	<content tag="heading">
	<c:out value="墙体广告修改页面" />
	</content>
	<br />
	<br />

	<tr>
		<td class="label">项目名称*</td>
		<td class="content" colspan="3"><input class="text max"
			type="text" name="mediaName" id="mediaName" maxLength="100"
			class="text max" alt="allowBlank:false"
			value="${mediaPublicityList.mediaName}" /></td>

	</tr>


	<tr>
		<td class="label">媒体宣传形式*</td>
				<td class="content" colspan="3"><input class="text max"
			type="text" name="mediaStyle" id="mediaStyle" maxLength="100"
			class="text max" alt="allowBlank:false"
			value="${mediaPublicityList.mediaStyle}" /></td>
	</tr>


	<tr>

		<td class="label">媒体宣传内容*</td>
		<td class="content" colspan="3"><textarea class="text max"
			type="text" name="mediaContent" id="mediaContent"
			maxLength="500" class="text max" alt="allowBlank:false">${mediaPublicityList.mediaContent}</textarea>
		</td>

	</tr>
	<tr>
			<td class="label">宣传效果评估*</td>
		<td class="content" colspan="3"><input class="text max" type="text"
			name="mediaAssess" id="mediaAssess"
			value="${mediaPublicityList.mediaAssess}" alt="allowBlank:false" /></td>
			</tr>
		<tr>
		<td class="label">媒体宣传时间*</td>
		<td class="content"><input class="text" type="text"
			name="mediaTime" id="mediaTime"
			value="${mediaPublicityList.mediaTime}"
			alt="allowBlank:false,vtext:'请输入完成时间期限...'"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);"
			readonly="true" /></td>
		</td>
		<td class="label">提交审批人</td>

       <td class="content">
		<input type="text"  class="text"  name="approvalUserName" id="approvalUserName"  value="<eoms:id2nameDB beanId="tawSystemUserDao" id="${mediaPublicityList.approvalUser}"/>" alt="allowBlank:false" readonly="readonly"/>
		   <input type="hidden" name="approvalUser" id="approvalUser"  value="${mediaPublicityList.approvalUser}"/>
	<%-- 		<input class="text" type="text"
				name="approvalUser" id="approvalUser" alt="allowBlank:false" />--%>
			<eoms:xbox id="approvalUserName" dataUrl="${app}/xtree.do?method=userFromDept"  
		rootId="2" rootText="用户树"  valueField="approvalUser" handler="approvalUserName" 
		textField="approvalUserName" checktype="user" single="true" />
		</td>

	</tr>

	<tr>

		<td class="label">备注</td>
		<td class="content" colspan="3"><textarea class="text max"
			type="text" name="remark" id="remark" alt="allowBlank:true">${mediaPublicityList.remark}</textarea></td>

	</tr>




</table>


<br />
<table>
	<tr>
		<td><input type="submit" class="btn" value="提交审批" onclick="edit();"/>
		</td>
		<td>
				<c:if test="${mediaPublicityType[mediaPublicityList.approvalType]=='驳回'}"> 
			<input type="button" class="btn" value="删除" onclick="remove();"/>&nbsp;	
		</c:if>
		
		<%---<td>
		<input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/>
		</td>--%>
	</tr>
</table>


</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
