<%@ page language="java" pageEncoding="UTF-8"
	import="java.util.List,com.boco.eoms.evaluation.model.IndicatorScore,com.boco.eoms.evaluation.model.web.TdObjectModel;"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript"
	src="${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
	
<table id="sheet" class="formTable">
	<td colspan="4">
		<div class="ui-widget-header">
			评分细则
		</div>
	</td>
	<table cellpadding="0" class="table protocolMainList" cellspacing="0">
		<caption>
			<div style="text-align: right" id='totalScore'>
				总分：${totalScore}分
			</div>
		</caption>
		<thead>
			<tr>
				<c:forEach items="${headList}" var="headList">
					<c:if test="${headList.show}">
						<th rowspan="${headList.rowspan}" colspan="${headList.colspan}">
							${headList.name}
						</th>
					</c:if>
				</c:forEach>
				<!-- 评分表头相关 -->
				<th rowspan="1" colspan="1">
					实际打分
				</th>
				<th rowspan="1" colspan="1">
					最终得分
				</th>
			</tr>
		</thead>
		<tbody>
			<%
				//注意变量声明不能放在 c:if内，放在某个if内 作用范围仅是这个if内
				int trIndex = 0;
				List indicatorScoreHistoryList = null;
				IndicatorScore indicatorScoreH = null;
				TdObjectModel indicator = null;
			%>
			<c:if test="${hashistory}">
				<%
					indicatorScoreHistoryList = (List) request
							.getAttribute("indicatorScoreHistoryList");
				%>
			</c:if>

			<c:forEach items="${templateLlt}" var="tdList">
				<c:if test="${hashistory}">
					<%
						indicatorScoreH = (IndicatorScore) indicatorScoreHistoryList
								.get(trIndex);
					%>
				</c:if>
				<tr>
					<c:forEach items="${tdList}" var="td">
						<c:if test="${td.show}">
							<c:if test="${td.datatype!='indicatorextra'}">
								<td rowspan="${td.rowspan}" colspan="${td.colspan}">
									${td.name}(${td.proportion})
								</td>
							</c:if>
							<c:if test="${td.datatype=='indicatorextra'}">
								<td rowspan="${td.rowspan}" colspan="${td.colspan}">
									${td.name}
								</td>
							</c:if>
						</c:if>
					</c:forEach>
					<!-- 评分表体相关 -->
					<td rowspan="1" colspan="1">
						<input type="text" class="text" id="inputscore_<%=trIndex%>"
							name="inputscore"
							<c:if test="${hashistory}"> value="<%=indicatorScoreH.getInputscore()%>"</c:if>
							readOnly="true" style="border: none; background: none;"">
						</input>
					</td>
					<td rowspan="1" colspan="1">
						<input type="text" class="text" id="score_<%=trIndex%>"
							name="score"
							<c:if test="${hashistory}"> value="<%=indicatorScoreH.getScore()%>"</c:if>
							readOnly="true" style="border: none; background: none;">
						</input>
					</td>
				</tr>
				<%
					trIndex++;
				%>
			</c:forEach>
		</tbody>
	</table>	
</table>

<script type="text/javascript">

</script>
<%@ include file="/common/footer_eoms.jsp"%>