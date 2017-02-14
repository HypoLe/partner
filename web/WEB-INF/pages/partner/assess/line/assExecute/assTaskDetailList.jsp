<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<%@page import="com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.partner.assess.util.AssStaticMethod"%>
<c:if test="${noPrice=='noPrice'}">
	<table class="formTable">
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#EDF5FD;border: 1px solid #98C0F4;" >${year}未配置该市线路维护费用，请配置费用后在执行此操作</td>
			</tr>
	</table>
</c:if>
<c:if test="${execute=='execute'}">
	<table class="formTable">
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#EDF5FD;border: 1px solid #98C0F4;" >任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 已执行</td>
			</tr>
	</table>
</c:if>
<c:if test="${noPrice!='noPrice'&&execute!='execute'}">
<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'assKpiInstanceForm'});
})
	var totalStr = "";
	var totalNodesStr = "";
	var totalPartnerStr = "";
	var partners = "";
	var allTotalnodes = "";
</script>

<html:form action="/lineAssExecute.do?method=saveTaskDetail" styleId="assKpiInstanceForm" method="post">
<table class="listTable" width="1300" >
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 后评估执行列表${after}
		</div>
	</caption>
	<thead>
	<tr>
		<td colspan="${requestScope.maxLevel}" width="30%" rowspan="2" style="background-color:#EDF5FD;">
			<center>后评估指标</center>
		</td>
		<% 
		   List partnerList = (List)request.getAttribute("partnerList");
		   for(int i=0;i<partnerList.size();i++){
			   String partnerId = String.valueOf(partnerList.get(i));
		%>
			<script type="text/javascript">
					if(partners!=''){
							partners = partners + '|<%=partnerId %>';
						}else{
							partners = partners + '<%=partnerId %>';
						}
			</script>
			<td colspan="3" style="background-color:#EDF5FD;">
				<center><eoms:id2nameDB id="<%=partnerId %>" beanId="partnerDeptDao" /></center>
				<input type='hidden' id='partnerId' name='partnerId' value='<%=partnerId %>'/>
			</td>
        <%} %>
        <!-- 
		<td width="400" rowspan="2">
			<center>算法描述</center>
		</td>
		 -->
	</tr>
	
	<tr>
		<% 
		   for(int i=0;i<partnerList.size();i++){
		%>
		
			<td  style="background-color:#EDF5FD;">
				扣款/扣分原因
			</td>
			<td  style="background-color:#EDF5FD;">
				扣分情况
			</td>
			<td  style="background-color:#EDF5FD;">
				扣款情况（元）
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
				<%if("total".equals(ekif.getKpiType())||"text".equals(ekif.getKpiType())){ %>
				<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>"  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="lineAssKpiDao" />
				</td>
				<%}else {%>		
				<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>" style="vertical-align:middle;text-align:left">
					<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="lineAssKpiDao" />
					<%if(ekif.getWeight()!=0){ %>
					(<%=ekif.getWeight()%>分)
					<%}%>
				</td>
				<%} %>

				<%if (AssConstants.NODE_LEAF.equals(ekif.getLeaf())) { %>
				<%
					if(!"total".equals(ekif.getKpiType())&&!"text".equals(ekif.getKpiType())){
				%>
					<script type="text/javascript">
					if(allTotalnodes!=''){
							allTotalnodes = allTotalnodes+',<%=ekif.getNodeId()%>';
						}else{
							allTotalnodes = <%=ekif.getNodeId()%>;
						}
					</script>
				<%
					}
				%>
					<% 
					   for(int j=0;j<partnerList.size();j++){
						   String parIds = String.valueOf(partnerList.get(j));
						   String parName = AssStaticMethod.orgId2Name(parIds, "dept");
						   String realScore = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_sc"),"0.0");
						   String reduceReason = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_rs"));
						   String remark = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_rm"));
						   String money = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_money"),"0.0");
						   %>
						   <input type="hidden"  id="<%=ekif.getNodeId()+"_"+parIds%>_kpiType" name="<%=ekif.getNodeId()+"_"+parIds%>_kpiType" value="<%=ekif.getKpiType() %>" />
						   <%
						   if("total".equals(ekif.getKpiType())){
						   
					%>	
						<input type="hidden"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc" name="<%=ekif.getNodeId()+"_"+parIds%>_sc" value="0" />
					<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
						
						<span id='<%=ekif.getNodeId() %>_<%=parIds %>_pay' style='text-align:center;display:none'>0<span>
					</td>
					<td  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
						<span id='<%=ekif.getParentNodeId() %>_<%=parIds %>_total' style= "text-align:center"><span>
						<script type="text/javascript">
							totalStr = totalStr + '<%=ekif.getParentNodeId() %>_<%=parIds %>|';
							totalNodesStr = totalNodesStr + '<%=ekif.getNodesForTotal() %>|';
							totalPartnerStr = totalPartnerStr + '<%=parIds %>|';
						</script>
					</td>
					<td  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
						<span id='<%=ekif.getParentNodeId() %>_<%=parIds %>_pay' style='text-align:center;'>0<span>
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
					}else{
					 %>
					<td>
						<input type="text"  id="<%=ekif.getNodeId()+"_"+parIds%>_rs"  style="width:100%;" 
						name="<%=ekif.getNodeId()+"_"+parIds%>_rs" value="<%=reduceReason%>" ${readOnly}
						alt="maxLength:255"/>
					</td>
					<%
						if("money".equals(ekif.getKpiType())){
					 %>		
					<td style="vertical-align:middle;text-align:center;">
					<input type="hidden"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc" name="<%=ekif.getNodeId()+"_"+parIds%>_sc" value="0" />
						/
					</td>
					<td  style="vertical-align:middle;text-align:center;">
					<input type="text"  id="<%=ekif.getNodeId() %>_<%=parIds %>_pay"  style="width:100%;" 
						name="<%=ekif.getNodeId() %>_<%=parIds %>_pay" value="<%=money%>" onblur="getPayTotal();"  ${readOnly}
						alt="allowBlank:false,vtext:'请输入金额',maxLength:32"/>
					</td>
					<%
					}else{
					 %>
					 <td>
						<input type="text"  id="<%=ekif.getNodeId()+"_"+parIds%>_sc"  style="width:100%;" 
						name="<%=ekif.getNodeId()+"_"+parIds%>_sc" value="<%=realScore%>" onblur="getTotal();" ${readOnly}
						alt="allowBlank:false,vtext:'请输入分数',maxLength:32"/>
					</td>
					<td  style="vertical-align:middle;text-align:center;">
					<input type="text"  id="<%=ekif.getNodeId() %>_<%=parIds %>_pay"  style="width:100%;" 
						name="<%=ekif.getNodeId() %>_<%=parIds %>_pay" value="<%=realScore%>" readonly='true'
						alt="allowBlank:false,vtext:'请输入金额',maxLength:32"/>
					</td>
					 <%
					 }
					}
					}%>
					<!-- 
					<td>
						<%=ekif.getAlgorithm()%>
					</td>
					 -->
				<%} %>
			<% 
			    }
			%>	
		</tr>

			<% 
			    }
			%>		
	</tbody>
	<tbody>
	<tr>
		<td colspan="${requestScope.maxLevel}"  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center>应付金额</center>
		</td>
	<% 
		for(int i=0;i<partnerList.size();i++){
		String parIds = String.valueOf(partnerList.get(i));
	%>
		<td colspan="3" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center>${monthPrice}</center>
		</td>
	<%
	}
	%>
	</tr>
	<tr>
		<td colspan="${requestScope.maxLevel}"  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center>实付金额</center>
		</td>
	<% 
		for(int i=0;i<partnerList.size();i++){
		String parIds = String.valueOf(partnerList.get(i));
	%>
		<td colspan="3" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center><span id='<%=parIds %>_pay' style='text-align:center;'>0<span></center>
		</td>
	<%
	}
	%>
	</tr>
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
      document.forms[0].action="lineAssExecute.do?method=saveTaskDetail";
    };
function  commit()
    {
      document.forms[0].action="lineAssExecute.do?method=commitTaskDetail";
    };
function  runBack()
    {
   	  v.passing="true";
   	  document.getElementById("taskId").value = '' ;
   	  document.getElementById("partner").value = '' ;
      document.forms[0].action="lineAssExecute.do?method=xquery&searchType=1";
    };
function  queryBack()
    {
      v.passing="true";
      document.getElementById("taskId").value = '' ;
      document.forms[0].action="lineAssExecute.do?method=xquery&searchType=0";
    };
function  getTotal()
    {
     var uniPrice = ${uniPrice};
     var total = totalStr.split("|");
     var totalNodes = totalNodesStr.split("|");
       //alert(totalNodesStr);
     var totalPartner = totalPartnerStr.split("|");
     var sum = 0;
     for(i=0;i<total.length-1;i++){
     //alert(totalNodes[i]);
     //alert(total[i]);
     	var totalNodeForSum = totalNodes[i].split(",");
     	//alert(totalNodeForSum);
     	for(j=0;j<totalNodeForSum.length;j++){
     		var scName = totalNodeForSum[j] + '_' + totalPartner[i] + '_sc';
     		//alert(scName);
     		var payName = totalNodeForSum[j] + '_' + totalPartner[i] + '_pay';
     		var kpiTypeName = totalNodeForSum[j] + '_' + totalPartner[i] + '_kpiType';
     		var scValue = document.getElementById(scName).value;
     		var kpiTypeValue = document.getElementById(kpiTypeName).value;
     		//alert(scName);
     		 //alert(scValue);
     		if(kpiTypeValue!='money'){
     			document.getElementById(payName).value = fomatFloat(uniPrice * scValue);
     		}
     		if(document.getElementById(scName).value!=null&&scValue!=''){
     			sum = sum +parseFloat(scValue);
     		}
     	}
     	document.getElementById(total[i]+'_total').innerHTML=fomatFloat(sum);
     	sum = 0;
     }
     getPayTotal();
    };
function  getPayTotal(){
     var total = totalStr.split("|");
     var totalNodes = totalNodesStr.split("|");
     var totalPartner = totalPartnerStr.split("|");
     var sum = 0;
     for(i=0;i<total.length-1;i++){
     	var totalNodeForSum = totalNodes[i].split(",");
     	for(j=0;j<totalNodeForSum.length;j++){
     		var payName = totalNodeForSum[j] + '_' + totalPartner[i] + '_pay';
     		var payValue = document.getElementById(payName).value;
     		if(payValue!=''){
     			sum = sum +parseFloat(payValue);
     		}
     	}
     	document.getElementById(total[i]+'_pay').innerHTML=fomatFloat(sum);
     	sum = 0;
	}
	//合计
     var allTotal = allTotalnodes.split(",");
	 var partnerIds = partners.split("|");
	 for(i=0;i<partnerIds.length;i++){
     	for(j=0;j<allTotal.length;j++){
     		var payName = allTotal[j] + '_' + partnerIds[i] + '_pay';
     		var payValue = document.getElementById(payName).value;
     		if(payValue!=''){
     			sum = sum +parseFloat(payValue);
     		}
     	}
     	document.getElementById(partnerIds[i]+'_pay').innerHTML=fomatFloat(${monthPrice}-sum);;
     	sum = 0;
	}
}
function fomatFloat(score)
	{ 
		return Math.round(score*Math.pow(10, 2))/Math.pow(10, 2); 
	}
Ext.onReady(function(){
	getTotal();
	})
</script>
</c:if>