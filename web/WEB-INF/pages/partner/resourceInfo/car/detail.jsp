<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*,com.boco.eoms.base.util.StaticMethod;"%>
<script type="text/javascript">
</script>
<form>
	<table class="formTable">
  		<caption>
  			<div class="header center">车辆详情</div>
  		</caption>
  		<tr>
				<td class="label">
					代维公司
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${car.maintainCompany}" beanId="tawSystemDeptDao"/>
				</td>
				<td class="label">
					所属区域
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${car.area}" beanId="tawSystemAreaDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					车牌号
				</td>
				<td class="content">
					${car.carNumber}
				</td>
				<td class="label">
					车型
				</td>
				<td class="content">
					${car.carType}
				</td>
				
		</tr>
		
		<tr>
				<td class="label">
					车辆品牌
				</td>
				<td class="content">
					${car.carBrand}
				</td>
				<td class="label">
					车辆型号
				</td>
				<td class="content">
					${car.carModel}
				</td>
		</tr>
		<tr>
				<td class="label">
					燃料种类
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${car.fuleType}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">
					标准油耗(L/百公里)
				</td>
				<td class="content">
					${car.fuleConStandard}
				</td>
		</tr>
		<tr>
				<td class="label">
					负责人（驾驶员）
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${car.driver}" beanId="partnerUserDao"/>
				</td>
				<td class="label">
					联系方式（驾驶员手机）
				</td>
				<td class="content">
					${car.driverContact}
				</td>
		</tr>
		<tr>
				<td class="label">
					产权属性
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${car.carProperty}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">
					车辆状态
				</td>
				<td class="content">
						<eoms:id2nameDB  id="${car.carStatus}" beanId="ItawSystemDictTypeDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					经度&nbsp;
				</td>
				<td class="content">
					${car.longitude}
				</td>
				<td class="label">
					纬度&nbsp;
				</td>
				<td class="content">
					${car.latitude}
				</td>
		</tr>
		<tr>
				<td class="label">
					车载GPS设备厂家
				</td>
				<td class="content">
					${car.carGPSFactory}
				</td>
				<td class="label">
					车载GPS设备编号
				</td>
				<td class="content">
					${car.carGPSNumber}
				</td>
		</tr>
		<tr>
				<td class="label">
					车载GPS移动号码
				</td>
				<td class="content">
					${car.carGPSSimCardNumber}
				</td>
				<td class="label">
					当前使用人
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${carTask.carUser}" beanId="tawSystemUserDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					任务类型
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${carTask.taskType}" beanId="ItawSystemDictTypeDao"/>
				</td>
				<td class="label">
					具体任务
				</td>
				<td class="content">
					${carTask.taskName}
				</td>
		</tr>
		<tr>
				<td class="label">
				 	备注
				</td>
				<td class="content" colspan="3"  >
					<pre>${car.notes}</pre>
				</td>
		</tr>
	</table>
<%--<table>
<tr>
	<td>
				<input type="button" class="btn" value="返回"  onclick="goBack()"/>	
	</td>
</tr>
</table>
--%></form>
<script type="text/javascript">
function edit(id){
	window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/car.do?method=edit&&id="+id;
}
function goBack(){
	window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/car.do?method=search";
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
