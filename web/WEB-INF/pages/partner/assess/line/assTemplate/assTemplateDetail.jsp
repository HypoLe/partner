<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<div class="list-title">
	评估模板
</div>
<table class="formTable" id="tplForm" style="width:88%" align="center">

	<tr>
		<td class="label">
			模板名称
		</td>
		<td class="content" colspan="3">
		${assTemplateForm.templateName}
			
		</td>
	</tr>
	<c:if test="${not empty assTemplateForm.id}">
	<tr>
		<td class="label">
			创建人
		</td>
		<td class="content">
			<eoms:id2nameDB id="${assTemplateForm.creator}" beanId="tawSystemUserDao" />
		</td>
		
		<td class="label">
			创建时间
		</td>
		<td class="content" format="{0,date,yyyy-MM-dd HH:mm:ss}">
			${assTemplateForm.createTime}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			模板状态
		</td>
		<td class="content" colspan="3">
			<eoms:dict key="dict-eva" dictId="activated" itemId="${assTemplateForm.activated}" beanId="id2nameXML" />
		</td>
	</tr>
	</c:if>
	<tr>
		<td class="label">
			评估周期
		</td>
		<td class="content"> 
		 月        
		</td>
		<td class="label">
			数据来源
		</td>
		<td class="content">	
		 手动          
		</td>
	</tr>
	<tr>
		<td class="label">
			所属专业
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="professional" itemId="${assTemplateForm.professional}" beanId="id2nameXML" />
		</td>
		<td class="label">
			评估层面 
		</td>
		<td class="content">
				<eoms:dict key="dict-partner-eva" dictId="executeType" itemId="${assTemplateForm.executeType}" beanId="id2nameXML" />

		</td>
	</tr>

	<tr>
		<td class="label">
			评估阶段
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="evaluationPhase" itemId="${assTemplateForm.evaluationPhase}" beanId="id2nameXML" />
		</td>
		<td class="label">
			是否客观评价
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="isImpersonal" itemId="${assTemplateForm.isImpersonal}" beanId="id2nameXML" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			锁定权重
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="weightLocked" itemId="${assTemplateForm.isLock}" beanId="id2nameXML" />
		</td>
<td class="label">
			汇总方式
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="sumType" itemId="${assTemplateForm.sumType}" beanId="id2nameXML" />
		</td>
	</tr>
	<tr>
		<td class="label">
		权重	
		</td>
		<td class="content" colspan="3">
		${assTemplateForm.weight}
		</td>
	</tr>
	<tr>
		<td class="label">
			评估内容
		</td>
		<td class="content" colspan="3">
			${assTemplateForm.content}
		</td>
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			${assTemplateForm.remark}
		</td>
	</tr>

</table>

<%@ include file="/common/footer_eoms.jsp"%>
