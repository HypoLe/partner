<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.lineEva.util.LineEvaConstants"/>
<%@page import="com.boco.eoms.partner.lineEva.webapp.form.LineEvaKpiInstanceForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.partner.lineEva.util.LineEvaStaticMethod"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'lineEvaKpiInstanceForm'});
})
</script>

<html:form action="/lineEvaTasks.do?method=saveTaskDetail" styleId="lineEvaKpiInstanceForm" method="post">
<table class="listTable" width="1300" >
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 考核执行列表
		</div>
	</caption>
	<thead>
	<tr>
		<td colspan="${requestScope.maxLevel}" width="600" rowspan="2">
			<center>考核指标</center>
		</td>
		<td width="400" rowspan="2">
			<center>算法描述</center>
		</td>
		<% 
		   List parIdss = (List)request.getAttribute("parIdss");
		   for(int i=0;i<parIdss.size();i++){
			   String parName = LineEvaStaticMethod.orgId2Name(String.valueOf(parIdss.get(i)), "dept");
		%>
			<td width="100" colspan="2">
				<center><%=parName%></center>
			</td>
        <%} %>
	</tr>
	<tr>
		<% 
		   for(int i=0;i<parIdss.size();i++){
		%>
			<td width="50">
				实际得分
			</td>
			<td width="50">
				扣分原因
			</td>
        <%} %>	
	</tr>
	</thead>
	<tbody>
	<input type="hidden" id="taskId" name="taskId" value="${requestScope.taskId}"/>
	<input type="hidden" id="partner" name="partner" value="${requestScope.partner}"/>
	<input type="hidden" name="timeType" value="${requestScope.timeType}"/>
	<input type="hidden" name="time" value="${requestScope.time}"/>
	<input type="hidden" name="city" value="${requestScope.city}"/>
	<input type="hidden" name="map" value="${requestScope.map}"/>
	<input type="hidden" name="parIdss" value="${requestScope.parIdss}"/>
	<input type="hidden" name="parIds" value="${requestScope.parIds}"/>
	<% 
	    Map map = (Map)request.getAttribute("map");
     	List tableList = (List)request.getAttribute("tableList");			 
	    for(int i=0;i<tableList.size();i++){
	%>
		<tr>
			<% 
				List rowList = (List)tableList.get(i);
			    for(int k=0;k<rowList.size();k++){
			    	LineEvaKpiInstanceForm ekif = (LineEvaKpiInstanceForm) rowList.get(k);
			%>			
				<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>" style="vertical-align:middle;text-align:left">
					<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="lineEvaKpiDao" />(<%=ekif.getWeight()%>分)
				</td>
				<%if (LineEvaConstants.NODE_LEAF.equals(ekif.getLeaf())) { %>
					<td>
						<%=ekif.getAlgorithm()%>
					</td>
					<% 
					   for(int j=0;j<parIdss.size();j++){
						   String parIds = String.valueOf(parIdss.get(j));
						   String parName = LineEvaStaticMethod.orgId2Name(parIds, "dept");
						   String realScore = (String)map.get(ekif.getId()+parIds+"RealScore");
						   String reduceReason = (String)map.get(ekif.getId()+parIds+"ReduceReason");
						   realScore = StaticMethod.null2String(realScore);
						   reduceReason = StaticMethod.null2String(reduceReason);
					%>			
						<%if(LineEvaConstants.TASK_PUBLISHED.equals(ekif.getIsPublish())){%>
					<td>
						<input type="text"  id="<%=ekif.getNodeId()+parIds%>_sc"  style="width:100%;" 
						name="<%=ekif.getNodeId()+parIds%>_sc" value="<%=realScore%>" readonly=""
						alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
					</td>
					<td>
						<input type="text"  id="<%=ekif.getNodeId()+parIds%>_rs"  style="width:100%;" 
						name="<%=ekif.getNodeId()+parIds%>_rs" value="<%=reduceReason%>" readonly=""
						alt="maxLength:255"/>
					</td>
					<%}else{%>
					<td>
						<input type="text"  id="<%=ekif.getNodeId()+parIds%>_sc"  style="width:100%;" 
						name="<%=ekif.getNodeId()+parIds%>_sc" value="<%=realScore%>"
						alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
					</td>
					<td>
						<input type="text"  id="<%=ekif.getNodeId()+parIds%>_rs"  style="width:100%;" 
						name="<%=ekif.getNodeId()+parIds%>_rs" value="<%=reduceReason%>"
						alt="maxLength:255"/>
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
</table>
<table>
	<tr>
		<td>	
		<%--	<input type="submit" class="btn" value="保存草稿" onclick="save()" style="${requestScope.isPublishButton}"/> --%>
			&nbsp;
			<input type="submit" class="btn" value="发布" onclick="commit()" style="${requestScope.isPublishButton}"/>	
			&nbsp;
			<input type="submit" class="btn" value="返回" onclick="${requestScope.queryType}Back()" />
		</td>
	</tr>
</table>

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
function  save()
    {
      document.forms[0].action="lineEvaTasks.do?method=saveTaskDetail";
    };
function  commit()
    {
      document.forms[0].action="lineEvaTasks.do?method=commitTaskDetail";
    };
function  runBack()
    {
   	  v.passing="true";
   	  document.getElementById("taskId").value = '' ;
   	  document.getElementById("partner").value = '' ;
      document.forms[0].action="lineEvaTasks.do?method=xquery&searchType=1";
    };
function  queryBack()
    {
      v.passing="true";
      document.getElementById("taskId").value = '' ;
      document.forms[0].action="lineEvaTasks.do?method=xquery&searchType=0";
    };
</script>