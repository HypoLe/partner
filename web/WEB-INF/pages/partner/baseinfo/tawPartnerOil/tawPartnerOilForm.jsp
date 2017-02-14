<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
    var areaid = '${tawPartnerOilForm.area_id}';
    var deptid = '${tawPartnerOilForm.dept_id}';
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawPartnerOilForm'});
	v1 = new eoms.form.Validation({form:'importForm'});
		if(areaid!=''){
		setDefaultValueOnSelect("area_id",areaid);
 		changePartner();
	}	
});

function openImport(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
}

function saveImport() {
	//表单验证
	if(!v1.check()) {
		return;
	}
	var partnerid = document.getElementById("partnerid").value;
	if (partnerid == '请选择'){
	     alert("请选择所属公司");
	     return;
	}
	
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
	url : "${app}/partner/baseinfo/tawPartnerOils.do?method=importCompanyUser",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
		},
		failure : function(form, action) {
			alert(action.result.infor);
		}
    });
	
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
			if (deptid != ''){
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


 <div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	 <a href="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerOils.do?method=download">模板下载</a>
	<form action="charge.do?method=importRecord" method="post" enctype="multipart/form-data" id="importForm" name="importForm">
			<fieldset>
				<legend>从Excel导入</legend>
				<table id="sheet" class="formTable">
				<tr>
						<td class="label">所属公司</td>
						<td width="35%">
							<eoms:pnrComp name="partnerid" id="partnerid" alt="allowBlank:false"/>
						    <input type="button" class="btn" onclick="JavaScript:searchWin('partnerid');" value=" 搜 索 "><span id="partneridspan"></span>
						</td>
						<td width="35%">
							
						</td>
					</tr>
					<tr>
						<td class="label">选择Excel文件</td>
						<td width="35%">
							<input type="file" name="importFile" class="file"  alt="allowBlank:false"/>
						</td>
						<td width="35%">
							<input type="button" onclick="saveImport()" value="确定"/>
						</td>
					</tr>
				</table>
			</fieldset>
	</form>
</div>

<html:form action="/tawPartnerOils.do?method=save" styleId="tawPartnerOilForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawPartnerOil.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.oil_number" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="oil_number" styleId="oil_number"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.oil_number}" />${fallure }
		</td>
		<td class="label">
			<fmt:message key="tawPartnerOil.type" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="type" styleId="type"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.type}" />
		</td>
		<!-- 
		<td class="label">
			<fmt:message key="tawPartnerOil.dimensionalCode" />&nbsp;*
		</td>
		<td class="content">
			<html:text property="dimensionalCode" styleId="dimensionalCode"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.dimensionalCode}" />
		</td>
		 -->
	</tr>

	<tr>
	<td class="label">
			<fmt:message key="tawPartnerOil.area_id" />&nbsp;*
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
			<fmt:message key="tawPartnerOil.serviceProfessional" />&nbsp;*
		</td>
		<td class="content">
			<eoms:comboBox name="serviceProfessional" id="serviceProfessional" initDicId="1121201" defaultValue="${tawPartnerOilForm.serviceProfessional}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>

		<!-- 所属县区
		<td class="label">
			<fmt:message key="tawPartnerOil.city" />&nbsp;*
		</td>
		<td class="content">
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所在县区'" onchange="">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
		</td>
		 -->
		
	</tr>

	<tr>
	
		<td class="label">
			<fmt:message key="tawPartnerOil.dept_id" />&nbsp;*
		</td>          
		<td class="content" >
			<!-- 合作伙伴 -->			
			<select name="dept_id" id="dept_id" 
				alt="allowBlank:false,vtext:'请选择所属公司'">
					<option value="">--请选择所属公司--</option>
			</select>
			<input type="button" class="btn" onclick="JavaScript:searchWin('dept_id');" value=" 搜 索 "><span id="dept_idspan"></span>
		</td>
	
		<td class="label">
			<fmt:message key="tawPartnerOil.power" />&nbsp;*
		</td>
		<td class="content">
		<eoms:comboBox name="power" id="power" initDicId="11207"
					defaultValue='${tawPartnerOilForm.power}' alt="allowBlank:false,vtext:''" />
			<%--<html:text property="power" styleId="power"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.power}" />
		--%></td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.kind" />&nbsp;*
		</td>
		<td class="content">
		<eoms:comboBox name="kind" id="kind" initDicId="11206"
					defaultValue='${tawPartnerOilForm.kind}' alt="allowBlank:false,vtext:''" />
			<%--<html:text property="kind" styleId="kind"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.kind}" />
		--%></td>
		<td class="label">
			<fmt:message key="tawPartnerOil.state" />&nbsp;*
		</td>
		<td class="content">
		<eoms:comboBox name="state" id="state" initDicId="11205"
					defaultValue='${tawPartnerOilForm.state}' alt="allowBlank:false,vtext:''" />
			<%--<html:text property="state" styleId="state"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.state}" />
		--%></td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.storage" />&nbsp;*
		</td>
		<td class="content" >
			<html:text property="storage" styleId="storage"
						styleClass="text medium" onblur="testCharSize(this,128);"
						alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.storage}" />
		</td>	
		<td class="label">
			<fmt:message key="tawPartnerOil.factory" />&nbsp;*
		</td>
		<td class="content">
<!-- 		<eoms:comboBox name="factory" id="factory" initDicId="11208"
					defaultValue='${tawPartnerOilForm.factory}' alt="allowBlank:false,vtext:''" />
 -->
 			<html:text property="factory" styleId="factory"
						styleClass="text medium" alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.factory}" />
		</td>		
	</tr>
		<logic:notEmpty name="tawPartnerOilForm" property="addMan">
			<tr>
				<td class="label">
					添加人姓名
				</td>
				<td class="content">
					<eoms:id2nameDB id="${tawPartnerOilForm.addMan}"
						beanId="tawSystemUserDao" />&nbsp;&nbsp;
						(<bean:write name="tawPartnerOilForm" property="connectType" />)
				</td>

				<td class="label">
					添加时间
				</td>
				<td class="content">
					<bean:write name="tawPartnerOilForm" property="addTime" />

				</td>
			</tr>
			</logic:notEmpty>
		<tr>
		     <td class="label">
		         <fmt:message key="tawPartnerOil.savePlace" />&nbsp;*
		     </td>
		     <td class="content">
		         <html:text property="savePlace" styleId="savePlace" 
		         styleClass="text medium" alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.savePlace}" />
		     </td> 
		     <td class="label">
		         <fmt:message key="tawPartnerOil.startWay" />&nbsp;*
		     </td>
		     <td class="content">
		         <html:text property="startWay" styleId="startWay" 
		         styleClass="text medium" alt="allowBlank:false,vtext:''" value="${tawPartnerOilForm.startWay}" />
		     </td>      
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.remark" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" onblur="testCharSize(this,255);"
						styleClass="text medium" cols="40" rows="4"
						value="${tawPartnerOilForm.remark}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty tawPartnerOilForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确认删除?')){
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerOils.do?method=remove&id=${tawPartnerOilForm.id}';
						location.href=url}"	/>
				<%--<input type="button" class="btn" value="返回" 
					onclick="{
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerOils.do?method=backToSearch';
						location.href=url}"	/>		
			--%></c:if>
			<!--
			<input type="button" class="btn" value="批量导入"
						onclick="javascript:{
						var url=eoms.appPath+'/partner/baseinfo/tawPartnerOils.do?method=toXls';
						location.href=url}" />
 			 -->						
		</td>
	</tr>
</table>
<html:hidden property="id" value="${tawPartnerOilForm.id}" />
</html:form>
<script type="text/javascript">
  </script>
<%@ include file="/common/footer_eoms.jsp"%>