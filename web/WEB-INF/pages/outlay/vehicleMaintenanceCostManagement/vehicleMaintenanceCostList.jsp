<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
var myJ = $.noConflict();
 myJ(function(){
 	//为Tr加上点击事件，选择checkbox
 	//在非关联窗口打开时使用，否则会与关联时checkbox单选操作冲突
 	if(!window.opener){
		myJ('table#vehicleMaintenanceCost').find('tr:gt(0)')
		.css('cursor','pointer')
		.bind('click',function(event){
			var myCheckbox =  myJ(this).find('input:checkbox');
			if( myCheckbox[0].checked){
				myCheckbox.attr('checked',false);
			}else{
				myCheckbox.attr('checked',true);
			}
			
			//只有当可选的checkBox的个数实际等于已经选择的checkbox的个数时，才选择上用于全选的checkbox
			if( myJ('input:checkbox.checkAble').size() == myJ(':checked.checkAble').size()){
				myJ('input#myCheckbox').attr('checked',true);		 
			}else{
				myJ('input#myCheckbox').attr('checked',false);	
			}
			
		});
	}
	
	myJ('input#myCheckbox').bind('click',function(event){
		//至少有1个checkbox没被选中的话，则执行全选操作，否则执行反选操作
		var iCheckbox = myJ('input:checkbox.checkAble');
		var iCheckedbox = myJ(':checked.checkAble');
		var iCheckboxSize = iCheckbox.size();
		var iCheckedboxSize = iCheckedbox.size();
		
		if(iCheckboxSize>iCheckedboxSize){
			myJ('input.checkAble').attr('checked',true);
		}else{
			myJ('input.checkAble').attr('checked',false);
		}
	});
	
	myJ('input.checkAble').bind('click',function(event){
		if(myJ(this)[0].checked){
			myJ(this).attr('checked',false);
		}else{
			myJ(this).attr('checked',true);
		}
	});
	myJ('input#showSearch').bind('click',function(event){
		myJ('form#searchForm').toggle();
	});
	myJ('form#searchForm').hide();
});
	
	
	
function goAdd(){
	myJ("form#mainForm").get(0).action = "VehicleMaintenanceCostManagement.do?method=add";
	myJ("form#mainForm").submit();
}
</script>
<input type="button" id="showSearch" value="快速查询" class="btn" />
	<form action="${app}/outlay/VehicleMaintenanceCostManagement.do?method=list" method="post" id="searchForm">
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					部门
				</td>
				<td>
				<input type="text" id="partnerName_CN" name="partnerName_CN" value="<eoms:id2nameDB id='${partnerName2search}' beanId='partnerDeptDao'/>" alt="allowBlank:false"/>
				<input type="hidden" id="partnerName2search" name ="partnerName2search" value="${partnerName2search}"/></td>
				 <eoms:xbox id="partnerNameTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
						rootId="" rootText="代维公司树"  valueField="partnerName2search" handler="partnerName_CN" 
						textField="partnerName_CN" checktype="dept" single="true" />
				</td>
			<td class="label">
				开始日期
			</td>
			<td>
				<input type="text" class="text"
					name="startDate2search"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"
					alt="vtype:'lessThen',link:'endDate2search',vtext:'开始时间不能晚于结束时间',allowBlank:false"
					id="startDate2search" value="${startDate2search}"/>
			</td>
			<td class="label">
				结束日期
			</td>
			<td>
				<input type="text" class="text"
					name="endDate2search"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"
					alt="vtype:'moreThen',link:'startDate2search',vtext:'结束时间不能早于开始时间',allowBlank:false"
					id="endDate2search" value="${endDate2search}"/>
			</td>
		</tr>
	</table>
		<input type="submit" value="提交查询"/>
	</form>

<form id="mainForm" action="/VehicleMaintenanceCostManagement.do?method=list" method="post"> 
<logic:notEmpty name="vehicleMaintenanceCostList">
<c:set var="myCheckboxAllBtn" scope="page">
	<input type="checkbox" id="myCheckbox" />
</c:set>
<display:table name="vehicleMaintenanceCostList" class="table" id="vehicleMaintenanceCost" export="true" sort="list"
		partialList="true" size="${size}" requestURI="VehicleMaintenanceCostManagement.do">  
<display:column media="html" title="${myCheckboxAllBtn}">
	<input type="checkbox" class="checkAble" value="${vehicleMaintenanceCost.id}" id="${vehicleMaintenanceCost.id}" />
</display:column>
<display:column title="合作伙伴" headerClass="sortable" sortable="true">
<eoms:id2nameDB id='${vehicleMaintenanceCost.partnerName}' beanId='partnerDeptDao'/>
</display:column>
<display:column property="vehicleDetail" title="车辆信息" headerClass="sortable" sortable="true"/>
<display:column property="createDate" title="时间" headerClass="sortable" sortable="true"/>
<display:column property="totalFee" title="费用" headerClass="sortable" sortable="true"/>
<display:column sortable="true" headerClass="sortable" title="查看"
	paramProperty="id" url="/outlay/VehicleMaintenanceCostManagement.do?method=detail"
	paramId="id" media="html">
	<img src="${app}/images/icons/search.gif">
</display:column>
<display:column sortable="true" headerClass="sortable" title="编辑"
	paramProperty="id" url="/outlay/VehicleMaintenanceCostManagement.do?method=edit"
	paramId="id" media="html">
	<img src="${app}/nop3/images/edit.gif">
</display:column>
<display:column sortable="true" headerClass="sortable" title="删除"
	paramProperty="id" url="/outlay/VehicleMaintenanceCostManagement.do?method=remove"
	paramId="id" media="html">
	<img src="${app}/nop3/images/delete.gif">
</display:column>
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
</display:table>
</logic:notEmpty>
<input type="button" name="add" id="add" value="新增" onclick="goAdd();"/> 
</form>
<%@ include file="/common/footer_eoms.jsp"%>