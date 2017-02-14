<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>

<!--  <script type="text/javascript">
var roleTree;
var v;
  Ext.onReady(function()
  {
   v = new eoms.form.Validation({form:'theform'});
  }
   );


  function changeType1()
  {
		
  }
</script>-->



<div style="height:20"></div>

<html:form action="/pnrTransferOfficeMaleGuest.do?method=saveBatchReply" styleId="theform" >
<input type="hidden" name="result" value="${result}">
<input type="hidden" name="handleState" value="handle">
<table id="sheet" class="formTable" >
				
 		<tr>
 				<td class="label">
 					故障原因*
 				</td>
				<td class="content">
						<select name="faultCause"  id="faultCause">
						<option value="2043">主干电缆障碍-宽带</option>
						<option value="2042">交接分线设备障碍-宽带</option>
						<option value="2044">光缆故障-宽带</option>
						<option value="2048">电缆被盗-宽带</option>
						<option value="2046">线路割接影响-宽带</option>
						<option value="2041">配线电缆故障-宽带</option>
						<option value="1229">主干、配线铜线距离超长-宽带</option>
						<option value="507">分光器-宽带</option>
						<option value="509">光电缆-宽带</option>
						<option value="391">主干故障-固话</option>
						<option value="392">配线故障-固话</option>
						<option value="743">电缆被盗-固话</option>
						<option value="9">光电缆-固话</option>
						<option value="7">分光器-固话</option>
						<option value="2">非本区县传输局故障</option>
						<option value="3">非光电缆原因故障</option>
						<option value="87">其他</option>
						</select>
				</td>
		</tr>		
		<tr>
 			<td class="label">
				处理描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入处理描述，最多输入 2000 字符'"></textarea>
			</td>
		</tr>
		<tr>
		
		 <td class="label">
				故障处理人+手机号*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="dealPerformer2" 
					id="dealPerformer2" alt="allowBlank:false,maxLength:500,vtext:'请填入处理人'"></textarea>
	     </td>
		</tr>
</table>

	
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				回复
			</html:submit>&nbsp;&nbsp;
			<html:button property="" styleClass="btn" onclick="javascript:history.go(-1);">
				返回
			</html:button>
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>