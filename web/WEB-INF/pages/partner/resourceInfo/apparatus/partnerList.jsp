<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
	var myJ = jQuery.noConflict();
	myJ(function() {
		myJ('div#searchDiv').bind('click',function(event){
			myJ('#searchForm').toggle();
		});
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
	function res(){
		var formElement=document.getElementById("apparatusForm")
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
  	<caption><div class="header center">仪器仪表列表	</div></caption>
</table>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
<form action="${app}/partner/resourceInfo/apparatus.do?method=search" id="apparatusForm" method="post">
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
					维护专业&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox  name="maintenanceMajorStringLike" id="maintenanceMajor"  defaultValue="${maintenanceMajorStringLike}"
					initDicId="1230101" styleClass="input select" sub="apparatusNameStringLike"/>
				</td>
				<td class="label">
					仪器名称&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox  name="apparatusNameStringLike" id="apparatusNameStringLike" initDicId="${maintenanceMajorStringLike}" 
					defaultValue="${apparatusNameStringLike}" styleClass="input select" alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					仪器仪表型号&nbsp;
				</td>
				<td class="content">
					<input type="text" name="apparatusTypeStringLike" value="${apparatusTypeStringLike}" class="text medium">
				</td>
					<td class="label">
					产权属性&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox name="apparatusPropertyStringLike" id="apparatusProperty"  initDicId="1230103" styleClass="input select" 
					defaultValue="${apparatusPropertyStringLike}" alt="allowBlank:false">
					</eoms:comboBox> 
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
	<logic:notEmpty name="apparatusList" scope="request">
	<display:table name="apparatusList" cellspacing="0" cellpadding="0"
		id="apparatusList" pagesize="${pageSize}" class="table"
		export="false"	requestURI="${app}/partner/resourceInfo/apparatus.do?method=search"
		sort="list" partialList="true" size="${resultSize}">
	<display:column  sortable="true" headerClass="sortable" title="序号">${apparatusList.id}
	</display:column>	
	<display:column  sortable="true" headerClass="sortable"   title="仪器仪表名称" >
		<eoms:id2nameDB id="${apparatusList.apparatusName}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="区域">
		<eoms:id2nameDB id="${apparatusList.area}"	beanId="tawSystemAreaDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="代维公司">
			<eoms:id2nameDB id="${apparatusList.maintenanceCompany}"	beanId="tawSystemDeptDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="专业" >
		<eoms:id2nameDB id="${apparatusList.maintenanceMajor}"  beanId="ItawSystemDictTypeDao" />
	</display:column>		
	<display:column  sortable="true" headerClass="sortable" title="仪器仪表型号">${apparatusList.apparatusType}
	</display:column>			
	<display:column   sortable="true" headerClass="sortable" title="产品序列号" >${apparatusList.apparatusSerialNumber}
	</display:column>			
	<%--<display:column  sortable="true" headerClass="sortable" title="采购时间">${apparatusList.purchasingTime}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="出厂日期">${apparatusList.apparatusDateOfProduction}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="使用年限">${apparatusList.apparatusServiceLife}
	</display:column>--%>			
	<display:column  sortable="true" headerClass="sortable" title="状态" >
		<eoms:id2nameDB id="${apparatusList.apparatusStatus}"  beanId="ItawSystemDictTypeDao" />
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="产权属性">
		<eoms:id2nameDB id="${apparatusList.apparatusProperty}"  beanId="ItawSystemDictTypeDao" />
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="资产所属">${apparatusList.apparatusBelongs}
	</display:column>			
	<display:column sortable="false" headerClass="sortable" title="详情" media="html">
		<a href="${app}/partner/resourceInfo/apparatus.do?method=detail&&id=${apparatusList.id }" target="blank" shape="rect">
			<img src="${app}/images/icons/table.gif">
		</a>
	</display:column>
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
</display:table>

<eoms:excelExport modelName="com.boco.eoms.partner.resourceInfo.model.Apparatus" 	 serviceBeanId="apparatusService"   title="仪器仪表信息" >
		<eoms:row name="序号" value="id"/>
		<eoms:row name="区域" value="area" dictDaoName="tawSystemAreaDao" />
		<eoms:row name="维护专业" value="maintenanceMajor" dictDaoName="ItawSystemDictTypeDao" />
		<eoms:row name="仪器仪表名称" value="apparatusName" dictDaoName="ItawSystemDictTypeDao"/>
		<eoms:row name="型号" value="apparatusType"/>
		<eoms:row name="产品序列号" value="apparatusSerialNumber"/>
		<eoms:row name="采购时间" value="purchasingTime"/>
		<eoms:row name="出厂日期" value="apparatusDateOfProduction"/>
		<eoms:row name="使用年限" value="apparatusServiceLife"/>
		<eoms:row name="状态" value="apparatusStatus" dictDaoName="ItawSystemDictTypeDao" />
		<eoms:row name="产权属性" value="apparatusProperty" dictDaoName="ItawSystemDictTypeDao"/>
		<eoms:row name="资产所属" value="apparatusBelongs"/>
		<eoms:row name="代维公司组织机构" value="maintenanceCompany" dictDaoName="tawSystemDeptDao"/>
		<eoms:row name="备注" value="notes"/>
		<eoms:row name="创建时间" value="addTime"/>
</eoms:excelExport>
</logic:notEmpty> 
<br><br>
<logic:empty name="apparatusList" scope="request">
没有记录！
</logic:empty> 
<%@ include file="/common/footer_eoms.jsp"%>