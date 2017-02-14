<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="JavaScript">
function WinClose() {
	window.close();
	}
</script>

<%
     String downloadFileUrl=(String)request.getParameter("url");
%> 
            <table cellpadding="0" cellspacing="0" border="0" width="100%">
           <caption>
                下载导出EXCEL表格
           </caption>
 
    <tr >
	  <td align=center >
         <a   href="<%=request.getContextPath()+downloadFileUrl%>">下载</a>
	</td>
</tr>
<tr class="tr_show">
	<td   align=center>
		<input type="button" value="返回" onclick="javascript:window.history.back();" class="clsbtn2">
	</td>
</tr>
<%@ include file="/common/footer_eoms.jsp"%>
