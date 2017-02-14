<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">
<content tag="heading">
	<div class="header center">
		服务协议管理列表
	</div>
</content>
<br/>
<html:form action="/appraisal.do?method=agreementsList"
	styleId="pnrAgreementMainForm" method="post">
<div style="border:1px solid #98c0f4;padding:5px;width:98%;" class="x-layout-panel-hd">
工具栏： 
  <img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer"/>
  <span id="openQuery"  style="cursor:pointer" onclick="openQuery(this);">关闭快速查询</span>
</div>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" width="75%">
	<tr>
		<td class="label" align="right">
			服务协议名称：
		</td>
		<td>
			<input type="text" name="agreementName" class="text"/>
		</td>
		<td class="label" align="right">
			服务协议状态：
		</td>
		<td>
			<select name="state" id="state" style="width:150px">
				<option></option>
				<option value="auditing">新建待确认</option>
				<option value="newReject">新建驳回</option>
				<option value="excute">有效</option>
				<option value="auditing">归档待审核</option>
				<option value="closed">归档</option>
				<option value="closeReject">归档驳回</option>
				<option value="upload">待总结</option>
				<option value="toClose">待归档</option>
				<%--<option value="draft">草稿</option><--%>
			</select>
		</td>
	</tr>
	
	<tr>	
		<td class="label" align="right">
			服务协议开始时间：
		</td>
		<td>
			<input id="startTime" name="startTime" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" alt="vtype:'lessThen',link:'endTime',vtext:'开始时间不能晚于结束时间'"/>
		</td>
		<td class="label" align="right">
			服务协议结束时间：
		</td>
		<td>
			<input id="endTime" name="endTime" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" alt="vtype:'moreThen',link:'startTime',vtext:'结束时间不能早于开始时间'"/>
		</td>
	</tr>
	
	<tr>
		<td colspan="4" align="center">
			<input type="submit" value="查询" id="submitSearch" class="button"/>
		</td>
	</tr>
	</table>
</div>
</html:form>

<c:if test="${resultSize!='0'}">
	<display:table name="pnrAgreementMainList" cellspacing="0" cellpadding="0"
		id="pnrAgreementMainList" pagesize="${pageSize}" class="table pnrAgreementMainList"
		export="false"
		requestURI="${app}/partner2/appraisal.do?method=agreementsList"
		sort="list" partialList="true" size="resultSize">
	<display:column property="agreementNO" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.agreementNO"  paramId="id" paramProperty="id"/>

	<display:column property="agreementName" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.agreementName"  paramId="id" paramProperty="id"/>

	<display:column property="startTime" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.startTime"  format="{0,date,yyyy-MM-dd}" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.endTime"  format="{0,date,yyyy-MM-dd}" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="pnrAgreementMain.state">
		<c:if test="${pnrAgreementMainList.state == '0'}">新建待确认</c:if>
		<c:if test="${pnrAgreementMainList.state == '1'}">新建驳回</c:if>
		<c:if test="${pnrAgreementMainList.state == '2'}">有效</c:if>
		<c:if test="${pnrAgreementMainList.state == '3'}">归档待审核</c:if>
		<c:if test="${pnrAgreementMainList.state == '4'}">归档</c:if>
		<c:if test="${pnrAgreementMainList.state == '5'}">归档驳回</c:if>
		<c:if test="${pnrAgreementMainList.state == '6'}">待总结</c:if>
		<c:if test="${pnrAgreementMainList.state == '7'}">待归档</c:if>
		<c:if test="${pnrAgreementMainList.state == '8'}">草稿</c:if>
	</display:column>
	<display:column title="查看">
		<a href="${app}/partner/agreement/pnrAgreementMains.do?method=detail&id=${pnrAgreementMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" />
		</a>
	</display:column>
	<display:column title="关联考核模板">
		<input type="button" id="${pnrAgreementMainList.id}" name="showAppraisals" class="btn" value="添加考核模板"/>
	</display:column>
	</display:table>
	</c:if>
	<c:if test="${resultSize=='0'}">
		<table  class="formTable"  border="1"   bordercolor="#98C0F4">
		</br>
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >服务协议编号</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">服务协议名称</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label" >服务协议开始时间</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">服务协议结束时间</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">状态</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">查看</td>
			</tr>
			<tr>
				<td  style="text-align:center;"  colspan="6" >暂无记录</td>
			</tr>
		</table>
	</c:if>
	<input type="hidden" id="agreementId" value=""/>
</fmt:bundle>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	myJ("input[name='showAppraisals']").click(function(){
		//点击后防止多次提交
		myJ(this).attr('disabled','disabled');
		var _AppraisalsWindow = window.open("${app}/partner2/appraisal.do?method=list",null,"left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes");
		//窗口打开后恢复按钮
		myJ('input#agreementId').val(myJ(this).attr('id'));
		myJ(this).removeAttr('disabled');
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