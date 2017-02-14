<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>

<script type="text/javascript">
var roleTree;
var v;
  Ext.onReady(function()
  {
   v = new eoms.form.Validation({form:'theform'});
  }
   );


  function changeType1()
 {		var checkState = document.getElementById("checkState");
 		var mainRemark = document.getElementById("mainRemark").value;
 
        var strsel = checkState.options[checkState.selectedIndex].text;
        mainRemark = mainRemark.trim();
        if(strsel=="请选择"){
            alert("请选择审核状态");
        	return false;
        }        
        if(strsel=="不同意" &&(''==mainRemark||null==mainRemark)){
        	alert("请填写专家意见");
        	return false;
        }
	return true;
 }
  function selectRes(){
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferPrecheck.do?method=openRejectView&marker=provinceManageCheck&taskId=${taskId}&processInstanceId=${processInstanceId}&returnPerson=${pnrTransferOffice.provinceLine}&themeId=${pnrTransferOffice.id}&theme=${pnrTransferOffice.theme}';
        //window.open(url);
		var _sHeight = 180;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		var answer = window.showModalDialog(url,window,sFeatures);
		if(answer){
		window.location="${app}/activiti/pnrTransferPrecheck/pnrTransferPrecheck.do?method=listBacklog";
		}
	}
</script>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferPrecheck/transfer_basis.jsp"%>

<div style="height:20"></div>

<html:form action="/pnrTransferPrecheck.do?method=expertTeamTest" styleId="theform" >
	
            <input type="hidden" name="processInstanceId" value="${processInstanceId}">            
            <input type="hidden" name="title" value="${pnrTransferOffice.theme}">
            <input type="hidden" name="titleId" value="${pnrTransferOffice.id}">
			<input type="hidden" name="linkName" value="expertTeamTest">
<table id="sheet" class="formTable" >
		<tr>
			
		  
		  <td class="label">审核状态*</td>
		  <td class="content" colspan="3">		    
		     <eoms:comboBox name="checkState" id="checkState"
					defaultValue="" initDicId="1012311"
					alt="allowBlank:false" styleClass="select" />
		  </td>	 			
		</tr>
			
		<tr>
 			<td class="label">
				专家意见*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入专家意见，最多输入 2000 字符'"></textarea>
			</td>
		</tr>
</table>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="v.passing=true;javascript:return changeType1();" styleId="method.save">
				提交
			</html:submit>&nbsp;&nbsp;
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>