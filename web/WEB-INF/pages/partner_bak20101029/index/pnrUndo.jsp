<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
  <!--[if IE]><link rel="stylesheet" type="text/css" href="${app}/styles/common/ie.css" /><![endif]-->
</head>
<body>
<script type="text/javascript" src="${app}/scripts/partner/TabPanel.js"></script>

<script type="text/javascript">
    var _tabs;
    Ext.onReady(function(){
        var tabConfig = {
            items:[
                {id:'workplan-unDo',   text:'工作计划待执行', url:'${app}/partner/baseinfo/index.do?method=workplanUndoList', isIframe : true},
                {id:'tempTask-unDo', text:'临时任务待执行', url:'${app}/partner/baseinfo/index.do?method=tempTaskUndoList', isIframe : true},
                {id:'feeinfo-unDo',   text:'付费计划待执行', url:'${app}/partner/baseinfo/index.do?method=feepayUndoList', isIframe : true}
            ]
        };
        _tabs = new eoms.TabPanel('unDo-detail-page', tabConfig);
    });
</script>

<div id="unDo-detail-page"></div>
</body>
</html>