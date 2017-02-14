<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>



<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<style type="text/css">
.small {
	width: 10px;
}

</style>

<script type="text/javascript">
var myjs=jQuery.noConflict();
Ext.onReady(function(){
    Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
})
  
  
  
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
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>


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
<html:form action="publicityCatchline.do?method=approvaledList" method="post">
		<table id="sheet" class="formTable">
			<tr>		
				<td class="label">
					项目名称:
				</td>
				<td class="label" colspan="3"> 
					<html:text property="publicityCatchline" styleClass="text"/>
					<input type="hidden" name="publicityCatchlineStringLike" value=""/>
			</tr>
			
				<tr >
				<td class="label" colspan="4">宣传标语创建时间：</td>
				</tr>
				<tr >
				<td class="label">从</td><td class="label">
					<input type="text" class="text" name="creatTimeA"
						onclick="popUpCalendar(this, this,null,null,null,true,-1);"
						alt="vtype:'lessThen',link:'finishTimeEnd',vtext:'开始时间不能晚于结束时间'"
						id="creatTimeStart" value="${creatTimeBegin}"/>
						<input type="hidden" name="creatTimeA" value="">
				</td>
				<td class="label">到</td>
				<td class="label">
					<input type="text" class="text" name="creatTimeB"
						onclick= "popUpCalendar(this, this,null,null,null,true,-1);"
                        alt="vtype:'moreThen',link:'mediaTimeStart',vtext:'结束时间不能早于开始时间'"
						id="creatTimeEnd" value="${creatTimeEnd}"/>
					<input type="hidden" name="creatTimeB" value=""/>
				</td>
			</tr>
	</table>
	
		<html:submit styleClass="btn" property="method.list"
			styleId="method.iist" value="提交查询结果"></html:submit>
			
	</html:form>
	
	</div>

</hr>





 <!-- Information hints area end-->


 <logic:present
	name="publicityCatchlineApprovalList" scope="request">
	<display:table name="publicityCatchlineApprovalList" cellspacing="0" cellpadding="0"
		id="publicityCatchlineApprovalList" pagesize="${pagesize}"
		class="table publicityCatchlineApprovalList" export="true"
		requestURI="publicityCatchline.do" sort="list" partialList="true"
		size="${size}">
		<display:column sortable="true" headerClass="sortable" title="媒体宣传标语">
			${publicityCatchlineApprovalList.publicityCatchline}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核人">
			<eoms:id2nameDB beanId="tawSystemUserDao" id="${publicityCatchlineApprovalList.approvalUser}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="创建时间">
			${publicityCatchlineApprovalList.creatTime}
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${publicityCatchlineApprovalList.id }"
				href="${app}/deviceManagement/publicityCatchline/publicityCatchline.do?method=goToDetail&id=${publicityCatchlineApprovalList.id}"
				target="blank" shape="rect"> <img
				src="${app }/images/icons/table.gif"> </a>
		</display:column>


		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present></div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
