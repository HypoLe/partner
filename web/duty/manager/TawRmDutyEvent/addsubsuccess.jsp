<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="/dutyevent.do?method=edit" styleId="tawRmDutyEventSubForm" method="post"> 

<script language="javascript">
	Ext.onReady(function() {
		v = new eoms.form.Validation({form:'tawRmDutyEventSubForm'});
		var url='${app}/duty/dutyevent.do?method=edit&id=${tawRmDutyEventSubForm.eventid}';
		window.location.href=url;
	});
</script>

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>