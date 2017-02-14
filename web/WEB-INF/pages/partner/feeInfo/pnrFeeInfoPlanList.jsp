<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">

<content tag="heading">
	<caption>
		<div class="header center"  style="text-align:center;font-weight:bold;">
		付款信息列表
		</div>
	</caption>	
</content>
<br/>

<html:form action="/pnrFeeInfoPlans.do?method=search"
	styleId="pnrFeeInfoPlanForm" method="post">
<div style="border:1px solid #98c0f4;padding:5px;width:98%;" class="x-layout-panel-hd">
工具栏： 
  <img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer"/>
  <span id="openQuery"  style="cursor:pointer" onclick="openQuery(this);">关闭快速查询</span>
</div>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" width="75%">
	
	<tr>
		<td class="label" align="right">
			付款信息名称：
		</td>
		<td>
			<input type="text" name="planPayName" class="text"/>
		</td>
		<td class="label" align="right">
			付款状态：
		</td>
		<td>
			<select name="planState" id="planState" style="width:150px">
				<option></option>
				<option value="0">发起付款</option>
				<option value="1">待审核</option>
				<option value="2">驳回的付款申请</option>
				<option value="3">等待提交付款材料</option>
				<option value="4">材料等待审核中</option>
				<option value="5">被驳回的付款材料</option>
				<option value="6">开始付款</option>
				<option value="7">完成付款</option>
				<option value="8">付款回复</option>
			</select>
		</td>
	</tr>
	
	<tr>	
		<td class="label" align="right">
			计划付款额大于等于：
		</td>
		<td>
			<input type="text" name="planPayFeeMe" class="text"/>
			<font>(万元)</font>
		</td>
		<td class="label" align="right">
			计划付款额小于等于：
		</td>
		<td>
			<input type="text" name="planPayFeeLe" class="text"/>
			<font>(万元)</font>
		</td>
	</tr>
	
	<tr>
		<td class="label" align="right">
			计划付费时间晚于：
		</td>
		<td>
			<input id="planPayTimeStrM" name="planPayTimeStrM" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" alt="vtype:'lessThen',link:'planPayTimeStrL',vtext:'开始时间不能晚于结束时间'"/>
		</td>
		<td class="label" align="right">
			计划付费时间早于：
		</td>
		<td>
			<input id="planPayTimeStrL" name="planPayTimeStrL" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" alt="vtype:'moreThen',link:'planPayTimeStrM',vtext:'结束时间不能早于开始时间'"/>
		</td>
	</tr>
	
	<tr>
		<td colspan="4" align="center">
			<input type="submit" value="查询" id="submitSearch" class="button"/>
		</td>
	</tr>
	</table>
</div>
</html:form>
	<display:table name="pnrFeeInfoPlanList" cellspacing="0" cellpadding="0"
		id="pnrFeeInfoPlanList" pagesize="${pageSize}" class="table pnrFeeInfoPlanList"
		export="false"
		requestURI="${app}/partner/feeInfo/pnrFeeInfoPlans.do?method=search"
		sort="list" partialList="true" size="resultSize">
<display:column sortable="true" headerClass="sortable" title="付款信息名称" paramId="id" paramProperty="id">
			<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=${pnrFeeInfoPlanList.feeId}" target="_blank" name="feeId">
				<eoms:id2nameDB id="${pnrFeeInfoPlanList.feeId}" beanId="pnrFeeInfoMainDao" />
			</a>
</display:column>
			<display:column property="planPayName" sortable="true"
			headerClass="sortable" title="阶段付款名称"  paramId="id" paramProperty="id"/>


	<display:column property="planPayTimeStr" sortable="true"
			headerClass="sortable" title="计划付费时间" paramId="id" paramProperty="id"/>
			
	<display:column property="planPayFee" sortable="true"
			headerClass="sortable" title="计划付款额" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" title="付款状态">
		<c:if test="${pnrFeeInfoPlanList.planState == 0}">发起付款</c:if>
		<c:if test="${pnrFeeInfoPlanList.planState == 1}">待审核</c:if>
		<c:if test="${pnrFeeInfoPlanList.planState == 2}">驳回的付款申请</c:if>
		<c:if test="${pnrFeeInfoPlanList.planState == 3}">等待提交付款材料</c:if>
		<c:if test="${pnrFeeInfoPlanList.planState == 4}">材料等待审核中</c:if>
		<c:if test="${pnrFeeInfoPlanList.planState == 5}">被驳回的付款材料</c:if>
		<c:if test="${pnrFeeInfoPlanList.planState == 6}">开始付款</c:if>
		<c:if test="${pnrFeeInfoPlanList.planState == 7}">完成付款</c:if>
		<c:if test="${pnrFeeInfoPlanList.planState == 8}">付款回复</c:if>
	</display:column>
	
	</display:table>
	<c:if test="${resultSize=='0'}">
		<table  class="formTable"  border="1"   bordercolor="#98C0F4">
		</br>
			<tr>
				<%--<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >付款计划编号</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">付款计划名称</td>
				--%><td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label" >合同编号</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">合同名称</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">合同金额(万元)</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">状态</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">查看</td>
			</tr>
			<tr>
				<td  style="text-align:center;"  colspan="6" >暂无记录</td>
			</tr>
		</table>
	</c:if>		
</fmt:bundle>

<script type="text/javascript">
Ext.onReady(function() {
		v = new eoms.form.Validation( {
			form : 'pnrFeeInfoPlanForm'
		});
	});
function openQuery(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开快速查询";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭快速查询";
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>