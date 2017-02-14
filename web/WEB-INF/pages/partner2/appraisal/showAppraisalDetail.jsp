<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<style type="text/css">
#toAuditOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
<script type="text/javascript">
</script>

<form action="${app}/partner2/appraisal.do?method=goToAppraisalNextStep&flag=passAudit" method="post" id="appForm">
<table class="listTable" id="list-table">
	<caption>
		<div style="text-align:right"  id='totalScore'>
			总分：${appraisalTotalScore}
		</div>
	</caption>
	<thead>
	<tr>
		<td>
			考核指标
		</td>
		<td>
			算法描述
		</td>
		<td>
			评分
		</td>
		<td>
			实际得分
		</td>
		<td>
			增扣分原因
		</td>
		<td>
			备注
		</td>
	</tr>
	</thead>
	<c:forEach var="a2a" items="${proxyScaleWithAppraisalList}">
		<tr>
			<td class="label" style="vertical-align:middle;text-align:left">
				${a2a.appraisalObjectName}(${a2a.appraiSalScore})
			</td>
			
			<td>
				${a2a.appraiSalScore}/100*评分
			</td>
				
			<td id="score_${a2a.id}">
				${a2a.appraisalRealScore}
			</td>
				
			<td>
				${a2a.appraisalScoreByWeight}
			</td>
			
			<td>
				${a2a.deductionStandard}
			</td>
			<td>
				${a2a.remark}
			</td>
		</tr>
	</c:forEach>
</table>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff">
<table class="formTable" width="75%">
	<tr>
		<td>	
			<input type="submit" class="btn" value="提交" />
			<c:if test="${currentUserId == auditUserId}">
				请选择代维公司确认人员*：<input type="text" name="monitorCompany_i" id="monitorCompany_i"  class="text" alt="allowBlank:false" readonly="readonly"/>
			 	<eoms:xbox id="monitorCompanyTree" dataUrl="${app}/xtree.do?method=userFromDept"  
					rootId="${monitorCompanyTreeId}" rootText="${monitorCompanyName}"  valueField="iUser2" handler="monitorCompany_i" 
					textField="monitorCompany_i" checktype="user" single="true">
				</eoms:xbox>
				<input type="hidden" id="iUser2" name="iUser2" value=""/>
			</c:if>
		</td>
		<td>
			<input type="button" class="btn" value="驳回" onclick="reject();" />
			(驳回给提交者:<font color="red">${rejectUser}</font>重新考核打分)
		</td>
	</tr>
</table>
</div>
<input type="hidden" value="${proxyScaleId}" name="proxyScaleId" />
</form>
	
<script type="text/javascript">
 var myJ=$.noConflict();

 function reject(){
 	myJ('form#appForm').attr("action","${app}/partner2/appraisal.do?method=reject");
 	myJ('form#appForm').submit();
 }
 
 var v = new eoms.form.Validation({form:"appForm"});
myJ('#monitorCompany').bind('change',function(event){
	v.validate(document.forms[0].monitorCompany_i);
});

</script>
<%@ include file="/common/footer_eoms.jsp"%>