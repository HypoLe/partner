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


<html:form action="/evaTemplateTemps.do?method=saveTemplateTempEdit&nodeId=${nodeId}&agrwor=${agrwor}&evaDeptNodeNodeId=${evaDeptNodeNodeId}&orgDeptId=${orgDeptId}" styleId="pnrTemplateForm" method="post"> 
<table class="formTable">
	<caption>
		<div class="header center">考核信息新增</div>
	</caption>
	<tr>
		<td class="label" width="25%">
			模板名称
		</td>
		<td class="content" width="75%">
			${evaTemplateTempForm.templateName}
			<input type="hidden" id="templateName" name="templateName" value="${evaTemplateTempForm.templateName}"/>
		</td>
		<c:choose>
		   <c:when test="${agrwor!='tempTask'}">
				<td class="label" width="25%">
					关联协议
				</td>
				<td class="content" width="25%">
				  		<eoms:id2nameDB id="${evaTemplateTempForm.agreementId}" beanId="pnrAgreementMainDao" />
						<input type="hidden" id="agreementId" name="agreementId" value="${evaTemplateTempForm.agreementId}"/>			
						<input type="hidden" id="contractId" name="contractId" value="${evaTemplateTempForm.contractId}"/>
				</td>
		   </c:when>
		   <c:otherwise>
		   		<td class="label" width="25%">
					关联临时工作任务
				</td>
				<td class="content" width="25%">
				  		<eoms:id2nameDB id="${evaTemplateTempForm.agreementId}" beanId="pnrTempTaskMainDao" />
						<input type="hidden" id="agreementId" name="agreementId" value="${evaTemplateTempForm.agreementId}"/>			
				</td>
		   </c:otherwise>
	  	</c:choose>		
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			开始时间
		</td>
		<td class="content">
			<html:text property="startTime" styleId="startTime"  style="width:95%;" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${evaTemplateTempForm.startTime}" onclick="popUpCalendar(this, this,null,null,null,false,-1);" readonly="true" />
		</td>
		<td class="label" style="vertical-align:middle">
			结束时间
		</td>
		<td class="content">
			<html:text property="endTime" styleId="endTime"  style="width:95%;" 
						styleClass="text medium"
						 value="${evaTemplateTempForm.endTime}" onclick="popUpCalendar(this, this,null,null,null,false,-1);" alt="allowBlank:false,vtype:'moreThen',link:'startTime',vtext:'结束时间应晚于开始时间'" readonly="true" />
		</td>	
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" style="width:98%" 
						  value="${evaTemplateTempForm.remark}" rows="2" />
		</td>
	</tr>
	<tr>
	<td colspan="4"  class="label">
	指标：
		<div id="kpiDiv">
			<c:if test="${agrwor=='tempTask'}">
				<c:forEach var="agreementEva" items="${tempTaskList}" varStatus="stauts">
					<div id="${agreementEva.id}">
						<table class="formTable">
							<tr>
								<td class="label" style="vertical-align:middle">
									考核指标开始时间
								</td>
								<td class="content">
									${agreementEva.workEvaStartTimeStr}
									<input  id="evaStartTime" name="evaStartTime"  type="hidden"  value="${agreementEva.workEvaStartTimeStr}" />
								</td>
								<td class="label" style="vertical-align:middle">
									考核指标结束时间
								</td>
								<td class="content">
									${agreementEva.workEvaEndTimeStr}
									<input  id="evaEndTime" name="evaEndTime"  type="hidden"  value="${agreementEva.workEvaEndTimeStr}" />					
								</td>			
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
									<eoms:id2nameDB id="${agreementEva.toOrgUser}" beanId="tawSystemUserDao" />
									<input  name="toOrgUserName" id="toOrgUserrName"  type="hidden"   value="<eoms:id2nameDB id="${agreementEva.toOrgUser}" beanId="tawSystemUserDao" />" />
									<input  id="toOrgUser" name="toOrgUser"  type="hidden"  value="${agreementEva.toOrgUser}" />					
									<font style="color: red;"></font>
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
								<html:hidden property="evaId" value="${agreementEva.id}" />	
							<tr>
								<td class="label" style="vertical-align:middle">
									备注
								</td>
								<td colspan="3" class="content">
									<input  id="kpiRemark" name="kpiRemark"  type="hidden"  value="" />
								<img align=right src="${app}/images/icons/delete_c.gif" alt="删除上方任务项" onclick="removeNodes(parentNode.parentNode.parentNode);"/>		
								<img align=right src="${app}/images/icons/edit_c.gif" alt="编辑工作任务" onclick="javascript:show_windowsEva('${app}/eva/evaTemplateTemps.do?method=createEva','','${agreementEva.id}');" />
										
								</td>
							</tr>
								<html:hidden property="agreementWorkId" value="${agreementEva.id}" />
						</table>
					</div>	
				</c:forEach>
			</c:if>
			<c:if test="${agrwor!='tempTask'}">
				<c:forEach var="agreementEva" items="${agreementEvaList}" varStatus="stauts">
					<div id="${agreementEva.id}">
						<table class="formTable">
							<tr>
								<td class="label" style="vertical-align:middle">
									考核指标开始时间
								</td>
								<td class="content">
									${agreementEva.evaStartTimeStr}
									<input id="evaStartTime" name="evaStartTime"  type="hidden"  value="${agreementEva.evaStartTimeStr}" />
								</td>
								<td class="label" style="vertical-align:middle">
									考核指标结束时间
								</td>
								<td class="content">
									${agreementEva.evaEndTimeStr}
									<input  id="evaEndTime" name="evaEndTime"  type="hidden"  value="${agreementEva.evaEndTimeStr}" />
								</td>			
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
									<input  name="toOrgUserName" id="toOrgUserrName"  type="hidden"   value="${agreementEva.toEvaUserName}" />
									<input  id="toOrgUser" name="toOrgUser"  type="hidden"  value="${agreementEva.toEvaUser}" />					
									<font style="color: red;"></font>
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
								<html:hidden property="evaId" value="${agreementEva.id}" />	
							<tr>
								<td class="label" style="vertical-align:middle">
									备注
								</td>
								<td colspan="3" class="content">
									<input  id="kpiRemark" name="kpiRemark"  type="hidden"  value="" />
									<img align=right src="${app}/images/icons/delete_c.gif" alt="删除上方任务项" onclick="removeNodes(parentNode.parentNode.parentNode);"/>		
									<img align=right src="${app}/images/icons/edit_c.gif" alt="编辑工作任务" onclick="javascript:show_windowsEva('${app}/eva/evaTemplateTemps.do?method=createEva','','${agreementEva.id}');" />
								</td>
							</tr>
							<html:hidden property="agreementWorkId" value="${agreementEva.agreementWorkId}" />
						</table>
					</div>	
				</c:forEach>
			</c:if>
		</div>
		<img src="${app}/images/icons/add_c.gif" alt="添加考核内容"  align=right  onclick="javascript:show_windowsEva('${app}/eva/evaTemplateTemps.do?method=createEva','toolbar=no,scrollBars=yes,width=800,height=450','');" />
	</td>	
	</tr>
<input type="hidden" id="parentNodeId" name="parentNodeId" value="${requestScope.parentNodeId}" />
<input type="hidden" id="toOrgId" name="toOrgId" value="${toOrgId}" />
<input type="hidden" id="operateId" name="operateId" value="${operateId}" />
<input type="hidden" id="confirmUser" name="confirmUser" value="${confirmUser}" />
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</td>
	</tr>
</table>
</html:form>
<script type="text/javascript">

function show_windowsEva(myurl,props,tableId)
{  
  var newWindow;
  var temp_props = props;
  var planWeights = document.getElementsByName("weight");
  var toOrgId = '';
  var editWeight ='';//要编辑的记录权重
  var allWeight = 100;
  for(var i = 0 ;i<planWeights.length ; i++){
	allWeight = allWeight - parseFloat(planWeights[i].value);
	}
  if(tableId!=''){
  	editWeight = document.getElementById(tableId).getElementsByTagName("input")[8].value;
  	allWeight = allWeight + parseFloat(editWeight);
  	toOrgId = document.getElementById(tableId).getElementsByTagName("input")[4].value;
  }
  myurl = myurl+'&allWeight='+allWeight+'&tableId='+tableId+'&toOrgId='+toOrgId;
  newWindow = window.open(myurl,'',temp_props);
}

function removeNodes(obj) {
		if(confirm("确定要删除该子项？")){   
   			obj.removeNode(true);  
		}
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>