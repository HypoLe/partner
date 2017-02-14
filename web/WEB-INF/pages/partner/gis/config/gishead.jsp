<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
	
<html>
<head>
  <title><fmt:message key="webapp.name" /></title>
  <%@ include file="/common/meta.jsp"%>
  <script type="text/javascript" charset="utf-8" src="${app}/scripts/local/zh_CN.js"></script>  
  <script type="text/javascript" charset="utf-8" src="${app}/scripts/base/eoms.js"></script>
  <script type="text/javascript">eoms.appPath = "${app}";</script>
  <link rel="stylesheet" type="text/css" href="${app}/styles/${theme}/theme.css" />
  
  
  <!-- EXT LIBS verson 1.1 -->
  <script type="text/javascript" src="${app}/scripts/ext/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext/ext-all.js"></script>
  <script type="text/javascript" src="${app}/scripts/adapter/ext-ext.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext/source/locale/ext-lang-zh_CN.js"></script>
  <link rel="stylesheet" type="text/css" href="${app}/scripts/ext/resources/css/ext-all.css" />
  <c:if test="${theme ne 'default'}"><link rel="stylesheet" type="text/css" href="${app}/scripts/ext/resources/css/xtheme-gray.css" /></c:if>
  <link rel="stylesheet" type="text/css" href="${app}/styles/${theme}/ext-adpter.css" />
  <!-- EXT LIBS END -->
  
  
  
  <script type="text/javascript" src="${app}/scripts/form/Options.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/combobox.js"></script>
  <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/validation.js"></script>
  
  
  
  
  
  
    <!--[if IE]><link rel="stylesheet" type="text/css" href="${app}/styles/common/ie.css" /><![endif]-->
</head>

<body>
<div id="page">
<%-- Put constants into request scope --%>
<eoms:constants scope="request" />
  <div id="content" class="clearfix">
    <div id="main"><br/><br/>