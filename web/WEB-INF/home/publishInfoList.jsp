<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<head>
	<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
	<style type="text/css">
		ul,li{margin:0;padding:0;line-height: 20px}
		#scrollDiv{width:90%;   height:220px;overflow:hidden ;font-size: 12px }
		#scrollDiv li{height:200px;}
		</style>
<script type="text/javascript">
		function AutoScroll(obj){
		        $(obj).find("ul:first").animate({
		                marginTop:"-20px"
		        },500,function(){
		                $(this).css({marginTop:"0px"}).find("li:first").appendTo(this);
		        });
		}
		$(document).ready(function(){
		setInterval('AutoScroll("#scrollDiv")',3000)
		});
</script>
</head>

<body>
	<c:if test="${!empty requestScope.pubList[0]}">
		<div  id="scrollDiv">
			<ul>
				<c:forEach var="info" items="${requestScope.pubList}">
					<li>
						${info }
					</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
	
	<c:if test="${empty requestScope.pubList[0]}">
		<div	style="font-size: 12px">
				无未读公告信息
		</div>
	</c:if>
	
</body>
</html>