<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">

var jq=jQuery.noConflict();
Ext.onReady(function(){
    v = new eoms.form.Validation({form:'taskOrderForm'});
    /*v.custom = function(){ 
    	return true;
    };*/
   getInspectType();
   getdeviceType();
});

function getInspectType(){
	var key = jq("#deviceSpecialty").val();
	var map = '${map}';
	var devicejson ='${json}';
	var json = eval('(' + devicejson + ')');
	var object = json[0][key];
	var htmlStr2 = '<option value="" >请选择</option>';
	var htmlStr = '<select class="select" id="deviceType" name="deviceType" onchange="getdeviceType()" alt="allowBlank:false"><option value="" >请选择</option>';
	for(var i = 0 ;i < object.length ;i++){
		if('${pnrInspectMapping.deviceType}' == object[i].resourceMark){
			htmlStr += '<option value="'+object[i].resourceMark+'" selected="selected">'+object[i].resourceName+'</option>';
			htmlStr2 += '<option value="'+object[i].resourceMark+'" selected="selected">'+object[i].resourceName+'</option>';
		}else{
			htmlStr += '<option value="'+object[i].resourceMark+'">'+object[i].resourceName+'</option>';
			htmlStr2 += '<option value="'+object[i].resourceMark+'">'+object[i].resourceName+'</option>';
		
		}
	}
	htmlStr += '</select>'
	//document.getElementsByName("deviceType")[row].innerHTML="";
	//jq(document.getElementsByName("deviceType")[row]).html("");
	
	if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
		jq(document.getElementsByName("deviceType")[0]).html(htmlStr2);
    }else{
		jq(document.getElementsByName("tddeviceType")[0]).html(htmlStr);
    }
	
	jq("#deviceSpecialtyName").val(jq("#deviceSpecialty").find('option:selected').text());
}

function getdeviceType(){
	
	var deviceSpecialty = jq("#deviceSpecialty").val();
	var deviceType = jq("#deviceType").val();
	var devicejson ='${json}';
	
	var json = eval('(' + devicejson + ')');
	var htmlStr = '<select name="netresFieldValue" id="netresFieldValue"><option value="">请选择</option>';
	for(var j  = 0; j< json[0][deviceSpecialty].length;j++){
	 var mapdeviceType = json[0][deviceSpecialty][j].resourceMark;
	 if(deviceType == mapdeviceType){
	 	var arr = json[0][deviceSpecialty][j].filedList;
	 	for(var i=0;i<arr.length;i++ ){
	 		if('${pnrInspectMapping.netresFieldValue}' == arr[i]){
	 			htmlStr += '<option value="'+arr[i]+'" selected="selected">'+arr[i]+'</option>';
	 		}else{
		 		htmlStr += '<option value="'+arr[i]+'">'+arr[i]+'</option>';
	 		}
	 	}
		 jq("#netresTableName").val(json[0][deviceSpecialty][j].tableName);
		 jq("#netresFieldName").val(json[0][deviceSpecialty][j].fieldName);
	 }
	}
	htmlStr += "</select>";
	//jq(tr).find("#netresFieldValue").html("");
	jq("#tdnetresFieldValue").html(htmlStr);
	jq("#deviceTypeName").val(jq("#deviceType").find('option:selected').text());
}


</script>
 
<br/>
	
    
<form action="${app}/partner/deviceInspect/inspectMapping.do?method=editInspectDeviceMapping" method="post" id="taskOrderForm" name="taskOrderForm" >
	<input type="hidden" name="id" value="${pnrInspectMapping.id }" />
	<input type="hidden" name="deviceSpecialtyName" id="deviceSpecialtyName" />
	<input type="hidden" name="deviceTypeName" id="deviceTypeName" />
	<table class="table list" cellpadding="0" cellspacing="0" id="tab">
		<thead>
			<tr><th>巡检专业</th>
				<th>代维资源类别</th>
				<th>网络资源专业</th>
				<th>网络资源类别</th>
				<th>网络资源名</th>
				<th>类别区分</th>
				<th>类别区分值</th>
			</tr>	
		</thead>
		<tr>
			<td id="tdspecialty">
			<eoms:comboBox  name="specialty" id="specialty" sub="inspectType" defaultValue="${pnrInspectMapping.specialty}"
	        			initDicId="11225" alt="allowBlank:false" styleClass="select" />
			</td>
			<td id="tdresourceTeype">
				<eoms:comboBox name="inspectType" defaultValue="${pnrInspectMapping.inspectType}" id="inspectType" initDicId="${pnrInspectMapping.specialty}" alt="allowBlank:false"  styleClass="select" /> 
			</td>
			<td>
				<select id="deviceSpecialty" name="deviceSpecialty" onchange="getInspectType()" alt="allowBlank:false">
					<option value="" >请选择</option>
					<c:forEach var="res" items="${map}">
						<c:set var="i" value="${res.key }"></c:set>
						<option value="${res.key }" <c:if test="${res.key eq pnrInspectMapping.deviceSpecialty }">selected='selected'</c:if> >${ keyMap[i] }</option>
					</c:forEach>
				</select>
				
			</td>
			<td id='tddeviceType'>
				<select class="select" id="deviceType" name="deviceType" onchange="getdeviceType()" alt="allowBlank:false">
					<option value="" >请选择</option>
				</select>
				
			</td>
			<td >
				<input type="text" readonly="readonly" class="text" name="netresTableName" id="netresTableName" />
			</td>
			<td >
				<input type="text" readonly="readonly" class="text" name="netresFieldName" id="netresFieldName"/>
			</td>
			<td id="tdnetresFieldValue">
				<select name="netresFieldValue" id="netresFieldValue">
					<option value="">请选择</option>
				</select>
			</td>

		</tr>
	</table>
	</br>
	<input type="submit" value="提交" class="btn" />
</form>

<%@ include file="/common/footer_eoms.jsp"%>