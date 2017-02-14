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
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
       changeType("");
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
	
 </script>

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>

<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
 
	<div align="center">基站故障记录详情页面</div><br>
	<table id="sheet" class="formTable">
		
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 资源点编码* 
			</td>
			<td class="content">
				${checkPoint.resourceCode }
			</td>
			<td class="label">
				资源点名称*
			</td>
			<td class="content">
				${checkPoint.resourceName }
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 经度* 
			</td>
			<td class="content">
				${checkPoint.longitude }
			</td>
			<td class="label">
				纬度*
			</td>
			<td class="content">
				${checkPoint.latitude }
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 地址* 
			</td>
			<td class="content">
				${checkPoint.address }
			</td>
			<td class="label">
			 是否为检查点* 
			</td>
			<td class="content">
				<eoms:id2nameDB id="${checkPoint.isCheckPoint}" beanId="IItawSystemDictTypeDao" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				所属光缆段*
			</td>
			<td class="content">
				<eoms:id2nameDB id="${checkPoint.cableSegment}" beanId="IItawSystemDictTypeDao" />
			</td>
			<td class="label">
				所属光缆系统*
			</td>
			<td class="content">
				<eoms:id2nameDB id="${checkPoint.cableSystem}" beanId="IItawSystemDictTypeDao" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				所属巡检段*
			</td>
			<td class="content">
				<eoms:id2nameDB id="${checkPoint.checkPointSegmentId}" beanId="checkSegmentDao" />
			</td>
			<td class="label">
				重要关注点*
			</td>
			<td class="content" >
				${checkPoint.importantFocus }
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
				<input type="hidden" name="type" id="type" value="${checkPoint.type}"> 
				<eoms:id2nameDB id="${checkPoint.type}" beanId="IItawSystemDictTypeDao" />
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
				${handleWell.handleWellNum }
			</td>
			<td class="label">
				承载光缆中继段名称*
			</td>
			<td class="content" >
				${handleWell.loadCableSegmentName }
			</td>
		</tr>
		
		<tr class="handleWell">
			<td class="label">
				巡检员*
			</td>
			<td class="content" >
				${handleWell.checkPointUser }
			</td>
			<td class="label">
				承载系统级别*
			</td>
			<td class="content" >
				${handleWell.loadSysLevel }
			</td>
		</tr>
		
		<tr class="handleWell">
			<td class="label">
				所属管道名*
			</td>
			<td class="content" >
				${handleWell.pipelineName }
			</td>
			<td class="label">
				是否接头井*
			</td>
			<td class="content" >
				<eoms:id2nameDB id="${handleWell.isJointWell}" beanId="IItawSystemDictTypeDao" />
			</td>
		</tr>
		
		
		<tr class="handleWell">
			<td class="label">
				是否有光缆预留*
			</td>
			<td class="content" >
				<eoms:id2nameDB id="${handleWell.isCableObligate}" beanId="IItawSystemDictTypeDao" />
			</td>
			<td class="label">
				是否重要关注点*
			</td>
			<td class="content" >
				<eoms:id2nameDB id="${handleWell.isImportantFocus}" beanId="IItawSystemDictTypeDao" />
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
				基站名*
			</td>
			<td class="content" >
				${baseStation.baseStationName }
			</td>
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
				${lightJoinBox.lightJoinBoxName }
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
				${pole.poleNum }
			</td>
			<td class="label">
				巡检员*
			</td>
			<td class="content" >
				${pole.poletUser }
			</td>
		</tr>
		
		<tr class="pole">
			<td class="label">
				承载光缆中继段名称*
			</td>
			<td class="content" >
				${pole.loadCableSegmentName }
			</td>
			<td class="label">
				承载系统级别*
			</td>
			<td class="content" >
				${pole.loadSysLevel }
			</td>
		</tr>
		
		<tr  class="pole">
			<td class="label">
				所属杆路名*
			</td>
			<td class="content" >
				${pole.poleName }
			</td>
			<td class="label">
				是否接头杆*
			</td>
			<td class="content" >
				<eoms:id2nameDB id="${pole.isJointPole}" beanId="IItawSystemDictTypeDao" />
			</td>
		</tr>
		
		
		<tr  class="pole">
			<td class="label">
				是否有光缆预留*
			</td>
			<td class="content" >
				<eoms:id2nameDB id="${pole.isCableObligate}" beanId="IItawSystemDictTypeDao" />
			</td>
			<td class="label">
				是否重要关注点*
			</td>
			<td class="content" >
				<eoms:id2nameDB id="${pole.isImportantFocus}" beanId="IItawSystemDictTypeDao" />
			</td>
		</tr>
	
		</table>


</form>

<%@ include file="/common/footer_eoms.jsp"%>