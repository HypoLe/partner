<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="center"><b>人员培训经历管理-人员培训经历信息详细</div><br><br/>
	 <table id="sheet" class="formTable">
			<tr>
				<td class="label" >
				 员工姓名
				</td>
				<td class="content">
					${requestScope.pxExperience.workername }  
				</td>
				<td class="label" >
				 系统编号
				</td>
				<td class="content">
					${requestScope.pxExperience.sysno}  
				</td>
			</tr>
			<tr>
				<td class="label">
				  培训起始时间
				</td>
				<td class="content">
					${requestScope.pxExperience.starttime }
				</td>
				<td class="label">
				 培训结束时间
				</td>
				<td class="content">
					 ${requestScope.pxExperience.endtime}
				</td>
			</tr>
			<tr>
				<td class="label">
				 培训天数
				</td>
				<td class="content">
					${requestScope.pxExperience.days}
				</td>
				<td class="label">
				 培训成绩 
				</td>
				<td class="content">
						${requestScope.pxExperience.score}
				</td>
			</tr>
			<tr>
				<td class="label">
				 培训内容
				</td>
				<td class="content" colspan="3">
					${requestScope.pxExperience.content}
				</td>
			</tr>
			<tr>
				<td class="label">
				 培训证书扫描件
				</td>
				<td class="content" colspan="3">
					<eoms:attachment name="pxExperience" property="imagepath" scope="request" 
						idField="imagepath" appCode="dwInfoFile" viewFlag="Y"/> 
				</td>
			</tr>
			<tr>
				<td class="label"> 
					备注 
				</td>
				<td colspan="3">
					<pre>${requestScope.pxExperience.memo }</pre>
				</td>
			</tr>
		</table>
		<br><br>
	<c:if test="${empty hasRightGoBack}">
		<input type="button" class="btn" value="返回" onclick="javascript:window.location.href='<%=request.getContextPath()%>/personnel/pxExperience.do?method=search&operationType=tomgr&personCardNo=${personCardNo}'"/>
	</c:if>
<%@ include file="/common/footer_eoms.jsp"%>