<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>  
<%@page import="com.boco.supplychain.util.StaticMethod,com.boco.supplychain.util.StaticVariable" %>
 
		<%
		String webapp = request.getContextPath();
		String addLinkPage="";	
		String viewLinkPage="";
		long contractModelId=StaticMethod.nullObject2Long(request.getAttribute("contractModelId"));
		long contractTypeId=StaticMethod.nullObject2Long(request.getAttribute("contractTypeId"));
		if(contractModelId==0  || contractTypeId==0){
			request.setAttribute("message","参数传递丢失,请联系管理员!");
			response.sendRedirect("../failureWithMessage.jsp");
		}
		if(contractTypeId==StaticVariable.CONTRACT_TYPE_PROPERTY){
			 addLinkPage="/contract/propertiesContractPageSave_BaseInfo.do?contractModelId="+contractModelId;
			 viewLinkPage="/contract/propertyContract_view.do";
		}else{
			 addLinkPage="/contract/pageSaveMain.do?contractModelId="+contractModelId;
			  viewLinkPage="/contract/view.do";
			 }
		%>
		<script type="text/javascript">
			function gotoAdd(){
				window.location="<%=webapp%><%=addLinkPage%>";
			}
		</script>
	 

		<CENTER>
			<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
				<b>合同查询<bean:message key="label.list" />
				</b>
			</P>
			<div align="right">
				<button onclick="gotoAdd();" style="word-spacing: 3;width: 2cm;margin-right: 1cm"><bean:message key="label.add" /></button>
			</div>
			
			<display:table name="sessionScope.contractListCollection" id="row"
				cellspacing="1" cellpadding="1">
				<display:column property="contractNo" title="合同号"
					style="width:60px" />
				<display:column property="contractTypeName" title="合同类型"
					style="width:60px" />
				<logic:notEmpty name="row">
					<logic:equal value="2" name="row" property="contractType"> 
						<display:column property="workAroundNames" title="维护工作区"
						style="width:60px" />
					</logic:equal>
				</logic:notEmpty>
				<display:column property="contractName" title="合同名称"
					style="width:60px" />
				<display:column property="strEffactDate" title="生效日期（↓）"
					style="width:100px" />
				<display:column property="strEndDate" title="结束日期"
					style="width:100px" />
				<display:column title="操作" style="width:100px">
				
					<!-- 下面链接转到合同控制页面,用合同类型来表示控制页面跳转的标志,1表示维护合同控制页面,2物业合同控制页面 -->
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

	</body>

<%@ include file="/common/footer_eoms_innerpage.jsp"%>
