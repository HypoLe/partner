<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style>
#showUser{
	background-color:#F5FAFA;
	padding:2px;
	position:absolute;
	left:expression((this.parentNode.clientWidth-this.clientWidth)/2);
	top:150px;
}
#showUser span{
	line-height:20px;
	height:20px;
	border:1px #98C0F4 solid;
	text-align:center;
}
.span1{
	background-color:#CAE8EA;
	width:49%;
}
.span2{
	width:49%;
	float:left;
}
</style>
<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">
	
	<div>
		<form action="${app}/kmmanager/kmExamMonitors.do?method=examMonitor" method="post">
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
							value="${beginTime}"  />
			 -->
			考试名称<input id="testName" name="testName" class="text" value="${testName }">
			<input type="submit" id="testName" name="testName" class="btn"  value="查找">
			<input type="hidden" id="testId" name="testId"  value="${testID}">
		</form>
	</div>
	<br/><br/>
	<caption>
		<div class="header center"><b>考试监控</b></div>
	</caption>
	<br/>
	<display:table name="kmExamMonitorList" cellspacing="0" cellpadding="0"
		id="kmExamMonitorList" pagesize="${pageSize}"
		class="table kmExamTestList" export="false"
		requestURI="${app}/kmmanager/kmExamAttends.do?method=search">
		<display:column  titleKey="kmExamTest.testName" >
			<script>
				var testName = '<eoms:id2nameDB id="${kmExamMonitorList[0]}" beanId="kmExamTestDao" />';
				if(testName=='')
					document.write('<eoms:id2nameDB id="${kmExamMonitorList[0]}" beanId="kmExamRandomTestDao" />');
				else 
					document.write(testName);
			</script>
		</display:column>
		<display:column title="参考人数" >
			<a href="javascript:void(0)" onclick="showUser('','${kmExamMonitorList[0]}')">${kmExamMonitorList[1]}</a>
		</display:column>
		<display:column title="已交卷人数" >
			<a href="javascript:void(0)" onclick="showUser('1','${kmExamMonitorList[0]}')">${kmExamMonitorList[2]}</a>
		</display:column>
		<display:column title="未交卷人数" >
			<a href="javascript:void(0)" onclick="showUser('0','${kmExamMonitorList[0]}')">${kmExamMonitorList[1]-kmExamMonitorList[2]}</a>
		</display:column>
		<display:column title="查看" >
			<a href="${app }/kmmanager/kmExamMonitors.do?method=attenderMonitor&testId=${testID}"><img src="${app}/images/icons/search.gif" /></a>
		</display:column>
	</display:table>
	<div id="showUser" style="display:none;width:300px;height:300px;border:8px #98C0F4 solid;">
		<div style="text-align:right;cursor:pointer;" onclick="this.parentNode.style.display='none'">关闭</div>
		<div id="nameContainer" style="overflow-y:scroll;height:300px;">
			
		</div>
	</div>
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
function createRequest()
{
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}

function showUser(isOut,testId)
{
	var url = "${app}/kmmanager/kmExamMonitors.do?method=listUserName&testId="+testId;
	if(isOut!='')
		url += "&isOut="+isOut;
	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		document.getElementById("showUser").style.display="";
		     		var userContainer = document.getElementById("nameContainer");
		     		userContainer.innerHTML = '<div><span class="span1">用户号</span><span class="span1">用户名称</span><div>';
		     		for(var i=0;i<json.length;i++)
		     		{
		     			var newDiv = document.createElement("div");
		     			newDiv.innerHTML = '<span class="span2">'+json[i].userId+'</span><span class="span2">'+json[i].userName+'</span>';
		     			userContainer.appendChild(newDiv);
		     		}
				}	
	     }
	     httpRequest.send(null);
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>