<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>



<script type="text/javascript">
     var meteringCode='${meteringCode}';
     var majorId='${major}';
     var indexCode='${feePoolIndex.indexCode}';
	 Ext.onReady(function(){
	 loadIndex();
	 var v= new eoms.form.Validation({form:'meteringForm'});
	
	
   });


  
  
  function loadIndex(){
 
  var prv = document.getElementById("indexCode");
     while(prv.options.length>0){
       prv.options.remove(prv.options.length-1);   
       }
	var ajax = createXmlHttpRequest();
	ajax.open("POST","${app}/partner/feeManage/feeMetering.do?method=indexSelect",true);
	ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4 && ajax.status==200){
			var result = ajax.responseText;
						var results=result.split(",");
			for(var i=0;i<results.length-3;i=i+3){
		var oOption = document.createElement("OPTION");
       oOption.value=results[i].trim()+","+results[i+2].trim();
       oOption.innerHTML=results[i+1].trim();
     if(indexCode!=''&&indexCode==results[i].trim()){
      oOption.selected = true;
      }
      else{
      document.getElementById("indexName").value=results[1].trim();
      }
       prv.appendChild(oOption);
       }
          rowId=0;
		}
	};
	ajax.send("meteringCode="+meteringCode+"&majorId="+majorId);
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
	 function loadIndexName(obj){
  // alert(obj.options[obj.selectedIndex].text);
    //  alert(obj);
  document.getElementById("indexName").value=obj.options[obj.selectedIndex].text;
  }
   
   function returnBack(){
   document.getElementById("aFrom").submit();
		//window.history.back();
	}
   function subMeteringForm(){
   

	        document.getElementById("meteringForm").submit();
	    
	}
</script>
<form action="${app}/partner/feeManage/feeMetering.do?method=saveIndex"
	method="post" id="meteringForm" name="meteringForm"/>


	<table id="sheet" class="formTable">
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					编辑计次指标
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>计次类型名称：
			</td>
			<td>
					${meteringName}
			<input type="hidden" id="meteringName" name="meteringName"  value="${meteringName}"/>
			</td>

			<td class="label">
				<font color='red'> * </font>专业：
			</td>
			<td class="content">
			<eoms:id2nameDB id="${major}"  beanId="ItawSystemDictTypeDao" />	
			<input type="hidden" id="major" name="major"  value="${major}"/>			
					</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>指标：
			</td>
			<td>
			<select id="indexCode" name="indexCode" class="select" 
			 alt="allowBlank:false"  onchange="loadIndexName(this)"
					>
				</select>
			<input type="hidden" id="indexName" name="indexName"  value="${feePoolIndex.indexName}"/>
			</td>

		</tr>
	<input type="hidden" id="id" name="id"  value="${feePoolIndex.id}"/>
	<input type="hidden" id="meteringId" name="meteringId"  value="${meteringId}"/>
	</table>
</form>
<table> 
<tr> <td><input type="button" onclick="subMeteringForm();"
		value="提交"  class="btn"/>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" onclick="returnBack();"
		value="返回"  class="btn"/></td></tr>
</table>
<form action="${app}/partner/feeManage/feeMetering.do?method=meteringIndexList&id=${meteringId}"
	method="post" id="aFrom" name="aFrom">
	</form>
<%@ include file="/common/footer_eoms.jsp"%>