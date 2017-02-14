<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<option><div class="header center">物品查询</div></option>

<display:table name="tawRmThingNoteList" cellspacing="0" cellpadding="0"
		id="tawRmThingNoteList"  class="table"
		pagesize="${pageSize}" export="true"  requestURI=""
		sort="list"  partialList="true" size="resultSize">
 	<display:column title="序号" sortable="true">
		<%=pageContext.getAttribute("tawRmThingNoteList_rowNum")%>
	</display:column>
	<display:column  property="beginTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true"
			headerClass="sortable" title="外借时间"/>
	<display:column  property="toUser" sortable="false"
			headerClass="sortable" title="外借人"/>
	<display:column  property="endTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true"
			headerClass="sortable" title="归还时间"/>
	<display:column property="noteComment" sortable="true"
			headerClass="sortable" title="说明"/>
	<display:column  property="inputUserName" sortable="true"
			headerClass="sortable" title="记录人"/>
			
	<display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>  
	<display:setProperty name="export.rtf" value="false"/>  
	</display:table>