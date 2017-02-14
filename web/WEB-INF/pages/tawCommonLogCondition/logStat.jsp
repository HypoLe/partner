<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page import="java.util.*,java.lang.*, org.apache.struts.util.*,com.boco.eoms.common.util.StaticMethod,org.joda.time.LocalDate;"%>
<% GregorianCalendar cal_start = new GregorianCalendar();
      cal_start.add(cal_start.DATE,-1);
      String str_start = StaticMethod.Cal2String(cal_start);
      str_start = String.valueOf(StaticMethod.getVector(str_start," ").elementAt(0));
      LocalDate date = new LocalDate();
	  LocalDate date1 = date.minusDays(2);
	  String nowDay = date.toString() ;
	  String minDay = date1.toString();
%>
<style>
#tabs {
	width:90%;
}
#tabs .x-tabs-item-body {
	display:none;
	padding:10px;
}
</style>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker4.7.2/WdatePicker.js"></script>
<script type="text/javascript">
var Tabs = {
    init : function(){
        var tabs = new Ext.TabPanel('tabs');
        tabs.addTab('form', '${eoms:a2u('统计')}');
        tabs.addTab('info', '${eoms:a2u('帮助')}');
        tabs.activate('form');
     }
}
Ext.onReady(Tabs.init, Tabs, true);
</script>
<div id="tabs">
<div id="form" class="tab-content">
<html:form action="/TawCommonLogCondition/logstatdo">
<table border="0" width="95%" cellspacing="1">
       
	
 <tr class="tr_show">
		<td class="clsfth">${eoms:a2u('开始时间')}</td>
		<td colspan=3>
		<!--<eoms:SelectDate name="searchbystarttime" formName="TawCommonLogConditionForm" flag = "-1" value = "<%=str_start%>"/>-->
		<input id="d4311" name="searchbystarttime" class="Wdate text" type="text" readonly="readonly" value="<%=minDay%>"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'%y-%M-%d\'}'})"/>
		<!-- html:text property="searchbystarttime" style="width:100%" title="STARTTIME"/>-->
	   </td>
 </tr>
 <tr class="tr_show">
		<td class="clsfth">${eoms:a2u('结束时间')}</td>
		<td colspan=3>
		<!--<eoms:SelectDate name="searchbyendtime" formName="TawCommonLogConditionForm" flag = "0" value = "<%=str_start%>"/>-->
		<input id="d4312" name="searchbyendtime" class="Wdate text" readonly="readonly" value="<%=nowDay%>" type="text" onfocus="WdatePicker({maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'d4311\')}'})"/>
		<!--html:text property="searchbyendtime" style="width:100%" title="ENDTIME"/-->
	   </td>
 </tr>
 
</table>
<table border="0" width="70%" cellspacing="0">
  <tr>
    <td width="100%" height="32" align="left">
                    <html:submit property="strutsButton" styleClass="btn" >
                     ${eoms:a2u('部门登录统计')}
                    </html:submit>
                    &nbsp;&nbsp;</td>
  </tr>
  </table>
  
</html:form>
</div>
  <div id="info">
  	<dl>
  		<dt>${eoms:a2u('日志管理')}</dt>
        <dd>${eoms:a2u('系统日志管理提供对系统日志的查询和统计功能。')}</dd>
		<dt>${eoms:a2u('日志统计')}</dt>
        <dd>${eoms:a2u('请选择统计时间段。')}</dd>
    </dl>
  </div>
</div>
</body>
</html>
