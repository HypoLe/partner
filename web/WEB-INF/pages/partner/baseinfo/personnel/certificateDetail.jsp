<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="center"><b>人员证书信息管理-人员证书信息详细</div><br><br/>
	 <table id="sheet" class="formTable">
			<tr>
				<td class="label" >
				 员工姓名
				</td>
				<td class="content">
					${requestScope.certificate.workername }  
				</td>
				<td class="label" >
				 系统编号
				</td>
				<td class="content">
					${requestScope.certificate.sysno}  
				</td>
			</tr>
			<tr>
				<td class="label">
				 证书类别
				</td>
				<td class="content">
					${requestScope.certificate.type }
				</td>
				<td class="label">
				 证书等级
				</td>
				<td class="content">
					 ${requestScope.certificate.level }
				</td>
			<tr>
			<tr>
				<td class="label">
				 颁发时间
				</td>
				<td class="content">
					${requestScope.certificate.issuetime}
				</td>
				<td class="label">
				 发证机关 
				</td>
				<td class="content">
						${requestScope.certificate.issueorg }
				</td>
			<tr>
			<tr>
				<td class="label">
				 有效期
				</td>
				<td class="content">
					${requestScope.certificate.validity}
				</td>
				<td class="label">
				 证书编号
				</td>
				<td class="content">
					 ${requestScope.certificate.codeno }
				</td>
			<tr>
			<tr>
				<td class="label">
				 证书附件 
				</td>
				<td class="content" colspan="3" height="100px">
						<%--<eoms:download ids="${requestScope.certificate.imagepath }"/>
						--%>
						<eoms:attachment name="certificate" property="imagepath" scope="request" 
						idField="imagepath" appCode="dwInfoFile" viewFlag="Y"/> 
				</td>
			<tr>
			
			<tr>
				<td class="label"> 
					备注 
				</td>
				<td colspan="3">
					<pre>${requestScope.certificate.memo }</pre>
				</td>
			</tr>
		</table>
		<br/>
		<c:if test="${empty hasRightGoBack}">
			<input type="button" class="btn" value="返回" onclick="javascript:window.location.href='<%=request.getContextPath()%>/personnel/certificate.do?method=search&operationType=tomgr&personCardNo=${personCardNo}'"/>
		</c:if>
<%@ include file="/common/footer_eoms.jsp"%>