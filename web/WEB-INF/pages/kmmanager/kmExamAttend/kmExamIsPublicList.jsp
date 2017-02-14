<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	var config = {
			btnId:'testName',
			treeDataUrl:'${app}/kmmanager/kmExamAttends.do?method=getNoReadTestName&isRead=3',
			treeRootId:'1',
			treeRootText:'考试名称列表',
			treeChkMode:'single',
			treeChkType:'forums',
			showChkFldId:'testName',
			saveChkFldId:'testId'
		}
	tree = new xbox(config);
});
</script>

<fmt:bundle	basename="com/boco/eoms/km/config/applicationResource-kmmanager">
	<caption>
		<div class="header center"><b>考试结果列表</b></div>
	</caption>

	<div>
		<form action="${app}/kmmanager/kmExamAttends.do?method=searchIsPublic" method="post">
			<input id="testName" name="testName" class="text" value="${testName }">
			<input type="submit" id="testName" name="testName" class="btn"  value="查看试卷成绩">
			<input type="hidden" id="testId" name="testId" value="${testId }">
		</form>
	</div>

	<display:table name="kmExamAttendList" cellspacing="0" cellpadding="0"
		id="kmExamAttendList" pagesize="${pageSize}"
		class="table kmExamAttendList" export="false"
		requestURI="${app}/kmmanager/kmExamAttends.do?method=searchIsPublic">

		<display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.testId">
			<eoms:id2nameDB id="${kmExamAttendList.testId}" beanId="kmExamTestDao" />
		</display:column>

		<display:column property="attendTime" sortable="true"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" headerClass="sortable"
			titleKey="kmExamAttend.attendTime" paramId="id" paramProperty="id" />

		<display:column property="attendOverTime" sortable="true"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" headerClass="sortable"
			titleKey="kmExamAttend.attendOverTime" paramId="id"
			paramProperty="id" />

		<display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.readDept">
			<c:if test="${kmExamAttendList.readUser==null}">自动阅卷</c:if>
			<c:if test="${kmExamAttendList.readUser!=null}"><eoms:id2nameDB id="${kmExamAttendList.readDept}" beanId="tawSystemDeptDao" /></c:if>
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.readUser">
		 	<c:if test="${kmExamAttendList.readUser==null}">自动阅卷</c:if>
			<c:if test="${kmExamAttendList.readUser!=null}"><eoms:id2nameDB id="${kmExamAttendList.readUser}" beanId="tawSystemUserDao" /></c:if>
		</display:column>

		<display:column property="score" sortable="true"
			headerClass="sortable" titleKey="kmExamAttend.score" paramId="id"
			paramProperty="id" />

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
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>