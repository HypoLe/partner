<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
  <!--[if IE]><link rel="stylesheet" type="text/css" href="${app}/styles/common/ie.css" /><![endif]-->
</head>
<body>
<table  width="100%" height="100%">
<tr>
<td>
  <span>
	<div><marquee scrolldelay="150"  onmouseover="this.stop()"   onmouseout="this.start();">${listStr}</marquee></div>
  </span>
  </td>
  </tr>
</table>

</body>
</html>