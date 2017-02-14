<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.boco.eoms.km.expert.config.KmExpertProps" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmExpertBasicForm'});

   <c:if test="${kmExpertBasicForm.userId == '' }">
    new xbox({
        btnId: 'realName',
        dlgId: 'showuser-dlg',
        treeDataUrl: '${app}/kmmanager/kmExpertTree.do?method=userFromDept',
        treeRootId: '-1',
        treeRootText: '人员列表',
        treeChkMode: 'single',
        treeChkType: 'user',
        showChkFldId: 'realName',
        saveChkFldId: 'userId'
    });
    </c:if>



	var areaXbox = new xbox({
	        btnId: "areaName",
	        dlgId: 'area-dlg',
	        treeDataUrl: '${app}/kmmanager/kmExpertTree.do?method=getAreas',
	        treeRootId: '<%=KmExpertProps.getDictRootId("RootArea")%>',
	        treeRootText: '所属地域',
	        treeChkMode: 'single',
	        showChkFldId: "areaName",
	        saveChkFldId: "area"
	});
});

window.onload = function(){
    var userId = '${kmExpertBasicForm.userId}';
	var operType = '${operType}';
	if(userId != '' && operType == 'save'){
	    parent.setUserid(userId);
    }
}

</script>

<html:form action="/kmExpertBasics.do?method=save" styleId="kmExpertBasicForm" method="post">

	<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager"><br>

		<table class="formTable">

<!-- 新增专家信息 -->
<c:if test="${kmExpertBasicForm.id == null or kmExpertBasicForm.id == ''}">
			<tr>
				<td class="label">
					<fmt:message key="kmExpertBasic.userId" />&nbsp;<font color='red'>*</font>
				</td>
				<td colspan="3" class="content">
					<input type="text" id="realName" name="realName" value="" readonly="readonly" class="text medium" 
					    alt="allowBlank:false,vtext:'请选择专家姓名...'" />					
					<html:hidden property="userId" value="${kmExpertBasicForm.userId}" />
				</td>				
			</tr>
</c:if>
<!-- 修改专家信息 -->
<c:if test="${kmExpertBasicForm.id != null and kmExpertBasicForm.id != ''}">
			<tr>
				<td class="label">
					<fmt:message key="kmExpertBasic.userId" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
				    <eoms:id2nameDB id="${kmExpertBasicForm.userId}" beanId="tawSystemUserDao" />
				    <input type="hidden" name="realName" value="" />				    
					<html:hidden property="userId" value="${kmExpertBasicForm.userId}" />
				</td>
				<td class="label">
					<fmt:message key="kmExpertBasic.deptId" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
				    <eoms:id2nameDB id="${kmExpertBasicForm.deptId}" beanId="tawSystemDeptDao" />
					<html:hidden property="deptId" value="${kmExpertBasicForm.deptId}" />
				</td>							
			</tr>
</c:if>

			<tr>
				<td class="label">
				    <fmt:message key="kmExpertBasic.phone" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
				    <html:text property="phone" styleId="phone" styleClass="text medium" alt="allowBlank:false,vtext:'请输入联系电话...'" value="${kmExpertBasicForm.phone}" />
				</td>
				<td class="label">
					<fmt:message key="kmExpertBasic.position" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="position" styleId="position" styleClass="text medium" 
						value="${kmExpertBasicForm.position}" 
						alt="allowBlank:false,vtext:'请输入职位...'" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="kmExpertBasic.joinJobDate" />&nbsp;<font color='red'>*</font>
				</td>			
				<td class="content">
					<html:text property="joinJobDate" styleId="joinJobDate" styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,false,-1);" readonly="true"  
						value="${kmExpertBasicForm.joinJobDate}" 
						alt="allowBlank:false,vtext:'请选择参加工作时间...'" />
				</td>
				<td class="label">
					<fmt:message key="kmExpertBasic.education" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
				    <eoms:comboBox name="education" id="education" initDicId='<%=KmExpertProps.getDictRootId("RootEducation")%>' 
				        defaultValue="${kmExpertBasicForm.education}" 
				        alt="allowBlank:false,vtext:'请选择专家最高学历(单选字典)...'"/>				        			
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="kmExpertBasic.privateJobDate" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="privateJobDate" styleId="privateJobDate" styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,false,-1);" readonly="true"  
						value="${kmExpertBasicForm.privateJobDate}" 
						alt="allowBlank:false,vtext:'请选择参加本单位时间...'" />
				</td>
				<td class="label">
					<fmt:message key="kmExpertBasic.polity" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
				    <eoms:comboBox name="polity" id="polity" initDicId='<%=KmExpertProps.getDictRootId("RootPolity")%>'
				        defaultValue="${kmExpertBasicForm.polity}" 
				        alt="allowBlank:false,vtext:'请选择专家政治面貌(单选字典)...'"/>
				</td>				
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="kmExpertBasic.company" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="company" styleId="company" styleClass="text medium" 
						value="${kmExpertBasicForm.company}" 
						alt="allowBlank:false,vtext:'请输入工作单位...'"/>
				</td>	
				<td class="label">
					员工编号&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="expertNumber" styleId="expertNumber" styleClass="text medium" 
						value="${kmExpertBasicForm.expertNumber}" 
						alt="allowBlank:false,vtext:'请输入员工编号...'"/>
				</td>			
			</tr>

			<tr>
			    <td class="label">
					<fmt:message key="kmExpertBasic.expertClass" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
				    <eoms:comboBox name="expertClass" id="expertClass" initDicId='<%=KmExpertProps.getDictRootId("RootExpertClass")%>'
				        defaultValue="${kmExpertBasicForm.expertClass}" 
				        alt="allowBlank:false,vtext:'请选择专家类型(单选字典)...'"/>
				</td>			
			    <td class="label">
					<fmt:message key="kmExpertBasic.expertLevel" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
				    <eoms:comboBox name="expertLevel" id="expertLevel" initDicId='<%=KmExpertProps.getDictRootId("RootExpertLevel")%>'
				        defaultValue="${kmExpertBasicForm.expertLevel}" 
				        alt="allowBlank:false,vtext:'请选择专家级别(单选字典)...'"/>
				</td>
			</tr>

			<tr>				
				<td class="label">
					<fmt:message key="kmExpertBasic.specialty" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" id="specialtyName" name="specialtyName" class="text" 
					    value="<eoms:id2nameDB id="${kmExpertBasicForm.specialty}" beanId="IItawSystemDictTypeDao" />" 
					    />					
					<html:hidden property="specialty" styleId="specialty" value="${kmExpertBasicForm.specialty}" />
				</td>
				<td class="label">
					<fmt:message key="kmExpertBasic.area" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" id="areaName" name="areaName" class="text" readonly="readonly" 
					    value="<eoms:id2nameDB id="${kmExpertBasicForm.area}" beanId="tawSystemAreaDao" />" 
					    alt="allowBlank:false,vtext:'请选择所属地域(字典)...'"/>					
					<html:hidden property="area" styleId="area" value="${kmExpertBasicForm.area}" />				
				</td>				
			</tr>
			
			<!-- 
			<tr>				
				<td class="label">
					专家组别设值&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
				
				</td>				
			</tr>
			-->
			
			<tr>				
				<td class="label">
					性别&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<eoms:dict key="dict-kmmanager" dictId="sex" isQuery="false" 
			           defaultId="${kmExpertBasicForm.sex}" selectId="sex" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择性别(字典)...'"/>	
				</td>
				<td class="label">
					出生日期&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="birthday" styleId="birthday" styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,false,-1);" readonly="true"  
						value="${kmExpertBasicForm.birthday}" 
						alt="allowBlank:false,vtext:'请选择出生日期...'" />	
				</td>				
			</tr>
			
			<tr>				
				<td class="label">
					邮箱&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="email" styleId="email" styleClass="text medium" 
						value="${kmExpertBasicForm.email}" 
						alt="allowBlank:false,vtext:'请输入邮箱...'"/>
				</td>
				<td class="label">
					民族&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="nation" styleId="nation" styleClass="text medium" 
						value="${kmExpertBasicForm.nation}" 
						alt="allowBlank:false,vtext:'请输入民族...'"/>				
				</td>				
			</tr>

			<tr>
				<td class="label">
				    <fmt:message key="kmExpertBasic.intro" />&nbsp;<font color='red'>*</font>
				</td>
				<td colspan="3" class="content">
				    <textarea name="intro" cols="50" id="intro" class="textarea max" alt="allowBlank:false,vtext:'请输入专家特长...'">${kmExpertBasicForm.intro}</textarea>			    
				</td>
			</tr>
		</table>

	</fmt:bundle>

	<table>
		<tr>
			<td>
				<input type="submit" class="btn" value='<fmt:message key="button.save"/>' />
				<c:if test="${not empty kmExpertBasicForm.id}">
					<input type="button" class="btn" value='<fmt:message key="button.back"/>' onclick="javascript:parent.window.history.back();" />
				</c:if>
			</td>
		</tr>
	</table>

	<html:hidden property="id" value="${kmExpertBasicForm.id}" />
	<html:hidden property="baseScore" value="100" />

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>