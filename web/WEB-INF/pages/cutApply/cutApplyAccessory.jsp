<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
function checkForm(form,num){ 
 
    if(document.getElementById("accessoryName").value==null||document.getElementById("accessoryName").value==""){
    	alert("请选择上传文件");
    	return false;
    }else if(document.getElementById("accessoryName").value.indexOf(".xls")<0){
    	alert("确认选择的文件为Excel");
    	return false;
    }else{
    return Validator.Validate(form,num);
    }
  }
</script>
<title>干线切割管理批量录入</title>
<html:form action="cutApplys.do?method=xaccessory"
	method="post" styleId="tawSystemDeptForm"
	enctype="multipart/form-data" onsubmit="return checkForm()">
	<table class="formTable small" align="center">
		<tr>
			<td colspan="2" align="center" class="label">
				<h2>
				干线切割管理批量录入
				</h2>
				<p align="right"><font color="#ff0000">请先下载模板，录入后再上传该xls文件</font></p>
			</td>
		</tr>
		<tr>
			<td class="label" nowrap="nowrap" align="right" >
				模板文件
			</td>
			<td>
			<%
					String url = "/partner/model";
					String fileRealName = "cutApplyTemplate.xls";
					url += "/" + fileRealName;
			%>
			 <!--	wid下不能下载，报文件找不到。因此用链接的这种简单的形式。
			 <a href="<%=request.getContextPath()%>/cutapply/cutApplys.do?method=xmlOutPut&url=<%=url%>"><%=fileRealName%>  -->
				 	<a href="<%=request.getContextPath()%>/partner/model/cutApplyTemplate.xls"><%=fileRealName%>  
				</a>
			</td>
		</tr>
		<tr>
			<td class="label" nowrap="nowrap" align="right">
				文件上传
			</td>
			<td >          
				<input name="accessoryName" type="file" class="file" />
			</td>
		</tr>
		<tr>
			<td align="right">
				<html:submit styleClass="button" property="method.save">
					<fmt:message key="button.save" />
				</html:submit>
			</td>
			<td >
				<html:reset styleClass="button" property="method.reset">
					<fmt:message key="button.reset" />
				</html:reset>
			</td>
		</tr>
	</table>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>