<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<form>
	<table class="formTable">
  		<caption>
  			<div class="header center">仪器仪表详情</div>
  		</caption>
		<tr>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${apparatus.maintenanceCompany}" beanId="tawSystemDeptDao"/>
				</td>
				<td class="label">
					所属区域&nbsp;*
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${apparatus.area}" beanId="tawSystemAreaDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					所属专业&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${apparatus.maintenanceMajor}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">
					仪器名称&nbsp;*
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${apparatus.apparatusName}" beanId="ItawSystemDictTypeDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					型号&nbsp;*
				</td>
				<td class="content" >
					${apparatus.apparatusType}
				</td>
				</td>
				<td class="label">
					序列号&nbsp;
				</td>
				<td class="content" >
					${apparatus.apparatusSerialNumber}
				</td>
		</tr>
		<tr>
				<td class="label">
					出厂日期&nbsp;*
				</td>
				<td class="content" >
					${apparatus.apparatusDateOfProduction}
				</td>
				<td class="label">
					采购日期&nbsp;*
				</td>
				<td class="content" >
					${apparatus.purchasingTime}
				</td>
		</tr>
		<tr>
				<td class="label">
					使用年限&nbsp;*
				</td>
				<td class="content" >
				${apparatus.apparatusServiceLife}
				</td>
				<td class="label">
					在用状态&nbsp;*
				</td>
				<td class="content"  colspan="3">
						<eoms:id2nameDB  id="${apparatus.apparatusStatus}" beanId="ItawSystemDictTypeDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					产权属性&nbsp;*
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${apparatus.apparatusProperty}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">
					资源所属&nbsp;*
				</td>
				<td class="content" >
				${apparatus.apparatusBelongs}
				</td>
		</tr>
		<tr>
				<td class="label">
				 	备注
				</td>
				<td class="content" colspan="3"  >
					<pre>${apparatus.notes}</pre>
				</td>
		</tr>
	</table>
	<%--<table>
	<tr>
		<td>
			<input type="button" class="btn" value="返回"  onclick="reward();"/>	
		</td>
	</tr>
	</table>
--%></form>
<script type="text/javascript">
function edit(id){
	window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/apparatus.do?method=edit&&id="+id;
}
function reward(){
	window.history.back;
	//window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/apparatus.do?method=search";
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
