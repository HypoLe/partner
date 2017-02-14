<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js">
</script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js">
</script>
<script type="text/javascript">
var myjs = jQuery.noConflict();
function deleteInfo(id) {
if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/armypolicecivilian/armypolicecivilian.do",
					params:{method:"delete",id:id},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {location.href='<html:rewrite page='/armypolicecivilian.do?method=approvedSuccessedList'/>'});
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
					url:"${app}/armypolicecivilian/armypolicecivilian.do",
					params:{method:"deleteSome",ids:ids},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {location.href='<html:rewrite page='/armypolicecivilian.do?method=approvedSuccessedList'/>'});
					},
					failuer:function(res) {
						Ext.Msg.alert("提示：","删除失败！");
					}
				});
			}
			}
			function jumpToAdd(){
	location.href='<html:rewrite page='/armypolicecivilian.do?method=gotoAdd'/>';
}
function deleall(){
 var aa=document.getElementById("ckall");
 var cardNumberList = document.getElementsByName("cardNumber");
 if(aa.checked){
  //  alert(cardNumberList.length);
 	for (i = 0 ; i < cardNumberList.length; i ++) {
 				//alert(myjs('#cardNumberList[i]'));
				//myjs('#cardNumberList[i]').attr('checked',true);
				cardNumberList[i].checked=true;
				};
 }else {
 	for (i = 0 ; i < cardNumberList.length; i ++) {
 				//alert(myjs('#cardNumberList[i]'));
				//myjs('#cardNumberList[i]').attr('checked',true);
				cardNumberList[i].checked=false;
 }
 }
}
function gogo(){
var projectName=document.getElementById("projectName1").value;
var nameOfOrganization=document.getElementById("nameOfOrganization1").value;

window.location.href='${app }//armypolicecivilian/armypolicecivilian.do?method=listArchived&projectName='+projectName+'&nameOfOrganization='+nameOfOrganization;
}
</script>
<form action="armypolicecivilian.do?method=list" method="post" name="armyPoliceCivilian">
	<fieldset>
		<legend>
			快速查询
		</legend>   
		
		<table id="sheet" class="formTable">
		
			<tr>
				<td class="label">
					项目名称:
				</td>
				<td>
					<input type="text" class="text" name="projectName" id="projectName1"/>
					
				</td>

				<td class="label">
					军警民共建单位名称:
				</td>
				<td>
					<input type="text" class="text" id="nameOfOrganization1" name="nameOfOrganization" />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div align="left">
						<input type="submit" class="btn" value="确认查询" />
						&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn" value="查询已归档信息" onclick="gogo()"/>
					</div>
				</td>
			</tr>
		</table>
	</fieldset>


<!-- Information hints area end-->
<logic:notEmpty name="armyPoliceCivilian" scope="request">
	<display:table name="armyPoliceCivilian" cellspacing="0" cellpadding="0"
		class="table armyPoliceCivilian" id="armyPoliceCivilian"
		pagesize="${pagesize}" export="true"
		requestURI="armypolicecivilian.do" sort="list" partialList="true"
		size="${size}">
		<display:column sortable="true" headerClass="sortable" title="审核人">
			<eoms:id2nameDB id="${armyPoliceCivilian.approvingPerson}"
				beanId="tawSystemUserDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="填报时间">
			${armyPoliceCivilian.writeTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所属地市">
			<eoms:id2nameDB id="${armyPoliceCivilian.belongTheLocal}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="项目名称">
			${armyPoliceCivilian.projectName}
		</display:column>
		<display:column sortable="true" headerClass="sortable"
			title="军警民共建单位名称">
			${armyPoliceCivilian.nameOfOrganization}
		</display:column>
		<display:column sortable="true" headerClass="sortable"
			title="合作内容">
		     ${armyPoliceCivilian.cooperativeContent}
		</display:column>
		<display:column sortable="true" headerClass="sortable"
			title="负责光缆线路里程（km）">
			${armyPoliceCivilian.lengthOfOpticalCableRoutes}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="实际效果">
			${armyPoliceCivilian.realEffect}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="状态">
			${armyPoliceCivilian.state}
			<input type="hidden" name="bystate" value="${armyPoliceCivilian.state}">
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${armyPoliceCivilian.id }"
				href="${app }/armypolicecivilian/armypolicecivilian.do?method=gotoDetail&id=${armyPoliceCivilian.id}">
				<img src="${app }/images/icons/table.gif"> </a>
		</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:notEmpty>
</form>
	 
	<logic:empty name="armyPoliceCivilian" scope="request">
	没有数据！
	</logic:empty>
	
<%@ include file="/common/footer_eoms.jsp"%>
