<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
 
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script>
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawRmDutyEventForm'});
});

//// 置阅读标志
function fun_setEventReadFlag(object) {
	if ( fun_getChkedCount('my_check') < 1 ) {
		alert('请先勾选');
		return;		     
	}
	document.forms[0].action =  document.forms[0].action + '?method=batchRead';
	document.forms[0].submit();
}
</script>
<html:form action="/yndutyevent">
<display:table name="TawRmDutyEventList" cellspacing="0" cellpadding="0"
    id="TawRmDutyEventList" pagesize="15" class="table TawRmDutyEventList"
    sort="list" partialList="true" size="resultSize"
    requestURI="${app}/duty/yndutyevent.do?method=add"  >
    
    <display:column title="选择" media="html">
    	<input type="checkbox" name="my_check" value="${TawRmDutyEventList.id}" onclick="if(this.checked==true) { checkAll('my_check');} else { clearAll('my_check');}" />
	</display:column>

    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.faultType" >
    	<span onclick="showSubList('<bean:write name="TawRmDutyEventList" property="id"/>', this, 'TawRmDutyEventList');">
			<img src="${app}/duty/images/plus.gif">
		</span>
    	<eoms:dict key="dict-duty" dictId="faultType" itemId="${TawRmDutyEventList.faultType}" beanId="id2nameXML" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.flag" >
    	<eoms:dict key="dict-duty" dictId="faultflag" itemId="${TawRmDutyEventList.flag}" beanId="id2nameXML" />
    </display:column>
  
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.deptid" >
  		<eoms:id2nameDB id="${TawRmDutyEventList.deptid}" beanId="tawSystemDeptDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.inputuser" >
    	<eoms:id2nameDB id="${TawRmDutyEventList.inputuser}" beanId="tawSystemUserDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.eventtitle" >
    	<html:link title="打开管理界面" href="${app}/duty/yndutyevent.do?method=edit" paramId="id" paramProperty="id" paramName="TawRmDutyEventList">
     		<bean:write name="TawRmDutyEventList" property="eventtitle"/>
       	</html:link>
    </display:column>  
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.sheetid" >
    	<a href="#" onclick='return faultsheet("<bean:write name="TawRmDutyEventList" property="sheetid"/>")'>
    		<bean:write name="TawRmDutyEventList" property="sheetid"/>
       	</a>
    </display:column>        
						
    <display:column property="beginTime" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.beginTime"/>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.complateFlag" >
  		<eoms:dict key="dict-duty" dictId="complateFlag" itemId="${TawRmDutyEventList.complateFlag}" beanId="id2nameXML" />
    </display:column>
    
    <display:column property="inputdate" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.inputdate"/>
    
    <display:setProperty name="paging.banner.item_name" value="thread"/>
    <display:setProperty name="paging.banner.items_name" value="threads"/>
    <display:footer>
		<tr><td colspan="10">
			<input type="checkbox" name="my_check" value="" onclick="if(this.checked==true) { checkAll('my_check');} else { clearAll('my_check');}" />
			<html:button property="save" value="确认阅读" styleClass="btn" onclick="if(window.confirm('您确认已阅知所选事件及子过程？'))fun_setEventReadFlag(this);" />
		</td></tr>
	</display:footer>
</display:table>
</html:form>
 <div style="display: none">
 	<iframe name="subListIframe" id="subListIframe" src="" width="400" height="300"></iframe>
</div>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
