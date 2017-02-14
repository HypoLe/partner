<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var myjs=jQuery.noConflict();
function deleteInfo(id) {
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/checkSegment/checkSegmentAction.do",
					params:{method:"delete",id:id},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {location.href='<html:rewrite page='/checkSegmentAction.do?method=list'/>'});
					},
					failuer:function(res) {
						Ext.Msg.alert("提示：","删除失败！");
					}
				});
			}
}

function deleteSome() {
			var cardNumberList = document.getElementsByName("cardNumber");
				var ids = "";
				for (i = 0 ; i < cardNumberList.length; i ++) {
				if (cardNumberList[i].checked) {
					var myTempStr=cardNumberList[i].value;
					ids+=myTempStr.toString()+";"
					}
				};
			if (ids == "") {
				alert("请至少选择一项");
				return false;
			} 
			if(confirm("确定要删除吗？")){
				
				Ext.Ajax.request({
					url:"${app}/checkSegment/checkSegmentAction.do",
					params:{method:"deleteSome",ids:ids},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {location.href='<html:rewrite page='/checkSegmentAction.do?method=list'/>'});
					},
					failuer:function(res) {
						Ext.Msg.alert("提示：","删除失败！");
					}
				});
			}
}


function jumpToAdd(){
	location.href='<html:rewrite page='/checkSegmentAction.do?method=goToAdd'/>';
}
</script>
	<div id="tabs-1">
	<form action="checkSegmentAction.do?method=find" method="post" id="checkSegmentFindForm" name="checkSegmentFindForm">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label" >巡检段名称</td>
					<td colspan="3" class="content">
						<input type="text" class="text" name="segmentName"/>
					</td>
					
				</tr>
			
				<tr>
					<td class="label">
						巡检段类型
					</td>
					<td colspan="3">
						<input type="text" class="text" name="segmentType"/>
					</td>
				</tr>
				
				<tr>
					<td class="label">
						管辖部门
					</td>
					<td colspan="3" class="content">
	 		 			<input type="button" name="deptButton" id="deptButton" value="请选择部门" class="btn"/>
	  					<input type="hidden" name="department" id="department"/>
	 		 			<eoms:xbox id="userTree" dataUrl="${app}/xtree.do?method=dept" rootId="-1" rootText="部门树" valueField="department" handler="deptButton" viewer="true" single="true"></eoms:xbox>
					</td>
				</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>

<!-- Information hints area end-->
<logic:present name="checkSegmentList" scope="request">
	<display:table name="checkSegmentList" cellspacing="0" cellpadding="0"
		id="checkSegmentList" pagesize="${pagesize}"
		class="table checkSegmentList" export="true"
		requestURI="checkSegmentAction.do" sort="list"
		partialList="true" size="${size}">
		<display:column media="html" title="">
				 <input type="checkbox" name="cardNumber" value="${checkSegmentList.id}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="巡检段名称">
			${checkSegmentList.segmentName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="线路类型">
			${checkSegmentList.lineType}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="线路级别">
			${checkSegmentList.lineLevel}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="开始标示">
			${checkSegmentList.startFlag}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="结束标示">
			${checkSegmentList.endFlag}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="巡检段类型">
			${checkSegmentList.segmentType}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="管辖部门">			
			<eoms:id2nameDB id="${checkSegmentList.department}" beanId="tawSystemDeptDao" />
		</display:column>
		
		
		<display:column sortable="false" headerClass="sortable" title="编辑"
			paramProperty="id" url="/checkSegment/checkSegmentAction.do?method=goToEdit"
			paramId="id" media="html">
			<img src="${app }/images/icons/edit.gif">
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="删除"
			 media="html">
			 <a href="javascript:void(0)" onclick="deleteInfo('${checkSegmentList.id}')">
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
	<br/>
	<input type="button" onclick="jumpToAdd()" value="添加" class="btn"/>
	<input type="button" onclick="deleteSome()" value="删除" class="btn"/>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
