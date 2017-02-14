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
    if(document.getElementById("area_id").value==null||document.getElementById("area_id").value.trim()==''){
        alert("所属地市不能为空！");
        return false;
    }
    if(document.getElementById("dept_id").value==null||document.getElementById("dept_id").value.trim()==''){
        alert("所属公司不能为空！");
        return false;
    }
    if(document.getElementById("start_time").value==null||document.getElementById("start_time").value.trim()==''){
        alert("开始使用时间不能为空！");
        return false;
    }
    
    
	 //缩略图为必填项 为空时提示
	 var imgphoto=document.getElementById("imgphoto").src;
	 var imgdriving=document.getElementById("imgdriving").src;
	 
	 var imgphotoIsNull= imgphoto.indexOf("images/pnr_thumbnail/man.gif");
	 var imgdrivingIsNull= imgdriving.indexOf("images/pnr_thumbnail/man.gif");
	 if(imgphotoIsNull!=-1){
	 	alert("请上传车辆缩略图！");
	 	return false;
	 }
	 if(imgdrivingIsNull!=-1){
	 	alert("请上传行驶证缩略图！");
	 	return false;
	 }
    
    
    return true;
}

function submitForm(){
    if(checkIsNull()){
      document.forms[0].submit();
    }
    else {
       return false;
    }
    
}

	function openThumbnail(){
		 window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=1');
	}

	function openThumbnailDriving(){
		 window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=2');
	}

</script>

<html:form action="/tawPartnerCars.do?method=save" styleId="tawPartnerCarForm" method="post"  > 


<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawPartnerCar.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerCar.car_number" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="car_number" styleId="car_number"
						styleClass="text medium" onblur="testCharSize(this,64);"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.car_number}" />${fallure }
		</td>

		<td class="label">
			<fmt:message key="tawPartnerCar.dept_id" />&nbsp;*
		</td>
		<td class="content">
				 <html:select disabled="true" property="dept_id" styleId="dept_id" size="1">
				 <html:hidden property="dept_id"/>
				 </html:select>
			<%--<html:text property="dept_id" styleId="dept_id"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.dept_id}" />
		--%></td>
	</tr>		
	
	<tr>


				<td class="label">
			<fmt:message key="tawPartnerCar.start_time" />
		</td>
		<td class="content">
		<html:text property="start_time"
						onclick="popUpCalendar(this, this,null,null,null,true,-1); " readonly="true"
						styleId="startTime" styleClass="text medium"
						alt="allowBlank:false,vtext:'请输入开始时间'" />
		
			<%--<html:text property="start_time" styleId="start_time"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.start_time}" />
		--%></td>
		<!-- 年检日期 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.annualCheckData" />&nbsp;*
		</td>
		<td class="content">
<!--  			<html:text property="annualCheckData" styleId="annualCheckData"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.annualCheckData}"/>
-->						
		<html:text property="annualCheckData"
						onclick="popUpCalendar(this, this,null,null,null,true,-1); " readonly="true"
						styleId="annualCheckData" styleClass="text medium"
						alt="allowBlank:false,vtext:'请输入年检日期'" />
						
		</td>


	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerCar.area_id" />&nbsp;*
		</td>
		<td class="content">
		 <html:select disabled="true" property="area_id" styleId="area_id" size="1" >
					 <%List listId =(List) request.getAttribute("listId");
					List listName = (List)request.getAttribute("listName");
					TawPartnerCarForm fm = (TawPartnerCarForm)request.getAttribute("tawPartnerCarForm");
					String nodeId = fm.getArea_id();
					for(int i=0;i<listId.size();i++){
					if(nodeId.equals(listId.get(i))){
					 %>
					 <option value="<%=listId.get(i) %>" selected>
					<%=listName.get(i) %>
						</option>
						 <%}else{ %>
						  <option value="<%=listId.get(i) %>">
					<%=listName.get(i) %>
						</option>
						<%}} %>
						<html:hidden property="area_id" styleId="area_id"/>
						 </html:select>
		
			<%--<html:text property="area_id" styleId="area_id"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.area_id}" />
		--%></td>

		<!-- 型号规格 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.model" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="model" styleId="model"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.model}"/>
		</td>
<!-- 
		<td class="label">
			<fmt:message key="tawPartnerCar.city" />&nbsp;*
		</td>
		<td class="content">
					<select name="city" id="city" 
						alt="allowBlank:false,vtext:'请选择所在县区'" onchange="">
						<option value="">
							--请选择所在县区--
						</option>				
					</select>
		</td>
 -->		
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

		<!-- 行驶证号 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.drivingLicense" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="drivingLicense" styleId="drivingLicense"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.drivingLicense}"/>
		</td>
	
	</tr>

	<tr>
		<!-- 生产厂家 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.factory" />&nbsp;*
		</td>
		<td class="content"  colspan="3">
			<html:text property="factory" styleId="factory"
						styleClass="text medium"   style="width:84%;" 
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
	
	<tr>
		<!-- 所有性质 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.ownershipType" />&nbsp;*
		</td>
		<td class="content">

			<eoms:comboBox name="ownershipType" id="ownershipType" initDicId="1121307" defaultValue="${tawPartnerCarForm.ownershipType}"
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
		<logic:notEmpty name="tawPartnerCarForm" property="addMan">
	
				<td class="label">
					添加人姓名
				</td>
				<td class="content">
					<eoms:id2nameDB id="${tawPartnerCarForm.addMan}"
						beanId="tawSystemUserDao" />&nbsp;&nbsp;
						(<bean:write name="tawPartnerCarForm" property="connectType" />)
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
						styleClass="text medium" rows="3" cols="60"
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
					<!--<c:if test="${not empty tawPartnerCarForm.thumbnail}">
							<img id="imgphoto" src="${app}/<%=basePath%>${tawPartnerCarForm.thumbnail}" style="border-width: 0px;"/>
					</c:if>
					 -->
					<c:choose>
					   <c:when test="${not empty tawPartnerCarForm.thumbnail}">
					   		<img id="imgphoto" src="${app}/<%=basePath%>${tawPartnerCarForm.thumbnail}" style="border-width: 0px;"/>
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
					   		<img id="imgdriving" src="${app}/<%=basePath%>${tawPartnerCarForm.drivingLicenseThumbnail}" style="border-width: 0px;"/>
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
			<input type="button" id="btn1" class="btn" value="<fmt:message key="button.save"/>" onclick="submitForm();"/>
			<c:if test="${not empty tawPartnerCarForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确认删除?')){
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerCars.do?method=remove&id=${tawPartnerCarForm.id}';
						location.href=url}"	/>
				<%--<input type="button" class="btn" value="返回" 
					onclick="{
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerCars.do?method=backToSearch';
						location.href=url}"	/>		
			--%></c:if>
			<!-- 
			<input type="button" class="btn" value="批量导入"
						onclick="javascript:{
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerCars.do?method=toXls';
						location.href=url}" />
 			-->						
		</td>
	</tr>
</table>
<html:hidden property="id" value="${tawPartnerCarForm.id}" />
 </html:form>

<script type="text/javascript">
var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerCars.do?method=changeDep&id="+id;
			 var fieldName = "dept_id";
			 var deptId ="<%=((TawPartnerCarForm)request.getAttribute("tawPartnerCarForm")).getDept_id()%>";
//			 changeDep();
			 changeList(url,fieldName,deptId);
function changeDep()
		{    
			 var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerCars.do?method=changeDep&id="+id;
			 var fieldName = "dept_id";
			 changeList(url,fieldName,"");
			 			 
			 delAllOption("city");//地市选择更新后，重新刷新县区
			var url1="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerOils.do?method=changeCity&city="+id;
			
					Ext.Ajax.request({
									url : url1 ,
									method: 'POST',
									success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
										var json = eval(res);
				     								var countyName = "city";

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
											
										var city = '${tawPartnerCarForm.city}';	
										if(city!=''){
											document.getElementById("city").value = city;
										}
																						

									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});			
		}
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