<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<head>
	<style type="text/css">
		ul,li{margin:0;padding:0;line-height: 20px;font-size: 12px}
		a {
			text-decoration:none;
			color:#039;
		}
		body {
			    background-color: #EBF2FC;
			    background-position: center top;
			    background-repeat: no-repeat;
			    margin: 0;
			}
		</style>
</head>

<body>
		<c:if test="${!empty requestScope.pubList[0]}">
			<marquee behavior="scroll" scrolldelay="200" direction="up" width="100%" height="100%" onmouseover="this.stop()"  onmouseout="this.start()">
				<ul class="sbr-box-ul">
					<c:forEach var="info" items="${requestScope.pubList}" varStatus="s">
						<li style="">
							<a href="#">${s.index+1 }：${info }</a>
						</li>
					</c:forEach>
				</ul>
			</marquee>	
		</c:if>
		<c:if test="${empty requestScope.pubList[0]}">
			<div style="font-size: 12px">
					无未读公告信息
			</div>
		</c:if>
		
		<%--
		<c:if test="${!empty requestScope.pubList[0]}">
	<marquee behavior="scroll" scrolldelay="200" direction="up" width="100%" height="100%" onmouseover="this.stop()"  onmouseout="this.start()">
			<div  id="scrollDiv">
				<ul>
					<c:forEach var="info" items="${requestScope.pubList}" varStatus="s">
						<li style="">
							${s.index+1 }：${info }
						</li>
					</c:forEach>
				</ul>
			</div>
	</marquee>	
		</c:if>
		<c:if test="${empty requestScope.pubList[0]}">
			<div	style="font-size: 12px">
					无未读公告信息
			</div>
		</c:if>
	--%>
</body>
</html>