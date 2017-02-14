<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<% request.setAttribute("province", "10"); %>
<script type="text/javascript">



 
  function sureFeeApplicationMoney(obj){
    	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var protocolPrice=$('feeApplicationMoney').value; //获得文本框输入的金额
        var protocolPriceDiv=$('feeApplicationMoneyDiv');	//获得显示信息的div
   		if(protocolPrice.match(price) && ""!=protocolPrice){
   			protocolPriceDiv.innerHTML="格式正确";
   			return true;
      	}else{
           	protocolPriceDiv.innerHTML="输入不合法,请输入正确的金额 例：23,23.00";
           	return false;
      	}
  }
 


	Ext.onReady(function(){
	 var v= new eoms.form.Validation({form:'feeApplicationMainForm'});
	
	 v.custom = function(){ 
	            	if(sureFeeApplicationMoney()) {
	            		return true;
	            	} else {
	            		alert("费用金额格式输入错误，请检查!");
	            		$('feeApplicationMoney').focus();
	            		return false;
	            	}
	            };
		var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';

	    new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
		treeRootId:'${province}',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
		showChkFldId:'feeApplicationArea',saveChkFldId:'feeApplicationCity',returnJSON:false
	});		
	
	
	
	

		function moneyValidate(){
	            var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
                var protocolPrice=myjs('#feeApplicationMoney').val(); 
				if(protocolPrice.match(price))
				{	
						return true;
					} else {
						return false;
					}
				};
   v1 = new eoms.form.Validation({form:'importForm'});
   });




function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}


 function feeAppliSubmit(){
			if(confirm("确定提交申请？")){
			document.getElementById("feeApplicationStatus").value='2'
			document.getElementById("feeApplicationMainForm").submit();
			}else{
			return  false;
			}
}

function feeAppliSubmitAAA(){
			if(confirm("确定提交申请？")){
			document.getElementById("feeApplicationStatus").value='2'
			}else{
			return  false;
			}
}
function openImport(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
}

function saveImport() {
	//表单验证
	if(!v1.check()) {
		return;
	}
	
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
	url : "${app}/deviceManagement/chargeFeeAppli/charge.do?method=importRecord",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
		},
		failure : function(form, action) {
			alert(action.result.infor);
		}
    });
	
}
</script>
 
 <div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="charge.do?method=importRecord" method="post" enctype="multipart/form-data" id="importForm" name="importForm">
			<fieldset>
				<legend>从Excel导入</legend>
				<table id="sheet" class="formTable">
					<tr>
						<td class="label">选择Excel文件</td>
						<td width="35%">
							<input type="file" name="importFile" class="file"  alt="allowBlank:false"/>
						</td>
						<td width="35%">
							<input type="button" onclick="saveImport()" value="确定"/>
						</td>
					</tr>
				</table>
			</fieldset>
	</form>
</div>

<br/>
 
 
     
<form action="charge.do?method=add" method="post" id="feeApplicationMainForm" name="feeApplicationMainForm" >
	<table id="sheet" class="formTable">
	
		<tr>
		<caption>
		<div class="header center">线路代维费用信息</div>
	</caption>	
			
		</tr>
		
			
		<tr>
			<td class="label">
			 费用类型&nbsp;* 
			</td>
			<td class="content">
				<input class="text" type="text" name="feeApplicationType"
					id="feeApplicationType" alt="allowBlank:false" />
			</td>
			<td class="label">
				费用金额（RMB）&nbsp;*
			</td>
				
			<td class="content" >
				<input class="text" type="text" name="feeApplicationMoney"
				 id="feeApplicationMoney" alt="allowBlank:false" onblur="javascript:sureFeeApplicationMoney(this);"></input>
				 <div id="feeApplicationMoneyDiv" class="ui-state-highlight ui-corner-all" style="width:150px">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				<div>例：23,23.00 (默认单位为：RMB)</div>
				</div>
			</td>
			
		</tr>
		
	<tr>	
	
	<td class="label">
			地市&nbsp;*
		</td>
		 <td colspan="3">
			

            <input class="text" type="text" name="feeApplicationArea" id="feeApplicationArea" readonly="true" 
					    value="${feeApplicationMainForm.feeApplicationArea}"
					 alt="allowBlank:false" />
		<input type="button" name="areatree" id="areatree" value="选择地域" class="btn"/>
					 
		<input type="hidden" name="feeApplicationCity" id="feeApplicationCity"/> 			 
		<input type="hidden" name="areaId" id="areaId"/> 	
		<input type="hidden" name="areaType" id="areaType"/> 
  		</td>
	
	
	</tr>
	
	
	
		
		<tr>
			<td class="label">
			 费用描述
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="feeApplicationDiscribe"
					id="feeApplicationDiscribe" alt="width:500,allowBlank:true"></textarea>
			
			</td>
				</tr>
			<tr>
			<td class="label">
				备注
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="feeApplicationRemark"
					id="feeApplicationRemark" alt="allowBlank:true"></textarea>
			</td>
		</tr>
		
		<tr>
			<td class="label">
				附件
				<br>
			</td>
			<br>
			<td colspan="3">
				<eoms:attachment scope="request" idField="feeApplicationAccessory"
					name="feeApplicationAccessory" appCode="charge" alt="allowBlank:true" />
			</td>
		</tr>
		
		
		<input type="hidden" name="feeApplicationStatus"  id="feeApplicationStatus" />
		
		<input type="hidden"  name="Type" value="1" />
		</table>




	  <html:submit styleClass="btn"  property="method.save"  
	        styleId="method.save" value="提交申请"  ></html:submit>	  
			  	
	  <html:submit styleClass="btn"  property="method.save"  onclick="{feeApplicationStatus.value='1'}"
	        styleId="method.save" value="保存至草稿"  ></html:submit>
</form>

<input type="button"  id="feeAppliSub"  style="margin-right: 5px" onclick="return feeAppliSubmit()"
		value="提交TTT"   class="btn"/>

<%@ include file="/common/footer_eoms.jsp"%>