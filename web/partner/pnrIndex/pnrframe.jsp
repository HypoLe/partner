<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
  <!--[if IE]><link rel="stylesheet" type="text/css" href="${app}/styles/common/ie.css" /><![endif]-->

</head>
<body>
<%-- Put constants into request scope --%>
<eoms:constants scope="request" />
  <div id="content" class="clearfix">
<script type="text/javascript"  src="${app}/scripts/partner/pnrIndex.js"></script>
<script type="text/javascript" src="${app}/scripts/partner/calendar.js"></script>
<link rel="stylesheet" type="text/css" href="${app}/partner/pnrIndex/pnrIndex.css" />
<div id="container">
	<div id="leftPart">
		
		<!-- 登陆用户头像 -->
		<div class="leftContent">
		<div id="loginUser">
			<div class="userLeftTop"></div>
			<div class="userRightTop"></div>
			<div class="userRightBottom"></div>
			<div class="userLeftBottom"></div>
			<div class="userLeftBorder"></div>
			<div class="userTopBorder"></div>
			<div class="userRightBorder"></div>
			<div class="userBottomBorder"></div>
			<div class="userContent">
			<c:if test="${fn:substring(sessionScope.sessionform.deptid, 0, 1)=='1'}">
				<img id="expertPhoto" src="${app}/partner/pnrIndex/pnrIndexImg/ydgs.jpg" width="80" height="120"/>
			</c:if>	
			<c:if test="${fn:substring(sessionScope.sessionform.deptid, 0, 1)!='1'}">
				<img id="expertPhoto" src="${app}/partner/pnrIndex/pnrIndexImg/hzgs.jpg" width="80" height="120"/>
			</c:if>	
				<span>
					<div><marquee scrolldelay="150">${sessionScope.sessionform.username},您好,欢迎使用合作伙伴管理系统!</marquee></div>
				</span>
			</div>
		</div>
		</div>
	
		<div class="leftContent">			
		<div class="orderTitle">协议管理</div>
			<div class="dataList" id="agreementTree" >
			<table>
			<tr><td>
			<a href="<c:url value='/partner/agreement/pnrAgreementMains.do?state=excute' />" target="_blank" ><span>有效协议列表</span></a>
			</td></tr>
			</table>
			</div>
		</div>
		<div class="leftContent">
			<div class="orderTitle">工作计划</div>
			<div class="dataList" id="workplanTree" >
			<table>
			<tr><td>
			<a href="<c:url value='/partner/workplan/pnrWorkplanMains.do?state=2' />" target="_blank" ><span>有效计划列表</span></a>
			</td></tr>
			</table>
			</div>
		</div>
		<div class="leftContent">
			<div class="orderTitle">临时任务</div>
			<div class="dataList" id="tempTaskTree">
			<table>
			<tr><td>
			<a href="<c:url value='/partner/tempTask/pnrTempTaskMains.do?state=2' />" target="_blank" ><span>有效任务列表</span></a>
			</td></tr>
			</table>
			</div>
		</div>
	</div>
	<div id="middlePart">
			
		<!-- x信息发布部分-->
		<div id="infopubList" onmouseover="infopubOver();" onmouseout="infopubOut()">
			<div id="infopubContainer"><iframe id="infopubFrame" name="infopubFrame" frameborder="0" width="100%" height="100%" src="${app}/partner/baseinfo/index.do?method=listInfopubsForIndex" scrolling="no"></iframe></div>
		</div> 
		<!-- 数据1列表 -->
		<div class="dataDetailList">
		
			<div class="dataDetailTitle">
				<div class="leftBorder"></div>
				<div class="topBorder" id="topBorder1"></div>
				<div class="rightBorder"></div>
				<div class="bottomBorder" id="bottomBorder1"></div>
				<div class="dataDetailTitleContent">
					<span class="span1" style="text-align:center;">待审核列表</span>
				</div>
			</div>
			<div class="dataDetailContent" id="dataDetailContent">
			<span style="text-align:center;"><iframe id="evaFrame" name="evaFrame" frameborder="0" width="90%" height="150px" src="${app}/partner/baseinfo/index.do?method=getUnAudit" scrolling="no"></iframe></span>
			</div>
		</div>
		<!-- 数据2列表 -->
		<div class="dataDetailList">
			<div class="dataDetailTitle">
				<div class="leftBorder"></div>
				<div class="topBorder" id="topBorder2"></div>
				<div class="rightBorder"></div>
				<div class="bottomBorder" id="bottomBorder2"></div>
				<div class="dataDetailTitleContent">
					<span class="span1" style="text-align:center;">待执行列表</span>
				</div>
			</div>
			<div class="dataDetailContent" id="dataDetailFile">
			<span style="text-align:center;"><iframe id="evaFrame" name="evaFrame" frameborder="0" width="90%" height="150px" src="${app}/partner/baseinfo/index.do?method=getUndo" scrolling="no"></iframe></span>
			</div>
		</div>
		
	</div>
	<div id="rightPart">
		<!-- 排行 -->
		<div class="orderList">
			<div class="orderTitle">当前时间</div>
			<div id="timeContent" class="orderContent">				
			</div>
			
			<div class="orderTitle">我收到的便签</div>
			<div class="msgContent">
			<iframe id="msgFrame" name="msgFrame" frameborder="0" width="94%" height="200px" src="${app}/partner/baseinfo/index.do?method=getReciverMsg&folderPath=3" scrolling="no"></iframe>
			</div>

		</div>
	</div>
	
</div>

<script type="text/javascript">
Ext.onReady(function() {
    var url = "${app}";
	//var clientWidth = document.getElementById("infopubList").clientWidth;
	//页面右边当前用户信息
	//showUser(url);
	//页面右边的排行
	//switchTab(url, "", 1);
	//switchTab(url, "readcount", 1);
	//switchTab(url, "score", 1);
	//switchTab(url, "expertScore", 1);
	//switchTab(url, "contribute", 1);	
	//页面中间的值班专家
	//showInfopubs(url);	
	//页面中间的内容
	//showContent(url, 1);
	//showContent(url, 2);
	showBestContent(url);
	showLeaderReadFile(url);
	//页面左边菜单树
	showNavigationTree(url, 1);
	showNavigationTree(url, 2);
	//showNavigationTree(url, 3);
	//初始化窗口宽度
	resizeWidth();
	//当窗口宽度变化时调用resizeWidth方法
	window.onresize = resizeWidth;
});
</script>
			</div>
<script type="text/javascript">
	setCal("timeContent");
</script>
	</body>
</html>
