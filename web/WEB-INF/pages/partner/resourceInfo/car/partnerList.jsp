<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" 	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" 	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
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
		var formElement=document.getElementById("carForm")
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
  	<caption><div class="header center">车辆信息列表</div></caption>
</table>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
<form action="${app}/partner/resourceInfo/car.do?method=search" id="carForm" method="post">
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
					车辆品牌&nbsp;
				</td>
				<td class="content">
					<input type="text" name="carBrandStringLike" class="text medium" id="carBrand" value="${carBrandStringLike}"/>
				</td>
				<td class="label">
					车辆型号&nbsp;
				</td>
				<td class="content">
					<input type="text" name="carModelStringLike"class="text medium" id="carModel" value="${carModelStringLike}"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					燃料种类&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox name="fuleTypeStringLike" id="fuleTypeStringLike"  initDicId="1230203" alt="allowBlank:false"  defaultValue="${fuleTypeStringLike}" styleClass="input select">
					</eoms:comboBox> 
				</td>
				<td class="label">
					标准油耗(L/百公里)&nbsp;
				</td>
				<td class="content">
					<input type="text" name="fuleConStandardStringLike" class="text medium" id="fuleConStandardStringLike" value="${fuleConStandardStringLike}"/>
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
		<tr>
				<td class="label">
					车载GPS设备编号&nbsp;
				</td>
				<td class="content">
					<input type="text" name="carGPSNumberStringLike" id="carGPSNumberStringLike" class="text medium"  value="${carGPSNumberStringLike}" />
					<span class="error" id="error2"></span>
				</td>
				<td class="label">
					车载GPS设备厂家&nbsp;
				</td>
				<td class="content">
					<input type="text" name="carGPSFactoryStringLike" id="carGPSFactoryStringLike" 	class="text medium" 	 value="${carGPSFactoryStringLike}"/>
				</td>
		</tr>
		<tr>
			<td class="label">
						车载GPS移动号码&nbsp;
			</td>
			<td class="content" colspan="3">
				<input type="text" name="carGPSSimCardNumberStringLike" id="carGPSSimCardNumberStringLike" class="text medium" 	 value="${carGPSSimCardNumberStringLike}"/>
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
	<logic:notEmpty name="carList" scope="request">
	<display:table name="carList" cellspacing="0" cellpadding="0"	id="carList" pagesize="${pageSize}" class="table"		export="false"
		requestURI="${app}/partner/resourceInfo/car.do?method=search" 	sort="list" partialList="true" size="${resultSize}">
	<display:column  sortable="true" headerClass="sortable" title="序号">${carList.id}
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="区域">
		<eoms:id2nameDB id="${carList.area}"	beanId="tawSystemAreaDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="代维公司">
		<eoms:id2nameDB id="${carList.maintainCompany}"	beanId="tawSystemDeptDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="车牌号">${carList.carNumber}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="车辆类型">${carList.carType}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="车载GPS设备编号">${carList.carGPSNumber}
	</display:column>	
	<%--		
	<display:column  sortable="true" headerClass="sortable" title="车载GPS厂家">${carList.carGPSFactory}
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="车载GPS的SIM卡号">${carList.carGPSSimCardNumber}
	</display:column>	
	--%><display:column  sortable="true" headerClass="sortable" title="车辆状态">
		<eoms:id2nameDB id="${carList.carStatus}"  beanId="ItawSystemDictTypeDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="产权属性">
		<eoms:id2nameDB id="${carList.carProperty}"  beanId="ItawSystemDictTypeDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="负责人/驾驶员">
		<eoms:id2nameDB id="${carList.driver}"  beanId="partnerUserDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="联系方式/驾驶员联系方式">${carList.driverContact}
	</display:column>
		<display:column sortable="false" headerClass="sortable" title="查看使用历史" media="html">
		<a href="${app}/partner/resourceInfo/car.do?method=getCarAllTaskList&&carNumber=${carList.carNumber }" target="_blank" shape="rect">
			查看
		</a>
	</display:column>
	<display:column sortable="false" headerClass="sortable" title="查看当前任务" media="html">
		<c:if test="${carList.dispatchStatus=='2'}">
			<a href="#" onclick="checkCarCurrentTask('${carList.carNumber }','${carList.applyId }');">
				查看
			</a>
		</c:if>
	</display:column>
	<display:column sortable="false" headerClass="sortable" title="详情" media="html">
		<a href="${app}/partner/resourceInfo/car.do?method=detail&&id=${carList.id }" target="blank" shape="rect">
			<img src="${app}/images/icons/table.gif">
		</a>
	</display:column>
<%--
	<display:setProperty name="export.rtf" value="false" />
	<display:setProperty name="export.pdf" value="false" />
	<display:setProperty name="export.xml" value="false" />
	<display:setProperty name="export.csv" value="false" />	
--%></display:table>
<eoms:excelExport modelName="com.boco.eoms.partner.resourceInfo.model.Car" 	 serviceBeanId="carService"   title="车辆信息" >
		<eoms:row name="序号" value="id"/>
		<eoms:row name="区域" value="area" dictDaoName="tawSystemAreaDao" />
		<eoms:row name="代维公司" value="maintainCompany" dictDaoName="tawSystemDeptDao"/>
		<eoms:row name="车牌号" value="carNumber" />
		<eoms:row name="车辆类型" value="carType" />
		<eoms:row name="车辆型号" value="carModel" />
		<eoms:row name="车辆品牌" value="carBrand" />
		<eoms:row name="燃料种类" value="fuleType"  dictDaoName="ItawSystemDictTypeDao" />
		<eoms:row name="标准油耗(L/百公里)" value="fuleConStandard" />
		<eoms:row name="车载GPS设备编号" value="carGPSNumber"/>
		<eoms:row name="车载GPS设备厂家" value="carGPSFactory"/>
		<eoms:row name="车载GPS移动号码" value="carGPSSimCardNumber"/>
		<eoms:row name="负责人(驾驶员)" value="driver" dictDaoName="partnerUserDao"/>
		<eoms:row name="联系方式(驾驶员联系方式)" value="driverContact"/>
		<eoms:row name="车辆状态" value="carStatus" dictDaoName="ItawSystemDictTypeDao" />
		<eoms:row name="产权属性" value="carProperty" dictDaoName="ItawSystemDictTypeDao"/>
		<eoms:row name="备注" value="notes"/>
		<eoms:row name="创建时间" value="addTime"/>
</eoms:excelExport>
</logic:notEmpty> 
<br><br>
<logic:empty name="carList" scope="request">
没有记录！
</logic:empty> 
<%@ include file="/common/footer_eoms.jsp"%>