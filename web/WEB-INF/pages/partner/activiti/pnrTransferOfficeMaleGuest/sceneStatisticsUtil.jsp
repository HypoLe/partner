<%@ page language="java" pageEncoding="utf-8"%>
<div id="maleSceneStatisticsDiv" ${statisticsFlag== "yes"? 'style="display:block;"':'style="display:none;"'}>
	<table class="formTable">
		<tr>
			<td class="label">材料金额</td>
			<td class="content"><span id="totalAmountSpan">${maleSceneStatisticsModel.totalAmount}</span>元</td>
			<td class="label">电杆</td>
			<td class="content"><span id="poleNumSpan">${maleSceneStatisticsModel.poleNum}</span>棵</td>
		</tr>
		<tr>
			<td class="label">光缆数量</td>
			<td class="content"><span id="opticalCableLengthSpan">${maleSceneStatisticsModel.opticalCableLength}</span>米</td>
			<td class="label">接头盒数量</td>
			<td class="content"><span id="jointBoxNumSpan">${maleSceneStatisticsModel.jointBoxNum}</span>套</td>
		</tr>
	</table>
</div>
<br />