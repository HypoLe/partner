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
					<%--<td class="label">
					 	区域
					</td>
					<td class="content" colspan="3">
							<eoms:id2nameDB id="${pnrStandardConfig.areaId}" beanId="tawSystemAreaDao"/>
					</td>
					--%>
					<td class="label">
					 	巡检专业
					</td>
					<td class="content" colspan="3">
							<eoms:id2nameDB id="${pnrStandardConfig.professional}" beanId="ItawSystemDictTypeDao"/>
					</td>
					<td class="label">
					 	配置类型
					</td>
					<td class="content" colspan="3">
							<eoms:id2nameDB id="${pnrStandardConfig.configType}" beanId="ItawSystemDictTypeDao"/>
					</td>
				</tr>
				<tr>
					<td class="label">
					 	标准配置
					</td>
					<td class="content" colspan="7">
							${pnrStandardConfig.standardConfig}${pnrStandardConfig.configDw}
					</td>
				</tr>
				<tr>
					<td class="label">
					 	备注
					</td>
					<td class="content" colspan="7">
							<pre>${pnrStandardConfig.remark}</pre>
					</td>
				</tr>
</table>
<br>
<input  type="button" class="btn"  value="返回" onclick="goBack();" />
<script type="text/javascript">
var jq=jQuery.noConflict();
	function goBack(){
		window.location.href="${app}/partner/baseinfo/pnrStandardConfig.do?method=gotoPnrStandardConfigListPage";
	}
</script>

<%@ include file="/common/footer_eoms.jsp"%>