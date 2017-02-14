<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE struts-config PUBLIC "-//Apache Software Foundation//DTD Struts Configuration 1.2//EN" "http://jakarta.apache.org/struts/dtds/struts-config_1_2.dtd">

<struts-config>
	<form-beans>


	</form-beans>


	<!-- ========== Global Exceptions Definitions =================================== -->
	<global-exceptions>
		<exception type="java.lang.Exception" key="errors.general"
			handler="com.boco.eoms.base.webapp.action.ActionExceptionHandler" />
	</global-exceptions>

	<!-- ========== Global Forward Definitions =================================== -->
	<global-forwards>
		<forward name="success" path="/common/success.jsp"
			redirect="false" contextRelative="true" />
		<forward name="nopriv" path="/common/nopriv.jsp"
			redirect="false" contextRelative="true" />
		<forward name="fail" path="/common/failure.jsp" redirect="false"
			contextRelative="true" />
	</global-forwards>

	<!-- ========== Action Mapping Definitions =================================== -->

	<action-mappings>
		
		
		<action path="/arcGis" 
			type="com.boco.eoms.arcGis.webapp.action.ArcGisAction" 
			 scope="request" input="mainMenu"
			parameter="method" unknown="false"  validate="false">
			<forward name="goToArcGis" path="/WEB-INF/pages/arcGis/sc/index_arcGis.jsp" 
			redirect="false" contextRelative="true" />
			<forward name="goToArcGisMap" path="/WEB-INF/pages/arcGis/sc/arcGisMap.jsp" 
			redirect="false" contextRelative="true" />
			<forward name="list" 
			    path="/WEB-INF/pages/arcGis/PnrResConfigList.jsp"
			    redirect="false" contextRelative="true"></forward>
			<forward name="gisInformation" 
			    path="/WEB-INF/pages/arcGis/gisInfromationList.jsp"
			    redirect="false" contextRelative="true"></forward>
			    
			<forward name="countryCountDetailForArcGis" 
			    path="/WEB-INF/pages/arcGis/acrgisCountryCountList.jsp"
			    redirect="false" contextRelative="true"></forward>
		    <forward name="carList" 
			    path="/WEB-INF/pages/arcGis/carList.jsp"
			    redirect="false" contextRelative="true"></forward>
		    <forward name="userList" 
			    path="/WEB-INF/pages/arcGis/companyUserList.jsp"
			    redirect="false" contextRelative="true"></forward>
		     <forward name="oilList" 
			    path="/WEB-INF/pages/arcGis/sc/oilList.jsp"
			    redirect="false" contextRelative="true"></forward>
		    <forward name="oilEdit" 
			    path="/WEB-INF/pages/arcGis/sc/oilEdit.jsp"
			    redirect="false" contextRelative="true"></forward>
		    <forward name="transLineList" 
			    path="/WEB-INF/pages/arcGis/sc/transLineList.jsp"
			    redirect="false" contextRelative="true"></forward>
			
		</action>
		
	</action-mappings>
	<controller nocache="true" inputForward="true" maxFileSize="2M" />
	<message-resources parameter="config/ApplicationResources" />
	<!-- ========== Validator plugin ===================  -->
	<!-- 
		<plug-in className="org.apache.struts.validator.ValidatorPlugIn">
		<set-property property="pathnames"
		value="/WEB-INF/validator-rules.xml,
		/WEB-INF/validator-rules-custom.xml,
		/WEB-INF/validation.xml" />
		</plug-in>
	-->
</struts-config>