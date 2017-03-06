<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
  Ext.onReady(function(){
   
   v = new eoms.form.Validation({form:'theform'});
	
   });
</script>

<html:form action="/shortPeriod.do?method=updateTowerNew" styleId="theform" >
	<input type="hidden" id="productNum" name="productNum" value="${towerModel.productNum}" />
	<input type="hidden" id="condition" name="condition" value="${condition}" />
	<table id="jibenTable" class="formTable">
		<tr>
			<td class="label" style="width:100%" colspan = "6" align="center">
				基本信息
			</td>
		</tr>
		<tr>
			<td class="label" style="width: 10%">
				产品业务确认单编号
			</td>
			<td class="content" style="width: 23%">
				${towerModel.productNum}
			</td>
			
			<td class="label" style="width: 10%">
				地市
			</td>
			<td class="content" style="width: 23%">
				<eoms:id2nameDB id="${towerModel.cityId}" beanId="tawSystemAreaDao" />
			</td>

			<td class="label" style="width: 10%">
				区县
			</td>
			<td class="content" style="width: 23%">
				<eoms:id2nameDB id="${towerModel.countyId}" beanId="tawSystemAreaDao" />
			</td>
		</tr>
		<tr>
			<td class="label" style="width: 10%">
				站点名称
			</td>
			<td class="content" style="width: 23%">
				${towerModel.resName}
			</td>
			
			<td class="label" style="width: 10%">
				站址编码
			</td>
			<td class="content" style="width: 23%">
				${towerModel.stationCode}
			</td>

			<td class="label" style="width: 10%">
				需求确认编号
			</td>
			<td class="content" style="width: 23%">
				${towerModel.needNo}
			</td>
		</tr>
	</table>
	
	<table id="updateTable" style="margin-top:5px;" class="formTable" >
		<tr>
			<td>
				&nbsp;
			</td>
			<td class="label">
				原资源内容
			</td>
			<td class="label">
				新资源内容
			</td>
		</tr>
		<tr>
			<td class="label">
				塔型<font color="red">*</font>
			</td>
			<td>
				${towerModel.towerType}
			</td>
			<td>
				<eoms:comboBox name="bTowerType" id="bTowerType" defaultValue="${towerModel.BTowerType}" initDicId="10308"
								alt="allowBlank:false" styleClass="select" />
			    <!-- <input type="text" id="newa2" name="newa2" class="text" value="${towerModel.newa2}" /> -->
			</td>
		</tr>
		<tr>
			<td class="label">
				机房类型<font color="red">*</font>
			</td>
			<td>
				${towerModel.roomType}
			</td>
			<td>
				<eoms:comboBox name="bRoomType" id="bRoomType" defaultValue="${towerModel.BRoomType}" initDicId="10307"
									alt="allowBlank:false" styleClass="select" />
				<!-- <input type="text" id="newa3" name="newa3" class="text" value="${towerModel.newa3}" /> -->
			</td>
		</tr>
		<tr>
			<td class="label">
				实际最高天线挂高1<font color="red">*</font>
			</td>
			<td>
				${towerModel.hangHigh1}
			</td>
			<td>
				<eoms:comboBox name="bHangHigh1" id="bHangHigh1" defaultValue="${towerModel.BHangHigh1}" initDicId="10310"
											alt="allowBlank:false" styleClass="select" />
				<!--<input type="text" id="newa5" name="newa5" class="text" value="${towerModel.newa5}" />-->
			</td>
		</tr>
		<tr>
			<td class="label">
				BBU是否放在铁塔公司机房1
			</td>
			<td>
				${towerModel.rruRoom1}
			</td>
			<td>
				<eoms:comboBox name="bRruRoom1" id="bRruRoom1" defaultValue="${towerModel.BRruRoom1}" initDicId="10301"
												alt="allowBlank:true" styleClass="select" />
				<!--<input type="text" id="newa6" name="newa6" class="text" value="${towerModel.newa6}" />-->
			</td>
		</tr>
		<tr>
			<td class="label">
				铁塔共享用户数<font color="red">*</font>
			</td>
			<td>
				${towerModel.towerNum}
			</td>
			<td>
				<eoms:comboBox name="bTowerNum" id="bTowerNum" defaultValue="${towerModel.BTowerNum}" initDicId="10312"
											alt="allowBlank:false" styleClass="select" />
				<!-- <input type="text" id="bTowerNum" name="bTowerNum" class="text" value="${towerModel.BTowerNum}" alt="allowBlank:false" /> -->
			</td>
		</tr>
		<tr>
			<td class="label">
				机房共享用户数<font color="red">*</font>
			</td>
			<td>
				${towerModel.roomNum}
			</td>
			<td>
				<eoms:comboBox name="bRoomNum" id="bRoomNum" defaultValue="${towerModel.BRoomNum}" initDicId="10312"
											alt="allowBlank:false" styleClass="select" />
			<!-- 	<input type="text" id="bRoomNum" name="bRoomNum" class="text" value="${towerModel.BRoomNum}" alt="allowBlank:false" /> -->
			</td>
		</tr>
		<tr>
			<td class="label">
				配套共享用户数<font color="red">*</font>
			</td>
			<td>
				${towerModel.supportNum}
			</td>
			<td>
				<eoms:comboBox name="bSupportNum" id="bSupportNum" defaultValue="${towerModel.BSupportNum}" initDicId="10312"
											alt="allowBlank:false" styleClass="select" />
			<!-- 	<input type="text" id="bSupportNum" name="bSupportNum" class="text" value="${towerModel.BSupportNum}" alt="allowBlank:false" /> -->
			</td>
		</tr>
		<tr>
			<td class="label">
				维护费共享用户数<font color="red">*</font>
			</td>
			<td>
				${towerModel.maitainNum}
			</td>
			<td>
				<eoms:comboBox name="bMaitainNum" id="bMaitainNum" defaultValue="${towerModel.BMaitainNum}" initDicId="10312"
											alt="allowBlank:false" styleClass="select" />
				<!-- <input type="text" id="bMaitainNum" name="bMaitainNum" class="text" value="${towerModel.BMaitainNum}" alt="allowBlank:false" /> -->
			</td>
		</tr>
		<tr>
			<td class="label">
				场地费共享用户数<font color="red">*</font>
			</td>
			<td>
				${towerModel.fieldNum}
			</td>
			<td>
				<eoms:comboBox name="bFieldNum" id="bFieldNum" defaultValue="${towerModel.BFieldNum}" initDicId="10312"
											alt="allowBlank:false" styleClass="select" />
				<!--  <input type="text" id="bFieldNum" name="bFieldNum" class="text" value="${towerModel.BFieldNum}" alt="allowBlank:false"/>-->
			</td>
		</tr>
		<tr>
			<td class="label">
				电力引入费共享用户数<font color="red">*</font>
			</td>
			<td>
				${towerModel.powerNum}
			</td>
			<td>
				<eoms:comboBox name="bPowerNum" id="bPowerNum" defaultValue="${towerModel.BPowerNum}" initDicId="10312"
											alt="allowBlank:false" styleClass="select" />
				<!-- <input type="text" id="bPowerNum" name="bPowerNum" class="text" value="${towerModel.BPowerNum}" alt="allowBlank:false"/> -->
			</td>
		</tr>
		<tr>
			<td class="label">
				RRU数量<font color="red">*</font>
			</td>
			<td>
				${towerModel.rruNum}
			</td>
			<td>
				<select id="bRruNum" name="bRruNum" value="${towerModel.BRruNum}" class="select">
					<option value = "0" <c:if test="${'0' eq towerModel.BRruNum}">selected</c:if>>0</option>
					<option value = "1" <c:if test="${'1' eq towerModel.BRruNum}">selected</c:if>>1</option>
					<option value = "2" <c:if test="${'2' eq towerModel.BRruNum}">selected</c:if>>2</option>
					<option value = "3" <c:if test="${'3' eq towerModel.BRruNum}">selected</c:if>>3</option>
					<option value = "4" <c:if test="${'4' eq towerModel.BRruNum}">selected</c:if>>4</option>
					<option value = "5" <c:if test="${'5' eq towerModel.BRruNum}">selected</c:if>>5</option>
					<option value = "6" <c:if test="${'6' eq towerModel.BRruNum}">selected</c:if>>6</option>
					<option value = "7" <c:if test="${'7' eq towerModel.BRruNum}">selected</c:if>>7</option>
					<option value = "8" <c:if test="${'8' eq towerModel.BRruNum}">selected</c:if>>8</option>
					<option value = "9" <c:if test="${'9' eq towerModel.BRruNum}">selected</c:if>>9</option>
					<option value = "10" <c:if test="${'10' eq towerModel.BRruNum}">selected</c:if>>10</option>
				</select>
				<!-- <input type="text" id="bPowerNum" name="bPowerNum" class="text" value="${towerModel.BPowerNum}" alt="allowBlank:false"/> -->
			</td>
		</tr>
		<tr>
			<td class="label">
				天线数量<font color="red">*</font>
			</td>
			<td>
				${towerModel.antennaNum}
			</td>
			<td>
				<select id="bAntennaNum" name="bAntennaNum" value="${towerModel.BAntennaNum}" class="select">
					<option value = "0" <c:if test="${'0' eq towerModel.BAntennaNum}">selected</c:if>>0</option>
					<option value = "1" <c:if test="${'1' eq towerModel.BAntennaNum}">selected</c:if>>1</option>
					<option value = "2" <c:if test="${'2' eq towerModel.BAntennaNum}">selected</c:if>>2</option>
					<option value = "3" <c:if test="${'3' eq towerModel.BAntennaNum}">selected</c:if>>3</option>
					<option value = "4" <c:if test="${'4' eq towerModel.BAntennaNum}">selected</c:if>>4</option>
					<option value = "5" <c:if test="${'5' eq towerModel.BAntennaNum}">selected</c:if>>5</option>
					<option value = "6" <c:if test="${'6' eq towerModel.BAntennaNum}">selected</c:if>>6</option>
					<option value = "7" <c:if test="${'7' eq towerModel.BAntennaNum}">selected</c:if>>7</option>
					<option value = "8" <c:if test="${'8' eq towerModel.BAntennaNum}">selected</c:if>>8</option>
					<option value = "9" <c:if test="${'9' eq towerModel.BAntennaNum}">selected</c:if>>9</option>
					<option value = "10" <c:if test="${'10' eq towerModel.BAntennaNum}">selected</c:if>>10</option>
				</select>
				<!-- <input type="text" id="bPowerNum" name="bPowerNum" class="text" value="${towerModel.BPowerNum}" alt="allowBlank:false"/> -->
			</td>
		</tr>
	</table>
	<!-- buttons -->
	<div class="form-btns" id="btns" style="display:block">
		<html:submit styleClass="btn" property="method.save" onclick="return changeType1();" styleId="method.save">
			提交
		</html:submit>
	</div>
</html:form>
</script>  