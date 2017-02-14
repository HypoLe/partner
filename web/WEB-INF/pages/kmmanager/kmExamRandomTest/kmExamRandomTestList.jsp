<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" class="btn"
		onclick="location.href='<html:rewrite page='/kmExamRandomTests.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<content tag="heading">
	<center><b><fmt:message key="kmExamRandomTest.list.heading" /></b></center>
</content>

	<display:table name="kmExamRandomTestList" cellspacing="0" cellpadding="0"
		id="kmExamRandomTestList" pagesize="${pageSize}" class="table kmExamRandomTestList"
		export="false"
		requestURI="${app}/kmmanager/kmExamRandomTests.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="testName" sortable="true" style="width:40%;"
			headerClass="sortable" titleKey="kmExamRandomTest.name" href="${app}/kmmanager/kmExamRandomTests.do?method=edit" paramId="id" paramProperty="testID"/>
	
	<%-- 
	<display:column  sortable="true"
			headerClass="sortable" titleKey="kmExamRandomTest.randomId" href="${app}/kmmanager/kmExamRandomTests.do?method=edit" paramId="id" paramProperty="testID">
		<eoms:id2nameDB id="${kmExamRandomTestList.randomId}" beanId="kmExamRandomSpcailityDao"/>
	</display:column>
	
	<display:column sortable="true"
			headerClass="sortable" titleKey="kmExamRandomTest.createUser" href="${app}/kmmanager/kmExamRandomTests.do?method=edit" paramId="id" paramProperty="testID">
		<eoms:id2nameDB id="${kmExamRandomTestList.createUser}" beanId="tawSystemUserDao" />
	</display:column>

	<display:column sortable="true"
			headerClass="sortable" titleKey="kmExamRandomTest.createDept" href="${app}/kmmanager/kmExamRandomTests.do?method=edit" paramId="id" paramProperty="testID">
		<eoms:id2nameDB id="${kmExamRandomTestList.createDept}" beanId="tawSystemDeptDao" />
	</display:column>
	<display:column property="createTime" sortable="true"
			headerClass="sortable" titleKey="kmExamRandomTest.createTime" href="${app}/kmmanager/kmExamRandomTests.do?method=edit" paramId="id" paramProperty="testID"/>
	 
	<display:column property="testDescription" sortable="true"
			headerClass="sortable" titleKey="kmExamRandomTest.testDescription" href="${app}/kmmanager/kmExamRandomTests.do?method=edit" paramId="id" paramProperty="testID"/>
	--%>
	
	<display:column property="testBeginTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="kmExamRandomTest.testBeginTime" href="${app}/kmmanager/kmExamRandomTests.do?method=edit" paramId="id" paramProperty="testID"/>

	<display:column property="testEndTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="kmExamRandomTest.testEndTime" href="${app}/kmmanager/kmExamRandomTests.do?method=edit" paramId="id" paramProperty="testID"/>
	
	<display:column property="totalScore" sortable="true"
			headerClass="sortable" titleKey="kmExamRandomTest.totalScore" href="${app}/kmmanager/kmExamRandomTests.do?method=edit" paramId="id" paramProperty="testID"/>
	
	<display:column sortable="true" headerClass="sortable"
			titleKey="kmExamTest.isPublic">
			<eoms:dict key="dict-kmmanager" dictId="isOrNot"
				itemId="${kmExamRandomTestList.isPublic }" beanId="id2nameXML" />
	</display:column>

	<display:column sortable="true" headerClass="sortable" titleKey="kmExamTest.browse">
		<a href="${app}/kmmanager/kmExamRandomTests.do?method=getRandomTestPaper&testID=${kmExamRandomTestList.testID}">
		    <img src="${app}/images/icons/table.png" />
		</a>
	</display:column>
		<display:setProperty name="paging.banner.item_name" value="kmExamRandomTest" />
		<display:setProperty name="paging.banner.items_name" value="kmExamRandomTests" />
	</display:table>
	
	<br>
	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>