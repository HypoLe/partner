<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
  <!--[if IE]><link rel="stylesheet" type="text/css" href="${app}/styles/common/ie.css" /><![endif]-->
</head>
<body>
<script type="text/javascript" src="${app}/scripts/partner/TabPanel.js"></script>

<script type="text/javascript">
    var _tabs;
    Ext.onReady(function(){
        var tabConfig = {
            items:[
            <c:if test="${fn:substring(sessionScope.sessionform.deptid, 0, 1)=='1'}">
				{id:'eva-unDo',   text:'考核等待处理', url:'${app}/partner/baseinfo/index.do?method=evaUndoList', isIframe : true},
                {id:'agreeunDo2',   text:'综合评分等待处理', url:'${app}/partner/baseinfo/index.do?method=toCloseAgreement', isIframe : true},
                {id:'addEva',   text:'考核等待制定', url:'${app}/partner/baseinfo/index.do?method=addEvaList', isIframe : true},
			</c:if>
			<c:if test="${fn:substring(sessionScope.sessionform.deptid, 0, 1)!='1'}">
				{id:'workplan-unDo',   text:'工作计划待处理', url:'${app}/partner/baseinfo/index.do?method=workplanUndoList', isIframe : true},
                {id:'tempTask-unDo', text:'临时任务待处理', url:'${app}/partner/baseinfo/index.do?method=tempTaskUndoList', isIframe : true},
                {id:'agreeunDo1',   text:'协议待总结', url:'${app}/partner/baseinfo/index.do?method=uploadAgreement', isIframe : true},
			</c:if>
                {id:'feeinfo-unDo',   text:'付费信息待处理', url:'${app}/partner/baseinfo/index.do?method=feepayUndoList', isIframe : true},
                {id:'contract',   text:'无合同信息的协议', url:'${app}/partner/baseinfo/index.do?method=contractList', isIframe : true}
            ]
        };
        _tabs = new eoms.TabPanel('unDo-detail-page', tabConfig);
    });
</script>

<div id="unDo-detail-page"></div>
</body>
</html>