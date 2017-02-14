<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="center"><b>人员教育经历管理-人员教育经历信息详情</div><br><br/>
	 <table id="sheet" class="formTable">
			<tr>
				<td class="label" >
				 员工姓名
				</td>
				<td class="content">
					${requestScope.studyExperience.workername }  
				</td>
				<td class="label" >
				 系统编号
				</td>
				<td class="content">
					${requestScope.studyExperience.sysno }  
				</td>
			</tr>
			<tr>
				<td class="label">
				 入学时间
				</td>
				<td class="content">
					${requestScope.studyExperience.intime }
				</td>
				<td class="label">
				 毕业时间
				</td>
				<td class="content">
					 ${requestScope.studyExperience.leavetime}
				</td>
			<tr>
			<tr>
				<td class="label">
				毕业院校
				</td>
				<td class="content">
					${requestScope.studyExperience.university}
				</td>
				<td class="label">
				所学专业 
				</td>
				<td class="content">
						${requestScope.studyExperience.professional }
				</td>
			<tr>
			<tr>
				<td class="label">
				 所获学历
				</td>
				<td class="content">
					<eoms:id2nameDB id="${requestScope.studyExperience.degree}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">
				 学历证书编号
				</td>
				<td class="content">
					 ${requestScope.studyExperience.code }
				</td>
			<tr>
			<tr>
				<td class="label">
				 证书附件 
				</td>
				<td class="content" colspan="3" height="100px">
						<eoms:attachment name="studyExperience" property="imagepath" scope="request" 
						idField="imagepath" appCode="dwInfoFile" viewFlag="Y"/> 
				</td>
			<tr>
			<tr>
				<td class="label"> 
					备注 
				</td>
				<td colspan="3">
					<pre>${requestScope.studyExperience.memo }</pre>
				</td>
			</tr>
		</table>
		<br><br>
		<c:if test="${empty hasRightGoBack}">
				<input type="button" class="btn" value="返回" onclick="javascript:window.location.href='<%=request.getContextPath()%>/personnel/studyExperience.do?method=search&operationType=tomgr&personCardNo=${personCardNo}'"/>
		</c:if>
<%@ include file="/common/footer_eoms.jsp"%>