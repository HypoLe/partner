<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<%@page import="com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.partner.assess.util.AssStaticMethod"%>
<c:if test="${execute=='execute'}">
	<table class="formTable">
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#EDF5FD;border: 1px solid #98C0F4;" >任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 已执行</td>
			</tr>
	</table>
</c:if>
<c:if test="${execute!='execute'}">
<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'assKpiInstanceForm'});
})
	var totalStr = "";
	var totalNodesStr = "";
	var totalAreaStr = "";
</script>

<html:form action="/changeAssExecute.do?method=saveTaskDetail" styleId="assKpiInstanceForm" method="post">
<table class="listTable" width="1300" >
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 后评估执行列表
		</div>
	</caption>
	<thead>
	<tr>
		<td colspan="${requestScope.maxLevel}" width="30%" rowspan="2" style="background-color:#EDF5FD;">
			<center>评估指标</center>
		</td>
		<td width="400" rowspan="2" style="background-color:#EDF5FD;">
			<center>评分规则</center>
		</td>
		<% 
		   List partnerList = (List)request.getAttribute("partnerList");
		   for(int i=0;i<partnerList.size();i++){
			   String parName = AssStaticMethod.orgId2Name(String.valueOf(partnerList.get(i)), "dept");
		%>
			<td colspan="2" style="background-color:#EDF5FD;">
				<center><eoms:id2nameDB id="<%=String.valueOf(partnerList.get(i)) %>" beanId="partnerDeptDao" /></center>
				<input type='hidden' id='partnerId' name='partnerId' value='<%=String.valueOf(partnerList.get(i)) %>'/>
			</td>
        <%} %> 

		 
	</tr>
	
	<tr>
		<% 
		   for(int i=0;i<partnerList.size();i++){
		%>
		
			<td  style="background-color:#EDF5FD;">
				评分原因
			</td>
			<td  style="background-color:#EDF5FD;">
				评估得分
			</td>
        <%} %>	
	</tr>
	</thead>
	<tbody>
	<input type="hidden" id="taskId" name="taskId" value="${requestScope.taskId}"/>
	<input type="hidden" name="timeType" value="${requestScope.timeType}"/>
	<input type="hidden" name="time" value="${requestScope.time}"/>
	<input type="hidden" name="areaId" value="${requestScope.areaId}"/>
	<% 
	    Map map = (Map)request.getAttribute("map");
     	List tableList = (List)request.getAttribute("tableList");			 
	    for(int i=0;i<tableList.size();i++){
	%>
		<tr>
			<% 
				List rowList = (List)tableList.get(i);
			    for(int k=0;k<rowList.size();k++){
			    	AssKpiInstanceForm ekif = (AssKpiInstanceForm) rowList.get(k);
			%>	
				<%if("total".equals(ekif.getKpiType())){ %>
				<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>"  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="changeAssKpiDao" />
				</td>
				<%}else {%>		
				<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>" style="vertical-align:middle;text-align:left">
					<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="changeAssKpiDao" />
					<%if(ekif.getWeight()!=0){ %>
					(<%=ekif.getWeight()%>分)
					<%}%>
				</td>
				<%} %>
				
				<%if (AssConstants.NODE_LEAF.equals(ekif.getLeaf())) { 
					if("total".equals(ekif.getKpiType())){
				%>
					<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					</td>
					<%
					}else{
					 %>
					<td>
						<%=ekif.getAlgorithm()%>
					</td>
					<% 
					}
					   for(int j=0;j<partnerList.size();j++){
						   String parIds = String.valueOf(partnerList.get(j));
						   String parName = AssStaticMethod.orgId2Name(parIds, "dept");
						   String realScore = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_sc"),"0.0");
						   String reduceReason = (String)map.get(ekif.getNodeId()+"_"+parIds+"_rs");
						   realScore = StaticMethod.null2String(realScore);
						   reduceReason = StaticMethod.null2String(reduceReason);
						   if("total".equals(ekif.getKpiType())){
						   
					%>	
					
						<input type="hidden"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc" name="<%=ekif.getNodeId()+"_"+parIds%>_sc" value="0" />
					<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					
					</td>
					<td  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
						<span id='<%=ekif.getParentNodeId() %>_<%=parIds %>_total' style= "text-align:center"><span>
						<script type="text/javascript">
							totalStr = totalStr + '<%=ekif.getParentNodeId() %>_<%=parIds %>|';
							totalNodesStr = totalNodesStr + '<%=ekif.getNodesForTotal() %>|';
							totalAreaStr = totalAreaStr + '<%=parIds %>|';
						</script>
					</td>
					<%
						}else{
					 %>		

					<td>
						<input type="text"  id="<%=ekif.getNodeId()+"_"+parIds%>_rs"  style="width:100%;" 
						name="<%=ekif.getNodeId()+"_"+parIds%>_rs" value="<%=reduceReason%>"  ${readOnly}
						alt="maxLength:255"/>
					</td>
					
					<td>
						<input type="text"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc"  style="width:100%;" 
						name="<%=ekif.getNodeId()+"_"+parIds%>_sc" value="<%=realScore%>" onblur="getTotal();"  ${readOnly}
						alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
					</td>
					<%}
					}%>
				<%} %>
			<% 
			    }
			%>	
		</tr>
			<% 
			    }
			%>		
	</tbody>
	<input type='hidden' id='treeNodeId' name='treeNodeId' value='${treeNodeId}'/>
</table>
<table>
	<tr>
		<td>	
			<input type="submit" class="btn" value="保存草稿" onclick="save()" style="${requestScope.isPublishButton}"/> 
			&nbsp;
			<input type="submit" class="btn" value="发布" onclick="commit()" style="${requestScope.isPublishButton}"/>	
			<!-- 
			&nbsp;
			<input type="submit" class="btn" value="返回" onclick="${requestScope.queryType}Back()" />
			 -->
		</td>
	</tr>
</table>

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
function  save()
    {
      document.forms[0].action="changeAssExecute.do?method=saveTaskDetail";
    };
function  commit()
    {
      document.forms[0].action="changeAssExecute.do?method=commitTaskDetail";
    };
function  runBack()
    {
   	  v.passing="true";
   	  document.getElementById("taskId").value = '' ;
   	  document.getElementById("partner").value = '' ;
      document.forms[0].action="changeAssExecute.do?method=xquery&searchType=1";
    };
function  queryBack()
    {
      v.passing="true";
      document.getElementById("taskId").value = '' ;
      document.forms[0].action="changeAssExecute.do?method=xquery&searchType=0";
    };
function  getTotal()
    {
     var total = totalStr.split("|");
     var totalNodes = totalNodesStr.split("|");
     var totalArea = totalAreaStr.split("|");
     var sum = 0;
     for(i=0;i<total.length-1;i++){
     	var totalNodeForSum = totalNodes[i].split(",");
     	for(j=0;j<totalNodeForSum.length;j++){
     		var scName = totalNodeForSum[j] + '_' + totalArea[i] + '_sc';
     		var scValue = document.getElementById(scName).value;
     		if(document.getElementById(scName).value!=null&&scValue!=''){
     			sum = sum +parseFloat(scValue);
     		}
     	}
     	document.getElementById(total[i]+'_total').innerHTML=sum;
     	sum = 0;
     }
    };

Ext.onReady(function(){
	getTotal();
	})
</script>
</c:if>