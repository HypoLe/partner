<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/tawzjMonths.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/ApplicationResources-summary">

<content tag="heading">
	<fmt:message key="tawzjMonth.heading.lisst" />
</content>
<html:form action="/tawzjMonths.do?method=edit" method="post" styleId="tawzjMonthForm" enctype="multipart/form-data">
	<display:table name="tawzjMonthList" cellspacing="0" cellpadding="0" id="tawzjMonthList" pagesize="${pageSize}" class="table tawzjMonthList"
		export="false" requestURI="${app}/summary/tawzjMonths.do?method=search" sort="list" partialList="true"
		 size="resultSize" decorator="com.boco.eoms.summary.displaytag.MonthDisplay">

	<logic:notEmpty name="tawzjMonthList">		 
			<display:column property="id" titleKey="month.choose" />
			<display:setProperty name="css.table" value="0,"/>
	</logic:notEmpty>	
	
	<display:column property="work" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.work" href="${app}/summary/tawzjMonths.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="specialWork" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.specialWork" href="${app}/summary/tawzjMonths.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="legacy" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.legacy"  paramId="id" paramProperty="id"/>
			
	<display:column property="team" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.team" paramId="id" paramProperty="id"/>

	<display:column property="monthWork" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.planmonthWork"  paramId="id" paramProperty="id"/>

	<display:column property="planSpecialWork" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.planlist" paramId="id" paramProperty="id"/>

	<display:column property="dateTime" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.dateTime"  paramId="id" paramProperty="id"/>
			
	<display:column property="statusName" sortable="true" paramName="statusName" 
			headerClass="sortable" titleKey="tawzjMonth.status"  paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="tawzjMonth" />
		<display:setProperty name="paging.banner.items_name" value="tawzjMonths" />
	</display:table>
	
	<br/>
	<input type="button" class="btn" value="<fmt:message key="button.appYes"/>" onclick="app(2);"/>
	<input type="button" class="btn" value="<fmt:message key="button.appNo"/>" onclick="app(3);"/>
</html:form>
</fmt:bundle>
<script type="text/javascript">  
	function app(num){
	var objs=document.getElementsByName("ids");
	var frm=document.forms[0];
	var a=0;
	var id;
    for(var i=0; i<objs.length; i++) {
    	if(objs[i].type.toLowerCase() == "checkbox" )
    	if(objs[i].checked){
    		id=objs[i].value;
    		a=a+1;
      }	
    }
     if(a==1){
     	if(num==2){
	     	document.forms[0].action ="${app}/summary/tawzjMonths.do?method=save&&status=2&&id="+id;
	    	document.forms[0].submit();
	    	return true;
	    }if(num==3){
	     	document.forms[0].action ="${app}/summary/tawzjMonths.do?method=save&&status=3&&id="+id;
	    	document.forms[0].submit();
	    	return true;
	    }
     
     }else{
     	alert('${eoms:a2u("请选择一条信息进行操作！！")}');
     	return false;
     }

	}

	
</script>
<%@ include file="/common/footer_eoms.jsp"%>