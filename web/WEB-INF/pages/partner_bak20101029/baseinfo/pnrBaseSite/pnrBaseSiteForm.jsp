<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrBaseSiteForm'});
});
function getResponseText(url) {
    var xmlhttp;
    if(eoms.isIE){
    	try{
    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); 
    	}catch(e){
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");    		
    	}
    }else{
    	xmlhttp = new XMLHttpRequest();
    }
    var method = "post";
    url=url+"&"+new Date();
    xmlhttp.open(method, url, false);
    xmlhttp.setRequestHeader("content-type", "text/html; charset=GBK");
    xmlhttp.send(null);
    return xmlhttp.responseText;
}
 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
function changeCity(con)
		{    
			delAllOption("town");//地市选择更新后，重新刷新县区
			var city = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/baseinfo/pnrBaseSites.do?method=changeCity&city="+city;
			Ext.Ajax.request({
				url : url ,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("<\/SCRIPT>")>0){
				  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					var json = eval(res);
		
					var townName = "town";
					var arrOptions = json[0].cb.split(",");
					var obj=$(townName);
					var i=0;
					var j=0;
					for(i=0;i<arrOptions.length;i++){
						var opt=new Option(arrOptions[i+1],arrOptions[i]);
						obj.options[j]=opt;
						j++;
						i++;
					}
					if(con==1){
						var town = '${pnrBaseSiteForm.town}';
						if(city!=''){
							document.getElementById("town").value = town;
						}									
					}					
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}
</script>


<html:form action="/pnrBaseSites.do?method=save" styleId="pnrBaseSiteForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center">合作伙伴站址信息管理</div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.name" />
		</td>
		<td class="content" colspan="3">
			<html:text property="name" styleId="name"
						styleClass="text"   style="width:90%" 
						 value="${pnrBaseSiteForm.name}" />
		</td>
	</tr>
<!-- 
	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.addTime" />
		</td>
		<td class="content">
			<html:text property="addTime" styleId="addTime"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.addTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.addUser" />
		</td>
		<td class="content">
			<html:text property="addUser" styleId="addUser"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.addUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.updateTime" />
		</td>
		<td class="content">
			<html:text property="updateTime" styleId="updateTime"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.updateTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.updateUser" />
		</td>
		<td class="content">
			<html:text property="updateUser" styleId="updateUser"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.updateUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.delTime" />
		</td>
		<td class="content">
			<html:text property="delTime" styleId="delTime"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.delTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.delUser" />
		</td>
		<td class="content">
			<html:text property="delUser" styleId="delUser"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.delUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.isDel" />
		</td>
		<td class="content">
			<html:text property="isDel" styleId="isDel"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.isDel}" />
		</td>
	</tr>
 -->
	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.code" />
		</td>
		<td class="content" colspan="3">
			<html:text property="code" styleId="code"
						styleClass="text"  style="width:90%" 
						 value="${pnrBaseSiteForm.code}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.state" />
		</td>
		<td class="content">
			<html:text property="state" styleId="state"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.state}" />
		</td>
		<td class="label">
			<fmt:message key="pnrBaseSite.softVersion" />
		</td>
		<td class="content">
			<html:text property="softVersion" styleId="softVersion"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.softVersion}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.hardwareFlat" />
		</td>
		<td class="content">
			<html:text property="hardwareFlat" styleId="hardwareFlat"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.hardwareFlat}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.hardwareVersion" />
		</td>
		<td class="content">
			<html:text property="hardwareVersion" styleId="hardwareVersion"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.hardwareVersion}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.siteType" />
		</td>
		<td class="content">
			<html:text property="siteType" styleId="siteType"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.siteType}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.siteNO" />
		</td>
		<td class="content">
			<html:text property="siteNO" styleId="siteNO"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.siteNO}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.siteUse" />
		</td>
		<td class="content">
			<html:text property="siteUse" styleId="siteUse"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.siteUse}" />
		</td>

		<td class="label">
			Vip标识
		</td>
		<td class="content">
			<html:text property="vipNO" styleId="vipNO"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.vipNO}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.connectCode" />
		</td>
		<td class="content">
			<html:text property="connectCode" styleId="connectCode"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.connectCode}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.frequencyBand" />
		</td>
		<td class="content">
			<html:text property="frequencyBand" styleId="frequencyBand"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.frequencyBand}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.equipmentFactury" />
		</td>
		<td class="content">
			<html:text property="equipmentFactury" styleId="equipmentFactury"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.equipmentFactury}" />
		</td>
		<td class="label">
			<fmt:message key="pnrBaseSite.vendor" />
		</td>
		<td class="content">
			<html:text property="vendor" styleId="vendor"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.vendor}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.productDate" />
		</td>
		<td class="content">
			<html:text property="productDate" styleId="productDate"
						styleClass="text" readonly="true"
			           onclick="popUpCalendar(this, this);"
						 value="${pnrBaseSiteForm.productDate}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.productNO" />
		</td>
		<td class="content">
			<html:text property="productNO" styleId="productNO"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.productNO}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.siteName" />
		</td>
		<td class="content">
			<html:text property="siteName" styleId="siteName"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.siteName}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.addressNO" />
		</td>
		<td class="content">
			<html:text property="addressNO" styleId="addressNO"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.addressNO}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.city" />
		</td>
		<td class="content">
					<!-- 地市 -->			
			<select name="city" id="city"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changeCity(0);">
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="city" name="city">
					<option value="${city.areaid}">
						${city.areaname}
					</option>
				</logic:iterate>
			</select>
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.town" />
		</td>
		<td class="content">
			<!-- 县区 -->			
			<select name="town" id="town" 
				alt="allowBlank:false,vtext:'请选择所在县区'">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.address" />
		</td>
		<td class="content" colspan="3">
			<html:text property="address" styleId="address"
						styleClass="text max"   style="width:90%" 
						 value="${pnrBaseSiteForm.address}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			归属BSC
		</td>
		<td class="content">
			<html:text property="bsc" styleId="bsc"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.bsc}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.siteSort" />
		</td>
		<td class="content">
			<html:text property="siteSort" styleId="siteSort"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.siteSort}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.longitude" />
		</td>
		<td class="content">
			<html:text property="longitude" styleId="longitude"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.longitude}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.latitude" />
		</td>
		<td class="content">
			<html:text property="latitude" styleId="latitude"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.latitude}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.altitude" />
		</td>
		<td class="content">
			<html:text property="altitude" styleId="altitude"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.altitude}" />
		</td>

		<td class="label">
			管理此网元的OMC
		</td>
		<td class="content">
			<html:text property="omc" styleId="omc"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.omc}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.borderDescription" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="borderDescription" styleId="borderDescription" 
						 rows="2" style="width:90%" styleClass="textarea" value="${pnrBaseSiteForm.borderDescription}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.coverArea" />
		</td>
		<td class="content">
			<html:text property="coverArea" styleId="coverArea"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.coverArea}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.coverType" />
		</td>
		<td class="content">
			<html:text property="coverType" styleId="coverType"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.coverType}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.courtCover" />
		</td>
		<td class="content">
			<html:text property="courtCover" styleId="courtCover"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.courtCover}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.geographyCharacter" />
		</td>
		<td class="content">
			<html:text property="geographyCharacter" styleId="geographyCharacter"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.geographyCharacter}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.areaType" />
		</td>
		<td class="content">
			<html:text property="areaType" styleId="areaType"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.areaType}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.isMonitor" />
		</td>
		<td class="content">
			<html:text property="isMonitor" styleId="isMonitor"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.isMonitor}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.courtNum" />
		</td>
		<td class="content">
			<html:text property="courtNum" styleId="courtNum"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.courtNum}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.carrierFrequencyNum" />
		</td>
		<td class="content">
			<html:text property="carrierFrequencyNum" styleId="carrierFrequencyNum"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.carrierFrequencyNum}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.networkDate" />
		</td>
		<td class="content">
			<html:text property="networkDate" styleId="networkDate"
						styleClass="text" readonly="true"
			           onclick="popUpCalendar(this, this);"
						 value="${pnrBaseSiteForm.networkDate}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.timeForArrive" />
		</td>
		<td class="content">
			<html:text property="timeForArrive" styleId="timeForArrive"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.timeForArrive}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.powerProviderTel" />
		</td>
		<td class="content">
			<html:text property="powerProviderTel" styleId="powerProviderTel"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.powerProviderTel}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.acOrDc" />
		</td>
		<td class="content">
			<html:text property="acOrDc" styleId="acOrDc"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.acOrDc}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.transfersType" />
		</td>
		<td class="content">
			<html:text property="transfersType" styleId="transfersType"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.transfersType}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.togetherSite" />
		</td>
		<td class="content">
			<html:text property="togetherSite" styleId="togetherSite"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.togetherSite}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.togetherSiteInf" />
		</td>
		<td class="content">
			<html:text property="togetherSiteInf" styleId="togetherSiteInf"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.togetherSiteInf}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.noncommissionNO" />
		</td>
		<td class="content">
			<html:text property="noncommissionNO" styleId="noncommissionNO"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.noncommissionNO}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.setDate" />
		</td>
		<td class="content">
			<html:text property="setDate" styleId="setDate"
						styleClass="text" readonly="true"
			           onclick="popUpCalendar(this, this);"
						 value="${pnrBaseSiteForm.setDate}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.maintainType" />
		</td>
		<td class="content">
			<html:text property="maintainType" styleId="maintainType"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.maintainType}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.provider" />
		</td>
		<td class="content">
			<html:text property="provider" styleId="provider"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.provider}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.powerState" />
		</td>
		<td class="content">
			<html:text property="powerState" styleId="powerState"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.powerState}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.lockState" />
		</td>
		<td class="content">
			<html:text property="lockState" styleId="lockState"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.lockState}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.dutyPerson" />
		</td>
		<td class="content">
			<html:text property="dutyPerson" styleId="dutyPerson"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.dutyPerson}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.dutyPersonTel" />
		</td>
		<td class="content">
			<html:text property="dutyPersonTel" styleId="dutyPersonTel"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.dutyPersonTel}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.inOrOut" />
		</td>
		<td class="content">
			<html:text property="inOrOut" styleId="inOrOut"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.inOrOut}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.keyKeeper" />
		</td>
		<td class="content">
			<html:text property="keyKeeper" styleId="keyKeeper"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.keyKeeper}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.keyLeave" />
		</td>
		<td class="content">
			<html:text property="keyLeave" styleId="keyLeave"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.keyLeave}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.roomRight" />
		</td>
		<td class="content">
			<html:text property="roomRight" styleId="roomRight"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.roomRight}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.houseFrame" />
		</td>
		<td class="content">
			<html:text property="houseFrame" styleId="houseFrame"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.houseFrame}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.roomType" />
		</td>
		<td class="content">
			<html:text property="roomType" styleId="roomType"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.roomType}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.houseArea" />
		</td>
		<td class="content">
			<html:text property="houseArea" styleId="houseArea"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.houseArea}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.rent" />
		</td>
		<td class="content">
			<html:text property="rent" styleId="rent"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.rent}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.owner" />
		</td>
		<td class="content">
			<html:text property="owner" styleId="owner"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.owner}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.ownerIDcard" />
		</td>
		<td class="content">
			<html:text property="ownerIDcard" styleId="ownerIDcard"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.ownerIDcard}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.ownerTel" />
		</td>
		<td class="content">
			<html:text property="ownerTel" styleId="ownerTel"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.ownerTel}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.roomNO" />
		</td>
		<td class="content">
			<html:text property="roomNO" styleId="roomNO"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.roomNO}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.groundCardNO" />
		</td>
		<td class="content">
			<html:text property="groundCardNO" styleId="groundCardNO"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.groundCardNO}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.tenancyNO" />
		</td>
		<td class="content">
			<html:text property="tenancyNO" styleId="tenancyNO"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.tenancyNO}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.transEquipmentType" />
		</td>
		<td class="content">
			<html:text property="transEquipmentType" styleId="transEquipmentType"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.transEquipmentType}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.transfersBelong" />
		</td>
		<td class="content">
			<html:text property="transfersBelong" styleId="transfersBelong"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.transfersBelong}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.transfersPort" />
		</td>
		<td class="content">
			<html:text property="transfersPort" styleId="transfersPort"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.transfersPort}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.fiberType" />
		</td>
		<td class="content">
			<html:text property="fiberType" styleId="fiberType"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.fiberType}" />
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.isTransNode" />
		</td>
		<td class="content">
			<html:text property="isTransNode" styleId="isTransNode"
						styleClass="text medium"
						 value="${pnrBaseSiteForm.isTransNode}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.description" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="description" rows="4" style="width:90%" styleClass="textarea" value="${pnrBaseSiteForm.description}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.remark" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" rows="4" style="width:90%" styleClass="textarea" value="${pnrBaseSiteForm.remark}" />
		</td>
	</tr>
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty pnrBaseSiteForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确定删除该记录')){
						var url='${app}/partner/baseinfo/pnrBaseSites.do?method=remove&id=${pnrBaseSiteForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${pnrBaseSiteForm.id}" />
<html:hidden property="addTime" value="${pnrBaseSiteForm.addTime}" />
<html:hidden property="addUser" value="${pnrBaseSiteForm.addUser}" />
<html:hidden property="isDel" value="${pnrBaseSiteForm.isDel}" />
</html:form>
<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var city = '${pnrBaseSiteForm.city}';
		
	if(city!=''){
 		document.getElementById("city").value = city;
		changeCity(1);
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>