<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="com.boco.eoms.partner.baseinfo.model.AreaDeptTree"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.boco.eoms.commons.system.dict.model.TawSystemDictType"%>
<style type="text/css">
body {
	background-image: none;
}
</style>
		<html:form action="/editFactury.do?method=saveFactory" styleId="pnrEvaKpiForm" >
			<table class="formTable" id="form" >
				<caption>
					<div class="header center">叶子模板厂商配置</div>
				</caption>
			<tr>
				<td class="label" >
					模板名称
				</td>
			<td class="content" >
			<eoms:id2nameDB id="${pnrEvaKpiFacturyForm.templateId}" beanId="pnrEvaTemplateDao" />
			</td>
			</tr>
				<tr>
				<td class="label" >
					厂家名称
				</td>
					<td class="content">
					<table border="0" cellpadding="0" cellspacing="0" >
					<tr><td style="border:none">
					
										<% 
					if(request.getAttribute("partnerList")!=null){
						List partnerList = (List) request.getAttribute("partnerList");
						if(request.getAttribute("allFacturyMap")!=null){
							Map allFacturyMap = (Map)request.getAttribute("allFacturyMap");
							for(int i=0;i<partnerList.size();i++){
								AreaDeptTree areaDeptTree = (AreaDeptTree)partnerList.get(i);
								String checked = "";
								if(allFacturyMap.get(areaDeptTree.getNodeId())!=null){
									checked = "checked='true'";
								}else{
									checked = "";
								}
					%><input type="checkbox" name="factury" value="<%=areaDeptTree.getNodeId() %>"<%=checked %>/><%=areaDeptTree.getNodeName() %>			
					
					<%
						if((i+1)%3==0){
							%>
							</td></tr><tr><td style="border:none">
							<%
						}else{
							%>
							</td><td style="border:none">
							<%	
						}
							}
						}
					}
					 %>
					
					
					
					
					
					<!-- 
					
					
					<% 
					if(request.getAttribute("tawSystemDictType")!=null){
						List dictList = (List) request.getAttribute("tawSystemDictType");
						if(request.getAttribute("allFacturyMap")!=null){
							Map allFacturyMap = (Map)request.getAttribute("allFacturyMap");
							for(int i=0;i<dictList.size();i++){
								TawSystemDictType tawSystemDictType = (TawSystemDictType)dictList.get(i);
								String checked = "";
								if(allFacturyMap.get(tawSystemDictType.getDictId())!=null){
									checked = "checked='true'";
								}else{
									checked = "";
								}
								System.out.println("+++++++++++++"+tawSystemDictType.getDictId());
					%><input type="checkbox" name="factury" value="<%=tawSystemDictType.getDictId() %>"<%=checked %>/><%=tawSystemDictType.getDictName() %>			
					
					<%
						if((i+1)%3==0){
							%>
							</td></tr><tr><td style="border:none">
							<%
						}else{
							%>
							</td><td style="border:none">
							<%	
						}
							}
						}
					}
					 %>
					  -->
					  
					 </td></tr>
					</table>
					</td>
				</tr>
			</table>
			<table><tr>
				<td>
					<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="validateForm();"/>
				</td>
				</tr>
			</table>
			<input type="hidden" id="templateId" name="templateId" value="${pnrEvaKpiFacturyForm.templateId}" />
			<input type="hidden" id="nodeId" name="nodeId" value="${pnrEvaKpiFacturyForm.nodeId}" />
		</html:form>
	</div>
<script type="text/javascript" />
	function validateForm() {
			document.forms[0].submit();
	}
</script>
