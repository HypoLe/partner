<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script language="JavaScript" type="text/JavaScript"	src="${app}/scripts/module/partner/ajax.js"></script>
<script language="JavaScript" type="text/JavaScript"	src="${app}/scripts/module/partner/util.js"></script>
<script language="JavaScript" type="text/JavaScript"	src="${app}/scripts/module/partner/personCard.js"></script>

<script language="javascript" for="document" event="onkeydown">
    if (event.keyCode == 13)
    {
        isEmail(document.getElementById('email').value);
    }  
</script>
<script type="text/javascript">
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
</script>

<div align="center"><b>人员信息管理-人员信息编辑</div><br><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer">从Excel导入</span>
</div>
<div id="listQueryObject"		style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="charge.do?method=importRecord" method="post" 	enctype="multipart/form-data" id="importForm" name="importForm">
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
								onclick="javascript:location.href='${app}/partner/baseinfo/partnerUsers.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/>
<html:form action="/partnerUsers.do?method=saveCompanyUser&isPartner=${isPartner}" styleId="partnerUserForm" method="post">
	<fmt:bundle		basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
		<html:hidden property="treeNodeId" />
		<input type="hidden" value="${param.pnrType}" name="pnrType" />
		<!-- 保存类型（是否是技能认证人力信息添加） -->
		<table class="formTable">
			<caption>
				<div class="header center">
					<fmt:message key="partnerUser.form.heading" />
				</div>
			</caption>
			<tr>
				<td class="label">
					<fmt:message key="partnerUser.name" />
					&nbsp;*
				</td>
				<td class="content">
					<html:text property="name" styleId="name" styleClass="text medium"
						onblur="testCharSize(this,255);" alt="allowBlank:false,vtext:''"
						value="${partnerUserForm.name}" />
				</td>
				<c:if test="${empty partnerUserForm.name}">
					<td class="label"  rowspan="3" valign="middle">
						<fmt:message key="partnerUser.photo" />
					</td>
					<html:hidden property="picName" value="" />
					<td rowspan="3" valign="bottom">
				</c:if>
				<c:if test="${!empty partnerUserForm.name}">
					<td class="label"  rowspan="4" valign="middle">
						<fmt:message key="partnerUser.photo" />
					</td>
					<html:hidden property="picName" value="" />
					<td rowspan="4" valign="bottom">
				</c:if>
					<input type="hidden" name="photo" id="photo" value="" />
					<html:hidden property="accessory" styleId="accessory"
						value="${partnerUserForm.accessory}" />
					<c:if test="${partnerUserForm.photo!=null}">
						<!-- 修改时 -->
						<div id="imgdiv">
							<img id="imgUser" name="imgUser" src="${partnerUserForm.photo }"
								border="0" width="130" height="180">
							<br>
							<input type="button" name="selectPicUser" id="selectPicUser"
								value="选择图片" class="btn" onclick="openUserPhotoPage()" />
							<!-- <br>
			      <input type="button" name="delPhoto" id="delPhoto" value="删除" class="btn" onclick="if(confirm('确定删除电子照片吗？'))deletePhoto();">
			   -->
						</div>
					</c:if>

					<c:if test="${partnerUserForm.photo==null}">
						<!-- 新建时 -->
						<div id="imgdiv" style="display: none">
							<!-- 
			      <input id="thumbnail" type="hidden" name="thumbnail" value=""/>
			    
			      <input type="button" name="delPhoto" id="delPhoto" value="删除" class="btn" onclick="if(confirm('确定删除电子照片吗？'))javascript:deletePhoto();" >
			   -->
						</div>
						<img id="imgUser" name="imgUser"
							src="${app }/images/pnr_thumbnail/man.gif"
							style="border-width: 0px;">
						<br>
						<input type="button" name="changePicUser" id="changePicUser"
							value="编辑图片" class="btn" onclick="openUserPhotoPage()" />
					</c:if>
				</td>
			</tr>
			<c:if test="${!empty partnerUserForm.userId}">
				<tr>
					<td class="label">人员ID
					</td>
					<td class="content">
						${partnerUserForm.userId}
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="label">
					身份证号码&nbsp;
				</td>
				<td class="content">
					<html:text property="personCardNo" styleId="personCardNo"
						styleClass="text max" alt="allowBlank:true,vtext:''"
						value="${partnerUserForm.personCardNo}" />
					<div id="showCardNo"></div>
				</td>
			</tr>
			<tr>
				<td class="label">
					所属组织&nbsp;*
				</td>
				<td class="content">
					<input type="text" class="text max" name="provName" id="provName"
						value="<eoms:id2nameDB id="${partnerUserForm.partnerid}" beanId="partnerDeptDao"/>"
						alt="allowBlank:false" readonly="readonly" />
					<input name="prov" id="prov" value="${partnerUserForm.partnerid}" type="hidden" />
					<input name="oldProv" id="oldProv" value="${partnerUserForm.partnerid}" type="hidden" />
					<eoms:xbox id="provTree"
						dataUrl="${app}/xtree.do?method=getPnrDeptWithRight&isPartner=${isPartner}"
						rootId="" rootText="公司树" valueField="prov" handler="provName"
						textField="provName" checktype="dept" single="true" />
				</td>
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="partnerUser.mobilePhone" />
					&nbsp;*
				</td>
				<td class="content">
				<span>若多个电话号码，请用英文状态下的";"分隔</span><br>
					<html:text property="mobilePhone" styleId="mobilePhone"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''"
						value="${partnerUserForm.mobilePhone}" />
				</td>
				<td class="label">
					Email	&nbsp;
				</td>
				<td class="content">
					<html:text property="email" styleId="email" onblur="isEmail(this.value);testCharSize(this,255);"
						styleClass="text medium" 	value="${partnerUserForm.email}" />
				</td>
			</tr>
			<tr>
				<td class="label">
					性别&nbsp;*
				</td>
				<td class="content">			
				<eoms:comboBox name="sex" id="sex" initDicId="1120202"
						defaultValue='${partnerUserForm.sex}'	alt="allowBlank:false,vtext:''" styleClass="select"/>
				</td>
				
				<td class="label">
					<fmt:message key="partnerUser.birthdey" />
					&nbsp;*
				</td>
				<td class="content">
					<html:text property="birthdey" styleId="birthdey" readonly="true" styleClass="text medium"
						onclick="popUpCalendar(this, this,null,null,null,false,-1);" value="${partnerUserForm.birthdey}"
						alt="allowBlank:false,vtype:'lessThen',link:'workTime',vtext:'出生时间应早于工作时间'" />
				</td>
			</tr>
			<tr>
				<td class="label">
				学历情况&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="diploma" id="diploma" initDicId="12405"
						defaultValue='${partnerUserForm.diploma}'	alt="allowBlank:false,vtext:''" styleClass="select"/>
				</td>

				<td class="label">
					参加工作时间&nbsp;
				</td>
				<td class="content">
					<html:text property="workTime" styleId="workTime" readonly="true"		styleClass="text medium"
						onclick="popUpCalendar(this, this,null,null,null,false,-1);" value="${partnerUserForm.workTime}"
						alt="allowBlank:true,vtype:'moreThen',link:'birthdey',vtext:'工作时间应晚于出生时间'" />
				</td>
			</tr>
			<tr>
				<td class="label">
					籍贯&nbsp;*
				</td>
				<td class="content" id="nativePlace">
					<input id="nativePlace" name="nativePlace" type="text" class="text" alt="allowBlank:false,vtext:''" value="${partnerUserForm.nativePlace}">
				</td>
				<td class="label">
					民族&nbsp;
				</td>
				<td class="content">
						<eoms:comboBox  name="nationality" id="nationality" 	initDicId="12408" 	defaultValue="${partnerUserForm.nationality}"  styleClass="input select"  />
				</td>
			</tr>
			<tr>
				<td class="label">
					年龄&nbsp;*
				</td>
				<td class="content" >
					<input id="age" name="age" type="text" class="text" value="${partnerUserForm.age}" onkeypress="IsNum(event)">
					
				</td>
				<td class="label">
					集团短号&nbsp;
				</td>
				<td class="content">
					<html:text property="groupPhone" styleId="groupPhone" 	styleClass="text medium" value="${partnerUserForm.groupPhone}" />
				</td>
			</tr>
			<tr>
				<td class="label">
					经度&nbsp;
				</td>
				<td class="content">
					<input type="text" name="longtitude"   id="longtitude"   class="text" value="${partnerUserForm.longtitude}">
				</td>
				<td class="label">
					纬度&nbsp;
				</td>
				<td class="content">
					<input type="text" name="latitude"    id="latitude"    class="text" value="${partnerUserForm.latitude}">
				</td>
			</tr>
			<tr>
				<td class="label">
					毕业院校&nbsp;
				</td>
				<td class="content">
					<html:text property="graduateSchool" styleId="graduateSchool"
						styleClass="text medium" value="${partnerUserForm.graduateSchool}"/>
				</td>
				<td class="label">
					所学专业&nbsp;
				</td>
				<td class="content">
					<html:text property="learnSpecialty" styleId="learnSpecialty"
						styleClass="text medium" value="${partnerUserForm.learnSpecialty}"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					在职状态&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="postState" id="postState" initDicId="12409" defaultValue="${partnerUserForm.postState}"
					 styleClass="select" onchange="getpostState(this.options[this.options.selectedIndex].value);" alt="allowBlank:false">
					 </eoms:comboBox>
				</td>
				<td class="label">
						黑名单标识&nbsp;*
					</td>
					<td class="content">
						<select id="blacklist" name="blacklist" alt="allowBlank:false" class="select">
							<option selected="selected" value='0' />
							否
							</option>
							<option value='1'>
								是
							</option>
						</select>
					</td>
					</tr>
				<tr style="display: none" id="reasonTr">
					<td class="label">
						离职时间&nbsp;*
					</td>
					<td class="content"  >
						<html:text property="leavaTime" styleId="leavaTime" readonly="true"		styleClass="text medium"
						onclick="popUpCalendar(this, this,null,null,null,false,-1);" value="${partnerUserForm.leavaTime}"
						alt="allowBlank:false',vtext:'工作时间应晚于出生时间'" />
					</td>
					<td class="label">
						离职原因&nbsp;*
					</td>
					<td class="content"  >
						<input class="text" type="text" name="leavaReason" id="leavaReason"  value="${partnerUserForm.leavaReason}" 
						alt="vtext:'离职原因不能为空！',allowBlank:false" />
					</td>
			</tr>
			<tr>
				<td class="label">
					密码&nbsp;*
				</td>
				<td class="content" colspan="3">
					<c:if test="${!empty partnerUserForm.userId}">
						<span>密码(大于等于8位并有大写字母、小写字母、数字及特殊字符的组合)</span><br>
						<input type="password" name="userPassword" id="userPassword" value="${partnerUserForm.userPassword}" onblur="checkUserPassWord();" class="text" alt="allowBlank:false"/>
						<input type="hidden" name="oldUserPassword" id="oldUserPassword" value="${partnerUserForm.userPassword}"/>
						<span style="color:red" id="passwordError"></span>
					</c:if>
					<c:if test="${empty partnerUserForm.userId}">
						<input type="password" name="userPassword" id="userPassword" 
						value="${partnerUserForm.userPassword}"  readonly="true" class="text"/>
						<span style="color:red">默认初始密码为Boco42@#</span>
					
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="label">
					身份证附件
				</td>
				<td class="content" colspan="3">
					<eoms:attachment idList="" idField="identificationAccessory"
						appCode="baseinfo" scope="request" name="partnerUserForm"
						property="identificationAccessory" />
				</td>
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="partnerUser.remark" />
				</td>
				<td class="content" colspan="3">
					<html:textarea property="remark" styleId="remark" styleClass="textarea max"
						value="${partnerUserForm.remark}" />
				</td>
			</tr>
		<input  name="userId"  id="userId" type="hidden" value="${partnerUserForm.userId}"> 
		<input  name="userNo"  id="userNo" type="hidden" value="${partnerUserForm.userNo}"> 
		</table>
	</fmt:bundle>
	<table>
		<tr>
			<td>
					<input type="submit" class="btn" value="保存" />
			</td>
		</tr>
	</table>
	<html:hidden property="id" value="${partnerUserForm.id}" />
</html:form>
<script type="text/javascript">

		function deletePhoto(){
			 var id = "${partnerUserForm.id}";
			 var url = null;
			 if(id!=null&&id!="")//编辑时
			    url="<%=request.getContextPath()%>/partner/baseinfo/partnerUsers.do?method=delPhoto&id="+id;
			 else {//新建时
			    var accessory = document.getElementById("accessory").value;
			    url="<%=request.getContextPath()%>/partner/baseinfo/partnerUsers.do?method=delPhoto&accessory="+accessory;
		}
		var info = getResponseText(url);
		if (info != null && info == "delIsSuccess") {
			document.getElementById("accessory").value = "";
			document.getElementById("photo").value = "";
			document.getElementById("imgdiv").style.display = "none";
			if (id != null && id != "") {//修改时
				document.getElementById("upframe").style.display = "block";
			}
			document.getElementById("upframe").style.height = "50pt";
			window.frames['upframe'].document.getElementById('div').style.display = "";
		}
	}
</script>
<script type="text/javascript">
	var myJ = jQuery.noConflict();
	var userIdResult;
	var validity=false;
	var cardNoResult=false;
	var oldidcard;
	var passwordV=false;
	//ajax校验密码
	function checkUserPassWord(){
		var password=document.getElementById("userPassword").value.trim();
		if(password==""){//为空时默认不修改，此时不发生校验
			passwordV=true;
			return true;
		}
		myJ.get("${app}/partner/baseinfo/partnerUsers.do?method=checkUserPassWord&password="+password,
			function(data){
				if(data=='false'){
					document.getElementById("passwordError").innerHTML="";
					document.getElementById("passwordError").innerHTML="密码格式不符合要求(大于等于8位并有大写字母、小写字母、数字及特殊字符的组合)";
					passwordV=false;
					return false;
				}else{
					document.getElementById("passwordError").innerHTML="";
					passwordV=true;
					return true;
				}
			});
		}
myJ(function() {
	
	
	myJ('input#userId').blur(function(){
		var userId = myJ('input#userId').val();
		if(userId != ''){
			myJ.get("${app}/partner/baseinfo/partnerUsers.do?method=checkUserIdUnique&userId="+userId,function(data){
				if(data=='false'){
					myJ('div#showUserId').html("<font color=red>用户ID已存在，请重新输入！</font>");
					userIdResult = false;
				}else{
					myJ('div#showUserId').html('');
					userIdResult = true;
				}
			});
		}else{
			myJ('div#showUserId').html('');
		}
	});
	
});
//验证细则见validation.js
Ext.onReady(function() {
 	passwordV=true;//加载时身份证校验通过标志
    var value='${partnerUserForm.postState}';
    if(value=="1240903"){
		eoms.form.enableArea("reasonTr");
	}else{
		eoms.form.disableArea("reasonTr");
	}
	v = new eoms.form.Validation({form:"partnerUserForm"});
	//页面自定义验证
	v.custom = function(){
			
		if(userIdResult==false){
			alert("用户ID已经存在"); 
			return false; 
		}
		var userid="${partnerUserForm.userId}";
		if(userid!=""){//修改页面才发生校验
			if(passwordV==false){
				alert("修改的密码不符合要求"); 
				return false; 
			}
		}
		var reg=/^(\d+)(\.\d{1,15})?$/;//验证经纬度是否符合要求;
		var longitude=document.getElementById("longtitude").value;//经度;
		var latitude=document.getElementById("latitude").value;//纬度;
		if(longitude!=""){
			if(longitude.match(reg)==null){
				alert("经度必须为数字");
				return false;
			}
		}
		if(latitude!=""){
			if(latitude.match(reg)==null){
				alert("纬度必须为数字");
				return false;
			}
		}
		return true; 
	}
	 v2 = new eoms.form.Validation({form:'importForm'});
	 v2.custom = function() {
			var reg = "(.xls)$";
			var file = myJ("#importFile").val();
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
		url : "${app}/partner/baseinfo/partnerUsers.do?method=importCompanyUser",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
		},
		failure : function(form, action) {
			alert(action.result.infor);
		}
    });
}

function getpostState(value){
	if(value=="1240903"){
		eoms.form.enableArea("reasonTr");
	}else{
		eoms.form.disableArea("reasonTr",true);
	}
}
function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
        
function isEmail(strEmail) {
	if(strEmail=="")
	return true;
	if (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
	return true;
	else{
		document.forms[0].email.value = '';
		alert("请检查邮箱格式！");
	}
}
function fucCheckNum()
   {
     var r,re;
           re = /^\d*\$/;
           var personCardNoValue = document.getElementById('personCardNo').value;
           r = personCardNoValue.match(re);
           	if(r!=personCardNoValue){   
           		personCardNoValue = '';        		
           		alert("只能输入数字！");	
           	};
    }
function checkNum(theInput,str){ 
  a=parseInt(theInput); 
    if (isNaN(a)) 
  { 
      alert(str+"请输入数字"); 
      return false;
  } 
  else 
  return true;
  } 		

function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas&type=addUser','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}

function openThumbnail(){
window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=1&formNo=1&picIDImage=imgUser&hdId=photo');
	//window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhotoJ&picNo=2&formNo=1');
}
function openUserPhotoPage(){
window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=1&formNo=1&picIDImage=imgUser&hdId=photo');
//	window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhotoJ&picID=imgUser&picNameID=picName');
}
function createRequest()
{
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}
	window.frameElement.height = 1000;  
	var isEdit = '${isEdit}';
	if(isEdit!='add'){
		document.body.style.overflow = "hidden";
	}  
function IsNum(e)
{ 
      var k = window.event ? e.keyCode:e.which; 
     if (((k >= 48) && (k <= 57)) || k==8 || k==0){ 
     }else{ 
         if(window.event) 
          { 
               window.event.returnValue = false; 
         } 
         else{ 
               e.preventDefault();//for firefox 
         } 
     } 

}      
</script>
<%@ include file="/common/footer_eoms.jsp"%>