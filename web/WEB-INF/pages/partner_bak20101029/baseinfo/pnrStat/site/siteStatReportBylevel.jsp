
<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<html:form action="/pnrStats.do?method=getSiteReportBylevel&first=fir" styleId="SiteReportFrom" method="post" > 

<table class="formTable">
	<caption>
		<div class="header center">基站统计</div>
	</caption>
<!-- 区域地市 -->
	<tr>
		<td class="label">
			所属地市：&nbsp;
		</td>
		<td class="content">
					<!-- 地市 -->			
					<select name="region" id="region"
						alt="allowBlank:false,vtext:'请选择所在地市'" 
						>
						<option value="">
							--请选择所在地市--
						</option>
						<logic:iterate id="regionBuffer" name="regionBuffer">
							<option value="${regionBuffer.areaid}">
								${regionBuffer.areaname}
							</option>
						</logic:iterate>
						
					</select>		
		</td>

		<td class="label">
			基站等级：&nbsp;
		</td>
		<td class="content">
			
			<eoms:comboBox name="siteLevle" id="siteLevle" initDicId="1121106" defaultValue="${siteForm.siteLevle}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
			
		</td>
	</tr>

</table>

<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="统计" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>


<table class="formTable">
<%
Map rowMap = (Map)request.getAttribute("rowMap");
List listSiteStat = (List)request.getAttribute("listSiteStat");


%>
			<tr>
				<td class="label">地市名称</td>
				<td class="label">基站等级</td>
				<td class="label">基站数量</td>
			</tr>
		
			<%
			if(listSiteStat!=null){			
			String regionTem = "";
			String regionStat = null;
			String sitelevelStat = null;
			String siteNumStat = null;
			for(int i=0;i<listSiteStat.size();i++){
				Object[] mark =  (Object[])listSiteStat.get(i);
				
				regionStat = (String)mark[0];
				sitelevelStat = (String)mark[1];
				siteNumStat = String.valueOf(mark[2]);
				
	    		String regionTab = StaticMethod.nullObject2String(rowMap.get(regionStat+"_num"));
			 %>
			<tr>
			<%
			if(!regionTem.equals(regionStat)){
			 %>
				<td rowspan="<%=regionTab%>">
					<%=regionStat %>
				</td>
			<%
			}
			 %>
				<td >
					<%if(sitelevelStat!=null && !"".equals(sitelevelStat)){ %>
						
						<eoms:id2nameDB id="<%=sitelevelStat%>"  beanId="ItawSystemDictTypeDao" />
					<% 	}else{ %>
					<%	}%>
				</td>
			<td>
					<%
					if(siteNumStat == "null"){
						out.print("0");
					
					}else{
						out.print(siteNumStat);
					}
					 %>							
			</td>
			</tr>
			<%
				if(!regionTem.equals(regionStat)){
					regionTem = regionStat;
				}
			}
			}else{
			 %>	
			 <% }%>
</table>



<%@ include file="/common/footer_eoms.jsp"%>