<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
function deleteInfo(id) {
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/faultInfo/faultInfo.do",
					params:{method:"delete",id:id},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {window.location.reload();});
					},
					failuer:function(res) {
						Ext.Msg.alert("提示：","删除失败！");
					}
				});
			}
}

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
	<html:form action="taskOrderAction.do?method=search" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">工单主题:</td>
					<td colspan="3">
						<input class="text" type="text" name="topic" id="topic"  />
					</td>
				</tr>
				<tr>
					<td class="label">操作方式:</td>
					<td>
						<select id="status" name="status" class="select">
							<option value="所有" selected>所有</option>
							<option value="已派发" >回复</option>
							<option value="已回复" >归档</option>
						</select>
					</td>
					
					<td class="label">
						工单类型:
					</td>
					<td>
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
							<!--  <input type="button" class="btn" value="查看归档工单" onclick="jumpTolistHis()"/>-->
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
		<display:column sortable="true" headerClass="sortable" title="派送对象">
			<c:if test="${taskOrderList.sendType=='0'}">
				<eoms:id2nameDB id="${taskOrderList.sendTo}" beanId="tawSystemDeptDao" />
			</c:if>
			<c:if test="${taskOrderList.sendType=='1'}">
				<eoms:id2nameDB id="${taskOrderList.sendTo}" beanId="tawSystemUserDao" />
			</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="抄送对象">
			<c:if test="${taskOrderList.send2Type=='0'}">
				<eoms:id2nameDB id="${taskOrderList.sendTo2}" beanId="tawSystemDeptDao" />
			</c:if>
			<c:if test="${taskOrderList.send2Type=='1'}">
				<eoms:id2nameDB id="${taskOrderList.sendTo2}" beanId="tawSystemUserDao" />
			</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单状态">
			${taskOrderList.status}
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="操作"
			paramProperty="id" url="/taskorder/taskOrderAction.do?method=goToOperate"
			paramId="id" media="html">
			<img src="${app }/images/icons/edit.gif">
			<!--  
			<c:if test="${taskOrderList.status=='已派发'}">
				<c:if test="${taskOrderList.nextOperId == uid}">回复</c:if>
			</c:if>
			<c:if test="${taskOrderList.status=='已回复'}">归档</c:if>
			<c:if test="${taskOrderList.status=='未派发'}">派发</c:if>
			-->
		</display:column>
		
		
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
	</div>

		




<%@ include file="/common/footer_eoms.jsp"%>
