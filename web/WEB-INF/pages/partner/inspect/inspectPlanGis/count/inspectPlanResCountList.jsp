<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
</script>
<style>
	th {text-align:center; }
</style>

 <form  action="${app}/partner/inspect/inspectPlanGisAction.do?method=saveInspectPlanGis" method="post">
 <input type="hidden" value="提交"/>
  </form>
  
  
  <table class="table list" cellpadding="0" cellspacing="0">
  	<thead>
  		<tr>
  			<c:if test="${ordercode==1}">
  			<th rowspan="3">地市</th>
  			</c:if>
			<c:if test="${ordercode==2}">
  			<th rowspan="3" width="100">区县</th>
  			</c:if>
			<c:if test="${ordercode==3}">
  			<th rowspan="3">处理人</th>
  			</c:if>
  			<th colspan="12">基站</th>
  			<th colspan="9">接入网</th>
  			<th colspan="6">铁塔</th>
  			<th colspan="12">室分</th>
  			<th colspan="9">WLAN</th>
  		</tr>
  		<tr>
  		    <th colspan="3">VIP基站</th>
  		    <th colspan="3">A类基站</th>
  		    <th colspan="3">B类基站</th>
  		    <th colspan="3">C类基站</th>
  		    <th colspan="3">A类接入网</th>
  		    <th colspan="3">B类接入网</th>
  		    <th colspan="3">C类接入网</th>
  		    <th colspan="3">铁塔季度</th>
  		    <th colspan="3">铁塔月标准</th>
  		    <th colspan="3">室分标准</th>
  		    <th colspan="3">室分VIP</th>
  		    <th colspan="3">室分A类</th>
  		    <th colspan="3">室分B类</th>
  		    <th colspan="3">A类WLAN</th>
  		    <th colspan="3">B类WLAN</th>
  		    <th colspan="3">C类WLAN</th>
  		</tr>
  		<tr>
  		<%for(int i=0;i<=15;i++){ %>
            <th>站点总数</th>
            <th>本月完成</th>
            <th>待巡检</th>  
            <%} %>
  		</tr>
  	</thead>
  
  	<c:forEach var="list" items="${resultList}">
  		<tr>
  			<td>
  				<c:if test="${ordercode==1}"><a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCount&flag=1&ordercode=2&rootAreaId=${list.cityId}">${list.name }</a></c:if>
  				<c:if test="${ordercode==2}"><a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCount&flag=1&ordercode=3&rootAreaId=${list.cityId}">${list.name }</a></c:if>
  				<c:if test="${ordercode==3}">
  				<c:choose>
  				<c:when test="${list.name == '无' }">无</c:when>
  				<c:otherwise>
  				<eoms:id2nameDB id='${list.name }' beanId='tawSystemUserDao'/>
  				</c:otherwise>
  				</c:choose>
  				</c:if>
  				
  			</td>
  			<td>
  		          ${list.v_stage_a }
  			</td>
  			<td>
  				  ${list.v_stage_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225010301&ordercode=${ordercode}&name=${list.name}">
				  ${list.v_stage_d }</a>
  			</td>
  			<td>
  		          ${list.a_stage_a }
  			</td>
  			<td>
  				  ${list.a_stage_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225010302&ordercode=${ordercode}&name=${list.name}">
				  ${list.a_stage_d }</a>
  			</td>
  			<td>
  		          ${list.b_stage_a }
  			</td>
  			<td>
  				  ${list.b_stage_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225010303&ordercode=${ordercode}&name=${list.name}">
				  ${list.b_stage_d }</a>
  			</td>
  			<td>
  		          ${list.c_stage_a }
  			</td>
  			<td>
  				  ${list.c_stage_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225010304&ordercode=${ordercode}&name=${list.name}">
				  ${list.c_stage_d }</a>
  			</td>
  			
  			<td>
  		          ${list.a_accessNetwork_a }
  			</td>
  			<td>
  				  ${list.a_accessNetwork_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225050201&ordercode=${ordercode}&name=${list.name}">
				  ${list.a_accessNetwork_d }</a>
  			</td>
  			<td>
  		          ${list.b_accessNetwork_a }
  			</td>
  			<td>
  				  ${list.b_accessNetwork_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225050202&ordercode=${ordercode}&name=${list.name}">
				  ${list.b_accessNetwork_d }</a>
  			</td>
  			<td>
  		          ${list.c_accessNetwork_a }
  			</td>
  			<td>
  				  ${list.c_accessNetwork_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225050203&ordercode=${ordercode}&name=${list.name}">
				  ${list.c_accessNetwork_d }</a>
  			</td>
  			<td>
  		          ${list.j_tower_a }
  			</td>
  			<td>
  				  ${list.j_tower_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225040402&ordercode=${ordercode}&name=${list.name}">
				  ${list.j_tower_d }</a>
  			</td>
  			<td>
  		          ${list.y_tower_a }
  			</td>
  			<td>
  				  ${list.y_tower_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225040401&ordercode=${ordercode}&name=${list.name}">
				  ${list.y_tower_d }
  			</td>
  			
  			<td>
  		          ${list.z_distribution_a }
  			</td>
  			<td>
  				  ${list.z_distribution_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225030201&ordercode=${ordercode}&name=${list.name}">
				  ${list.z_distribution_d }</a>
  			</td>
  			<td>
  		          ${list.v_distribution_a }
  			</td>
  			<td>
  				  ${list.v_distribution_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225030202&ordercode=${ordercode}&name=${list.name}">
				  ${list.v_distribution_d }</a>
  			</td>
  			<td>
  		          ${list.a_distribution_a }
  			</td>
  			<td>
  				  ${list.a_distribution_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225030203&ordercode=${ordercode}&name=${list.name}">
				  ${list.a_distribution_d }</a>
  			</td>
  			<td>
  		          ${list.b_distribution_a }
  			</td>
  			<td>
  				  ${list.b_distribution_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225030204&ordercode=${ordercode}&name=${list.name}">
				  ${list.b_distribution_d }</a>
  			</td>
  			<td>
  		          ${list.a_wlan_a }
  			</td>
  			<td>
  				  ${list.a_wlan_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225060101&ordercode=${ordercode}&name=${list.name}">
				  ${list.a_wlan_d }</a>
  			</td>
  			
  			<td>
  		          ${list.b_wlan_a }
  			</td>
  			<td>
  				  ${list.b_wlan_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225060102&ordercode=${ordercode}&name=${list.name}">
				  ${list.b_wlan_d }</a>
  			</td>
  			
  			<td>
  		          ${list.c_wlan_a }
  			</td>
  			<td>
  				  ${list.c_wlan_w }
  			</td>
  			<td>
  			<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCountSpecialty&rootAreaId=${list.cityId}&year=${year}&month=${month}&specialty=11225060103&ordercode=${ordercode}&name=${list.name}">
				  ${list.c_wlan_d }</a>
  			</td>
  		</tr>
  	</c:forEach>

  </table>
 <c:if test="${ordercode!=1}">
 	<input type="button" class="btn" value="返回"  onclick="javascript:history.back();" /> 
 </c:if>
<%@ include file="/common/footer_eoms.jsp"%>