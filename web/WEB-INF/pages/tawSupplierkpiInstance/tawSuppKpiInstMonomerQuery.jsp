<jsp:directive.page import="java.util.ArrayList,java.util.Iterator" />
<jsp:directive.page
	import="com.boco.eoms.extra.supplierkpi.model.TawSupplierkpiInfo" />
<jsp:directive.page import="com.boco.eoms.extra.supplierkpi.webapp.util.SupplierkpiAttributes"/>
<jsp:directive.page import="com.boco.eoms.base.util.ApplicationContextHolder"/>
<%@ taglib uri="/WEB-INF/tlds/supplierkpi.tld" prefix="supplierkpi" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
String treeRootId = ((SupplierkpiAttributes) ApplicationContextHolder.getInstance().getBean("supplierkpiAttributes")).getTreeRootId();
%>
<script type="text/javascript">
Ext.onReady(function(){
	colorRows('list-table');
    //init Form validation and styles
	valider({form:'theform',vbtn:''});
})

function hidded1() {
	var objResult = document.getElementById("slt2");
	
		objResult.disabled = "disabled";
	
	}
function hidded2() {
	var objResult = document.getElementById("slt1");
	
		objResult.disabled = "disabled";
	
	}	
function optionDisabled(ee){
    if(ee.name == 'quarter'&&ee.value!='-1')document.getElementById("slt2").disabled=true;
    if(ee.name == 'month'&&ee.value!='-1')document.getElementById("slt1").disabled=true;
    if(ee.value=='-1'){
       document.getElementById("slt1").disabled=false;
       document.getElementById("slt2").disabled=false;
    }
}	
	
</script>

<div class="list-title">
	<bean:message key="tawSupplierkpiInstanceList.queryInstanse" />
</div>

<div class="list">
	<html:form action="/queryMonomertawSuppKpiIns.do?method=query" method="post" styleId="theform">
		<table style="width:85%;">
			<tr height="50">
			    <td>
			        <center><bean:message key="tawSupplierkpiInstanceList.supplier" /></center>
			    </td>
			    <td colspan="3">
			       <select name="manufacturerName">
					<option value="-1">
						=<label>${eoms:a2u('请选择厂商名称')}</label>=
					</option>
					<%
						ArrayList Manufacturers = (ArrayList) request
								.getAttribute("Manufacturers");
						Iterator iterator = Manufacturers.iterator();
						while (iterator.hasNext()) {
							TawSupplierkpiInfo tawSupplierkpiInfo = (TawSupplierkpiInfo)iterator.next();
					%>
                      <option value="<%=tawSupplierkpiInfo.getSupplierName() %>"><%=tawSupplierkpiInfo.getSupplierName() %></option>
					<%
					}
					%>					
				  </select>			        
			    </td>			    
			</tr>		
			<tr height="50">
			    <td width = "7%">
			       <center><bean:message key="tawSupplierkpiInstanceList.serviceType" /></center>
			    </td>
			    <td width = "25%">
			    	<supplierkpi:comboBox name="serviceType" id="serviceType" 
                  initDicId="<%=treeRootId%>" 
		      		sub="specialType" ds="/supplierkpi/tawSupplierkpiDicts.do?method=xsearch&dictId=" styleClass="select-class" />		    
			    </td>
			    <td width = "7%">
			       <center><bean:message key="tawSupplierkpiInstanceList.specialType" /></center>
			    </td>
			    <td width = "25%">
			       <eoms:comboBox name="specialType" id="specialType" />
			    </td>

			</tr>					    
			<tr height="50">

				<td>
					<center><bean:message key="tawSupplierkpiInstanceList.latitude" /></center>
				</td>
				<td colspan="3">
				    
					<select name="year" value="0">
						<option value="-1">====<bean:message key="tawSupplierkpiInstanceList.dark" />====</option>
						<option value="2005">2005</option>
						<option value="2006">2006</option>
						<option value="2007">2007</option>
						<option value="2008">2008</option>
						<option value="2009">2009</option>
						<option value="2010">2010</option>
						<option value="2011">2011</option>
						<option value="2012">2012</option>
						<option value="2013">2013</option>
						<option value="2014">2014</option>
						<option value="2015">2015</option>
					</select>
    			    <bean:message key="tawSupplierkpiInstanceList.year" />
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select name="quarter" id="slt1" onchange="optionDisabled(this);">
						<option value="-1">====<bean:message key="tawSupplierkpiInstanceList.dark" />====</option>
						<option value="one">${eoms:a2u('第一季度')}</option>
						<option value="two">${eoms:a2u('第二季度')}</option>
						<option value="three">${eoms:a2u('第三季度')}</option>
						<option value="four">${eoms:a2u('第四季度')}</option>
					</select>
					<bean:message key="tawSupplierkpiInstanceList.quarter" />	
			         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select name="month" id="slt2" onchange="optionDisabled(this);">
						<option value="-1">====<bean:message
								key="tawSupplierkpiInstanceList.dark" />====</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
					</select>
					<bean:message key="tawSupplierkpiInstanceList.month" />					
				</td>

			</tr>
			<tr align="right" height="40">
				<td colspan="4">
				<html:submit><bean:message key="button.querys" /></html:submit>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				
			</tr>
		</table>
	</html:form>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
