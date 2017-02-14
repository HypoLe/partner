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


</script>

<content tag="heading">
<c:out value="线路修缮施工列表" />
</content>  <br/><br/>
<!-- Information hints area end-->
<logic:present name="projectBaseInfoList" scope="request">
	<display:table name="projectBaseInfoList" cellspacing="0" cellpadding="0"
		id="projectBaseInfoList" pagesize="${pagesize}"
		class="table projectBaseInfoList" export="true"
		requestURI="lineProjectAction.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="工程名称">
			${projectBaseInfoList.projectName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单类型">
			${projectBaseInfoList.projectType}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="工程地点">
			${projectBaseInfoList.projectLocation}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="网络性质">
			<eoms:id2nameDB id="${projectBaseInfoList.networkType}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="影响系统">
			${projectBaseInfoList.effect}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="迁改长度">
			${projectBaseInfoList.projectLength}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请人">
			<eoms:id2nameDB id="${projectBaseInfoList.applyer}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请时间">
			${projectBaseInfoList.applyTime}
		</display:column>
	
		
		<display:column sortable="false" headerClass="sortable" title="施工"
			paramProperty="id" url="/lineProject/lineProjectAction.do?method=goToConsturct"
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
