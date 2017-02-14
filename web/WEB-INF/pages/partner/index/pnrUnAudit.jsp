<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
  <!--[if IE]><link rel="stylesheet" type="text/css" href="${app}/styles/common/ie.css" /><![endif]-->
</head>
<body>
<script type="text/javascript" src="${app}/scripts/partner/TabPanel.js"></script>

<%
  request.setAttribute("userId", request.getParameter("userId")); 
%>

<script type="text/javascript">
    var _tabs;
    Ext.onReady(function(){
        var tabConfig = {
            items:[
          		{id:'unAudit',  text:'待审核', url:'${app}/partner/baseinfo/index.do?method=unAuditList', isIframe : true}
//                {id:'eva-unAudit',  text:'考核待审核', url:'${app}/partner/baseinfo/index.do?method=evaUnAuditList', isIframe : true},
//                {id:'agreement-unAudit',   text:'服务协议待审核', url:'${app}/partner/baseinfo/index.do?method=agreeUnAuditList', isIframe : true},
//                {id:'workplan-unAudit',   text:'工作计划待审核', url:'${app}/partner/baseinfo/index.do?method=workplanUnAuditList', isIframe : true},
//                {id:'tempTask-unAudit', text:'临时任务待审核', url:'${app}/partner/baseinfo/index.do?method=tempTaskUnAuditList', isIframe : true},
//                {id:'feeinfo-unAudit',   text:'合同待审核', url:'${app}/partner/baseinfo/index.do?method=feeUnAuditList', isIframe : true}
            ]
        };
        _tabs = new eoms.TabPanel('unAudit-detail-page', tabConfig);
    });
</script>
<iframe id="mainFrame" name="mainFrame" frameborder="0" width="0" height="0" src="${app}/partner/baseinfo/index.do?method=unAuditList"></iframe>
<div id="unAudit-detail-page"></div>

</body>
</html>