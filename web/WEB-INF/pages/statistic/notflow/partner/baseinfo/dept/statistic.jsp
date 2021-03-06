<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="com.boco.eoms.commons.statistic.base.config.excel.* ,java.util.*"%>
<%
	Excel excelConfig =(Excel) request.getAttribute("excelConfig");
	if (excelConfig == null) throw new Exception("读取配置统计文件失败!");
	
	String excelConfigURL = String.valueOf(request.getAttribute("excelConfigURL"));
	String findListForward = String.valueOf(request.getAttribute("findListForward"));
	Calendar cld = Calendar.getInstance();
	int year = cld.get(Calendar.YEAR);
	int mondth = cld.get(Calendar.MONTH) + 1;
%>
<script type="text/javascript">
var	userTreeAction='${app}/xtree.do?method=dept';
var treeAction='${app}/xtree.do?method=userByDept';
var userByDeptTree='${app}/xtree.do?method=userFromDept';//部门用户树
var	roleTree='${app}/xtree.do?method=roleTree'; //角色树
var subroleFromDept='${app}/xtree.do?method=subroleFromDept'; //部门角色树
var v;

</script>
	
<html:form action="/stat.do?method=performStatistic" styleId="theform">


 <table class="formTable middle" align="left"  >
   <caption>输入条件</caption>   
   <tr>
   		<td>
			<input type="hidden" name="excelConfigURL" value="<%=excelConfigURL %>">  
			<input type="hidden" name="findListForward" value="<%=findListForward %>"> 
			<input type="hidden" name="reportIndex"  value="2">			
   		</td> 
   </tr>
   
   
            <tr>
               <td noWrap class="label">
              <!-- <bean:message bundle="statistic" key="statistic.sendtime" /> -->
                 地市名称
           	   <select name="areaName">
            	<c:forEach var="area"  items="${areaList}">
             	   <option value="${area.nodeName }" >${area.nodeName }</option>
                </c:forEach>
           	  </select>
              </td>
            </tr>
		<tr>
			<td align="left" colspan="2">
				<html:submit styleClass="btn" property="method.save"
					styleId="method.save" >
					<bean:message bundle="statistic" key="button.done" />
				</html:submit>
			</td>
		</tr>
          </table>

  <br/>	
  <!-- buttons 

     <html:submit styleClass="btn" property="method.save" styleId="method.save">
				<bean:message bundle="statistic" key="button.done"/>
     </html:submit>  -->

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
