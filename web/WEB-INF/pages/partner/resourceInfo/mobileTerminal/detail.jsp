<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*,com.boco.eoms.base.util.StaticMethod;"%>
<script type="text/javascript">
</script>
	<table class="formTable">
			<caption>
					<div class="header center">
						移动终端信息
					</div>
			</caption>
		<tr>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${mobileTerminal.maintainCompany}" beanId="tawSystemDeptDao"/>
				</td>
				<td class="label">
					所属区域&nbsp;*
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${mobileTerminal.area}" beanId="tawSystemAreaDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					维护组&nbsp;*
				</td>
				<td class="content">
				<eoms:id2nameDB  id="${mobileTerminal.maintainTeam}" beanId="tawSystemDeptDao"/>
				</td>
				<td class="label">
					手机号码&nbsp;*
				</td>
				<td class="content">
					${mobileTerminal.simCardNumber}
				</td>
				<%--<td class="label">
					设备识别码&nbsp;*
				</td>
				<td class="content">
					${mobileTerminal.identificationCode}
				</td>
		--%></tr>
		<tr>
				<td class="label">
					设备编号&nbsp;*
				</td>
				<td class="content">
				${mobileTerminal.mobileTerminalNumber}
				</td>
				<td class="label">
					设备序列号&nbsp;*
				</td>
				<td class="content">
					${mobileTerminal.mobileTerminalSerilNumber}
				</td>
		</tr>
		<tr>
				<td class="label">
					生产厂家&nbsp;*
				</td>
				<td class="content">
					${mobileTerminal.mobileTerminalFactory}
				</td>
				<td class="label">
					移动终端类型&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${mobileTerminal.mobileTerminalType}" beanId="ItawSystemDictTypeDao"/>
				</td>
		</tr>
		<%--<tr>
				<td class="label">
					SIM类型&nbsp;*
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${mobileTerminal.simCardType}" beanId="ItawSystemDictTypeDao"/>
				</td>
		</tr>
		--%><tr>
				<td class="label">
				 	备注
				</td>
				<td class="content" colspan="3"  >
					<pre>${mobileTerminal.notes}</pre>
				</td>
		</tr>
	</table>
	<%--<table>
	<tr>
		<td>
		<input type="button" value="返回" onclick="goBack()">
		</td>
	</tr>
	</table>
--%><script type="text/javascript">
function goBack(){
window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/mobileTerminal.do?method=search";
}
function edit(id){
window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/mobileTerminal.do?method=edit&id="+id;
}
Ext.onReady(function(){
	//var v =new eoms.form.Validation({form:'mobileTerminalForm'});
});
</script>
<%@ include file="/common/footer_eoms.jsp"%>
