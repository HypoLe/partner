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
<script type="text/javascript">
	function edit(){
		document.getElementById("subButton").disabled=true;
    	var url='${app}/eva/evaTemplateTemps.do?method=edit&evaTemplateId=${evaTemplateTemp.id}&confirmId=${confirmId}';
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
</script>
<html:form action="/evaTemplates.do?method=saveEvaTemplate&confirmId=${confirmId}&evaTemplateId=${evaTemplateTemp.id}&agrwor=${agrwor}&evaDeptNodeNodeId=${evaDeptNodeNodeId}&orgDeptId=${orgDeptId}" styleId="pnrTemplateForm" method="post"> 
<table class="formTable">
	<caption>
		<div class="header center">考核信息</div>
	</caption>
	<tr>
		<td class="label" width="25%">
			模板名称
		</td>
		<td class="content" width="75%">
			${evaTemplateTemp.templateName}
			<input type="hidden" id="templateName" name="templateName" value="${evaTemplateTemp.templateName}"/>
		</td>
		<c:choose>
		   <c:when test="${evaTemplateTemp.agrwor!='tempTask'}">
				<td class="label" width="25%">
					关联协议
				</td>
				<td class="content" width="25%">
				  		<eoms:id2nameDB id="${evaTemplateTemp.agreementId}" beanId="pnrAgreementMainDao" />
						<input type="hidden" id="agreementId" name="agreementId" value="${evaTemplateTemp.agreementId}"/>			
						<input type="hidden" id="contractId" name="contractId" value="${evaTemplateTemp.contractId}"/>
				</td>
		   </c:when>
		   <c:otherwise>
		   		<td class="label" width="25%">
					关联临时工作任务
				</td>
				<td class="content" width="25%">
				  		<eoms:id2nameDB id="${evaTemplateTemp.agreementId}" beanId="pnrTempTaskMainDao" />
						<input type="hidden" id="agreementId" name="agreementId" value="${evaTemplateTemp.agreementId}"/>				  		
				</td>
		   </c:otherwise>
	  	</c:choose>		
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			开始时间
		</td>
		<td class="content">
			${evaTemplateTemp.startTime}
			<input type="hidden" id="startTime" name="startTime" value="${evaTemplateTemp.startTime}"/>
		</td>
		<td class="label" style="vertical-align:middle">
			结束时间
		</td>
		<td class="content">
			${evaTemplateTemp.endTime}
			<input type="hidden" id="endTime" name="endTime" value="${evaTemplateTemp.endTime}"/>
		</td>	
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			${evaTemplateTemp.remark}
			<input type="hidden" id="remark" name="remark" value="${evaTemplateTemp.remark}"/>
		</td>
	</tr>
	<tr>
	<td colspan="4"  class="label">
	指标：
		<div id="kpiDiv">
				<c:forEach var="agreementEva" items="${evaKpiList}" varStatus="stauts">
					<div id="${agreementEva.id}">
						<table class="formTable">
							<tr>
								<td class="label" style="vertical-align:middle">
									考核指标开始时间
								</td>
								<td class="content">
									${agreementEva.evaStartTime}
									<input  id="evaStartTime" name="evaStartTime"  type="hidden"  value="${agreementEva.evaStartTime}" />
								</td>
								<td class="label" style="vertical-align:middle">
									考核指标结束时间
								</td>
								<td class="content">
									${agreementEva.evaEndTime}
									<input  id="evaEndTime" name="evaEndTime"  type="hidden"  value="${agreementEva.evaEndTime}" />
								</td>			
							</tr>	
							<tr>
								<td class="label" style="vertical-align:middle">
									指标名称
								</td>
								<td class="content">
									${agreementEva.kpiName}
									<input  id="kpiName" name="kpiName"  type="hidden"  value="${agreementEva.kpiName}" />
								</td>
								<td class="label" style="vertical-align:middle">
									被考核人
								</td>
								<td class="content">
									${agreementEva.toOrgUserName}
									<input  name="toOrgUserName" id="toOrgUserName"  type="hidden"   value="${agreementEva.toOrgUserName}" />
									<input  id="toOrgUser" name="toOrgUser"  type="hidden"  value="${agreementEva.toOrgUser}" />					
								</td>					
							</tr>
							<tr>
								<td class="label" style="vertical-align:middle">
									考核来源
								</td>
								<td class="content" >
										<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${agreementEva.evaSource}" beanId="id2nameXML" />
										<input  id="evaSource" name="evaSource"  type="hidden"  value="${agreementEva.evaSource}" />
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
									${agreementEva.evaContentByType}		
									<input  type="hidden"  id="algorithm" name="algorithm"   value="${agreementEva.algorithm}" />			
								</td>
							</tr>
							<tr>
								<td class="label" style="vertical-align:middle">
									权重
								</td>
								<td class="content">
									${agreementEva.weight}
									<input  id="weight" name="weight" type="hidden"  value="${agreementEva.weight}" />
								</td>
								<td class="label" style="vertical-align:middle">
									考核指标周期
								</td class="content">
								<td>
									<eoms:dict key="dict-eva" dictId="cycle" itemId="${agreementEva.cycle}" beanId="id2nameXML" />
									<input  id="cycle" name="cycle"  type="hidden"  value="${agreementEva.cycle}" />
								</td>					
							</tr>	
								<html:hidden property="evaId" value="${agreementEva.id}" />	
							<tr>
								<td class="label" style="vertical-align:middle">
									备注
								</td>
								<td colspan="3" class="content">
									${agreementEva.remark}
									<input  id="kpiRemark" name="kpiRemark"  type="hidden"  value="${agreementEva.remark}" />
								</td>
							</tr>
							<html:hidden property="agreementWorkId" value="${agreementEva.agreementWorkId}" />
						</table>
					</div>	
				</c:forEach>
		</div>
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
    
	<html:hidden property="confirmId" value="${confirmId}" />
<table>
	<tr>
		<td>
			<input type="submit" class="btn" id="subButton" value="提交" />
		</td>
	</tr>
</table>
</html:form>
</br>
	<table class="formTable">
		  	<caption>
					<a  onclick="javascript:showAuditTable();">考核确认信息</a>
			</caption>
	</table>
	<table class="formTable" id="auditTable" style="display:none;" >
			<c:forEach items="${confirmList}" var="audit" varStatus="stauts">
					<tr>
					<th colspan="4">第${stauts.count}项&nbsp;</th>
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
							<c:if test="${audit.confirmResult==1}">
								确认返回
							</c:if>
							<c:if test="${audit.confirmResult==2}">
								确认通过
							</c:if>	
							<c:if test="${audit.confirmResult==''||audit.confirmResult==null}">
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