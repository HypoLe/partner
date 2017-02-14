<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>
<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">

<content tag="heading">
	<div class="header center">
		服务协议管理列表
	</div>
</content>

<br/>
<html:form action="/pnrFeeInfoMains.do?method=contractRelateProtocol"
	styleId="pnrAgreementMainForm1" method="post">
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
		<td class="label" style="vertical-align: middle">
				服务类型
			</td>
			<td class="content">
				<select name="abilityType" id="abilityType">
					<option value="baseStationProxy" id="baseStationProxy">
						基站代维
					</option>
					<option value="circuitProxy" id="circuitProxy">
						线路代维
					</option>
				</select>
			</td>
	</tr>
	
	<tr>
		<td colspan="4" align="center">
			<input type="hidden" name="contractStartTime" id="contractStartTime" />
			<input type="hidden" name="contractEndTime" id="contractEndTime"/>
			<input type="hidden" name="serverType" id="serverType"/>
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
		requestURI="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=contractRelateProtocol"
		sort="list" partialList="true" size="resultSize">
	<display:column media="html" title="${myTitleSelectBtn}">
			<input type="checkbox" name="cardNumber" value="${pnrAgreementMainList.id}" />
			<input type="hidden" name="agreementName" value="${pnrAgreementMainList.agreementName}"/>
			<input type="hidden" name="serverTypeName" value="${pnrAgreementMainList.serverType}"/>
			<input type="hidden" name="monitorCompanyName" value="${pnrAgreementMainList.monitorCompany}"/>
	</display:column>
	<display:column property="agreementNO" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.agreementNO"  paramId="id" paramProperty="id"/>

	<display:column property="agreementName" sortable="true" 
			headerClass="sortable" titleKey="pnrAgreementMain.agreementName"  paramId="id" paramProperty="id"/>
	<display:column property="startTime" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.startTime"  format="{0,date,yyyy-MM-dd}" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.endTime"  format="{0,date,yyyy-MM-dd}" paramId="id" paramProperty="id"/>
			
	<display:column sortable="false"
			headerClass="sortable" titleKey="pnrAgreementMain.serverType" >
		<c:if test="${pnrAgreementMainList.serverType == 'circuitProxy'}">线路代维</c:if>
		<c:if test="${pnrAgreementMainList.serverType == 'baseStationProxy'}">基站代维</c:if>
	</display:column>		
			
	<display:column sortable="false"
			headerClass="sortable" titleKey="pnrAgreementMain.monitorCompany"  >
		<eoms:id2nameDB id="${pnrAgreementMainList.monitorCompany}" beanId="tawSystemAreaDao" />
	</display:column>

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
	<display:column title="查看" headerClass="imageColumn">
		<a href="${app}/partner/agreement/pnrAgreementMains.do?method=detail&id=${pnrAgreementMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
</display:table>
<br/>
<input type="button" id="addAgreementButton" value="添加关联协议" class="btn"/>
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
	<!-- 
	<c:if test="${state == ''||state == '2'||state == '5'}">
			<c:if test="${retPnr==''||retPnr==null}">
				<c:out value="${buttons}" escapeXml="false" />
			</c:if>
	</c:if>
	 -->
</fmt:bundle>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {

		myJ('#'+'${serverType}').attr('selected','selected');
		myJ('#abilityType').change(function(){
			myJ('#serverType').val(myJ(this).val());
		});
		document.getElementById('contractStartTime').value = window.opener.document.getElementById('startTime').value;
		document.getElementById('contractEndTime').value = window.opener.document.getElementById('endTime').value;
		
		myJ('input#addAgreementButton').bind('click',function(event){
		
			var cardNumberList = document.getElementsByName("cardNumber");
			var agreementNameList = document.getElementsByName("agreementName");
			var serverTypeArray = document.getElementsByName("serverTypeName");
			var monitorCompanyArray = document.getElementsByName("monitorCompanyName");
			var agreementNames="";
			var idResult = "";
			var monitorCompanys = "";
			for (i = 0 ; i < cardNumberList.length; i ++) {
				if (cardNumberList[i].checked) {
					var myTempStr=cardNumberList[i].value;
					var myAgreementName = agreementNameList[i+1].value;
					idResult+=myTempStr.toString()+";"
					agreementNames += myAgreementName.toString()+";"
				}
			}
			if (idResult == "") {
				alert("请选择需要关联的服务协议");
				return false;
			} else {
				window.opener.document.getElementById('protocolIds').value += idResult.toString();
				var protocolIds = idResult.toString().split(';');
				var protocolNames = agreementNames.toString().split(';');
				var myProtocolTable = myJ(window.opener.document.getElementById('myProtocolTable'));
				myProtocolTable.show();
				if(confirm("确认添加？")){
					
					//判定页面上已经选择的代维公司和代维类型是否有重复
					var iServerTypeArray = myJ(window.opener.document.getElementById('serverTypeArray')).val();
					var iMonitorCompanyArray = myJ(window.opener.document.getElementById('monitorCompanyArray')).val();
					
					if(iServerTypeArray!=""&&iMonitorCompanyArray!=""){
					var canAdd = "yes";
						myJ.each(serverTypeArray,function(index,sT){
							myJ.each(monitorCompanyArray,function(index,mC){
							if(myJ.inArray(sT,iServerTypeArray)&&myJ.inArray(mC,monitorCompanyArray)){
								canAdd =  "no";
								return;
							}
						});
						});
					}
					if(canAdd=="no"){
						alert("该代维公司和服务协议类型已选择，请勿重复添加");
						return false;
					}
				
		 			alert('添加成功');
		 			myJ(window.opener.document.getElementById('serverTypeArray')).val(serverTypeArray);
		 			myJ(window.opener.document.getElementById('monitorCompanyArray')).val(monitorCompanyArray);
		 			myJ(window.opener.document.getElementById('abilityType')).val(myJ('#abilityType').val());
		 			for(var i=0;i<protocolIds.length-1;i++){
		 				myProtocolTable.append("<tr><td class='content'>"+protocolNames[i]+"</td>"
		 				+"<td class='content'><a href="+"${app}/partner/agreement/pnrAgreementMains.do?method=detail&id="+protocolIds[i]+" target='_blank'>"+"<img src='${app}/images/icons/search.gif'/></a></td>"
		 				+"<td class='content serverType'>"+serverTypeArray[i].value+"</td>"
		 				+"<td class='content monitorCompanys'>"+monitorCompanyArray[i].value+"</td>"
		 				+"<td class='content'><img src='${app}/nop3/images/icon.gif'"+"onmousedown='deleteWeight(this);' style='cursor:pointer;'/>"+"</td>"+"<input type='hidden' value="+protocolIds[i]+" name='agreementId'/>"+"</tr>");
		 			}
					window.close();
				}
				else{
					return false;
				}
			}
		});
});//End myJ(function()

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

function selectAll(){
	var cardNumberList = document.getElementsByName("cardNumber");
	//Judge whether it has been checked first. One element is enough for our decision.
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
</script>

<%@ include file="/common/footer_eoms.jsp"%>