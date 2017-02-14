<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var jq=jQuery.noConflict();
	function deleteInfo(id) {
				if(confirm("确定要删除吗？")){
					Ext.Ajax.request({
						url:"${app}/partner/property/rent.do",
						params:{method:"deletePnrRentBills",id:id},
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
		var formElement=document.getElementById("rentSearchForm");
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
	<div align="center"><b>租赁费用-列表页面</div><br><br/>
	<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA" class="x-layout-panel-hd">
		<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
		<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
	</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="${app}/partner/property/rent.do?method=gotoPnrRentBillsListPage" method="post" id="rentSearchForm">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
					<tr>
						<td class="label">
						 	合同编码
						</td>
						<td class="content" >
								<input class="text" type="text" name="relatedAgreementNoStringLike" value="${relatedAgreementNoStringLike}" />
						</td>
						<td class="label">
						 	合同类型
						</td>
						<td class="content" >
								<eoms:comboBox id="relatedAgreementType" name="relatedAgreementTypeStringLike" initDicId="12501" defaultValue="${relatedAgreementTypeStringLike}" styleClass="input select"   />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	甲方
						</td>
						<td class="content" >
								<input class="text" type="text" name="relatedPartaStringLike" value="${relatedPartaStringLike}"  />
						</td>
						<td class="label">
						 	乙方
						</td>
						<td class="content" >
								<input class="text" type="text" name="relatedPartbStringLike" value="${relatedPartbStringLike}"/>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	经办人
						</td>
						<td class="content" >
								<input class="text" type="text" name="managerStringLike" value="${managerStringLike}" />
						</td>
						<td class="label">
						 	所属行政区域
						</td>
						<td class="content" >
								<c:set var="boxData">[{id:'${relatedDistrictStringLike}',name:'<eoms:id2nameDB id="${relatedDistrictStringLike}" beanId="tawSystemAreaDao"/>'}]</c:set>
								<input type="text" name="textReviewer_10" id="textReviewer_10" class="text"  readonly="readonly" />
								<input type="button" name="treeBtn_10" id="treeBtn_10" value="请选择所属行政区域" class="btn" />
						  		<input type="hidden" name="relatedDistrictStringLike" id="relatedDistrictStringLike"/>
						  		<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
									rootText='所属区域' valueField="relatedDistrictStringLike" handler="treeBtn_10" textField="textReviewer_10"
									checktype="" single="true" data="${boxData}"></eoms:xbox>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	付款周期
						</td>
						<td class="content" >
								<eoms:comboBox id="payCircle" name="payCircleStringLike" initDicId="12502" defaultValue="${payCircleStringLike}" styleClass="input select"  />
						</td>
						<td class="label">
						 	付款方式
						</td>
						<td class="content" >
								<eoms:comboBox id="payWay" name="payWayStringLike" initDicId="12503" defaultValue="${payWayStringLike}" styleClass="input select"  />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	结算日期
						</td>
						<td class="content" colspan="3">
								<input type="text"  id="settlementDateDateGreaterOrEqual" name="settlementDateDateGreaterOrEqual" 
								class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,false,-1);"  readonly="true"  value="${settlementDateDateGreaterOrEqual}"/>
								<span>至</span>
								<input type="text"  id="settlementDateDateLessOrEqual" name="settlementDateDateLessOrEqual" 
								class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,false,-1);"  readonly="true"   value="${settlementDateDateLessOrEqual}"/>
						</td>
				</tr>
			</table>
			<table>
				<tr>
					<td>
							<input type="submit" class="btn" value="查询" />
							<input type="button" class="btn" value="重置" onclick="res();"/>
					</td>
				<input type="hidden" name="payStatus" value="${payStatus}"
				</tr>
			</table>
		</fieldset>
	</form>
</div>
<logic:present name="pnrRentBillsList" scope="request">
	<display:table id="pnrRentBillsList"	name="pnrRentBillsList" 	pagesize="${pagesize}" size="${size}"
					requestURI="${app}/partner/property/rent.do" export="true" 		sort="list" cellspacing="0" cellpadding="0" class="table pnrRentBillsList" partialList="true" ><%--
				<display:column sortable="true" headerClass="sortable" title="合同编码">
					${pnrRentBillsList.relatedAgreementNo}
				</display:column>
				--%><display:column sortable="true" headerClass="sortable" title="合同名称">
					${pnrRentBillsList.relatedAgreementName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="甲方">
					${pnrRentBillsList.relatedParta}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="乙方">
					${pnrRentBillsList.relatedPartb}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属行政区域">
					<eoms:id2nameDB id="${pnrRentBillsList.relatedDistrict}" beanId="tawSystemAreaDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="付款方式">
					<eoms:id2nameDB id="${pnrRentBillsList.payWay}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="付款周期">
					<eoms:id2nameDB id="${pnrRentBillsList.payCircle}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<%--<span style='display:block;width:70'>应付金额</span><span style='display:block;'>(单位:元)</span>--%>
				<display:column sortable="true" headerClass="sortable" title="应付金额(元)">
					${pnrRentBillsList.mustPayMoney}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="已付金额(元)</span>">
					${pnrRentBillsList.paidMoney}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="未付金额(元)">
					${pnrRentBillsList.unpaidMoney}
				</display:column>
				<%--
				<display:column sortable="true" headerClass="sortable" title="结算日期">
					${pnrRentBillsList.settlementDate}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="经办人">
					${pnrRentBillsList.manager}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="到期提醒对象">
					${pnrRentBillsList.remindObject}
				</display:column>
				--%><display:column sortable="false" headerClass="sortable" title="编辑"
								url="/partner/property/rent.do?method=gotoAddPnrRentBillsPage"
								paramProperty="id" paramId="id" media="html">
					<img src="${app}/images/icons/edit.gif">
				</display:column>
				
				<display:column sortable="false" headerClass="sortable" title="详情" media="html">
					<a href="${app}/partner/property/rent.do?method=gotoPnrRentBillsDetailPage&id=${pnrRentBillsList.id}" target="blank" shape="rect">
						<img src="${app}/images/icons/table.gif">
					</a>
				</display:column>
				<display:column sortable="false" headerClass="sortable" title="删除" media="html">
						<img class="delete" src="${app}/images/icons/icon.gif" onclick="deleteInfo('${pnrRentBillsList.id}')"/>
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
