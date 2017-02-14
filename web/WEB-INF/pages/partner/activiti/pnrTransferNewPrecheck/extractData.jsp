<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<%
	Calendar cld = Calendar.getInstance();
	cld.set(Calendar.DAY_OF_MONTH,cld.getActualMinimum(Calendar.DAY_OF_MONTH));
	Date strDateFrom = cld.getTime();
	java.text.DateFormat dfp = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
	String beginTime = dfp.format(strDateFrom) + " 00:00:00";
	String endTime = com.boco.eoms.common.util.StaticMethod.getCurrentDateTime("yyyy-MM-dd") + " 23:59:59";
%>
<html>
<head>
<style type="text/css">     
    .mask {       
            position: absolute; top: 0px; filter: alpha(opacity=60); background-color: #777;     
            z-index: 1002; left: 0px;     
            opacity:0.5; -moz-opacity:0.5;     
        }     
</style> 
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"; 
		%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>		
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
 Ext.onReady(function(){
		
  });
		//重置
		function newReset(){
			document.getElementById("sheetAcceptLimit").value="";            // 派单开始时间
			document.getElementById("sheetCompleteLimit").value="";		// 派单结束时间
		}
		
 function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         }	
         
     }		
   function checkSubmit(){
 	var sheetAcceptLimit=jq("#sheetAcceptLimit").val();
 	var sheetCompleteLimit=jq("#sheetCompleteLimit").val();
 	var startDate=StringToDate(sheetAcceptLimit);
    var endDate=StringToDate(sheetCompleteLimit);
 	var urlStr="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=extractData";
 	if(sheetAcceptLimit=="" || sheetCompleteLimit==""){
 		alert("请正确填写提取时间段!");
 	}else if(startDate>endDate){
 		alert("开始时间不能晚于结束时间!");
 	}else if(parseInt(Math.abs(endDate - startDate) / 1000 / 60 / 60 / 24)>31){
 		alert("提取时间段在一个月内!");
 	}else{
 		 jq("#mask").css("height",jq("#sheetform").height());     
	     jq("#mask").css("width",jq(document).width());     
	     jq("#mask").show();  
 		 jq.ajax( {
			type : "POST",
			url : urlStr, 
			data : {"sheetAcceptLimit": sheetAcceptLimit,"sheetCompleteLimit":sheetCompleteLimit},
			success : function(data){ // 回调函数
				alert(data);
				jq("#mask").hide();     
			}	
		});
 	}
}	 
	
	
	
function StringToDate(s) { 
	//alert("得到的日期字符串 ： " + s); 
	
	var d = new Date(); 
	d.setYear(parseInt(s.substring(0,4),10)); 
	d.setMonth(parseInt(s.substring(5,7)-1,10)); 
	d.setDate(parseInt(s.substring(8,10),10)); 
	d.setHours(parseInt(s.substring(11,13),10)); 
	d.setMinutes(parseInt(s.substring(14,16),10)); 
	d.setSeconds(parseInt(s.substring(17,19),10)); 
	
	return d; 
}
	
</script>
		
		<div id="mask" class="mask" style="text-align:center;display:none;line-height:100px;">数据提取中,请稍后...</div>
		<div id="sheetform">
			<form action="/pnrTransferNewPrecheck.do?method=extractData">
				<table class="formTable"  style="width:100%">
					<!--时间 -->
					<tr>
						
						<td class="content" style="width:20%">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="<%=beginTime %>"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'开始时间不能晚于结束时间',allowBlank:false" />
							至<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="<%=endTime %>"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'结束时间不能早于开始时间',allowBlank:false" />
						</td>
						
					
					</tr>
					<tr>
						<td colspan="1">
							<input type="button" class="bin" value="点击提取数据" onclick="checkSubmit()" />	
						</td>
					</tr>
				</table>
			</form>
			<br>
			<br>
			<br>
		</div>
		
		
		<div>
			<h5>规则说明:</h5><br><br>
			
			1、提取时间段不可大于一个月.<br><br>
			2、从商城表查询数据结果如果超出100000条,程序不会执行提取操作.<br><br>
			3、如果条件满足执行提取操作,提取规则：针对mysql数据库netshop_scene表中的other_sys_number字段和oracle数据库pnr_act_oper_return_result<br>
			   表中的PROCESS_INSTANCE_ID（工单号）做比对,如果oracle数据库中有此条数据不提取,如果没有则从mysql数据库中提取此条记录到oracle数据库中.
			<br><br><br>
			
		</div>
		<div>
			<h5>数据库链接配置:</h5><br><br>
			1、data_extraction.properties配置文件中配置了mysql数据库和oracle数据库的链接方式.<br><br>
		</div>
		

		<%@ include file="/common/footer_eoms.jsp"%>