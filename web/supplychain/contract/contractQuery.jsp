<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
 
 <script language="javascript"
			src="<%=request.getContextPath()%>/js/calendar2.js"></script>
			<script type="text/javascript">
				function cleanDate() {
					document.forms[0].strCreateDateLower.value="";
				}
             </script>
	  
			<html:form method="post" action="/contract/queryList.do">
				<table width=200 class="formTable">
					<caption> <bean:write name="contractTypeName" /> ${eoms:a2u('查询')}</caption>
					 
				<html:hidden property="contractTypeId"/>
				 
					<logic:equal value="1" name="contractTypeId">
						<tr class="tr_show">
							<td  class="label">
								${eoms:a2u('专业')}
							</td>
							<td   height="25">
								<html:select property="specialtyId" style="width: 4cm;">
									 
								</html:select>
							</td>
						</tr>
					</logic:equal>
					<logic:equal value="2" name="contractTypeId"><%--
					<tr class="tr_show">
						<td width="30%" height="25" class="clsfth">
							地区
						</td>
						<td width="35%" height="25">	
						<html:select property="regionId" style="width: 4cm;" disabled="false">
							<html:options collection="netRegionCollection" property="value"
								labelProperty="label" />
						</html:select>
						</td>
					</tr>
					<tr class="tr_show">
						<td width="30%" height="25" class="clsfth">
							县市
						</td>
						<td width="35%" height="25">	
						<html:select property="cityId" style="width: 4cm;" disabled="false">
							<html:options collection="netCityCollection" property="value"
								labelProperty="label" />
						</html:select>
						</td>
					</tr>
					--%>
					<tr class="tr_show">
							<td width="100"  class="label">
								${eoms:a2u('工作区名称')}
							</td>
							<td    height="25">
								<html:text property="workAroundName" styleClass="clstext" size="20"
									maxlength="20" />
							</td>
					</tr>
					</logic:equal>
					<logic:equal value="1" name="contractTypeId">
						<tr class="tr_show">
							<td  width="100" class="label">
								${eoms:a2u('合同对方(代维公司)')}
							</td>
							<td   height="25">
								<html:text property="companyNamePartB" styleClass="clstext" size="20"
									maxlength="20" />
							</td>
						</tr>
					</logic:equal>
					<logic:equal value="2" name="contractTypeId">
						<tr class="tr_show">
							<td width="100" class="label">
								${eoms:a2u('合同对方')}
							</td>
							<td   height="25">
								<html:text property="partB" styleClass="clstext" size="20"
									maxlength="20" />
							</td>
						</tr>
					</logic:equal>
					<tr class="tr_show">
						<td width="100"  class="label">
							${eoms:a2u('合同额')}
						</td>
						<td   height="25">
							<html:text property="money" styleClass="clstext" size="20"
								maxlength="20" />
						</td>
					</tr>
					<tr class="tr_show">
						<td width="100"  class="label">
							${eoms:a2u('创建日期(下限)')}
						</td>
						<td   height="25">
							<html:text property="strCreateDateLower" size="20"
								onfocus="calendar()" readonly="true" styleClass="clstext" />
						</td>
					</tr>
					<tr class="tr_show">
						<td width="100" class="label">
							${eoms:a2u('创建日期(上限)')}
						</td>
						<td   height="25">
							<html:text property="strCreateDateUpper" size="20"
								onfocus="calendar();" readonly="true" styleClass="clstext" />
						</td>
					</tr>
					<tr class="tr_show">
						<td width="100" class="label">
							${eoms:a2u('创建人')}
						</td>
						<td   height="25">
							<html:text property="creator" styleClass="clstext" size="20"
								maxlength="20" />
						</td>
					</tr>
				 
					<tr align="center" >
						<td colspan="2">
							<p align="left">
								<html:submit styleClass="submit" value="${eoms:a2u('查询')}"  />
								&nbsp;&nbsp;
								<html:reset styleClass="reset" value="${eoms:a2u('重置')}" onclick="cleanDate()" />
							</p>
						</td>
					</tr>
				</table>
        </html:form>
		 
		<logic:notEmpty name="message">
			<script type="text/javascript">
			<!--
			alert('<bean:write name="message"/>');
			//-->
			</script>
		</logic:notEmpty>
	 
<%@ include file="/common/footer_eoms_innerpage.jsp"%>
