<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/header_self.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<script language="javaScript" type="text/javascript" src="${app}/scripts/module/partner/ajax.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style type="text/css">
    .firstTr td{background-color:#f7f7f7;}

    .mainOrder{
    	color:blue;
    }
    
</style>

<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突

 Ext.onReady(function(){
  		// 初始的时候给区县默认值
		delAllOption("country");//地市选择更新后，重新刷新县区
			var region = "${city}";
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
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
					var country = "${country}";
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
		});//初始的时候给区县默认值结束
		
  });
   function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         }	
         
     }	
  
//解锁操作
function unlock(subProcessInstanceId){
	if(confirm('确定要解锁吗?'))
    {
    	//alert(subProcessInstanceId);
    	var mainProcessInstanceId = jq("#processInstanceId").val();
    	var propertyUrl="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=unlock&subProcessInstanceId="+subProcessInstanceId+"&mainProcessInstanceId="+mainProcessInstanceId;
		jq.ajax({
			type: "post",
	 		url: propertyUrl,
	 		async: false,//控制同步        
			contentType: "application/json; charset=utf-8",
	 		dataType: "json",
	 		cache: false,
	 		success: function (data) {
	 			if(data.result=='success'){
	 				alert("解锁成功");
	 				//jq("#method.save")click();
	 				refresh();
	 				reload.click();
	 			}   
	 		},
	 		error: function (err) {
	           alert(err);
	        }		
		});
		//refresh();
		//alert("2222");
        return true;
    }
    return false; 
}

//加入归集
function joinCollection(subProcessInstanceId){
	if(confirm('确定要加入归集吗?'))
    {
    	//alert(subProcessInstanceId);
    	//alert(subProcessInstanceId);
    	var processInstanceId=jq("#processInstanceId").val();
    	var propertyUrl="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=joinCollection&singleProId="+processInstanceId+"&noProId="+subProcessInstanceId;
		jq.ajax({
			type: "post",
	 		url: propertyUrl,
	 		async: false,//控制同步        
			contentType: "application/json; charset=utf-8",
	 		dataType: "json",
	 		cache: false,
	 		success: function (data) {
	 			if(data.result=='success'){
	 				alert("加入归集成功");
	 				refresh();
	 				reload.click();
	 			}else{
	 				alert("处理异常");
	 			}   
	 		},
	 		error: function (err) {
	           alert(err);
	        }		
		});
    }
  
}


//解锁成功或加入归集成功后 刷新整个页面的数据
function refresh(){
	var processInstanceId=jq("#processInstanceId").val();
	var siteCd=jq("#siteCd").val();
	var genusArea=jq("#genusArea").val();
	var stationName=jq("#stationName").val();
	var daiWeiCompany=jq("#daiWeiCompany").val();
	reload.href = "${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=querySingleImputationDetail&processInstanceId="+processInstanceId+"&siteCd="+siteCd+"&genusArea="+genusArea+"&stationName="+stationName+"&daiWeiCompany="+daiWeiCompany;
} 

//点击主工单工单号跳转到处理界面
function todo(processInstanceId,taskId,flag){
	//alert("processInstanceId="+processInstanceId);
	//alert("taskId="+taskId);
	//alert("flag="+flag);
	window.dialogArguments.jumpTodo(processInstanceId,taskId,flag);
	window.close(); //关闭本页面
}

//归集工单主工单可以修改工单主题
jq(function(){
	jq("#ptaskList tbody tr ").eq(0).find("td:eq(1)").click(function(){
			var fontStart = "<font class='mainOrder'>";
			var fontEnd = "</font>";
			var pare = jq(this).parent().find("td:eq(0) input:hidden");//流程号
			var td = jq(this); 
			var txt = td.text();
			var input = jq("<input type='text' style='width:100%;' value='" + txt + "'/>"); 
			td.html(input); 
			input.click(function() { return false; }); 
				//获取焦点 
			input.trigger("focus"); 
		
			//文本框失去焦点后提交内容，重新变为文本 <p></p>
			input.blur(function() {
				var newtxt = jq(this).val(); 
				if (newtxt != txt) {
					if(newtxt==''){
						alert("工单主题不可以为空!");
						td.html(fontStart+txt+fontEnd); 
						return;
					}else{
						//判断文本有没有修改
						jq.ajax({
							type:"POST",
							url:"${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=updateTheme",
							data:{"processInstanceId":pare.val(),"theme":newtxt},
							dataType:"json",
							success:function(data){
								if(data.result=="success"){
								   //alert(data.content);
								   td.html(fontStart+newtxt+fontEnd);
								   window.dialogArguments.updateTheme(pare.val(),newtxt);
								}else{
								   //alert(data.content);
								   td.html(fontStart+txt+fontEnd);
								}                           			
							},
							error: function (err) {
			           			alert("修改工单主题失败");
			           			td.html(fontStart+txt+fontEnd);
			       			}
						});
					}
				} 
			else 
			{ 
				td.html(fontStart+newtxt+fontEnd); 
			} 	
		});
	});
});

</script>

<%@ include file="/common/body.jsp"%>

<div><font color="blue">注意：主工单的工单主题可以点击修改！</font></div>

<display:table name="ptaskList" cellspacing="0" cellpadding="0"
	id="ptaskList" pagesize="${ppageSize}" class="listTable taskList"
	export="${export}" requestURI="pnrTransferOfficeMaleGuest.do"
	sort="list" size="ptotal" partialList="true">
    <display:column  sortable="true" headerClass="sortable" title="工单号">		
    	<c:if test="${ptaskList.maleGuestState eq '1'}">
			<a href="javascript:void(0);" onclick="todo(&quot;${ptaskList.processInstanceId}&quot;,&quot;${ptaskList.taskId}&quot;,'single')"><font class="mainOrder">${ptaskList.processInstanceId}</font></a>
   		</c:if>
   		<c:if test="${ptaskList.maleGuestState ne '1'}">
			${ptaskList.processInstanceId}
   		</c:if>
   		<input type="hidden" value="${ptaskList.processInstanceId}" />				
	</display:column>
	
	<display:column sortable="true"
			headerClass="sortable" title="工单主题"  >
		<c:if test="${ptaskList.maleGuestState eq '1'}">
			<font class="mainOrder">${ptaskList.theme}</font>
		</c:if>
		<c:if test="${ptaskList.maleGuestState ne '1'}">
			${ptaskList.theme}
  		</c:if>			
	</display:column>
			
       <display:column  sortable="true"
			headerClass="sortable" title="业务号码" >
		<c:if test="${ptaskList.maleGuestState eq '1'}">
			<font class="mainOrder">${ptaskList.barrierNumber}</font>
		</c:if>
		<c:if test="${ptaskList.maleGuestState ne '1'}">
			${ptaskList.barrierNumber}
  		</c:if>	
			</display:column>
	
		<display:column  sortable="true"
			headerClass="sortable" title="故障发生时间">
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder"><fmt:formatDate value="${ptaskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				<fmt:formatDate value="${ptaskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	  		</c:if>
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" >
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder"><fmt:formatDate value="${ptaskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				<fmt:formatDate value="${ptaskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
  			</c:if>	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="工单历时">
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder">${ptaskList.workTask}</font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				${ptaskList.workTask}
	  		</c:if>	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="处理时限(小时)">
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder">${ptaskList.processLimit}</font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				${ptaskList.processLimit}
	  		</c:if>
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="当前状态">
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder">${ptaskList.statusName}</font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				${ptaskList.statusName}
	  		</c:if>
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="联系人" >
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder">${ptaskList.connectPerson}</font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				${ptaskList.connectPerson}
	  		</c:if>
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="地址" maxLength="15">
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder">${ptaskList.installAddress}</font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				${ptaskList.installAddress}
	  		</c:if>
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="当前处理人" >
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder"><eoms:id2nameDB id="${ptaskList.executor}" beanId="tawSystemUserDao"/></font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				<eoms:id2nameDB id="${ptaskList.executor}" beanId="tawSystemUserDao"/>
	  		</c:if>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="所属区域">
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder"><eoms:id2nameDB id="${ptaskList.deptId}" beanId="tawSystemDeptDao"/></font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				<eoms:id2nameDB id="${ptaskList.deptId}" beanId="tawSystemDeptDao"/>
	  		</c:if>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="局站名称">
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder">${ptaskList.stationName}</font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				${ptaskList.stationName}
	  		</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司" >
			<c:if test="${ptaskList.maleGuestState eq '1'}">
				<font class="mainOrder"><eoms:id2nameDB id="${ptaskList.initiator}" beanId="tawSystemUserDao"/></font>
			</c:if>
			<c:if test="${ptaskList.maleGuestState ne '1'}">
				<eoms:id2nameDB id="${ptaskList.initiator}" beanId="tawSystemUserDao"/>
	  		</c:if>
		</display:column>	
	
	<display:column sortable="false" headerClass="sortable" title="归集" media="html">
		<c:if test="${ptaskList.maleGuestState eq '1'}">
			<font class="mainOrder">主工单</font>
		</c:if>	
		<c:if test="${ptaskList.maleGuestState eq '2'}">
			    <a href="javascript:void(0);"onclick="unlock('${ptaskList.processInstanceId}')">解锁</a>			
		</c:if>
		
	</display:column>		
	<display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>
</display:table>

<br />
<html:form action="/pnrTransferOfficeMaleGuest.do?method=querySingleImputationDetail"
				styleId="theform">
<a id="reload" href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=querySingleImputationDetail" style="display:none"></a>
<input type="hidden" id="processInstanceId" name="processInstanceId" class="text" value="${processInstanceId }" />				
<input type="hidden" id="siteCd" name="siteCd" class="text" value="${siteCd }" />				
				
工单号&nbsp;<input type="text" name="maleGuestNumber" class="text" id="maleGuestNumber" value="${selectAttribute.maleGuestNumber}" />
工单主题&nbsp;<input type="text" name="wsTitle" class="text" id="wsTitle" value="${selectAttribute.theme}" />
区县&nbsp;<td class="content" style="width:20%">
				<select name="country" id="country" class="select"
					alt="allowBlank:false,vtext:'请选择所在县区'">
					<option value="">
					--请选择所在县区--
					</option>				
				</select>
			</td>

<html:submit styleClass="btn" property="method.save" styleId="method.save">查询</html:submit>

<c:if test="${queryFlag=='no'}">
<font color="red">查询条件不能为空，请填写查询条件，查询未归集工单数据！</font>
</c:if>

</html:form>

<!-- 下面的未归集工单查询列表 -->
<display:table name="taskList" cellspacing="0" cellpadding="0"
	id="taskList" pagesize="${pageSize}" class="listTable taskList"
	export="${export}" requestURI="pnrTransferOfficeMaleGuest.do"
	sort="list" size="total" partialList="true">
    <display:column  sortable="true" headerClass="sortable" title="工单号">		
			${taskList.processInstanceId}
	</display:column>
	
	<display:column sortable="true"
			headerClass="sortable" title="工单主题"  >
				${taskList.theme}
			</display:column>
			
       <display:column  sortable="true"
			headerClass="sortable" title="业务号码" >
			${taskList.barrierNumber}
			</display:column>
	
		<display:column  sortable="true"
			headerClass="sortable" title="故障发生时间">
				<fmt:formatDate value="${taskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>				
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" >
				<fmt:formatDate value="${taskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="工单历时">
				${taskList.workTask}
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="处理时限(小时)">
				${taskList.processLimit}
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="当前状态">
			${taskList.statusName}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="联系人" >
		${taskList.connectPerson}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="地址" maxLength="15">
			${taskList.installAddress}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="当前处理人" >
			<eoms:id2nameDB id="${taskList.executor}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="所属区域">
			<eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司" >
			<eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/>
		</display:column>	
	
	<display:column sortable="false" headerClass="sortable" title="加入归集" media="html">
				<a href="javascript:void(0);" onclick="return joinCollection(&quot;${taskList.processInstanceId}&quot;)">加入归集</a>
		</display:column>		
	<display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>
</display:table>

<%@ include file="/common/footer_eoms.jsp"%>