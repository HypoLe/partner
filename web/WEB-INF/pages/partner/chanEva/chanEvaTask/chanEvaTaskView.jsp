<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.chanEva.util.ChanEvaConstants"/>
<%@ page language="java" import="java.util.*"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'chanEvaKpiInstanceForm'});
})

 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }

function changeTemplateType()
		{    
		    delAllOption("taskId");//考核模版类型改变后，需要把考核模版处的选项清除，不然选项会乱
			var templateTypeId = document.getElementById("templateTypeId").value;
			var url="<%=request.getContextPath()%>/partner/chanEva/chanEvaTasks.do?method=changeTemplateType&templateTypeId="+templateTypeId;
			var fieldName = "taskId";
			 
			var result=getResponseText(url);
			
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
		
function changeCycle()
		{    
			var cycleValue = document.getElementById("cycle").value;
			if (cycleValue=="quarter"){
				document.getElementById("monthSpan").style.display = "none";
				document.getElementById("quarterSpan").style.display = "";
			} else if (cycleValue=="month"){
				document.getElementById("monthSpan").style.display = "";
				document.getElementById("quarterSpan").style.display = "none";
			} else if (cycleValue=="year"){
				document.getElementById("monthSpan").style.display = "none";
				document.getElementById("quarterSpan").style.display = "none";
			}
		}		
function getResponseText(url) {
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
    xmlhttp.setRequestHeader("content-type", "text/html; charset=GBK");
    xmlhttp.send(null);
    return xmlhttp.responseText;
}
function checkChanEvaTime(){
    var task = document.getElementById("taskId").options[document.getElementById("taskId").selectedIndex].text;
    var year = document.getElementById("year").value;
    var month = document.getElementById("month").value;
    var time = year+"-"+month+"-01";//Date.parse(startDate)
    if(year==""||month=="")return true;
    else{
        var length = task.length;
        task = task.substring(length-11,length-3)+"01";
        if(task<=time)return true;
        else if(task>time)return false; 
    }
}
function isSubmit(){
    if(checkChanEvaTime())return true;//document.getElementById("chanEvaKpiInstanceForm").submit();
    else if(!checkChanEvaTime()){
       alert("考核月份在模板激活时间之后！");
       return false;
    }
}
</script>

<html:form action="/chanEvaTasks.do?method=taskDetailList" styleId="chanEvaKpiInstanceForm" method="post" > 
<table class="formTable" id="form">
	<caption>
		<div class="header center">考核实例执行</div>
	</caption>
	<input type="hidden" id="queryType" name="queryType" value="run"/>
	<tr>
		<td class="label">
			选择考核模板
		</td>
		<td class="content">
			<select name="templateTypeId" id="templateTypeId"
					alt="allowBlank:false,vtext:'请选择考核模板分类'"
					onchange="changeTemplateType();">
					<option value="">
						--请选择模板分类--
					</option>
					<logic:iterate id="templateType" name="templateType">
						<option value="${templateType.nodeId}">
							<eoms:id2nameDB id="${templateType.nodeId}" beanId="chanEvaTreeDao" />
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
		<%-- 
		<td class="label">
			选择合作伙伴
		</td>
		<td class="content">
		<select name="partner" id="partner"
					alt="allowBlank:false,vtext:'请选择考核厂商'">
					<option value="">
						--请选择合作伙伴--
					</option>
					<% 
					   List factoryList = (List)request.getAttribute("factoryList");
					   Map hashMap = new HashMap();
					   String partnerId = null;
					   String partnerName = null;
					   for(int i=0;i<factoryList.size();i++){
					       hashMap = (Map)factoryList.get(i);
					       partnerId = (String)hashMap.get("partnerId");
					       partnerName = (String)hashMap.get("partnerName");
					%>
						<option value="<%= partnerId%>">
							<%= partnerName%>
						</option>
	                <%} %>
				</select>
		</td>
		--%>
		<td class="label">
			地市
		</td>
		<td class="content">
						<!-- 地市 -->			
			<select name="region" id="region"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				>
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="city" name="city">
					<option value="${city.areaid}">
						${city.areaname}
					</option>
				</logic:iterate>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="label">
			时间周期类型
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-chanEva" dictId="cycle" defaultId="month" onchange="changeCycle();"
						 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核周期'" />
		</td>
		<td class="label">
			选择时间
		</td>
		<td class="content">
			<select name="year" id="year" alt="allowBlank:false,vtext:'请选择年份'">
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
			<span  id ='monthSpan'  >
			<select name="month" id="month" >
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
			</span>
			<span id ='quarterSpan' style="display:none">
			第
			<select name="quarter" id="quarter" >
				<option value="">请选择</option>
				<option value="01">1</option>
				<option value="02">2</option>
				<option value="03">3</option>
				<option value="04">4</option>
			</select>季度		
			</span>	
		</td>
	</tr>
	<input type="hidden" name="specialty" value="${requestScope.specialty}"/>	
</table>
<table id="submit-btn" align="left">
	<tr>
		<td>
			<input type="submit" class="btn" value="执行考核" onclick="if(isSubmit())return true;else return false;"/>
		</td>
	</tr>
</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>