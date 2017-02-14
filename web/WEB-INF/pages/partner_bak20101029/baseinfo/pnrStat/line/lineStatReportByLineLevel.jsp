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
List listLineStat = (List)request.getAttribute("listLineStatByLineLevel");
 %>



	<html>
		<table align="center" class="formTable">
		 	<caption>
				<div class="header center">统计结果列表</div>
			</caption>
			<tr>

				<td class="label">
					地市名称&nbsp;&nbsp;
				</td>
					
				<td class="label">
					县市&nbsp;&nbsp;
				</td>

				<td class="label">
					线路等级&nbsp;&nbsp;
				</td>
					
				<td class="label">
					线路长度（公里）&nbsp;&nbsp;
				</td>

			</tr> 
			<%
			String regionTem = "";
			String cityTem = "";
			String gradeTem = "";			
			
			String regionStat =null;
			String cityStat =null;
			String gradeStat =null;			
			String sunLineLengthStat =null;
			for(int i=0;i<listLineStat.size();i++){
				Object[] lineObject =  (Object[])listLineStat.get(i);
	    		//地市名称
	    		regionStat = (String)lineObject[0];
	    		//县区名称
	    		cityStat = (String)lineObject[2];
				//线路等级
	    		gradeStat = (String)lineObject[4];
	    		//线路长度（公里）
	    		sunLineLengthStat = String.valueOf(lineObject[5]);
	    		
	    		String regionTab = StaticMethod.nullObject2String(rowMap.get(regionStat+"_num"));
	    		String  cityTab = StaticMethod.nullObject2String(rowMap.get(regionStat+"_"+cityStat+"_num"));
	    		//String  gradeTab = StaticMethod.nullObject2String(rowMap.get(regionStat+"_"+cityStat+"_"+gradeStat+"_num"));
				 %>
				<tr>
				<%
				if(!regionTem.equals(regionStat)){
				 %>
					<td rowspan="<%=regionTab %>">
						<!-- <eoms:id2nameDB id="<%=regionStat %>" beanId="tawSystemAreaDao" /> -->
						<%= regionStat%>
					</td>
					
				<%
				}
				if(!regionTem.equals(regionStat)||!cityTem.equals(cityStat)){
				 %>
					<td rowspan="<%=cityTab %>">
						<!-- <eoms:id2nameDB id="<%=cityStat %>" beanId="tawSystemAreaDao" /> -->
						<%= cityStat%>
					</td>
				<%
				}
				 %>
					<td>
						
						<%if(gradeStat!=null && !"".equals(gradeStat)){ %>
							
						<eoms:id2nameDB id="<%=gradeStat %>"  beanId="ItawSystemDictTypeDao" />
						
					<% 	}else{ %>
							<font style="color: blue;">未配置等级</font>
					<%	}
						 %>
						
					</td>
					
					<td >
						<%if(sunLineLengthStat == "null"){
							out.print("0");
						
						}else{
							out.print(sunLineLengthStat);
						}
						 %>
						
					</td>
				
				
				</tr>
				
				
				<%
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