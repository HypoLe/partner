<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
    v = new eoms.form.Validation({form:'demandForm'});
                                            
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
</script>

<html:form action="/demands.do?method=save" styleId="demandForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/training/config/applicationResource-training">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="demand.form.heading"/></div>
    </caption>
                                                    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="demand.topic" />
        </td>
        <td class="content">
            <html:text property="topic" styleId="topic"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'培训主题不能为空！',maxLength:125" value="${demandForm.topic}" />
        </td>
    
        <td class="label">
            <font color='red'> * </font><fmt:message key="demand.expectTime" />
        </td>
        <td class="content">
            <html:text property="expectTime" styleId="expectTime"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'期望培训时间不能为空！'" value="${demandForm.expectTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" readonly="true" />
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="demand.referUnit" />
        </td>
        <td class="content">
            <html:text property="referUnit" styleId="referUnit"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'涉及单位不能为空！',maxLength:125" value="${demandForm.referUnit}" />
        </td>
    
        <td class="label">
            <font color='red'> * </font><fmt:message key="demand.referSpac" />
        </td>
        <td class="content">
            <html:text property="referSpac" styleId="referSpac"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'涉及专业不能为空！',maxLength:125" value="${demandForm.referSpac}" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="demand.applyUser" />
        </td>
        <td class="content">
            <html:hidden property="applyUser" styleId="applyUser"
                        alt="allowBlank:false,vtext:'培训申请人不能为空！'" value="${demandForm.applyUser}" />
            <input type="button" class="btn" name="applyUserBtn" value="<fmt:message key="button.select" />" /> 
            <div id="applyUserView" class="viewer-list"></div>
        </td>
    
        <td class="label">
            <font color='red'> * </font><fmt:message key="demand.dept" />
        </td>
        <td class="content">
            <html:hidden property="dept" styleId="dept"
                        alt="allowBlank:false,vtext:'所属部门不能为空！'" value="${demandForm.dept}" />
            <input type="button" class="btn" name="deptBtn" value="<fmt:message key="button.select" />" /> 
            <div id="deptView" class="viewer-list"></div>
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="demand.linkMobile" />
        </td>
        <td class="content">
            <html:text property="linkMobile" styleId="linkMobile"
                        styleClass="text medium"
                        alt="vtype:'number',allowBlank:false,vtext:'联系电话不能为空！',maxLength:25" value="${demandForm.linkMobile}" />
        </td>
        
        <td class="label"></td>
        <td class="content"></td>
    </tr>
            
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="demand.expectContent" />
        </td>
        <td class="content" colspan='3'>
            <html:textarea property="expectContent" styleId="expectContent"
                        styleClass="text medium" style='width:98%' rows='6'
                        alt="allowBlank:false,vtext:'期望培训内容不能为空！',maxLength:2000" value="${demandForm.expectContent}" />
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color=''> * </font><fmt:message key="demand.remark" />
        </td>
        <td class="content" colspan='3'>
            <html:textarea property="remark" styleId="remark"
                        styleClass="text medium" style='width:98%' rows='3'
                        alt="allowBlank:true,vtext:'备注',maxLength:125" value="${demandForm.remark}" />
        </td>
    </tr>
        
</table>
</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
            <c:if test="${not empty demandForm.id}">
                <input type="button" class="btn" value="<fmt:message key="button.back"/>" 
                    onclick="javascript:history.back();"    />
            </c:if>
        </td>
    </tr>
</table>

<html:hidden property="id" value="${demandForm.id}" />
<html:hidden property="createUser" value="${demandForm.createUser}" />
<html:hidden property="createTime" value="${demandForm.createTime}" />
<html:hidden property="isDeleted" value="${demandForm.isDeleted}" />

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>