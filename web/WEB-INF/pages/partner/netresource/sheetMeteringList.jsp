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
	<form action="pnrResMeteringAction.do?method=findSheetMeteringList" method="post">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">计次类型</td>
				<td>
					<select name="eventType" class="select">
						<option value="001">故障处理</option>
						<option value="002">投诉处理</option>
					</select>
				</td>
				<td class="label">工单编号</td>
				<td>
					<input class="text" type="text" name="eventTitle"/>
				</td>
			</tr>
	</table>
	
		<html:submit styleClass="btn" property="method.approvalList"
			styleId="method.approvalList" value="查询"></html:submit>
	</form>
	</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>

<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="pnrResMeteringAction.do?method=findSheetMeteringList" 
		partialList="true" size="${size}">
		<display:column property="eventTitle" title="工单编号" />
		<display:column property="eventTypeName" title="计次类型" />
		<display:column property="price" title="单价" />
		<display:column property="formula" title="公式" />
		<display:column property="formulavalue" title="计算公式" />
		<display:column property="eventnumber" title="次数" />
		<display:column property="charging" title="费用" />
		<display:column title="异常内容">
			<c:forTokens items="${list.physicalproperty }" delims=";" var="array">
				<c:out value="${array}"></c:out>;<br/>
			</c:forTokens>
		</display:column>
		
	</display:table>
</logic:present>
	</br>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
