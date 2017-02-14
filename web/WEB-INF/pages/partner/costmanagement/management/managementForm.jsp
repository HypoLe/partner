<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
    v = new eoms.form.Validation({form:'managementForm'});
    v.custom = function(){
    	var partnerId = document.getElementById("partnerId").value;
    	if(partnerId == ''){
    		alert("请选择【合作伙伴】！");
			return false;
    	}
    	document.getElementById("submitInput").disabled = true;//锁定提交按钮
      	return true;
    }
                                                                                                                                        
});
</script>

<html:form action="/managements.do?method=save" styleId="managementForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/management/config/applicationResource-management">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="management.form.heading"/></div>
    </caption>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="management.beginCostTime" />
        </td>
        <td class="content">
        	<input property="beginCostTime" id="beginCostTime" name="beginCostTime"
                        readonly="readonly" styleClass="text medium"
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" value="${managementForm.beginCostTime}" />
        </td>
    
        <td class="label">
            <font color='red'> * </font><fmt:message key="management.endCostTime" />
        </td>
        <td class="content">
        	<input property="endCostTime" id="endCostTime" name="endCostTime"
                        readonly="readonly" styleClass="text medium"
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" value="${managementForm.endCostTime}" />
        </td>
    </tr>
                                                    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="management.partnerNum" />
        </td>
        <td class="content">
            <html:text property="partnerNum" styleId="partnerNum"
                        styleClass="text medium"
                        alt="vtype:'number',allowBlank:false,vtext:'人员规模不能为空！',maxLength:25" 
                        value="${managementForm.partnerNum}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="management.expenseCost" />(单位:元)
        </td>
        <td class="content">
            <html:text property="expenseCost" styleId="expenseCost"
                        styleClass="text medium"
                        alt="vtype:'number',allowBlank:false,vtext:'成本开资费用不能为空！',maxLength:25" 
                        value="${managementForm.expenseCost}" />
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="management.partnerId" />
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
            <font color=''> * </font><fmt:message key="management.remark" />
        </td>
        <td class="content">
            <html:textarea property="remark" styleId="remark"
                        styleClass="text medium"
                        alt="allowBlank:true,vtext:'备注',maxLength:125" value="${managementForm.remark}" />
        </td>
    </tr>
        
</table>
</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.save"/>" id='submitInput' name='submitInput' />
            <c:if test="${not empty managementForm.id}">
                <input type="button" class="btn" value="<fmt:message key="button.back"/>" 
                    onclick="javascript:history.back();"    />
            </c:if>
        </td>
    </tr>
</table>

<html:hidden property="id" value="${managementForm.id}" />
<html:hidden property="createUser" value="${managementForm.createUser}" />
<html:hidden property="createTime" value="${managementForm.createTime}" />
<html:hidden property="isDeleted" value="${managementForm.isDeleted}" />

</html:form>

<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面
window.onload = function(){
	var err = '${err}';
    if(err!=''){
    	alert(err);
    }
    var partnerId = '${managementForm.partnerId}';
    var partnerIdByUserID = '${partnerIdByUserID}';
    var currentUserId = '${currentUserId}';
	
	if(partnerId != ''){
		document.getElementById("partnerId").value = partnerId;
	}else if(partnerIdByUserID != ''){
		document.getElementById("partnerId").value = partnerIdByUserID;
	}
	
	//判断当前用户是否为管理员
	if(currentUserId != 'admin'){
		document.getElementById("partnerId").disabled = true;//锁定不可选
	}
	
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>