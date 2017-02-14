<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<jsp:directive.page import="com.boco.eoms.km.exam.util.KmExamSpecialtyConstants"/>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/kmExamQuestionss.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
	<!--<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/kmExamQuestionss.do?method=getUpload'/>'"
		value="批量导入" />
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/kmExamQuestionss.do?method=download'/>'"
		value="下载导入模版" /> -->
</c:set>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<display:table name="kmExamRandomQuestionList" cellspacing="0" cellpadding="0"
		id="kmExamQuestionsList" pagesize="${pageSize}" class="table kmExamQuestionsList"
		export="false"
		requestURI="${app}/kmmanager/kmExamRandomQuestions.do?method=searchBySpcaility"
		sort="list" partialList="true" size="resultSize">
	
	<display:column property="question" sortable="true" style="width:60%;"
			headerClass="sortable" titleKey="kmExamQuestions.question" href="${app}/kmmanager/kmExamQuestionss.do?method=edit" paramId="questionID" paramProperty="questionID"/>
	
	<%-- 
	<display:column sortable="true" headerClass="sortable" titleKey="kmExamQuestions.createDept">
			<eoms:id2nameDB id="${kmExamQuestionsList.createDept}" beanId="tawSystemDeptDao" />
	</display:column>
	 <display:column sortable="true" headerClass="sortable" titleKey="kmExamQuestions.createUser">
		<eoms:id2nameDB id="${kmExamQuestionsList.createUser}" beanId="tawSystemUserDao" />
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="kmExamQuestions.deptId">
			<eoms:id2nameDB id="${kmExamQuestionsList.deptId}" beanId="tawSystemDeptDao" />
	</display:column>
    <display:column sortable="true" headerClass="sortable" titleKey="kmExamQuestions.questionType">
	   <eoms:dict key="dict-kmmanager" dictId="questionType" itemId="${kmExamQuestionsList.questionType}" beanId="id2nameXML" />			
	</display:column>
	<display:column sortable="true" headerClass="sortable" titleKey="kmExamQuestions.difficulty">
	   <eoms:dict key="dict-kmmanager" dictId="difficulty" itemId="${kmExamQuestionsList.difficulty}" beanId="id2nameXML" />			
	</display:column>
	--%>
	<display:column property="createTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="kmExamQuestions.createTime" href="${app}/kmmanager/kmExamQuestionss.do?method=edit" paramId="questionID" paramProperty="questionID"/>

	<display:column sortable="true" headerClass="sortable" titleKey="kmExamQuestions.specialtyID" >
		<eoms:id2nameDB id="${kmExamQuestionsList.specialtyID}" beanId="kmExamSpecialtyDao" />
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" title="删除">
	   <a href="${app}/kmmanager/kmExamRandomQuestions.do?method=remove&questionId=${kmExamQuestionsList.questionID}&questionType=${questionType}&id=${spcailityId}">删除</a>			
	</display:column>
		<display:setProperty name="paging.banner.item_name" value="kmExamQuestions" />
		<display:setProperty name="paging.banner.items_name" value="kmExamQuestionss" />
	</display:table>
	
</fmt:bundle>


<%@ include file="/common/footer_eoms.jsp"%>