<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<%@page import="com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
  <!-- EXT LIBS verson 3.0 -->
  <script type="text/javascript" src="${app}/scripts/ext3/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext3/ext-all.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext3/source/locale/ext-lang-zh_CN.js"></script>
  <script type="text/javascript" src="${app}/scripts/partner/select.js"></script>
  <link rel="stylesheet" type="text/css" href="${app}/scripts/ext3/resources/css/ext-all.css" />
  <c:if test="${theme ne 'default'}"><link rel="stylesheet" type="text/css" href="${app}/scripts/ext3/resources/css/xtheme-gray.css" /></c:if>
  <link rel="stylesheet" type="text/css" href="${app}/styles/${theme}/ext-adpter.css" />
  
  <script type="text/javascript" src="${app}/scripts/form/Options.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/combobox.js"></script>
  <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/validation.js"></script>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.partner.assess.util.AssStaticMethod"%>
<script type="text/javascript" src="${app}/scripts/partner/select.js"></script>
<%@ include file="/common/body.jsp"%>
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
	var deductStr = "";
	var deductNodesStr = "";
	var deductAreaStr = "";
</script>

<html:form action="/deviceAssExecute.do?method=saveTaskDetail" styleId="assKpiInstanceForm" method="post">
<table id="assTable" class="listTable" width="1300" >
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 后评估执行列表${after}
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
		   Map map = (Map)request.getAttribute("map");
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
	<tr>
			<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
				网络基础信息
			</td>
			<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
				网组设备量
			</td>
			<c:if test="${maxLevel==3}">
				<td>
					用于汇总后考量些关键指标评估(物理网元数)
				</td>	
				<td>
					作为参考指标量，用于故障率等指标项的基数
				</td>
			</c:if>
			<c:if test="${maxLevel==2}">
				<td>
					用于汇总后考量些关键指标评估(物理网元数)--
					作为参考指标量，用于故障率等指标项的基数
				</td>
			</c:if>			
			<%
				for(int j=0;j<partnerList.size();j++){
			%>
				<td colspan="2" >
					<input id="<%=partnerList.get(j)%>_par" name="<%=partnerList.get(j)%>_par" value="<%=StaticMethod.nullObject2String(map.get(partnerList.get(j)+"_par"),"") %>" >
				</td>
			<%
				}
			%>
	</tr>
	<% 
	    
     	List tableList = (List)request.getAttribute("tableList");			 
	    for(int i=0;i<tableList.size();i++){
	%>
		<tr>
			<% 
				List rowList = (List)tableList.get(i);
			    for(int k=0;k<rowList.size();k++){
			    	AssKpiInstanceForm ekif = (AssKpiInstanceForm) rowList.get(k);
			%>	
				<%if("total".equals(ekif.getKpiType())||"text".equals(ekif.getKpiType())){ %>
				<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>"  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="deviceAssKpiDao" />
				</td>
				<%}else {%>		
				<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="deviceAssKpiDao" />
					<%if(ekif.getWeight()!=0){ %>
					(<%=ekif.getWeight()%>分)
					<%}%>
				</td>
				<%} %>
				
				<%if (AssConstants.NODE_LEAF.equals(ekif.getLeaf())) { 
					if("total".equals(ekif.getKpiType())&&!"text".equals(ekif.getKpiType())){
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
						   String remark = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_rm"));
						   String scoreColor = (String)map.get(ekif.getNodeId()+"_"+parIds+"_color");
						   String algorithm = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_rm"));
						   if("0.0".equals(realScore)&&scoreColor==null){
							   scoreColor = "color:red";
						   }
						   realScore = StaticMethod.null2String(realScore);
						   scoreColor = StaticMethod.null2String(scoreColor);
						   reduceReason = StaticMethod.null2String(reduceReason);
						   if("total".equals(ekif.getKpiType())){
						   
					%>	
					
						<input type="hidden"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc" name="<%=ekif.getNodeId()+"_"+parIds%>_sc" value="0" />
					<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					
					</td>
					<td  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
						<span id='<%=ekif.getParentNodeId() %>_<%=parIds %>_total' style= "text-align:center"><span>
						<script type="text/javascript">
							totalStr = totalStr + '<%=ekif.getParentNodeId() %>_<%=parIds %>_total|';
							totalNodesStr = totalNodesStr + '<%=ekif.getNodesForTotal() %>|';
							totalAreaStr = totalAreaStr + '<%=parIds %>|';
						</script>
					</td>
					<%
						}else if("text".equals(ekif.getKpiType())){
					 %>		
					 	<input type="hidden"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc" name="<%=ekif.getNodeId()+"_"+parIds%>_sc" value="0" />
					 	<input type="hidden"  id="<%=ekif.getNodeId()+"_"+parIds%>_rs" name="<%=ekif.getNodeId()+"_"+parIds%>_rs" value="0" />

					<td colspan="3">
						<input type="text"  id="<%=ekif.getNodeId()+"_"+parIds%>_rm"  style="width:100%;" 
						name="<%=ekif.getNodeId()+"_"+parIds%>_rm" value="<%=remark%>" ${readOnly}
						alt="maxLength:255"/>
					</td>
					<%
					}else if("deductScore".equals(ekif.getKpiType())){
							String deductWeight = "0.0";
							if(!"0.0".equals(realScore)){
								deductWeight = "-1";
								scoreColor = "color:red";
							}
					 %>	
					 <td>
						<input type="text"  id="<%=ekif.getNodeId()+"_"+parIds%>_rs"  style="width:100%;" 
						name="<%=ekif.getNodeId()+"_"+parIds%>_rs" value="<%=reduceReason%>"  ${readOnly}
						alt="maxLength:255"/>
						<input type="hidden"   id="<%=ekif.getNodeId()+"_"+parIds%>_deductWeight" value="<%=deductWeight%>"/>
					</td>
					
					<td>
						<input type="button" class="btn"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc_btn"  style="width:100%;<%=scoreColor%>" 
						name="<%=ekif.getNodeId()+"_"+parIds%>_sc_btn" value="<%=realScore%>" 
						Onclick="javascript:getSelectTotal('<%=ekif.getNodeId() %>','<%=ekif.getKpiId() %>','${requestScope.taskId}','<%=parIds %>','${requestScope.time}','${requestScope.areaId}');"/>
						<input type="hidden"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc"  style="width:100%;" 
						name="<%=ekif.getNodeId()+"_"+parIds%>_sc" value="<%=realScore%>" 
						alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
						<span style=display:none id='<%=ekif.getNodeId() %>_<%=parIds %>_deductScore' style= "text-align:center">0<span>
						<br>
						
						<script type="text/javascript">
							deductStr = deductStr + '<%=ekif.getNodeId() %>_<%=parIds %>|';
							deductNodesStr = deductNodesStr + '<%=ekif.getNodesForTotal() %>|';
							deductAreaStr = deductAreaStr + '<%=parIds %>|';
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
						<input type="button" class="btn"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc_btn"  style="width:100%;<%=scoreColor%>" 
						name="<%=ekif.getNodeId()+"_"+parIds%>_sc_btn" value="<%=realScore%>" 
						Onclick="javascript:getSelectTotal('<%=ekif.getNodeId() %>','<%=ekif.getKpiId() %>','${requestScope.taskId}','<%=parIds %>','${requestScope.time}','${requestScope.areaId}');" title="<%=ekif.getAlgorithm()%>"/>
						<input type="hidden"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc"  style="width:100%;" 
						name="<%=ekif.getNodeId()+"_"+parIds%>_sc" value="<%=realScore%>" 
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
function  getSelectTotal(nodeId,kpiId,taskId,partnerId,time,areaId)
    {
      var parNum = document.getElementById(partnerId+"_par").value;
      showSelect('tran',nodeId,kpiId,taskId,partnerId,time,areaId,parNum);
      
    };
function  save()
    {
      document.forms[0].action="deviceAssExecute.do?method=saveTaskDetail";
    };
function  commit()
    {
      document.forms[0].action="deviceAssExecute.do?method=commitTaskDetail";
    };
function  runBack()
    {
   	  v.passing="true";
   	  document.getElementById("taskId").value = '' ;
   	  document.getElementById("partner").value = '' ;
      document.forms[0].action="tranAssExecute.do?method=xquery&searchType=1";
    };
function  queryBack()
    {
      v.passing="true";
      document.getElementById("taskId").value = '' ;
      document.forms[0].action="tranAssExecute.do?method=xquery&searchType=0";
    };
Ext.onReady(function(){
	getTotal();
	})
</script>
</c:if>