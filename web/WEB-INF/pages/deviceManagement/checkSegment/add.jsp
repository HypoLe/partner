<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

var myjs=jQuery.noConflict();
Ext.onReady(function(){
            v = new eoms.form.Validation({form:'checkSegmentForm'});
            v.custom = function(){ 
            	return true;
            };
            
         
});
   
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
	url : "${app}/taskOrder/taskOrderAction.do?method=importRecord",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
		},
		failure : function(form, action) {
			alert(action.result.infor);
		}
    });
	
}
</script>
<div align="center">巡检段信息录入</div>	
<br/>
<form action="checkSegmentAction.do?method=add" method="post" id="checkSegmentForm" name="checkSegmentForm" >
	<table id="checkSegmentTable" class="formTable">
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >巡检段基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 巡检段名称* 
			</td>
			<td class="content" colspan="3">
				<input class="text" type="text" name="name"
					id="topic" alt="allowBlank:false" />
			</td>
			
		</tr>
		<tr>
			<td class="label">
			传输线路类型* 
			</td>
			<td class="content">
				<eoms:comboBox name="lineType"
					id="sheetType" initDicId="1122401" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				传输线路级别*
			</td>
			<td class="content">
				<eoms:comboBox name="lineLevel"
					id="sheetType" initDicId="1122402" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				起始点标示*
			</td>
			<td class="content">
			  <input class="text" type="text" name="startFlag"
					id="topic" alt="allowBlank:false" />
			</td> 
			<td class="label">
				结束点标示*
			</td>
			<td class="content">
			  <input class="text" type="text" name="endFlag"
					id="topic" alt="allowBlank:false" />
			</td> 
		</tr>
		
		
		<tr>
			<td class="label">
				巡检段类型*
			</td>
			<td colspan="3" class="content">
				<eoms:comboBox name="segmentType"
					id="sheetType" initDicId="1122403" alt="allowBlank:false" styleClass="select" />
			</td> 
		</tr>
		
		<tr>
			<td class="label">
				管辖部门*
			</td>
			<td colspan="3" class="content">
			<div class="panel">
	 		 <input type="button" name="deptButton" id="deptButton" value="请选择部门" class="btn" alt="allowBlank:false"/>
	  		<input type="hidden" name="department" id="department"/>
	 		 <eoms:xbox id="userTree" dataUrl="${app}/xtree.do?method=dept" rootId="-1" rootText="部门树" valueField="department" handler="deptButton"
				 viewer="true" single="true"></eoms:xbox>
	  		</div>
			</td> 
		</tr>
</table>



		
		
	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="新增" ></html:submit>	
		
	 
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>

</form>



<%@ include file="/common/footer_eoms.jsp"%>