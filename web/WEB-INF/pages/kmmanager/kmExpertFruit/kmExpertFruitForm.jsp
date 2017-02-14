<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
var tr = true;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmExpertFruitForm'});
});

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
	function valgrid()
	{
		var obj = document.getElementById("courseName");
		var url = "../kmmanager/kmExpertFruits.do?method=validationCourseName&id="+document.getElementById("id").value;
		var httpRequest = createRequest();
		if(httpRequest){
		     httpRequest.open("POST",url,true);
		     httpRequest.onreadystatechange=function()
		     {
		     	if(httpRequest.readyState==4)
			     	if(httpRequest.status==200){
			     		var json = eval(httpRequest.responseText);
			     			if(json[0].message == true){
			     				if(obj.value!=""){
			     					document.getElementById("message").innerHTML = "<font color='green'>此课题名可以使用</font>";
			     					tr = true;
			     				}else{
			     					obj.focus();
			     				}
			     			}else{
			     				document.getElementById("message").innerHTML = "<font color='red'>对不起,此课题名称已存在</font>";
			     				tr = false;
			     				obj.focus();
			     			}		     			
					}	
		     }
		     httpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); 
		     httpRequest.send("courseName="+document.getElementById("courseName").value);
		}
	}
	
	function sub(){
		if(tr){
		}else{
			alert("课题名称已经存在");
			return false;
		}
		if(v.check()){
	       $("kmExpertFruitForm").submit();
		}	
	}
	function window.onload()
	{	
		document.getElementById("courseName").focus();
	}

</script>




<html:form action="/kmExpertFruits.do?method=save" styleId="kmExpertFruitForm" method="post" > 

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="kmExpertFruit.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.courseName" />
		</td>
		<td class="content">
			<html:text property="courseName" styleId="courseName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${kmExpertFruitForm.courseName}" onblur="valgrid()" maxlength="32"/>
			<span id = "message"></span>			
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.beginTime" />
		</td>
		<td class="content">
	          <input type="text" size="20" readonly="true" class="text" 
                name="beginTime" id="beginTime"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt=" vtype:'lessThen',link:'endTime', allowBlank:false,vtext:'结束时间要大于开始时间'" value="${kmExpertFruitForm.beginTime}" />
		</td>
		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.endTime" />
		</td>
		<td class="content">
	          <input type="text" size="20" readonly="true" class="text" 
                name="endTime" id="endTime"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt=" vtype:'moreThen',link:'beginTime', allowBlank:false,vtext:'结束时间要大于开始时间'" value="${kmExpertFruitForm.endTime}" />
		</td>
		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.courseContent" />
		</td>
		<td class="content">
			<html:text property="courseContent" styleId="courseContent"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${kmExpertFruitForm.courseContent}" maxlength="128" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.accessories" />
		</td>
	    
	    <td class="content" >
			<eoms:attachment name="kmExpertFruitForm" property="accessories" scope="request" idField="accessories" appCode="kmmanager" />
		</td>
		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.participant" />
		</td>
		<td class="content">
			<html:text property="participant" styleId="participant"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${kmExpertFruitForm.participant}" maxlength="32"/>
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="sub();" />
			<c:if test="${not empty kmExpertFruitForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/kmExpertFruit/kmExpertFruits.do?method=remove&id=${kmExpertFruitForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${kmExpertFruitForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>