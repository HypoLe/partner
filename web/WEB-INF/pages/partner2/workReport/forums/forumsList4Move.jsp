<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
	<script type="text/javascript">
Ext.onReady(function(){
var treeAction='${app}/partner2/workReport/forums.do?method=getNodes4query';
	  var config4 = {
		btnId:'forumsName',
		dlgId:'dlg4',
		treeDataUrl:treeAction,
		treeRootId:'-1',
		treeRootText:'移动到',
		treeChkMode:'single',
		treeChkType:'forums',
		showChkFldId:'forumsName',
		saveChkFldId:'toForumsId'
	}
	var t4 = new xbox(config4);
});
</script>
<html:form action="/forums.do" method="post" >
	<input type="hidden" name="forumsId" value="${forumsId }"/>
	
	<html:submit styleClass="button" property="method.move"
				onclick="javascript:var o=$('toForumsId');if(o.value=='')o.value=-1;">移动
	</html:submit>	
	
	<input type="text" id="forumsName" name="forumsName" class="text" readonly="readonly" value="选择您的专题..."/>
	<html:hidden property="toForumsId" styleId="toForumsId" value="-1"/>
			
</html:form>



<%@ include file="/common/footer_eoms.jsp"%>
