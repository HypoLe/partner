<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var jq=jQuery.noConflict();
	function deleteInfo(id) {
				if(confirm("确定要删除吗？")){
					Ext.Ajax.request({
						url:"${app}/partner/property/agreement.do",
						params:{method:"deletePnrPropertyAgreement",id:id},
						success:function(res,opt) {
							Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
						},
						failure:function(res,opt) {
							Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
						}
					});
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
	
	function res(){
		var formElement=document.getElementById("agreementForm");
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
	              inputs[i].value = '';
	         }
     	}
     	 for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	              selects[i].options[0].selected = true;
	         }
     	}
	}
</script>
<div align="center"><b>物业合同-列表页面</div><br><br/>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA" class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff">
	<form action="${app}/partner/property/agreement.do?method=gotoPnrPropertyAgreementListPage" method="post" id="agreementForm">
			<table id="sheet" class="formTable">
				<tr>
						<td class="label">
						 	合同编码
						</td>
						<td class="content" >
								<input class="text" type="text" name="agreementNoStringLike" value="${agreementNoStringLike}" alt="allowBlank:false"/>
						</td>
						<td class="label">
						 	合同类型
						</td>
						<td class="content" >
								<eoms:comboBox id="agreementType"  name="agreementTypeStringLike" initDicId="12501" defaultValue="${agreementTypeStringLike}" styleClass="input select"   />
						</td>
				</tr>
				<tr>
						<td class="label">
						 	所属站点
						</td>
						<td class="content" >
								<input class="text" type="text" name="siteStringLike" value="${siteStringLike}" alt="allowBlank:false"/>
						</td>
						<td class="label">
						 	所属行政区域
						</td>
						<td class="content" >
							<c:set var="boxData">[{id:'${distirctStringLike}',name:'<eoms:id2nameDB id="${distirctStringLike}" beanId="tawSystemAreaDao"/>'}]</c:set>
								<input type="text" name="textReviewer_10" id="textReviewer_10" class="text" readonly="readonly" />
								<input type="button" name="treeBtn_10" id="treeBtn_10" value="请选择所属行政区域" class="btn" />
						  		<input type="hidden" name="distirctStringLike" id="distirctStringLike"/>
						  		<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
									rootText='所属区域' valueField="distirctStringLike" handler="treeBtn_10" textField="textReviewer_10"
									checktype="" single="true" data="${boxData}"></eoms:xbox>
						</td>
				</tr>
				<tr>
						<td class="label">
						 	合同金额（单位：元）
						</td>
						<td class="content" >
								<input class="text" type="text" name="agreementAmountStringEqual" value="${agreementAmountStringEqual}" alt="allowBlank:false"/>
						</td>
						<td class="label">
						 	付款周期
						</td>
						<td class="content" >
								<eoms:comboBox id="payCycle" name="payCycleStringLike" initDicId="12502" defaultValue="${payCycleStringLike}" styleClass="input select"  alt="allowBlank:false" />
						</td>
				</tr>
				<tr>
						<td class="label">
						 	甲方
						</td>
						<td class="content" >
								<input class="text" type="text" name="partaStringLike" value="${partaStringLike}" />
						</td>
						<td class="label">
						 	乙方
						</td>
						<td class="content" >
								<input class="text" type="text" name="partbStringLike" value="${partbStringLike}" alt="allowBlank:false"/>
						</td>
				</tr>
				<tr>
						<td class="label">
						 	甲方签订人
						</td>
						<td class="content" >
								<input class="text" type="text" name="partaSigningPersonStringLike" value="${partaSigningPersonStringLike}" />
						</td>
						<td class="label">
						 	乙方签订人
						</td>
						<td class="content" >
								<input class="text" type="text" name="partbSigningPersonStringLike" value="${partbSigningPersonStringLike}" />
						</td>
				</tr>
				<tr>
						<td class="label">
						 	甲方签订人电话
						</td>
						<td class="content" >
								<input class="text" type="text" name="partaSigningPersonTelStringLike" value="${partaSigningPersonTelStringLike}" />
						</td>
						<td class="label">
						 	乙方签订人电话
						</td>
						<td class="content" >
								<input class="text" type="text" name="partbSigningPersonTelStringLike" value="${partbSigningPersonTelStringLike}" />
						</td>
				</tr>
				<tr>
						<td class="label">
						 	签订日期
						</td>
						<td class="content" colspan="3">
								<input type="text"  id="signDateDateGreaterThan" name="signDateDateGreaterThan" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,false,-1);"  readonly="true" alt="allowBlank:false,vtext:'请选择签订日期！'" value="${signDateDateGreaterThan}"/>
								<span>至</span>
								<input type="text"  id="signDateDateLessThan" name="signDateDateLessThan" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,false,-1);"  readonly="true" alt="allowBlank:false,vtext:'请选择签订日期！'" value="${signDateDateLessThan}"/>
						</td>
				</tr>
				<tr>	
						<td class="label">
						 	截止日期
						</td>
						<td class="content" colspan="3" >
								<input type="text" id="endDateDateGreaterThan" name="endDateDateGreaterThan" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,false,-1);"  readonly="true" alt="allowBlank:false,vtext:'请选择截止日期！'" value="${endDateDateGreaterThan}"/>
								<span>至</span>
								<input type="text" id="endDateDateLessThan" name="endDateDateLessThan" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,false,-1);"  readonly="true" alt="allowBlank:false,vtext:'请选择截止日期！'" value="${endDateDateLessThan}"/>
						</td>
					</tr>
				</table>
				<table>
				<tr>
					<td>
							<input type="submit" class="btn" value="查询" />
							<input type="button" class="btn" value="重置" onclick="res();"/>
					</td>
					<input type="hidden" name="effect" value="${effect}">
				</tr>
			</table>
	</form>
</div>
<logic:present name="pnrPropertyAgreementList" scope="request">
	<display:table id="pnrPropertyAgreementList"	name="pnrPropertyAgreementList" 	pagesize="${pagesize}" size="${size}"
					requestURI="${app}/partner/property/agreement.do" export="false" 		sort="list" cellspacing="0" cellpadding="0"
					 class="table pnrPropertyAgreementList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="所属站点">
					${pnrPropertyAgreementList.site}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="合同名称">
					${pnrPropertyAgreementList.agreementName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="合同类型">
					<eoms:id2nameDB id="${pnrPropertyAgreementList.agreementType}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<%--<display:column sortable="true" headerClass="sortable" title="签订日期">
					${pnrPropertyAgreementList.signDate}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="截止日期">
					${pnrPropertyAgreementList.endDate}
				</display:column>
				--%><display:column sortable="true" headerClass="sortable" title="付款周期">
					<eoms:id2nameDB id="${pnrPropertyAgreementList.payCycle}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属行政区域">
					<eoms:id2nameDB id="${pnrPropertyAgreementList.distirct}" beanId="tawSystemAreaDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="合同金额（元）">
					${pnrPropertyAgreementList.agreementAmount}
				</display:column>
				<%--<display:column sortable="true" headerClass="sortable" title="到期提醒对象">
					${pnrPropertyAgreementList.remindObject}
				</display:column>
				--%><c:if test="${effectFlag}"><!--根据合同是否有效来控制操作权限-->
				<display:column sortable="false" headerClass="sortable" title="编辑"
								url="/partner/property/agreement.do?method=gotoAddPnrPropertyAgreementPage"
								paramProperty="id" paramId="id" media="html">
					<img src="${app}/images/icons/edit.gif">
				</display:column>
				<display:column sortable="false" headerClass="sortable" title="删除" media="html">
					<img class="delete" src="${app}/images/icons/icon.gif" onclick="deleteInfo('${pnrPropertyAgreementList.id}')"/>
				</display:column>
				</c:if>
				<display:column sortable="false" headerClass="sortable" title="详情" media="html">
					<a href="${app}/partner/property/agreement.do?method=gotoPnrPropertyAgreementDetailPage&id=${pnrPropertyAgreementList.id}" target="blank" shape="rect">
						<img src="${app}/images/icons/table.gif"></a>
				</display:column>
				<!-- 当支付周期为其它的时候才有手动添加的功能,支付周期为规律性的需要在待支付列表里添加-->
				<display:column sortable="false" headerClass="sortable" title="添加支付记录" media="html">
					<c:if test="${pnrPropertyAgreementList.payCycle=='1250205'}">
							<a href="${app}/partner/property/agreement.do?method=gotoAddBills&id=${pnrPropertyAgreementList.id}" target="blank" shape="rect">
								<img src="${app}/images/icons/add.png"></a>
					</c:if>
				</display:column>
				<!-- Exclude the formats i do not need. -->
				<display:setProperty name="export.rtf" value="false" />
				<display:setProperty name="export.pdf" value="false" />
				<display:setProperty name="export.xml" value="false" />
				<display:setProperty name="export.csv" value="false" />
			</display:table>
		</logic:present>
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
