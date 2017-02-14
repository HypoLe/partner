<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript" src="${app}/scripts/layout/AppSimpleTree.js"></script>
<script type="text/javascript">
//覆盖doDelNode，改变删除时弹出框提示内容
AppSimpleTree.doDelNode = function(nodeData,node){
    		var params = "id="+nodeData.id;
    		
    		if(typeof config.actions.delNode.customData == "function"){
    			params = config.actions.delNode.customData(nodeData);
    		}
    		
	    	//弹出一个确认框
	    	Ext.MessageBox.confirm('确认:', '删除此项会同时删除该项下的所有子项，确定删除该项吗？', 
	    		function(btn){
	    			if(btn=="yes"){
				    	Ext.Ajax.request({
				    		method : 'post',
				    		url : config.actions.delNode.url,
				    		scope : AppSimpleTree,
				    		success: function(){
								if(node){
	            					this.treesm.selectPrevious();
	            					node.parentNode.removeChild(node);
	        					}
	        					this.initRegion('east','helpPanel');
	        					this.reloadStatus.deleted = true;
	        					Ext.MessageBox.alert('提示',"您成功删除了一个节点。");
	        					this.initLayout();
				    		},
				    		failure: function() {
						    	Ext.MessageBox.alert('提示',"删除失败");
							},
				    		params:params
				    	});   				
	    			}
	    		}
	    	);
}
</script>
<script type="text/javascript">
	var config = {
			
		/**************
		* Tree Configs
		**************/
		treeGetNodeUrl:"${app}/area/tawSystemAreas.do?method=getNodes",
		treeRootId:'-1',
		treeRootText:"所有地域",
		ctxMenu:{
			newNode:{text:"新建下级地域"},
			delNode:{text:"删除这个地域"}
		},//end of ctxMenu
		
		/**************
		* Form Configs
		**************/
		actions:{
			newNode : {
				btnText:"保存",
				url:"${app}/area/tawSystemAreas.do?method=xsave",
				init:function(){
					AppSimpleTree.setField("parentAreaid","id");
				}
			},
			getNode : {
				url:"${app}/area/tawSystemAreas.do?method=xget"
			},
			edtNode : {
				btnText:"保存修改",
				url:"${app}/area/tawSystemAreas.do?method=xedit"
			},
			delNode : {
				url:"${app}/area/tawSystemAreas.do?method=xdelete"
			}
		},
		fieldOptions : {
			width:150
		},
		fields : [
	        new Ext.form.TextField({
	            fieldLabel: '<fmt:message key="tawSystemAreaForm.areaname"/>',
	            name: 'areaname',
	            allowBlank:false
	        }),
	        new Ext.form.TextArea({
	            fieldLabel: '<fmt:message key="tawSystemAreaForm.areacode"/>',
	            name: 'areacode',
	            grow: true,
	            preventScrollbars:true
	        }),	
	        new Ext.form.ComboBox({
	            fieldLabel: '<fmt:message key="tawSystemAreaForm.capital"/>',
	            hiddenName:'capital',
	            store: new Ext.data.SimpleStore({
	                fields: ['typevalue', 'type'],
	                data : [
	        				['0', '否'],
	        				['1', '是']
	        			   ]
	            }),
	            displayField:'type',
	            valueField:'typevalue',
	            editable:false,
	            mode: 'local',
	            triggerAction: 'all',
	            emptyText:'Please Select',
	            selectOnFocus:true,
	            allowBlank:false,
	            value:'0'
	        }),	        
	        new Ext.form.TextArea({
	            fieldLabel: '<fmt:message key="tawSystemAreaForm.remark"/>',
	            name: 'remark',
	            grow: true,
	            preventScrollbars:true
	        }),	
	        new Ext.form.HiddenField({name: 'leaf',id:'leaf'}),
	        new Ext.form.HiddenField({name: 'areaid',id:'areaid'}),
	        new Ext.form.HiddenField({name: 'oldAreaName'}),
	        new Ext.form.HiddenField({name: 'id',id:'id'}),
	        new Ext.form.HiddenField({name: 'parentAreaid',id:'parentAreaid'}),
	        new Ext.form.HiddenField({name: 'ordercode',id:'ordercode'})
		], // end of fields
		
		/************************
		* Custom onLoad Functions
		*************************/	
		onLoadFunctions:function(){
		}
	}; // end of config
	Ext.onReady(AppSimpleTree.init, AppSimpleTree);
</script>

<div id="headerPanel" class="app-header"><img src="${app}/styles/${theme}/images/header-area.gif"></div>
<div id="helpPanel" class="app-panel">
	<dl>
		<dt>功能说明</dt>
		<dd>管理设定的所有地域，呈树状结构，可实现增删改查等功能。有"组织管理"权限的用户有此操作的权限。</dd>
	</dl>
	<br/>
	<dl>
		<dt>添加一个下级地域</dt>
		<dd>在树图中的地域上点击右键，并选择"新建下级地域"</dd>
		<dt>修改一个地域的信息</dt>
		<dd>在树图中的地域上点击右键，并选择"修改"</dd>
		<dt>删除地域</dt>
		<dd>在树图中的地域上点击右键，并选择"删除这个地域"</dd>
	</dl>
</div>
<div id="treePanel">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel">
	<div id="formPanel-body" class="app-panel"></div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
