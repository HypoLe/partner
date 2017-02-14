<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>

<div style="color: red;font: bold;"><c:out value="${msg}"></c:out></div>


<div id="searchDiv" class="ui-widget-content ui-state-default " >快速查询</div>


<div id="searchForm"  >
<form action="${app}/partner2/target/circuit.do?method=list" method="post">
	<fieldset><br />
	<table id="sheet" class="formTable">
		<tr>
			<td class="label">月度</td>
			<td class="content"><select size='1'
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
		<td class="label">代维公司*</td>
			<td>
			<input class="text popupMonitorCompanyTree" style="width:300" type="text" readonly="readonly" id="showMonitorCompany" 
				alt="allowBlank:false,vtext:'请选择代维公司'" />
				<input type="hidden" name="monitorCompanyStringLike" id="monitorCompanyStringLike" /> 
			</td>
		</tr>
		<tr>
			<td class="label">分公司*</td>
			<td>
				<input class="text popupDeptTree" type="text" readonly="readonly" id="showCity" 
				alt="allowBlank:false,vtext:'请选择分公司'" />
				<input type="hidden" name="cityStringEqual" id="cityStringEqual" /> 
			</td>
			<td class="label">归属县公司*</td>
			<td>
				<input class="text popupDeptTree" type="text" readonly="readonly" id="showCountry" 
				alt="allowBlank:false,vtext:'请选择归属县公司'" />
				<input type="hidden" name="countryStringEqual" id="countryStringEqual" /> 				
			</td>
		</tr>
	<tr>
	</table>
	<br />
	<html:submit styleClass="btn" property="method.send"
		styleId="method.save" value="提交查询结果"></html:submit></fieldset>
</form></div>


<!-- Information hints area end-->
<logic:present name="${listName }" scope="request">
	<display:table name="${listName }" cellspacing="0" cellpadding="0"
		id="${listName }" pagesize="${pagesize }" class="table ${listName }"
		export="false" requestURI="${app}/partner2/target/circuit.do?method=list&listType=list"
		sort="list" partialList="true" size="${size }">
		<display:column sortable="true"  title="分公司">
			<eoms:id2nameDB id="${circuitTargetList.city}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true"  title="归属县公司">
			<eoms:id2nameDB id="${circuitTargetList.country}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true"  title="代维公司">
			<eoms:id2nameDB id="${circuitTargetList.monitorCompany}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="false" title="月份">
			${circuitTargetList.monthFlag}月份
		</display:column>
		<display:column sortable="false" property="myText1" title="线路百公里阻断历时"></display:column>
		<display:column sortable="false" property="myText2" title="平均单次障碍历时"></display:column>
		<display:column sortable="false" property="myText3" title="每次障碍抢通历时"></display:column>
		<display:column sortable="false" property="myText4" title="线路巡检完成率"></display:column>
		<display:column sortable="false" title="删除"
				url="/partner2/target/circuit.do?method=delete" paramProperty="id"
				paramId="id" media="html">
				<img src="${app }/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>
</logic:present>

<logic:notPresent name="${listName }" scope="request">
	无记录
</logic:notPresent>

<div style="margin-top: 10px">
	<input type="button" class="btn" id="goToAddButton" value="新增记录" />
	<input type="button" class="btn" id="goToImportExcelButton" value="导入记录" />
</div>

<!-- 弹出树图隐藏域 开始 -->
<div id="deptview" class="hide"></div>
<input type="hidden" id="treeBtnId" name="treeBtnId" value=""/>
<!-- 弹出树图隐藏域 结束 -->


<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	myJ('div#searchDiv').bind('click',function(event){
		myJ('#searchForm').toggle();
	});
	
	myJ("input.popupDeptTree").bind("click",function(event) {
			var showChkFldId = "";
			var saveChkFldId = "";
			if(this.id == 'showCity'){
				showChkFldId="showCity";
				saveChkFldId="cityStringEqual";
				showCityTree(showChkFldId,saveChkFldId);
			}else if(this.id == 'showCountry'){
				showChkFldId="showCountry";
				saveChkFldId="countryStringEqual";
				showCountryTree(showChkFldId,saveChkFldId);
			}else{
				//Do nothing.
			}
	});
	
	
	myJ("input.popupMonitorCompanyTree").bind("click",function(event) {
			var showChkFldId = "showMonitorCompany";
			var saveChkFldId = "monitorCompanyStringLike";
			showPartnerTree(showChkFldId,saveChkFldId);
	});
	
		myJ('input[type=button]').bind('click',function(event){
		var buttonId= event.target.id;
		if(buttonId == 'goToAddButton'){
			//myJ(this).attr('disabled','disabled');
			location.href = '${goToAddURI}';
		}else if(buttonId=='goToImportExcelButton'){
			location.href = '${goToImportExcelURI}';
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

function showPartnerTree(showChkFldId,saveChkFldId) {
			var deptViewer = new Ext.JsonView("deptview",
					'<div class="viewlistitem-{nodeType}">{name}</div>',
					{ 
						emptyText : '<div>${eoms:a2u('没有选择项目')}</div>'
					}
					);
			var data = "[]";
			deptViewer.jsonData = eoms.JSONDecode(data);
			deptViewer.refresh();
			
			 var deptTreeAction='${app}/partner/baseinfo/areaDeptTrees.do?method=selectPartner';
		   	 deptetree = new xbox({
		      	  btnId:"treeBtnId",
			      dlgId:'dlg3',
			      treeDataUrl:deptTreeAction,
			      treeRootId:'1',
			      treeRootText:'代维公司',
			      treeChkMode:'single',
			      treeChkType:'factory',
			      showChkFldId:showChkFldId,
			      saveChkFldId:saveChkFldId,
			      viewer:deptViewer
		    });
		    deptetree.onBtnClick();
		    deptetree=null;
}


</script>
<%@ include file="/common/footer_eoms.jsp"%>