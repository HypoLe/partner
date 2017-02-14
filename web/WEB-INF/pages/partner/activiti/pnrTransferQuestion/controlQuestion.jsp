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
	//获得详细信息
	function getMoreInformation(number){
		window.location = "pnrTransferQuestion.do?method=getMoreQuestionInformation&number="+number;
	}
</script>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">问题条件查询</span>
</div>

<div id="listQueryObject" style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	
	<html:form action="/pnrTransferQuestion.do?method=controlQuestion"
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
			<tr>
				<td class="label">问题状态</td>
				<td>
					<eoms:comboBox name="questionState" id="mainFaultNet"
					defaultValue="" initDicId="1012303"
					alt="allowBlank:false" styleClass="select" />
				</td>
				<td class="label">紧急程度</td>
				<td>
					<select Class="select" name="questionLevel">
						<option value="3">请选择</option>
						<option value="0">一般</option>
						<option value="1">加急</option>
					</select>
				</td>
			</tr>
	</table>
		<input type="submit" class="btn" value="查询"/>
	</html:form>
</div>
	
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>


	<display:table name="taskList" cellspacing="0" cellpadding="0" pagesize="${pagesize}"
	id="taskList" requestURI="pnrTransferQuestion.do"
		 class="table list" export="false">
		<display:column title="问题编号">
			<a href="javascript:getMoreInformation('${taskList.questionNumber}')">${taskList.questionNumber}</a>
		</display:column>
		<display:column title="问题详情" property="questionContent" maxLength="25"/>
		<display:column title="提问时间" property="useTime"/>
		<display:column title="问题紧急程度" >
			<c:if test="${taskList.questionLevel==0}">
			一般
			</c:if>
			<c:if test="${taskList.questionLevel==1}">
			特急
			</c:if>
		</display:column>
		<display:column title="现在状态">
		<eoms:id2nameDB id="${taskList.questionState}" beanId="ItawSystemDictTypeDao"/>
		</display:column>
		<display:column title="操作">
		<a href="javascript:getMoreInformation('${taskList.questionNumber}' )">详情</a>
		</display:column>
	</display:table>
<%@ include file="/common/footer_eoms.jsp"%>
