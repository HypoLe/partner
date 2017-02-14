<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>


<script language='javascript' type='text/javascript'>

var secs =3; //倒计时的秒数
var URL ;
function Load(url){
URL =url;
for(var i=secs;i>=0;i--)
{
window.setTimeout('doUpdate(' + i + ')', (secs-i) * 1000);
}
}
function doUpdate(num)
{
document.getElementById('ShowDiv').innerHTML = '将在'+num+'秒后自动跳转到故障工单列表,若不成功请从新加载页面' ;
if(num == 0) { window.location=URL; }
}
</script>


<h3>
	恭喜您操作成功
</h3>
<div id="ShowDiv"></div>
<script language="javascript">
Load("${app}/faultSheethz/FaultSheetResponse.do?method=responseAdd");
</script>
<%@ include file="/common/footer_eoms.jsp"%>
