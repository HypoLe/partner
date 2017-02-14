<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%response.setHeader("cache-control","public"); %>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
	
<script type="text/javascript">

var id = '${id}';
var specialty = '${specialty}';
var resType = '${resType}';

function relation(){
	
	window.location='${app}/partner/deviceInspect/inspectMapping.do?method=waitRelationInspectDeviceTypeList&id='+id+'&specialty='+specialty+'&resType='+resType;
}

function cancle(){
 var string="";
 var resid = '${resid}';
 var mappingId = '${pnrInspectMapping.id}';
 var objName= document.getElementsByName("checkbox11");
        for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string+=objName[i].value;   
                string+="|";
                }
        }  
        if(confirm("确定取消所选项的关联？")){
        	if(string == null || "" ==  string){
        		alert("请选择需取消关联的网络资源");
        		return false;
        	}
        	//alert(string);
		 	location.href="${app}/partner/deviceInspect/inspectMapping.do?method=calncleRelationInspectDevice&id="+id+"&specialty="+specialty+"&resType="+resType+"&seldelcar="+string;
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

</script>

<table id="sheet" class="listTable">
  <tr>
    <td class="label">资源名称</td>
    <td class="content">${pnrResConfig.resName }</td>
    <td class="label">代维资源专业</td>
    <td class="content">
    	<eoms:id2nameDB id="${pnrResConfig.specialty}" beanId="ItawSystemDictTypeDao" />	
    </td>
  </tr>
  <tr>
     <td class="label">代维资源类别</td>
    <td class="content">
    	<eoms:id2nameDB id="${pnrResConfig.resType}" beanId="ItawSystemDictTypeDao" />
    </td>
    <td class="label">代维资源级别</td>
    <td class="content">
    	<eoms:id2nameDB id="${pnrResConfig.resLevel}" beanId="ItawSystemDictTypeDao" />
    </td>
  </tr>
  <tr>
     <td class="label">地市</td>
    <td class="content">
    	<eoms:id2nameDB id="${pnrResConfig.city}" beanId="tawSystemAreaDao" />
    </td>
    <td class="label">区县</td>
    <td class="content">
    	<eoms:id2nameDB id="${pnrResConfig.country}" beanId="tawSystemAreaDao" />
    </td>
  </tr>
</table>
<!-- 
</br>
<form action="${app}/partner/deviceInspect/inspectMapping.do?method=findRelationNetResourceList" method="post" id="taskOrderForm" name="taskOrderForm" >
	<table id="taskOrderTable" class="listTable">
		<tr>
			
			<td class="label">设备名称
				<input type="hidden" name="id" value="${pnrResConfig.id}" />
				<input type="hidden" name="specialty" value="${pnrResConfig.specialty}" />
				<input type="hidden" name=""resType value="${pnrResConfig.resType}" />
			</td>
			<td class="content"><input class="text medium" name="netresNameStringEqual"/></td>
			<td class="label">网络资源专业</td>
			<td class="content"><input class="text medium" name="deviceSpecialtyNameStringEqual"/></td>
		</tr>
		<tr>
			<td class="label">网络资源设备类型</td>
			<td class="content"><input class="text medium" name="deviceTypeNameNameStringEqual"/></td>
			<td class="label"></td>
			<td class="content"></td>
		</tr>
		<tr>
			<td colspan="4"><input type="submit" class="btn" value="查询"/></td>
		</tr>
	</table>
</form> -->
</br>
<input type="button" value="查看网络资源类型" class="btn" onclick="relation()"/>
<input type="button" value="取消关联项" class="btn" onclick="cancle()"/>
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pageSize}" requestURI="inspectMapping.do?" 
		partialList="true" size="${size}">
		
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
        	<input type="checkbox" name="checkbox11" id="checkbox11"  value="${list.id},${list.netResId},${list.netresTableName}"/>
    	</display:column>
		
		<display:column title="网络资源专业">
			${list.deviceSpecialtyName }
		</display:column>
		<display:column title="网络资源设备类型">
			${list.deviceTypeName }
		</display:column>
		<display:column title="网络资源名称">
			${list.netresName }
		</display:column>
		<display:column title="类别区分值">
			<eoms:id2nameDB id="${list.netresFieldValue}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
	</display:table>
</logic:present>
</br>
<input type="button" class="btn" value="返回" onclick="javascript:history.back();" />
<%@ include file="/common/footer_eoms.jsp"%>