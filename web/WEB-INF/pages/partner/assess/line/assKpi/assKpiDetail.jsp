<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<div class="list-title">
	指标信息
</div>
<div style="height: 400px">
	<div id="kpiContent">
			<table  class="formTable">
				<tr>
					<td class="label">
						指标名称
					</td>
					<td class="content">
						${assKpiForm.kpiName}
					</td>
				</tr>
				<tr>
					<td class="label">
						所占分数
					</td>
					<td class="content">
						${assKpiForm.weight}
					</td>
				</tr>
				<tr>
					<td class="label">
						打分类型
					</td>
					<td class="content">
						<eoms:dict key="dict-partner-assess" dictId="kpiType" itemId="${assKpiForm.kpiType}" beanId="id2nameXML" />
					</td>
				</tr>				
				<tr>
					<td class="label">
						算法描述
					</td>
					<td class="content">
						${assKpiForm.algorithm}
					</td>
				</tr>
				<tr>
					<td class="label">
						备注
					</td>
					<td class="content">
						${assKpiForm.remark}
					</td>
				</tr>
			</table>
	</div>

</div>

<%@ include file="/common/footer_eoms.jsp"%>
