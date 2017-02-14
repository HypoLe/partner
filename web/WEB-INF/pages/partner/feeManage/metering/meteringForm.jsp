<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>



<script type="text/javascript">

var qj=jQuery.noConflict();
var rowId=0;
var majorId;
var cntTypId;
var majorName;
var cntTypName;
var majorid='${feePoolMetering.major}';
var code='${feePoolMetering.meteringCode}';
var pageInit=true;
	Ext.onReady(function(){
	 var v= new eoms.form.Validation({form:'meteringForm'});
     v.custom = function(){
      try{
			  var prices =qj("input[id$='_factor']");
			  for(var i=0;i<prices.length;i++){
			  var price=prices[i];
			  if(notNumber(price.value.trim())){
			  price.focus();
			  alert("系数必须是大于等于0的数字，最多两位小数");
			  return false;
			  }
			  }
			  }
			  catch(e){
			  alert(e);
			return false;
			}
      return true;
      }
   });

     function notNumber(value){
		  	   	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
		        var value=value; 
		   		if(value.match(price) && ""!=value){
		   			return false;
		      	}else{
		           	return true;
		      	}
		  }
  
   function loadCntTypByMajor(select){
	var hasData=false;
	
	if(!pageInit){
	var options=document.getElementById("meteringCode").options;
	if(options.length){
	for(var i =0;i<options.length;i++){
			var tab = document.getElementById(options[i].value.trim());
			
			if(tab&&tableHasData(tab)){hasData=true;}
	}
	}
	}
	var val = select;
	var prv = document.getElementById("meteringCode");
	 while(prv.options.length>0){
     prv.options.remove(prv.options.length-1);   
       }
	if(select==''){
	   var oOption = document.createElement("OPTION");
       oOption.value="";
       oOption.innerHTML="请先选择专业";
       prv.appendChild(oOption);
       loadMerting();
       }
	else{
	
    
	var ajax = createXmlHttpRequest();
	ajax.open("POST","${app}/partner/feeManage/feeCountManage.do?method=cntTypSelect",true);
	ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4 && ajax.status==200){
			var result = ajax.responseText;
						var results=result.split(",");
			for(var i=0;i<results.length-3;i=i+3){
	   var oOption = document.createElement("OPTION");
       oOption.value=results[i].trim();
       oOption.innerHTML=results[i+1].trim();
       if(code!=''&&code==results[i].trim()){
       oOption.selected = true;
       }
       else {
       document.getElementById("meteringName").value=results[1].trim();
       }
       prv.appendChild(oOption);
       }
          rowId=0;
          loadMerting();
		}
	};
	ajax.send("id="+val);
	}
}

  function loadMeteringName(obj){
  // alert(obj.options[obj.selectedIndex].text);
    //  alert(obj);
   loadMerting();
  document.getElementById("meteringName").value=obj.options[obj.selectedIndex].text;
 
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
  function returnBack(){
  document.getElementById("aFrom").submit();
		//window.history.back();
	}
	
	
	    function check(factory){
     	Ext.Ajax.request({
					url:"${app}/partner/deviceAssess/facilityquantity.do",
					params:{method:"checkfactory",factory:factory},
					dataType: "JSON",
					success:function(result) {
					var er = eval(result.responseText);
					
					if(er[0].writerResult==0){
					
					alert("该厂家设备信息已存在，请点击设备量信息列表页面进行修改");
					document.getElementById("factory").value=''
					}
					}
 });
}
	
	   function loadMerting(){
    
        var meteringCode=document.getElementById("meteringCode").value.trim();
		var major=document.getElementById("major").value.trim();
        var div=document.getElementById("merting");
        div.innerHTML="";
  		var ajax = createXmlHttpRequest();
	    ajax.open("POST","${app}/partner/feeManage/feeMetering.do?method=loadMerting",true);
		ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		ajax.onreadystatechange=function(){
			if(ajax.readyState==4 && ajax.status==200){
				var result = ajax.responseText;
				var subDiv=document.createElement("div");
			    
			//	subDiv.id="div_"+cntTypId+"-"+resourceType.value.trim();
				subDiv.innerHTML = result;
           		div.appendChild(subDiv);
           		

		}
	};
	
	ajax.send("&majorId="+major+"&cntTypId="+meteringCode);
  
  
  }
</script>


<div align="center"><b>编辑计次类型</div><br><br/>
<div id="listQueryObject" 
	 style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
<form action="${app}/partner/feeManage/feeMetering.do?method=saveMetering"
	method="post" id="meteringForm" name="meteringForm"/>

	<input type="hidden" id="id" name="id"  value="${feePoolMetering.id}"/>
	<table id="sheet" class="formTable">
		<tr>
		<td class="label">
				<font color='red'> * </font>专业：
			</td>
			<td class="content">
						<eoms:comboBox name="major" id="major" onchange="loadCntTypByMajor(this.value.trim())"
							styleClass="select max" initDicId="11225"
							defaultValue="${feePoolMetering.major}"
							alt="allowBlank:false,vtext:'请选择专业...'"
							/>
					</td>
			<td class="label">
				<font color='red'> * </font>计次类型：
			</td>
			<td>
			
			<select id="meteringCode" name="meteringCode" class="select" 
			 alt="allowBlank:false"  onchange="loadMeteringName(this)"
					>
					<option>请先选择专业</option>
				</select>
			<input type="hidden" id="meteringName" name="meteringName"  value="${feePoolMetering.meteringName}"/>
			<!-- <input class="text" type="text" name="meteringName" id="meteringName"  value="${feePoolMetering.meteringName}" alt="allowBlank:false"/>
			 loadMerting-->
			</td>

			
		</tr>
		<!--<tr>
			<td class="label">
				<font color='red'> * </font>最大计次系数
			</td>
			<td>
			<input class="text" type="text" name="maximum"  value="${feePoolMetering.maximum}"
				 id="maximum" alt="allowBlank:false" onblur="javascript:sureMaximum(this);"></input>
				 <div id="maximumDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>例：23,23.00 </div>
				</div>
			</td>

			<td class="label">
				<font color='red'> * </font>最小计次系数：
			</td>
			<td>
            <input class="text" type="text" name="minimum"   value="${feePoolMetering.minimum}"
				 id="minimum" alt="allowBlank:false" onblur="javascript:sureMinimum(this);"></input>
				 <div id="minimumDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>例：23,23.00 </div>
				</div>


			</td>
		</tr>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="返回" onclick="returnBack();" /> -->
		
	
			
	
	</table>
	<div id="merting"></div>
	<input type="submit" value="提交"  />
</form>
</div>
<form action="${app}/partner/feeManage/feeMetering.do?method=meteringList"
	method="post" id="aFrom" name="aFrom">
</form>

<%@ include file="/common/footer_eoms.jsp"%>