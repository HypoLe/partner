<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<html:form action="/ctContentsOpinions.do?method=save" styleId="ctContentsOpinionForm" method="post"> 
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="content-info" class="tabContent">
<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<!-- 知详细信息 -->
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctContents.detail.title"/></div>
	</caption>

	<!-- 定义合同内容变量 -->
	<c:set var="CtContentsMap" scope="page" value="${CtContents}"/>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.tableId" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.TABLE_ID}" beanId="ctTableGeneralDao" />
		</td>

		<td class="label">
			<fmt:message key="ctContents.themeId" />
		</td>
		<td class="content">
		  	<eoms:id2nameDB id="${CtContentsMap.THEME_ID}" beanId="ctTableThemeDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.createUser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.CREATE_USER}" beanId="tawSystemUserDao" />
		</td>

		<td class="label">
			<fmt:message key="ctContents.createDept" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.CREATE_DEPT}" beanId="tawSystemDeptDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentTitle" />			
		</td>
		<td class="content" colspan="3">
			${CtContentsMap.CONTRACT_TITLE}
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentKeys" />
		</td>
		<td class="content" colspan="3">
		    ${CtContentsMap.CONTRACT_NO}
		</td>
	</tr>
	<tr>	
		<td class="label">
			服务期限&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${CtContentsMap.SERVICR_LIMIT}
		</td>
		<td class="label">
			合同总额&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${CtContentsMap.AMOUNT}
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${CtContentsMap.PARTY_A}
		</td>
		<td class="label">
			乙方&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		   ${CtContentsMap.PARTY_B}
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方联系方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${CtContentsMap.PARTY_A_CONTACT}
		</td>
		<td class="label">
			乙方联系方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${CtContentsMap.PARTY_B_CONTACT}
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方接口规范&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    ${CtContentsMap.PARTY_A_INTERFACE}
		</td>
	</tr>
	<tr>
		<td class="label">
			乙方接口规范&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    ${CtContentsMap.PARTY_B_INTERFACE}
		</td>
	</tr>
	<tr>
		<td class="label">
			责权描述&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.DUTY_DESCRIBE}
		</td>
	</tr>
	<tr>
		<td class="label">
			质量考核内容&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.QUALITY_CHECK}	 
		</td>
	</tr>
	<tr>
		<td class="label">
			质量考核方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.QUALITY_CHECK_WAY}		 
		</td>
	</tr>	
	<tr>
		<td class="label">
			争议解决方案&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.DERAIGN_WAY}	 
		</td>
	</tr>
	<tr>
		<td class="label">
			业务变更管理&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.CHANGING_MANAGE}		 
		</td>
	</tr>
	<tr>
		<td class="label">
			附则&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.OTHER_RULE}		 
		</td>
	</tr>	
	<tr>
		<td class="label">
			附件&nbsp;<font color='red'></font>
		</td>
		<td class="content" colspan="3">
		<eoms:attachment name="CtContents" property="ACCESSORIES" scope="request" idField="${CtContentsMap.ACCESSORIES}" appCode="contract" viewFlag="Y"/> 
		</td>
	</tr>	
	<tr>
		<td class="label">
			备注&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.REMARK}		 
		</td>
	</tr>
	<tr>
		<td class="label" >
			对应付费计划
		</td>
		<td class="content">
			<c:if test="${CtContentsMap.FEE_INFO_ID==null||CtContentsMap.FEE_INFO_ID==''}">
				<input type="button" class="btn" value="新增付费计划" 
				onclick="window.open('${app}/partner/feeInfo/pnrFeeInfoMains.do?method=edit&contentId=${CtContentsMap.ID}&tableId=${CtContentsMap.TABLE_ID}&themeId=${CtContentsMap.THEME_ID}')"/>
			</c:if>	
			<c:if test="${CtContentsMap.FEE_INFO_ID!=''}">
				<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=${CtContentsMap.FEE_INFO_ID}" target="_blank">
					<eoms:id2nameDB id="${CtContentsMap.FEE_INFO_ID}" beanId="pnrFeeInfoMainDao" /></a>	
			</c:if>		
		</td>
		<td class="content" colspan="2">
			<a href="${app}/partner/agreement/pnrAgreementMains.do?method=search&ctContentId=${CtContentsMap.ID}" target="_blank">
				相关协议
			</a>
		</td>
	</tr>
	<tr>
		<td class="label" colspan="4">
			附加信息
		</td>
	</tr>







	<%@ include file="ctContentColDetail.jsp"%>
	
	<!-- 判断修改人是否为空 -->
	<!-- 
	<c:if test="${not empty CtContentsMap.MODIFY_USER}">
	<tr>
		<td class="label">
			<fmt:message key="ctContents.modifyUser" />			
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.MODIFY_USER}" beanId="tawSystemUserDao" />
		</td>

		<td class="label">
			<fmt:message key="ctContents.modifyDept" />			
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.MODIFY_DEPT}" beanId="tawSystemDeptDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="ctContents.modifyTime" />			
		</td>
		<td class="content" colspan="3">
			${CtContentsMap.MODIFY_TIME}
		</td>
	</tr>
	</c:if>
	
	
	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentStatus" />			
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-contract" dictId="contractStatus" itemId="${CtContentsMap.CONTRACT_STATUS}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="ctContents.isPublic" />			
		</td>
		<td class="content">
		    <eoms:dict key="dict-partner-contract" dictId="isOrNot" itemId="${CtContentsMap.IS_PUBLIC}" beanId="id2nameXML" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.readCount" />			
		</td>
		<td class="content">
			${CtContentsMap.READ_COUNT}
		</td>
		<td class="label">
			<fmt:message key="ctContents.modifyCount" />			
		</td>
		<td class="content">
			${CtContentsMap.MODIFY_COUNT}
		</td>
	</tr>
-->

</table>

<input type="hidden" id="contentId" name="contentId" value="${CtContentsMap.ID}" />
<input type="hidden" id="tableId"   name="tableId"   value="${CtContentsMap.TABLE_ID}" />
<input type="hidden" id="themeId"   name="themeId"   value="${CtContentsMap.THEME_ID}" />

<c:if test="${CtContentsMap.CREATE_USER == sessionScope.sessionform.userid}">		
<br>
<!-- 修改或删除合同 -->
	<%
//	if(operateType.indexOf("W")!=-1||"admin".equals(operateUserId)){
	%>
<table>
	<tr>
		<td>
		    <input type="button" class="btn" value="修改" onclick="javascript:onSubmitEdit();"/>&nbsp;
		    
		    <c:if test="${CtContentsMap.CONTRACT_STATUS != 3}"><!-- 可以将其它状态的合同失效 -->
		    <input type="button" class="btn" value="失效" onclick="javascript:onSubmitOver();"/>&nbsp;
		    </c:if>
		    
		    <c:if test="${CtContentsMap.CONTRACT_STATUS == 3}"><!-- 可以删除失效的合同 -->
		    <input type="button" class="btn" value="删除" onclick="javascript:onSubmitDele();"/>&nbsp;
		    </c:if>

		</td>
	</tr>
</table>
	<%
//	}
	%>
</c:if>

  <!-- 查看合同版本信息 -->
  <div id="history-info" class="tabContent">
    <display:table name="CtContentsHistoryList" cellspacing="0" cellpadding="0"  class="table CtContentsHistoryList"  
        id="CtContentsHistoryList" export="false" sort="list" partialList="true" size="resultSize">

	    <display:column property="modifyTime" headerClass="sortable" title="修改时间" paramId="id" paramProperty="id" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column headerClass="sortable" title="修改作者">
			<eoms:id2nameDB id="${CtContentsHistoryList.modifyUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column headerClass="sortable" title="修改部门">
			<eoms:id2nameDB id="${CtContentsHistoryList.modifyDept}" beanId="tawSystemDeptDao" />
		</display:column>
		<display:column headerClass="sortable" title="版本号">
			${CtContentsHistoryList.version}.0
		</display:column>
		
		<display:column title="查看" headerClass="imageColumn">
		    <a href="#" onClick="javascript:var id = '${CtContentsHistoryList.id }';
		                        var tableId = '${CtContentsHistoryList.tableId}';
		                        var themeId = '${CtContentsHistoryList.themeId}';
		                        var version = '${CtContentsHistoryList.version}';
		                        var url='${app}/partner/contract/ctContentss.do?method=detailHistory';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId + '&VERSION=' + version;
		                        window.open(url);">
		       <img src="${app}/images/icons/search.gif"/></a>
		</display:column>
	</display:table>
  </div>
  
  <div id="comments-info" class="tabContent">	 
  </div>

</fmt:bundle>
</html:form>

<script type="text/javascript">
	var readTree;
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '内容信息 ');
        	tabs.addTab('history-info', '合同版本信息 ');
    		tabs.activate(0);
	});
	
	//修改
	function onSubmitEdit(){
	    var id = document.getElementById("contentId").value;
	    var tableId = document.getElementById("tableId").value;
	    var themeId = document.getElementById("themeId").value;
	    var url='${app}/partner/contract/ctContentss.do?method=edit&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
	    window.location.href(url);
	    //alert(url);
    }
    //失效
	function onSubmitOver(){
	    var id = document.getElementById("contentId").value;
	    var tableId = document.getElementById("tableId").value;
	    var themeId = document.getElementById("themeId").value;
	    var url='${app}/partner/contract/ctContentss.do?method=over&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
	    window.location.href(url);
	    //alert(url);	
    }
    //删除
    function onSubmitDele(){
	    var id = document.getElementById("contentId").value;
	    var tableId = document.getElementById("tableId").value;
	    var themeId = document.getElementById("themeId").value;
	    var url='${app}/partner/contract/ctContentss.do?method=remove&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
	    window.location.href(url);
	    //alert(url);    
    }
 
	//提交评论
	function onSubmit(){
    	if(document.forms[0].opinionGrade.value==""){
    		alert('请选择评价星级');
    		return false; 
    	}
    	if(document.forms[0].isEdit.value==""){
    		alert('请选择是否提出修改');
    		return false; 
    	}     	
    	if(document.forms[0].opinionContent.value==""){
    		alert('请填写评价内容');
    		return false; 
    	}
       // document.forms[0].historyType.value="1";
        document.forms[0].submit();
        return true;
    }
    
    //以下是推荐合同
    function createRequest()
	{
		var httpRequest = null;
		if(window.XMLHttpRequest){
	     httpRequest=new XMLHttpRequest();
	    }else if(window.ActiveXObject){
	     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
	    }
	    return httpRequest;
	}
	function recommend(tableId,id,isBest)
	{
		if(isBest=='1'){
			alert('已经是推荐合同！');
			return;
		}
		var url='${app}/partner/contract/ctContentss.do?method=recommend&tableId='+tableId+'&id='+id+'&isBest=1';
	 	var httpRequest = createRequest();
		if(httpRequest){
		     httpRequest.open("POST",url,true);
		     httpRequest.onreadystatechange=function()
		     {
		     	if(httpRequest.readyState==4)
			     	if(httpRequest.status==200){
			     		if(httpRequest.responseText=='fail'){
			     			alert('出现异常，推荐失败！');
			     			return;
			     		}
		                alert('推荐成功！');
					}	
		     }
		     httpRequest.send(null);
		}
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>