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
	tabs.addTab('sheetform', "<bean:message bundle="pnrcomplaint" key="pnrcomplaint.header"/>");
	tabs.activate('sheetform');     
    var el = Ext.get("idSpecial");
	var mgr = el.getUpdateManager();
	mgr.loadScripts = true;
	var params = 'fromEoms=${param.fromEoms}&sheetId=${param.sheetId}&parentCorrelation=${param.parentCorrelation}'
		+ '&title=${param.title}&operateUserId=${param.operateUserId}&acceptLimit=${param.acceptLimit}'
		+'&completeLimit=${param.completeLimit}&faultGenerantTime=${param.faultGenerantTime}'
		+'&complaintTime=${param.complaintTime}&netSortType=${param.netSortType}&customName=${param.customName}'
		+'&customNum=${param.customNum}&complaintDesc=${param.complaintDesc}&toDeptId=${param.toDeptId}&complaintNum=${param.complaintNum}&faultSite=${param.faultSite}';
	mgr.update("${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=showNewInputSheetPage&processname=PnrFaultDeal",params);
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
	<html:form action="/pnrcomplaint.do?method=performAdd" styleId="theform" >
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