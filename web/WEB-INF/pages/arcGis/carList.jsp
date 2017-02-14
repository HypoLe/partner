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
	
	jq("#carList tbody tr").bind("mouseover",function(e) {
		jq(this).addClass("trMouseOver");
	});

	jq("#carList tbody tr").bind("mouseout",function(e) {
		jq(this).removeClass("trMouseOver");
	});
	
	jq("#carList tbody tr").bind(	"click",function(e) {
		var id = jq(this).find("input:hidden").val();
		parent.addblock();
		var url="${app}/partner/arcGis/arcGis.do?method=carDetail";
		Ext.Ajax.request({  
		       url : url ,
			   method: 'POST',
			   params:{id:id},
				success: function (result, request) { 
				resjson = result.responseText;
				var json = eval( '(' + resjson + ')' ); //转换为json对象
				if(json!=null&&json.length!=0){
					parent.frames['arcGis-page'].addCarMarker(json,pathUrl,false,true,true);
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
	jsonList = eval(  <%=jsonList%> );
			if(parent.frames['arcGis-page'].map != null && jsonList.length!=0 && parent.frames['arcGis-page'].map.loaded) {
			
				for(var j=0;j<jsonList.length;j++){
					var jsonForMap=[];
					jsonForMap.push(jsonList[j]);
					parent.frames['arcGis-page'].addCarMarker(jsonForMap,pathUrl,true,false,false);
				}
			}
			parent.removeblock();
});


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
	
	function clearText(){
		jq("#sheet").find("input:text")[0].value="";
		jq("#sheet").find("input:text")[1].value="";
		jq("#sheet").find("input:text")[2].value="";
		jq("#sheet").find("input:text")[3].value="";
		jq("#carStatusStringLike")[0].value="";
		jq("#carPropertyStringLike")[0].value="";
		jq("#company_id")[0].value="";
		jq("#areaStringLike")[0].value="";
	}

function changePage(v) {
		location.href = '${app}/partner/arcGis/arcGis.do?method=carList&pagesize='+v;
		addblock();
	}
	
</script>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
<form action="${app}/partner/arcGis/arcGis.do?method=carList" id="carForm" method="post">
<input type="hidden" name="pagesize" value="${pageSize}">
	<table id="sheet" class="listTable">
		<tr>
				<td class="label">
					代维公司&nbsp;
				</td>
				<td class="content">
					<input type="text" name="company_name" class="text max" id="company_name"   value="${companyName}" readonly="readonly"/>
					<input type="hidden" name="company_id" id="company_id"   value="${companyDeptId}"	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf" rootId=""
					rootText='代维公司组织' valueField="company_id" handler="company_name" textField="company_name"
					checktype="dept" data="${boxData}" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					所属区域&nbsp;
				</td>
				<td class="content">
					<c:set var="boxData">[{id:'${areaStringLike}',name:'<eoms:id2nameDB id="${areaStringLike}" beanId="tawSystemAreaDao"/>'}]</c:set>
					<input type="text" name="area_name" id="area_name"    class="text medium" alt="allowBlank:false" readonly="readonly"/>
					<input type="hidden" name="areaStringLike" id="areaStringLike"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="areaStringLike" handler="area_name" textField="area_name"
					checktype="" data="${boxData}" single="true">
					</eoms:xbox>
				</td>
		</tr>
		<tr>
				<td class="label">
					车牌号&nbsp;
				</td>
				<td class="content">
					<input type="text" name="carNumberStringLike" id="carNumber" class="text medium" value="${carNumberStringLike}"/>
				</td>
				<td class="label">
					车辆类型&nbsp;
				</td>
				<td class="content">
					<input type="text" name="carTypeStringLike" id="carTypeStringLike" class="text medium" value="${carTypeStringLike}"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					车辆状态&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox name="carStatusStringLike" id="carStatusStringLike"  initDicId="1230202" styleClass="input select" 
					defaultValue="${carStatusStringLike}">
					</eoms:comboBox> 
				</td>
				<td class="label">
					产权属性&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox name="carPropertyStringLike" id="carPropertyStringLike"  initDicId="1230201" 
					styleClass="input select" defaultValue="${carPropertyStringLike}">
					</eoms:comboBox> 
				</td>
		</tr>
	</table>
					<input type="submit" value="查询">&nbsp;&nbsp;
					<input type="button"  id="reset" value="重置" onclick="clearText();">
</form>
</div>
	<display:table name="carList" cellspacing="0" cellpadding="0"	id="carList" pagesize="${pageSize}" class="table"		export="false"
		requestURI="${app}/partner/resourceInfo/car.do?method=carList" 	sort="list" partialList="true" size="${resultSize}">
	<display:column  media="html" sortable="true" headerClass="sortable" title="车牌号">${carList.carNumber}
	<input type="hidden" id="dataId_${carList_rowNum }" value="${carList.id}"/>
	</display:column>	
			
	<display:column  sortable="true" headerClass="sortable" title="车辆类型">${carList.carType}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="车载GPS编号">${carList.carGPSNumber}
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="车辆状态">
		<eoms:id2nameDB id="${carList.carStatus}"  beanId="tawSystemDictTypeDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="产权属性">
		<eoms:id2nameDB id="${carList.carProperty}"  beanId="tawSystemDictTypeDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="负责人/驾驶员">
		<eoms:id2nameDB id="${carList.driver}"  beanId="partnerUserDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="联系方式/驾驶员联系方式">${carList.driverContact}
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="代维公司">
		<eoms:id2nameDB id="${carList.maintainCompany}"	beanId="tawSystemDeptDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="区域">
		<eoms:id2nameDB id="${carList.area}"	beanId="tawSystemAreaDao" />
	</display:column>	
	
	<display:column style="text-align:center;" sortable = "false" title = "今日轨迹">
		  		<a href="${app}/partner/arcGis/arcGis.do?method=getCarTrajectory&carnum=${carList.carNumber}">查看</a>
	</display:column>
	<%--<display:column sortable="false" headerClass="sortable" title="编辑" url="/partner/resourceInfo/car.do?method=edit"
				paramProperty="id" paramId="id" media="html">
	<img src="${app}/images/icons/edit.gif">
	</display:column>
	<display:column sortable="false" headerClass="sortable" title="详情" media="html">
		<a href="${app}/partner/resourceInfo/car.do?method=detail&&id=${carList.id }" target="blank" shape="rect">
			<img src="${app}/images/icons/table.gif">
		</a>
	</display:column>
	<display:column sortable="false" headerClass="sortable" title="删除" media="html">
		<img class="delete" src="${app}/images/icons/icon.gif" onclick="del('${carList.id}')"/>
	</display:column>--%>
	</display:table>
	
 <center>
	<div>
		<table>
			<tr>
				<td>
				设置每页显示条数
				</td>
				<td>
					<select id="pagesize" name="pagesize" style="width: 58px" onchange=changePage(this.value) >
											<option value="15" ${pageSize == "15" ? 'selected="selected"':'' } >15</option>
											<option value="40" ${pageSize == "40" ? 'selected="selected"':'' } >40</option>
											<option value="80" ${pageSize == "80" ? 'selected="selected"':'' } >80</option>
											<option value="160" ${pageSize == "160" ? 'selected="selected"':'' } >160</option>
					</select>
				</td>
			</tr>
		</table>
	</div>
</center>
	
<br><br>

<%@ include file="/common/footer_eoms.jsp"%>