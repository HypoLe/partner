<%--参考福建inspectItemForm.jsp---%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
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
		jq("#dictTypeName").hide();
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
		jq("#importTemplateItem").click(function() {
			//submit();
			importTemplateItem();
		});
		jq("#editInspectTemplateItem").click(function() {
			jq("#edit").show();
			jq("#info").hide();
			jq("#reset").hide();
			jq("#editTemplateItem").val("保存");
		});
		jq("#reset").click(function() {
			jq(':input','#importTemplateItemForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected'); 
		});
		changeInputType(jq("#inputType"));
		initValues();
		
		 
		
		/*
		document.getElementById('dictId').attachEvent('onpropertychange',function(o){
		if(o.propertyName!='value'){//不是value改变不执行下面的操作
	
		}else{
			getValues();
		};  
        
     	});
     	*/
});
function importTemplateItem(){
	var normalRange;
	if('' == jq("#cycle").val()){
		alert("请选择巡检频率");
		return;
	}
	
	if('text' == inputType){
		jq("#importTemplateItemForm").attr("action","inspectTemplateManger.do?method=editTemplateItem&importType=import&inputType=text&templateId="+'${templateId}'+'&cycle='+jq("#cycle").val()); 
	}
	if('number' == inputType){
		if("" == jq("#minValue").val()||""==jq("#maxValue").val()){
			alert("请选择最小值和最大值");
			return;
		}
		var minValueTemp = jq.trim(jq("#minValue").val());
		var maxValueTemp = jq.trim(jq("#maxValue").val());
		
        var len = minValueTemp.length;  
        for(var i=0;i<len;i++) {  
            c=minValueTemp.charAt(i).charCodeAt(0);  
            if(c<48 || c>57) {  
                alert("最小值必须为数字");
                event.preventDefault();  
                return;  
            }
        }
        var len2 = maxValueTemp.length;  
        for(var i=0;i<len2;i++) {  
            c=maxValueTemp.charAt(i).charCodeAt(0);  
            if(c<48 || c>57) {  
                alert("最大值必须为数字");
                event.preventDefault();  
                return;  
            }
        }
		
		
		
		
		if(minValueTemp>maxValueTemp){
			alert("最小值不能大于最大值");
			return;
		}
		normalRange = jq("#minValue").val()+"|"+jq("#maxValue").val();
		jq("#importTemplateItemForm").attr("action","inspectTemplateManger.do?method=editTemplateItem&importType=import&inputType=number&normalRange="+normalRange+"&templateId="+'${templateId}'+'&cycle='+jq("#cycle").val()); 
	}
	if('radio' == inputType){
		var str="";
   		var i=1;
   		jq('input[name="dictNormalValue"]:checked').each(function(){
                str=jq(this).val();
                i++;
          });
        if(i>2){
        	alert("单选只能选一个");
			return;
        }
        if(i==1&&type=='new'){
        	alert("请选择单选默认值");
			return;
        }
        normalRange = str;
        jq("#importTemplateItemForm").attr("action","inspectTemplateManger.do?method=editTemplateItem&importType=import&inputType=radio&dictId="+jq("#dictId").val()+"&normalRange="+normalRange+"&templateId="+'${templateId}'+'&cycle='+jq("#cycle").val()); 
	}
	if('multiple' == inputType){
		if(0==jq("#dictId").val()||''==jq("#dictId").val()){
			alert(jq("#dictId").val()+"请选择多选字典值");
			return;
			
		}
		jq("#importTemplateItemForm").attr("action","inspectTemplateManger.do?method=editTemplateItem&importType=import&inputType=multiple&dictId="+jq("#dictId").val()+"&templateId="+'${templateId}'+'&cycle='+jq("#cycle").val()); 
	}
	if('' == inputType){
		alert("请选择输入类型");
		return;
	}
	if(''==jq("#fileName").val()){
		alert("请选择文件");
		return;
	}



	//jq("#importTemplateItemForm").attr("action","inspectTemplateManger.do?method=editTemplateItem&importType=import&inputType"+inputType)
	//jq('#importTemplateItem').hide();
	if(confirm('您是否要提交该信息?')){
	  		Ext.Ajax.request({	
		    form: 'importTemplateItemForm',
		    success: function(response){
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
		    	 	jq('#importTemplateItem').show();
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
			window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplateItemsList&id='+jq("#templateId").val();
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
	jq("#dictSpan").hide();
	var value = obj.value;
	jq("#hidenRadioValue").hide();
	jq("#trDefaultValue").hide();
	jq("#dictId").val("");
	//alert(value);
	if('1222401' == value){//文本
		jq("#dictTypeName").show();
		jq("#dictTypeName").hide();
		jq("#hideTr").hide();
		jq("#dictId").hide();
		inputType='text';
		jq("#tdNormalRange").html("<input class=\"text\" type=\"text\" id=\"normalRange\" name=\"normalRange\"/>");
		return;
	}
	if('1222402' == value){//数值
		jq("#dictTypeName").show();
		jq("#dictTypeName").hide();
		jq("#hideTr").show();
		jq("#dictId").hide();
		inputType='number';
		jq("#tdNormalRange").html("最小值&nbsp:&nbsp<input class=\"text\" type=\"text\" id=\"minValue\" name=\"minValue\"/>&nbsp;&nbsp最大值&nbsp:&nbsp<input class=\"text\" type=\"text\" id=\"maxValue\" name=\"maxValue\"/>");
		return;
	}
	if('1222403' == value){//单选
		jq("#hidenRadioValue").hide();
		jq("#dictSpan").show();
		isFirst = true;
		jq("#hideTr").show();
		jq("#dictTypeName").show();
		jq("#dictId").show();
		jq("#tdNormalRange").html("");
		inputType='radio';
		getValues();
		if(getOs() == 'Firefox'){
			isFirefox = true;
			jq("#hidenRadioValue").show();
			
		}
		return;
	}
	if('1222404' == value){//多选
		jq("#hideTr").hide();
		jq("#dictSpan").show();
		jq("#dictTypeName").show();
		jq("#dictTypeName").show();
		jq("#dictId").show();
		inputType='multiple';
		jq("#tdNormalRange").html("多选");
		return;
	}
	//inputType='';
	jq("#dictId").hide();
}


var htmlStr = '';
function refreshRadioValue(){
		getValues();	
}

function getValues(){
	htmlStr = '';
	var parentDict = jq("#dictId").val();
	if(parentDict!=''){
	var url="<%=request.getContextPath()%>/partner/inspect/inspectTemplateManger.do?method=getDictValues&dict="+parentDict;
	
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) {
			res = result.responseText;
			var json = eval(res);
			if(inputType == 'radio'){
				if(json.length==0)return;
				for(var i=0;i<json.length;i++){
					htmlStr = htmlStr+'<input type="checkbox" id="dictNormalValue" name="dictNormalValue" value="'+json[i].dictId+'" />'+json[i].name;
				}
				if(isFirefox&&isFirst){
					isFirst = false;
					getValues();
				}else{
					jq("#tdNormalRange").html(htmlStr);
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
				for(var i=0;i<json.length;i++){
				
					if(normalRange.indexOf(json[i].dictId+',')>-1){
						htmlStr = htmlStr+'<input type="checkbox" id="dictNormalValue" name="dictNormalValue" value="'+json[i].dictId+'" checked="true"/>'+json[i].name;
					}else{
						htmlStr = htmlStr+'<input type="checkbox" id="dictNormalValue" name="dictNormalValue" value="'+json[i].dictId+'" />'+json[i].name;
					}
					
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
		var normalRanges = normalRange.split(',');
		document.getElementById('numRangeLow').value=normalRanges[0];
		document.getElementById('numRangeTop').value=normalRanges[1];
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
if(navigator.userAgent.indexOf("MSIE")>0){
	document.getElementById('dictId').attachEvent('onpropertychange',function(o){   
          if(o.propertyName!='value'){
             return;  //不是value改变不执行下面的操作   
          }else{
          		getValues();
              //attachMethod(o);
          }
        });



	//document.getElementById('dictId').attachEvent("onpropertychange",txChange); 
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


</script>



<%--
<eoms:xbox id="tree" dataUrl="${app}/partner/inspect/inspectItems.do?method=getDict" 
	  	rootId="-1" 
	  	rootText="绑定类型编号" 
	  	valueField="dictId" handler="dictTypeName"
		textField="dictTypeName"
		checktype="forums" single="true"
		data='${data}'></eoms:xbox>
${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplatesList
--%>
<eoms:xbox id="tree" dataUrl="${app}/partner/inspect/inspectTemplateManger.do?method=getDict" 
	  	rootId="-1" 
	  	rootText="绑定类型编号" 
	  	valueField="dictId" handler="dictTypeName"
		textField="dictTypeName"
		checktype="forums" single="true"
		data='${data}'></eoms:xbox>





<div id="edit">
<form method="post" id="importTemplateItemForm" name="importTemplateItemForm" enctype="multipart/form-data">
	<table class="formTable">
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					巡检模板主体新增
				</div>
			</td>
		</tr>
	<%--
		<tr>
		
		<td class="label">
				巡检项目*
			</td>
			<td class="content" colspan="1">
			<input class="text" type="text" name="inspectItem"
					id="inspectItem" alt="allowBlank:false" value="${inspectTemplateItem.inspectItem }"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				检查内容*
			</td>
			<td class="content" colspan="3">
			<textarea name="inspectContent" id="inspectContent" class="textarea max">${inspectTemplateItem.inspectContent }</textarea>
			</td>
		</tr>
		
		--%>
		<tr>
		
		<td class="label">
				输入方式*
			</td>
			<td class="content" colspan="3">
			<c:choose>
			<c:when test="${inspectTemplateItem.inputType eq 'text'}">
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" id="inputType2" initDicId="12224" defaultValue="1222401"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" styleClass="select" />
			</c:when>
			<c:when test="${inspectTemplateItem.inputType eq 'number'}">
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" id="inputType2" initDicId="12224" defaultValue="1222402"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" styleClass="select" />
			</c:when>
			<c:when test="${inspectTemplateItem.inputType eq 'radio'}">
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" id="inputType2" initDicId="12224" defaultValue="1222403"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" styleClass="select" />
			</c:when>
			<c:when test="${inspectTemplateItem.inputType eq 'multiple'}">
				<eoms:comboBox onchange="changeInputType(this)" name="inputType2" id="inputType2" initDicId="12224" defaultValue="1222404"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" styleClass="select" />
			</c:when>
			<c:otherwise>
			<eoms:comboBox onchange="changeInputType(this)" name="inputType2" id="inputType2" initDicId="12224" defaultValue="1222401"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" styleClass="select" />
			</c:otherwise>
			</c:choose>
					
				<span id="dictSpan">关联字典</span>
				<input type="text"   id="dictTypeName" alt="allowBlank:false,vtext:'关联字典'" 
				name="dictTypeName" class="text" readonly="readonly" 
				value='<eoms:id2nameDB id="${inspectTemplateItem.dictId}" beanId="IItawSystemDictTypeDao" />'/>
				
				<input id="hidenRadioValue" onclick="refreshRadioValue()" value="点击刷新单正常值范围" class="text" type="button"/>
			</td>
		<tr id="hideTr">
			<td class="label">
				正常值范围*
			</td>
			<td class="content" colspan="3" id="tdNormalRange">
				<input class="text" type="text" name="normalRange"
					id="normalRange" alt="allowBlank:false" value="${inspectTemplateItem.normalRange }"/>
			</td>
		</tr>
		<tr id="trDefaultValue">
			<td class="label">
				默认值*
			</td>
			<td class="content" colspan="3" >
				<input class="text" type="text" name="defaultValue"
					id="defaultValue" alt="allowBlank:false" value="${inspectTemplateItem.defaultValue }"/>
			</td>
		</tr>
		<!-- <tr>
		<td class="label">
			周期*
		</td>
		<td class="content" colspan="3">
		
				<eoms:comboBox defaultValue="${inspectTemplateItem.cycle }" name="cycle" id="cycle" initDicId="1122003"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
		
		</td>
		</tr> -->
		<tr style="display: none">
			<td class="label">
				关联数据字典*
			</td>
			<td class="content" colspan="3">
			<!-- 
				<input class="text" type="text" name="dictId"
					id="dictId" alt="allowBlank:false" value="${inspectTemplateItem.dictId }"/>
					 -->
					
					<input class="text" type="hidden" id="dictId" name="dictId" value="${inspectTemplateItem.dictId}" />
					
			</td>
		</tr>
		<tr>
		
		<td class="label">
				导入文件*
			</td>
			<td class="content" colspan="3">
			<input type="file" name="fileName" id="fileName" class="file">
			</td>
		</tr>
	</table>
	<input class="content" type="hidden" name="templateId"
					id="templateId" value="${templateId}" alt="allowBlank:false" />
	<input  type="hidden" name="id"
					id="id" value="${inspectTemplateItem.id}" alt="allowBlank:false" />
		<br/>
		<input  type="button" value="添加" class="btn" id="importTemplateItem" />
		<input type="button" value="重置" class="btn" id="reset" />
		<input type="button" value="返回" class="btn" id="goBack" />
</form>
</div>
<%@ include file="/common/footer_eoms.jsp"%>

<script>


</script>