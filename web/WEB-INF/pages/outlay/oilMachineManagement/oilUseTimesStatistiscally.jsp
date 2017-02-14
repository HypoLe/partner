<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
function sendBox() {
	
		document.getElementById("checkAndSearchFrom").submit();
	}
	
	function changeCheckBox(){
		var area_name = myjs('#dt0nameT').val();
		var accName=myjs('#d0area_nameT').val();
		var aptitude=myjs('#d0nameT').val();
		var d0diploma=myjs('#d0typeT').val();
		if(area_name){
		myjs('#dt0name').attr('checked',true);
		myjs('#dt0name').attr('disabled','disabled');
		}
		else{
			if((myjs('#dt0name').attr('disabled'))){
		myjs('#dt0name').attr('checked',false);
		myjs('#dt0name').attr('disabled','');
		}
		}
		if(accName){
		myjs('#d0area_name').attr('checked',true);
		myjs('#d0area_name').attr('disabled','disabled');
		}
		else{
			if((myjs('#d0area_name').attr('disabled'))){
		myjs('#d0area_name').attr('checked',false);
		myjs('#d0area_name').attr('disabled','');
		}
		}
		if(aptitude){
		myjs('#d0name').attr('checked',true);
		myjs('#d0name').attr('disabled','disabled');
		}
		else{
			if((myjs('#d0name').attr('disabled'))){
		myjs('#d0name').attr('checked',false);
		myjs('#d0name').attr('disabled','');
		}
		}
		if(d0diploma!=''){
			myjs('#d0type').attr('checked',true);
			myjs('#d0type').attr('disabled','disabled');
			}
			else{
				if((myjs('#d0type').attr('disabled'))){
			myjs('#d0type').attr('checked',false);
			myjs('#d0type').attr('disabled','');
			}
			}
	}
	
	
	
	
	Ext.onReady(function(){
	 
	  getRegionCity();
	var check=document.getElementById("checkIds");
	if(check&&check.value!=""){
	checkV=check.value
	var checks=checkV.toString().split(";");
	var cardNumberList = document.getElementsByName("cardNumber");
	for(var i=0;i<checks.length-1 ;i++){
	//alert(checks[i].toString());
	checkValue ='#'+checks[i].toString();
	myjs(checkValue).attr('checked',true);
	}
	}
	});
	
	 //选取县区后的关联
       function getUnderCountryInfo(v){
            getResidentSiteName(v);
            getStation(v);
       }
	
	//地市
       function getRegionCity(){
        
         myjs.getJSON("${app}/partner/oilmachine/NetOilEngineManagement.do?method=getRegionCity",
                   function(data){
                   	myjs("#countryid").empty().append('<option value="" >--请选择--</option>');
                   	myjs("#cityid").empty().append('<option value="" >--请选择--</option>');
                    myjs("#residentSiteNameid").empty().append('<option value="" >--请选择--</option>');
 					myjs("#stationid").empty().append('<option value="" >--请选择--</option>');
 					
 						for(var i=0;i<data.length;i++){
							myjs('<option/>')
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
        
         myjs.getJSON("${app}/partner/oilmachine/NetOilEngineManagement.do?method=getRegionCountry&city="+v,
                   function(data){
                  
                   	myjs("#countryid").empty().append('<option value="" >--请选择--</option>');
                
 						for(var i=0;i<data.length;i++){
							myjs('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#countryid");	
				    	}	
				    	
				    	var  countryid = '${netOilEngine.countryid}';    
					        if(countryid != ''){
					          document.forms[0].countryid.value = countryid ;						          			         
					        }			  
			        }
         );
       }
       
       //驻点信息
       function getResidentSiteName(v){
        
         myjs.getJSON("${app}/partner/oilmachine/OilEngineManagement.do?method=getResidentSiteName&countryid="+v,
                   function(data){
                   	myjs("#residentSiteNameid").empty().append('<option value="" >--请选择--</option>');
                
 						for(var i=0;i<data.length;i++){
							myjs('<option/>')
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
       
        //发电基站
       function getStation(v){        
       
         myjs.getJSON("${app}/partner/oilmachine/OilEngineManagement.do?method=getStation&countryid="+v,
                   function(data){
                   	myjs("#stationid").empty().append('<option value="" >--请选择--</option>');
                  
 						for(var i=0;i<data.length;i++){
							myjs('<option/>')
								.val(data[i].id)
								.text(data[i].station)
								.appendTo("#stationid");	
				    	}
				    	var  stationid = '${netOilEngine.stationid}';    
					        if(stationid != ''){
					          document.forms[0].stationid.value = stationid ;						          			         
					        }				  
			        }
         );
       }
	
	
</script>
<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="10"
	rootText='所属地市' valueField="d0area_nameId" handler="d0area_nameT" textField="d0area_nameT"
	checktype="area" single="true"></eoms:xbox>
	<eoms:xbox id="tree2" dataUrl="${app}/partner/statistically/paternerStaff.do?method=dept&type=parent" rootId="2"
	rootText='代维公司（省）' valueField="dt0nameTId" handler="dt0nameT" textField="dt0nameT"
	checktype="dept" single="true"></eoms:xbox>

<form  id="checkAndSearchFrom" action="OilEngineManagement.do?method=getOilUseTimes" method="post">

<fieldset>
			<legend>请输入统计条件</legend>
<table class="formTable">
		<tr>
		
		<td class="label">地市</td>    
			<td>
         <input id="city" name="city" type="hidden"  value="${netOilEngine.city}"> 
		<select id="cityid" name="cityid" class="ddd" onchange="getRegionCountry(this.options[this.options.selectedIndex].value);">
		    </select>	
			<td class="label">县区</td>
			<td>
				<input id="country" name="country" type="hidden"  value="${netOilEngine.country}"> 
		<select id="countryid" name="countryid" class="ddd" onchange="getUnderCountryInfo(this.options[this.options.selectedIndex].value);">
		    </select>							
						</tr>
						<tr>
						<td class="label">驻点名称</td>
			<td>
			<input id="residentSiteName" name="residentSiteName" type="hidden" value="${oilEngine.residentSiteName}" > 
	    	<select id=residentSiteNameid name="residentSiteNameid" class="ddd" >
		    </select>	
			</td>		
			<td class="label">发电基站</td>
			<td>
			<input id="station" name="station" type="hidden"  value="${oilEngine.stationid}" > 
		    <select id="stationid" name="stationid" class="ddd" >
		    </select>	
			</td>
		</tr>
			<tr>
						<td class="label">油机使用开始时间</td>
			<td>
			<input class="text" type="text" name="beginTime" value="${oilEngine.recordTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					 id="operateTime" readonly="readonly"/>		
			</td>		
			<td class="label">油机使用结束时间</td>
			<td>
			<input class="text" type="text" name="endTime" value="${oilEngine.recordTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					 id="operateTime" readonly="readonly"/>		
			</td>
		</tr>
	</table>
	</fieldset>

<input type="button" name="统计" value="统计" onclick="sendBox()"/>
	<!-- This hidden area is for batch deleting. -->
<form>

<div>
 <table  cellpadding="0" class="table protocolMainList" width="90%" cellspacing="0">
 	 <thead>
	 <tr >
	 <logic-el:present name="headList">
	 <c:forEach items="${headList}"  var="headlist" >
	 <th width="45%">
	 ${headlist}
	 </th>
	 
	 </c:forEach>
	 
     </logic-el:present>
     </tr>
     </thead>
     <logic-el:present name="tableList">
     <tbody>
     <c:forEach items="${tableList}" var="tdList">
     <tr>
     <c:forEach items="${tdList}" var="tdValue"> 
     <td>
     ${tdValue}
     </td> 
     </c:forEach>
     </tr>
     </c:forEach>
	  </tbody>
	  </logic-el:present>
 </table>
</div>






<%@ include file="/common/footer_eoms.jsp"%>