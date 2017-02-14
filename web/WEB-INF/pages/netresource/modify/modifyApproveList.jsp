<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/netresource/modify/config/applicationResource-modify">

<html:form action="/modifys.do?method=removeSel" styleId="modifyForm" method="post"> 

<caption>
    <div class="header center"><b><fmt:message key="modify.approvelist.title"/></b></div>
</caption>

<display:table name="modifyList" cellspacing="0" cellpadding="0"
    id="modifyList" pagesize="${pageSize}" class="table modifyList"
    export="false"
    requestURI="${app}/netresource/modify/modifys.do?method=search"
    sort="list" partialList="true" size="resultSize"
    decorator="com.boco.eoms.netresource.modify.util.ModifyDecorator">
    
    <logic:present name="modifyList" property="id">
    
    <logic:notEmpty name="modifyList" property="id">
        <display:column title="<input type='checkbox' onclick='javascript:choose();'>" >
        	           <input type="checkbox" id="${modifyList.id}" name="ids" value="${modifyList.id}" />
        </display:column>
        <display:setProperty name="css.table" value="0,"/>
    </logic:notEmpty>
                
    <display:column sortable="true" headerClass="sortable" titleKey="modify.applyUser">
        <eoms:id2nameDB id="${modifyList.applyUser}" beanId="tawSystemUserDao" />
    </display:column>    
                    
    <display:column property="applyDept" sortable="true" headerClass="sortable"
        titleKey="modify.applyDept" paramId="id" paramProperty="id"/>
                    
    <display:column property="applyTime" sortable="true" headerClass="sortable"
        titleKey="modify.applyTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="description" sortable="true" headerClass="sortable"
        titleKey="modify.description" paramId="id" paramProperty="id"/>
                    
    <display:column sortable="true" headerClass="sortable" titleKey="modify.approveUser">
        <eoms:id2nameDB id="${modifyList.approveUser}" beanId="tawSystemUserDao" />
    </display:column>
                    
    <display:column property="acceptTime" sortable="true" headerClass="sortable"
        titleKey="modify.acceptTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="approveTime" sortable="true" headerClass="sortable"
        titleKey="modify.approveTime" paramId="id" paramProperty="id"/>
    
    <display:column property="approveStatus" sortable="true" headerClass="sortable"
        titleKey="modify.approveStatus" paramId="id" paramProperty="id"/>                
                    
    <display:column property="modifyType" sortable="true" headerClass="sortable"
        titleKey="modify.modifyType" paramId="id" paramProperty="id"/>
                    
    <display:column property="resourceType" sortable="true" headerClass="sortable"
        titleKey="modify.resourceType" paramId="id" paramProperty="id"/>
                    
    <display:column sortable="true" headerClass="sortable" titleKey="button.detail" >
        <a href="${app}/netresource/modify/modifys.do?method=detail&id=${modifyList.id}"
            title="<fmt:message key="button.detail"/>"><img src="${app}/images/icons/search.gif" /></a>
    </display:column>

    <display:column sortable="true" headerClass="sortable" titleKey="button.edit">
        <!-- 此处添加权限控制 -->
        <a href="${app}/netresource/modify/modifys.do?method=edit&id=${modifyList.id}"
                title="<fmt:message key="button.edit"/>"><img src="${app}/images/icons/edit.gif" /></a>
    </display:column>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.remove">
        <!-- 此处添加权限控制 -->
        <a href="javascript:if(confirm('您确定要删除您选择的数据？')) {window.location.href = '${app}/netresource/modify/modifys.do?method=remove&id=${modifyList.id}';}"
                title="<fmt:message key="button.remove"/>"><img src="${app}/images/icons/icon.gif" /></a>
	</display:column>

    </logic:present>
</display:table>

</html:form>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>