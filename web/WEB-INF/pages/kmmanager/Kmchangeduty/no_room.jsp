<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<html:html>

<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="<%=request.getContextPath()%>/template/img/bg001.gif">
   <tr>
      <td width="100%" height="100%" align="center"><table width="743" height="440" background="<%=request.getContextPath()%>/images/bg001.jpg">
			<tr>
				<td align="center">
				  <font style="font-size:14px;color:#CC0000;"><strong>${eoms:a2u('系统检测到您不属于任何机房，无法进行申请换班操作！')}</strong></font>
				</td>
			</tr>
		</table></td>
    </tr>
</table>
</body>
</html:html>