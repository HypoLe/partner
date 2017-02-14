<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%@page import="com.boco.supplychain.util.StaticVariable"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/supplychain.tld" prefix="supplychain"%>
<%
	String webapp = request.getContextPath();
	String contractNo = StaticMethod.nullObject2String(request.getParameter("contractNo"));
	int contractTypeId = StaticMethod.null2int(request.getParameter("contractTypeId"));
	request.setAttribute("contractTypeId",contractTypeId+"");  //放入request域，用于logic:equal比较
%>
<supplychain:contactType></supplychain:contactType>
<html>
	<head>
		<title>服务对象树</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<style>
  #wrap_div{ width:100%; height:auto;}
  #left_div{ float:left; width:30%;}
  #right_div{float:right; width:70%;}
  .clear{ clear:both;}
</style>
		<link rel="stylesheet"
			href="<%=webapp%>/css/table_style.css"
			type="text/css">
		
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/xtree/xtree.css">
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/xtree/xtree.js"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/js/calendar2.js"></script>
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/dojo/dojo.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dojo/src/io.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dojo/src/string.js"></script>
		<script type="text/javascript">
			djConfig={
				isDebug:false,
				bindEncoding:"utf-8"
			};
			
			dojo.require("dojo.io.BrowserIO");
		</script>
		<script type="text/javascript">
			var checkedflag=false;
			function saveContractNo(no){
				checkedflag=true;
				window.opener.resetContractNo(no);
			}	
			//确定时检查是否已选则了合同号
			function checkData(){
			  if(!checkedflag){
			    alert("请选择一个合同号!");
			    return false;
			  }
			  window.close();
			}
			function init(){	
				document.forms[0].submit();
			}
			dojo.addOnLoad(init);
		</script>
<supplychain:specialty type="all"></supplychain:specialty>
	</head>
	<body>
		<table width="100%" height="100%-32" border="0" cellspacing="1"
			cellpadding="5" bgcolor="#BEDEF8">
			<tr height="30">
				<td nowrap class="small" bgcolor="#E2F1FC" valign="absmiddle">
					&nbsp;请输入查询条件
				</td>
			</tr>
			<tr>
				<td bgcolor="#FFFFFF" valign="top" align="center">
					<html:form  method="post" action="/contract/queryList.do?forward=foward4selectContractNo" target="contractNoTableFrm">
						<table class="table_show" width="100%" cellspacing="1"
							cellpadding="1" border="0">
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									专业
								</td>
								<td width="35%" height="25">
									<html:select property="specialtyId" style="width: 4cm;">
										<html:options collection="specialtyOptions" property="value"
											labelProperty="label" styleClass="clstext" />
									</html:select>
								</td>
								<!-- 服务合同 -->
								<logic:equal value="1" name="contractTypeId">
									<td width="15%" height="25" class="clsfth">
										供应商名称
									</td>
									<td width="35%" height="25">
										<html:text property="companyNamePartB" styleClass="clstext" size="20"
											maxlength="20" />
									</td>
									<html:hidden property="contractTypeId" value="1"/>
								</logic:equal>
								<logic:equal value="2" name="contractTypeId">
									<td width="15%" height="25" class="clsfth">
										合同乙方
									</td>
									<td width="35%" height="25">
										<html:text property="partB" styleClass="clstext" size="20"
											maxlength="20" />
									</td>
									<html:hidden property="contractTypeId" value="2"/>
								</logic:equal>
								<logic:equal value="0" name="contractTypeId">
									<td width="15%" height="25" class="clsfth">
										合同类型
									</td>
									<td width="35%" height="25">
										<html:select property="contractTypeId" style="width: 4cm;">
										<html:options collection="contractTypeOptions" property="value"
											labelProperty="label" styleClass="clstext" />
									</html:select>
									</td>
								</logic:equal>

							</tr>
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									创建日期(下限)
								</td>
								<td width="35%" height="25">
									<html:text property="strCreateDateLower" size="20"
										onfocus="calendar()" readonly="true" styleClass="clstext" />
								</td>
								<td width="15%" height="25" class="clsfth">
									创建日期(上限)
								</td>
								<td width="35%" height="25">
									<html:text property="strCreateDateUpper" size="20"
										onfocus="calendar();" readonly="true" styleClass="clstext" />
								</td>
							</tr>
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									合同名称
								</td>
								<td width="35%" height="25" >
									<html:text property="contractName" ></html:text>
								</td>
								<td width="15%" height="25" class="clsfth">
									合同号
								</td>
								<td width="35%" height="25">
									<html:text property="contractNo" value="<%=contractNo%>"></html:text>
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3" cellpadding="3">
							<tr>
								<td align="center">
									<a href="javascript://nop/" class="contentLink"
										onclick="document.forms[0].submit();">查询</a>
								</td>
							</tr>
						</table>
					</html:form>
				</td>
			</tr>
			<tr id="workAroundInfoView" height="80%">
				<td bgcolor="#FFFFFF">
					<iframe name="contractNoTableFrm" src="contractNoTable.jsp" width="100%" height="100%"
						scrolling="no" frameborder="0"></iframe>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="buttonBanner" height="30">
					<table cellpadding="5" width="100%">
						<tr>
							<td align="center">
								<input type="button" class="clsbtn2" value="确定"
									onclick="return checkData();">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="clsbtn2" value="取消"
									onclick="window.close();">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
