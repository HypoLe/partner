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
<script type="text/javascript">
	function openImport(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}
</script>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">

<form action="armypolicecivilian.do?method=gotoList" method="post" name="armyPoliceCivilian">
	
		<table id="sheet" class="formTable">
		<tr>
				<td class="label">
					状态:
				</td>
				<td>	
					<select name="state" id="state">
					 <option selected="selected"/>
						
						<option value="1">
							待审核
						</option>
						<option value="2">
							驳回
						</option>
						<option value="3">
							归档
						</option>
					
					</select>
				</td>

				<td class="label">
					审核人:
				</td>
				<td>	
				<input type="text"  class="text"  name="approvingPerson1" id="approvingPerson1"  
					value="" alt="allowBlank:false" readonly="readonly"/>
		   		<input type="hidden" name="approvingPerson" id="approvingPerson"  value=""/>
				<eoms:xbox id="approvingPerson" dataUrl="${app}/xtree.do?method=userFromDept"  
				rootId="2" rootText="用户树"  valueField="approvingPerson" handler="approvingPerson1" 
				textField="approvingPerson1" checktype="user" single="true" />
				</td>
			</tr>		
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
						
					</div>
				</td>
			</tr>
		</table>
		</div>
<!-- Information hints area end-->
<logic:notEmpty name="armyPoliceCivilian" scope="request">
	<display:table name="armyPoliceCivilian" cellspacing="0" cellpadding="0"
		class="table armyPoliceCivilian" id="armyPoliceCivilian"
		pagesize="${pagesize}" export="true"
		requestURI="armypolicecivilian.do" sort="list" partialList="true"
		size="${size}">
	
		<display:column sortable="true" headerClass="sortable" title="项目名称">
			${armyPoliceCivilian.projectName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核人">
			<eoms:id2nameDB id="${armyPoliceCivilian.approvingPerson}"
				beanId="tawSystemUserDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="填报时间">
			${armyPoliceCivilian.writeTime}
		</display:column>
		<%----
		<display:column sortable="true" headerClass="sortable" title="所属地市">
			<eoms:id2nameDB id="${armyPoliceCivilian.belongTheLocal}"
				beanId="tawSystemAreaDao" />
		</display:column>
		--%>
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
		
			<display:column sortable="false" headerClass="sortable" title="操作"
				media="html">
				<c:if test="${armyPoliceCivilian.approvingresult eq '驳回'}">
				<a id="${armyPoliceCivilian.id }"
					href="${app }/armypolicecivilian/armypolicecivilian.do?method=gotoEdit&id=${armyPoliceCivilian.id}&state=${armyPoliceCivilian.state}">
					<img src="${app }/images/icons/edit.gif"> </a>
					</c:if>
					<c:if test="${!(armyPoliceCivilian.approvingresult eq '驳回')}">
					无
					</c:if>
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
<div>
    <c:if test="${armyPoliceCivilian.state eq '待审核'}">
	<input type="button" onclick="jumpToAdd()" value="添加" class="btn" /></c:if>
</div>
</form>
	</logic:notEmpty> 
	<logic:empty name="armyPoliceCivilian" scope="request">
	没有数据！
	</logic:empty>
	
<%@ include file="/common/footer_eoms.jsp"%>
