<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%> 
<%
	String webapp = request.getContextPath();
%>

 
		<title>${eoms:a2u('合同项类别列表')}</title>
		 
	<script type="text/javascript">
		<!--
		function confirmDel(subContractTypeId){
		  if( confirm("${eoms:a2u('您好!合同项类别删除后可能会造成该合同项类别相关的数据不可见,您确认要删除吗？')}") ){
		  	window.location='../contract/subContractType.do?method=delete&subContractTypeId=' + subContractTypeId;
		    return true;
		  }
		  else{
		    return false;
		  }
		}
		function gotoAdd(){
			window.location="../contract/subContractType.do?method=add_page";
		}
		//-->
	</script>
 
	
		<CENTER>
			<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
				<b>${eoms:a2u('合同项类别')} 
				</b>
			</P>
	
			<display:table name="requestScope.subContractTypeFormList" id="row" cellspacing="1" cellpadding="1">	
				<display:column property="subContractTypeName" title="${eoms:a2u('合同项类别名称')}"
					style="width:200px" />
				<display:column title="${eoms:a2u('操作')}" style="width:60px">
					<a href="../contract/subContractType.do?method=update_page&subContractTypeId=<bean:write name='row' property='subContractTypeId'/>" />
						 ${eoms:a2u('编辑')} </a>
					<a href="#" onclick="confirmDel(<bean:write name='row' property='subContractTypeId'/>);" >
						  ${eoms:a2u('删除')}</a>
				</display:column>
			</display:table>
			<div align="left">
				<button class="button" onclick="gotoAdd();"  >${eoms:a2u('添加')}</button>
			</div>
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
			<%request.removeAttribute("message");%>
			//-->
			</script>	
		</logic:notEmpty>

<%@ include file="/common/footer_eoms_innerpage.jsp"%>
