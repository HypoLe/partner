<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'SiteNauticaDeputyForm'});
});

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
	            }
            
        }

function changeCity(con)
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
		    
			var region = document.getElementById("region").value;
			
			var url="../serviceArea/lines.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("[{")!=0){
					  					res = "[{"+result.responseText;
									}
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									
									var json = eval(res);
									var cityName = "city";
						
									var arrOptions = json[0].cb.split(",");
									var obj=$(cityName);
											
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									
									if(con==1){
										var city = '${SiteNauticaDeputyForm.city}';
										if(city!=''){
											document.getElementById("city").value = city;
										}	
									}	
									changeGrid(con);						
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}
function changeGrid(con){
    var region = document.getElementById("region").value;
    // delAllOption("siteName");//地市选择更新后，重新刷新基站
    delAllOption("grid");//地市选择更新后，重新刷新网格
	var url="../baseinfo/linkage.do?method=changeGridList&region="+region;
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) { 
		res = result.responseText;
		if(res.indexOf("[{")!=0){
			res = "[{"+result.responseText;
		}
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
				var grid = '${SiteNauticaDeputyForm.grid}';
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
function changeSite(con){
    var grid = document.getElementById("grid").value;
    // var speciality = document.getElementById("speciality").value;
    delAllOption("siteName");//地市选择更新后，重新刷新基站
	var url="../baseinfo/linkage.do?method=changeSiteList&grid="+grid;
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) { 
		res = result.responseText;
		if(res.indexOf("[{")!=0){
			res = "[{"+result.responseText;
		}
		if(res.indexOf("<\/SCRIPT>")>0){
	  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
		}
			var json = eval(res);
  			var countyName = "siteName";
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
				var site = '${SiteNauticaDeputyForm.siteName}';
				if(site!=''){
					document.getElementById("siteName").value = site;
				}
			}		
		},
		failure: function ( result, request) { 
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
		} 
	});
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
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var err = '${err}';
    if(err!=''){
    	alert(err);
    }
    var region = '${SiteNauticaDeputyForm.region}';
    var city = '${SiteNauticaDeputyForm.city}';
    var grid = '${SiteNauticaDeputyForm.grid}';
   
	if(region!=''){
		document.getElementById("region").value = region;
		changeCity(1);
	}
	if(city!=''){
		document.getElementById("city").value = city;
		changeGrid(1);
		
	}
}
</script>
<html:form  action="/SiteNauticaDeputys.do?method=save" styleId="SiteNauticaDeputyForm" method="post" > 
<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center">申请站点经纬度变更</div>
	</caption>
	<tr>
	    <td class="label">
			<fmt:message key="site.region" />&nbsp;<font color='red'>*</font>
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
		 <td class="label">
			<fmt:message key="site.city" />&nbsp;<font color='red'>*</font>
		</td>		
		<td class="content">
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所在县区'"
				onchange="changeGrid(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>						
		</td>			
	</tr>	
	<tr>				    	
		<td class="label">
			<fmt:message key="site.grid" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select name="grid" id="grid" 
				alt="allowBlank:false,vtext:'请选择所属网格'" 
				onchange="changeSite(0);" >
				<option value="">
					--请选择所属网格--
				</option>				
			</select>						
		</td>
		<td class="label">
			归属站点&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select name="siteName" id="siteName" 
				alt="allowBlank:false,vtext:'请选择站点'">
				<option value="">
					--请选择站点--
				</option>				
			</select>	
			<!--  
			<input type="button" class="btn" onclick="JavaScript:searchWin('site');" value=" 搜 索 "><span id="sitespan"></span>-->
		</td>	
	</tr>
	<tr>
		<td class="label">
			申请人<font color='red'>*</font>
		</td>
		<td class="content">
			         ${sessionform.username}
			<html:hidden property="requestPerson" value="${sessionform.username}" />		
		</td>
		<td class="label">
			申请人联系方式<font color='red'>*</font>
		</td>
		<td class="content">		     
			<input type="text" name="requestPersonTel" id="requestPersonTel" value=" ${sessionform.contactMobile}" styleClass="text medium"/>			
		</td>
	</tr>	
	<tr>
		<td class="label">
			站点经度<font color='red'>*</font>
		</td>
		<td class="content">			       
		<input type="text" name="longitude" id="longitude"  value="${SiteNauticaDeputyForm.longitude}" styleClass="text medium"/>				
		</td>
		<td class="label">
			 站点维度<font color='red'>*</font>
		</td>
		<td class="content">
			<input type="text" name="latitude" id="latitude"  value="${SiteNauticaDeputyForm.latitude}" styleClass="text medium"/>			
		</td>
	</tr>	
	<tr>
		<td class="label">
			     申请说明&nbsp;<font color='red'>*</font>
		</td>
		<td class="content max" colspan="3">
			<textarea cols="83" rows="5" id="requestReason" name="requestReason">${SiteNauticaDeputyForm.requestReason}</textarea>	
		</td>
	</tr>
	    <c:if test="${SiteNauticaDeputyForm.id!=null}">
			<tr>
			   <td class="label">
			        审核是否通过&nbsp;<font color='red'>*</font>
			   </td>
		       <td>
		           <select name="state">
		                <option value="2">通过</option>
		                <option value="0">驳回</option>
		           </select>
		       </td>
		    </tr>
			<tr height="20">
			    <td class="label">
			           审核意见 <font color='red'>*</font>
			    </td>
				<td class="content max" colspan="3">
				    <textarea cols="83" rows="5" id="auditReason" name="auditReason"></textarea>		
				</td>			
		   </tr> 
	    </c:if>
	</table>
<input type="hidden" name="id" id="id" value="${SiteNauticaDeputyForm.id}">
<table>
   <tr height="20">
		<td colspan="2">
			<input type="submit" class="btn" value="保存" />
		</td>
	</tr>
</table>
</fmt:bundle>
</html:form>


<%@ include file="/common/footer_eoms.jsp"%>