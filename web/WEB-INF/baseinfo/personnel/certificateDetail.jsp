<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	 <table id="sheet" class="formTable">
		<c:if test="${(empty workerid)||workerid==''}">
			<tr>
				<td class="label" >
				 员工姓名* 
				</td>
				<td class="content" colspan="3">
					${requestScope.certificate.workername }  
				</td>
			</tr>
		</c:if>	
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
						<eoms:download ids="${requestScope.certificate.imagepath }"/>
				</td>
			<tr>
			
			<tr>
				<td class="label"> 
					备注 
				</td>
				<td class="content" colspan="3">
					 ${requestScope.certificate.memo }
				</td>
			</tr>
		</table>
		<br/>
<%@ include file="/common/footer_eoms.jsp"%>