
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/ApplicationResources-parter-baseinfo-lanmetermgr">
<content tag="heading"><fmt:message key="lanmetermgrForm.heading.update"/></content>
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
	            alert("<bean:message key='lanmetermgrForm.tips.nochoose'/>"); 
             	return false;
             }
             
           }else{
             return false;
           }
    }	
	</script>
<c:set var="buttons">
</c:set>
<html:form action="/lanmetermgrs.do" method="post" styleId="lanmetermgrForm" >

<!-- <c:out value="${buttons}" escapeXml="false"/> -->
<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/ApplicationResources-parter-baseinfo-lanmetermgr">
<display:table name="lanmetermgrList" cellspacing="0" cellpadding="0"
    id="lanmetermgrList" pagesize="${pageSize }" class="table lanmetermgrList"
    export="false" requestURI="lanmetermgrs.do?method=list&pageSize=${pageSize }" 
     sort="external" partialList="true" size="resultSize"
     decorator="com.boco.eoms.parter.baseinfo.lanmetermgr.displaytag.support.LanmetermgrListDisplaytagDecorator" >
	<logic:notEmpty name="Nums">
 	<display:column property="num" titleKey="lanmetermgrForm.Num"/>
 	</logic:notEmpty>
 	
	<logic:notEmpty name="lanmetermgrPriv">
	<display:column property="id" title="<input type='checkbox' onclick='javascript:choose();'/>" />
	</logic:notEmpty>
	
	<display:column titleKey="lanmetermgrForm.maintenUnitId" href="${app}/lanmetermgr/lanmetermgrs.do?method=toUpdateEdit" paramId="id" paramProperty="id" >
		<eoms:id2nameDB id="${lanmetermgrList.maintenUnitId}"
			beanId="tawSystemDeptDao" />
	</display:column>


    <display:column property="OTDRCou" sortable="true" headerClass="sortable"
         titleKey="lanmetermgrForm.OTDRCou"/>

    <display:column property="OTDRRem" sortable="true" headerClass="sortable"
         titleKey="lanmetermgrForm.OTDRRem"/>

    <display:column property="lighCableFinderCou" sortable="true" headerClass="sortable"
         titleKey="lanmetermgrForm.lighCableFinderCou"/>

    <display:column property="lighCableFinderRem" sortable="true" headerClass="sortable"
         titleKey="lanmetermgrForm.lighCableFinderRem"/>


    <display:column property="groundFinderCou" sortable="true" headerClass="sortable"
         titleKey="lanmetermgrForm.groundFinderCou"/>

    <display:column property="groundFinderRem" sortable="true" headerClass="sortable"
         titleKey="lanmetermgrForm.groundFinderRem"/>

   

    <display:setProperty name="paging.banner.item_name" value="lanmetermgr"/>
    <display:setProperty name="paging.banner.items_name" value="lanmetermgrs"/>
</display:table>
</fmt:bundle> 
<c:out value="${buttons}" escapeXml="false"/>

</html:form>


<%@ include file="/common/footer_eoms.jsp"%>

