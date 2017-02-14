<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<logic:notPresent name="list" scope="request">
	无该月基站代维数据
</logic:notPresent>

<logic:present name="list" scope="request">

<div style="margin-bottom: 15px;font-size: 18;font: bold;">
<h1 align="center">
	山东联通${iCity}基站代维结算表( ${yearFlag }年${monthFlag }月)
</h1>
</div>
<div style="margin-bottom: 15px">
<span >
	代维公司:${monitorCompanyName }
</span>
</div>

	<table class="formTable">
	<thead>
		<tr>
			<td rowspan="2">区域(项目)</td>
			<td colspan="2">2G宏基站总数(个)</td>
			<td colspan="2">边际网基站(个)</td>
			<td colspan="2">独立TD宏基站(个)</td>
			<td colspan="2">共址TD宏基站(个)</td>
			<td colspan="2">直放站(个)</td>
			<td rowspan="2">基站基准单价(元)</td>
			<td rowspan="2">地区差异系数</td>
			<td rowspan="2">应付金额(元)</td>
			<td rowspan="2">本月基站考核得分(分)</td>
			<td rowspan="2">本月结算代维费(元)</td>
		</tr>
		<tr>
			<td>代维数量</td>
			<td>设备差异系数</td>
			<td>代维数量</td>
			<td>设备差异系数</td>
			<td>代维数量</td>
			<td>设备差异系数</td>
			<td>代维数量</td>
			<td>设备差异系数</td>
			<td>代维数量</td>
			<td>设备差异系数</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="station" items="${list}">
				<tr>
					<td><eoms:id2nameDB id="${station.country}" beanId="tawSystemAreaDao" /></td>
					<td>${station.station1Monitor }</td>
					<td>${station.station1Differ }</td>
					<td>${station.station2Monitor }</td>
					<td>${station.station2Differ }</td>
					<td>${station.station3Monitor }</td>
					<td>${station.station3Differ }</td>
					<td>${station.station4Monitor }</td>
					<td>${station.station4Differ }</td>
					<td>${station.station5Monitor }</td>
					<td>${station.station5Differ }</td>
					<td>${station.stationMaintenancePrice }</td>
					<td>${station.stationAreaDiffer }</td>
					<td>${station.stationFee }</td>
					<td>${station.appraisalRealScore }</td>
					<td>${station.balanceFee }</td>
				</tr>
			</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td >合计</td>
			<td colspan="2">${sum1 }</td>
			<td colspan="2">${sum2 }</td>
			<td colspan="2">${sum3 }</td>
			<td colspan="2">${sum4 }</td>
			<td colspan="2">${sum5 }</td>
			<td></td>
			<td></td>
			<td>${sumStationFee }</td>
			<td>${avgAppraisalRealScore }</td>
			<td>${sumBalanceFee }</td>
		</tr>
	</tfoot>
	</table>

<table width=100%>
	<tbody>
		<tr>
			<td width=20%  ></td>
			<td width=20%  >经手人(代维方):</td>
			<td width=20%  ></td>
			<td width=20%  >经手人(联通公司):</td>
			<td width=20%  ></td>
		</tr>
		<tr>
		<td width=20%  ></td>
			<td width=20%  >确认日期:</td>
			<td width=20%  ></td>
			<td width=20%  >确认日期:</td>
			<td width=20%  ></td>
		</tr>
		<tr>
		<td width=20%  ></td>
			<td width=20%  >部门负责人(代维方):</td>
			<td width=20%  ></td>
			<td width=20%  >部门负责人(联通公司):</td>
			<td width=20%  ></td>
		</tr>
		<tr>
		<td width=20%  ></td>
			<td width=20%  >部门盖章:</td>
			<td width=20%  ></td>
			<td width=20%  >部门盖章:</td>
			<td width=20%  ></td>
		</tr>
	</tbody>
</table>

</logic:present>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	
});
</script>

<%@ include file="/common/footer_eoms.jsp"%>
