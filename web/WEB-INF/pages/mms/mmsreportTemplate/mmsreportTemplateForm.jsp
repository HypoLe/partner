<%@ page language="java" import="java.util.*,java.util.List,com.boco.eoms.commons.mms.base.config.*,com.boco.eoms.base.util.StaticMethod" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%

	List sheetList = (List)request.getAttribute("sheetList");
	String uri = request.getRequestURI();
	Report[] reports = (Report[])request.getAttribute("reports");
 %>
 
 <SCRIPT type="text/javascript">
        var req;
        window.onload=function(){
        }
        
        function Change_Select()
        {
            var zhi=document.getElementById('statReportType').value;
            
            //http://127.0.0.1:8085/eoms/mms/mmsreportTemplates.do?method=add
            //var url="select?id="+escape(zhi);
            var url="<%=request.getContextPath() + "/" + "mms"%>/mmsreportTemplates.do?method=selectMenu&id=" + escape(zhi);
            //alert(url);
           
            if(eoms.isIE){
	    	try{
	    		req = new ActiveXObject("Msxml2.XMLHTTP"); 
	    	}catch(e){
	    		req = new ActiveXObject("Microsoft.XMLHTTP");    		
	    	}
		    }else{
		    	req = new XMLHttpRequest();
		    }
            
            if(req)
            {
                req.open("GET",url,true);
                req.onreadystatechange=callback;
                req.send(null);
            }
        }
        
        function callback()
        {
            if(req.readyState == 4)
            {
                if(req.status == 200)
                {
                    parseMessage();
                }else{
                    alert("Not able to retrieve description"+req.statusText);
                }
            }
        }
        
        function parseMessage()
        {
            var xmlDoc=req.responseXML.documentElement;
            var xSel=xmlDoc.getElementsByTagName('select');
            var select_root=document.getElementById('skill');
            //select_root.options.length=0;
            
            var div = document.getElementById("statReportIdsDiv").innerHTML="";
            
            //alert(xSel.length);
            for(var i=0;i<xSel.length;i++)
            {
                var xValue=xSel[i].childNodes[0].firstChild.nodeValue;
                var xText=xSel[i].childNodes[1].firstChild.nodeValue;
                //alert(xValue);
                //alert(xText);
                //var option=new Option(xText,xValue);
               // var checkbox=new CheckBox(xValue,xText,false);
                //statReportIds.appendChild(checkbox);
		     	 //statReportIds.insertAdjacentHTML("beforeEnd","<input   type=checkbox  id=statReportIds  name=statReportIds   value='"+   xValue   +"'>"+ xText);
		     	 
		     	var div = document.getElementById("statReportIdsDiv"); 
		     	var check = document.createElement("input"); 
           		check.type = "checkbox"; 
          		check.id = "statReportIds"; 
           		check.name = "statReportIds";
           		check.value=xValue;
           		
           		var text = document.createTextNode(xText + "  "); 
           		div.appendChild(check); 
           		div.appendChild(text); 
           		
           		if(i!=0 && i%5==0)
           		{
           			//换行
           			//alert("i:" +  i);
           			//div.innerHTML=div.innerHTML+"</br>";
           		}
            }
        }
        
    </SCRIPT>
    
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'mmsreportTemplateForm'});
});

function submitCheck(){
	
	if(document.forms[0].mmsName.value=='')
	{
   		alert( "请输入彩信报名称" );
    	return false;
  	}
	
	//判断多选框
    var form1 = document.forms[0];
	//alert(form1.statReportIds[0].checked + 'checked');
	var check = false;
	var arrayCheckBox = document.getElementsByName("statReportIds");
	//alert(arrayCheckBox.length);
	for(var i=0;i<arrayCheckBox.length;i++){
		//alert(form1.statReportIds[i].checked);
		if(arrayCheckBox[i].checked == true){
			check = true;
		}	
	}
	
	if(check)
	{
		document.forms[0].submit();
	}
	else
	{
		alert( "请选择定制的报表");
	}
	
	//alert(document.forms[0].statReportIds);
  //if(document.forms[0].statReportIds.value=="" || document.forms[0].statReportIds.value=='undefine' ){
  //  alert( "请选择定制的报表");
  //  return false;  
  //}
  
  //document.forms[0].submit();
}

</script>

<html:form action="/mmsreportTemplates.do?method=save" styleId="mmsreportTemplateForm" method="post"> 

<fmt:bundle basename="config/applicationResources-mms">

<table class="formTable">
	<caption>
		<div class="header center">定制彩信报</div>
	</caption>

 <tr class="tr_show">
          <td class="label" width="2%" nowrap="nowrap" align="center">
    	          <input type="radio" name="executeCycle" value="dailyReport"checked>
    	   </td>
          <td noWrap width="15%">
    	          日报统计
    	   </td>
    	    <td noWrap width="85%">
	         	    统计时间段：从昨天0点到昨天23点59分，报表呈现的时间是今日     
	       </td>
	
	</tr>
	
		<tr class="tr_show">
         <td class="label" width="2%" nowrap="nowrap" align="center">
              <input type="radio" name="executeCycle" value="weekReport">
        </td>
        <td noWrap width="15%">	
        	     周报统计
       </td>
        <td width="85%">
    		       统计选择起始时间:   
        <select name="reportCreatDate" style="width: 2.0cm;">
        <option value="1">周一</option>
        <option value="2">周二</option>
        <option value="3">周三</option>
        <option value="4">周四</option>
        <option value="5">周五</option>
        <option value="6">周六</option>
        <option value="7">周日</option>
        </select>	
      	   以此为周期    
        </td>
    </tr>	
    
	
 	<tr class="tr_show">
         <td class="label" width="2%" nowrap="nowrap" align="center">
            <input type="radio" name="executeCycle" value="monthReport" >
        </td>
        <td  width="15%">
            	月报统计
        </td>
        <td width="85%">
    	       	统计选择起始时间:  
          <select name="reportMonthCreatDate" style="width: 2.0cm;">
          <option value="1" >1号</option>
          <option value="2" >2号</option>
          <option value="3">3号</option>
          <option value="4">4号</option>
          <option value="5">5号</option>
          <option value="6">6号</option>
          <option value="7">7号</option>
          <option value="8">8号</option>
          <option value="9">9号</option>
          <option value="10">10号</option>
          <option value="11">11号</option>
          <option value="12">12号</option>
          <option value="13">13号</option>
          <option value="14">14号</option>
          <option value="15">15号</option>
          <option value="16">16号</option>
          <option value="17">17号</option>
          <option value="18">18号</option>
          <option value="19">19号</option>
          <option value="20">20号</option>
          <option value="21">21号</option>
          <option value="22">22号</option>
          <option value="23">23号</option>
          <option value="24">24号</option>
          <option value="25">25号</option>
          <option value="26">26号</option>
          <option value="27">27号</option>
          <option value="28">28号</option> 
          <option value="29">29号</option>
          <option value="30">30号</option>
          <option value="31">31号</option>
          </select>	
          	以此为周期
        </td>
    </tr>
    	
	<tr>
		<td class="label">
		</td>
		
		 <td  width="15%">
			彩信报表名称：
		</td>
		<td class="content">
			<html:text property="mmsName" styleId="mmsName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${reportTemplateForm.reportTemplateName}" />
		</td>
	</tr>
	
	<!-- 
	<tr>
		<td class="label">
		</td>
		<td>
			报表显示类型：
		</td>
		<td class="content">
			<select name="reportDisplayType">
				<option value="stat">表格</option>
				<option value="column">柱图</option>
				<option value="line">线图</option>
				<option value="pie">饼图</option>
				<option value="columnline">线柱结合图</option>
			</select>
		</td>
	</tr>
	
	<tr>
		<td class="label">
		</td>
	
		<td>
			报表图片格式：
		</td>
		<td class="content">
			<select name="pictureFormat">
				<option value="gif">gif</option>
				<option value="jpg">jpg</option>
				<option value="png">png</option>
			</select>
		</td>
	</tr>
	 -->
	<tr>
		<td class="label">
		</td>
		<td width="25%" >
			选择报表类型：
		</td>
		<td>
			<select name="statReportType" id="statReportType" onChange="Change_Select()">
				<option>报表类型 </option>
			  <%
			  	for(int r=0;r<reports.length;r++)
			  	{
			  		String name = reports[r].getType();
			  		String value = reports[r].getId();
			   %>
			   	<option value="<%=value %>"><%=name %></option>
			   <%
			   	}
			    %>
			 </select>
		</td>
	</tr>
	
	<tr>
		<td class="label">
		
		</td>
		
		<td>
		选择报表：
		</td>
		
		<td>
        	<div  name="statReportIdsDiv" id="statReportIdsDiv"></div>
		</td>
	</tr>

	<tr>
		<td class="label">
		</td>
		
		<td>
			彩信报说明：
		</td>
		<td class="content">
			<html:textarea property="mmsReportDesc" styleId="mmsReportDesc"  rows="6" cols="100"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${mmsreportTemplateForm.mmsReportDesc}" />
		</td>
	</tr>

 
</table>

</fmt:bundle>
  
<table>
	<tr>
		<td>
			 <!--<input type="submit" class="btn" value="<fmt:message key="button.save"/>" "/> -->
			<input type="button" class="btn" value="定制"  return onclick=submitCheck() />
		</td>
	</tr>
</table>
<html:hidden property="id" value="${reportTemplateForm.id}" />

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>