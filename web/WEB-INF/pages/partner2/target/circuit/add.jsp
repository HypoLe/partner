<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>


<form action="${app }/partner2/target/circuit.do?method=add" id="circuitTargetForm" method="post">

<!-- 弹出树图隐藏域 开始 -->
<div id="deptview" class="hide"></div>
<input type="hidden" id="treeBtnId" name="treeBtnId" value=""/>
<!-- 弹出树图隐藏域 结束 -->

<div class="ui-widget-header ui-corner-top ui-state-default " id="opBasicInfo">分公司和归属县公司</div>
<div id="basicInfo">
<table class="formTable" >
	<tr>
			<td class="label">分公司*</td>
			<td>
				<input class="text popupDeptTree" type="text" readonly="readonly" id="showCity" 
				alt="allowBlank:false,vtext:'请选择分公司'" />
				<input type="hidden" name="city" id="city" /> 
			</td>
			<td class="label">归属县公司*</td>
			<td>
				<input class="text popupDeptTree" type="text" readonly="readonly" id="showCountry" 
				alt="allowBlank:false,vtext:'请选择归属县公司'" />
				<input type="hidden" name="country" id="country" /> 				
			</td>
		</tr>
	<tr>
		<td class="label">代维公司*</td>
			<td>
			<input class="text popupMonitorCompanyTree" style="width:300" type="text" readonly="readonly" id="showMonitorCompany" 
				alt="allowBlank:false,vtext:'请选择代维公司'" />
				<input type="hidden" name="monitorCompany" id="monitorCompany" /> 
			</td>
	</tr>
		</table>
</div>


<div class="ui-widget-header ui-corner-top ui-state-default" style="margin-top: 15px">年度和月度信息</div>
<div>
<table class="formTable" >
	<tr>
			<td class="label">年度</td>
			<td class="content"><select size='1'
				name='yearFlag' id='yearFlag'
				class='select'>
				<option value=''>请选择</option>
				<option value='2008'>2008年</option>
				<option value='2009'>2009年</option>
				<option value='2010'>2010年</option>
				<option value='2011' selected="selected">2011年</option>
				<option value='2012'>2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
				<option value='2015'>2015年</option>
			</select></td>
			<td class="label">月度</td>
			<td class="content"><select size='1' name="monthFlag"
				id="monthFlag" class='select'>
				<option value="default">请选择</option>
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
		</table>
</div>


<div class="ui-widget-header ui-corner-top ui-state-default " style="margin-top: 15px">线路考核指标信息
<table class="formTable" >
			<tr>
				<td class="label">
					线路百公里阻断历时*
				</td>
				<td>
					<input type="text" name="myText1" class="text" alt="allowBlank:false"  />
				</td>
				<td class="label">
					平均单次障碍历时*
				</td>
				<td>
					<input type="text" name="myText2" class="text" alt="allowBlank:false"  />
				</td>
			</tr>
			<tr>
				<td class="label">
					每次障碍抢通历时*
				</td>
				<td>
					<input type="text" name="myText3" class="text" alt="allowBlank:false"  />
				</td>
				<td class="label">
					线路巡检完成率*
				</td>
				<td>
					<input type="text" name="myText4" class="text" alt="allowBlank:false"  />
				</td>
			</tr>
		</table>
</div>

<div style="margin-top: 30px"> 
<input type="submit" id="operationSubmitButton"  class="btn" value="新增记录" />
</div>

</form>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {

// Add validation function supplied by Ext.
	v = new eoms.form.Validation({form:'circuitTargetForm'});
	// Write your validation here.
	v.custom = function(){
		return true;
	};


	myJ('div#opBasicInfo').bind('click',function(event){
		myJ('div#basicInfo').fadeToggle("fast", "linear");
	});

	myJ("input.popupMonitorCompanyTree").bind("click",function(event) {
			var showChkFldId = "showMonitorCompany";
			var saveChkFldId = "monitorCompany";
			showPartnerTree(showChkFldId,saveChkFldId);
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
	
	//As this function's special, so read the comments please. By Steve. 2011-10-09
	myJ('input[type=button]').bind('click',function(event){
		var buttonId= event.target.id;
		if(buttonId == 'addNewRecord'){
		
		}else if(buttonId == 'goToImportExcel'){
			location.href = '${app}/partner2/baseStation/baseStationMain.do?method=goToImportExcel&listType=goToImportExcel';
			//禁用该按钮防止多次提交
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