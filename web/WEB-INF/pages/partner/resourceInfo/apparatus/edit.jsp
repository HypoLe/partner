<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*,com.boco.eoms.base.util.StaticMethod;"%>
<script type="text/javascript">
</script>
<form action="${app}/partner/resourceInfo/apparatus.do?method=update"    id="apparatusForm" method="post"  >
	<table class="formTable">
			<caption>
					<div class="header center">
						修改仪器仪表
					</div>
			</caption>
		<tr>
				<td class="label">
					所属区域&nbsp;*
				</td>
				<td class="content" >
					<c:set var="boxData">[{id:'${apparatus.area}',name:'<eoms:id2nameDB id="${apparatus.area}" beanId="tawSystemAreaDao"/>'}]</c:set>
					<input type="text" name="area_name" id="area_name"    class="text medium" alt="allowBlank:false" readonly="readonly"/>
					<input type="hidden" name="area" id="area"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="area" handler="area_name" textField="area_name"
					checktype="" single="true" data="${boxData}">
					</eoms:xbox>
				</td>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content" >
					<c:set var="boxData">[{id:'${apparatus.maintenanceCompany}',name:'<eoms:id2nameDB id="${apparatus.maintenanceCompany}" beanId="tawSystemDeptDao"/>'}]</c:set>
					<input type="text" name="company" id="company" class="text max"  alt="allowBlank:false" />
			  		<input type="hidden" name="maintenanceCompany" id="maintenanceCompany"/>
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight" rootId=""
					rootText='代维公司组织' valueField="maintenanceCompany" handler="company" textField="company"
					checktype="dept" single="true" data="${boxData}">
					</eoms:xbox>
				</td>
			<tr>
				</td>
				<td class="label">
					所属专业&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox  name="maintenanceMajor" id="maintenanceMajor" 	initDicId="1230101" styleClass="input select" 	sub="apparatusName"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" 	defaultValue="${apparatus.maintenanceMajor}"/>
				</td>
				<td class="label">
					仪器名称&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox  name="apparatusName" id="apparatusName" alt="allowBlank:false,vtext:'请选择(单选字典)...'"
					initDicId="${apparatus.maintenanceMajor}" styleClass="input select"	defaultValue="${apparatus.apparatusName}"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					型号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="apparatusType" id="apparatusType" class="text medium"	alt="allowBlank:false" value="${apparatus.apparatusType}"/>
				</td>
				<td class="label">
					序列号&nbsp;
				</td>
				<td class="content">
					<input type="text" name="apparatusSerialNumber" id="apparatusSerialNumber" 	class="text medium" 
					alt="allowBlank:true"  value="${apparatus.apparatusSerialNumber}"	/>
				</td>
		</tr>
		<tr>
				<td class="label">
					出厂日期&nbsp;*
				</td>
				<td class="content">
					<input class="text"  name="apparatusDateOfProduction"  id="apparatusDateOfProduction" styleClass="text medium" 
					onclick="popUpCalendar(this, this,'yyyy-mm-dd ',null,null,false,-1)" readonly="readonly" 
					alt="allowBlank:false,vtype:'lessThen',link:'purchasingTime',vtext:'出厂日期必须小于购买日期'"
					value="${apparatus.apparatusDateOfProduction}"
					/>
				</td>
				<td class="label">
					采购日期&nbsp;*
				</td>
				<td class="content">
					<input class="text" name="purchasingTime" id="purchasingTime" 	onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1)"
					readonly="readonly" alt="allowBlank:false" value="${apparatus.purchasingTime}"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					产权属性&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="apparatusProperty" id="apparatusProperty"  initDicId="1230103" alt="allowBlank:false" 
					styleClass="input select" defaultValue="${apparatus.apparatusProperty}">
					</eoms:comboBox> 
				</td>
				<td class="label">
					资源所属&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="apparatusBelongs" id="apparatusBelongs" class="text medium"	alt="allowBlank:false" value="${apparatus.apparatusBelongs}"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					使用年限(年)&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="apparatusServiceLife" id="apparatusServiceLife" 	class="text medium" 
					alt="allowBlank:false,vtype:'number', vtext:'输入必须为数值'" value="${apparatus.apparatusServiceLife}"	/>            <!--必须为数值  -->
				</td>
				<td class="label">
					在用状态&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="apparatusStatus" id="apparatusStatus" 	initDicId="1230102" styleClass="input select" 
					alt="allowBlank:false" 	defaultValue="${apparatus.apparatusStatus}"/>
				</td>
		</tr>
		<tr>
				<td class="label">
				 	备注
				</td>
				<td class="content" colspan="3"  >
						<textarea class="textarea max"  name="notes" id="notes" >${apparatus.notes}</textarea>
				</td>
		</tr>
			<input type="hidden" name="id" value="${apparatus.id }">
	</table>
	<input type="submit" value="保存"> 		
	<input type="button" value="返回" onclick="goBack()">
</form>
<script type="text/javascript">
function removeApp(id){
	window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/apparatus.do?method=delete&id="+id;
}
function goBack(){
	window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/apparatus.do?method=search";
}
Ext.onReady(function(){
	  v1 = new eoms.form.Validation({form:'apparatusForm'});
	  v1.custom = function() {
        		return true;
        }
});
</script>
<%@ include file="/common/footer_eoms.jsp"%>
