<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function(){

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
	
	myJ('div#searchDiv').bind('click',function(event){
		myJ('#searchForm').toggle();
	});
	

	//处理由合同关联基站规模数据的情况,如果windwo.opener存在则说明是由合同关联打开
	if(window.opener){
		myJ('#goToDeleteAll').hide();
		myJ('div#relateCircuit').show();
		myJ('div#relateCircuit').append('<font color=red>(未关联考核的代维规模数据不能选择！)</font>');
		var circuitIds = window.opener.document.getElementById('circuitIds').value;
		var myProxyModelsTable = myJ(window.opener.document.getElementById('myProxyModelsTable'));
		myJ("input:checkbox").removeAttr("disabled");
		if(circuitIds){	
			var circuitIdArray=circuitIds.split(";");
			for(var i=0;i<circuitIdArray.length;i++){
				if(circuitIdArray[i]!=''){
					myJ('input#'+circuitIdArray[i]).attr("disabled","disabled");
				}
			}
		}
		myJ("input#addCirToContract").click(function(){
			if(confirm("确认添加？")){
				var checkedId="";
				var city="";
				var country="";
				var monitorCompany="";
				var myProxyModelsTable=myJ(window.opener.document.getElementById("myProxyModelsTable"));
				myJ("input:checked").each(function(){
					checkedId+=myJ(this).val()+";";
					city=myJ.trim(myJ(this).parent().next().text());
					country=myJ.trim(myJ(this).parent().next().next().text());
					monitorCompany=myJ.trim(myJ(this).parent().next().next().next().text());
					myProxyModelsTable.append("<tr><td class='content'>"+city+"</td>"
	 				+"<td>"+country+"</td>"
	 				+"<td>"+monitorCompany+"</td>"
	 				+"<td>线路类</td>"
	 				+"<td><a href="+"${app}/partner2/circuit/circuitLength.do?method=goToSingleDetail&id="+myJ(this).val()+" target='_blank'>"+"<img src='${app}/images/icons/search.gif'/></a></td>"
	 				+"<td><img src='${app}/nop3/images/icon.gif'"+"onmousedown='deleteCircuit(this);' style='cursor:pointer;'/>"+"</td>"+"<input type='hidden' value="+myJ(this).val()+" name='circuitId'/>"+"</tr>");
				});
				if(checkedId!=""){
					window.opener.document.getElementById('circuitIds').value+=checkedId;
				}
				alert('添加成功！');
				window.close();
			}else{
				return false;
			}
		});
		//只能选择关联了考核的代维数据
			myJ('input:checkbox.no').attr("disabled","disabled");
	}	
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

function deleteAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";"
		}
	}
	if (idResult == "") {
		alert("请选择需要删除的记录");
		return false;
	} else {
		if(confirm("确定要全部删除吗？")){
			$("deleteIds").value=idResult.toString();
			myJ('form#patchDeleteForm').submit();
		}
		else{
			return false;
		}
	}
}

</script>

<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>

<div id="searchDiv" class="x-layout-panel-hd" style="cursor:pointer;border: 1px solid rgb(152, 192, 244); padding: 5px; width: 98%;">快速查询</div>
<div id="searchForm" style="display:none;border-style: none solid solid; border-color:rgb(152, 192, 244); border-width: 0pt 1px 1px; padding: 5px; background-color: rgb(239, 246, 255); width: 98%;">

<html:form action="circuitLength.do?method=search" method="post">
	<table id="sheet" class="formTable">
		<tr>
		<td class="label">分公司*</td>
		<td><select id='city' name="cityStringExpression"></select></td>
		<td class="label">归属县公司*</td>
		<td><select id='country' name="countryStringExpression"></select></td>
		<tr>
			<td class="label">年度</td>
			<td class="content"><select size='1'
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
				<option value='2015'>2015年</option>
			</select></td>
			<td class="label">月度</td>
			<td class="content"><select size='1'
				name='monthFlagStringExpression' id='monthFlagStringExpression'
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
		<td colspan='4' align='center' class='label'>
			<html:submit styleClass="btn" property="method.send"
		styleId="method.save" value="提交查询结果"></html:submit>
		</td>
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
			<input type="checkbox" name="cardNumber" value="${circuitLengthList.id}" id="${circuitLengthList.id}" class="${circuitLengthList.isWithAppraisal}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="分公司">
			<eoms:id2nameDB id="${circuitLengthList.city}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归属县公司">
			<eoms:id2nameDB id="${circuitLengthList.country}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司">
			<eoms:id2nameDB id="${circuitLengthList.monitorCompany}"
				beanId="partnerDeptDao" />
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
			<c:if test="${empty circuitLengthList.contractId}">
			<a id="${circuitLengthList.id }"
				href="circuitLength.do?method=goToEdit&id=${circuitLengthList.id}"
				target="_blank" shape="rect">编辑</a>
			</c:if>
			<c:if test="${not empty circuitLengthList.contractId}">
				<font color="red">此条数据已被合同关联,不能修改</font>
			</c:if>
		</display:column>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
	<input type="hidden" name="isRelated" value="${isRelated}"/>
	<div id="relateCircuit" style="display: none;">
			<input type="button" name="circuitIds" id="addCirToContract" value="确认关联"/>
	</div>
	<input type="button" class="btn" id="goToDeleteAll" onclick="deleteAll();" value="批量删除" />
</logic:present>

<logic:notPresent name="circuitLengthList" scope="request">
	无该条件下记录
</logic:notPresent>

<html:form action="circuitLength.do?method=deleteAll" method="post" styleId="patchDeleteForm">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
