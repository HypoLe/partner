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
 	//在非关联窗口打开时使用，否则会与关联时checkbox单选操作冲突  !window.opener
 	if(false){
		myJ('table#netOilEngine').find('tr:gt(0)')
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
	myJ("form#mainForm").get(0).action = "NetOilEngineManagement.do?method=add";
	myJ("form#mainForm").submit();
}

function removeSelected(){
	var inputOBJ = document.getElementsByTagName("input");
	var chkValueArray = new Array();
	var j=0;
	for(var i=0;i<inputOBJ.length;i++){
		if(inputOBJ[i].type == 'checkbox'){
			if(inputOBJ[i].checked == true){
				if(inputOBJ[i].value != 'on'){
					chkValueArray[j] = inputOBJ[i].value;
					j++;
				}
			}
		}
	}
	
	if(chkValueArray.length==0){
		alert("请选择要删除的项目!");
	}else{
		document.all["removeMore"].value = chkValueArray;
		myJ("form#mainForm").get(0).action = "NetOilEngineManagement.do?method=removeMore";
		myJ("form#mainForm").submit();
	}
}
</script>
<input type="button" id="showSearch" value="快速查询" class="btn" />
	<form action="${app}/outlay/NetOilEngineManagement.do?method=search" method="post" id="searchForm">
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					告警标题
				</td>
				<td>
				<input type="text" id="partnerName2search" name="partnerName2search"/>
				</td>
			<td class="label">
				告警开始时间范围从：
			</td>
			<td>
				<input type="text" class="text"
					name="startDate2search"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"
					alt="vtype:'lessThen',link:'endDate2search',vtext:'开始时间不能晚于结束时间',allowBlank:false"
					id="startDate2search" value="${startDate2search}"/>
			</td>
			<td class="label">
				到
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
<form id="mainForm" action="/NetOilEngineManagement.do?method=list" method="post"> 
<logic:notEmpty name="netOilEngineList">
<c:set var="myCheckboxAllBtn" scope="page">
	<input type="checkbox" id="myCheckbox" />
</c:set>
<display:table name="netOilEngineList" class="table" id="netOilEngine" export="true" sort="list"
		partialList="true" size="${size}" requestURI="NetOilEngineManagement.do">  
<display:column media="html" title="${myCheckboxAllBtn}">
	<input type="checkbox" class="checkAble" value="${netOilEngine.id}" id="${netOilEngine.id}" />
</display:column>

<display:column property="city" title="地市" headerClass="sortable" sortable="true"/>
<display:column property="country" title="县区" headerClass="sortable" sortable="true"/>
<display:column property="station" title="基站名称" headerClass="sortable" sortable="true"/>
<display:column property="beginTime" title="发生时间" headerClass="sortable" sortable="true"/>
<display:column property="endTime" title="结束时间" headerClass="sortable" sortable="true"/>
<display:column sortable="true" headerClass="sortable" title="查看"
	paramProperty="id" url="/partner/oilmachine/NetOilEngineManagement.do?method=detail"
	paramId="id" media="html">
	<img src="${app}/images/icons/search.gif">
</display:column>
<display:column sortable="true" headerClass="sortable" title="编辑"
	paramProperty="id" url="/partner/oilmachine/NetOilEngineManagement.do?method=edit"
	paramId="id" media="html">
		<img src="${app}/nop3/images/edit.gif">
</display:column>
<display:column sortable="true" headerClass="sortable" title="删除"
	paramProperty="id" url="/partner/oilmachine/NetOilEngineManagement.do?method=remove"
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
<input type="button" name="removeSelect" id="removeSelect" value="删除选中" onclick="removeSelected();"/> 
<input type="hidden"  name="removeMore" value=""/>
</form>
<%@ include file="/common/footer_eoms.jsp"%>