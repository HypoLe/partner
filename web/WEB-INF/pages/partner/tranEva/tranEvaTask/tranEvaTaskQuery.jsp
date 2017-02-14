<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.tranEva.util.TranEvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'tranEvaKpiInstanceForm'});
});
function query(){
	var taskId = document.getElementById("taskId").value;
	var year1 = document.getElementById("year1").value;
	var month1 = document.getElementById("month1").value;
	var year2 = document.getElementById("year2").value;
	var month2 = document.getElementById("month2").value;
	var time1 = year1 + month1;
	var time2 = year2 + month2;
	if(""==taskId.trim()){
		alert("请选择考核任务!");
		document.forms[0].taskId.focus();
	}
	else if(""==year1.trim()&&""!=month1.trim()){
		alert("请输入开始年份!");
		document.forms[0].year1.focus();
	}
	else if(""!=year1.trim()&&""==month1.trim()){
		alert("请输入开始月份!");
		document.forms[0].month1.focus();
	}
	else if(""==year2.trim()&&""!=month2.trim()){
		alert("请输入结束年份!");
		document.forms[0].year2.focus();
	}
	else if(""!=year2.trim()&&""==month2.trim()){
		alert("请输入结束月份!");
		document.forms[0].month2.focus();
	}
	else if(""!=time1.trim()&&""!=time2.trim()&&time1 > time2){
		alert("开始时间不得晚于结束时间!");
		document.forms[0].year1.focus();
	}
	else {
		document.forms[0].submit();
	}
};

 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }

function changeTemplateTypeAndTime()
		{    		    
		    var year1 = document.getElementById("year1").value;
	        var month1 =  document.getElementById("month1").value;
	        var year2 =  document.getElementById("year2").value;
	        var month2 =  document.getElementById("month2").value;
	        var templateTypeId = document.getElementById("templateTypeId").value;
		    if(year1!=""&&month1!=""&&year2!=""&&month2!=""&&templateTypeId!=""){
			    delAllOption("taskId");//考核模版类型改变后，需要把考核模版处的选项清除，不然选项会乱
				var para = "year1="+year1+"&month1="+month1+"&year2="+year2+"&month2="+month2+"&queryType=2";
				var url="<%=request.getContextPath()%>/partner/tranEva/tranEvaTasks.do?method=changeTemplateTypeAndTime&templateTypeId="+templateTypeId;
				var fieldName = "taskId";
				var result=getResponseText(url,para);
				 
				var arrOptions=result.split(",");
				var obj=$(fieldName);
				var i=0;
				var j=0;
				for(i=0;i<arrOptions.length;i++){
					var opt=new Option(arrOptions[i+1],arrOptions[i]);
					obj.options[j]=opt;
					j++;
					i++;
				}
			}
		}
function getResponseText(url,para) {
    var xmlhttp;
    if(eoms.isIE){
    	try{
    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); 
    	}catch(e){
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");    		
    	}
    }else{
    	xmlhttp = new XMLHttpRequest();
    }
    
    var method = "post";
    url=url+"&"+new Date();
    xmlhttp.open(method, url, false);
    //xmlhttp.setRequestHeader("content-type", "text/html; charset=GBK");
    xmlhttp.setRequestHeader("Content-Type" , "application/x-www-form-urlencoded" );      
    xmlhttp.send(para);
    return xmlhttp.responseText;
}
</script>

<html:form action="/tranEvaTasks.do?method=xquery" styleId="tranEvaKpiInstanceForm" method="post"> 
<table class="formTable" id="form" name="form">
	<caption>
		<div class="header center">考核结果查询</div>
	</caption>
	<tr>
		<td class="label">
			选择开始时间
		</td>
		<td class="content">
			<select name="year1" id="year1" alt="vtext:'请选择年份'" onchange="changeTemplateTypeAndTime();">
				<option value="">请选择</option>
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
			</select>年
			<select name="month1" id="month1" alt="vtext:'请选择月份'" onchange="changeTemplateTypeAndTime();">
				<option value="">请选择</option>
				<option value="01">1</option>
				<option value="02">2</option>
				<option value="03">3</option>
				<option value="04">4</option>
				<option value="05">5</option>
				<option value="06">6</option>
				<option value="07">7</option>
				<option value="08">8</option>
				<option value="09">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
			</select>月
		</td>
		<td class="label">
			选择结束时间
		</td>
		<td class="content">
			<select name="year2" id="year2" alt="vtext:'请选择年份'" onchange="changeTemplateTypeAndTime();">
				<option value="">请选择</option>
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
			</select>年
			<select name="month2" id="month2" alt="vtext:'请选择月份'" onchange="changeTemplateTypeAndTime();">
				<option value="">请选择</option>
				<option value="01">1</option>
				<option value="02">2</option>
				<option value="03">3</option>
				<option value="04">4</option>
				<option value="05">5</option>
				<option value="06">6</option>
				<option value="07">7</option>
				<option value="08">8</option>
				<option value="09">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
			</select>月
		</td>
	</tr>	
	<tr>
		<td class="label">
			选择考核模板
		</td>
		<td class="content" colspan="3">
			<select name="templateTypeId" id="templateTypeId"
					alt="allowBlank:false,vtext:'请选择考核模板分类'"
					onchange="changeTemplateTypeAndTime();">
					<option value="">
						--请选择模板分类--
					</option>
					<logic:iterate id="templateType" name="templateType">
						<option value="${templateType.nodeId}">
							<eoms:id2nameDB id="${templateType.nodeId}" beanId="tranEvaTreeDao" />
						</option>
					</logic:iterate>
				</select>
				<select name="taskId" id="taskId"
					alt="allowBlank:false,vtext:'请选择考核模板'">
					<option value="">
						--请选择模板--
					</option>
				</select>
		</td>
	</tr>
<input type="hidden" name="specialty" value="${requestScope.specialty}"/>	
</table>
<table id="submit-btn" align="left"> 
	<tr>
		<td>
			<input type="button" class="btn" value="查询" onclick="query();" />
		</td>
	</tr>
</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>