<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>

<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"; 
		%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>		
	

			<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="true" requestURI="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=timeoutGongdanList" sort="list"
			size="total" partialList="true">
			<display:column sortable="true" headerClass="sortable" title="工单号">${taskList.processInstanceId}</display:column>
			<display:column property="theme" sortable="false" headerClass="sortable" title="工单名称" maxLength="15" />
			<display:column sortable="false" headerClass="sortable" title="地市">
				<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="局站名称">
				${taskList.stationName}
			</display:column>
			<display:column property="createTime" sortable="true"	headerClass="sortable" title="公客发单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="sendTime" sortable="true"	headerClass="sortable" title="现场发单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column property="lastReplyTime" sortable="true"	headerClass="sortable" title="施工队回单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column sortable="false" headerClass="sortable" title="机线员">
				${taskList.tempTask}
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="所在账号">
				<eoms:id2nameDB id="${taskList.executor}" beanId="tawSystemUserDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="归集工单号">
				${taskList.sheetId}
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="当前状态">
				${taskList.taskDefKeyName}
			</display:column>
			<c:if test="${type eq '2'}">
		  		<display:column sortable="false" headerClass="sortable" title="障碍业务号码">
				${taskList.barrierNumber}
				</display:column>
		  	</c:if>
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>