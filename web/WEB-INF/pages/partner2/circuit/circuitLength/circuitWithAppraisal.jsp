<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<script type="text/javascript">
function openQuery(handler){
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
var myJ = jQuery.noConflict();
myJ(function(){
	myJ("input[name='showAppraisals']").click(function(event){
		//点击后防止多次提交
		myJ(this).attr('disabled','disabled');
		var buttonId=event.target.id;
		var proxyScaleId=buttonId.substring(7,buttonId.length);
		myJ('input#proxyScaleId').val(proxyScaleId);
		var monitorCompany=myJ(this).parents('tr').find('td').eq(3).find('input').val();
		var _AppraisalsWindow = window.open("${app}/partner2/appraisal.do?method=list&proxyScaleType=circuit&proxyScaleId="+proxyScaleId,null,"left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes");
		//窗口打开后恢复按钮
		myJ(this).removeAttr('disabled');
	});
	
		myJ('select#country').html('<option value="">请选择县</option>').attr('disabled',true);
	myJ('select#monitorCompany').html('<option value="">请选择代维公司</option>').attr('disabled',true);
	myJ.ajax({
		  type:"POST",
		 url: "${app}/partner/baseinfo/partnerDepts.do?method=retriveCCC",
		  data: "flag=city&parentAreaId=10",
		  success: function(jsonMsg){
        	  var cityHtml =myJ.parseJSON(jsonMsg).city;
			  myJ('select#city').html(cityHtml);
      	  }
	});
	
	myJ('select#city').bind('change',function(event){
		var cityValue = myJ(this).val();
		if(cityValue==""){
			 myJ('select#country').html('<option value="">请选择县</option>').attr('disabled',true);
		}else{
			myJ('select#country').html('<option value="">载入中...</option>').attr('disabled',false);
			myJ.ajax({
				  type:"POST",
		  		  url: "${app}/partner/baseinfo/partnerDepts.do?method=retriveCCC",
				  data: "flag=country&parentAreaId="+cityValue,
		  		  success: function(jsonMsg){
        		  var countryHtml =myJ.parseJSON(jsonMsg).country;
        		  var monitorCompanyHtml =myJ.parseJSON(jsonMsg).monitorCompany;
			 	  myJ('select#country').html(countryHtml);
			 	  myJ('select#monitorCompany').html(monitorCompanyHtml);
      	  		}
			});
		}
	});
	
});


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
	myJ("input:disabled").removeAttr("checked");
}
</script>

<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>
<div style="border:1px solid #98c0f4;padding:5px;width:98%;"
	class="x-layout-panel-hd"><img
	src="${app}/images/icons/search.gif" align="absmiddle"
	style="cursor:pointer" /> <span id="openQuery" style="cursor:pointer"
	onclick="openQuery(this);">查询界面</span></div>

<div id="listQueryObject"
	style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<html:form action="circuitLength.do?method=goToWithAppraisal" method="post">
	<table id="sheet" class="formTable">
	
		<tr>
			<td class="label">年度</td>
			<td class="content"><select size='1' style="width:95%"
				name='yearFlag' id='yearFlag'
				class='select'>
				<option value=''>请选择</option>
				<option value='2008'>2008年</option>
				<option value='2009'>2009年</option>
				<option value='2010'>2010年</option>
				<option value='2011'>2011年</option>
				<option value='2012'>2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
			</select></td>
			<td class="label">月度</td>
			<td class="content"><select size='1' style="width:95%"
				name='monthFlag' id='monthFlag'
				class='select'>
				<option value=''>请选择</option>
				<option value='1'>1</option>
				<option value='2'>2</option>
				<option value='3'>3</option>
				<option value='4'>4</option>
				<option value='5'>5</option>
				<option value='6'>6</option>
				<option value='7'>7</option>
				<option value='8'>8</option>
				<option value='9'>9</option>
				<option value='10'>10</option>
				<option value='11'>11</option>
				<option value='12'>12</option>
			</select></td>
		</tr>
		<tr>
			<td class="label">分公司*</td>
			<td>
				<select id='city' name="city"></select>
			</td>
			<td class="label">归属县公司*</td>
			<td>
				<select id='country' name="country"></select>
			</td>
		</tr>
	<tr>
		<td colspan='4' align='center' class='label'>
			<html:submit styleClass="btn" property="method.send"
		styleId="method.save" value="提交查询结果"></html:submit>
		</td>
	</tr>
	</table>
	
</html:form></div>

<!-- Information hints area end-->
<logic:present name="circuitLengthList" scope="request">
	<display:table name="circuitLengthList" cellspacing="0" cellpadding="0"
		id="circuitLengthList" pagesize="15" class="table circuitLengthList"
		export="false" requestURI="/duty/circuitLength.do?method=list"
		sort="list" partialList="true" size="resultSize">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber" value="${circuitLengthList.id}" id="${circuitLengthList.id}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="分公司">
			<eoms:id2nameDB id="${circuitLengthList.city}"
				beanId="tawSystemAreaDao" />
			<input type="hidden" value="${circuitLengthList.city}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归属县公司">
			<eoms:id2nameDB id="${circuitLengthList.country}"
				beanId="tawSystemAreaDao" />
				<input type="hidden" value="${circuitLengthList.country}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司">
			<eoms:id2nameDB id="${circuitLengthList.monitorCompany}"
				beanId="partnerDeptDao" />
				<input type="hidden" value="${circuitLengthList.monitorCompany}"/>
		</display:column>
		<display:column property="cableLength" sortable="true"
			headerClass="sortable" title="光缆长度" />
		<display:column property="yearFlag" sortable="true"
			headerClass="sortable" title="年度" />
		<display:column property="monthFlag" sortable="true"
			headerClass="sortable" title="月度" />
		<display:column property="cableLength1" sortable="true"
			headerClass="sortable" title="架空光缆" />
		<display:column property="cableLength2" sortable="true"
			headerClass="sortable" title="管道（直埋）光缆" />
		<display:column property="cableLength3" sortable="true"
			headerClass="sortable" title="同路由架空光缆" />
		<display:column property="cableLength4" sortable="true"
			headerClass="sortable" title="同路由管道（直埋）光缆" />
		<display:column property="cableLength5" sortable="true"
			headerClass="sortable" title="空闲管道" />
		<display:column sortable="false" title="记录详情" media="html">
			<a id="${circuitLengthList.id }"
				href="circuitLength.do?method=goToSingleDetail&id=${circuitLengthList.id}"
				target="blank" shape="rect">详情</a>
		</display:column>
		<display:column sortable="false" title="编辑" media="html">
			<a id="${circuitLengthList.id }"
				href="circuitLength.do?method=goToEdit&id=${circuitLengthList.id}"
				target="blank" shape="rect">编辑</a>
		</display:column>
		<display:column title="关联考核模板">
			<c:if test="${circuitLengthList.isWithAppraisal eq 'no'}">
				<input type="button" id="button_${circuitLengthList.id}" name="showAppraisals" class="btn" value="添加考核模板"/>
			</c:if>
			<c:if test="${circuitLengthList.isWithAppraisal eq 'yes'}">
				<font color="red">已关联考核</font>
			</c:if>
		</display:column>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
	<input type="hidden" id="proxyScaleId" value="" />
	<input type="hidden" id="proxyScaleType" value="circuit" />
	<div id="relateCircuit" style="display: none;">
			<input type="button" name="circuitIds" id="addCirToContract" value="确认关联"/>
	</div>
</logic:present>

<logic:notPresent name="circuitLengthList" scope="request">
	无该条件下记录
</logic:notPresent>

<%@ include file="/common/footer_eoms.jsp"%>
