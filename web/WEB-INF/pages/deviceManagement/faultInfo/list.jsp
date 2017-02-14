<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
function deleteInfo(id) {
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/faultInfo/faultInfo.do",
					params:{method:"delete",id:id},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {window.location.reload();});
					},
					failuer:function(res) {
						Ext.Msg.alert("提示：","删除失败！");
					}
				});
			}
}
</script>
	<div id="tabs-1">
	<html:form action="faultInfo.do?method=list" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">工单类别:</td>
					<td>
						<eoms:comboBox name="sheetTypeStringEqual"
							id="sheetTypeStringEqual" initDicId="11802" alt="allowBlank:false" styleClass="select" />
					</td>
					
					<td class="label">
						故障类别:
					</td>
					<td>
						<eoms:comboBox name="faultTypeStringEqual"
								id="faultTypeStringEqual" initDicId="11804" alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>
			
				<tr>
					<td class="label">
						工单编号:
					</td>
					<td >
						<input type="text" class="text" name="sheetIdStringLike"/>
					</td>
					
					<td class="label">
						基站名称:
					</td>
					<td >
						<input type="text" class="text" name="baseStationNameStringLike" />
					</td>
				</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app }/faultInfo/faultInfo.do?method=list'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</html:form>

<!-- Information hints area end-->
<logic:present name="faultInfoList" scope="request">
	<display:table name="faultInfoList" cellspacing="0" cellpadding="0"
		id="faultInfoList" pagesize="${pagesize}"
		class="table faultInfoList" export="true"
		requestURI="faultInfo.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="上报人">
			${faultInfoList.reportPerson}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="上报时间">
			${faultInfoList.reportTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="基站名称">
			${faultInfoList.baseStationName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="维护级别">
			<eoms:id2nameDB id="${faultInfoList.maintainLevel}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单编号">
			${faultInfoList.sheetId}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单创建时间">
			${faultInfoList.sheetStartTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单确认时间">
			${faultInfoList.sheetConfirmTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单结束时间">
			${faultInfoList.sheeEndTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="处理结果">
			${faultInfoList.processingResults}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="故障类别">
			<eoms:id2nameDB id="${faultInfoList.faultType }" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="设备调整">
			${faultInfoList.deviceAdjust}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="遗留问题">
			${faultInfoList.residualProblem}
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="编辑"
			paramProperty="id" url="/faultInfo/faultInfo.do?method=goToEdit"
			paramId="id" media="html">
			<img src="${app }/images/icons/edit.gif">
		</display:column>
		
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${faultInfoList.id }"
				href="${app }/faultInfo/faultInfo.do?method=goToDetail&id=${faultInfoList.id}"
				target="blank" shape="rect">
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="删除"
			 media="html">
			 <a href="javascript:void(0)" onclick="deleteInfo('${faultInfoList.id}')">
				<img class="delete" src="${app }/images/icons/icon.gif" />
			</a>
		</display:column>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
	</div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
