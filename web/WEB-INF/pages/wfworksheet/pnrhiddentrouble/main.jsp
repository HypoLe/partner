<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
var roleTree;
	var v;
	function initPage(){
		 v = new eoms.form.Validation({form:'theform'});
		 $('btns').show();   
	}
     
   Ext.onReady(function(){
    var tabs = new Ext.TabPanel('main');
	tabs.addTab('sheetform', "<bean:message bundle="pnrhiddentrouble" key="pnrhiddentrouble.header"/>");
	tabs.activate('sheetform');     
    var el = Ext.get("idSpecial");
	var mgr = el.getUpdateManager();
	mgr.loadScripts = true;
	mgr.update("${app}/sheet/pnrhiddentrouble/pnrhiddentrouble.do?method=showNewInputSheetPage&processname=PartnerHiddenTroubleProcess");
	mgr.on("update", initPage);
   });
   
   function changeType1(){
		//$('phaseId').value = "DealTask";
   		//$('operateType').value = "0";
   }
   
   function changeType2(){
   	$('phaseId').value = 'DraftTask';
   	$('operateType').value = "22";
   }
</script>

<div id="sheetform" class="tabContent">
	<html:form action="/pnrhiddentrouble.do?method=performAdd" styleId="theform" >
		<div ID="idSpecial"></div>
	    <p/>	
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:none">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
			 <bean:message bundle="sheet" key="button.send"/>
			</html:submit>
			
			<html:submit styleClass="btn" property="method.saveDraft" onclick="v.passing=true;javascript:changeType2()" styleId="method.saveDraft">
			<bean:message bundle="sheet" key="button.saveAsDraft" />
			</html:submit>
		</div>
	</html:form>
</div>

<%@ include file="/common/footer_eoms.jsp"%>