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
<c:out value="线路修缮审定" />
</content>  <br/><br/>
	




<br/>
	
	
	
	
	
	
	
	     
<form action="lineProjectAction.do?method=commis" method="post" id="projectLineForm" name="projectLineForm" >
	
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
			  ${projectBaseInfo.projectPay}元
			</td> 
			<td class="label">
				勘测日期
			</td>
			<td class="content">
			  ${projectBaseInfo.checkDate}
			</td> 
		</tr>
		
		<tr>
			<td class="label">
				申请附件
			</td>
			<td colspan="3" class="content">
				<eoms:download ids="${projectBaseInfo.attachment}"></eoms:download>
			</td> 
		</tr>
		<tr>
			<td colspan="4"><div class="ui-widget-header" >委托施工信息</div></td>
		</tr>
		<tr>
			<td class="label">
				委托施工单位名称*
			</td>
			<td class="content">
				<input class="text" type="text" name="constructor"
					id="constructor" alt="allowBlank:false" />
			</td> 
			<td class="label">
				委托施工单位地址*
			</td>
			<td class="content">
				<input class="text" type="text" name="constructorLocation"
					id="constructorLocation" alt="allowBlank:false" />
			</td> 
		</tr>
		
		<tr>
			<td class="label">
				委托施工单位联系人*
			</td>
			<td class="content">
				<input class="text" type="text" name="constructorContacter"
					id="constructorContacter" alt="allowBlank:false" />
			</td> 
			<td class="label">
				委托施工单位联系电话*
			</td>
			<td class="content">
				<input class="text" type="text" name="constructorPhone"
					id="constructorPhone" alt="allowBlank:false, vtype:'number'" />
			</td> 
		</tr>
		<tr>
			<td class="label">
				委托施工单位资质*
			</td>
			<td colspan="3" class="content">
				<select name="constructorType">
					<option value="甲级">甲级</option>
					<option value="乙级">乙级</option>
					<option value="丙级">丙级</option>
				</select>
			</td> 
			
		</tr>
		<tr>
			<td class="label">
				工程开始时间*
			</td>
			<td class="content">
				<input class="text" value=""
					onclick="popUpCalendar(this, this, 'yyyy-mm-dd', -1, -1, false, -1 )"
					type="text" name="constructStartTime" id="constructStartTime"
					readonly="readonly"
					alt="vtext:'不能为空',allowBlank:false" />
			</td> 
			<td class="label">
				工程造价*
			</td>
			<td class="content">
				<input class="text" type="text" name="constructPay"
					id="constructPay" alt="allowBlank:false, vtype:'number'"/>万元
			</td> 
		</tr>
		
		<tr class="checkInfo">
			<td class="label">
				委托附件
			</td>
			<td colspan="3" class="content">
				<eoms:attachment scope="request" idField="constructattachment" name="constructattachment" 
					appCode="faultSheet" alt="allowBlank:true" />
			</td> 
		</tr>
		<tr>
			<td class="label">
				委托备注
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="constructRemarks"
					id="constructRemarks" alt="allowBlank:true"></textarea>
			</td> 
		</tr>
</table>



		
		
	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="确定" ></html:submit>	
		
	 
	

</form>



<%@ include file="/common/footer_eoms.jsp"%>