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
   
});

function getInspectType(obj){
	var tr = obj.parentNode.parentNode;
	var row = tr.rowIndex-1;
	var key = jq(obj).val();
	var map = '${map}';
	var devicejson ='${json}';
	var json = eval('(' + devicejson + ')');
	var object = json[0][key];
	var htmlStr2 = '<option value="" >请选择</option>';
	var htmlStr = '<select class="select" id="deviceType" name="deviceType" onchange="getdeviceType(this)" alt="allowBlank:false"><option value="" >请选择</option>';
	for(var i = 0 ;i < object.length ;i++){
		htmlStr += '<option value="'+object[i].resourceMark+'">'+object[i].resourceName+'</option>';
		htmlStr2 += '<option value="'+object[i].resourceMark+'">'+object[i].resourceName+'</option>';
	}
	htmlStr += '</select>'
	
	if(isFirefox=navigator.userAgent.indexOf("MSIE")>0){  
		jq(document.getElementsByName("tddeviceType")[row]).html(htmlStr);
    }else{
		jq(document.getElementsByName("deviceType")[row]).html(htmlStr2);
    }
	
	jq(tr).find("#deviceSpecialtyName").val(jq(obj).find('option:selected').text());
}

function getdeviceType(obj){
	var tr = obj.parentNode.parentNode;
	var deviceSpecialty = jq(tr).find("#deviceSpecialty").val();
	var deviceType = jq(tr).find("#deviceType").val();
	var devicejson ='${json}';
	var json = eval('(' + devicejson + ')');
	//var htmlStr = '<select name="netresFieldValue" id="netresFieldValue"><option value="">请选择</option>';
	var htmlStr = '';
	var string = '';
	for(var j  = 0; j< json[0][deviceSpecialty].length;j++){
	 var mapdeviceType = json[0][deviceSpecialty][j].resourceMark;
	 if(deviceType == mapdeviceType){
	 	var arr = json[0][deviceSpecialty][j].filedList;
	 	for(var i=0;i<arr.length;i++ ){
	 		string += '<option value="'+arr[i]+'">'+arr[i]+'</option>';
	 	}
	 	if(json[0][deviceSpecialty][j].typeByFiled == "y"){
	 		 htmlStr = '<select name="netresFieldValue" id="netresFieldValue" alt="allowBlank:false"><option value="">请选择</option>'+string;
			 jq(tr).find("#netresTableName").val(json[0][deviceSpecialty][j].tableName);
			 jq(tr).find("#netresFieldName").val(json[0][deviceSpecialty][j].fieldName);
			 jq(tr).find("#netresCHTableName").val(json[0][deviceSpecialty][j].resourceName);
			 jq(tr).find("#netresCHFieldName").val(json[0][deviceSpecialty][j].fieldChName);
	 	}else{
	 		 jq(tr).find("#netresTableName").val(json[0][deviceSpecialty][j].tableName);
	 		 htmlStr = '<select name="netresFieldValue" id="netresFieldValue"><option value="">请选择</option>'+string;
			 jq(tr).find("#netresCHTableName").val('N/A');
			 jq(tr).find("#netresCHFieldName").val('N/A');
	 	}
	 }
	}
	htmlStr += "</select>";
	jq(tr).find("#tdnetresFieldValue").html(htmlStr);
	jq(tr).find("#deviceTypeName").val(jq(obj).find('option:selected').text());
}

function addRow(obj){
  	var trEl = obj.parentNode.parentNode;
  	var newTR = trEl.cloneNode(true);
  	clearTable(newTR);
    trEl.parentNode.appendChild(newTR);
    if(trEl.rowIndex == 1){
    	trEl.getElementsByTagName("IMG")[1].style.display = "none";
    }else{
	   	trEl.getElementsByTagName("IMG")[1].style.display = "block";
    }
   	trEl.getElementsByTagName("IMG")[0].style.display = "none";
	newTR.getElementsByTagName("IMG")[0].style.display = "block";
	newTR.getElementsByTagName("IMG")[1].style.display = "block";
	var td = jq(newTR).find("td");
	var sumRow = jq("#tab").find("tr").length-1;
	_w_table_rowspan("#tab",1);  
	_w_table_rowspan("#tab",2);  
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
   
  }
  var selects = obj.getElementsByTagName("SELECT");
  for(var i=0;i<selects.length;i++){   
  	  if(selects[i].name=="deviceType" || selects[i].name=="netresFieldValue"){
  	  	selects[i].value = "";
  	  	jq(selects[i]).html('<option value="">请选择</option>');
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
      var o = tr.parentNode;
      var row = tr.rowIndex;
      var sumRow = jq("#tab").find("tr").length-1;
      if(row==sumRow){ //说明当前操作的是是最后一行
      	tr.previousSibling.getElementsByTagName("IMG")[0].style.display = "block";
      }
      tr.parentNode.deleteRow(tr.rowIndex-1);
    }
  }
}
  
  
//单元格合并
function _w_table_rowspan(_w_table_id,_w_table_colnum) {
 
            _w_table_firsttd="";
 
            _w_table_currenttd="";
 
            _w_table_SpanNum=0;
 
            _w_table_Obj=jq(_w_table_id+" tr td:nth-child("+_w_table_colnum+")");
 
            _w_table_Obj.each(function (i) {
 
                if (i==0) {
 
                    _w_table_firsttd=jq(this);
 
                    _w_table_SpanNum=1;
 
                } else {
 
                    _w_table_currenttd=jq(this);
 
                    if (_w_table_firsttd.text() ==_w_table_currenttd.text()) {
 
                        _w_table_SpanNum++;
 
                        _w_table_currenttd.hide(); //remove();  
 
                        _w_table_firsttd.attr("rowSpan",_w_table_SpanNum);
 
                    } else {
 
                        _w_table_firsttd=jq(this);
 
                        _w_table_SpanNum=1;
 
                    }
 
                }
 
            });
 
        }

  
</script>
 
<br/>
	
    
<form action="${app}/partner/deviceInspect/inspectMapping.do?method=savePartnerDeviceDeploy" method="post" id="taskOrderForm" name="taskOrderForm" >
	<table class="table list" cellpadding="0" cellspacing="0" id="tab">
		<thead>
			<tr><th>巡检专业</th>
				<th>代维资源类别</th>
				<th>网络资源专业</th>
				<th>网络资源类别</th>
				<th>网络资源名</th>
				<th>类别区分</th>
				<th>类别区分值</th>
				<th>操作</th></tr>
		</thead>
		<tr>
			<td id="tdspecialty">
			<eoms:comboBox  name="specialty" id="specialty" sub="inspectType" defaultValue=""
	        			initDicId="11225"  styleClass="select" />
			</td>
			<td id="tdresourceTeype">
				<eoms:comboBox name="inspectType" defaultValue="" id="inspectType" initDicId="${zhuanye}"   styleClass="select" /> 
			</td>
			<td>
				<select id="deviceSpecialty" name="deviceSpecialty" onchange="getInspectType(this)" alt="allowBlank:false">
					<option value="" >请选择</option>
					<c:forEach var="res" items="${map}">
						<c:set var="i" value="${res.key }"></c:set>
						<option value="${res.key }">${ keyMap[i] }</option>
					</c:forEach>
				</select>
				<input type="hidden" name="deviceSpecialtyName" id="deviceSpecialtyName" />
				<input type="hidden" name="deviceTypeName" id="deviceTypeName" />
			</td>
			<td id='tddeviceType'>
				<select class="select" id="deviceType" name="deviceType" onchange="getdeviceType(this)" alt="allowBlank:false">
					<option value="">请选择</option>
				</select>
				
			</td>
			<td >
				<input type="hidden"  class="text" style="width:110px" name="netresTableName" id="netresTableName" />
				<input type="text" readonly="readonly" class="text" style="width:110px" name="netresCHTableName" id="netresCHTableName" />
			</td>
			<td >
				<input type="hidden" class="text" style="width:110px" name="netresFieldName" id="netresFieldName"/>
				<input type="text" readonly="readonly" class="text" style="width:110px" name="netresCHFieldName" id="netresCHFieldName"/>
			</td>
			<td id="tdnetresFieldValue">
				<select name="netresFieldValue" id="netresFieldValue">
					<option value="">请选择</option>
				</select>
			</td>
			<td>
				<img src="${app}/images/icons/add.gif" onclick="addRow(this)" style="cursor:hand;display:block">
				<img src="${app}/images/icons/delete.gif" onclick="delRow(this)" style="cursor:hand;display:none">
			</td>

		</tr>
	</table>
	<input type="submit" value="提交" class="btn" />
</form>

<%@ include file="/common/footer_eoms.jsp"%>