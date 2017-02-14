<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language="javascript">

Ext.onReady(function() {

    // 设置检索条件部分不显示
	Ext.fly("listQueryObject").setDisplayed(false);
                                                                                                                                                                                                                
});

    
    	var checkflag = "false";
    function choose() {
        var objs = document.getElementsByName("ids");
        if(checkflag=="false"){
            for(var i=0; i<objs.length; i++) {
                if(objs[i].type.toLowerCase() == "checkbox" )
                    objs[i].checked = true;
                checkflag="true";
            }
        }else if(checkflag=="true"){
            for(var i=0; i<objs.length; i++) {
                if(objs[i].type.toLowerCase() == "checkbox" )
                    objs[i].checked = false;
                checkflag="false"
            }
        }
    }
	
    function ConfirmDel(){
        
        var flag=false;
        var ids;
        
        var objs = document.getElementsByName("ids");
        for(var i=0; i<objs.length; i++) {
            if(objs[i].type.toLowerCase() == "checkbox" )
            if(objs[i].checked){
                flag=true;
                ids=objs[i];
            }
         }
        if(flag){
            if(confirm("您确定要删除您选择的数据？")){
                return true;
            }else{
                return false;
            }
        }else {
            alert("请选择要删除的数据！");
            return false;
        }
    }
    
    function clear() {
        
    }

    function openQuery(){
      
        // 设置检索条件部分显示
	    Ext.fly("listQueryObject").setDisplayed(true);
	    
        // 设置展开查询的链接部分不显示
	    var openQuery = document.getElementById("openQuery");
	    openQuery.style.display = "none";
	    
	    // 设置关闭查询的链接部分显示
	    var closeQuery = document.getElementById("closeQuery");
	    closeQuery.style.display = "";
	    
	    document.getElementById("beginCostTime").value = '';
	    document.getElementById("endCostTime").value = '';
   }
   function closeQuery(){

        // 设置检索条件部分不显示
        var listQueryObject = document.getElementById("listQueryObject");
        listQueryObject.style.display = "none";
        
        // 设置展开查询的链接部分显示
        var openQuery = document.getElementById("openQuery");
        openQuery.style.display = "";
        
        // 设置关闭查询的链接部分不显示
        var closeQuery = document.getElementById("closeQuery");
        closeQuery.style.display = "none";
   }

</script>

<fmt:bundle basename="com/boco/eoms/partner/management/config/applicationResource-management">

<div class="form-btns" id="form-btns">
  <span id="openQuery">
  	<input type="button" class="btn" onclick="openQuery()" value='展开快速查询'/>
  </span>
  <span id="closeQuery" style="display:none">
    <input type="button" class="btn" onclick="closeQuery()" value='关闭快速查询'/>
  </span>
</div>

<span id="listQueryObject">

<html:form action="/managements.do?method=search" styleId="managementForm" method="post"> 

<html:hidden property="flag" value="${flag}" />

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="management.search.heading"/></div>
    </caption>

    <tr>
        <td class="label">
            <fmt:message key="management.beginCostTime" />
        </td>
        <td class="content">
            <input property="beginCostTime" id="beginCostTime" name="beginCostTime"
                        readonly="readonly" styleClass="text medium"
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" value="${managementForm.beginCostTime}" />
        </td>
    
        <td class="label">
            <fmt:message key="management.endCostTime" />
        </td>
        <td class="content">
            <input property="endCostTime" id="endCostTime" name="endCostTime"
                        readonly="readonly" styleClass="text medium"
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" value="${managementForm.endCostTime}" />
        </td>
    </tr>
                
    <tr>
        <td class="label">
            <fmt:message key="management.partnerNum" />
        </td>
        <td class="content">
            <html:text property="partnerNum" styleId="partnerNum"
                        styleClass="text medium"
                        value="${managementForm.partnerNum}" />
        </td>
    
        <td class="label">
            <fmt:message key="management.expenseCost" />
        </td>
        <td class="content">
            <html:text property="expenseCost" styleId="expenseCost"
                        styleClass="text medium"
                        value="${managementForm.expenseCost}" />
        </td>
    </tr>

    <tr>
        <td class="label">
            <fmt:message key="management.partnerId" />
        </td>
        <td class="content">
            <select name="partnerId" id="partnerId"
				alt="allowBlank:false,vtext:'请选择合作伙伴'">
				<option value="">
					--请选择合作伙伴--
				</option>
				<logic:iterate id="partnerDeptList" name="partnerDeptList">
					<option value="${partnerDeptList.id}">
						${partnerDeptList.name}
					</option>
				</logic:iterate>
			</select>
        </td>
    
        <td class="label">
            <fmt:message key="management.remark" />
        </td>
        <td class="content">
            <html:text property="remark" styleId="remark"
                        styleClass="text medium"
                        value="${managementForm.remark}" />
        </td>
    </tr>
    
</table>
<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.search"/>" />
            <!--<input type="button" class="btn" value="<fmt:message key="button.clear"/>" />-->
        </td>
    </tr>
</table>
</html:form>
</span>

<html:form action="/managements.do?method=removeSel" styleId="managementForm" method="post"> 
<caption>
    <div class="header center"><b><fmt:message key="management.list.heading"/></b></div>
</caption>
<display:table name="managementList" cellspacing="0" cellpadding="0"
    id="managementList" pagesize="${pageSize}" class="table managementList"
    export="false"
    requestURI="${app}/partner/costmanagement/managements.do?method=search"
    sort="list" partialList="true" size="resultSize"
    decorator="com.boco.eoms.partner.management.util.ManagementDecorator">
    
    <logic:present name="managementList" property="id">
    <logic:notEmpty name="managementList" property="id">
        <display:column title="<input type='checkbox' onclick='javascript:choose();'>" >
        
        <input type="checkbox" id="${managementList.id}" name="ids" value="${managementList.id}" />
	            
        </display:column>
        <display:setProperty name="css.table" value="0,"/>
    </logic:notEmpty>
    
                
    <display:column sortable="true" headerClass="sortable" titleKey="management.partnerId">
        <eoms:id2nameDB id="${managementList.partnerId}" beanId="partnerDeptDao" />
    </display:column>
                    
    <display:column property="partnerNum" sortable="true" headerClass="sortable"
        titleKey="management.partnerNum" paramId="id" paramProperty="id"/>
                    
    <display:column property="expenseCost" sortable="true" headerClass="sortable"
        titleKey="management.expenseCost" paramId="id" paramProperty="id"/>
                    
    <display:column property="beginCostTime" sortable="true" headerClass="sortable"
        titleKey="management.beginCostTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="endCostTime" sortable="true" headerClass="sortable"
        titleKey="management.endCostTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="remark" sortable="true" headerClass="sortable"
        titleKey="management.remark" paramId="id" paramProperty="id"/>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.detail" >
        <a href="${app}/partner/costmanagement/managements.do?method=detail&id=${managementList.id}"
            title="<fmt:message key="button.detail"/>">
            	<img src="${app}/images/icons/search.gif" />
            </a>
    </display:column>

	<!-- 此处添加权限控制 -->
	<% if( null != request.getAttribute("flag") && "manager".equals(request.getAttribute("flag")) ){ %>
    <display:column sortable="true" headerClass="sortable" titleKey="button.edit">
        	           <a href="${app}/partner/costmanagement/managements.do?method=edit&id=${managementList.id}"
                title="<fmt:message key="button.edit"/>">
                	<img src="${app}/images/icons/edit.gif" />
                </a>
    </display:column>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.remove">
         <a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
         {window.location.href = '${app}/partner/costmanagement/managements.do?method=remove&flag=manager&id=${managementList.id}';}"
                title="<fmt:message key="button.remove"/>">
                	<img src="${app}/images/icons/icon.gif" />
                </a>
	</display:column>
	<% } %>

    </logic:present>
    
</display:table>

</html:form>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>