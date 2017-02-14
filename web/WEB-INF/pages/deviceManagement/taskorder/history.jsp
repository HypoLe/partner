<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">


function jumpToAdd(){
	location.href='<html:rewrite page='/taskOrderAction.do?method=goToAdd'/>';
}

function jumpToChart(){
	location.href='<html:rewrite page='/taskOrderAction.do?method=goToChart'/>';
}

function jumpTolistHis(){
	location.href='<html:rewrite page='/taskOrderAction.do?method=listHistory'/>';
}
</script>
	<div id="tabs-1">
	<html:form action="taskOrderAction.do?method=searchHis" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					
					
					<td class="label">
						工单类型:
					</td>
					<td colspan="3">
						<select id="type" name="type" class="select">
							<option value="所有" selected>所有</option>
							<option value="101010202">一般</option>
							<option value="101010201">紧急</option>
						</select>
					</td>
				</tr>
			
				
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</html:form>

<!-- Information hints area end-->
<logic:present name="taskOrderList" scope="request">
	<display:table name="taskOrderList" cellspacing="0" cellpadding="0"
		id="taskOrderList" pagesize="${pagesize}"
		class="table taskOrderList" export="true"
		requestURI="taskOrderAction.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="工单编号">
			${taskOrderList.number}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单主题">
			${taskOrderList.topic}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单类型">
			<eoms:id2nameDB id="${taskOrderList.type}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="网络类型">
			<eoms:id2nameDB id="${taskOrderList.netGroup}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="创建人">
			<eoms:id2nameDB id="${taskOrderList.createUserId}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="创建时间">
			${taskOrderList.createTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归档人">
			<eoms:id2nameDB id="${taskOrderList.createUserId}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归档时间">
			${taskOrderList.endTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="是否延时">
			<c:out value="${taskOrderList.delay=='1'?'延时回复':'按时回复' }"/> 
		</display:column>
		
		
		
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${taskOrderList.id }"
				href="${app }/taskorder/taskOrderAction.do?method=goToDetail&id=${taskOrderList.id}"
				target="blank" shape="rect">
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
	</div>

		

<html:button styleClass="btn" property="method.save"
			styleId="method.save" value="任务工单统计" onclick="jumpToChart()"></html:button>




<%@ include file="/common/footer_eoms.jsp"%>
