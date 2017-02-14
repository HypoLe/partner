<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>   
 
<CENTER>
	 <div>

<script  language="javascript">
 function addModel(){ 
 
			    document.forms[0].action='pageSave.do';
			    document.forms[0].submit(); 
				 
 }
  
  <%--
			function submitForm(){ 
			    document.forms[0].action='list.do';
			    document.forms[0].submit(); 
			}--%>
</script>			
			<html:form method="post" action="/contractModel/list" >				
				<table class="formTable">
				  <caption>
				 ${eoms:a2u('合同模板查询')}
			      </caption>
					<tr>
						<td>
							${eoms:a2u('专业')}
						</td>
						<td>
							<!-- html:select property="specialtyId" style="width: 3cm;"-->
								<!--html:options collection="specialtyOptions" property="value" labelProperty="label" styleClass="clstext"-->
							<!--/html:select>  -->
						</td>
					 
						<td>
							${eoms:a2u('合同类型')}
						</td>
						<td>
							<!-- html:select property="contractType" style="width: 3cm;" -->
							<!--html:options collection="contractTypeOptions" property="value" labelProperty="label" styleClass="clstext" /-->
							<!--/html:select> -->
						</td>
					</tr>
					<tr>
						<td>
							${eoms:a2u('模板名称')}
						</td>
						<td>
							<html:text property="modelName" styleClass="clstext" size="20" maxlength="20" />
						</td>
				 	
						<td>
							${eoms:a2u('创建人')}
						</td>
						<td>
							<html:text property="creator" styleClass="clstext" size="20" maxlength="20" />
						</td>
				 </tr>
				 <tr>
						<td>
							${eoms:a2u('区域关系')}
						</td>			
						<td>
							<% /* logic:equal value="true" name="queryFormFieldReadOnly">
								<html:hidden  property="regionId"/>
								<input type="text" class="clstext" value="<bean:write name="regionName"/>" readonly="readonly"/>
							</logic:equal>
							<logic:equal value="false" name="queryFormFieldReadOnly">
								  html:select property="regionId" style="width:3cm" 
									styleClass="clstext" >
									<html:options collection="simpleDictRegionOptions"
										property="value" labelProperty="label" />
								</html:select>
							</logic:equal  */%>
							

						</td>
					 
						<td>
							${eoms:a2u('创建日期')}
						</td>
						<td colspan="3">
							${eoms:a2u('从')}<html:text property="beginDate" size="20" onfocus="calendar()" readonly="true" styleClass="clstext" />
						${eoms:a2u('到')}
							<html:text property="endDate" size="20" onfocus="calendar();" readonly="true" styleClass="clstext" />
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<p align="left">
								<html:submit  styleClass="button" onclick="submitForm();"  value="${eoms:a2u('查询')}"  />
								&nbsp;
								<html:reset  styleClass="button" value="${eoms:a2u('重置')}" />
								&nbsp; 
								<html:button styleClass="button" value="${eoms:a2u('添加')}"  property="button1" onclick="javascript:addModel()" ></html:button>
 
							  </p>
						</td>
					</tr>
				</table>				
			</html:form>
			</div> 
			 
			<display:table name="sessionScope.contractModelFormList" id="row"
				cellspacing="1" cellpadding="1">
				<display:column property="specialtyName" title="${eoms:a2u('专业')}"
					 />
				<display:column property="serviceTypeName" title="${eoms:a2u('代维类型')}"
					 />
				<display:column property="regionName" title="${eoms:a2u('地市信息')}"
					 />
				<display:column property="modelName" title="${eoms:a2u('合同模板名称')}"
					 />
				<display:column property="strCreateDate" title="${eoms:a2u('创建日期（↓）')}"
					 />
				<display:column property="contractTypeName" title="${eoms:a2u('合同类型')}"
					style="width:60px" />
				<display:column property="extend_fields_name" title="${eoms:a2u('模板扩展字段')}"
					style="width:300px" />
				<display:column title="${eoms:a2u('操作')}" style="width:60px">
					<a href="pageEdit.do?modelId=<bean:write name='row' property='modelId'/>" />
						<img src="/images/bottom/an_bj.gif" border="0"
							alt="${eoms:a2u('修改')}"> </a>
					<a href="pageDelete.do?modelId=<bean:write name='row' property='modelId'/>" />
						<img src="/images/bottom/an_sc.gif" border="0"
							alt="${eoms:a2u('删除')}"> </a>
				</display:column>
				
				<display:setProperty name="export.xml" value="false" />
				<display:setProperty name="export.csv" value="false" />
				<display:setProperty name="export.pdf" value="false" />
				<display:setProperty name="export.excel.filename" value="supplychainReport.xls" />
			
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
		 

<%@ include file="/common/footer_eoms_innerpage.jsp"%>
