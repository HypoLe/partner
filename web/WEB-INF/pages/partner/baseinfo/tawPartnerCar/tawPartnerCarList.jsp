<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
	
<script type="text/javascript">
function setvalue(ll){
   return document.getElementById(ll).value;
}
function delselcar(){
var string="";
 var objName= document.getElementsByName("checkbox11");
        for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string+=objName[i].value;   
                string+="|";
                }
        }  
        if(confirm("确认要删除吗？")){
        	if(string == null || "" ==  string){
        		alert("请选择要删除的车辆");
        		return false;
        	}
		 	location.href="${app}/partner/baseinfo/tawPartnerCars.do?method=remove&&seldelcar="+string;
		 }else{
		 return false;
		 }
}
  var checkflag = "false";
	function chooseAll(){	
	   var objs = document.getElementsByName("checkbox11");    
	    if(checkflag=="false"){
	        for(var i=0; i<objs.length; i++){
	           objs[i].checked="checked";
	        } 
	        checkflag="checked";
	    }
	    else if(checkflag=="checked"){ 	    	    
		    for(var i=0; i<objs.length; i++){
		           objs[i].checked=false;
		    } 
		    checkflag="false";
	    }
	}
function getselcompany(){
	var provvalue=document.getElementById("prov");
	var index = provvalue.selectedIndex
	var text = provvalue.options[index].text;
	document.getElementById("provvalue").value=provvalue.options[index].value;
	document.getElementById("company").value=text;
}
</script>

<c:if test="${in!=null}">

</c:if>
<c:choose>
	<c:when test="${isCity=='yes'}">
</c:when>
</c:choose>
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<html:form action="tawPartnerCars.do?method=xquery" method="post"
		styleId="tawPartnerCarForm">
		<input type="hidden" name="">
	<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawPartnerCar.list.heading" /></div>
	</caption>
			<tr>
				<td class="label">
			<fmt:message key="tawPartnerCar.car_number" />
		</td>
		<td class="content">
			<html:text property="car_number" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.car_number}" />
		</td>
		<td class="label">
			<fmt:message key="tawPartnerCar.dept_id" />
		</td>
		<td class="content">
	
		<eoms:pnrComp name="prov" id="prov" onchange="getselcompany()" styleClass="text medium"/>
				<input type="hidden" name="provvalue" id="provvalue" value=""/>
		<input type="hidden" name="company" id="company" value=""/>
		<tr><td colspan="4" align="center">
				<html:submit styleClass="button" property="method.query">
						查询
					</html:submit>
					</td></tr>
		</table> 
	</html:form>
	<br>	<div>操作提示：点击牌号可查看该车信息</div><br>
	<%--<a onclick="javascript:location.href='tawPartnerCars.do?method=add'">添加车辆信息</a>&emsp;&emsp;
	<a onclick="delselcar()">删除所选信息</a>--%>
<html:form action="tawPartnerCars" method="post" styleId="tawPartnerCarForm">
	<display:table name="tawPartnerCarList" cellspacing="0" cellpadding="0"
		id="tawPartnerCarList" pagesize="${pageSize}" class="table tawPartnerCarList"
		export="false" decorator="com.boco.eoms.partner.baseinfo.util.PartnerCarDecorator"
		requestURI="${app}/partner/baseinfo/tawPartnerCars.do?method=search" sort="list" partialList="true" size="resultSize">
    <display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
         <input type="checkbox" name="checkbox11" id="checkbox11"  value="${tawPartnerCarList.id }"/>
    </display:column>
	<display:column property="car_number" sortable="true"
			headerClass="sortable" titleKey="tawPartnerCar.car_number" href="${app}/partner/baseinfo/tawPartnerCars.do?method=detail" paramId="id" paramProperty="id"/>
	<display:column sortable="true" headerClass="sortable" title="所属公司" >
<eoms:id2nameDB beanId="partnerDeptDao" id="${tawPartnerCarList.partnerid}"/>
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="所属地市" >
				<eoms:id2nameDB beanId="partnerDeptAreaDao" id="${tawPartnerCarList.partnerid}"/>
</display:column>
	<display:column property="start_time" sortable="true" format="{0,date,yyyy-MM-dd}"
			headerClass="sortable" titleKey="tawPartnerCar.start_time" />
	<%--<display:column  sortable="true" 
			headerClass="sortable" title="备注a" style="width: 410" >
			<textarea readonly="readonly" style="width: 400">${tawPartnerCarList.remark}</textarea>
			</display:column>
<display:column  headerClass="sortable"  title="操作" > 
	<a onclick="delone('${tawPartnerCarList.id}')">  删  除  </a>
	</display:column> --%>
		<display:setProperty name="paging.banner.item_name" value="tawPartnerCar" />
		<display:setProperty name="paging.banner.items_name" value="tawPartnerCars" />
	</display:table>
	<c:out value="${buttons}" escapeXml="false" />&nbsp;&nbsp;&nbsp;<c:out value="${buttonsdel}" escapeXml="false" />
	<br><br>
	<input class="button" type="button" onclick="addcar()" value="添加车辆信息"/>&emsp;&emsp;
	<!-- <a href='tawPartnerCars.do?method=add' onclick="javascript:location.href=tawPartnerCars.do?method=add">添加车辆信息</a>&emsp;&emsp; -->
	<input class="button" type="button" onclick="delselcar()" value="删除所选信息"/>
</html:form>
</fmt:bundle>
<script type="text/javascript">
function addcar(){
location.href="tawPartnerCars.do?method=add";
}
function delone(sel){
	if(confirm("确定要删除吗?")==true){
		location.href="${app}/partner/baseinfo/tawPartnerCars.do?method=remove&id="+sel;
	}
}
  </script>
<%@ include file="/common/footer_eoms.jsp"%>