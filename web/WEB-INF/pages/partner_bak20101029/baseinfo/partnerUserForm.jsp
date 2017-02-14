<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'partnerUserForm'});
});

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
        
function isEmail(strEmail) {
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
           r = document.forms[0].personCardNo.value.match(re);
           	if(r!=document.forms[0].personCardNo.value){   
           		document.forms[0].personCardNo.value = '';        		
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

window.onload = function(){
    var personCardNo = '${partnerUserForm.personCardNo}';
	var operType = '${operType}';
	if(personCardNo != '' && operType == 'save'){
	    parent.setPersonCardNo(personCardNo);
    }
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
	window.frameElement.height = 740;  
	var isEdit = '${isEdit}';
	if(isEdit!='add'){
		document.body.style.overflow = "hidden";
	}       
</script>
<script language="javascript" for="document" event="onkeydown">
    if (event.keyCode == 13)
    {
        isEmail(document.getElementById('email').value);
    }
</script>

<html:form action="/partnerUsers.do?method=save" styleId="partnerUserForm" method="post" > 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<html:hidden property="treeNodeId"/>

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="partnerUser.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="partnerUser.name" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="name" styleId="name"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerUserForm.name}" />
		</td>
		
	    <td  rowspan="4" valign="middle">
			<fmt:message key="partnerUser.photo" />
		</td>		
	    <td  rowspan="4" valign="bottom">
		   <html:hidden property="photo" styleId="photo" value="${partnerUserForm.photo}"/>
		   <html:hidden property="accessory" styleId="accessory" value="${partnerUserForm.accessory}"/>
		   <c:if test="${imgSrc!=null}"><!-- 修改时 -->
			   <div id="imgdiv">
			      <img id="imgUser" name="imgUser" src="${app }${imgSrc }"  border="0" >
			      <br>
			      <input type="button" name="delPhoto" id="delPhoto" value="删除" class="btn" onclick="if(confirm('确定删除电子照片吗？'))deletePhoto();">
			   </div>   
		      <iframe id="upframe" name="upframe" class="uploadframe" frameborder="0" style="display:none;height:50pt;width:100%" src="${app }/partner/baseinfo/partnerUsers.do?method=toUploadphotoPage" scrolling="no" ></iframe>
		   </c:if>
		   
		   <c:if test="${imgSrc==null}"><!-- 新建时 -->
			   <div id="imgdiv" style="display:none">
			      <img id="imgUser" name="imgUser" src="${app }${imgSrc }" width="80" height="110" border="0" >
			      <input type="button" name="delPhoto" id="delPhoto" value="删除" class="btn" onclick="if(confirm('确定删除电子照片吗？'))javascript:deletePhoto();" >
			   </div>
		      <iframe id="upframe" name="upframe" class="uploadframe" frameborder="0" style="height:50pt;width:100%" src="${app }/partner/baseinfo/partnerUsers.do?method=toUploadphotoPage" scrolling="no" ></iframe>
		   </c:if>
		</td>
	</tr>
	<tr>	
		<td class="label">
			<fmt:message key="partnerUser.personCardNo" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="personCardNo" styleId="personCardNo" onblur="fucCheckNum();testCharSize(this,255);"
						styleClass="text medium" 
						alt="allowBlank:false,vtype:'number',vtext:''" value="${partnerUserForm.personCardNo}" />
		</td>	
	</tr>
	<tr>
	    <td class="label">
			<fmt:message key="partnerUser.areaName" />&nbsp;*
		</td>
		<td class="content">
		<!-- <html:text property="areaName" styleId="areaName"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${partnerUserForm.areaName}" />  -->	
						
			 <html:select disabled="true"  property="areaId" styleId="areaId" size="1" >
					 <%List listId =(List) request.getAttribute("listId");
					List listName = (List)request.getAttribute("listName");
					PartnerUserForm fm = (PartnerUserForm)request.getAttribute("partnerUserForm");
					String nodeId = fm.getAreaId();
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
						 </html:select>
						 <html:hidden property="areaId" styleId="areaId"/>
		</td>
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="partnerUser.deptName" />&nbsp;*
		</td>
		<td class="content">
				<html:select disabled="true"  property="deptId" styleId="deptId" size="1" onchange ="changeGrid()">
				<html:hidden property="deptId"/>
				</html:select>
	<!-- 		<html:text property="deptName" styleId="deptName"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${partnerUserForm.deptName}" />  -->
		</td>
		</tr>
<!--
		<tr>
			<td class="label">
				<fmt:message key="partnerUser.city" />&nbsp;*
			</td>
				<td class="content">
							县区			
							<select name="city" id="city" 
								alt="allowBlank:false,vtext:'请选择所在县区'" size="1" onchange="">
								<option value="">
									--请选择所在县区--
								</option>				
							</select>		
			</td>
		</tr>
-->		  
		<tr>
		<td class="label">
			<fmt:message key="partnerUser.areaNames" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="areaNames" styleId="areaNames" styleClass="text medium"
			onclick="openSelectAreas();" readonly="true"
						alt="allowBlank:false,vtext:''" value="${partnerUserForm.areaNames}" />
						
			<input type="hidden" name="areaType" id="areaType" >	<!-- 无用，只为不出错 -->		
		</td>

		<td class="label">
			<fmt:message key="partnerUser.userId" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="userId" styleId="userId"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${partnerUserForm.userId}" /><font style="color: red;">${fallure }</font>
		</td>
		</tr>
		
		<tr>
			<td class="label">
				<fmt:message key="partnerUser.serviceProfessional" />&nbsp;*
			</td>
			<td class="content">
				
				<eoms:comboBox name="serviceProfessional" id="serviceProfessional" initDicId="1121201" defaultValue="${partnerUserForm.serviceProfessional}"
				    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
			</td>
			<td class="label">
				<fmt:message key="partnerUser.workLicenseLevel" />&nbsp;*
			</td>
			<td class="content">
					
				<eoms:comboBox name="workLicenseLevel" id="workLicenseLevel" initDicId="1121301" defaultValue="${partnerUserForm.workLicenseLevel}"
				    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
			</td>
		</tr>

		<tr>
			<td class="label">
				<fmt:message key="partnerUser.workLicenseMajor" />&nbsp;*
			</td>
			<td class="content">
				<eoms:comboBox name="workLicenseMajor" id="workLicenseMajor" initDicId="1121302" defaultValue="${partnerUserForm.workLicenseMajor}"
				    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
			</td>
		
			<td class="label">
				<fmt:message key="partnerUser.projectName" />&nbsp;*
			</td>
			<td class="content">
				<html:text property="projectName" styleId="projectName"
							styleClass="text medium" onblur="testCharSize(this,255);"
							alt="allowBlank:false,vtext:'',maxLength:32" value="${partnerUserForm.projectName}" />
			</td>
		</tr>

		<tr>
			<td class="label">
				<fmt:message key="partnerUser.maintainLevel" />&nbsp;*
			</td>
			<td class="content">
				<eoms:comboBox name="maintainLevel" id="maintainLevel" initDicId="1121303" defaultValue="${partnerUserForm.maintainLevel}"
				    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
						
			</td>
		
			<td class="label">
				<fmt:message key="partnerUser.projectProperty" />&nbsp;*
			</td>
			<td class="content">
				
				<eoms:comboBox name="projectProperty" id="projectProperty" initDicId="1121304" defaultValue="${partnerUserForm.projectProperty}"
				    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
			</td>
		</tr>


		<tr>
			<td class="label">
				<fmt:message key="partnerUser.responsibility" />&nbsp;*
			</td>
			<td class="content">
				
				<eoms:comboBox name="responsibility" id="responsibility" initDicId="1121305" defaultValue="${partnerUserForm.responsibility}"
				    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
			</td>
		
			<td class="label">
				<fmt:message key="partnerUser.isbackbone" />&nbsp;*
			</td>
			<td class="content">
				<eoms:dict key="dict-partner-baseinfo" dictId="isbackbone" isQuery="false" alt="allowBlank:false"
				defaultId="${partnerUserForm.isbackbone}" selectId="isbackbone" beanId="selectXML" />
			</td>
		</tr>

		<tr>
			<td class="label">
				<fmt:message key="partnerUser.mobilePhone" />&nbsp;*
			</td>
			<td class="content">
				<html:text property="mobilePhone" styleId="mobilePhone"
							styleClass="text medium" onblur="testCharSize(this,255);"
							alt="allowBlank:false,vtext:'',maxLength:255" value="${partnerUserForm.mobilePhone}" />
			</td>
			<td class="label">
				<fmt:message key="partnerUser.email" />&nbsp;*
			</td>
			<td class="content">
				<html:text property="email" styleId="email" onblur="isEmail(this.value);testCharSize(this,255);"
							styleClass="text medium"
							alt="allowBlank:false,vtype:'email'" value="${partnerUserForm.email}" />
			</td>		

		</tr>										
		<tr>
		<td class="label">
			<fmt:message key="partnerUser.sex" />&nbsp;*
		</td>
		<td class="content">
		    
		    <eoms:comboBox name="sex" id="sex" initDicId="1120202"
					defaultValue='${partnerUserForm.sex}' alt="allowBlank:false,vtext:''" />

		</td>

		<td class="label">
			<fmt:message key="partnerUser.birthdey" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="birthdey" styleId="birthdey" readonly="true" 
						styleClass="text medium" onclick="popUpCalendar(this, this,null,null,null,false,-1);"
						alt="allowBlank:false,vtype:'lessThen',link:'workTime',vtext:'出生日期应早于工作日期'"
						 value="${partnerUserForm.birthdey}" />
		</td>
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="partnerUser.diploma" />&nbsp;*
		</td>
		<td class="content">

			<eoms:comboBox name="diploma" id="diploma" initDicId="1120203"
					defaultValue='${partnerUserForm.diploma}' alt="allowBlank:false,vtext:''" />
		</td>

		<td class="label">
			<fmt:message key="partnerUser.workTime" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="workTime" styleId="workTime" readonly="true"
						styleClass="text medium" onclick="popUpCalendar(this, this,null,null,null,false,-1);"
						alt="allowBlank:false,vtype:'moreThen',link:'birthdey',vtext:'工作时间应晚于出生时间'"
						 value="${partnerUserForm.workTime}" />
		</td>
       </tr>
       <tr>
		<td class="label">
			<fmt:message key="partnerUser.deptWorkTime" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="deptWorkTime" styleId="deptWorkTime" readonly="true"
						styleClass="text medium" onclick="popUpCalendar(this, this,null,null,null,false,-1);"
						alt="allowBlank:false,vtype:'moreThen',link:'birthdey',vtext:'工作时间应晚于出生时间'"
						 value="${partnerUserForm.deptWorkTime}" />
		</td>

		<td class="label">
			<fmt:message key="partnerUser.licenseType" />&nbsp;*
		</td>
		<td class="content">
		    <eoms:comboBox name="licenseType" id="licenseType" initDicId="1120201"
					defaultValue='${partnerUserForm.licenseType}' alt="allowBlank:false,vtext:''" />

		</td>
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="partnerUser.licenseNo" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="licenseNo" styleId="licenseNo"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerUserForm.licenseNo}" />
		</td>

		<td class="label">
			<fmt:message key="partnerUser.post" />&nbsp;*
		</td>
		<td class="content">					
			<eoms:comboBox name="post" id="post" initDicId="1120205"
					defaultValue='${partnerUserForm.post}' alt="allowBlank:false,vtext:''" />
		</td>
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="partnerUser.postState" />&nbsp;*
		</td>
		<td class="content">
						
			<eoms:comboBox name="postState" id="postState" initDicId="1120204"
					defaultValue='${partnerUserForm.postState}' alt="allowBlank:false,vtext:''" />
		</td>

		<td class="label">
			<fmt:message key="partnerUser.phone" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="phone" styleId="phone" 
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerUserForm.phone}" />
		</td>
		</tr>
			<td class="label">
				<fmt:message key="partnerUser.remark" />
			</td>
			<td class="content" colspan="3">
				<html:textarea property="remark" styleId="remark" onblur="testCharSize(this,255);"
				styleClass="text medium" style="width:80%" rows="3"
				alt="allowBlank:true,vtext:''" value="${partnerUserForm.remark}" />
			</td>
		<tr>

	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
		<c:if test="${requestScope.hasRightForDel=='1'}" >
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</c:if>
		<!-- 
			<input type="button" class="btn" value="批量导入"
						onclick="javascript:{
						var url=eoms.appPath+'/partner/baseinfo/partnerUsers.do?method=toXls&treeNodeId=${partnerUserForm.treeNodeId}';
						location.href=url}" />
		 -->
		</td>
	</tr>
</table>
<html:hidden property="id" value="${partnerUserForm.id}" />
</html:form>
<script type="text/javascript">
            var id = null;
            if(document.getElementById("areaId")!=null){
                id = document.getElementById("areaId").value;
            }
			 var url="<%=request.getContextPath()%>/partner/baseinfo/partnerUsers.do?method=changeDep&id="+id;
			 var fieldName = "deptId";
			 var deptId ="<%=((PartnerUserForm)request.getAttribute("partnerUserForm")).getDeptId()%>";
			 changeList(url,fieldName,deptId);
		
	function deletePhoto()
		{    
			 var id = "${partnerUserForm.id}";
			 var url = null;
			 if(id!=null&&id!="")//编辑时
			    url="<%=request.getContextPath()%>/partner/baseinfo/partnerUsers.do?method=delPhoto&id="+id;
			 else {//新建时
			    var accessory = document.getElementById("accessory").value;
			    url="<%=request.getContextPath()%>/partner/baseinfo/partnerUsers.do?method=delPhoto&accessory="+accessory;
			 }
			 var info = getResponseText(url);
             if(info!=null&&info=="delIsSuccess"){
                 document.getElementById("accessory").value = "";
                 document.getElementById("photo").value = "";
                 document.getElementById("imgdiv").style.display = "none";
                 if(id!=null&&id!=""){//修改时
	                 document.getElementById("upframe").style.display = "block";
                 }
                 document.getElementById("upframe").style.height = "50pt";
                 window.frames['upframe'].document.getElementById('div').style.display = "";
             }
		}
	

  </script>
<%@ include file="/common/footer_eoms.jsp"%>