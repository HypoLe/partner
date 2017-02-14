<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%
 long localTimes=com.boco.eoms.base.util.StaticMethod.getLocalTime().getTime();
 %>
<%@ include file="/WEB-INF/pages/wfworksheet/pnrhiddentrouble/baseinputmainhtmlnew.jsp"%>

<script type="text/javascript">
	Ext.onReady(function() {
		initCountry();
		var maindealSituation = '${sheetMain.maindealSituation}'
		if(maindealSituation == '101200102'){//未处理
			document.getElementById("queation").disabled=true;
			document.getElementById("mainlegacyQuestion").style.display='none';
		}
	});

     	//selectLimit();
	function selectLimit(){
	    Ext.Ajax.request({
			method:"get",
			url: "pnrhiddentrouble.do?method=newShowLimit&flowName=PartnerHiddenTroubleProcess",
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
	
	
	
	 var v1 = eoms.form;	
	  function changeSituation(CaseKeywords){
		if(CaseKeywords=='101200102'){
			v1.disableArea('mainlegacyQuestion',true);
		}else{
		    v1.enableArea('mainlegacyQuestion');
		}		
	}
   </script>
	<input type="hidden" name="processTemplateName" value="PartnerHiddenTroubleProcess" />
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
    
    <input type="hidden" name="sheetTaskTypeBySheetId" value="012"/><!-- 工单任务类型，生成工单流水号专用 -->
    <input type="hidden" name="beanId" value="iPnrHiddenTroubleMainManager"/> 
    <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.pnrhiddentrouble.model.PnrHiddenTroubleMain"/>	
    <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.pnrhiddentrouble.model.PnrHiddenTroubleLink"/>
    <br>

    <!-- 工单基本信息 --> 
	<table id="sheet" class="formTable" >
		<tr>
		  <td class="label">受理时限*</td>
		  <td class="content">
		    <input type="text" class="text" name="sheetAcceptLimit" readonly="readonly" 
				id="sheetAcceptLimit" value="${eoms:date2String(sheetMain.sheetAcceptLimit)}" 
				onclick="popUpCalendar(this, this)" alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'受理时限不能晚于处理时限',allowBlank:false"/> 
	  			  
		  </td>				
		  <td class="label">完成时限*</td>
		  <td class="content">
		    <input type="text" class="text" name="sheetCompleteLimit" readonly="readonly" 
				id="sheetCompleteLimit" value="${eoms:date2String(sheetMain.sheetCompleteLimit)}" 
				onclick="popUpCalendar(this, this)" alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'完成时限不能早于受理时限',allowBlank:false"/>   
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
 			<td class="label">专业类型*</td>
			<td class="content">
				<eoms:comboBox name="mainSpecialty" id="mainSpecialty" defaultValue="${sheetMain.mainSpecialty}"
					initDicId="11225" alt="allowBlank:false" styleClass="select"/>
			</td>
			<td class="label">网元名称*</td>
			<td class="content">
				<input class="text" type="text" name="mainnetName"
					id="mainLocation" alt="allowBlank:false" value="${sheetMain.mainnetName }"/>
			</td>
		</tr>
		
 		<tr>
 			<td class="label">隐患类别*</td>
			<td class="content">
				<eoms:comboBox name="mainhiddenTroubleType" id="mainhiddenTroubleType" defaultValue="${sheetMain.mainhiddenTroubleType}"
					initDicId="1012002" alt="allowBlank:false" styleClass="select"/>
			</td>
			<td class="label">经度*</td>
			<td class="content">
			
				<input class="text" type="text" name="mainlongitude"
					id="mainlongitude" alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'" value="${sheetMain.mainlongitude }"/>
			</td>
		</tr>
		<tr>
 			<td class="label">纬度*</td>
			<td class="content">
				<input class="text" type="text" name="mainlatitude"
					id="mainlatitude" alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'" value="${sheetMain.mainlatitude }"/>
			</td>
			<td class="label">隐患紧急程度</td>
			<td class="content">
				<eoms:comboBox name="mainurgentLevel" id="mainurgentLevel" defaultValue="${sheetMain.mainurgentLevel}"
						initDicId="1010102"  styleClass="select"/>
			</td>
		</tr>
		
		<tr>
			<td class="label">现场处理情况</td>
			<td class="content">
				<eoms:comboBox name="maindealSituation" id="maindealSituation" defaultValue="${sheetMain.maindealSituation}"
					initDicId="1012001"  styleClass="select" onchange="changeSituation(this.value)" />
			</td>
			<td class="label"></td>
			<td class="content">
				
			</td>
		</tr>
		
		
		
		<tr>
			<td class="label">隐患现象描述</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainhiddenTroublePhenomenon" 
						id="mainTerminalType" alt="maxLength:255,vtext:'请填入内容，最多输入 255 字符'">${sheetMain.mainhiddenTroublePhenomenon}</textarea>
			</td>
		</tr>
		<tr>
			<td class="label">隐患位置描述</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainhiddenTroubleLocation" 
						id="mainTerminalType" alt="maxLength:255,vtext:'请填入内容，最多输入 255 字符'">${sheetMain.mainhiddenTroubleLocation}</textarea>
			</td>
			
		</tr>
		<tr id="mainlegacyQuestion">
			<td class="label">遗留问题</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainlegacyQuestion" 
						id="queation" alt="allowBlank:true,maxLength:255,vtext:'请填入内容，最多输入 255 字符'">${sheetMain.mainlegacyQuestion}</textarea>
			</td>
			
		</tr>
		<tr>
			<td class="label">处理建议</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainprocessSuggest" 
						id="mainTerminalType" alt="allowBlank:true,maxLength:255,vtext:'请填入内容，最多输入 255 字符'">${sheetMain.mainprocessSuggest}</textarea>
			</td>
		</tr>
		<tr>
			<td class="label">临时措施</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainprocessMethod" 
						id="mainTerminalType" alt="allowBlank:true,maxLength:255,vtext:'请填入内容，最多输入 255 字符'">${sheetMain.mainprocessMethod}</textarea>
			</td>
		</tr>
		
		
		
		
		
</table>

	
<!-- 附件 -->
<table id="sheet" class="formTable">
	    <tr>
		    <td class="label">
		    	<bean:message bundle="sheet" key="mainForm.accessories"/>
			</td>	
			<td colspan="3">					
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrhiddentrouble" alt="allowBlank:true"/> 				
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
		   category="[{id:'dealPerformer',text:'派发',allowBlank:false,limit:1,vtext:'请选择派发对象'},{id:'copyPerformer',childType:'user',limit:'none',text:'抄送'}]" 
		  data="${sheetMain.sendObjectTotalJSON}" />
	</div>	
</fieldset>
