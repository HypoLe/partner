<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style type="text/css">
#toOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
#toAuditOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>


<html:form action="/evaTemplates.do?method=saveTemplateFromAgree&agrwor=${agrwor}&evaDeptNodeNodeId=${evaDeptNodeNodeId}&orgDeptId=${orgDeptId}" styleId="pnrTemplateForm" method="post"> 
<table class="formTable">
	<caption>
		<div class="header center">考核信息新增</div>
	</caption>
	<tr>
		<td class="label" width="25%">
			模板名称
		</td>
		<td class="content" colspan="3" width="75%">
			${evaTemplateForm.templateName}
			<input type="hidden" id="templateName" name="templateName" value="${evaTemplateForm.templateName}"/>
		</td>
	</tr>
	<tr>
		<td class="label" width="25%">
			所属专业
		</td>
		<td class="content" width="25%">
			<html:text property="professional" styleId="professional" style="width:88%" 
						styleClass="text medium"
						value="${evaTemplateForm.professional}" />
		</td>
		<c:choose>
		   <c:when test="${agrwor!='tempTask'}">
				<td class="label" width="25%">
					关联协议
				</td>
				<td class="content" width="25%">
				  		<eoms:id2nameDB id="${evaTemplateForm.agreementId}" beanId="pnrAgreementMainDao" />
						<input type="hidden" id="agreementId" name="agreementId" value="${evaTemplateForm.agreementId}"/>			
						<input type="hidden" id="contractId" name="contractId" value="${evaTemplateForm.contractId}"/>
				</td>
		   </c:when>
		   <c:otherwise>
		   		<td class="label" width="25%">
					关联临时工作任务
				</td>
				<td class="content" width="25%">
				  		<eoms:id2nameDB id="${evaTemplateForm.agreementId}" beanId="pnrTempTaskMainDao" />
						<input type="hidden" id="agreementId" name="agreementId" value="${evaTemplateForm.agreementId}"/>			
				</td>
		   </c:otherwise>
	  	</c:choose>		
	</tr>
	<tr>
		<td class="label">
			所属地市
		</td>
		<td class="content" colspan="3">
			<div id="dept-list" class="viewer-box"></div>
			<input type="button" value="选择所属地市" id="deptTreeBtn" class="btn"/>
			<input type="hidden" id="orgIds" name="orgIds" alt="allowBlank:false,vtext:'请选择所属地市'" />
		</td>
	</tr>

	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" style="width:95%" 
						styleClass="textarea"  value="${evaTemplateForm.remark}" />
		</td>
	</tr>
	<tr>
	<td colspan="4"  class="label">
	指标：
	<div id="kpiTemplate">
	<table class="formTable">
				<tr>
					<td class="label" style="vertical-align:middle">
						指标名称
					</td>
					<td  class="content" colspan="3">
						<input type="text" id="kpiName" name="kpiName" class="text medium" style="width:95%;" 
							value="" />
						<font style="color: red;"></font>
					</td>
				</tr>	
				<tr>
					<td class="label" style="vertical-align:middle">
						考核指标开始时间
					</td>
					<td class="content">
						<html:text property="kpiStartTime" styleId="kpiStartTime"
									styleClass="text medium"  style="width:88%;" 
									 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:''" readonly="true"  value="" />
					</td>
					<td class="label" style="vertical-align:middle">
						考核指标结束时间
					</td>
					<td class="content">
						<html:text property="evaEndTime" styleId="evaEndTime"
									styleClass="text medium"  style="width:88%;" 
									 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:'moreThen',link:'workStartTime',vtext:'结束时间应晚于开始时间'" readonly="true"  value="" />
					</td>			
				</tr>	
				<tr>
					<td class="label" style="vertical-align:middle">
						权重
					</td>
					<td class="content">
						<input type="text" id="weight" name="weight" class="text medium"
							value="" />
						<font color="red">*可分配权重范围：0.0~任意分</font>
					</td>
					<td class="label" style="vertical-align:middle">
						考核指标周期
					</td class="content">
					<td>
							<eoms:dict key="dict-eva" dictId="cycle" defaultId=""
								 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核指标周期'" />			
					</td>					
				</tr>	
				<tr>
					<td class="label" style="vertical-align:middle">
						考核来源
					</td>
					<td class="content" >
							<eoms:dict key="dict-partner-agreement" dictId="workType" defaultId=""
								 selectId="evaSource" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核来源'" />	
								 <font style="color: red;"></font>		
					</td>
					<td class="label" style="vertical-align:middle">
						算法分类
					</td>
					<td class="content">
						<eoms:dict key="dict-eva" dictId="algorithmType" defaultId=""
								 selectId="algorithmType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择算法类型'" />	
					</td>					
				</tr>						
											
				<tr>
					<td class="label" style="vertical-align:middle">
						权重
					</td>
					<td class="content">
						<input type="text" id="weight" name="weight" class="text medium"
							value="" />
						<font color="red">*可分配权重范围：0.0~任意分</font>
					</td>
					<td class="label" style="vertical-align:middle">
						考核指标周期
					</td>
					<td class="content">
							<eoms:dict key="dict-eva" dictId="cycle" defaultId="${agreementEva.workEvaCycle}"
								 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核指标周期'" />			
					</td>	
				</tr>								
				<tr>
					<td class="label" style="vertical-align:middle">
						算法描述
					</td>
					<td colspan="3" class="content">
						<input type="textarea" id="algorithm" name="algorithm"
							class="textarea" style="width:95%;" 
							value="" />
					</td>
				</tr>
				<tr>
					<td class="label" style="vertical-align:middle">
						备注
					</td>
					<td colspan="3" class="content">
						<input type="textarea" id="kpiRemark" name="kpiRemark" class="textarea"
							style="width:95%;"  value="" />
					</td>
				</tr>
			<tr>
<!--  		<th colspan="4"><img align=right src="${app}/images/icons/add.gif" alt="添加考核指标" onclick="javascript:addKpi();" /><img align=right src="${app}/images/icons/list-delete.gif" alt="删除上方内容" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th> -->
		</tr>
</table>
		
	</div>
<div id="kpiDiv">
	<c:if test="${agrwor=='tempTask'}">
		<c:forEach var="agreementEva" items="${tempTaskList}" varStatus="stauts">
			<table class="formTable">
				<tr>
					<th colspan="4"><b>第${stauts.count} 项：</b></th>
				</tr>
				<tr>
					<td class="label" style="vertical-align:middle">
						指标名称
					</td>
					<td class="content">
						${agreementEva.workEvaName}
						<input  id="kpiName" name="kpiName"  type="hidden"  value="${agreementEva.workEvaName}" />					
						<font style="color: red;"></font>
					</td>
					<td class="label" style="vertical-align:middle">
						被考核人
					</td>
					<td class="content">
						${agreementEva.toOrgUserName}
						<input  id="toOrgUser" name="toOrgUser"  type="hidden"  value="${agreementEva.toOrgUser}" />					
						<font style="color: red;"></font>
					</td>					
				</tr>
				<tr>
					<td class="label" style="vertical-align:middle">
						考核指标开始时间
					</td>
					<td class="content">
						${agreementEva.workEvaStartTimeStr}
						<input  id="evaStartTime" name="evaStartTime"  type="hidden"  value="${agreementEva.workEvaStartTime}" />
					</td>
					<td class="label" style="vertical-align:middle">
						考核指标结束时间
					</td>
					<td class="content">
						${agreementEva.workEvaEndTimeStr}
						<input  id="evaEndTime" name="evaEndTime"  type="hidden"  value="${agreementEva.workEvaEndTime}" />					
					</td>			
				</tr>	
				<tr>
					<td class="label" style="vertical-align:middle">
						权重
					</td>
					<td class="content">
						${agreementEva.workEvaWeight}
						<input  id="weight" name="weight" type="hidden"  value="${agreementEva.workEvaWeight}" />					
<!-- 						<font color="red">*可分配权重范围：0.0~任意分</font> -->
					</td>
					<td class="label" style="vertical-align:middle">
						考核指标周期
					</td class="content">
					<td>
						<eoms:dict key="dict-eva" dictId="cycle" itemId="${agreementEva.workEvaCycle}" beanId="id2nameXML" />
						<input  id="cycle" name="cycle"  type="hidden"  value="${agreementEva.workEvaCycle}" />					
					</td>					
				</tr>	
				<tr>
					<td class="label" style="vertical-align:middle">
						考核来源
					</td>
					<td class="content" >
							<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${agreementEva.workType}" beanId="id2nameXML" />
							<input  id="evaSource" name="evaSource"  type="hidden"  value="${agreementEva.workType}" />
								 <font style="color: red;"></font>		
					</td>
					<td class="label" style="vertical-align:middle">
						算法分类
					</td>
					<td class="content">
						<eoms:dict key="dict-eva" dictId="algorithmType" itemId="${agreementEva.algorithmType}" beanId="id2nameXML" />
						<input  id="algorithmType" name="algorithmType"  type="hidden"  value="${agreementEva.algorithmType}" />
					</td>					
				</tr>						
				<tr>
					<td class="label" style="vertical-align:middle">
						算法描述
					</td>
					<td colspan="3" class="content">
						${agreementEva.workEvaContentByType}					
						<input  type="hidden"  id="algorithm" name="algorithm"   value="${agreementEva.workEvaContent}" />					
					</td>
				</tr>
				<tr>
					<td class="label" style="vertical-align:middle">
						备注
					</td>
					<td colspan="3" class="content">
						<input  id="kpiRemark" name="kpiRemark" class="textarea"
							style="width:95%;"  value="" />
					</td>
				</tr>
			</table>
		</c:forEach>
	</c:if>
	<c:if test="${agrwor!='tempTask'}">
		<c:forEach var="agreementEva" items="${agreementEvaList}" varStatus="stauts">

			<table class="formTable">
				<tr>
					<th colspan="4"><b>第${stauts.count} 项：</b></th>
				</tr>
				<tr>
					<td class="label" style="vertical-align:middle">
						指标名称
					</td>
					<td class="content">
						${agreementEva.evaName}
						<html:hidden property="kpiName" value="${agreementEva.evaName}" />
						<font style="color: red;"></font>
					</td>
					<td class="label" style="vertical-align:middle">
						被考核人
					</td>
					<td class="content">
						${agreementEva.toEvaUserName}
						<input  id="toOrgUser" name="toOrgUser"  type="hidden"  value="${agreementEva.toEvaUser}" />					
						<font style="color: red;"></font>
					</td>					
				</tr>
				<tr>
					<td class="label" style="vertical-align:middle">
						考核指标开始时间
					</td>
					<td class="content">
						${agreementEva.evaStartTimeStr}
						<input id="evaStartTime" name="evaStartTime"  type="hidden"  value="${agreementEva.evaStartTime}" />
					</td>
					<td class="label" style="vertical-align:middle">
						考核指标结束时间
					</td>
					<td class="content">
						${agreementEva.evaEndTimeStr}
						<input  id="evaEndTime" name="evaEndTime"  type="hidden"  value="${agreementEva.evaEndTime}" />
					</td>			
				</tr>				
				<tr>
					<td class="label" style="vertical-align:middle">
						权重
					</td>
					<td class="content">
						${agreementEva.evaWeight}
						<input  id="weight" name="weight" type="hidden"  value="${agreementEva.evaWeight}" />
<!-- 						<font color="red">*可分配权重范围：0.0~任意分</font> -->
					</td>
					<td class="label" style="vertical-align:middle">
						考核指标周期
					</td>
					<td class="content">
						<eoms:dict key="dict-eva" dictId="cycle" itemId="${agreementEva.evaCycle}" beanId="id2nameXML" />
						<input  id="cycle" name="cycle"  type="hidden"  value="${agreementEva.evaCycle}" />
					</td>						
				</tr>	
				<tr>
					<td class="label" style="vertical-align:middle">
						考核来源
					</td>
					<td class="content" >
							<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${agreementEva.evaSource}" beanId="id2nameXML" />
							<input  id="evaSource" name="evaSource"  type="hidden"  value="${agreementEva.evaSource}" />
							<font style="color: red;"></font>		
					</td>
					<td class="label" style="vertical-align:middle">
						算法分类
					</td>
					<td class="content">
						<eoms:dict key="dict-eva" dictId="algorithmType" itemId="${agreementEva.evaAlgorithmType}" beanId="id2nameXML" />
						<input  id="algorithmType" name="algorithmType"  type="hidden"  value="${agreementEva.evaAlgorithmType}" />
					</td>					
				</tr>								
				<tr>
					<td class="label" style="vertical-align:middle">
						算法描述
					</td>
					<td colspan="3" class="content">
						${agreementEva.evaContentByType}					
						<input type="hidden"  id="algorithm" name="algorithm"   value="${agreementEva.evaContent}" />
					</td>
				</tr>
				<tr>
					<td class="label" style="vertical-align:middle">
						备注
					</td>
					<td colspan="3" class="content">
						<input type="textarea" id="kpiRemark" name="kpiRemark" class="textarea"
							style="width:95%;"  value="" />
					</td>
				</tr>
<!--			<tr>
					<th colspan="4"><img align=right src="${app}/images/icons/add.gif" alt="添加考核指标" onclick="javascript:addKpi();" /><img align=right src="${app}/images/icons/list-delete.gif" alt="删除上方内容" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
				</tr>	  -->
			</table>
		</c:forEach>
	</c:if>
</div>
<input type="hidden" id="parentNodeId" name="parentNodeId" value="${requestScope.parentNodeId}" />
<input type="hidden" id="startTime" name="startTime" value="${evaTemplateForm.startTime}" />
<input type="hidden" id="endTime" name="endTime" value="${evaTemplateForm.endTime}" />
<input type="hidden" id="toOrgId" name="toOrgId" value="${toOrgId}" />
<input type="hidden" id="operateId" name="operateId" value="${operateId}" />
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</td>
	</tr>
</table>
</html:form>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrTemplateForm'});
	
	var	deptTreeAction='${app}/xtree.do?method=dept';
	deptViewer = new Ext.JsonView("dept-list",
		'<div id="dept-{id}" class="viewlistitem-{nodeType}">{name}</div>',
		{ 
			multiSelect: true,
			emptyText : '<div>未选择地市</div>'
		}
	);
	var deptStr = '${requestScope.orgIds}';
	deptViewer.jsonData = eoms.JSONDecode(deptStr);
	deptViewer.refresh();
	
	deptTree = new xbox({
		btnId:'deptTreeBtn',dlgId:'dlg-dept',
		treeDataUrl:deptTreeAction,treeRootId:'-1',treeRootText:'部门',treeChkMode:'',treeChkType:'dept',
		viewer:deptViewer,saveChkFldId:'orgIds',single:true
	});
	
});
	var kpiStr = document.getElementById("kpiTemplate").innerHTML;
	document.getElementById("kpiTemplate").removeNode(true);
function addKpi() {
	Ext.DomHelper.append('kpiDiv', 
		{tag:'div', 			
		html:kpiStr
        }
    );
}

function removeNodes(obj) {   
   	obj.removeNode(true);  
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>