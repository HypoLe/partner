<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style type="text/css">
	.trMouseOver {
		cursor:pointer;
	}
	tr.trMouseOver td {
		background-color:#CDE0FC;
	}
</style>
	
	<input type="button" value="返回" onclick="javascript:history.back();" />
 	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlanGisAction.do"
		sort="list" partialList="true" size="${resultSize}" 
		decorator="com.boco.eoms.partner.inspect.util.InspectPlanMainDetailByCheckUserDecorator"
		>
		<display:column  title="巡检项" >
			${list.inspectItem}
		</display:column>
		<display:column  title="巡检内容" >
			${list.content}
		</display:column>
		<display:column  title="是否异常" >
			<c:choose>
				<c:when test="${list.exceptionFlag eq 0}">
					异常
				</c:when>
				<c:otherwise>
					正常
				</c:otherwise>
			</c:choose>
		</display:column>
			<display:column  title="异常内容" >
			${list.exceptionContent}
		</display:column>
		<display:column  title="处理情况" >
			<c:choose>
				<c:when test="${list.exceptionFlag eq 0}">
					<c:choose>
						<c:when test="${list.isHandle eq 1}">
							已处理
						</c:when>
						<c:otherwise>
							未处理
						</c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>
		</display:column>
	</display:table>
	
	
	
<%@ include file="/common/footer_eoms.jsp"%>