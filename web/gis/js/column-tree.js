/*
 * Ext JS Library 2.3.0
 * Copyright(c) 2006-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.onReady(function(){
	var searchBar = new Ext.Toolbar({  
		width:330,
        items:[   
    '开始时间',' ', { xtype : 'textfield', width : '70', id : 'q_eventtime_start' , listeners:{focus:function(){WdatePicker({dateFmt:'yyyy-MM-dd'});} }},   
    '结束时间',' ', { xtype : 'textfield', width : '70', id : 'q_eventtime_start' , listeners:{focus:function(){WdatePicker({dateFmt:'yyyy-MM-dd'});} }},   
    '-', { xtype : 'button', text: '历史轨迹' }   
    ]});
    var tree = new Ext.tree.ColumnTree({
        width: 330,
        //autoHeight:true,
        height: 700,
        rootVisible:false,
        autoScroll:true,
        title: '资源信息',
        renderTo: $('resourceDiv'),
        tbar: searchBar,
        columns:[{
            header:'手机号码',
            width:140,
            dataIndex:'resourceId'
        },{
            header:'资源名称',
            width:120,
            dataIndex:'resourceName'
        },{
            header:'类型',
            width:80,
            dataIndex:'resourceType'
        }],
        loader: new Ext.tree.TreeLoader({
            dataUrl:'data3.jsp',
            uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
        }),
        root: new Ext.tree.AsyncTreeNode({
            text:'Tasks'
        }
        )
    });
    tree.render();
    tree.on("click",function(node){
    	
    	var url = 'data.jsp';
    	var resourceId = node.attributes['resourceId'];
		var resoureTypeName = node.attributes['resourceType'];
		var resourceType =1;
		if(resoureTypeName == '车辆'){
			resourceType =2 ;
			resourceId =node.attributes['resourceName'];
		}
		Ext.Ajax.request({  
                url : url,
                success : function someFn(result, request)  
                {  
                   	var json = result.responseText.evalJSON();
                   	if(json != null){
                   		var x = json.longitude;
				    	var y = json.latitude;
				    
				    	showPosition(x,y,json);
                   	}else{
                   		Ext.MessageBox.alert('信息',"无定位数据");
                   	}
                   	
                },  
                params : {resourceId: resourceId, resourceType: resourceType},
                method : 'POST'
            });  


    });
 
});