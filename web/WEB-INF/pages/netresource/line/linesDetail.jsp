<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<fmt:bundle basename="com/boco/eoms/netresource/line/config/ApplicationResources-line">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="line.detail.heading"/></div>
    </caption>
            
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.name" />
        </td>
        <td class="content">
            ${linesForm.lineName}
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.no" />
        </td>
        <td class="content">
            ${linesForm.lineNo}
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.remark" />
        </td>
        <td class="content">
            ${linesForm.remark}
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.partner" />
        </td>
        <td class="content">
        	<eoms:id2nameDB id="${linesForm.partner}" beanId="partnerDeptDao" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.region" />
        </td>
        <td class="content">
            <eoms:id2nameDB id="${linesForm.region}" beanId="tawSystemAreaDao" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.city" />
        </td>
        <td class="content">
            <eoms:id2nameDB id="${linesForm.city}" beanId="tawSystemAreaDao" />
        </td>
     </tr>
     
     <tr>   
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.grid" />
        </td>
        <td class="content">
            <eoms:id2nameDB id="${linesForm.grid}" beanId="gridDao" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.maintainType" />
        </td>
        <td class="content">
            <eoms:dict key="dict-partner-serviceArea" dictId="maintainType" itemId="${linesForm.maintainType}" beanId="id2nameXML" />
        </td>
    </tr>
        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.manager" />
        </td>
        <td class="content">
            ${linesForm.manager}
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.managertel" />
        </td>
        <td class="content">
            ${linesForm.managerTel}
        </td>
      </tr>
      
      <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.contacter" />
        </td>
        <td class="content">
            ${linesForm.contacter}
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.contactertel" />
        </td>
        <td class="content">
            ${linesForm.contacterTel}
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.openTime" />
        </td>
        <td class="content">
            ${linesForm.openTime}
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.userYear" />
        </td>
        <td class="content">
            ${linesForm.userYear}
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.beginLong" />
        </td>
        <td class="content">
            ${linesForm.beginLong}
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.beginLat" />
        </td>
        <td class="content">
            ${linesForm.beginLat}
        </td>
     </tr>
     
     <tr>   
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.endLong" />
        </td>
        <td class="content">
            ${linesForm.endLong}
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.endLat" />
        </td>
        <td class="content">
            ${linesForm.endLat}
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.level" />
        </td>
        <td class="content">
            ${linesForm.level}
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.errorScope" />
        </td>
        <td class="content">
            ${linesForm.errorScope}
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.mender" />
        </td>
        <td class="content">
            ${linesForm.mender}
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.modifyTime" />
        </td>
        <td class="content">
            ${linesForm.modifyTime}
        </td>
    </tr>
                                    
                
</table>

<table>
    <tr>
        <td>
            <input type="button" class="btn" onclick="javascript:history.back();" value="<fmt:message key="button.back"/>" />
        </td>
        &nbsp;&nbsp;
        <td>
            <input type="button" class="btn" onclick="window.location.href = '${app}/netresource/line/addPointByLine.do?method=addPointByLine&id=${linesForm.id}'" value="<fmt:message key="line.detail.addPoint"/>" />
        </td>
    </tr>
</table>

<!-- 线路下的标点列表 -->

<caption>
    <div class="header center"><b><fmt:message key="line.detail.pointsList"/></b></div>
</caption>

</fmt:bundle>

<fmt:bundle basename="com/boco/eoms/netresource/point/config/applicationResource-points">

<display:table name="pointsList" cellspacing="0" cellpadding="0"
    id="pointsList" pagesize="${pageSize}" class="table pointsList"
    export="false"
    requestURI="${app}/points/points.do?method=search"
    sort="list" partialList="true" size="resultSize"
    decorator="com.boco.eoms.netresource.point.util.PointsDecorator">
    
    <logic:present name="pointsList" property="id">
    
    <logic:notEmpty name="pointsList" property="id">
        <display:column title="<input type='checkbox' onclick='javascript:choose();'>" >
        	<input type="checkbox" id="${pointsList.id}" name="ids" value="${pointsList.id}" />
        </display:column>
        <display:setProperty name="css.table" value="0,"/>
    </logic:notEmpty>
    
    <display:column property="pointName" sortable="true" headerClass="sortable"
        titleKey="points.pointName" paramId="id" paramProperty="id"/>
        
    <display:column property="pointNo" sortable="true" headerClass="sortable"
        titleKey="points.pointNo" paramId="id" paramProperty="id"/>
                    
    <display:column sortable="true" headerClass="sortable" titleKey="points.region">
    	<eoms:id2nameDB id="${pointsList.region}" beanId="tawSystemAreaDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="points.city">
    	<eoms:id2nameDB id="${pointsList.city}" beanId="tawSystemAreaDao" />
    </display:column>
                    
    <display:column sortable="true" headerClass="sortable" titleKey="points.grid">
        <eoms:id2nameDB id="${pointsList.grid}" beanId="gridDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="points.line">
        <eoms:id2nameDB id="${pointsList.line}" beanId="linesDao" />
    </display:column>
                    
    <display:column property="specialtyType" sortable="true" headerClass="sortable"
        titleKey="points.specialtyType" paramId="id" paramProperty="id"/>
                    
    <display:column property="loadwork" sortable="true" headerClass="sortable"
        titleKey="points.loadwork" paramId="id" paramProperty="id"/>
                    
    <display:column property="groupUser" sortable="true" headerClass="sortable"
        titleKey="points.groupUser" paramId="id" paramProperty="id"/>
                    
    <display:column property="groupUserNo" sortable="true" headerClass="sortable"
        titleKey="points.groupUserNo" paramId="id" paramProperty="id"/>
                    
    <display:column sortable="true" headerClass="sortable" titleKey="button.detail" >
        <a href="${app}/netresource/point/points.do?method=detail&id=${pointsList.id}"
            title="<fmt:message key="button.detail"/>"><img src="${app}/images/icons/search.gif" /></a>
    </display:column>
	
	<!-- 此处添加权限控制 -->
	<% if( null != request.getAttribute("flag") && "manager".equals(request.getAttribute("flag")) ){ %>
    <display:column sortable="true" headerClass="sortable" titleKey="button.edit">
       <a href="${app}/netresource/point/points.do?method=edit&id=${pointsList.id}" title="<fmt:message key="button.edit"/>">
       		<img src="${app}/images/icons/edit.gif" />
       </a>
	    	    
    </display:column>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.remove">
        <a href="javascript:if(confirm('您确定要删除您选择的数据？')) {window.location.href = '${app}/netresource/point/points.do?method=remove&id=${pointsList.id}';}"
                title="<fmt:message key="button.remove"/>">
             <img src="${app}/images/icons/icon.gif" />
        </a>
	</display:column>
	<% } %>

    </logic:present>
    
</display:table>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>