

Ext.onReady(function(){

    Ext.QuickTips.init();

//    var msg = function(msg){
//        Ext.Msg.show({
//            title: '温馨提示',
//            msg: msg,
//            minWidth: 200,
//            modal: true,
//            icon: Ext.Msg.INFO,
//            buttons: Ext.Msg.OK
//        });
//    };   

    var fp = new Ext.FormPanel({
        renderTo: 'fi-form',
        fileUpload: true,
        width: 500,
        frame: true,
        title: '模板导入',
        autoHeight: true,
        bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 55,
        defaults: {
            anchor: '95%',
            allowBlank: false,
            msgTarget: 'side'
        },
        items: [{xtype: 'radiogroup',
            	fieldLabel: '模板类型',
           		 items: [
                	{boxLabel: '考核指标', name: 'importType', inputValue: 0, checked: true},
                	{boxLabel: '工作任务', name: 'importType', inputValue: 1, disabled: true}
            	]},{
            	xtype: 'fileuploadfield',
            	id: 'form-file',
            	emptyText: '请选择模板文件(*.xls)',
            	fieldLabel: '模板文件',
            	name: 'file',
           	 buttonText: '',
           	 buttonCfg: {
                iconCls: 'upload-icon'
            	}
       	 }],
        buttons: [{
            text: '导入',
            handler: function(){
                if(fp.getForm().isValid()){
	                fp.getForm().submit({
	                    url: 'impExamineInfor.do?method=importFileExamineInfor',
	                    waitTitle: '请稍后',
	                    waitMsg: '正在导入文件....',
	                    success: function(fp, action){
	                		window.location.href('newExamine.do?method=list');
	                		//alert("dfdfd");
	                    },
	                    failure: function(fp, action){
	                    	//msg(action);
	                    	 Ext.MessageBox.alert('温馨提示', action.result.msg);
	                    }
	                });
                }
            }
        }]
    });

});