<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function() {
	setTabs();
});
function setTabs() {
		var pageType = '${ifSee}';
		var tabs = new Ext.TabPanel('info-page');
		tabs.addTab('base-info', '统计内容');
		if(pageType != null && pageType =="ifSee") {
	       	tabs.addTab('history-info', '上一季度统计内容 ');
		}
   		tabs.activate(0);
	}
</script>


<div id="info-page">
	<div id="base-info" class="tabContent">
<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">
<table id="sheet" class="table">
	<logic-el:present name="allStatisticList">
		<thead>
	<tr ><th colspan="2" ><b>设备类型</b></th>
		<c:forEach items="${factoryList}" var="factList">
		<th><b><eoms:id2nameDB beanId="IItawSystemDictTypeDao" id="${factList}"/></b></th>
			</c:forEach>		
				 </tr>	
			</thead>	 
				 <tbody>
				<c:forEach items="${allStatisticList}" var="allList" >
				<tr><td  class="label"  rowspan="3"><b><eoms:id2nameDB beanId="IItawSystemDictTypeDao" id="${allList.key}"/></b></td>
				<td><b>得分</b></td>
				<c:forEach items="${allList.listScore}" var="scoreList" varStatus="i">
				<td>
				<c:if test="${scoreList=='NaN'}">
				${scoreList}
				</c:if>
				<c:if test="${scoreList!='NaN'}">
				<a href="javascript:void(0);"
									onclick="window.open('${app}/partner/deviceAssess/statistic.do?method=statistic&factory=${factoryList[i.count-1]}&devicetype=${allList.key}&startTime=${startTime}&endTime=${endTime}');">${scoreList}</a>
									</c:if>
									</td>
				</c:forEach>
				</tr>
				<tr>
				<td><b>排名</b></td>
				<c:forEach items="${allList.listRanking}" var="rankingList">
				<td>${rankingList}</td>
				</c:forEach>
				</tr>
				<tr>
				<td><b>设备占比</b></td>
				<c:forEach items="${allList.listPercent}" var="percentList">
				<td>${ percentList}</td>
				</c:forEach>
				</tr>
				</c:forEach>
		</tbody>
	</logic-el:present>
						<tr ><td class="label" colspan="2" ><b>综合得分</b></td>
		<c:forEach items="${finallyScore}" var="fsList">
		<td>${fsList }</td>
			</c:forEach>		
				 </tr>
				 
				 <tr ><td class="label" colspan="2" ><b>综合排名</b></td>
		<c:forEach items="${finallyRank}" var="frList">
		<td>${frList}</td>
			</c:forEach>		
				 </tr>
				 <c:if test="${!empty finallyRankHistory}">
				 				 <tr ><td class="label" colspan="2" ><b>前季度排名</b></td>
		<c:forEach items="${finallyRankHistory}" var="fRHList">
		<td>${fRHList}</td>
			</c:forEach>		
				 </tr>
				 </c:if>
</table>
</fmt:bundle>
</div>
<div id="history-info" class="tabContent">
<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">
<table id="sheet" class="table">
	<logic-el:present name="allStatisticListHistory">
		<thead>
	<tr ><th colspan="2" ><b>设备类型</b></th>
		<c:forEach items="${factoryList}" var="factList">
		<th><b><eoms:id2nameDB beanId="IItawSystemDictTypeDao" id="${factList}"/></b></th>
			</c:forEach>		
				 </tr>	
			</thead>	 
				 <tbody>
				<c:forEach items="${allStatisticListHistory}" var="allListHistory" >
				<tr><td  class="label"  rowspan="3"><b><eoms:id2nameDB beanId="IItawSystemDictTypeDao" id="${allListHistory.key}"/></b></td>
				<td row="2"><b>得分</b></td>
				<c:forEach items="${allListHistory.listScore}" var="scoreListHistory" varStatus="i">
				<td>
				<c:if test="${scoreListHistory=='NaN'}">
				${scoreListHistory}
				</c:if>
				<c:if test="${scoreListHistory!='NaN'}">
				<a href="javascript:void(0);"
									onclick="window.open('${app}/partner/deviceAssess/statistic.do?method=statistic&factory=${factoryList[i.count-1]}&devicetype=${allListHistory.key}&startTime=${startTimeHistory}&endTime=${endTimeHistory}');">${scoreListHistory}</a>
									</c:if>
									</td>
				</c:forEach>
				</tr>
				<tr>
				<td><b>排名</b></td>
				<c:forEach items="${allListHistory.listRanking}" var="rankingListHistory">
				<td>${rankingListHistory}</td>
				</c:forEach>
				</tr>
				<tr>
				<td><b>设备占比</b></td>
				<c:forEach items="${allListHistory.listPercent}" var="percentListHistory">
				<td>${ percentListHistory}</td>
				</c:forEach>
				</tr>
				</c:forEach>
		</tbody>
	</logic-el:present>
						<tr ><td class="label" colspan="2" ><b>综合得分</b></td>
		<c:forEach items="${finallyScore}" var="fsList">
		<td>${fsList }</td>
			</c:forEach>		
				 </tr>
				 
				 <tr ><td class="label" colspan="2" ><b>综合排名</b></td>
		<c:forEach items="${finallyRank}" var="frList">
		<td>${frList}</td>
			</c:forEach>		
				 </tr>
</table>
</fmt:bundle>
</div>
</div>






<%@ include file="/common/footer_eoms.jsp"%>