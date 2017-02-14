<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<style>
	.redStar {
		color:red;
	}
</style>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
 
<div align="center"><b>电费费用记录-增加页面</div><br><br/><%--

<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer"	>从Excel导入</span>
</div>

<div id="listQueryObject"
			style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="electricity.do?method=importData" method="post" 	enctype="multipart/form-data" id="importForm" name="importForm">
				<fieldset>
					<legend>
						从Excel导入
					</legend>
					<table class="formTable">
						<tr>
							<td class="label">
								选择Excel文件
							</td>
							<td width="35%">
								<input id="importFile" type="file" name="importFile"	class="file" alt="allowBlank:false" />
							</td>
						</tr>
						<tr>
							<td class="label">
								模板下载
							</td>
							<td>
								<input type="button" class="btn" value="下载导入文件模板" 
										onclick="javascript:location.href='${app}/partner/property/electricity.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/>

--%>
<form action="${app}/partner/property/electricity.do?method=savePnrElectricityBills" method="post" id="pnrElectricityBillsForm" name="pnrElectricityBillsForm" >
	<!-- 隐藏域 begin -->
	<input type="hidden" id="id" name="id" value="${pnrElectricityBills.id}" />
	<input type="hidden" id="createTime" name="createTime" value="${pnrElectricityBills.createTime}" />
	<!-- 隐藏域 end -->
	<table id="sheet" class="formTable">
					<tr>
						<td class="label">
						 	合同编码<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="relatedAgreementNo" name="relatedAgreementNo" value="${pnrElectricityBills.relatedAgreementNo}" alt="allowBlank:false" />
						</td>
						<td class="label">
						 	合同名称<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="relatedAgreementName" name="relatedAgreementName" value="${pnrElectricityBills.relatedAgreementName}" alt="allowBlank:false" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	合同类型
						</td>
						<td class="content" >
								<eoms:comboBox id="relatedAgreementType" name="relatedAgreementType" initDicId="12501" defaultValue="1250101" styleClass="input select"  alt="allowBlank:false"  />
						</td>
						<td class="label">
						 	所属行政区域<span class="redStar">*</span>
						</td>
						<td class="content" >
							   <c:set var="boxData">[{id:'${pnrElectricityBills.relatedDistrict}',name:'<eoms:id2nameDB id="${pnrElectricityBills.relatedDistrict}" beanId="tawSystemAreaDao"/>'}]</c:set>
								<input type="text" name="textReviewer_6" id="textReviewer_6" class="text"  alt="allowBlank:false" />
								<input type="button" name="treeBtn_6" id="treeBtn_6" value="请选择所属行政区域!" class="btn" />
						  		<input type="hidden" name="relatedDistrict" id="relatedDistrict"/>
						  		<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
									rootText='所属区域' valueField="relatedDistrict" handler="treeBtn_6" textField="textReviewer_6"
									checktype="" single="true" data="${boxData}"></eoms:xbox>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	甲方
						</td>
						<td class="content" >
								<input class="text" type="text" id="relatedParta" name="relatedParta" value="${pnrElectricityBills.relatedParta}"  />
						</td>
						<td class="label">
						 	乙方<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="relatedPartb" name="relatedPartb" value="${pnrElectricityBills.relatedPartb}" alt="allowBlank:false" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	上期读数<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="lastNum" name="lastNum" value="${pnrElectricityBills.lastNum}" alt="allowBlank:false" />
						</td>
						<td class="label">
						 	本期读数<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="nowNum" name="nowNum" value="${pnrElectricityBills.nowNum}" alt="allowBlank:false" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	单价（单位：元）<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="univalency" name="univalency" value="${pnrElectricityBills.univalency}" 
								alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,8})?$/,re_vt:'请输入整数或小数（小数位不超过8位）'"/>
						</td>
						<td class="label">
						 	应付金额（单位：元）<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="mustPayMoney" name="mustPayMoney" value="${pnrElectricityBills.mustPayMoney}"
								alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,2})?$/,re_vt:'请输入整数或小数（小数位不超过2位）'" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	已付金额（单位：元）<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="paidMoney" name="paidMoney" value="${pnrElectricityBills.paidMoney}" 
								alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,2})?$/,re_vt:'请输入整数或小数（小数位不超过2位）'"/>
						</td>
						<td class="label">
						 	未付金额（单位：元）<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="unpaidMoney" name="unpaidMoney" value="${pnrElectricityBills.unpaidMoney}" 
								alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,2})?$/,re_vt:'请输入整数或小数（小数位不超过2位）'"/>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	付款方式<span class="redStar">*</span>
						</td>
						<td class="content" >
								<eoms:comboBox id="payWay" name="payWay" initDicId="12503" defaultValue="${pnrElectricityBills.payWay}" styleClass="input select"  alt="allowBlank:false" />
						</td>
						<td class="label">
						 	付款周期<span class="redStar">*</span>
						</td>
						<td class="content" >
								<eoms:comboBox id="payCircle" name="payCircle" initDicId="12502" defaultValue="${pnrElectricityBills.payCircle}" styleClass="input select"  alt="allowBlank:false" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	经办人<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="manager" name="manager" value="${pnrElectricityBills.manager}" alt="allowBlank:false" />
						</td>
						<td class="label">
						 	结算日期<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input type="text" id="settlementDate" name="settlementDate" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1);"  readonly="true" alt="allowBlank:false,vtext:'请选择结算日期！'" 
								value="<fmt:formatDate value="${pnrElectricityBills.settlementDate}" pattern="yyyy-MM-dd"/>"/>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	结算时间段<span class="redStar">*</span>
						</td>
						<td class="content"  colspan="3">
								<input type="text" id="settlementTimeSectStart" name="settlementTimeSectStart" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1);"  readonly="true" alt="allowBlank:false,vtype:'lessThen',link:'settlementTimeSectEnd',vtext:'结算起始日期必须小于结算截止日期'" 
								value="<fmt:formatDate value="${pnrElectricityBills.settlementTimeSectStart}" pattern="yyyy-MM-dd" />"/>
								<span class="text">至 </span>
								<input type="text" id="settlementTimeSectEnd" name="settlementTimeSectEnd" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1);"  readonly="true" alt="allowBlank:false,'moreThen',link:'settlementTimeSectEnd',vtext:'结算截止日期必须大于结算起始日期'" 
								value="<fmt:formatDate value="${pnrElectricityBills.settlementTimeSectEnd}" pattern="yyyy-MM-dd" />"/><br>
								<span class="redStar" >请以整月为单位如:2012-02-01 至 2012-03-01</span><br>
								<span class="redStar">如果当月日期数值大于或等于15将作为下月费用如:2012-02-16 至 2012-03-16,该结算段作为3月份费用，
								2012-02-12 至 2012-03-12该结算段为2月份费用</span>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	到期提醒对象<span class="redStar">*</span>
						</td>
						<td class="content" colspan="3" >
							<input class="text" type="text" id="dataText" name="dataText"  alt="allowBlank:false" readonly="readonly"/>
							<input type="button" name="userButton" id="userButton" value="选择提醒对象" class="btn"  alt="allowBlank:false" />
							<input type="hidden" name="remindObject" id="remindObject" />
								<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" rootId="1"	rootText='用户树' valueField="remindObject"
								 handler="userButton"	textField="dataText" checktype="user" single="false" data="${sendUserAndRoles}"></eoms:xbox>
							<input onclick="btnAdd(this)" type="button" style="width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/add.png');background-repeat: no-repeat;"  value="" />
							<c:forEach var="phone" items="${phonesList}">
								<tr>
									<td class="label">提醒对象号码</td>
									<td class="content" colspan="3" >
										<input type="text" name="remindObject" id="remindObject" value="${phone}" class="text"/>
										<input onclick="btnAdd(this)" type="button" style="width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/add.png');background-repeat: no-repeat;"  value="" />
										<input onclick="btnDelete(this)" type="button" style="width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/list-delete.gif');background-repeat: no-repeat;"  value="" />
									</td>
								</tr>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	附件列表
						</td>
						<td class="content" colspan="3" >
								 <eoms:attachment name="pnrElectricityBills" property="attachment" 
		         				   scope="request" idField="attachment" appCode="contract" alt="allowBlank:true"/> 	
						</td>
					</tr>
					<tr>
						<td class="label">
						 	备注
						</td>
						<td class="content" colspan="3"  >
								<textarea class="textarea max"  name="remark" id="remark" value="${pnrElectricityBills.remark}">${pnrElectricityBills.remark}</textarea>
						</td>
					</tr>
	</table>
		<input type="hidden" name="payStatus" value="${pnrElectricityBills.payStatus}">
		<input type="hidden" name="refId" value="${pnrElectricityBills.refId}">
		<input type="hidden" name="relatedSite" value="${pnrElectricityBills.relatedSite}">
		<input  type="submit" class="btn"  value="保存信息" />
		<input  type="reset" class="btn"  value="重置" />
</form>

<script type="text/javascript">
	var jq=jQuery.noConflict();
	//添加填写号码的框
	function btnAdd(obj) {
		var tr = jq(obj).parents("tr");//找到所在的tr
		var phoneTr="<tr><td class=\"label\">增加提醒对象号码</td>"
		phoneTr+="<td class=\"content\" colspan=\"3\" ><input type=\"text\" name=\"remindObject\" class=\"text\">";
		phoneTr+=	"   <input onclick=\"btnAdd(this)\" type=\"button\" style=\"width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/add.png');background-repeat: no-repeat;\"  value=\"   \"/>"
		phoneTr+=	"  <input onclick=\"btnDelete(this)\" type=\"button\" style=\"width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/list-delete.gif');background-repeat: no-repeat;\"  value=\"\"/>"
		phoneTr+="</td></tr>";
		tr.after(phoneTr);
	}
	//删除填写号码的框
	function btnDelete(obj) {
		var tr = jq(obj).parents("tr");
		tr.remove();
	}
	Ext.onReady(function(){
        v1 = new eoms.form.Validation({form:'pnrElectricityBillsForm'});
        v1.custom = function() {
        		var nowNum=document.getElementById("nowNum").value;
        		var lastNum=document.getElementById("lastNum").value;
        		if(lastNum>nowNum){
        			alert("本期读数不能小于上期读数!");
        			return false;
        		}
        		return true;
        }
        /*
	    v2 = new eoms.form.Validation({form:'importForm'});
	   	v2.custom = function() {
			var reg = "(.xls)$";
			var file = jq("#importFile").val();
			var right = file.match(reg);
			if(right == null) {
				alert("请选择Excel文件!");
				return false;
			} else {
				return true;
			}
		}
		*/
	});
	function saveImport() {
	//表单验证
	//	if(!v2.check()) {
	//		return;
	//	}
		new Ext.form.BasicForm('importForm').submit({
		method : 'post',
			url : "${app}/partner/property/electricity.do?method=importData",
		  	waitTitle : '请稍后...',
			waitMsg : '正在导入数据,请稍后...',
		    success : function(form, action) {
				alert(action.result.infor);
				jq("#importFile").val("");
			},
			failure : function(form, action) {
				alert(action.result.infor);
				jq("#importFile").val("");
			}
	    });
	 }
	 function openImport(){
	 	var handler = document.getElementById("openQuery");
		var el = Ext.get('listQueryObject'); 
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开导入界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭导入界面";
		}
	}
</script>

<%@ include file="/common/footer_eoms.jsp"%>