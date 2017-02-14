<%@ page contentType="text/html; charset=GB2312" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>维护合同创建</title>
</head>

<!---------------------------------------页面主体开始--------------------------------------->
<body>

<!------------------标识页面位置------------------>
<p align="left">所在位置－>服务合同管理－>创建XX专业XX类型合同</p>

<!---------------------------------------维护合同创建目录-------------------------------------->
<table width="100%" border="1">
  <tr>
    <td align="center"><a href="/contract/fillInBasicInfo.jsp">录入合同基本信息</a></td>
    <td align="center"><a href="/contract/serviceContractTarget.jsp">选择服务对象</a></td>
  </tr>
  <tr>
    <td align="center"><a href="/contract/serviceContractMoney.jsp">录入合同金额</a></td>
    <td align="center"><a href="/contract/serviceQuantityStandard.jsp">选择服务质量标准</a></td>
  </tr>
  <tr>
    <td align="center"><a href="/contract/payPlan.jsp">选择付款计划</a></td>
    <td align="center"><a href="/contract/attachment.jsp">上传附件</a></td>
  </tr>
</table>

</body>
<!---------------------------------------页面主体结束--------------------------------------->

</html>
