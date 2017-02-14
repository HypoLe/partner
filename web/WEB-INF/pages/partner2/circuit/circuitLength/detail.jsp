<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
	});
</script>
<div id="loadIndicator" class="loading-indicator">加载故障详细信息页面完毕...</div>
<div id="circuitLength"><logic:present name="circuitLength"
	scope="request">

	<table class="history-item-table" width="100%" cellpadding="0"
		cellspacing="0">
		<tr>
			<td class="label">分公司</td>
			<td><eoms:id2nameDB id="${circuitLength.city}"
				beanId="tawSystemAreaDao" /></td>
			<td class="label">归属县公司</td>
			<td><eoms:id2nameDB id="${circuitLength.country}"
				beanId="tawSystemAreaDao" /></td>
		</tr>
		<tr>
			<td class="label">代维公司</td>
			<td><eoms:id2nameDB id="${circuitLength.monitorCompany}"
				beanId="tawSystemAreaDao" /></td>
			<td class="label">记录插入创建时间(服务器时间)</td>
			<td>${circuitLength.insertTime}</td>
		</tr>
		<c:if test="${templateId!=null}">
			<tr>
				<td class="label">
					关联考核模板
				</td>
				<td id="templateLink" colspan="3">
					<a href="${app}/partner2/appraisal.do?method=showDetail&templateId=${templateId}" target="_blank">
						${templateName}
					</a>
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="label">辖区内光缆长度</td>
			<td>${circuitLength.cableLength}</td>
			<td class="label">当前任务所有者</td>
			<td>${circuitLength.currentTaskOwner}</td>
		</tr>
		<tr>
			<td class="label">架空光缆（皮长公里）</td>
			<td>${circuitLength.cableLength1 }</td>
			<td class="label">架空光缆增减（皮长公里）</td>
			<td>${circuitLength.cableLength1x }</td>
		</tr>
		<tr>
			<td class="label">管道（直埋）光缆（皮长公里）</td>
			<td>${circuitLength.cableLength2 }</td>
			<td class="label">管道（直埋）光缆增减（皮长公里）</td>
			<td>${circuitLength.cableLength2x }</td>
		</tr>
		<tr>
			<td class="label">同路由架空光缆（皮长公里）</td>
			<td>${circuitLength.cableLength3 }</td>
			<td class="label">同路由架空光缆增减（皮长公里）</td>
			<td>${circuitLength.cableLength3x }</td>
		</tr>
		<tr>
			<td class="label">同路由管道（直埋）光缆（皮长公里）</td>
			<td>${circuitLength.cableLength4 }</td>
			<td class="label">同路由管道（直埋）光缆增减（皮长公里）</td>
			<td>${circuitLength.cableLength4x }</td>
		</tr>
		<tr>
			<td class="label">空闲管道（管程公里）</td>
			<td>${circuitLength.cableLength5 }</td>
			<td class="label">空闲管道增减（管程公里）</td>
			<td>${circuitLength.cableLength5x }</td>
		</tr>
	</table>
	<br />
</logic:present> <logic:notPresent name="circuitLength" scope="request"> 
     无记录
</logic:notPresent></div>
