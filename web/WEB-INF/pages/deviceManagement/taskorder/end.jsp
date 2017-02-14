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
	url : "${app}/faultInfo/faultInfo.do?method=importRecord",
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
<c:out value="代维任务工单归档" />
</content>  <br/><br/>
	

		     
<form action="taskOrderAction.do?method=end" method="post" id="taskOrderForm" name="taskOrderForm" >
	<input type="hidden" name="id" value="${taskOrder.id}" />
	<table id="taskOrderTable" class="formTable">
		
	    
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 任务编号
			</td>
			<td class="content">
				${taskOrder.number}
			</td>
			<td class="label">
				任务主题
			</td>
			<td class="content">
				${taskOrder.topic}
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 任务类型 
			</td>
			<td class="content">
				<eoms:id2nameDB id="${taskOrder.type}" beanId="IItawSystemDictTypeDao" />
			</td>
			<td class="label">
			 网络分类 
			</td>
			<td class="content">
				<eoms:id2nameDB id="${taskOrder.netGroup}" beanId="IItawSystemDictTypeDao" />
			</td>
		</tr>
		<tr>
			<td class="label">
				任务描述
			</td>
			<td colspan="3" class="content">
				<textarea class="textarea max" name="replyMsg"
					id="description" alt="allowBlank:false" readonly="true">${taskOrder.description}</textarea>
			</td> 
		</tr>
		
		<tr>
			<td class="label">
			 派送对象 
			</td>
			<td class="content">
				<c:if test="${taskOrder.sendType=='0'}">
					<eoms:id2nameDB id="${taskOrder.sendTo}" beanId="tawSystemDeptDao" />
				</c:if>
				<c:if test="${taskOrder.sendType=='1'}">
					<eoms:id2nameDB id="${taskOrder.sendTo}" beanId="tawSystemUserDao" />
				</c:if>
			</td>
			<td class="label">
				抄送对象
			</td>
			<td class="content">
				<c:if test="${taskOrder.sendType=='0'}">
					<eoms:id2nameDB id="${taskOrder.sendTo2}" beanId="tawSystemDeptDao" />
				</c:if>
				<c:if test="${taskOrder.sendType=='1'}">
					<eoms:id2nameDB id="${taskOrder.sendTo2}" beanId="tawSystemUserDao" />
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div  class="ui-widget-header">操作信息</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				创建人
			</td>
			<td class="content">
				 <eoms:id2nameDB id="${taskOrder.createUserId}" beanId="tawSystemUserDao" /> 
		
			</td>
			<td class="label">
				创建时间
			</td>
			<td class="content">
				 ${taskOrder.createTime} 
		
			</td>
		</tr>
		<tr>
			<td class="label">
				创建部门
			</td>
			<td class="content">
				  ${taskOrder.createDeptId} 
		
			</td>
			<td class="label">
				联系方式
			</td>
			<td class="content">
				 ${taskOrder.createUserContact} 
		
			</td>
		</tr>
		<tr>
			<td class="label">
				派发人
			</td>
			<td class="content">
				 <eoms:id2nameDB id="${taskOrder.sendUserId}" beanId="tawSystemUserDao" /> 
		
			</td>
			<td class="label">
				派发时间
			</td>
			<td class="content">
				 ${taskOrder.sendTime} 
		
			</td>
		</tr>
		<tr>
			<td class="label">
				回复人
			</td>
			<td class="content">
				 <eoms:id2nameDB id="${taskOrder.replyUserId}" beanId="tawSystemUserDao" /> 
		
			</td>
			<td class="label">
				回复时间
			</td>
			<td class="content">
				 ${taskOrder.replyTime} 
		
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				处理时限
			</td>
			<td class="content">
				${taskOrder.deadline}
			</td>
			<td class="label">
				是否延时
			</td>
			<td class="content">
				<c:out value="${taskOrder.delay=='1'?'延时回复':'按时回复' }"/> 
			</td>
		</tr>
		
		
		
		<tr>
			<td class="label">
				回复类容
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="replyMsg"
					id="description" alt="allowBlank:false" readonly="true" >${taskOrder.replyMsg}</textarea>
			</td> 
		</tr>
		<tr>
			<td colspan="4"><div class="ui-widget-header" >归档操作</div></td>
		</tr>
		<tr>
			<td class="label">
				满意程度*
			</td>
			<td class="content" colspan="3">
			
				<eoms:comboBox name="satisfiedLevel"
					id="satisfiedLevel" initDicId="1010105" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		<tr>
			<td class="label">
				归档意见*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="endMsg"
					id="endMsg"></textarea>
				
			</td>
		</tr>
</table>

		
		
		
		

		
		


		
		
<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="归档" ></html:submit>
</form>



<%@ include file="/common/footer_eoms.jsp"%>