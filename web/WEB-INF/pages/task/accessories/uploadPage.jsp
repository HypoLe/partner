<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'accessoriesForm'});
})
</script>

<html:form enctype="multipart/form-data" styleId="accessoriesForm" action="/accessories.do?method=upload" method="post" target="uploadPage" >
<html:hidden property="taskId" value="${requestScope.taskId}" />
<table class="formTable">
	<caption>
	<div class="header center">附件上传/下载</div>
	<c:if test="${not empty requestScope.uploadInfo}">
	<div class="help">
		<span>
		<dl>
			<dt class="warn">${requestScope.uploadInfo}</dt>
		</dl>
		</span>
    </div>
    </c:if>
	</caption>
	<tr>
		<td width="30%" class="label">
			文件列表
		</td>
		<td width="70%">
			<logic:iterate id="accessories" name="accessories">
				<c:if test="${sessionScope.sessionform.userid == requestScope.drafter}">
				<a href="${app}/task/accessories.do?method=delete&id=${accessories.id}&taskId=${requestScope.taskId}">
					<img title="删除文件" src="${app}/images/icons/list-delete.gif"/>
				</a>
				</c:if>
				<a href="${app}/task/accessories.do?method=download&id=${accessories.id}">
					${accessories.fileName}
				</a><br/><br/>
			</logic:iterate>
		</td>
	</tr>
	<c:if test="${sessionScope.sessionform.userid == requestScope.drafter}">
	<tr>
		<td class="label">
			选择文件
		</td>
		<td>
			<html:file property="uploadFile" style="width:50%;height:22;border:1px solid #006699" alt="allowBlank:false,vtext:'请选择要上传的文件'" />
		</td>
	</tr>
	</c:if>
	<tr>
		<td colspan="2" align=center>
			<c:if test="${sessionScope.sessionform.userid == requestScope.drafter}">
			<html:submit value="提交" styleClass="btn" />
			</c:if>
			<html:button property="close" value="关闭" styleClass="btn" onclick="javascript:window.close();" />
		</td>
	</tr>

</table>

</html:form>
<script language="JavaScript"> 
	 window.name = "uploadPage";
</script>
<%@ include file="/common/footer_eoms.jsp"%>
