<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
	var myJ = jQuery.noConflict();
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
	<form action="${app}/partner/resourceInfo/oilEngine.do?method=search" id="oilEngineForm"	method="post">
		<table id="sheet" class="listTable">
			<tr>
				<td class="label">
					代维公司&nbsp;
				</td>
				<td class="content">
					<input type="text" name="company_name" class="text max" id="company_name"   value="${companyName}" readonly="readonly"/>
					<input type="hidden" name="company_id" id="company_id"   value="${companyId}"	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight" rootId=""
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
<logic:notEmpty name="oilEngineList" scope="request">
<display:table name="oilEngineList" cellspacing="0" cellpadding="0"	id="oilEngineList" pagesize="${pageSize}"
		class="table oilEngineList" export="false"	requestURI="${app}/partner/resourceInfo/oilEngine.do?method=search" sort="list"
		partialList="true" size="${total}">
	<display:column  sortable="true" headerClass="sortable" title="序号">${oilEngineList.id}
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="油机编号">
			${oilEngineList.oilEngineNumber}
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
		<eoms:id2nameDB id="${oilEngineList.powerRating}" beanId="ItawSystemDictTypeDao" />
	--%>${oilEngineList.powerRating}
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="标准油耗(L/小时)">${oilEngineList.standardFuelConsumption}
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="燃油种类">
		<eoms:id2nameDB id="${oilEngineList.fuleType}" beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="油机类型">
		<eoms:id2nameDB id="${oilEngineList.oilEngineType}" beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="产权归属">
		<eoms:id2nameDB id="${oilEngineList.oilEngineProperty}" beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="油机状态">
		<eoms:id2nameDB id="${oilEngineList.oilEngineStatus}" beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="false" headerClass="sortable" title="详情" media="html">
		<a href="${app}/partner/resourceInfo/oilEngine.do?method=detailChange&&id=${oilEngineList.id }" target="blank" shape="rect">
			<img src="${app}/images/icons/table.gif">
		</a>
	</display:column>
	<%--<display:setProperty name="export.rtf" value="false" />
	<display:setProperty name="export.pdf" value="false" />
	<display:setProperty name="export.xml" value="false" />
	<display:setProperty name="export.csv" value="false" />
--%></display:table>
<eoms:excelExport modelName="com.boco.eoms.partner.resourceInfo.model.OilEngine" 	 serviceBeanId="oilEngineService"   title="油机信息" >
		<eoms:row name="序号" value="id"/>
		<eoms:row name="区域" value="area" dictDaoName="tawSystemAreaDao" />
		<eoms:row name="代维公司" value="maintainCompany" dictDaoName="tawSystemDeptDao"/>
		<eoms:row name="油机编号" value="oilEngineNumber" />
		<eoms:row name="油机型号" value="oilEngineModel" />
		<eoms:row name="油机厂商" value="oilEngineFactory"/>
		<eoms:row name="额定功率" value="powerRating"/>
		<eoms:row name="燃油种类" value="fuleType" dictDaoName="ItawSystemDictTypeDao"/>
		<eoms:row name="标准油耗" value="standardFuelConsumption"/>
		<eoms:row name="油机类型" value="oilEngineType" dictDaoName="ItawSystemDictTypeDao"/>
		<eoms:row name="油机状态" value="oilEngineStatus" dictDaoName="ItawSystemDictTypeDao" />
		<eoms:row name="产权属性" value="oilEngineProperty" dictDaoName="ItawSystemDictTypeDao"/>
		<eoms:row name="存放地点" value="place" dictDaoName="tawSystemAreaDao"/>
		<eoms:row name="备注" value="notes"/>
		<eoms:row name="创建时间" value="addTime"/>
	</eoms:excelExport>
</logic:notEmpty> 
<logic:empty name="oilEngineList" scope="request">
没有记录！
</logic:empty> 
<%@ include file="/common/footer_eoms.jsp"%>