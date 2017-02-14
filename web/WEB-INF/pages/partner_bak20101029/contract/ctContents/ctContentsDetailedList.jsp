<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<jsp:directive.page	import="com.boco.eoms.partner.contract.table.util.CtTableGeneralConstants" />
<jsp:directive.page	import="java.util.*" />
<jsp:directive.page	import="com.boco.eoms.base.util.*" />
<jsp:directive.page	import="com.boco.eoms.partner.contract.contents.webapp.form.*" />


<style type="text/css">
.small {
	width: 10px;
}
</style>

<script type="text/javascript">
	function choose(checkbox){
		eoms.util.checkAll(checkbox,'ids');
	}

	var checkflag = "false";
	function choose() {
		var objs = document.getElementsByName("ids");
		if(checkflag=="false"){
			for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			objs[i].checked = true;
      			checkflag="true";
			}
		}else if(checkflag=="true"){
			for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			objs[i].checked = false;
      			checkflag="false"
			}
		}
	}
	
	function fillSearchUrl(){
		var url = "${app}/partner/contract/ctContentss.do?method=takeFee";
		var tpa = document.getElementById("partyAId");
		var tpb = document.getElementById("partyBId");
		var cn  = document.getElementById("ctName");
		var cp  = document.getElementById("planName")
		if(tpa.value != "")
		{
			url += "&partyA=" + tpa.value;
		}
		if(tpb.value != ""){
			url += "&partyB=" + tpb.value;
		}
		if(cn.value != ""){
			url += "&ctName=" + cn.value;
		}
		if(cp.value != ""){
			url += "&ctPlan=" + cp.value;
		}
		window.location.href = url;
	}
</script>

		<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="partyAId" handler="txtPartyA"
		textField="txtPartyA"
		checktype="dept" single="true">
		</eoms:xbox>
		<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="partyBId" handler="txtPartyB"
		textField="txtPartyB"
		checktype="dept" single="true">
		</eoms:xbox>


	
	<table class="formTable">
	<caption>
		<div class="header center">付费计划查询</div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			甲方：
		</td>
		<td>
		<input type="text" id="txtPartyA" class="text" />
			<input type="hidden" name="partyAId" id="partyAId" maxLength="32" class="text medium" /> 		
		</td>
		<td class="label" style="vertical-align:middle">
		乙方：
		</td>
		<td>
		<input type="text" id="txtPartyB" class="text" />
			<input type="hidden" name="partyBId" id="partyBId" maxLength="32" class="text medium" /> 
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同名称：
		</td>
		<td>
		<input type="text" id="ctName" class="text" />
		</td>
		<td class="label" style="vertical-align:middle">
		付款计划：
		</td>
		<td>
		<input type="text" id="planName" class="text" />
		</td>
	</tr>
	</table>
	<input type="submit" class="btn" value="搜索" onclick="fillSearchUrl();" />
	<!-- 
	<select id="Select1" name="D1">
        <option value="1">协议</option>
        <option value="2">付款计划</option>
        <option value="3">甲方</option>
        <option value="4">乙方</option>
    </select>
     -->
	<% int rowIndex = 0; %>
	<% int count = 0; %>
	<% Integer i1 = 0, i2 = 0; %>
	<% List dataList = (List)request.getAttribute("tabledata"); %>
	<% CtContentsPlanForm ctcontentForm1 = null; %>
	<% CtContentsPlanForm ctcontentForm2 = null; %>
	<% Integer totalRows = (Integer)request.getAttribute("resultSize"); %>
	<% List payFee = (List)request.getAttribute("contentsPayFee"); %>
	<% List group = (List)request.getAttribute("contentsGroup"); %>
	<% List factPayFee = (List)request.getAttribute("contentsFactPayFee"); %>
	<% String ctID = ""; %>
	<% Float stepPay = Float.valueOf(0.0f); %>
	<% Float factStepPay = Float.valueOf(0.0f); %>
	<display:table name="tabledata" cellspacing="0"
		cellpadding="0" id="tabledata" pagesize="${pageSize}"
		class="table ctTableGeneralList" export="false"
		requestURI="${app}/partner/contract/ctContentss.do"
		sort="list" partialList="true" size="resultSize">
	<display:column title="合同名称" >
	<a target="_blank" href="${app}/partner/contract/ctContentss.do?method=detailAgr&id=${tabledata.ctContentsID}" >${tabledata.ctTitle}</a>
	</display:column>
	<display:column property="ctPartyAName" title="甲方" />
	<display:column property="ctPartyBName" title="乙方" />
	<display:column title="付款计划" >
	<a target="_blank" href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=${tabledata.ctfeeInfoID}">${tabledata.ctfeeInfoName}</a>
	</display:column>
	<display:column property="ctFeeRemark" title="付款备注" />
	<display:column property="ctPayFee" title="合同金额" />
	<display:column  title="已付金额">
	${tabledata.ctfactPayFee }
	<% stepPay += (Float)payFee.get(rowIndex); %>
	<% factStepPay += (Float)factPayFee.get(rowIndex); %>
	<% ctcontentForm1 = (CtContentsPlanForm)dataList.get(rowIndex); %>
	<% if(rowIndex >= totalRows - 1){ %>
	<% 	ctcontentForm2 = null; %>
	<% }else{ %>
	<% 	ctcontentForm2 = (CtContentsPlanForm)dataList.get(rowIndex + 1); %>
	<% } %>
	<% if(ctcontentForm2 == null || ctcontentForm1.getCtContentsID() != ctcontentForm2.getCtContentsID()){ %>
	<%	 count = 0; %>
		</tr><tr>
		<tr>
		<td colspan="7" bgcolor="#99FFFF"><div align="center">总金额：<% out.print(stepPay); %><div/>实际付款金额：<% out.print(factStepPay); %></div></td> 
		</tr>
	<%     stepPay = 0.0f;%>
	<%     factStepPay = 0.0f;%>
	<%   } %>

	<% rowIndex++; %>
	
	</display:column>
	
	</display:table>

<%@ include file="/common/footer_eoms.jsp"%>
