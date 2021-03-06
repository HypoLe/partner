﻿<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="org.acegisecurity.ui.AbstractProcessingFilter"/>
<jsp:directive.page import="org.acegisecurity.LockedException"/>
<jsp:directive.page import="org.acegisecurity.DisabledException"/>
<jsp:directive.page import="org.acegisecurity.BadCredentialsException"/>
<%@ include file="/common/taglibs.jsp"%>
 
<%@ page import ="com.boco.eoms.base.util.UtilMgrLocator"%>
<%@ page import ="com.boco.eoms.base.Constants;"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 
<logic:notEmpty name="edu.yale.its.tp.cas.client.filter.user" scope="session">
	<% response.sendRedirect(request.getContextPath()+"/index.do?method=saveSession");%>
	<jsp:forward page="<c:url value='/index.do?method=saveSession'/>" />
</logic:notEmpty>

<head>
  <title><fmt:message key="webapp.name" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <script type="text/javascript" charset="utf-8" src="${app}/scripts/local/zh_CN.js"></script>  
  <script type="text/javascript" charset="utf-8" src="${app}/scripts/base/eoms.js"></script>
  <script type="text/javascript">eoms.appPath = "${app}";</script>
  <link rel="stylesheet" type="text/css" media="all" href="${app}/styles/${theme}/theme.css" />
  <script type="text/javascript" src="${app}/scripts/util/Cookie.js"></script> 
</head>

<body id="login">
<div id="page">
  <div id="content" class="clearfix">
    <div id="main"><br/><br/>
  
  <%
  	String loginType = UtilMgrLocator.getEOMSAttributes().getLoginType();
  	String acegiStr = Constants.LOGIN_ACEGI;
  	String eomsStr = Constants.LOGIN_EOMS;
  	String loginerrObj = request.getAttribute(Constants.EOMS_SECURITY_EXCEPTION_KEY)==null?"":(String)request.getAttribute(Constants.EOMS_SECURITY_EXCEPTION_KEY);
  	System.out.println(loginerrObj);
  	System.out.println(loginType);
  	if (!(loginerrObj.equals(Constants.EOMS_SECURITY_EXCEPTION_KEY)&&loginerrObj.equals("")))
  		request.setAttribute("loginerror",loginerrObj);
  	System.out.println(request.getAttribute("loginerror"));
  	request.setAttribute("loginType",loginType);
  	request.setAttribute("acegiStr",acegiStr);
  	request.setAttribute("eomsStr",eomsStr);
   %>

  <c:choose>
	  <c:when test="${loginType==acegiStr}">
	  <form method="post" id="loginForm" action="<c:url value="/j_security_check"/>" onsubmit="saveUsername(this);">
	  </c:when>
	  <c:when test="${loginType==eomsStr}">
	  <form method="post" id="loginForm" action="<c:url value='/index.do?method=saveSession'/>" onsubmit="saveUsername(this);">
	  </c:when>
	  <c:otherwise>
	  		<script type="text/javascript"> 
	  			alert("aaaaaaaaaaa");
	  		</script>
	  </c:otherwise>
  </c:choose>
    
  <div class="login-box">

	<div class="login-form">
	  <div class="login-form-content">
        <ul>
        <c:choose>
	  		<c:when test="${loginType==acegiStr}">
        		<c:if test="${param.error != null}">
		          <li class="error">
		            <img src="<c:url value="/images/icons/warning.gif"/>" alt="<fmt:message key="icon.warning"/>" class="icon" />
		            <%
		                Exception exceptionAcegi = (Exception)session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY);
		                if(exceptionAcegi instanceof BadCredentialsException){
		            %>
		            	 <fmt:message key="errors.password.mismatch"/>
		            <%
		                }
		            	else if(exceptionAcegi instanceof LockedException){
		            %>
		            		<fmt:message key="errors.account.locked"/>
		            <%
		            	}else if(exceptionAcegi instanceof DisabledException){
		            %>
		            		<fmt:message key="errors.account.expired"/>
		            <%
		            	}
		             %>
		       
		          <!--${sessionScope.ACEGI_SECURITY_LAST_EXCEPTION.message}-->
		          </li>
        		</c:if>
        	</c:when>
        	<c:when test="${loginType==eomsStr}">
	  			<c:if test="${loginerror != ''}">
		          <li class="error">
		            <img src="<c:url value="/images/icons/warning.gif"/>" alt="<fmt:message key="icon.warning"/>" class="icon" />
		            <%
		                String exceptionEoms = (String)request.getAttribute("loginerror");
		                if(exceptionEoms.equals("BadCredentialsException")){
		            %>
		            	 <fmt:message key="errors.password.mismatch"/>
		            <%
		                }
		            	else if(exceptionEoms.equals("LockedException")){
		            %>
		            		<fmt:message key="errors.account.locked"/>
		            <%
		            	}else if(exceptionEoms.equals("DisabledException")){
		            %>
		            		<fmt:message key="errors.account.expired"/>
		            <%
		            	}
		             %>
		       
		          <!--${sessionScope.ACEGI_SECURITY_LAST_EXCEPTION.message}-->
		          </li>
        		</c:if>
	 		</c:when>
        </c:choose>
          <li>
            <label for="j_username" class="desc">
            <!--<fmt:message key="label.username"/>-->用户名 <span class="req">*</span>
            </label>
            <input type="text" class="text medium" name="j_username" id="j_username" tabindex="1" />
          </li>

          <li>
            <label for="j_password" class="desc">
            <fmt:message key="label.password"/> <span class="req">*</span>
            </label>
            <input type="password" class="text medium" name="j_password" id="j_password" tabindex="2" />
          </li>

          <li><br/>
            <input type="submit" class="btn" name="login" value="<fmt:message key='button.login'/>" tabindex="3" />
          
          </li>
          
         
        </ul>
      </div>
	</div>
  </div>
  </form>
<script type="text/javascript">  
	var c = eoms.util.Cookie;
	if (c.get("username") != null) {
		$("j_username").value = c.get("username");
		$("j_password").focus();
    } else {
        $("j_username").focus();
    }
    
    function saveUsername(frm) {
		c.set("username",frm.j_username.value,30);
    }
</script>
<%@ include file="/common/footer_eoms.jsp"%>