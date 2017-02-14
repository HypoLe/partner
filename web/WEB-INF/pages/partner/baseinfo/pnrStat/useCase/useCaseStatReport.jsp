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
List listUseCaseStat = (List)request.getAttribute("listUseCaseStat");
 %>

	<html>
		<table align="center" class="formTable">
			<caption>
				<div class="header center">合作伙伴使用情况统计结果列表</div>
			</caption>
			<tr>

				<td class="label">
					合作伙伴名称&nbsp;&nbsp;
				</td>
				<td class="label">
					用户ID&nbsp;&nbsp;
				</td>
				<td class="label">
					登陆次数&nbsp;&nbsp;
				</td>
				<td class="label">
					最后一次登陆时间&nbsp;&nbsp;
				</td>
				<td class="label">
					最后一次登陆IP&nbsp;&nbsp;
				</td>

			</tr> 
			<%
			String partnerTem = "";
			String userID = "";
			String lgNum = "";
			String operTime = "";
			String remoteIp = "";
			String partnerNameStat =null;
			for(int i=0;i<listUseCaseStat.size();i++){
				Object[] useCaseObject =  (Object[])listUseCaseStat.get(i);
	    		//合作伙伴名称
	    		partnerNameStat = (String)useCaseObject[0];
	    		//用户ID
	    		userID = (String)useCaseObject[1];
	    		//登陆次数
	    		lgNum = String.valueOf(useCaseObject[2]);
    			//最后一次登陆时间
    			operTime = String.valueOf(useCaseObject[3]);
	    		//最后一次登陆IP
	    		remoteIp = String.valueOf(useCaseObject[4]);
	    		
	    		String partnerNameTab = StaticMethod.nullObject2String(rowMap.get(partnerNameStat+"_num"));
				 %>
				<tr>
				<%
				if(!partnerTem.equals(partnerNameStat)){
				 %>
					<td rowspan="<%=partnerNameTab %>">
						<%=partnerNameStat %>
					</td>
					
				<%
				}
				 %>
					<td >
						<%=userID%>
					</td>
				 	
				 	<td >
						<% if(lgNum=="null"){  
							out.print("0");
						  }else{ %>
								<%=lgNum %>
					<%	} %> 
					</td>
				 	
					<td >
						<%if(operTime == "null"){ %>
							<font style="color: blue;">该用户未登陆过</font>
						
					<%	}else{
							out.print(operTime);
						}
						 %>
					</td>
					<td >
						<%if(remoteIp == "null"){%>
							<font style="color: blue;">该用户未登陆过</font>
					<%	}else{
							out.print(remoteIp);
						}
						 %>
					</td>
				</tr>
				
				<%
					if(!partnerTem.equals(partnerNameStat)){
						partnerTem = partnerNameStat;
					}
			}
			 %>
			
			
		</table>
	</html>





<%@ include file="/common/footer_eoms.jsp"%>