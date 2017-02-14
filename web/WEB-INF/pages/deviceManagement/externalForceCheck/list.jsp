<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
 var myjs=jQuery.noConflict();
	function goadd(){
		window.open("${app}/externalForceCheck/externalForceCheck.do?method=goToAddPage");
		}
		
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
<form action="externalForceCheck.do?method=list" method="post">
		<fieldset>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">外力盯防计划现场:</td>
					<td>
						<input type="text" class="text" name="placeStringLike"/>
					</td>
					
					<td class="label">
						外力盯防计划状态:
					</td>
					<td>
						<select name="status" >
						<option value="">请选择</option> 
						<option value="0">新建状态</option>
						<option value="1">计划执行中</option>
						<option value="2">计划归档</option>
						<option value="-1">计划取消或终止</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="查询" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form></div>
<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>

<logic:present name="externalForceCheckList" scope="request">
	<display:table name="externalForceCheckList" cellspacing="0" cellpadding="0"
		id="externalForceCheckList" pagesize="${pagesize}"
		class="table externalForceCheckList" export="false"
		requestURI="externalForceCheck.do" sort="external"
		partialList="true" size="${size}">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${externalForceCheckList.id}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="外力盯防现场">
			${externalForceCheckList.place}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="外力盯防负责人">
			<eoms:id2nameDB id="${externalForceCheckList.ownerUser}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="外力盯防所属线路">
			${externalForceCheckList.route}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="外力盯防所属线路段">
			${externalForceCheckList.routeStage}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="外力盯防起始日期">
			${externalForceCheckList.startDate}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="外力盯防结束日期">
			${externalForceCheckList.endDate}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="协议状态">
			<c:if test="${externalForceCheckList.status eq '0'}">
				新建状态
			</c:if>
			<c:if test="${externalForceCheckList.status eq '1'}">
				计划执行中
			</c:if>
			<c:if test="${externalForceCheckList.status eq '2'}">
				计划归档
			</c:if>
			<c:if test="${externalForceCheckList.status eq '-1'}">
				计划取消或终止
			</c:if>
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="编辑"
			 media="html">
			 <c:if test="${externalForceCheckList.status=='0'}">
			<a id="${externalForceCheckList.id }"
				href="${app }/externalForceCheck/externalForceCheck.do?method=goToEditPage&id=${externalForceCheckList.id}"
				>
				<img src="${app }/images/icons/edit.gif">
				</a>
				</c:if>
				 <c:if test="${externalForceCheckList.status!='0'}">
				无
				 </c:if>
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="填写值班信息"
			 media="html">
			 <c:if test="${externalForceCheckList.status=='0'||externalForceCheckList.status=='1'}">
			<a id="${externalForceCheckList.id }"
				href="${app }/externalForceCheck/externalForceCheck.do?method=goToAddSubPage&id=${externalForceCheckList.id}"
				>
				<img src="${app }/images/icons/edit.gif">
				</a>
				</c:if>
				 <c:if test="${externalForceCheckList.status!='0'&&externalForceCheckList.status!='1'}">
				 无 
				 </c:if>
		</display:column>
		
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${faultSheetList.id }"
				href="${app }/externalForceCheck/externalForceCheck.do?method=detail&id=${externalForceCheckList.id}"
				>
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>
		
				
		</display:table>
		</logic:present>
		<input type="button" name="add" id="add" value="新增盯防计划" onclick="goadd();"/> 
		<input type="button" name="deleteGps" id="deleteGps" value="删除" onclick="deleteAll();"/> 
<form id="deleteAllForm" action="externalForceCheck.do?method=delete" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</form>
<%@ include file="/common/footer_eoms.jsp"%>