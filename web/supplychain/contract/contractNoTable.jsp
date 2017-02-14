<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>    
 
		<%
		String webapp = request.getContextPath();
		%>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/screen.css" type="text/css"
			media="screen, print" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/table_style.css"
			type="text/css">
		<style type="text/css">
		table	{ font-size: 12px;
			margin-top: 0em;
			margin-left: 0em;
			margin-right: 0em;
			margin-bottom: 2em;
		}
		</style>
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
			function saveContractNo(no){
				window.parent.saveContractNo(no);
			}
		</script>
	 
		<CENTER>
			<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
				<b>合同号查询<bean:message key="label.list" /> </b>
			</P>
			<display:table name="sessionScope.contractFormCollection" id="row"
				cellspacing="1" cellpadding="1">
				<display:column media="html" title="选择" style="width:50px">
					<input type="radio" name="workAroundChecked"
						onclick="saveContractNo('<bean:write name="row" property="contractNo"/>');">
				</display:column>
				<display:column property="contractNo" title="合同号" style="width:80px" />
				<display:column property="contractName" title="合同名称"
					style="width:80px" />
				<logic:equal value="1" name="contractTypeId">
					<display:column property="companyName" title="合同对方(代维公司)"
						style="width:80px" />
				</logic:equal>
				<logic:equal value="2" name="contractTypeId">
					<display:column property="partB" title="合同对方" style="width:60px" />
				</logic:equal>

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
	</body>
<%@ include file="/common/footer_eoms.jsp"%>