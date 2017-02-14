<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="com.boco.eoms.partner.agreement.model.PnrAgreementAudit"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String auditTime = "";
%>
<script type="text/javascript">
	function getContractUrl(){
	 window.open ('${app}/partner/agreement/pnrAgreementMains.do?method=queryContractDo','newwindow','height=600,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
    }
    function setCompactNostr(obj){
    	document.getElementById("compactNostr").innerHTML=obj;
    }
	function edit(){
		document.getElementById("subButton").disabled=true;
    	var url='${app}/partner/agreement/pnrAgreementMains.do?method=edit&id=${pnrAgreementMainForm.id}&auditId=${id}';
		location.href=url;
    }
    function showAuditTable(){
		works = document.getElementById("auditTable");  
		if(works.style.display=='block'){
			works.style.display="none";
		} else {
			works.style.display="block";
		}
    }
	function getContractUrlNew(){
	 window.open ('${app}/partner/feeInfo/pnrFeeInfoMains.do?method=edit&contractNew=contractNew&agreementId=${pnrAgreementMainForm.id}');
    }    
</script>
<html:form action="pnrAgreementAudits.do?method=auditDoForNew" styleId="pnrAgreementAuditForm" method="post">

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">

<table class="formTable" >
	<caption>
		<div class="header center"><fmt:message key="pnrAgreementMain.form.heading"/></div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			服务协议甲方名称&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${pnrAgreementMainForm.partyA}
		    </td>
		<td class="label" style="vertical-align:middle">
			服务协议乙方名称&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		 ${pnrAgreementMainForm.partyB}
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			甲方负责人&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${pnrAgreementMainForm.partyAUser}		
		</td>
		<td class="label" style="vertical-align:middle">
			乙方负责人&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${pnrAgreementMainForm.partyBUser}		 
		 </td>
	</tr>	
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.agreementName" />
		</td>
		<td class="content" >
			${pnrAgreementMainForm.agreementName}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.agreementNO" />
		</td>
		<td class="content" >
			${pnrAgreementMainForm.agreementNO}
		</td>	
	</tr>		
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.startTime" />
		</td>
		<td class="content">
			${pnrAgreementMainForm.startTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.endTime" />
		</td>
		<td class="content">
			${pnrAgreementMainForm.endTimeStr}
		</td>
	</tr>
	<tr>
		
		<td class="label" style="vertical-align:middle">
			合同编号
		</td>
		<td class="content" >
			<html:hidden property="compactName" value="${pnrAgreementMainForm.compactName}"/>
			<html:hidden property="contentId" value="${pnrAgreementMainForm.contentId}"/>
			<html:hidden property="compactNo" value="${pnrAgreementMainForm.compactNo}"/>
			<c:if test="${pnrAgreementMainForm.compactNo ==null||pnrAgreementMainForm.compactNo==''}">
				<c:if test="${pnrAgreementMainForm.state==2}">
					<input type="button" name="contractUrl" id="contractUrl" value="关联已有合同" class="btn" onclick="getContractUrl();"/>
					<input type="button" name="contractUrlNew" id="contractUrlNew" value="新增关联合同" class="btn" onclick="getContractUrlNew();"/>
				</c:if>
				<c:if test="${pnrAgreementMainForm.state!=2}">
					<input type="button" name="contractUrl" id="contractUrl" value="关联已有合同" class="btn" onclick="getContractUrl();"  disabled="disabled"/>
					<input type="button" name="contractUrlNew" id="contractUrlNew" value="新增关联合同" class="btn" onclick="getContractUrlNew();"  disabled="disabled"/>
				</c:if>			
			</c:if>
				<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=${pnrAgreementMainForm.contentId}" target="_blank">
				${pnrAgreementMainForm.compactNo}</a>
		</td>
		<td class="label" style="vertical-align:middle">
			服务类型
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-agreement" dictId="serviceType" itemId="${pnrAgreementMainForm.serverType}" beanId="id2nameXML" />		
		</td>		
	</tr>
	<!-- 
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.specialCase" />
		</td>
		<td class="content" colspan="3">
			${pnrAgreementMainForm.specialCase}
		</td>
	</tr>	
	 -->
	<c:if test="${pnrAgreementMainForm.state==2}">
	<tr>
	
		<td class="label" style="vertical-align:middle">
			工作计划编号
		</td>
		<td class="content">
		<c:if test="${pnrAgreementMainForm.workplanId ==null }">
			<c:if test="${pnrAgreementMainForm.partyBUser == paruserId }">
							<input type="button" class="btn" value="新增工作计划" 
					onclick="window.open('${app}/partner/workplan/pnrWorkplanMains.do?method=edit&agreementId=${pnrAgreementMainForm.id}&agreementNO=${pnrAgreementMainForm.agreementNO}&partyAId=${pnrAgreementMainForm.partyAId}')"/>
			</c:if>
			<c:if test="${pnrAgreementMainForm.partyBUser != paruserId }">
							<input type="button" class="btn" value="新增工作计划" 
					onclick="window.open('${app}/partner/workplan/pnrWorkplanMains.do?method=edit&agreementId=${pnrAgreementMainForm.id}&agreementNO=${pnrAgreementMainForm.agreementNO}&partyAId=${pnrAgreementMainForm.partyAId}')" disabled="disabled"/>
			</c:if>
		</c:if>
		<c:if test="${pnrAgreementMainForm.workplanId !=null }">
				<a href="${app}/partner/workplan/pnrWorkplanMains.do?method=detail&id=${pnrAgreementMainForm.workplanId }" target="_blank">
			${pnrAgreementMainForm.workplanNO}</a>
		</c:if>
		</td>
		<td class="label" style="vertical-align:middle">
			考核模板名称
		</td>
		<td class="content">
		<c:if test="${pnrAgreementMainForm.evaTemplateId ==null }">
			<c:if test="${pnrAgreementMainForm.partyAUser == paruserId }">
							<input type="button" class="btn" value="新增考核任务" 
					onclick="window.open('${app}/eva/evaTemplates.do?method=newTemplateFromAgree&agreementId=${pnrAgreementMainForm.id}&partner=10101')"/>
			</c:if>
			<c:if test="${pnrAgreementMainForm.partyAUser != paruserId }">
							<input type="button" class="btn" value="新增考核任务" 
					onclick="window.open('${app}/eva/evaTemplates.do?method=newTemplateFromAgree&agreementId=${pnrAgreementMainForm.id}&partner=10101')"  disabled="disabled"/>
			</c:if>			
		</c:if>
		<c:if test="${pnrAgreementMainForm.evaTemplateId !=null }">
		<a href="${app}/eva/evaTasks.do?method=xqueryByTpid&templateId=${pnrAgreementMainForm.evaTemplateId}" target="_blank">
			<eoms:id2nameDB id="${pnrAgreementMainForm.evaTemplateId}" beanId="evaTemplateDao" />
		</a>
		</c:if>
		</td>
	</tr>
	</c:if>

	<tr>
	<td colspan="4">
		<div id="workDiv">
			服务工作任务及考核要求:
				<c:forEach var="work" items="${workList}" varStatus="stauts">
					<table class="formTable">
					<tr>
						<th colspan="4"><b>第${stauts.count} 项：</b></th>
					</tr>	
			
						<tr><td> </td></tr>
					<tr>		
						<td class="label" style="vertical-align:middle" >
							工作内容
						</td>
						<td class="content">
							${work.workContent}
							<html:hidden property="workContent" value="${work.workContent}" />
						</td>
						<td class="label" style="vertical-align:middle" >
							执行人
						</td>
						<td class="content">
								${work.toOrgUserName}
							<html:hidden property="toOrgUserName" value="${work.toOrgUserName}" />
							<html:hidden property="toOrgUser" value="${work.toOrgUser}" />
						</td>
					</tr>
					<tr>
						<td class="label" style="vertical-align:middle" >
							工作完成标准
						</td>
						<td class="content" colspan="3">
								${work.workStandard}
							<html:hidden property="workStandard" value="${work.workStandard}" />
						</td>				
					</tr>
					<tr>		
						<td class="label" style="vertical-align:middle">
							工作任务类型
						</td>
						<td class="content" >
								<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${work.workType}" beanId="id2nameXML" />
							<html:hidden property="workType" value="${work.workType}" />				
						</td>
						<td class="label" style="vertical-align:middle">
							工作任务执行周期
						</td>
						<td class="content">
							<eoms:dict key="dict-partner-agreement" dictId="workCycle" itemId="${work.workCycle}" beanId="id2nameXML" />
							<html:hidden property="workCycle" value="${work.workCycle}" />	
						</td>	
					</tr>	
					<tr>		
							<td class="label" style="vertical-align:middle">
								考核指标名称
							</td>
							<td class="content">
								${work.workEvaName}
							<html:hidden property="workEvaName" value="${work.workEvaName}" />
							</td>		
							<td class="label" style="vertical-align:middle">
								算法分类
							</td>
							<td class="content">
								<eoms:dict key="dict-eva" dictId="algorithmType" itemId="${work.algorithmType}" beanId="id2nameXML" />
							<html:hidden property="algorithmType" value="${work.algorithmType}" />
							</td>	
					</tr>
					<tr>
							<td class="label" style="vertical-align:middle">
								指标详细算法
							</td>
							<td class="content" colspan="3">
								${work.workEvaContentByType}
							<html:hidden property="workEvaContent" value="${work.workEvaContent}" />
							</td>						
					</tr>
					<tr>
						<td class="label" style="vertical-align:middle">
							考核开始时间
						</td>
						<td class="content">
							${work.workEvaStartTimeStr}
							<html:hidden property="workEvaStartTime" value="${work.workEvaStartTime}" />
						</td>
						<td class="label" style="vertical-align:middle">
							考核结束时间
						</td>
						<td class="content">
							${work.workEvaEndTimeStr}
							<html:hidden property="workEvaEndTime" value="${work.workEvaEndTime}" />			
						</td>			
					</tr>
					<tr>		
						<td class="label" style="vertical-align:middle">
							所占分数
						</td>
						<td class="content">
								${work.workEvaWeight}
							<html:hidden property="workEvaWeight" value="${work.workEvaWeight}" />
						</td>	
						<td class="label" style="vertical-align:middle">
							考核周期
						</td>
						<td class="content">
							<eoms:dict key="dict-eva" dictId="cycle" itemId="${work.workEvaCycle}" beanId="id2nameXML" />
							<html:hidden property="workEvaCycle" value="${work.workEvaCycle}" />
						</td>	
					</tr>
					<html:hidden property="workId" value="${work.id}" />
					</table>
				</c:forEach>
			
		</div>
		</td>
		</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			工作任务特殊说明
		</td>
		<td class="content" colspan="3">
			${pnrAgreementMainForm.workRemark}	
		</td>
	</tr>
	<tr>
		<td colspan="4">
		其他考核内容:
			<div id="evaDiv">
				
				<c:forEach var="eva" items="${evaList}" varStatus="stauts">
					<table class="formTable">
							<tr>
								<th colspan="4"><b>第${stauts.count} 项：</b></th>
							</tr>
						<tr><td> </td></tr>
						<tr>
							<td class="label" style="vertical-align:middle">
								考核开始时间
							</td>
							<td class="content">
								${eva.evaStartTimeStr}
								<html:hidden property="evaStartTime" value="${eva.evaStartTime}" />
							</td>
							<td class="label" style="vertical-align:middle">
								考核结束时间
							</td>
							<td class="content">
								${eva.evaEndTimeStr}
								<html:hidden property="evaEndTime" value="${eva.evaEndTime}" />			
							</td>			
						</tr>		
						<tr>	
							<td class="label" style="vertical-align:middle">
									考核指标名称
								</td>
								<td class="content">
									${eva.evaName}
								<html:hidden property="evaName" value="${eva.evaName}" />
							</td>			
							<td class="label" style="vertical-align:middle" >
								被考核人
							</td>
							<td class="content">
								${eva.toEvaUserName}
								<html:hidden property="toEvaUserName" value="${eva.toEvaUserName}" />
								<html:hidden property="toEvaUser" value="${eva.toEvaUser}" />
							</td>
						</tr>
						<tr>		
							<td class="label" style="vertical-align:middle">
								考核来源
							</td>
							<td class="content" >
									<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${eva.evaSource}" beanId="id2nameXML" />
								<html:hidden property="evaSource" value="${eva.evaSource}" />				
							</td>
							<td class="label" style="vertical-align:middle">
								算法分类
							</td>
							<td class="content">
								<eoms:dict key="dict-eva" dictId="algorithmType" itemId="${eva.evaAlgorithmType}" beanId="id2nameXML" />
							<html:hidden property="evaAlgorithmType" value="${eva.evaAlgorithmType}" />
							</td>					
						</tr>	
						<tr>
							<td class="label" style="vertical-align:middle">
								指标详细算法
							</td>
							<td class="content" colspan="3">
								${eva.evaContentByType}
							<html:hidden property="evaContent" value="${eva.evaContent}" />
							</td>						
						</tr>
						<tr>		
							<td class="label" style="vertical-align:middle">
								所占分数
							</td>
							<td class="content">
								${eva.evaWeight}
								<html:hidden property="evaWeight" value="${eva.evaWeight}" />
							</td>	
							<td class="label" style="vertical-align:middle">
								考核周期
							</td>
							<td class="content">
								<eoms:dict key="dict-eva" dictId="cycle" itemId="${eva.evaCycle}" beanId="id2nameXML" />
								<html:hidden property="evaCycle" value="${eva.evaCycle}" />
							</td>	
						</tr>
						<html:hidden property="evaId" value="${eva.id}" />

					</table>
				</c:forEach>
				
			</div>
		</td>
	</tr>
	<!--
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.settlingRule" />
		</td>
		<td class="content" colspan="3">
			${pnrAgreementMainForm.settlingRule}
		</td>
	</tr>
  -->

	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.remark" />
		</td>
		<td class="content" colspan="3">
			${pnrAgreementMainForm.remark}
		</td>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.accessories" />
		</td>
		<td class="content" colspan="3">
 			<eoms:attachment name="pnrAgreementMainForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="agreement"  viewFlag="Y"/> 
		</td>
	</tr>	
	<tr>
		<td class="label">
			确认结果			
		</td>
		<td class="content" colspan="3">
		       <INPUT type="radio" name="auditResult" value="2" checked="true">确认通过
		       <INPUT type="radio" name="auditResult" value="1"  onclick="edit()">进行修改
		</td>
		
	</tr>
	
	<tr>
      <td class="label">
        	确认意见
      </td>
      <td class="content" colspan="3">
      <!-- property中配一个空属性 -->
      		<textarea name="remark" maxLength="1000" rows="1" style="width:98%;" id="remark"></textarea>										
      </td>
    </tr>
    
	<html:hidden property="id" value="${id}" />
	</table>
        <input type="submit" id="subButton" value="提交"  class="button" />
</fmt:bundle>
</html:form>
</br>
	<table class="formTable">
		  	<caption>
					<a  onclick="javascript:showAuditTable();">服务协议确认信息</a>
			</caption>
	</table>
	<table class="formTable" id="auditTable" style="display:none;" >
			<c:forEach items="${auditList}" var="audit">
					<tr>
					<th colspan="4"><eoms:dict key="dict-partner-agreement" dictId="auditType" itemId="${audit.auditType}" beanId="id2nameXML" />&nbsp;</th>
					</tr>
			
					<tr>
						<td class="label">
							确认人
						</td>
						<td class="content">
							<c:if test="${audit.toOrgType=='user'}">
								<eoms:id2nameDB id="${audit.toOrgId}" beanId="tawSystemUserDao" />
							</c:if>
							<c:if test="${audit.toOrgType=='dept'}">
								<eoms:id2nameDB id="${audit.toOrgId}" beanId="tawSystemDeptDao" />	
							</c:if>			
						</td>	
		
						<td class="label">
							确认时间
						</td>
						<td class="content">
							<c:if test="${audit.operateTime!=null}">
								${audit.operateTimeString}
							</c:if>						
						</td>
					</tr>
					<tr>
						<td class="label">
								确认结果
						</td>
						<td class="content" colspan="3">
							<c:if test="${audit.auditResult==1}">
								确认返回
							</c:if>
							<c:if test="${audit.auditResult==2}">
								确认通过
							</c:if>	
							<c:if test="${audit.auditResult==''||audit.auditResult==null}">
								待确认
							</c:if>								
						</td>
					</tr>	
					<tr>
						<td class="label">
							确认说明
						</td>
						<td class="content" colspan="3">
							${audit.remark}
						</td>
					</tr>
			</c:forEach>
	</table>

<%@ include file="/common/footer_eoms.jsp"%>