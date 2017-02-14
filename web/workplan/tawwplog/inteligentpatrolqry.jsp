<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.Hashtable"%>
<%@ page import ="java.util.Enumeration"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpStaticVariable"%>
 
<script language="javascript">
function SubmitCheck(){
var time1 = document.tawwplogform.startdate.value;
if(time1 == ""){
	alert("日期不能为空！");
	return false;
}
document.tawwplogform.submit();
}
</script>
  
<form name="tawwplogform" method="post" action="patrolerrloglist.do">


<table class="formTable"  id="list-table">
 <caption>
      智能巡检错误日志查询
 </caption>

  
  <tr >
    <td  class="label" width="100">
       日期：
    </td>
    <td  width="400">
        <input type="text" name="startdate"  onclick="popUpCalendar(this, this,null,null,null,false,-1);"  readonly="true" >
    </td>
  </tr>
</table>
  <br>
      <input type="button" onclick="SubmitCheck()" value="查询" name="B1" Class="clsbtn2">
   

</form>

<%@ include file="/common/footer_eoms.jsp"%>