<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-carat-1-e"></span> 快速导航
		</div>
	</div>
<div id="qucikNavRef" style="margin-bottom: 15px">
	<a class="linkDraft" href="${app }/partner2/indicator.do?method=list">
		返回列表<font color="red">${currentWorkReportForm.title }</font>
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<span>"详情"</span>	
</div>

<table class="formTable">
	<caption>
		<div class="header center">
			考核项目指标详情
		</div>
	</caption>
	<tr>
		<td class="label">
		考核项目指标名称
		</td>
		<td class="content" >
		${indicator.appraisalName}
		</td>
		<td class="label" >
		状态
		</td>
		<td class="content">
		${indicator.status}
		</td>
		<td class="label" >
		类型
		</td>
		<td class="content">
		${indicator.execType}
		</td>
	</tr>

</table>
<%@ include file="/common/footer_eoms.jsp"%>