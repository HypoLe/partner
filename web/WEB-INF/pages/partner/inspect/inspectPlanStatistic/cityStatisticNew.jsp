<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" import="java.util.*,com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromNew"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
	response.setHeader("cache-control", "public");
%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<style type="text/css">
.text-center {
	text-align: center;
}
</style>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

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
//获得字段名和相应的数据
function getValues(){
	//获取查询出的数据 
	var counts = document.getElementsByTagName("span");
	//不同地区的标志:0 地市;1 区县;2 处理人
	var flag = 0;
	if(counts.length>1){
		var flag = "${ordercode}";
			window.location = "inspectPlanStatistic.do?method=exportTablePartnerStatistic&flag="+flag;
		}else{
			alert("没有数据,请重新选择");
	}
}

function goBack(){
		window.location = "inspectPlanStatistic.do?method=findCityStatisticNew&buttonFlag=2&flag=1";
}
</script>
<form action="inspectPlanStatistic.do?method=findCityStatisticNew"
	id="gridForm" method="post">
	<!--  
	<table class="formTable">
		<tr>
			<td class="label">
				年份*
			</td>
			<td class="content">
				<eoms:dict key="dict-partner-inspect" selectId="year"
					dictId="yearflag" beanId="selectXML" cls="select" />
			</td>
			<td class="label">
				月度*
			</td>
			<td class="content">
				<eoms:dict key="dict-partner-inspect" selectId="month"
					dictId="monthflag" beanId="selectXML" cls="select" />
			</td>
		</tr>
	</table>
	-->

	
	
	<table class="formTable">
		<tr>
			<td class="label">
				年份*
			</td>
			<td class="content">
				<select id="year" name="year">
					<option value="">请选择</option>
					<option value="2012" <c:if test="${year=='2012'}">selected="selected"</c:if>>2012</option>
					<option value="2013" <c:if test="${year=='2013'}">selected="selected"</c:if>>2013</option>
					<option value="2014" <c:if test="${year=='2014'}">selected="selected"</c:if>>2014</option>
					<option value="2015" <c:if test="${year=='2015'}">selected="selected"</c:if>>2015</option>
					<option value="2016" <c:if test="${year=='2016'}">selected="selected"</c:if>>2016</option>
					<option value="2017" <c:if test="${year=='2017'}">selected="selected"</c:if>>2017</option>
					<option value="2018" <c:if test="${year=='2018'}">selected="selected"</c:if>>2018</option>
					<option value="2019" <c:if test="${year=='2019'}">selected="selected"</c:if>>2019</option>
					<option value="2020" <c:if test="${year=='2020'}">selected="selected"</c:if>>2020</option>
				</select>	
			</td>
			<td class="label">
				月度*
			</td>
			<td class="content">
					<select id="month" name="month">
					<option value="">请选择</option>
					<option value="1" <c:if test="${month=='1'}">selected="selected"</c:if>>1</option>
					<option value="2" <c:if test="${month=='2'}">selected="selected"</c:if>>2</option>
					<option value="3" <c:if test="${month=='3'}">selected="selected"</c:if>>3</option>
					<option value="4" <c:if test="${month=='4'}">selected="selected"</c:if>>4</option>
					<option value="5" <c:if test="${month=='5'}">selected="selected"</c:if>>5</option>
					<option value="6" <c:if test="${month=='6'}">selected="selected"</c:if>>6</option>
					<option value="7" <c:if test="${month=='7'}">selected="selected"</c:if>>7</option>
					<option value="8" <c:if test="${month=='8'}">selected="selected"</c:if>>8</option>
					<option value="9" <c:if test="${month=='9'}">selected="selected"</c:if>>9</option>
					<option value="10" <c:if test="${month=='10'}">selected="selected"</c:if>>10</option>
					<option value="11" <c:if test="${month=='11'}">selected="selected"</c:if>>11</option>
					<option value="12" <c:if test="${month=='12'}">selected="selected"</c:if>>12</option>
				</select>	
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td>
				<input type="submit" class="btn" value="统计" onclick="return query()" />
				&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</form>
<br />

<table class="table list" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<c:if test="${ordercode==1}">
				<th rowspan="3">
					地市
				</th>
			</c:if>
			<c:if test="${ordercode==2}">
				<th rowspan="3" width="100">
					区县
				</th>
			</c:if>
			<c:if test="${ordercode==3}">
				<th rowspan="3">
					负责人
				</th>
			</c:if>
			<th rowspan="3">
				年份
			</th>
			<th rowspan="3">
				月度
			</th>
			<th colspan="12" class="text-center" >
				基站
			</th>
			<th colspan="9" class="text-center" >
				接入网
			</th>
			<th colspan="6" class="text-center" >
				铁塔
			</th>
			<th colspan="12" class="text-center" >
				室分
			</th>
			<th colspan="9" class="text-center" >
				WLAN
			</th>
			<th colspan="12" class="text-center" >
				重点机房
			</th>
		</tr>
		<tr>
			<th colspan="3" class="text-center" >
				基站VIP
			</th>
			<th colspan="3" class="text-center" >
				基站A类
			</th>
			<th colspan="3" class="text-center" >
				基站B类
			</th>
			<th colspan="3" class="text-center" >
				基站C类
			</th>
			<th colspan="3" class="text-center" >
				接入网A类
			</th>
			<th colspan="3" class="text-center" >
				接入网B类
			</th>
			<th colspan="3" class="text-center" >
				接入网C类
			</th>
			<th colspan="3" class="text-center" >
				铁塔季度
			</th>
			<th colspan="3" class="text-center" >
				铁塔月标准
			</th>
			<th colspan="3" class="text-center" >
				室分标准
			</th>
			<th colspan="3" class="text-center" >
				室分VIP
			</th>
			<th colspan="3" class="text-center" >
				室分A类
			</th>
			<th colspan="3" class="text-center" >
				室分B类
			</th>
			<th colspan="3" class="text-center" >
				WLANA类
			</th>
			<th colspan="3" class="text-center" >
				WLANB类
			</th>
			<th colspan="3" class="text-center" >
				WLANC类
			</th>
			<th colspan="3" class="text-center" >
				重点机房VIP
			</th>
			<th colspan="3" class="text-center" >
				重点机房A类
			</th>
			<th colspan="3" class="text-center" >
				重点机房B类
			</th>
			<th colspan="3" class="text-center" >
				重点机房C类
			</th>
		</tr>
		<tr>
			<%
				for (int i = 0; i <= 19; i++) {
			%>
			<th>
				巡检总数
			</th>
			<th>
				月需巡检数
			</th>
			<th>
				月已巡检数
			</th>
			<%
				}
			%>
		</tr>
	</thead>
	<c:forEach var="list" items="${resultList}">
		<tr>
			<td id="myChoose">
				<c:if test="${ordercode==1}">
					<a
						href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNew&flag=1&ordercode=2&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&buttonFlag=1">${list.name
						}</a>
				</c:if>
				<c:if test="${ordercode==2}">
					<a
						href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNew&flag=1&ordercode=3&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&buttonFlag=1">${list.name
						}</a>
				</c:if>
				<c:if test="${ordercode==3}">
					<c:choose>
						<c:when test="${list.name == '无'||list.name == ''||list.name ==null}">无</c:when>
						<c:otherwise>
							<eoms:id2nameDB id='${list.name}' beanId='tawSystemUserDao' />
						</c:otherwise>
					</c:choose>
				</c:if>
				<input type="hidden" id="city" value="${list.name }" />
			</td>
			<td id="newYear">
				${list.year }
			</td>
			<td id="newMonth">
				${list.month }
			</td>
			<td class="text-center">
				 <span>${list.v_stage_a }</span>
			</td>
			<td class="text-center">
				 <span>${list.v_stage_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225010301&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.v_stage_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.a_stage_a }</span>
			</td>
			<td class="text-center">
				<span>${list.a_stage_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225010302&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					><span>${list.a_stage_w }</span></a>
			</td>
			<td class="text-center">
				 <span>${list.b_stage_a }</span>
			</td>
			<td class="text-center">
				<span>${list.b_stage_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225010302&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					><span>${list.b_stage_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.c_stage_a }</span>
			</td>
			<td class="text-center">
				<span> ${list.c_stage_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225010304&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					><span> ${list.c_stage_w }</span></a>
			</td>
			<td class="text-center">
				 <span>${list.a_accessNetwork_a }</span>
			</td>
			<td class="text-center">
				<span>${list.a_accessNetwork_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225050201&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.a_accessNetwork_w }</span></a>
			</td>
			<td class="text-center">
				 <span>${list.b_accessNetwork_a }</span>
			</td>
			<td class="text-center">
				 <span>${list.b_accessNetwork_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225050202&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.b_accessNetwork_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.c_accessNetwork_a }</span>
			</td>
			<td class="text-center">
			<span>${list.c_accessNetwork_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225050203&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.c_accessNetwork_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.j_tower_a }</span>
			</td>
			<td class="text-center">
				<span>${list.j_tower_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225040402&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.j_tower_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.y_tower_a } </span>
			</td>
			<td class="text-center">
				 <span>${list.y_tower_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225040401&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.y_tower_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.z_distribution_a }</span>
			</td>
			<td class="text-center">
				<span>${list.z_distribution_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225030201&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.z_distribution_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.v_distribution_a }</span>
			</td>
			<td class="text-center">
				<span>${list.v_distribution_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225030202&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.v_distribution_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.a_distribution_a }</span>
			</td>
			<td class="text-center">
			<span>${list.a_distribution_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225030203&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.a_distribution_w }</span></a>
			</td>
			<td class="text-center">
			<span>${list.b_distribution_a }</span>
			</td>
			<td class="text-center">
				<span>${list.b_distribution_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225030204&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.b_distribution_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.a_wlan_a }</span>
			</td>
			<td class="text-center">
				<span>${list.a_wlan_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225060101&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.a_wlan_w }</span></a>
			</td>

			<td class="text-center">
				 <span>${list.b_wlan_a }</span>
			</td>
			<td class="text-center">
			<span>${list.b_wlan_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225060102&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.b_wlan_w }</span></a>
			</td>

			<td class="text-center">
				<span>${list.c_wlan_a }</span>
			</td>
			<td class="text-center">
				<span>${list.c_wlan_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225060103&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.c_wlan_w }</span></a>
			</td>
			
			<!-- 重点机房 -->
			<td class="text-center">
				<span>${list.v_jifang_a }</span>
			</td>
			<td class="text-center">
				<span>${list.v_jifang_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225060103&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.v_jifang_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.a_jifang_a }</span>
			</td>
			<td class="text-center">
				<span>${list.a_jifang_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225060103&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.a_jifang_w }</span></a>
			</td>
			
			<td class="text-center">
				<span>${list.b_jifang_a }</span>
			</td>
			<td class="text-center">
				<span>${list.b_jifang_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225060103&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.b_jifang_w }</span></a>
			</td>
			<td class="text-center">
				<span>${list.c_jifang_a }</span>
			</td>
			<td class="text-center">
				<span>${list.c_jifang_d }</span>
			</td>
			<td class="text-center">
				<a
					href="${app}/partner/inspect/inspectPlanStatistic.do?method=findCityStatisticNewSpecialty&year=${list.year }&month=${list.month }&rootAreaId=${list.cityId}&specialty=11225060103&ordercode=${ordercode}&inspectState=1&name=${list.name}"
					> <span>${list.c_jifang_w }</span></a>
			</td>
		</tr>
	</c:forEach>

</table>
<input type="button" value="导出" class="btn" onclick="getValues()" />
<c:if test="${ordercode!=1}">
	<input type="button" class="btn" value="返回"
		onclick="goBack();" />
</c:if>




<%@ include file="/common/footer_eoms.jsp"%>
