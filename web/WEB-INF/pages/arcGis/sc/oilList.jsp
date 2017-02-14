<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" 	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" 	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style type="text/css">
	.trMouseOver {
		cursor:pointer;
	}
	tr.trMouseOver td {
		background-color:#CDE0FC;
	}
</style>
<%
String jsonList=request.getAttribute("jsonString").toString();
%>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
var pathUrl="${app}";
	if(parent.frames['arcGis-page'].map!=null){
		parent.frames['arcGis-page'].mapCenterAndZoom(true,true);
		}
	parent.removeblock();
	
	jq("#oilEngineList tbody tr").bind("mouseover",function(e) {
		jq(this).addClass("trMouseOver");
	});

	jq("#oilEngineList tbody tr").bind("mouseout",function(e) {
		jq(this).removeClass("trMouseOver");
	});
		jq("#oilEngineList tbody tr").bind(	"click",function(e) {
		var id = jq(this).find("input:hidden").val();
		parent.addblock();
		var url="${app}/partner/arcGis/arcGis.do?method=oilDetail";
		Ext.Ajax.request({  
		       url : url ,
			   method: 'POST',
			   params:{id:id},
				success: function (result, request) { 
				resjson = result.responseText;
				var json = eval( '(' + resjson + ')' ); //转换为json对象
				if(json!=null&&json.length!=0){
					parent.frames['arcGis-page'].addOilMarker(json,pathUrl,false,true,true);
				}
				else{
					alert("该资源数据错误！");
				}
				parent.removeblock();
				 },
				 failure: function ( result, request) { 
					 parent.removeblock();
						 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					 } 
			 });
	});
	
	var jsonList='<%=jsonList%>';
	jsonList = eval( '(' + jsonList + ')' );
			if(parent.frames['arcGis-page'].map != null&&jsonList.length!=0&&parent.frames['arcGis-page'].map.loaded) {
				for(var j=0;j<jsonList.length;j++){
					var jsonForMap=[];
					jsonForMap.push(jsonList[j]);
					parent.frames['arcGis-page'].addOilMarker(jsonForMap,pathUrl,true,false,false);
				}
			}
			parent.removeblock();
});



	function add(){
		window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/oilEngine.do?method=goToAdd";
	}
	function openImport(){
		var handler = document.getElementById("openQuery");
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
		var formElement=document.getElementById("oilEngineForm")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
	   	 companyId=document.getElementById('company_id').value='';
	   	 companyId=document.getElementById('area_name').value='';
	   	 companyId=document.getElementById('company_name').value='';
	   	 companyId=document.getElementById('areaStringLike').value='';
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
<table class="formTable">
	<caption>
		<div class="header center">
			油机信息列表
		</div>
	</caption>
</table>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
	<form action="${app}/partner/arcGis/arcGis.do?method=oilSearch" id="oilEngineForm"	method="post">
		<table id="sheet" class="listTable">
			<tr>
				<td class="label">
					代维公司&nbsp;
				</td>
				<td class="content">
					<input type="text" name="company_name" class="text max" id="company_name"   value="${companyName}" readonly="readonly"/>
					<input type="hidden" name="company_id" id="company_id"   value="${companyId}"	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf" rootId=""
					rootText='代维公司组织' valueField="company_id" handler="company_name" textField="company_name"
					checktype="dept" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					所属区域&nbsp;
				</td>
				<td class="content">
					<input type="text" name="area_name" id="area_name"    class="text medium" value="${areaName}"  readonly="readonly"/>
					<input type="hidden" name="areaStringLike" id="areaStringLike"   class="text medium" value="${areaStringLike}"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="areaStringLike" handler="area_name" textField="area_name"
					checktype=""  single="true">
					</eoms:xbox>
				</td>
			</tr>
		<tr>
				<td class="label">
					油机编号&nbsp;
				</td>
				<td class="content">
					<input type="text" name="oilEngineNumberStringLike" class="text medium"	id="oilEngineNumberStringLike" value="${oilEngineNumberStringLike}"/>
				</td>
				<td class="label">
					油机类型&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox name="oilEngineTypeStringLike" styleClass="input select" id="oilEngineTypeStringLike" 
					initDicId="1230302"  defaultValue="${oilEngineTypeStringLike}"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					额定功率(KW)&nbsp;
				</td>
				<td class="content"><%--
					<eoms:comboBox name="powerRatingStringLike" id="powerRatingStringLike" initDicId="1230301" 
					styleClass="input select"	 defaultValue="${powerRatingStringLike}"	/>
				--%>
					<input type="text" name="powerRatingStringLike" class="text medium"	id="powerRatingStringLike" value="${powerRatingStringLike}"/>
				</td>
				<td class="label">
					油机状态&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox name="oilEngineStatusStringLike" id="oilEngineStatusStringLike"
						initDicId="1230303" styleClass="input select" defaultValue="${oilEngineStatusStringLike}" />
				</td>
		</tr>
		<tr>
				<td colspan='4' class='label'>
					<input type="submit" value="查询">
					<input type="button"  id="reset" value="重置" onclick="res();">
				</td>
		</tr>
	</table>
	</form>
</div>
<br>
<br>
<display:table name="oilEngineList" cellspacing="0" cellpadding="0"	id="oilEngineList" pagesize="${pageSize}"
		class="table oilEngineList" export="false"	requestURI="${app}/partner/arcGis/arcGis.do?method=oilSearch" sort="list"
		partialList="true" size="${total}">
	
	<display:column media="html" sortable="true" headerClass="sortable" title="油机编号">
			${oilEngineList.oilEngineNumber}
			<input type="hidden" id="dataId_${oilEngineList_rowNum }" value="${oilEngineList.id}"/>
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="区域">
		<eoms:id2nameDB id="${oilEngineList.area}" beanId="tawSystemAreaDao"/>
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="代维公司">
		<eoms:id2nameDB id="${oilEngineList.maintainCompany}"	beanId="tawSystemDeptDao" />
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="规格型号">${oilEngineList.oilEngineModel}
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="额定功率(KW)"><%--
		<eoms:id2nameDB id="${oilEngineList.powerRating}" beanId="tawSystemDictTypeDao" />
	--%>${oilEngineList.powerRating}
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="标准油耗(L/小时)">${oilEngineList.standardFuelConsumption}
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="燃油种类">
		<eoms:id2nameDB id="${oilEngineList.fuleType}" beanId="tawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="油机类型">
		<eoms:id2nameDB id="${oilEngineList.oilEngineType}" beanId="tawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="产权归属">
		<eoms:id2nameDB id="${oilEngineList.oilEngineProperty}" beanId="tawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="油机状态">
		<eoms:id2nameDB id="${oilEngineList.oilEngineStatus}" beanId="tawSystemDictTypeDao" />
	</display:column>
</display:table>
	
<%@ include file="/common/footer_eoms.jsp"%>