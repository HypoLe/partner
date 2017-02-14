<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="center"><b>人员技能信息管理-人员技能信息详情</div><br><br/>
	 <table id="sheet" class="formTable">
			<tr>
				<td class="label" >
				 员工姓名
				</td>
				<td class="content">
					${requestScope.dwinfo.workername }  
				</td>
				<td class="label" >
				 系统编号
				</td>
				<td class="content">
					${requestScope.dwinfo.sysno}  
				</td>
			</tr>
			<tr>
				<td class="label">
				所属组织
				</td>
				<td class="content">
					${requestScope.dwinfo.group }
				</td>
				<td class="label">
				 巡检专业
				</td>
				<td class="content"><%--
					<c:forTokens items="${dwinfo.professional}" delims="," var="professional" varStatus="s1">
			   		 	<c:if test="${!empty professional||professional!=null}">
			    			--%><eoms:id2nameDB id="${dwinfo.professional}" beanId="ItawSystemDictTypeDao" />
			    			<%--<c:if test="${!s1.last}">,
			    			</c:if>
			    		</c:if>
			    	</c:forTokens>
				--%></td>
			</tr>
			<tr>
				<td class="label">
				 拥有系统账号
				</td>
				<td class="content"><%--
					<c:forTokens items="${dwinfo.accountno}" delims="," var="accountno" varStatus="s2">
			   		 	<c:if test="${!empty accountno||accountno!=null}">
			    			--%><eoms:id2nameDB id="${dwinfo.accountno}" beanId="ItawSystemDictTypeDao" /><%--
			    			<c:if test="${!s2.last}">,
			    			</c:if>
			    		</c:if>
		    		</c:forTokens>
				--%></td>
				<td class="label">
				 技能等级 
				</td>
				<td class="content">
						<eoms:id2nameDB id="${requestScope.dwinfo.skilllevel}" beanId="ItawSystemDictTypeDao" />
				</td>
			</tr>
			<tr>
				<td class="label">
				 岗位
				</td>
				<td class="content" colspan="3">
					${requestScope.dwinfo.duty}
				</td>
		</tr>
		<tr >
			<td class="label"> 
				人员技能证书附件列表
			</td>
			<td class="content" colspan="7">
				<eoms:download ids="${certListImagepaths}">
				</eoms:download>
			</td>
		</tr>
		<tr >
			<td class="label"> 
				人员培训经历附件列表
			</td>
			<td class="content" colspan="7">
				<eoms:download ids="${pxListImagepaths}">
				</eoms:download>
			</td>
		</tr>
			<%--
			<tr>
				<td class="label">
				 证书附件 
				</td>
				<td class="content" colspan="3" height="100px">
						<eoms:download ids="${requestScope.dwinfo.imagepath }"/>
				</td>
			<tr>
			
			--%>
			<tr>
				<td class="label"> 
					备注 
				</td>
				<td colspan="3">
					<pre>${requestScope.dwinfo.memo }</pre>
				</td>
			</tr>
		</table>
		<br><br>
		<c:if test="${empty hasRightGoBack}">
			<input type="button" class="btn" value="返回" onclick="javascript:window.location.href='<%=request.getContextPath()%>/personnel/dwInfo.do?method=search&operationType=tomgr&personCardNo=${personCardNo}'"/>
		</c:if>
<%@ include file="/common/footer_eoms.jsp"%>