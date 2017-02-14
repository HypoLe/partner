<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript"> 
var myjs=jQuery.noConflict();
 function dele(myValue){
    myjs( "#dialog:ui-dialog" ).dialog( "destroy" );
		myjs( "#dialog-confirm" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			buttons: {
				"确定": function() {
					myjs( this ).dialog( "close" );					
					myjs.ajax({
					  type:"POST",
					  url:"FaultSheetManagement.do?method=delete&id="+myValue,
					  
					 success:deleteResult					 	  
					 });	
				},
				"退出": function() {
					myjs( this ).dialog( "close" );
				}
			}
		
		});
   
   };
	function deleteResult() {
	  
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		myjs( "#dialog:ui-dialog" ).dialog( "destroy" );
	
		myjs( "#dialog-message" ).dialog({
			modal: true,
			buttons: {
				Ok: function() {
				    window.location.reload();
					myjs( this).dialog( "close" );
					
				}
			}
			
		});
		
	};

</script>
<html:form action="FaultSheetManagement.do?method=list" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">工单编号:</td>
					<td>
						<input type="text" class="text" name="work_OrderNo"/>
					</td>
					
					<td class="label">
						主题:
					</td>
					<td>
						<input type="text" class="text" name="themess"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app }/faultSheethz/FaultSheetManagement.do?method=listAll'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</html:form>

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
			<eoms:id2nameDB id="${faultSheetList.operatePerson_Department}" beanId="tawSystemDeptDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="派往对象">
		     ${faultSheetList.detailment_Object}
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
		<display:column sortable="true" headerClass="sortable" title="接收时限">
			${faultSheetList.receivedtimelimited}
		</display:column>
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${faultSheetList.id }"
				href="${app }/faultSheethz/FaultSheetManagement.do?method=goToDetail&id=${faultSheetList.id}"
				>
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>		
		<display:column sortable="false" headerClass="sortable" title="作废"
			 media="html">
			 <a href="javascript:void(0)" onclick="dele('${faultSheetList.id}')">
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
	
<div id="dialog-confirm" title="确认删除" style="display:none">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>您确定要删除此条信息?</p>
</div>	
<div id="dialog-message" title="删除结果" style="display:none">
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
		删除成功
	</p>
	
</div>




<%@ include file="/common/footer_eoms.jsp"%>
