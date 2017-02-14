<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var inputType='${inspectTemplateItem.inputType}';
var i=2 ;
var jq=jQuery.noConflict();
jq(function(){
		var v = new eoms.form.Validation({form:'templateItemForm'});
		v.custom = function(){ 
			var inputTypes = document.getElementsByName("inputType2");
			var minValues = document.getElementsByName("minValue");
			var maxValues = document.getElementsByName("maxValue");
			var number =0; //用来记录第几个数值型的输入方式
			
			for(var j=0;j<inputTypes.length;j++){
				if(inputTypes[j].value=='1222402'){   //数值
					if(minValues[number].value.trim()=='' || maxValues[number].value.trim()==''){
						var m = j+1;
						alert("请输入第"+m+"行的最小值和最大值");
						return false;
					}else{
						var m = j+1;
						if(minValues[number].value-0<0){
							alert("第"+m+"行的最小值必须大于等于零");
			                 
			                return false;  
						}else if(minValues[number].value-0>=0){
						
						}else{
							alert("第"+m+"行的最小值必须是数字");
			                 
			                return false; 
						}
						
						if(maxValues[number].value-0<0){
							alert("第"+m+"行的最大值必须大于等于零");
			                 
			                return false;  
						}else if(maxValues[number].value-0>=0){
						
						}else{
							alert("第"+m+"行的最大值必须是数字");
			                 
			                return false; 
						}
						
						if(minValues[number].value-0>maxValues[number].value-0){
							alert("第"+m+"行的最小值不能大于最大值");
			                 
			                return false; 
						}
					}
					number = number+1;
				}
				
				
			}

	    	return true;
	    };
		jq("#dictTypeName").hide();
		jq("#hidenRadioValue").hide();
		changeInputType(jq("#inputType"));
		refreshRadioValue(jq("#inputType"));
		//initValues();
		
		if('text' == inputType || 'multiple'==inputType || 'custom'==inputType){
		 jq("#minfont").attr("disabled",true);
		 jq("#minfont").hide();
		 jq("#normalRange").val("N\\A");
		 jq("#normalRange").attr('readnoly','readnoly');
		 jq("#dictId").attr("disabled",true);
		}
		if('radio' == inputType){
			jq("#normalRange").attr("disabled",true);
			jq("#normalRange").hide();
			jq("#dictId").show();
		}
		
		if('number' == inputType){
			jq("#normalRange").attr("disabled",true);
			jq("#normalRange").hide();
			jq("#dictId").hide();
			 jq("#dictId").attr("disabled",true);
		}
		if('multiple'==inputType){
			jq("#dictId").show();
			jq("#dictId").attr("disabled",false);
		}
		if('text' == inputType || 'custom'==inputType){
			 jq("#defaultValue").hide();
			 jq("#defaultValue").val('');
		 	 jq("#defaultValue").attr("alt","allowBlank:true");
		}
		
});

//验证数字的方法
function vaidateNum(){
	
	
	var inputTypes = document.getElementsByName("inputType2");
	var minValues = document.getElementsByName("minValue");
	var maxValues = document.getElementsByName("maxValue");
	var number =0; //用来记录第几个数值型的输入方式
	
	for(var j=0;j<inputTypes.length;j++){
		if(inputTypes[j].value=='1222402'){   //数值
			if(minValues[number].value.trim()=='' || maxValues[number].value.trim()==''){
				var m = j+1;
				alert("请输入第"+m+"行的最小值和最大值");
				return false;
			}else{
				var m = j+1;
				if(minValues[number].value-0<0){
					alert("第"+m+"行的最小值必须大于等于零");
	                event.preventDefault();  
	                return false;  
				}else if(minValues[number].value-0>=0){
				
				}else{
					alert("第"+m+"行的最小值必须是数字");
	                event.preventDefault();  
	                return false; 
				}
				
				if(maxValues[number].value-0<0){
					alert("第"+m+"行的最大值必须大于等于零");
	                event.preventDefault();  
	                return false;  
				}else if(maxValues[number].value-0>=0){
				
				}else{
					alert("第"+m+"行的最大值必须是数字");
	                event.preventDefault();  
	                return false; 
				}
				
				if(minValues[number].value-0>maxValues[number].value-0){
					alert("第"+m+"行的最小值不能大于最大值");
	                event.preventDefault();  
	                return false; 
				}
			}
			number = number+1;
		}
		
		
	}
	
	
}
	
var isFirefox = false;
var isFirst = false;
function changeInputType(obj){
	try{
	i=i-1;
	var value = obj.value;
	
	
	//alert(value);
	if('1222401' == value){//文本
		jq(document.getElementsByName("dictId")[0]).attr("disabled",true);
		jq(document.getElementsByName("dictTypeName")[0]).attr("disabled",true);
		jq(document.getElementsByName("hidenRadioValue")[0]).attr("disabled",true);
		jq(document.getElementsByName("dictId")[0]).hide();
		jq(document.getElementsByName("dictTypeName")[0]).hide();
		jq(document.getElementsByName("hidenRadioValue")[0]).hide();
		inputType='text';
		jq(document.getElementsByName("tdNormalRange")[0]).html("<input class=\"text\" value=\"N\\A\" name=\"normalRange\" readonly=\"readonly\" >");
		//jq(document.getElementsByName("tdDefaultValue")[0]).html("<td><input class=\"text\" type=\"text\" name=\"defaultValue\" id=\"defaultValue\" alt=\"allowBlank:false\" /></td>");
		jq(document.getElementsByName("tdDefaultValue")[0]).html("<td><input class=\"text\" type=\"hidden\" name=\"defaultValue\" id=\"defaultValue\"  /></td>");
		return;
	}
	if('1222402' == value){//数值
		jq(document.getElementsByName("dictId")[0]).attr("disabled",true);
		jq(document.getElementsByName("dictTypeName")[0]).attr("disabled",true);
		jq(document.getElementsByName("hidenRadioValue")[0]).attr("disabled",true);
		jq(document.getElementsByName("dictId")[0]).hide();
		jq(document.getElementsByName("dictTypeName")[0]).hide();
		jq(document.getElementsByName("hidenRadioValue")[0]).hide();
		inputType='number';
		jq(document.getElementsByName("tdDefaultValue")[0]).html("<td><input class=\"text\" type=\"text\" name=\"defaultValue\" id=\"defaultValue\" alt=\"allowBlank:false\" /></td>");
		jq(document.getElementsByName("tdNormalRange")[0]).html("<font size=\"2px\">最小值&nbsp:&nbsp<input class=\"text\" type=\"text\" id=\"minValue\" name=\"minValue\" alt=\"allowBlank:false\"/>最大值&nbsp:&nbsp<input class=\"text\" type=\"text\" id=\"maxValue\" name=\"maxValue\" alt=\"allowBlank:false\"/></font>");
		return;
	}
	if('1222403' == value){//单选
		jq(document.getElementsByName("dictId")[0]).attr("disabled",false);
		jq(document.getElementsByName("dictTypeName")[0]).attr("disabled",false);
		jq(document.getElementsByName("hidenRadioValue")[0]).attr("disabled",false);
		jq(document.getElementsByName("dictTypeName")[0]).show();
		jq(document.getElementsByName("hidenRadioValue")[0]).show();
		jq(document.getElementsByName("tdNormalRange")[0]).html("");
		jq(document.getElementsByName("dictId")[0]).show();
		isFirst = true;
		inputType='radio';
		getValues(i);
		if(getOs() == 'Firefox'){
			isFirefox = true;
			jq(document.getElementsByName("hidenRadioValue")[0]).show();
			
		}
		return;
	}
	if('1222404' == value){//多选
		jq(document.getElementsByName("dictId")[0]).attr("disabled",false);
		jq(document.getElementsByName("dictTypeName")[0]).attr("disabled",false);
		jq(document.getElementsByName("hidenRadioValue")[0]).attr("disabled",false);
		jq(document.getElementsByName("dictId")[0]).show();
		jq(document.getElementsByName("dictTypeName")[0]).show();
		jq(document.getElementsByName("hidenRadioValue")[0]).show();
		inputType='multiple';
		jq(document.getElementsByName("tdNormalRange")[0]).html("<input class=\"text\" value=\"N\\A\" name=\"normalRange\" readonly=\"readonly\" >");
		jq(document.getElementsByName("tdDefaultValue")[0]).html("<td><input class=\"text\" type=\"text\" name=\"defaultValue\" id=\"defaultValue\" alt=\"allowBlank:false\" /></td>");
		return;
	}
	
	if('1222406' == value){//自定义 2016-10-08添加
		jq(document.getElementsByName("dictId")[0]).attr("disabled",true);
		jq(document.getElementsByName("dictTypeName")[0]).attr("disabled",true);
		jq(document.getElementsByName("hidenRadioValue")[0]).attr("disabled",true);
		jq(document.getElementsByName("dictId")[0]).hide();
		jq(document.getElementsByName("dictTypeName")[0]).hide();
		jq(document.getElementsByName("hidenRadioValue")[0]).hide();
		inputType='custom';
		jq(document.getElementsByName("tdNormalRange")[0]).html("<input class=\"text\" value=\"N\\A\" name=\"normalRange\" readonly=\"readonly\" >");
		//jq(document.getElementsByName("tdDefaultValue")[0]).html("<td><input class=\"text\" type=\"text\" name=\"defaultValue\" id=\"defaultValue\" alt=\"allowBlank:false\" /></td>");
		jq(document.getElementsByName("tdDefaultValue")[0]).html("<td><input class=\"text\" type=\"hidden\" name=\"defaultValue\" id=\"defaultValue\"  /></td>");
		return;
	}
	//inputType='';
	jq(document.getElementsByName("dictId")[0]).hide();
	}catch(e){};
}


var htmlStr = '';
var htmlStr2 = '';
var htmlStr3 = '';

function refreshRadioValue(obj){
		//var tr = obj.parentNode.parentNode;
		getValues(0);	
}

function getValues(num){
	var parentDict = jq(document.getElementsByName("dictId")[num*1]).val();
	if(parentDict!=''){
	var url="<%=request.getContextPath()%>/partner/inspect/inspectTemplateManger.do?method=getDictValues&dict="+parentDict;
	htmlStr = '<select id="normalRange" name="normalRange" class="select"  alt="allowBlank:false" >';
	htmlStr = htmlStr + '<option value="">请选择</option>';
	
	htmlStr2 = '<select id="defaultValue" name="defaultValue" class="select"  alt="allowBlank:false" >';
	htmlStr2 = htmlStr2 + '<option value="">请选择</option>';
	
	htmlStr3 = '<select id="defaultValue" name="defaultValue" multiple="multiple" class="select"  alt="allowBlank:false" >';
	htmlStr3 = htmlStr3 + '<option value="">请选择</option>';
	
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) {
			res = result.responseText;
			var json = eval(res);
			if(inputType == 'radio'){
				if(json.length==0)return;
				var defaultValue = '${inspectTemplateItem.defaultValue}';
				var normalRange = '${inspectTemplateItem.normalRange}';
				for(var i=0;i<json.length;i++){
					if(json[i].dictId+"" == normalRange){
						htmlStr = htmlStr+'<option value="'+json[i].dictId+'" selected>'+json[i].name+'</option>';
					}else{
						htmlStr = htmlStr + '<option value="'+json[i].dictId+'">'+json[i].name+'</option>';
					}
					
					if(json[i].dictId+"" == defaultValue){
						htmlStr2 = htmlStr2+'<option value="'+json[i].dictId+'" selected>'+json[i].name+'</option>';
					}else{
						htmlStr2 = htmlStr2 + '<option value="'+json[i].dictId+'">'+json[i].name+'</option>';
					}
				}
				htmlStr = htmlStr + '</select>';
				htmlStr2 = htmlStr2 + '</select>';
					jq(document.getElementsByName("tdNormalRange")[num]).html(htmlStr);
					jq(document.getElementsByName("tdDefaultValue")[num]).find("#defaultValue").attr("disabled",true);
					jq(document.getElementsByName("tdDefaultValue")[num]).find("#defaultValue").hide();
					jq(document.getElementsByName("tdDefaultValue")[num]).html(htmlStr2);
					
			}else if(inputType == 'multiple'){
				if(json.length==0)return;
				var defaultValue = ${inspectTemplateItem.defaultValue}+"";
				for(var i=0;i<json.length;i++){
					if(json[i].dictId+"" == defaultValue){
						htmlStr3 = htmlStr3+'<option value="'+json[i].dictId+'" selected>'+json[i].name+'</option>';
					}else{
						htmlStr3 = htmlStr3 + '<option value="'+json[i].dictId+'">'+json[i].name+'</option>';
					}
				}
				htmlStr3 = htmlStr3 + '</select>';
				jq(document.getElementsByName("tdDefaultValue")[num]).html(htmlStr3);
			}
		},
		failure: function ( result, request) { 
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
		} 
	});
	}else{
		
	}
}



function initValues()
{    
	//var inputType = '${inputType}';
	//var inputType = document.getElementById('inputType').value;
	var normalRange = '${inspectTemplateItem.normalRange}';
	if(inputType=='radio'){
	 	isFirst = true;
		var parentDict = '${inspectTemplateItem.dictId}';
		if(parentDict!=''){
		var url="<%=request.getContextPath()%>/partner/inspect/inspectTemplateManger.do?method=getDictValues&dict="+parentDict;
		var htmlStr = '';
		var htmlStr2 = '';
		Ext.Ajax.request({
			url : url ,
			method: 'POST',
			success: function ( result, request ) {
				res = result.responseText;
				var json = eval(res);
				var defaultValue = ${inspectTemplateItem.defaultValue}+"";
				for(var i=0;i<json.length;i++){
					if(json[i].dictId+"" == defaultValue){
						htmlStr = htmlStr+'<input type="radio" id="dictNormalValue" name="dictNormalValue" value="'+json[i].dictId+'" checked="true"  />'+json[i].name;
					}else{
						htmlStr = htmlStr+'<input type="radio" id="dictNormalValue" name="dictNormalValue" value="'+json[i].dictId+'"  />'+json[i].name;
					}
				}	
				jq("#tdNormalRange").html(htmlStr);
				jq("#hidenRadioValue").hide();
				jq("#dictTypeName").show();
				jq("#dictId").val('${inspectTemplateItem.dictId}');
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});
	}else{
		
	}
	}
	else if(inputType=='number'){
		var normalRanges = normalRange.split('|');
		jq("#dictTypeName").show();
		jq("#dictTypeName").hide();
		jq("#dictId").hide();
		inputType='number';
		jq("#tdNormalRange").html("最小值&nbsp:&nbsp<input class=\"text\" value="+normalRanges[0]+" type=\"text\" id=\"minValue\" name=\"minValue\"/>&nbsp;&nbsp最大值&nbsp:&nbsp<input class=\"text\" type=\"text\" value="+normalRanges[1]+" id=\"maxValue\" name=\"maxValue\"/>");

		
	}
	else if(inputType=='text'){
	}
	else if(inputType=='multiple'){
		jq("#dictId").val('${inspectTemplateItem.dictId}');
		jq("#dictTypeName").show();
	}
}


function selectDevice(obj){
		var inspectTemplate = '${inspectTemplate}';
		var resType = '${resType}';
		var specialty = '${specialty}';
		var url = '${app}/partner/deviceInspect/inspectMapping.do?method=gotoSelectDevice&specialty='+specialty+'&resType='+resType;
        //window.open(url);
        tr = obj.parentNode.parentNode;
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		var arr = window.showModalDialog(url,window,sFeatures);
		if(arr != null){
		jq(tr).find("#deviceId").val(arr[0]);
		jq(tr).find("#bigitemName").val('网络资源-'+arr[1]+'-'+arr[2]);
		}
	}

</script>



<table class="formTable">
</table>

<div id="edit">
<form method="post" id="templateItemForm" name="templateItemForm" action="inspectTemplateManger.do?method=saveTemplateItem">
	<table  id="tab" class="table list" cellspacing="0" cellpadding="0">
		<thead>
		<tr>
			<th>
				设备类别*<input type="hidden" name="id" value="${inspectTemplateItem.id }" />
			</th>
			<th>
				巡检项目*
			</th>
			<th>
				检查内容*
			</th>
			<th>
				输入方式*
			</th>
			<th>
				正常值范围*<div style="width: 140px;height: 1px;"></div>
			</th>
			
			<th>
				默认值*<div style="width: 140px;height: 1px;"></div>
			</th>
			
			<th>
				 需上传照片数
			</th>
			
			<th style="display: none;">
				周期*
			</th>

		</tr>
		
		</thead>
		
		<tr id="templateItem">
		
			<td class="content">
			
			<c:choose>
				<c:when test="${inspectTemplateItem.deviceInspectFlag eq 1 }">
					<input class="text" type="text" name="bigitemName" readonly="readonly" onclick="selectDevice(this)"
					id="bigitemName" value="${inspectTemplateItem.bigitemName }" alt="allowBlank:false"/>
				</c:when>
				<c:otherwise>
					<input class="text" type="text" name="bigitemName"
					id="bigitemName" value="${inspectTemplateItem.bigitemName }" alt="allowBlank:false"/>
				</c:otherwise>
			</c:choose>
			
			</td>
			
			<td class="content">
			<input class="text" type="text" name="inspectItem"
					id="inspectItem" value="${inspectTemplateItem.inspectItem }" alt="allowBlank:false"/>
			</td>
	
			<td class="content">
			<textarea name="inspectContent" id="inspectContent"  style="width: 98%" class="textarea max" alt="allowBlank:false">${inspectTemplateItem.inspectContent }</textarea>
			<div style="width: 100px;height: 1px;"></div>
			</td>
	
			<td class="content" >
			<c:choose>
			<c:when test="${inspectTemplateItem.inputType eq 'text'}">
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" styleClass="select" id="inputType2" initDicId="12224" defaultValue="1222401"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
			</c:when>
			<c:when test="${inspectTemplateItem.inputType eq 'number'}">
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" styleClass="select" id="inputType2" initDicId="12224" defaultValue="1222402"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
			</c:when>
			<c:when test="${inspectTemplateItem.inputType eq 'radio'}">
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" sub="normalRange" styleClass="select" id="inputType2" initDicId="12224" defaultValue="1222403"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
			</c:when>
			<c:when test="${inspectTemplateItem.inputType eq 'multiple'}">
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" styleClass="select" id="inputType2" initDicId="12224" defaultValue="1222404"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
			</c:when>
			<c:when test="${inspectTemplateItem.inputType eq 'custom'}"><!-- 自定义，用于巡检项的特殊情况 2016-10-08添加 --> 
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" styleClass="select" id="inputType2" initDicId="12224" defaultValue="1222406"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
			</c:when>
			<c:otherwise>
			<eoms:comboBox onchange="changeInputType(this)" name="inputType2"  styleClass="select" id="inputType2" initDicId="12224" 
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
			</c:otherwise>
			</c:choose>
			<!--  -->
			<br/>
				
				<eoms:comboBox  name="dictId" id="dictId"  defaultValue="${inspectTemplateItem.dictId}" onchange="refreshRadioValue(this)" 
	        			initDicId="103" alt="allowBlank:false" styleClass="select" />	
	        			
			</td>
			
			<td class="content" id="tdNormalRange" name="tdNormalRange" style="width: 100px;">
				<input class="text" type="text" name="normalRange"
					id="normalRange" alt="allowBlank:false" value="${inspectTemplateItem.normalRange }"/>
					
				<font id="minfont">最小值<input class="text" value="${fn:substringBefore(inspectTemplateItem.normalRange,"|")}" type="text" id="minValue" name="minValue"/>
				最大值<input class="text" type="text" value="${fn:substringAfter(inspectTemplateItem.normalRange,"|")} " id="maxValue" name="maxValue"/>	</font>
			</td>
			
			<td class="content" id="tdDefaultValue" name="tdDefaultValue">
				<input class="text" type="text" name="defaultValue"
					id="defaultValue" alt="allowBlank:false" value="${inspectTemplateItem.defaultValue }" />
			</td>
			
			<td class="content">
				<input class="text" type="text" name="pictureNum" alt="re:/^((\d{1}?)|(10))?$/,re_vt:'请输入10以内的正整数'" value="${inspectTemplateItem.pictureNum}" />
			</td>
			
			<td id="tdcycle" class="content" style="display: none;">
				<eoms:comboBox defaultValue="${inspectTemplateItem.cycle }" name="cycle" id="cycle" initDicId="1122001"
					alt="allowBlank:true,vtext:'请选择(单选字典)...'"  />
			</td>
		</tr>
		
	</table>
	<br/>
	<input  type="submit" value="保存" class="btn" id="editTemplateItema" /> 
	<input type="button" value="返回" onClick="javascript:window.history.back();" class="btn">
</form>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">

function mobileLogin(j_username,j_password){
	document.getElementsByName("j_username")[0].value=j_username;
	document.getElementsByName("j_password")[0].value=j_password;
	document.getElementsByName("form2")[0].submit();
}

</script>
