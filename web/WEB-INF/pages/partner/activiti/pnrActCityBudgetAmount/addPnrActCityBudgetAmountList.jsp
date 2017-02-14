<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>	
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
Ext.onReady(function(){
	//地域树
    new xbox({
		btnId:'cityName',
			single : true,
		treeDataUrl:'${app}/xtree.do?method=area&areaMaxLevel=2',treeRootId:'28',treeRootText:'全省',treeChkType:'area',
		showChkFldId:'cityName',saveChkFldId:'cityId',returnJSON:false
	});
})

function formatStringToFloatNew(strValue){
	if(strValue!=""){
		return parseFloat(strValue);
	}else{
		return 0;
	}	
}

//第一列为序号
//jq(function(){
	//jq("#addPnrActCityBudgetAmountList tbody tr ").find("td:eq(4)").click(function(){
		//var pare=jq(this).parent().find("td:eq(0) input:hidden");
		//var td = jq(this); 
	//	var txt = td.text(); 
	//	var input = jq("<input type='text' style='width:100%;' value='" + txt + "'/>"); 
	//	td.html(input); 
	//	input.click(function() { return false; }); 
		
		//获取焦点 
	//	input.trigger("focus"); 
		
		
		
		//文本框失去焦点后提交内容，重新变为文本 <p></p>
	//	input.blur(function() {
	//	    var a=/^([1-9]\d*(\.\d+)?|0)$/; 
		//	var newtxt = jq(this).val(); 
			//alert("newtxt="+newtxt);
		//	if (newtxt != txt) {
			//	if(newtxt==''){
				//	alert("地市金额不可以为空!");
				//	td.html(txt); 
				//	return;
			//	}
			//	if(!a.test(newtxt)){
				//	alert("地市金额请输入整数或小数");
				//	td.html(txt);
			//	}else{
				//判断文本有没有修改
				//	jq.ajax({
				//		type:"POST",
				//		url:"${app}/activiti/pnrTransferPrecheck/pnrActCityBudgetAmount.do?method=updatePnrActCityBudgetAmountByAjax",
				//		data:{"id":pare.val(),"budgetAmount":newtxt},
				//		dataType:"json",
				//		success:function(data){
				//			if(data.result=="success"){
							   //alert(data.content);
				//			   td.html(newtxt);
				//			}else{
							   //alert(data.content);
					//		   td.html(txt);
						//	}                           			
					//	}
					//});			
				//}
		//	} 
	//	else 
	//	{ 
			//td.html(newtxt); 
		//} 	
	//}); 	
 // });
//});

//不带序号
jq(function(){
	jq("#addPnrActCityBudgetAmountList tbody tr ").find("td:eq(3)").click(function(){
		//alert(jq(this).parent().find("td:eq(0) input:hidden").val());
		var pare=jq(this).parent().find("td:eq(0) input:hidden");
		var td = jq(this); 
		var txt = td.text(); 
		var input = jq("<input type='text' style='width:100%;' value='" + txt + "'/>"); 
		td.html(input); 
		input.click(function() { return false; }); 
		
		//获取焦点 
		input.trigger("focus"); 
		
		
		
		//文本框失去焦点后提交内容，重新变为文本 <p></p>
		input.blur(function() {
		    var expr = new RegExp("^[0-9]\\d*(\\.\\d+)?$");
			var newtxt = jq(this).val(); 
			if (newtxt != txt) {
				if(newtxt==''){
					alert("地市金额不可以为空!");
					td.html(txt); 
					return;
				}
			if(!expr.test(formatStringToFloatNew(newtxt))){
					alert("地市金额请输入整数或小数");
					td.html(txt);
				}else{
				//判断文本有没有修改
					jq.ajax({
						type:"POST",
						url:"${app}/activiti/pnrTransferPrecheck/pnrActCityBudgetAmount.do?method=updatePnrActCityBudgetAmountByAjax",
						data:{"id":pare.val(),"budgetAmount":newtxt},
						dataType:"json",
						success:function(data){
							if(data.result=="success"){
							   //alert(data.content);
							   td.html(newtxt);
							}else{
							   //alert(data.content);
							   td.html(txt);
							}                           			
						}
					});			
				}
			} 
		else 
		{ 
			td.html(newtxt); 
		} 	
	}); 	
  });
});

</script>
<div id="sheetform">
    <html:form action="/pnrActCityBudgetAmount.do?method=selectAddPnrActCityBudgetAmountList&flag=1" styleId="theform">
        <table class="formTable">
        <tr>
			<td class="label">年份*</td>
			<td class="content">
				<eoms:dict key="dict-partner-task" selectId="year"
					dictId="yearflag" beanId="selectXML" cls="select" defaultId="${budgetYear}"/>
			</td>
		<td class="label">
			地市*
		</td>
		<td class="content" colspan=3>
			<html:text property="cityName" styleId="cityName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${cityName}" />
			<html:hidden property="cityId" styleId="cityId" value="${cityId}"/>
		</td>
	    </tr>
        </table>
        <!-- buttons -->
        <div class="form-btns">
            <html:submit styleClass="btn" property="method.save" styleId="method.save">
                查询
            </html:submit>
          <!--  <html:button property="" styleClass="btn" onclick="newReset()">重置</html:button> -->
            
        </div>
    </html:form>
</div>
<display:table name="addPnrActCityBudgetAmountList" cellspacing="0" cellpadding="0"
		id="addPnrActCityBudgetAmountList" pagesize="${pageSize}" class="table addPnrActCityBudgetAmountList"
		export="false"
		requestURI="${app}/activiti/pnrTransferPrecheck/pnrActCityBudgetAmount.do?method=selectAddPnrActCityBudgetAmountList"
		sort="list" partialList="true" size="${total}">
		
		
	<%--	<display:column sortable="false" headerClass="sortable" title="序号"  >
		${addPnrActCityBudgetAmountList.orderCode}
		<input type="hidden" value="${addPnrActCityBudgetAmountList.id}" />
		</display:column>--%>
		<display:column  sortable="false" headerClass="sortable" title="地市"  >
		 <input type="hidden" value="${addPnrActCityBudgetAmountList.id}" />
		 <a href="${app}/activiti/pnrTransferPrecheck/pnrActCityBudgetAmount.do?method=selectAddPnrActCountyBudgetAmountList&amp;pId=${addPnrActCityBudgetAmountList.id}&amp;budgetAmount=${addPnrActCityBudgetAmountList.budgetAmount}&amp;budgetYear=${addPnrActCityBudgetAmountList.budgetYear}&amp;budgetQuarter=${addPnrActCityBudgetAmountList.budgetQuarter}" target="_blank">
				${addPnrActCityBudgetAmountList.cityName}
			    </a> 
			    </display:column>
		<display:column property="budgetYear" sortable="false" headerClass="sortable" title="年份"  />
		<display:column sortable="false" headerClass="sortable" title="季度">
			<c:if test="${addPnrActCityBudgetAmountList.budgetQuarter eq '01'}">
				第一季度
			</c:if>
			<c:if test="${addPnrActCityBudgetAmountList.budgetQuarter eq '04'}">
				第二季度
			</c:if>
			<c:if test="${addPnrActCityBudgetAmountList.budgetQuarter eq '07'}">
				第三季度
			</c:if>
			<c:if test="${addPnrActCityBudgetAmountList.budgetQuarter eq '10'}">
				第四季度
			</c:if>
		</display:column>
		<display:column  sortable="false" headerClass="sortable" title="金额" style="width:200px;height:20px;">
		<fmt:formatNumber value="${addPnrActCityBudgetAmountList.budgetAmount}" pattern="##.##" maxFractionDigits="8" minFractionDigits="0"/>		
		</display:column>
</display:table>

<%@ include file="/common/footer_eoms.jsp"%>