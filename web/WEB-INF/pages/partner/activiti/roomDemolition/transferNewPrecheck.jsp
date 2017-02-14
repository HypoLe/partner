<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/showNewDistributeAapproveBack.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>

<style>
	.test{
		font-family:verdana,​arial,​helvetica,​sans-serif;
		font-size:12px;
		color:	rgb(68,​ 68,​ 68);
		line-height:16px;
		text-align:	start;
		vertical-align:	top;
	}	
</style>

<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突


Date.prototype.format =function(format)
{
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
                RegExp.$1.length==1? o[k] :
                        ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}
var roleTree;
var v;

  Ext.onReady(function(){
  var values = "${photoList}";
	if (values!=null){
	
	var photoDiv =  document.getElementById("photoDiv");  	
	
		photoDiv.style.display = "block";
	}
  
  	//回显资产
	//var valuesAssetList = "${assetList}";
	if (${!empty assetList}){
		var assetDiv =  document.getElementById("assetDiv");  	
		assetDiv.style.display = "block";
	} 
  
   v = new eoms.form.Validation({form:'theform'});
    v.custom = function(){ 
     if(eoms.$('comparisonResults').value.trim()!="yes"){
       alert("事前照片与巡检经纬度比对误差范围超过1千米，不能派发工单"); return false; 
      }
	var t01 = jq("#myAssetTable tr").length;
     if(t01=='0'){
       	alert("资产不能为空"); return false;
      }
		
     if(eoms.$('stationType').value.trim()==""){
       alert("机房类型不能为空"); return false; 
      } 
     if(eoms.$('tempStationName').value.trim()==""){
       alert("机房名称不能为空"); return false; 
      } 
     if(eoms.$('tempStationLevel').value.trim()==""){
       alert("机房级别不能为空"); return false; 
      } 
     if(eoms.$('photoIds').value.trim()==""){
       alert("请选择事前照片"); return false; 
      } 
     if(eoms.$('sheetAccessories').value.trim()==""){
       alert("附件（方案）不能为空"); return false; 
      } 
      return true;
   }
   
    //回显 机房类型
  	var targetValue="${roomDemolition.stationType}";
  	if(targetValue=="1270401"||targetValue=="1270402"||targetValue=="1270403"){
  		jq("#tempStationName").removeAttr("disabled");
	 	jq("#tempStationLevel").removeAttr("disabled");
  	}
  	
  	var obj = document.getElementById("stationType");
  	if(obj){
    var options = obj.options;
    if(options){
      var len = options.length;
      for(var i=0;i<len;i++){
        if(options[i].value == targetValue){
          options[i].defaultSelected = true;
          options[i].selected = true;
          return true;
        }
      }
    } else {
      alert('missing element(s)!');
    }
  } else {
    alert('missing element(s)!');
  }
   
});

  /* function changeType1(){
       var myDate=new Date();
       var b =$('sheetCompleteLimit').value*60;
       myDate.setMinutes(myDate.getMinutes() + b, myDate.getSeconds(), 0)
       $('dueDate').value = myDate.format('yyyy-MM-dd hh:mm:ss');
   }*/
   
   
</script>
<!--  <script type="text/javascript">
	function check(v){
		var a=/^(\d+)(\.\d+)?$/;
		if(v==''){
			alert("项目金额估算不可以为空!");
			return;
		}
		if(!a.test(v)){
			alert("项目金额估算请输入整数或小数");
			document.getElementById("projectAmount").value=""; 
			return;
		}
		if(v>=50000){
			alert("项目金额估算不能大于等于50000");
			document.getElementById("projectAmount").value="";
			return;
		}
	}
</script>-->
<script type="text/javascript">
	//机房类型控制机房名称
	 jq(function(){
	 	jq("#comparisonResults").attr("disabled","disabled");	 	
	 	jq("#stationType").change(function () {
	 		jq("#stationId").val("");
	 		jq("#stationName").val("");
	 		jq("#stationLevelId").val("");
	 		jq("#stationLevel").val("");
	 		jq("#tempStationName").val("");
	 		jq("#tempStationLevel").val("");
	 		if(jq(this).val()==""){
	 			jq("#tempStationName").attr("disabled","disabled");
	 			jq("#selectButton").attr("disabled","disabled");
	 			jq("#tempStationLevel").attr("disabled","disabled");
	 			jq("#comparisonResults").val("no");
	 		}else if(jq(this).val()=="1122501"||jq(this).val()=="1122505"||jq(this).val()=="10123160101"||jq(this).val()=="10123160102"){
	 			jq("#selectButton").removeAttr("disabled");
	 			jq("#tempStationName").attr("disabled","disabled");
	 			jq("#tempStationLevel").attr("disabled","disabled");
	 			jq("#comparisonResults").val("no");
	 		}else if(jq(this).val()=="1270401"||jq(this).val()=="1270402"||jq(this).val()=="1270403"){
	 			jq("#selectButton").attr("disabled","disabled");
	 			jq("#tempStationName").removeAttr("disabled");
	 			jq("#tempStationLevel").removeAttr("disabled");
	 			jq("#comparisonResults").val("yes");
	 		}
	 	});
       });

 	//打开选择机房界面
	function selectRes(){
		var tempStationType =document.getElementById("stationType").value;//有效，能得到正确的值.				
		var url = '${app}/activiti/roomDemolition/roomDemolition.do?method=chooseRoom&region=${region}&executeDept=${executeDept}&specialty='+tempStationType;
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
	
	/**
	* 设置机房ID、机房名称、机房级别ID
	*/
	function setRes(returnValue){
	//alert(returnValue);
		if(returnValue){
			var index = returnValue.indexOf(',');
			if(index != -1){
			var strs= new Array(); //定义一数组 
			strs=returnValue.split(","); //字符分割 
			var resId = strs[0];
	        var resName = strs[1];
	        var stationLevelId=strs[2];
	        var stationLevel = strs[3];	
	        var roomLongitude = strs[4];	
	        var roomLatitude = strs[5];	
	               	
        	document.getElementById('stationId').value = resId;
        	document.getElementById('stationName').value = resName;
        	document.getElementById('tempStationName').value = resName;
        	document.getElementById('stationLevelId').value = stationLevelId;
        	document.getElementById('stationLevel').value = stationLevel;
        	document.getElementById('tempStationLevel').value = stationLevel;
        	document.getElementById('roomLongitude').value = roomLongitude;
        	document.getElementById('roomLatitude').value = roomLatitude;

			getDistance();
	        //	setTaskAssignee(resId);
			}
        }
	}
	

  function setTaskAssignee(resId){
	
	var url="${app}/partner/res/PnrResConfig.do?method=getChargePerson&resId="+resId;
		Ext.Ajax.request({
							url : url ,
							method: 'POST',
							success: function ( result, request ) { 
								res = result.responseText;
								var json = eval(res);
								var u = json['0']['chargePerson'];
								var n = json['0']['personName'];
								if(u!=null && u!=''){
									setValue(u,n);				
								}
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}
	
	function getDistance(){
		var tempStationType =	document.getElementById('stationType').value;
		if(tempStationType=='1122501'||tempStationType=='1122505'||tempStationType=='10123160101'||tempStationType=='10123160102'){
			var roomLongitude =	document.getElementById('roomLongitude').value;
	//	alert(roomLongitude);
        var roomLatitude = document.getElementById('roomLatitude').value ;
      //  alert(roomLatitude);
        var potoAvgLongitude = document.getElementById('potoAvgLongitude').value;
        // alert(potoAvgLongitude);
		var potoAvgLatitude = document.getElementById('potoAvgLatitude').value;
		//alert(potoAvgLatitude);
		if(roomLongitude==""||roomLatitude==""||potoAvgLongitude==""||potoAvgLatitude==""){
			//alert("不通过");
			jq("#comparisonResults").val("no");
		}else{
			var url="${app}/activiti/roomDemolition/roomDemolition.do?method=calculationDistance&roomLongitude="+roomLongitude+"&roomLatitude="+roomLatitude+"&potoAvgLongitude="+potoAvgLongitude+"&potoAvgLatitude="+potoAvgLatitude;
				Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
						//alert(result.responseText);
						var distance =result.responseText;
						//var distance ="500";
						jq("#distance").val(distance);
						//alert(distance);
						if(distance > 200){
							//alert("不通过1111111111111");
							jq("#comparisonResults").val("no");
						}else{
							//alert("通过");
							jq("#comparisonResults").val("yes");
						}
					},
					failure: function ( result, request) { 
						alert("获得图片和机房之间的经纬度距离失败！");
						//alert("不通过");
						jq("#comparisonResults").val("no");
					} 
				});
			
		}
	}else if(tempStationType=='1270401'||tempStationType=='1270402'||tempStationType=='1270403'){
		jq("#comparisonResults").val("yes");
	}else{
		jq("#comparisonResults").val("no");
	}
}	

	 function savaDraft(){
		
		      document.getElementById("isDraft").value = "true";
   			  document.forms(0).submit() 
		 }
</script>

<html:form action="/roomDemolition.do?method=performAdd" styleId="theform" >
	<input id="themeId" type="hidden" name="themeId" value="${roomDemolition.id}"><!-- 工单ID -->
	<input type="hidden" id="isDraft" name="isDraft" value="" /><!-- 保存草稿标示 -->	
	<input id="processInstanceId" type="hidden" name="processInstanceId" value="${roomDemolition.processInstanceId}"><!-- 流程号 -->
	<input id="sheetId" type="hidden" name="sheetId" value="${roomDemolition.sheetId}"><!-- 工单编号 -->
    <input id="state" type="hidden" name="state" value="${roomDemolition.state}">
    <input type="hidden" id="roomLongitude" name="roomLongitude" value="${roomDemolition.roomLongitude }"/>
    <input type="hidden" id="roomLatitude" name="roomLatitude" value="${roomDemolition.roomLatitude }"/ >
    <input type="hidden" id="potoAvgLongitude" name="potoAvgLongitude" value="${roomDemolition.potoAvgLongitude }"/>
    <input type="hidden" id="potoAvgLatitude" name="potoAvgLatitude" value="${roomDemolition.potoAvgLatitude }"/>
    <input type="hidden" id="distance" name="distance" value="${roomDemolition.distance}"/>

<table id="sheet" class="formTable" >
	<tr>
	  <td class="label">工单名称*</td>
	  <td colspan="3">
	  <c:if test="${roomDemolition.theme ==null}">
	  
	    <input type="text" name="title" class="text max" id="title"
			value="${roomDemolition.theme}" alt="allowBlank:false,maxLength:120,vtext:'请输入工单名称，最大长度为60个汉字！'"/>
	  </c:if>
	  <c:if test="${roomDemolition.theme !=null}">
	    <input type="text" name="title" class="text max" value="${roomDemolition.theme}" readOnly=true/>
	  </c:if>
	  </td>
	</tr>

	<tr>
	  <td class="label">工单发起人*</td>
	  <td class="content">
	  <eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao"/>
	  <input type="hidden" name="initiator" value="${userId}" />
	  </td>
	  
	  <td class="label">发起人部门*</td>
	  <td class="content">
	  <eoms:id2nameDB id="${deptId}" beanId="tawSystemDeptDao"/>
	  <input type="hidden" id="dept" name="dept" value="${deptId}" />
	  </td>
	</tr>
	
	<tr>
	  <td class="label">地市*</td>
	  <td class="content">
	  <eoms:id2nameDB id="${city}" beanId="tawSystemAreaDao" />
	  <input type="hidden" id="city" name="city" value="${city}" />
	  </td>
	  
	  <td class="label">区县*</td>
	  <td class="content">
	  <eoms:id2nameDB id="${quxian}" beanId="tawSystemAreaDao" />
	  <input type="hidden" id="country" name="country" value="${quxian}" />
	  </td>
	</tr>
	
	<tr>
	  <td class="label">机房类型*</td>
	  <td class="content">
		  <select id="stationType" name="stationType" class="select">
					<option value="">
						请选择
					</option>
					<option value="1122501">
						基站
					</option>
					<option value="1122505">
						接入网
					</option>
					<option value="1270401">
						市级核心机房
					</option>
					<option value="1270402">
						区/县核心机房
					</option>
					<!-- <option value="1270403">
						乡镇支局（模块局）
					</option> -->
					<option value="10123160101">
						乡镇支局
					</option>
					<option value="10123160102">
						大型综合业务接入点
					</option>
		   </select>
	  </td>
	  
	  <td class="label">机房名称*</td>
	  <td class="content">
				<input class="text" type="text" name="tempStationName" 
					id="tempStationName" alt="allowBlank:false"  disabled="disabled" 
					value="${roomDemolition.stationName}" />
				<input type="hidden" name="stationName" id="stationName"
					value="${roomDemolition.stationName}" />
				<input type="hidden" name="stationId" id="stationId"
					value="${roomDemolition.stationId}" />
				<input type="button" class="btn" id="selectButton"
					name="selectButton" value="选择" onclick="selectRes()" disabled="disabled"/>
			</td>
	</tr>
	
	<tr>
	  <td class="label">机房级别*</td>
	  <td class="content">
	  <input type="text" class="text" id="tempStationLevel" name="tempStationLevel" value="${roomDemolition.stationLevel}" disabled="disabled"/>
	  <input type="hidden"  id="stationLevel" name="stationLevel" value="${roomDemolition.stationLevel}" />
	  <input type="hidden"  id="stationLevelId" name="stationLevelId" value="${roomDemolition.stationLevelId}" />
	  </td>
	  
	  <td class="label">机房面积</td>
	  <td class="content">
	  <input type="text" class="text" id="stationArea" name="stationArea" value="${roomDemolition.stationArea}" />
	  </td>
	</tr>
	
	<tr>
	  <td class="label">机房产权*</td>
	  <td class="content">
	  <eoms:comboBox name="stationEquity" id="stationEquity"
					defaultValue="${roomDemolition.stationEquity}" initDicId="12701"
					alt="allowBlank:false" styleClass="select" />
	  </td>
	  
	  <td class="label">年租金（元）*</td>
	  <td class="content">
	  <input type="text" class="text" id="annualRent" name="annualRent" value="${roomDemolition.annualRent}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>
	  </td>
	</tr>
	
	<tr>
	  <td class="label">租用日期*</td>
	  <td class="content">
		<input type="text" class="text" name="hireDate"
			readonly="readonly" id="hireDate"
			value="${eoms:date2String(roomDemolition.hireDate)}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1,0);"
			alt="allowBlank:false" />
	  </td>
	  
	  <td class="label">合同到期时间</td>
	  <td class="content">
	  <input type="text" class="text" name="contractTime"
			readonly="readonly" id="contractTime"
			value="${eoms:date2String(roomDemolition.contractTime)}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1,null);"
			 />
	  </td>
	</tr>
	
	<tr>
	  <td class="label">光缆纤芯数*</td>
	  <td class="content">
	  <input type="text" class="text" id="opticcableNum" name="opticcableNum" value="${roomDemolition.opticcableNum}" alt="re:/^(0|[1-9]\d*)$/,re_vt:'请输入正整数'" />
	  </td>
	  
	  <td class="label">光缆改造方式*</td>
	  <td class="content">
	  <eoms:comboBox name="opticcableWay" id="opticcableWay"
					defaultValue="${roomDemolition.opticcableWay}" initDicId="12702"
					alt="allowBlank:false" styleClass="select" />
	  </td>
	</tr>
	
	<tr>
	  <td class="label">设备机架数*</td>
	  <td class="content">
	  <input type="text" class="text" id="equipmentRackNum" name="equipmentRackNum" value="${roomDemolition.equipmentRackNum}" alt="re:/^(0|[1-9]\d*)$/,re_vt:'请输入正整数'" />
	  </td>
	  
	  <td class="label">材料费用*</td>
	  <td class="content">
	  <input type="text" class="text" id="materialCost" name="materialCost" value="${roomDemolition.materialCost}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'" />
	  </td>
	</tr>
	
	<tr>
	  <td class="label">对应能耗系统机房名称*</td>
	  <td class="content">
	  <input type="text" class="text" id="energyStationName" name="energyStationName" value="${roomDemolition.energyStationName}" alt="allowBlank:false" />
	  </td>
	  
	  <td class="label">对应能耗系统机房编号*</td>
	  <td class="content">
	  <input type="text" class="text" id="energyStationCode" name="energyStationCode" value="${roomDemolition.energyStationCode}" alt="allowBlank:false" />
	  </td>
	</tr>
	
	<tr>
	  <td class="label">对应能耗系统是否关站*</td>
	  <td class="content">
	  <eoms:comboBox name="energyIsstation" id="energyIsstation"
					defaultValue="${roomDemolition.energyIsstation}" initDicId="12703"
					alt="allowBlank:false" styleClass="select" />
	  </td>
	  <td class="label">发起时间*</td>
	  <td class="content">
	  ${initiateTime}
	  <input type="hidden"  id="initiateTime" name="initiateTime" value="${initiateTime}" />
	  </td>
	</tr>
	
	<tr>
	  <td class="label">事前照片与巡检经纬度比对（允许误差范围200米）</td>
	  <td class="content" colspan="1">
	  <select id="comparisonResults" name="comparisonResults" class="select">
					<option value="">
						请选择
					</option>
					<option value="yes" ${comparisonResults == "yes" ? 'selected="selected"':'' }>
						通过
					</option>
					<option value="no" ${comparisonResults == "no" ? 'selected="selected"':'' }>
						不通过
					</option>
		   </select>
	  </td>
	   <td class="label">批次*</td>
	  <td class="content" colspan="3">
	<eoms:comboBox name="pici" id="pici"
					defaultValue="${roomDemolition.pici}" initDicId="12706"
					alt="allowBlank:false" styleClass="select" />
	  </td>
	</tr>
	
	 <tr>
	   	 <td class="label">
	    	事前照片*
		</td>	
		<td  colspan="3">
		<div id="photoDiv" style="display:none">
				<table id="myPhotoTable">
				${photoList}
				</table>
			</div>
	   <input class="text" type="hidden" name="photoIds" readonly="true" id="photoIds" alt="allowBlank:false" value="${photoIds}"/>
			<input type="hidden" name="mainResId" id="mainResId" value="" />
			<input type="button" class="btn" value="选择" onclick="selectPhoto()" id="sig"/>
			<!-- <input type="hidden" name="photoIds" id="photoIds" value=""/> -->
		
	    </td>
	</tr>
	
	<tr>
		<td class="label">
			描述
		</td>
		<td class="content" colspan="3">
			<textarea class="textarea max" name="description" id="description">${roomDemolition.description}</textarea>
		</td>
	</tr>

	  <tr>
		    
		    <td class="label">
		    	附件（方案）*
			</td>	
			<td colspan="3">
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
		          				
		    </td>
	   </tr>
	  
	  <!-- 资产 -->
	   <tr>
	   	 <td class="label">
	    	资产*
		</td>	
		<td  colspan="3">
	   	<!-- 	<input  type="hidden" class="text" id="assetIds" name="assetIds" alt="allowBlank:false" value="${assetIds}"/>  -->
	   		<input  type="hidden" class="text" id="assetIds" name="assetIds" alt="allowBlank:true" value="${assetIds}"/>
			<input type="hidden" name="mainResId" id="mainResId" value="" />
			<input type="button" class="btn" id="assetsButton" name="assetsButton" value="添加" onclick="selectAssets()" />
	    </td>
	</tr>
	
	<tr>
		<td colspan="4">
		<div id="assetDiv" style="display:none">
				<table id="myAssetTable" border="1">
				<tr>
				<th>资产名称</th><th>资产标签号</th><th>资产类别</th><th>设备型号</th><th>启用日期</th>
				<th>资产使用月数</th><th>原值</th><th>累计折旧</th><th>资产净产</th><th>累计减值准备</th>
				<th>退网使用方向</th><th>操作</th>
				</tr>
				${assetList}
				</table>
			</div>
	    </td>
	</tr> 
	   			  
</table>
<br/>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save"  styleId="method.save">
				派发
			</html:submit>
			<!-- <c:if test="${access eq '1'}">
				<html:button styleClass="btn" property="" onclick="return savaDraft();" >
					保存
				</html:button>
			</c:if>	-->
		</div>
	</html:form>
<script type="text/javascript">
//资产添加脚本
function selectAssets() {
	var tempAssetIds=document.getElementById("assetIds").value;
    var url = '${app}/activiti/roomDemolition/roomAssets.do?method=queryAssets&assetIds='+tempAssetIds;
    var _sHeight = 600;
    var _sWidth = 820;
    var sTop = (window.screen.availHeight - _sHeight) / 2;
    var sLeft = (window.screen.availWidth - _sWidth) / 2;
    var sFeatures = "dialogHeight: " + _sHeight + "px; dialogWidth: " + _sWidth + "px; dialogTop: " + sTop + "; dialogLeft: " + sLeft + "; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
    window.showModalDialog(url, window, sFeatures);
}


function setAssetIds(selectedAssetlist,selectedAssetIds){
	var assetIds = document.getElementById("assetIds");
	assetIds.value+=selectedAssetIds;
	var assetDiv = document.getElementById("assetDiv");
		assetDiv.style.display="block";
	for(var i=0;i<selectedAssetlist.length;i++){
		var id = selectedAssetlist[i].id;
		var assetNumbers = selectedAssetlist[i].assetNumbers;
		var assetName = selectedAssetlist[i].assetName;
		var assetsSort = selectedAssetlist[i].assetsSort;
		var modelVersion = selectedAssetlist[i].modelVersion;
		var assetsStartTime = selectedAssetlist[i].assetsStartTime;
		var assetsMonthNum = selectedAssetlist[i].assetsMonthNum;
		var originalValue = selectedAssetlist[i].originalValue;
		var accumulatedDepreciation = selectedAssetlist[i].accumulatedDepreciation;
		var assetsNet = selectedAssetlist[i].assetsNet;
		var assetsDevalue = selectedAssetlist[i].assetsDevalue;
		
		addOneAssets(id,assetName,assetNumbers,assetsSort,modelVersion,assetsStartTime,assetsMonthNum,originalValue,accumulatedDepreciation,assetsNet,assetsDevalue);
		//saddAssetMess(id,assetNumbers,assetName,assetTagNumber);
		}
	}
function addOneAssets(id,assetName,assetNumbers,assetsSort,modelVersion,assetsStartTime,assetsMonthNum,originalValue,accumulatedDepreciation,assetsNet,assetsDevalue){
		var myTable = document.getElementById("myAssetTable");
		var newTR = myTable.insertRow(myTable.rows.length);
		newTR.insertCell(0).innerHTML="<input type='hidden' name='hId' value='"+id+"' />"+assetName;
		newTR.insertCell(1).innerHTML=assetNumbers;
		newTR.insertCell(2).innerHTML=assetsSort;
		newTR.insertCell(3).innerHTML=modelVersion;
		newTR.insertCell(4).innerHTML=assetsStartTime;
		newTR.insertCell(5).innerHTML=assetsMonthNum;
		newTR.insertCell(6).innerHTML=originalValue;
		newTR.insertCell(7).innerHTML=accumulatedDepreciation;
		newTR.insertCell(8).innerHTML=assetsNet;
		newTR.insertCell(9).innerHTML=assetsDevalue;
		newTR.insertCell(10).innerHTML="<select onchange='setIdAndExitDirection(this)'><option value='1270501'>闲置-可利用</option><option value='1270502'>闲置-待报废</option></select>";
		newTR.insertCell(11).innerHTML="<a href='javascript:;' onclick='delRow(this)'>删除</a><input type='hidden' name='idAndExit' value='"+id+","+"1270501"+"' />";
		
		
}
function addAssetMess(id,assetNumbers,assetName,assetTagNumber){
		var myTable = document.getElementById("myAssetTable");
		var newTR = myTable.insertRow(myTable.rows.length);
		//newTR.insertCell(0).innerHTML="主键:";
		//newTR.insertCell(1).innerHTML=id;
		newTR.insertCell(0).innerHTML="<input type='hidden' name='hId' value='"+id+"' />";
		newTR.insertCell(1).innerHTML="资产编号:";
		newTR.insertCell(2).innerHTML=assetNumbers;
		newTR.insertCell(3).innerHTML="资产名称:";
		newTR.insertCell(4).innerHTML=assetName;
		newTR.insertCell(5).innerHTML="资产标签号:";
		newTR.insertCell(6).innerHTML=assetTagNumber;
		newTR.insertCell(7).innerHTML="退网使用方向:";
		newTR.insertCell(8).innerHTML="<select onchange='setIdAndExitDirection(this)'><option value='1270501'>闲置-可利用</option><option value='1270502'>闲置-待报废</option></select>";
		newTR.insertCell(9).innerHTML="<a href='javascript:;' onclick='delRow(this)'>删除</a>";
		newTR.insertCell(10).innerHTML="<input type='hidden' name='idAndExit' value='"+id+","+"1270501"+"' />";		
	}

function setIdAndExitDirection(obj){
	var tempId=jq(obj).parent().parent().find("input[name='hId']").val();
	var tempValue=tempId+","+obj.value;
	jq(obj).parent().parent().find("input[name='idAndExit']").val(tempValue);
	//alert(obj.value);
	//alert(jq(obj).parent().parent().find("input[name='bb']").val());
	//alert(jq(obj).parent().find("input[name=bb]").val());
	//jq(" + input")
}


//得到行对象
function getRowObj(obj)
{
	var i = 0;
	while(obj.tagName.toLowerCase() != "tr"){
		obj = obj.parentNode;
	 if(obj.tagName.toLowerCase() == "table"){
		return null;
		}
	}
	return obj;
}

//删除行
function delRow(obj){
	var tr = this.getRowObj(obj);
	if(tr != null){
		var tempId=jq(tr).find("td input[name=hId]").val()+",";
		var tempAssetIds=jq("#assetIds").val();
		tempAssetIds=tempAssetIds.replace(tempId,"");
		jq("#assetIds").val(tempAssetIds);
		tr.parentNode.removeChild(tr);
		
		//当表格中的所有行都删除了，则隐藏div
	    var t01 = jq("#myAssetTable tr").length;
        if(t01=='0'){
        	jq("#assetDiv").css("display","none");
        }
		
	}else{
		throw new Error("the given object is not contained by the table");
	}
}


</script>	
		
	<script type="text/javascript">
	/**
	*  打开图片选择页面
	*/
	function selectPhoto(){	
		var selectedPhotoIds=document.getElementById("photoIds").value;
		var photoType="roomDemolition";	//机房拆除
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=conditionSelectPhoto&selectedPhotoIds='+selectedPhotoIds+"&photoType="+photoType;
		//var url = '${app}/activiti/roomDemolition/roomDemolition.do?method=conditionSelectPhoto';
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
	
	
	/**
	* 设置图片名称和ID
	*/
	function setPhoto(photoes,photoIds){
	var photoId = document.getElementById("photoIds");
	photoId.value=photoIds;
//	alert(photoes.length);
		if(photoes.length-1>0&&photoes.length-1<=5){
				alert("您选择的图片数量不足六张，请重新选择！");
				}else if(photoes.length-1>5){
				var photoDiv = document.getElementById("photoDiv");
					photoDiv.style.display="block";
					deleteAllChild();
				for(var i=0;i<photoes.length-1;i++){
					var id = photoes[i].id;
					var longitude = photoes[i].longitude;
					var latitude = photoes[i].latitude;
					var createtime = photoes[i].createtime;
					var userId = photoes[i].userId;
					addMess(longitude,latitude,createtime,userId);
					}
				document.getElementById('potoAvgLongitude').value = photoes[photoes.length-1].avgLongitude;
				document.getElementById('potoAvgLatitude').value = photoes[photoes.length-1].avgLatitude;
				
				getDistance();
				
				}
			}
	
	
	function addMess(longitude,latitude,createtime,userId){
		
		var myTable = document.getElementById("myPhotoTable");
		var newTR = myTable.insertRow(myTable.rows.length);
		var myTdTime1=newTR.insertCell(0);
		var myTdTime2=newTR.insertCell(1);
			myTdTime1.innerHTML = "拍照时间：";
			myTdTime2.innerHTML = createtime;
		
		var myTdPerson1 = newTR.insertCell(2);
		var myTdPerson2 = newTR.insertCell(3);
			myTdPerson1.innerHTML = "拍照人：";
			myTdPerson2.innerHTML = userId;
			
		var myTdLongitude1 = newTR.insertCell(4);
		var myTdLongitude2 = newTR.insertCell(5);
			myTdLongitude1.innerHTML = "经度：";
			myTdLongitude2.innerHTML = longitude;
			
		var myTdLatitude1 = newTR.insertCell(6);
		var myTdLatitude2 = newTR.insertCell(7);
			myTdLatitude1.innerHTML = "纬度：";
			myTdLatitude2.innerHTML = latitude;
	}
	
	function deleteAllChild(){
		
		var myTable = document.getElementById("myPhotoTable");
		while(myTable.hasChildNodes()){
		myTable.removeChild(myTable.lastChild);
		}
		
	}
	</script>
	

	
<!--

//-->
</script>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 approveBackSwitcher = new detailSwitcher();
  approveBackSwitcher.init({
	container:'approveBackHistory',
  	handleEl:'div.history-item-title-back'
  });
  
  
</script>