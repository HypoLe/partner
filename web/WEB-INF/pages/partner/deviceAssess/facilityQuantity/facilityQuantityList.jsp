<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>




<div>

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">
<table id="sheet" class="formTable">

	<logic-el:present name="mapObjList">
		<tbody>
	
	<tr><td></td>
			<c:forEach items="${mapObjList}" var="tdList">
				<td colspan="${tdList.deviceTypeSize}" align="center" >${tdList.key}</td>
					</c:forEach>
         </tr> 
         <tr>
         <td class="label">厂家名称</td>
         <c:forEach items="${mapObjList}" var="tdList">
					<c:forEach items="${tdList.list}" var="td">
					<td class="label">${td}</td>
					
					</c:forEach>
					</c:forEach>
				</tr>
				
		<c:forEach items="${factoryList}" var="factList">
				<tr>
				<td class="label" >
				
				
				<a 
				href="${app }/partner/deviceAssess/facilityquantity.do?method=goEdit&factory=${factList.key}"
				 >
				<eoms:id2nameDB beanId="IItawSystemDictTypeDao" id="${factList.key}"/>
				</a>
				
				</td>
					<c:forEach items="${factList.list}" var="number">
						
							<td>${number}</td>
						
					</c:forEach>
				</tr>
			</c:forEach>		
				 	
		</tbody>
	</logic-el:present>
</table>
</fmt:bundle>

</div>


<%@ include file="/common/footer_eoms.jsp"%>