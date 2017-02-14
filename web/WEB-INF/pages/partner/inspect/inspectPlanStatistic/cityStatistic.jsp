<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%response.setHeader("cache-control","public"); %>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
function query(){
	if(document.getElementById('year').value == ''){
		alert('请选择年份');
		return false;
	}
	if(document.getElementById('month').value == ''){
		alert('请选择月度');
		return false;
	}
	
}
</script>
	
<form  action="inspectPlanStatistic.do?method=findCityStatistic" id="gridForm" method="post" > 
	<table class="formTable">
		<tr>
			<td class="label">年份*</td>
			<td class="content">
				<eoms:dict key="dict-partner-inspect" selectId="year" dictId="yearflag" 
					beanId="selectXML" cls="select"/>
			</td>
			<td class="label">月度*</td>
			<td class="content" >
				<eoms:dict key="dict-partner-inspect" selectId="month" dictId="monthflag" 
					beanId="selectXML" cls="select" />
			</td>
		</tr>
	</table>
</center> 
<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="统计" onclick="return query()"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>	
</form>
<br/>

<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
                   requestURI="inspectPlanStatistic.do?method=findCityStatistic"
		id="list" class="table list" export="false" sort="list" >
		<display:column  sortable="true" headerClass="sortable" title="地市">
			<a href="${app }/partner/inspect/inspectPlanStatistic.do?method=findCountryStatistic&city=${list.area }&year=${list.year}&month=${list.month} "> 
				<eoms:id2nameDB id="${list.area}" beanId="tawSystemAreaDao" /><a>
		</display:column>	
		<display:column property="year" title="年份" />	
		<display:column property="month" title="月度" />
		<display:column property="planResNum" title="计划巡检数量" sortable="true" />
		
		<display:column sortable="true" title="完成率(100%)">
		<fmt:formatNumber value="${list.doneRate*100 }" pattern="#.##"></fmt:formatNumber>%
		</display:column>
		
		<display:column sortable="true" title="平均在站时长(小时)">
		<fmt:formatNumber value="${list.aveTimeOnSite*100 }" pattern="#.##"></fmt:formatNumber>
		</display:column>
		
		<display:column property="resExceptionNum" title="异常资源数量" sortable="true" />
	</display:table>
</logic:present>
	</br>
	</div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
