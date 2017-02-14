<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
var tr = true;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'lineForm'});
});


	function createRequest()
	{
		var httpRequest = null;
		if(window.XMLHttpRequest){
	     httpRequest=new XMLHttpRequest();
	    }else if(window.ActiveXObject){
	     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
	    }
	    return httpRequest;
	}
	function valgrid()
	{
		var obj = document.getElementById("lineName");
		var lineName = encodeURIComponent(document.getElementById("lineName").value);
		var url = eoms.appPath+"/partner/serviceArea/lines.do?method=validationLineName&id="+document.getElementById("id").value+"&lineName="+lineName;
			Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("<\/SCRIPT>")>0){
				  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
						var json = eval(res);
			     			if(json[0].message == true){
			     				if(obj.value!=""){
			     					document.getElementById("message").innerHTML = "<font color='green'>此线路名称可以使用</font>";
			     					tr = true;
			     				}else{
			     					obj.focus();
			     				}
			     			}else{
			     				document.getElementById("message").innerHTML = "<font color='red'>对不起,此线路名称已存在</font>";
			     				tr = false;
			     			}		
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});		
	}

function sub(){
     
 
      
	if(tr){
	}else{
		alert("对不起,此线路名称已存在");
		return false;
	}
	 var   regx=/^[0-9]+.?[0-9]*$/;   
    var   m=regx.test(document.all.lineLength.value);   
    if(m==false)   
      {   
          alert("请你输入正确的线路长度格式！");   
          return false;
      }  	
	if(v.check()){
       $("lineForm").submit();
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
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); 
    xmlhttp.send(null);
    return xmlhttp.responseText;
}
//县区联动
function changeCity(con)
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
		    delAllOption("provider");//地市选择更新后，重新刷新合作伙伴
		    delAllOption("grid");
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									
									var json = eval(res);
									var countyName = "city";
						
									var arrOptions = json[0].cb.split(",");
									var obj=$(countyName);
											
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									var providerName = "provider";
									var arrOptionsP=json[0].pb.split(",");
									var objp=$(providerName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsP.length;m++){
										var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
										objp.options[n]=optp;
										n++;
										m++;
										
									}
									
									var gridName = "grid";
									var arrOptionsG=json[0].gl.split(",");
									var objp=$(gridName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsG.length;m++){
										var optp=new Option(arrOptionsG[m+1],arrOptionsG[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}	
									
									if(con==1){
										var city = '${lineForm.city}';
										if(city!=''){
											document.getElementById("city").value = city;
										}	
									}	
									changePartner(con);							
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}
		
//地市、县区联动合作伙伴		
function changePartner(con)
		{    
		    delAllOption("provider");//地市选择更新后，重新刷新合作伙伴
		    delAllOption("grid");
			//地市
			var regionValue = document.getElementById("region").value;
			//县区
			var cityValue = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changePartner&region="+regionValue+"&city="+cityValue;
			//var result=getResponseText(url);
			Ext.Ajax.request({
							url : url ,
							method: 'POST',
							//params:{providerValue:providerValue},
							success: function ( result, request ) { 
							res = result.responseText;
							if(res.indexOf("<\/SCRIPT>")>0){
						  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
							}
								var json = eval(res);
								
								var providerName = "provider";
								var arrOptionsP=json[0].pb.split(",");
								var objp=$(providerName);
								var m=0;
								var n=0;
								for(m=0;m<arrOptionsP.length;m++){
									var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
									objp.options[n]=optp;
									n++;
									m++;
									
								}
								
								var gridName = "grid";
									var arrOptionsG=json[0].gl.split(",");
									var objp=$(gridName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsG.length;m++){
										var optp=new Option(arrOptionsG[m+1],arrOptionsG[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}	
								if(con==1){
										var provider = '${lineForm.provider}';
										if(provider!=''){
											document.getElementById("provider").value = provider;
										}
										
										changeGrid(con);
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}
		
		
		
//地市、县区、合作伙伴 联动 网格		
function changeGrid(con){
    var cityValue = document.getElementById("city").value;
    var providerValue = document.getElementById("provider").value;
    if(cityValue!=''&&providerValue!=''){
	    delAllOption("grid");//合作伙伴和县区选择更新后，重新刷新网格
	    var regionValue = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeGrid&city="+regionValue+"&cityValue="+cityValue;
		
			Ext.Ajax.request({
							url : url ,
							method: 'POST',
							params:{providerValue:providerValue},
							success: function ( result, request ) { 
							res = result.responseText;
							if(res.indexOf("<\/SCRIPT>")>0){
						  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
							}
								var json = eval(res);
		     					var countyName = "grid";
								var arrOptions = json[0].gl.split(",");
								var obj=$(countyName);
								var i=0;
								var j=0;
								for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
									obj.options[j]=opt;
									j++;
									i++;
								}
								if(con==1){
										var grid = '${lineForm.grid}';
										if(grid!=''){
											document.getElementById("grid").value = grid;
										}
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
    }
    
}	
		

		

</script>

<html:form action="/lines.do?method=save" styleId="lineForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="line.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="line.lineName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="lineName" styleId="lineName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${lineForm.lineName}" onblur="valgrid()" /><font style="color: red;">${failure}</font>
			<span id = "message"></span>
		</td>

		<td class="label">
			<fmt:message key="line.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">						
			<!-- 地市 -->			
			<select name="region" id="region"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changeCity(0);">
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
			<fmt:message key="line.city" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 县区 -->			
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所在县区'" onchange="changePartner(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>

		</td>
		<td class="label">
			<fmt:message key="line.provider" />&nbsp;<font color='red'>*</font>
		</td>
			<!-- 合作伙伴 -->			
		<td class="content">
			<select name="provider" id="provider" 
				alt="allowBlank:false,vtext:'请选择合作伙伴'" onchange="changeGrid(0);">
				<option value="">
					--请选择合作伙伴--
				</option>				
			</select>
						
		</td>

	</tr>

	<tr>

		<td class="label">
			<fmt:message key="line.grid" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 所属网格 -->
			<select name="grid" id="grid" 
				alt="allowBlank:false,vtext:'请选择所属网格'" >
				<option value="">
					--请选择所属网格--
				</option>				
			</select>
		</td>

		<td class="label">
			<fmt:message key="line.lineLength" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="lineLength" styleId="lineLength"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${lineForm.lineLength}"  />
		</td>
	</tr>


	<tr>
		<td class="label">
			<fmt:message key="line.isFullService" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
					
			<eoms:dict key="dict-partner-serviceArea" dictId="isFullService" isQuery="false" alt="allowBlank:false,vtext:'请选择是否开放(字典)...'"
				defaultId="${lineForm.isFullService}" selectId="isFullService" beanId="selectXML" />
		
		</td>


		<td class="label">
			<fmt:message key="line.grade" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">

			<eoms:comboBox name="grade" id="grade" initDicId="1121103" defaultValue="${lineForm.grade}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
					
		</td>
	</tr>
			<html:hidden property="userNameNew" value="${lineForm.userNameNew}" />

			<html:hidden property="userNameModify" value="${lineForm.userNameModify}" />
			
			<html:hidden property="userNameDel" value="${lineForm.userNameDel}" />


			<html:hidden property="timeNew" value="${lineForm.timeNew}" />
			
			<html:hidden property="timeModify" value="${lineForm.timeModify}" />

			<html:hidden property="timeDel" value="${lineForm.timeDel}" />





</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>"  onclick="sub();"/>
			<c:if test="${not empty lineForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('是否删除？')){
						var url=eoms.appPath+'/partner/serviceArea/lines.do?method=removeIsDel&id=${lineForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${lineForm.id}" />
</html:form>


<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${lineForm.region}';
	if(region!=''){
 		document.getElementById("region").value = region;
		changeCity(1);
	}
}

function   check()   
  {   
    var   regx=/^([-]?)([0-9]+)((.[0-9]{2})?)$/;   
    var   m=regx.test(document.all.lineLength.value);   
    if(m==true)   
      {   
          alert("right");   
      }   
    else   
      {   
          alert("error");   
      }   
  }   
</script>

<%@ include file="/common/footer_eoms.jsp"%>