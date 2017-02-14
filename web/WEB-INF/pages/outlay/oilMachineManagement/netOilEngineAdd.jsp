<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ page import="org.apache.struts.Globals" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

<form id="mainForm" name="mainForm" action="NetOilEngineManagement.do?method=save" method="post"> 
<input type="hidden" name="id" id="id" value="${netOilEngine.id}"/> 
<input type="hidden" name="createUserId" id="createUserId" value="${createUserId}"/> 
<input type="hidden" name="nextAction" id="nextAction" value="${nextAction}"/> 
<table class="formTable">
	<tr>
		<td class="label">地市</td>
		<td class="content">
		<input id="city" name="city" type="hidden"  value="${netOilEngine.city}"> 
		<select id="cityid" name="cityid" class="ddd" onchange="getRegionCountry(this.options[this.options.selectedIndex].value);">
		    </select>	
		</td>	
		<td class="label">区县</td>
		<td class="content">
		<input id="country" name="country" type="hidden"  value="${netOilEngine.country}"> 
		<select id="countryid" name="countryid" class="ddd" onchange="getResidentSiteName(this.options[this.options.selectedIndex].value);">
		    </select>	
		</td>
	</tr>
	
	<tr>
		<td class="label">基站名称</td>
		<td class="content">
		 <input id="station" name ="station" type="text" value="${netOilEngine.station}"/>
		</td>
		<td class="label">驻点名称</td>
		<td class="content">
		<input id="residentSiteName" name="residentSiteName" type="hidden" value="${oilEngine.residentSiteName}" > 
		<select id=residentSiteNameid name="residentSiteNameid" class="ddd" >
		    </select>	
		    </td>

	</tr>
	
	<tr>
		<td class="label">告警发生时间</td>
		<td class="content">
		<input class="text" type="text" name="beginTime" value="${netOilEngine.beginTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'lessThen',link:'endTime',vtext:'开始时间不能晚于结束时间！',allowBlank:false"
					 id="startTime" readonly="readonly"/>
		</td>	
		<td class="label">告警清除时间</td>
		<td class="content">
		<input class="text" type="text" name="endTime" value="${netOilEngine.endTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)" 
					alt="vtype:'moreThen',link:'startTime',vtext:'结束时间不能早于开始时间！',allowBlank:false"
					 id="endTime" readonly="readonly"/>
		</td>
	</tr>	

	<!-- 保留字段，暂时不用
	<tr>
		<td class="label">油耗</td>
		<td class="content"><input id="oilWear" name ="oilWear" type="text" value="${oilEngine.oilWear}" onchange="autoCountFee();"  alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数',allowBlank:false"/> 升
		</td>	
		<td class="label">单价</td>
		<td class="content"><input id="perPrice" name ="perPrice" type="text" value="${oilEngine.perPrice}" onchange="autoCountFee();" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数',allowBlank:false"/> 元</td>
	</tr>
		<tr>
		<td class="label">费用</td>
		<td class="content"><input id="totalFee" name ="totalFee" type="text" value="${oilEngine.totalFee}" alt="vtype:'number',allowBlank:false" readonly="true"/> 元
		</td>	
	</tr>
 -->
</table>
			<input type="button" name="button" id="button" value="保存" onclick="sub();"/>
			<input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>"/> 
</form>
<script>
var myJ = $.noConflict();

Ext.onReady(function(){

    getRegionCity();
	setOldSelectionSelected("approvedView");
	if(document.all["approvedView"].value == '${APPROVED_YES}'){
		document.getElementById("auditUserTree").style.display = "";
	}
})
var vlt = new eoms.form.Validation({form:'mainForm'});

function saveConfirm(){
	if(document.all["status"].value == '${STATUS_REJECT}'){
		document.all["status"].value = '${STATUS_AUDIT}';
		return true;
	}

	if(document.all["approvedView"].selectedIndex == 0){
		alert("请选择是否审核！");
		return false;
	}
	if(document.all["approvedView"].selectedIndex == 1){
		if(confirm("确认提交审核？")){
			document.all["status"].value = '${STATUS_AUDIT}';
			return true;
		}else{
			return false;		
		}
	}else if(document.all["approvedView"].selectedIndex == 2){
		document.all["status"].value = '${STATUS_NO_NEED_AUDIT}';
		return true;
	}else{
		document.all["status"].value = '${STATUS_AUDIT}';
		return true;
	}


}

    function sub(){
        //地市
        var objcity=document.getElementsByName("cityid")[0];
        var city= objcity.options[objcity.options.selectedIndex].text//
        document.forms[0].city.value = city ;
            //县区
        var objcountry=document.getElementsByName("countryid")[0];
        var country= objcountry.options[objcountry.options.selectedIndex].text//
        document.forms[0].country.value = country ;
        //驻点
        var objresidentSiteName=document.getElementsByName("residentSiteNameid")[0];
        var residentSiteName= objresidentSiteName.options[objresidentSiteName.options.selectedIndex].text//
        document.forms[0].residentSiteName.value = residentSiteName ;
        
        document.forms[0].submit();

    }
function setOldSelectionSelected(selectObjName){
	var selectObj = document.getElementsByName(selectObjName)[0];

		for(var i=0;i<selectObj.options.length;i++){
			if(selectObj.options[i].value=="${oilConsuming.approved}"){
				selectObj.options[i].selected = true;
				break;
			}
		}
	}


function setHiddenStatusValue(slt){
	document.getElementById("auditUserTree").style.display = "none";
	if(slt.selectedIndex == 0){
		document.all["status"].value = '${STATUS_DRAFT}';
		document.all["taskOwner"].value = "";
		document.all["taskOwner_CN"].value = "";
	}
	if(slt.selectedIndex == 1){
		document.all["status"].value = '${STATUS_AUDIT}';
		document.getElementById("auditUserTree").style.display = "";
	}
	if(slt.selectedIndex == 2){
		document.all["status"].value = '${STATUS_NO_NEED_AUDIT}';
		document.all["taskOwner"].value = "";
		document.all["taskOwner_CN"].value = "";
	}
	
	document.all["approved"].value = document.all["approvedView"].options[document.all["approvedView"].selectedIndex].value;
}

function saveAsDraft(){
//	document.all["status"].value = '${STATUS_DRAFT}';
	return true;
}

function autoCountFee(){
	document.all["totalFee"].value =  document.all["oilConsumingCount"].value * document.all["perPrice"].value;
}

     //地市
       function getRegionCity(){
        
         myJ.getJSON("${app}/partner/oilmachine/NetOilEngineManagement.do?method=getRegionCity",
                   function(data){
                   	myJ("#countryid").empty().append('<option value="" >--请选择--</option>');
                   	myJ("#cityid").empty().append('<option value="" >--请选择--</option>');
                    myJ("#residentSiteNameid").empty().append('<option value="" >--请选择--</option>');
 						for(var i=0;i<data.length;i++){
							myJ('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#cityid");	
				    	}	
				    	
				    	var  cityid = '${netOilEngine.cityid}';    
					        if(cityid != ''){
					          document.forms[0].cityid.value = cityid ;	
					          getRegionCountry(cityid);					          			         
					        }			  
			        }
         );
       }
       
       //县区
       function getRegionCountry(v){
        
         myJ.getJSON("${app}/partner/oilmachine/NetOilEngineManagement.do?method=getRegionCountry&city="+v,
                   function(data){
                  
                   	myJ("#countryid").empty().append('<option value="" >--请选择--</option>');
                
 						for(var i=0;i<data.length;i++){
							myJ('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#countryid");	
				    	}	
				    	
				    	var  countryid = '${netOilEngine.countryid}';    
					        if(countryid != ''){
					          document.forms[0].countryid.value = countryid ;
					          getResidentSiteName(countryid);						          			         
					        }			  
			        }
         );
       }
       
        //驻点信息
       function getResidentSiteName(v){
       
         myJ.getJSON("${app}/partner/oilmachine/OilEngineManagement.do?method=getResidentSiteName&countryid="+v,
                   function(data){
                   	myJ("#residentSiteNameid").empty().append('<option value="" >--请选择--</option>');
                
 						for(var i=0;i<data.length;i++){
							myJ('<option/>')
								.val(data[i].id)
								.text(data[i].residentSiteName)
								.appendTo("#residentSiteNameid");	
				    	}	
				    	
				    	var  residentSiteNameid = '${netOilEngine.residentSiteNameid}';    
					        if(residentSiteNameid != ''){
					          document.forms[0].residentSiteNameid.value = residentSiteNameid ;						          			         
					        }			  
			        }
         );
       }
</script>
<%@ include file="/common/footer_eoms.jsp"%>