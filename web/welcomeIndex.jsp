<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
    <%--
<script type="text/javascript"  src="${app}/scripts/partner/pnrIndex.js"></script>
--%>
<link rel="stylesheet" type="text/css" href="${app}/partner/pnrIndex/pnrIndex.css" />
    <script type="text/javascript">
		function infopubOver(){
			flag = false;
		}
		function infopubOut(){
			flag = true;
		}
  </script>
</head>
<body>
<%-- Put constants into request scope --%>
<eoms:constants scope="request" />
  <div id="content" class="clearfix">
   <div id="container"   style="width:1024px">
	<div id="middlePart" style="float: left;width:820px" >
		<!-- 滚动文字-->
		<div id="infopubList" onmouseover="infopubOver();" onmouseout="infopubOut()">
			<div id="infopubContainer">
				<iframe id="infopubFrame" name="infopubFrame" frameborder="0" width="100%" height="100%" 
					src="${app}/partner/baseinfo/index.do?method=listInfopubsForIndex" scrolling="no"></iframe>
		 	</div>
		</div> 
		
		<!-- 业务联系函 -->
		<div class="dataDetailList">
			<div class="dataDetailTitle">
				<div class="dataDetailTitleContent">
					<span >业务联系函 </span>
				</div>
			</div>
			<div class="dataDetailContent" >
					<span style="text-align:center;">
					<br/>业务联系函 数据表格
						<iframe id="evaFrame" name="evaFrame" frameborder="0" width="90%" height="150px" 
						src="" scrolling="no">
						</iframe>
					</span>
			</div>
		</div>
		<!-- 工单 -->
		<div class="dataDetailList">
			<div class="dataDetailTitle">
				<div class="dataDetailTitleContent">
					<span >工单 </span>
				</div>
			</div>
			<div class="dataDetailContent" id="dataDetailContent">
					<span style="text-align:center;">
						<iframe id="evaFrame" name="evaFrame" frameborder="0" width="90%" height="150px" 
						src="" scrolling="no">
						</iframe>
					</span>
			</div>
		</div>
		
		
		<!-- 巡检任务 -->
		<div class="dataDetailList">
			<div class="dataDetailTitle">
				<div class="dataDetailTitleContent">
					<span >巡检任务 </span>
				</div>
			</div>
			<div class="dataDetailContent" id="dataDetailContent">
					<span style="text-align:center;">
						<iframe id="evaFrame" name="evaFrame" frameborder="0" width="90%" height="150px" 
						src="" scrolling="no">
						</iframe>
					</span>
			</div>
		</div>
	</div>	
		
		 	<!--           右边部分          -->
	<div id="rightPart" style="float: right;background-color:white;border:1px #D8EDF2 solid; margin-top: 5px;width: 190px;">
			<!-- 公告 -->
			<!-- 辅助功能 -->
					<!-- 电子地图 -->
					<!-- 资料库 -->
		<div class="orderList">
			<div class="orderTitle" style="width: 135px!important;width: 185px">
				<div style="float: left">公告</div>
				<div style="float: right ;padding-left: 0px" >
						<a  target="pages" href="home/publish.do?method=getList&listType=3" style="color: white;">更多</a>
				</div>
			</div>
			<div class="msgContent">
				<iframe id="msgFrame" name="msgFrame" frameborder="0" width="94%" height="200px"
					src="${app}/home/publish.do?method=getNewInfo" scrolling="no"></iframe>
			</div>		
			
			<div class="orderTitle">辅助功能</div>
			<div class="msgContent">
			<iframe id="msgFrame" name="msgFrame" frameborder="0" width="94%" height="200px" 
				src="" scrolling="no"></iframe>
			</div>
			&nbsp;
		</div>
	</div>	
		
	<div id="footer"  style="float: left ;margin-bottom: 30px;margin-bottom: 40px;" >
	<!-- 版权信息 -->
	<span style="width: 100%; padding-left: 200px;" >
		代维服务热线：10010   E-mail:test@testboco.com		|   版权所有 中国联通
	</span>
</div>	
	</div>
 </div>
	</body>
</html>
