<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

			<!-- base info -->
		     <input type="hidden" name="linkId" id="linkId" value="${sheetLink.id}"/>
		     <input type="hidden" name="id" id="id" value="${sheetLink.id}"/>
		     <input type="hidden" name="sheetId" id="sheetId" value="${sheetMain.sheetId}"/>
			 <input type="hidden" name="piid" value="${sheetLink.piid}"/>
		     <input type="hidden" name="aiid" value="${sheetLink.aiid}"/>
		     <input type="hidden" name="activeTemplateId" value="${taskName}"/>
		     <input type="hidden" name="mainId" value="${sheetMain.id}"/>
		     <input type="hidden" name="sheetKey" value="${sheetMain.id}"/>
		     <input type="hidden" name="correlationKey" value="${sheetMain.correlationKey}"/>		    
		     <input type="hidden" name="TKID" value="${sheetLink.tkid}"/>
		     <input type="hidden" name="preLinkId" value="${preLink.id}"/>
		     <input type="hidden" name="taskCompleteLimit" id="taskCompleteLimit" value="${eoms:date2String(sheetMain.sheetAcceptLimit)}"/>
		     <input type="hidden" name="taskAcceptLimit" id="taskAcceptLimit" value="${eoms:date2String(sheetMain.sheetCompleteLimit)}"/>
		     <input type="hidden" name="taskName" value="${taskName}"/>
		     <input type="hidden" name="taskStatus" value="${taskStatus}"/>
		     <input type="hidden" name="sheetPageName" id="sheetPageName" value=""/>
             <input type="hidden" name="methodBeanId"  id="methodBeanId" value="${methodBeanId}"/>
           
		<table class="formTable">
		  <caption><bean:message bundle="sheet" key="linkForm.header"/></caption>
		  <tr>
		   <td class="label">
		     <bean:message bundle="sheet" key="linkForm.operateUserName"/>*
             <input type="hidden" name="operateUserId" value="${sheetLink.operateUserId}"/>
           </td>
           <td class="content">
              <eoms:id2nameDB id="${sheetLink.operateUserId}" beanId="tawSystemUserDao"/>&nbsp;
           </td>
		   <td class="label">		     
             <bean:message bundle="sheet" key="linkForm.operateDeptName"/>*
             <input type="hidden" name="operateDeptId" value="${sheetLink.operateDeptId}"/>
           </td>
           <td class="content"> 
                 <eoms:id2nameDB id="${sheetLink.operateDeptId}" beanId="tawSystemDeptDao"/>&nbsp;
           </td>
		  </tr>
		  
		  
		  <tr>
		   <td class="label">
		     <bean:message bundle="sheet" key="linkForm.operaterContact"/> *           
           </td>
           <td class="content" colspan="3">
              <input type="text" class="text" name="operaterContact" 
                id="operaterContact" value="${sheetLink.operaterContact}" alt="allowBlank:false,maxLength:32,vtext:'请填写联系人'"/>
           </td>
		  
           
		  </tr>
		</table>     