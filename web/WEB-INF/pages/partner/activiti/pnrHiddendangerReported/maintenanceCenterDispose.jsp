<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>

<script type="text/javascript">
var roleTree;
var v;
  Ext.onReady(function()
  {
   v = new eoms.form.Validation({form:'theform'});
  }
   );


  function changeType1()
  {
		
  }
  function selectRes(){
		//var url = '${app}/activiti/pnrHiddendangerReported/pnrHiddendangerReported.do?method=openRejectView';
		var url = '${app}/activiti/pnrHiddendangerReported/pnrHiddendangerReported.do?method=showCommonRejectPage&sign=under';
		//var _sHeight = 180;
		var _sHeight = 200;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
  
  function refresh(){//此刷新函数被弹出的子模态窗口调用。
  		var condition=document.getElementById("condition").value;
	    window.location.href = "${app}/activiti/pnrHiddendangerReported/pnrHiddendangerReported.do?method=listBacklog"+condition;
  }
</script>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrHiddendangerReported/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrHiddendangerReported/showAapprove_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrHiddendangerReported/showAapproveBack_basis.jsp"%>


<div style="height:10"></div>

<div>
	<h6><font color="red">该环节请在手机端操作!</font></h6><br />
</div>

<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 approveBackSwitcher = new detailSwitcher();
  approveBackSwitcher.init({
	container:'approveBackHistory',
  	handleEl:'div.history-item-title-back'
  });
  
  
</script>  
<script type="text/javascript">
 approveSwitcher= new detailSwitcher();
 	 approveSwitcher.init({
	container:'approveHistory',
  	handleEl:'div.history-item-title'
  });
 </script>  