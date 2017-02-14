<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>


<!-- Information hints area end-->
<logic:present name="faultSheetList" scope="request">
	<display:table name="faultSheetList" cellspacing="0" cellpadding="0"
		id="faultSheetList" pagesize="${pagesize}"
		class="table faultSheetList" export="true"
		requestURI="FaultSheetManagement.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="工单编号">
			${faultSheetList.work_OrderNo}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="状态">
			${faultSheetList.faultState}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="主题">
			${faultSheetList.themess}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="操作人">
			${faultSheetList.operatePerson}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="操作人部门">
			${faultSheetList.operatePerson_Department}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="派往对象">
		<eoms:id2nameDB id="${faultSheetList.detailment_Object}" beanId="tawSystemDeptDao" />
			
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="基站属地">
			${faultSheetList.base_Station_Location}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="BSC号">
			${faultSheetList.bscNo}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="BCF号">
			${faultSheetList.bcfNo}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="故障开始时间">
			${faultSheetList.faultStartTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归档时限">
			${faultSheetList.file_TimeLimit}
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="编辑"
			 media="html">
			<a id="${faultSheetList.id }"
				href="${app }/faultSheethz/FaultSheetManagement.do?method=goToEdit&id=${faultSheetList.id}"
				>
				<img src="${app }/images/icons/edit.gif">
				</a>
			
		</display:column>
		
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${faultSheetList.id }"
				href="${app }/faultSheethz/FaultSheetManagement.do?method=goToDetail&id=${faultSheetList.id}"
				>
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>		
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	





<%@ include file="/common/footer_eoms.jsp"%>
