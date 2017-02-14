<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
</script>

<script type="text/javascript">
function openImport(){
	var handler = document.getElementById("openQuery");
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}
</script>
<div align="center">
	<b>线路到点率规则设置管理</b>
</div><br><br/>
<logic:present name="pnrArrivedRateList" scope="request">
	<display:table id="pnrArrivedRateList"
					name="pnrArrivedRateList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app }/partner/res/pnrTransLineAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table pnrArrivedRateList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="地市">
						<eoms:id2nameDB id="${pnrArrivedRateList.city}" beanId="tawSystemAreaDao" />
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="区县">
						<eoms:id2nameDB id="${pnrArrivedRateList.country}" beanId="tawSystemAreaDao" />
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="标准到点率">
						${pnrArrivedRateList.arrivedRate }
				</display:column>
	</display:table>
</logic:present>

<br/>
<a href="${app }/partner/res/pnrTransLineAction.do?page=goToAddArrivedRate&method=goToPage">添加到点率</a>

<%@ include file="/common/footer_eoms.jsp"%>
