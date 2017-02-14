<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*,com.boco.eoms.base.util.StaticMethod;"%>
<script type="text/javascript">
</script>
<form>
	<table class="formTable">
  		<caption>
  			<div class="header center">油机详情</div>
  		</caption>
	<tr>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${oilEngine.maintainCompany}" beanId="tawSystemDeptDao"/>
				</td>
				<td class="label">
					所属区域&nbsp;*
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${oilEngine.area}" beanId="tawSystemAreaDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					油机编号&nbsp;*
				</td>
				<td class="content">
					${oilEngine.oilEngineNumber}
				</td>
				<td class="label">
					油机型号&nbsp;*
				</td>
				<td class="content">
					${oilEngine.oilEngineModel}
				</td>
		</tr>
		<tr>
				<td class="label">
					油机厂商&nbsp;*
				</td>
				<td class="content">
				${oilEngine.oilEngineFactory}
				</td>
				<td class="label">
					油机类型&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${oilEngine.oilEngineType}" beanId="ItawSystemDictTypeDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					额定功率(KW)&nbsp;*
				</td>
				<td class="content">
					${oilEngine.powerRating}
				</td>
				<td class="label">
					标准油耗(L/小时)&nbsp;*
				</td>
				<td class="content">
					${oilEngine.standardFuelConsumption}
				</td>
		</tr>
		<tr>
				<td class="label">
					燃料种类&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${oilEngine.fuleType}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">
					存放地点&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${oilEngine.place}" beanId="tawSystemAreaDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					产权归属&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${oilEngine.oilEngineProperty}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">
					油机状态&nbsp;*
				</td>
				<td class="content">
						<eoms:id2nameDB  id="${oilEngine.oilEngineStatus}" beanId="ItawSystemDictTypeDao"/>
				</td>
		</tr>
		<%--<tr>
				<td class="label">
					经度&nbsp;*
				</td>
				<td class="content">
					${car.longitude}
				</td>
				<td class="label">
					纬度&nbsp;*
				</td>
				<td class="content">
					${car.latitude}
				</td>
		</tr>
		--%><tr>
				<td class="label">
				 	备注
				</td>
				<td class="content" colspan="3"  >
					<pre>${oilEngine.notes}</pre>
				</td>
		</tr>
	</table>
<%--<table>
<tr>
	<td>
				<input type="button" class="btn" value="编辑"	 onclick="edit('${oilEngine.id}')"/>
				<input type="button" class="btn" value="返回"  onclick="goBack()"/>	
	</td>
</tr>
</table>
--%></form>
<script type="text/javascript">
function edit(id){
window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/oilEngine.do?method=edit&&id="+id;

}
function goBack(){
window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/oilEngine.do?method=search";
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
