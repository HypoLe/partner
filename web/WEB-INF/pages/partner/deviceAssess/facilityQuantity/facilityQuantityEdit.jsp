<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>




<div>
<form action="facilityquantity.do?method=add" method="post" id="facilityQuantityForm" name="facilityQuantityForm" >

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">
<table id="sheetone" class="formTable">
<tr>
	
		<td class="label">
			<fmt:message key="facilityNum.factory"  />
		</td>
		<td class="content"  >
		<eoms:id2nameDB beanId="IItawSystemDictTypeDao" id="${factory}"/>
		<input  type="hidden" name="factory"  id="factory"
		value="${factory}" />
					
		           		
		</td>
		</tr>
		</table>
<table id="sheet" class="formTable">
	
	<logic-el:present name="modelList">
		<tbody>
	<td class="label">专业</td><td class="label" colspan="100">设备类型</td>
			<c:forEach items="${modelList}"  var="tdList">
			
				<tr>
				<td class="label">${tdList.key}</td>
				
					<c:forEach items="${tdList.mapList}" var="td">
					
					<c:forEach items="${td}" var="entry">
<td class="label">
        <c:out value="${entry.key}" />
        </td>
					<td ><input size="5" type="text" name="${entry.key}"  
					id="${entry.key}" alt="allowBlank:false"  value="${entry.value}"  onblur="javascript:numberCheck(this,${entry.value});" /></td>

     </c:forEach>
						
					</c:forEach>
					
				</tr>
			</c:forEach>
		</tbody>
	</logic-el:present>
</table>
</fmt:bundle>

   <html:submit styleClass="btn"  property="method.save" 
	        styleId="method.save" value="保存修改" ></html:submit>
</div>

<script type="text/javascript">

  
function numberCheck(obj,obj1){
         var price=/^\d+$/;
         var numberValue=obj.value; 
         var aa=obj1
         if(!numberValue.match(price) && ""!=numberValue){
         alert("格式不正确，设备数量只能为非负整数");
					obj.value=aa;
         }
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>