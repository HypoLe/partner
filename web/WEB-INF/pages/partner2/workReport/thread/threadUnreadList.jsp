<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<div style="margin-bottom: 15px">
		<input name="listType1" class="listFilter"  id="unread" type="radio" value="unread"/>未读报告
		<input name="listType2" class="listFilter" id="read" type="radio" value="read" />已读报告
		<input name="listType3" class="listFilter" id="draft" type="radio" value="draft" />报告草稿
		<input name="listType4" class="listFilter" id="createByMe" type="radio" value="createByMe" />由我提交的报告
</div>

<div id="quickSearchOp" class="ui-widget-content"><input type="image" src="${app }/nop3/images/search.gif" />快速查询我的信息</div>
<div id="quickSearch" style="display: none">
<!-- 部门树使用 -->
<div id="deptview" class="hide"></div>
<input type="hidden" id="treeBtnId" name="treeBtnId" value=""/>
	<html:form action="thread.do?method=listUnread&listType=${listType}"  method="post" styleId="workReportForm" styleClass="mySearchForm">
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					报告标题
				</td>
				<td>
					<input type="text" class="text" name="titleStringLike" />
				</td>
				<td class="label">
					报告上传人
				</td>
				<td>
					<input class="text" type="text" readonly="readonly" name="createrIdDisplay" id="createrIdDisplay" 
				alt="allowBlank:true,vtext:'请选择上报人'"
				value="<eoms:id2nameDB id='myCreateUserIdDisplay' beanId='tawSystemUserDao'/>" />
					<input type="hidden" id="createrIdStringLike" name="createrIdStringLike" />
				</td>
			</tr>
			<tr>
				<td class="label">
					报告上传开始时间
				</td>
				<td>
					<input id="createTimeDateGreaterThan"
						name="createTimeDateGreaterThan" type="text" class="text"
						readonly="readonly"
						onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',-1,-1,true,-1);"
						alt="vtype:'lessThen',link:'endDate',vtext:'开始时间不能晚于结束时间'" />
				</td>
				<td class="label">
					报告上传结束时间
				</td>
				<td>
					<input id="createTimeDateLessThan" name="createTimeDateLessThan"
						type="text" class="text" readonly="readonly"
						onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',-1,-1,true,-1);"
						alt="vtype:'moreThen',link:'startDate',vtext:'结束时间不能早于开始时间'" />
				</td>
			</tr>
		</table>
		<html:button styleClass="btn" property="method.list"
		styleId="mySearchButton" value="查询"></html:button>
</html:form>
</div>
<% 
	List myTmpList = (List)request.getAttribute("threadList");
	if(myTmpList!=null&&myTmpList.size()>0){
%>	
<html:form action="/thread.do" method="post" styleId="workReportForm">

	<html:hidden property="forumsId" />
		<display:table name="threadList" cellspacing="0" cellpadding="0"
			id="threadList" pagesize="${pageSize }" class="table threadList"
			export="false"
			requestURI="${app}/partner2/workReport/thread.do?method=listUnread&forumsId=${forumsId }"
			sort="list" partialList="true" size="resultSize"
			decorator="com.boco.partner2.workReport.displaytag.support.ThreadListDisplaytagDecorator">
			<display:setProperty name="export.rtf" value="false"></display:setProperty>
			<logic:notEmpty name="threadMgrPriv">
				<display:column property="id"
					title="<input type='checkbox' onclick='javascript:choose();'/>" />
				<display:setProperty name="css.table" value="0,"/>	
			</logic:notEmpty>
			<display:column property="title" sortable="true"
				headerClass="sortable" title="标题" paramId="id"
				paramProperty="id"
				href="${app}/partner2/workReport/thread.do?method=detail&forumsId=${threadList.forumsId }&listType=${listType }" />

			<display:column property="threadCount" sortable="true"
				headerClass="sortable" title="阅读次数" />

			<display:column property="createrName" sortable="true"
				headerClass="sortable" title="上报人" />

			<display:column property="createTime" sortable="true"
				headerClass="sortable" title="上报时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			 <display:column sortable="true" sortName="forumsId" headerClass="sortable" 
        		 title="所属报告专题">
      		   <eoms:id2nameDB id="${threadList.forumsId}" beanId="forumsDao2" />
 		   </display:column>
		</display:table>
	</html:form>
<%}else{%>
	无未读信息
<%} %>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入视图中，请稍候</div>
<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	myJ('input.listFilter').bind('change',function(event){
			window.location.href='${app}/partner2/workReport/thread.do?method=listUnread&listType='+this.value;
			myJ('div#loadIndicator').show();
	});
	myJ('div#quickSearchOp').bind('click',function(event){
		myJ('div#quickSearch').fadeToggle("fast", "linear");
	});
	myJ('input#mySearchButton').bind('click',function(event){
		myJ('form.mySearchForm').submit();
	});
	myJ('input#'+'${listType}').attr("checked","checked");
	
	//Initiallize tree click function.
	myJ("#createrIdDisplay").bind("click",function(event) {
			var showChkFldId = "createrIdDisplay";
			var saveChkFldId = "createrIdStringLike";
			showTree(showChkFldId,saveChkFldId);
	});
			
});

function showTree(showChkFldId,saveChkFldId) {
			var deptViewer = new Ext.JsonView("deptview",
					'<div class="viewlistitem-{nodeType}">{name}</div>',
					{ 
						emptyText : '<div>${eoms:a2u('没有选择项目')}</div>'
					}
					);
			var data = "[{id:'',name:'<eoms:id2nameDB id="" beanId="tawSystemUserDao"/>',nodeType:'user'}]";
			deptViewer.jsonData = eoms.JSONDecode(data);
			deptViewer.refresh();
			
			 var deptTreeAction='${app}/xtree.do?method=userFromDept';
		   	 deptetree = new xbox({
		      	  btnId:"treeBtnId",
			      dlgId:'dlg3',
			      treeDataUrl:deptTreeAction,
			      treeRootId:'-1',
			      treeRootText:'使用部门',
			      treeChkMode:'single',
			      treeChkType:'user',
			      showChkFldId:showChkFldId,
			      saveChkFldId:saveChkFldId,
			      viewer:deptViewer
		    });
		    deptetree.onBtnClick();
		    deptetree=null;
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>