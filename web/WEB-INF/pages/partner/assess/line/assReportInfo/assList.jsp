<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="com.boco.eoms.partner.assess.AssTree.model.AssTree"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.DecimalFormat"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<c:if test="${noPrice=='noPrice'}">
	<table class="formTable">
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#EDF5FD;border: 1px solid #98C0F4;" >任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 未全部执行</td>
			</tr>
	</table>
</c:if>
<c:if test="${noPrice!='noPrice'}">
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
		<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
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
		<td rowspan="2" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			序号
		</td>
		<td rowspan="2" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			后评估单位
		</td>
		<td rowspan="2" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			当季度应付代维费用（元）
		</td>
		<td colspan="<%=assTreeList.size()+1 %>" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			${requestScope.timeTypeStr}扣分/扣款情况（元）
		</td>
		<td rowspan="2" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			当季度实付代维费用（元）
		</td>						
	</tr>
	<tr>
		<%
			for(int i = 0 ;i<assTreeList.size();i++ ){
				AssTree assTree = (AssTree)assTreeList.get(i);
		%>
			<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
				<%=assTree.getNodeName() %>
			</td>
		<%
			}
		%>
			<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
				合计扣款
			</td>
	</tr>
	<%
		for( int i = 0 ;i<areaList.size();i++ ){
			String areaId = String.valueOf(areaList.get(i));
	%>
		<tr>
			<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
				<%=i+1 %>
			</td>
			<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
				<a href="${app}/partner/assess/lineAssReportInfo.do?method=taskDetailListForQuarter&taskId=${taskId}&areaId=<%=areaId%>&year=${year}&quarter=${quarter}&timeType=${timeType}&partnerId=${partnerId }&asseseTime=${asseseTime }&userId=${userId }" target='_blank'>
				<eoms:id2nameDB id="<%=areaId %>" beanId="tawSystemAreaDao" /></a>
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
		<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<%=areaList.size()+1%>
		</td>
		<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
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
		<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<%=areaList.size()+2%>
		</td>
		<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
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
<table id="submit-btn" align="left">
	<tr>
		<td valign="top">
			<c:if test="${assId==null||assId==''}">
				<input type="submit" class="btn" value="保存" />
			</c:if>
			<c:if test="${assId!=null&&assId!=''}">
				<a href="${app}/partner/assess/lineAssReportInfo.do?method=assListPrint&taskId=${taskId}&areaId=${areaId}&year=${year}&quarter=${quarter}&timeType=${timeType}&partnerId=${partnerId }&asseseTime=${asseseTime }&userId=${userId }" target='_blank'>
			     	  打印报表</a>		
			</c:if>			
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
</c:if>