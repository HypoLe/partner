<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="com.boco.eoms.partner.agreement.model.PnrAgreementAudit"%>
<%@ page import="com.boco.eoms.partner.agreement.model.PnrAgreementLastScore"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String auditTime = "";

List lastScoreList = (List)request.getAttribute("lastScoreList");
%>
<script type="text/javascript">
	Ext.onReady(function(){
			var state = ${pnrAgreementMainForm.state};
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('plan-info', '服务协议信息 ');
        	tabs.addTab('audit-info', '服务协议确认及审核信息 ');
        	tabs.addTab('comment-info', '综合评价信息 ');
        	if(state!=4){
        		tabs.hideTab('comment-info');
        	}
    		tabs.activate(0);
	});
	
	function getContractUrl(){
	 window.open ('${app}/partner/agreement/pnrAgreementMains.do?method=queryContractDoAgr&id=${pnrAgreementMainForm.id}','newwindow','height=600,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
    }	
	function getContractUrlNew(){
	 window.open ('${app}/partner/feeInfo/pnrFeeInfoMains.do?method=edit&contractNew=contractNew&agreementId=${pnrAgreementMainForm.id}');
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
			<eoms:id2nameDB id="${pnrAgreementMainForm.partyAUser}" beanId="tawSystemUserDao" />
		</td>
		<td class="label" style="vertical-align:middle">
			乙方负责人&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrAgreementMainForm.partyBUser}" beanId="tawSystemUserDao" />
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
			<!-- 
			<c:if test="${pnrAgreementMainForm.compactNo ==null||pnrAgreementMainForm.compactNo==''}">
				<c:if test="${pnrAgreementMainForm.state==2}">
					<c:if test="${pnrAgreementMainForm.partyAUser == paruserId||pnrAgreementMainForm.partyBUser == paruserId}">
					<input type="button" name="contractUrl" id="contractUrl" value="关联已有合同" class="btn" onclick="getContractUrl();"/>
					<input type="button" name="contractUrlNew" id="contractUrlNew" value="新增关联合同" class="btn" onclick="getContractUrlNew();"/>
					</c:if>
					<c:if test="${pnrAgreementMainForm.partyAUser != paruserId&&pnrAgreementMainForm.partyBUser != paruserId}">
					<input type="button" name="contractUrl" id="contractUrl" value="关联已有合同" class="btn" onclick="getContractUrl();"  disabled="disabled"/>
					<input type="button" name="contractUrlNew" id="contractUrlNew" value="新增关联合同" class="btn" onclick="getContractUrlNew();"  disabled="disabled"/>
					</c:if>					
				</c:if>
				<c:if test="${pnrAgreementMainForm.state!=2}">
					<input type="button" name="contractUrl" id="contractUrl" value="关联已有合同" class="btn" onclick="getContractUrl();"  disabled="disabled"/>
					<input type="button" name="contractUrlNew" id="contractUrlNew" value="新增关联合同" class="btn" onclick="getContractUrlNew();"  disabled="disabled"/>
				</c:if>
			</c:if>
			 -->
			<c:if test="${pnrAgreementMainForm.compactNo ==null||pnrAgreementMainForm.compactNo==''}">
					<c:if test="${pnrAgreementMainForm.partyAUser == paruserId||pnrAgreementMainForm.partyBUser == paruserId}">
					<input type="button" name="contractUrl" id="contractUrl" value="关联已有合同" class="btn" onclick="getContractUrl();"/>
					<input type="button" name="contractUrlNew" id="contractUrlNew" value="新增关联合同" class="btn" onclick="getContractUrlNew();"/>
					</c:if>
			</c:if>			 
				<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=${pnrAgreementMainForm.contentId}" target="_blank">
				${pnrAgreementMainForm.compactNo}</a>
		</td>
		<td class="label" style="vertical-align:middle">
			服务类型
		</td>
		<td class="content">
			<input id="serverType" type="hidden" value="${pnrAgreementMainForm.serverType}"/>
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
<c:if test="${pnrAgreementMainForm.evaTemplateId !=null }">
	<tr>
	
		<td class="label" style="vertical-align:middle">
			工作计划编号
		</td>
		<td class="content">
			<c:if test="${pnrAgreementMainForm.workplanId !=null }">
					<a href="${app}/partner/workplan/pnrWorkplanMains.do?method=detail&id=${pnrAgreementMainForm.workplanId }" target="_blank">
				${pnrAgreementMainForm.workplanNO}</a>
				
			</c:if>
		</td>
		<td class="label" style="vertical-align:middle">
			考核模板名称
		</td>
		<td class="content">
		<c:if test="${pnrAgreementMainForm.evaTemplateId !=null }">
			<c:if test="${unName=='未知名称'}">
				此模板正在确认过程中
			</c:if>
			<c:if test="${unName!='未知名称'}">
				<a href="${app}/eva/evaReportInfos.do?method=getAllScroe&templateId=${pnrAgreementMainForm.evaTemplateId}" target="_blank">
					<eoms:id2nameDB id="${pnrAgreementMainForm.evaTemplateId}" beanId="evaTemplateDao" />
				</a>
			</c:if>
			</c:if>
		</td>
	</tr>
</c:if>
	<tr>
	<td colspan="4">
	<div id="workDiv">
	服务工作任务及考核要求:
	<table class="formTable">
	<c:forEach var="work" items="${workList}" varStatus="stauts">
	<tr>
						<th colspan="4"><b>第${stauts.count} 项：</b></th>
					</tr>	
			
						<tr><td> </td></tr>
					<tr>		
						<td class="label" style="vertical-align:middle" >
							工作内容
						</td>
						<c:if test="${pnrAgreementMainForm.serverType =='Purchase'}">
						<td class="content" colspan="3">
							${work.workContent}
							<html:hidden property="workContent" value="${work.workContent}" />
						</td>
						<td id="tdtoOrgUser1" class="label" style="vertical-align:middle" >
							执行人
						</td>
						<td id="tdtoOrgUser2" class="content">
								${work.toOrgUserName}
							<html:hidden property="toOrgUserName" value="${work.toOrgUserName}" />
							<html:hidden property="toOrgUser" value="${work.toOrgUser}" />
						</td>
						</c:if>
						<c:if test="${pnrAgreementMainForm.serverType !='Purchase'}">
						<td class="content">
							${work.workContent}
							<html:hidden property="workContent" value="${work.workContent}" />
						</td>
						<td id="tdtoOrgUser1" class="label" style="vertical-align:middle" >
							执行人
						</td>
						<td id="tdtoOrgUser2" class="content">
								${work.toOrgUserName}
							<html:hidden property="toOrgUserName" value="${work.toOrgUserName}" />
							<html:hidden property="toOrgUser" value="${work.toOrgUser}" />
						</td>
						</c:if>
						
						
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
						<td id="tdworkType1" class="label" style="vertical-align:middle">
							工作任务类型
						</td>
						<td id="tdworkType2" class="content" >
								<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${work.workType}" beanId="id2nameXML" />
							<html:hidden property="workType" value="${work.workType}" />				
						</td>
						<td id="tdworkCycle1" class="label" style="vertical-align:middle">
							工作任务执行周期
						</td>
						<td id="tdworkCycle2" class="content">
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
							<td id="tdalgorithmType1" class="label" style="vertical-align:middle">
								算法分类
							</td>
							<td id="tdalgorithmType2" class="content">
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
						<td id="tdworkEvaStartTime1" class="label" style="vertical-align:middle">
							考核开始时间
						</td>
						<td id="tdworkEvaStartTime2" class="content">
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
						<td id="tdworkEvaCycle1" class="label" style="vertical-align:middle">
							考核周期
						</td>
						<td id="tdworkEvaCycle2" class="content">
							<eoms:dict key="dict-eva" dictId="cycle" itemId="${work.workEvaCycle}" beanId="id2nameXML" />
							<html:hidden property="workEvaCycle" value="${work.workEvaCycle}" />
						</td>	
					</tr>
					<html:hidden property="workId" value="${work.id}" />
	</c:forEach>
	</table>
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
	<table class="formTable">
	<c:forEach var="eva" items="${evaList}" varStatus="stauts">
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
	</c:forEach>
	</table>
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
</table>

<a href="${app}/partner/baseinfo/pnrFlow.do?method=showFlow&id=${pnrAgreementMainForm.id}&flowType=agreement" target="_blank">
<img align=left height="15px"  width="65px"  src="${app}/images/icons/showflow.gif" alt="查看流程图" />			
</a>
  </div>
  <div id="audit-info" class="tabContent">

  <table class="formTable">
  			<caption>
				<div class="header center">
					服务协议确认及审核信息
				</div>
			</caption>
			  <%
  	List auditList = (List)request.getAttribute("auditList");
  PnrAgreementAudit pnrAgreementAudit = null;
  for(int i=0;i<auditList.size();i++){
	  pnrAgreementAudit = (PnrAgreementAudit)auditList.get(i);
  %>
			<tr>
			<th colspan="4"><eoms:dict key="dict-partner-agreement" dictId="auditType" itemId="<%=pnrAgreementAudit.getAuditType() %>" beanId="id2nameXML" />&nbsp;</th>
			</tr>
				<%
				if("new".equals(pnrAgreementAudit.getAuditType())){
				%>	
					<tr>
					<td class="label">
						确认人
					</td>
					<td class="content">
					<%
					if("user".equals(pnrAgreementAudit.getToOrgType())){
					%>
						<eoms:id2nameDB id="<%=pnrAgreementAudit.getToOrgId() %>" beanId="tawSystemUserDao" />	
					<%
					}else if("dept".equals(pnrAgreementAudit.getToOrgType())){
					%>
						<eoms:id2nameDB id="<%=pnrAgreementAudit.getToOrgId() %>" beanId="tawSystemDeptDao" />
					<%
					}
					%>
					</td>
	
					<td class="label">
						确认时间
					</td>
					<td class="content">
					<%
					if(pnrAgreementAudit.getOperateTime()!=null){
						auditTime = dateFormat.format(pnrAgreementAudit.getOperateTime());
					}else{
						auditTime = "";
					}
					%>
						<%=auditTime %>
							
						</td>
					</tr>
					<tr>
					<td class="label">
							确认结果
						</td>
						<td class="content" colspan="3">
						
						<%
						if("1".equals(pnrAgreementAudit.getAuditResult())){
						%>
							确认返回
						<%
						}else if("2".equals(pnrAgreementAudit.getAuditResult())){
						%>
						    确认通过
						<%
						}else{
						%>
							待确认
						<%
						}
						%>
						</td>
					</tr>
					<%
						if(pnrAgreementAudit.getRemark() != null){
					%>
					<tr>
						<td class="label">
							确认说明
						</td>
						<td class="content" colspan="3"s>
							<%=pnrAgreementAudit.getRemark() %>
						</td>
					</tr>
				<%		 }
					} else {
				%>				
				<tr>
					<td class="label">
						审核者
					</td>
					<td class="content">
					<%
					if("user".equals(pnrAgreementAudit.getToOrgType())){
					%>
						<eoms:id2nameDB id="<%=pnrAgreementAudit.getToOrgId() %>" beanId="tawSystemUserDao" />	
					<%
					}else if("dept".equals(pnrAgreementAudit.getToOrgType())){
					%>
						<eoms:id2nameDB id="<%=pnrAgreementAudit.getToOrgId() %>" beanId="tawSystemDeptDao" />
					<%
					}
					%>
					</td>
					<td class="label">
						审核时间
					</td>
					<td class="content">
					<%
					if(pnrAgreementAudit.getOperateTime()!=null){
						auditTime = dateFormat.format(pnrAgreementAudit.getOperateTime());
					}else{
						auditTime = "";
					}
					%>
						<%=auditTime %>
						
					</td>
				</tr>
				<tr>
					<td class="label">
						审核结果
					</td>
					<td class="content" colspan="3">
					
					<%
					if("1".equals(pnrAgreementAudit.getAuditResult())){
					%>
						驳回
					<%
					}else if("2".equals(pnrAgreementAudit.getAuditResult())){
					%>
					    通过
					<%
					}else{
					%>
						待审核
					<%
					}
					%>
					</td>
				</tr>
				<%
					if(pnrAgreementAudit.getRemark() != null){
				%>
				<tr>
					<td class="label">
						审核说明
					</td>
					<td class="content" colspan="3"s>
						<%=pnrAgreementAudit.getRemark() %>
					</td>
				</tr>
			<%		}
				}
 			 }
			%>
	</table>
	<br>
  </div>
  <div id="comment-info" class="tabContent">
  	<c:if test="${pnrAgreementMainForm.satisfy != null}">
    <table class="formTable">
    	<caption>
		<div class="header center">综合评价信息</div>
	</caption>
	<tr>
	<td class="label" >考核得分</td>
		<td class="label" colspan="8">
	<table class="formTable">
	<%
	PnrAgreementLastScore lastScore = new PnrAgreementLastScore();
	float score = 0;
	float scoreWeight = 0;
	String evaType = "";
	for(int i=0;i<lastScoreList.size();i++){
		lastScore = (PnrAgreementLastScore)lastScoreList.get(i);
		if("eva".equals(lastScore.getEvaType())){
			if(!evaType.equals(lastScore.getEvaType())){
				score = 0;
%>
				<tr><td colspan="8" class="label" >原始考核项:（总分：${allEvaScore }分， 整体权重：<%=lastScore.getEvaWeight() %>%）</td></tr>
				
<%
			}
			//score = score + Float.parseFloat(lastScore.getEvaScore())*Float.parseFloat(lastScore.getEvaWeight())/100;
			
%>
	
			<tr>
					<td class="label" >
						考核项
					</td>
					<td>
					<%=lastScore.getEvaName() %>
					</td>
					<td class="label">
						最终分数
					</td>
					<td >
					<%=lastScore.getEvaScore() %>
					</td>								
					<td class="label" >
						打分说明
					</td>
					<td>
					<%=lastScore.getEvaContent() %>
					</td>	
					<td class="label" >
						权重
					</td>
					<td>
						<%=lastScore.getEvaWeight() %>%
					</td>		
			</tr>
<%
		}else{
			if(!evaType.equals(lastScore.getEvaType())){
%>
				<tr><td colspan="8" class="label" >增加考核项:</td></tr>
<%
			}
%>

				<tr>
					<td class="label">
						考核项
					</td>
					<td>
					<%=lastScore.getEvaName() %>
					</td>	
					<td class="label">
						总分
					</td>
					<td>
					<%=lastScore.getEvaScore() %>
					</td>
					<td class="label">
						打分说明
					</td>
					<td>
					<%=lastScore.getEvaContent() %>
					</td>			
					<td class="label">
						权重
					</td>
					<td>
						<%=lastScore.getEvaWeight() %>%
					</td>		
			</tr>
	
	<%
	}
	%>
		
	<%
	score = score + Float.parseFloat(lastScore.getEvaScore())*Float.parseFloat(lastScore.getEvaWeight())/100;
	evaType = lastScore.getEvaType();
	}
	%>
			</table>
	</td>
	</tr>
	<tr>
      <td class="label">
        	总分
      </td>
      <td class="content" colspan="8">
      ${pnrAgreementMainForm.lastScore}分
      </td>
    </tr>
	<tr>
      <td class="label">
        	满意度
      </td>
      <td class="content" colspan="8">
      <!-- property中配一个空属性 -->
		<eoms:dict key="dict-partner-agreement" dictId="satisfy" itemId="${pnrAgreementMainForm.satisfy}" beanId="id2nameXML" />			
      </td>
    </tr>
	<tr>
      <td class="label">
        	综合评价
      </td>
      <td class="content" colspan="8">
      <!-- property中配一个空属性 -->
      		${pnrAgreementMainForm.assessment}									
      </td>
    </tr>
    </table>
    </c:if>
  </div>
</div>

</fmt:bundle>

<script type="text/javascript">
  var serverType = document.getElementById('serverType').value;
  //如果是采购类型隐藏部分表单
  if(serverType == 'Purchase'){
    eoms.$("tdtoOrgUser1").hide();
	eoms.$("tdtoOrgUser2").hide();
	//eoms.$("tdworkCycle1").hide();
	//eoms.$("tdworkCycle2").hide();
	//eoms.$("tdworkType1").hide();
	//eoms.$("tdworkType2").hide();
	//eoms.$("tdworkEvaStartTime1").hide();
	//eoms.$("tdworkEvaStartTime2").hide();
	//eoms.$("tdalgorithmType1").hide();
	//eoms.$("tdalgorithmType2").hide();
	//eoms.$("tdworkEvaCycle1").hide();
	//eoms.$("tdworkEvaCycle2").hide();
  };
</script>
<%@ include file="/common/footer_eoms.jsp"%>