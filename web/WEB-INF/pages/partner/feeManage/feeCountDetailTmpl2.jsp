<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<style type="text/css">
  .classOrder{
    width:10%
  }
  .classMajor{
    width:20%
  }
  .classFeeCountType{
    width:20%
  }
  .classResType{
    width:20%
  }
  .classPrcFilter{
    width:20%
  }
  .classPrcQuotiety{
    width:20%
  }
</style>

<form action="${app}/partner/feeManage/feeCountManage.do?method=saveFeeCountDetailTemplate"
	method="post" id="filterForm" name="filterForm">
	<input type='hidden' name='prcTmplId' id='prcTmplId'
		value='${prcTmplId}' />
<table class="table">
    <tr>
    	<td class="label"><font color='red'> * </font>模板名称：</td> 
    	<td><input type="text" class="text" name="prcTmplNm" id="prcTmplNm" alt="allowBlank:false"/></td>
    	<td class="label">
    	<font color='red'> * </font>专业：</td>
    	<td id=majorInput>
   	 	<eoms:comboBox name="majorSelect" id="majorSelect" initDicId="11225" defaultValue=""
			sub="resourceType" onchange="loadCntTypByMajor(this)"  alt="allowBlank:false,vtext:'请选择专业...'"/>		 
			 <!--
				<select id="majorSelect" name="majorSelect" class="text"
					onchange="loadCntTypByMajor(this)";>
				</select>  -->
    	</td>
    </tr>
	<tr>
		<td class="label"><font color='red'> * </font>单价</td>
		<td><input type="text" class="text" name="feePrice" id="feePrice" alt="allowBlank:false"/></td>
		<td class="label">区域</td>
		<td><input type="text" class="text" name="areaName" id="areaName" readonly="true" 
				 value="<eoms:id2nameDB id='${areaId}' beanId='tawSystemAreaDao'/>"/>
			<input type="hidden" id="areaId" name="areaId" value="${areaId}"/>
		</td>	   
			
	</tr>
	<tr>
		<td class="label">代维公司</td>
		<td class="content">
			<input type="text" id="compName" name="compName" class="text" readonly="true" 
                  value="<eoms:id2nameDB id='${compId}' beanId='partnerDeptDao'/>" />
			<input type="hidden" id="compId" name="compId" value="${compId}"/>
		</td>  
		<td class="label">备注</td>
		<td class="content" >
		<textarea name="remark" id="remark" class="text medium"></textarea>
		</td>  	
	</tr>
    <tr>
		<td class="label"><font color='red'> * </font>计次类型</td>
		<td><select id="cntTypSelect" name="cntTypSelect" class="text"
			 onchange="loadFeeFilterByCntTyp('cntTyp')";>
			 </select>
		</td>	
		<td class="label" id="resourceTypeTD">资源类别</td>
		<td id="resInput"><eoms:comboBox name="resourceType" id="resourceType" initDicId="majorSelect" defaultValue=""
		 onchange="loadFeeFilterByCntTyp('resType')"/>	
		</td>     	
	</tr>
	</table>
	
	<div id="feeFilter">

	</div>
  <table><tr><td>
  <input type="button" id="button1"  name="button1" onclick="addRecordTable()" value="添加清单"  >
  </td></tr></table>
<!--  		<input type="button" onclick="loadAllUnitPrice()" value="复位">
<input type="button" onclick="addRecord4Remove()" value="删除">   -->


	<div id="tableDiv"></div>
	<input type="submit" value="提交"  />

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
			var flag;
			var rowId=0;
			var majorId;
			var cntTypId;
			var majorName;
			var cntTypName;
			var pageInit=true;
			　　window.onload=function(){
			  var prv=document.getElementById("majorSelect");
			  var button1=document.getElementById("button1");
			  button1.style.display="none";
			     
			  var v = new eoms.form.Validation({form:"filterForm"});
			  v.custom = function(){
			  checkTmp();
			  var returnFag=false;
			  var hasData=false;
			  var options=document.getElementById("cntTypSelect").options;
			  var resOptions=document.getElementById("resourceType").options;
                 if(options.length){
                          for(var i =0;i<options.length;i++){
                             for(var j =0;j<resOptions.length;j++){
		                     var tab = options[i].value.trim()+"-"+resOptions[j].value.trim();
		                     var sDiv=document.getElementById("div_"+tab);
		         if(sDiv){hasData=true;}
		                     }
		                  }
		         }
		         if(!hasData){
		         alert("请点击'添加清单'添加单价清单再提交");
		         return false;
		         }
				  try{
				  var prices =qj("input[id$='_tablePrice']");
						  for(var i=0;i<prices.length;i++){
						  var price=prices[i];
								  if(notNumber(price.value.trim())||notNumber(document.getElementById("feePrice").value)){
								  price.focus();
								  alert("单价或单价系数必须是大于等于0的数字，最多两位小数");
								  return false;
								  }
				  		  }
				  }
				  catch(e){
				           return false;
				          }
				          
				          
				if(flag=='0'){
				          return false;
				}
				return true;
				
			  }
			};

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
			var val = select.value.trim();
			var div= document.getElementById("tableDiv");
			var button1=document.getElementById("button1");
			button1.style.display="none";
				
				var prv = document.getElementById("cntTypSelect");
			     while(prv.options.length>0){
			       prv.options.remove(prv.options.length-1);   
			       }
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
			       prv.appendChild(oOption);
			       }
			          rowId=0;
			          div.innerHTML="";
			        loadFeeFilterByCntTyp("cntTyp");
					}
				};
				ajax.send("id="+val);
				
			}

		function loadFeeFilterByCntTyp(select){
			
	            var button1=document.getElementById("button1");
	            button1.style.display="none";
				majorId=document.getElementById("majorSelect").value.trim();
	            cntTypId=document.getElementById("cntTypSelect").value.trim();
	            var resourceType=document.getElementById("resourceType");
				var val = cntTypId;
				var parentVal=document.getElementById("majorSelect").value.trim();
				var prv = document.getElementById("feeFilter");
			    var resInput=document.getElementById("resInput");
			    var resTd=document.getElementById("resourceTypeTD");
			    if(select=="cntTyp"){
			    resourceType.options[0].selected='selected';}
				var ajax = createXmlHttpRequest();
				ajax.open("POST","${app}/partner/feeManage/feeCountManage.do?method=loadFilterCheckbox",true);
				ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				ajax.onreadystatechange=function(){
					if(ajax.readyState==4 && ajax.status==200){
					var result = ajax.responseText;
						resultTab=result.split("/");
						if(resultTab[0].trim()==0){//如果该计次类型需要选择资源类别
						resInput.style.display=""; 
						resTd.style.display=""; 
					    prv.innerHTML = "";
						}
						else{
						if(select=="cntTyp"){
						
						
						resInput.style.display="none"; 
						resTd.style.display="none"; 
						}
						prv.innerHTML = result;
						button1.style.display="block";
						}	
			  	        
					}
				};
				
				ajax.send("id="+val+"&parentVal="+parentVal+"&resourceType="+resourceType.value.trim());
			

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
  
  
  
		  function addRecordTable(){
		       try{
			
		 var resourceType=document.getElementById("resourceType").value.trim();
		 var cntTypId=document.getElementById("cntTypSelect").value.trim();
			    
		 var table =document.getElementById(cntTypId+"-"+resourceType);
		     if(table){
		     alert("该计次类型的清单已经存在，如需修改详细条件请先删除存在的清单列表！");
		        
		     return;}
		        
				 }catch(e){
				     alert("该计次类型的清单已经存在，如需修改详细条件请先删除存在的清单列表！");
				  return ;
				 }
		 
		  var filters=document.getElementsByName("hidden");
		  var sb="";
		  if(filters&&filters.length>0){
		
		  for(var i=0;i<filters.length;i++){
		  if(filters[i].value&&""!=filters[i].value.trim()&&filters[i].checked){
		  sb=sb+filters[i].value.trim()+",";
		  }
		  }
		   sb=sb.substr(0,sb.length-1);
		  }
		  
		  var majorSelect=document.getElementById("majorSelect");
		  var cntTypSelect=document.getElementById("cntTypSelect");
		  var resourceType=document.getElementById("resourceType");
		  
		  var div=document.getElementById("tableDiv");
		  var ajax = createXmlHttpRequest();
			ajax.open("POST","${app}/partner/feeManage/feeCountManage.do?method=creatTableByFilters",true);
			ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			ajax.onreadystatechange=function(){
				if(ajax.readyState==4 && ajax.status==200){
					var result = ajax.responseText;
					var subDiv=document.createElement("div");
					subDiv.id="div_"+cntTypId+"-"+resourceType.value.trim();
					subDiv.innerHTML = result;
		            div.appendChild(subDiv);
		
				}
			};
			
			ajax.send("filters="+sb+"&majorId="+majorSelect.value.trim()+"&cntTypId="+cntTypSelect.value.trim()+"&resourceType="+resourceType.value.trim());
		  
		  
		  }
		  function delRecordTable(divId){
		 
				  var subDiv=document.getElementById("div_"+divId);
				  if(!subDiv){
				  alert("当前计次类型清单不存在，可以添加清单");
				  return;
				  }
				  if(subDiv){
				   if(!confirm("是否要删除清单")){
				   return;
				  }
				  subDiv.parentNode.removeChild(subDiv);
				  alert("删除成功！");
				  }
				  return;
			}
			
			
			
		function checkTmp(){
		  var majorId=document.getElementById("majorSelect").value;
		  var areaId=document.getElementById("areaId").value;
		  var compId=document.getElementById("compId").value;
		  var results=0;
		  var ajax = createXmlHttpRequest();
			ajax.open("POST","${app}/partner/feeManage/feeCountManage.do?method=checkTmp",false);
			ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			ajax.onreadystatechange=function(){
				if(ajax.readyState==4 && ajax.status==200){
					var result = ajax.responseText;
					resulta=result.split("/");
				    results=resulta[0].trim();
				    
				    
				    
				    
				    
				    if(results!=0) 
				     {
				      alert("当前类型模板已存在，请勿重复添加！");
				      flag='0';
				     
			  			}
			else{ 
			flag='1';
			
			}  		
				}
			};
			
			ajax.send("&majorId="+majorId+"&areaId="+areaId+"&compId="+compId);
		  }
		  
	</script>
<%@ include file="/common/footer_eoms.jsp"%>