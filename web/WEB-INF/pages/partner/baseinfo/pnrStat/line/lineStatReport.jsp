<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<jsp:directive.page import="com.boco.eoms.partner.baseinfo.webapp.form.LineStatReportForm" />
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/lines.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>



<%
Map rowMap = (Map)request.getAttribute("rowMap");
List lineStatList = (List)request.getAttribute("lineStatList");
 %>

	<html>
		<table align="center" class="formTable">
			<caption>
				<div class="header center">统计结果列表</div>
			</caption>
			<tr>

				<td class="label">
					合作伙伴名称&nbsp;&nbsp;
				</td>
				
				<td class="label">
					地市&nbsp;&nbsp;
				</td>
					
				<td class="label">
					县市&nbsp;&nbsp;
				</td>
					
				<td class="label">
					线路长度（公里）&nbsp;&nbsp;
				</td>

			</tr> 
			<%
			String partnerTem = "";
			String regionTem = "";
			String cityTem = "";
			
			String bigPartnerIdStat =null;
			String partnerNameStat =null;
			String regionStat =null;
			String cityStat =null;
			String sunLineLengthStat =null;
			LineStatReportForm lineStatReportForm = null;
			for(int i=0;i<lineStatList.size();i++){
				lineStatReportForm  =  (LineStatReportForm)lineStatList.get(i);
				//合作伙伴ID
				bigPartnerIdStat = (String)lineStatReportForm.getBigProviderID();
				//合作伙伴名称
	    		partnerNameStat = (String)lineStatReportForm.getBigProvider();
	    		//地市名称
	    		regionStat = (String)lineStatReportForm.getRegion();
	    		//县区名称
	    		cityStat = (String)lineStatReportForm.getCity();
	    		//线路长度（公里）
	    		sunLineLengthStat = String.valueOf(lineStatReportForm.getLineLength());
	    		
	    		String partnerTab = StaticMethod.nullObject2String(rowMap.get(bigPartnerIdStat+"_num"));
	    		String regionTab = StaticMethod.nullObject2String(rowMap.get(bigPartnerIdStat+"_"+regionStat+"_num"));
	    		String cityTab = StaticMethod.nullObject2String(rowMap.get(bigPartnerIdStat+"_"+regionStat+"_"+cityStat+"_num"));
				 %>
				<tr>
				<%
				if(!partnerTem.equals(bigPartnerIdStat)){
				 %>
					<td rowspan="<%=partnerTab %>">
						
						<%=partnerNameStat %>
					</td>
					
				<%
				}
				if(!partnerTem.equals(bigPartnerIdStat)||!regionTem.equals(regionStat)){
				 %>
					<td rowspan="<%=regionTab %>">
						 <eoms:id2nameDB id="<%=regionStat %>" beanId="tawSystemAreaDao" /> 
					<!--	<%= regionStat%> -->
					</td>
				<%
				}
				if(!partnerTem.equals(bigPartnerIdStat)||!regionTem.equals(regionStat)||!cityTem.equals(cityStat)){
				 %>
					<td rowspan="<%=cityTab %>">
						 <eoms:id2nameDB id="<%=cityStat %>" beanId="tawSystemAreaDao" /> 
					<!--	<%= cityStat%> -->
					</td>
				<%
				}
				 %>		
					<td >
						
							<%= sunLineLengthStat%>
						
					</td>
				</tr>
				
				
				<%
					if(!partnerTem.equals(bigPartnerIdStat)){
						partnerTem = bigPartnerIdStat;
					}
					if(!regionTem.equals(regionStat)){
						regionTem = regionStat;
					}
					if(!cityTem.equals(cityStat)){
						cityTem = cityStat;
					}
			}
			 %>
			
			
		</table>
	</html>





<%@ include file="/common/footer_eoms.jsp"%>