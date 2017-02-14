<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<div class="header center">
	<b>申请结果查询
</div>
<br>
<div >
	<logic:notEmpty name="resultList" scope="request">
	<display:table name="resultList" cellspacing="0" cellpadding="0" 		id="resultList" pagesize="${pageSize}"
			class="table resultList" export="false" 
			requestURI="${app}/partner/process/process.do?method=goToApplyResultPage" sort="list"
			partialList="true" size="${resultSize}">
		<%--<display:column  sortable="true" headerClass="sortable" title="序号">${resultList[0].id2}
		</display:column>
		--%><%--
		<display:column sortable="true" headerClass="sortable" title="附件名称">${resultList[0].changeAttachment}
		</display:column>
		--%>
		<display:column sortable="true" headerClass="sortable" title="申请时间">${resultList[0].startTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审批时间">${resultList[0].endTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审批人员">
			<eoms:id2nameDB  id="${resultList[1].dataSender}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审批意见" maxLength="18">${resultList[1].reason}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请变更类型">
			<eoms:id2nameDB id="${resultList[0].changeType}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请模块">
			<eoms:id2nameDB id="${resultList[0].referenceModel}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核结果">
				<eoms:id2nameDB id="${resultList[0].currentState}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<%--<display:column sortable="true" headerClass="sortable" title="重申">
			<a href="${app}/partner/process/process.do?method=applyAgain&idMain=${resultList[0].id}&idLink=${resultList[1].id}&dictId=${dictId}">go</a>
		</display:column>
		--%>
		<display:column sortable="true" headerClass="sortable" title="终止">
			<a  href="javascript:void(0);" onclick="applyEnd('${resultList[0].id}')">终止</a>
		</display:column>
	</display:table>
	</logic:notEmpty> 
	<logic:empty name="resultList" scope="request">
	没有记录！
	</logic:empty> 
</div>
<script type="text/javascript">
function applyEnd(id){
	if(confirm("确定终止该申请么?")){
		Ext.Ajax.request({
			url:"${app}/partner/process/process.do",
			params:{method:"applyEnd",idMain:id},
			success:function(response,opt){
				Ext.MessageBox.alert("提示信息",response.responseText,function(){window.location.reload});
			},
			failure:function(response,opt){
				Ext.MessageBox.alert("提示信息","请求异常",function(){window.location.reload});
			}
		});
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>