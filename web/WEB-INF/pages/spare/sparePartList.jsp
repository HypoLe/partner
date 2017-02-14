<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.spare.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrSparePartForm'});
	v1 = new eoms.form.Validation({form:'importForm'});
});

	function goadd(){
		window.open("${app}/spare/sparePart.do?method=add");
		}
   	function deleteAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].defaultValue;
			idResult+=myTempStr.toString()+";"
		}
	}
	if (idResult == "") {
		alert("请选择需要删除的记录");
		return false;
	} else {
		if(confirm("确定要删除所选记录吗？")){
			$("deleteIds").value=idResult.toString();
			//var myParam=idResult.toString();
			var formObj = document.getElementById("deleteAllForm");
 			formObj.submit();
		}
		else{
			return false;
		}
	}
}

function selectAll(){
	var cardNumberList = document.getElementsByName("cardNumber");
	var temp=cardNumberList[0].checked;
	if(temp==null){
		for (i = 0 ; i < cardNumberList.length; i ++) {
		cardNumberList[i].checked='checked';
		}
	}else{
		for (i = 0 ; i < cardNumberList.length; i ++) {
		cardNumberList[i].checked=!cardNumberList[i].checked;
		}
	}
}

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
			setDefaultValueOnSelect("city","${city}");
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
<html:form action="/sparePart.do?method=xquery" styleId="pnrSparePartForm" method="post"> 
<fmt:bundle basename="com/boco/eoms/spare/config/applicationResources-spare">
  <table class="formTable">
			<caption>
				<div class="header center">
					<fmt:message key="pnrSparePart.list.heading" />
				</div>
			</caption>
        <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.professional" />
		</td>
		<td class="content">
		<eoms:comboBox name="professional" id="professional" initDicId="11216"></eoms:comboBox>
		</td>
		<td class="label">
			<fmt:message key="pnrSparePart.factory" />
		</td>
				<td class="content">
				<html:text property="factory" styleId="factory"
						styleClass="text medium" />
				</td>
    </tr>
    <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.region" />&nbsp;*
		</td>
				<td class="content">
			<select name="region" id="region"
				alt="allowBlank:true,vtext:'请选择所在地市'"
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
				alt="allowBlank:true,vtext:'请选择所属公司'">
					<option value="">--请选择所属公司--</option>
			</select>
			<input type="button" class="btn" onclick="JavaScript:searchWin('city');" value=" 搜 索 "><span id="cityspan"></span>
		</td>
    </tr>        
        <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.barCode" />
		</td>
				<td class="content">
				<html:text property="barCode" styleId="barCode"
						styleClass="text medium" />
				</td>
        <td class="label">
			<fmt:message key="pnrSparePart.spareName" />
		</td>
				<td class="content">
				<html:text property="spareName" styleId="spareName"
						styleClass="text medium" />
				</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.spareType" />
		</td>
				<td class="content">
				<html:text property="spareType" styleId="spareType"
						styleClass="text medium" />
				</td>
        <td class="label">
			<fmt:message key="pnrSparePart.storehouseName" />
		</td>
				<td class="content">
				<html:text property="storehouseName" styleId="storehouseName"
						styleClass="text medium" />
				</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.saveSite" />
		</td>
				<td class="content">
				<html:text property="saveSite" styleId="saveSite"
						styleClass="text medium" />
				</td>
		<td class="label">
			<fmt:message key="pnrSparePart.storck" />
		</td>
				<td class="content">
				<html:text property="storck" styleId="storck"
						styleClass="text medium"  />
				</td>
    </tr>	
        <tr>
        <td class="label">
			<fmt:message key="pnrSparePart.applyEquip" />
		</td>
				<td class="content">
				<html:text property="applyEquip" styleId="applyEquip"
						styleClass="text medium" />
				</td>
		<td class="label">
			<fmt:message key="pnrSparePart.spareStauts" />
		</td>
				<td class="content">
				<html:text property="spareStauts" styleId="spareStauts"
						styleClass="text medium"  />
				</td>
    </tr>	    	
     <tr>
		
				<td colspan="4" align="center">
				<html:submit styleClass="button" property="method.query">
						查询
					</html:submit>
				</tr>
  </table> 
  
  <c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
  </c:set>
  <display:table name="pnrSparePartList" cellspacing="0" cellpadding="0"
      id="pnrSparePartList"  pagesize="${pageSize}" class="table pnrSparePartList" 
      export="false" 
      requestURI="${app}/spare/sparePart.do?method=search"
      sort="list" partialList="true" size="resultSize">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${pnrSparePartList.id}" />
		</display:column>
        <display:column property="spareName" sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.spareName" href="${app}/spare/sparePart.do?method=edit" paramId="id" paramProperty="id"/>      
      	<display:column sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.professional" >
		<eoms:id2nameDB id="${pnrSparePartList.professional}" beanId="ItawSystemDictTypeDao" />
		</display:column>
        <display:column property="factory" sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.factory" />
	<!-- 所在地市 -->
        <display:column sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.region" >	
		<eoms:id2nameDB id="${pnrSparePartList.region}" beanId="tawSystemAreaDao" />
	    </display:column>			

	<!-- 所在县区 -->
	    <display:column  sortable="true" headerClass="sortable" titleKey="pnrSparePart.city">
		<eoms:id2nameDB id="${pnrSparePartList.city}" beanId="tawSystemAreaDao" />
	    </display:column>				
        <display:column property="barCode" sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.barCode" />
        <display:column property="spareName" sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.spareName" />
        <display:column property="spareType" sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.spareType" />
        <display:column property="storehouseName" sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.storehouseName" />
        <display:column property="saveSite" sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.saveSite" />
        <display:column property="storck" sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.storck" />
        <display:column property="applyEquip" sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.applyEquip" />
        <display:column property="spareStauts" sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.spareStauts" />
  </display:table>
</fmt:bundle>
		<input type="button" name="add" id="add" value="新增" onclick="goadd();"/> 
		<input type="button" name="deleteGps" id="deleteGps" value="删除" onclick="deleteAll();"/> 
</html:form>
<form id="deleteAllForm" action="sparePart.do?method=removeall" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</form>
<%@ include file="/common/footer_eoms.jsp"%>