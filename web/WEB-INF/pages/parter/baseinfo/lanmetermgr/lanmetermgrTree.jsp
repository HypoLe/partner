
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript" src="${app}/scripts/layout/AppSimpleTree.js"></script>

<script type="text/javascript">
	var config = {			
		/**************
		* Tree Configs
		**************/
		treeGetNodeUrl:"lanmetermgrs.do?method=xGetChildNodes",
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
				url:"lanmetermgrs.do?method=xsave",
				init:function(){
					AppSimpleTree.setField("parentId","id");
				}
			},
			getNode : {
				url:"lanmetermgrs.do?method=xget"
			},
			edtNode : {
				btnText:"${eoms:a2u('保存修改')}",
				url:"lanmetermgrs.do?method=xedit"
			},
			delNode : {
				url:"lanmetermgrs.do?method=xdelete"
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
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.maintenUnitId"/>',
	            	name: 'maintenUnitId',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.maintenUnitIdCou"/>',
	            	name: 'maintenUnitIdCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.maintenUnitIdRem"/>',
	            	name: 'maintenUnitIdRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.OTDR"/>',
	            	name: 'OTDR',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.OTDRCou"/>',
	            	name: 'OTDRCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.OTDRRem"/>',
	            	name: 'OTDRRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.lighCableFinder"/>',
	            	name: 'lighCableFinder',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.lighCableFinderCou"/>',
	            	name: 'lighCableFinderCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.lighCableFinderRem"/>',
	            	name: 'lighCableFinderRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.groundFinder"/>',
	            	name: 'groundFinder',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.groundFinderCou"/>',
	            	name: 'groundFinderCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.groundFinderRem"/>',
	            	name: 'groundFinderRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.portableGPS"/>',
	            	name: 'portableGPS',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.portableGPSCou"/>',
	            	name: 'portableGPSCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.portableGPSRem"/>',
	            	name: 'portableGPSRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.autoFiberWeld"/>',
	            	name: 'autoFiberWeld',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.autoFiberWeldCou"/>',
	            	name: 'autoFiberWeldCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.autoFiberWeldRem"/>',
	            	name: 'autoFiberWeldRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.fiberStrip"/>',
	            	name: 'fiberStrip',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.fiberStripCou"/>',
	            	name: 'fiberStripCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.fiberStripRem"/>',
	            	name: 'fiberStripRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.losetubeStrip"/>',
	            	name: 'losetubeStrip',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.losetubeStripCou"/>',
	            	name: 'losetubeStripCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.losetubeStripRem"/>',
	            	name: 'losetubeStripRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.pspStrip"/>',
	            	name: 'pspStrip',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.pspStripCou"/>',
	            	name: 'pspStripCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.pspStripRem"/>',
	            	name: 'pspStripRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.lighCableReame"/>',
	            	name: 'lighCableReame',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.lighCableReameCou"/>',
	            	name: 'lighCableReameCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.lighCableReameRem"/>',
	            	name: 'lighCableReameRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.beamTubeFinde"/>',
	            	name: 'beamTubeFinde',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.beamTubeFindeCou"/>',
	            	name: 'beamTubeFindeCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.beamTubeFindeRem"/>',
	            	name: 'beamTubeFindeRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.fiberAmputat"/>',
	            	name: 'fiberAmputat',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.fiberAmputatCou"/>',
	            	name: 'fiberAmputatCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.fiberAmputatRem"/>',
	            	name: 'fiberAmputatRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.VLinker"/>',
	            	name: 'VLinker',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.VLinkerCou"/>',
	            	name: 'VLinkerCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.VLinkerRem"/>',
	            	name: 'VLinkerRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.scavPump"/>',
	            	name: 'scavPump',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.scavPumpCou"/>',
	            	name: 'scavPumpCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.scavPumpRem"/>',
	            	name: 'scavPumpRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.fiberRecognizer"/>',
	            	name: 'fiberRecognizer',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.fiberRecognizerCou"/>',
	            	name: 'fiberRecognizerCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.fiberRecognizerRem"/>',
	            	name: 'fiberRecognizerRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.mechSpecTool"/>',
	            	name: 'mechSpecTool',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.mechSpecToolCou"/>',
	            	name: 'mechSpecToolCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.mechSpecToolRem"/>',
	            	name: 'mechSpecToolRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.dynamo"/>',
	            	name: 'dynamo',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.dynamoCou"/>',
	            	name: 'dynamoCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.dynamoRem"/>',
	            	name: 'dynamoRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.mainterCar"/>',
	            	name: 'mainterCar',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.mainterCarCou"/>',
	            	name: 'mainterCarCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.mainterCarRem"/>',
	            	name: 'mainterCarRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.waterPump"/>',
	            	name: 'waterPump',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.waterPumpCou"/>',
	            	name: 'waterPumpCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.waterPumpRem"/>',
	            	name: 'waterPumpRem',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.mobileGps"/>',
	            	name: 'mobileGps',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.mobileGpsCou"/>',
	            	name: 'mobileGpsCou',
	            	allowBlank:false
	        	})

		    	,new Ext.form.TextField({
	            	fieldLabel: '<fmt:message key="lanmetermgrForm.mobileGpsRem"/>',
	            	name: 'mobileGpsRem',
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

