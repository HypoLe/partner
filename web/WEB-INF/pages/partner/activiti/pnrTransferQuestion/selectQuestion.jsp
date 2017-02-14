<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var myjs=jQuery.noConflict();
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
	function changeValue(number){
		document.getElementById("questionNumber").value=number;
	}
</script>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">问题跟踪</span>
</div>

<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	
	<html:form action="/pnrTransferQuestion.do?method=getQuestionByQuestionNumber"
	styleId="theform">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">问题编号</td>
				<td><input class="text" type="text" name="questionNumber" id="questionNumber"/></td>
			</tr>
	</table>
		<input type="submit" class="btn" value="查询"/>
	</html:form>
</div>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">忘记问题编号？</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
 
	<html:form action="/pnrTransferQuestion.do?method=checkQuestionNumber"
	styleId="theform">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">联系人</td>
				<td><input class="text" type="text" name="linkman"/></td>
				<td class="label">联系电话</td>
				<td><input class="text" type="text" name="linkmanPhone"/></td>
			</tr>
			<tr>		
				<td class="label">问题详情</td>
				<td><input class="text" type="text" name="commonQuestion"/></td>
				<td class="label">提问时间</td>
				<td> <input type="text" class="text" name="raiseTime" readonly="readonly" 
				id="bTime" 
				onclick="popUpCalendar(this, this,null,null,null,false,-1)" /> </td>
			</tr>
	</table>
		<input type="submit" class="btn" value="找回"/>
	</html:form>
</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>

<!-- Information hints area end-->

	<logic:present name="taskList" scope="request">
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		pagesize="${pagesize}"
		 requestURI="pnrTransferQuestion.do"
		id="taskList" class="table list" export="false"  >
		<display:column property="questionNumber" title="问题编号" />
		<display:column property="linkman" title="联系人" />
		<display:column title="问题详情" property="questionContent" maxLength = "26"/>
		<display:column title="提问时间" property="useTime"/>
		<display:column title="操作">
			<a href="javascript:changeValue('${taskList.questionNumber}' )">选择</a>
		</display:column>
	</display:table>
</logic:present>
<!-- Information hints area end-->

	<logic:present name="onePnrTransferQuestion" scope="request">
	<display:table name="onePnrTransferQuestion" cellspacing="0" cellpadding="0"
		id="onePnrTransferQuestion" class="table list" export="false" sort="list" >
		<display:column property="questionNumber" title="问题编号" />
		<display:column title="提问人" >
			<eoms:id2nameDB id="${onePnrTransferQuestion.questioner}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column property="linkman" title="联系人" />
		<display:column property="linkmanPhone" title="联系方式" />
		<display:column title="问题详情" property="questionContent"/>
		<display:column title="提问时间" property="useTime"/>
		<display:column title="问题紧急程度" >
			<c:if test="${onePnrTransferQuestion.questionLevel==0}">
			一般
			</c:if>
			<c:if test="${onePnrTransferQuestion.questionLevel==1}">
			特急
			</c:if>
		</display:column>
		<display:column title="现在状态">
		<eoms:id2nameDB id="${onePnrTransferQuestion.questionState}" beanId="ItawSystemDictTypeDao"/>
		</display:column>
	</display:table>
	
</logic:present>
	<logic:present name="questionStateList" scope="request">
	<display:table name="questionStateList" cellspacing="0" cellpadding="0"
		id="questionStateList" class="table list" export="false" sort="list" >
		<display:column title="问题历史状态">
			<eoms:id2nameDB id="${questionStateList.quesitonState}" beanId="ItawSystemDictTypeDao"/>
		</display:column>
		<display:column property="useTime" title="改变状态时间" />
	</display:table>
</logic:present>


<%@ include file="/common/footer_eoms.jsp"%>
