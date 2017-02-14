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
	<a class="linkDraft" href="${app }/partner2/appraisal.do?method=publishAppraisalResultList">
		返回"考核结果发布"列表<font color="red">${currentWorkReportForm.title }</font>
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<span>"考核结果打印"</span>	
</div>
<table class="formTable">
	<caption>
		<div class="header center">
			<font size="6">${title}</font><br>
			${subTitle}
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
	</tr>
	<c:forEach items="${orderedProxyScaleWithAppraisalList}" var="o" varStatus="i">
		<tr>
		<c:choose>
			<c:when test="${o.appraisalType=='project'}">
			<td class="label">
				${o.appraisalProjectName}
			</td>
			<td class="content" ></td>
			<td class="content" ></td>
			<td class="content" ></td>
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
			</c:otherwise>
			</c:choose>
			</tr>
	</c:forEach>
	<form action="appraisal.do?method=execPrint" method="post">
		<input type="hidden" id="title" name="title" value="${title}"/>
		<input type="hidden" id="title" name="subTitle" value="${subTitle}"/>
		<tr><td class="content" colspan=4>意见：<textarea id="suggess" name="suggess" rows=3 cols=140></textarea></td></tr>
		<tr><<td class="label">甲方签字</td><td class="content"></td><td class="label">乙方签字</td><td class="content"></td></tr>
		<tr><td class="label" >日期</td><td class="content"></td><td class="label" >日期</td><td class="content"></td></tr>
		<tr><td class="content" colspan=4 align=right><input type="submit" value="打印"></td></tr>
	</form>
</table>
<%@ include file="/common/footer_eoms.jsp"%>