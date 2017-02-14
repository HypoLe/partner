<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();

function add(){
	 window.location.href= '${app }/partner/inspect/inspectSpotcheckAction.do?method=toEidtSpotcheckTemplate&inspectTemplateId=${inspectTemplateId}&year=${year}&month=${month}';
	// window.location.href= '${app }/partner/inspect/inspectSpotcheckAction.do?method=checkTemplateItemList&inspectTemplateId=${inspectTemplateId}&year=${year}&month=${month}';
}
function backList(){
	 window.location.href= '${app }/partner/inspect/inspectSpotcheckAction.do?method=findInspectTemplateList&year=${year}&month=${month}';
}


</script>

<table class="table list" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<th>设备类别</th>
			<th>扣分规则</th>
			<th>总分</th>
			<th>巡检项目</th>
			<th>巡检内容</th>
		</tr>
	</thead>
	<c:forEach items="${list}" var="map">
		<c:if test="${empty map.itemList }" var="result">
      		<tr>
				<td>
					<a href="${app }/partner/inspect/inspectSpotcheckAction.do?method=toEidtSpotcheckTemplate&spottmpid=${map.spottmpid}&inspectTemplateId=${inspectTemplateId}">
					${map.itemgroup}</a>
				</td>
				<td>${map.markrule}</td>
				<td>${map.score}</td>
				<td></td>
				<td></td>
			</tr>
      	</c:if>
      	<c:if test="${!result}">
      		<c:forEach items="${map.itemList}" var="obj" varStatus="status"> 
				<tr>
					<c:if test="${status.count==1}">
						<td rowspan="${map.count}">
							${map.itemgroup}
						</td>
						<td rowspan="${map.count}">${map.markrule}</td>
						<td rowspan="${map.count}">${map.score}</td>
					</c:if>
					
					<td>${obj.item}</td>
					<td>${obj.content}</td>
				</tr>
	  		</c:forEach>
      	</c:if>
      
    </c:forEach>
</table>
</br>

<input type="button" class="btn" value="维护抽检模板" onclick="add()"/>
<input type="button" onclick="backList();" value="返回" class="btn"/>
<%@ include file="/common/footer_eoms.jsp"%>