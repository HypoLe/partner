
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript" src="${app}/scripts/layout/AppSimpleTree.js"></script>

<script type="text/javascript">
	var config = {			
		/**************
		* Tree Configs
		**************/
		treeGetNodeUrl:"basemetermgrs.do?method=xGetChildNodes",
		treeRootId:'-1',
		treeRootText:"${eoms:a2u('�?有项�?')}",
		ctxMenu:{
			newNode:{text:"${eoms:a2u('新建')}"},
			delNode:{text:"${eoms:a2u('删除')}"}
		},//end of ctxMenu		
		/**************
		* Form Configs
		**************/
		actions:{
			newNode : {
				btnText:"${eoms:a2u('保存')}",
				url:"basemetermgrs.do?method=xsave",
				init:function(){
					AppSimpleTree.setField("parentId","id");
				}
			},
			getNode : {
				url:"basemetermgrs.do?method=xget"
			},
			edtNode : {
				btnText:"${eoms:a2u('保存修改')}",
				url:"basemetermgrs.do?method=xedit"
			},
			delNode : {
				url:"basemetermgrs.do?method=xdelete"
			}
		},
		fieldOptions : {
			width:150
		},
		fields : [

			   /* Hidden Field
	            */
			  	new Ext.form.HiddenField({name: 'id'})

			   /* Hidden Field
	            */
			  	new Ext.form.HiddenField({name: 'id'})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.maintenUnitIdVal"/>',
	            	name: 'maintenUnitIdVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.maintenUnitIdRem"/>',
	            	name: 'maintenUnitIdRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.desktopComputerVal"/>',
	            	name: 'desktopComputerVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.desktopComputerRem"/>',
	            	name: 'desktopComputerRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.faxVal"/>',
	            	name: 'faxVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.faxRem"/>',
	            	name: 'faxRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.fixedPhoneVal"/>',
	            	name: 'fixedPhoneVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.fixedPhoneRem"/>',
	            	name: 'fixedPhoneRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.moveTestPhoneVal"/>',
	            	name: 'moveTestPhoneVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.moveTestPhoneRem"/>',
	            	name: 'moveTestPhoneRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.lineTesterVal"/>',
	            	name: 'lineTesterVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.lineTesterRem"/>',
	            	name: 'lineTesterRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.terraBlockTesterVal"/>',
	            	name: 'terraBlockTesterVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.terraBlockTesterRem"/>',
	            	name: 'terraBlockTesterRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.cellTesterVal"/>',
	            	name: 'cellTesterVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.cellTesterRem"/>',
	            	name: 'cellTesterRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.maintenanceToolsVal"/>',
	            	name: 'maintenanceToolsVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.maintenanceToolsRem"/>',
	            	name: 'maintenanceToolsRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.maintenanceCarsVal"/>',
	            	name: 'maintenanceCarsVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.maintenanceCarsRem"/>',
	            	name: 'maintenanceCarsRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.multimeterVal"/>',
	            	name: 'multimeterVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.multimeterRem"/>',
	            	name: 'multimeterRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.ACDCclampMeterVal"/>',
	            	name: 'ACDCclampMeterVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.ACDCclampMeterRem"/>',
	            	name: 'ACDCclampMeterRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.twoMInstrumentVal"/>',
	            	name: 'twoMInstrumentVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.twoMInstrumentRem"/>',
	            	name: 'twoMInstrumentRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.antennaAngleGaugeVal"/>',
	            	name: 'antennaAngleGaugeVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.antennaAngleGaugeRem"/>',
	            	name: 'antennaAngleGaugeRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.telescopeVal"/>',
	            	name: 'telescopeVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.telescopeRem"/>',
	            	name: 'telescopeRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.digitalCameraVal"/>',
	            	name: 'digitalCameraVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.digitalCameraRem"/>',
	            	name: 'digitalCameraRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.gradienterVal"/>',
	            	name: 'gradienterVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.gradienterRem"/>',
	            	name: 'gradienterRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.GPSLocatorVal"/>',
	            	name: 'GPSLocatorVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.GPSLocatorRem"/>',
	            	name: 'GPSLocatorRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.infraredThermoscopeVal"/>',
	            	name: 'infraredThermoscopeVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.infraredThermoscopeRem"/>',
	            	name: 'infraredThermoscopeRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.notebookPCVal"/>',
	            	name: 'notebookPCVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.notebookPCRem"/>',
	            	name: 'notebookPCRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.mobileGpsVal"/>',
	            	name: 'mobileGpsVal',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.mobileGps"/>',
	            	name: 'mobileGps',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="basemetermgrForm.remark"/>',
	            	name: 'remark',
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
		<dt>${eoms:a2u('添加')}</dt>
		<dd></dd>
		<dt>${eoms:a2u('修改')}</dt>
		<dd></dd>
		<dt>${eoms:a2u('删除')}</dt>
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

