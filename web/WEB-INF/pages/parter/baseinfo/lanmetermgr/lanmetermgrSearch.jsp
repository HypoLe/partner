<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ include file="/common/header_eoms_ext_debug.jsp"%>
<!--根据给定的实例名生成标题 -->
<title><fmt:message key="lanmetermgrForm.heading.search"/></title>
<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/ApplicationResources-parter-baseinfo-lanmetermgr">
<content tag="heading"><fmt:message key="lanmetermgrForm.heading.search"/></content>
</fmt:bundle>
<script type="text/javascript">
	function onLoadFunctions(){
	         var treeAction3='${app}/dept/tawSystemDepts.do?method=userFromDaiweiDept&showType=dept';
			 new xbox({
				btnId:'maintenUnitId',
				treeDataUrl:treeAction3,treeRootId:'-1',treeRootText:'${eoms:a2u('代维部门')}',treeChkMode:'single',treeChkType:'dept',
				showChkFldId:'maintenUnitId',saveChkFldId:'tempid'
			});
}

	var deptTree;
	function FCKeditor_OnComplete(editorInstance) {
		window.status = editorInstance.Description;
	}	
	Ext.onReady(function(){
		deptViewer = new Ext.JsonView("view",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				emptyText : '<div>${eoms:a2u('没有选择项目')}</div>'
			}
		);
		var s='[]';
		deptViewer.jsonData = eoms.JSONDecode(s);
		deptViewer.refresh();
		var	treeAction='${app}/dept/tawSystemDepts.do?method=userFromDaiweiDept&showType=dept';
		deptTree = new xbox({
			btnId:'maintenUnitId',dlgId:'hello-dlg',
			treeDataUrl:treeAction,treeRootId:'-1',treeRootText:'${eoms:a2u('代维公司选择')}',treeChkMode:'single',treeChkType:'dept',
			showChkFldId:'maintenUnitId',saveChkFldId:'tempid',viewer:deptViewer
		});
	});
</script>
<!--对表单的自动生成的处�?-->
<html:form action="saveLanmetermgr" method="post" styleId="lanmetermgrForm"> 
<ul>
<table class="formTable middle"  cellspacing="1">
    <tr class="tr_show">   
		    <td >      
		        <eoms:label styleClass="desc" key="lanmetermgrForm.maintenUnitId"/>
		        <html:errors property="maintenUnitId"/>
			     <input type="text"  readonly id ="maintenUnitId" name="maintenUnitId" styleClass="text"/>
					<input type="hidden" id="tempid" name="tempid"/>
					<div id="view" class="viewer-box"></div>
			  <!--   <html:text property="maintenUnitId" styleId="maintenUnitId" styleClass="text medium"/>-->
		     </td>	
    </tr>
 </table>
<%--<table class="formTable middle" cellspacing="3" >
    <!--表示对所有的域有�? -->
	       <html:hidden property="id"/>
		 
		<!--    <li>
		        
		        <eoms:label styleClass="desc" key="lanmetermgrForm.maintenUnitIdCou"/>
		        <html:errors property="maintenUnitIdCou"/>
			    
			        <html:text property="maintenUnitIdCou" styleId="maintenUnitIdCou" styleClass="text medium"/>
		    </li>
		    <li>
		        
		        <eoms:label styleClass="desc" key="lanmetermgrForm.maintenUnitIdRem"/>
		        <html:errors property="maintenUnitIdRem"/>
			   
			        <html:text property="maintenUnitIdRem" styleId="maintenUnitIdRem" styleClass="text medium"/>
		    </li>-->
		 <tr class="tr_show">   		       
		       <td >     
		           <eoms:label styleClass="desc" key="lanmetermgrForm.nameOrTypes"/>

		  </td>		   
		      <td >
	   
			      <eoms:label styleClass="desc" key="lanmetermgrForm.count"/>
		  </td>
		      <td>
	  
			       <eoms:label styleClass="desc" key="lanmetermgrForm.remark"/>
		   </td>
		    
		     </tr>
		 <tr class="tr_show">   		       
		       <td >     
		           <eoms:label styleClass="desc" key="lanmetermgrForm.OTDR"/>
		           <html:errors property="OTDR"/>
		  </td>		   
		      <td >
	   
			       <html:text property="OTDRCou" styleId="OTDRCou" styleClass="text medium"/>
		  </td>
		      <td>
	 
			       <html:textarea property="OTDRRem" styleId="OTDRRem" styleClass="text medium"/>
		   </td>
		    
		     </tr>
		    <tr class="tr_show">   	
		     <td>		        
		        <eoms:label styleClass="desc" key="lanmetermgrForm.lighCableFinder"/>
		        <html:errors property="lighCableFinder"/>
			  </td>
		        <td>
		  
			        <html:text property="lighCableFinderCou" styleId="lighCableFinderCou" styleClass="text medium"/>
		  </td>
		        <td>
   
			        <html:textarea property="lighCableFinderRem" styleId="lighCableFinderRem" styleClass="text medium"/>
		   </td>
		 </tr>
		        <tr class="tr_show">  
		         <td> 		  
		        <eoms:label styleClass="desc" key="lanmetermgrForm.groundFinder"/>
		        <html:errors property="groundFinder"/>
			   
			  </td>
		    <td>
		       
			  
			        <html:text property="groundFinderCou" styleId="groundFinderCou" styleClass="text medium"/>
			 </td>
		    <td>
		       
		        <html:textarea property="groundFinderRem" styleId="groundFinderRem" styleClass="text medium"/>
			  </td>
		    </tr>
		       <tr class="tr_show">  
		         <td> 		  
		        <eoms:label styleClass="desc" key="lanmetermgrForm.portableGPS"/>
		        <html:errors property="portableGPS"/>
			   
			    </td>
		    <td>
		        
			   
			        <html:text property="portableGPSCou" styleId="portableGPSCou" styleClass="text medium"/>
		    </td>
		    <td>
		     
			  
			        <html:textarea property="portableGPSRem" styleId="portableGPSRem" styleClass="text medium"/>
		     </td>		    
		  </tr> 
		  <tr class="tr_show">  
		         <td> 	
		        <eoms:label styleClass="desc" key="lanmetermgrForm.autoFiberWeld"/>
		        <html:errors property="autoFiberWeld"/>
			   
			     </td>
		    <td>
		        
		   
			        <html:text property="autoFiberWeldCou" styleId="autoFiberWeldCou" styleClass="text medium"/>
		     </td>
		    <td>
		       
			   
			        <html:textarea property="autoFiberWeldRem" styleId="autoFiberWeldRem" styleClass="text medium"/>
		     </td>
		    </tr> 
		    <tr class="tr_show">  
		         <td> 		      
		        <eoms:label styleClass="desc" key="lanmetermgrForm.fiberStrip"/>
		        <html:errors property="fiberStrip"/>
		    </td>
		    <td>
		        
			   
			        <html:text property="fiberStripCou" styleId="fiberStripCou" styleClass="text medium"/>
		     </td>
		    <td>
		      
			
			        <html:textarea property="fiberStripRem" styleId="fiberStripRem" styleClass="text medium"/>
		     </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		     
		        <eoms:label styleClass="desc" key="lanmetermgrForm.losetubeStrip"/>
		        <html:errors property="losetubeStrip"/>
		     </td>
		    <td>
		     
		 
			        <html:text property="losetubeStripCou" styleId="losetubeStripCou" styleClass="text medium"/>
		    </td>
		    <td>
		       
			   
			        <html:textarea property="losetubeStripRem" styleId="losetubeStripRem" styleClass="text medium"/>
		     </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		       
		        <eoms:label styleClass="desc" key="lanmetermgrForm.pspStrip"/>
		        <html:errors property="pspStrip"/>
		    </td>
		    <td>
		       
		  
			        <html:text property="pspStripCou" styleId="pspStripCou" styleClass="text medium"/>
		    </td>
		    <td>
		       
		        <html:textarea property="pspStripRem" styleId="pspStripRem" styleClass="text medium"/>
		     </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		      
		        <eoms:label styleClass="desc" key="lanmetermgrForm.lighCableReame"/>
		        <html:errors property="lighCableReame"/>
		     </td>
		    <td>
		      
		   
			        <html:text property="lighCableReameCou" styleId="lighCableReameCou" styleClass="text medium"/>
		     </td>
		    <td>
		       
   
			        <html:textarea property="lighCableReameRem" styleId="lighCableReameRem" styleClass="text medium"/>
		   </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		    
		        <eoms:label styleClass="desc" key="lanmetermgrForm.beamTubeFinde"/>
		        <html:errors property="beamTubeFinde"/>
		    </td>
		    <td>
		        
			    
			        <html:text property="beamTubeFindeCou" styleId="beamTubeFindeCou" styleClass="text medium"/>
		   </td>
		    <td>
		       
		  
			        <html:textarea property="beamTubeFindeRem" styleId="beamTubeFindeRem" styleClass="text medium"/>
		    </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		       
		        <eoms:label styleClass="desc" key="lanmetermgrForm.fiberAmputat"/>
		        <html:errors property="fiberAmputat"/>
		     </td>
		    <td>
		      
	   
			        <html:text property="fiberAmputatCou" styleId="fiberAmputatCou" styleClass="text medium"/>
		     </td>
		    <td>
		        
	  
			        <html:textarea property="fiberAmputatRem" styleId="fiberAmputatRem" styleClass="text medium"/>
		   </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		     
		        <eoms:label styleClass="desc" key="lanmetermgrForm.VLinker"/>
		        <html:errors property="VLinker"/>
			  
			     </td>
		    <td>
		    
		    
			        <html:text property="VLinkerCou" styleId="VLinkerCou" styleClass="text medium"/>
		      </td>
		    <td>
		      
  
			        <html:textarea property="VLinkerRem" styleId="VLinkerRem" styleClass="text medium"/>
		    </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		     
		        <eoms:label styleClass="desc" key="lanmetermgrForm.scavPump"/>
			  
		     </td>
		    <td>
		      
	 
			        <html:text property="scavPumpCou" styleId="scavPumpCou" styleClass="text medium"/>
		     </td>
		    <td>
		      
  					 <html:textarea property="scavPumpRem" styleId="scavPumpRem" styleClass="text medium"/>
		      </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		     
		        <eoms:label styleClass="desc" key="lanmetermgrForm.fiberRecognizer"/>
		        <html:errors property="fiberRecognizer"/>
					     </td>
		    <td>
		       
		  
			        <html:text property="fiberRecognizerCou" styleId="fiberRecognizerCou" styleClass="text medium"/>
		     </td>
		    <td>
		   
			        <html:textarea property="fiberRecognizerRem" styleId="fiberRecognizerRem" styleClass="text medium"/>
		       </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		      
		        <eoms:label styleClass="desc" key="lanmetermgrForm.mechSpecTool"/>
		        <html:errors property="mechSpecTool"/>
			   
			        </td>
		    <td>
		       
        <html:text property="mechSpecToolCou" styleId="mechSpecToolCou" styleClass="text medium"/>
		     </td>
		    <td>
	  
			        <html:textarea property="mechSpecToolRem" styleId="mechSpecToolRem" styleClass="text medium"/>
		    </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		       
		        <eoms:label styleClass="desc" key="lanmetermgrForm.dynamo"/>
		        <html:errors property="dynamo"/>
				    </td>
		    <td>
		       
	  
			        <html:text property="dynamoCou" styleId="dynamoCou" styleClass="text medium"/>
		    </td>
		    <td>
		       
		  
			        <html:textarea property="dynamoRem" styleId="dynamoRem" styleClass="text medium"/>
		    </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		       
		        <eoms:label styleClass="desc" key="lanmetermgrForm.mainterCar"/>
		        <html:errors property="mainterCar"/>
		    </td>
		    <td>
		        
  
			        <html:text property="mainterCarCou" styleId="mainterCarCou" styleClass="text medium"/>
		    </td>
		    <td>
		    

			        <html:textarea property="mainterCarRem" styleId="mainterCarRem" styleClass="text medium"/>
		     </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		        
		        <eoms:label styleClass="desc" key="lanmetermgrForm.waterPump"/>
		        <html:errors property="waterPump"/>
			
			    </td>
		    <td>
		       

			        <html:text property="waterPumpCou" styleId="waterPumpCou" styleClass="text medium"/>
		    </td>
		    <td>
		      
	 
			        <html:textarea property="waterPumpRem" styleId="waterPumpRem" styleClass="text medium"/>
		     </td>
		   </tr>  
		   <tr class="tr_show">  
		    <td>
		      
		        <eoms:label styleClass="desc" key="lanmetermgrForm.mobileGps"/>
		        <html:errors property="mobileGps"/>
			   
		    </td>
		    <td>

			        <html:text property="mobileGpsCou" styleId="mobileGpsCou" styleClass="text medium"/>
		    </td>
		    <td>

			   
			        <html:textarea property="mobileGpsRem" styleId="mobileGpsRem" styleClass="text medium"/>
		     </td>
		   </tr>  
		     </table>
		     --%><html:hidden property="id"/>
    <li class="buttonBar bottom">
      <!--  <html:submit styleClass="button" property="method.xsave" onclick="bCancel=false">
            <fmt:message key="button.save"/>
        </html:submit>
        <!--用自动生成的参数调用Javascript --> 
       <html:submit styleClass="button" property="method.search" onclick="bCancel=true; return confirmDelete('Lanmetermgr')">
            <fmt:message key="button.search"/>
        </html:submit>

      <!--   <html:cancel styleClass="button" onclick="bCancel=true">
            <fmt:message key="button.cancel"/>
        </html:cancel> --> 
    </li>
</ul>
  <!--自动生成的Javascript脚本-->

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>