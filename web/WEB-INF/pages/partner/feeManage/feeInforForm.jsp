<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List,com.boco.eoms.evaluation.model.*"%>

<link rel="stylesheet" href="${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/demos.css">
<link rel="stylesheet" type="text/css" href="${app}/nop3/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>


<script type="text/javascript">

 var items=[];
 items.push({	text : "按月计次服务费用",
			 	url : "${app}/partner/feeManage/feeCountManage.do?method=feeCountPrcTmplListByMajor4entity&majorId=${feePoolMain.special}&id=${feePoolMain.id}" ,
				isIframe : true 
		       },
		       {	text : "月度考核费用",
			 	url : "${app}/partner/feeManage/evaluFee.do?method=initateFeeEntity&specialty=${feePoolMain.special}&mainentityid=${feePoolMain.id}" ,
				isIframe : true 
		       },
		       {	text : "月度其他费用",
			 	url : "${app}/partner/feeManage/monthOtherPay.do?method=goToMonthOtherPay2&id=${feePoolMain.id}" ,
				isIframe : true 
		       },
		       {	text : "操作记录",
			 	url : "${app}/partner/feeManage/feeManage.do?method=goToApprovePage&id=${feePoolMain.id}" ,
				isIframe : true 
		       }
		       
		       );	
  </script>
  <script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function() {
	setTabs();
});

	function setTabs() {
		var tabConfig = {
		          items : items
	         };
	    var tabs = new eoms.TabPanel('info-page', tabConfig);		
	}  	

    function fullConfim(ths){
     Ext.Ajax.request({
         url: '${app}/partner/feeManage/feeManage.do?method=oneMajorConfirmCheck', 
         params:{ 
       	  			mainentityid:'${feePoolMain.id}'  				
       			}, 
       	 success:function(response,options){          				  
       			 var resulttext=response.responseText;
       			 var rslt=Ext.decode(resulttext);   
       					  
       			 if(rslt.have==false){       					     					     
       				alert(rslt.msg);   
       				return false;
       			 }     						         					 
       			 else{     					    
           				var aform=document.createElement("form");
                        aform.action="${app}/partner/feeManage/feeManage.do?method=oneMajorConfirm&mainentityid=${feePoolMain.id}";
       					aform.method="post"; 
       					document.body.appendChild(aform);
       					aform.submit();	
       			 }	
      		}
      });
       
    }
    
    function returnBack(){
	 // window.history.back();	
	 /*
	  var parentUrls=[
	   '${app}/partner/feeManage/feeManage.do?method=goToRoleList',
	   '${app}/partner/feeManage/feeManage.do?method=goToSpecialtyList&pageType=${pageType}&id=${parentMainId}',
	   '${app}/partner/feeManage/feeManage.do?method=goToSpecialtyList&pageType=${pageType}&id=${parentMainId}'
	  ];
	  var idxInParentUrls=Number(${idxInParentUrls});
	  var url=parentUrls[idxInParentUrls];*/
	  
	  var url;
	  if('${pageType}'!=''&&'${parentMainId}'!=''){
	     url='${app}/partner/feeManage/feeManage.do?method=goToSpecialtyList&pageType=${pageType}&id=${parentMainId}';
	  }else{
	     url='${app}/partner/feeManage/feeManage.do?method=goToRoleList';
	  }
	  
	  jq('form#aFrom').attr('action',url);
	  document.getElementById("aFrom").submit(); 
	}
	
</script>
<body  width="100%" height="100%">

 <!-- 设置隐藏input,供生成实例时 被 js获得其值 -->
 <input type="hidden" id="year" name="year" value="${feePoolMain.feeYear}"/>
 <input type="hidden" id="month" name="month" value="${feePoolMain.feeMonth}"/>
 <input type="hidden" id="comp" name="comp" value="${feePoolMain.company}"/>
 <input type="hidden" id="area" name="area" value="${feePoolMain.region}"/>
 <input type="hidden" id="mainentityid" name="mainentityid" value="${feePoolMain.id}"/>

<table id="sheet" class="formTable">

        <tr>
		<td class="label">时间</td>
			<td>
             ${feePoolMain.feeYear}年${feePoolMain.feeMonth}月
            
			</td>

		<td class="label">代维公司</td>
			<td>
			<eoms:id2nameDB id="${feePoolMain.company}" beanId="partnerDeptDao" />
			</td>
		</tr>

		<tr>
		<td class="label">所属地市</td>
			<td>
			
			<eoms:id2nameDB beanId="tawSystemAreaDao" id="${feePoolMain.region}"/>
			</td>

		<td class="label">专业</td>
			<td> 
			<eoms:id2nameDB id="${feePoolMain.special}"  beanId="ItawSystemDictTypeDao" />				
			</td>
		</tr>
		</table>	
		
<c:if test="${not (empty feePoolMain.status or feePoolMain.status=='' or feePoolMain.status=='0' or feePoolMain.status=='1' )}">
  <br />
  <!-- 
  <div class="ui-widget-header" style="height:30px;font-size:30px;color:#083772;">	
     已经整体确认		
  </div>  -->
  <span class="ui-widget-header" style="height:20px;width:100%;line-height:150%;font-size:12px;color:#083772;">已经整体确认</span>	
</c:if>	
	
<div id="info-page" style="width:100%;overflow-x:hidden;overflow-y:hidden;">
</div>	
	
		
<table>
  <c:if test="${empty feePoolMain.status or feePoolMain.status=='' or feePoolMain.status=='0' or feePoolMain.status=='1'}">
   <input type="button" class="btn" value="整体确认" onClick="javascript:fullConfim(this);"/> &nbsp;&nbsp;&nbsp;
  </c:if>	
  <input type="button" class="btn" onClick="returnBack();" value="返回"  /><br/><br/>  
</table>  

</body>

<form method="post" id="aFrom" name="aFrom">
</form>
<%@ include file="/common/footer_eoms.jsp"%>
