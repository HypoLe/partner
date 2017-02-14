<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript"> 
  var checkflag = "false";
var myjs=jQuery.noConflict();
 function dele(myValue){
    myjs( "#dialog:ui-dialog" ).dialog( "destroy" );
		myjs( "#dialog-confirm" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			buttons: {
				"确定": function() {
					myjs( this ).dialog( "close" );					
					myjs.ajax({
					  type:"POST",
					  url:"FaultSheetManagement.do?method=delete&id="+myValue,
					  
					 success:deleteResult					 	  
					 });	
				},
				"退出": function() {
					myjs( this ).dialog( "close" );
				}
			}
		
		});
   
   };
	function deleteResult() {
	  
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		myjs( "#dialog:ui-dialog" ).dialog( "destroy" );
	
		myjs( "#dialog-message" ).dialog({
			modal: true,
			buttons: {
				Ok: function() {
				    window.location.reload();
					myjs( this).dialog( "close" );
					
				}
			}
		});
	};
	function addBaseStation(){
	window.location.href="${app}/baseinfo/baseStation.do?method=goToAdd"
	}
	
	function delSelBaseStation(){
var string="";
 var objName= document.getElementsByName("checkbox11");
        for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string+=objName[i].value;   
                string+="|";
                }
        }  
        if(confirm("确认要删除吗？")){
        	if(string == null || "" ==  string){
        		alert("请选择要删除的基站信息");
        		return false;
        	}
		 	location.href="${app}/baseinfo/baseStation.do?method=deleteAll&&sel="+string;
		 }else{
		 return false;
		 }
}

	function chooseAll(){	
	   var objs = document.getElementsByName("checkbox11");    
	    if(checkflag=="false"){
	        for(var i=0; i<objs.length; i++){
	           objs[i].checked="checked";
	        } 
	        checkflag="checked";
	    }
	    else if(checkflag=="checked"){ 	    	    
		    for(var i=0; i<objs.length; i++){
		           objs[i].checked=false;
		    } 
		    checkflag="false";
	    }
	}
	function openImport(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}
</script>
<!-- ---
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<content tag="heading">
	<c:out value="请输入查询条件" />
	</content> 
<form action="${app}/baseinfo/baseStation.do?method=list" method="post">
		<input type="hidden" name="">
	<table class="formTable">
	<caption>
		<div class="header center">基站信息查询</div>
	</caption>
		<tr>
				<td class="label">基站名</td>
		<td class="content">
		<input type="text" class="text medium" id="baseStationNameStringLike" name="baseStationNameStringLike" value=""/>
		</td>
		<td class="label">载波数量
		</td>
		<td class="content">
				<input type="text" name="carrierNumStringEqual" id="carrierNumStringEqual" value=""/>
		<input type="hidden" name="" id="" value=""/></td>
		
		<tr>
		<tr>
				<td class="label">维护级别</td>
		<td class="content">
		<eoms:comboBox name="maintenanceLevelStringLike" id="maintenanceLevelStringLike" initDicId="1121803" 
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		<%--
		<input type="text" class="text medium" id="maintenanceLevelStringLike" name="maintenanceLevelStringLike" value=""/>
		----%></td>
		<td class="label">站型
		</td>
		<td class="content">
							<eoms:comboBox name="stationFormStringEqual" id="stationFormStringEqual" initDicId="1121801" defaultValue="${baseStation.stationForm }"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td></td>
		<tr>
		
		<td colspan="4" align="center">
				<input type="submit" value="查询"/>
					</td></tr>
		</table> 
	</form>
</div>-->
<%int i=1; %>
<logic:present name="baseStationList" scope="request">
	<display:table name="baseStationList" cellspacing="0" cellpadding="0"
		id="baseStationList" pagesize="${pagesize}"
		class="table baseStationList" export="true"
		requestURI="${app}/baseinfo/baseStation.do" sort="list" partialList="true"
		size="${size}">
		<display:column title="选择 " href="" paramId="id" paramProperty="id"> 
			<input type="radio" name="ids" id="${baseStationList.id }" value="${baseStationList.id }" 
			onclick="getContract(this.value)" /> 
			<input type="hidden" name="${baseStationList.id }_no" id="${baseStationList.id }_no" value="${baseStationList.id}"/> 
			<input type="hidden"  id="${baseStationList.id }_name" value="${baseStationList.baseStationName}"/> 
		</display:column> 
		<display:column property="baseStationName" sortable="true"
			headerClass="sortable" title="基站名"
			 paramId="id"
			paramProperty="id" />
		<display:column sortable="true" headerClass="sortable" title="站型">
				<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${baseStationList.stationForm}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="维护级别">
		<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${baseStationList.maintenanceLevel}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="机房类型">
				<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${baseStationList.stationHouseType}"/>
           </display:column>
		<display:column sortable="true" headerClass="sortable" title="设备厂家">
				${baseStationList.manufacturer}
           </display:column>
		<display:column sortable="true" headerClass="sortable" title="载波设备">
				${baseStationList.carrierFacility}
           </display:column>
		<display:column sortable="true" headerClass="sortable" title="载波数量">
				${baseStationList.carrierNum}
           </display:column>
		<display:column sortable="true" headerClass="sortable" title="详情">
		<c:if test="${type =='check'}">
			<a href="${app}/baseinfo/baseStation.do?method=goToDetail&id=${baseStationList.id }" target="blank"><img src="${app }/images/icons/table.gif">
		</c:if>
		<c:if test="${type !='check'}">
			<a href="${app}/baseinfo/baseStation.do?method=goToDetail&id=${baseStationList.id }"><img src="${app }/images/icons/table.gif">
		</c:if>
			</a>
           </display:column>
	</display:table>
</logic:present>
<script>
    function getContract(contractId){
   		 if(confirm('你确定关联该基站?')){
	    	 var contractNO = document.getElementById(contractId+"_no").value;
	    	 var baseStationName = document.getElementById(contractId+"_name").value;
	         //window.opener.document.all.compactNo.value=window.opener.document.all.compactNo.value+'<br>'+contractNO;
	          window.opener.setCompactNostr(contractNO,baseStationName);
	         self.close(); 
         }
    }
</script>
<%@ include file="/common/footer_eoms.jsp"%>
