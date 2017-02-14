<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
  <title><fmt:message key="webapp.name" /></title>
 <base target="_self"/>
<%@ include file="/common/meta.jsp"%>
  <script type="text/javascript" charset="utf-8" src="${app}/scripts/local/zh_CN.js"></script>  
  <script type="text/javascript" charset="utf-8" src="${app}/scripts/base/eoms.js"></script>
  <script type="text/javascript">eoms.appPath = "${app}";</script>
  <link rel="stylesheet" type="text/css" href="${app}/styles/${theme}/theme.css" />
<%@ include file="/common/extlibs.jsp"%>
  <script type="text/javascript" src="${app}/scripts/form/Options.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/combobox.js"></script>
  <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/validation.js"></script>
<%@ include file="/common/body.jsp"%>


<%@ page language="java" pageEncoding="UTF-8" %>
<%response.setHeader("cache-control","public"); %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


    <link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

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
	
	function chooseRes(){
			var res= document.getElementsByName("checkbox11");
        	for (var i = 0; i<res.length; i++){
                if (res[i].checked==true){ 
                
	                var resValue = res[i].value;
	                var arr = resValue.split(',');
	                arr[1] = '网络资源-' + arr[1];
	                
	                window.returnValue=arr;
	               /* if(eoms.isIE){
						window.dialogArguments.setRes(resValue);
					}else{
						window.opener.setRes(resValue);
					}*/
					//alert('选择设备成功');
					window.close();
                }
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
	<form ${app}/partner/deviceInspect/inspectMapping.do?method=gotoSelectDevice" method="post">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">
					网络资源专业
					<input type="hidden" value="${specialty }" name='specialty' />
					<input type="hidden" value="${inspectType }" name='resType' />
				</td>
				<td>
						<eoms:comboBox name="deviceSpecialtyStringEqual" id="deviceSpecialty" styleClass="select"
							sub="deviceType" defaultValue="${deviceSpecialtyStringEqual}" initDicId="12108"
							alt="allowBlank:false" />
				</td>
				<td class="label">
					网络资源类别
				</td>
				<td id='tddeviceType'>
	 				<eoms:comboBox name="deviceTypeStringEqual" defaultValue="${deviceTypeStringEqual}"
							id="deviceType" initDicId="${deviceSpecialtyStringEqual}" alt="allowBlank:false" styleClass="select" />
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
		<display:column title="选择">
			<input type="radio" name="checkbox11" id="checkbox11"  value="${list.id},${list.deviceSpecialtyName }-${list.deviceTypeName }-<eoms:id2nameDB id="${list.netresFieldValue}" beanId="ItawSystemDictTypeDao" />"/>
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
		<display:column title="网络资源类型">
			${list.deviceTypeName }
		</display:column>
		<display:column title="类别区分值">
			<eoms:id2nameDB id="${list.netresFieldValue}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
	</display:table>
	</br>
	<input class="button" type="button" onclick="chooseRes()" value="确定"/>
</logic:present>
	</br>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
