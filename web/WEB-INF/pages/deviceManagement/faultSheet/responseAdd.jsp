<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
function theUrl(){
  var aa=document.getElementById("work_OrderNo").value;
  
    var time=document.getElementById("themess").value;
window.location.href="${app }/faultSheethz/FaultSheetManagement.do?method=listHistory&work_OrderNo="+aa+"&themess="+time+"";

}
</script>

<content tag="heading">
<c:out value="黑龙江代维故障工单管理回复添加页面" />
</content>
<html:form action="FaultSheetManagement.do?method=listThem"
	method="post">
	<fieldset>
		<legend>
			快速查询
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					工单编号:
				</td>
				<td colspan="3">
					<input type="text" id="work_OrderNo" class="text"
						name="work_OrderNo" />
				</td>


				<td class="label">
					主题:
				</td>
				<td>
					<input type="text"id="themess" class="text" name="themess" />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div align="left">
						<input type="submit" class="btn" value="确认查询" />
						<input type="button" class="btn" value="历史查询" onclick="theUrl()" />
					</div>

				</td>
			</tr>
		</table>
	</fieldset>
</html:form>

<logic:present name="faultSheetListResponse" scope="request">
	<display:table name="faultSheetListResponse" cellspacing="0"
		cellpadding="0" id="faultSheetListResponse" pagesize="${pagesize}"
		class="table faultSheetList" export="true"
		requestURI="FaultSheetManagement.do" sort="list" partialList="true"
		size="${size}">
		<display:column sortable="true" headerClass="sortable" title="工单编号">
			${faultSheetListResponse.work_OrderNo}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="主题">
			${faultSheetListResponse.themess}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="状态">
			${faultSheetListResponse.faultState}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="主题">
			${faultSheetListResponse.themess}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="派发人">
			${faultSheetListResponse.operatePerson}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="派发人部门">
			${faultSheetListResponse.operatePerson_Department}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="派往对象">
			${faultSheetListResponse.detailment_Object}
				
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="基站属地">
			${faultSheetListResponse.base_Station_Location}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="BSC号">
			${faultSheetListResponse.bscNo}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="BCF号">
			${faultSheetListResponse.bcfNo}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="故障开始时间">
			${faultSheetListResponse.faultStartTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归档时限">
			${faultSheetListResponse.file_TimeLimit}
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${faultSheetListResponse.id }"
				href="${app }/faultSheethz/FaultSheetManagement.do?method=goToResponseDetail&themess=${faultSheetListResponse.themess}&work_OrderNo=${faultSheetListResponse.work_OrderNo}&id=${faultSheetListResponse.id}">
				<img src="${app }/images/icons/table.gif"> </a>
		</display:column>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>

<%@ include file="/common/footer_eoms.jsp"%>