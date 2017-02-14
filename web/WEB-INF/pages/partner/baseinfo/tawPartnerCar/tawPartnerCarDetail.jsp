<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
String defaultCarPic = "images/pnr_thumbnail/man.gif";

String defaultImgDriving = "images/pnr_thumbnail/man.gif";

String basePath="images/pnr_thumbnail/photo/zoom/";

 %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	//v = new eoms.form.Validation({form:'tawPartnerCarForm'});
});

</script>

<html:form action="/tawPartnerCars.do?method=save" styleId="tawPartnerCarForm" method="post"  > 


<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawPartnerCar.form.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="tawPartnerCar.car_number" />&nbsp;*
		</td>
		<td class="content">
			${tawPartnerCarForm.car_number}
		</td>
		<!-- 所属公司 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.dept_id" />&nbsp;*
		</td>
		<td class="content">
		<eoms:id2nameDB beanId="partnerDeptDao" id="${tawPartnerCarForm.partnerid}"/>
			<!--<eoms:id2nameDB id="${tawPartnerCarForm.partnerid}" beanId="partnerDeptDao" />-->
		</td>
	</tr>
	<tr><td class="label">车型 *</td><td>
	<eoms:id2nameDB id="${tawPartnerCarForm.carForm}"  beanId="ItawSystemDictTypeDao" />
	</td>
	<!-- 所属地市 --><td class="label">
			<fmt:message key="tawPartnerCar.area_id" />&nbsp;*
		</td>
		<td class="content">
		<eoms:id2nameDB beanId="partnerDeptAreaDao" id="${tawPartnerCarForm.partnerid}"/>
		</td></tr>		
	<tr>
		<td class="label">
			购买时间&nbsp;*
		</td>
		<td class="content">
			${tawPartnerCarForm.purchaseTime}
		</td>
		<td class="label">
			驱动类型&nbsp;*
		</td>	<td class="content">
				<eoms:id2nameDB id="${tawPartnerCarForm.driveType}"  beanId="ItawSystemDictTypeDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="tawPartnerCar.start_time" />*
		</td>
		<td class="content">
			${tawPartnerCarForm.start_time}
		</td>
<td class="label">
			<fmt:message key="tawPartnerCar.endTime" />*
		</td>
		<td class="content">
			${tawPartnerCarForm.endTime}
		</td>
	</tr>
	<tr>
	<!-- 年检日期 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.annualCheckData" />&nbsp;*
		</td>
		<td class="content">
			${tawPartnerCarForm.annualCheckData}
		</td>
		<td class="label">出厂日期</td><td>${tawPartnerCarForm.carOutDate}</td>
	</tr>
	<!-- 以下是新增字段 -->
	<tr>
	<!-- 行驶证号 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.drivingLicense" />&nbsp;*
		</td>
		<td class="content">
			${tawPartnerCarForm.drivingLicense}
		</td>		
		<!-- 车辆品牌 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.brand" />&nbsp;*
		</td>
		<td class="content" >
			${tawPartnerCarForm.brand}
		</td>
	</tr>
	<tr>
		<!-- 生产厂家 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.factory" />&nbsp;*
		</td>
		<td class="content">
			${tawPartnerCarForm.factory}
		</td>
		<td class="label">
			服务类型&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${tawPartnerCarForm.category}"  beanId="ItawSystemDictTypeDao" />
		</td>
	</tr>
	
	<tr>
		<!-- 发动机号 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.engineNo" />&nbsp;*
		</td>
		<td class="content">
			${tawPartnerCarForm.engineNo}
		</td>
		<!-- 排气量 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.airDisplacement" />&nbsp;*
		</td>
		<td class="content">
			${tawPartnerCarForm.airDisplacement}
		</td>
	</tr>
	<tr>
		<!-- 维护专业 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.serviceProfessional" />&nbsp;*
		</td>
		<td class="content">
				<eoms:id2nameDB id="${tawPartnerCarForm.serviceProfessional}"  beanId="ItawSystemDictTypeDao" />
		</td>

		<!-- 开始使用里程数 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.startUseMilemeter" />&nbsp;*
		</td>
		<td class="content">
			${tawPartnerCarForm.startUseMilemeter}
		</td>
	</tr>
	<tr><td class="label">车辆状态</td>
	<td><eoms:id2nameDB id="${tawPartnerCarForm.carState}"  beanId="ItawSystemDictTypeDao" /></td>
	</td>
	<td class="label">是否可调配</td>
	<td>
	<eoms:id2nameDB id="${tawPartnerCarForm.isPrepare}"  beanId="ItawSystemDictTypeDao" />
	</td>
	</tr>
	<tr>
		<!-- 所有性质 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.ownershipType" />&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${tawPartnerCarForm.ownershipType}"  beanId="ItawSystemDictTypeDao" />
		</td>
		<!-- 用途 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.use" />&nbsp;*
		</td>
		<td class="content">
			<eoms:id2nameDB id="${tawPartnerCarForm.use}"  beanId="ItawSystemDictTypeDao" />
		</td>
	</tr>
	<tr><td class="label" colspan="1">备注</td><td colspan="3" class="content">
	${tawPartnerCarForm.remark}
	<tr>	
		<logic:notEmpty name="tawPartnerCarForm" property="addMan">
	
				<td class="label">
					添加人姓名
				</td>
				<td class="content">
					<eoms:id2nameDB id="${tawPartnerCarForm.addMan}"
						beanId="tawSystemUserDao" />&nbsp;&nbsp;
						<bean:write name="tawPartnerCarForm" property="connectType" />
				</td>
				<td class="label">
					添加时间
				</td>
				<td class="content">
					<bean:write name="tawPartnerCarForm" property="addTime" />
				</td>
			</logic:notEmpty>
	</tr>
		<!-- 车辆缩略图 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.thumbnail" />&nbsp;*
		</td>
		<td>
			<div id='pic' class="photocontainer">
				<c:if test="${empty tawPartnerCarForm.id}">
					<img id="imgphoto" src="${app}/<%=defaultCarPic%>" style="border-width: 0px;" />
				</c:if>
				<c:if test="${not empty tawPartnerCarForm.id}">
					<c:choose>
					   <c:when test="${not empty tawPartnerCarForm.thumbnail}">
					   		<%--<img id="imgphoto" src="${app}/<%=basePath%>${tawPartnerCarForm.thumbnail}" style="border-width: 0px;" width="250" height="180"/>
					   		--%><img id="imgphoto" src="${tawPartnerCarForm.thumbnail}" style="border-width: 0px;" width="250" height="180"/>
					   </c:when>
					   <c:otherwise>
					   		<img id="imgphoto" src="${app}/<%=defaultCarPic%>" style="border-width: 0px;"/>
					   </c:otherwise>
					</c:choose>
				</c:if>
			</div>
			<input id="thumbnail" type="hidden" name="thumbnail" value="${tawPartnerCarForm.thumbnail}"/><br>
		</td>
		<!-- 行驶证缩略图 -->
		<td class="label">
			<fmt:message key="tawPartnerCar.drivingLicenseThumbnail" />&nbsp;*
		</td>
		<td class="content" colspan=3>
			
			<div id='picDriving' class="photocontainer">
				<c:if test="${empty tawPartnerCarForm.id}">
					<img id="imgdriving" src="${app}/<%=defaultImgDriving%>" style="border-width: 0px;" />
				</c:if>
				<c:if test="${not empty tawPartnerCarForm.id}">
					<c:choose>
					   <c:when test="${not empty tawPartnerCarForm.drivingLicenseThumbnail}">
					   		<img id="imgdriving" src="${tawPartnerCarForm.drivingLicenseThumbnail}" style="border-width: 0px;" width="250" height="180"/>
					   </c:when>
					   <c:otherwise>
					   		<img id="imgdriving" src="${app}/<%=defaultCarPic%>" style="border-width: 0px;"/>
					   </c:otherwise>
					</c:choose>
				</c:if>
			</div>
			<input id="drivingLicenseThumbnail" type="hidden" name="drivingLicenseThumbnail" value="${tawPartnerCarForm.drivingLicenseThumbnail}"/><br>
		</td>
	</tr>
			<html:hidden property="dimensionalCode" value="${tawPartnerCarForm.dimensionalCode}" />
</table>
</fmt:bundle>
<table>
	<tr>
		<td>
			<input type="button" class="btn" value="编辑" onclick="var url='${app}/partner/baseinfo/tawPartnerCars.do?method=edit&id=${tawPartnerCarForm.id}';location.href=url"/>
				<input type="button" class="btn" value="返回"　 onclick="var url='<%=request.getContextPath()%>/partner/baseinfo/tawPartnerCars.do?method=search&in=factory&nodeId=1010101';location.href=url"/>	
		</td>
	</tr>
</table>
<html:hidden property="id" value="${tawPartnerCarForm.id}" />
 </html:form>

<script type="text/javascript">
  </script>
<%@ include file="/common/footer_eoms.jsp"%>