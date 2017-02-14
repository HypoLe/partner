<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('plan-info', '服务协议信息 ');
        	tabs.addTab('audit-info', '协议关闭审核 ');
    		tabs.activate(1);
	});
	function getContractUrl(){
	 window.open ('${app}/partner/agreement/pnrAgreementMains.do?method=queryContractDo','newwindow','height=600,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
    }
    function setCompactNostr(obj){
    	document.getElementById("compactNostr").innerHTML=obj;
    }
    
</script>
<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">
<div id="info-page">
   <div id="plan-info" class="tabContent">
<table class="formTable">
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
			${pnrAgreementMainForm.startTime}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.endTime" />
		</td>
		<td class="content">
			${pnrAgreementMainForm.endTime}
		</td>
	</tr>
	<tr>
		
		<td class="label" style="vertical-align:middle">
			合同编号
		</td>
		<td class="content"  colspan="3">
			<html:hidden property="compactName" value="${pnrAgreementMainForm.compactName}"/>
			<html:hidden property="contentId" value="${pnrAgreementMainForm.contentId}"/>
			<html:hidden property="compactNo" value="${pnrAgreementMainForm.compactNo}"/>
			<c:if test="${pnrAgreementMainForm.compactNo ==null||pnrAgreementMainForm.compactNo==''}">
				<c:if test="${pnrAgreementMainForm.state==2}">
					<input type="button" name="contractUrl" id="contractUrl" value="选择合同" class="btn" onclick="getContractUrl();"/>
				</c:if>
				<c:if test="${pnrAgreementMainForm.state!=2}">
					<input type="button" name="contractUrl" id="contractUrl" value="选择合同" class="btn" onclick="getContractUrl();"  disabled="disabled"/>
				</c:if>
			</c:if>
				<a href="${app}/partner/contract/ctContentss.do?method=detailAgr&id=${pnrAgreementMainForm.contentId}" target="_blank">
				${pnrAgreementMainForm.compactNo}</a>
		</td>
		
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.specialCase" />
		</td>
		<td class="content" colspan="3">
			${pnrAgreementMainForm.specialCase}
		</td>
	</tr>	
	<c:if test="${pnrAgreementMainForm.state!=0||pnrAgreementMainForm.state!=1}">
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
					onclick="window.open('${app}/eva/evaTemplates.do?method=newTemplateFromAgree&agreementId=${pnrAgreementMainForm.id}&agrwor=agreement&partner=${parDeptId}&evaDeptId=${evaDeptId}')"/>
			</c:if>
			<c:if test="${pnrAgreementMainForm.partyAUser != paruserId }">
							<input type="button" class="btn" value="新增考核任务" 
					onclick="window.open('${app}/eva/evaTemplates.do?method=newTemplateFromAgree&agreementId=${pnrAgreementMainForm.id}&agrwor=agreement&partner=${parDeptId}&evaDeptId=${evaDeptId}')"  disabled="disabled"/>
			</c:if>			
		</c:if>
		<c:if test="${pnrAgreementMainForm.evaTemplateId !=null }">
		<a href="${app}/eva/evaReportInfos.do?method=getAllScroe&templateId=${pnrAgreementMainForm.evaTemplateId}" target="_blank">
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
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.settlingRule" />
		</td>
		<td class="content" colspan="3">
			${pnrAgreementMainForm.settlingRule}
		</td>
	</tr>


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
</table>

  </div>
  <div id="audit-info" class="tabContent">
  <html:form action="pnrAgreementAudits.do?method=auditDoForClose" styleId="pnrAgreementAuditForm" method="post">
  
  <table class="formTable">
  	<caption>
		<div class="header center">审核信息</div>
	</caption>
	<tr>
	<td class="label" >考核得分</td>
		<td class="label" colspan="3">
	<c:forEach var="lastScore" items="${lastScoreList}">
	
	<table class="formTable">
			<tr>
					<td class="label" style="vertical-align:middle">
						考核项
					</td>
					<td class="content">
						${lastScore.evaName}
					</td>			
					<td class="label" style="vertical-align:middle">
						最终分数
					</td>
					<td class="content">
						${lastScore.evaScore}
					</td>		
			</tr>
		</table>
	
	
	</c:forEach>
	</td>
	</tr>
	<tr>
      <td class="label">
        	满意度
      </td>
      <td class="content" colspan="3">
      <!-- property中配一个空属性 -->
		<eoms:dict key="dict-partner-agreement" dictId="satisfy" itemId="${pnrAgreementMainForm.satisfy}" beanId="id2nameXML" />			
      </td>
    </tr>
	<tr>
      <td class="label">
        	综合评价
      </td>
      <td class="content" colspan="3">
      <!-- property中配一个空属性 -->
      		${pnrAgreementMainForm.assessment}									
      </td>
    </tr>
	<tr>
		<td class="label">
			审核结果			
		</td>
		<td class="content" colspan="3">
		       <INPUT type="radio" name="auditResult" value="2" checked="true">通过，可以关闭
		       <INPUT type="radio" name="auditResult" value="1">不通过，不能关闭
		</td>
		<tr>
	</tr>
	
	
      <td class="label">
        	审核意见
      </td>
      <td class="content" colspan="3">
      <!-- property中配一个空属性 -->
      		<textarea name="remark" maxLength="1000" rows="2" style="width:98%;" id="remark"></textarea>										
      </td>
    </tr>
    
	<html:hidden property="id" value="${id}" />
	<html:hidden property="agreementId" value="${pnrAgreementMainForm.id}" />
	</table>
        <input type="submit" value="提交"  class="button" />
</html:form>
  </div>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>