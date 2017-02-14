<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page import="com.boco.eoms.netresource.modify.model.*" %>

<%
	Modify modify = (Modify)request.getAttribute("modifyForm");
	String userId = (String)request.getAttribute("userId");
%>

<fmt:bundle basename="com/boco/eoms/netresource/modify/config/applicationResource-modify">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="modify.detail.heading"/></div>
    </caption>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.applyUser" />
        </td>
        <td class="content">
            <eoms:id2nameDB id="${modifyForm.applyUser}" beanId="tawSystemUserDao" />
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.applyDept" />
        </td>
        <td class="content">
            <eoms:id2nameDB id="${modifyForm.applyDept}" beanId="tawSystemDeptDao" />
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.applyTime" />
        </td>
        <td class="content">
                                    
            ${modifyForm.applyTime}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.description" />
        </td>
        <td class="content">
                                    
            ${modifyForm.description}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.approveUser" />
        </td>
        <td class="content">
            <eoms:id2nameDB id="${modifyForm.approveUser}" beanId="tawSystemUserDao" />
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.acceptTime" />
        </td>
        <td class="content">
                                    
            ${modifyForm.acceptTime}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.approveTime" />
        </td>
        <td class="content">
                                    
            ${modifyForm.approveTime}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.approveStatus" />
        </td>
        <td class="content">
                                    
            ${modifyForm.approveStatus}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.errorScope" />
        </td>
        <td class="content">
                                    
            ${modifyForm.errorScope}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.modifyType" />
        </td>
        <td class="content">
                                    
            ${modifyForm.modifyType}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.resourceType" />
        </td>
        <td class="content">
                                    
            ${modifyForm.resourceType}
                
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="modify.resourceId" />
        </td>
        <td class="content">
                                    
            ${modifyForm.resourceId}
                
        </td>
    </tr>
            
</table>

<table>
    <tr>
        <% if( modify != null && "待审批".equals(modify.getApproveStatus()) && (userId.equals(modify.getApproveUser()) || "admin".equals(userId)) ){ //待审批 %>
        <td>
            <input type="button" class="btn" onclick="location.href='${app}/netresource/modify/modifys.do?method=accept&id=${modifyForm.id}'" value="确认受理" />
        </td>
        &nbsp;&nbsp;
        <td>
            <input type="button" class="btn" onclick="javascript:history.back();" value=" 返回 " />
        </td>
        <% }else if( modify != null &&  "已受理".equals(modify.getApproveStatus()) && (userId.equals(modify.getApproveUser()) || "admin".equals(userId))  ){ //确认受理 %>
        <td>
            <input type="button" class="btn" onclick="location.href='${app}/netresource/modify/modifys.do?method=agree&id=${modifyForm.id}'" value=" 同意 " />
        </td>
        &nbsp;&nbsp;
        <td>
            <input type="button" class="btn" onclick="location.href='${app}/netresource/modify/modifys.do?method=refuse&id=${modifyForm.id}'" value=" 驳回 " />
        </td>
        <% }else{ %>
        <td>
            <input type="button" class="btn" onclick="javascript:history.back();" value=" 返回 " />
        </td>
        <% } %>
    </tr>
</table>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>