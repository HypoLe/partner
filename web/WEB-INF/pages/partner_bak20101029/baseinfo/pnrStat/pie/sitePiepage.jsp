<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<table width="10%">
	<caption>
	<div class="header center">合作伙伴市场份额</div>
	<html:form action="/pnrStats.do?method=getSitePie" method="post" >
	<select name="area" id="area" >
						<option value="">
							全省
						</option>
						<logic:iterate id="regionBuffer" name="regionBuffer">
						<c:if test="${regionBuffer.areaname != '省公司'}">
							<option value="${regionBuffer.areaid}">
								${regionBuffer.areaname}
							</option>
						</c:if>	
						</logic:iterate>
						
		</select>
	<input type="submit" class="btn" value="查看" />	
	</html:form>	
	</caption>
	<tr>
		<td class="content">
			<img src="${filePathProvince}"/>
		</td>
</table>
<script type="text/javascript" >
	document.getElementById("area").value="${area}";
</script>		
<%@ include file="/common/footer_eoms.jsp"%>