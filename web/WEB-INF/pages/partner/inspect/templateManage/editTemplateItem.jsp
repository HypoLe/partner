<%--参考福建inspectItemForm.jsp---%>
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
var type = '${type}';
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
		jq("#dictId").attr("disabled",true);
		jq("#dictId").hide();
		jq("#dictSpan").hide();
		jq("#hidenRadioValue").hide();
		jq("#hideTr").hide();
		if('detail' == type){
			jq("#edit").hide();
		}
		if('new' == type){
			jq("#info").hide();
		}
		jq("#editTemplateItem").click(function() {
			//submit();
			submit()
		});
		jq("#goBack").click(function() {
			//submit();
			closewin();
		});
		jq("#editInspectTemplateItem").click(function() {
			jq("#edit").show();
			jq("#info").hide();
			jq("#reset").hide();
			jq("#editTemplateItem").val("保存");
		});
		jq("#reset").click(function() {
			jq(':input','#templateItemForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected'); 
		});
		changeInputType(jq("#inputType"));
		initValues();
		
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

function vaidateForm(){
	if(jq('#templateName').val() == ''||jq('#templateName').val() == null){
		jq('#info').html("名称不能为空");
		return false;
	}
	if(jq('#dept').val() == ''||jq('#dept').val() == null){
		jq('#info').html("制作单位不能为空");
		return false;
	}
	if(jq('#specialty').val() == ''||jq('#specialty').val() == null){
		jq('#info').html("专业不能为空");
		return false;
	}
	return true;
}

function submit(){

}

function submit2(){
		jq('#info').html("");
		if(!vaidateForm()){
			return false;
		}
		
		jq("#templateItemForm").attr("action","inspectTemplateManger.do?method=editTemplateItem");  
		if(confirm('您是否要提交该信息?')){
	  		Ext.Ajax.request({	
		    form: 'templateItemForm',
		    success: function(response){
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
		    	 	jq('#editTemplateItem').hide();
		    		jq('#reset').hide();
			    	 if(confirm('数据提交成功')){	
					  	   closewin();
					       return true;
					 	}else{
					       return false;
					     }
      			 }
		    	 if (jsonResult[0].success=='false'){
		    	 	Ext.Msg.alert('提示','数据提交失败'); 
      			 }
		    },
	     	failure: function(response) { 
                    Ext.Msg.alert('提示','数据提交失败'); 
            }   
		    
			});	
			
      		 return true;
 		}else{
       		return false;
    	 }
}
function closewin(){ 

		if("detail" == type){
			window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplateItemsList&id='+jq("#templateId").val();
			//window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplatesList';
			return;
		}
		if("new" == type){
			window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplateItemsList&id='+jq("#templateId").val();
			//window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplatesList';
			return;
		}
		//opener.location.reload();
		//self.opener=null; 
		//self.close();
		//window.opener.location.reload();
}
	
function clock(){
		if(i==2){
		var myDate = new Date();
			 alert("您于 "+myDate.toLocaleString()+" 提交了该模版");
		}
		i=i-1;
		document.title="本窗口将在"+i+"秒后自动关闭!"; 
		if(i>0)setTimeout("clock();",1000); 
		else closewin();
} 
var isFirefox = false;
var isFirst = false;
function changeInputType(obj){
	var tr1=obj.parentNode.parentNode;
	var i = tr1.rowIndex;
	var tr = document;
	try{
	i=i-1;
	jq(tr.getElementsByName("dictSpan")[0]).hide();
	var value = obj.value;
	
	
	//alert(value);

	if('1222401' == value){//文本
		jq(tr.getElementsByName("dictId")[i]).attr("disabled",true);
		jq(tr.getElementsByName("dictSpan")[i]).attr("disabled",true);
		jq(tr.getElementsByName("dictTypeName")[i]).attr("disabled",true);
		jq(tr.getElementsByName("hidenRadioValue")[i]).attr("disabled",true);
		jq(tr.getElementsByName("dictId")[i]).hide();
		jq(tr.getElementsByName("dictSpan")[i]).hide();
		jq(tr.getElementsByName("dictTypeName")[i]).hide();
		jq(tr.getElementsByName("hidenRadioValue")[i]).hide();
		inputType='text';
		jq(tr.getElementsByName("tdNormalRange")[i]).html("<input class=\"text\" value=\"N\\A\" name=\"normalRange\" readonly=\"readonly\" >");
		jq(tr.getElementsByName("tdDefaultValue")[i]).html("<td><input class=\"text\" type=\"text\" name=\"defaultValue\" id=\"defaultValue\" alt=\"allowBlank:false\" /></td>");
		return;
	}
	if('1222402' == value){//数值
		jq(tr.getElementsByName("dictId")[i]).attr("disabled",true);
		jq(tr.getElementsByName("dictSpan")[i]).attr("disabled",true);
		jq(tr.getElementsByName("dictTypeName")[i]).attr("disabled",true);
		jq(tr.getElementsByName("hidenRadioValue")[i]).attr("disabled",true);
		jq(tr.getElementsByName("dictId")[i]).hide();
		jq(tr.getElementsByName("dictSpan")[i]).hide();
		jq(tr.getElementsByName("dictTypeName")[i]).hide();
		jq(tr.getElementsByName("hidenRadioValue")[i]).hide();
		inputType='number';
		jq(tr.getElementsByName("tdDefaultValue")[i]).html("<td><input class=\"text\" type=\"text\" name=\"defaultValue\" id=\"defaultValue\" alt=\"allowBlank:false\" /></td>");
		jq(tr.getElementsByName("tdNormalRange")[i]).html("<font size=\"2px\">最小值&nbsp:&nbsp<input class=\"text\" type=\"text\" id=\"minValue\" name=\"minValue\" alt=\"allowBlank:false\"/>最大值&nbsp:&nbsp<input class=\"text\" type=\"text\" id=\"maxValue\" name=\"maxValue\" alt=\"allowBlank:false\"/></font>");
		return;
	}
	if('1222403' == value){//单选
		jq(tr.getElementsByName("dictId")[i]).attr("disabled",false);
		jq(tr.getElementsByName("dictSpan")[i]).attr("disabled",false);
		jq(tr.getElementsByName("dictTypeName")[i]).attr("disabled",false);
		jq(tr.getElementsByName("hidenRadioValue")[i]).attr("disabled",false);
		jq(tr.getElementsByName("dictSpan")[i]).show();
		jq(tr.getElementsByName("dictTypeName")[i]).show();
		jq(tr.getElementsByName("hidenRadioValue")[i]).show();
		jq(tr.getElementsByName("tdNormalRange")[i]).html("");
		jq(tr.getElementsByName("dictId")[i]).show();
		isFirst = true;
		inputType='radio';
		getValues(i);
		if(getOs() == 'Firefox'){
			isFirefox = true;
			jq(tr.getElementsByName("hidenRadioValue")[i]).show();
			
		}
		return;
	}
	if('1222404' == value){//多选
		jq(tr.getElementsByName("dictId")[i]).attr("disabled",false);
		jq(tr.getElementsByName("dictSpan")[i]).attr("disabled",false);
		jq(tr.getElementsByName("dictTypeName")[i]).attr("disabled",false);
		jq(tr.getElementsByName("hidenRadioValue")[i]).attr("disabled",false);
		jq(tr.getElementsByName("dictId")[i]).show();
		jq(tr.getElementsByName("dictSpan")[i]).show();
		jq(tr.getElementsByName("dictTypeName")[i]).show();
		jq(tr.getElementsByName("hidenRadioValue")[i]).show();
		inputType='multiple';
		jq(tr.getElementsByName("tdNormalRange")[i]).html("<input class=\"text\" value=\"N\\A\" name=\"normalRange\" readonly=\"readonly\" >");
		jq(tr.getElementsByName("tdDefaultValue")[i]).html("<td><input class=\"text\" type=\"text\" name=\"defaultValue\" id=\"defaultValue\" alt=\"allowBlank:false\" /></td>");
		return;
	}
	//inputType='';
	jq(tr.getElementsByName("dictId")[i]).hide();
	}catch(e){alert(e)};
}


var htmlStr = '';
function refreshRadioValue(obj){
		var tr = obj.parentNode.parentNode;
		getValues(tr.rowIndex-1);	
}

function getValues(num){
	var parentDict = jq(document.getElementsByName("dictId")[num*1]).val();
	if(parentDict!=''){
	var url="<%=request.getContextPath()%>/partner/inspect/inspectTemplateManger.do?method=getDictValues&dict="+parentDict;
	htmlStr = '<select id="normalRange" name="normalRange" class="select"  alt="allowBlank:false" >';
	htmlStr = htmlStr + '<option value="">请选择</option>';
	
	htmlStr2 = '<select id="defaultValue" name="defaultValue" class="select"  alt="allowBlank:false" >';
	htmlStr2 = htmlStr2 + '<option value="">请选择</option>';
	
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) {
			res = result.responseText;
			var json = eval(res);
			if(inputType == 'radio'){
				
				if(json.length==0)return;
				var defaultValue = ${inspectTemplateItem.defaultValue}+"";
				for(var i=0;i<json.length;i++){
					if(json[i].dictId+"" == defaultValue){
						htmlStr = htmlStr+'<option value="'+json[i].dictId+'" selected>'+json[i].name+'</option>';
						htmlStr2 = htmlStr2+'<option value="'+json[i].dictId+'" selected>'+json[i].name+'</option>';
					}else{
						htmlStr = htmlStr + '<option value="'+json[i].dictId+'">'+json[i].name+'</option>';
						htmlStr2 = htmlStr2 + '<option value="'+json[i].dictId+'">'+json[i].name+'</option>';
					}
				}
				if(isFirefox&&isFirst){
					isFirst = false;
					getValues(num);
				}else{
				htmlStr = htmlStr + '</select>';
					jq(document.getElementsByName("tdNormalRange")[num]).html(htmlStr);
					jq(document.getElementsByName("tdDefaultValue")[num]).find("#defaultValue").attr("disabled",true);
					jq(document.getElementsByName("tdDefaultValue")[num]).find("#defaultValue").hide();
					jq(document.getElementsByName("tdDefaultValue")[num]).html(htmlStr2);
					
				}
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
					
					/*alert(json[i].dictId == ${inspectTemplateItem.defaultValue});
				    htmlStr = htmlStr+'<input type="radio" id="dictNormalValue" name="dictNormalValue" value="'+json[i].dictId+'"  />'+json[i].name;
					if(normalRange.indexOf(json[i].dictId+',')>-1){
						htmlStr = htmlStr+'<input type="radio" id="dictNormalValue" name="dictNormalValue" value="'+json[i].dictId+'" checked="true"/>'+json[i].name;
					}else{
						htmlStr = htmlStr+'<input type="radio" id="dictNormalValue" name="dictNormalValue" value="'+json[i].dictId+'"  />'+json[i].name;
					}*/
					
				}	
				//alert(htmlStr);
				//Ext.DomHelper.append(document.getElementById('dictNormalRangeSpan'),htmlStr,true);
				jq("#tdNormalRange").html(htmlStr);
				jq("#hidenRadioValue").hide();
				jq("#dictSpan").show();
				//isFirst = true;
				jq("#hideTr").show();
				jq("#dictTypeName").show();
				jq("#dictId").val('${inspectTemplateItem.dictId}');
				//jq("#dictId").show();
				//alert(jq("#dictId").val());
				//document.getElementById('dictNormalRangeSpan').innerHTML = htmlStr;
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
		//document.getElementById('#numRangeLow').value=normalRanges[0];
		//document.getElementById('#numRangeTop').value=normalRanges[1];
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
		jq("#dictSpan").show();
		jq("#dictTypeName").show();
	}
}


function getOs(){//判断浏览器类型 
	var OsObject = ""; 
	if(navigator.userAgent.indexOf("MSIE")>0) {
	return "MSIE"; 
	} 
	if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
	return "Firefox"; 
	} 
	if(isSafari=navigator.userAgent.indexOf("Safari")>0) { 
	return "Safari"; 
	} 
	if(isCamino=navigator.userAgent.indexOf("Camino")>0){ 
	return "Camino"; 
	} 
	if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){ 
	return "Gecko"; 
	} 
}
/*
else if(navigator.userAgent.indexOf("Firefox")>0){
	document.getElementById('dictId').watch("value",  
    function (id, oldval, newval) {  
   		getValues();
      //console.log("foo." + id + " changed from " + oldval + " to " + newval);  
      return newval;  
   }); 




	document.getElementById('dictId').addEventListener("input",function(o){
	 if(o.propertyName!='value'){
             return;  //不是value改变不执行下面的操作   
          }else{
              attachMethod(o);
          }
	
	
	},false); 
}

*/


//依据当前行添加一个新行
function addRow(obj){

  	var trEl = obj.parentNode.parentNode;
  	jq(document.getElementsByName("tdDefaultValue")[trEl.rowIndex]).find("#defaultValue").attr("disabled",false);
	jq(document.getElementsByName("tdDefaultValue")[trEl.rowIndex]).find("#defaultValue").show();
  	var newTR = trEl.cloneNode(true);
  	newTR.id = "templateItem" + (++rowidm);
  	//newTR.id = "";
  	clearTable(newTR);
  	//newTR.style.display = "block";
    trEl.parentNode.appendChild(newTR);
   	trEl.getElementsByTagName("IMG")[0].style.display = "none";
   	if(trEl.id!='templateItem'){
   		trEl.getElementsByTagName("IMG")[1].style.display = "block";
   	}
   jq(newTR).find("#tdDefaultValue").html("<td><input class=\"text\" type=\"text\" name=\"defaultValue\" id=\"defaultValue\" alt=\"allowBlank:false\" /></td>");
   jq(newTR).find("#tdNormalRange").html("<input class=\"text\" type=\"text\" name=\"normalRange\" id=\"normalRange\" alt=\"allowBlank:false\" value=\"${inspectTemplateItem.normalRange }\"/>");
   newTR.getElementsByTagName("IMG")[0].style.display = "block";
   newTR.getElementsByTagName("IMG")[1].style.display = "block";
}

//用于在复制表格时
function clearTable(obj){
  //清除输入的值
  var inputs = obj.getElementsByTagName("INPUT");
  for(var i=0;i<inputs.length;i++){
    var t = inputs[i].type;
    if(t == "text" || t == "textarea" || t=="hidden"){
      inputs[i].value = "";
    }
    if(inputs[i].name=="dictId" || inputs[i].name=="minValue" || inputs[i].name=="maxValue"){
    jq(inputs[i]).hide();
    }
  }
  var spans = obj.getElementsByTagName("SPAN");
  var font = obj.getElementsByTagName("FONT");
  jq(spans[0]).hide();
  jq(font[0]).hide();
  var textareas = obj.getElementsByTagName("TEXTAREA");
  
 for(var i=0;i<textareas.length;i++){
      textareas[i].value = "";
  }
  var selects = obj.getElementsByTagName("SELECT");
  for(var i=0;i<selects.length;i++){   
  	  if(selects[i].name=="cycle"){
  	  
  	  }else{
	      selects[i].value = "";
	      if(selects[i].name=="inputType2"){
	      	changeInputType(selects[i]);
	      }else{
	      	 jq(selects[i]).hide();
	      }
      }
  }
  inputs = null;
  //清除URL参数
  var ifrs = obj.getElementsByTagName("IFRAME");
  for(var i=0;i<ifrs.length;i++){
    var s = ifrs[i].src;
    var index = s.indexOf("idList=");
    if(index!=-1){
    
      var temp = s.substring(index);
      var temp1 = s.substring(0,index);
      var temp2 = temp.substring(temp.indexOf("&"));
      ifrs[i].src = temp1+"idList="+temp2;
    }
  }  
}

function delRow(obj){
  var tr = obj.parentNode.parentNode;
  if(tr.tagName == "TR"){  	
    if(confirm("确定删除此行数据吗？")) { 
      --rowidm;
      var o = tr.parentNode;
      var row = tr.rowIndex;
      var sumRow = jq("#tab").find("tr").length-1;
      if(row==sumRow){ //说明当前操作的是是最后一行
      	
      	tr.previousSibling.getElementsByTagName("IMG")[0].style.display = "block";
      }
      tr.parentNode.deleteRow(tr.rowIndex);
    }
  }
}

</script>



<table class="formTable">
</table>

<div id="edit">
<form method="post" id="templateItemForm" name="templateItemForm" action="inspectTemplateManger.do?method=editTemplateItem">
	<table  id="tab" class="table list" cellspacing="0" cellpadding="0">
		<tr>
			<th>
				设备类别*<input type="hidden" value="${templateId }" />
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
				正常值范围*
			</th>
			
			<th>
				默认值*
			</th>
			
			<th>
				周期*
			</th>

			<th>
			  	操作
			</th>
		</tr>
		
		
		
		<tr id="templateItem">
		
			<td class="content">
			<input class="text" type="text" name="bigitemName"
					id="bigitemName" value="${inspectTemplateItem.bigitemName }" alt="allowBlank:false"/>
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
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" styleClass="select" id="inputType2" initDicId="12224" defaultValue="1222403"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
			</c:when>
			<c:when test="${inspectTemplateItem.inputType eq 'multiple'}">
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" styleClass="select" id="inputType2" initDicId="12224" defaultValue="1222404"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
			</c:when>
			<c:otherwise>
			<eoms:comboBox onchange="changeInputType(this)" name="inputType2"  styleClass="select" id="inputType2" initDicId="12224" 
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
			</c:otherwise>
			</c:choose>
			<br/>
				
				<eoms:comboBox  name="dictId" id="dictId"  defaultValue="${inspectTemplateItem.dictId}" onchange="refreshRadioValue(this)" 
	        			initDicId="101" alt="allowBlank:false" styleClass="select" />	
	        			
			</td>
			
			<td class="content" id="tdNormalRange" name="tdNormalRange" style="width: 100px;">
				<input class="text" type="text" name="normalRange"
					id="normalRange" alt="allowBlank:false" value="${inspectTemplateItem.normalRange }"/>
				
			</td>
			
			<td class="content" id="tdDefaultValue" name="tdDefaultValue">
				<input class="text" type="text" name="defaultValue"
					id="defaultValue" alt="allowBlank:false" value="${inspectTemplateItem.defaultValue }" />
			</td>
			<td id="tdcycle" class="content">
				<eoms:comboBox defaultValue="" name="cycle" id="cycle" initDicId="1122001"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'"  />
			</td>
		
			<td width="5%" style="vertical-align:middle;">
						<img src="${app}/images/icons/add.gif" onclick="addRow(this)" style="cursor:hand;display:block"><img src="${app}/images/icons/delete.gif" onclick="delRow(this)" style="cursor:hand;display:none">
						
					</td>
		</tr>
		
	</table>
	<input  type="submit" value="保存" class="btn" id="editTemplateItema" /> 
	<input type="button" value="重置" class="btn" id="reset" />
	<input type="button" value="返回" class="btn" id="goBack" />
</form>
</div>
<div id="info"><table class="formTable">
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					巡检模板项信息
				</div>
			</td>
		</tr>

		<tr>
			<td class="label">
				巡检项目*
			</td>
			<td class="content" colspan="3">
			${inspectTemplateItem.inspectItem }
			</td>
		</tr>
		<tr>
			<td class="label">
				检查内容*
			</td>
			<td class="content" colspan="3">
			${inspectTemplateItem.inspectContent }
			</td>
		</tr>
		<tr>
			<td class="label">
				输入方式*
			</td>
			<td class="content" colspan="3">
			<c:choose>
			<c:when test="${inspectTemplateItem.inputType=='multiple'}">
				多选
			</c:when>
			<c:when test="${inspectTemplateItem.inputType=='radio'}">
				单选
			</c:when>
			<c:when test="${inspectTemplateItem.inputType=='number'}">
				数值
			</c:when>
			<c:when test="${inspectTemplateItem.inputType=='text'}">
				文本
			</c:when>
			</c:choose>
			</td>
		</tr>
		<tr>
			<td class="label">
				默认值*
			</td>
			<td class="content" colspan="3">
			<c:choose>
			<c:when test="${inspectTemplateItem.inputType == 'radio'|| inspectTemplateItem.inputType=='multiple'}">
			<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectTemplateItem.defaultValue}" />
			</c:when>
			<c:otherwise>
			${inspectTemplateItem.defaultValue}
			</c:otherwise>
			</c:choose>
			</td>
		</tr>
		<tr>
			<td class="label">
				正常值范围*
			</td>
			<td class="content" colspan="3">
			
			<c:choose>
			<c:when test="${inspectTemplateItem.inputType == 'radio'|| inspectTemplateItem.inputType=='multiple'}">
			<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectTemplateItem.normalRange}" />
			</c:when>
			<c:otherwise>
			${inspectTemplateItem.normalRange}
			</c:otherwise>
			</c:choose>
			</td>
		</tr>
		<tr>
			<td class="label">
				关联数据字典*
			</td>
			<td class="content" colspan="3">
			<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectTemplateItem.dictId}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				添加人*
			</td>
			<td class="content" colspan="3">
			<eoms:id2nameDB beanId="tawSystemUserDao"
						id="${inspectTemplateItem.addUser}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				添加时间*
			</td>
			<td class="content" colspan="3">
			${inspectTemplateItem.addTime}
			<input class="text" type="hidden" name="templateId" 
					id="templateId" value="${templateId}" alt="allowBlank:false" />
			</td>
		</tr>
		
	</table></div>
<%@ include file="/common/footer_eoms.jsp"%>

