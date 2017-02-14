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
					url:"${app}/mechanicalArm/mechanicalArmManagement.do",
					params:{method:"delete",id:id},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {location.href='<html:rewrite page='/mechanicalArmManagement.do?method=list'/>'});
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
					url:"${app}/mechanicalArm/mechanicalArmManagement.do",
					params:{method:"deleteSome",ids:ids},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {location.href='<html:rewrite page='/mechanicalArmManagement.do?method=list'/>'});
					},
					failuer:function(res) {
						Ext.Msg.alert("提示：","删除失败！");
					}
				});
			}
			}
			function jumpToAdd(){
	location.href='<html:rewrite page='/mechanicalArmManagement.do?method=gotoAdd'/>';
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
var constructionMachineryName=document.getElementById("constructionMachineryName1").value;

window.location.href='${app }//mechanicalArm/mechanicalArmManagement.do?method=listAll&projectName='+projectName+'&constructionMachineryName='+constructionMachineryName;
}
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
<html:form  action="mechanicalArmManagement.do?method=listAll" styleId="MechanicalArmList" method="post">
	
		
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					项目名称:
				</td>
				<td>
					<input type="text" class="text" name="projectName" id="projectName1"/>
				</td>

				<td class="label">
					大型施工机械名称:
				</td>
				<td>
					<input type="text" class="text" id="constructionMachineryName1" name="constructionMachineryName" />
				</td>
			</tr>
					<tr>							
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
				<td colspan="4">
					<div align="left">
						<input type="submit" class="btn" value="确认查询" />
						
					</div>
				</td>
			</tr>
		</table>
	</html:form>
</div>

<!-- Information hints area end-->
<logic:notEmpty name="MechanicalArmList" scope="request">
	<display:table name="MechanicalArmList" cellspacing="0" cellpadding="0"
		class="table MechanicalArmList" id="MechanicalArmList"
		pagesize="${pagesize}" export="true"
		requestURI="mechanicalArmManagement.do" sort="list" partialList="true"
		size="${size}">
		
				<display:column sortable="true" headerClass="sortable" title="项目名称">
			${MechanicalArmList.projectName}
		</display:column>
<%-----
		<display:column sortable="true" headerClass="sortable" title="所属地市">
			<eoms:id2nameDB id="${MechanicalArmList.belongTheLocal}"
				beanId="tawSystemAreaDao" />
		</display:column>
--%>
		<display:column sortable="true" headerClass="sortable"
			title="大型施工机械名称">
			${MechanicalArmList.constructionMachineryName}
		</display:column>
		<display:column sortable="true" headerClass="sortable"
			title="大型施工机械手施工地点">
		     ${MechanicalArmList.mechanicalArm_workyard}
		</display:column>
		<display:column sortable="true" headerClass="sortable"
			title="大型施工机械手姓名">
			${MechanicalArmList.mechanicalArmName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="联系电话">
			${MechanicalArmList.contactNumber}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核人">
			<eoms:id2nameDB id="${MechanicalArmList.approvingPerson}"
				beanId="tawSystemUserDao" />
		</display:column>
				<display:column sortable="true" headerClass="sortable" title="填报时间">
			${MechanicalArmList.writeTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="状态">
			${MechanicalArmList.state}
			<input type="hidden" name="state" value="${MechanicalArmList.state}"/>
		</display:column>

		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${MechanicalArmList.id }"
				href="${app }/mechanicalArm/mechanicalArmManagement.do?method=gotoDetail&id=${MechanicalArmList.id}">
				<img src="${app }/images/icons/table.gif"> </a>
		</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
	</logic:notEmpty> 
	<logic:empty name="MechanicalArmList" scope="request">
	没有数据！
	</logic:empty>
	
<%@ include file="/common/footer_eoms.jsp"%>
