<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<style type='text/css'>
  .td {background-color:#00ff00} 
</style>
<script type="text/javascript">
 //var myJ = $.noConflict();
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
        //alert('v:'+v);
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
	
	 function  test(){
    
        var monitorCompany = document.forms[0].monitorCompany.value ;
         if(monitorCompany == '' || monitorCompany == null){
          return ;
         }
        myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCityInfo&monitorCompany="+monitorCompany,
                   function(data){
						  document.forms[0].cityid.value = data[0].area_id ; 	
						  document.forms[0].city.value = data[0].area_name ; 
						  if(data[0].area_id != ''){
						    getRegionCounty(data[0].area_id);	  
						  }
						  					
			        }
               );
         
        

   }
   
    //县区
       function getRegionCounty(v){
          
           myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCountyCom&city="+v,
                   function(data){  
                	    myjs("#countryid").empty().append('<option value="" >--请选择--</option>');
                  		for(var i=0;i<data.length;i++){
							myjs('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#countryid");	
				    	}	
				    	// document.forms[0].countryid.value= v;			
			        }
               );
       }
       
     
	  // 自定义2个日期相减
	 function subTime(beginTime,endTime){
	   beginTime = beginTime.substring(0,19);
	   endTime = endTime.substring(0,19);
	//   alert('beginTime:'+beginTime);
	 //  alert('endTime:'+endTime);
	   var d1 = Date.parseDate(beginTime,'Y-m-d h:i:s');
	   var d2 = Date.parseDate(endTime,'Y-m-d h:i:s');
	   	    
	  var milliSecondSub = d2.getTime()-d1.getTime();	  
	  if(milliSecondSub<0){
	   milliSecondSub = -milliSecondSub;	  
	  }	  
	  second = milliSecondSub/1000%60;
	  minute = milliSecondSub/1000/60%60;
	  hour = milliSecondSub/1000/60/60%24;
	  day = Math.floor(milliSecondSub/1000/60/60/24);	  //.toFixed(0)  
	//  alert(day+'天'+hour+'小时'+minute+'分钟');		 
	  return day+'天'+hour+'小时'+minute+'分钟';
	 }


	
</script>
<form  id="checkAndSearchFrom" action="OilEngineManagement.do?method=getReportDetail" method="post">
<fieldset>
			<legend>请输入统计条件</legend>
<table class="formTable">
		<tr>
		
		<td class="label">代维公司</td>    
			<td>
			<input type="text" id="monitorCompany" name="monitorCompany" value="${residentSiteForm.monitorCompany}"  onfocus="test()"  alt="allowBlank:false"/>
			<input type="hidden" id="monitorCompanyid" name ="monitorCompanyid" value="${residentSiteForm.monitorCompanyid}"/></td>
			<eoms:xbox id="partnerNameTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
				rootId="" rootText="代维公司树"  valueField="monitorCompanyid" handler="monitorCompany" 
			textField="monitorCompany" checktype="dept" single="true" />
			<td class="label">地市</td>
			<td>    
			<input id="cityid" name="cityid" type="hidden" > 
           <input id="city" name="city" type="text" > 
		   </td>
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
						<td class="label">开始时间</td>
			<td>
			<input class="text" type="text" name="beginTime" value="${oilEngine.recordTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					 id="operateTime" readonly="readonly"/>		
			</td>		
			<td class="label">结束时间</td>
			<td>
			<input class="text" type="text" name="endTime" value="${oilEngine.recordTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					 id="operateTime" readonly="readonly"/>		
			</td>
		</tr>
	</table>
	</fieldset>

<input type="button" name="统计" value="统计" onclick="sendBox()"/>
<table class="formTable" style="width:100%" >
	<tr >
		<td rowspan="2" align="center" valign="middle" style="width:8%;background:#EDF5FD">地市</td>		
		<td rowspan="2" align="center" valign="middle" style="width:8%;background:#EDF5FD">县区</td>
		<td rowspan="2" align="center" valign="middle" style="width:9%;background:#EDF5FD">驻点名称</td>
		<td rowspan="2" align="center" valign="middle" style="width:9%;background:#EDF5FD">基站名称</td>
		<td colspan="3" align="center" style="width:20%;background:#EDF5FD">代维录入油机发电信息</td>
		<td colspan="3" align="center" style="width:20%;background:#EDF5FD">动环告警信息</td>
		<td colspan="3" align="center" style="width:20%;background:#EDF5FD">能耗信息</td>
	</tr>
	<tr>
	   <td style="background:#EDF5FD">开始时间</td>
	   <td style="background:#EDF5FD">结束时间</td>
	   <td style="background:#EDF5FD">&nbsp;时&nbsp;长</td>
	   <td style="background:#EDF5FD">开始时间</td>
	   <td style="background:#EDF5FD">结束时间</td>
	   <td style="background:#EDF5FD">&nbsp;时&nbsp;长</td>
	   <td style="background:#EDF5FD">历史电量</td>
	   <td style="background:#EDF5FD">发电期间电量</td>
	   <td style="background:#EDF5FD">节省电量</td>
	</tr>
	  <logic-el:present name="tableList">
	    <c:forEach var="rowMap" items="${tableList}">
	      <tr align="center">
	        <td>${rowMap.city}</td>
	        <td>${rowMap.country}</td>
	        <td>${rowMap.residentSiteName}</td>
	        <td>${rowMap.station}</td>
	        <td>${rowMap.oilbegintime}</td>
	        <td>${rowMap.oilendtime}</td>
	        <td><script type="text/javascript">document.write(subTime('${rowMap.oilbegintime}','${rowMap.oilendtime}'))</script></td>
	        <td>${rowMap.netoilbegintime}</td>
	        <td>${rowMap.netoilendtime}</td>
	        <td><script type="text/javascript">document.write(subTime('${rowMap.netoilbegintime}','${rowMap.netoilendtime}'))</script></td>	       
	        <td>-</td>
	        <td>-</td>
	        <td>-</td>
	      <tr>
	    </c:forEach>
	  </logic-el:present>

</table>
 </form>
<%@ include file="/common/footer_eoms.jsp"%>