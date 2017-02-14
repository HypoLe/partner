<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*,com.boco.eoms.base.util.StaticMethod;"%>

<%

String photoName = StaticMethod.nullObject2String(request.getAttribute("photoName"));
String defaultNoPic = "images/pnr_thumbnail/man.gif";

String defaultThumbnailPic = "images/pnr_thumbnail/man.gif";

String basePath="images/pnr_thumbnail/photo/zoom/";



 %>

<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

<script type="text/javascript">

Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawApparatusForm'});
});
 
	function openNoThumbnail(){
		 //window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=1&formNo=1');
		 window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=1&formNo=1&picIDImage=imgNo&hdId=noThumbnail');
	}

	function openThumbnail(){
		 //window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=2&formNo=2');
		 window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=1&formNo=1&picIDImage=imgApparatus&hdId=thumbnail');
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
function getselcompany(){
	var selindex=document.getElementById("dept_id").selecte;
}

</script>

<html:form action="/tawApparatuss.do?method=save"
	styleId="tawApparatusForm" method="post">

	<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

		<table class="formTable">
			<caption>
				<div class="header center">
					<fmt:message key="tawApparatus.form.heading" />
				</div>
			</caption>
<tr>
				<td class="label">
					仪器仪表编号&nbsp;*
				</td>
				<td class="content">
					<html:text property="apparatusId" styleId="apparatusId" onblur="testCharSize(this,64);"
						styleClass="text medium" alt="allowBlank:false,vtext:''"
						value="${tawApparatusForm.apparatusId}" />${fallure }
				</td>
		<td class="label">
			<fmt:message key="tawApparatus.no" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="no" styleId="no"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawApparatusForm.no}"/>
		</td>
			
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="tawApparatus.apparatusName" />&nbsp;*
				</td>
				<td class="content">
					<html:text property="apparatusName" styleId="apparatusName" onblur="testCharSize(this,64);"
						styleClass="text medium" alt="allowBlank:false,vtext:''"
						value="${tawApparatusForm.apparatusName}" />
				</td>

				<td class="label">
					<fmt:message key="tawApparatus.factory" />&nbsp;*
				</td>
				<td class="content">
					<%--<eoms:dict key="dict-partner" dictId="factory_apparatus" beanId="selectXML" alt="allowBlank:false,vtext:''"
                 defaultId="" isQuery="false"  selectId="factory" onchange="factorychange(this.value)"/>
                --%>
					<eoms:comboBox name="factory" id="factory" initDicId="11203"
						alt="allowBlank:false,vtext:''"
						onchange="factorychange(this.value)"
						defaultValue='${tawApparatusForm.factory}' />
					<div style="display:none;" id="control">
						<html:text property="factory2" styleId="factory2" onblur="testCharSize(this,64);"
							styleClass="text medium" />
					</div>
				</td>
			</tr>

			<tr>
			<td class="label">
					<fmt:message key="tawApparatus.dept_id" />&nbsp;*
				</td>
				<td class="content" >
				 <eoms:pnrComp name="partnerid" id="dept_id" defaultValue='${tawApparatusForm.partnerid}'/>
				</td>
				<td class="label">
			<fmt:message key="tawApparatus.use" />&nbsp;*
		</td>
		<td class="content">
			<eoms:comboBox name="use" id="use" initDicId="1121306" defaultValue="${tawApparatusForm.use}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
		</td>	
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="tawApparatus.type" />&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="type" id="type" initDicId="11204"
						defaultValue='${tawApparatusForm.type}'
						alt="allowBlank:false,vtext:''" />
					<%--<html:text property="type" styleId="type"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawApparatusForm.type}" />
		--%>
				</td>

				<td class="label">
					<fmt:message key="tawApparatus.model" />&nbsp;*
				</td>
				<td class="content">
					<html:text property="model" styleId="model" onblur="testCharSize(this,64);"
						styleClass="text medium" alt="allowBlank:false,vtext:''"
						value="${tawApparatusForm.model}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="tawApparatus.state" />&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="state" id="state" initDicId="11205"
						defaultValue='${tawApparatusForm.state}'
						alt="allowBlank:false,vtext:''" />
					<%--<html:text property="state" styleId="state"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawApparatusForm.state}" />
		--%>
				</td>
				<!-- 价格 -->
				<td class="label">
					<fmt:message key="tawApparatus.price" />&nbsp;*
				</td>
				<td class="content">
					<html:text property="price" styleId="price"
								styleClass="text medium"
								alt="allowBlank:false,vtext:''" value="${tawApparatusForm.price}"/>
				</td>				
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="tawApparatus.storage" />&nbsp;*
				</td>
				<td class="content"  >
					<html:text property="storage" styleId="storage" onblur="testCharSize(this,128);"
						styleClass="text medium" alt="allowBlank:false,vtext:''"  style="width:84%;" 
						value="${tawApparatusForm.storage}" />
				</td>
				<td class="label">
				   <fmt:message key="tawApparatus.whether_allocate" />&nbsp;*
				</td>
				<td class="content" >
				   <select id="whether_allocate" name="whether_allocate" alt="allowBlank:false,vtext:''">
				   <option value="1030101">
				      是
				   </option>
				   <option value="1030102">
				     否
				   </option>
				
			</tr>			
			<logic:notEmpty name="tawApparatusForm" property="addMan">
			<tr>
				<td class="label">
					添加人姓名
				</td>
				<td class="content">
					<eoms:id2nameDB id="${tawApparatusForm.addMan}"
						beanId="tawSystemUserDao" />&nbsp;&nbsp;
						(<bean:write name="tawApparatusForm" property="connectType" />)
				</td>

				<td class="label">
					添加时间
				</td>
				<td class="content">
					<bean:write name="tawApparatusForm" property="addTime" />

				</td>
			</tr>
			</logic:notEmpty>

<input type="hidden" name="nodeId" value="<%=request.getAttribute("nodeId") %>">




	<!-- 以下是新增字段 -->
	<tr>

		<!-- 维护专业 -->
		<td class="label">
			<fmt:message key="tawApparatus.serviceProfessional" />&nbsp;*
		</td>
		<td class="content">
				<eoms:comboBox name="serviceProfessional" id="serviceProfessional" initDicId="1121201" defaultValue="${tawApparatusForm.serviceProfessional}"
				    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>
		<!-- 用途 
		<td class="label">
			<fmt:message key="tawApparatus.use" />&nbsp;*
		</td>
		<td class="content">
			<eoms:comboBox name="use" id="use" initDicId="1121306" defaultValue="${tawApparatusForm.use}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
		</td>	-->	
				<!-- 所属县区
				<td class="label">
					<fmt:message key="tawApparatus.city" />&nbsp;*
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
	<tr>
		<td class="label">
			<fmt:message key="tawApparatus.remark" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" onblur="testCharSize(this,256);"
				styleClass="text medium" rows="3" cols="40"  style="width:84%;" 
				value="${tawApparatusForm.remark}" />
		</td>
	</tr>		
	<tr>
		<!-- 序列号缩略图 -->
		<td class="label">
			<fmt:message key="tawApparatus.noThumbnail" />&nbsp;*
		</td>
		<td class="content">
<!-- 			<html:text property="noThumbnail" styleId="noThumbnail"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawApparatusForm.noThumbnail}"/>
 -->						

			<div id='pic' class="photocontainer">
				<c:if test="${empty tawApparatusForm.id}">
					<img id="imgNo" src="${app}/<%=defaultNoPic%>" style="border-width: 0px;" />
					
				</c:if>
				<c:if test="${not empty tawApparatusForm.id}">
					<c:choose>
					   <c:when test="${not empty tawApparatusForm.noThumbnail}">
					   		<img id="imgNo" src="${tawApparatusForm.noThumbnail}" style="border-width: 0px;"/>
					   </c:when>
					   <c:otherwise>
					   		<img id="imgNo" src="${app}/<%=defaultNoPic%>" style="border-width: 0px;"/>
					   </c:otherwise>
					</c:choose>
					
				</c:if>
			</div>
			<input id="noThumbnail" type="hidden" name="noThumbnail" value="${tawApparatusForm.noThumbnail}"/><br>
			<input type="button" name="changePicThumbnail" id="changePicThumbnail" value="选择图片" class="btn" onclick="openNoThumbnail()"/>			
						
		</td>



		<!-- 仪器仪表缩略图 -->
		<td class="label">
			<fmt:message key="tawApparatus.thumbnail" />&nbsp;*
		</td>
		<td class="content">
<!-- 			<html:text property="thumbnail" styleId="thumbnail"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawApparatusForm.thumbnail}"/>
 -->						
						
			<div id='pic' class="photocontainer">
				<c:if test="${empty tawApparatusForm.id}">
					<img id="imgApparatus" src="${app}/<%=defaultThumbnailPic%>" style="border-width: 0px;" />
					
				</c:if>
				<c:if test="${not empty tawApparatusForm.id}">
					<c:choose>
					   <c:when test="${not empty tawApparatusForm.thumbnail}">
					   		<img id="imgApparatus" src="${tawApparatusForm.thumbnail}" style="border-width: 0px;"/>
					   </c:when>
					   <c:otherwise>
					   		<img id="imgApparatus" src="${app}/<%=defaultNoPic%>" style="border-width: 0px;"/>
					   </c:otherwise>
					</c:choose>
					
				</c:if>
			</div>
			<input id="thumbnail" type="hidden" name="thumbnail" value="${tawApparatusForm.thumbnail}"/><br>
			<input type="button" name="changePicThumbnail" id="changePicThumbnail" value="选择图片" class="btn" onclick="openThumbnail()"/>			
						
		</td>



	
	</tr>
			<html:hidden property="dimensionalCode" value="${tawApparatusForm.dimensionalCode}" />


		</table>

	</fmt:bundle>

	<table>
		<tr>
			<td>
				<input type="submit" class="btn"
					onclick="check();"
					value="<fmt:message key="button.save"/>" />
				<c:if test="${not empty tawApparatusForm.id}">
					<input type="button" class="btn"
						value="<fmt:message key="button.delete"/>"
						onclick="javascript:if(confirm('确认删除?')){
						var url=eoms.appPath+'/partner/baseinfo/tawApparatuss.do?method=remove&id=${tawApparatusForm.id}';
						location.href=url}" />
					<%--<input type="button" class="btn" value="返回"
						onclick="javascript:{
						var url=eoms.appPath+'/partner/baseinfo/tawApparatuss.do?method=backToSearch';
						location.href=url}" />
				--%></c:if>
				<!--
				<input type="button" class="btn" value="批量导入"
						onclick="javascript:{
						var url=eoms.appPath+'/partner/baseinfo/tawApparatuss.do?method=toXls';
						location.href=url}" />
  				-->						
			</td>
		</tr>
	</table>
	<html:hidden property="id" value="${tawApparatusForm.id}" />
</html:form>

<script language="java" type="text/javascript">
<%--
var id = document.getElementById("area_id").value;

			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawApparatuss.do?method=changeDep&id="+id;
			 var fieldName = "dept_id";
			 
			 var deptId ="<%=((TawApparatusForm)request.getAttribute("tawApparatusForm")).getDept_id()%>";
	//		 changeDep();
			 changeList(url,fieldName,deptId);
function changeDep()
		{    
			 var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawApparatuss.do?method=changeDep&id="+id;
			 var fieldName = "dept_id";
			 changeList(url,fieldName,"");
			 
			 var url1="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerOils.do?method=changeCity&city="+id;
			 delAllOption("city");//地市选择更新后，重新刷新县区
			
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
											
										var city = '${tawApparatusForm.city}';	
										if(city!=''){
											document.getElementById("city").value = city;
										}
											
									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});			
		}
		
		--%>
function factorychange(ss){
if(ss=="1120301"){
document.getElementById("control").style.display="inline";
}
else{
document.getElementById("control").style.display="none";
}
   }
function check(){

var fac = document.getElementById("factory").value;
if(fac=="1120301"){
	var fac2 = document.getElementById("factory2").value;
	if(fac2==""||fac2==" "){
	alert("请输入厂商！");
	return false;
	}
}
	 //缩略图为必填项 为空时提示
	 <%--
	 var imgNo=document.getElementById("imgNo").src;
	 var imgApparatus=document.getElementById("imgApparatus").src;
	 
	 var imgNoIsNull= imgNo.indexOf("images/pnr_thumbnail/man.gif");
	 var imgApparatusIsNull= imgApparatus.indexOf("images/pnr_thumbnail/man.gif");
	 if(imgNoIsNull!=-1){
	 	alert("请上传序列号缩略图！");
	 }else if(imgApparatusIsNull!=-1){
	 	alert("请上传仪器仪表缩略图！");
	}else{
	 	$("tawApparatusForm").submit();
	 }
	 --%>

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
