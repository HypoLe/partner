<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.boco.eoms.commons.ui.util.UIConstants"%>
<c:choose>
<c:when test="${nodeTemplate=='node-with-other'}"><jsp:include page="json/node-with-other.jsp" flush="true" /></c:when>
<c:when test="${nodeTemplate=='area-dict-subrole'}"><jsp:include page="json/area-dict-subrole.jsp" flush="true" /></c:when>
<c:otherwise><jsp:include page="json/node.jsp" flush="true" /></c:otherwise>
</c:choose>