<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8" %>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var myjs=jQuery.noConflict();
	function openImport(handler){
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
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

function relationRes(){
	var id = '${res.id}';
	var tlWire = '${res.tlWire}';
	var tlSeg = '${res.tlSeg}';
	Ext.Ajax.request({
		    url: '${app }/partner/res/pnrTransLineAction.do?method=relationTransLine',
		    params:{id:id,tlWire:tlWire,tlSeg:tlSeg},
		    success: function(response){
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
		    	 Ext.Msg.alert('提示','关联成功',function(){
		    	 	window.location.reload();
		    	 }); 
      			 }
		    	 if (jsonResult[0].success=='false'){
		    	 	Ext.Msg.alert('提示','关联失败'); 
      			 }
      			
		    },
	     	failure: function(response) {
	     			Ext.get(document.body).unmask();
                    Ext.Msg.alert('提示','关联失败'); 
            }
			});	
}

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
	
	function setMustArrive(obj){
	var string="";
	var objName= document.getElementsByName("checkbox11");
        for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string+="'"+objName[i].value.trim()+"',";   
                //string+=",";
                }
        }  
        	if(string == null || "" ==  string){
        		alert("请选择线路段");
        		return false;
        	}
        	
        	Ext.Ajax.request({
        	method:"post",
		    url: '${app }/partner/res/pnrTransLineAction.do?method=setMustArrive',
		    params:{ids:string,cancle:obj},
		    success: function(response){
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
				    	Ext.Msg.alert('提示','设置成功',function(){
				    		window.location='${app }/partner/res/pnrTransLineAction.do?method=gotoTransLinePointList&id=${resid}';
				    	}); 
      			 }
		    	 if (jsonResult[0].success=='false'){
		    	 	Ext.Msg.alert('提示','设置失败'); 
      			 }
      			
		    },
	     	failure: function(response) {
	     			Ext.get(document.body).unmask();
                    Ext.Msg.alert('提示','设置失败'); 
            }
			});	
        	
		 	//location.href="${app }/partner/res/pnrTransLineAction.do?method=setMustArrive&ids="+string;
}

function resetMsg(){
	myjs(".text").val('');
	myjs(".select").val('');
	changeCity('')
}

function backList(){
window.location='${app }/partner/res/pnrTransLineAction.do?method=gotoTransLineList';
}

</script>
 	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="${app}/partner/res/pnrTransLineAction.do?method=gotoTransLinePointList" method="post">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">起点名称</td>
				<td><input class="text" type="text" name="tlpPAName" value="${pnrTransLinePoint.tlpPAName }"/></td>
				<td class="label">终点名称</td>
				<td>
					<input class="text" type="text" name="tlpPZName" value="${pnrTransLinePoint.tlpPZName }"/>
				</td>
			</tr>
			<tr>
					<td class="label">光缆系统</td>
					<td class="content">
						<html:text property="tlpWire" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrTransLinePoint.tlpWire}" />
					</td>
					<td class="label">光缆段</td>
					<td class="content">
						<html:text property="tlpSeg" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrTransLinePoint.tlpSeg}" />
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
				<td class="label">区域</td>
				<td class="content">
					<input class="text" type="text" name="tlDis" value="${pnrTransLinePoint.tlpDis }"/>
				</td>
				<td class="label"></td>
				<td class="content">
				</td>
			</tr>
			 
	</table>
	<input type="hidden" name="id" value="${res.id}">
	<input type="submit" value="查询" class="btn">
	<input type="button" value="重置" class="btn" onclick="resetMsg();">
	<form>
</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>
<br/>

<c:choose>
	<c:when test="${relation eq '1'}">
		<!-- <input type="button" value="重新关联所属光缆段" class="btn" onclick="relationRes();">-->
		<span style="color:red">以下所有敷设点已关联光缆段</span><br/><br/>
	</c:when>
	<c:otherwise>
		<input type="button" value="关联所属光缆段" class="btn" onclick="relationRes();"><font color="red">（单击该功能键后，系统会将以下所有敷设点纳入巡检，并将光缆段的起点与终点新增为敷设点。）</font><br/>
	</c:otherwise>
</c:choose>

<input type="button" value="必到点设置" class="btn" onclick="setMustArrive('yes');">
<input type="button" value="取消必到点" class="btn" onclick="setMustArrive('no');">
<input type="button" value="返回" class="btn" onclick="backList();" />

<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pageSize}" requestURI="pnrTransLineAction.do?method=gotoTransLinePointList" 
		partialList="true" size="${size}">
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
	        	<input type="checkbox" name="checkbox11" id="checkbox11"  value="${list.id} "/>
	    	</display:column>
	    	
		<display:column  sortable="true"  title="所在地市"
			headerClass="sortable" >
			<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column  sortable="区县" title="所在区县"
			headerClass="sortable"  >
		<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column  sortable="true"  title="区域">
			<c:if test="${empty list.tlpDis}">-</c:if>
			${list.tlpDis }
		</display:column>
		<display:column  sortable="true"  title="光缆系统"
					headerClass="sortable" >
					${list.tlpWire }
		</display:column>
		<display:column  sortable="true"  title="光缆段"
				headerClass="sortable" >
				${list.tlpSeg }
		</display:column>
		<display:column  sortable="true" property="tlpPAName" title="起点名称" />
		<display:column  sortable="true" property="tlpPALo" title="起点经度" />
		<display:column  sortable="true" property="tlpPALa" title="起点纬度" />
		<display:column  sortable="true" property="tlpPZName" title="终点名称" />
		<display:column  sortable="true" property="tlpPZLo" title="终点经度" />
		<display:column  sortable="true" property="tlpPZLa" title="终点纬度" />
		<display:column  sortable="true" title="是否必到点">
			<c:if test="${list.isMustArrive eq '1'}">
				<font color="red">是</font>
			</c:if>
			<c:if test="${list.isMustArrive ne '1'}">
				否
			</c:if>
		</display:column>
		<display:column  sortable="true" title="敷设点来源">
			<c:if test="${list.tlpSource eq '0'}">
				段间点
			</c:if>
			<c:if test="${list.tlpSource eq '1'}">
				<font color="red">光缆段起点新增</font>
			</c:if>
			<c:if test="${list.tlpSource eq '2'}">
				<font color="red">光缆段终点新增</font>
			</c:if>
		</display:column>
	</display:table>
</logic:present>
	</br>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
