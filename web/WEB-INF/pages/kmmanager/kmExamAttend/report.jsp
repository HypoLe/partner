<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>

<% String excelUrl = StaticMethod.nullObject2String(request.getAttribute("excelUrl")); %>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">
<eoms:xbox id="tree" dataUrl="${app}/kmmanager/kmExamTests.do?method=getNodesRadioTree" 
	  	rootId="1" 
	  	rootText='试卷名称' 
	  	valueField="testId" handler="testName"
		textField="testName"
		checktype="forums" single="true"		
	  ></eoms:xbox>
<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="1" 
	  	rootText='部门树' 
	  	valueField="attendUser" handler="userName"
		textField="userName"
		checktype="user" single="true"		
	  ></eoms:xbox>

<div style="text-align:center;margin-bottom:8px;"><b>考试成绩汇总</b></div>

<html:form action="/kmExamAttends.do?method=searchX" styleId="kmExamAttendForm" method="post"> 
<table>
	<tr>	
		<td>
			人员名称：
			<input type="text"   id="userName" name="userName" class="text" readonly="readonly" value='<eoms:id2nameDB id="${kmExamAttendForm.attendUser}" beanId="tawSystemUserDao" />' alt="allowBlank:false'"/>
			<input type="hidden" id="attendUser"   name="attendUser" value="${kmExamAttendForm.attendUser}" />&nbsp;&nbsp;
			试卷名称：
			<input type="text"   id="testName" name="testName" class="text" readonly="readonly" value='<eoms:id2nameDB id="${kmExamAttendForm.testId}" beanId="kmExamTestDao" />' alt="allowBlank:false'"/>
			<input type="hidden" id="testId"   name="testId" value="${kmExamAttendForm.testId}" />&nbsp;&nbsp;
			成绩排序：
			<html:select property="scoreOrder" styleId="scoreOrder"	styleClass="text medium" value="${scoreOrder}">
			<html:option value="desc">从高到低&nbsp;</html:option>
			<html:option value="asc">从低到高&nbsp;</html:option>
			</html:select>&nbsp;&nbsp;
			<input type="submit" class="btn" value="<fmt:message key="kmTable.query"/>"/>&nbsp;
		 	<input type="reset" class="btn" value="重置"/>	
		</td>
	</tr>
</table>
</html:form>

	<display:table name="kmExamAttendList" cellspacing="0" cellpadding="0"
		id="kmExamAttendList" pagesize="${pageSize}" class="table kmExamAttendList"
		export="false"
		requestURI="${app}/kmmanager/kmExamAttends.do?method=report"
		sort="list" partialList="true" size="resultSize">
		
	<display:column sortable="true" headerClass="sortable" title="人员名称">
			<eoms:id2nameDB id="${kmExamAttendList.attendUser}" beanId="tawSystemUserDao" />
	</display:column>
	 <display:column sortable="true" headerClass="sortable" title="所属部门">
		<eoms:id2nameDB id="${kmExamAttendList.attendDept}" beanId="tawSystemDeptDao" />
	</display:column>	
	 <display:column sortable="true" headerClass="sortable" title="考试名称">
		<eoms:id2nameDB id="${kmExamAttendList.testId}" beanId="kmExamTestDao" />
	</display:column>
    <display:column property="attendTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="参加时间"  paramId="id" paramProperty="id"/>
	<display:column property="score" sortable="true" 
			headerClass="sortable" title="考试分数"  paramId="id" paramProperty="id"/>
	<display:column title="查看" headerClass="imageColumn">
		<a href="javascript:var id = '${kmExamAttendList.id }';
	                        var testID = '${kmExamAttendList.testId}';
	                        var attendUser = '${kmExamAttendList.attendUser}';
	                        var url='${app}/kmmanager/kmExamAttends.do?method=attendRead';
	                        url = url + '&id=' + id + '&testID=' + testID +'&attendUser='+attendUser;
	                        location.href=url">
			<img src="${app}/images/icons/search.gif" />
		</a>
	</display:column>
	
		<display:setProperty name="paging.banner.item_name" value="kmExamAttend" />
		<display:setProperty name="paging.banner.items_name" value="kmExamAttends" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>
<table cellpadding="0" cellspacing="0" width="98%">
<tr>
    <td width="100%" height="32" align="right">
      <html:link href="<%=excelUrl%>" scope="page" >导出EXCEL</html:link>
    </td>

</tr>
</table>
<%@ include file="/common/footer_eoms.jsp"%>