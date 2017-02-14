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
	<a class="linkDraft" style="cursor:hand" onclick="javascript:history.go(-1)">
		返回列表<font color="red">${currentWorkReportForm.title }</font>
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<span>"考核结果详情"</span>	
</div>

<table class="formTable">
	<caption>
		<div class="header center">
			考核结果:<font color="red">${appraisalTemplateName}</font>详情
		</div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle" colspan="2">
		考核项目
		</td>
		<td class="label" style="vertical-align:middle">
		考核权重
		</td>
		<td class="label" style="vertical-align:middle">
		考核结果
		</td>
		<td class="label" style="vertical-align:middle">
		评分说明
		</td>
	</tr>
	<c:forEach items="${orderedProxyScaleWithAppraisalList}" var="o" varStatus="i">
		<tr>
		<c:choose>
			<c:when test="${o.appraisalType=='project'}">
			<td class="label" >
			${o.appraisalProjectName}
			</td>
			<td class="content"  ><!--
				${o.appraisalProjectName}
			--></td>
			<td class="content"  ><!--
				${o.appraiSalScore}
			--></td>
			<td class="content"  ><!--
				${o.appraisalScoreByWeight}
			--></td>
			<td class="content" ><!--
				${o.deductionStandard}
			-->
			</td>
		</c:when>
		<c:when test="${o.appraisalType=='subTotal'}">
			<td class="label" style="background-color:#009966">
			</td>
			<td class="content"  style="background-color:#009966">
				${o.appraisalProjectName}
			</td>
			<td class="content"  style="background-color:#009966">
				${o.appraiSalScore}
			</td>
			<td class="content"  style="background-color:#009966">
				${o.appraisalScoreByWeight}
			</td>
			<td class="content"  style="background-color:#009966">
				${o.deductionStandard}
			</td>
		</c:when>
		<c:when test="${o.appraisalType=='total'}">
			<td class="label" style="background-color:#FF9933">
				${o.appraisalProjectName}
			</td>
			<td class="content"  style="background-color:#FF9933">
				
			</td>
			<td class="content"  style="background-color:#FF9933">
				${o.appraiSalScore}
			</td>
			<td class="content"  style="background-color:#FF9933">
				${o.appraisalScoreByWeight}
			</td>
			<td class="content"  style="background-color:#FF9933">
				${o.deductionStandard}
			</td>
		</c:when>
		<c:otherwise>
			<td class="label">
			</td>
			<td class="content" style="">
				${o.appraisalProjectName}
			</td>
			<td class="content" style="">
				${o.appraiSalScore}
			</td>
			<td class="content"  style="">
				${o.appraisalScoreByWeight}
			</td>
			<td class="content" style="">
				${o.deductionStandard}
			</td>
		</c:otherwise>
		</c:choose>
		</tr>
	</c:forEach>
</table>
<%@ include file="/common/footer_eoms.jsp"%>