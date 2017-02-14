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
	function openImport(handler){
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
	}
	
		function getInspectType(obj){
			var tr = obj.parentNode.parentNode;
			var row = tr.rowIndex-1;
			var key = jq(obj).val();
			var map = '${map}';
			var devicejson ='${json}';
			var json = eval('(' + devicejson + ')');
			var object = json[0][key];
			var htmlStr = '<select class="select" id="deviceType" name="deviceTypeStringEqual" ><option value="" selected>请选择</option>';
			for(var i = 0 ;i < object.length ;i++){
				htmlStr += '<option value="'+object[i].resourceMark+'">'+object[i].resourceName+'</option>';
			}
			htmlStr += '</select>'
			jq("#tddeviceType").html(htmlStr);
			//jq(tr).find("#deviceSpecialtyName").val(jq(obj).find('option:selected').text());
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
	
		function delselcar(){
		var string="";
		 var objName= document.getElementsByName("checkbox11");
		        for (var i = 0; i<objName.length; i++){
		                if (objName[i].checked==true){ 
		                string+=objName[i].value.trim();   
		                string+="|";
		                }
		        }  
		        if(confirm("确认要删除吗？")){
		        	if(string == null || "" ==  string){
		        		alert("请选择要删除的设备巡检项");
		        		return false;
		        	}
				 	location.href="${app}/partner/deviceInspect/inspectMapping.do?method=deleteInspectDeviceMapplist&&ids="+string;
				 }else{
				 return false;
				 }
		}

</script>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="${app}/partner/deviceInspect/inspectMapping.do?method=gotoInspectDeviceMapplist" method="post">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">
					代维资源专业
				</td>
				<td>
					<eoms:comboBox  name="specialtyStringEqual" id="specialty"  defaultValue="" sub="inspectType"
		        			initDicId="11225" alt="allowBlank:false" styleClass="select" />
				</td>
				<td class="label">
					代维资源类别
				</td>
				<td >
					<eoms:comboBox name="inspectTypeStringEqual" defaultValue="" id="inspectType" initDicId=""  styleClass="select" /> 
				</td>
			</tr>
			<tr>		
				<td class="label">
					网络资源专业
				</td>
				<td>
					<select id="deviceSpecialty" name="deviceSpecialtyStringEqual" class="select" onchange="getInspectType(this)">
						<option value="" >请选择</option>
						<c:forEach var="res" items="${map}">
							<c:set var="i" value="${res.key }"></c:set>
							<option value="${res.key }">${ keyMap[i] }</option>
						</c:forEach>
					</select>
				</td>
				<td class="label">
					网络资源类别
				</td>
				<td id='tddeviceType'>
					<select name="deviceTypeStringEqual" id="deviceType" class="select">
						<option value="">请选择</option>
					</select>
				</td>
			</tr>
	</table>
	
		<html:submit styleClass="btn" property="method.approvalList"
			styleId="method.approvalList" value="查询"></html:submit>
	</form>
	</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>


<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pageSize}" requestURI="inspectMapping.do?" 
		partialList="true" size="${resultSize}">
		
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
        	<input type="checkbox" name="checkbox11" id="checkbox11"  value="${list.id} "/>
    	</display:column>
		
		<display:column title="代维资源专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="代维资源类别">
			<eoms:id2nameDB id="${list.inspectType}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="网络资源专业">
			${list.deviceSpecialtyName }
		</display:column>
		<display:column title="网络资源设备类型">
			${list.deviceTypeName }
		</display:column>
		<display:column title="类别区分">
			<c:if test="${(not empty list.netresFieldName) and (list.netresFieldName != 'N/A')}" var="result">
					${Table2NetResourceMapping[list.netresTableName].fieldChName }
				</c:if>				
				<c:if test="${result eq false}">
					N/A
				</c:if>		
		</display:column>
		<display:column title="类别区分值">
			<c:if test="${(not empty list.netresFieldName) and (list.netresFieldName != 'N/A')}">
					${list.netresFieldValue }
				</c:if>				
				<c:if test="${!result}">
					N/A
				</c:if>	
		</display:column>
		<%-- <display:column sortable="true" headerClass="sortable" title="编辑">
			<a href="${app}/partner/deviceInspect/inspectMapping.do?method=gotoeditInspectDeviceMapping&id=${list.id}" >
	        	<img src="${app}/images/icons/edit.gif" />
	        </a>
		</display:column> --%>
		<display:column title="删除">
			<a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
	        {window.location.href = '${app}/partner/deviceInspect/inspectMapping.do?method=deleteInspectDeviceMapplist&&id=${list.id}';}" title="删除"/>
	        	<img src="${app}/images/icons/icon.gif" />
	        </a>
		</display:column>
	</display:table>
</logic:present>
	</br>
	<input class="button" type="button" onclick="delselcar()" value="删除所选项"/>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
