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
            
});
   

</script>

<content tag="heading">
<c:out value="代维任务工单派发" />
</content>  <br/><br/>
	

		     
<form action="taskOrderAction.do?method=send" method="post" id="taskOrderForm" name="taskOrderForm" >
	<input type="hidden" name="id" value="${taskOrder.id}" />
	<table id="taskOrderTable" class="formTable">
		
	    
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 任务编号 
			</td>
			<td class="content" colspan="3">
				${taskOrder.number}
			</td>			
		</tr>
		<tr>
			<td class="label">
				任务主题
			</td>
			<td class="content" colspan="3">
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
			  <textarea class="textarea max" name="description"
					id="description" alt="allowBlank:false" readonly="true">${taskOrder.description}</textarea>
			</td> 
		</tr>
		<tr>
			<td class="label">
				任务附件
			</td>
			<td colspan="3" class="content">
			  <eoms:download ids="${taskOrder.attachment}"></eoms:download>
			</td> 
		</tr>
		<tr>
			<td colspan="4">
				<div  class="ui-widget-header">创建信息</div>
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
			<td colspan="4"><div class="ui-widget-header" >派发操作</div></td>
		</tr>
		<tr>
			<td class="label">
			 派送对象* 
			</td>
			<td class="content">
				<eoms:chooser id="sendTo" 
						config="{returnJSON:true,showLeader:true}"
						category="[{id:'sendTo',text:'派发',childType:'dept,user',allowBlank:false,vtext:'请选择派发对象'}]" />
			</td>
			<td class="label">
				抄送对象
			</td>
			<td class="content">
				<eoms:chooser id="sendTo2" type="dept"
						config="{returnJSON:true,showLeader:true}"
						category="[{id:'sendTo2',text:'抄送',childType:'dept,user',allowBlank:true,vtext:'请选择抄送对象'}]" />
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				处理时限*
			</td>
			<td class="content" colspan="3">
				<input class="text" value="${taskOrder.deadline}"
					onclick="popUpCalendar(this, this)"
					type="text" name="deadline" id="deadline"
					readonly="readonly"
					alt="vtext:'处理时限',allowBlank:false" />
			</td>
		</tr>

</table>

		
		
		
		

		
		


		
		
<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="派发" ></html:submit>
</form>



<%@ include file="/common/footer_eoms.jsp"%>