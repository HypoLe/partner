<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
        v = new eoms.form.Validation({form:'checkpointForm'});
        
        v1 = new eoms.form.Validation({form:'importForm'});
        v1.custom = function() {
       		var reg = "(.xls)$";
        	var file = jq("#importFile").val();
        	var right = file.match(reg);
        	if(right == null) {
        		alert("请选择Excel文件!");
        		return false;
        	} else {
        		return true;
        	}
        }
        
        jq("#sheet").find("tr.handleWell,tr.baseStation,tr.lightJoinBox,tr.pole").each(function(index){
			jq(this).hide();
       		jq(this).find(":input").each(function(index){
       			jq(this).attr("disabled",true);
       		});	
		}); 
        
        jq("#type").bind("change",changeType);
        
        //经纬度限制
        jq("#longitude").bind("change",numberValueCheck);
        jq("#latitude").bind("change",numberValueCheck);
        
        function numberValueCheck(e) {
        	var value = e.target.value;
        	var regStr = "^(\\d{1,}|\\d{1,}.\\d{1,4})$";
        	var right = value.match(regStr);
        	if(right == null) {
        		alert("请输入正确的数据,支持精确小数点后四位！");
        		e.target.value = "";
        	} else {
        		e.target.value = Number(e.target.value).toFixed(4);
        	}
        }
});
   function relationship(){

   if(jq("#baseStationName").val() == "" ){
	 window.open ('${app}/baseinfo/baseStation.do?method=list&type=station','newwindow',
	 'height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
   }else if(jq("#baseStationName").val() != "" ) 
   {	
   document.getElementById("baseStationName").value=""  
   document.getElementById("baseStationId").value=""  
   		window.open ('${app}/baseinfo/baseStation.do?method=list&type=station','newwindow',
	 'height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
	 }
  }
    var idseed = 1;
    
    function removeFile(obj) { 
   	obj.parentNode.removeChild(obj);
   	window.open ('${app}/baseinfo/baseStation.do?method=list&type=station','newwindow',
	 'height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
  }
  function setCompactNostr(obj,baseStationName){
  document.getElementById("baseStationName").value = baseStationName;
  document.getElementById("baseStationId").value=obj;
  	//idseed++;
	//Ext.DomHelper.append('fileDiv', {
	// 	html:'&nbsp;&nbsp;<input type="hidden" id="sbaseStationId" name="baseStationId" value="'+obj+'">'
    //});
  }
 function changeType(event) {
           	var type = jq("#type").val();
           	//type：字典值对应的value，参考配置
           	switch(type) {
           		case "1190101":
            		jq("#sheet tr.baseStation").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.handleWell,tr.lightJoinBox,tr.pole").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
		            	});	
					});
           			break;
           		
           		case "1190102":
           			jq("#sheet tr.handleWell").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.baseStation,tr.lightJoinBox,tr.pole").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
		            	});	
					});
           			break;
           		
           		case "1190103":
           			jq("#sheet tr.lightJoinBox").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.handleWell,tr.baseStation,tr.pole").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
		            	});	
					});
           			break;
           		
           		case "1190104":
		            jq("#sheet tr.pole").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.handleWell,tr.lightJoinBox,tr.baseStation").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
		            	});	
					});
           			break;
           	}
} 
 
function openImport(handler){
	var el = Ext.get('listQueryObject'); 
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
}


  
function saveImport() {
	//表单验证
	if(!v1.check()) {
		return;
	}
	
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
	url : "${app}/checkpoint/checkpoint.do?method=importCheckPoint",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		},
		failure : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		}
    });
	
}
</script>
 
<div align="center">巡检点基本信息添加页面</div>	<br>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/layout_add.png"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="checkpoint.do?method=importCheckPoint" method="post" enctype="multipart/form-data" id="importForm" name="importForm">
			<fieldset>
				<legend>从Excel导入</legend>
				<table class="formTable">
					<tr>
						<td class="label">选择Excel文件</td>
						<td width="35%">
							<input id="importFile" type="file" name="importFile" class="file"  alt="allowBlank:false"/>
						</td>
						<td class="label">选择巡检点类型</td>
						<td width="35%">
						<eoms:comboBox name="cpType" id="cpType" initDicId="11901" alt="allowBlank:false" styleClass="select" />
						</td>
					</tr>
				</table>
						<input type="button" onclick="saveImport()" value="确定"/>
			</fieldset>
	</form>
</div>

<br/>
<%--
	private String id;
	private String resourceCode;//资源点编码
	private String resourceName;//资源点名称
	private String address;//地址
	private String type;//类型
	private String longitude;//经度
	private String latitude;//纬度
	private String cableSegment;//所属光缆段
	private String cableSystem;//所属光缆系统
	private String checkPointSegmentId;//所属巡检段
	private String importantFocus;//重要关注点
	private String isCheckPoint;//是否为检查点    
--%>
<form action="checkpoint.do?method=add" method="post" id="checkpointForm" name="checkpointForm" >
	<table id="sheet" class="formTable">
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 资源点编码* 
			</td>
			<td class="content">
				<input class="text" type="text" name="resourceCode"
					id="resourceCode" alt="allowBlank:false" />
			</td>
			<td class="label">
				资源点名称*
			</td>
			<td class="content">
				<input class="text" type="text" name="resourceName"
					id="resourceName" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 经度* 
			</td>
			<td class="content">
				<input class="text" type="text" name="longitude"
					id="longitude" alt="allowBlank:false" />
			</td>
			<td class="label">
				纬度*
			</td>
			<td class="content">
				<input class="text" type="text" name="latitude"
					id="latitude" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 地址* 
			</td>
			<td class="content">
				<input class="text" type="text" name="address" id="address"
					alt="allowBlank:false" />
			</td>
			<td class="label">
			 是否为检查点* 
			</td>
			<td class="content">
				<eoms:comboBox name="isCheckPoint"
					id="isCheckPoint" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				所属光缆段*
			</td>
			<td class="content">
				<input class="text" type="text" name="cableSegment" id="cableSegment"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				所属光缆系统*
			</td>
			<td class="content">
				<input class="text" type="text" name="cableSystem" id="cableSystem"
					alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				所属巡检段*
			</td>
			<td class="content">
				<select name="checkPointSegmentId" id="checkPointSegmentId" alt="allowBlank:false" class="select">
						<option value="">请选择</option>
					<c:forEach items="${css}" var="cs">
						<option value="${cs.id }">${cs.segmentName}</option>
					</c:forEach>
				</select>
			</td>
			<td class="label">
				重要关注点*
			</td>
			<td class="content" >
				<input class="text" type="text" name="importantFocus"
					id="importantFocus" alt="allowBlank:false" />
			</td>
		</tr>
		
		
		<tr>
			<td colspan="4">
				<div  class="ui-widget-header">类型</div>
			</td>
		</tr>
		
		
		
		<tr>
			<td class="label">
			 类型* 
			</td>
			<td class="content" colspan="3">
				<eoms:comboBox name="type"
					id="type" initDicId="11901" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		<!-- 人手井 -->
		<%--
		private String id;
		private String checkPointUser;//巡检员
		private String loadSysLevel;//承载系统级别
		private String loadCableSegmentName;//承载光缆中继段名称
		private String handleWellNum;//人手井编号
		private String pipelineName;//所属管道名
		private String isJointWell;//是否接头井
		private String isCableObligate;//是否有光缆预留
		private String isImportantFocus;//是否重要关注点 
		--%>
		<tr class="handleWell">
			<td colspan="4">
				<div  class="ui-widget-header">人手井</div>
			</td>
		</tr>
		
		
		<tr class="handleWell">
			<td class="label">
				人手井编号*
			</td>
			<td class="content" >
				<input class="text" type="text" name="handleWellNum" id="handleWellNum"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				承载光缆中继段名称*
			</td>
			<td class="content" >
				<input class="text" type="text" name="loadCableSegmentName"
					id="loadCableSegmentName" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr class="handleWell">
			<td class="label">
				巡检员*
			</td>
			<td class="content" >
				<input class="text" type="text" name="checkPointUser" id="checkPointUser"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				承载系统级别*
			</td>
			<td class="content" >
				<input class="text" type="text" name="loadSysLevel"
					id="loadSysLevel" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr class="handleWell">
			<td class="label">
				所属管道名*
			</td>
			<td class="content" >
				<input class="text" type="text" name="pipelineName" id="pipelineName"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				是否接头井*
			</td>
			<td class="content" >
								<eoms:comboBox name="isJointWell"
					id="isJointWell" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		
		<tr class="handleWell">
			<td class="label">
				是否有光缆预留*
			</td>
			<td class="content" >
				<eoms:comboBox name="isCableObligate"
					id="isCableObligate" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				是否重要关注点*
			</td>
			<td class="content" >
				<eoms:comboBox name="isImportantFocus"
					id="isImportantFocus" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		<!-- 基站 -->
		<%--
		private String id;
		private String baseStationLevel;//等级
		private String baseStationName;//名称
		--%>
		<tr class="baseStation">
			<td colspan="4">
				<div  class="ui-widget-header">基站</div>
			</td>
		</tr>
		<tr class="baseStation">
		<td class="label">
			关联基站
			</td>
			<td colspan="3" class="content">
				<input type="text" value="" class="text" name="baseStationName" id="baseStationName" onclick="relationship()"/>
				<div id="fileDiv">
				<input type="hidden" id="baseStationId" name="baseStationId" value="">
				<!-- <input type="hidden" name="baseStationId" value="${str}"> -->
				</div>
			</td>
		<!-- --
			<td class="label">
				等级*
			</td>
			<td class="content" >
				<input class="text" type="text" name="baseStationLevel" id="baseStationLevel"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				名称*
			</td>
			<td class="content" >
				<input class="text" type="text" name="baseStationName"
					id="baseStationName" alt="allowBlank:false" />
			</td> -->
		</tr>
		
		<!-- 光交接箱 -->
		<%--
		private String id;
		private String lightJoinBoxName;//名称
		--%>
		<tr class="lightJoinBox">
			<td colspan="4">
				<div  class="ui-widget-header">光交接箱</div>
			</td>
		</tr>
		
		<tr class="lightJoinBox">
			<td class="label">
				名称*
			</td>
			<td class="content" colspan="3" >
				<input class="text" type="text" name="lightJoinBoxName"
					id="lightJoinBoxName" alt="allowBlank:false" />
			</td>
		</tr>
		
		
		<!-- 电杆 -->
		<%--
		private String id;
		private String poletUser;//巡检员
		private String loadSysLevel;//承载系统级别
		private String loadCableSegmentName;//承载光缆中继段名称
		private String poleNum;//电杆编号
		private String poleName;//所属杆路名
		private String isJointPole;//是否接头杆
		private String isCableObligate;//是否有光缆预留
		private String isImportantFocus;//是否重要关注点 
		--%>
		<tr class="pole">
			<td colspan="4">
				<div  class="ui-widget-header">电杆</div>
			</td>
		</tr>
		
		<tr class="pole">
			<td class="label">
				电杆编号*
			</td>
			<td class="content" >
				<input class="text" type="text" name="poleNum" id="poleNum"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				巡检员*
			</td>
			<td class="content" >
				<input class="text" type="text" name="poletUser"
					id="poletUser" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr class="pole">
			<td class="label">
				承载光缆中继段名称*
			</td>
			<td class="content" >
				<input class="text" type="text" name="loadCableSegmentName" id="loadCableSegmentName"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				承载系统级别*
			</td>
			<td class="content" >
				<input class="text" type="text" name="loadSysLevel"
					id="loadSysLevel" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr  class="pole">
			<td class="label">
				所属杆路名*
			</td>
			<td class="content" >
				<input class="text" type="text" name="poleName" id="poleName"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				是否接头杆*
			</td>
			<td class="content" >
								<eoms:comboBox name="isJointPole"
					id="isJointPole" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		
		<tr  class="pole">
			<td class="label">
				是否有光缆预留*
			</td>
			<td class="content" >
				<eoms:comboBox name="isCableObligate"
					id="isCableObligate" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				是否重要关注点*
			</td>
			<td class="content" >
				<eoms:comboBox name="isImportantFocus"
					id="isImportantFocus" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
	
		</table>
		
		<br/>

		<input type="submit" class="btn"  value="保存信息" />
		<input type="reset" class="btn"  value="重置" />
</form>



<%@ include file="/common/footer_eoms.jsp"%>