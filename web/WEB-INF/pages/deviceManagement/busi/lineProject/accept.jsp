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
            v = new eoms.form.Validation({form:'projectLineForm'});
            v.custom = function(){ 
            	return true;
            };
            
          //  v1 = new eoms.form.Validation({form:'importForm'});
          
          	
          	
			
			
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




  

</script>
 
<content tag="heading">
<c:out value="线路修缮施工信息" />
</content>  <br/><br/>
	




<br/>
	
	
	
	
	
	
	
	     
<form action="lineProjectAction.do?method=accept" method="post" id="projectLineForm" name="projectLineForm" >
	
	<input name="id" type="hidden" value="${projectBaseInfo.id}"/>
	<table id="projectLineTable" class="formTable">
		
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 工程名称
			</td>
			<td class="content" colspan="3">
				${projectBaseInfo.projectName}
			</td>
			
		</tr>
		<tr>
			<td class="label">
			 工程性质
			</td>
			<td class="content">
				${projectBaseInfo.projectType}
			</td>
			<td class="label">
				工程地点
			</td>
			<td class="content">
				${projectBaseInfo.projectLocation}
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				网络性质
			</td>
			<td class="content">
			 <eoms:id2nameDB id="${projectBaseInfo.networkType}" beanId="IItawSystemDictTypeDao" />
			</td> 
			<td class="label">
				影响系统
			</td>
			<td class="content">
			  ${projectBaseInfo.effect}
			</td> 
		</tr>
		<tr>
			<td class="label">
				迁改长度
			</td>
			<td class="content">
			  ${projectBaseInfo.projectLength}米
			</td> 
			<td class="label">
				计划工期
			</td>
			<td class="content">
			  ${projectBaseInfo.lastTime}天
			</td> 
		</tr>
		<tr>
			<td class="label">
				工程预算
			</td>
			<td class="content">
			  ${projectBaseInfo.projectPay}万元
			</td> 
			<td class="label">
				工程造价
			</td>
			<td class="content">
			  ${projectBaseInfo.constructPay}万元
			</td> 
		</tr>
		
		<tr>
			<td class="label">
				施工单位名称
			</td>
			<td class="content" colspan="3">
				${projectBaseInfo.constructor2}
			</td> 
		</tr>
		
		
		<tr>
			<td colspan="4"><div class="ui-widget-header" >验收信息</div></td>
		</tr>
		<tr>
			<td class="label">
				验收结果*
			</td>
			<td colspan="3" class="content">
				<select name="acceptResult">
					<option value="验收合格" selected="selected">验收合格</option>
					<option value="验收不合格">验收不合格</option>
				</select>
			</td> 
			
		</tr>
		
		<tr class="checkInfo">
			<td class="label">
				验收附件
			</td>
			<td colspan="3" class="content">
				<eoms:attachment scope="request" idField="acceptattachment" name="acceptattachment" 
					appCode="faultSheet" alt="allowBlank:true" />
			</td> 
			
			
		</tr>
		<tr>
			<td class="label">
				验收备注
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="acceptRemarks"
					id="acceptRemarks" alt="allowBlank:false"></textarea>
			</td> 
		</tr>
</table>



		
		
	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="验收" ></html:submit>	
		
	 
	

</form>



<%@ include file="/common/footer_eoms.jsp"%>