<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="com.boco.eoms.partner.assess.AssTree.model.AssTree"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.DecimalFormat"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<OBJECT   id="WebBrowser"   classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"   height="0"   width="0"   VIEWASTEXT> 
</OBJECT> 
<input   type="button"   value="打印"       onclick= "document.all.WebBrowser.ExecWB(6,1) "   class= "NOPRINT "> 
<input   type="button"   value="直接打印"   onclick= "document.all.WebBrowser.ExecWB(6,6) "   class= "NOPRINT "> 
<input   type="button"   value="页面设置"   onclick= "document.all.WebBrowser.ExecWB(8,1) "   class= "NOPRINT "> 
<input   type="button"   value="打印预览"   onclick= "document.all.WebBrowser.ExecWB(7,1) "   class= "NOPRINT ">
<%
	DecimalFormat df = new DecimalFormat("0.00");
	List assTreeList =  (List)request.getAttribute("assTreeList");
	List areaList =  (List)request.getAttribute("areaList");
	Map map =  (Map)request.getAttribute("map");
	String nodeId = "";
%>
<html:form action="/lineAssReportInfo.do?method=saveConfirm" method="post" >
<table class="listTable">
	<caption>
		<div class="header center">
			${requestScope.timeStr}全省光缆线路代维后评估确认费用计算表
		</div>
	</caption>
	<tbody>
	<tr>
		<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			<center>
			后评估时间：
			${asseseTime}
			&nbsp;&nbsp;&nbsp;&nbsp;
			制表人：
			<eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao" />
			</center>
		</td>						
	</tr>
	</tbody>
</table>	
<table class="listTable">
	<tr>
		<td rowspan="2" style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			序号
		</td>
		<td rowspan="2" style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			后评估单位
		</td>
		<td rowspan="2" style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			当季度应付代维费用（元）
		</td>
		<td colspan="<%=assTreeList.size()+1 %>" style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			${requestScope.timeTypeStr}扣分/扣款情况（元）
		</td>
		<td rowspan="2" style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			当季度实付代维费用（元）
		</td>						
	</tr>
	<tr>
		<%
			for(int i = 0 ;i<assTreeList.size();i++ ){
				AssTree assTree = (AssTree)assTreeList.get(i);
		%>
			<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
				<%=assTree.getNodeName() %>
			</td>
		<%
			}
		%>
			<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
				合计扣款
			</td>
	</tr>
	<%
		for( int i = 0 ;i<areaList.size();i++ ){
			String areaId = String.valueOf(areaList.get(i));
	%>
		<tr>
			<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
				<%=i+1 %>
			</td>
			<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
				<eoms:id2nameDB id="<%=areaId %>" beanId="tawSystemAreaDao" />
			</td>
			<td>
				<%=df.format(Double.parseDouble(String.valueOf(map.get(areaId+"_quarterMoney")))) %>
			</td>
			<%
			for(int k = 0 ;k<assTreeList.size();k++ ){
				AssTree assTree = (AssTree)assTreeList.get(k);
				nodeId = assTree.getNodeId();
			%>
			<td>
				<%=df.format(Double.parseDouble(String.valueOf(map.get(nodeId+"_"+areaId+"_total"))))  %>
			</td>			
			<%
			}	
			%>
			<td>
				<%=df.format(Double.parseDouble(String.valueOf(map.get(areaId+"_totalQuarter"))))  %>
			</td>
			<td>
				<%=df.format(Double.parseDouble(String.valueOf(map.get(areaId+"_realMoney"))))  %>
			</td>			
		</tr>
	<% 		
		}
	%>
	<tr>
		<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			<%=areaList.size()+1%>
		</td>
		<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			省公司
		</td>
		<td>
		</td>	
		<%
			for(int k = 0 ;k<assTreeList.size();k++ ){
		%>
		<td>
		</td>			
		<%				
			}
		%>
		<td>
			<c:if test="${assId==null||assId==''}">
				<input type="text" id="province" name="province" style="width:100%;"  value = "${provinceMoney}"  onblur="total(0);">
			</c:if>
			<c:if test="${assId!=null&&assId!=''}">
				<input type="hidden"  id="province" name="province" value="${provinceMoney}" />
				${provinceMoney}
			</c:if>
		</td>
		<td>
		</td>								
	</tr>
	<tr>
		<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			<%=areaList.size()+2%>
		</td>
		<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			合计
		</td>		
		<td>
			<%=df.format(Double.parseDouble(String.valueOf(map.get("quarterMoney"))))  %>
		</td>
		<%
			for(int k = 0 ;k<assTreeList.size();k++ ){
				AssTree assTree = (AssTree)assTreeList.get(k);
				nodeId = assTree.getNodeId();
		%>
		<td>
			<%=df.format(Double.parseDouble(String.valueOf(map.get(nodeId+"_totalKpi"))))  %>
		</td>			
		<%				
			}
		%>
		<td>
			<input type="hidden"  id="deduct" name="deduct" value="<%=df.format(Double.parseDouble(String.valueOf(map.get("totalKpi"))))  %>" />
			<span id ="deductShow"><%=df.format(Double.parseDouble(String.valueOf(map.get("totalKpi"))))  %></span>
		</td>
		<td>
			<input type="hidden"  id="real" name="real" value="<%=df.format(Double.parseDouble(String.valueOf(map.get("realMoney"))))  %>" />
			<span id ="realShow"><%=df.format(Double.parseDouble(String.valueOf(map.get("realMoney"))))  %></span>		
			
		</td>							
	</tr>
	<input type="hidden"  id="taskId" name="taskId" value="${taskId}" />
	<input type="hidden"  id="time" name="time" value="${time}" />
	<input type="hidden"  id="userId" name="userId" value="${userId}" />
</table>
<table  width="1024" >
	<tr>
		<td style="font-size:25px;vertical-align:middle;text-align:left" width="50%" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;网络部负责人签字：
		</td>
		<td style="font-size:25px;vertical-align:middle;text-align:left" width="50%" >
			&nbsp;&nbsp;&nbsp;&nbsp;代维单位负责人签字：
		</td>
	</tr>
	<tr>	
		<td style="font-size:25px;vertical-align:middle;text-align:left" width="50%" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（盖&nbsp;   章）
		</td>
		<td style="font-size:25px;vertical-align:middle;text-align:left" width="50%" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（盖   章）
		</td>	
	</tr>
</table>
</html:form>
<script type="text/javascript">
function  total(type){
	var provinceValue = document.getElementById("province").value;
	var deductValue = document.getElementById("deduct").value;
	var realValue = document.getElementById("real").value;
	if(type==0){
	 	var userreg=/^[0-9]+([.]{1}[0-9]+)?$/; 
	   if(!userreg.test(provinceValue)){ 
	     alert("请输入合法数字,非负整数或小数！"); 
	     document.getElementById("province").value ='0';
	     document.getElementById("province").focus; 
	     return false;
	   }  
	}
	if(provinceValue==''){
		provinceValue=0;
	}
	var dp = parseFloat(deductValue)+parseFloat(provinceValue);	   
	var pp = parseFloat(realValue)-parseFloat(provinceValue);
	
	document.getElementById("deductShow").innerHTML = Math.round(dp*100)/100;
	document.getElementById("realShow").innerHTML = Math.round(pp*100)/100;	   
};	
total(1);
</script>
