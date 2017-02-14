<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.spare.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrSparePieceForm'});
	v1 = new eoms.form.Validation({form:'importForm'});
});

	function goadd(){
		window.open("${app}/spare/sparePiece.do?method=add");
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
<html:form action="/sparePiece.do?method=xquery" styleId="pnrSparePieceForm" method="post"> 
<fmt:bundle basename="com/boco/eoms/spare/config/applicationResources-spare">
  <table class="formTable">
			<caption>
				<div class="header center">
					<fmt:message key="pnrSparePiece.list.heading" />
				</div>
			</caption>
        <tr>
        <td class="label">
			<fmt:message key="pnrSparePiece.professional" />
		</td>
		<td class="content">
		<eoms:comboBox name="professional" id="professional" initDicId="11216"></eoms:comboBox>
		</td>
        <td class="label">
			<fmt:message key="pnrSparePiece.spareName" />
		</td>
				<td class="content">
				<html:text property="spareName" styleId="spareName"
						styleClass="text medium" />
				</td>
    </tr>
    <tr>
        <td class="label">
			<fmt:message key="pnrSparePiece.region" />&nbsp;*
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
			<fmt:message key="pnrSparePiece.city" />&nbsp;*
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
			<fmt:message key="pnrSparePiece.spareType" />
		</td>
				<td class="content">
				<html:text property="spareType" styleId="spareType"
						styleClass="text medium" />
				</td>
        <td class="label">
			<fmt:message key="pnrSparePiece.storehouseName" />
		</td>
				<td class="content">
				<html:text property="storehouseName" styleId="storehouseName"
						styleClass="text medium" />
				</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="pnrSparePiece.number" />
		</td>
				<td class="content">
				<html:text property="number" styleId="number"
						styleClass="text medium" />
				</td>
		<td class="label">
			<fmt:message key="pnrSparePiece.contactMan" />
		</td>
				<td class="content">
				<html:text property="contactMan" styleId="contactMan"
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
  <display:table name="pnrSparePieceList" cellspacing="0" cellpadding="0"
      id="pnrSparePieceList"  pagesize="${pageSize}" class="table pnrSparePieceList" 
      export="false" 
      requestURI="${app}/spare/sparePiece.do?method=search"
      sort="list" partialList="true" size="resultSize">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${pnrSparePieceList.id}" />
		</display:column>	
        <display:column property="spareName" sortable="true"
			headerClass="sortable" titleKey="pnrSparePiece.spareName"  href="${app}/spare/sparePiece.do?method=edit" paramId="id" paramProperty="id"/>      
      	<display:column sortable="true"
			headerClass="sortable" titleKey="pnrSparePiece.professional" >
        	<eoms:id2nameDB id="${pnrSparePieceList.professional}" beanId="ItawSystemDictTypeDao" />
        </display:column>	
	<!-- 所在地市 -->
        <display:column sortable="true"
			headerClass="sortable" titleKey="pnrSparePart.region" >	
		<eoms:id2nameDB id="${pnrSparePieceList.region}" beanId="tawSystemAreaDao" />
	    </display:column>			

	<!-- 所在县区 -->
	    <display:column  sortable="true" headerClass="sortable" titleKey="pnrSparePart.city">
		<eoms:id2nameDB id="${pnrSparePieceList.city}" beanId="tawSystemAreaDao" />
	    </display:column>				
		<display:column property="spareType" sortable="true"
			headerClass="sortable" titleKey="pnrSparePiece.spareType" />
        <display:column property="storehouseName" sortable="true"
			headerClass="sortable" titleKey="pnrSparePiece.storehouseName" />
        <display:column property="number" sortable="true"
			headerClass="sortable" titleKey="pnrSparePiece.number" />
        <display:column property="contactMan" sortable="true"
			headerClass="sortable" titleKey="pnrSparePiece.contactMan" />
  </display:table>
</fmt:bundle>
		<input type="button" name="add" id="add" value="新增" onclick="goadd();"/> 
		<input type="button" name="deleteGps" id="deleteGps" value="删除" onclick="deleteAll();"/> 
</html:form>
<form id="deleteAllForm" action="sparePiece.do?method=removeall" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</form>
<%@ include file="/common/footer_eoms.jsp"%>