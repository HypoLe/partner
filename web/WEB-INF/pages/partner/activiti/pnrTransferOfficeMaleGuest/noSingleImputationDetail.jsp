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
//点击加入归集按钮的函数
function addColForButton(noProId){
	if(confirm('确定要加入归集吗?'))
    {
		var singleProId=jq("#singleProId").val();
		if(singleProId==null||singleProId==""){
			alert("工单号不能为空！");
			return;
		}
		var v_country=jq("#v_country").val();
		
		//判断一下填写的工单号是否存在，有可能填错
		
		var sign=false;
		var signStr="";
		var propertyUrl="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=existsSingleWorkOrderByProId&processInstanceId="+singleProId+"&country="+v_country;
		jq.ajax({
			type: "post",
	 		url: propertyUrl,
	 		async: false,//控制同步        
			contentType: "application/json; charset=utf-8",
	 		dataType: "json",
	 		cache: false,
	 		success: function (data) {
	 			if(data.result=='yes'){
	 				sign=true;
	 				signStr="归集工单号存在";
	 			}else{
	 				signStr="归集工单号不存在或不在同一个区县或已不在故障处理环节，请确认后，重新填写！";
	 			} 
	 		},
	 		error: function (err) {
	          	//alert(err);
	          	signStr="获取该归集工单信息异常!";
	        }		
		});
		
		if(sign==false){
			alert(signStr);
			return;
		}
	
		//alert("到这里了吗？");
		addedToCollection(singleProId,noProId);
	}
}

//点击加入归集链接的函数
function addColForHref(singleProId){
	if(confirm('确定要加入归集吗?'))
    {
		var noProId=jq("#processInstanceId").val();
		addedToCollection(singleProId,noProId);
	}
}


//加入归集函数
function addedToCollection(singleProId,noProId){
    	var propertyUrl="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=joinCollection&singleProId="+singleProId+"&noProId="+noProId;
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
	 				window.dialogArguments.refresh(); //调用父窗体中定义的刷新方法从而刷新父窗体
	                window.close(); //关闭本页面
	 			}else{
	 				alert("处理异常");
	 			}  
	 		},
	 		error: function (err) {
	           alert(err);
	        }		
		});
}
</script>

<%@ include file="/common/body.jsp"%>

<display:table name="ptaskList" cellspacing="0" cellpadding="0"
	id="ptaskList" pagesize="10" class="listTable taskList"
	export="${export}" requestURI="pnrTransferOfficeMaleGuest.do"
	sort="list" size="ptotal" partialList="true">
  	<display:column  sortable="true" headerClass="sortable" title="工单号">		
			${ptaskList.processInstanceId}		 
	</display:column>
	
	<display:column sortable="true"
			headerClass="sortable" title="工单主题"  >
				${ptaskList.theme}
			</display:column>
			
       <display:column  sortable="true"
			headerClass="sortable" title="业务号码" >
			${ptaskList.barrierNumber}
			</display:column>
	
		<display:column  sortable="true"
			headerClass="sortable" title="故障发生时间">
				<fmt:formatDate value="${ptaskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>				
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" >
				<fmt:formatDate value="${ptaskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="工单历时">
				${ptaskList.workTask}
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="处理时限(小时)">
				${ptaskList.processLimit}
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="当前状态">
			${ptaskList.statusName}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="联系人" >
		${ptaskList.connectPerson}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="地址" maxLength="15">
			${ptaskList.installAddress}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="当前处理人" >
			<eoms:id2nameDB id="${ptaskList.executor}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="所属区域">
			<eoms:id2nameDB id="${ptaskList.deptId}" beanId="tawSystemDeptDao"/>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="局站名称">
			${ptaskList.stationName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司" >
			<eoms:id2nameDB id="${ptaskList.initiator}" beanId="tawSystemUserDao"/>
		</display:column>	
	
	    <display:column sortable="false" headerClass="sortable" title="加入归集" media="html">	
	            <input type="text" id="singleProId" name="singleProId" value="" /><div>(此处可填写工单号)</div>
	    			
				<input type="button" value="加入归集" onclick="addColForButton('${ptaskList.processInstanceId}')"/>
		</display:column>		
	<display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>
</display:table>

<br />
<html:form action="/pnrTransferOfficeMaleGuest.do?method=addNoSingleImputationDetail"
				styleId="theform">
<a id="reload" href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=querySingleImputationDetail" style="display:none"></a>
<input type="hidden" id="processInstanceId" name="processInstanceId" class="text" value="${processInstanceId }" />				
<input type="hidden" id="siteCd" name="siteCd" class="text" value="${siteCd }" />				
<input type="hidden" id="v_country" name=""v_country"" class="text" value="${country}" />				
				
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
<font color="red">查询条件不能为空，请填写查询条件，查询已归集工单数据！</font>
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
	
	<display:column sortable="false" headerClass="sortable" title="归集" media="html">
				<a href="javascript:void(0);" onclick="return addColForHref(&quot;${taskList.processInstanceId}&quot;)">加入归集</a>
		</display:column>		
	<display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>
</display:table>

<%@ include file="/common/footer_eoms.jsp"%>