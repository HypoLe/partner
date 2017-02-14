<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.boco.eoms.base.util.StaticMethod"%>

<script language="JavaScript">
	  function GoBack(){
          window.close()
       }
	 	Ext.onReady(function(){
	   colorRows('list-table');
    })
       

</script>
 
            <%
		String excelFilePath=request.getAttribute("excelFilePath").toString();
		String excelFileName=request.getAttribute("excelFileName").toString();
		String dataUrl=request.getAttribute("dataUrl").toString();
		
		String beginTime=StaticMethod.nullObject2String(request.getAttribute("beginTime"));
		String endTime=StaticMethod.nullObject2String(request.getAttribute("endTime"));
		
		String aurl="/statistic/logstat/stat.do?method=performStatistic&excelConfigURL=logstat_all&findListForward=all_statlist&reportIndex=5&beginTime="+beginTime+"&endTime="+endTime;
		String burl="/statistic/logstat/stat.do?method=performStatistic&excelConfigURL=logstat_all&findListForward=all_statlist&reportIndex=7&beginTime="+beginTime+"&endTime="+endTime;
		
		           %>

<div id="sheet-list">
	<div class="list-title"></div>




<table class="listTable" > 
  
  <tr class="tr_show" >    
  	<td nowrap align="left" colspan="6" width="100%">
  		  统计时间段:从<%=beginTime%>到<%=endTime%>
  	</td>		      
  </tr>
	<tr class="tr_show">
		  <td nowrap align="center" ><b>序号</b></td>	
			<td nowrap align="center" ><b>公司</b></td>			
			<td nowrap align="center" ><b>子部门</b></td>		
			<td nowrap align="center" ><b>操作模块数</b></td>
			<td nowrap align="center" ><b>操作总用户数</b></td>				
			<td nowrap align="center" ><b>操作总次数</b></td>					
	</tr>
	<% int i=0;  %>
	<logic:iterate id="STATLIST" name="STATLIST" type="com.boco.eoms.commons.statistic.logstat.webapp.action.Statbean">
	<tr class="tr_show">
		   <bean:define id="s2" name="STATLIST" property="s2" type="java.lang.String"/>
		    <bean:define id="s1" name="STATLIST" property="s1" type="java.lang.String"/>
	   	 <bean:define id="f3" name="STATLIST" property="f3" type="java.lang.String"/>
	  <td nowrap align="center"><%=++i%></td> 
	  <td nowrap align="center">
	  	      <bean:write name="STATLIST" property="s11" scope="page"/>
		 </td> 
	   <td nowrap align="center">
	      <bean:write name="STATLIST" property="s22" scope="page"/>
	   </td> 	 		 	
	  <td nowrap align="center">
	       <a href="#" onclick="javascript:window.open('${app}<%=aurl%>&revdeptid=<%=s2%>');">
	 	     	<bean:write name="STATLIST" property="f1" scope="page"/>
	 	     </a>
		</td>
		<td nowrap align="center">
		   <a href="#" onclick="javascript:window.open('${app}<%=burl%>&revdeptid=<%=s2%>');">
				 <bean:write name="STATLIST" property="f2" scope="page"/>
			 </a>
		</td>
		<td nowrap align="center">
			<% if(!"0".equals(f3)){ %>
		        <a href='<%=dataUrl%>&s2=<%=s2%>&fieldId=f3' target="_blank">
		         <bean:write name="STATLIST" property="f3" scope="page"/>
		        </a>
		          <% }else{ %> 
		         <a>	<bean:write name="STATLIST" property="f3" scope="page"/></a>
		   <% } %>  
	 </td>       
	
		
	</tr>
	
	</logic:iterate>
	
	 
 
</table>    
							
					
	<br>		
			
			       
 
			
		
				 <table>  
			  <tr >
					<td  align="left">
				 	<form method="post" action="${app}/statisticfile/download.jsp" >
		           <input type="hidden" name="excelFilePath" value="<%=excelFilePath%>">
		           <input type="hidden" name="excelFileName" value="<%=excelFileName%>">
		           <input type="submit" name="fileNamesubmit" value="导出Excel" >
          </form>
          </td>
        <td align="left">
						<INPUT id="gback" type="button"   value="关闭窗口" Onclick="GoBack();">
					</td>
				</tr>
			</table>  
      
   
       
       
       
       
       
       
         
		</div>	
<br>
<%@ include file="/common/footer_eoms.jsp"%>