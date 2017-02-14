<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
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
function relateion(){
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
        if(confirm("确定关联所选项？")){
        	if(string == null || "" ==  string){
        		alert("请选择需关联的网络资源");
        		return false;
        	}
        	jq("#resid").val(resid);
        	jq("#mappingId").val(mappingId);
        	jq("#seldelcar").val(string);
        	jq("#taskOrderForm").submit();
		 	//location.href="${app}/partner/deviceInspect/inspectMapping.do?method=relationInspectDevice&resid="+resid+"&mappingId="+mappingId+"&seldelcar="+string;
		 }else{
		 return false;
		 }
}	
	
</script>
<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pageSize}" requestURI="inspectMapping.do" 
		partialList="true" size="${resultSize}">
		
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
        	<input type="checkbox" name="checkbox11" id="checkbox11"  value="${list.id}~${list.filedname}~<c:if test="${(not empty pnrInspectMapping.netresFieldName) and (pnrInspectMapping.netresFieldName != 'N/A')}" var="result">${Table2NetResourceMapping[pnrInspectMapping.netresTableName].fieldName}</c:if><c:if test="${result eq false}">N/A</c:if>~<c:if test="${(not empty pnrInspectMapping.netresFieldValue) and (pnrInspectMapping.netresFieldValue != 'N/A')}">${pnrInspectMapping.netresFieldValue}</c:if><c:if test="${!result}">N/A</c:if> "/>
    	</display:column>
		
		<display:column title="网络资源专业">
			${pnrInspectMapping.deviceSpecialtyName }
		</display:column>
		<display:column title="网络资源设备类型">
			${pnrInspectMapping.deviceTypeName }
		</display:column>
		<display:column title="类别区分值">
			<eoms:id2nameDB id="${pnrInspectMapping.netresFieldValue}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="设备名称">
			${list.filedname}
		</display:column>
		
</display:table>

</br>

<form action="${app}/partner/deviceInspect/inspectMapping.do?method=relationInspectDevice&resid" method="post" id="taskOrderForm" name="taskOrderForm" >
	<input type="hidden" id="resid" name="res" />
	<input type="hidden" id="mappingId" name="mappingId" />
	<input type="hidden" id="seldelcar" name="seldelcar" />
</form>

<input class="button" type="button" onclick="relateion()" value="关联所选项"/>

<input type="button" class="btn" value="返回" onclick="javascript:history.back();" />

<%@ include file="/common/footer_eoms.jsp"%>