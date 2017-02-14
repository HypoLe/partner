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
	function state(){
		var state = document.getElementById("state").value;
		var newState = document.getElementById("mainFaultNet").value;
		if(state==newState){
		return false;
		}else{
		return true;
		}
	}
	function level(){
		var level = document.getElementById("level").value;
		var newLevel = document.getElementById("questionLevel").value;
		if(level==newLevel){
			return false;
		}else{
			return true;
		}
	}
	function judgeSubmit(){
		state();
		level();
		return state()||level();
	}
	function back(){
	window.location="pnrTransferQuestion.do?method=controlQuestion";
	}
</script>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">问题详细信息</span>
</div>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	
	<html:form action="/pnrTransferQuestion.do?method=changeQuestionInformation"
	styleId="theform" onsubmit="return judgeSubmit();">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">提问人</td>
				<td>
				<eoms:id2nameDB id="${pnrTransferQuestion.questioner}" beanId="tawSystemUserDao" />
			</tr>
			<tr>		
				<td class="label">联系人</td>
				<td>${pnrTransferQuestion.linkman}</td>
				
			</tr>
			<tr>
			<td class="label">联系电话</td>
				<td>${pnrTransferQuestion.linkmanPhone}</td>
			</tr>
			<tr>		
				<td class="label">问题编号</td>
				<td>${pnrTransferQuestion.questionNumber }<input type="hidden" name="questionNumber" value="${pnrTransferQuestion.questionNumber }"/></td>
			</tr>
			<tr>		
				<td class="label">问题详情</td>
				<td>${pnrTransferQuestion.questionContent }</td>
			</tr>
			<tr>
				<td class="label">提问时间</td>
				<td> ${pnrTransferQuestion.useTime }</td>
			</tr>
			<tr>
				<td class="label">问题状态</td>
				<td>
					<eoms:comboBox name="questionState" id="mainFaultNet"
					defaultValue="${pnrTransferQuestion.questionState }" initDicId="1012303"
					alt="allowBlank:false" styleClass="select"  />
				</td>
				</tr>
				<tr>
				<td class="label">紧急程度</td>
				<td>
					<select Class="select" name="questionLevel" >
					<c:if test="${pnrTransferQuestion.questionLevel==0}">
						<option value="0" selected>一般</option>
						<option value="1">加急</option>
					</c:if>
					<c:if test="${pnrTransferQuestion.questionLevel==1}">
						<option value="0">一般</option>
						<option value="1" selected>加急</option>
					</c:if>
					</select>
					<input type="hidden" id="state" value="${pnrTransferQuestion.questionState }"/>
					<input type="hidden" id="level" value="${pnrTransferQuestion.questionLevel}"/>
				</td>
			</tr>
	</table>
		<input type="submit" class="btn" value="确认修改"/>&nbsp;&nbsp;<input type="button" class="btn" value="返回" onclick="back()"/>
	</html:form>
</div>
	
<%@ include file="/common/footer_eoms.jsp"%>
