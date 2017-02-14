<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
 
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script>
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawRmDutyEventForm'});
});

function show_currentcount(){
	if(document.getElementById("tr_cCount").style.display=="none"){
		document.getElementById("tr_cCount").style.display="block";
	} else {
		document.getElementById("tr_cCount").style.display="none";
	}
}
			
function show_lastcount(){
	if(document.getElementById("tr_lcount").style.display == "none") {
		document.getElementById("tr_lcount").style.display = "block";
	} else {
		document.getElementById("tr_lcount").style.display = "none";
	}
}		
</script> 

<table cellpadding="0" class="table CURRENTEVENTTLIST" cellspacing="0"
	<tr>
		<td align="left" class="label" width="5%" onclick="show_currentcount()" style="cursor:hand;" onmousemove="this.style['color']='blue'" onmouseout="this.style['color']='#333333'" title="${eoms:a2u('统计')}">
			${eoms:a2u('统计事件')}
		</td>
		<td align="left" class="content">
			${eoms:a2u('本班次监控事件')}
		</td>
	</tr>
	<tr style="display:none" id="tr_cCount">
		<td height="25px" bgcolor="#E5EDF8" colSpan="8">
		<font color="ff2000">${eoms:a2u('事件数:')}
			<c:choose><c:when test="${CURRENTEVENTINFO.faultTypeNum!=null}">
			<bean:define id="faultTypeNum" name="CURRENTEVENTINFO" property="faultTypeNum" type="java.lang.String[][]"/>
			<% String innerHtml_E = "";
				for(int i=0;i<faultTypeNum.length;i++) {
					if(faultTypeNum[i][1]!=null)
					innerHtml_E +=faultTypeNum[i][0]+" "+ faultTypeNum[i][1]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				out.print(innerHtml_E+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			%>		
			</c:when></c:choose>
			${eoms:a2u('星级数:')}
			<c:choose><c:when test="${CURRENTEVENTINFO.faultFlagNum!=null}">
			<bean:define id="faultFlagNum" name="CURRENTEVENTINFO" property="faultFlagNum" type="java.lang.String[][]"/>
			<% 	String innerHtml_E2 = "";
				for(int i=0;i<faultFlagNum.length;i++) {
					if(faultFlagNum[i][1]!=null)
						innerHtml_E2 +=faultFlagNum[i][0]+" "+ faultFlagNum[i][1]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				out.print(innerHtml_E2+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); 
			%>		
			</c:when></c:choose>
		</font>
		</td>
	</tr>
	<tr>
		<td align="left" height="20px" class="label">${eoms:a2u('值班时间')}</td>
		<td align="left" height="20px" class="content">
			${CURRENTEVENTINFO.dutyBeginTime}--${CURRENTEVENTINFO.dutyEndTime}
		</td>
	</tr>
</table>
<display:table name="CURRENTEVENTTLIST" cellspacing="0" cellpadding="0"
    id="CURRENTEVENTTLIST" class="table CURRENTEVENTTLIST"
    sort="list" partialList="true" size="resultSize"
    requestURI="${app}/duty/yndutyevent.do?method=add"  >

    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.faultType" >
    	<span onclick="showSubList('<bean:write name="CURRENTEVENTTLIST" property="id"/>', this, 'CURRENTEVENTTLIST');">
			<img src="${app}/duty/images/plus.gif">
		</span>
    	<eoms:dict key="dict-duty" dictId="faultType" itemId="${CURRENTEVENTTLIST.faultType}" beanId="id2nameXML" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.flag" >
    	<eoms:dict key="dict-duty" dictId="faultflag" itemId="${CURRENTEVENTTLIST.flag}" beanId="id2nameXML" />
    </display:column>
  
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.deptid" >
  		<eoms:id2nameDB id="${CURRENTEVENTTLIST.deptid}" beanId="tawSystemDeptDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.inputuser" >
    	<eoms:id2nameDB id="${CURRENTEVENTTLIST.inputuser}" beanId="tawSystemUserDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.eventtitle" >
    	<html:link title="${eoms:a2u('打开管理界面!')}" href="${app}/duty/yndutyevent.do?method=edit" paramId="id" paramProperty="id" paramName="CURRENTEVENTTLIST">
     		<bean:write name="CURRENTEVENTTLIST" property="eventtitle"/>
       	</html:link>
    </display:column>  
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.sheetid" >
    	<a href="#" onclick='return faultsheet("<bean:write name="CURRENTEVENTTLIST" property="sheetid"/>")'>
    		<bean:write name="CURRENTEVENTTLIST" property="sheetid"/>
       	</a>
    </display:column>        
						
    <display:column property="beginTime" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.beginTime"/>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.complateFlag" >
  		<eoms:dict key="dict-duty" dictId="complateFlag" itemId="${CURRENTEVENTTLIST.complateFlag}" beanId="id2nameXML" />
    </display:column>
    
    <display:column property="inputdate" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.inputdate"/>
    
    <display:setProperty name="paging.banner.item_name" value="thread"/>
    <display:setProperty name="paging.banner.items_name" value="threads"/>
</display:table>

<table border="0" width="100%" cellspacing="1" class="table_show" cellPadding="0">
	<tr><!----------------------- 呈现前一班次活动事件 BEGIN----------------------->
		<td align="center" class="clsscd1" colSpan="3">${eoms:a2u('前班次监控纪要')}</td>
	</tr>
	<tr>
		<td align="center" height="20px" bgcolor="#E5EDF8" width="15%">${eoms:a2u('值班时间')}</td>
		<td align="center" height="20px" bgcolor="#E5EDF8" colSpan="2">
			<bean:write name="LASTEVENTINFO" property="dutyBeginTime" />--
			<bean:write name="LASTEVENTINFO" property="dutyEndTime" />
		</td>
	</tr>

	<tr class="tr_show">
		<td rowSpan="4" align="left" class="clsfth" width="20%">${eoms:a2u('监控纪要')}</td>
		<td align="center" class="clsfth" width="50" width="20%">${eoms:a2u('网络重要故障')}</td>
		<td width="60%">
			<TEXTAREA name="netfault" rows=4 style="width: 100%; BACKGROUND-COLOR: lightgrey" readOnly=""><bean:write name="LASTTAWRMRECORD" property="netfault" /></TEXTAREA>
		</td>
	</tr>
	<tr class="tr_show">
		<td align="left" class="clsfth" width="20%">${eoms:a2u('重要社会或灾害事件')}</td>
		<td >
			<TEXTAREA name="importantaffair" rows=6 style="width: 100%; BACKGROUND-COLOR: lightgrey" readOnly=""><bean:write name="LASTTAWRMRECORD" property="importantaffair" /></TEXTAREA>
		</td>
	</tr>
	<tr class="tr_show">
		<td align="left" class="clsfth" width="20%">${eoms:a2u('需要省公司协调事项')}</td>
		<td>
			<TEXTAREA name="harmony" rows=6 style="width: 100%; BACKGROUND-COLOR: lightgrey" readOnly=""><bean:write name="LASTTAWRMRECORD" property="harmony" /></TEXTAREA>
		</td>
	</tr>
	<tr class="tr_show">
		<td align="left" class="clsfth" width="20%">${eoms:a2u('交班事项')}</td>
		<td>
			<TEXTAREA name="otheraffair" rows=16 style="width: 100%; BACKGROUND-COLOR: lightgrey" readOnly=""><bean:write name="LASTTAWRMRECORD" property="otheraffair" /></TEXTAREA>
		</td>
	</tr>
</table>

<table cellpadding="0" class="table CURRENTEVENTTLIST" cellspacing="0"
	<tr>
		<td align="left" class="label" width="10%"onclick="show_lastcount()" style="cursor:hand;" onmousemove="this.style['color']='blue'" onmouseout="this.style['color']='#333333'" title="${eoms:a2u('统计')}">
			${eoms:a2u('统计事件')}
		</td>
		<td align="left" class="content">
			${eoms:a2u('前班次监控事件')}
		</td>
	</tr>
	<tr style="display:none" id="tr_lcount">
		<td height="25px" bgcolor="#E5EDF8" colSpan="8">
		<font color="ff2000">${eoms:a2u('事件数:')}
			<c:choose><c:when test="${LASTEVENTINFO.faultTypeNum!=null}">
			<bean:define id="faultTypeNum2" name="LASTEVENTINFO" property="faultTypeNum" type="java.lang.String[][]"/>
			<% String innerHtml_L = "";
				for(int i=0;i<faultTypeNum2.length;i++) {
					if(faultTypeNum2[i][1]!=null)
					innerHtml_L +=faultTypeNum2[i][0]+" "+ faultTypeNum2[i][1]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				out.print(innerHtml_L+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			%>		
			</c:when></c:choose>
			<c:choose><c:when test="${LASTEVENTINFO.faultFlagNum!=null}">
			${eoms:a2u('星级数:')}
			<bean:define id="faultFlagNum2" name="LASTEVENTINFO" property="faultFlagNum" type="java.lang.String[][]"/>
			<%  String innerHtml_L2 = "";
				for(int i=0;i<faultFlagNum2.length;i++) {
					if(faultFlagNum2[i][1]!=null)
					innerHtml_L2 +=faultFlagNum2[i][0]+" "+ faultFlagNum2[i][1]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				out.print(innerHtml_L2+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			%>		
			</c:when></c:choose>		
		</font>
		</td>
	</tr>
	<tr>
		<td align="left" height="20px" class="label">${eoms:a2u('值班时间')}</td>
		<td align="left" height="20px" class="content">
			${LASTEVENTINFO.dutyBeginTime}--${LASTEVENTINFO.dutyEndTime}
		</td>
	</tr>
</table>
<display:table name="LASTEVENTLIST" cellspacing="0" cellpadding="0"
    id="LASTEVENTLIST" class="table LASTEVENTLIST"
    sort="list" partialList="true" size="resultSize"
    requestURI="${app}/duty/yndutyevent.do?method=add"  >

    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.faultType" >
    	<span onclick="showSubList('<bean:write name="LASTEVENTLIST" property="id"/>', this, 'LASTEVENTLIST');">
			<img src="${app}/duty/images/plus.gif">
		</span>
    	<eoms:dict key="dict-duty" dictId="faultType" itemId="${LASTEVENTLIST.faultType}" beanId="id2nameXML" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.flag" >
    	<eoms:dict key="dict-duty" dictId="faultflag" itemId="${LASTEVENTLIST.flag}" beanId="id2nameXML" />
    </display:column>
  
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.deptid" >
  		<eoms:id2nameDB id="${LASTEVENTLIST.deptid}" beanId="tawSystemDeptDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.inputuser" >
    	<eoms:id2nameDB id="${LASTEVENTLIST.inputuser}" beanId="tawSystemUserDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.eventtitle" >
    	<html:link title="${eoms:a2u('打开管理界面!')}" href="${app}/duty/yndutyevent.do?method=edit" paramId="id" paramProperty="id" paramName="LASTEVENTLIST">
     		<bean:write name="LASTEVENTLIST" property="eventtitle"/>
       	</html:link>
    </display:column>  
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.sheetid" >
    	<a href="#" onclick='return faultsheet("<bean:write name="LASTEVENTLIST" property="sheetid"/>")'>
    		<bean:write name="LASTEVENTLIST" property="sheetid"/>
       	</a>
    </display:column>        
    <display:column property="beginTime" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.beginTime"/>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.complateFlag" >
  		<eoms:dict key="dict-duty" dictId="complateFlag" itemId="${LASTEVENTLIST.complateFlag}" beanId="id2nameXML" />
    </display:column>
    
    <display:column property="inputdate" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.inputdate"/>
    
    <display:setProperty name="paging.banner.item_name" value="thread"/>
    <display:setProperty name="paging.banner.items_name" value="threads"/>
</display:table>
 <div style="display: none">
 	<iframe name="subListIframe" id="subListIframe" src="" width="400" height="300"></iframe>
</div>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
