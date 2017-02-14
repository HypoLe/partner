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
List listApparatusStat = (List)request.getAttribute("listApparatusStat");
 %>

	<html>
		<table align="center" class="formTable">
			<caption>
				<div class="header center">仪器仪表统计结果列表</div>
			</caption>
			<tr>

				<td class="label">
					合作伙伴名称&nbsp;&nbsp;
				</td>
				<td class="label">
					仪器仪表名称&nbsp;&nbsp;
				</td>
				<td class="label">
					运行状态&nbsp;&nbsp;
				</td>
				<td class="label">
					数量&nbsp;&nbsp;
				</td>
				

			</tr> 
			<%
			String partnerTem = "";
			String apparatusNameTem = "";
			String stateTem = "";
			String numTem = "";
			
			String partnerNameStat =null;
			String apparatusNameStat =null;
			String apparatusStateStat = null;
			String numStat =null;
			
			for(int i=0;i<listApparatusStat.size();i++){
				Object[] apparatusObject =  (Object[])listApparatusStat.get(i);
	    		//合作伙伴名称
	    		partnerNameStat = (String)apparatusObject[0];
	    		//仪器仪表名称
	    		apparatusNameStat = String.valueOf(apparatusObject[1]);
	    		//运行状态
	    		apparatusStateStat = String.valueOf(apparatusObject[2]);
	    		//仪器仪表数量
	    		numStat = String.valueOf(apparatusObject[3]);
	    		
	    		String partnerTab = StaticMethod.nullObject2String(rowMap.get(partnerNameStat+"_num"));
	    		String apparatusNameTab = StaticMethod.nullObject2String(rowMap.get(partnerNameStat+"_"+apparatusNameStat+"_num"));
	    		
//	    		String stateTab = StaticMethod.nullObject2String(rowMap.get(partnerNameStat+"_"+typeStat+"_"+apparatusStateStat+"_num"));
//	    		String numTab = StaticMethod.nullObject2String(rowMap.get(partnerNameStat+"_"+typeStat+"_"+apparatusStateStat+"_"+numStat+"_num"));
				 %>
				<tr>
				<%
				if(!partnerTem.equals(partnerNameStat)){
				 %>
					<td rowspan="<%=partnerTab %>">
						<%=partnerNameStat %>
					</td>
					
				<%
				}
				if(!partnerTem.equals(partnerNameStat)||!apparatusNameTem.equals(apparatusNameStat)){
				 %>
				 	<td >
						<% if(apparatusNameStat=="null"){  %>
							<font style="color: blue;">该地市、合作伙伴未配置仪器仪表</font>
						 <% }else{ 
							out.print(apparatusNameStat);
						} %> 
						
					</td>
				<%
				}
//				if(!partnerTem.equals(partnerNameStat)||!typeTem.equals(typeStat)||!stateTem.equals(apparatusStateStat)){
				 %>	
				 	<td >
						<% if(apparatusStateStat=="null"){  %>
							<font style="color: blue;">无运行状态</font>
						 <% }else{ %>
							<eoms:id2nameDB id="<%=apparatusStateStat %>" beanId="ItawSystemDictTypeDao" />
					<%	} %> 
					</td>
				<%
//				}
//				if(!partnerTem.equals(partnerNameStat)||!typeTem.equals(typeStat)||!stateTem.equals(apparatusStateStat)||!numTem.equals(numStat)){
				 %>	
				 	
					<td >
						<%if(numStat == "null"){
							out.print("0");
						
						}else{
							out.print(numStat);
						}
						 %>
					</td>
				<%
//				} %>	
				
				</tr>
				
				
				<%
					if(!partnerTem.equals(partnerNameStat)){
						partnerTem = partnerNameStat;
					}
					if(!apparatusNameTem.equals(apparatusNameStat)){
						apparatusNameTem = apparatusNameStat;
					}
			}
			 %>
			
			
		</table>
	</html>





<%@ include file="/common/footer_eoms.jsp"%>