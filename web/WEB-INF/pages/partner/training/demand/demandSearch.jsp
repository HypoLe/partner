<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language="javascript">

Ext.onReady(function() {

    // 设置检索条件部分不显示
	Ext.fly("listQueryObject").setDisplayed(false);
                                                        
    applyUserViewer = new Ext.JsonView("applyUserView",
            '<div class="viewlistitem-user">{name}</div>',
            { 
                emptyText : '<div>未选择用户</div>'
            }
        );
    var applyUserTreeData = '${jsonApplyUserTree}';
    applyUserViewer.jsonData = eoms.JSONDecode(applyUserTreeData);
    applyUserViewer.refresh();
    var treeAction='${app}/xtree.do?method=userFromDept';
    applyUserTree = new xbox({
            btnId:'applyUserBtn',
            dlgId:'hello-dlg',
            treeDataUrl:treeAction,
            treeRootId:'-1',
            treeRootText:'选择用户',
            treeChkMode:'',
            treeChkType:'user',
            saveChkFldId:'applyUser',
            viewer:applyUserViewer
        });
                                        
    deptViewer = new Ext.JsonView("deptView",
            '<div class="viewlistitem-dept">{name}</div>',
            { 
                emptyText : '<div>未选择部门</div>'
            }
        );
    var deptTreeData = '${jsonDeptTree}';
    deptViewer.jsonData = eoms.JSONDecode(deptTreeData);
    deptViewer.refresh();
    var treeAction='${app}/xtree.do?method=userFromDept';
    deptTree = new xbox({
            btnId:'deptBtn',
            dlgId:'hello-dlg',
            treeDataUrl:treeAction,
            treeRootId:'-1',
            treeRootText:'选择部门',
            treeChkMode:'',
            treeChkType:'dept',
            saveChkFldId:'dept',
            viewer:deptViewer
        });
                                                                                                                                                                                        
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

<fmt:bundle basename="com/boco/eoms/partner/training/config/applicationResource-training">

<div class="form-btns" id="form-btns">
  <span id="openQuery">
  	<input type="button" class="btn" onclick="openQuery()" value='展开快速查询'/>
  </span>
  <span id="closeQuery" style="display:none">
    <input type="button" class="btn" onclick="closeQuery()" value='关闭快速查询'/>
  </span>
</div>

<span id="listQueryObject">

<html:form action="/demands.do?method=search" styleId="demandForm" method="post"> 

<html:hidden property="flag" value="${flag}" />

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="demand.search.heading"/></div>
    </caption>
            
    <tr>
        <td class="label">
            <fmt:message key="demand.topic" />
        </td>
        <td class="content">
            <html:text property="topic" styleId="topic"
                        styleClass="text medium"
                        value="${demandForm.topic}" />
        </td>
        
        <td class="label">
            <fmt:message key="demand.expectTime" />
        </td>
        <td class="content">
            <html:text property="expectTime" styleId="expectTime"
                        styleClass="text medium"
                        value="${demandForm.expectTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" readonly="true" />
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="demand.applyUser" />
        </td>
        <td class="content">
            <html:hidden property="applyUser" styleId="applyUser"
                        value="${demandForm.applyUser}" />
            <input type="button" class="btn" name="applyUserBtn" value="<fmt:message key="button.select" />" /> 
            <div id="applyUserView" class="viewer-list"></div>
        </td>
    
        <td class="label">
            <fmt:message key="demand.dept" />
        </td>
        <td class="content">
            <html:hidden property="dept" styleId="dept"
                        value="${demandForm.dept}" />
            <input type="button" class="btn" name="deptBtn" value="<fmt:message key="button.select" />" /> 
            <div id="deptView" class="viewer-list"></div>
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="demand.linkMobile" />
        </td>
        <td class="content">
            <html:text property="linkMobile" styleId="linkMobile"
                        styleClass="text medium"
                        value="${demandForm.linkMobile}" />
        </td>
    
        <td class="label">
            <fmt:message key="demand.referUnit" />
        </td>
        <td class="content">
            <html:text property="referUnit" styleId="referUnit"
                        styleClass="text medium"
                        value="${demandForm.referUnit}" />
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="demand.referSpac" />
        </td>
        <td class="content" colspan='3'>
            <html:text property="referSpac" styleId="referSpac"
                        styleClass="text medium"
                        value="${demandForm.referSpac}" />
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

<html:form action="/demands.do?method=removeSel" styleId="demandForm" method="post"> 
<caption>
    <div class="header center"><b><fmt:message key="demand.list.heading"/></b></div>
</caption>
<display:table name="demandList" cellspacing="0" cellpadding="0"
    id="demandList" pagesize="${pageSize}" class="table demandList"
    export="false"
    requestURI="${app}/partner/training/demands.do?method=search"
    sort="list" partialList="true" size="resultSize"
    decorator="com.boco.eoms.partner.training.util.DemandDecorator">
    
    <logic:present name="demandList" property="id">
    <logic:notEmpty name="demandList" property="id">
        <display:column title="<input type='checkbox' onclick='javascript:choose();'>" >
        
        	           <input type="checkbox" id="${demandList.id}" name="ids" value="${demandList.id}" />
	            
        </display:column>
        <display:setProperty name="css.table" value="0,"/>
    </logic:notEmpty>
    
    <display:column property="topic" sortable="true" headerClass="sortable"
        titleKey="demand.topic" paramId="id" paramProperty="id"/>
        
    <display:column property="applyUser" sortable="true" headerClass="sortable"
        titleKey="demand.applyUser" paramId="id" paramProperty="id"/>
                    
    <display:column property="dept" sortable="true" headerClass="sortable"
        titleKey="demand.dept" paramId="id" paramProperty="id"/>
                    
    <display:column property="linkMobile" sortable="true" headerClass="sortable"
        titleKey="demand.linkMobile" paramId="id" paramProperty="id"/>
                    
    <display:column property="expectTime" sortable="true" headerClass="sortable"
        titleKey="demand.expectTime" paramId="id" paramProperty="id"/>
                    
    <display:column property="referUnit" sortable="true" headerClass="sortable"
        titleKey="demand.referUnit" paramId="id" paramProperty="id"/>
                    
    <display:column property="referSpac" sortable="true" headerClass="sortable"
        titleKey="demand.referSpac" paramId="id" paramProperty="id"/>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.detail" >
        <a href="${app}/partner/training/demands.do?method=detail&id=${demandList.id}"
            title="<fmt:message key="button.detail"/>">
            	<img src="${app}/images/icons/search.gif" />
            </a>
    </display:column>

	<!-- 此处添加权限控制 -->
	<% if( null != request.getAttribute("flag") && "manager".equals(request.getAttribute("flag")) ){ %>
    <display:column sortable="true" headerClass="sortable" titleKey="button.edit">
        <!-- 此处添加权限控制 -->
        	           <a href="${app}/partner/training/demands.do?method=edit&id=${demandList.id}"
                title="<fmt:message key="button.edit"/>">
                	<img src="${app}/images/icons/edit.gif" />
                </a>
	    	    
    </display:column>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.remove">
        <!-- 此处添加权限控制 -->
                        <a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
               {window.location.href = '${app}/partner/training/demands.do?method=remove&flag=manager&id=${demandList.id}';}"
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