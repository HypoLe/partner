<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.oilEngineCheck.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
    var areaid = '${oilEngineCheckForm.area_id}';
    var deptid = '${oilEngineCheckForm.dept_id}';
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'oilEngineCheckForm'});
		if(areaid!=''){
		setDefaultValueOnSelect("area_id",areaid);
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
			if(dept_id != '') {
			setDefaultValueOnSelect("dept_id",deptid);
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
<html:form action="/oilEngineCheck.do?method=save" styleId="oilEngineCheckForm" method="post"> 
<html:hidden property="id" value="${oilEngineCheckForm.id}" />
<fmt:bundle basename="com/boco/eoms/oilEngineCheck/config/applicationResources-oilEngineCheck">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="oilEngineCheck.form.heading"/></div>
	</caption>
    <tr>
        <td class="label">
			<fmt:message key="oilEngineCheck.oil_number" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="oil_number" styleId="oil_number"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${oilEngineCheckForm.oil_number}" />
		</td>
		<td class="label">
			<fmt:message key="oilEngineCheck.type" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="type" styleId="type"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${oilEngineCheckForm.type}" />
				</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="oilEngineCheck.area_id" />&nbsp;*
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
			<fmt:message key="oilEngineCheck.dept_id" />&nbsp;*
		</td>
				<td class="content">
			<select name="dept_id" id="dept_id" 
				alt="allowBlank:false,vtext:'请选择所属公司'">
					<option value="">--请选择所属公司--</option>
			</select>
			<input type="button" class="btn" onclick="JavaScript:searchWin('dept_id');" value=" 搜 索 "><span id="dept_idspan"></span>
		</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="oilEngineCheck.factory" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="factory" styleId="factory"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${oilEngineCheckForm.factory}" />
				</td>
        <td class="label">
			<fmt:message key="oilEngineCheck.power" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="power" styleId="power"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${oilEngineCheckForm.power}" />
				</td>
    </tr>
        <tr>
        <td class="label">
			<fmt:message key="oilEngineCheck.startWay" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="startWay" styleId="startWay"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${oilEngineCheckForm.startWay}" />
				</td>
		<td class="label">
			<fmt:message key="oilEngineCheck.savePlace" />&nbsp;*
		</td>
				<td class="content">
				<html:text property="savePlace" styleId="savePlace"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${oilEngineCheckForm.savePlace}" />
				</td>
    </tr>
	<tr>
		<td class="label">
			<fmt:message key="oilEngineCheck.checkinfo" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="checkinfo" styleId="checkinfo" onblur="testCharSize(this,1000);"
						styleClass="text max" cols="40" rows="4"
						value="${oilEngineCheckForm.checkinfo}" />
		</td>
	</tr>
    <tr>
		<td class="label">
			<fmt:message key="oilEngineCheck.conclusions" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="conclusions" styleId="conclusions" onblur="testCharSize(this,1000);"
						styleClass="text max" cols="40" rows="4"
						value="${oilEngineCheckForm.conclusions}" />
		</td>
	</tr>    
		<tr>
		<td class="label">
			<fmt:message key="oilEngineCheck.remark" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" onblur="testCharSize(this,1000);"
						styleClass="text max" cols="40" rows="4"
						value="${oilEngineCheckForm.remark}" />
		</td>
	</tr>
</table>

</fmt:bundle>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty oilEngineCheckForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确认删除?')){
						var url=eoms.appPath+'/oilEngineCheck/oilEngineCheck.do?method=remove&id=${oilEngineCheckForm.id}';
						location.href=url}"	/>		
			</c:if>							
		</td>
	</tr>
</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>