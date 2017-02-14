<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
  Ext.onReady(function(){
   
   v = new eoms.form.Validation({form:'theform'});
	
   });
</script>

<html:form action="/shortPeriod.do?method=updateTower" styleId="theform" >
	<input type="hidden" id="productNum" name="productNum" value="${towerModel.productNum}" />
	<input type="hidden" id="condition" name="condition" value="${condition}" />
	<table id="jibenTable" class="formTable">
		<tr>
			<td class="label" style="width:100%" colspan = "6" align="center">
				基本信息
			</td>
		</tr>
		<tr>
			<td class="label" style="width: 10%">
				产品业务确认单编号
			</td>
			<td class="content" style="width: 23%">
				${towerModel.productNum}
			</td>
			
			<td class="label" style="width: 10%">
				所属地市
			</td>
			<td class="content" style="width: 23%">
				${towerModel.cityName}
			</td>

			<td class="label" style="width: 10%">
				区县
			</td>
			<td class="content" style="width: 23%">
				${towerModel.countryName}
			</td>
		</tr>
		<tr>
			<td class="label" style="width: 10%">
				站址名称
			</td>
			<td class="content" style="width: 23%">
				${towerModel.resName}
			</td>
			
			<td class="label" style="width: 10%">
				站址编码
			</td>
			<td class="content" style="width: 23%">
				${towerModel.nameId}
			</td>

			<td class="label" style="width: 10%">
				需求确认编号
			</td>
			<td class="content" style="width: 23%">
				${towerModel.nameNumber}
			</td>
		</tr>
	</table>
	
	<table id="updateTable" style="margin-top:5px;" class="formTable" >
		<tr>
			<td>
				&nbsp;
			</td>
			<td class="label">
				原资源内容
			</td>
			<td class="label">
				新资源内容
			</td>
		</tr>
		<tr>
			<td class="label">
				产品类型
			</td>
			<td>
				${towerModel.a2}
			</td>
			<td>
				<eoms:comboBox name="newa2" id="newa2" defaultValue="${towerModel.newa2}" initDicId="10308"
								alt="allowBlank:true" styleClass="select" />
			    <!-- <input type="text" id="newa2" name="newa2" class="text" value="${towerModel.newa2}" /> -->
			</td>
		</tr>
		<tr>
			<td class="label">
				机房类型
			</td>
			<td>
				${towerModel.a3}
			</td>
			<td>
				<eoms:comboBox name="newa3" id="newa3" defaultValue="${towerModel.newa3}" initDicId="10307"
									alt="allowBlank:true" styleClass="select" />
				<!-- <input type="text" id="newa3" name="newa3" class="text" value="${towerModel.newa3}" /> -->
			</td>
		</tr>
		<tr>
			<td class="label">
				产品单元数1
			</td>
			<td>
				${towerModel.a4}
			</td>
			<td>
				<eoms:comboBox name="newa4" id="newa4" defaultValue="${towerModel.newa4}" initDicId="10309"
										alt="allowBlank:true" styleClass="select" />
				<!-- <input type="text" id="newa4" name="newa4" class="text" value="${towerModel.newa4}" />-->
			</td>
		</tr>
		<tr>
			<td class="label">
				对应实际最高天线挂高1
			</td>
			<td>
				${towerModel.a5}
			</td>
			<td>
				<eoms:comboBox name="newa5" id="newa5" defaultValue="${towerModel.newa5}" initDicId="10310"
											alt="allowBlank:true" styleClass="select" />
				<!--<input type="text" id="newa5" name="newa5" class="text" value="${towerModel.newa5}" />-->
			</td>
		</tr>
		<tr>
			<td class="label">
				BBU是否放在铁塔公司机房1
			</td>
			<td>
				${towerModel.a6}
			</td>
			<td>
				<eoms:comboBox name="newa6" id="newa6" defaultValue="${towerModel.newa6}" initDicId="10301"
												alt="allowBlank:true" styleClass="select" />
				<!--<input type="text" id="newa6" name="newa6" class="text" value="${towerModel.newa6}" />-->
			</td>
		</tr>
		<tr>
			<td class="label">
				产品单元数2
			</td>
			<td>
				${towerModel.a8}
			</td>
			<td>
				<input type="text" id="newa8" name="newa8" class="text" value="${towerModel.newa8}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				实际最高天线挂高2
			</td>
			<td>
				${towerModel.a9}
			</td>
			<td>
				<input type="text" id="newa9" name="newa9" class="text" value="${towerModel.newa9}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				BBU是否放在铁塔公司机房2
			</td>
			<td>
				${towerModel.a10}
			</td>
			<td>
				<input type="text" id="newa10" name="newa10" class="text" value="${towerModel.newa10}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				产品单元数3
			</td>
			<td>
				${towerModel.a12}
			</td>
			<td>
				<input type="text" id="newa12" name="newa12" class="text" value="${towerModel.newa12}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				实际最高天线挂高3
			</td>
			<td>
				${towerModel.a13}
			</td>
			<td>
				<input type="text" id="newa13" name="newa13" class="text" value="${towerModel.newa13}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				BBU是否放在铁塔公司机房3
			</td>
			<td>
				${towerModel.a14}
			</td>
			<td>
				<input type="text" id="newa14" name="newa14" class="text" value="${towerModel.newa14}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				期末铁塔共享用户数
			</td>
			<td>
				${towerModel.a16}
			</td>
			<td>
				<input type="text" id="newa16" name="newa16" class="text" value="${towerModel.newa16}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				期末机房共享用户数
			</td>
			<td>
				${towerModel.a21}
			</td>
			<td>
				<input type="text" id="newa21" name="newa21" class="text" value="${towerModel.newa21}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				配套共享用户数
			</td>
			<td>
				${towerModel.a26}
			</td>
			<td>
				<input type="text" id="newa26" name="newa26" class="text" value="${towerModel.newa26}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				场地费共享用户数
			</td>
			<td>
				${towerModel.a35}
			</td>
			<td>
				<input type="text" id="newa36" name="newa36" class="text" value="${towerModel.newa36}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				电力引入费共享用户数
			</td>
			<td>
				${towerModel.a41}
			</td>
			<td>
				<input type="text" id="newa41" name="newa41" class="text" value="${towerModel.newa41}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				维护费共享用户数
			</td>
			<td>
				${towerModel.a48}
			</td>
			<td>
				<input type="text" id="newa31" name="newa31" class="text" value="${towerModel.newa31}" />
			</td>
		</tr>
	</table>
	
	<table id="tianxieTable" style="width:100%;margin-top:5px;" class="formTable">
		<tr>
			<td class="label" style="width:20%">
				铁塔共享运营商1的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa17"
								readonly="readonly" id="newa17" value="${towerModel.newa17}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				铁塔共享运营商1起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa18" name="newa18" class="text" value="${towerModel.newa18}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				铁塔共享运营商2的起租日期 
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa19"
								readonly="readonly" id="newa19" value="${towerModel.newa19}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				铁塔共享运营商2起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa20" name="newa20" class="text" value="${towerModel.newa20}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				机房共享运营商1的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa22"
								readonly="readonly" id="newa22" value="${towerModel.newa22}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				机房共享运营商1起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa23" name="newa23" class="text" value="${towerModel.newa23}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				机房共享运营商2的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa24"
								readonly="readonly" id="newa24" value="${towerModel.newa24}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				机房共享运营商2起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa25" name="newa25" class="text" value="${towerModel.newa25}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				配套共享运营商1的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa27"
								readonly="readonly" id="newa27" value="${towerModel.newa27}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				配套共享运营商1起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa28" name="newa28" class="text" value="${towerModel.newa28}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				配套共享运营商2的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa29"
								readonly="readonly" id="newa29" value="${towerModel.newa29}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				配套共享运营商2起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa30" name="newa30" class="text" value="${towerModel.newa30}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				维护费共享运营商1的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa32"
								readonly="readonly" id="newa32" value="${towerModel.newa32}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				维护费共享运营商1起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa33" name="newa33" class="text" value="${towerModel.newa33}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				维护费共享运营商2的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa34"
								readonly="readonly" id="newa34" value="${towerModel.newa34}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				维护费共享运营商2起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa35" name="newa35" class="text" value="${towerModel.newa35}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				场地费
			</td>
			<td style="width:30%">
				<input type="text" id="newa48" name="newa48" class="text" value="${towerModel.newa48}" />
			</td>
			<td class="label" style="width:20%">
				电力引入费
			</td>
			<td style="width:30%">
				<input type="text" id="newa49" name="newa49" class="text" value="${towerModel.newa49}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				场地费共享运营商1的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa37"
								readonly="readonly" id="newa37" value="${towerModel.newa37}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				场地费共享运营商1起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa38" name="newa38" class="text" value="${towerModel.newa38}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				场地费共享运营商2的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa39"
								readonly="readonly" id="newa39" value="${towerModel.newa39}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				场地费共享运营商2起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa40" name="newa40" class="text" value="${towerModel.newa40}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				电力引入费共享运营商1的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa42"
								readonly="readonly" id="newa42" value="${towerModel.newa42}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				电力引入费共享运营商1起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa43" name="newa43" class="text" value="${towerModel.newa43}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				电力引入费共享运营商2的起租日期
			</td>
			<td style="width:30%">
						<input type="text" class="text" name="newa44"
								readonly="readonly" id="newa44" value="${towerModel.newa44}"
								onclick="popUpCalendar(this,this,'yyyy-mm-dd',null,null,false,-1,null)"
								 />
			</td>
			<td class="label" style="width:20%">
				电力引入费共享运营商2起租后的共享折扣
			</td>
			<td style="width:30%">
				<input type="text" id="newa45" name="newa45" class="text" value="${towerModel.newa45}" />
			</td>
		</tr>
		
		<tr>
			<td class="label" style="width:20%">
				备注
			</td>
			<td style="width:30%">
				<eoms:comboBox name="towerRemark" id="towerRemark" defaultValue="${towerModel.towerRemark}" initDicId="10314"
												alt="allowBlank:true" styleClass="select" />
			</td>
			<td class="label" style="width:20%">
				描述
			</td>
			<td style="width:30%">
				<input type="text" id="towerDescribe" name="towerDescribe" class="text" value="${towerModel.towerDescribe}" />
			</td>
		</tr>
		
	</table>
	
	<br/>
	<!-- buttons -->
	<div class="form-btns" id="btns" style="display:block">
		<html:submit styleClass="btn" property="method.save" onclick="return changeType1();" styleId="method.save">
			提交
		</html:submit>
	</div>
</html:form>
</script>  