<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
</head>
<body>
	<div id="menubar">
	<%@ include file="/partner/pnrIndex/menu.jsp"%>
	</div>
<iframe id="pnrFrame" name="pnrFrame" frameborder="0" width="100%" height="90%" src="${app}/partner/pnrIndex/pnrframe.jsp"></iframe>
</body>
</html>