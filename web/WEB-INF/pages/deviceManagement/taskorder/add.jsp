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
            v = new eoms.form.Validation({form:'taskOrderForm'});
            v.custom = function(){ 
            	return true;
            };
            
            v1 = new eoms.form.Validation({form:'importForm'});
            
			$('deadline').value = new Date().format('Y-m-d H:i:s'); 
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
	url : "${app}/taskorder/taskOrderAction.do?method=importRecord",
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
 
<content tag="heading">
<c:out value="代维任务工单录入" />
</content>  <br/><br/>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="taskOrderAction.do?method=importRecord" method="post" enctype="multipart/form-data" id="importForm" name="importForm">
			<fieldset>
				<legend>从Excel导入</legend>
				<table id="sheet" class="formTable">
					<tr>
						<td class="label">选择Excel文件</td>
						<td width="10%">
							<input type="file" name="formFile" class="file"  alt="allowBlank:false"/>	
						</td>

						<td width="35%">
							<html:button onclick="saveImport()" styleClass="btn" property="method.save"
			styleId="method.save" value="导入" ></html:button>	
						</td> 
					</tr>
				</table>
			</fieldset>
	</form>
	<br>
	<a href="template.xls">模板文件</a>
</div>

<br/>
	
	
	
	
	
	
	
	     
<form action="taskOrderAction.do?method=add" method="post" id="taskOrderForm" name="taskOrderForm" >
	<table id="taskOrderTable" class="formTable">
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 任务主题* 
			</td>
			<td class="content" colspan="3">
				<input class="text" type="text" name="topic"
					id="topic" alt="allowBlank:false" />
			</td>
			
		</tr>
		<tr>
			<td class="label">
			 网络分类* 
			</td>
			<td class="content">
				<eoms:comboBox name="netGroup"
					id="sheetType" initDicId="1010104" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				任务类型*
			</td>
			<td class="content">
				<eoms:comboBox name="type"
					id="sheetType" initDicId="1010102" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				任务描述*
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="description"
					id="description" alt="allowBlank:false"></textarea>
			</td> 
		</tr>
		<tr>
			<td class="label">
				工单附件
			</td>
			<td class="content" colspan="3">
				<eoms:attachment scope="request" idField="attachment" name="attachment" 
					appCode="faultSheet" alt="allowBlank:true" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				备注信息
			</td>
			<td colspan="3" class="content">
				<textarea class="textarea max" name="remarks1"
					id="remarks1" alt="allowBlank:true"></textarea>
			</td> 
		</tr>
</table>



		
		
	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存" ></html:submit>	
		
	 
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>

</form>



<%@ include file="/common/footer_eoms.jsp"%>