<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ include file="/common/header_eoms_ext_debug.jsp"%>
<!--根据给定的实例名生成标题 -->
<title><fmt:message key="basemetermgrDetail.title"/></title>
<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/ApplicationResources-parter-baseinfo-basemetermgr">
<content tag="heading"><fmt:message key="basemetermgrList.heading.add"/></content>
</fmt:bundle> 
<script type="text/javascript">
	function onSubmit(){
		if(checknumber(document.forms[0].desktopComputerVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.desktopComputerVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].faxVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.faxVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].fixedPhoneVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.fixedPhoneVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].moveTestPhoneVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.moveTestPhoneVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].lineTesterVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.lineTesterVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].terraBlockTesterVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.terraBlockTesterVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].cellTesterVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.cellTesterVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].maintenanceToolsVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.maintenanceToolsVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].maintenanceCarsVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.maintenanceCarsVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].multimeterVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.multimeterVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].ACDCclampMeterVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.ACDCclampMeterVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].twoMInstrumentVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.twoMInstrumentVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].antennaAngleGaugeVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.antennaAngleGaugeVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].telescopeVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.telescopeVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].digitalCameraVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.digitalCameraVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].gradienterVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.gradienterVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].infraredThermoscopeVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.infraredThermoscopeVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].notebookPCVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.notebookPCVal.focus();
				return false; 
			}else if(checknumber(document.forms[0].mobileGpsVal.value)) 
			{ 
				alert('${eoms:a2u(" 必须填写数字!")}'); 
				document.all.mobileGpsVal.focus();
				return false; 
			}
		document.all.basemetermgrForm.action=='${app}/saveBasemetermgr.do?method=xsave';
    	document.all.basemetermgrForm.submit();
        return true;
	}
	function checknumber(String) 
	{ 
		var Letters = "1234567890"; 
		var i; 
		var c; 
		for( i = 0; i < String.length; i ++ ) 
		{ 
			c = String.charAt( i ); 
			if (Letters.indexOf( c ) ==-1) 
			{ 
			return true; 
			} 
		} 
		return false; 
	} 
</script>
<script type="text/javascript">
	function onLoadFunctions(){
	         var treeAction3='${app}/dept/tawSystemDepts.do?method=userFromDaiweiDept&showType=dept';
			 new xbox({
				btnId:'maintenUnitIdVal',
				treeDataUrl:treeAction3,treeRootId:'-1',treeRootText:'${eoms:a2u('代维部门')}',treeChkMode:'single',treeChkType:'dept',
				showChkFldId:'maintenUnitIdVal',saveChkFldId:'tempid'
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
			btnId:'maintenUnitIdVal',dlgId:'hello-dlg',
			treeDataUrl:treeAction,treeRootId:'-1',treeRootText:'${eoms:a2u('代维公司选择')}',treeChkMode:'single',treeChkType:'dept',
			showChkFldId:'maintenUnitIdVal',saveChkFldId:'tempid',viewer:deptViewer
		});
	});
</script>
<!--对表单的自动生成的处�?-->
<html:form action="saveBasemetermgr.do?method=xsave" method="post" styleId="basemetermgrForm"> 
<ul>
  <input name="newSave"  type="hidden" id="newSave" value="newSave"/>
    <!--表示对所有的域有�? -->
	       <html:hidden property="id"/>
	 <table class="formTable middle" cellspacing="3" >	       
	   <tr class="tr_show">   	
		      <td >     
		           <eoms:label styleClass="desc" key="basemetermgrForm.entry"/>

		  	</td>	
		  	<td >     
		           <eoms:label styleClass="desc" key="basemetermgrForm.val"/>

		  	</td>
		  	<td >     
		           <eoms:label styleClass="desc" key="basemetermgrForm.rem"/>

		  	</td>
	  </tr>
		 <tr class="tr_show">   	
		      <td >     
		           <eoms:label styleClass="desc" key="basemetermgrForm.maintenUnitId"/>

		  	</td>		       
		       <td >  
		       <input type="text"  readonly id ="maintenUnitIdVal" name="maintenUnitIdVal" styleClass="text"/>
					<input type="hidden" id="tempid" name="tempid"/>
					<div id="view" class="viewer-box"></div>		        
		    </td>
		    <td>
			        <html:textarea property="maintenUnitIdRem" styleId="maintenUnitIdRem" styleClass="text medium"/>
		    </td>
	</tr>
	    <tr class="tr_show">   
		    <td >     
		           <eoms:label styleClass="desc" key="basemetermgrForm.desktopComputer"/>
		  	</td>
		    <td>
		    		<html:text property="desktopComputerVal" styleId="desktopComputerVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="desktopComputerRem" styleId="desktopComputerRem" styleClass="text medium"/>
		    </td>
	 </tr>
		 <tr class="tr_show">
		  	<td >     
		           <eoms:label styleClass="desc" key="basemetermgrForm.fax"/>
		  	</td>
		    <td>
			        <html:text property="faxVal" styleId="faxVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="faxRem" styleId="faxRem" styleClass="text medium"/>
		    </td>
	 </tr>
	   <tr class="tr_show">
	 	   <td >     
		           <eoms:label styleClass="desc" key="basemetermgrForm.fixedPhone"/>
		  	</td>
		    <td>
			        <html:text property="fixedPhoneVal" styleId="fixedPhoneVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="fixedPhoneRem" styleId="fixedPhoneRem" styleClass="text medium"/>
		    </td>
	 </tr>
	   <tr class="tr_show">
	   		<td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.moveTestPhone"/>
		  	</td>
		    <td>
			        <html:text property="moveTestPhoneVal" styleId="moveTestPhoneVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="moveTestPhoneRem" styleId="moveTestPhoneRem" styleClass="text medium"/>
		    </td>
	 </tr>
	   <tr class="tr_show">
	   		<td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.lineTester"/>
		  	</td>  
		    <td>
			        <html:text property="lineTesterVal" styleId="lineTesterVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="lineTesterRem" styleId="lineTesterRem" styleClass="text medium"/>
		    </td>
	 </tr>
	   <tr class="tr_show">
	   		<td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.terraBlockTester"/>
		  	</td> 
		    <td>
			        <html:text property="terraBlockTesterVal" styleId="terraBlockTesterVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="terraBlockTesterRem" styleId="terraBlockTesterRem" styleClass="text medium"/>
		    </td>
	 </tr>
	   <tr class="tr_show">
	   		<td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.cellTester"/>
		  	</td> 
		    <td>
			        <html:text property="cellTesterVal" styleId="cellTesterVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="cellTesterRem" styleId="cellTesterRem" styleClass="text medium"/>
		    </td>
	 </tr>
	   <tr class="tr_show">
	   		<td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.maintenanceTools"/>
		  	</td> 
		    <td>
			        <html:text property="maintenanceToolsVal" styleId="maintenanceToolsVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="maintenanceToolsRem" styleId="maintenanceToolsRem" styleClass="text medium"/>
		    </td>
	</tr>
	   <tr class="tr_show">
	   		<td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.maintenanceCars"/>
		  	</td> 
		    <td>
			        <html:text property="maintenanceCarsVal" styleId="maintenanceCarsVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="maintenanceCarsRem" styleId="maintenanceCarsRem" styleClass="text medium"/>
		    </td>
		 </tr>
	   <tr class="tr_show">
	   		<td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.multimeter"/>
		  	</td> 
		    <td>
			        <html:text property="multimeterVal" styleId="multimeterVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="multimeterRem" styleId="multimeterRem" styleClass="text medium"/>
		    </td>
		 </tr>
	   <tr class="tr_show">
	   		<td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.ACDCclampMeter"/>
		  	</td> 
		    <td>
			        <html:text property="ACDCclampMeterVal" styleId="ACDCclampMeterVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="ACDCclampMeterRem" styleId="ACDCclampMeterRem" styleClass="text medium"/>
		    </td>
		 </tr>
	   <tr class="tr_show">
	 	   <td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.twoMInstrument"/>
		  	</td> 
		    <td>
			        <html:text property="twoMInstrumentVal" styleId="twoMInstrumentVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="twoMInstrumentRem" styleId="twoMInstrumentRem" styleClass="text medium"/>
		    </td>
	  </tr>
	   <tr class="tr_show">
	 	   <td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.antennaAngleGauge"/>
		  	</td> 
		    <td>
			        <html:text property="antennaAngleGaugeVal" styleId="antennaAngleGaugeVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="antennaAngleGaugeRem" styleId="antennaAngleGaugeRem" styleClass="text medium"/>
		    </td>
	</tr>
	   <tr class="tr_show">
	 	   <td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.telescope"/>
		  	</td>   
		    <td>
			        <html:text property="telescopeVal" styleId="telescopeVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="telescopeRem" styleId="telescopeRem" styleClass="text medium"/>
		    </td>
	</tr>
	   <tr class="tr_show">
	 	   <td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.digitalCamera"/>
		  	</td>   
		    <td>
			        <html:text property="digitalCameraVal" styleId="digitalCameraVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="digitalCameraRem" styleId="digitalCameraRem" styleClass="text medium"/>
		    </td>
	</tr>
	   <tr class="tr_show">
	 	   <td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.gradienter"/>
		  	</td>   
		    <td>
			        <html:text property="gradienterVal" styleId="gradienterVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="gradienterRem" styleId="gradienterRem" styleClass="text medium"/>
		    </td>
	</tr>
	   <tr class="tr_show">
	 	   <td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.GPSLocator"/>
		  	</td>   	    
		    <td>
			        <html:text property="GPSLocatorVal" styleId="GPSLocatorVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="GPSLocatorRem" styleId="GPSLocatorRem" styleClass="text medium"/>
		    </td>
	</tr>
	   <tr class="tr_show">
	 	   <td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.infraredThermoscope"/>
		  	</td>  
		    <td>
			        <html:text property="infraredThermoscopeVal" styleId="infraredThermoscopeVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="infraredThermoscopeRem" styleId="infraredThermoscopeRem" styleClass="text medium"/>
		    </td>
	</tr>
	   <tr class="tr_show">
	 	   <td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.notebookPC"/>
		  	</td>  
		    <td>
			        <html:text property="notebookPCVal" styleId="notebookPCVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="notebookPCRem" styleId="notebookPCRem" styleClass="text medium"/>
		    </td>
	</tr>
	   <tr class="tr_show">
	 	   <td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.mobileGps"/>
		  	</td>  
		    <td>
			        <html:text property="mobileGpsVal" styleId="mobileGpsVal" styleClass="text medium"/>
		    </td>
		    <td>
			        <html:textarea property="mobileGps" styleId="mobileGpsRem" styleClass="text medium"/>
		    </td>
	</tr>
	   <tr class="tr_show">
	 	   <td>     
		           <eoms:label styleClass="desc" key="basemetermgrForm.remark"/>
		  	</td> 	
		  	<td colspan=2>	    
			        <html:textarea property="remark" styleId="remark" styleClass="text medium"/>
		    </td>
   </table>
   <table class="formTable middle"  cellspacing="1">
    <li class="buttonBar bottom">
    <input type="button" value="<fmt:message key='button.save'/>" onclick="javascript:onSubmit()" class="button" />
        <!-- <html:submit styleClass="button" property="method.save" onclick="bCancel=false">
            <fmt:message key="button.save"/>
        </html:submit>
        <!--用自动生成的参数调用Javascript --> 
      <!--   <html:submit styleClass="button" property="method.delete" onclick="bCancel=true; return confirmDelete('Basemetermgr')">
            <fmt:message key="button.delete"/>
        </html:submit>

        <html:cancel styleClass="button" onclick="bCancel=true">
            <fmt:message key="button.cancel"/>
        </html:cancel>--> 
    </li>
       </table>
</ul>
  <!--自动生成的Javascript脚本-->

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>