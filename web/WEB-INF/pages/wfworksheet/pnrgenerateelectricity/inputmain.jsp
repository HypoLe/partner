<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%
 long localTimes=com.boco.eoms.base.util.StaticMethod.getLocalTime().getTime();
 %>
<%@ include file="/WEB-INF/pages/wfworksheet/pnrgenerateelectricity/baseinputmainhtmlnew.jsp"%>

<script type="text/javascript">
	Ext.onReady(function() {
		initCountry();
	});

     	//selectLimit();
	function selectLimit(){
	    Ext.Ajax.request({
			method:"get",
			url: "pnrgenerateelectricity.do?method=newShowLimit&flowName=PartnerGenerateElectricityProcess",
			success: function(x){
	        	var o = eoms.JSONDecode(x.responseText);
	        	if((o.acceptLimit == null || o.acceptLimit == "")&&(o.replyLimit == null || o.replyLimit == "")){
	        	   // $("sheetAcceptLimit").value = "";
	        	   // $('sheetCompleteLimit').value = "";
	           	}else{
	           	    var times=<%=localTimes%>;
		        	var dt1 = new Date().add(Date.MINUTE,parseInt(o.acceptLimit,10));
		        	var dt2 = dt1.add(Date.MINUTE,parseInt(o.replyLimit,10));
		           	$("sheetAcceptLimit").value = dt1.format('Y-m-d H:i:s');
		          	$('sheetCompleteLimit').value = dt2.format('Y-m-d H:i:s');
	        	}
	 		}
	    });
   }
   
   function delAllOption(elementid){
        var ddlObj = document.getElementById(elementid);//获取对象
        for(var i=ddlObj.length-1;i>=0;i--){
             ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
        }
    }

	//地市区县联动
	function changeCity(con){    
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
	
	/**	
	* 页面初始化地市下的区县
	*/
	function initCountry(){
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
					var country = "${sheetMain.mainCountry}";
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
	}
		
	/**
	*  打开选择资源页面
	*/
	function selectRes(){
		var url = '${app}/partner/res/PnrResConfig.do?method=searchResBySheet';
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
		
	
	/**
	* 设置资源名称和ID
	*/
	function setRes(returnValue){
		if(returnValue){
			var index = returnValue.indexOf(',');
			if(index != -1){
				var resId = returnValue.substring(0,index);
	        	var resName = returnValue.substring(index+1);
	        	document.getElementById('mainResId').value = resId;
	        	document.getElementById('mainResName').value = resName;
			}
        }
	}
   </script>
	<input type="hidden" name="processTemplateName" value="PartnerGenerateElectricityProcess" />
	<input type="hidden" name="operateName" value="newWorkSheet" />
	<input type="hidden" name="gatherPhaseId" value="HoldTask" />
	<c:if test="${status!=1}">
	   <input type="hidden" name="phaseId" id="phaseId" value="AcceptTask" />
       <input type="hidden" id="operateType" name="operateType" value="0" />
       <input type="hidden" name="gatherPhaseId" id="gatherPhaseId" value="HoldTask" />
    </c:if>
    
    <c:if test="${status==1}">
	   <input type="hidden" name="phaseId" id="phaseId" value="OverTask" />
    </c:if>
    
    <input type="hidden" name="sheetTaskTypeBySheetId" value="005"/><!-- 工单任务类型，生成工单流水号专用 -->
    <input type="hidden" name="beanId" value="iPnrGenerateElectricityMainManager"/> 
    <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.generateelectricity.model.PnrGenerateElectricityMain"/>	
    <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.generateelectricity.model.PnrGenerateElectricityLink"/>
    <br>

    <!-- 工单基本信息 --> 
	<table id="sheet" class="formTable" >
		<tr>
		  <td class="label">受理时限*</td>
		  <td class="content">
		    <input type="text" class="text" name="${sheetPageName}sheetAcceptLimit" readonly="readonly" 
				id="${sheetPageName}sheetAcceptLimit" value="${eoms:date2String(sheetMain.sheetAcceptLimit)}" 
				onclick="popUpCalendar(this, this)" alt="vtype:'lessThen',link:'${sheetPageName}sheetCompleteLimit',vtext:'受理时限不能晚于处理时限',allowBlank:false"/> 
	  			  
		  </td>				
		  <td class="label">处理时限*</td>
		  <td class="content">
		    <input type="text" class="text" name="${sheetPageName}sheetCompleteLimit" readonly="readonly" 
				id="${sheetPageName}sheetCompleteLimit" value="${eoms:date2String(sheetMain.sheetCompleteLimit)}" 
				onclick="popUpCalendar(this, this)" alt="vtype:'moreThen',link:'${sheetPageName}sheetAcceptLimit',vtext:'完成时限不能早于受理时限',allowBlank:false"/>   
		  </td>		  
		</tr>
		<tr>
			<td class="label">专业类型*</td>
			<td class="content">
				<eoms:comboBox name="mainSpecialty" id="mainSpecialty" defaultValue="${sheetMain.mainSpecialty}"
					initDicId="11225" alt="allowBlank:false" styleClass="select"/>
			</td>
 			<td class="label">站点类型*</td>
			<td class="content">
				<eoms:comboBox name="mainTaskType" id="mainTaskType" defaultValue="${sheetMain.mainTaskType}"
					initDicId="1010402" alt="allowBlank:false" styleClass="select"/>
			</td>
		</tr>
		<tr>
 			<td class="label">地市*</td>
			<td class="content" >
				<!-- 地市 -->			
				<select name="mainCity" id="city" class="select" alt="allowBlank:false,vtext:'请选择所在地市'" onchange="changeCity(0);">
					<option value="">
						--请选择所在地市--
					</option>
					<logic:iterate id="city" name="city">
						<c:if test="${sheetMain.mainCity==city.areaid}" var="result">
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
			<td class="content" >
				<!-- 区县 -->			
				<select name="mainCountry" id="country" class="select"
					alt="allowBlank:true,vtext:'请选择所在县区'">
					<option value="">
						--请选择所在县区--
					</option>				
				</select>
			</td>
 		</tr>
 		<tr>
			<td class="label">是否计费</td>
			<td class="content">
				<select name="mainIfcharging" class="select">
					<option value="0" >否</option>
					<option value="1" <c:if test="${sheetMain.mainIfcharging eq '1' }">selected="selected"</c:if> >是</option>
				</select>
			</td>
 			<td class="label">区域特征*</td>
			<td class="content">
				<eoms:comboBox  name="mainRegiontype" id="mainRegiontype"  defaultValue="${sheetMain.mainRegiontype}" alt="allowBlank:false"
	        			initDicId="11230"  styleClass="select" />
			</td>
		</tr>
 		<tr>
			<td class="label">站点</td>
			<td class="content">
				<input class="text" type="text" name="mainResName" readonly="true" 
					id="mainResName" alt="allowBlank:true" value="${sheetMain.mainResName }"/>
				<input type="hidden" name="mainResId" id="mainResId" value="" />
				 <input type="button" class="btn" value="选择" onclick="selectRes()" />
			</td>
 			<td class="label">回复需附件*</td>
			<td class="content">
				<eoms:comboBox name="mainFileNeeded" id="mainFileNeeded" defaultValue="${sheetMain.mainFileNeeded}"
					initDicId="10301" alt="allowBlank:false" styleClass="select"/>
			</td>
		</tr>
		<tr>
			<td class="label">停电发生时间*</td>
			<td class="content">
				<!-- 
				<input type="text" class="text" name="${sheetPageName}sheetCompleteLimit" readonly="readonly" 
				id="${sheetPageName}sheetCompleteLimit" value="${eoms:date2String(sheetMain.sheetCompleteLimit)}" 
				onclick="popUpCalendar(this, this)" alt="vtype:'moreThen',link:'${sheetPageName}sheetAcceptLimit',vtext:'完成时限不能早于受理时限',allowBlank:false"/>   
					 -->
			
				<input class="text" type="text" name="mainPowerCutOccurTime" readonly="readonly" 
					id="mainPowerCutOccurTime" alt="allowBlank:false" value="${sheetMain.mainPowerCutOccurTime }" onclick="popUpCalendar(this, this,null,null,null,true,-1,0);"
					/>
			</td>
			<td class="label">紧急程度*</td>
			<td class="content">
				<eoms:comboBox name="mainExigenceLevel" id="mainExigenceLevel" defaultValue="${sheetMain.mainExigenceLevel}"
					initDicId="1010102" alt="allowBlank:false" styleClass="select"/>
			</td>
			
			<!-- 
 			<td class="label">服务区域*</td>
			<td class="content">
				<eoms:comboBox name="mainServiceArea" id="mainServiceArea" defaultValue="${sheetMain.mainServiceArea}"
					initDicId="10301" alt="allowBlank:false" styleClass="select"/>
			</td> -->
		</tr>
 		<tr>
			<td class="label">巡检任务地点*</td>
			<td class="content">
				<input class="text" type="text" name="mainLocation"
					id="mainLocation" alt="allowBlank:false" value="${sheetMain.mainLocation }"/>
			</td>
			
		</tr>
 			<td class="label">
				停电事件描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:1000,vtext:'请填入巡检任务描述，最多输入 1000 字符'">${sheetMain.mainRemark}</textarea>
			</td>
</table>

	
<!-- 附件 -->
<%---
---%>
<table id="sheet" class="formTable">
	  <tr>	
		    <td class="label">
		    	<bean:message bundle="sheet" key="mainForm.accessories"/>
			</td>	
			<td colspan="3">					
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrgenerateelectricity" alt="allowBlank:true"/> 				
		    </td>
	   </tr>			  
</table>

<!--派单树-->
<br/>
<fieldset>
 	<legend>
     	 <bean:message bundle="sheet" key="mainForm.toOperateRoleName"/>：
		 <span id="roleName">
		 	 处理人员
		 </span>
  	</legend>
    <div class="x-form-item" >
		<eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
		   category="[{id:'dealPerformer',text:'派发',allowBlank:false,limit:'none',vtext:'请选择派发对象'},{id:'copyPerformer',childType:'user',limit:'none',text:'抄送'}]" 
		  data="${sheetMain.sendObjectTotalJSON}" />
	</div>	
</fieldset>
