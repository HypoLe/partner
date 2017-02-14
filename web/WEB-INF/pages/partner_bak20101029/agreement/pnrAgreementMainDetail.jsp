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
	<tr>
	
		<td class="label" style="vertical-align:middle">
			工作计划编号
		</td>
		<td class="content">
		<c:if test="${pnrAgreementMainForm.workplanId ==null }">
			<c:if test="${pnrAgreementMainForm.partyBUser == paruserId && pnrAgreementMainForm.state!=0 && pnrAgreementMainForm.state!=1}">
							<input type="button" class="btn" value="新增工作计划" 
					onclick="window.open('${app}/partner/workplan/pnrWorkplanMains.do?method=edit&agreementId=${pnrAgreementMainForm.id}&agreementNO=${pnrAgreementMainForm.agreementNO}&partyAId=${pnrAgreementMainForm.partyAId}')"/>
			</c:if>
			<c:if test="${pnrAgreementMainForm.partyBUser != paruserId}">
				<c:if test="${pnrAgreementMainForm.state==0 || pnrAgreementMainForm.state==1}">
							<input type="button" class="btn" value="新增工作计划" 
					onclick="window.open('${app}/partner/workplan/pnrWorkplanMains.do?method=edit&agreementId=${pnrAgreementMainForm.id}&agreementNO=${pnrAgreementMainForm.agreementNO}&partyAId=${pnrAgreementMainForm.partyAId}')" disabled="disabled"/>
				</c:if>
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
			<c:if test="${pnrAgreementMainForm.partyAUser == paruserId && pnrAgreementMainForm.state!=0 && pnrAgreementMainForm.state!=1}">
							<input type="button" class="btn" value="新增考核任务" 
					onclick="window.open('${app}/eva/evaTemplates.do?method=newTemplateFromAgree&agreementId=${pnrAgreementMainForm.id}&agrwor=agreement&partner=${parDeptId}&evaDeptId=${evaDeptId}')"/>
			</c:if>
			<c:if test="${pnrAgreementMainForm.partyBUser != paruserId }">
					<c:if test="${pnrAgreementMainForm.state==0 || pnrAgreementMainForm.state==1}">
							<input type="button" class="btn" value="新增考核任务" 
					onclick="window.open('${app}/eva/evaTemplates.do?method=newTemplateFromAgree&agreementId=${pnrAgreementMainForm.id}&agrwor=agreement&partner=${parDeptId}&evaDeptId=${evaDeptId}')"  disabled="disabled"/>
					</c:if>
			</c:if>			
		</c:if>
		<c:if test="${pnrAgreementMainForm.evaTemplateId !=null }">
		<a href="${app}/eva/evaReportInfos.do?method=getAllScroe&templateId=${pnrAgreementMainForm.evaTemplateId}" target="_blank">
			<eoms:id2nameDB id="${pnrAgreementMainForm.evaTemplateId}" beanId="evaTemplateDao" />
		</a>
		</c:if>
		</td>
	</tr>

	<tr>
	<td colspan="4">
	<div id="workDiv">
	服务工作任务及考核要求:
	<table class="formTable">
	<c:forEach var="work" items="${workList}" varStatus="stauts">
			
	<tr>
			<th colspan="4"><b>第${stauts.count} 项：</b></th>
	</tr>
			<tr>		
				<td class="label" style="vertical-align:middle" >
					<fmt:message key="pnrAgreementWork.workContent" />
				</td>
				<td class="content">
					${work.workContent}
				</td>
				<td class="label" style="vertical-align:middle" >
					工作完成标准
				</td>
				<td class="content">
					${work.workStandard}
				</td>				
			</tr>
			<tr>		
				<td class="label" style="vertical-align:middle">
					工作任务类型
				</td>
				<td class="content" >
						<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${work.workType}" beanId="id2nameXML" />					
				</td>
				<td class="label" style="vertical-align:middle">
					工作任务执行周期
				</td>
				<td class="content">
					<eoms:dict key="dict-partner-agreement" dictId="workCycle" itemId="${work.workCycle}" beanId="id2nameXML" />			
				</td>	
			</tr>			
			<!-- 
			<tr>
				<td class="label" style="vertical-align:middle">
					<fmt:message key="pnrAgreementWork.startTime" />
				</td>
				<td class="content">
					${work.startTimeStr}
				</td>
				<td class="label" style="vertical-align:middle">
					<fmt:message key="pnrAgreementWork.endTime" />
				</td>
				<td class="content">
					${work.endTimeStr}
				</td>	
			</tr>
			 -->
			<tr>		
					<td class="label" style="vertical-align:middle">
						考核指标名称
					</td>
					<td class="content">
						${work.workEvaName}
					</td>	
					<td class="label" style="vertical-align:middle">
						指标详细算法
					</td>
					<td class="content">
						${work.workEvaContent}		
					</td>						
			</tr>
			<tr>
				<td class="label" style="vertical-align:middle">
					考核开始时间
				</td>
				<td class="content">
					${work.workEvaStartTimeStr}
				</td>
				<td class="label" style="vertical-align:middle">
					考核结束时间
				</td>
				<td class="content">
					${work.workEvaEndTimeStr}				
				</td>			
			</tr>
			<tr>		
				<td class="label" style="vertical-align:middle">
					所占分数
				</td>
				<td class="content">
					${work.workEvaWeight}
				</td>	
				<td class="label" style="vertical-align:middle">
					考核周期
				</td>
				<td class="content">
					<eoms:dict key="dict-eva" dictId="cycle" itemId="${work.workEvaCycle}" beanId="id2nameXML" />			
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
			<tr>
					<td class="label" style="vertical-align:middle">
						考核指标名称
					</td>
					<td class="content">
						${eva.evaName}
					</td>		
					<td class="label" style="vertical-align:middle">
						指标详细算法
					</td>
					<td class="content">
						${eva.evaContent}		
						<html:hidden property="evaId" value="${eva.id}" />
					</td>						
			</tr>
		    <tr>	
				<td class="label" style="vertical-align:middle">
					考核来源
				</td>
				<td class="content"  colspan="3">
						<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${eva.evaSource}" beanId="id2nameXML" />							
				</td>						
			</tr>
			<tr>
				<td class="label" style="vertical-align:middle">
					考核开始时间
				</td>
				<td class="content">
					${eva.evaStartTimeStr}
				</td>
				<td class="label" style="vertical-align:middle">
					考核结束时间
				</td>
				<td class="content">
					${eva.evaEndTimeStr}				
				</td>			
			</tr>			
			<tr>	
				<td class="label" style="vertical-align:middle">
					所占分数
				</td>
				<td class="content">
					${eva.evaWeight}
				</td>	
				<td class="label" style="vertical-align:middle">
					考核周期
				</td>
				<td class="content">
					<eoms:dict key="dict-eva" dictId="cycle" itemId="${eva.evaCycle}" beanId="id2nameXML" />			
				</td>						
			</tr>
	</c:forEach>
	</table>
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
    </table>
    </c:if>
  </div>
</div>
<c:if test="${pnrAgreementMainForm.partyAUser == paruserId && (pnrAgreementMainForm.state=='2' || pnrAgreementMainForm.state=='5')&&pnrAgreementMainForm.evaTemplateId !=null }">
	<table>
		<tr>
			<td  style="text-align:right;"  colspan="4" >
				<input type="button" class="btn" value="关闭协议" 
					onclick="window.open('${app}/partner/agreement/pnrAgreementMains.do?method=closeAgreement&agreementId=${pnrAgreementMainForm.id}&partner=10101');"/>
			</td>
		</tr>
	</table>
</c:if>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>