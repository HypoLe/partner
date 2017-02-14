<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
	});
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


 
	<content tag="heading">
	<c:out value="代维任务工单详细信息" />
	</content><br/><br/>
<form action="taskOrderAction.do?method=send" method="post" id="taskOrderForm" name="taskOrderForm" >	
	<table id="sheet" class="formTable">
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 工单编号
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
			 任务类别
			</td>
			<td class="content">
				<eoms:id2nameDB id="${taskOrder.type}" beanId="IItawSystemDictTypeDao" />
			</td>
			<td class="label">
				网络类型
			</td>
			<td class="content">
				<eoms:id2nameDB id="${taskOrder.netGroup}" beanId="IItawSystemDictTypeDao" />
			</td>
		</tr>
		
		
		
		
		<tr>
			<td class="label">
				任务描述
			</td>
			<td class="content" colspan="3">
				  <textarea class="textarea max" name="description"
					id="description" alt="allowBlank:false" readonly="true">${taskOrder.description}</textarea>
		</td>
	 </tr>	
	 		<tr>
			<td class="label">
				工单附件
			</td>
			<td class="content" colspan="3">
				<eoms:download ids="${taskOrder.attachment}"></eoms:download>
			</td>
		</tr>
		<tr>
			<td class="label">
			 派送对象* 
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
				<c:if test="${taskOrder.send2Type=='0'}">
				<eoms:id2nameDB id="${taskOrder.sendTo2}" beanId="tawSystemDeptDao" />
			</c:if>
			<c:if test="${taskOrder.send2Type=='1'}">
				<eoms:id2nameDB id="${taskOrder.sendTo2}" beanId="tawSystemUserDao" />
			</c:if>
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				处理时限*
			</td>
			<td class="content" colspan="3">
				${taskOrder.deadline}
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
			<td colspan="4">
				<div  class="ui-widget-header">流转信息</div>
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
				回复内容
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="description"
					id="description" alt="allowBlank:false" readonly="true">${taskOrder.replyMsg}</textarea>
			</td>
		</tr>
		<tr>
			<td class="label">
				归档人
			</td>
			<td class="content">
				<eoms:id2nameDB id="${taskOrder.endUserId}" beanId="tawSystemUserDao" /> 
				 
		
			</td>
			<td class="label">
				归档时间
			</td>
			<td class="content">
				 ${taskOrder.endTime} 
		
			</td>
		</tr>
		<tr>
			<td class="label">
				归档满意度
			</td>			
			<td class="content" colspan="3">
				<eoms:id2nameDB id="${taskOrder.satisfiedLevel}" beanId="IItawSystemDictTypeDao" />
		
			</td>
		</tr>
		<tr>
			<td class="label">
				归档意见
			</td>			
			<td class="content" colspan="3">
				<textarea class="textarea max" name="description"
					id="description" alt="allowBlank:false" readonly="true">${taskOrder.endMsg}</textarea>
		
			</td>
		</tr>
		

		

		
		
		</table>
	

</form>

<%@ include file="/common/footer_eoms.jsp"%>