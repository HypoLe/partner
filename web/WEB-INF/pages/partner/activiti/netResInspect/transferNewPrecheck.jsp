<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
var v;
 Ext.onReady(function(){
 		v = new eoms.form.Validation({form:'theform'});//验证表单必填
  		// 初始的时候给区县默认值
		delAllOption("country");//地市选择更新后，重新刷新县区
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
				
				var countyName = "country";
				var arrOptions = json[0].cb.split(",");
				var obj=$(countyName);
				var i=0;
				var j=0;
				for(i=0;i<arrOptions.length;i++){
					var opt=new Option(arrOptions[i+1],arrOptions[i]);
					obj.options[j]=opt;
					var country = "${country}";
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
		});//初始的时候给区县默认值结束
		
  });
		//重置
		function newReset(){
			document.getElementById("sheetAcceptLimit").value="";            // 派单开始时间
			document.getElementById("sheetCompleteLimit").value="";		// 派单结束时间
			document.getElementById("wsNum").value="";		// 工单号
			document.getElementById("wsTitle").value="";	        // 工单主题
			document.getElementById("status").value="";	        // 当前状态
			document.getElementById("region").value="";	        
			document.getElementById("country").value="";	      
			document.getElementById("lineType").value="";	       
			//document.getElementById("provName").value="";	        
			document.getElementById("precheckFlag").value="";	        
			document.getElementById("mainSceneId").value="";	 //场景       
		}
		// 地区、区县连动
function changeCity(con)
		{    
		    delAllOption("country");//地市选择更新后，重新刷新县区
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
 function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         }	
         
     }		
     
</script>


<html:form action="/netResInspect.do?method=performAdd" styleId="theform" >
<table id="sheet" class="formTable" >
	<tr>
	  <td class="label">工单名称*</td>
	  <td colspan="3">
	    <input type="text" class="text max" id="theme" name="theme" value="${roomDemolition.theme}" alt="allowBlank:false"/>
	  </td>
	</tr>

	<tr>
	  <td class="label">资源类型*</td>
	  <td class="content">
	  <eoms:comboBox name="resourceType" id="resourceType"
					defaultValue="${roomDemolition.resourceType}" initDicId="12811"
					alt="allowBlank:false" styleClass="select" />
	  </td>
	  
	  <td class="label">问题类型</td>
	  <td class="content">
	  <eoms:comboBox name="questionType" id="questionType"
					defaultValue="${roomDemolition.questionType}" initDicId="12803"
					alt="allowBlank:true" styleClass="select" />
	  </td>
	</tr>
	
	<tr>
	  <td class="label">资源地址*</td>
	  <td colspan="3">
	    <select name="region"  id="region" class="select"
							alt="allowBlank:false,vtext:'请选择所在地市'"
							onchange="changeCity(0);">
							<option value="">
								--请选择所在地市--
							</option>
							<logic:iterate id="city" name="city">
							<c:if test="${city.areaid ==region}">
								<option value="${city.areaid}" selected="selected" >
									${city.areaname}
								</option>
							</c:if>
							<option value="${city.areaid}">
								${city.areaname}
							</option>
							</logic:iterate>
						</select>市
		
		<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>县
		
		  <input type="text" class="text" id="street" name="street" value="${roomDemolition.street}" alt="allowBlank:false" />街道
	  </td>
	</tr>
	
	<tr>
	  <td class="label">描述*</td>
	  <td colspan="3">
	    <input type="text" class="text max" id="reportedDescribe" name="reportedDescribe" value="${roomDemolition.reportedDescribe}" alt="allowBlank:false" />
	  </td>
	</tr>
	
	<tr>
	  <td class="label">定位地址</td>
	  <td colspan="3">
	    <input type="text" class="text max" id="locatedAddress" name="locatedAddress" value="${roomDemolition.locatedAddress}" />
	  </td>
	</tr>
</table>
<br/>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save"  styleId="method.save">
				上报
			</html:submit>
			<font color='red'>只能手机端上报，web端没有上报功能，上线升级时屏蔽</font>
		</div>
	</html:form>
<!--

//-->
</script>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 approveBackSwitcher = new detailSwitcher();
  approveBackSwitcher.init({
	container:'approveBackHistory',
  	handleEl:'div.history-item-title-back'
  });
  
  
</script>