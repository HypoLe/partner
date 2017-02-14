<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
  <!-- EXT LIBS verson 1.1 -->
  <script type="text/javascript" src="${app}/scripts/ext3/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext3/ext-all.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext3/source/locale/ext-lang-zh_CN.js"></script>
  <link rel="stylesheet" type="text/css" href="${app}/scripts/ext3/resources/css/ext-all.css" />
  <c:if test="${theme ne 'default'}"><link rel="stylesheet" type="text/css" href="${app}/scripts/ext3/resources/css/xtheme-gray.css" /></c:if>
  <link rel="stylesheet" type="text/css" href="${app}/styles/${theme}/ext-adpter.css" />
  
<script type="text/javascript">
Ext.onReady(function(){
    var win;
    var button = Ext.get('show-btn');

    button.on('click', function(){
        // create the window on the first click and reuse on subsequent clicks
        if(!win){
			win=new Ext.Window({
	title:"我的窗口",
	width:300,
	height:"",
	autoHeight:true,
	autoScroll:true,
	minimizable:false,
	maximizable:false,
	items:[
		{
			xtype:"form",
			title:"",
			labelWidth:"",
			labelAlign:"left",
			layout:"auto",
			formId:"kpiAlgorithm",
			width:"",
			autoScroll:true,
			submit:function(){
　　　　　　　　　　this.getEl().dom.action='GetForm.aspx',
　　　　　　　　　　this.getEl().dom.method='POST',
　　　　　　　　　　this.getEl().dom.submit();
　　　　　　　},
			frame:true,
			items:[
				{
					xtype:"label",
					text:"单选一"
				},
				{
					xtype:"radio",
					name :"radio",
					fieldLabel:"radio",
					inputValue:"3",
					boxLabel:"选择2"
				},
				{
					xtype:"radio",
					name :"radio",
					fieldLabel:"radio",
					inputValue:"2",
					boxLabel:"选择1"
				},
				{
					xtype:"radio",
					name :"radio",
					fieldLabel:"radio",
					inputValue:"1",
					boxLabel:"选择3"
				},
				{
					xtype:"label",
					text:"多选一",
					region:"north"
				},
				{
					xtype:"checkbox",
					fieldLabel:"标签",
					boxLabel:"选择3",
					region:"south"
				},
				{
					xtype:"checkbox",
					fieldLabel:"标签",
					boxLabel:"选择2"
				},
				{
					xtype:"checkbox",
					fieldLabel:"标签",
					boxLabel:"选择1"
				}
			],
			buttons : [{
					text:"确定",
					handler: function(){
                        alert(Ext.get("radio").getValue());
                        win.close(); 
                        Ext.getBody().unmask();
                    }
				}
			]
		}
	]
});
        }
        win.show(this);
        Ext.getBody().mask();
    });
});
</script>




</head>
<body>
<input type="button" id='show-btn' value="Show Dialog" /><br /><br />

</body>

</body>
</html>