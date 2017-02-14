<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>
<html>
  <head>
	<link rel="stylesheet" type="text/css" href="/partner/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
	<script type="text/javascript" src="/partner/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="/partner/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
	<script type="text/javascript" src="/partner/deviceManagement/jquery-ui-1.8.14.custom/development-bundle/ui/jquery.effects.bounce.js"></script>
  </head>
  <body>
  <%
String str = StaticMethod.nullObject2String(request.getAttribute("msg"));
%>
<script language="javascript">
	$(function() {
		var pBody = $(window.parent)[0].jq("body");
		 var divObj="<div id=\"msgDIV\" style=\" position: relative; left:75%;top:80%;width: 290px; height: 235px; z-index:99999\">";
		 divObj+="<div id=\"effect\" title=\"提示信息\" class=\"ui-widget-content ui-corner-all\" style=\"padding: 0.4em\">";
		 divObj+="<%=str%>";
		 divObj+="</div>"+"</div>";
		pBody.append(divObj);
		function runEffect() {
			var option={direction:'down'};
			$(window.parent)[0].jq("#effect").dialog({ 
				position: ["right", "bottom"],
				show:"slide",
				hide:"slide"
			});
			//$(window.parent)[0].jq("#effect").show( "slide", option, 3000,callback);
		};
		//回调函数5s后自动关闭提示框
		function callback() {
			setTimeout(function() {
				$(window.parent)[0].jq( "#msgDIV" ).hide();
			}, 5000 );
		};
		runEffect();
		callback();
	});
	
	//关闭提示框
	function closeMsgWin() {
		$(window.parent)[0].jq("#effect:visible").removeAttr("style").fadeOut();
	}
</script>
 </body>
</html>

