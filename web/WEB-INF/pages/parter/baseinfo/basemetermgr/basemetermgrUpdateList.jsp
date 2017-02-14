
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/ApplicationResources-parter-baseinfo-basemetermgr">
<content tag="heading"><fmt:message key="basemetermgrList.heading.update"/></content>
</fmt:bundle> 
<script type="text/javascript">
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
           if(confirm("<bean:message key='common.tips.delete'/>")){
           	 var objs = document.getElementsByName("ids");
           	 for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			if(objs[i].checked){
      				flag=true;
      			}
			 }
			 if(flag){
             	return true;
             }else {
	            alert("<bean:message key='basemetermgrForm.tips.nochoose'/>"); 
             	return false;
             }
             
           }else{
             return false;
           }
    }
	</script>
<c:set var="buttons">
</c:set>
<html:form action="/basemetermgrs.do" method="post" styleId="basemetermgrForm" >
<!-- <c:out value="${buttons}" escapeXml="false"/> -->
<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/ApplicationResources-parter-baseinfo-basemetermgr">
<display:table name="basemetermgrList" cellspacing="0" cellpadding="0"
    id="basemetermgrList" pagesize="${pageSize }" class="table basemetermgrList"
    export="false" requestURI="basemetermgrs.do?method=list&pageSize=${pageSize }"  sort="external" partialList="true" size="resultSize"
    decorator="com.boco.eoms.parter.baseinfo.basemetermgr.displaytag.support.BasemetermgrListDisplaytagDecorator">
    
 	 <logic:notEmpty name="Nums">
 	<display:column property="num" titleKey="basemetermgrForm.Num"/>
 	</logic:notEmpty>
 	
	<logic:notEmpty name="basemetermgrPriv">
	<display:column property="id" title="<input type='checkbox' onclick='javascript:choose();'/>" />
	</logic:notEmpty>
	

    <display:column titleKey="basemetermgrForm.maintenUnitIdVal" href="${app}/basemetermgr/basemetermgrs.do?method=toUpdateEdit" paramId="id" paramProperty="id" >
		<eoms:id2nameDB id="${basemetermgrList.maintenUnitIdVal}"
			beanId="tawSystemDeptDao" />
	</display:column>

    <display:column property="maintenUnitIdRem" sortable="true" headerClass="sortable"
         titleKey="basemetermgrForm.maintenUnitIdRem"/>

    <display:column property="desktopComputerVal" sortable="true" headerClass="sortable"
         titleKey="basemetermgrForm.desktopComputerVal"/>

    <display:column property="desktopComputerRem" sortable="true" headerClass="sortable"
         titleKey="basemetermgrForm.desktopComputerRem"/>

    <display:column property="faxVal" sortable="true" headerClass="sortable"
         titleKey="basemetermgrForm.faxVal"/>

    <display:column property="faxRem" sortable="true" headerClass="sortable"
         titleKey="basemetermgrForm.faxRem"/>

    <display:column property="fixedPhoneVal" sortable="true" headerClass="sortable"
         titleKey="basemetermgrForm.fixedPhoneVal"/>

    <display:column property="fixedPhoneRem" sortable="true" headerClass="sortable"
         titleKey="basemetermgrForm.fixedPhoneRem"/>

    <display:column property="moveTestPhoneVal" sortable="true" headerClass="sortable"
         titleKey="basemetermgrForm.moveTestPhoneVal"/>

  
    <display:column property="remark" sortable="true" headerClass="sortable"
         titleKey="basemetermgrForm.remark"/>

    <display:setProperty name="paging.banner.item_name" value="basemetermgr"/>
    <display:setProperty name="paging.banner.items_name" value="basemetermgrs"/>
</display:table>
</fmt:bundle> 
<c:out value="${buttons}" escapeXml="false"/>

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>

