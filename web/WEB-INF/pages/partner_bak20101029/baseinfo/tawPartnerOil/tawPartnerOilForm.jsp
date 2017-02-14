<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawPartnerOilForm'});
});


 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
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

<html:form action="/tawPartnerOils.do?method=save" styleId="tawPartnerOilForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawPartnerOil.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.oil_number" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="oil_number" styleId="oil_number"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.oil_number}" />${fallure }
		</td>
		<td class="label">
			<fmt:message key="tawPartnerOil.type" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="type" styleId="type"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.type}" />
		</td>
		<!-- 
		<td class="label">
			<fmt:message key="tawPartnerOil.dimensionalCode" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="dimensionalCode" styleId="dimensionalCode"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.dimensionalCode}" />
		</td>
		 -->
	</tr>

	<tr>
	<td class="label">
			<fmt:message key="tawPartnerOil.area_id" />&nbsp;*
		</td>
		<td class="content">
		<html:select disabled="true" property="area_id"  styleId="area_id" size="1" >
					 <%List listId =(List) request.getAttribute("listId");
					List listName = (List)request.getAttribute("listName");
					TawPartnerOilForm fm = (TawPartnerOilForm)request.getAttribute("tawPartnerOilForm");
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
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.area_id}" />
		--%></td>
		<td class="label">
			<fmt:message key="tawPartnerOil.serviceProfessional" />&nbsp;*
		</td>
		<td class="content">
			<eoms:comboBox name="serviceProfessional" id="serviceProfessional" initDicId="1121201" defaultValue="${tawPartnerOilForm.serviceProfessional}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>

		<!-- 所属县区
		<td class="label">
			<fmt:message key="tawPartnerOil.city" />&nbsp;*
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
			<fmt:message key="tawPartnerOil.dept_id" />&nbsp;*
		</td>
		<td class="content">
				 <html:select disabled="true"  property="dept_id" styleId="dept_id" size="1">
				 <html:hidden property="dept_id"/>
				 </html:select>
			<%--<html:text property="dept_id" styleId="dept_id"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.dept_id}" />
		--%></td>
	
		<td class="label">
			<fmt:message key="tawPartnerOil.power" />&nbsp;*
		</td>
		<td class="content">
		<eoms:comboBox name="power" id="power" initDicId="11207"
					defaultValue='${tawPartnerOilForm.power}' alt="allowBlank:false,vtext:''" />
			<%--<html:text property="power" styleId="power"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.power}" />
		--%></td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.kind" />&nbsp;*
		</td>
		<td class="content">
		<eoms:comboBox name="kind" id="kind" initDicId="11206"
					defaultValue='${tawPartnerOilForm.kind}' alt="allowBlank:false,vtext:''" />
			<%--<html:text property="kind" styleId="kind"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.kind}" />
		--%></td>
		<td class="label">
			<fmt:message key="tawPartnerOil.state" />&nbsp;*
		</td>
		<td class="content">
		<eoms:comboBox name="state" id="state" initDicId="11205"
					defaultValue='${tawPartnerOilForm.state}' alt="allowBlank:false,vtext:''" />
			<%--<html:text property="state" styleId="state"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.state}" />
		--%></td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.storage" />&nbsp;*
		</td>
		<td class="content" >
			<html:text property="storage" styleId="storage"
						styleClass="text medium" onblur="testCharSize(this,128);"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.storage}" />
		</td>	
		<td class="label">
			<fmt:message key="tawPartnerOil.factory" />&nbsp;*
		</td>
		<td class="content">
<!-- 		<eoms:comboBox name="factory" id="factory" initDicId="11208"
					defaultValue='${tawPartnerOilForm.factory}' alt="allowBlank:false,vtext:''" />
 -->
 			<html:text property="factory" styleId="factory"
						styleClass="text medium" alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.factory}" />
		</td>		
	</tr>
		<logic:notEmpty name="tawPartnerOilForm" property="addMan">
			<tr>
				<td class="label">
					添加人姓名
				</td>
				<td class="content">
					<eoms:id2nameDB id="${tawPartnerOilForm.addMan}"
						beanId="tawSystemUserDao" />&nbsp;&nbsp;
						(<bean:write name="tawPartnerOilForm" property="connectType" />)
				</td>

				<td class="label">
					添加时间
				</td>
				<td class="content">
					<bean:write name="tawPartnerOilForm" property="addTime" />

				</td>
			</tr>
			</logic:notEmpty>
		<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.remark" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" onblur="testCharSize(this,255);"
						styleClass="text medium" cols="40" rows="4"
						value="${tawPartnerOilForm.remark}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty tawPartnerOilForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确认删除?')){
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerOils.do?method=remove&id=${tawPartnerOilForm.id}';
						location.href=url}"	/>
				<%--<input type="button" class="btn" value="返回" 
					onclick="{
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerOils.do?method=backToSearch';
						location.href=url}"	/>		
			--%></c:if>
			<!--
			<input type="button" class="btn" value="批量导入"
						onclick="javascript:{
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerOils.do?method=toXls';
						location.href=url}" />
 			 -->						
		</td>
	</tr>
</table>
<html:hidden property="id" value="${tawPartnerOilForm.id}" />
</html:form>
<script type="text/javascript">
var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerOils.do?method=changeDep&id="+id;
			 var fieldName = "dept_id";
			 var deptId ="<%=((TawPartnerOilForm)request.getAttribute("tawPartnerOilForm")).getDept_id()%>";
	//		 	changeDep();
			 changeList(url,fieldName,deptId);
function changeDep()
		{    
			 var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerOils.do?method=changeDep&id="+id;
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
											
										var city = '${tawPartnerOilForm.city}';	
										if(city!=''){
											document.getElementById("city").value = city;
										}
											
									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});			
			 
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