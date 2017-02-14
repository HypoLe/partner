<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>

<div style="color: red;font: bold;"><c:out value="${msg}"></c:out></div>


<!--<div id="searchDiv" class="ui-widget-content ui-state-default " >快速查询</div>


<div id="searchForm"  >-->
<div id="searchDiv" class="x-layout-panel-hd" style="border: 1px solid rgb(152, 192, 244); padding: 5px; width: 98%;">快速查询</div>
<div id="searchForm" style="border-style: none solid solid; border-color:rgb(152, 192, 244); border-width: 0pt 1px 1px; padding: 5px; background-color: rgb(239, 246, 255); width: 98%;">
<html:form action="baseStationMain.do?method=goToWithAppraisal" method="post">
	<table id="sheet" class="formTable">
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
		<td class="label">分公司*</td>
		<td><select id='city' name="cityStringEqual"></select></td>
		<td class="label">归属县公司*</td>
		<td><select id='country' name="countryStringEqual"></select></td>
	</tr>
	<tr>
	
	<td class="label" colspan="4" align="center">
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
				value="${baseStationMainList.id}" id="${baseStationMainList.id}" />
		</display:column>
		<display:column sortable="true"   title="分公司">
			<eoms:id2nameDB id="${baseStationMainList.city}"
				beanId="tawSystemAreaDao" />
			<input type="hidden" value="${baseStationMainList.city}"/>
		</display:column>
		<display:column sortable="true"  title="归属县公司">
			<eoms:id2nameDB id="${baseStationMainList.country}"
				beanId="tawSystemAreaDao" />
			<input type="hidden" value="${baseStationMainList.country}"/>
		</display:column>
		<display:column sortable="true"  title="代维公司">
			<eoms:id2nameDB id="${baseStationMainList.monitorCompany}"
				beanId="partnerDeptDao" />
			<input type="hidden" value="${baseStationMainList.monitorCompany}"/>
		</display:column>
		<display:column sortable="false" property="station1Monitor" title="2G宏基站代维数"></display:column>
		<display:column sortable="false" property="station2Monitor" title="边际网基站代维数"></display:column>
		<display:column sortable="false" property="station3Monitor" title="独立TD宏基站代维数"></display:column>
		<display:column sortable="false" property="station4Monitor" title="共址TD宏基站代维数"></display:column>
		<display:column sortable="false" property="station5Monitor" title="直放站代维数"></display:column>
		<display:column sortable="false" property="yearFlag" title="年度"></display:column>
		<display:column sortable="false" property="monthFlag" title="月度"></display:column>
		<display:column title="关联考核模板">
			<c:if test="${baseStationMainList.isWithAppraisal eq 'no'}">
				<input type="button" id="button_${baseStationMainList.id}" name="showAppraisals" class="btn" value="添加考核模板"/>
			</c:if>
			<c:if test="${baseStationMainList.isWithAppraisal eq 'yes'}">
				<font color="red">已关联考核</font>
			</c:if>
		</display:column>
		
		<c:if test="${listType == 'listPrivilege'}">
			<display:column sortable="false" title="编辑"
				url="${app}/baseStation/baseStationMain.do?method=goToEdit&listType=listPrivilege" paramProperty="id"
				paramId="id" media="html">
				<img src="${app }/nop3/images/edit.gif" />
			</display:column>
		</c:if>

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
	<input type="hidden" id="proxyScaleId" value="" />
	<input type="hidden" id="proxyScaleType" value="baseStation" />
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

<!-- 弹出树图隐藏域 开始 -->
<div id="deptview" class="hide"></div>
<input type="hidden" id="treeBtnId" name="treeBtnId" value=""/>
<!-- 弹出树图隐藏域 结束 -->


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
	
	
	myJ("input[name='showAppraisals']").click(function(event){
		//点击后防止多次提交
		myJ(this).attr('disabled','disabled');
		var buttonId=event.target.id;
		var proxyScaleId=buttonId.substring(7,buttonId.length);
		myJ('input#proxyScaleId').val(proxyScaleId);
		var _AppraisalsWindow = window.open("${app}/partner2/appraisal.do?method=list&proxyScaleType=baseStation&proxyScaleId="+proxyScaleId,null,"left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes");
		//窗口打开后恢复按钮
		myJ(this).removeAttr('disabled');
	});
	
	myJ('div#searchDiv').bind('click',function(event){
		myJ('#searchForm').toggle();
	});
	
		myJ("input.popupDeptTree").bind("click",function(event) {
			var showChkFldId = "";
			var saveChkFldId = "";
			if(this.id == 'showCity'){
				showChkFldId="showCity";
				saveChkFldId="city";
				showCityTree(showChkFldId,saveChkFldId);
			}else if(this.id == 'showCountry'){
				showChkFldId="showCountry";
				saveChkFldId="country";
				showCountryTree(showChkFldId,saveChkFldId);
			}else{
				//Do nothing.
			}
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
});


function showCityTree(showChkFldId,saveChkFldId) {
			var deptViewer = new Ext.JsonView("deptview",
					'<div class="viewlistitem-{nodeType}">{name}</div>',
					{ 
						emptyText : '<div>${eoms:a2u('没有选择项目')}</div>'
					}
					);
			var data = "[]";
			deptViewer.jsonData = eoms.JSONDecode(data);
			deptViewer.refresh();
			
			 var deptTreeAction='${app}/partner/baseinfo/areaDeptTrees.do?method=getAreaTree';
		   	 deptetree = new xbox({
		      	  btnId:"treeBtnId",
			      dlgId:'dlg3',
			      treeDataUrl:deptTreeAction,
			      treeRootId:'-1',
			      treeRootText:'分公司',
			      treeChkMode:'single',
			      treeChkType:'city',
			      showChkFldId:showChkFldId,
			      saveChkFldId:saveChkFldId,
			      viewer:deptViewer
		    });
		    deptetree.onBtnClick();
		    deptetree=null;
}

function showCountryTree(showChkFldId,saveChkFldId) {
			var deptViewer = new Ext.JsonView("deptview",
					'<div class="viewlistitem-{nodeType}">{name}</div>',
					{ 
						emptyText : '<div>${eoms:a2u('没有选择项目')}</div>'
					}
					);
			var data = "[]";
			deptViewer.jsonData = eoms.JSONDecode(data);
			deptViewer.refresh();
			
			 var deptTreeAction='${app}/partner/baseinfo/areaDeptTrees.do?method=getAreaTree';
		   	 deptetree = new xbox({
		      	  btnId:"treeBtnId",
			      dlgId:'dlg3',
			      treeDataUrl:deptTreeAction,
			      treeRootId:'-1',
			      treeRootText:'分公司',
			      treeChkMode:'single',
			      treeChkType:'country',
			      showChkFldId:showChkFldId,
			      saveChkFldId:saveChkFldId,
			      viewer:deptViewer
		    });
		    deptetree.onBtnClick();
		    deptetree=null;
}

</script>

<%@ include file="/common/footer_eoms.jsp"%>
