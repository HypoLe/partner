<%@page import="java.util.List"%>
<%@page import="com.boco.eoms.partner.assess.AssFee.webapp.form.FeeDetailForm"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'feeTotalForm'});
});

function  count(nodeId)
{
    var number = document.getElementById("number_"+nodeId).value;
    var price = document.getElementById("price_"+nodeId).value;
    if(number!=''&&number!='null'){
    	number = parseFloat(number);
    }else{
    	number = 0;
    }
    if(price!=''&&price!='null'){
    	price = parseFloat(price);
    }else{
    	number = 0;
    }
    
    rs = number*price;
    document.getElementById("totleShow"+nodeId).innerHTML = (Math.round(rs*100)/100);  
    document.getElementById("total_"+nodeId).value = (Math.round(rs*100)/100); 
};

function sum(){
		var sum = 0.0 ; 
		<%
		List feeDetailList =  (List)request.getAttribute("feeDetailList");
		FeeDetailForm feeDetailForm = null;
		for(int i = 0;feeDetailList.size()>i;i++){
			feeDetailForm = (FeeDetailForm)feeDetailList.get(i);
			if("lineName".equals(feeDetailForm.getType())){
		%>
			var nodeId = <%=feeDetailForm.getNodeId()%>;
			var value = document.getElementById("total_"+nodeId).value ;
			if(value!=''&&value!='null'){
				sum = sum + parseFloat(value);
			}
		<%
			}
		}
		%>
		document.getElementById("totalreaMoneyShow").innerHTML = (Math.round(sum*100)/100); 
		document.getElementById("totalreaMoney").value = (Math.round(sum*100)/100);
}

function changeCity(){
		    delAllOption("areaId");//地市选择更新后，重新刷新县区
			var year = document.getElementById("year").value;
			var url="<%=request.getContextPath()%>/partner/assess/feeTotals.do?method=changeCity&year="+year;
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									
									var json = eval(res);
									var areaId = "areaId";
						
									var arrOptions = json[0].areaId.split(",");
									var obj=$(areaId);
											
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});	
}

function delAllOption(elementid){
    var ddlObj = document.getElementById(elementid);//获取对象
     for(var i=ddlObj.length-1;i>=0;i--){
         ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
    }
    
}
</script>

<html:form action="/feeTotals.do?method=save" styleId="feeTotalForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">线路代维维护量配置</div>
	</caption>
	
	<tr>
		<td class="label">
			年 
		</td>
		<td class="content" colspan="2">
			<c:if test="${empty feeTotalForm.id}">
				<select id="year" name="year" alt="allowBlank:false" onchange="changeCity()">
						<option id="0" value="">请选择</option>
					<c:forEach begin="2009" end="2025" var="num">
						<c:if test="${feeTotalForm.year ==num}">
							<option id="${num}" value="${num}" selected="true">${num}年</option>
						</c:if>
						<c:if test="${feeTotalForm.year !=num}">
							<option id="${num}" value="${num}">${num}年</option>
						</c:if>					
					</c:forEach>
				</select>		
			</c:if>
			<c:if test="${not empty feeTotalForm.id}">
				${feeTotalForm.year}年
				<html:hidden property="year" value="${feeTotalForm.year}" />
			</c:if>
		</td>	
		<td class="label">
			地市
		</td>
		<td class="content" colspan="2">
			<!-- 地市 -->			
			<c:if test="${empty feeTotalForm.id}">
				<select name="areaId" id="areaId" 
					alt="allowBlank:false,vtext:'请选择所在地市'" >
					<option value="">
						--请选择地市--
					</option>
					<logic:iterate id="city" name="city">
						<option value="${city.areaid}">
							${city.areaname}
						</option>
					</logic:iterate>
				</select>	
			</c:if>	
			<c:if test="${not empty feeTotalForm.id}">
				<eoms:id2nameDB id="${feeTotalForm.areaId}" beanId="tawSystemAreaDao" />
				<html:hidden property="areaId" value="${feeTotalForm.areaId}" />
			</c:if>			
		</td>
	</tr>
	<%
		for(int i = 0;feeDetailList.size()>i;i++){
			feeDetailForm = (FeeDetailForm)feeDetailList.get(i);
			if("lineName".equals(feeDetailForm.getType())){
	%>				
			<tr>
				<td colspan="6">
					<eoms:id2nameDB id="<%=feeDetailForm.getNodeId()%>" beanId="feeTreeDao" />
				</td>
			</tr>
			<tr>
				<td class="label">皮长<%=feeDetailForm.getUnit()%></td>
				<td class="content">
					<input type="text"  id="number_<%=feeDetailForm.getNodeId()%>"  style="width:100%;" onblur="count(<%=feeDetailForm.getNodeId()%>);sum()"
						name="number_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getNumber()%>"
						alt="re:/^[0-9]+([.]{1}[0-9]+)?$/,allowBlank:false,vtext:''"/>
				</td>
				<td class="label">单价</td>
				<td class="content">
					<%=feeDetailForm.getPrice()%>
					<input type="hidden" id ="price_<%=feeDetailForm.getNodeId()%>" name="price_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getPrice()%>" />
				</td>	
				<td class="label">合计</td>
				<td class="content">
					<span id="totleShow<%=feeDetailForm.getNodeId()%>">
						<%
							if("".equals(feeDetailForm.getTotal())||null==feeDetailForm.getTotal()){
						%>	
							待计算
						<%	
							}else{
						%>
							<%=feeDetailForm.getTotal()%>
						<%		
							}
						%>
						
					</span>
					<input type="hidden"  id="total_<%=feeDetailForm.getNodeId()%>" name="total_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getTotal()%>" />
				</td>	
				<input type="hidden"  id="type_<%=feeDetailForm.getNodeId()%>" name="type_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getType()%>" />
				<input type="hidden"  id="unit_<%=feeDetailForm.getNodeId()%>" name="unit_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getUnit()%>" />
				<input type="hidden"  id="nodeId_<%=feeDetailForm.getNodeId()%>" name="nodeId_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getNodeId()%>" />
				<input type="hidden"  id="parentNodeId_<%=feeDetailForm.getNodeId()%>" name="parentNodeId_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getParentNodeId()%>"/>				
			</tr>
	<%			
			} else {
			%>	
				<input type="hidden"  id="total_<%=feeDetailForm.getNodeId()%>" name="total_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getTotal()%>" />
				<input type="hidden"  id="type_<%=feeDetailForm.getNodeId()%>" name="type_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getType()%>" />
				<input type="hidden"  id="unit_<%=feeDetailForm.getNodeId()%>" name="unit_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getUnit()%>" />
				<input type="hidden"  id="nodeId_<%=feeDetailForm.getNodeId()%>" name="nodeId_<%=feeDetailForm.getNodeId()%>" value="<%=feeDetailForm.getNodeId()%>" />					
			<%						
			}
		}
	%>
	<tr>
		<td class="label">
			维护费用合计(修改后)
		</td>
		<td class="content" colspan="2">
			<html:text property="totalMoney" styleId="totalMoney"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeTotalForm.totalMoney}" />
			<font color='red'>&nbsp;&nbsp;&nbsp;&nbsp;请根据【维护费用合计(实际)】填写</font>
		</td>	
		<td class="label">
			维护费用合计(实际)
		</td>
		<td class="content" colspan="2">
			<span id="totalreaMoneyShow">
				<c:if test="${feeTotalForm.totalreaMoney==''||feeTotalForm.totalreaMoney==null}">待计算</c:if>
				<c:if test="${feeTotalForm.totalreaMoney!=''&&feeTotalForm.totalreaMoney!=null}">${feeTotalForm.totalreaMoney}</c:if>
			</span>
			<html:hidden property="totalreaMoney" value="${feeTotalForm.totalreaMoney}" />
		</td>
	</tr>
<%-- 
	<tr>
		<td class="label">
			<fmt:message key="feeTotal.monthMoney" />
		</td>
		<td class="content">
			<html:text property="monthMoney" styleId="monthMoney"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeTotalForm.monthMoney}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="feeTotal.pointMoney" />
		</td>
		<td class="content">
			<html:text property="pointMoney" styleId="pointMoney"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeTotalForm.pointMoney}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="feeTotal.quarterMoney" />
		</td>
		<td class="content">
			<html:text property="quarterMoney" styleId="quarterMoney"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeTotalForm.quarterMoney}" />
		</td>
	</tr>
--%>
</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
<%--		<c:if test="${not empty feeTotalForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/feeTotal/feeTotals.do?method=remove&id=${feeTotalForm.id}';
						location.href=url}"	/>
			</c:if>
 --%>			
		</td>
	</tr>
</table>
<input type="hidden"  id="deleted" name="deleted" value="${feeTotalForm.deleted}" />
<html:hidden property="id" value="${feeTotalForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>