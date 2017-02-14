<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<form
	action="${app}/partner/feeManage/feeCountManage.do?method=saveEditFeeCountDetailTemplate"
	method="post" id="filterForm" name="filterForm">
	<input type='hidden' name='prcTmplId' id='prcTmplId'
		value='${prcTmplId}' />

	<table class="table">

<tr>
<td class="label"><font color='red'> * </font>模板名称：</td> <td><input type="text" class="text" name="prcTmplNm" id="prcTmplNm" value="${prcTmplNm}" /></td>

			<td class="label">
				专业：
			</td>
			<td id=majorInput>
			    ${majorName}
				<input type="hidden" id="majorSelect" name="majorSelect" value="${majorId}"/>
				<input type="hidden" id="majorName" name="majorName" value="${majorName}" />


			
			</td>
</tr>
	    <tr>	       		
			<td class="label">
				区域</font>
			</td>
			<td>
				<input type="text" class="text" name="areaName" id="areaName" readonly="true" 
				 value="<eoms:id2nameDB id='${areaId}' beanId='tawSystemAreaDao'/>"/>
				<input type="hidden" id="areaId" name="areaId" value="${areaId}"/>
			</td>	   
			<td class="label">代维公司</td>
			<td class="content">
				<input type="text" id="compName" name="compName" class="text" readonly="true" 
                  value="<eoms:id2nameDB id='${compId}' beanId='partnerDeptDao'/>" />
				<input type="hidden" id="compId" name="compId" value="${compId}"/>
			</td>   	  	
	    </tr>
		<tr>
			<td class="label">
				计次类型
			</td>
			<td>
				<select id="cntTypSelect" name="cntTypSelect" class="text"
					onchange="loadFeeFilterByCntTyp(this)";>


				</select>
			</td>
			<td class="label">
			备注
			</td>
			<td class="content">
				<textarea name="remark" id="remark" class="text medium">${remark}</textarea>
			</td>
		</tr>

	</table>

	<div id="feeFilter"></div>
	<!--
	<table class="table">
		<tr>
			<td>
				原单价（元）
			</td>
			<td name="prv" id="prv">
			</td>
			<td>
				定单价为（元）
			</td>
			<td name="unitPrice" id="unitPrice">
				<input type="text" class="text" name="unitPrice" id="unitPrice"
					alt="allowBlank:false,vtext:'考核指标名不能为空 不能超出25个汉字！',maxLength:25" />
			</td>
		</tr>
	</table>
  -->
  <table><tr><td>
	<input type="button" id="button1" name="button1" onclick="addRecord4Save()" value="添加到保存列表" style="display:none;">
	</td><td>
	<input type="button" id="button2" name="button2"onclick="loadTablePrice()" value="复位当前计次类型" style="display:none;">
</td>
</tr>
</table>
<!-- <input type="button" onclick="addRecord4Remove()" value="删除">   -->


	<div id="tableDiv"></div>
	<input type="submit" value="提交" />

</form>
<eoms:xbox id="areaTree" dataUrl="${app}/xtree.do?method=areaTree"  
	rootId="${sessionScope.sessionform.rootAreaId}" rootText="${sessionScope.sessionform.rootAreaName}" 
	valueField="areaId" handler="areaName" 
	textField="areaName" single="true">
</eoms:xbox>
<eoms:xbox id="compTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"
	rootId="" rootText='${sessionScope.sessionform.rootAreaName}' valueField="compId"
	handler="compName" textField="compName" checktype="dept"
	single="true"></eoms:xbox>

<script type="text/javascript">
var qj=jQuery.noConflict();
var rowId=0;
var majorId;
var cntTypId;
var majorName;
var cntTypName;
var pageInit=true;
	window.onload=function(){

             
		    
			loadCntTypByMajor(document.getElementById("majorSelect"));
			var tableIds= document.getElementById("cntTypSelect").options;
			if(tableIds.length){
			for(var k=0;k<tableIds.length;k++){
			
		     var table =document.getElementById(tableIds[k].value.trim());
		
            if(!table){
            
            
            tableIds[k].selected='selected';
            loadFeeFilterByCntTyp(null);
            
            }
        
			}
			tableIds[0].selected='selected';
			loadFeeFilterByCntTyp(null);
			}

						 var v = new eoms.form.Validation({form:"filterForm"});
			  v.custom = function(){
			  var hasData=false;
			  var options=document.getElementById("cntTypSelect").options;
                 if(options.length){
                          for(var i =0;i<options.length;i++){
		                     var tab = document.getElementById(options[i].value.trim());
		
		         if(tab&&tableHasData(tab)){hasData=true;}
		         }
		         }
		         if(!hasData){
		         alert("模板没数据，请添加在保存");
		         return false;
		         }
			  try{
			  var prices =qj("input[id$='_tablePrice']");
			  for(var i=0;i<prices.length;i++){
			  var price=prices[i];
			  if(notNumber(price.value.trim())){
			  price.focus();
			  alert("单价必须是大于等于0的数字，最多两位小数");
			  return false;
			  }
			  }
			  }
			  catch(e){
	
		return false;
	}
	  if(!confirm("是否要提交？")) { 
     
        return false;
     } 
	return true;
			  }

}

	function createXmlHttpRequest(){
	var xmlHttp = null;
	try{
		//Firefox, Opera 8.0+, Safari
	     xmlHttp=new XMLHttpRequest();
	}catch(e){
		//IEIE7.0以下的浏览器以ActiveX组件的方式来创建XMLHttpRequest对象
		 var MSXML = ['MSXML2.XMLHTTP.6.0','MSXML2.XMLHTTP.5.0',
		'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',
		'MSXML2.XMLHTTP','Microsoft.XMLHTTP'];
		 
		 for(var n = 0; n < MSXML.length; n ++){
		    try{
		    	xmlHttp = new ActiveXObject(MSXML[n]);
		      break;
		    }catch(e){
		    }
		  }
	}
	return xmlHttp;
}



function loadCntTypByMajor(select){
            var button1=document.getElementById("button1");
            button1.style.display="none";
var hasData=false;

if(!pageInit){
var options=document.getElementById("cntTypSelect").options;
if(options.length){
for(var i =0;i<options.length;i++){
		var tab = document.getElementById(options[i].value.trim());
		
		if(tab&&tableHasData(tab)){hasData=true;}
}
}
}
pageInit=false;
        if(hasData){
        
        if(confirm("需要先保存操作列表吗？")) { 
     
        return ;
     } 
}
var div= document.getElementById("tableDiv");

             var button1=document.getElementById("button1");
             var button2=document.getElementById("button2");
            button1.style.display="none";          
            button2.style.display="none";    
	var val = select.value;
	var prv = document.getElementById("cntTypSelect");


	var ajax = createXmlHttpRequest();
	ajax.open("Get","${app}/partner/feeManage/feeCountManage.do?method=cntTypSelect&id="+val,false);
	ajax.send(null);
			var result = ajax.responseText;
						var results=result.split(",");
			for(var i=0;i<results.length-2;i=i+2){
		var oOption = document.createElement("OPTION");
       oOption.value=results[i].trim();
       oOption.text=results[i+1].trim();
       prv.add(oOption)
       }        
          rowId=0;
           div.innerHTML="";
			loadFeeFilterByCntTyp(document.getElementById("cntTypSelect"));
	
}

function loadFeeFilterByCntTyp(select){
             var button1=document.getElementById("button1");
             var button2=document.getElementById("button2");
            button1.style.display="none";          
            button2.style.display="none";          
           
			majorId=document.getElementById("majorSelect").value.trim();
            cntTypId=document.getElementById("cntTypSelect").value.trim();


            majorName = document.getElementById("majorName").value.trim();
            cntTypName=document.getElementById("cntTypSelect").options[document.getElementById("cntTypSelect").selectedIndex].text;

   /* try{
        var tbody = document.getElementById("tbody");
        var trs=tbody.rows;
        if(trs.length>3){
        
        if(confirm("需要先保存操作列表吗？")) { 
     
        return ;
     } 
        }
    
    
  }catch(e){
	
		
	}
	*/
	var val =cntTypId;
	var parentVal=document.getElementById("majorSelect").value.trim();
	var prv = document.getElementById("feeFilter");


	var ajax = createXmlHttpRequest();
	ajax.open("GET","${app}/partner/feeManage/feeCountManage.do?method=feeFilterSelectAndBindeEvent",false);
	ajax.send("id="+val+"&parentVal="+parentVal);
			var result = ajax.responseText;
			prv.innerHTML = result;
            
   //         loadUnitPrice();
            addTable(); 
             var str=document.getElementById("cntTypSelect").value.trim();
          var table=document.getElementById(str);
           if(!tableHasData(table)){
           			qj(table).hide();
					qj(table).attr("disable","disable");
           }
           
			button1.style.display="block";
			button2.style.display="block";
	
	
}

/*
function loadUnitPrice(){

    
    var businessNames = document.getElementsByName("hidden");
    var fields = document.getElementsByName("select");
    

	
	var majorId=document.getElementById("majorSelect").value;
	var cntTypId=document.getElementById("cntTypSelect").value;
	var prv = document.getElementById("prv");

    var sb="m="+fields.length+"&cntTypId="+cntTypId+"&majorId="+majorId;
    for(var i=0;i<fields.length;i++){
    sb=sb+"&businessName"+i+"="+businessNames[i].value+"&field"+i+"="+fields[i].value
    }
    
	
	var ajax = createXmlHttpRequest();
	ajax.open("POST","${app}/partner/feeManage/feeCountManage.do?method=getUnitPrice",true);
	ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4 && ajax.status==200){
			var result = ajax.responseText;
			prv.innerHTML = result;

			
		}
	};
	ajax.send(sb);

}
*/


function addTable(){
 try{
		var table =document.getElementById(cntTypId);
		
        if(table!=null){return;}
        
		    }catch(e){
		    return ;
		    }

var div= document.getElementById("tableDiv");
var subDiv=document.createElement("div");
subDiv.id="div_"+cntTypId;

	var businessNames = document.getElementsByName("hidden");
 var sb="<input type='hidden' name='tableId' value='"+cntTypId+"'/><input type='hidden' name='tableName' value='"+cntTypName+"'/><tr><td colspan='"+(businessNames.length+6)+"'><div class='ui-widget-header'>"+majorName+"_"+cntTypName+"</div></td></tr><tr><td>序号</td><td>专业</td><td>计费类型</td>";
    for(var i=0;i<businessNames.length;i++){
    sb=sb+"<td>"+businessNames[i].value+"</td>"
    }

sb=sb+"<td>单价</td><td>删除此行</td>"

subDiv.innerHTML="<table id='"+cntTypId+"' class='table'><tbody><tr>"+sb+"</tr></tbody></table>";
div.appendChild(subDiv);
loadTablePrice();
/*
var tr=document.createElement("tr");
var td=document.createElement("td");
document.createElement()
td.appendChild(document.createTextNode("<input type='submit' name='tableId' />"));
tr.appendChild(td);
tbody.appendChild(tr);
table.appendChild(tbody);

div.appendChild(table);
*/


}

function addRecord4Save(){
var str = qj("#cntTypSelect").val().trim();
			qj(document.getElementById(str)).show();
			qj(document.getElementById(str)).attr("disable",false);
if(isHasRecord()==true){

  return ;
   }
 rowId++;
  var tbody=document.getElementById(cntTypId).children[0];

  var fields = document.getElementsByName("select");
  

  // var id=document.getElementById("id");
  /*
  var tr = document.createElement("tr");
  var sb="";
  for(var i=0;i<fields.length;i++){
  sb=sb+"<input type='hidden' name='"+cntTypId.trim()+"_tableField"+(i+1)+"'id='"+cntTypId.trim()+"_tableField"+(i+1)+"' value='"+fields[i].value+"'/>"
  }
  sb=sb+"<td>"+rowId+"</td><td>"+majorName+"</td><td>"+cntTypName+"</td>";
  for(var i=0;i<fields.length;i++)
  {
  sb=sb+"<td id='"+cntTypId.trim()+"_tableFiled'"+(i+1)+">"+fields[i].value+"</td>"
  
  }
  sb=sb+"<td><input type='text' id='"+cntTypId.trim()+"_tablePrice' name='"+cntTypId+"_tablePrice' class='text' /></td><td><img src='${app}/images/icons/delete.gif' onclick='delRow(this)' style='cursor:hand;'/></td><input type='hidden' name='rowId' id='rowId' value='"+rowId+"'/><input type='hidden' name='"+cntTypId.trim()+"_detailTemplateId' id='"+cntTypId.trim()+"_detailTemplateId' value=''/>"
   tr.innerHTML = sb;
tbody.appendChild(tr);
*/
var tr = document.createElement("tr");//对上面代码兼容ie
   for(var i=0;i<fields.length;i++){
   var input=document.createElement("input");
   input.type="hidden";
   input.name=cntTypId+"_tableField"+(i+1);
   input.id=cntTypId+"_tableField"+(i+1);
   input.value=fields[i].value;
   tr.appendChild(input);
   }
   var td1=document.createElement("td");
   td1.innerHTML=rowId;
   tr.appendChild(td1);
   var td2=document.createElement("td");
   td2.innerHTML=majorName;
   tr.appendChild(td2);
   var td3=document.createElement("td");
   td3.innerHTML=cntTypName;
   tr.appendChild(td3);
   for(var i=0;i<fields.length;i++){
   var td=document.createElement("td");
   td.id=cntTypId+"_tableFiled'"+(i+1);
   td.innerHTML=fields[i].value;
   tr.appendChild(td);
   }
   var td4=document.createElement("td");
   var input1=document.createElement("input");
   input1.type="text";
   input1.name=cntTypId+"_tablePrice";
   input1.id=cntTypId+"_tablePrice";
   input1.className="text";
   td4.appendChild(input1);
   tr.appendChild(td4);
   var td5=document.createElement("td");
   var img=document.createElement("img");
   img.src="${app}/images/icons/delete.gif";
  var a = qj("<a href='#'  onclick='delRow(this)'></a>");
   var A=a[0];
   A.appendChild(img);
   td5.appendChild(A);
   tr.appendChild(td5);
   var input2=document.createElement("input");
   input2.type="hidden";
   input2.name="rowId";
   input2.id="rowId";
   input2.value=rowId;
   tr.appendChild(input2);
   
tbody.appendChild(tr);
}

function isHasRecord(){
   
    
    var fields = document.getElementsByName("select");
    var tbody = document.getElementById(cntTypId).children[0];
    var trs=tbody.rows;
    
    for(var m=3;m<trs.length;m++){
    
     var tableFields = trs[m].getElementsByTagName("input");
     for(var i=0;i<fields.length;i++)
     
       { 
      
  
      
       
      
         if(tableFields[i].value==fields[i].value){
           if((i+1)==fields.length){ 
          
           alert("改记录已存在。序号为："+tableFields[i+2].value);
           
           return true;}
         
         
         
         }
         else{break;}
        }   
         
    }
    return false;
}
/*
 function addRecord4Remove(){
 if(isHasRecord()==true){

  return ;
   }
   if(document.getElementById("priceFlag").value==1)
   {
   alert("此情况未定价，不需要删除");
   return ;
   
   }
   rowId++;
  var tbody=document.getElementById("tbody");
  var fields = document.getElementsByName("select");
  var majorSelect = document.getElementById("majorSelect").value;
  var cntTypSelect = document.getElementById("cntTypSelect").value;
  var id=document.getElementById("id");
  var addElement = document.createElement("tr");
  var sb="";
  for(var i=0;i<fields.length;i++){
  sb=sb+"<input type='hidden' name='tableField"+(i+1)+" 'id='tableField"+(i+1)+"' value='"+fields[i].value+"'/>"
  }
  sb=sb+"<td>"+rowId+"</td><td>"+majorSelect+"</td><td>"+cntTypSelect+"</td>";
  for(var i=0;i<fields.length;i++)
  {
  sb=sb+"<td id='tableFiled'"+i+">"+fields[i].value+"</td>"
  
  }
  sb=sb+"<td><input type='text' id='tablePrice' name='tablePrice' class='text' value='0' readOnly='true'/></td><td><img src='${app}/images/icons/delete.gif' onclick='delRow(this)' style='cursor:hand;'</td><input type='hidden' name='rowId' id='rowId' value='"+rowId+"'/>";
   addElement.innerHTML = sb;
tbody.appendChild(addElement);


}
*/
function delRow(obj){
  var tr = obj.parentNode.parentNode;
  
  if(tr.tagName == "TR"){  	
    if(confirm("确定删除此行数据吗？")) { 
      var table = tr.parentNode.parentNode;
      tr.parentNode.removeChild(tr);
      if(!tableHasData(table)){
      	qj(table).attr("disable","disable");
			qj(table).hide();
      }
  }
  }
}
/**
function loadAllUnitPrice(){
	var prcTmplId = document.getElementById("prcTmplId").value;
    var majorId = document.getElementById("majorSelect").value;
	var cntTypId = document.getElementById("cntTypSelect").value;

	var div= document.getElementById("table");
	


	var ajax = createXmlHttpRequest();
	ajax.open("POST","${app}/partner/feeManage/feeCountManage.do?method=loadAllUnitPrice",true);
	ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4 && ajax.status==200){
			var result = ajax.responseText;
			div.innerHTML = result;
         

		}
	};
	ajax.send("prcTmplId="+prcTmplId+"&majorId="+majorId+"&cntTypId="+cntTypId);

}
*/
function loadTablePrice(){

    var prcTmplId=document.getElementById("prcTmplId").value.trim();
    var subDiv=document.getElementById("div_"+cntTypId);
    
	var ajax = createXmlHttpRequest();
	ajax.open("GET","${app}/partner/feeManage/feeCountManage.do?method=loadTablePrice&prcTmplId="+prcTmplId+"&majorId="+majorId+"&cntTypId="+cntTypId+"&rowId="+rowId,false);
		ajax.send(null);
			var result = ajax.responseText;
			subDiv.innerHTML = result;
			rowId=document.getElementById(cntTypId.trim()+"_rowId".trim()).value;


            var delImg=document.getElementsByName("delImg");
            if(delImg){
            for(var i=0;i<delImg.length;i++){
            delImg[i].src="${app}/images/icons/delete.gif";
            }
            }
            var table=document.getElementById(document.getElementById("cntTypSelect").value.trim());
		if(!tableHasData(table)){
      	qj(table).attr("disable","disable");
			qj(table).hide();
		}
		
	

}
  function notNumber(value){
  	   	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var value=value; 
   		if(value.match(price) && ""!=value){
   			return false;
      	}else{
           	return true;
      	}
  }
    function tableHasData(table){
  var trs=table.getElementsByTagName("tr");
  if(trs.length>3){return true;}
  else{return false;}
  
  }
	</script>
<%@ include file="/common/footer_eoms.jsp"%>