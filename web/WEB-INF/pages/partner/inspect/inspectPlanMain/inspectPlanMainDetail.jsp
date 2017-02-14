<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var myjs=jQuery.noConflict();
	var isPage = '${param.p}';	//获取是否是关联资源翻页操作
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
		var tabs = new Ext.TabPanel('info-page');
		tabs.addTab('content-info', '内容信息 ');
       	tabs.addTab('history-info', '关联资源');
       	tabs.addTab('approve-info', '审批历史');
       	if(isPage=='t'){ //如果是关联资源翻页操作则刷新页面后定位到关联资源的tab上
       		tabs.activate(1);
       	}else{
       		tabs.activate(0);
       	}
	});

</script>

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>

<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="content-info" class="tabContent">
	
<table id="stylesheet" class="formTable">

	<content tag="heading">
	</content>
		<tr>
			<td class="label">巡检任务名称</td>
			<td class="content">
			${planMain.planName}
			</td>
			<td class="label">巡检专业</td>
			<td><eoms:id2nameDB id="${planMain.specialty}" beanId="ItawSystemDictTypeDao" /></td>
		</tr>

		<tr>
			<td class="label">巡检单位</td>
			<td><eoms:id2nameDB id="${planMain.partnerDeptId }" beanId="partnerDeptDao"/></td>
			<td class="label">关联资源数</td>
			<td class="content">${planMain.resNum }</td>

		</tr>
		<tr>
			<td class="label">巡检日期</td>
			<td class="content">
			<c:if test="${!empty planMain.year}" var="result">
				${planMain.year}年${planMain.month}月
			</c:if>
			</td>
			<td class="label">制定人</td>
			<td class="content"><eoms:id2nameDB id="${planMain.creator }" beanId="tawSystemUserDao" /></td>
		</tr>
		<tr>
			<td class="label">制定日期</td>
			<td class="content">
				<bean:write name="planMain" property="createTime" scope="request" format="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td class="label">是否下月复制</td>
			<td class="content">
				<c:choose>
					<c:when test="${planMain.copyFlag eq 1}">是</c:when>
					<c:otherwise>
						否
					</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
		<tr>
			<td class="label">描述</td>
			<td class="content" colspan="3">${planMain.content}</td>
		</tr>
		
		<caption>
		</caption>	
		</tr>		
	</table>
	
<div id="history-info" class="tabContent">
 
<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlan.do?p=t"
		sort="list" partialList="true" size="resultSize" 
		decorator="com.boco.eoms.partner.inspect.util.InspectPlanResDetailListDecorator"
	>
	<center>
		<display:column property="resName" title="巡检任务名称" />
		<display:column title="巡检专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源类型">
			<eoms:id2nameDB id="${list.resType}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect ne true }">
		<display:column title="资源级别">
			<eoms:id2nameDB id="${list.resLevel}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		</c:if>
		<display:column  sortable="true" headerClass="sortable" title="地市">
			<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>			
		<display:column  sortable="true" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column title="巡检小组">
			<eoms:id2nameDB id="${list.executeObject}" beanId="partnerDeptDao"/>
		</display:column>
		<display:column title="巡检周期" >
			${cycleMap[list.inspectCycle]}
		</display:column>
		<display:column title="周期开始日期" >
			<bean:write name="list" property="cycleStartTime" format="yyyy-MM-dd"/>
		</display:column>
		<display:column title="周期结束日期" >
			<bean:write name="list" property="cycleEndTime" format="yyyy-MM-dd"/>
		</display:column>
		<display:column title="巡检开始日期" >
			<c:if test="${list.inspectCycle=='week' && empty list.planStartTime}" var="result">
				<bean:write name="list" property="cycleStartTime" format="yyyy-MM-dd"/>
			</c:if>
			<c:if test="${list.inspectCycle!='week' && empty list.planStartTime}" var="result">
				${currentMonthStart}
			</c:if>
			<c:if test="${!empty list.planStartTime}" var="result">
				<bean:write name="list" property="planStartTime" format="yyyy-MM-dd"/>
			</c:if>
			
		</display:column>
		<display:column title="巡检结束日期" property="planEndTime" />
	</display:table>
  </div>

  <div id="approve-info" class="tabContent">	 
  	   <logic:present name="approveList" scope="request">
		<display:table name="approveList" cellspacing="0" cellpadding="0"
			id="approveList" class="table approveList" export="false" sort="list">
			<display:column title="审批人">
				<eoms:id2nameDB id="${approveList.approver}" beanId="tawSystemUserDao" />
			</display:column>
			<display:column title="审批操作">
				${approveStatusMap[approveList.approveStatus]}
			</display:column>
			<display:column property="approveIdea" title="审批意见" />
			<display:column title="审批时间" >
				<bean:write name="approveList" property="approveTime" format="yyyy-MM-dd HH:mm:ss"/>
			</display:column>
			<display:column title="计划类型">
				${planTypeMap[approveList.planType]}
			</display:column>
		</display:table>
	</logic:present>
  	
  </div>

</form>

<%@ include file="/common/footer_eoms.jsp"%>