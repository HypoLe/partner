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
       v = new eoms.form.Validation({form:'checkPointForm'});
       changeType("");
       jq("#type").bind("change",changeType);
       jq("#checkPointSegmentId option[value='${checkPoint.checkPointSegmentId}']").attr("selected",true);
       
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
    function removeFile(obj) { 
	  	obj.parentNode.removeChild(obj);
	   	window.open ('${app}/baseinfo/baseStation.do?method=list&type=station','newwindow',
		 'height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
  }
  var idseed = 1;
	   function setCompactNostr(obj,baseStationName){
	  document.getElementById("baseStationName").value = baseStationName;
	  document.getElementById("baseStationId").value=obj;
  }	
 </script>
 <%--
 	<input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/><br/><br/>----%>
	<div align="center">巡检点基本信息修改页面</div><br>
<form action="checkpoint.do?method=edit" method="post"
	id="checkPointForm" name="checkPointForm">
	
	<!-- hidden area start -->
		<input type="hidden" name="id" value="${checkPoint.id}" />
		<input type="hidden" name="addTime" value="${checkPoint.addTime}" />
		<input type="hidden" name="checkPointId" value="${checkPoint.id}"/>
		<input type="hidden" name="baseStationId" value="${baseStation.id}" />
		<input type="hidden" name="handleWellId" value="${handleWell.id}" />
		<input type="hidden" name="lightJoinBoxId" value="${lightJoinBox.id}" />
		<input type="hidden" name="poleId" value="${pole.id}" />
		<input type="hidden" name="oldType" value="${checkPoint.type}"/>
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
	<!-- hidden area end -->
	
		<table id="sheet" class="formTable">
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 资源点编码* 
			</td>
			<td class="content">
				<input class="text" type="text" name="resourceCode" value="${checkPoint.resourceCode }"
					id="resourceCode" alt="allowBlank:false" />
			</td>
			<td class="label">
				资源点名称*
			</td>
			<td class="content">
				<input class="text" type="text" name="resourceName" value="${checkPoint.resourceName }"
					id="resourceName" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 经度* 
			</td>
			<td class="content">
				<input class="text" type="text" name="longitude" value="${checkPoint.longitude }"
					id="longitude" alt="allowBlank:false" />
			</td>
			<td class="label">
				纬度*
			</td>
			<td class="content">
				<input class="text" type="text" name="latitude" value="${checkPoint.latitude }"
					id="latitude" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 地址* 
			</td>
			<td class="content">
				<input class="text" type="text" name="address" id="address" value="${checkPoint.address }"
					alt="allowBlank:false" />
			</td>
			<td class="label">
			 是否为检查点* 
			</td>
			<td class="content">
				<eoms:comboBox name="isCheckPoint"
					id="isCheckPoint" initDicId="10301" defaultValue="${checkPoint.isCheckPoint }" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				所属光缆段*
			</td>
			<td class="content">
				
				<input class="text" type="text" name="cableSegment" id="cableSegment" value="${checkPoint.cableSegment}"
					alt="allowBlank:false"/>
			</td>
			<td class="label">
				所属光缆系统*
			</td>
			<td class="content">
				
				<input class="text" type="text" name="cableSystem" id="cableSystem" value="${checkPoint.cableSystem}"
					alt="allowBlank:false"/>
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
				<input class="text" type="text" name="importantFocus"  value="${checkPoint.importantFocus }"
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
				<eoms:comboBox name="type" defaultValue="${checkPoint.type}"
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
				<input class="text" type="text" name="handleWellNum" id="handleWellNum" value="${handleWell.handleWellNum }"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				承载光缆中继段名称*
			</td>
			<td class="content" >
				<input class="text" type="text" name="loadCableSegmentName" value="${handleWell.loadCableSegmentName }"
					id="loadCableSegmentName" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr class="handleWell">
			<td class="label">
				巡检员*
			</td>
			<td class="content" >
				<input class="text" type="text" name="checkPointUser" id="checkPointUser" value="${handleWell.checkPointUser }"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				承载系统级别*
			</td>
			<td class="content" >
				<input class="text" type="text" name="loadSysLevel" value="${handleWell.loadSysLevel }"
					id="loadSysLevel" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr class="handleWell">
			<td class="label">
				所属管道名*
			</td>
			<td class="content" >
				<input class="text" type="text" name="pipelineName" id="pipelineName" value="${handleWell.pipelineName }"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				是否接头井*
			</td>
			<td class="content" >
								<eoms:comboBox name="isJointWell" defaultValue="${handleWell.isJointWell }"
					id="isJointWell" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		
		<tr class="handleWell">
			<td class="label">
				是否有光缆预留*
			</td>
			<td class="content" >
				<eoms:comboBox name="isCableObligate" defaultValue="${handleWell.isCableObligate }"
					id="isCableObligate" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				是否重要关注点*
			</td>
			<td class="content" >
				<eoms:comboBox name="isImportantFocus" defaultValue="${handleWell.isImportantFocus }"
					id="isImportantFocus" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		<tr class="baseStation">
		<td class="label">
			关联基站
			</td>
			<td colspan="3" class="content">
				<input type="text" value="${baseStation.baseStationName }" class="text" name="baseStationName" id="baseStationName" onclick="relationship()" />
				<div id="fileDiv">
					    	<input type="hidden" id="baseStationId" name="baseStationId" value="${baseStation.id }">
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
				<input class="text" type="text" name="lightJoinBoxName" value="${lightJoinBox.lightJoinBoxName }"
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
				<input class="text" type="text" name="poleNum" id="poleNum" value="${pole.poleNum }"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				巡检员*
			</td>
			<td class="content" >
				<input class="text" type="text" name="poletUser" value="${pole.poletUser }"
					id="poletUser" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr class="pole">
			<td class="label">
				承载光缆中继段名称*
			</td>
			<td class="content" >
				<input class="text" type="text" name="loadCableSegmentName" id="loadCableSegmentName" value="${pole.loadCableSegmentName }"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				承载系统级别*
			</td>
			<td class="content" >
				<input class="text" type="text" name="loadSysLevel" value="${pole.loadSysLevel }"
					id="loadSysLevel" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr  class="pole">
			<td class="label">
				所属杆路名*
			</td>
			<td class="content" >
				<input class="text" type="text" name="poleName" id="poleName" value="${pole.poleName }"
					alt="allowBlank:false" />
			</td>
			<td class="label">
				是否接头杆*
			</td>
			<td class="content" >
								<eoms:comboBox name="isJointPole" defaultValue="${pole.isJointPole }"
					id="isJointPole" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		
		<tr  class="pole">
			<td class="label">
				是否有光缆预留*
			</td>
			<td class="content" >
				<eoms:comboBox name="isCableObligate" defaultValue="${pole.isCableObligate }"
					id="isCableObligate" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				是否重要关注点*
			</td>
			<td class="content" >
				<eoms:comboBox name="isImportantFocus" defaultValue="${pole.isImportantFocus }"
					id="isImportantFocus" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
	
		</table>

		<br/>
		<input type="submit" class="btn"  value="保存修改" />
		<input type="reset" class="btn"  value="重置" />
</form>

<%@ include file="/common/footer_eoms.jsp"%>