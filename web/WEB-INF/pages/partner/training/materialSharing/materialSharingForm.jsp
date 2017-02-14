<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
    v = new eoms.form.Validation({form:'materialSharingForm'});
    });
    
</script>

<html:form action="/materialSharing.do?method=save" styleId="materialSharingForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/training/config/applicationResource-training">

<table class="formTable">
    <caption>
        <div class="header center">培训资料共享新增</div>
    </caption>
                                                    
    <tr>
        <td class="label">
                            
            <font color='red'> * </font><fmt:message key="plan.planName" />
                        
        </td>
        <td class="content">
        <select name="planId" onChange="changeObj(this.value);">
        <c:forEach items="${list}"  var="obj" >
        <option value="${obj.id}">${obj.planName}</option>
        </c:forEach>
        </select>
        </td>
        </tr>
        <tr>
        <td class="label">
            * 资料名
        </td>
        <td class="content">
                             <html:text property="materialName" styleId="materialName"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'资料名称不能为空！'" value="${materialSharingForm.materialName}" />                                                                 
        </td>
        </tr>
         <tr>
        <td class="label">
            * 附件上传
        </td>
        <td class="content">
                              <eoms:attachment idList="" idField="accessoriesId"
								appCode="feeInfo" />                                                                 
        </td>
        </tr>
        
    <tr>
        <td class="label">
                            
            <font color=''> * </font><fmt:message key="plan.remark" />
                        
        </td>
        <td class="content" colspan='3'>
                                                
            <html:textarea property="remark" styleId="remark"
                        styleClass="text medium" style='width:98%' rows='3'
                        alt="allowBlank:true,vtext:'备注',maxLength:125" value="${materialSharingForm.remark}" />
                                                                                    
        </td>
    </tr>
        
</table>
</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
            <c:if test="${not empty materialSharingForm.id}">
                <input type="button" class="btn" value="<fmt:message key="button.back"/>" 
                    onclick="javascript:history.back();"    />
            </c:if>
        </td>
    </tr>
</table>

<html:hidden property="id" value="${materialSharingForm.id}" />

</html:form>


<%@ include file="/common/footer_eoms.jsp"%>