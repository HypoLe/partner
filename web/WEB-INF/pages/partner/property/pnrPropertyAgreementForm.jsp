<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style>
	.redStar {
		color:red;
	}
</style>
<script type="text/javascript">
	var jq=jQuery.noConflict();
	var phoneCheckj=false;
	var phoneChecky=false;
	//重置功能
	function res(){
		var obj=document.getElementById("pnrPropertyAgreementForm");
		var inputs = obj.getElementsByTagName('input');
	   	 var selects =obj.getElementsByTagName('select');
			     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
	              inputs[i].value = '';
	         }
	          if(inputs[i].type == 'checkbox'){
	              inputs[i].checked =false;
	         }
     	}
     	 for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	              selects[i].options[0].selected = true;
	         }
     	}
	}
	//添加填写号码的框
	function btnAdd(obj) {
		var tr = jq(obj).parents("tr");//找到所在的tr
		var phoneTr="<tr><td class=\"label\">增加提醒对象号码</td>"
		phoneTr+="<td class=\"content\" colspan=\"3\" ><input type=\"text\" name=\"remindObject\" class=\"text\">";
		phoneTr+=	"   <input onclick=\"btnAdd(this)\" type=\"button\" style=\"width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/add.png');background-repeat: no-repeat;\"  value=\"   \"/>"
		phoneTr+=	"  <input onclick=\"btnDelete(this)\" type=\"button\" style=\"width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/list-delete.gif');background-repeat: no-repeat;\"  value=\"\"/>"
		phoneTr+="</td></tr>";
		tr.after(phoneTr);
	}
	//删除填写号码的框
	function btnDelete(obj) {
		var tr = jq(obj).parents("tr");
		tr.remove();
	}
	Ext.onReady(function(){
		if("${pnrPropertyAgreement.partaSigningPersonTel}"!=""){//修改时先对电话号码进行校验
			if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test("${pnrPropertyAgreement.partaSigningPersonTel}"))){
			      phoneCheckj= false; 
			}else{
				 phoneCheckj=true; 
			}
		}else{
			phoneCheckj=true;
		}
		if("${pnrPropertyAgreement.partbSigningPersonTel}"!=""){
			if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test("${pnrPropertyAgreement.partbSigningPersonTel}"))){
			      phoneChecky= false; 
			}else{
				phoneChecky=true;
			}
		}else{
			phoneChecky=true;
		}
        v1 = new eoms.form.Validation({form:'pnrPropertyAgreementForm'});
        v1.custom = function() {
        	if(!phoneCheckj){
        		alert("甲方签订人联系电话不是完整的11位手机号或者正确的手机号前七位");
        		return false;
        	}
        	if(!phoneChecky){
        		alert("乙方签订人联系电话不是完整的11位手机号或者正确的手机号前七位");
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
	
	function saveImport() {
	//表单验证
		if(!v2.check()) {
			return;
		}
		new Ext.form.BasicForm('importForm').submit({
		method : 'post',
			url : "${app}/partner/property/agreement.do?method=importData",
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
	 	var handler = document.getElementById("openQuery");
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
	 
	 function checkPhone(aa){ 
		    var jMobile =  document.getElementById("partSigningPersonTelJ").value;
		    var yMobile =  document.getElementById("partSigningPersonTelY").value;
		    var sMobile="";
		    if(aa=="1"){
		    	sMobile=jMobile;
		    	if(sMobile==""){
		    		phoneCheckj=true;
		    		return;
		    	}
		    	if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(sMobile))){
			        alert("不是完整的11位手机号或者正确的手机号前七位"); 
			        phoneCheckj= false; 
		    	}else{
		    		phoneCheckj=true;
		    	}
		    }else{
		    	sMobile=yMobile;
		    	if(sMobile==""){
		    		phoneChecky=true;
		    		return;
		    	}
		    	if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(sMobile))){
			        alert("不是完整的11位手机号或者正确的手机号前七位"); 
			        phoneChecky= false; 
		    	}else{
		    		phoneChecky=true;
		    	}
		    }
		} 
</script>
<div align="center"><b>物业合同-增加页面</div><br><br/>

<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer"	>从Excel导入</span>
</div>

<div id="listQueryObject"		style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="agreement.do?method=importData" method="post" 	enctype="multipart/form-data" id="importForm" name="importForm">
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
										onclick="javascript:location.href='${app}/partner/property/agreement.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/>
<form action="${app}/partner/property/agreement.do?method=savePnrPropertyAgreement" method="post"
 id="pnrPropertyAgreementForm" name="pnrPropertyAgreementForm" >
	<!-- 隐藏域 begin -->
	<input type="hidden" id="id" name="id" value="${pnrPropertyAgreement.id}" />
	<input type="hidden" id="createTime" name="createTime" value="${pnrPropertyAgreement.createTime}" />
	<!-- 隐藏域 end -->
	<table id="sheet" class="formTable">
					<tr>
						<td class="label">
						 	合同编码<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="agreementNo" name="agreementNo" 
								value="${pnrPropertyAgreement.agreementNo}" alt="allowBlank:false"/>
						</td>
						<td class="label">
						 	合同名称<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="agreementName" name="agreementName" 
								value="${pnrPropertyAgreement.agreementName}" alt="allowBlank:false"/>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	合同类型<span class="redStar">*</span>
						</td>
						<td class="content" >
								<eoms:comboBox id="agreementType" name="agreementType" initDicId="12501" 
								defaultValue="${pnrPropertyAgreement.agreementType}"  alt="allowBlank:false" styleClass="input select"   />
						</td>
						<td class="label">
						 	付款周期<span class="redStar">*</span>
						</td>
						<td class="content" >
								<eoms:comboBox id="payCycle" name="payCycle" initDicId="12502" 
								defaultValue="${pnrPropertyAgreement.payCycle}" styleClass="input select"  alt="allowBlank:false" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	所属站点<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="site" name="site" value="${pnrPropertyAgreement.site}" alt="allowBlank:false"/>
						</td>
						
						<td class="label">
						 	所属行政区域<span class="redStar">*</span>
						</td>
						<td class="content" >
								<c:set var="boxData">[{id:'${pnrPropertyAgreement.distirct}',name:'<eoms:id2nameDB id="${pnrPropertyAgreement.distirct}" beanId="tawSystemAreaDao"/>'}]</c:set>
								<input type="text" name="textReviewer_10" id="textReviewer_10" class="text"  alt="allowBlank:false" readonly="readonly" />
								<input type="button" name="treeBtn_10" id="treeBtn_10" value="请选择所属行政区域!" class="btn" />
						  		<input type="hidden" name="distirct" id="distirct"/>
						  		<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
									rootText='所属区域' valueField="distirct" handler="treeBtn_10" textField="textReviewer_10"
									checktype="" single="true" data="${boxData}"></eoms:xbox>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	签订日期<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input type="text" id="signDate" name="signDate" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1);"  readonly="true" 
								value="<fmt:formatDate value='${pnrPropertyAgreement.signDate}' pattern='yyyy-MM-dd' />"
								alt="allowBlank:false,vtype:'lessThen',link:'endDate',vtext:'签订日期必须小于截止日期'" />
						</td>
						<td class="label">
						 	截止日期<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input type="text" id="endDate" name="endDate" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,false,-1);"  readonly="true" alt="allowBlank:false,vtype:'moreThen',link:'signDate',vtext:'截止日期必须大于签订日期'" 
								value="<fmt:formatDate value='${pnrPropertyAgreement.endDate}' pattern='yyyy-MM-dd' />" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	甲方
						</td>
						<td class="content" >
								<input class="text" type="text" id="parta" name="parta" value="${pnrPropertyAgreement.parta}"/>
						</td>
						<td class="label">
						 	乙方<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="partb" name="partb" value="${pnrPropertyAgreement.partb}" alt="allowBlank:false"/>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	甲方签订人
						</td>
						<td class="content" >
								<input class="text" type="text" id="partaSigningPerson" name="partaSigningPerson" 
								value="${pnrPropertyAgreement.partaSigningPerson}" />
						</td>
						<td class="label">
						 	乙方签订人
						</td>
						<td class="content" >
								<input class="text" type="text" id="partbSigningPerson" name="partbSigningPerson"
								 value="${pnrPropertyAgreement.partbSigningPerson}"/>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	甲方签订人电话
						</td>
						<td class="content" >
								<input class="text" type="text" id="partSigningPersonTelJ" name="partaSigningPersonTel" 
								value="${pnrPropertyAgreement.partaSigningPersonTel}" onblur="checkPhone('1');" />
						</td>
						<td class="label">
						 	乙方签订人电话
						</td>
						<td class="content" >
								<input class="text" type="text" id="partSigningPersonTelY" name="partbSigningPersonTel" 
								value="${pnrPropertyAgreement.partbSigningPersonTel}"  onblur="checkPhone('2');" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	合同金额（单位：元）<span class="redStar">*</span>
						</td>
						<td class="content" colspan="3" >
								<input class="text" type="text" id="agreementAmount" name="agreementAmount" value="${pnrPropertyAgreement.agreementAmount}" 
								alt="allowBlank:true,vtext:'',vtype:'float'"/>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	到期提醒对象<span class="redStar">*</span>
						</td>
						<td class="content" colspan="3" >
							<input class="text" type="text" id="dataText" name="dataText"  alt="allowBlank:false" readonly="readonly"/>
							<input type="button" name="userButton" id="userButton" value="选择提醒对象" class="btn"  alt="allowBlank:false" />
							<input type="hidden" name="remindObject" id="remindObject" />
								<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" rootId="1"	rootText='用户树' valueField="remindObject"
								 handler="userButton"	textField="dataText" checktype="user" single="false" data="${sendUserAndRoles}"></eoms:xbox>
							<input onclick="btnAdd(this)" type="button" style="width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/add.png');background-repeat: no-repeat;"  value="" />
						</td>
					</tr>
					<c:forEach var="phone" items="${phonesList}">
					<tr>
						<td class="label">提醒对象号码</td>
						<td class="content" colspan="3" >
							<input type="text" name="remindObject" id="remindObject" value="${phone}" class="text"/>
							<input onclick="btnAdd(this)" type="button" style="width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/add.png');background-repeat: no-repeat;"  value="" />
							<input onclick="btnDelete(this)" type="button" style="width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/list-delete.gif');background-repeat: no-repeat;"  value="" />
						</td>
					</tr>
					</c:forEach>
					<tr>
						<td class="label">
						 	附件列表
						</td>
						<td class="content" colspan="3" >
								 <eoms:attachment name="pnrPropertyAgreement" property="attachment" 
		         				   scope="request" idField="attachment" appCode="contract" alt="allowBlank:true"/> 	
						</td>
					</tr>
					<tr>
						<td class="label">
						 	备注
						</td>
						<td class="content"colspan="3">
								<textarea class="textarea max"  name="remark" id="remark" value="${pnrPropertyAgreement.remark}">${pnrPropertyAgreement.remark}</textarea>
						</td>
					</tr>
		</table>
		<input  type="submit" class="btn"  value="保存" />
		<input  type="button" class="btn"  value="重置"  onclick="res();" />
</form>


<%@ include file="/common/footer_eoms.jsp"%>