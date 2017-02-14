<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*,com.boco.eoms.base.util.StaticMethod;"%>
<%
String photoName = StaticMethod.nullObject2String(request.getAttribute("photoName"));

String defaultCarPic = "images/pnr_thumbnail/man.gif";

String defaultImgDriving = "images/pnr_thumbnail/man.gif";

String basePath="images/pnr_thumbnail/photo/zoom/";


 %>
 
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	//v = new eoms.form.Validation({form:'tawPartnerCarForm'});
});

function checkPiece(){
    var piece = document.getElementsByName("arrayPiece");
    for(var i=0;i<piece.length;i++){
        if(piece[i].checked == true){
           return true;
        }
    }   
    return false;
}

function checkIsNull(){
    if(document.getElementById("car_number").value==null||document.getElementById("car_number").value.trim()==''){
        alert("车牌号不能为空！");
        return false;
    }
    if(document.getElementById("start_time").value==null||document.getElementById("start_time").value.trim()==''){
        alert("开始使用时间不能为空！");
        return false;
    }
	if(document.getElementById("start_time").value<document.getElementById("carOutDate").value){
	alert("开始时间不能小于车辆出厂时间");
    return false;
    }
	if(document.getElementById("annualCheckData").value<document.getElementById("start_time").value){
	alert("年检时间不能小于开始时间");
    return false;
    }
	if(document.getElementById("endTime").value<document.getElementById("start_time").value){
	alert("开始时间不能小于车辆出厂时间");
    return false;
    }
	if(document.getElementById("purchaseTime").value<document.getElementById("carOutDate").value){
	alert("购买时间不能小于车辆出厂时间");
    return false;
    }
	 //缩略图为必填项 为空时提示
	 //var imgphoto=document.getElementById("imgphoto").src;
	 //var imgdriving=document.getElementById("imgdriving").src;
	 
	 //var imgphotoIsNull= imgphoto.indexOf("images/pnr_thumbnail/man.gif");
	 //var imgdrivingIsNull= imgdriving.indexOf("images/pnr_thumbnail/man.gif");
	 //if(imgphotoIsNull!=-1){
	 //	alert("请上传车辆缩略图！");
	// 	return false;
	// }
	// if(imgdrivingIsNull!=-1){
	 //	alert("请上传行驶证缩略图！");
	 //	return false;
	// }
    
    return true;
}
function submitForm(){
	var provvalue=document.getElementById("prov");
	var index = provvalue.selectedIndex
	var text = provvalue.options[index].text;
	document.getElementById("provvalue").value=provvalue.options[index].value;
	document.getElementById("company").value=text;
    if(checkIsNull()){
      document.forms[1].submit();
    }
    else {
       return false;
   }
    
}
	function openThumbnail(){
		 window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=1&formNo=1&picIDImage=imgphoto&hdId=thumbnail');
		 //window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=1&formNo=1&thumbnail=thumbnail&imgphoto=imgphoto&picIDImage=imgphoto');
	}
	function openThumbnailDriving(){
		 window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=2&formNo=2&picIDImage=imgdriving&hdId=drivingLicenseThumbnail');
	}
function selCarForm(){
	var sel2=document.getElementById("selcarform").value;
	document.getElementById("carFormHd").value=sel2;
	alert(document.getElementById("carFormHd").value);
}
function selCarBelongCompanyHd(){
	var sel=document.getElementById("carBelongCompany").value;
	document.getElementById("carBelongCompanyHd").value=sel
}
function selCarState(){
	var sel=document.getElementById("selCarStateId").value;
	document.getElementById("carStateHd").value=sel;
}
function selIsPrepare(){
	var sel=document.getElementById("selIsPrepareId").value;
	document.getElementById("isPrepareHd").value=sel;
}
function query(){
	location.href="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerCars.do?method=search&in=factory&nodeId=1010101";
}
function back(){
	location.href="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerCars.do?method=search&in=factory&nodeId=1010101";
}
function getselcompany(){
	var provvalue=document.getElementById("prov");
	var index = provvalue.selectedIndex
	var text = provvalue.options[index].text;
	document.getElementById("provvalue").value=provvalue.options[index].value;
	document.getElementById("company").value=text;
}
  function getselcompany1(){
	var provvalue=document.getElementById("prov1");
	var text = provvalue.options[provvalue.selectedIndex].text;
	document.getElementById("partneridXls").value=provvalue.options[provvalue.selectedIndex].value;
	var interfaceHeadId=document.getElementById("interfaceHeadId");
	document.getElementById("formInterfaceHeadIdXls").value=interfaceHeadId.value;
}
function openImport(handler){
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
//xls批量导入
function submitvalue(){
if(document.getElementById("partneridXls").value==null||document.getElementById("partneridXls").value==""){
		alert("请选择公司");
		return false;
    }
document.forms[0].submit();
}
</script>
<!-- xls导入部分 -->
<table class="formTable">
	<caption>
		车辆批量录入
	</caption>
</table>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="/eoms/images/icons/layout_add.png"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
</div>
 
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">

<html:form action="/tawPartnerCars.do?method=xlsSave"
	method="post" styleId="tawPartnerCarForm"
	enctype="multipart/form-data" onsubmit="return checkForm()">
	
	<table border=0 cellspacing="1" cellpadding="1" class="listTable">
		<!--附加表以及XML文件基本属性表格：开始-->
		<tr class="tr_show"><td  COLSPAN="3" class="label">请选择公司</td><td><eoms:pnrComp name="prov1" id="prov1" onchange="getselcompany1()"/></td></tr>
		<tr class="tr_show">
			<td COLSPAN="3" class="label">
				车辆表上传
			</td>
			<td COLSPAN="14">
				<input name="accessoryName" type="file"
					class="file" /><font color="red">请选择上传xls格式的文件</font>
			</td>
		</tr>
<tr>
<td>

<font color="red">${problemRow }</font>

</td>
</tr>
		<tr>
			<td COLSPAN="17">
				<input type="button"
					value="提交"
					class="submit" Onclick="submitvalue()">
					&nbsp;&nbsp;&nbsp;
&nbsp; <input type="button" class="button" name="save" value="下载导入模板" Onclick="window.location.href ='${app }/partner/baseinfo/tawPartnerCars.do?method=outPutModel'" >
&nbsp;
			</td>
		</tr>
	</table>
	<tr><td><input type="hidden" name="partneridXls" id="partneridXls" value=""/>
		<input type="hidden" name="formInterfaceHeadIdXls" id="formInterfaceHeadIdxls" value=""/></td></tr>
</html:form>
</div>




<!-- xls导入部分 -->




<html:form action="/tawPartnerCars.do?method=save" styleId="tawPartnerCarForm" method="post"  > 

<!--  <tr><td><input type="button" value="返回" onclick="back()"/></td></tr>-->

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawPartnerCar.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
		<!--车牌号 *  -->
			<fmt:message key="tawPartnerCar.car_number" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="car_number" styleId="car_number"
						styleClass="text medium" onblur="testCharSize(this,64);"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.car_number}" />${fallure }
		</td>
		<!-- 所属公司 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.dept_id" />&nbsp;*
		</td>
		<td class="content">
		<eoms:pnrComp name="prov" id="prov" onchange="getselcompany()" defaultValue="${tawPartnerCarForm.partnerid}"/>
		<input type="hidden" name="provvalue" id="provvalue" value=""/>
		<input type="hidden" name="company" id="company" value=""/>
			 </td>
	</tr>
	<tr><td class="label">车型&nbsp;*</td><td>
<eoms:comboBox name="carForm" id="carForm" initDicId="1121701" defaultValue="${tawPartnerCarForm.carForm}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>

	</td>
	<!-- 用途 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.use" />&nbsp;*
		</td>
		<td class="content">
		<eoms:comboBox name="use" id="use" initDicId="1121306" defaultValue="${tawPartnerCarForm.use}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>
	</tr>	
	<tr>
	<td class="label">
			驱动类型&nbsp;*
		</td>
		<td class="content">
			<eoms:comboBox name="driveType" id="driveType" initDicId="11215" defaultValue="${tawPartnerCarForm.driveType}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>
		<td class="label">出厂日期</td>
		<td>
	<input type="text" class="text" name="carOutDate" readonly="readonly" 
					id="carOutDate" 
					value="${tawPartnerCarForm.carOutDate}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)" 
					 alt="vtype:'lessThen',link:'start_time',vtext:'受理时限不能晚于处理时限',allowBlank:false"
					/> 
				</td>
	</tr>	
	
	<tr>
				<td class="label">
			<!-- 开始使用时间  -->
			<fmt:message key="tawPartnerCar.start_time" />
		</td>
		<td class="content">
			 
					<input type="text" class="text" name=start_time readonly="readonly" 
					id="start_time" 
					value="${tawPartnerCarForm.start_time}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)" 
					alt="vtype:'moreThen',link:'carOutDate',vtext:'完成时限不能早于受理时限',allowBlank:false"
					/> 
		</td>
		
<td class="label">
			<!-- 停止使用时间  -->
			<fmt:message key="tawPartnerCar.endTime" />
		</td>
		<td class="content">
		
		<input type="text" class="text" name="endTime" readonly="readonly" 
					id="endTime" 
					value="${tawPartnerCarForm.endTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)" 
					alt="vtype:'lessThen',link:'start_time',vtext:'请选择停止使用时间',allowBlank:false"
					/> 
		</td>
	</tr>
	<tr>
	<!-- 年检日期 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.annualCheckData" />&nbsp;*
		</td><td>
				<input type="text" class="text" name=annualCheckData readonly="readonly" 
					id="annualCheckData" 
					value="${tawPartnerCarForm.annualCheckData}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)" 
					alt="vtype:'lessThen',link:'endTime',vtext:'请选择年检时间',allowBlank:false"
					/> 
		</td>
		<td class="label">
			购买时间
		</td>
		<td class="content">
						<input type="text" class="text" name=purchaseTime readonly="readonly" 
					id="purchaseTime" 
					value="${tawPartnerCarForm.purchaseTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)" 
					alt="vtype:'lessThen',link:'start_time',vtext:'请选择购买时间',allowBlank:false"
					/> 
			</td>
	</tr>
	

	
	<!-- 以下是新增字段 -->
	<tr>
		<!-- 车辆品牌 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.brand" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="brand" styleId="brand"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.brand}"/>
		</td>
		<td class="label">
			服务类型&nbsp;*
		</td>
		<td class="content">
			<eoms:comboBox name="category" id="category" initDicId="1121311" defaultValue="${tawPartnerCarForm.category}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>
	</tr>

	<tr>
	<!-- 行驶证号 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.drivingLicense" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="drivingLicense" styleId="drivingLicense"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.drivingLicense}"/>
		</td>
		<!-- 生产厂家 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.factory" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="factory" styleId="factory"
						styleClass="text medium"   style="width:45%;" 
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.factory}"/>
		</td>
	</tr>
	
	<tr>
		<!-- 发动机号 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.engineNo" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="engineNo" styleId="engineNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.engineNo}"/>
		</td>

		<!-- 排气量 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.airDisplacement" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="airDisplacement" styleId="airDisplacement"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.airDisplacement}"/>
		</td>
	</tr>


	
	<tr>
		<!-- 维护专业 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.serviceProfessional" />&nbsp;*
		</td>
		<td class="content">
		<eoms:comboBox name="serviceProfessional" id="serviceProfessional" initDicId="1121201" defaultValue="${tawPartnerCarForm.serviceProfessional}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>

		<!-- 开始使用里程数 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.startUseMilemeter" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="startUseMilemeter" styleId="startUseMilemeter"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.startUseMilemeter}"/>
		</td>
	</tr>
	<tr><td class="label">车辆状态</td>
	<td>
	<eoms:comboBox name="carState" id="carState" initDicId="1121702" defaultValue="${tawPartnerCarForm.carState}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
	</td>	
	<td class="label">是否可调配</td><td>
	<eoms:comboBox name="isPrepare" id="isPrepare" initDicId="1121703" defaultValue="${tawPartnerCarForm.isPrepare}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/></td>
	</tr>
	<tr>
		<!-- 所有性质 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.ownershipType" />&nbsp;*
		</td>
		<td class="content" colspan="3">
		<eoms:comboBox name="ownershipType" id="ownershipType" initDicId="1121307" defaultValue="${tawPartnerCarForm.ownershipType}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>
	</tr>
	<tr style="display: none"><td class="label" style="display: none">
			<!-- 所属地市 -->
			<fmt:message key="tawPartnerCar.area_id" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="area_id" styleId="area_id"
						 styleClass="text medium" style="width:10"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.area_id}" />
		</td></tr>
	<tr>	
		<logic:notEmpty name="tawPartnerCarForm" property="addMan">
	
				<td class="label">
					添加人姓名
				</td>
				<td class="content">
					<eoms:id2nameDB id="${tawPartnerCarForm.addMan}"
						beanId="tawSystemUserDao" />&nbsp;&nbsp;
						<bean:write name="tawPartnerCarForm" property="connectType" />
				</td>
				<td class="label">
					添加时间
				</td>
				<td class="content">
					<bean:write name="tawPartnerCarForm" property="addTime" />

				</td>
			
			</logic:notEmpty>
	</tr>
	<tr>
		
		<td class="label">
			<fmt:message key="tawPartnerCar.remark" />
		</td>
		<td class="content" colspan="3">
		<html:textarea property="remark" styleId="remark" onblur="testCharSize(this,256);"
						styleClass="text max" rows="3" cols="60"
						value="${tawPartnerCarForm.remark}" />
		</td>
	</tr>		
	<tr>	
		<!-- 车辆缩略图 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.thumbnail" />&nbsp;*
		</td>

		<td>
			<div id='pic' class="photocontainer">
				<c:if test="${empty tawPartnerCarForm.id}">
					<img id="imgphoto" src="${app}/<%=defaultCarPic%>" style="border-width: 0px;" />
					
				</c:if>
				<c:if test="${not empty tawPartnerCarForm.id}">
					<c:choose>
					   <c:when test="${not empty tawPartnerCarForm.thumbnail}">
					   		<img id="imgphoto" src="${tawPartnerCarForm.thumbnail}" style="border-width: 0px;" width="250" height="180"/>
					   </c:when>
					   <c:otherwise>
					   		<img id="imgphoto" src="${app}/<%=defaultCarPic%>" style="border-width: 0px;"/>
					   </c:otherwise>
					</c:choose>
					
				</c:if>
			</div>
			<input id="thumbnail" type="hidden" name="thumbnail" value="${tawPartnerCarForm.thumbnail}"/><br>
			<input type="button" name="changePicThumbnail" id="changePicThumbnail" value="选择图片" class="btn" onclick="openThumbnail()"/>			
			
		</td>

		
		<!-- 行驶证缩略图 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.drivingLicenseThumbnail" />&nbsp;*
		</td>
		<td class="content" colspan=3>
			
			<div id='picDriving' class="photocontainer">
				<c:if test="${empty tawPartnerCarForm.id}">
					<img id="imgdriving" src="${app}/<%=defaultImgDriving%>" style="border-width: 0px;" />
				</c:if>
				<c:if test="${not empty tawPartnerCarForm.id}">

					<c:choose>
					   <c:when test="${not empty tawPartnerCarForm.drivingLicenseThumbnail}">
					   		<img id="imgdriving" src="${tawPartnerCarForm.drivingLicenseThumbnail}" style="border-width: 0px;" width="250" height="180"/>
					   </c:when>
					   <c:otherwise>
					   		<img id="imgdriving" src="${app}/<%=defaultCarPic%>" style="border-width: 0px;"/>
					   </c:otherwise>
					</c:choose>
				
				
				
				</c:if>
			</div>

			<input id="drivingLicenseThumbnail" type="hidden" name="drivingLicenseThumbnail" value="${tawPartnerCarForm.drivingLicenseThumbnail}"/><br>
			<input type="button" name="changePicDriving" id="changePicDriving" value="选择图片" class="btn" onclick="openThumbnailDriving()"/>			
						
		</td>
	</tr>
		
			<html:hidden property="dimensionalCode" value="${tawPartnerCarForm.dimensionalCode}" />


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
		<!-- 保存 -->
			<input type="button" id="btn1" class="btn" value="<fmt:message key="button.save"/>" onclick="submitForm();"/>
			<c:if test="${not empty tawPartnerCarForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确认删除?')){
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerCars.do?method=remove&id=${tawPartnerCarForm.id}';
						location.href=url}"	/>
			</c:if>
			<!-- <input type="button" class="btn" value="批量导入"
						onclick="javascript:{
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerCars.do?method=toXls';
						location.href=url}" /> -->&emsp;&emsp;
						<td><input class="btn" type="button" value="返回" onclick="query()"/></td>
	</tr>
</table>
<html:hidden property="id" value="${tawPartnerCarForm.id}" />
 </html:form>

<script type="text/javascript">
function getResponseText(url) {
    var xmlhttp;
    if(eoms.isIE){
    	try{
    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); 
    	}catch(e){
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");    		
    	}
    }else{
    	xmlhttp = new XMLHttpRequest();
    }
    var method = "post";
    url=url+"&"+new Date();
    xmlhttp.open(method, url, false);
    xmlhttp.setRequestHeader("content-type", "text/html; charset=GBK");
    xmlhttp.send(null);
    return xmlhttp.responseText;
}

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
        
function createRequest(){
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}
  </script>
<%@ include file="/common/footer_eoms.jsp"%>