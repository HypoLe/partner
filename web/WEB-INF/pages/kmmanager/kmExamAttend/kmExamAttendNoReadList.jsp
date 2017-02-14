<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>



<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">
	<content tag="heading">
		<center><b><fmt:message key="kmExamAttend.noReadList" /></b></center>
	</content>
	<div>
		<form action="${app}/kmmanager/kmExamAttends.do?method=searchNoRead" method="post">
			<input id="testName" name="testName" class="text" value="${testName }">
			<input type="submit" id="testName" name="testName" class="btn"  value="查找待阅试卷">
			<input type="hidden" id="testId" name="testId" value="${testId }">
		</form>
	</div>
	<display:table name="kmExamAttendList" cellspacing="0" cellpadding="0"
		id="kmExamAttendList" pagesize="${pageSize}" class="table kmExamAttendList"
		export="false"
		requestURI="${app}/kmmanager/kmExamAttends.do?method=searchNoRead"
		sort="list" partialList="true" size="resultSize">

	<display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.testId">
			<script>
				var testName = '<eoms:id2nameDB id="${kmExamAttendList.testId}" beanId="kmExamTestDao" />';
				if(testName=='')
					document.write('<eoms:id2nameDB id="${kmExamAttendList.testId}" beanId="kmExamRandomTestDao" />');
				else 
					document.write(testName);
			</script>
			
	</display:column>

     <display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.attendDept">
			<eoms:id2nameDB id="${kmExamAttendList.attendDept}" beanId="tawSystemDeptDao" />
	</display:column>
	
	<display:column property="attendOverTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="kmExamAttend.attendOverTime"  paramId="id" paramProperty="id"/>

	<display:column title="进入阅卷" headerClass="imageColumn">
		    <a href="javascript:var id = '${kmExamAttendList.id }';
		                        var testID = '${kmExamAttendList.testId}';
		                        var attendUser = '${kmExamAttendList.attendUser}';
		                        var url='${app}/kmmanager/kmExamAttends.do?method=attendNoRead';
		                        url = url + '&id=' + id + '&testID=' + testID +'&attendUser='+attendUser;
		                        location.href=url">
		       <img src="${app}/images/icons/sheet-icons/arrow_join.png"></a>
		</display:column>

		<display:setProperty name="paging.banner.item_name" value="kmExamAttend" />
		<display:setProperty name="paging.banner.items_name" value="kmExamAttends" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>
<script type="text/javascript">
Ext.onReady(function() {
	var config = {
			btnId:'testName',
			treeDataUrl:'${app}/kmmanager/kmExamAttends.do?method=getNoReadTestName&isRead=0',
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
<%@ include file="/common/footer_eoms.jsp"%>