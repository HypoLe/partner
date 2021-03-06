﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
 <% 
  	String editFlag = (String)request.getAttribute("editflag");
   
  %>

<table>
	<caption>
		  <bean:message key="tawRmDutyEvent.addEvent" />
	</caption>
</table>
 
<html:form action="/dutyevent.do?method=edit"
	method="post" styleId="tawRmDutyEventForm">
	 <input type="hidden" id="id" name="id" value='${tawRmDutyEventForm.id}'/>
	 <html:hidden property="id" />
	<table border=0 cellspacing="1" cellpadding="1" class="listTable">
		<!--附加表以及XML文件基本属性表格：开始-->
		<tr>
			<td COLSPAN="4" class="label">
				 <bean:message key="tawRmDutyEvent.faultType" />
			</td>
			
			<td COLSPAN="10">
			<eoms:dict key="dict-duty" dictId="faultType" beanId="selectXML" defaultId="${tawRmDutyEventForm.faultType}"  isQuery="false"  selectId="faultType"  alt="allowBlank:false"/>
			<%--<html:text property="faultType" styleId="faultType"
						styleClass="text medium" />
				--%><%--<input name="faultType" value="" 
					class="text">
			--%></td>
			<td COLSPAN="4" class="label">
				 <bean:message key="tawRmDutyEvent.flag" />
			</td>
			<td COLSPAN="10"> 
			  			<eoms:dict key="dict-duty" dictId="faultflag" beanId="selectXML"  defaultId="${tawRmDutyEventForm.flag}" isQuery="false"  selectId="flag"  alt="allowBlank:false"/>
						<%--
				<input name="flag" value=""
					class="text">
			--%></td>
		</tr> 
		<tr>
			<td COLSPAN="4" class="label">
					 <bean:message key="tawRmDutyEvent.beginTime" />
			</td>
			<td COLSPAN="10">

				<html:text property="beginTime" styleId="beginTime"
						styleClass="text medium"  onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true" alt="allowBlank:false,vtext:'请填写开始时间'"/>

			</td>
			<td COLSPAN="4" class="label">
					 <bean:message key="tawRmDutyEvent.endtime" />
			</td>
			<td COLSPAN="10">
			<html:text property="endtime" styleId="endtime"
						styleClass="text medium"  onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true" alt="allowBlank:false,vtext:'请填写结束时间'"/>
					 
			</td>
		</tr>

		<tr class="tr_show">
			<td COLSPAN="4" class="label">
				 <bean:message key="tawRmDutyEvent.eventtitle" />
			</td>
			<td COLSPAN="24">
					<input name="eventtitle"   type="text"  class="clstext"  size="70" value="${tawRmDutyEventForm.eventtitle}" alt="allowBlank:false,vtext:'请填写标题时间'"/>
	 	</tr>
		<TR>
		 	<td COLSPAN="4" class="label">
				 <bean:message key="tawRmDutyEvent.complateFlag" />
			</td>
			<td COLSPAN="24"> 
						<eoms:dict key="dict-duty" dictId="complateFlag" beanId="selectXML"  isQuery="false"  defaultId="${tawRmDutyEventForm.complateFlag}" selectId="complateFlag"  alt="allowBlank:false"/>
			
		</tr>
				<TR>
		 	<td COLSPAN="4" class="label">
				 <bean:message key="tawRmDutyEvent.accessories" />
			</td>
			<td COLSPAN="34"> 
					 <eoms:attachment name="tawRmDutyEventForm" property="accessories" 
            scope="request" idField="accessories" appCode="9" viewFlag="N"/> 
		</td>
		 
		</tr>
	 
			<tr id="pubstatus" style="display:none">
		 	<td COLSPAN="4" class="label">				
			</td>
			<td COLSPAN="10">
			<html:select property="pubStatus" style="width:100px">
					<html:option value="" ><bean:message key="tawRmDutyEvent.chose" /></html:option>
        			<html:option value="0" ><bean:message key="tawRmDutyEvent.nopublish" /></html:option>
        			<html:option value="1" ><bean:message key="tawRmDutyEvent.alpublish" /></html:option>
        		</html:select>
				 
			</td>
			<td COLSPAN="4" class="label">				
			</td>
			<td COLSPAN="10">
				 <html:select property="pflag" style="width:100px">
					<html:option value="" ><bean:message key="tawRmDutyEvent.chose" /></html:option>
        			<html:option value="0" ><bean:message key="tawRmDutyEvent.common" /></html:option>
        			<html:option value="1" ><bean:message key="tawRmDutyEvent.top" /></html:option>
        			<html:option value="2" ><bean:message key="tawRmDutyEvent.cycle" /></html:option>
        		</html:select>
			</td>
		</tr>
		<tr id="time" style="display:none">
		 	<td COLSPAN="4" class="label">
				<bean:message key="tawRmDutyEvent.starttime" />
			</td>
			<td COLSPAN="10">
					<html:text property="startTime" styleId="startTime"
						styleClass="text medium"  onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"/>
			</td>
			<td COLSPAN="4" class="label">
				<bean:message key="tawRmDutyEvent.pendtime" />
			</td>
			<td COLSPAN="10">
					<html:text property="endTime" styleId="endTime"
						styleClass="text medium"  onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true" />
			</td>
			<html:hidden  name="tawRmDutyEventForm" property="beginTime" />
			<input type="hidden" name="begintime" value='${tawRmDutyEventForm.beginTime}' />
			<input type="hidden" name="endtime" value='${tawRmDutyEventForm.endtime}' />
			
		</tr>
		<tr id="title" style="display:none">
		 	<td COLSPAN="4" class="label">
				<bean:message key="tawRmDutyEvent.oid" />
			</td>
			<td COLSPAN="10">
				<html:text name="tawRmDutyEventForm" property="oid"/>
			</td>
			<td COLSPAN="4" class="label">
				<bean:message key="tawRmDutyEvent.title" />
			</td>
			<td COLSPAN="10">
				<html:text name="tawRmDutyEventForm" property="title"/>
			</td>
		</tr>
		<%if(editFlag!="1"){ %>	
		<tr>
			<td COLSPAN="28" > 
			<input type="submit" value="<bean:message key="tawRmDutyEvent.editEvent" />"  class="button">
			<input type="button" value="<bean:message key="tawRmDutyEvent.removeEvent" />" Onclick="javascript:removeEvent('${tawRmDutyEventForm.id}');" class="button">
			</td>
		</tr>
		<%} %>
	</table>
<table>
	<caption>
		 <bean:message key="tawRmDutyEvent.addEventSub" />
	</caption>
</table>
	<table border=0 cellspacing="1" cellpadding="1" class="listTable">
		<!--附加表以及XML文件基本属性表格：开始-->
		<tr>
			<td COLSPAN="4" class="label">
				 <bean:message key="tawRmDutyEventSub.happentime" />
			</td>
			<td COLSPAN="10">
			
				<input type="text" name="happentime" size="20" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
			<td COLSPAN="4" class="label">
				 <bean:message key="tawRmDutyEventSub.worksheetid" />
			</td>
			<td COLSPAN="10">
		    <html:text property="worksheetid" styleId="worksheetid"
						styleClass="text medium" />
				 
			</td>
		</tr> 
	 

		<tr class="tr_show">
			<td COLSPAN="4" class="label">
				 <bean:message key="tawRmDutyEventSub.content" />
			</td>
			<td COLSPAN="24">
					  <textarea name="content"  rows="3" cols="20" ></textarea>
		</tr>
		
		 
		<tr>
			<td COLSPAN="28" > 
			<input type="button" value=<bean:message key="tawRmDutyEventSub.addEventSub" /> onClick="javascript:onsubSave(0);" class="button">
			<input type="button" value=<bean:message key="tawRmDutyEventSub.addEventSubandClose" />  onClick="javascript:onsubSave(1);" class="button">
			</td>
		</tr>
	</table>
	
	<table border=0 cellspacing="1" cellpadding="1" class="listTable">
	<display:table name="TawRmDutyEventSubList" cellspacing="0" cellpadding="0"
    id="TawRmDutyEventSubList" pagesize="15" class="table tawRmDutyEventSubList"
      sort="list" partialList="true" size="resultSize"
    requestURI="${app}/duty/dutyevent.do?method.do?method=search"  >

	<display:column property="recordman" sortable="true" headerClass="sortable"
         titleKey="tawRmDutyEventSub.recordman" />
         
    <display:column property="happentime" sortable="true" headerClass="sortable"
         titleKey="tawRmDutyEventSub.happentime"/>

    <display:column property="worksheetid" sortable="true" headerClass="sortable"
         titleKey="tawRmDutyEventSub.worksheetid"/>

    <display:column property="content" sortable="true" headerClass="sortable"
         titleKey="tawRmDutyEventSub.content"/>
         
 
    <display:setProperty name="paging.banner.item_name" value="thread"/>
    <display:setProperty name="paging.banner.items_name" value="threads"/>
</display:table>

</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>

<script language="javascript">
var el = eoms.$('flag');

if(el.value>2){
	    eoms.$('time').show();
	    eoms.$('title').show();
	    eoms.$('pubstatus').show();
	  }
	  else{
	    eoms.$('time').hide();
	    eoms.$('title').hide();
	    eoms.$('pubstatus').hide();
	  	
	  }
Ext.onReady(function() {
 v = new eoms.form.Validation({form:'tawRmDutyEventForm'});
  var el = eoms.$('flag');
  var maxFlag = 3;
  el.on('change',function(el){
	  if(el.selectedIndex>=maxFlag){
	    eoms.$('time').show();
	    eoms.$('title').show();
	    eoms.$('pubstatus').show();
	  }
	  else{
	    eoms.$('time').hide();
	    eoms.$('title').hide();
	    eoms.$('pubstatus').hide();
	  	
	  }
  });

});

</script>
<script>
 
function onsubSave(struts){
 	    var begin = document.forms[0].begintime.value;
 	    
 	    var end = document.getElementById("endtime").value;
 	   
 	    var happentime = document.forms[0].happentime.value;
 	    
 	   
    	if(happentime==""){
    		alert("请填写发生时间!");
    		return false; 
    	}
        if(comptime(begin,happentime)<0){
    		alert("发生时间不能小于主事件的开始时间");
    		return false; 
    	}
    	if(comptime(end,happentime)>0){
    		alert("发生时间不能大于主事件的结束时间");
    		return false; 
    	}
    	if(document.forms[0].content.value.length >300){
    		alert("内容长度不能大于300字节")
    		return false;
    	}
    	
    	var worksheetid = document.forms[0].worksheetid.value;
    	//if(worksheetid==""){
    		//alert("请选择紧急程度");
    		//return false; 
    	//}
    	var content = document.forms[0].content.value;
    	if(content==""){
    		alert("请填写内容!");
    		return false; 
    	} 
    	var id = document.forms[0].id.value;
     	document.forms[0].id.value = happentime;
    	document.forms[0].happentime.value = happentime;
    	document.forms[0].worksheetid.value = worksheetid;
    	document.forms[0].content.value = content;
    	document.forms[0].action ="${app}/duty/dutyevent.do?method=saveSub&struts="+struts;
    	document.forms[0].submit();
        return true;
  	  
  	}
 function removeEvent(eventid){ 
 	if(confirm("您确认删除这个项目吗？")){
 		document.forms[0].action ="${app}/duty/dutyevent.do?method=deleted&id="+eventid;
 		document.forms[0].submit();
 		return true;
 	}else{
 			return false;
 	}
 }	
  	
function comptime(beginTime,endTime){

		var beginTimes=beginTime.substring(0,10).split('-');
		var endTimes=endTime.substring(0,10).split('-');

		beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
		endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
		
		var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;

		if(a<0){
		return -1;
		}else if (a>0){
		return 1;
		}else if (a==0){
		return 0;
		}else{
		return 'exception'
		}
 
}
  	
 
</script>
