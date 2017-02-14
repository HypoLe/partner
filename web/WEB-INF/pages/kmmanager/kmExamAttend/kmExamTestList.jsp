<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">
	<div style="text-align:right">系统时间：<b><font color="red" id="year">0</font></b>年<b><font color="red" id="month">0</font></b>月<b><font color="red" id="date">0</font></b>日&nbsp;<b id="day"></b>&nbsp;<b><font color="red" id="hour">0</font></b>:<b><font color="red" id="minute">0</font></b>:<b><font color="red" id="second">0</font></b></div>
	<caption>
		<div class="header center"><b>考试列表</b></div>
	</caption>
	
	<display:table name="kmExamTestList" cellspacing="0" cellpadding="0"
		id="kmExamTestList" pagesize="${pageSize}"
		class="table kmExamTestList" export="false"
		requestURI="${app}/kmmanager/kmExamAttends.do?method=search">

		<display:column property="[1]"  style="width:50%;" titleKey="kmExamTest.testName"  />

		<display:column property="[2]"  titleKey="kmExamTest.testBeginTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>

		<display:column property="[3]"  titleKey="kmExamTest.testEndTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="[4]" 	titleKey="kmExamTest.testDuration" />

		<display:column title="参加考试" headerClass="imageColumn">
			<a href='${app}/kmmanager/kmExamAttends.do?method=attend&testID=${kmExamTestList[0] }&testType=${kmExamTestList[5] }' >
			  <img src="${app}/images/icons/sheet-icons/arrow_join.png">
			</a>
		</display:column>

		<display:setProperty name="paging.banner.item_name" value="kmExamTest" />
		<display:setProperty name="paging.banner.items_name" value="kmExamTests" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>
<script type="text/javascript">
var days = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];
var currentTime = "${currentTime }";
var currentDate = new Date();
currentDate.setTime(currentTime);
function showTime()
{	
	document.getElementById("year").innerHTML = currentDate.getFullYear();
	document.getElementById("month").innerHTML = currentDate.getMonth()+1;
	document.getElementById("date").innerHTML = currentDate.getDate();
	document.getElementById("day").innerHTML = days[currentDate.getDay()];
	document.getElementById("hour").innerHTML = currentDate.getHours()<10?"0"+currentDate.getHours():currentDate.getHours();
	document.getElementById("minute").innerHTML = currentDate.getMinutes()<10?"0"+currentDate.getMinutes():currentDate.getMinutes();
	document.getElementById("second").innerHTML = currentDate.getSeconds()<10?"0"+currentDate.getSeconds():currentDate.getSeconds();
	window.setTimeout(showTime,1000);
	currentDate.setTime(currentDate.getTime()+1000);
}
showTime();
</script>
<%@ include file="/common/footer_eoms.jsp"%>