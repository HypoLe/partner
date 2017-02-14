<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/training/config/applicationResource-training">

<table class="formTable">
    <caption>
        <div class="header center">培训资料详细</div>
    </caption>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.planName" />
        </td>
        <td class="content">
                                    
            ${MaterialSharing.planName}
                
        </td>
        
        <td class="label">
            资料名称
        </td>
        <td class="content">
                                    
            ${MaterialSharing.materialName}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.releaseUser" />
        </td>
        <td class="content">
    <eoms:id2nameDB id="${MaterialSharing.releaseUser}"
				beanId="tawSystemUserDao" />
        </td>
        <td class="label">
            <fmt:message key="plan.dept" />
        </td>
        <td class="content">
       <eoms:id2nameDB id="${MaterialSharing.dept}"
				beanId="tawSystemDeptDao" />          
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.beginTime" />
        </td>
        <td class="content">
            ${MaterialSharing.beginTime}
        </td>
        <td class="label">
            <fmt:message key="plan.days" />
        </td>
        <td class="content">
                                    
            ${MaterialSharing.days}
                
        </td>
    </tr>
    <tr>
        <td class="label">
           相关附件
        </td>
        <td class="content" colspan='3'>
                <eoms:attachment name="MaterialSharing" property="accessoriesId" scope="request" idField="accessoriesId" appCode="feeInfo" viewFlag="Y"/>
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="plan.remark" />
        </td>
        <td class="content" colspan='3'>
                                    
            ${MaterialSharing.remark}
                
        </td>
    </tr>
    
</table>

</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="button" class="btn" onclick="javascript:history.back();" value="<fmt:message key="button.back"/>" />
        </td>
    </tr>
</table>

<%@ include file="/common/footer_eoms.jsp"%>