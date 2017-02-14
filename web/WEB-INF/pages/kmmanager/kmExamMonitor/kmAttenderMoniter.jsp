<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">
	<div>
		<form action="${app}/kmmanager/kmExamMonitors.do?method=attenderMonitor" method="post">
			<!-- 
				考试时间：开始时间
				<input type="text" size="20" readonly="true" class="text"
							name="beginTime" id="beginTime"
							onclick="popUpCalendar(this, this,null,null,null,false,-1);"
							 value="${beginTime}" />
				结束时间
				<input type="text" size="20" readonly="true" class="text"
							name="endTime" id="endTime"
							onclick="popUpCalendar(this, this,null,null,null,false,-1);"
							 value="${endTime}" />
			 -->
			考试名称<input id="testName" name="testName" class="text" value='<eoms:id2nameDB id="${testID}" beanId="kmExamTestDao" />'>
			<input type="submit" class="btn"  value="查找">
			<input type="submit" class="btn"  value="查看当前考试考生" onclick="document.getElementById('current').value='1';">
			<input type="hidden" id="current" name="current" value="0">
			<input type="hidden" id="testId" name="testId" value="${testID}">
		</form>
	</div>
	<br>
	<caption>
		<div class="header center"><b>考试监控</b></div>
	</caption>
	<br>
	<display:table name="kmExamMonitorList" cellspacing="0" cellpadding="0"
		id="kmExamMonitorList" pagesize="${pageSize}"  partialList="true" size="resultSize"
		class="table kmExamTestList" export="false"
		requestURI="${app}/kmmanager/kmExamMonitors.do?method=attenderMonitor">
		
		<display:column titleKey="kmExamTest.testName" >
			<script>
				var testName = '<eoms:id2nameDB id="${kmExamMonitorList.testId}" beanId="kmExamTestDao" />';
				if(testName=='')
					document.write('<eoms:id2nameDB id="${kmExamMonitorList.testId}" beanId="kmExamRandomTestDao" />');
				else 
					document.write(testName);
			</script>
		</display:column>
		<display:column  title="参考人" >
			<eoms:id2nameDB id="${kmExamMonitorList.userId}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column property="inTime"  title="进入考试时间"  format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
		<display:column property="overTime"  title="考试结束时间"  format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
		<display:column property="outTime" title="提交考试时间"  format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
		<display:column title="是否提交" >
			<eoms:dict key="dict-kmmanager" dictId="isOrNot" itemId="${kmExamMonitorList.isOut}" beanId="id2nameXML" />
		</display:column>
		<display:column title="删除" url="/kmmanager/kmExamMonitors.do?method=remove"  paramId="id" paramProperty="id">
			删除
		</display:column>
	</display:table>
	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>
<script type="text/javascript">
Ext.onReady(function() {
	var config = {
			btnId:'testName',
			treeDataUrl:'${app}/kmmanager/kmExamAttends.do?method=getTestName',
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