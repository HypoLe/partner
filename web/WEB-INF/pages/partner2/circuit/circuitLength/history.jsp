<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.List"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
	});
</script>
<div id="loadIndicator" class="loading-indicator">加载历史记录信息页面完毕...</div>
<div id="circuitLengthList"><logic:present
	name="circuitLengthList" scope="request">

	<logic:iterate id="circuitLength" name="circuitLengthList"
		type="com.boco.eoms.circuit.model.CircuitLink">
		<table class="history-item-table" width="100%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="label">操作时间</td>
				<td class="content"><bean:write name="circuitLength"
					property="operateTime" format="yyyy-MM-dd HH:mm:ss" scope="page" /></td>
				<td class="label">操作名称</td>
				<td class="content"><bean:write name="circuitLength"
					property="operaterCnName" scope="page" /></td>
			</tr>
			<tr>
				<td class="label">操作人</td>
				<td class="content"><bean:write name="circuitLength"
					property="operateUser" scope="page" /></td>
				<td class="label">操作人部门</td>
				<td class="content"><bean:write name="circuitLength"
					property="operateDeptName" scope="page" /></td>
			</tr>
			<c:if test="${circuitLength.operaterType == 'SUBMIT_TO_CITY' }">
				<tr>
					<td class="label">代维公司意见</td>
					<td class="content"><bean:write name="circuitLength"
						property="linkCutOption" scope="page" /></td>
				</tr>
			</c:if>
			<c:if test="${circuitLength.operaterType == 'CITY_TO_MONITOR' }">
				<tr>
					<td class="label">分公司意见</td>
					<td class="content"><bean:write name="circuitLength"
						property="linkCutOption" scope="page" /></td>
				</tr>
			</c:if>
		</table>
		<img src="${app }/images/icons/icon-add.gif">
	</logic:iterate>
</logic:present> <logic:notPresent name="circuitLengthList" scope="request"> 
     无记录
</logic:notPresent></div>
