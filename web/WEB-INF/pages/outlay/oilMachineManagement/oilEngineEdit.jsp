<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ page import="org.apache.struts.Globals" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

<form id="mainForm" name="mainForm" action="OilEngineManagement.do?method=save" method="post"> 
<input type="hidden" name="id" id="id" value="${oilEngine.id}"/> 
<input type="hidden" name="createUserId" id="createUserId" value="${createUserId}"/> 
<input type="hidden" name="nextAction" id="nextAction" value="${nextAction}"/> 
<table class="formTable">
	<tr>
		<td class="label">代维公司</td>
		<td class="content" colspan=1>
		<input id="monitorCompany" name="monitorCompany" type="hidden"  value="${oilEngine.monitorCompanyid}"> 
		<select id="monitorCompanyid" name="monitorCompanyid" class="ddd" onchange="getCityInfo(this.options[this.options.selectedIndex].value);">
		    </select>	
			<td class="label">市区</td>
		<td class="content">
		 <input id="cityid" name="cityid" type="hidden" value="${oilEngine.cityid}"> 
		<input id="city" name ="city" type="text" readonly="readonly" value="${oilEngine.city}" />
		</td>			
	</tr>
	<tr>
	<td class="label">区县</td>
		<td class="content">
		<input id="country" name="country" type="hidden" value="${oilEngine.countryid}"> 
		<select id="countryid" name="countryid" class="ddd" onchange="getUnderContryInfo(this.options[this.options.selectedIndex].value);">
		    </select>	
		</td>	
		<td class="label">油机费用填写时间</td>
		<td class="content">
		<input class="text" type="text" name="recordTime" value="${oilEngine.recordTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					 id="operateTime" readonly="readonly"/>		
		</td>	
		
	</tr>
	<tr>
		<td class="label">油机开始使用时间</td>
		<td class="content">
		<input class="text" type="text" name="beginTime" value="${oilEngine.beginTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'lessThen',link:'endTime',vtext:'开始时间不能晚于结束时间！',allowBlank:false"
					 id="startTime" readonly="readonly"/>
		</td>	
		<td class="label">油机使用结束时间</td>
		<td class="content">
		<input class="text" type="text" name="endTime" value="${oilEngine.endTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'moreThen',link:'startTime',vtext:'结束时间不能早于开始时间！',allowBlank:false"
					 id="endTime" readonly="readonly"/>
		</td>
	</tr>	
	
	
	<tr>
	   <td class="label">驻点名称</td>
		<td class="content">
		<input id="residentSiteName" name="residentSiteName" type="hidden" value="${oilEngine.residentSiteNameid}" > 
		<select id=residentSiteNameid name="residentSiteNameid" class="ddd" onchange="getCityInfo(this.options[this.options.selectedIndex].value);">
		    </select>	
		    </td>
		<td class="label">发电基站</td>
		<td class="content">
		<input id="station" name="station" type="hidden"  value="${oilEngine.stationid}" > 
		<select id="stationid" name="stationid" class="ddd" onchange="getCityInfo(this.options[this.options.selectedIndex].value);">
		    </select>	
		</td>
		
	</tr>	

</table>
			<input type="button" name="button" value="保存" onclick="saveAsDraft();"/>
			<input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>"/> 
</form>
<script>
var myJ = $.noConflict();

Ext.onReady(function(){
      
	setOldSelectionSelected("approvedView");
	if(document.all["approvedView"].value == '${APPROVED_YES}'){
		document.getElementById("auditUserTree").style.display = "";
	}
})
   window.onload = function(){ 
         
          getDept('');  
        //  getresidentSiteName('');   \
       
            
       }
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

        //代维公司
       function getDept(v){
        
         myJ.getJSON("${app}/partner/oilmachine/OilEngineManagement.do?method=getDeptCom",
                   function(data){
                    myJ("#countryid").empty().append('<option value="" >--请选择--</option>');
                    myJ("#monitorCompanyid").empty().append('<option value="" >--请选择--</option>');
                   	myJ("#residentSiteNameid").empty().append('<option value="" >--请选择--</option>');
                   	myJ("#stationid").empty().append('<option value="" >--请选择--</option>');
                   	
 						for(var i=0;i<data.length;i++){
							myJ('<option/>')
								.val(data[i].deptid)
								.text(data[i].deptname)
								.appendTo("#monitorCompanyid");	
				    	}
				    	
				    	var monitorCompanyid = '${residentSiteForm.monitorCompanyid}';
                        if (monitorCompanyid != ''){
                         document.forms[0].monitorCompanyid.value = monitorCompanyid ;
                         }
				  
			        }
         );
       }
       //选取县区后的关联
       function getUnderContryInfo(v){
            getResidentSiteName(v);
            getStation(v);
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
			        }
         );
       }
       
       //发电基站
       function getStation(v){        
         myJ.getJSON("${app}/partner/oilmachine/OilEngineManagement.do?method=getStation&countryid="+v,
                   function(data){
                   	myJ("#stationid").empty().append('<option value="" >--请选择--</option>');
                    myJ("#stationid").append('<option value="1" >test</option>');
 						for(var i=0;i<data.length;i++){
							myJ('<option/>')
								.val(data[i].id)
								.text(data[i].residentSiteName)
								.appendTo("#stationid");	
				    	}			  
			        }
         );
       }
       
       //城市信息、区县下拉列表
       function getCityInfo(v){
     
         myJ.getJSON("${app}/partner/oilmachine/OilEngineManagement.do?method=getCityInfo&company="+v,
                   function(data){
                  
                  //   alert(data[0].area_id);
                //     alert(data[0].area_name)
				    document.forms[0].cityid.value = data[0].area_id;
				    document.forms[0].city.value = data[0].area_name;
				    getRegionCounty(data[0].area_id);
			        }
         ); 
       }
       
       //县区
       function getRegionCounty(v){
           
           myJ.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCountyCom&city="+v,
                   function(data){
  
                    myJ("#countryid").empty().append('<option value="" >--请选择--</option>');
                   	myJ("#residentSiteNameid").empty().append('<option value="" >--请选择--</option>');
                   	myJ("#stationid").empty().append('<option value="" >--请选择--</option>');
 						for(var i=0;i<data.length;i++){

							myJ('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#countryid");	
				    	}	
				    	document.forms[0].countryid.value= v;
				    	var countryid = '${residentSiteForm.countryid}';
				    	//alert('countryid:'+countryid);
                      if (countryid != ''){
                             document.forms[0].countryid.value = countryid ;
                        }
				
			        }
               );
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

          //区县
        var objcountry=document.getElementsByName("countryid")[0];
        var country= objcountry.options[objcountry.options.selectedIndex].text//
        document.forms[0].country.value = country ;
            //驻点名称
        var objresidentSiteName=document.getElementsByName("residentSiteNameid")[0];
        var residentSiteName= objresidentSiteName.options[objresidentSiteName.options.selectedIndex].text//
        document.forms[0].residentSiteName.value = residentSiteName ;
           //基站
        var objstation=document.getElementsByName("stationid")[0];
        var station= objstation.options[objstation.options.selectedIndex].text//
        document.forms[0].station.value = station ;
        
         //代维公司
        var objmonitorCompany=document.getElementsByName("monitorCompanyid")[0];
        var monitorCompany= objmonitorCompany.options[objmonitorCompany.options.selectedIndex].text//
        document.forms[0].monitorCompany.value = monitorCompany ;
	
	     document.forms[0].submit();
}

function autoCountFee(){
	document.all["totalFee"].value =  document.all["oilConsumingCount"].value * document.all["perPrice"].value;
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>