<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<form action="statistic.do?method=statistic" method="post" name="statisticFrom" id="statisticFrom">
	<fieldset>
		<legend>查询条件</legend>
		<table id="statisticFrom" class="formTable">
			<tr>
				<td><input type="radio" name="statisType" id="statisType" value="byTime" checked="checked" onchange="changeType(this.value)"/> 时间段统计</td>
				<td class="label">开始时间: </td>
				<td><input class="text" type="text" name="startTime"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="allowBlank:false,vtype:'lessThen',link:'endTime',vtext:'开始时间不能晚于结束时间！'"
					readonly="readonly" id="startTime"  /></td>
				<td class="label">结束时间: </td>
				<td><input class="text" type="text" name="endTime"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="allowBlank:false,vtype:'moreThen',link:'startTime',vtext:'开始时间不能晚于结束时间！'"
					readonly="readonly" id="endTime" /></td>
			</tr>
			<tr>
				<td><input type="radio" name="statisType" id="statisType"  value="byYear"  onchange="changeType(this.value)"/> 按年统计</td>
				<td class="label">年份: </td>
				<td><select id="year" name="year" disabled="disabled" >
					<option value="2010" >2010年</option>
					<option value="2011" >2011年</option>
					<option value="2012" >2012年</option>
				</select></td>
			</tr>
			<tr>
				<td><input type="radio" name="statisType" id="statisType"   value="byQuarter" onchange="changeType(this.value)"/> 按季度统计</td>
				<td class="label">年份: </td>
				<td><select id="yearAndQuarter" name="yearAndQuarter" disabled="disabled" >
					<option value="2010" >2010年</option>
					<option value="2011" >2011年</option>
					<option value="2012" >2012年</option>
				</select></td>
				<td class="label">季度: </td>
				<td><select id="quarter" name="quarter" disabled="disabled" >
					<option value="1" >一季度</option>
					<option value="2" >二季度</option>
					<option value="3" >三季度</option>
					<option value="4" >四季度</option>
				</select></td>
			</tr>
			<tr>
			<td></td>
				<td class="label">专业: </td>
			<td><eoms:comboBox name="special" id="special"
				initDicId="11216" alt="allowBlank:false,vtext:'请选择专业...'"  onchange="change()"/></td>
				<td class="label">设备类型: </td>
				 <td class="content" colspan="3">
						<select name="devicetype" id="devicetype" class="select max"
							alt="allowBlank:false,vtext:'请选择设备类型'">
							<option value="">
								请选择设备类型
							</option>
						</select>
					</td>
			</tr>
			<tr>
			<td></td>
				<td class="label">统计模式</td>
				<td><select name="statisticType" id="statisticType" onchange="changeStatistic(this.value)" >
				<option value="single">单专业统计</option>
				<option value="completely">全专业统计</option>
				</select></td>
				<td class="label">厂家（如果不选默认统计所有厂家）</td>
					<td >
			<eoms:comboBox name="factory" id="factory" initDicId="1121502" 
			    multiple="multiple" />		
		</td>
			</tr>
		</table>
		<center><input type="submit" value="统计" calss="btn"/></center>
	</fieldset>
</form>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
 var myjs=jQuery.noConflict();
Ext.onReady(function(){
            v = new eoms.form.Validation({form:'statisticFrom'});
            var unId = "${unIdName}";
            if(unId){
            	alert('${unIdName}没有此类型设备');
            }
            var deviceType= "${deviceTypeName}";
            if(deviceType){
            	alert('请录入${deviceTypeName}的指标值');
            	}
            });
            document.getElementById("devicetype").disabled='disabled';
            
            function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
	            }
    }
	//专业 联动 设备类型	
	function changeFacility(con){
		
		    delAllOption("devicetype");
			var speciality = document.getElementById("special").value;
			var url="<%=request.getContextPath()%>/partner/deviceAssess/backEstimates.do?method=changeFacility&speciality="+speciality;
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									var json = eval(res);
									var devicetype = "devicetype";
									var arrOptions = json[0].facility.split(",");
									var obj=$(devicetype);
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									if(con==1){
										var devicetype = '${softApplyRecord.devicetype}';
										if(devicetype!=''){
											document.getElementById("devicetype").value = devicetype;
										}	
									
									}	
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
	}
            
            
            
            
            
            
            function change(){
            var type = document.getElementById("statisticType").value;
            	if(type=='single'){
            	changeFacility(1);
            	}
            	changeStatistic(type);
            	
            }
            function changeStatistic(type){
            	if(type=='completely'){
            	myjs('#devicetype').removeAttr('alt');
            	document.getElementById("devicetype").value='';
            		document.getElementById("devicetype").disabled='disabled';
            		document.getElementById("factory").disabled='disabled';
            		document.getElementById("factory").value='';
            	}
            	if(type=='single'){
            		document.getElementById("devicetype").disabled='';
            		myjs('#devicetype').attr("alt","allowBlank:false,vtext:'请选择设备类型...'");
            		document.getElementById("factory").disabled='';
            		
            	}
            }
            
            
            
	function changeType(type){
		if(type=='byTime'){
			document.getElementById("startTime").disabled='';
			document.getElementById("endTime").disabled='';
			document.getElementById("year").disabled='disabled';
			document.getElementById("yearAndQuarter").disabled='disabled';
			document.getElementById("quarter").disabled='disabled';
		}
		if(type=='byYear'){
			document.getElementById("year").disabled='';
			document.getElementById("startTime").disabled='disabled';
			document.getElementById("endTime").disabled='disabled';
			document.getElementById("yearAndQuarter").disabled='disabled';
			document.getElementById("quarter").disabled='disabled';
		}
		if(type=='byQuarter'){
		document.getElementById("year").disabled='disabled';
		document.getElementById("startTime").disabled='disabled';
		document.getElementById("endTime").disabled='disabled';
		document.getElementById("yearAndQuarter").disabled='';
		document.getElementById("quarter").disabled='';
		}
	}
</script>



<%@ include file="/common/footer_eoms.jsp"%>
