<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/pages/wfworksheet/pnrrescheck/baseinputmainhtmlnew.jsp"%>

<script type="text/javascript">
	Ext.onReady(function() {
		initCountry();
	});

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
	
   </script>
	<input type="hidden" name="processTemplateName" value="PartnerResCheckProcess" />
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
    
    <input type="hidden" name="sheetTaskTypeBySheetId" value="009"/><!-- 工单任务类型，生成工单流水号专用 -->
    <input type="hidden" name="beanId" value="iPnrResCheckMainManager"/> 
    <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.pnrrescheck.model.PnrResCheckMain"/>	
    <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.pnrrescheck.model.PnrResCheckLink"/>
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
			<td class="content">
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
 			<td class="label">区县*</td>
			<td class="content">
				<!-- 区县 -->
				<select name="mainCountry" id="country" class="select"
					alt="allowBlank:false,vtext:'请选择所在县区'">
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
					<option value="1" <c:if test="${sheetMain.mainIfcharging eq '1' }">selected="selected"</c:if> >是</option>
					<option value="0" >否</option>
			
				</select>
			</td>
 			<td class="label">区域特征*</td>
			<td class="content">
				<eoms:comboBox  name="mainRegiontype" id="mainRegiontype"  defaultValue="${sheetMain.mainRegiontype}" alt="allowBlank:false"
	        			initDicId="11230"  styleClass="select" />
			</td>
		</tr>
 		<tr>
			<td class="label">专业类型*</td>
			<td class="content">
				<eoms:comboBox name="mainSpecialty" id="mainSpecialty" defaultValue="${sheetMain.mainSpecialty}"
					initDicId="11225" alt="allowBlank:false" styleClass="select"/>
			</td>
			<td class="label">紧急程度*</td>
			<td class="content">
				<eoms:comboBox name="mainUrgencyLevel" id="mainUrgencyLevel" defaultValue="${sheetMain.mainUrgencyLevel}"
						initDicId="1010102" alt="allowBlank:false" styleClass="select"/>
			</td>
		</tr>
		<tr>
 			<td class="label">任务描述*</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:1000,vtext:'请填入内容，最多输入 1000 字符'">${sheetMain.mainRemark}</textarea>
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
		            scope="request" idField="sheetAccessories" appCode="pnrrescheck" alt="allowBlank:true"/> 				
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
