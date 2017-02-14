
<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<html:form action="/pnrStats.do?method=getReportPersonViewStat&first=fir" styleId="SiteReportFrom" method="post" > 

<table class="formTable">
	<caption>
		<div class="header center">合作伙伴人力资源视图</div>
	</caption>
</table>

<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="统计" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>

<table class="formTable"  width="85%" >
<%
Map rowMap = (Map)request.getAttribute("rowMap");
List listSiteStat = (List)request.getAttribute("listPersonStat");


%>
			<tr>
				<td style="background-color:#E7F6FC;" >地市</td>
				<td style="background-color:#E7F6FC;" >合作伙伴名称</td>
				<td style="background-color:#E7F6FC;" >专业</td>
				<td style="background-color:#E7F6FC;" >骨干员工数</td>
				<td style="background-color:#E7F6FC;" >总人数</td>
				<td style="background-color:#E7F6FC;" >骨干员工占比</td>
				<td style="background-color:#E7F6FC;" >当月流入人数</td>
				<td style="background-color:#E7F6FC;" >当月流出人数</td>
				<td style="background-color:#E7F6FC;" >当月流入骨干人数</td>
				<td style="background-color:#E7F6FC;" >当月流出骨干人数</td>
			</tr>
		
			<%
			if(listSiteStat!=null){	
			String regionTem = "";
			String proTem = "";
			
			String regStat = null;
			String proStat = null;
			String professionalStat = null;
			String personConGuStat = null;
			String personConStat = null;
			String personBiStat = null;
			String personConMrStat = null;
			String personConMcStat = null;
			String personConMrgStat = null;
			String personConMcgStat = null;
			for(int i=0;i<listSiteStat.size();i++){
				Object[] mark =  (Object[])listSiteStat.get(i);
				
				regStat = (String)mark[0];
				proStat = (String)mark[1];
				professionalStat = (String)mark[2];
				personConGuStat = String.valueOf(mark[4]);
				personConStat = String.valueOf(mark[3]);
				if(personConStat=="null"){
					personBiStat = "0%";
				}else{
					 if(personConGuStat=="null"){
						 personBiStat = "0%";
					 }else{
						 float a = Float.parseFloat(personConGuStat);
						 float b = Float.parseFloat(personConStat); 
						 int c = (int)(a/b*100);
						 personBiStat = String.valueOf(c+"%");
					 } 
				}
				
				personConMrStat = String.valueOf(mark[5]);
				personConMcStat = String.valueOf(mark[6]);
				personConMrgStat = String.valueOf(mark[7]);
				personConMcgStat = String.valueOf(mark[8]);

				String regionTab = StaticMethod.nullObject2String(rowMap.get(regStat+"_num"));
	    		String proTab = StaticMethod.nullObject2String(rowMap.get(regStat+"_"+proStat+"_num"));
			 %>
			<tr>
			<%
			if(!regionTem.equals(regStat)){
			 %>
				<td rowspan="<%=regionTab%>">
					<%=regStat %>
				</td>
			<%
			}
			if(!proTem.equals(proStat)||!regionTem.equals(regStat)){
			 %>
				<td rowspan="<%=proTab%>">
					<%=proStat %>
				</td>
			<%} %>
			<td>
					<%if(professionalStat!=null && !"".equals(professionalStat)){ %>
						<eoms:dict key="dict-partner-baseinfo" dictId="serviceProfession" itemId="<%=professionalStat%>" beanId="id2nameXML" /> 
					<% 	}else{ %>
							<font style="color: blue;">无维护专业</font>
					<%	}%>
			</td>
			 	<td>
						<%
						if(personConGuStat == "null"){
							out.print("0");
						}else{
							out.print(personConGuStat);
						}
						 %>	
				</td>
			
				<td>
						<%if(personConStat == "null"){
							out.print("0");
						}else{
							out.print(personConStat);
						}
						 %>				
				</td>
				<td>
						<%if(personBiStat == "null"){
							out.print("0");
						}else{
							out.print(personBiStat);
						}
						 %>				
				</td>
				<td>
						<%if(personConMrStat == "null"){
							out.print("0");
						}else{
							out.print(personConMrStat);
						}
						 %>				
				</td>
				<td>
						<%if(personConMcStat == "null"){
							out.print("0");
						}else{
							out.print(personConMcStat);
						}
						 %>				
				</td>
				<td>
						<%if(personConMrgStat == "null"){
							out.print("0");
						}else{
							out.print(personConMrgStat);
						}
						 %>				
				</td>
				<td>
						<%if(personConMcgStat == "null"){
							out.print("0");
						}else{
							out.print(personConMcgStat);
						}
						 %>				
				</td>
			</tr>
			<%
				if(!proTem.equals(proStat)){
					proTem = proStat;
				}
				if(!regionTem.equals(regStat)){
					regionTem = regStat;
				}
			}
			}else{
			 %>	
			 <% }%>
</table>


<%@ include file="/common/footer_eoms.jsp"%>