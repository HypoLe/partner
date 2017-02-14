<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<html:form action="mechanicalArmManagement.do?method=archiveList" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">项目名称:</td>
					<td>
						<input type="text" class="text" name="projectName"/>
					</td>
					
					<td class="label">
						大型施工机械名称:
					</td>
					<td>
						<input type="text" class="text" name="constructionMachineryName"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
						<!--  	<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app }/faultSheethz/FaultSheetManagement.do?method=listAll'"/>-->
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</html:form>

<!-- Information hints area end-->
<logic:present name="MechanicalArmList" scope="request">
	<display:table name="MechanicalArmList" cellspacing="0" cellpadding="0"
		class="table MechanicalArmList"id="MechanicalArmList" pagesize="${pagesize}"
		 export="true"
		requestURI="mechanicalArmManagement.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="审核人">
			<eoms:id2nameDB id="${MechanicalArmList.approvingPerson}"
				beanId="tawSystemUserDao" />
		</display:column>
	
		<display:column sortable="true" headerClass="sortable" title="填报时间">
			${MechanicalArmList.writeTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所属地市">
			<eoms:id2nameDB id="${MechanicalArmList.belongTheLocal}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="项目名称">
			${MechanicalArmList.projectName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="大型施工机械名称">
			${MechanicalArmList.constructionMachineryName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="大型施工机械手施工地点">
		     ${MechanicalArmList.mechanicalArm_workyard}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="大型施工机械手姓名">
			${MechanicalArmList.mechanicalArmName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="联系电话">
			${MechanicalArmList.contactNumber}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="走访情况">
			${MechanicalArmList.visitSituation}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="状态">
			${MechanicalArmList.state}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核结果">
			${MechanicalArmList.approvingresult}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="备注">
			${MechanicalArmList.remarks}
		</display:column>
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${faultSheetList.id }"
				href="${app }/mechanicalArm/mechanicalArmManagement.do?method=gotoArchivedDetail&id=${MechanicalArmList.id}"
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
