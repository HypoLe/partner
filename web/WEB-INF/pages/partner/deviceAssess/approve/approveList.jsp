<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
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
	<form action="${app }/partner/deviceAssess/approve.do?method=list" method="post">
		<table id="sheet" class="formTable">
			<tr>		
				<td class="label">
					事件类型:
				</td>
				<td>
					 <eoms:comboBox name="assessType" id="assessType" initDicId="11221" />
				</td>
				<td class="label">名称：</td>
				<td>
					<input type="text" name="name" />
				</td>
			</tr>
			<tr>		
				<td class="label">
					工单号:
				</td>
				<td>
					 <input type="text" name="sheetNum" />
				</td>
				<td class="label">
				</td>
				<td>
				</td>
			</tr>
	</table>
		<input type="submit" value="查询" class="btn" id="method.list"/>
	</form>
	
	</div>

</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>

<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pageSize}" requestURI="approve.do" 
		partialList="true" size="${size}">
		<display:column title="事件类型">
			<eoms:id2nameDB id="${list.assessType}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column property="name" title="名称" />
		<display:column title="工单号">
			<c:if test="${list.sheetNum!=''}" var="result">
				${list.sheetNum}
			</c:if>
			<c:if test="${!result}">
				无
			</c:if>
		</display:column>
		<display:column property="commitTime" title="提交日期" />
		<display:column  title="审批状态">
			待审批
		</display:column>
		<display:column  title="审批">
			<a href="${app }/partner/deviceAssess/approve.do?method=toApprove&id=${list.id }&assessId=${list.assessId }">
				<img src="${app }/images/icons/edit.gif"><a>
		</display:column>
		<display:column  title="修改">
			<a href="${app }${list.modifyUrl }">
				<img src="${app }/images/icons/edit.gif"><a>
		</display:column>
	</display:table>
</logic:present>
	</br>
	
	</div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
