<%@ page language="java" import="java.util.*,com.boco.supplychain.util.StaticMethod,com.boco.supplychain.hibernate.QueryCondition,com.boco.supplychain.contract.webapp.form.ContractListForm" pageEncoding="GB2312"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/jstl-core.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.boco.supplychain.util.StaticMethod,com.boco.supplychain.util.StaticVariable" %>
<%@taglib uri="/WEB-INF/supplychain.tld" prefix="supplychain"%>
 <supplychain:dictDictRegion type="all"></supplychain:dictDictRegion> 
<html>
	<head>
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
		background-color: #C6DAE1;
		}
		</style>
		<%
		String path = request.getContextPath();		
		%>
		
	
	<script type="text/javascript" src="<%=path%>/js/calendar2.js"></script>
	<script type="text/javascript" src="<%=path%>/js/form/checkform.js"></script>
	
	<script language="javascript"
			src="<%=path%>/js/dojo/dojo.js"></script>
		<script type="text/javascript"
			src="<%=path%>/js/dojo/src/io.js"></script>
		<script type="text/javascript"
			src="<%=path%>/js/dojo/src/string.js"></script>
	
		<script type="text/javascript">
			djConfig={
				isDebug:false,
				bindEncoding:"utf-8"
			};
			
			dojo.require("dojo.io.BrowserIO");
		</script>
		<script type="text/javascript">
			function init(){
				
				<%
					String addLinkPage="";
					String selectCityId = "0";
					long contractModelId=0;
					long contractTypeId=0;
					
					String isQuery = StaticMethod.null2String(request.getParameter("isQuery"));
					if (isQuery.equalsIgnoreCase("true")) {
								
						Map params = ((QueryCondition)request.getSession().getAttribute("queryCondition")).getQueryParamsStr();	
							if (params.containsKey("cityId")) {
								//保存所选中的县市
								selectCityId = params.get("cityId").toString();
							}
						for (Iterator i=params.entrySet().iterator(); i.hasNext(); ) {
							Map.Entry entry = (Map.Entry) i.next();
			%>
						document.getElementById('<%=(String) entry.getKey()%>').value='<%=(String) entry.getValue()%>'
			<%
						}
						contractModelId=(new Long(params.get("contractModelId").toString())).longValue();
						
						contractTypeId=(new Long(params.get("contractTypeId").toString())).longValue();
						
					} else {
						contractModelId=StaticMethod.nullObject2Long(request.getAttribute("contractModelId"));
						contractTypeId=StaticMethod.nullObject2Long(request.getAttribute("contractTypeId"));
						%>
						document.getElementById('contractModelId').value='<%=contractModelId%>'
						document.getElementById('contractTypeId').value='<%=contractTypeId%>'
						<%
					}
					
					if(contractModelId==0  || contractTypeId==0){
						request.setAttribute("message","参数传递丢失,请联系管理员!");
						//response.sendRedirect(webapp+"/failureWithMessage.jsp");
					}
					//新增合同
					if(contractTypeId==StaticVariable.CONTRACT_TYPE_PROPERTY){
						 addLinkPage="/contract/propertiesContractPageSave_BaseInfo.do?contractModelId="+contractModelId;
					}else{
						 addLinkPage="/contract/pageSaveMain.do?contractModelId="+contractModelId;
					}
					
				%>
				updateInfo(document.forms[0].regionId.value);
			}
			
			
			
			/**根据地市变化动态更新县市变化*/
			function updateInfo(regionId){
					var request={
						url:"<%=path%>/common/listCityByRegionId.do?regionId="+regionId,
						mimetype:"text/plain",
						method:"get",
						transport: "XMLHTTPTransport",
						sync:false,
						preventCache:true,
						encoding:"utf-8",
						load: function(type,data,evt){
							var cityArr=eval(data);	
							var select=document.getElementsByName("cityId")[0];
							select.options.length=0;
							var optionTop=new Option("--县市--");
							optionTop.value=0;
							select.options.add(optionTop);
							for(var i=0;i<cityArr.length;i++){
								var option=new Option();
								option.text=cityArr[i].cityName;
								option.value=cityArr[i].cityId;
								select.options[select.options.length]=option;
							}
							document.getElementById('cityId').value=<%=selectCityId%>
						},
						error:function(type,error){
							//alert(error.message);//FIXME 
						}
					};
					dojo.io.bind(request);
		
			}
			
			dojo.addOnLoad(init);
			
			/**表单验证**/
			function checkForm(){
				var siteno = document.getElementById("siteno");
				var si = document.getElementById("si");
				var lac = document.getElementById("lac");
				if(numCheckAllowNull(siteno,'基站编号只能是数字！')
				 &&	numCheckAllowNull(si,'si站号只能是数字！')
				 &&	numCheckAllowNull(lac,'lac只能是数字！')
				 )	{
				 	return true;
				}
				else{
					return false;
				}
			}
			/**确认删除**/
			function confirmDel(id,name){
			  if( confirm("物业合同《"+name+"》删除后不能恢复，您确认要删除吗？") ){
			  	window.location='<%=path%>/contract/property/delete.do?contractId=' + id;
			    return true;
			  }
			  else{
			    return false;
			  }
			}
		</script>
		
		<script type="text/javascript">
			function gotoAdd(){
				window.location="<%=path%><%=addLinkPage%>";
			}
		</script>
	</head>
	<body>

		<CENTER>
			<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
				<b>合同查询<bean:message key="label.list" />
				</b>
			</P>
			<div>
			<form name="condition" action="/supplychain/contract/property/list.do" method="post" onsubmit="return checkForm()">
				<input type="hidden" name="isQuery" value="true" />
				<input type="hidden" name="contractModelId"/>
				<input type="hidden" name="contractTypeId"/>
				<table align="center" border="0" cellpadding="2" cellspacing="0">
				<tr height="35">
					<td align="center">
							地市
					</td>
					<td>
					<logic:notEmpty  name="regionId">
						<input type="hidden" name="regionId" value='<bean:write name="regionId"/>' />
						<input type="text" name="regionName" value='<bean:write name="regionName"/>' class="clstext" size="10" readonly="true" />
					</logic:notEmpty>
					<logic:empty name="regionId">
						<select id="regionId" name="regionId" style="width: 2.5cm;"
						>
						<logic:iterate id="row" name="simpleDictRegionOptions">
							<option value='<bean:write name="row" property="value"/>'><bean:write name="row" property="label"/></option>
						</logic:iterate>
						</select>
					</logic:empty>
					</td>
					<td align="center">合同编号</td>					
					<td align="left">
						<input type="text" id="contractNo" name="contractNo" class="clstext" size="20"/>
					</td>
					
					<td align="center">合同名称</td>
					<td>
					<input type="text" id="contractName" name="contractName" class="clstext" size="35"/>
					</td>	
						
					<td>
						<input type="submit" class="clsbtn2" name="submit" value="查询" />
						<input type="reset" class="clsbtn2" name="reset" value="重置" />
						<input type="button" onclick="gotoAdd();" class="clsbtn2" value="<bean:message key="label.add" />合同" />
					</td>		
				</tr>
			</table>
			</form>
			</div>
			<logic:notEmpty name="contractFormList">
			<display:table name="contractFormList" id="row"	cellspacing="1" cellpadding="1">
				<display:column property="contractNo" title="合同号"	style="word-break: keep-all;white-space:normal;" />
				<display:column title="合同名称"	style="word-break: keep-all;white-space:normal;">
					<a title="查看合同内容" href="<%=path%>/contract/controller.do?contractId=<bean:write name='row' property='contractId'/>&controller_flag=<bean:write name='row' property='contractType'/>&view_type=viewView" />
						<bean:write name="row" property="contractName"/>
					</a>
				</display:column>
				<logic:notEmpty name="row">
					<logic:equal value="2" name="row" property="contractType"> 
						<display:column property="workAroundNames" title="维护工作区"
						style="width:150px" />
					</logic:equal>
				</logic:notEmpty>
				<c:set var="rowId" scope="request" value="${row_rowNum -1}" />	
				<c:set var="subContractList" scope="request" value="${contractFormList[rowId].subContractList}" />	
				<display:column title="合同子项" style="word-break: keep-all;white-space:normal;width:15%">
					<display:table name="subContractList" id="child${rowId}" class="simple" cellspacing="0" cellpadding="0" style="margin: 8 0 8 0;width:80%">	
						<display:column property="subContractType.subContractTypeName" title=""
							style="word-break: keep-all;white-space:normal;" />	
							<display:column title="" style="word-break: keep-all;white-space:normal;width:30%;text-align:center" >	
								<a href="<%=path%>/pay/listSubPropertiesPayRecord.do?isQuery=true&subId=<bean:write name='child${rowId}' property='subId'/>" />
									付款记录
								</a><%--&nbsp;|&nbsp;
								<a href="<%=path%>/contract/controller.do?contractId=<bean:write name='row' property='contractId'/>&controller_flag=<bean:write name='row' property='contractType'/>&view_type=modifyView" />
								编辑
								</a>
							--%></display:column>
					</display:table>
				</display:column>
				<display:column property="remark" title="物业问题"
						style="width:250px" />
				<display:column title="操作" style="width:100px;text-align:center">					
					<!-- 下面链接转到合同控制页面,用合同类型来表示控制页面跳转的标志,1表示维护合同控制页面,2物业合同控制页面 -->					
					<a href="<%=path%>/contract/property/edit.do?contractId=<bean:write name='row' property='contractId'/>">
						<bean:message key="label.edit"/></a>&nbsp;|&nbsp;
					<a href="#" onclick="confirmDel(<bean:write name='row' property='contractId'/>,'<bean:write name='row' property='contractName'/>')"/>
						<bean:message key="label.remove"/></a>
				</display:column>
			</display:table>
			</logic:notEmpty>
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
</html>
