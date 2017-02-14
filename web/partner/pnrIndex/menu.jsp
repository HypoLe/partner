<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:forEach var='pnrmenu' items='${sessionScope.pnrmenu}'><c:if test="${fn:startsWith(pnrmenu.url, ':')}"><c:set target="${pnrmenu}" property="url" value="${scheme}://${serverName}${pnrmenu.url}" /></c:if></c:forEach>
<div style="text-align:left;">
<ul id="menu">
	<br/>
  <c:forEach var='pnrmenu' items='${sessionScope.pnrmenu}' varStatus="status">
<a id="mi-${pnrmenu.itemId}" href="<c:url value='${pnrmenu.url}' />" target="pnrFrame" ${status.first?"class=cur":""}><span>${pnrmenu.text}</span></a>
    </c:forEach>
</ul>
</div>
