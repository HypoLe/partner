<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
    var myjs=jQuery.noConflict();
</script>


<div>
 <table id="list-table"   cellpadding="0" class="listTable" cellspacing="0">
 	 <thead>
 	 	<tr>
 	 		<th>文件分类</th>
 	 		<th>文件总数</th>
 	 		<th>总浏览次数</th>
 	 	</tr>
 	 </thead>
 	 <tbody>
	 	 <c:forEach var="statistics" items="${statisticsInfor}">
	 	 		<tr>
	 	 			<td>
	 	 				${statistics.folder_name }
	 	 			</td>
	 	 			<td>
	 	 				${statistics.counts }
	 	 			</td>
	 	 			<td>
	 	 				${statistics.times }
	 	 			</td>
	 	 		</tr>
	 	 	</c:forEach>
	 	 	<c:forEach var="sum" items="${statisticsSum}">
	 	 		<tr>
	 	 			<td>
	 	 				<b>合计</b>
	 	 			</td>
	 	 			<td>
	 	 				${sum.counts }
	 	 			</td>
	 	 			<td>
	 	 				${sum.times }
	 	 			</td>
	 	 		</tr>
	 	 	</c:forEach>
 	 </tbody>
 </table><%--
 
 	<display:table name="statisticsInfor" cellspacing="0" cellpadding="0"
		id="statisticsInfor" pagesize="${pagesize}"
		class="table statisticsInfor" export="true"
		requestURI="technicDataFiles.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="文件分类">
			${statisticsInfor.folder_name }
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="文件总数">
			${statisticsInfor.counts }
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="总浏览次数">
			${statisticsInfor.times }
		</display:column>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="true" />
		<display:setProperty name="export.excel" value="true"/>
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
--%></div>






<%@ include file="/common/footer_eoms.jsp"%>