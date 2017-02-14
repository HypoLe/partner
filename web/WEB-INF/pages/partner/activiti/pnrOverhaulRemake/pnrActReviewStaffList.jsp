<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript">
function openAddView(){
	window.location= "${app}/activiti/pnrTransferPrecheck/pnrActReviewStaff.do?method=addPnrActReviewStaff";
}
</script>


<display:table name="pnrActReviewStaffList" cellspacing="0" cellpadding="0"
		id="pnrActReviewStaffList" pagesize="${pageSize}" class="table pnrActReviewStaffList"
		export="false"
		requestURI="${app}/activiti/pnrTransferPrecheck/pnrActReviewStaff.do?method=queryPnrActReviewStaff"
		sort="list" partialList="true" size="${total}">
		
		<display:column property="cityName" sortable="true" headerClass="sortable" title="地域"  />
		<display:column property="userName" sortable="true" headerClass="sortable" title="人员"  />
		
		<display:column sortable="true" headerClass="sortable" title="处理">			
			<a href="${app}/activiti/pnrTransferPrecheck/pnrActReviewStaff.do?method=updatePnrActReviewStaff&id=${pnrActReviewStaffList.id}"
					title="修改"> 修改 </a>
		</display:column> 
</display:table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="添加" onclick="openAddView();"/>
		</td>
	</tr>
</table>

<%@ include file="/common/footer_eoms.jsp"%>