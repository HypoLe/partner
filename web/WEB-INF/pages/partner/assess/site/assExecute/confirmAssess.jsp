<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="com.boco.eoms.partner.assess.util.AssConstants"%>
<%@ page import="com.boco.eoms.partner.assess.AssFlow.model.AssFlow"%>
<%@ page import="com.boco.eoms.partner.assess.AssFlow.mgr.impl.LineAssFlowMgrImpl"%>
<%@ page import="java.util.List"%>
<%
int i=0;
int j=0;
List tableList = (List)request.getAttribute("tableList");
int colspan = StaticMethod.nullObject2int(request.getAttribute("maxLevel"));

if(tableList.size()>0){
	colspan = colspan+7;
}else{
	colspan = colspan+5;
}
%>
<style type="text/css">
.tContent{
	padding:15px;
	display: ;
}
</style>
<script type="text/javascript">
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('main-info', '后评估信息 ');
        	tabs.addTab('step-info', '步骤列表 ');
        	tabs.addTab('confirm-info', '确认信息 ');
    		tabs.activate(2);
	});	
	var totalStr = "";
	var totalNodesStr = "";
	var totalPartnerStr = "";
	var partners = "";
	var allTotalnodes = "";
</script>
<div id="info-page">
  <div id="confirm-info" class="tContent">
<html:form action="/lineAssOperateStep.do?method=stepDone" styleId="assOperateStepForm" method="post">

	<input type="hidden" id="operateId" name="operateId" value="${reportAndStepForm.id}"/>
	<input type="hidden" id="reportId" name="reportId" value="${report.id}"/>
	<input type="hidden" id="areaId" name="areaId" value="${reportAndStepForm.area}"/>
	<input type="hidden" id="templateId" name="templateId" value="${reportAndStepForm.templateId}"/>
	<input type="hidden" id="partnerId" name="partnerId" value="${reportAndStepForm.partnerId}"/>
	<input type="hidden" id="partnerName" name="partnerName" value="<eoms:id2nameDB id="${reportAndStepForm.partnerId}" beanId="partnerDeptDao" />"/>
	<input type="hidden" name="timeType" value="month"/>
	<input type="hidden" name="time" value="${reportAndStepForm.time}"/>
	<input type="hidden" name="state" value="${reportAndStepForm.state}"/> 
	<input type="hidden" name="auditFlag">
<table class="formTable">
	<caption>
		<div class="header center">确认信息</div>
	</caption>
	<tr>
		<td class="label">
			操作人
		</td>
		<td>
			<eoms:id2nameDB id="${sessionform.userid }" beanId="tawSystemUserDao" />	
		</td>
		<td class="label">
			操作角色
		</td>
		<td>
			<eoms:id2nameDB id="${roleId }" beanId="tawSystemRoleDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			联系方式
		</td>
		<td class="content" colspan="3">
			<html:text property="operateUserContact" styleId="operateUserContact"
			  value="${reportAndStepForm.operateUserContact}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			确认意见
		</td>
		<td class="content" colspan="3">
			<html:textarea property="operateOpinion" styleId="operateOpinion"
						rows="2" style="width:98%;"  value="${reportAndStepForm.operateOpinion}" />
		</td>
	</tr>
	<tr>
	<td class="label">
	附件
	</td>
	<td class="content" colspan="3">
	
	<eoms:attachment name="reportAndStepForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="assess"  startsWith='0'/>
	</td>
	</tr>
	<tr>
		<td class="label">
			确认结果		
		</td>
		<td class="content" colspan="3">
			       <INPUT type="radio" name="auditResult" value="2" onclick="showRejectStep(this.value)" checked="true" >通过
			       <INPUT type="radio" name="auditResult" value="1" onclick="showRejectStep(this.value)">驳回 
		</td>
	</tr>
	<tr></tr>
	<tr id='reject'>
		<td class="label">
			驳回环节
		</td>
		<td class="content" colspan="3">
			<select name="nextStep" id="nextStep"
					alt="allowBlank:false,vtext:'请选择要驳回的环节'">
				<logic:iterate id="rejectList" name="rejectList" >
						<option value="${rejectList.id}">${rejectList.description}</option>
				</logic:iterate>
			</select>
		</td>
	</tr>
	</table>
	<table>
	<tr height="30">
		<td colspan="2">
            <input type="submit" value="提交" >
		</td>
	</tr>
</table>
</html:form>
</div>
<div id="main-info" class="tabContent">
<table class="listTable" id="list-table">
  <caption>
		<div class="header center">
			<eoms:id2nameDB id="${report.area}" beanId="tawSystemAreaDao" />-${report.taskName}-<eoms:id2nameDB id="${requestScope.partnerId}" beanId="partnerDeptDao" />-${requestScope.time} 
		</div>
	</caption>
	<thead>
	<tr>
	<!-- 
		<td>
			序号
		</td>
	 -->
		<td colspan="${requestScope.maxLevel}">
			后评估指标
		</td>
		<td >
			扣款/扣分原因
		</td>
		<td >
			扣分情况
		</td>
		<td >
			扣款情况（元）
		</td>
	</tr>
	</thead>
	<tbody>
	<logic:iterate id="rowList" name="tableList" type="java.util.List" indexId="index">
	<tr>
		<logic:iterate id="taskDetail" name="rowList">
		<bean:define id="leaf" name="taskDetail" property="leaf" />
		<bean:define id="nodeId" name="taskDetail" property="nodeId" />
		<bean:define id="nodesForTotal" name="taskDetail" property="nodesForTotal" />
		<bean:define id="kpiType" name="taskDetail" property="kpiType" />
		<!--
		<%
		if(nodeId.toString().length()==10){ 
		%>
		<td rowspan="${taskDetail.rowspan}" colspan="1" class="label" style="vertical-align:middle;text-align:center">
			<%=++i %>
		</td>
		<%
		}
		%>
		-->
		<td rowspan="${taskDetail.rowspan}" colspan="${taskDetail.colspan}" class="label" style="vertical-align:middle;text-align:left">
			<eoms:id2nameDB id="${taskDetail.kpiId}" beanId="lineAssKpiDao" />
			<c:if test="${taskDetail.weight!=0}">
					(${taskDetail.weight}%)
			</c:if>  
		</td>
		<%if (AssConstants.NODE_LEAF.equals(leaf)) { 
			j++;
		%>
		<bean:define id="parentNodeId" name="taskDetail" property="parentNodeId" />
		<input type="hidden"  id="<%=nodeId %>_${partnerId}_kpiType"  value="<%=kpiType %>" />
						   
				<%
					if(!"total".equals(kpiType)&&!"text".equals(kpiType)){
				%>
					<script type="text/javascript">
					if(allTotalnodes!=''){
							allTotalnodes = allTotalnodes+',<%=nodeId%>';
						}else{
							allTotalnodes = <%=nodeId%>;
						}
					</script>
				<%
					}
					  if("total".equals(kpiType)){
				%>
				<script type="text/javascript">
						totalStr = totalStr + '<%=parentNodeId %>_${partnerId}|';
						totalNodesStr = totalNodesStr + '<%=nodesForTotal %>|';
						totalPartnerStr = totalPartnerStr + '${partnerId}|';
				</script>
				<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
				</td>
				<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					<span id='<%=parentNodeId %>_${partnerId}_total' style='text-align:center;'>0<span>
				</td>
				<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					<span id='<%=parentNodeId %>_${partnerId}_pay' style='text-align:center;'>0<span>
				</td>
				<%
				}else if("text".equals(kpiType)){
				 %>
				<td style="vertical-align:middle;text-align:center;">
					${taskDetail.remark}
				</td>
				 <%
				}else{
				 %>
		<td>
			${taskDetail.reduceReason}
		</td>
		<td>
			${taskDetail.realScore}
			<input type="hidden"  id="<%=nodeId %>_${partnerId}_sc" value="${taskDetail.realScore}" />
		</td>
		<td>
			${taskDetail.money}
			<input type="hidden"  id="<%=nodeId %>_${partnerId}_pay" value="${taskDetail.money}" />
		</td>
		<%
		}
		}	
		%>
		</logic:iterate>
	</tr>
	</logic:iterate>
	</tbody>
		<tbody>
	<tr>
		<td colspan="${requestScope.maxLevel}"  class="label" style="vertical-align:middle;text-align:center;">
			<center>应付金额</center>
		</td>
		<td colspan="3" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center>${monthPrice}</center>
		</td>
	</tr>
	<tr>
		<td colspan="${requestScope.maxLevel}"  class="label" style="vertical-align:middle;text-align:center;">
			<center>实付金额</center>
		</td>
		<td colspan="3" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center><span id='${partnerId }_pay' style='text-align:center;'>0<span></center>
		</td>
	</tr>
	</tbody>
</table>
  </div>
  <div id="step-info" class="tabContent">
    <table class="formTable">
	<caption>
		<div class="header center">操作步骤信息</div>
	</caption>
	<tr>
		<td width="10%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			操作人
		</td>
		<!-- 
		<td class="label" style="vertical-align:middle;text-align:center;">
			联系方式
		</td>
		-->
		<td width="15%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			操作时间
		</td>
		<td width="15%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			操作步骤
		</td>
		<td width="10%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			操作状态
		</td>
		<td width="25%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			操作意见
		</td>
		<td width="25%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			相关附件
		</td>
	</tr>
	<%
	AssFlow assFlow = null;
	LineAssFlowMgrImpl flowMgr = new LineAssFlowMgrImpl(); 
	%>
	<logic:iterate id="step" name="operateSteps">
	<bean:define id="state" name="step" property="state" />
	<%
	assFlow = flowMgr.getAssFlowByXml(String.valueOf(state));
	%>
	<tr height="50">
		<td class="label">
			<eoms:id2nameDB id="${step.operateUser }" beanId="tawSystemUserDao" />
		</td>
		<!-- 
		<td class="label">
			${step.operateUserContact }
		</td>
		-->
		<td class="label">
			${step.operateTime }
		</td>
		<td class="label">
			<%=assFlow.getDescription() %>
		</td>
		<td class="label">
		<c:if test="${step.operateFlag=='1' }">
			未处理
		</c:if>
		<c:if test="${step.operateFlag=='2' }">
			完成
		</c:if>
		<c:if test="${step.operateFlag=='3' }">
			驳回
		</c:if>
		<c:if test="${step.operateFlag=='4' }">
			通过
		</c:if>
		</td>
		<td class="label">
			${step.operateOpinion }
		</td>		
		<td  style="vertical-align:middle;text-align:center;">
		 <eoms:attachment name="step" property="accessoriesId"
						scope="page" idField="accessoriesId" appCode="assess"  viewFlag="Y"/>
		</td>
	</tr>
	</logic:iterate>
	</table>
    </div>
</div>




<script type="text/javascript">

function fomatFloat(score)
	{ 
		return Math.round(score*Math.pow(10, 2))/Math.pow(10, 2); 
	}
function  getTotal()
    {
     var uniPrice = ${uniPrice};
     var total = totalStr.split("|");
     var totalNodes = totalNodesStr.split("|");
       //alert(totalNodesStr);
     var sum = 0;
     for(i=0;i<total.length-1;i++){
     //alert(totalNodes[i]);
     //alert(total[i]);
     	var totalNodeForSum = totalNodes[i].split(",");
     	//alert(totalNodeForSum);
     	for(j=0;j<totalNodeForSum.length;j++){
     		var scName = totalNodeForSum[j] + '_${partnerId}_sc';
     		//alert(scName);
     		var payName = totalNodeForSum[j] + '_${partnerId}_pay';
     		var kpiTypeName = totalNodeForSum[j] + '_${partnerId}_kpiType';
     		var scValue = document.getElementById(scName).value;
     		var kpiTypeValue = document.getElementById(kpiTypeName).value;
     		//alert(scName);
     		 //alert(scValue);
     		if(kpiTypeValue!='money'){
     			document.getElementById(payName).value = uniPrice * scValue;
     		}
     		if(document.getElementById(scName).value!=null&&scValue!=''){
     			sum = sum +parseFloat(scValue);
     		}
     	}
     	document.getElementById(total[i]+'_total').innerHTML=sum;
     	sum = 0;
     }
     getPayTotal();
    };
function  getPayTotal(){
     var total = totalStr.split("|");
     var totalNodes = totalNodesStr.split("|");
     var sum = 0;
     for(i=0;i<total.length-1;i++){
     	var totalNodeForSum = totalNodes[i].split(",");
     	for(j=0;j<totalNodeForSum.length;j++){
     		var payName = totalNodeForSum[j] + '_${partnerId}_pay';
     		var payValue = document.getElementById(payName).value;
     		if(payValue!=''){
     			sum = sum +parseFloat(payValue);
     		}
     	}
     	document.getElementById(total[i]+'_pay').innerHTML=sum;
     	sum = 0;
	}
	//合计
	var allSum = 0;
     var allTotal = allTotalnodes.split(",");
     for(j=0;j<allTotal.length;j++){
     	var payName = allTotal[j] + '_${partnerId}_pay';
     	var payValue = document.getElementById(payName).value;
     	if(payValue!=''){
     		allSum = allSum +parseFloat(payValue);
     	}
     	
     	document.getElementById('${partnerId}_pay').innerHTML=fomatFloat(${monthPrice}-allSum);
     
	}
}

function showRejectStep(auditResult){
	if(auditResult=='1'){
		eoms.$("reject").show(); 
	}else{	
		eoms.$("reject").hide(); 
	}
}

Ext.onReady(function(){
	getTotal();
	eoms.$("reject").hide();
	})	
</script>

<%@ include file="/common/footer_eoms.jsp"%>