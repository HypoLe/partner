<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%response.setHeader("cache-control","public"); %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
    <link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
 <style type="text/css">
	.trMouseOver {
		cursor:pointer;
	}
	tr.trMouseOver td {
		background-color:#CDE0FC;
	}
</style> 
  <script type="text/javascript">
  
  
  var jq=jQuery.noConflict();
  Ext.onReady(function(){
  
  var pathUrl="${app}";
	if(parent.frames['arcGis-page'].map!=null){
		parent.frames['arcGis-page'].mapCenterAndZoom(true,true);
		}
	parent.removeblock();
	
	jq("#list tbody tr").bind("mouseover",function(e) {
		jq(this).addClass("trMouseOver");
	});

	jq("#list tbody tr").bind("mouseout",function(e) {
		jq(this).removeClass("trMouseOver");
	});
		jq("#list tbody tr").bind(	"click",function(e) {
		var id = jq(this).find("input:hidden").val();
		parent.addblock();
		var url="${app}/partner/arcGis/arcGis.do?method=gotoTransLinePointList";
		Ext.Ajax.request({  
		       url : url ,
			   method: 'POST',
			   params:{id:id},
				success: function (result, request) { 
				resjson = result.responseText;
				var json = eval( '(' + resjson + ')' ); //转换为json对象
				if(json!=null&&json.length!=0){
					parent.frames['arcGis-page'].addTransLine(json,pathUrl,true,false);
					//parent.frames['arcGis-page'].addTransLineLocus(json,pathUrl,false,false);
				}
				else{
					alert("该资源数据错误！");
				}
				parent.removeblock();
				 },
				 failure: function ( result, request) { 
					 parent.removeblock();
						 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					 } 
			 });
			 });
			 
			 
  		delAllOption("country");
		var city = document.getElementById("city").value;
		var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
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
				var countyName = "country";
				var arrOptions = json[0].cb.split(",");
				var obj=$(countyName);
				var i=0;
				var j=0;
				for(i=0;i<arrOptions.length;i++){
					var opt=new Option(arrOptions[i+1],arrOptions[i]);
					obj.options[j]=opt;
					var country = "${pnrResConfigForm.country}";
					if(arrOptions[i]==country){
						obj.options[j].selected=true;
					}
					j++;
					i++;
				}
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});
		
  });
  
  var checkflag = "false";
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
	
	function delselcar(){
var string="";
 var objName= document.getElementsByName("checkbox11");
        for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string+=objName[i].value.trim();   
                string+="|";
                }
        }  
        if(confirm("确认要删除吗？")){
        	if(string == null || "" ==  string){
        		alert("请选择要删除的巡检资源");
        		return false;
        	}
		 	location.href="${app}/partner/res/PnrResConfig.do?method=remove&&seldelcar="+string;
		 }else{
		 return false;
		 }
}
  
 function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         }	
         
     }
	
//地区、区县连动
function changeCity(con)
		{    
		     delAllOption("country");//地市选择更新后，重新刷新县区
				var city = document.getElementById("city").value;
				var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
				Ext.Ajax.request({
					url : url,
					method: 'POST',
					success: function ( result, request ) { 
						res = result.responseText;
						if(res.indexOf("<\/SCRIPT>")>0){
					  		res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
						}
						var json = eval(res);
						var countyName = "country";
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
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
		}	


//导出界面的区县连动
 function changeCity1(con)
		{    
		  delAllOption("city1");//地市选择更新后，重新刷新县区
var region1 = document.getElementById("region1").value;
var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region1;
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
						
						var countyName = "city1";
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
						
						if(con==1){			
							var city = '${gridForm.city1}';
							var partnerid = '${gridForm.partnerid1}';
							if(city!=''){
								document.getElementById("city1").value = city;
							}
							if(partnerid!=''){
								changePartner(1);								

    
                                  }	
						}							
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
		}
 
  </script>
  
  <body>
  <form action="${app}/partner/arcGis/arcGis.do?method=gotoTransLineList" method="post" >
   <table class="formTable">
	<caption>
		<div class="header center">巡检资源信息</div>
	</caption>
			<tr>
					<td class="label">资源名称</td>
					<td class="content">
						<html:text property="resName" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrResConfigForm.resName}" />
					</td>
					<td class="label">巡检专业</td>
					<td class="content">
						<eoms:comboBox name="specialty" id="zhuanye"
							sub="resourceTeype" defaultValue="${pnrResConfigForm.specialty}" initDicId="11225" 
							alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>

				<tr>
					<td class="label">资源类别</td>
					<td class="content">
						<eoms:comboBox name="resType" defaultValue="${pnrResConfigForm.resType}"
							id="resourceTeype" initDicId="${pnrResConfigForm.specialty}" alt="allowBlank:false"
							sub="resourceLevel" styleClass="select" />
					</td>
					<td class="label">资源级别</td>
					<td class="content">
						<eoms:comboBox name="resLevel" defaultValue="${pnrResConfigForm.resLevel}"
							id="resourceLevel" initDicId="${pnrResConfigForm.resType}" alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>
				
				<tr>
					<td class="label">地市</td>
					<td class="content">
					<select name="city" id="city" class="select" alt="allowBlank:false,vtext:'请选择所在地市'" onchange="changeCity(0);">
					<option value="">
						--请选择所在地市--
					</option>
					<logic:iterate id="city" name="city">
						<c:if test="${pnrResConfigForm.city==city.areaid}" var="result">
							<option value="${city.areaid}" selected="selected" >
								${city.areaname}
							</option>
						</c:if>
						<c:if test="${!result}">
							<option value="${city.areaid}" >
								${city.areaname}
							</option>
						</c:if>
					</logic:iterate>
				</select>
					</td>
					<td class="label">区县</td>
					<td class="content">
						<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
								--请选择所在县区--
							</option>				
						</select>
					</td>
				</tr>
				<tr>
				<td colspan='4' class='label'>
					<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
			    	<input type="reset" class="btn" value="重置"/>
			    	</td>
				</tr>
		</table> 
			    
				
	</form>	
			    
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/arcGis/arcGis.do?method=gotoTransLineList"
		sort="list" partialList="true" size="${resultSize}">
	
		<display:column media="html" sortable="true" headerClass="sortable" title="资源名称">
			${list.resName}
			<input type="hidden" id="dataId_${list_rowNum }" value="${list.id}"/>
	</display:column>
		
				
		<display:column  sortable="true"  title="所在地市"
				headerClass="sortable" >
				<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>
				
		<display:column  sortable="区县" title="所在区县"
				headerClass="sortable"  >
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
				
		<display:column  sortable="区县" title="系统级别"
				headerClass="sortable"  >
			${list.tlSystemLevel }
		</display:column>
				
		<display:column  sortable="区县" title="a点名字"
				headerClass="sortable"  >
			${list.tlPAName }
		</display:column>
				
		<display:column  sortable="区县" title="a点经度"
				headerClass="sortable"  >
			${list.tlPALo }
		</display:column>
				
		<display:column  sortable="区县" title="a点纬度"
				headerClass="tlPALa"  >
			${list.tlPALa }
		</display:column>
				
		<display:column  sortable="区县" title="z点名字"
				headerClass="sortable"  >
			${list.tlPZName }
		</display:column>
		
		<display:column  sortable="区县" title="z点经度"
				headerClass="sortable"  >
			${list.tlPZLo }
		</display:column>
				
		<display:column  sortable="区县" title="z点纬度"
				headerClass="tlPALa"  >
			${list.tlPZLa }
		</display:column>
				
		
		
	</display:table>
	
  </div>
	
  </body>
</html>
