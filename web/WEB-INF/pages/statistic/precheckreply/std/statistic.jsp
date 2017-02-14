<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@page import="com.boco.eoms.commons.statistic.base.config.excel.* ,java.util.*"%>
<%
	Excel excelConfig =(Excel) request.getAttribute("excelConfig");
	if (excelConfig == null) throw new Exception("读取配置统计文件失败!");
	
	String excelConfigURL = String.valueOf(request.getAttribute("excelConfigURL"));
	String findListForward = String.valueOf(request.getAttribute("findListForward"));
	Calendar cld = Calendar.getInstance();
	int year = cld.get(Calendar.YEAR);
	int mondth = cld.get(Calendar.MONTH) + 1;
%>
<script type="text/javascript">
var	userTreeAction='${app}/xtree.do?method=dept';

var v;
Ext.onReady(function(){
	displayTR(document.all.type);
	v = new eoms.form.Validation({form:"theform"});
})

function displayTR(sel){

	if(sel.value=="year")
	{
		eoms.form.enableArea("td2");
		eoms.form.disableArea("td1",true);
		eoms.form.disableArea("td3",true);	
		document.getElementById("stype").value="年";
	}
	if(sel.value=="month")
	{	
		eoms.form.enableArea("td1");
		eoms.form.disableArea("td2",true);
		eoms.form.disableArea("td3",true);
		document.getElementById("stype").value="月";
	}
	if(sel.value=="season")
	{
		eoms.form.enableArea("td3");
		eoms.form.disableArea("td1",true);
		eoms.form.disableArea("td2",true);
		document.getElementById("stype").value="季";
	}
	return;
}
	
	function validateCheck(data)
	{
		alert(data.getElementById("beginTime").value);
		alert(data.getElementById("endTime").value);
	}
	
	function setDeptType(sel){
		if(sel.value=="netsys"){//网管支撑
		
		   eoms.$("reportIndex").value=1;
			 eoms.form.enableArea("selnetsys");
			 eoms.form.disableArea("selrevdept",true);
			
		}  else {//申请单位
			eoms.$("reportIndex").value=0;
			eoms.form.enableArea("selrevdept");
		  eoms.form.disableArea("selnetsys",true);
		}
	}
 
</script>
	
<html:form action="/stat.do?method=performStatistic" styleId="theform">


 <table class="formTable" >
   <caption>输入条件</caption>   
					<input type="hidden" name="excelConfigURL" value="<%=excelConfigURL %>">  
					<input type="hidden" name="findListForward" value="<%=findListForward %>"> 
					<input type="hidden" id="reportIndex" name ="reportIndex"  value="0">
					<input type="hidden" id="stype" name ="stype" >
					
            <tr>
              <td noWrap class="label">统计类型:
              <select name="type" onchange="displayTR(this)">
              	<option value="month">月</option>
              	<option value="season">季</option>
              	<option value="year">年</option>
              </select>
              </td>
              
              <td id="td1" >
              	按月统计
             	 <select name="beginyear">
	              	<%
	              		for(int i=2014; i<= year+1;i++)
	              		{
	              			String select = "";
	              			if(year == i)
	              			{
	              				select = "Selected";
	              			}
	              	 %>
	              		<option value="<%=i%>" <%=select %>><%= i%></option>
	              	<%}%>
	              </select>
	              年
	              
	              <select name="beginmonth">
	              	<%
	              		for(int i=1; i<=12;i++)
	              		{
	              		
	              			String value = String.valueOf(i);
		              		if(i<10)
		              		{
		              			value = "0" + String.valueOf(i);
		              		}
		              		
		              		String select = "";
	              			if(mondth == i)
	              			{
	              				select = "Selected";
	              			}
	              	 %>
	              		<option value="<%=value%>" <%=select %>><%= value%></option>
	              	<%}%>
	              </select>
	              月
              </td>
              
              <td id="td2" style="display:none;">
              	<select name="beginyear">
	              	<%
	              		for(int i=2014; i<= year+1;i++)
	              		{
	              			String select = "";
	              			if(year == i)
	              			{
	              				select = "Selected";
	              			}
	              	 %>
	              		<option value="<%=i%>" <%=select %>><%= i%></option>
	              	<%}%>
	              </select>
	              年
	          </td>
                           
              <td width="80%" id="td3" style="display:none;">
              	按季度统计
              	<select name="beginyear">
	              	<%
	              		for(int i=2014; i<= year+1;i++)
	              		{
	              			String select = "";
	              			if(year == i)
	              			{
	              				select = "Selected";
	              			}
	              	 %>
	              		<option value="<%=i%>" <%=select %>><%= i%></option>
	              	<%}%>
	              </select>
	            年
             	<select name="seasonselect">
              		<option value="season_one">第一季度</option>
              		<option value="season_two">第二季度</option>
              		<option value="season_three">第三季度</option>
              		<option value="season_four">第四季度</option>
              	</select>
              </td>
            </tr>
			<tr>
				<td><input type="hidden" id="reportIndex" name ="reportIndex"  value="0"></td>
			</tr>
          </table>

  <br/>	
  <!-- buttons -->

     <html:submit styleClass="btn" property="method.save" styleId="method.save">
				<bean:message bundle="statistic" key="button.done"/>
     </html:submit>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
