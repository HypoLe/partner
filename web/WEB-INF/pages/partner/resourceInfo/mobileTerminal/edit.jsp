<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*,com.boco.eoms.base.util.StaticMethod;"%>
<script type="text/javascript">
</script>
<form action="${app}/partner/resourceInfo/mobileTerminal.do?method=update"    id="mobileTerminalForm" method="post"  >
	<table class="formTable">
			<caption>
					<div class="header center">
						修改移动终端信息
					</div>
			</caption>
			<tr>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content" >
					<c:set var="boxData">[{id:'${mobileTerminal.maintainCompany}',name:'<eoms:id2nameDB id="${mobileTerminal.maintainCompany}" beanId="tawSystemDeptDao"/>'}]</c:set>
					<input type="text" name="company" id="company" class="text max"  alt="allowBlank:false" />
			  		<input type="hidden" name="maintainCompany" id="maintainCompany"/>
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight" rootId="3"
					rootText='代维公司组织' valueField="maintainCompany" handler="company" textField="company"
					checktype="dept" single="true" data="${boxData}">
					</eoms:xbox>
				</td>
				<td class="label">
					所属区域&nbsp;*
				</td>
				<td class="content" >
					<c:set var="boxData">[{id:'${mobileTerminal.area}',name:'<eoms:id2nameDB id="${mobileTerminal.area}" beanId="tawSystemAreaDao"/>'}]</c:set>
					<input type="text" name="area_name" id="area_name"    class="text medium" alt="allowBlank:false" readonly="readonly"/>
					<input type="hidden" name="area" id="area"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="area" handler="area_name" textField="area_name"
					checktype="" data="${boxData}" single="true">
					</eoms:xbox>
				</td>
		</tr>
		<tr>
				<td class="label">
					维护组&nbsp;*
				</td>
				<td class="content">
					<c:set var="boxData">[{id:'${mobileTerminal.maintainTeam}',name:'<eoms:id2nameDB id="${mobileTerminal.maintainTeam}" beanId="tawSystemDeptDao"/>'}]</c:set>
					<input type="text" name="team" id="team" class="text max"  alt="allowBlank:false" />
			  		<input type="hidden" name="maintainTeam" id="maintainTeam"/>
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight&showPartnerLevelType=4&showLevelControl=true" rootId=""
					rootText='合作伙伴公司（省）' valueField="maintainTeam" handler="team" textField="team"
					checktype="dept" single="true" data="${boxData}">
					</eoms:xbox>
				</td>
				<td class="label">
					手机号码&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="simCardNumber" id="simCardNumber" 	 class="text medium" 	onblur="checkPhone();"
					value="${mobileTerminal.simCardNumber}" alt="allowBlank:false"/>
				</td>
				<%--<td class="label">
					设备识别码&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="identificationCode" id="identificationCode"    class="text medium"
					 value="${mobileTerminal.identificationCode}"  alt="allowBlank:false"/>
				</td>
		--%></tr>
		<tr>
				<td class="label">
					设备编号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="mobileTerminalNumber" class="text medium" id="mobileTerminalNumber" 
					value="${mobileTerminal.mobileTerminalNumber}" alt="allowBlank:false"/>
				</td>
				<td class="label">
					机身序列号&nbsp;
				</td>
				<td class="content">
					<input type="text" name="mobileTerminalSerilNumber" id="mobileTerminalSerilNumber" 	
					 value="${mobileTerminal.mobileTerminalSerilNumber}" class="text medium" 	alt="allowBlank:false" />
				</td>
		</tr>
		<tr>
				<td class="label">
					生产厂家&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="mobileTerminalFactory" id="mobileTerminalFactory" 	
					 value="${mobileTerminal.mobileTerminalFactory}" class="text medium" 	alt="allowBlank:false" />
				</td>
				<td class="label">
					移动终端类型&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="mobileTerminalType" styleClass="input select" id="mobileTerminalType" 
					initDicId="1230401" defaultValue="${mobileTerminal.mobileTerminalType}" alt="allowBlank:false"/>
				</td>
		</tr>
		<%--
		<tr>
				<td class="label">
					SIM类型&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="simCardType" styleClass="input select" id="simCardType" 
					initDicId="1230402" defaultValue="${mobileTerminal.simCardType}" alt="allowBlank:false"/>
				</td>
		</tr>
		--%>
		<tr>
				<td class="label">
				 	备注
				</td>
				<td class="content" colspan="3"  >
						<textarea class="textarea max"  name="notes" id="notes" >${mobileTerminal.notes}</textarea>
				</td>
		</tr>
		<input type="hidden" name="id" value="${mobileTerminal.id}"/>
	</table>
	<table>
	<tr>
		<td>
		<input type="submit" value="保存"> 		
		<%--<input type="button" value="删除" onclick="goRemove('${mobileTerminal.id}')"> 		
		--%><input type="button" value="返回" onclick="goBack()">
		</td>
	</tr>
	</table>
</form>
<script type="text/javascript">
var phoneCheck=false;
function goRemove(id){
window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/mobileTerminal.do?method=delete&&id="+id;
}
function goBack(){
window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/mobileTerminal.do?method=search";
}
function checkPhone(){ 
		    var sMobile =  document.getElementById("simCardNumber").value;
		    if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(sMobile))){ 
		        alert("不是完整的11位手机号或者正确的手机号前七位"); 
		        phoneCheck= false; 
		    } 
		    else{
		    	phoneCheck=true;
		    }
} 
Ext.onReady(function(){
	var v =new eoms.form.Validation({form:'mobileTerminalForm'});
	v.custom = function() {
        	if(!phoneCheck){
        		return false;
        	}
        	return true;
     }
});
</script>
<%@ include file="/common/footer_eoms.jsp"%>
