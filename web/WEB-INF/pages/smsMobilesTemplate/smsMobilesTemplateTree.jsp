
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript" src="${app}/scripts/layout/AppSimpleTree.js"></script>

<script type="text/javascript">
	var config = {			
		/**************
		* Tree Configs
		**************/
		treeGetNodeUrl:"smsMobilesTemplates.do?method=xGetChildNodes",
		treeRootId:'-1',
		treeRootText:"${eoms:a2u('鎵�鏈夐」鐩�')}",
		ctxMenu:{
			newNode:{text:"${eoms:a2u('鏂板缓')}"},
			delNode:{text:"${eoms:a2u('鍒犻櫎')}"}
		},//end of ctxMenu		
		/**************
		* Form Configs
		**************/
		actions:{
			newNode : {
				btnText:"${eoms:a2u('淇濆瓨')}",
				url:"smsMobilesTemplates.do?method=xsave",
				init:function(){
					AppSimpleTree.setField("parentId","id");
				}
			},
			getNode : {
				url:"smsMobilesTemplates.do?method=xget"
			},
			edtNode : {
				btnText:"${eoms:a2u('淇濆瓨淇敼')}",
				url:"smsMobilesTemplates.do?method=xedit"
			},
			delNode : {
				url:"smsMobilesTemplates.do?method=xdelete"
			}
		},
		fieldOptions : {
			width:150
		},
		fields : [

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="smsMobilesTemplateForm.name"/>',
	            	name: 'name',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="smsMobilesTemplateForm.remark"/>',
	            	name: 'remark',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="smsMobilesTemplateForm.users"/>',
	            	name: 'users',
	            	allowBlank:false
	        	})

			   /* Hidden Field
	            */
			  	new Ext.form.HiddenField({name: 'id'})

			   /* Hidden Field
	            */
			  	new Ext.form.HiddenField({name: 'id'})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="smsMobilesTemplateForm.mobiles"/>',
	            	name: 'mobiles',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="smsMobilesTemplateForm.deleted"/>',
	            	name: 'deleted',
	            	allowBlank:false
	        	})

			], // end of fields
		/************************
		* Custom onLoad Functions
		*************************/	
		onLoadFunctions:function(){
		}
	}; // end of config
	Ext.onReady(AppSimpleTree.init, AppSimpleTree);
</script>

<div id="headerPanel" class="app-header"><h1>Title</h1></div>
<div id="helpPanel" class="app-panel">
	<dl>
		<dt>${eoms:a2u('娣诲姞')}</dt>
		<dd></dd>
		<dt>${eoms:a2u('淇敼')}</dt>
		<dd></dd>
		<dt>${eoms:a2u('鍒犻櫎')}</dt>
		<dd></dd>
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

