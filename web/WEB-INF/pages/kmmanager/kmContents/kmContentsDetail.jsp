<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<html:form action="/kmContentsOpinions.do?method=save" styleId="kmContentsOpinionForm" method="post"> 
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="content-info" class="tabContent">
<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<!-- 知详细信息 -->
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="kmContents.detail.title"/></div>
	</caption>

	<!-- 定义知识内容变量 -->
	<c:set var="KmContentsMap" scope="page" value="${KmContents}"/>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.tableId" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.TABLE_ID}" beanId="kmTableGeneralDao" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.themeId" />
		</td>
		<td class="content">
		  	<eoms:id2nameDB id="${KmContentsMap.THEME_ID}" beanId="kmTableThemeDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.rolestrFlag" />			
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="rolestrFlag" itemId="${KmContentsMap.ROLESTR_FLAG}" beanId="id2nameXML" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.levelFlag" />			
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="levelFlag" itemId="${KmContentsMap.LEVEL_FLAG}" beanId="id2nameXML" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.createUser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.CREATE_USER}" beanId="tawSystemUserDao" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.createDept" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.CREATE_DEPT}" beanId="tawSystemDeptDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.contentTitle" />			
		</td>
		<td class="content" colspan="3">
			${KmContentsMap.CONTENT_TITLE}
		</td>
	</tr>
	
	<!-- 判断修改人是否为空 -->
	<c:if test="${not empty KmContentsMap.MODIFY_USER}">
	<tr>
		<td class="label">
			<fmt:message key="kmContents.modifyUser" />			
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.MODIFY_USER}" beanId="tawSystemUserDao" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.modifyDept" />			
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.MODIFY_DEPT}" beanId="tawSystemDeptDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="kmContents.modifyTime" />			
		</td>
		<td class="content" colspan="3">
			${KmContentsMap.MODIFY_TIME}
		</td>
	</tr>
	</c:if>
	
	
	<tr>
		<td class="label">
			<fmt:message key="kmContents.contentStatus" />			
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="contentStatus" itemId="${KmContentsMap.CONTENT_STATUS}" beanId="id2nameXML" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.auditFlag" />			
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="isOrNot" itemId="${KmContentsMap.AUDIT_FLAG}" beanId="id2nameXML" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="kmContents.isBest" />			
		</td>
		<td class="content">
		    <eoms:dict key="dict-kmmanager" dictId="isOrNot" itemId="${KmContentsMap.IS_BEST}" beanId="id2nameXML" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.isPublic" />			
		</td>
		<td class="content">
		    <eoms:dict key="dict-kmmanager" dictId="isOrNot" itemId="${KmContentsMap.IS_PUBLIC}" beanId="id2nameXML" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			知识评价		
		</td>	
		<td class="content" colspan="3">
			<fmt:message key="kmContents.gradeOne" />   ${KmContentsMap.GRADE_ONE}   次 <b>|</b>
			<fmt:message key="kmContents.gradeTwo" />   ${KmContentsMap.GRADE_TWO}   次 <b>|</b>
			<fmt:message key="kmContents.gradeThree" /> ${KmContentsMap.GRADE_THREE} 次 <b>|</b>
			<fmt:message key="kmContents.gradeFour" />  ${KmContentsMap.GRADE_FOUR}  次 <b>|</b>
			<fmt:message key="kmContents.gradeFive" />  ${KmContentsMap.GRADE_FIVE}  次
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.readCount" />			
		</td>
		<td class="content">
			${KmContentsMap.READ_COUNT}
		</td>

		<td class="label">
			<fmt:message key="kmContents.useCount" />			
		</td>
		<td class="content">
			${KmContentsMap.USE_COUNT}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="kmContents.modifyCount" />			
		</td>
		<td class="content" colspan="3">
			${KmContentsMap.MODIFY_COUNT}
		</td>
	</tr>

	<%@ include file="kmContentColDetail.jsp"%>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.contentKeys" />
		</td>
		<td class="content" colspan="3">
		    ${KmContentsMap.CONTENT_KEYS}
		</td>
	</tr>
</table>

<input type="hidden" id="contentId" name="contentId" value="${KmContentsMap.ID}" />
<input type="hidden" id="tableId"   name="tableId"   value="${KmContentsMap.TABLE_ID}" />
<input type="hidden" id="themeId"   name="themeId"   value="${KmContentsMap.THEME_ID}" />

<c:if test="${KmContentsMap.CREATE_USER == sessionScope.sessionform.userid}">		
<br>
<!-- 修改或删除知识 -->
	<%
//	if(operateType.indexOf("W")!=-1||"admin".equals(operateUserId)){
	%>
<table>
	<tr>
		<td>
		    <input type="button" class="btn" value="修改" onclick="javascript:onSubmitEdit();"/>&nbsp;
		    
		    <c:if test="${KmContentsMap.CONTENT_STATUS != 3}"><!-- 可以将其它状态的知识失效 -->
		    <input type="button" class="btn" value="失效" onclick="javascript:onSubmitOver();"/>&nbsp;
		    </c:if>
		    
		    <c:if test="${KmContentsMap.CONTENT_STATUS == 3}"><!-- 可以删除失效的知识 -->
		    <input type="button" class="btn" value="删除" onclick="javascript:onSubmitDele();"/>&nbsp;
		    </c:if>
		    
		    <c:if test="${KmContentsMap.IS_BEST != 1}"><!-- 可以推荐的知识 -->
		    <input type="button" class="btn" value="推荐" onclick="recommend('${KmContentsMap.TABLE_ID}','${KmContentsMap.ID}','${KmContentsMap.IS_BEST}')"/>
		    </c:if>
		</td>
	</tr>
</table>
	<%
//	}
	%>
</c:if>

<!-- 对知识进行评价:zu作者不能对自己的知识进行评价 -->
<c:if test="${KmContentsMap.CREATE_USER != sessionScope.sessionform.userid}">			   
<br>
<table class="formTable">
	<tr>
		<td class="label">
			<fmt:message key="kmContentsOpinion.opinionGrade" />
		</td>
		<td class="content">
		    <eoms:dict key="dict-kmmanager" dictId="opinionGrade" isQuery="false" 
			           defaultId="" selectId="opinionGrade" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择评论星级(字典)...'"/>	
		</td>
		<td class="label">
			<fmt:message key="kmContentsOpinion.isEdit" />
		</td>
		<td class="content">
		    <eoms:dict key="dict-kmmanager" dictId="isOrNot" isQuery="false" 
			           defaultId="" selectId="isEdit" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'是否提出修改(字典)...'"/>	
		</td>		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContentsOpinion.opinionContent" />
		</td>
		<td class="content" colspan="3">
			<textarea name="opinionContent" cols="50" id="opinionContent" class="textarea max" ></textarea><p/>
			<input type="button" class="btn" value="提交知识评论" onclick="javascript:onSubmit();"/>&nbsp;
			<c:if test="${KmContentsMap.IS_BEST != 1}"><!-- 可以推荐的知识 -->
		    <input type="button" class="btn" value="推荐" onclick="recommend('${KmContentsMap.TABLE_ID}','${KmContentsMap.ID}','${KmContentsMap.IS_BEST}')"/>
		    </c:if>
		</td>
    </tr>

    <input type="hidden" name="isRefedit" value="0" /> <!-- 修改建议是否被采纳 -->
    <input type="hidden" name="isDeleted" value="0" /> <!-- 是否删除 -->
</table>
</c:if>

</fmt:bundle>

<br>
</div>

  <!-- 查看知识评价信息 -->
  <div id="count-info" class="tabContent">
    <display:table name="kmContentsOpinionList" cellspacing="0" cellpadding="0" class="table kmContentsOpinionList" 
        id="kmContentsOpinionList" export="false" sort="list" partialList="true" size="resultSize" 
        pagesize="${pageSize}" requestURI="${app}/kmContentsOpinion/kmContentsOpinions.do?method=search">
		
		<display:column headerClass="sortable" title="评论人">
			<eoms:id2nameDB id="${kmContentsOpinionList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column headerClass="sortable" title="评论部门">
			<eoms:id2nameDB id="${kmContentsOpinionList.createDept}" beanId="tawSystemDeptDao" />
		</display:column>

	    <display:column property="createTime" 
			headerClass="sortable" title="评论时间"  paramId="id" paramProperty="id"  format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		
		<display:column headerClass="sortable" title="评价星级">
		    <eoms:dict key="dict-kmmanager" dictId="opinionGrade" itemId="${kmContentsOpinionList.opinionGrade}" beanId="id2nameXML" />
		</display:column>
		
		<display:column headerClass="sortable" title="是否提出修改">
		    <eoms:dict key="dict-kmmanager" dictId="isOrNot" itemId="${kmContentsOpinionList.isEdit}" beanId="id2nameXML" />		    
		</display:column>
		
	    <display:column property="opinionContent" 
			headerClass="sortable" title="评论内容"  paramId="id" paramProperty="id"/>

	    <display:setProperty name="paging.banner.item_name" value="kmContentsOpinion" />
	    <display:setProperty name="paging.banner.items_name" value="kmContentsOpinions" />

	</display:table>
  </div>

  <!-- 查看知识版本信息 -->
  <div id="history-info" class="tabContent">
    <display:table name="KmContentsHistoryList" cellspacing="0" cellpadding="0"  class="table KmContentsHistoryList"  
        id="KmContentsHistoryList" export="false" sort="list" partialList="true" size="resultSize">

	    <display:column property="modifyTime" headerClass="sortable" title="修改时间" paramId="id" paramProperty="id" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column headerClass="sortable" title="修改作者">
			<eoms:id2nameDB id="${KmContentsHistoryList.modifyUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column headerClass="sortable" title="修改部门">
			<eoms:id2nameDB id="${KmContentsHistoryList.modifyDept}" beanId="tawSystemDeptDao" />
		</display:column>
		<display:column headerClass="sortable" title="版本号">
			${KmContentsHistoryList.version}.0
		</display:column>
		
		<display:column title="查看" headerClass="imageColumn">
		    <a href="#" onClick="javascript:var id = '${KmContentsHistoryList.id }';
		                        var tableId = '${KmContentsHistoryList.tableId}';
		                        var themeId = '${KmContentsHistoryList.themeId}';
		                        var version = '${KmContentsHistoryList.version}';
		                        var url='${app}/kmmanager/kmContentss.do?method=detailHistory';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId + '&VERSION=' + version;
		                        window.open(url);">
		       <img src="${app}/images/icons/search.gif"/></a>
		</display:column>
	</display:table>
  </div>

  <!-- 查看知识审核信息 -->
  <div id="audit-info" class="tabContent">
    <display:table name="KmAuditColumnList" cellspacing="0" cellpadding="0"  class="table KmAuditColumnList"  
        id="KmAuditColumnList" export="false" sort="list" partialList="true" size="resultSize">

	    <display:column property="createTime" sortable="false" 
			headerClass="sortable" title="操作时间"  paramId="id" paramProperty="id" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column headerClass="sortable" title="审核人">
			<eoms:id2nameDB id="${KmAuditColumnList.operateId}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column headerClass="sortable" title="审核结果">	
		<eoms:dict key="dict-kmmanager" dictId="auditResult" itemId="${KmAuditColumnList.auditResult}" beanId="id2nameXML" />		
		</display:column>
		
	    <display:column property="remark" sortable="false" 
			headerClass="sortable" title="审核内容"  paramId="id" paramProperty="id"/>			
 	</display:table>
  </div>
  
  <div id="comments-info" class="tabContent">	 
  </div>

</div>
</html:form>

<script type="text/javascript">
	var readTree;
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '内容信息 ');
        	tabs.addTab('count-info', '知识评价信息 ');
        	tabs.addTab('history-info', '知识版本信息 ');
        	tabs.addTab('audit-info', '知识审核信息 ');
    		tabs.activate(0);
	});
	
	//修改
	function onSubmitEdit(){
	    var id = document.getElementById("contentId").value;
	    var tableId = document.getElementById("tableId").value;
	    var themeId = document.getElementById("themeId").value;
	    var url='${app}/kmmanager/kmContentss.do?method=edit&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
	    window.location.href(url);
	    //alert(url);
    }
    //失效
	function onSubmitOver(){
	    var id = document.getElementById("contentId").value;
	    var tableId = document.getElementById("tableId").value;
	    var themeId = document.getElementById("themeId").value;
	    var url='${app}/kmmanager/kmContentss.do?method=over&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
	    window.location.href(url);
	    //alert(url);	
    }
    //删除
    function onSubmitDele(){
	    var id = document.getElementById("contentId").value;
	    var tableId = document.getElementById("tableId").value;
	    var themeId = document.getElementById("themeId").value;
	    var url='${app}/kmmanager/kmContentss.do?method=remove&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
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
    
    //以下是推荐知识
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
			alert('已经是推荐知识！');
			return;
		}
		var url='${app}/kmmanager/kmContentss.do?method=recommend&tableId='+tableId+'&id='+id+'&isBest=1';
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