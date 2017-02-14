<div align="right"><%@include file="/common/taglibs.jsp"%> 
<%@include file="/common/header_eoms_form.jsp"%> 
<%@page language="java" pageEncoding="UTF-8"%> 
</div><div class="header center">
	<b>已归档查询
</div>
<br>
<div >
	<logic:notEmpty name="resultList" scope="request">
	<display:table name="resultList" cellspacing="0" cellpadding="0" 		id="resultList" pagesize="${pageSize}"
			class="table resultList" export="false" 
			requestURI="${app}/partner/resourceInfo/process.do?method=goToApplyResultPage" sort="list"
			partialList="true" size="${resultSize}">
		<%--<display:column  sortable="true" headerClass="sortable" title="序号">${resultList[0].id2}
		</display:column>
		--%><display:column sortable="true" headerClass="sortable" title="申请时间">${resultList[0].startTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审批时间">${resultList[0].endTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归档时间">${resultList[0].endTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审批人员">
			<eoms:id2nameDB id="${resultList[1].dataSender}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审批意见">${resultList[1].reason}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请变更类型">
			<eoms:id2nameDB id="${resultList[0].changeType}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请模块">
			<eoms:id2nameDB id="${resultList[0].referenceModel}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归档">
			<a  href="#" onclick="operate('${resultList[0].id}')">执行</a>
		</display:column>
	</display:table>
	</logic:notEmpty> 
	<logic:empty name="resultList" scope="request">
	没有记录！
	</logic:empty> 
</div>
<script type="text/javascript">
	function operate(id){
		if(confirm("你确定执行归档么?")){
			Ext.Ajax.request({
				url:"${app}/partner/process/process.do",
				params:{method:"applyToFile",idMain:id},
				success:function(response,opt){
					//Ext.Msg.alert("提示信息",response.responseText,function(){window.location.reload})};
					var config={
		 				title:"提示信息",
		 				msg:response.responseText,
		 				width:200,
		 				closable:true,
		 				fn:function(){window.location.reload();}
		 			};
		 			Ext.MessageBox.show(config);
					},
				failure:function(response,opt){
					//Ext.Msg.alert("提示信息","请求异常",function(){window.location.reload});
					var config={
		 				title:"提示信息",
		 				msg:"请求异常",
		 				width:200,
		 				closable:true,
		 				fn:function(){window.location.reload();}
		 			};
		 			Ext.MessageBox.show(config);
					}
			});
		}
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>