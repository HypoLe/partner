<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="/attemperSubs.do?method=save" styleId="tawRmDutyEventForm" method="post"> 

<script language="javascript">
	Ext.onReady(function() {
		v = new eoms.form.Validation({form:'attemperSubForm'});
		var url='${app}/duty/attempers.do?method=deal&id=' + '<bean:write name="ATTEMPER" property="id" scope="request"/>';
		window.location.href=url;
	});
</script>

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>