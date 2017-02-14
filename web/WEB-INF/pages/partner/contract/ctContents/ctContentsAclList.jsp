<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm"%>

<script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>

<%

	String auditName = StaticMethod.nullObject2String(request.getAttribute("auditName"));
	String excelUrl = StaticMethod.nullObject2String(request.getAttribute("excelUrl"));

	
%>

<style type="text/css">
.small {
	width: 10px;
}


</style>

<script type="text/javascript">

<%	if(!"".equals(auditName)){ %>
		alert('该条记录已提交 <%=auditName%> 审核');
<%	} %>

	function choose(checkbox){
		eoms.util.checkAll(checkbox,'ids');
	}

	var checkflag = "false";
	function choose() {
		var objs = document.getElementsByName("ids");
		if(checkflag=="false"){
			for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			objs[i].checked = true;
      			checkflag="true";
			}
		}else if(checkflag=="true"){
			for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			objs[i].checked = false;
      			checkflag="false"
			}
		}
	}
	
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
	function openImport(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}
  function dele(myValue){
    myjs( "#dialog:ui-dialog" ).dialog( "destroy" );
		myjs( "#dialog-confirm" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			buttons: {
				"确定": function() {
					myjs( this ).dialog( "close" );					
					myjs.ajax({
					  type:"POST",
					  url:"FaultSheetManagement.do?method=delete&id="+myValue,
					  
					 success:deleteResult					 	  
					 });	
				},
				"退出": function() {
					myjs( this ).dialog( "close" );
				}
			}
		
		});
   
   };
   
	function deleteResult() {
	  
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		myjs( "#dialog:ui-dialog" ).dialog( "destroy" );
	
		myjs( "#dialog-message" ).dialog({
			modal: true,
			buttons: {
				Ok: function() {
				    window.location.reload();
					myjs( this).dialog( "close" );
					
				}
			}
			
		});
		
	};
 
   function deleteAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";"
		}
	}
	if (idResult == "") {
		alert("请选择需要删除的记录");
		return false;
	} else {
		if(confirm("确定要全部删除吗？")){
			$("deleteIds").value=idResult.toString();
			//var myParam=idResult.toString();
			var formObj = document.getElementById("deleteAllForm");
 			formObj.submit();
		}
		else{
			return false;
		}
	}
}

function selectAll(){
	var cardNumberList = document.getElementsByName("cardNumber");
	//Judge whether it has been checked first. One element is enough for our decision.
	var temp=cardNumberList[0].checked;
	if(temp==null){
		for (i = 0 ; i < cardNumberList.length; i ++) {
		cardNumberList[i].checked='checked';
		}
	}else{
		for (i = 0 ; i < cardNumberList.length; i ++) {
		cardNumberList[i].checked=!cardNumberList[i].checked;
		}
	}
}
    function onSubmitDele(){
	    var id = document.getElementById("contentId").value;
	    var tableId = document.getElementById("tableId").value;
	    var themeId = document.getElementById("themeId").value;
	    var url='${app}/partner/contract/ctContentss.do?method=remove&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
	    window.location.href=url;
	    //alert(url);    
    }
</script>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">

	<content tag="heading">
	<c:out value="请输入查询条件" />
	</content>
	<html:form action="ctContentss.do?method=search" method="post">
		<table id="sheet" class="formTable">
			<tr>
			<!--  	<td class="label">

					合同分类：
				</td>
				<td>
					<input type="text" class="text"
						name="themeIdT" />
				</td>-->
				
				<td class="label">
					合同标题:
				</td>
				<td>
					<input type="text" class="text" name="contractTitleTT" />
				</td>
				<td class="label">
				合同状态:	
				</td>
				<td>
				<select name="contractStatusWT">
				
				 <option value="2">有效</option>
				 <option value="1">草稿</option>
				 		 --><!--
				 

				 <option value="3">失效</option>

				 -->
				 
				 <option value="">所有合同</option>

				 <c:if test="${operateDeptId=='1'}">
				 <option value="4">已删除</option>
				 </c:if>
				</select>
				</td>
			</tr>
				<tr >
				<td class="label">合同开始时间</td><td >
					<input type="text" class="text" name="createTimeGreaterT"
						onclick="popUpCalendar(this, this,null,null,null,true,-1);"
						alt="vtype:'lessThen',link:'examineEnd',vtext:'开始时间不能晚于结束时间'"
						id="examineStart" /></td>
				<td class="label">合同结束时间</td>
				<td >
					<input type="text" class="text" name="createTimeLessT"
						onclick= "popUpCalendar(this, this,null,null,null,true,-1);"
                        alt="vtype:'moreThen',link:'examineStart',vtext:'结束时间不能早于开始时间'"
						id="examineEnd" />
				</td>
			</tr>
<!--		<tr>
 		<td class="label">
					创建人:
				</td>
				<td>
					<input type="text" class="text" name="createUserT" />
				</td>

				<td class="label">
				合同状态:	
				</td>
				<td>
				<select name="contractStatusWT">
				 <option value="2">有效</option>
				 		 --><!--
				 

				 <option value="3">失效</option>

				 -->
				 <!--<option value="1">草稿</option>
				 <option value="">所有合同</option>

				 <c:if test="${operateDeptId=='1'}">
				 <option value="4">已删除</option>
				 </c:if>
				</select>
				</td>
				
		</tr>
	-->
	</table>
		<html:submit styleClass="btn" property="method.list"
			styleId="method.list" value="提交查询结果"></html:submit>
	</html:form>
	
	</div>

</hr>
<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>


<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">
<logic-el:present name="ctContentsList">
	<display:table name="ctContentsList" cellspacing="0" cellpadding="0"
		id="ctContentsList" pagesize="${pageSize}"
		class="table ctContentsList" export="false"
		requestURI="${app}/partner/contract/ctContentss.do?method=search" sort="list"
		partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.contract.contents.displaytag.support.CtContentsDisplaytabDecorator">
		
<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${ctContentsList.id}" />
		</display:column>
<!-- 
		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.themeId">
			<eoms:id2nameDB id="${ctContentsList.themeId}" beanId="ctTableThemeDao" />
		</display:column>
 -->
		<display:column sortable="true" property="contractTitle" titleKey="ctContents.contentTitle" 
		    paramId="id" paramProperty="id"
			headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createUser">
			<eoms:id2nameDB id="${ctContentsList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createDept">
			<eoms:id2nameDB id="${ctContentsList.createDept}" beanId="tawSystemDeptDao" />
		</display:column>

		<display:column sortable="true" property="createTime" titleKey="ctContents.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"
		    paramId="id" paramProperty="id"
			headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.contentStatus">
			<eoms:dict key="dict-partner-contract" dictId="contractStatus" itemId="${ctContentsList.contractStatus}" beanId="id2nameXML" />
		</display:column>
<%--<display:column title="上传附件" headerClass="imageColumn">
			<a href="javascript:var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=editFile';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url" target="_blank">
				<img src="${app}/images/icons/icon1.gif"/> </a>
		</display:column>--%>
		<display:column title="查看" headerClass="imageColumn">
		<c:if test="${ctContentsList.contractStatus=='1'}">
					<a href="javascript:var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=detailDraft';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url">
		                        <img src="${app}/images/icons/edit.gif" /> </a>
		</c:if>
		<c:if test="${ctContentsList.contractStatus!='1'}">
			<a href="javascript:var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=detail';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
				</c:if>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />

	</display:table>
	</logic-el:present>
</fmt:bundle>


<input type="button" name="delete" onclick="deleteAll()" class="btn" value="批量删除" />

<form id="deleteAllForm" action="ctContentss.do?method=deleteAll" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</form>





<%@ include file="/common/footer_eoms.jsp"%>
