<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
	if(parent.frames['arcGis-page'].map!=null){
		parent.frames['arcGis-page'].mapCenterAndZoom(true,true);
		}
	parent.removeblock();
});
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
	function ff(){
	parent.addblock();
	}
	
	
	function myreset(){
		jq("#planNameStringLike").val("");
		jq("#specialty").val("");
		jq("#yearStringEqual").val("");
		jq("#monthStringEqual").val("");
		jq("#partnerDeptId").val("");
		jq("#partnerDeptName").val("");
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
	 style="display:online;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	
	<!-- 
	<content tag="heading">
	<c:out value="请输入查询条件" />
	</content>
	 -->
	 
	<html:form action="inspectPlanGisAction.do?method=getAllInspectMainPlanForArcGis" method="post">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">计划名称:</td>
				<td><input class="text" type="text" id="planNameStringLike" name="planNameStringLike" value="${planNameStringLike}"/></td>
				<td class="label">巡检专业：</td>
				<td>
					<eoms:comboBox name="specialtyStringEqual" id="specialty" defaultValue="${specialtyStringEqual}" styleClass="select"
					initDicId="11225" alt="allowBlank:false"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					年度
				</td>
				<td>
					<eoms:dict key="dict-partner-inspect" selectId="yearStringEqual" dictId="yearflag" defaultId="${yearStringEqual}"
					beanId="selectXML" cls="select" />
				</td>
				<td class="label">
					月份
				</td>
				<td>
					<eoms:dict key="dict-partner-inspect" selectId="monthStringEqual" dictId="monthflag"  defaultId="${monthStringEqual}"
					beanId="selectXML" cls="select" />
				</td>
			</tr>
			<tr>		
				<td class="label">
					代维公司:
				</td>
				<td>
					<input type="text"  class="text"  name="partnerDeptName" id="partnerDeptName"  
						value="<eoms:id2nameDB id="${partnerDeptIdStringEqual}" beanId="partnerDeptDao"/>" alt="allowBlank:false" readonly="readonly"/>
					<input name="partnerDeptIdStringEqual" id="partnerDeptId"  value="${partnerDeptIdStringEqual}" type="hidden" />
					<eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
						rootId="" rootText="公司树"  valueField="partnerDeptId" handler="partnerDeptName" 
						textField="partnerDeptName" checktype="dept" single="true" />	
				</td>
				<td class="label"></td>
				<td></td>
			</tr>
	</table>
	
		<input type="submit" name="submit" class="btn" value="查询">
					&nbsp;&nbsp;
			<input type="button" onclick="myreset()"  id="reset" class="btn" value="重置">
	</html:form>
	</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr><!--
计划完成进度   total:${size}  hashDone:<br>
实际完成进度   total:${size}  hashDone:<br>

 Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="inspectPlanGisAction.do?method=getAllInspectMainPlanForArcGis" 
		partialList="true" size="${size}">
		<display:column property="planName" title="巡检计划名称" />
		<display:column title="巡检专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="tawSystemDictTypeDao" />	
		</display:column>
		<display:column  title="代维公司" >
		<eoms:id2nameDB id="${list.partnerDeptId}" beanId="partnerDeptDao"/>
		</display:column>
		<display:column title="巡检日期" >
			<c:if test="${!empty list.year}" var="result">
				${list.year}年${list.month}月
			</c:if>
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="查看巡检资源"
			media="html">
			<a id="${list.id }"
				href="${app }/partner/inspect/inspectPlanGisAction.do?method=getInspectPlanMainDetailForArcGis&id=${list.id}&specialty=${list.specialty}"
				<%---target="blank" --%>shape="rect" onclick="ff();"> <img
				src="${app }/images/icons/table.gif"> </a>
		</display:column>
		
	</display:table>
</logic:present>
	</br>
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
