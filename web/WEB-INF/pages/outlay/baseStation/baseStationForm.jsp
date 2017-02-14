<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();

function saveImport() {
	//表单验证
	if(!v2.check()) {
		return;
	}
    new Ext.form.BasicForm('importForm').submit({
	method : 'post',
		url : "${app}/partner/oilmachine//BaseStation.do?method=importData",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		},
		failure : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		}
    });
 }
 
function openImport(){
	var handler=document.getElementById("openQuery");
	var el = Ext.get('listQueryObject'); 
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
}
Ext.onReady(function() {
	v1 =new eoms.form.Validation({form:'mobileTerminalForm'});
	v2 = new eoms.form.Validation({form:'importForm'});
	v2.custom = function() {
		var reg = "(.xls)$";
		var file = jq("#importFile").val();
		var right = file.match(reg);
		if(right == null) {
			alert("请选择Excel文件!");
			return false;
		} else {
			return true;
		}
	}
});	

</script>
<div class="header center"><b>增加基站信息</div><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="middle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer"	>从Excel导入</span>
</div>
<div id="listQueryObject"
			style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="/BaseStation.do?method=importData" method="post" 
				enctype="multipart/form-data" id="importForm" name="importForm">
				<fieldset>
					<legend>
						从Excel导入
					</legend>
					<table class="formTable">
						<tr>
							<td class="label">
								选择Excel文件
							</td>
							<td width="35%">
								<input id="importFile" type="file" name="importFile"	class="file" alt="allowBlank:false" />
							</td>
						</tr>
						<tr>
							<td class="label">
								模板下载
							</td>
							<td>
								<input type="button" class="btn" value="下载导入文件模板" 
										onclick="javascript:location.href='${app}/partner/oilmachine/BaseStation.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/>
<form action="${app}/partner/resourceInfo/mobileTerminal.do?method=save"    id="mobileTerminalForm" method="post"  >
	<table class="formTable">
		<tr>
				<td class="label">
					地市&nbsp;*
				</td>
				<td class="content">
					<select name="region" id="city" class="select"  onchange="changeCity(0);">
						<option value="">
							--请选择地市--
						</option>
						<logic:iterate id="city" name="city">
							<c:if test="${cityId==city.areaid}" var="result">
								<option value="${city.areaid}" selected="selected" >
									${city.areaname}
								</option>
							</c:if>
							<c:if test="${!result}">
								<option value="${city.areaid}" >
									${city.areaname}
								</option>
							</c:if>
						</logic:iterate>
					</select>
				</td>
				<td class="label">
					区县&nbsp;*
				</td>
				<td class="content">
					<input type="hidden" id="country0" value="${baseStation.county}">
					<select name="county" id="county"  class="select" onchange="changeCheckBox();">
						<option value="">
							请选择县区
						</option>				
					</select>
				</td>
		</tr>
		<tr>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="deptName" class="text medium" readonly="readonly" id="deptName" alt="allowBlank:false" value="${baseStation.deptName}"/>
					<input type="hidden" name="deptId" id="deptId"  maxLength="32" 	class="text medium" value="${baseStation.deptId}"/>
					<input type="button" name="treeBtn_9" id="treeBtn_9" value="请选择所属公司" class="btn" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf&showPartnerLevelType=3" rootId=""
					rootText='代维公司组织' valueField="deptId" handler="treeBtn_9" textField="deptName"
					checktype="dept" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					所属驻点&nbsp;*
				</td>
				<td class="content">
					<input  type="text" class="text" id="relativeSiteName" name="relativeSiteName" value="${baseStation.relativeSiteName}"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					基站名称&nbsp;*
				</td>
				<td class="content" colspan="3">
					<input  type="text" class="text" id="bsName" name="bsName" value="${baseStation.bsName}"/>
				</td>
		</tr>
		<tr>
			<td class="label">
				备注
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max"  name="remark" id="remark" value="${baseStation.remark}" ></textarea>
			</td>
		</tr>
	</table>
	<table>
	<tr>
		<td>
		<input type="submit" value="提交"> 		
		<input type="reset" value="重置" > 		
		<%--<input type="button" value="返回" onclick="goBack()">
		--%>
		</td>
	</tr>
	</table>
</form>
<script type="text/javascript">

	function goBack(){
		window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/mobileTerminal.do?method=search";
	}
	function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	         for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
	/**	
	* 页面初始化地市下的区县
	*/
	function initCountry(){
		delAllOption("country");
		var city = document.getElementById("city").value;
		var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
		Ext.Ajax.request({
			url : url ,
			method: 'POST',
			success: function ( result, request ) { 
				res = result.responseText;
				if(res.indexOf("[{")!=0){
					res = "[{"+result.responseText;
				}
				if(res.indexOf("<\/SCRIPT>")>0){
			  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
				}
				var json = eval(res);
				var countyName = "country";
				var arrOptions = json[0].cb.split(",");
				var obj=$(countyName);
				var i=0;
				var j=0;
				var country = "${country}";
				for(i=0;i<arrOptions.length;i++){
					var opt=new Option(arrOptions[i+1],arrOptions[i]);
					obj.options[j]=opt;
					if(arrOptions[i]==country){
						obj.options[j].selected=true;
					}
					j++;
					i++;
				}
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});
	}
//地区、区县连动
function changeCity(con){    
   delAllOption("country");//地市选择更新后，重新刷新县区
   changeCheckBox();//检查是否选中
	var region = document.getElementById("city").value;
	var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
	//var result=getResponseText(url);
	Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
						res = result.responseText;
						if(res.indexOf("[{")!=0){
 								res = "[{"+result.responseText;
						}
						if(res.indexOf("<\/SCRIPT>")>0){
					  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
						}
						var json = eval(res);
						var countyName = "country";
						var arrOptions = json[0].cb.split(",");
						var obj=$(countyName);
						var i=0;
						var j=0;
						for(i=0;i<arrOptions.length;i++){
							var opt=new Option(arrOptions[i+1],arrOptions[i]);
							obj.options[j]=opt;
							j++;
							i++;
						}
						if(con==1){				
							var city = '${gridForm.city}';
							var partnerid = '${gridForm.partnerid}';
							if(city!=''){
								document.getElementById("city").value = city;
							}
							if(partnerid!=''){
								changePartner(1);								
                            }	
						}							
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
		}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
