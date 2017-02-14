<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<% request.setAttribute("province", "10"); %>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

    var myjs=jQuery.noConflict();
    
    myjs(function(){

 myjs('input#feeApplicationMoney').bind('change',sureFeeApplicationMoney);
 
 
  function sureFeeApplicationMoney(event){
    	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        
        var protocolPrice=myjs('#feeApplicationMoney').val(); //获得文本框输入的金额
        var protocolPriceDiv=myjs('#feeApplicationMoneyDiv');	//获得显示信息的div
   		if(protocolPrice.match(price) && ""!=protocolPrice){
   			protocolPriceDiv.removeClass("ui-state-error");//移除样式
   			protocolPriceDiv.addClass("ui-state-highlight");      
      		protocolPriceDiv.children("div").html("输入正确");
      		protocolPriceDiv.children("span").attr("class","ui-icon ui-icon-info");
      	}else{
      		protocolPriceDiv.removeClass("ui-state-highlight");     
        	protocolPriceDiv.addClass("ui-state-error");    //添加样式    
           	protocolPriceDiv.children("div").html("输入不合法,请输入正确的金额 例：23,23.00");
           	protocolPriceDiv.children("span").attr("class","ui-icon ui-icon-alert");
      	}
  }
 
  });
 
 
    
    
	Ext.onReady(function(){
	            v = new eoms.form.Validation({form:'feeApplicationMainForm'});
	            v.custom = function(){ 
	            	return true;
	            };
   });
   
   Ext.onReady(function(){
	var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';

   
	new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
		treeRootId:'${province}',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',
		showChkFldId:'feeApplicationArea',saveChkFldId:'feeApplicationCity',returnJSON:false
	});
			
})
function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}
   
   
   
	function returnBack(){
		window.history.back();
	}
	
	 function feeAppliSubmit(){
			if(confirm("确定提交申请？")){
			document.getElementById("feeApplicationStatus").value='2'
			
			}else{
			return  false;
			}
}


 function deleteFeeAppli(){
			if(confirm("确定删除草稿？")){
			
			}else{
			return  false;
			}
}



function openSearch(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "查看处理详情";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭处理详情";
	}
}
</script>
	

 
<form action="charge.do?method=edit" method="post"
	id="feeApplicationMainForm" name="feeApplicationMainForm">
	
	<!-- hidden area start -->
		<input type="hidden"  name="Type" value="1" />
		<input type="hidden" name="id" value="${feeApplicationMain.id}" />
		<input type="hidden" name="deleted" value="${feeApplicationMain.deleted}" />
		<input type="hidden" name="deleteTime" value="${feeApplicationMain.deleteTime}" />
		<input type="hidden" name="feeApplicationStatus" id="feeApplicationStatus" value="${feeApplicationMain.feeApplicationStatus}"/>
		<input type="hidden" name="feeApplicationUser" value="${feeApplicationMain.feeApplicationUser}" />
		<input type="hidden" name="feeApplicationCall" value="${feeApplicationMain.feeApplicationCall}" />
	    <input type="hidden" name="feeApplicationDept" value="${feeApplicationMain.feeApplicationDept}" />
	    <input type="hidden" name="feeApplicationGreatTime" value="${feeApplicationMain.feeApplicationGreatTime}" />
		<input type="hidden" name="feeApplicationCompanyName" value="${feeApplicationMain.feeApplicationCompanyName}" />
		<input type="hidden" name="feeApplicationRoleID" value="${feeApplicationMain.feeApplicationRoleID}" />
		
	<!-- hidden area end -->
	
		<table id="sheet" class="formTable">
	
		
		<tr>
		<caption>
		<div class="header center">线路代维费用申请信息</div>
	</caption>	
			
		</tr>
		
		
	
		<tr>
			<td class="label">
			 费用类型* 
			</td>
			<td class="content">
				<input class="text" type="text" name="feeApplicationType"
					id="feeApplicationType" value="${feeApplicationMain.feeApplicationType}"  alt="allowBlank:false" />
			</td>
				<td class="label">
				费用金额*
			</td>
				
			<td class="content" >
				<input class="text" type="text" name="feeApplicationMoney"
				 id="feeApplicationMoney"  value="${feeApplicationMain.feeApplicationMoney}"  alt="allowBlank:false"/>
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
					    value="<eoms:id2nameDB beanId="tawSystemAreaDao" id="${feeApplicationMain.feeApplicationCity}"/>"
					 alt="allowBlank:false" />	 			 
		<input type="button" name="areatree" id="areatree" value="选择地域" class="btn"/>				 
		<input type="hidden" name="feeApplicationCity" id="feeApplicationCity" value="${feeApplicationMain.feeApplicationCity}"/> 			 
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
					id="feeApplicationDiscribe"  alt="allowBlank:false">${feeApplicationMain.feeApplicationDiscribe}</textarea>
			</td>
		</tr>
		 <tr>
			<td class="label">
				备注
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="feeApplicationRemark"
					id="feeApplicationRemark"  alt="allowBlank:true">${feeApplicationMain.feeApplicationRemark}</textarea>
			</td>
		</tr>	
        <tr>
			<td class="label">
				附件
			</td>
			
			<td colspan="3" id="uploadTd">
			
			<eoms:attachment name="feeApplicationMain"
							property="feeApplicationAccessory" scope="request"
							 idField="feeApplicationAccessory" appCode="charge"
							alt="allowBlank:true"/>
	
			</td>
		</tr>		
		</table>
       
   <html:submit styleClass="btn" property="method.save" 
			styleId="method.save" value="保存草稿" ></html:submit>
			
   <html:submit styleClass="btn" property="method.save"  onclick="return feeAppliSubmit()"
			styleId="method.save" value="提交申请" ></html:submit>		
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
	<input type="button"  onclick="returnBack()"
		value="返回"  class="btn"/>
 

</form>
<br></br>
<form action="charge.do?method=delete" method="post">
	<input type="hidden" name="id" value="${feeApplicationMain.id}" />
<html:submit styleClass="btn"  property="method.save" 
	        styleId="method.save" value="删除草稿"  onclick="return deleteFeeAppli()" ></html:submit>
</form>		




<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openSearch(this);">处理详情</span>
</div>	
		
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">	
				
		
		<logic:present name="feeApplicationLinkList" scope="request">
	<display:table name="feeApplicationLinkList" cellspacing="0" cellpadding="0"
		id="feeApplicationLinkList" 
		class="table feeApplicationMainList" export="true" sort="list"
		 >
		
	
		
		
		<display:column headerClass="sortable" title="操作人">
			${feeApplicationLinkList.operateUser}
		</display:column>
		<display:column  headerClass="sortable" title="时间">
			${feeApplicationLinkList.operateTime}
		</display:column>
		<display:column  headerClass="sortable" title="部门">
			${feeApplicationLinkList.operateDept}
		</display:column>
		<display:column  headerClass="sortable" title="结果">
				<c:if test="${1==(feeApplicationLinkList.operateResult)}" >审批通过</c:if>	
			<c:if test="${-1==(feeApplicationLinkList.operateResult)}" >审批不通过</c:if>
			
		</display:column>
		<display:column  headerClass="sortable" title="意见">
			${feeApplicationLinkList.operateOpinion}
		</display:column>
		
		<display:column  headerClass="sortable" title="附件">
		<c:if test="${empty feeApplicationLinkList.operateAccessory}">
					没有附件!
				</c:if>
				<c:if test="${not empty feeApplicationLinkList.operateAccessory}">
					<eoms:download ids="${feeApplicationLinkList.operateAccessory}"></eoms:download>
				</c:if>
		</display:column>
	
	</display:table>
</logic:present>
	
</div>		







<%@ include file="/common/footer_eoms.jsp"%>