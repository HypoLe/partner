<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<form action="${app}/partner2/appraisal.do?method=search" method="post">
<div style="border:1px solid #98c0f4;padding:5px;width:98%;" class="x-layout-panel-hd">
工具栏： 
  <img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer"/>
  <span id="openQuery"  style="cursor:pointer" onclick="openQuery(this);">关闭快速查询</span>
</div>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" width="75%">
	<tr>
		<td class="label" align="right">
			考核年份：
		</td>
		<td>
			<select name="yearFlag" id="yearFlag" style="width:150px">
				<option value="">请选择年份</option>
				<option value="${last2Year}">${last2Year}</option>
				<option value="${lastYear}">${lastYear}</option>
				<option value="${thisYear}">${thisYear}</option>
				<option value="${nextYear}">${nextYear}</option>
			</select>
		</td>
		<td class="label" align="right">
			考核月份：
		</td>
		<td>
			<input type="text" value="${monthFlag}" name="monthFlag" class="text numberlt12" id="monthFlag"/>
		</td>
	</tr>
	
	<tr>	
		<td class="label" align="right">
			是否执行:
		</td>
		<td>
			<select name="isExecuted" id="isExecuted" style="width:150px">
				<option value="">请选择</option>
				<option value="yes">是</option>
				<option value="no">否</option>
			</select>
		</td>
	</tr>
	
	<tr>
		<td colspan="4" align="center">
			<input type="submit" value="查询" id="submitSearch" class="button"/>
		</td>
	</tr>
</table>
</div>
<form>

<display:table name="appraisalCountList" cellspacing="0" cellpadding="0"
		id="appraisalCountList" pagesize="${pagesize}"
		class="table appraisalList" export="false"
		requestURI="index.do?method=evaUndoList" sort="list" partialList="true"
		size="${size}">
		<display:column property="yearFlag"  sortable="true"
			headerClass="sortable" title="年" />
		<display:column property="monthFlag"  sortable="true"
			headerClass="sortable" title="月" />
		<display:column sortable="true"
			headerClass="sortable" title="考核模板">
			<a href="${app}/partner2/appraisal.do?method=showDetail&templateId=${appraisalCountList.apprailTemplateId }" target="_blank">
				${appraisalCountList.appraisalTemplateName}
			</a>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维规模类型">
			<c:if test="${appraisalCountList.proxyScaleType eq 'baseStation'}">
				基站类
			</c:if>
			<c:if test="${appraisalCountList.proxyScaleType eq 'circuit'}">
				线路类
			</c:if>
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="代维规模数据详情">
			<c:if test="${appraisalCountList.proxyScaleType=='baseStation'}">
				<a href="${app}/partner2/baseStation/baseStationMain.do?method=showDetail&id=${appraisalCountList.proxyScaleId}"
				target="_blank">详情</a>
			</c:if>
			<c:if test="${appraisalCountList.proxyScaleType=='circuit'}">
				<a href="${app}/partner2/circuit/circuitLength.do?method=goToSingleDetail&id=${appraisalCountList.proxyScaleId}"
				target="blank">详情</a>
			</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="是否执行">
			<c:if test="${appraisalCountList.isExecuted eq 'yes'}">
				已执行
			</c:if>
			<c:if test="${appraisalCountList.isExecuted eq 'no'}">
				未执行
			</c:if>
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="总分">
			<c:if test="${appraisalCountList.isExecuted eq 'yes'}">
				${appraisalCountList.appraisalRealScore}
			</c:if>
			<c:if test="${appraisalCountList.isExecuted eq 'no'}">
				无
			</c:if>
		</display:column>
</display:table>

<script type="text/javascript">
var myJ=$.noConflict();
	 myJ(function(){
	 	myJ('select#yearFlag').find("option[value="+'${yearFlag}'+"]").attr('selected','selected');
	 	myJ('select#isExecuted').find("option[value="+'${isExecuted}'+"]").attr('selected','selected');
		myJ("input[class*='number']").keyup(function(){
			if(myJ(this).val().charAt(0)=='0'){
				myJ(this).val('');
			}else{
				myJ(this).val(myJ(this).val().replace(/[^0-9]+/,''));
				var inputClass=myJ(this).attr('class');
				var maxmum=inputClass.substring(inputClass.indexOf('numberlt')+8);
				if(parseInt(myJ(this).val())>parseInt(maxmum)){
					myJ(this).val(maxmum);
				}	
			}
		});
		myJ("input[class*='number']").blur(function(){
			if(myJ(this).val().charAt(0)=='0'){
				myJ(this).val('');
			}else{
				myJ(this).val(myJ(this).val().replace(/[^0-9]+/,''));
				var inputClass=myJ(this).attr('class');
				var maxmum=inputClass.substring(inputClass.indexOf('numberlt')+8);
				if(parseInt(myJ(this).val())>parseInt(maxmum)){
					myJ(this).val(maxmum);
				}	
			}
		});
	 });
function openQuery(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开快速查询";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭快速查询";
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>