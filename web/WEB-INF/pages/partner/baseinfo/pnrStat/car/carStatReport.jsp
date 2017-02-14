<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/lines.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>



<%
Map rowMap = (Map)request.getAttribute("rowMap");
List listCarStat = (List)request.getAttribute("listCarStat");
 %>

	<html>
		<table align="center" class="formTable">
			<caption>
				<div class="header center">车辆信息统计结果列表</div>
			</caption>
			<tr>

				<td class="label">
					地市&nbsp;&nbsp;
				</td>
				<td class="label">
					合作伙伴名称&nbsp;&nbsp;
				</td>
				<td class="label">
					车辆应配数量&nbsp;&nbsp;
				</td>
				<td class="label">
					车辆实际数量&nbsp;&nbsp;
				</td>

			</tr> 
			<%
			String regionTem = "";
			String partnerTem = "";
			
			String regionStat =null;
			String partnerNameStat =null;
			String ypNumStat =null;
			String spNumStat =null;
			
			for(int i=0;i<listCarStat.size();i++){
				Object[] carObject =  (Object[])listCarStat.get(i);
				
	    		//地市名称
	    		regionStat = (String)carObject[1];
	    		//合作伙伴名称
	    		partnerNameStat = (String)carObject[2];
	    		//车辆应配数量
	    		ypNumStat = String.valueOf(carObject[3]);
    			//车里实配数量
    			spNumStat = String.valueOf(carObject[4]);
	    		
	    		String regionTab = StaticMethod.nullObject2String(rowMap.get(regionStat+"_num"));
	    		String partnerTab = StaticMethod.nullObject2String(rowMap.get(regionStat+"_"+partnerNameStat+"_num"));
				 %>
				<tr>
				<%
				if(!regionTem.equals(regionStat)){
				 %>
					<td rowspan="<%=regionTab %>">
						<%=regionStat %>
					</td>
					
				<%
				}
				if(!regionTem.equals(regionStat)||!partnerTem.equals(partnerNameStat)){
				 %>
					<td rowspan="<%=partnerTab %>">
						<!-- <eoms:id2nameDB id="<%=regionStat %>" beanId="tawSystemAreaDao" /> -->
						<%= partnerNameStat%>
					</td>
				<%
				}

				 %>		
					<td >
						<%if(ypNumStat == "null"){
							out.print("0");
						
						}else{
							out.print(ypNumStat);
						}
						 %>
					</td>
					<td >
						<%if(spNumStat == "null"){
							out.print("0");
						
						}else{
							out.print(spNumStat);
						}
						 %>
					</td>
				
				
				</tr>
				
				
				<%
					if(!partnerTem.equals(partnerNameStat)){
						partnerTem = partnerNameStat;
					}
					if(!regionTem.equals(regionStat)){
						regionTem = regionStat;
					}
			}
			 %>
			
			
		</table>
	</html>





<%@ include file="/common/footer_eoms.jsp"%>