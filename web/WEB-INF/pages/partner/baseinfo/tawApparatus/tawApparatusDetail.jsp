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
		 window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=1&formNo=1');
		 
	}

	function openThumbnail(){
		 window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=2&formNo=1');
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
					仪器仪表编号
				</td>
				<td class="content">
					${tawApparatusForm.apparatusId}
				</td>
				<td class="label">
					<fmt:message key="tawApparatus.no" />
				</td>
				<td class="content">
					${tawApparatusForm.no}
				</td>
				
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="tawApparatus.apparatusName" />
				</td>
				<td class="content">
					${tawApparatusForm.apparatusName}
				</td>

				<td class="label">
					<fmt:message key="tawApparatus.factory" />
				</td>
				<td class="content">
					<eoms:id2nameDB id="${tawApparatusForm.factory}"  beanId="ItawSystemDictTypeDao" />
				</td>
			</tr>

			<tr>
				<td class="label">
				<fmt:message key="tawApparatus.storage" />
			</td>
				<td class="content">
				${tawApparatusForm.storage}
				</td>
				<!-- <td class="label">
					<fmt:message key="tawApparatus.area_id" />
				</td>
				<td class="content">
					<eoms:id2nameDB id="${tawApparatusForm.areaidtrue}" beanId="tawSystemAreaDao" />
				<html:hidden property="area_id" styleId="area_id"/>
				</td> -->
				<td class="label">
					<fmt:message key="tawApparatus.dept_id" />
				</td>
				<td class="content">
				 	<html:hidden property="dept_id"/>
				<eoms:id2nameDB id="${tawApparatusForm.partnerid}" beanId="partnerDeptDao" />
				
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="tawApparatus.type" />
				</td>
				<td class="content">
				<eoms:id2nameDB id="${tawApparatusForm.type}"  beanId="ItawSystemDictTypeDao" />
				</td>

				<td class="label">
					<fmt:message key="tawApparatus.model" />
				</td>
				<td class="content">
					${tawApparatusForm.model}
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="tawApparatus.state" />
				</td>
				<td class="content">
					<eoms:id2nameDB id="${tawApparatusForm.state}"  beanId="ItawSystemDictTypeDao" />				
				</td>
		
				<!-- 价格 -->
				<td class="label">
					<fmt:message key="tawApparatus.price" />
				</td>
				<td class="content">
					${tawApparatusForm.price}
				</td>		
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
	

				<!-- 所属县区
				<tr>
				<td class="label">
					<fmt:message key="tawApparatus.city" />
				</td>
				<td class="content">
					<select name="city" id="city" 
						alt="allowBlank:false,vtext:'请选择所在县区'" onchange="">
						<option value="">
							--请选择所在县区--
						</option>				
					</select>
				</td></tr>
				 -->
				 	<tr>
		<!-- 维护专业 -->
		<td class="label">
			<fmt:message key="tawApparatus.serviceProfessional" />
		</td>
		<td class="content">
				<eoms:id2nameDB id="${tawApparatusForm.serviceProfessional}"  beanId="ItawSystemDictTypeDao" />
		</td>	
		<!-- 用途 -->
		<td class="label">
			<fmt:message key="tawApparatus.use" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${tawApparatusForm.use}"  beanId="ItawSystemDictTypeDao" />		
		</td>

	</tr>
	<tr>
		<td class="label">
			<fmt:message key="tawApparatus.storage" />
		</td>
		<td class="content" colspan="3">
			${tawApparatusForm.storage}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawApparatus.remark" />
		</td>
		<td class="content" colspan="3">
			${tawApparatusForm.remark}
		</td>
	</tr>		
	<tr>
		<!-- 序列号缩略图 -->
		<td class="label">
			<fmt:message key="tawApparatus.noThumbnail" />
		</td>
		<td class="content">
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
						
		</td>



		<!-- 仪器仪表缩略图 -->
		<td class="label">
			<fmt:message key="tawApparatus.thumbnail" />
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
						
		</td>
	
	</tr>
			<html:hidden property="dimensionalCode" value="${tawApparatusForm.dimensionalCode}" />

		</table>

	</fmt:bundle>

	<table>
		<tr>
			<td>
				<input type="button" class="btn" value="编辑" onclick="var url='${app}/partner/baseinfo/tawApparatuss.do?method=edit&id=${tawApparatusForm.id}';location.href=url"/>			
			</td>
		</tr>
	</table>
	<html:hidden property="id" value="${tawApparatusForm.id}" />
</html:form>

<script language="java" type="text/javascript">
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
