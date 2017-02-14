<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<style>
	.redStar {
		color:red;
	}
</style>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<div align="center"><b>代维资源标准配置-详情页面</b></div><br><br/>
<table id="sheet" class="formTable">
				<tr>
					<td class="label">
					 	区域
					</td>
					<td class="content" colspan="3">
							<eoms:id2nameDB id="${pnrSourceStandardConfig.areaId}" beanId="tawSystemAreaDao"/>
					</td>
					<td class="label">
					 	代维公司
					</td>
					<td class="content" colspan="3">
							${pnrSourceStandardConfig.companyName}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	巡检专业
					</td>
					<td class="content" colspan="3">
							<eoms:id2nameDB id="${pnrSourceStandardConfig.professional}" beanId="ItawSystemDictTypeDao"/>
					</td>
<%--					<td class="label">
					 	资源类型
					</td>
					<td class="content" colspan="3">
							${pnrSourceStandardConfig.sourceType}
					</td>
				</tr>
				<tr>--%>
					<td class="label">
					 	维护资源数量
					</td>
					<td class="content" colspan="3">
							${pnrSourceStandardConfig.sourceAmount}
					</td>
					<%--<td class="label">
					 	资源计量单位
					</td>
					<td class="content" colspan="3">
							${pnrSourceStandardConfig.sourceDw}
					</td>
				--%>
				</tr>
				<tr>
					<td class="label">
					 	配置类型
					</td>
					<td class="content" colspan="3">
							<eoms:id2nameDB id="${pnrSourceStandardConfig.configType}" beanId="ItawSystemDictTypeDao"/>
					</td>
					<td class="label">
					 	配置标准
					</td>
					<td class="content" colspan="3">
							${pnrSourceStandardConfig.standardConfig}${pnrSourceStandardConfig.configDw}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	标准配置数量
					</td>
					<td class="content" colspan="3">
							${pnrSourceStandardConfig.standardAmout}
					</td>
					<td class="label">
					 	实际配置数量
					</td>
					<td class="content" colspan="3">
							${pnrSourceStandardConfig.actualConfig}
					</td>
				</tr><%--
				<tr>
					<td class="label">
					 	到位时间
					</td>
					<td class="content" colspan="3">
							${pnrSourceStandardConfig.configTime}
					</td>
					<td class="label">
					 	是否审核
					</td>
					<td class="content" colspan="3">
							<eoms:id2nameDB id="${pnrSourceStandardConfig.ischecked}" beanId="ItawSystemDictTypeDao"/>
					</td>
				</tr>
				--%>
				<tr>
					<td class="label">
					 	备注
					</td>
					<td class="content" colspan="7">
							<pre>${pnrSourceStandardConfig.remark}</pre>
					</td>
				</tr>
</table>

<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){

});
</script>

<%@ include file="/common/footer_eoms.jsp"%>