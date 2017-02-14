<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<table class="listTable" id="list-table">
	<caption>
		<div class="header center">考核任务列表</div>
	</caption>
	<thead>
	<tr>
		<td>
			模板名称
		</td>
		<td>
			模板状态
		</td>
		<td>
			创建时间
		</td>
		<td>
			被考核公司 
		</td>
		<td>
			查看详情
		</td>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${templateList}" var="template">
		<tr>
			<td>
				${template.templateName}
			</td>
			<td>
				<eoms:dict key="dict-eva" dictId="activated" itemId="${template.activated}" beanId="id2nameXML" />
			</td>
			<td>
				${template.createTime}
			</td>
			<td>
				<eoms:id2nameDB id="${template.partnerDept}" beanId="tawSystemDeptDao"/>
			</td>			
			<td>
				<a href="${app}/eva/evaReportInfos.do?method=getAllScroeView&templateId=${template.id}" target="_blank">
					查看详细
				</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<%@ include file="/common/footer_eoms.jsp"%>