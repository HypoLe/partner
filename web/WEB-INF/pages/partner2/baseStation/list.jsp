<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>

<div style="color: red;font: bold;"><c:out value="${msg}"></c:out></div>

<div id="searchDiv" class="x-layout-panel-hd" style="cursor:pointer;border: 1px solid rgb(152, 192, 244); padding: 5px; width: 98%;">快速查询</div>
<div id="searchForm" style="display:none;border-style: none solid solid; border-color:rgb(152, 192, 244); border-width: 0pt 1px 1px; padding: 5px; background-color: rgb(239, 246, 255); width: 98%;">
<html:form action="baseStationMain.do?method=list&listType=listOfCreateUser" method="post">
	<table id="sheet" class="formTable">
		<tr>
		<td class="label">分公司*</td>
		<td><select id='city' name="cityStringEqual"></select></td>
		<td class="label">归属县公司*</td>
		<td><select id='country' name="countryStringEqual"></select></td>
	</tr>
		<tr>
			<td class="label">年度</td>
			<td class="content"><select size='1' style="width:95%"
				name='yearFlagStringEqual' id='yearFlagStringEqual'
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
				name='monthFlagStringEqual' id='monthFlagStringEqual'
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
			<td colspan='4' align='center' class='label'>
			<html:submit styleClass="btn" property="method.send"
		styleId="method.save" value="提交查询结果"></html:submit>
		</td>
		</tr>
	</table>
</html:form></div>


<!-- Information hints area end-->
<logic:present name="${listName }" scope="request">
	<display:table name="${listName }" cellspacing="0" cellpadding="0"
		id="${listName }" pagesize="${pagesize }" class="table ${listName }"
		export="false" requestURI="${app }/partner2/baseStation/baseStationMain.do?method=list&listType=list"
		sort="list" partialList="true" size="${size }">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${baseStationMainList.id}" id="${baseStationMainList.id}" class="${baseStationMainList.isWithAppraisal}"/>
		</display:column>
		<display:column sortable="true"   title="分公司">
			<eoms:id2nameDB id="${baseStationMainList.city}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true"  title="归属县公司">
			<eoms:id2nameDB id="${baseStationMainList.country}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true"  title="代维公司">
			<eoms:id2nameDB id="${baseStationMainList.monitorCompany}"
				beanId="partnerDeptDao" />
		</display:column>
		<display:column sortable="false" property="station1Monitor" title="2G宏基站代维数"></display:column>
		<display:column sortable="false" property="station2Monitor" title="边际网基站代维数"></display:column>
		<display:column sortable="false" property="station3Monitor" title="独立TD宏基站代维数"></display:column>
		<display:column sortable="false" property="station4Monitor" title="共址TD宏基站代维数"></display:column>
		<display:column sortable="false" property="station5Monitor" title="直放站代维数"></display:column>
		<display:column sortable="false" property="yearFlag" title="年度"></display:column>
		<display:column sortable="false" property="monthFlag" title="月度"></display:column>
		
		<display:column sortable="false"  title="详情">
			<a id="${baseStationMainList.id }"
				href="baseStationMain.do?method=showDetail&id=${baseStationMainList.id}"
				target="_blank" shape="rect">详情</a>
		</display:column>
		
		<display:column sortable="false" title="编辑" media="html">
			<c:if test="${empty baseStationMainList.contractId}">
			<a id="${baseStationMainList.id }"
				href="baseStationMain.do?method=goToEdit&id=${baseStationMainList.id}"
				target="_blank" shape="rect">编辑</a>
			</c:if>
			<c:if test="${not empty baseStationMainList.contractId}">
				<font color="red">此条数据已被合同关联,不能修改</font>
			</c:if>
		</display:column>

		<c:if test="${listType == 'listPrivilege'}">
			<display:column sortable="false" title="删除"
				url="${app}/baseStation/baseStationMain.do?method=delete&listType=listPrivilege" paramProperty="id"
				paramId="id" media="html">
				<img src="${app }/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
		</c:if>
		
	</display:table>
	<div style="margin-top: 10px">
	<input type="button" class="btn" id="goToDeleteAll" onclick="deleteAll();" value="批量删除" />
	<input type="hidden" name="isRelated" value="${isRelated}"/>
	<div style="display: none;" id="relateBaseStation">
		<input type="button" class="btn" id="addBsToContract" value="确认关联" />
	</div>
	</div>
</logic:present>

<logic:notPresent name="${listName }" scope="request">
	无记录
</logic:notPresent>

<html:form action="baseStationMain.do?method=deleteAll&listType=list" method="post" styleId="patchDeleteForm">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</html:form>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	
	myJ('select#country').html('<option value="">请选择县</option>').attr('disabled',true);
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
	

	myJ('input[type=button]').bind('click',function(event){
		var buttonId= event.target.id;
		if(buttonId == 'goToAddButton'){
			//myJ(this).attr('disabled','disabled');
			location.href = '${app}/partner2/baseStation/baseStationMain.do?method=goToAdd';
		}else if(buttonId=='goToImportExcelButton'){
			location.href = '${app}/partner2/baseStation/baseStationMain.do?method=goToImportExcel';
		}else if(buttonId=='goToExport'){
			location.href = '${app}/partner/feeInfo/pnrFeeInfoMains.do?method=exportProxyExcel';
		}else{
			//Do nothing.
		}
	});
	
	//处理由合同关联基站规模数据的情况,如果windwo.opener存在则说明是由合同关联打开
	if(window.opener){
		myJ("input[id^='goTo']").hide();
		myJ('div#relateBaseStation').show();
		myJ('div#relateBaseStation').append('<font color=red>(未关联考核的代维规模数据不能选择！)</font>');
		var baseStationIds = window.opener.document.getElementById('baseStationIds').value;
		var myProxyModelsTable = myJ(window.opener.document.getElementById('myProxyModelsTable'));
		myJ("input:checkbox").removeAttr("disabled");
		if(baseStationIds){
			var baseStationIdArray=baseStationIds.split(";");
			for(var i=0;i<baseStationIdArray.length;i++){
				if(baseStationIdArray[i]!=''){
					myJ('input#'+baseStationIdArray[i]).attr("disabled","disabled");
				}
			}
		}
		myJ("input#addBsToContract").click(function(){
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
	 				+"<td>基站类</td>"
	 				+"<td><a href="+"${app}/partner2/baseStation/baseStationMain.do?method=showDetail&id="+myJ(this).val()+" target='_blank'>"+"<img src='${app}/images/icons/search.gif'/></a></td>"
	 				+"<td><img src='${app}/nop3/images/icon.gif'"+"onmousedown='deleteBaseStation(this);' style='cursor:pointer;'/>"+"</td>"+"<input type='hidden' value="+myJ(this).val()+" name='baseStationId'/>"+"</tr>");
				});
				if(checkedId!=""){
					window.opener.document.getElementById('baseStationIds').value+=checkedId;
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



function dealAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	var calc=0;
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";";
			calc++;
		}
	}
	var currentTaskOwner = document.getElementsByName("currentTaskOwner");
	if(!currentTaskOwner[0] || currentTaskOwner[0].value ==''){
		alert("请选择派发对象");
		return false;
	}
	if (idResult == "") {
		alert("请选择需要处理的记录");
		return false;
	} else {
		//如果只有1条记录的话，那么直接弹出隐藏域进行处理，如果有多条记录的话，则转入批处理页面
			if(confirm("确定处理记录吗？")){
				$("dealIds").value=idResult.toString();
				document.forms[1].submit();
			}else{
				return false;
			}
		}
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

function goToHistory(obj){
    var myId=obj.id;
	window.open('${app}/duty/circuitLength.do?method=goToHistory&id=myId',null,'left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes')
}

function jumpUrl(){
	location.href='<html:rewrite page='/circuitLength.do?method=goToAdd'/>';
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>
