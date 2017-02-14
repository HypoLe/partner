<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.spare.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">

    var region = '${pnrSparePartForm.region}';
    var city = '${pnrSparePartForm.city}';
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrSparePartForm'});
		if(region!=''){
		setDefaultValueOnSelect("region",region);
 		changePartner();
	}
});
 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
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
function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
            
        }
 function searchByName(id,value){     
    var spl=value.toLowerCase();//将输入值转成小写  
    var selectProjects=document.getElementById(id);     
    var options=selectProjects.options;     
    var total = -1;     
    var meetArray = new Array();     
    for(var i=0;i<options.length;i++){     
        var op_text=options[i].text.toLowerCase();//将option的文本转成小写  
        var opArray=op_text.split(spl);     
        if(spl.length>0 && opArray.length>1){  //匹配到了  
            total++;  
            meetArray[total]=i;  
        }  
    }     
    var beginIndex = 0;     
    for(var i=0;i<=total;i++){     
        var index = meetArray[i];     
        if(index!=beginIndex){     
            var tempText = options[index].text;     
            var tempValue = options[index].value;     
            options[index].text = options[beginIndex].text;     
            options[index].title = options[beginIndex].text;     
            options[index].value = options[beginIndex].value;     
            options[beginIndex].text=tempText;     
            options[beginIndex].title=tempText;     
            options[beginIndex].value=tempValue;     
        }     
        beginIndex++;  
        //selectProjects.options[i].selected = true;  
        selectProjects.options[0].selected = true;  
    }  
    document.getElementById(id+"span").innerHTML="匹配到"+(total+1)+"个选项";
}     
 function searchWin(id){ 
	Ext.MessageBox.prompt('筛选排序', '请输入关键字，支持模糊查询，匹配的选项会排在顶端！', function(btn, text) {
	searchByName(id,text);
    //alert('你刚刚点击了 ' + btn + '，刚刚输入了 ' + text);
});
}     
function changePartner(con)
		{    
			//地市
			var regionValue = document.getElementById("region").value;
			var url="${app}/partner/inspect/ajaxRequest.do?method=requestCountryOnCityChange&region="+regionValue;
			//var result=getResponseText(url);
			Ext.Ajax.request({
							url : url ,
							method: 'POST',
							//params:{providerValue:providerValue},
							success: function ( result, request ) { 
			res = result.responseText;
			var json = eval(res);
			var obj=$("city");
			for(i=0;i<json.length;i++){
				var opt=new Option(json[i].areaName,json[i].areaId);
				obj.options[i]=opt;
			}
			if(city!=''){
			setDefaultValueOnSelect("city",city);
			}
		},
		     failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
			});
		}
function setDefaultValueOnSelect(id,value) {
	document.getElementById(id).value = value;
}		
</script>
<html:form action="/sparePart.do?method=save" styleId="pnrSparePartForm" method="post"> 
<html:hidden property="id" value="${pnrSparePartForm.id}" />
<fmt:bundle basename="com/boco/eoms/spare/config/applicationResources-spare">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="pnrSparePart.form.title"/></div>
	</caption>
    <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.professional" />&nbsp;*
		</td>
		<td class="content">
			<eoms:comboBox name="professional" id="professional" initDicId="11216" defaultValue="${pnrSparePartForm.professional}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>
		<td class="label">
			<fmt:message key="pnrSparePart.factory" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="factory" styleId="factory"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrSparePartForm.factory}" />
				</td>
    </tr>
    <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.region" />&nbsp;*
		</td>
				<td class="content">
			<select name="region" id="region"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changePartner(0);">
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="cityList" name="cityList">
					<option value="${cityList.areaid}">
						${cityList.areaname}
					</option>
				</logic:iterate>
			</select>
		</td>	
		<td class="label">
			<fmt:message key="pnrSparePart.city" />&nbsp;*
		</td>
				<td class="content">
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所属公司'">
					<option value="">--请选择所属公司--</option>
			</select>
			<input type="button" class="btn" onclick="JavaScript:searchWin('city');" value=" 搜 索 "><span id="cityspan"></span>
		</td>
    </tr>    
        <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.barCode" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="barCode" styleId="barCode"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrSparePartForm.barCode}" />
				</td>
        <td class="label">
			<fmt:message key="pnrSparePart.spareName" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="spareName" styleId="spareName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrSparePartForm.spareName}" />
				</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.storehouseName" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="storehouseName" styleId="storehouseName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrSparePartForm.storehouseName}" />
				</td>
        <td class="label">
			<fmt:message key="pnrSparePart.saveSite" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="saveSite" styleId="saveSite"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrSparePartForm.saveSite}" />
				</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.storck" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="storck" styleId="storck"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrSparePartForm.storck}" />
				</td>
		<td class="label">
			<fmt:message key="pnrSparePart.spareType" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="spareType" styleId="spareType"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrSparePartForm.spareType}" />
				</td>
    </tr>
	<tr>
		<td class="label">
			<fmt:message key="pnrSparePart.applyEquip" />
		</td>
		<td class="content">
			<html:text property="applyEquip" styleId="applyEquip" 
						styleClass="text medium" 
						value="${pnrSparePartForm.applyEquip}" />
		</td>
		<td class="label">
			<fmt:message key="pnrSparePart.spareStauts" />
		</td>
		<td class="content">
			<html:text property="spareStauts" styleId="spareStauts" 
						styleClass="text medium" 
						value="${pnrSparePartForm.spareStauts}" />
		</td>
	</tr>    
		<tr>
		<td class="label">
			<fmt:message key="pnrSparePart.remark" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" onblur="testCharSize(this,255);"
						styleClass="text max" cols="40" rows="4"
						value="${pnrSparePartForm.remark}" />
		</td>
	</tr>
</table>

</fmt:bundle>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty pnrSparePartForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确认删除?')){
						var url=eoms.appPath+'/spare/sparePart.do?method=remove&id=${pnrSparePartForm.id}';
						location.href=url}"	/>		
			</c:if>							
		</td>
	</tr>
</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>