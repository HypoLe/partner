 
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%@page import="com.boco.supplychain.util.StaticVariable"%>
		<%
		String webapp = request.getContextPath();
		%>
 

		<CENTER>
			<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
				<b><bean:write name="contractTypeName"/>查询 
				</b>
			</P>
			<display:table name="sessionScope.contractFormCollection" id="row"
				cellspacing="1" cellpadding="1">
				<logic:equal value="1" name="contractTypeId">
					<display:column property="specialtyName" title="专业"
						style="width:60px" />
				</logic:equal>
				<logic:equal value="2" name="contractTypeId">
				<display:column property="workAroundNames" title="工作区"
					style="width:60px" />
					</logic:equal>
				<display:column property="contractNo" title="合同号"
					style="width:60px" />
				<display:column property="contractName" title="合同名称"
					style="width:60px" />
					<logic:equal value="1" name="contractTypeId">
				<display:column property="companyName" title="合同对方(代维公司)"
					style="width:80px" />
					</logic:equal>
					<logic:equal value="2" name="contractTypeId">
						<display:column property="partB" title="合同对方"
				style="width:60px" />
					</logic:equal>
				<display:column property="partB" title="合同描述"
					style="width:60px" />
				<display:column property="strEffactDate" title="生效日期（↓）"
					style="width:100px" />
				<display:column property="strEndDate" title="结束日期"
					style="width:100px" />
				<display:column title="操作" style="width:100px">
					<a
						href="<%=webapp%>/contract/controller.do?contractId=<bean:write name='row' property='contractId'/>&controller_flag=<bean:write name='row' property='contractType'/>&view_type=viewView" />
						<img src="<%=webapp%>/images/bottom/an_xs.gif" border="0"
							alt="<bean:message key="label.view"/>"> </a>
					<a
						href="<%=webapp%>/contract/controller.do?contractId=<bean:write name='row' property='contractId'/>&controller_flag=<bean:write name='row' property='contractType'/>&view_type=modifyView" />
						<img src="<%=webapp%>/images/bottom/an_bj.gif" border="0"
							alt="<bean:message key="label.edit"/>"> </a>
					<a
						href="<%=webapp%>/contract/controller.do?contractId=<bean:write name='row' property='contractId'/>&controller_flag=<bean:write name='row' property='contractType'/>&view_type=deleteView" />
						<img src="<%=webapp%>/images/bottom/an_sc.gif" border="0"
							alt="<bean:message key="label.remove"/>"> </a>
				</display:column>
			</display:table>
			<TABLE width="98%" cellpadding="1" cellspacing="1" border="0"
				class="table_show" align="center">
				<TR class="tr_show">
					<td class="td_right" width="100%">
						<bean:write name="pagerHeader" scope="session" filter="false" />
					</td>
				</TR>
			</TABLE>
		</CENTER>
		<logic:notEmpty name="message">
			<script type="text/javascript">
			<!--
			alert('<bean:write name="message"/>');
			//-->
			</script>
		</logic:notEmpty>
 
