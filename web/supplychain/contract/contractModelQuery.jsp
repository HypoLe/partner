<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%> 
<supplychain:specialty type="all"></supplychain:specialty>
<supplychain:contactType type="all"></supplychain:contactType>
<supplychain:dictDictRegion type="all"></supplychain:dictDictRegion>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<title>合同模板列表</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">
		<script language="javascript" src="<%=request.getContextPath()%>/js/calendar2.js"></script>
	</head>
	<body>
		<center>
			<html:form method="post" action="/contractModel/list">
				<table border="0" width="50%" cellspacing="0">
					<tr>
						<td width="100%" class="table_title" align="center">
							<b>合同模板查询</b>
						</td>
					</tr>
				</table>
				<table class="table_show" cellspacing="1" cellpadding="1" width="50%">
					<tr class="tr_show">
						<td width="30%" height="25" class="clsfth">
							专业
						</td>
						<td width="70%" height="25">
							<html:select property="specialtyId" style="width: 4cm;" >
								<html:options collection="specialtyOptions" property="value" labelProperty="label" styleClass="clstext" />
							</html:select>
						</td>
					
						<td width="30%" height="25" class="clsfth">
							合同类型
						</td>
						<td width="70%" height="25">
							<html:select property="contractType" style="width: 4cm;" >
								<html:options collection="contractTypeOptions" property="value" labelProperty="label" styleClass="clstext" />
							</html:select>
						</td>
					
						<td width="30%" height="25" class="clsfth">
							模板名称
						</td>
						<td width="70%" height="25">
							<html:text property="modelName" styleClass="clstext" size="20" maxlength="20" />
						</td>
						<td width="30%" height="25" class="clsfth">
							创建人
						</td>
						<td width="70%" height="25">
							<html:text property="creator" styleClass="clstext" size="20" maxlength="20" />
						</td>
						</tr>
						<tr class="tr_show">
						<td width="30%" height="25" class="clsfth">
							区域关系
						</td>			
						<td width="70%" height="25">
							<logic:equal value="true" name="queryFormFieldReadOnly">
								<html:hidden  property="regionId"/>
								<bean:write name="regionName"/>
							</logic:equal>
							<logic:equal value="false" name="queryFormFieldReadOnly">
								<html:select property="regionId" style="width:4cm" 
									styleClass="clstext" >
									<html:options collection="simpleDictRegionOptions"
										property="value" labelProperty="label" />
								</html:select>
							</logic:equal>
							

						</td>
					
						<td width="30%" height="25" class="clsfth">
							创建日期
						</td>
						<td width="70%" height="25" colspan="3">
							从<html:text property="beginDate" size="20" onfocus="calendar()" readonly="true" styleClass="clstext" />
						到
							<html:text property="endDate" size="20" onfocus="calendar();" readonly="true" styleClass="clstext" />
						</td>
						<td colspan="2">
							<p align="left">
								<html:submit styleClass="clsbtn2" value="查询" />
								&nbsp;&nbsp;
								<html:reset styleClass="clsbtn2" value="重置" />
							</p>
						</td>
					</tr>
				</table>				
			</html:form>
		</center>
	</body>
	

<%@ include file="/common/footer_eoms.jsp"%>
