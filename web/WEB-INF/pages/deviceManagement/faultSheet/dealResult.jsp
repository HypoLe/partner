<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<html:form action="FaultSheetResponse.do?method=listResponse" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">工单编号:</td>
					<td>
						<input type="text" class="text" name="work_OrderNo"/>
					</td>
					
					<td class="label">
						操作时间:
					</td>
					<td>
						<input type="text" class="text" name="operateTime"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</html:form>


<logic:present name="faultSheetResponse" scope="request">
	<display:table name="faultSheetResponse" cellspacing="0" cellpadding="0"
		id="faultSheetResponse" pagesize="${pagesize}"
		class="table faultSheetList" export="true"
		requestURI="FaultSheetResponse.do" sort="list"
		partialList="true" size="${size}">
		
<display:column sortable="true" headerClass="sortable" title="工单编号">
			${faultSheetResponse.work_OrderNo}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="主题">
			${faultSheetResponse.themess}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="派发人">
			${faultSheetResponse.operatePerson}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="派发人部门">
		<eoms:id2nameDB id="${faultSheetResponse.operatePerson_Department}" beanId="tawSystemDeptDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="操作时间">
			${faultSheetResponse.operateTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="附件">
			${faultSheetResponse.attachment}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="派往对象">
		${faultSheetResponse.detailment_Object}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="故障结束时间">
			${faultSheetResponse.faultEndTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="故障处理结束时间">
			${faultSheetResponse.faultDealEndTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="故障处理结果">
			${faultSheetResponse.faultDealResult}
		</display:column>	
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a 
				href="${app }/faultSheethz/FaultSheetResponse.do?method=detailAa&id=${faultSheetResponse.id}"
				>
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>
		<display:column sortable="false" headerClass="sortable"
			title="归档" media="html">
			<a 
				href="${app }/faultSheethz/FaultSheetManagement.do?method=archiving&id=${faultSheetResponse.id}&work_OrderNo=${faultSheetResponse.work_OrderNo}"
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