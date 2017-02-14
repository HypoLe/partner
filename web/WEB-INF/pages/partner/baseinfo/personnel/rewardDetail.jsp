<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="center"><b>人员奖励信息管理-人员奖励信息详情</div><br><br/>
	 <table id="sheet" class="formTable">
			<tr>
				<td class="label" >
				 员工姓名
				</td>
				<td class="content">
					${requestScope.reward.workername }  
				</td>
				<td class="label" >
				 系统编号
				</td>
				<td class="content">
					${requestScope.reward.sysno }  
				</td>
			</tr>
			<tr>
				<td class="label">
				  曾获奖励名称
				</td>
				<td class="content">
					${requestScope.reward.reward }
				</td>
				<td class="label">
				 获奖时间
				</td>
				<td class="content">
					 ${requestScope.reward.awardtime}
				</td>
			</tr>
			<tr>
				<td class="label">
				 授奖单位
				</td>
				<td class="content"  colspan="3">
					${requestScope.reward.awarddept}
				</td>
			</tr>
			<tr>
			<td class="label">
			 证书附件 
			</td>
			<td class="content" colspan="7" height="100px">
				<eoms:attachment name="reward" property="imagepath" scope="request" 
						idField="imagepath" appCode="dwInfoFile" viewFlag="Y"/> 
			</td>
		</tr>
		<tr>
				<td class="label"> 
					备注 
				</td>
				<td colspan="3">
					<pre>${requestScope.reward.memo }</pre>
				</td>
			</tr>
		</table>
		<br><br>
		<c:if test="${empty hasRightGoBack}">
			<input type="button" class="btn" value="返回" onclick="javascript:window.location.href='<%=request.getContextPath()%>/personnel/reward.do?method=search&operationType=tomgr&personCardNo=${personCardNo}'"/>
		</c:if>
<%@ include file="/common/footer_eoms.jsp"%>