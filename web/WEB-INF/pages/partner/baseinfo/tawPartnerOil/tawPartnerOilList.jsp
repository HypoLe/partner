<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
	
<c:if test="${in!=null}">
<%@ include file="/WEB-INF/pages/partner/baseinfo/hrefSearch.jsp"%>
</c:if>
<c:set var="buttons">
   <html:button style="margin-right: 5px" property="button1" onclick="goadd()" styleClass="btn">
   	        <fmt:message key="button.add"/>
   </html:button> 
		<html:button style="margin-right: 5px" property="button1" onclick="deleteAll()" styleClass="btn"> 
	        <fmt:message key="button.delete"/>
	    </html:button>	
    
</c:set>
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
	<html:form action="tawPartnerOils.do?method=xquery&in=${in}&nodeId=${nodeId}" method="post"
		styleId="tawPartnerOilForm">
		<table class="formTable">
			<caption>
				<div class="header center">
					<fmt:message key="tawPartnerOil.list.heading" />
				</div>
			</caption>
			<tr>
				<td class="label">
					<fmt:message key="tawPartnerOil.oil_number" />
				</td>
				<td class="content">
					<html:text property="oil_number" styleId="oil_number"
						styleClass="text medium "/>
		        </td>
		<td class="label">
			<fmt:message key="tawPartnerOil.factory" />
		</td>
		<td class="content">
<!-- 	<eoms:comboBox name="factory" id="factory" initDicId="11208"
					defaultValue='${tawPartnerOilForm.factory}' alt="allowBlank:false,vtext:''" />
 -->	
 
 			<html:text property="factory" styleId="factory"
						styleClass="text medium"/>
		
		
		</td>
				</tr>
				<tr>
				<td class="label">
					<fmt:message key="tawApparatus.area_id" />
				</td>
		    <td class="content">
			<select name="area_id" id="area_id"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changePartner(0);">
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
		<td class="label">
			<fmt:message key="tawPartnerOil.dept_id" />
		</td>
		<td class="content" >
			<!-- 合作伙伴 -->			
			<select name="dept_id" id="dept_id" 
				alt="allowBlank:false,vtext:'请选择所属公司'">
					<option value="">--请选择所属公司--</option>
			</select>
			<input type="button" class="btn" onclick="JavaScript:searchWin('dept_id');" value=" 搜 索 "><span id="dept_idspan"></span>
		</td>
		
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.type" />
		</td>
		<td class="content">
<!-- 	<eoms:comboBox name="type" id="type" initDicId="11209"
					defaultValue='${tawPartnerOilForm.type}' alt="allowBlank:false,vtext:''" />
 -->	
 		<html:text property="type" styleId="type"
						styleClass="text medium"/>
		</td>
		
		<td class="label">
			<fmt:message key="tawPartnerOil.state" />
		</td>
		<td class="content">
		<eoms:comboBox name="state" id="state" initDicId="11205"/>
		</td>
		</tr>
		<input type="hidden" name="nodeId" value="<%=request.getAttribute("nodeId") %>">
		<tr>
		
		<td class="label">
			<fmt:message key="tawPartnerOil.storage" />
		</td>
		<td class="content" colspan="3">
			<html:text property="storage" styleId="storage"
						styleClass="text medium"/>
		</td>
		</tr>
		<tr>
		
				<td colspan="4" align="center">
				<html:submit styleClass="button" property="method.query">
						查询
					</html:submit>
				</tr>
		</table>
	</html:form>
<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>	
	<display:table name="tawPartnerOilList" cellspacing="0" cellpadding="0"
		id="tawPartnerOilList" pagesize="${pageSize}" class="table tawPartnerOilList"
		export="false" 
		requestURI="${app}/partner/baseinfo/tawPartnerOils.do?method=search"
		sort="list" partialList="true" size="resultSize">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${tawPartnerOilList.id}" />
		</display:column> 
	<display:column property="oil_number" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.oil_number" href="${app}/partner/baseinfo/tawPartnerOils.do?method=edit" paramId="id" paramProperty="id"/>

			
	<display:column property="factory" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.factory" />
			
	<display:column sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.dept_id" >
     <eoms:id2nameDB id="${tawPartnerOilList.area_id}" beanId="tawSystemAreaDao" />
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.dept_id" >
     <eoms:id2nameDB id="${tawPartnerOilList.dept_id}" beanId="partnerDeptDao" />
	</display:column>

	<display:column property="type" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.type" />



	<display:column sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.power" >
					<eoms:id2nameDB id="${tawPartnerOilList.power}" beanId="ItawSystemDictTypeDao" />
			</display:column>

	<display:column sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.kind" >
					<eoms:id2nameDB id="${tawPartnerOilList.kind}" beanId="ItawSystemDictTypeDao" />
			</display:column>

	<display:column  sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.state" >
					<eoms:id2nameDB id="${tawPartnerOilList.state}" beanId="ItawSystemDictTypeDao" />
			</display:column>

	<display:column property="storage" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.storage" />

		<display:setProperty name="paging.banner.item_name" value="tawPartnerOil" />
		<display:setProperty name="paging.banner.items_name" value="tawPartnerOils" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>
<form id="deleteAllForm" action="tawPartnerOils.do?method=removeall" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</form>
<script type="text/javascript">

var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerOils.do?method=changeDep2&id="+id;
			 var fieldName = "dept_id";
			 var deptId ="<%=((TawPartnerOilForm) request
							.getAttribute("tawPartnerOilForm")).getDept_id()%>";
			 changeList(url,fieldName,deptId);
function changeDep()
		{    
			 var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerOils.do?method=changeDep2&id="+id;
			 var fieldName = "dept_id";
			 changeList(url,fieldName,"");
		}
   	function deleteAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
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
	function goadd(){
		window.open("${app}/partner/baseinfo/tawPartnerOils.do?method=add");
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
	        delAllOption("dept_id");//地市选择更新后，重新刷新合作伙伴
			//地市
			var regionValue = document.getElementById("area_id").value;
			var url="${app}/partner/inspect/ajaxRequest.do?method=requestPartnerDeptByRegionOrCity&region="+regionValue;
			//var result=getResponseText(url);
			Ext.Ajax.request({
							url : url ,
							method: 'POST',
							//params:{providerValue:providerValue},
							success: function ( result, request ) { 
			res = result.responseText;
			var json = eval(res);
			var obj=$("dept_id");
			for(i=0;i<json.length;i++){
				var opt=new Option(json[i].name,json[i].id);
				obj.options[i]=opt;
			}
			setDefaultValueOnSelect("dept_id","${dept_id}");
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
<%@ include file="/common/footer_eoms.jsp"%>
