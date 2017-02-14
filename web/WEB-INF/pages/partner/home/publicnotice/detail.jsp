<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<br/><br/>
<form id="form1" method="post" action="publish.do?method=read">
  <table id="sheet" class="formTable">
	   <tr>
				<td class="label" >
				 主题<font color="red" ><c:if test="${isRead==0}">（未阅知确认）</c:if></font>
				</td>
				<td class="content" colspan="3">
					${requestScope.publishInfo.subject }
				</td>
			</tr>			
			<tr>
				<td class="label">
					发布时间 <font color="red">*</font>
				</td>
				<td class="content">
					${fn:substring(requestScope.publishInfo.publishTime,0,19)}
				</td>
				<td class="label">
					有效期 <font color="red">*</font>
				</td>
				<td class="content" >
					 ${fn:substring(requestScope.publishInfo.valid, 0, 19)}
				</td>
			</tr>		
			<tr>
				<td class="label" >
				发布人 
				</td>
				<td class="content">
					 ${requestScope.publishInfo.publisherName }  
				</td>
				<td class="label" >
				发布人部门 
				</td>
				<td class="content">
					${requestScope.publishInfo.publisherDeptName }   
				</td>
			</tr>						
			<tr>
				<td class="label">
					内容 <font color="red">*</font>
				</td>
				<td class="content" colspan="3">
				  <pre>${requestScope.publishInfo.publishContent }</pre>
				</td>
			</tr>			
			<tr>
				<td class="label">
					发布范围
					<font color="red">*</font>
				</td>
				<td class="content" colspan="3">
					<fieldset id="fieldset_per">
						<legend>
							发布到下列组织机构或人员
						</legend>	 
						<div id="publishedRangeName_div" class="viewer-list">
							${publishInfo.publishedRangeName }
						</div>
					</fieldset>
				</td>
			</tr>	
			<tr>
				<td class="label">
				 附件 
				</td>
				<td class="content" colspan="3" height="100px">
					<eoms:download ids="${requestScope.publishInfo.file }"></eoms:download>
				</td>
		   <tr>			 
  </table>
  <input id="mainid"  type="hidden" name="id" value="${publishInfo.id}"/>
  <c:if test="${isRead==0}">
     <input id="taskId"  type="hidden" name="taskId" value="${taskId}"/>
     <input id="read" type="submit" class="btn" value="阅知" />
  </c:if>
</form>  
<%@ include file="/common/footer_eoms.jsp"%>