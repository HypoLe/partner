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
			var url='${app}/deviceManagement/publicityCatchline/publicityCatchline.do?method=delete&id=${publicityCatchlineList.id}';
			location.href=url;
		}
	}
	
function edit(){
       if(confirm('您是否要提交该信息?')){	
			var url='${app}/deviceManagement/publicityCatchline/publicityCatchline.do?method=edit';
			location.href=url;
			}
	}
 

</script>
<html:form action="publicityCatchline.do?method=edit" method="post"
	styleId="publicityCatchlineForm" >
	<!-- hidden area start -->
<input type="hidden" name="id" value="${publicityCatchlineList.id}" /> 
<input type="hidden" name="creatTime" value="${publicityCatchlineList.creatTime}" />
<input type="hidden" name="creatUser" value="${publicityCatchlineList.creatUser}" /> 
<input type="hidden" name="creatArea" value="${publicityCatchlineList.creatArea}" /> 
<input type="hidden" name="deleted" value="${publicityCatchlineList.deleted}" /> 
    <!-- hidden area end -->


*号为必填内容
<table id="stylesheet" class="formTable">

	<content tag="heading">
	<c:out value="媒体宣传标语修改页面" />
	</content>
	<br />
	<br />

	<tr>
		<td class="label">媒体宣传标语*</td>
		<td class="content" colspan="3"><textarea class="textarea max"
			 name="publicityCatchline" id="publicityCatchline" maxLength="500" alt="allowBlank:false" value="${publicityCatchlineList.publicityCatchline} ">${publicityCatchlineList.publicityCatchline} </textarea></td>
 
    
	</tr>

		<tr>
		<td class="label">提交审批人</td>

       <td class="content" colspan="3">
		<input type="text"  class="text"  name="approvalUserName" id="approvalUserName"  value="<eoms:id2nameDB beanId="tawSystemUserDao" id="${publicityCatchlineList.approvalUser}"/>" alt="allowBlank:false" readonly="readonly"/>
		   <input type="hidden" name="approvalUser" id="approvalUser"  value="${publicityCatchlineList.approvalUser}"/>
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
			type="text" name="remark" id="remark" alt="allowBlank:true">${publicityCatchlineList.remark}</textarea></td>

	</tr>




</table>


<br />
<table>
	<tr>
		<td><input type="submit" class="btn" value="提交审批" onclick="edit();"/>
		</td>
		<td>

				<c:if test="${publicityCatchlineType[publicityCatchlineList.approvalType]=='驳回'}"> 
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
