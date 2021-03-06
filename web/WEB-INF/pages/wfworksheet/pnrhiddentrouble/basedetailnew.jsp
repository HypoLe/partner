<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<table class="formTable">
		<caption><bean:message bundle="sheet" key="mainForm.header"/></caption>
		<tr>
			<td class="label"><bean:message bundle="sheet" key="mainForm.sheetId"/></td>
			<td class="content"><bean:write name="sheetMain" property="sheetId" scope="request"/></td>
			<td class="label"><bean:message bundle="sheet" key="mainForm.status"/></td>
		    <td class="content"><eoms:dict key="dict-sheet-common" dictId="sheetStatus" itemId="${sheetMain.status}" beanId="id2nameXML" /></td>
	  
		</tr>
		<tr>
			<td class="label"><bean:message bundle="sheet" key="mainForm.title"/></td>
			<td colspan='3'><bean:write name="sheetMain" property="title" scope="request"/></td>
		</tr>
		<tr>
			<td class="label"><bean:message bundle="sheet" key="mainForm.sendUserName"/></td>
			<td class="content">
              <eoms:id2nameDB id="${sheetMain.sendUserId}" beanId="tawSystemUserDao"/>		  			  
		    </td>
			<td class="label"><bean:message bundle="sheet" key="mainForm.sendDeptName"/></td>			
			<td class="content"><eoms:id2nameDB id="${sheetMain.sendDeptId}" beanId="tawSystemDeptDao"/></td>
		</tr>

		
		<tr>
		     <td class="label">
               <bean:message bundle="sheet" key="mainForm.sendContact"/>
           </td>
		   <td>
               <bean:write name="sheetMain" property="sendContact" scope="request"/>
		   </td>
			<td class="label"><bean:message bundle="sheet" key="mainForm.sendTime"/></td>
			<td><bean:write name="sheetMain" property="sendTime" formatKey="date.formatDateTimeAll" bundle="sheet" scope="request"/></td>
		</tr>
       		
		
	 <c:if test="${sheetMain.status==-13||sheetMain.status==-14||sheetMain.status==-12}">
	   <tr>
		<td class="label">
          <bean:message bundle="sheet" key="mainForm.cancelReason"/>
         </td>
		<td colspan='3'>
		  <bean:write name="sheetMain" property="cancelReason" scope="request"/>
		</td>
		</tr>
	</c:if>	

	 <c:if test="${sheetMain.status==1}">
		<tr>
		<eoms:id2nameDB id="${sheetMain.endRoleId}" beanId="tawSystemSubRoleDao"/>
		</td>
		 <td class="label">
           <bean:message bundle="sheet" key="mainForm.endTime"/>
         </td>
		<td colspan ='3'>		
		   <bean:write name="sheetMain" property="endTime" formatKey="date.formatDateTimeAll" bundle="sheet" scope="request"/>
		</td>
     </tr> 
     <tr>
       <td class="label">
          <bean:message bundle="sheet" key="mainForm.holdStatisfied"/>
       </td>
	   <td colspan='3'>
	     <eoms:id2nameDB id="${sheetMain.holdStatisfied}" beanId="IItawSystemDictTypeDao"/>
	   </td>
     </tr>    
     <tr>		
        <td class="label">
          <bean:message bundle="sheet" key="mainForm.endResult"/>
        </td>
		<td colspan='3'>
          <pre><bean:write name="sheetMain" property="endResult" scope="request"/></pre>
		</td>
     </tr>   
  </c:if>
  <c:if test="${!empty sheetMain.parentSheetId}">
    <tr>
      <td class="label">
          <bean:message bundle="sheet" key="mainForm.parentSheetName"/>
      </td>
	  <td>
      	<bean:write name="sheetMain" property="parentSheetName" scope="request"/>
		</td>
		<td class="label">
          <bean:message bundle="sheet" key="mainForm.parentSheetId"/>
         </td>
		<td>
		<bean:write name="sheetMain" property="parentSheetId" scope="request"/>
		</td>
     </tr>
    </c:if>   
 </table> 