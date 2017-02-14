<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>

<form action="fiber.do?method=list" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">光纤类型：</td>
					<td>
						<input class="text" type="text" name="fiberTypeStringLike"/>
					</td>
						<td class="label">光纤级别：</td>
					<td>
						<input class="text" type="text" name="fiberLevelStringLike"/>
					</td>
				</tr>
				<tr>
					<td class="label">数量大于：</td>
					<td>
						<input class="text" type="text" name="fiberNumberMt" id="digit1"
					</td>
						<td class="label">数量小于：</td>
					<td>
						<input class="text" type="text" name="fiberNumberLt" id="digit2"
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="查询" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>

<!-- Information hints area end-->
<logic:present name="fiberInformationList" scope="request">
	<display:table name="fiberInformationList" cellspacing="0" cellpadding="0"
		id="fiberInformationList" class="table fiberInformationList" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="fiber.do?method=list" 
		partialList="true" size="${size}">
		
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
         <input type="checkbox" name="checkbox11" value="<c:out value='${fiberInformationList.id}'/>" >
    	</display:column>
		<display:column property="fiberType" sortable="true" headerClass="sortable" title="光缆类型" />
		<display:column property="fiberLevel" sortable="true" headerClass="sortable" title="光缆级别" />
		<display:column property="fiberStructure" sortable="true" headerClass="sortable" title="光缆结构" />
		<display:column property="fiberNumber" sortable="true" headerClass="sortable" title="数量" />
		<display:column property="fiberCoreNumber" sortable="true" headerClass="sortable" title="纤芯数" />
		<display:column property="fiberCoreType" sortable="true" headerClass="sortable" title="纤芯类型" />
		
		<display:column  headerClass="sortable" title="修改">
			<a href="${app}/fiber/fiber.do?method=goToAddorEdit&type=edit&id=${fiberInformationList.id}">
				<img src="/nop3/images/icons/edit.gif">
			</a>
		</display:column>
		<display:column  headerClass="sortable" title="详情">
			<a href="${app}/fiber/fiber.do?method=goToAddorEdit&type=detail&id=${fiberInformationList.id}">
				<img src="/nop3/images/icons/table.gif">
			</a>
		</display:column>
	</display:table>
</logic:present>
<input type="button" value="新增" class="btn" onclick="location.href='${app}/fiber/fiber.do?method=goToAddorEdit&type=add'" />
<input type="button" value="删除" class="btn" onclick="javascritp:deletFibers();" />


<script type="text/javascript">
var myJ = $.noConflict();
    var checkflag = "false";
	function chooseAll(){	
	    var objs = document.getElementsByName("checkbox11");    
	    if(checkflag=="false"){
	        for(var i=0; i<objs.length; i++){
	           objs[i].checked="checked";
	        } 
	        checkflag="checked";
	    }
	    else if(checkflag=="checked"){ 	    	    
		    for(var i=0; i<objs.length; i++){
		           objs[i].checked=false;
		    } 
		    checkflag="false";
	    }
	}
myJ("input[id^='digit']").keyup(function(){
	var myValue=myJ(this).val();
	myJ(this).val(myValue.replace(/[^0-9]+/,''));
});
function deletFibers(){
	var _dealIds="";
	myJ("input[name='checkbox11']:checked").each(function(){
		_dealIds+=myJ(this).val()+";";
	});
	if(_dealIds==""){
		alert("请选择需要删除的信息");
		return false;
	}else{
		if(confirm("确认删除？")){
			location.href='${app}/fiber/fiber.do?method=delete&dealIds='+_dealIds;
			return true;
		}else{
			return false;
		}
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
