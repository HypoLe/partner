<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
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
		//get other information form the page who clicked
		var otherInformation=[];
				var pathUrl="${app}";
				var other=[];
				if(Ext.isIE){
					otherInformation=jq(this)[0].innerText.split(' ');
					if(otherInformation.length!=0){
						for(var i=0;i<otherInformation.length;i++){
							var oi=otherInformation[i];
							if(oi!=""){
								other.push(oi);
							}
						}
					}
					}
					else{
				 otherInformation=jq(this)[0].textContent.trim().split('\n');
						if(otherInformation.length!=0){
							for(var i=0;i<otherInformation.length;i++){
								var oi=otherInformation[i].trim()
								if(oi!=""){
									other.push(oi);
								}
							}
						}
					}
					parent.frames['arcGis-page'].addErrorMarker(other,pathUrl,true);
					parent.removeblock();
		

		
	});
		
});

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
   delAllOption("city");//地市选择更新后，重新刷新县区
var region = document.getElementById("region").value;
var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
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
						
						if(con==1){				
							var city = '${gridForm.city}';
							var partnerid = '${gridForm.partnerid}';
							if(city!=''){
								document.getElementById("city").value = city;
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
	
	
		
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<html:form action="inspectPlanGisAction.do?method=findInspectPlanErrorRes" method="post">
		<table id="sheet" class="listTable">
			<tr>
				<td class="label">地市：</td>
				<td>
						<select name="region" id="region" class="select"
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
				<td class="label">区县：</td>
				<td>
					<select name="country" id="city" class="select"
					alt="allowBlank:false,vtext:'请选择所在县区'">
					<option value="">
						--请选择所在县区--
					</option>	
					</select>
				</td>	
				</tr>
				<tr>	
				<td class="label">巡检专业</td>
				<td>
					<eoms:comboBox name="specialtyStringEqual" id="specialty" 
					initDicId="11225" alt="allowBlank:false" styleClass="select" />
				</td>
			</tr>
		</table>
	
		<html:submit styleClass="btn" property="method.approvalList"
			styleId="method.approvalList" value="查询"></html:submit>
	</html:form>
	</div>
</hr>
	
 	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlanGisAction.do"
		sort="list" partialList="true" size="${resultSize}" 
		decorator="com.boco.eoms.partner.inspect.util.InspectPlanMainDetailByCheckUserDecorator"
	>
		<display:column media="html" sortable="false"  headerClass="sortable" title="资源名称">
			${list.resName}
		</display:column>
		<display:column title="巡检专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源类型">
			<eoms:id2nameDB id="${list.resType}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源级别">
			<eoms:id2nameDB id="${list.resLevel}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="地市">
			<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>			
		<display:column  sortable="true" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		
		<display:column  title="代维小组" >
		<eoms:id2nameDB id="${list.executeObject}" beanId="partnerDeptDao"/>
		</display:column>
		
		<display:column  title="资源经度" >
			${list.resLongitude}
		</display:column>
		
		<display:column title="资源纬度" >
			${list.resLatitude}
		</display:column>
		
		<display:column  title="资源打点经度" >
			${list.signLongitude}
		</display:column>
		
		<display:column title="资源打点纬度" >
			${list.signLatitude}
		</display:column>
			
	</display:table>
	
	
	
<%@ include file="/common/footer_eoms.jsp"%>