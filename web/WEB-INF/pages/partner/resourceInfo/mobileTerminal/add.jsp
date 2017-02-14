<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*,com.boco.eoms.base.util.StaticMethod;"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
var phoneCheck=false;
function saveImport() {
	//表单验证
	if(!v2.check()) {
		if(!phoneCheck){
			return false;
		}
		return;
	}
    new Ext.form.BasicForm('importForm').submit({
	method : 'post',
		url : "${app}/partner/resourceInfo/mobileTerminal.do?method=importData",
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
	v1.custom = function() {
        	if(!phoneCheck){
        		return false;
        	}
        		return true;
     }
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
function checkPhone(){ 
		    var sMobile =  document.getElementById("simCardNumber").value;
		    if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(sMobile))){ 
		        alert("不是完整的11位手机号或者正确的手机号前七位"); 
		        phoneCheck= false; 
		    } 
		    else{
		    	phoneCheck=true;
		    }
} 
</script>
<div class="header center"><b>增加移动终端信息</div><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer"	>从Excel导入</span>
</div>
<div id="listQueryObject"
			style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="mobileTerminal.do?method=importData" method="post" 
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
										onclick="javascript:location.href='${app}/partner/resourceInfo/mobileTerminal.do?method=download'"/>
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
					区域&nbsp;*
				<br></td>
				<td class="content">
					<input type="text" name="area_name" id="area_name"  readonly="readonly"  class="text medium" alt="allowBlank:false"/>
					<input type="hidden" name="area" id="area"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="area" handler="area_name" textField="area_name"
					checktype="" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="maintainCompany_name" class="text max" readonly="readonly" id="maintainCompany_name" alt="allowBlank:false"/>
					<input type="hidden" name="maintainCompany" id="maintainCompany"  maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight" rootId=""
					rootText='代维公司组织' valueField="maintainCompany" handler="maintainCompany_name" textField="maintainCompany_name"
					checktype="dept" single="true">
					</eoms:xbox>
				</td>
		</tr>
		<tr>
				<td class="label">
					设备编号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="mobileTerminalNumber" class="text medium" id="mobileTerminalNumber" alt="allowBlank:false"/>
				</td>
				<td class="label">
					维护组&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="maintainTeam_view" class="text max" id="maintainTeam_view" alt="allowBlank:false"/>
					<input type="hidden" name="maintainTeam" id="maintainTeam"  maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree4" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight&showPartnerLevelType=4&showLevelControl=true"
						 rootId="" rootText='代维小组树' valueField="maintainTeam" handler="maintainTeam_view" textField="maintainTeam_view"
						checktype="dept" single="true">
					</eoms:xbox>
				</td>
		</tr>
		<tr>
				<td class="label">
					生产厂家&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="mobileTerminalFactory" id="mobileTerminalFactory" 	class="text medium" 	alt="allowBlank:false" />
				</td>
				<td class="label">
					设备序列号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="mobileTerminalSerilNumber" id="mobileTerminalSerilNumber" class="text medium" 	alt="allowBlank:false" />
				</td>
		</tr>
		<tr>
				<td class="label">
					手机号码&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="simCardNumber" id="simCardNumber" class="text medium" 	alt="allowBlank:false" onblur="checkPhone();" />
				</td>
				<%--
				<td class="label">
					SIM类型&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="simCardType" id="simCardType" 	initDicId="1230402" styleClass="input select" 	alt="allowBlank:false"/>
				</td>
	
		</tr>
		<tr>
				<td class="label">
					设备识别码&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="identificationCode" id="identificationCode"    class="text medium" alt="allowBlank:false"/>
				</td>
			--%>
				<td class="label">
					终端类型&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="mobileTerminalType"  id="mobileTerminalType"  styleClass="input select"  initDicId="1230401" alt="allowBlank:false" />
				</td>
		</tr>
		<tr>
			<td class="label">
				备注
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max"  name="notes" id="notes" ></textarea>
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
</script>
<%@ include file="/common/footer_eoms.jsp"%>
