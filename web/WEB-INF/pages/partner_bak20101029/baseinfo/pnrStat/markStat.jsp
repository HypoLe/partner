<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<html:form action="/pnrStats.do?method=searchMark" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">合作伙伴市场份额</div>
	</caption>

</table>
<html:hidden property="stat" value="stat" />	
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
List markList = (List)request.getAttribute("markList");

if(markList!=null){
%>
			<tr>
				<td class="label">合作伙伴名称</td>
				<td class="label">地市</td>
				<td class="label">维护基站数量</td>
				<td class="label">线路长度（公里）</td>
				<td class="label">数据点数量</td>
			</tr>
		
			<%
			
			String proTem = "";
			String proStat = null;
			String regStat = null;
			String siteStat = null;
			String lineStat = null;
			String poineStat = null;
			for(int i=0;i<markList.size();i++){
				Object[] mark =  (Object[])markList.get(i);
				
				proStat = (String)mark[0]; 
				regStat = (String)mark[1];
				//基站维护数量
				siteStat = String.valueOf(mark[2]);
				//线路长度
				lineStat = String.valueOf(mark[3]);
				//数据点数量
				poineStat = StaticMethod.null2String(String.valueOf(mark[4]),"0");
	    		String proTab = StaticMethod.nullObject2String(rowMap.get(proStat+"_num"));
			 %>
			<tr>
			<%
			if(!proTem.equals(proStat)){
			 %>
				<td rowspan="<%=proTab%>">
					<%=proStat %>
				</td>
				
			<%
			}
			 %>
				<td>
					 <eoms:id2nameDB id="<%=regStat %>" beanId="tawSystemAreaDao" /> 
				</td>
			 	<td>
						<%if(siteStat == "null"){
							out.print("0");
						
						}else{
							out.print(siteStat);
						}
						 %>			 	
				</td>
			
				<td>
						<%if(lineStat == "null"){
							out.print("0");
						
						}else{
							out.print(lineStat);
						}
						 %>				
				</td>
				<td >
						<%if(poineStat == "null"){
							out.print("0");
						
						}else{
							out.print(poineStat);
						}
						 %>					
				</td>
			</tr>
			<%
				if(!proTem.equals(proStat)){
					proTem = proStat;
				}
			}
			}else{
			 %>	
			 	<tr></tr>
			 <% }%>
</table>



<%@ include file="/common/footer_eoms.jsp"%>