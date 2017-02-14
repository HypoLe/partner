<%@ page language="java" pageEncoding="UTF-8" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose><c:when test="${nodeTemplate=='node-with-leader'}"><jsp:include page="json/node-with-leader.jsp" flush="true" /></c:when>
<c:when test="${param.tpl=='view-query'}"><jsp:include page="json/view-query.jsp" flush="true" /></c:when>
<c:otherwise><jsp:include page="json/node.jsp" flush="true" /></c:otherwise></c:choose>