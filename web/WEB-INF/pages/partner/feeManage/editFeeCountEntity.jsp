<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<form action="${app}/partner/feeManage/feeCountManage.do?method=saveEditFeeCountEntity"
	method="post" id="filterForm" name="filterForm">

			<input type="hidden" name="feePoolMainId" id="feePoolMainId"  value="${feePoolMainId}"/>
			<input type="hidden" name="region" id="region"  value="${region}"/>
			<input type="hidden" name="company" id="company"  value="${company}"/>
			<input type="hidden" name="year" id="year"  value="${year}"/>
			<input type="hidden" name="month" id="month" " value="${month}"/>
			<input type="hidden" name="majorName" id="majorName"  value="${majorName}"/>
			<input type="hidden" name="majorId" id="majorId"  value="${majorId}"/>
			<input type="hidden" name="baseEntityId" id="baseEntityId"  value="${baseEntityId}"/>
<table class="table">
	<tr>
	<td  class="label">
			模板名称
		</td>
		<td>${feeCountPrcTmplName}
			
		</td>
	<td  class="label">
			单价
		</td>
		<td>
		${feePrice}
		</td>
	</tr>	
		    <input type="hidden" name="feeCountPrcTmplName" id="feeCountPrcTmplName" class="text" value="${feeCountPrcTmplName}"/>
            <input type="hidden" name="feeCountPrcTmplId" id="feeCountPrcTmplId" class="text" value="${feeCountPrcTmplId}"/>
            <input type="hidden" name="feeCountEntityId" id="feeCountEntityId" value="${feeCountEntityId}"/>
</table>
<c:forEach items='${tableList}' var="table">
<table  class="formTable">
<tbody>
<input type="hidden" id="tableId" name="tableId" value="${table.fce.cntTypId}"/>
<input type="hidden" id="resourceType" name="resourceType" value="${table.fce.resourceType}"/>
<input type="hidden" id="tableName" name="tableName" value="${table.fce.cntTypName}"/>
<input type="hidden" id="resourceTypeAnd" name="resourceTypeAnd" value="${table.fce.cntTypId}_${table.fce.resourceType}"/>
<tr>
			<td colspan="15">
				<div class="ui-widget-header">
				${table.fce.cntTypName}<input type="hidden" id="feeCountCntTypEntityId" name="feeCountCntTypEntityId" value="${table.fce.id}"/>
				
				<c:if test="${not empty table.fce.resourceType}">-<eoms:id2nameDB id="${table.fce.resourceType}"  beanId="ItawSystemDictTypeDao" /></c:if>
				</div>
			</td>
		</tr>
		<tr>
		<td>专业</td><td>计次类型</td>
		
		<c:if test="${not empty table.feeList[0].template.resourceType}"><td>资源类别
		</td></c:if>
		<c:if test="${not empty table.feeList[0].template.field1text}"><td>${table.feeList[0].template.field1text}
		</td></c:if>
		<c:if test="${not empty table.feeList[0].template.field2text}"><td>${table.feeList[0].template.field2text}
		</td></c:if>
		<c:if test="${not empty table.feeList[0].template.field3text}"><td>${table.feeList[0].template.field3text}
		</td></c:if>
		<c:if test="${not empty table.feeList[0].template.field4text}"><td>${table.feeList[0].template.field4text}
		</td></c:if>
		<c:if test="${not empty table.feeList[0].template.field5text}"><td>${table.feeList[0].template.field5text}
		</td></c:if>
		
		<td>单价系数</td>
		<td>数量</td>
		<td>查看计次详情</td>
		</tr>
<c:forEach items='${table.feeList}' var="fee">
<tr><input type="hidden" id="feeCountDetailTemplateId_${fee.template.owncnttypid}_${fee.template.resourceType}" name="feeCountDetailTemplateId_${fee.template.owncnttypid}_${fee.template.resourceType}" value="${fee.template.id}">
    <input type="hidden" id="feeCountDetailEntityId_${fee.template.owncnttypid}_${fee.template.resourceType}" name="feeCountDetailEntityId_${fee.template.owncnttypid}_${fee.template.resourceType}" value="${fee.entity.id}">
<td>${majorName}</td><td>${table.fce.cntTypName}</td>
<c:if test="${not empty fee.template.resourceType}"><td><eoms:id2nameDB id="${fee.template.resourceType}"  beanId="ItawSystemDictTypeDao" /></td></c:if>
<c:if test="${not empty fee.template.field1text}"><td>${fee.template.field1dict}</td></c:if>
<c:if test="${not empty fee.template.field2text}"><td>${fee.template.field2dict}</td></c:if>
<c:if test="${not empty fee.template.field3text}"><td>${fee.template.field3dict}</td></c:if>
<c:if test="${not empty fee.template.field4text}"><td>${fee.template.field4dict}</td></c:if>
<c:if test="${not empty fee.template.field5text}"><td>${fee.template.field5dict}</td></c:if>

<td><input type="hidden" id="unitPrict_${fee.template.owncnttypid}_${fee.template.resourceType}" name="unitPrict_${fee.template.owncnttypid}_${fee.template.resourceType}" value="${fee.template.dtlprc}"/>${fee.template.dtlprc}</td>
<td><input type="text" id="count_${fee.template.owncnttypid}_${fee.template.resourceType}" name="count_${fee.template.owncnttypid}_${fee.template.resourceType}" value="${fee.entity.count}" onchange="loadshdMnyAmt(this)"  readOnly="true" style="border:none;background:none;"/></td>
<td>   
<!--<a href="${app}/netresource/pnrResMeteringAction.do?method=pnrResMeteringSearch&metering_type=${table.fce.cntTypName}&s_year=${year}&s_mon=${month}&region_comany_id=${company}&specialty=${majorId}">
					 -->			<img src="${app}/images/icons/search.gif" onClick='showDetail("${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&metering_type=${table.fce.cntTypId}&holdyear=${year}&holdmonth=${month}&region_comany_id=${company}&specialty=${majorId}&resourceType=${fee.template.resourceType}");'/>
	<!--		</a>   -->
   
      </td>
</tr>
</c:forEach>
</tbody>
</table>
<table  class="formTable">
<tr><td  class="label">
				小计
			</td>
		<td class="label">
				<font color='red'> * </font>应付款（元）：
			</td>
		<td>
		<input type="text" id="shdMnyAmt_${table.fce.cntTypId}_${table.fce.resourceType}" name="shdMnyAmt_${table.fce.cntTypId}_${table.fce.resourceType}" value="${table.fce.shdMnyAmt}" class="text" alt="allowBlank:false" readOnly="true" style="border:none;background:none;" />
		</td>
		<td class="label">
				<font color='red'> * </font>实付款（元）：
			</td>
		<td>
		<input type="text" id="realMnyAmt_${table.fce.cntTypId}_${table.fce.resourceType}" name="realMnyAmt_${table.fce.cntTypId}_${table.fce.resourceType}" value="${table.fce.realMnyAmt}" class="text" alt="allowBlank:false" onchange="loadTotalR();"/>
		</td></tr></table>
</c:forEach>
<br/>
<!--  
<input type="button" value="提交" onclick="mit()"/>
-->
<table class="formTable">
<tr>
			<td colspan="15">
				<div class="ui-widget-header">
				总计
				</div>
			</td>
		</tr>
<tr>

<td class=label >总计应付款</td><td><input type="text" id="totalS" name="totalS" class="text" readOnly="true" style="border:none;background:none;" value="${shdMnyAmt}"/></td>
<td class=label >总计实付款</td><td><input type="text" id="totalR" name="totalR" class="text" readOnly="true" style="border:none;background:none;" value="${realMnyAmt}"/></td>
</tr><tr ><td>
		备注:</td><td colspan="3">
				<textarea name="remark" id="remark" class="text medium" cols="100">${remark}</textarea>
				</td></tr>
			</table>	<br/>
<input type="submit" value="提交" />
<input type="button" value="重新选择模板" onclick="submitAFrom()"/>
</form>

<form action="${app}/partner/feeManage/feeCountManage.do?method=feeCountPrcTmplListByMajor4entity&majorId=${majorId}&id=${feePoolMainId}&delEntityId=${feeCountEntityId}"
	method="post" id="aFrom" name="aFrom">
	
	</form>
<script type="text/javascript">
var qj=jQuery.noConflict();
window.onload=function(){
var v = new eoms.form.Validation({form:"filterForm"});
			  v.custom = function(){
			  try{
			//  var counts =qj("input[id^='count_']");
			//  for(var i=0;i<counts.length;i++){
			//  var count=counts[i];
			//  if(notNumber("3",count.value.trim())){
			//  count.focus();
			//  alert("数量必须是大于等于0的数字，最多两位小数");
			//  return false;
			//  }
			//  }
			  
			  var realMnyAmts =qj("input[id^='realMnyAmt_']");
			  for(var i=0;i<realMnyAmts.length;i++){
			  var realMnyAmt=realMnyAmts[i];
			  if(notNumber("3",realMnyAmt.value.trim())){
			  realMnyAmt.focus();
			  alert("实付款必须是大于等于0的数字，最多三位小数");
			  return false;
			  }
			  }
			  
			  
			  }
			  catch(e){
	
		return false;
	}
	
	return true;
			  }


}
function loadshdMnyAmt(select){
var tbody=select.parentNode.parentNode.parentNode;
var tableId=tbody.getElementsByTagName("input")[0].value;
var resourceTypeId=tbody.getElementsByTagName("input")[1].value;
var rows=tbody.rows;
var price=0;
var feePrice='${feePrice}';
var shdMnyAmt=document.getElementById("shdMnyAmt_"+tableId+"_"+resourceTypeId);
var realMnyAmt=document.getElementById("realMnyAmt_"+tableId+"_"+resourceTypeId);

for(var i=2;i<rows.length;i++){
var inputs=rows[i].getElementsByTagName("input");

price=inputs[inputs.length-1].value*feePrice+price;
}
shdMnyAmt.value=parseFloat(price).toFixed(3);
realMnyAmt.value=parseFloat(price).toFixed(3);
var tables =qj("input[id^='shdMnyAmt_']");
var totalPrice=0;
for(var i=0;i<tables.length;i++){
			  var table=tables[i];
			  if(table.value.length>0&&!isNaN(table.value)){
			  totalPrice=totalPrice+parseFloat(table.value);
			  }
			  
 }			  
document.getElementById("totalS").value=parseFloat(totalPrice).toFixed(3);		
document.getElementById("totalR").value=parseFloat(totalPrice).toFixed(3)	
}
function notNumber(num,inputString){
		
		 var price=/^([1-9]{1}[0-9]{0,3}(\,[0-9]{4})*(\.[0-9]{0,3})?|[1-9]{1}\d*(\.[0-9]{0,3})?|0(\.[0-9]{0,3})?|(\.[0-9]{1,3})?)$/;
		 var aa=inputString*1;
		 
		 if(inputString.match(price) && ""!=inputString)
		 {
		 if(aa<0){
		 
			return true;
		 }else{
		 var bb=inputString.split(".");
		 if(bb==inputString){
		 	return false;
		 }
		 else{
		 var bbLength=bb[1].length;
		 if(bbLength>num){
		  	return true;
		 }
		 else{
		 
		  	return false;
		 }
		 }
		 }
		}
		 else{
		  	return true;
		 }
		}







  function notNumber1(value){
  	   	var price=/^([1-9]{1}[0-9]{0,3}(\,[0-9]{4})*(\.[0-9]{0,3})?|[1-9]{1}\d*(\.[0-9]{0,3})?|0(\.[0-9]{0,3})?|(\.[0-9]{1,3})?)$/;
  	   	var price1=/^\\d+(\\.\\d+)?$/
        var value=value; 
   		if(value.match(price) && ""!=value){
   			return false;
      	}else{
           	return true;
      	}
  }
function loadTotalR(){
var tables =qj("input[id^='realMnyAmt_']");
var totalPrice=0;
for(var i=0;i<tables.length;i++){
			  var table=tables[i];
			  if(table.value.length>0&&!isNaN(table.value)){
			  totalPrice=totalPrice+parseFloat(table.value);
			  }
			  
 }			  
document.getElementById("totalR").value=parseFloat(totalPrice).toFixed(3);		


}
function submitAFrom(){

var afrom=document.getElementById("aFrom").submit();
}

function showDetail(id){
      var url=id;
      window.open (url, "详细", "height=450, width=800, top=200, left=200, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no");     
  } 
</script>
<%@ include file="/common/footer_eoms.jsp"%>