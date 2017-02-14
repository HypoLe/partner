<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="com.boco.eoms.partner.assess.AssFee.model.FeeDetail"%>
<%@page import="com.boco.eoms.partner.assess.AssFee.model.FeeTotal"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<%
	List feeTotaList = (List)request.getAttribute("feeTotaList");
	if(feeTotaList.size()>0){
%>
	<c:set var="buttons">
		<input type="button" style="margin-right: 5px"
			onclick="location.href='<html:rewrite page='/feeTotals.do?method=add'/>'"
			value="<fmt:message key="button.add"/>" />
	</c:set>

	<table class="formTable">
		<caption>
			<div class="header center">${year}年光缆线路代理维护服务量及维护费用报价计算表</div>
		</caption>
		<tr>
			<td rowspan="2" class="label">
				序号
			</td>
			<td rowspan="2" class="label">
				地市
			</td>
			<%
	    	List feeDetaiList = (List)request.getAttribute("feeDetaiList");	
	    	FeeDetail feeDetail = null;
	    	int sortNum = 0;
	    	for(int i = 0 ; feeDetaiList.size()>i;i++){
	    		feeDetail = (FeeDetail)feeDetaiList.get(i);
	    		if("lineName".equals(feeDetail.getType())){
		    %>
				<td colspan="3" class="label">
					<eoms:id2nameDB id="<%=feeDetail.getNodeId()%>" beanId="feeTreeDao" />
				</td>
			<%  	
		    		}else{
		    			sortNum++;
		    		}
		    	}
		    %>
			<td colspan="<%=sortNum%>" class="label">
				维护量合计
			</td>
			<td rowspan="2" class="label">
				维护费用合计（实际）
			</td>
			<td rowspan="2" class="label">
				维护费用合计（修改后）
			</td>
			<td colspan="3" class="label">
				地市公司
			</td>
		</tr>
		<tr>
			<%
	    	for(int i = 0 ; feeDetaiList.size()>i;i++){
	    		feeDetail = (FeeDetail)feeDetaiList.get(i);
	   	 		if("lineName".equals(feeDetail.getType())){
	   		 %>
			<td class="label">
				皮长<%=feeDetail.getUnit() %>
			</td>
			<td class="label">
				单价
			</td>
			<td class="label">
				合计
			</td>
			<%  	
	    		}
	    	}
	  		%>
			<%
	    	for(int i = 0 ; feeDetaiList.size()>i;i++){
	    		feeDetail = (FeeDetail)feeDetaiList.get(i);
	   	 		if("sort".equals(feeDetail.getType())){
	   		 %>
			<td class="label">
				<eoms:id2nameDB id="<%=feeDetail.getNodeId()%>" beanId="feeTreeDao" />
			</td>
			<%  	
	    		}
	    	}
	  		%>  		
			<td class="label">
				月基础维护费
			</td>
			<td class="label">
				每扣一分扣款金额
			</td>
			<td class="label">
				季度基础维护费
			</td>
		</tr>
		
		<%
		Map feeDetailMap = (HashMap)request.getAttribute("feeDetailMap");
	   	FeeTotal feeTotal = null;
	   	Map totalMap = new HashMap();
	   	double monthMoney = 0.0;
	   	double quarterMoney = 0.0;
	   	double totalreaMoney = 0.0;
	   	double totalMoney = 0.0;
	   	String number ="";
	   	String total = "";
	   	DecimalFormat df = new DecimalFormat("0.00");
	   	for(int i = 0 ; feeTotaList.size()>i;i++){
	   		feeTotal = (FeeTotal)feeTotaList.get(i);
	   		monthMoney = monthMoney + feeTotal.getMonthMoney();
	   		quarterMoney = quarterMoney + feeTotal.getQuarterMoney();
	   		totalreaMoney = totalreaMoney + feeTotal.getTotalreaMoney();
	   		totalMoney = totalMoney + feeTotal.getTotalMoney();
	    %>
		    <tr>
				<td>
					<%=i+1%>
				</td>
				<td>
					<a href="${app}/partner/assess/feeTotals.do?method=edit&id=<%=feeTotal.getId() %>"><eoms:id2nameDB id="<%=feeTotal.getAreaId()  %>" beanId="tawSystemAreaDao" /></a>
				</td>			
			<%  	
				feeDetaiList = (List)feeDetailMap.get(feeTotal.getId());
				for(int j = 0 ;feeDetaiList.size()>j;j++){
					feeDetail = (FeeDetail)feeDetaiList.get(j);
					if("lineName".equals(feeDetail.getType())){
						number = StaticMethod.null2String(String.valueOf(totalMap.get(feeDetail.getNodeId()+"_number")));
						if("".equals(number)||"null".equals(number)){
							totalMap.put(feeDetail.getNodeId()+"_number", String.valueOf(feeDetail.getNumber()));
						}else{
							totalMap.put(feeDetail.getNodeId()+"_number", String.valueOf(Double.parseDouble(number)+feeDetail.getNumber()));
						}
						total = StaticMethod.null2String(String.valueOf(totalMap.get(feeDetail.getNodeId()+"_total")));
						if("".equals(number)||"null".equals(number)){
							totalMap.put(feeDetail.getNodeId()+"_total", String.valueOf(feeDetail.getTotal()));
						}else{
							totalMap.put(feeDetail.getNodeId()+"_total", String.valueOf(Double.parseDouble(total)+feeDetail.getTotal()));
						}					
			%>
					<td>
						<%=df.format(feeDetail.getNumber()) %>
					</td>
					<td>
						<%=df.format(feeDetail.getPrice()) %>
					</td>
					<td>
						<%=df.format(feeDetail.getTotal()) %>
					</td>			
			<%
					}
				}
				for(int j = 0 ;feeDetaiList.size()>j;j++){
					feeDetail = (FeeDetail)feeDetaiList.get(j);
					if("sort".equals(feeDetail.getType())){
						total = StaticMethod.null2String(String.valueOf(totalMap.get(feeDetail.getNodeId()+"_total")));
						if("".equals(number)||"null".equals(number)){
							totalMap.put(feeDetail.getNodeId()+"_total", String.valueOf(feeDetail.getTotal()));
						}else{
							totalMap.put(feeDetail.getNodeId()+"_total", String.valueOf(Double.parseDouble(total)+feeDetail.getTotal()));
						}					
			%>
					<td>
						<%=df.format(feeDetail.getTotal()) %>
					</td>
			<%			
					}
				}
			%>
				<td>
					<%=df.format(feeTotal.getTotalreaMoney()) %>
				</td>
				<td>
					<%=df.format(feeTotal.getTotalMoney())  %>
				</td>
				<td>
					<%=df.format(feeTotal.getMonthMoney()) %>
				</td>
				<td>
					<%=df.format(feeTotal.getPointMoney()) %>
				</td>	
				<td>
					<%=df.format(feeTotal.getQuarterMoney()) %>
				</td>											
			</tr>
		<%	
	   	}
	    %>		
		<tr>
			<td>
				<%=feeTotaList.size()+1 %>
			</td>
			<td>
				总计
			</td>
			<%
			   		for(int j = 0 ;feeDetaiList.size()>j;j++){
						feeDetail = (FeeDetail)feeDetaiList.get(j);
						if("lineName".equals(feeDetail.getType())){
			%>
						<td>
							<%=df.format(Double.parseDouble(String.valueOf(totalMap.get(feeDetail.getNodeId()+"_number")))) %>
						</td>	
						<td>
							<%=df.format(feeDetail.getPrice()) %>
						</td>
						<td>
							<%=df.format(Double.parseDouble(String.valueOf(totalMap.get(feeDetail.getNodeId()+"_total")))) %>
						</td>							
			<%
						}
			   		}
			   		for(int j = 0 ;feeDetaiList.size()>j;j++){
			   			feeDetail = (FeeDetail)feeDetaiList.get(j);
			   			if("sort".equals(feeDetail.getType())){
			%>
						<td>
							<%=df.format(Double.parseDouble(String.valueOf(totalMap.get(feeDetail.getNodeId()+"_total")))) %>
						</td>
			<%
			   			}
			   		}
			%>	
			<td>
				<%=df.format(totalreaMoney) %>
			</td>
			<td>
				<%=df.format(totalMoney) %>
			</td>	
			<td>
				<%=df.format(monthMoney)%>
			</td>
			<td>
			</td>
			<td>
				<%=df.format(quarterMoney)%>
			</td>				
		</tr>
	</table>
	<c:out value="${buttons}" escapeXml="false" />
<%}else{ %>
	<table class="formTable">
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >${year}年暂未配置维护量及报价信息</td>
			</tr>
	</table>
<%} %>
<%@ include file="/common/footer_eoms.jsp"%>