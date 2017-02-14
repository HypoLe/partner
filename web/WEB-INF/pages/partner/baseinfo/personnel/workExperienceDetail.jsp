<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="center"><b>人员工作经历管理-人员工作经历信息详情</div><br><br/>
	 <table id="sheet" class="formTable">
			<tr>
				<td class="label" >
				 员工姓名
				</td>
				<td class="content">
					${requestScope.workExperience.workername }  
				</td>
				<td class="label" >
				 系统编号
				</td>
				<td class="content">
					${requestScope.workExperience.sysno }  
				</td>
			</tr>
			<tr>
				<td class="label">
				 入职时间
				</td>
				<td class="content">
					${requestScope.workExperience.entryTime }
				</td>
				<td class="label">
				 离职时间
				</td>
				<td class="content">
					 ${requestScope.workExperience.leaveTime }
				</td>
			</tr>
			<tr>
				<td class="label">
				工作单位
				</td>
				<td class="content">
					${requestScope.workExperience.company}
				</td>
				<td class="label">
				 工作职务 
				</td>
				<td class="content">
						${requestScope.workExperience.duty}
				</td>
			</tr>
			<tr>
				<td class="label"> 
					备注 
				</td>
				<td colspan="3">
					<pre>${requestScope.workExperience.memo}</pre>
				</td>
			</tr>
		</table>
		<br><br>
		<c:if test="${empty hasRightGoBack}">
			<input type="button" class="btn" value="返回" onclick="javascript:window.location.href='<%=request.getContextPath()%>/personnel/workExperience.do?method=search&operationType=tomgr&personCardNo=${personCardNo}'"/>
		</c:if>
<%@ include file="/common/footer_eoms.jsp"%>