<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" href="${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/demos.css">
<link rel="stylesheet" type="text/css" href="${app}/nop3/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>

<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>

<script>
var jq=jQuery.noConflict();
Ext.onReady(function(){
	 var v= new eoms.form.Validation({form:'feePoolMainForm'});
	 });
function returnBack(){
	//window.history.back();
	var url;
	  if('${pageType}'=='specialtyList'){    
	     url='${app}/partner/feeManage/feeManage.do?method=goToSecondList';   
	  }else if('${pageType}'=='lastSpecialtyList'){
	     url='${app}/partner/feeManage/feeManage.do?method=goToLastList';  
	  }else{
	     url='${app}/partner/feeManage/feeManage.do?method=goToSecondList';
	  }
	  jq('form#aFrom').attr('action',url);
	  document.getElementById("aFrom").submit(); 
}
	
function selectAll() 
{ 
   var a = document.getElementsByTagName("input"); 
   for (var i=0; i<a.length; i++) 
   
      if ((a[i].type == "checkbox")&&(a[i].className == "checkAble")) a[i].checked =!a[i].checked; 
  } 
  
  
  function dealAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	var calc=0;
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";";
			calc++;
		}
	}
	if (idResult == "") {
		alert("请选择需要处理的记录");
		return false;
	} else {
		//如果只有1条记录的话，那么直接弹出隐藏域进行处理，如果有多条记录的话，则转入批处理页面
			if(confirm("确定处理记录吗？")){
				document.getElementById("dealIds").value=idResult.toString();
			}else{
				return false;
			}
		}
	}
  
  
  
</script>




<!-- Information hints area end-->


<logic:present name="feePoolMainList" scope="request">
<table class="formTable">
	<caption>
	<div class="header center">费用信息列表</div>
	</caption></table>
	<display:table name="feePoolMainList" cellspacing="0" cellpadding="0"
		id="feePoolMainList" pagesize="${pagesize}"
		class="table feePoolMainList" 
		requestURI="feeManage.do" sort="list"
		partialList="true" size="${size}">
		
       <c:if test="${pageType=='specialtyList'}">  
	   <display:column media="html" title="${myTitleSelectBtn}" >
			<c:if test="${feePoolMainList.status=='2'}">
			<input type="checkbox" class="checkAble" name="cardNumber" value="${feePoolMainList.id}" id="${feePoolMainList.id}" />
		    </c:if>
		    <c:if test="${feePoolMainList.status=='0'  or feePoolMainList.status=='1' or feePoolMainList.status=='3'}">
			<input type="checkbox" class="checkUnAble" readonly="readonly" disabled="disabled"/>
		    </c:if>
		</display:column>
		</c:if>

       <c:if test="${pageType=='lastSpecialtyList'}">  
	   <display:column media="html" title="${myTitleSelectBtn}" >
			<c:if test="${feePoolMainList.status=='3'}">
			<input type="checkbox" class="checkAble" name="cardNumber" value="${feePoolMainList.id}" id="${feePoolMainList.id}" />
		    </c:if>
		    <c:if test="${feePoolMainList.status=='0' or feePoolMainList.status=='1' or feePoolMainList.status=='2' or feePoolMainList.status=='4'or feePoolMainList.status=='5'}">
			<input type="checkbox" class="checkUnAble" readonly="readonly" disabled="disabled"/>
		    </c:if>
		</display:column>
		</c:if>

		<display:column sortable="true" headerClass="sortable" title="所属地市">
				<eoms:id2nameDB beanId="tawSystemAreaDao" id="${feePoolMainList.region}"/>
		</display:column>
				
		<display:column sortable="true" headerClass="sortable" title="代维公司">
			<eoms:id2nameDB id="${feePoolMainList.company}" beanId="partnerDeptDao" />
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="年份">
			${feePoolMainList.feeYear}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="月份">
			${feePoolMainList.feeMonth}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="专业">
		<eoms:id2nameDB id="${feePoolMainList.special}"  beanId="ItawSystemDictTypeDao" />	
		</display:column>
		
		<c:if test="${pageType=='roleList'}">
		<display:column sortable="true" headerClass="sortable" title="操作">
			<a href="${app}/partner/feeManage/feeManage.do?method=goToFeeInforForm&id=${feePoolMainList.id}&pageType=${pageType}&parentMainId=${parentMainId}">编辑费用信息</a>
		</display:column>
	    </c:if>
	    
	    <c:if test="${pageType=='specialtyList'}">  
	    
	    <display:column sortable="true" headerClass="sortable" title="应付款总额">
			${feePoolMainList.dueSum}
		</display:column>
	    <display:column sortable="true" headerClass="sortable" title="实付款总额">
			${feePoolMainList.paidSum}
		</display:column>
	    <display:column sortable="true" headerClass="sortable" title="扣款总额">
			${feePoolMainList.deductSum}
		</display:column>
	    
	    
	    <display:column sortable="true" headerClass="sortable" title="提交状态与详情">
		 <c:if test="${feePoolMainList.status=='0'  or feePoolMainList.status=='1' }">
		 未提交
		</c:if>
		
		<c:if test="${feePoolMainList.status=='2'}">
		<a href="${app}/partner/feeManage/feeManage.do?method=goToFeeInforForm&id=${feePoolMainList.id}&pageType=${pageType}&parentMainId=${parentMainId}">已提交</a>
		</c:if>
		<c:if test="${feePoolMainList.status=='3'}">
		<a href="${app}/partner/feeManage/feeManage.do?method=goToFeeInforForm&id=${feePoolMainList.id}&pageType=${pageType}&parentMainId=${parentMainId}">已审批</a>
		</c:if>
		</display:column>	
		</c:if>
		
	    <c:if test="${pageType=='lastSpecialtyList'}">  
	    
	    <display:column sortable="true" headerClass="sortable" title="应付款总额">
			${feePoolMainList.dueSum}
		</display:column>
	    <display:column sortable="true" headerClass="sortable" title="实付款总额">
			${feePoolMainList.paidSum}
		</display:column>
	    <display:column sortable="true" headerClass="sortable" title="扣款总额">
			${feePoolMainList.deductSum}
		</display:column>
	    
	    
	    <display:column sortable="true" headerClass="sortable" title="提交状态与详情">
		 <c:if test="${feePoolMainList.status=='0'  or feePoolMainList.status=='1' or feePoolMainList.status=='2'}">
		 未提交
		</c:if>
		
		<c:if test="${feePoolMainList.status=='3'}">
		<a href="${app}/partner/feeManage/feeManage.do?method=goToFeeInforForm&id=${feePoolMainList.id}&pageType=${pageType}&parentMainId=${parentMainId}">已提交</a>
		</c:if>
		<c:if test="${feePoolMainList.status=='4'}">
		<a href="${app}/partner/feeManage/feeManage.do?method=goToFeeInforForm&id=${feePoolMainList.id}&pageType=${pageType}&parentMainId=${parentMainId}">已审批</a>
		</c:if>
		</display:column>	
		</c:if>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
	
</logic:present>
		
<c:if test="${pageType=='specialtyList'}">  

<form action="feeManage.do?method=regionApprove" method="post" id="feePoolMainForm" name="feePoolMainForm" >
	<table id="sheet" class="formTable">
	<caption>
	<div class="header center">审核信息</div>
	</caption>

<tr>
	<td class="label">
					审批结果*
	</td>
		<td class="content">
				<select id="taskType" name="taskType"	>
						<option value='0' selected="selected">
							审批通过
						</option>
						<option value='1'>
							审批不通过
						</option>
					</select>
				</td>
			</tr>

<tr>
      <td class="label">
					审批意见*
				</td>
				<td colspan="3">
					<textarea id="taskName" class="textarea max" name="taskName"
						alt="width:500,allowBlank:false"></textarea>
				</td>
			</tr>
       <tr>
       </tr>
       <input type="hidden" name="dealIds" id="dealIds" />
       </table>
  <html:submit styleClass="btn"  property="method.save"  onclick="return dealAll();" 
	        styleId="method.save" value="提交"  ></html:submit>	 		
  <input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/><br/><br/>      
       </form>
</c:if>

<c:if test="${pageType=='lastSpecialtyList'}">  

<form action="feeManage.do?method=secondApprove" method="post" id="feePoolMainForm" name="feePoolMainForm" >
	<table id="sheet" class="formTable">
	<caption>
	<div class="header center">审核信息</div>
	</caption>

<tr>
	<td class="label">
					审批结果*
	</td>
		<td class="content">
				<select id="taskType" name="taskType"	>
						<option value='0' selected="selected">
							审批通过
						</option>
						<option value='1'>
							审批不通过
						</option>
					</select>
				</td>
			</tr>

<tr>
      <td class="label">
					审批意见*
				</td>
				<td colspan="3">
					<textarea id="taskName" class="textarea max" name="taskName"
						alt="width:500,allowBlank:false"></textarea>
				</td>
			</tr>
       <tr>
       </tr>
       <input type="hidden" name="dealIds" id="dealIds" />
       </table>
  <html:submit styleClass="btn"  property="method.save"  onclick="return dealAll();" 
	        styleId="method.save" value="提交"  ></html:submit>	 		
  <input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/><br/><br/>      
       </form>
</c:if>

<form method="post" id="aFrom" name="aFrom">
</form>
<%@ include file="/common/footer_eoms.jsp"%>
