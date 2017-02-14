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
		var formElement=document.getElementById("mobileTerminalForm")
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
  	<caption><div class="header center">移动终端信息列表	</div></caption>
</table>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
<form action="${app}/partner/resourceInfo/mobileTerminal.do?method=search" id="mobileTerminalForm" method="post">
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
						设备编号&nbsp;
					</td>
					<td class="content">
						<input type="text" name="mobileTerminalNumberStringLike" class="text medium" 	id="mobileTerminalNumberStringLike" value="${mobileTerminalNumberStringLike}"/>
					</td>
					<td class="label">
						手机号码&nbsp;
					</td>
					<td class="content">
						<input type="text" name="simCardNumberStringLike" class="text medium"	id="simCardNumberStringLike" value="${simCardNumberStringLike}"/>
					</td>
			</tr>
			<tr>
					<td class="label">
						移动终端类型&nbsp;
					</td>
					<td class="content" colspan="4">
						<eoms:comboBox name="mobileTerminalTypeStringLike" styleClass="input select" id="mobileTerminalTypeStringLike" 
						initDicId="1230401" defaultValue="${mobileTerminalTypeStringLike}"/>
					</td>
					<%--<td class="label">
						SIM类型&nbsp;*
					</td>
					<td class="content">
						<eoms:comboBox name="simCardTypeStringLike" id="simCardTypeStringLike" 	initDicId="1230402" 
						styleClass="input select" defaultValue="${simCardTypeStringLike}"/>
					</td>
			--%></tr>
			<tr>
				<td colspan='4' class='label'>
					<input type="submit" value="查询">
					<input type="button"  id="reset" value="重置" onclick="res();">
				</td>
			</tr>
	</table>
</form>
</div>
	<logic:notEmpty name="mobileTerminalList" scope="request">
	<display:table name="mobileTerminalList" cellspacing="0" cellpadding="0"	id="mobileTerminalList" pagesize="${pageSize}" class="table"
		export="false"		requestURI="${app}/partner/resourceInfo/mobileTerminal.do?method=search"
		sort="list" partialList="true" size="${resultSize}">
	<display:column  sortable="true" headerClass="sortable" title="序号">${mobileTerminalList.id}
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="设备编号" >
		${mobileTerminalList.mobileTerminalNumber}
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="所属区域">
		<eoms:id2nameDB id="${mobileTerminalList.area}"	beanId="tawSystemAreaDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="代维公司">
		<eoms:id2nameDB id="${mobileTerminalList.maintainCompany}"	beanId="tawSystemDeptDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="维护组">
		<eoms:id2nameDB id="${mobileTerminalList.maintainTeam}"	beanId="tawSystemDeptDao" />
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="设备序列号">${mobileTerminalList.mobileTerminalSerilNumber}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="手机号码">${mobileTerminalList.simCardNumber}
	</display:column>			
	<%--<display:column  sortable="true" headerClass="sortable" title="SIM类型">
		<eoms:id2nameDB id="${mobileTerminalList.simCardType}" beanId="ItawSystemDictTypeDao" />
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="设备识别码">${mobileTerminalList.identificationCode}
	</display:column>--%>
	
	<display:column  sortable="true" headerClass="sortable" title="移动终端类型">
		<eoms:id2nameDB id="${mobileTerminalList.mobileTerminalType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="生产厂家">${mobileTerminalList.mobileTerminalFactory}
	</display:column>	
	<display:column sortable="false" headerClass="sortable" title="详情" media="html">
		<a href="${app}/partner/resourceInfo/mobileTerminal.do?method=detail&&id=${mobileTerminalList.id }" target="blank" shape="rect">
			<img src="${app}/images/icons/table.gif">
		</a>
	</display:column>
	<%--<display:setProperty name="export.rtf" value="false" />
	<display:setProperty name="export.pdf" value="false" />
	<display:setProperty name="export.xml" value="false" />
	<display:setProperty name="export.csv" value="false" />	
--%></display:table>
<eoms:excelExport modelName="com.boco.eoms.partner.resourceInfo.model.MobileTerminal" 	 serviceBeanId="mobileTerminalService"   title="移动终端信息" >
		<eoms:row name="序号" value="id"/>
		<eoms:row name="区域" value="area" dictDaoName="tawSystemAreaDao" />
		<eoms:row name="代维公司" value="maintainCompany" dictDaoName="tawSystemDeptDao"/>
		<eoms:row name="维护组" value="maintainTeam" dictDaoName="tawSystemDeptDao" />
		<eoms:row name="移动终端类型" value="mobileTerminalType" dictDaoName="ItawSystemDictTypeDao"/>
		<eoms:row name="设备编号" value="mobileTerminalNumber" />
		<eoms:row name="设备序列号" value="mobileTerminalSerilNumber" />
		<eoms:row name="生产厂家" value="mobileTerminalFactory"/>
		<eoms:row name="手机号" value="simCardNumber" />
		<eoms:row name="备注" value="notes"/>
		<eoms:row name="创建时间" value="addTime"/>
</eoms:excelExport>
</logic:notEmpty> 
<br><br>
<logic:empty name="mobileTerminalList" scope="request">
没有记录！
</logic:empty> 
<%@ include file="/common/footer_eoms.jsp"%>