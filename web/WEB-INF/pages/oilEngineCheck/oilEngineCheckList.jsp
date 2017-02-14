<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.oilEngineCheck.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'oilEngineCheckForm'});
	v1 = new eoms.form.Validation({form:'importForm'});
});

	function goadd(){
		window.open("${app}/oilEngineCheck/oilEngineCheck.do?method=add");
		}
	function chooseAll(){	
	    var objs = document.getElementsByName("checkbox11");    
	    if(checkflag=="false"){
	        for(var i=0; i<objs.length; i++){
	           objs[i].checked="checked";
	        } 
	        checkflag="checked";
	    }
	    else if(checkflag=="checked"){ 	    	    
		    for(var i=0; i<objs.length; i++){
		           objs[i].checked=false;
		    } 
		    checkflag="false";
	    }
	    
	}

	function isChecked(){
	    var objs = document.getElementsByName("checkbox11");
	    document.forms[1].action = "${app}/oilEngineCheck/oilEngineCheck.do?method=remove";
	    var flag = false;
	    for(var i=0; i<objs.length; i++){
	       if(objs[i].checked==true){
	           flag=true;
	           break;
	       }
	    }
	    if(flag==true){
			var formObj = document.getElementById("deleteAllForm");
 			formObj.submit();
	       return true;
	    }
	    else if(flag==false){
	        alert("请选择删除项！");
	        return false;
	    }
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
<html:form action="/oilEngineCheck.do?method=xquery" styleId="oilEngineCheckForm" method="post"> 
<fmt:bundle basename="com/boco/eoms/oilEngineCheck/config/applicationResources-oilEngineCheck">
  <table class="formTable">
			<caption>
				<div class="header center">
					<fmt:message key="oilEngineCheck.list.heading" />
				</div>
			</caption>
        <tr>
        <td class="label">
			<fmt:message key="oilEngineCheck.oil_number" />
		</td>
		<td class="content">
		<html:text property="oil_number" styleId="oil_number"
						styleClass="text medium" onblur="testCharSize(this,32);" />
		</td>
		<td class="label">
			<fmt:message key="oilEngineCheck.type" />
		</td>
				<td class="content">
				<html:text property="type" styleId="type"
						styleClass="text medium" />
				</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="oilEngineCheck.area_id" />
		</td>
				<td class="content">
			<select name="area_id" id="area_id"
				alt="allowBlank:true,vtext:'请选择所在地市'"
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
			<fmt:message key="oilEngineCheck.dept_id" />
		</td>
				<td class="content">
			<select name="dept_id" id="dept_id" 
				alt="allowBlank:true,vtext:'请选择所属公司'">
					<option value="">--请选择所属公司--</option>
			</select>
			<input type="button" class="btn" onclick="JavaScript:searchWin('dept_id');" value=" 搜 索 "><span id="dept_idspan"></span>
		</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="oilEngineCheck.factory" />
		</td>
				<td class="content">
				<html:text property="factory" styleId="factory"
						styleClass="text medium" />
				</td>
        <td class="label">
			<fmt:message key="oilEngineCheck.power" />
		</td>
				<td class="content">
				<html:text property="power" styleId="power"
						styleClass="text medium" />
				</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="oilEngineCheck.startWay" />
		</td>
				<td class="content">
				<html:text property="startWay" styleId="startWay"
						styleClass="text medium" />
				</td>
		<td class="label">
			<fmt:message key="oilEngineCheck.savePlace" />
		</td>
				<td class="content">
				<html:text property="savePlace" styleId="savePlace"
						styleClass="text medium"  />
				</td>
    </tr>		
     <tr>
		
				<td colspan="4" align="center">
				<html:submit styleClass="button" property="method.query">
						查询
					</html:submit>
				</td>	
				</tr>
  </table> 
 
  
  <c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
  </c:set>
  <display:table name="oilEngineCheckList" cellspacing="0" cellpadding="0"
      id="oilEngineCheckList"  pagesize="${pageSize}" class="table oilEngineCheckList" 
      export="false" 
      requestURI="${app}/oilEngineCheck/oilEngineCheck.do?method=search"
      sort="list" partialList="true" size="resultSize">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${oilEngineCheckList.id}" />
		</display:column>      
      	<display:column property="oil_number" sortable="true"
			headerClass="sortable" titleKey="oilEngineCheck.oil_number" href="${app}/oilEngineCheck/oilEngineCheck.do?method=edit" paramId="id" paramProperty="id"/>
        <display:column property="type" sortable="true"
			headerClass="sortable" titleKey="oilEngineCheck.type" />
        <display:column sortable="true"
			headerClass="sortable" titleKey="oilEngineCheck.area_id" >
		    <eoms:id2nameDB id="${oilEngineCheckList.area_id}" beanId="tawSystemAreaDao" />
		</display:column>	
        <display:column sortable="true"
			headerClass="sortable" titleKey="oilEngineCheck.dept_id" >
            <eoms:id2nameDB id="${oilEngineCheckList.dept_id}" beanId="partnerDeptDao" />
		</display:column>	
        <display:column property="factory" sortable="true"
			headerClass="sortable" titleKey="oilEngineCheck.factory" />
        <display:column property="power" sortable="true"
			headerClass="sortable" titleKey="oilEngineCheck.power" />
        <display:column property="startWay" sortable="true"
			headerClass="sortable" titleKey="oilEngineCheck.startWay" />
        <display:column property="savePlace" sortable="true"
			headerClass="sortable" titleKey="oilEngineCheck.savePlace" />
  </display:table>
</fmt:bundle>
		<input type="button" name="add" id="add" value="新增" onclick="goadd();"/> 
		<input type="button" name="deleteGps" id="deleteGps" value="删除" onclick="deleteAll();"/> 
</html:form>
<form id="deleteAllForm" action="oilEngineCheck.do?method=removeall" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</form>
<%@ include file="/common/footer_eoms.jsp"%>