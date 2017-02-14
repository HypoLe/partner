<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
var v;
  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
   });
</script>  
<div id="sheetform">
	<html:form action="/pnrTroubleTicket.do?method=workOrderQuery" styleId="theform">
		<table class="formTable">
	
           <tr>
		   <!-- 工单标题 -->
              <td class="label">
                工单标题
              </td>
              <td>
	    		<input type="text" name="title" class="text max" id="title"  maxLength="20"
				value="" alt="allowBlank:true,maxLength:200,vtext:'请输入工单主题，最大长度为20个汉字！'"/>
	 		 </td>
       		  <td class="label">
              执行人
              </td>              
              <td>
	    		<eoms:xbox id="dutyManTree" dataUrl="${app}/partner/statistically/paternerStaff.do?method=user&isPartner=${isPartner}" rootId="${sessionform.rootPnrDeptId}"
						rootText='执行人' valueField="dutyMan" handler="intocheckman" textField="intocheckman"
						checktype="user" single="true"></eoms:xbox>
				<input type='text' id='intocheckman' name="workername" readonly="true" value=""  
					   alt="allowBlank:true,vtext:'员工姓名不能为空！'"  class="text"/>
				<input type='hidden' id="dutyMan" name="workerid" value=""   />
	 		 </td>
 		</tr>
		
		<tr>
		<!-- 地市-->
      
 			<td class="label">地市</td>
			<td class="content" >
				<!-- 地市 -->			
				<select name="mainCity" id="city" class="select" alt="allowBlank:true,vtext:'请选择所在地市'" onchange="changeCity(0);">
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
			<td class="label">工单子类型</td>
			
			
			<td class="content">
				<eoms:comboBox name="mainFaultNet" id="mainFaultNet" defaultValue=""
					initDicId="1012201" alt="allowBlank:true" styleClass="select"/>
			</td>
		</tr>
			<!--时间 -->
	   <tr>
		  <td class="label">计划开始时间</td>
		  <td class="content">
		    <input type="text" class="text" name="sheetAcceptLimit" readonly="readonly" 
				id="sheetAcceptLimit" value="${startTime}" 
				onclick="popUpCalendar(this, this,null,null,null,null,-1)" alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true"/>  

		  </td>
		  <td class="label">计划结束时间</td>
		  <td class="content">
		    <input type="text" class="text" name="sheetCompleteLimit" readonly="readonly" 
				id="sheetCompleteLimit" value="${endTime}" 
				onclick="popUpCalendar(this, this,null,null,null,null,-1)" alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true"/>
		  </td>		  
		</tr>
</table>
		<!-- buttons -->
		<div class="form-btns">
		  	<html:submit styleClass="btn" property="method.save" styleId="method.save">
				查询
			</html:submit>
		 </div>	 
	</html:form>
</div>
<%@ include file="/common/footer_eoms.jsp"%>