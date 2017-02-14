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
<div id="loadIndicator" class="loading-indicator">加载故障历史记录信息页面完毕...</div>
<div id="circuitList"><logic:present name="circuitList"
	scope="request">

	<logic:iterate id="circuit" name="circuitList"
		type="com.boco.eoms.circuit.model.CircuitLink">
		<table class="history-item-table" width="100%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="label">操作时间</td>
				<td class="content"><bean:write name="circuit"
					property="operateTime" format="yyyy-MM-dd HH:mm:ss" scope="page" /></td>
				<td class="label">操作名称</td>
				<td class="content"><bean:write name="circuit"
					property="operaterCnName" scope="page" /></td>
			</tr>
			<tr>
				<td class="label">操作人</td>
				<td class="content"><bean:write name="circuit"
					property="operateUser" scope="page" /></td>
				<td class="label">操作人部门</td>
				<td class="content"><bean:write name="circuit"
					property="operateDeptName" scope="page" /></td>
			</tr>
			<c:if test="${circuit.operaterType == 'MONITOR_TO_USER' }">
				<tr>
					<td class="label">是否核减通过</td>
					<td class="content"><eoms:id2nameDB
						id="${circuit.linkCheckIdea}" beanId="IItawSystemDictTypeDao" /></td>
					<td class="label">代维公司意见</td>
					<td class="content"><bean:write name="circuit"
						property="linkCutOption" scope="page" /></td>
				</tr>
			</c:if>
			<c:if test="${circuit.operaterType == 'USER_TO_MASTER' }">
				<tr>
					<td class="label">分公司意见</td>
					<td class="content"><bean:write name="circuit"
						property="linkCutOption" scope="page" /></td>
				</tr>
			</c:if>
			<c:if test="${circuit.operaterType == 'MASTER_TO_USER' }">
				<tr>
					<td class="label">省公司意见</td>
					<td class="content"><bean:write name="circuit"
						property="linkCutOption" scope="page" /></td>
				</tr>
			</c:if>
		</table>
		<img src="${app }/images/icons/icon-add.gif">
	</logic:iterate>
</logic:present> <logic:notPresent name="circuitList" scope="request"> 
      无法显示故障历史记录信息页面
</logic:notPresent></div>
