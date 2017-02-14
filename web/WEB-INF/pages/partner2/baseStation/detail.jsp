<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<logic:present name="baseStationMain">
<div class="ui-widget-header ui-corner-top ui-state-default "
	id="opBasicInfo">
	分公司和归属县公司
</div>
<div>
	<table class="formTable">
		<tr>
			<td class="label">
				分公司
			</td>
			<td>
				<eoms:id2nameDB id="${baseStationMain.city}"
					beanId="tawSystemAreaDao" />
			</td>
			<td class="label">
				归属县公司
			</td>
			<td>
				<eoms:id2nameDB id="${baseStationMain.country}"
					beanId="tawSystemAreaDao" />
			</td>
		</tr>
		<tr>
			<td class="label">
				代维公司
			</td>
			<td>
				<eoms:id2nameDB id="${baseStationMain.monitorCompany}"
					beanId="tawSystemAreaDao" />
			</td>
			<td class="label">
				记录插入创建时间(服务器时间)
			</td>
			<td>
				${baseStationMain.createDate}
			</td>
		</tr>
	</table>
</div>


<div class="ui-widget-header ui-corner-top ui-state-default"
	style="margin-top: 15px">
	年度和月度信息
</div>
<div>
	<table class="formTable">
		<tr>
			<td class="label">
				年度
			</td>
			<td class="content">
				${baseStationMain.yearFlag}
			</td>
			<td class="label">
				月度
			</td>
			<td class="content">
				${baseStationMain.monthFlag}
			</td>
		</tr>
	</table>
</div>

<c:if test="${templateId!=null}">
<div class="ui-widget-header ui-corner-top ui-state-default " style="margin-top: 15px">关联考核模板信息
<table class="formTable" >
			<tr>
				<td class="label">
					关联考核模板
				</td>
				<td id="templateLink">
					<a href="${app}/partner2/appraisal.do?method=showDetail&templateId=${templateId}" target="_blank">
						${templateName}
					</a>
				</td>
			</tr>
		</table>
</div>
</c:if>

<div class="ui-widget-header ui-corner-top ui-state-default " style="margin-top: 15px">代维基站维护费用
<table class="formTable" >
			<tr>
				<td class="label">
					维护费用（元/个）*
				</td>
				<td>
					${baseStationMain.stationMaintenancePrice }
				</td>
			</tr>
		</table>
</div>

<div class="ui-widget-header ui-corner-top ui-state-default " style="margin-top: 15px">代维基站信息
<table class="formTable" >
			<tr>
				<td class="label">
					2G宏基站总数(个)*
				</td>
				<td>
					${baseStationMain.station1Sum }
				</td>
				<td class="label">
					2G宏基站代维数*
				</td>
				<td>
					${baseStationMain.station1Monitor }
				</td>
				<td class="label">
					2G宏基站增减数*
				</td>
				<td>
					${baseStationMain.station1Minus }
				</td>
			</tr>
			<tr>
				<td class="label">
					边际网基站总数(个)*
				</td>
				<td>
					${baseStationMain.station2Sum }
				</td>
				<td class="label">
					边际网基站代维数*
				</td>
				<td>
					${baseStationMain.station2Monitor }
				</td>
				<td class="label">
					边际网基站增减数*
				</td>
				<td>
					${baseStationMain.station2Minus }
				</td>
			</tr>
			<tr>
				<td class="label">
					独立TD宏基站总数(个)*
				</td>
				<td>
					${baseStationMain.station3Sum }
				</td>
				<td class="label">
					独立TD宏基站代维数*
				</td>
				<td>
					${baseStationMain.station3Monitor }
				</td>
				<td class="label">
					独立TD宏基站增减数*
				</td>
				<td>
					${baseStationMain.station3Minus }
				</td>
			</tr>
			<tr>
				<td class="label">
					共址TD宏基站总数(个)*
				</td>
				<td>
					${baseStationMain.station4Sum }
				</td>
				<td class="label">
					共址TD宏基站代维数*
				</td>
				<td>
					${baseStationMain.station4Monitor }
				</td>
				<td class="label">
					共址TD宏基站增减数*
				</td>
				<td>
					${baseStationMain.station4Minus }
				</td>
			</tr>
			<tr>
				<td class="label">
					直放站总数(个)*
				</td>
				<td>
					${baseStationMain.station5Sum }
				</td>
				<td class="label">
					直放站代维数*
				</td>
				<td>
					${baseStationMain.station5Monitor }
				</td>
				<td class="label">
					直放站增减数*
				</td>
				<td>
					${baseStationMain.station5Minus }
				</td>
			</tr>
		</table>
</div>


</logic:present>
<logic:notPresent name="baseStationMain" scope="request"> 
     无记录
</logic:notPresent>
</div>

<%@ include file="/common/footer_eoms.jsp"%>