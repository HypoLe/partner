<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<div class="list-title">
	考核模板
</div>
<table class="formTable" id="tplForm" style="width:88%" align="center">

	<tr>
		<td class="label">
			模板名称
		</td>
		<td class="content" colspan="3">
		${pnrEvaTemplateForm.templateName}
			
		</td>
	</tr>
	<c:if test="${not empty pnrEvaTemplateForm.id}">
	<tr>
		<td class="label">
			创建人
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrEvaTemplateForm.creator}" beanId="tawSystemUserDao" />
		</td>
		
		<td class="label">
			创建时间
		</td>
		<td class="content" format="{0,date,yyyy-MM-dd HH:mm:ss}">
			${pnrEvaTemplateForm.createTime}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			模板状态
		</td>
		<td class="content" colspan="3">
			<eoms:dict key="dict-eva" dictId="activated" itemId="${pnrEvaTemplateForm.activated}" beanId="id2nameXML" />
		</td>
	</tr>
	</c:if>
	<tr>
		<td class="label">
			考核周期
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
		<eoms:dict key="dict-partner-eva" dictId="professional" itemId="${pnrEvaTemplateForm.professional}" beanId="id2nameXML" />
		</td>
		<td class="label">
			考核层面 
		</td>
		<td class="content">
				<eoms:dict key="dict-partner-eva" dictId="executeType" itemId="${pnrEvaTemplateForm.executeType}" beanId="id2nameXML" />

		</td>
	</tr>

	<tr>
		<td class="label">
			评估阶段
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="evaluationPhase" itemId="${pnrEvaTemplateForm.evaluationPhase}" beanId="id2nameXML" />
		</td>
		<td class="label">
			是否客观评价
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="isImpersonal" itemId="${pnrEvaTemplateForm.isImpersonal}" beanId="id2nameXML" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			锁定权重
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="weightLocked" itemId="${pnrEvaTemplateForm.isLock}" beanId="id2nameXML" />
		</td>
<td class="label">
			汇总方式
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="sumType" itemId="${pnrEvaTemplateForm.sumType}" beanId="id2nameXML" />
		</td>
	</tr>
	<tr>
		<td class="label">
		权重	
		</td>
		<td class="content" colspan="3">
		${pnrEvaTemplateForm.weight}
		</td>
	</tr>
	<tr>
		<td class="label">
			考核内容
		</td>
		<td class="content" colspan="3">
			${pnrEvaTemplateForm.content}
		</td>
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			${pnrEvaTemplateForm.remark}
		</td>
	</tr>

</table>

<%@ include file="/common/footer_eoms.jsp"%>
