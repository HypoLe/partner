<%@ page language="java"
	import="com.boco.activiti.partner.process.model.*,java.util.*"
	errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
    var v;
    Ext.onReady(function () {
    	
        v = new eoms.form.Validation({form: 'theform'});
        eoms.ComboBox.doCombo(document.getElementById("1010111"),'mainFaultNet')
        document.getElementById("seleId").disabled="disabled";  
        document.getElementById("seleId").onclick= changeDiv;
    });
</script>
<script type="text/javascript">
	function showSubmit(){
	
	  var test  = document.getElementById("sendObject-chooser-show");
	  var flag = document.getElementById("flagState").value;
	  var form = document.getElementById("theform");
	  if(flag=="0"){
	  	if(test.firstChild.innerHTML == "未选择" || test.firstChild.innerHTML=="${userName}"){
	  	var showError = document.getElementById("showError");
	  		showError.innerHTML = "<font color=red>请选择工单授权人</font>";
	  	}else{
	  		form.submit();
	  	}
	  }else{
			form.submit();	  
	  }
	}
	
	function stateChange(){
		var flag = document.getElementById("flagState").value;
		var state = document.getElementById("faultSource").value;
		//确定按钮
		var but = document.getElementById("but");
		//授权人
		var seleId = document.getElementById("seleId");
			if(state!=""){
				but.disabled="";
				seleId.disabled="";
			if(flag=="0"){
				if(state=="101011101"){
				but.disabled="disabled";
				seleId.disabled="disabled";
				if(window.attachEvent){//如果使用IE浏览器，相应的改变默认值
					hiddenDiv();
					}
				}
			}else if(flag=="1"){
				seleId.disabled="disabled";
			if(state=="101011102"){
				but.disabled="disabled";
					}
				}
			}else{
				but.disabled="disabled";
				seleId.disabled="disabled";
			}
	}
	//使用火狐浏览器时，设定默认值
	function show(){
		var accreditWorker = document.getElementById("sendObject-chooser-show");
       		accreditWorker.firstChild.innerHTML = "${userName}";
	}
	//使用IE浏览器时，设定默认值显示
	function showIE(){
		var accreditWorker = document.getElementById("sendObject-chooser-show");
		var parentWorker = accreditWorker.parentNode;
		var newDiv = document.createElement("div");
			newDiv.id="newDiv";
			newDiv.innerHTML = "<br/>${userName}";
			parentWorker.appendChild(newDiv);
			accreditWorker.style.display="none";
	}
	 if (window.addEventListener)
                {
                     window.addEventListener("load", show, false);//使用的是火狐浏览器
                }else if (window.attachEvent){//使用的是IE浏览器
                     window.attachEvent("onload", showIE);
                }else{
                     window.onload=loadHtml;//其他浏览器
                }
    //点击授权人控件，如使用IE浏览器，则相应的改变选择按钮的属性，onclick事件。      
    function changeDiv(){
		var flag = document.getElementById("flagState").value;
		var yesButton = document.getElementById("ext-gen55");
		if(flag==0 && window.attachEvent){
			var accreditWorker = document.getElementById("sendObject-chooser-show");
			var newDiv = document.getElementById("newDIv");
		yesButton.onclick = function(){
				openDiv();
			}
		}
	}
	//IE浏览器下，选择授权人时隐藏默认值，显示控件值
	function openDiv(){
		var accreditWorker = document.getElementById("sendObject-chooser-show");
		var newDiv = document.getElementById("newDIv");
				accreditWorker.style.display="";
				newDiv.style.display="none";
	}
	//IE浏览器下，选择授权人时显示默认值，隐藏控件值
	function hiddenDiv(){
		var accreditWorker = document.getElementById("sendObject-chooser-show");
		var newDiv = document.getElementById("newDIv");
				accreditWorker.style.display="none";
				newDiv.style.display="";
	}
</script>

<div id="sheetform">
	<html:form action="/pnrStatistics.do?method=addWorkerStatePage"
		styleId="theform">
		<table class="formTable">
			<tr>
				<!-- 工单类型 -->
				<td class="label">
					个人状态
				</td>
				<td class="content">
					<eoms:comboBox name="faultSource" id="faultSource"
						defaultValue="${workerState.state}" initDicId="1010111"
						alt="allowBlank:true" styleClass="select"
						onchange="stateChange();" />
				</td>
			</tr>

			<tr>
				<td class="label">
					工单授权
				</td>
				<td class="context">
					<eoms:chooser id="sendObject"
						panels="[{text:'部门与人员',dataUrl:'${app}/xtree.do?method=userFromDept&noself=true',rootId:'${dept}'}]"
						config="{returnJSON:true,showLeader:true}"
						category="[{id:'taskAssignee',text:'接收',allowBlank:true,childType:'user',limit:1,vtext:'请选择接收对象'}]"
						data="" />
					<eoms:id2nameDB id="${workerState.id}" beanId="tawSystemUserDao" />
					<input type="hidden" id="flagState" value="${workerState.flag}" />
					<span id="showError"></span>
				</td>
			</tr>
		</table>
		<!-- buttons -->
		<input type="button" Class="btn" value="确定" onclick="showSubmit();"
			id="but" disabled="disabled" />
	</html:form>
	<br>
	<br>
	<!--显示授权工单-->

	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="false" requestURI="pnrStatistics.do" sort="list" size="total"
		partialList="true">

		<display:column sortable="true" property="orderId"
			headerClass="sortable" title="工单号">
		</display:column>
		<display:column property="zhuTi" sortable="true"
			headerClass="sortable" title="主题" />
		<display:column property="payoutTime" sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		<display:column sortable="true" headerClass="sortable" title="接手工单人">
			<eoms:id2nameDB id="${workerState.accredit}"
				beanId="tawSystemUserDao" />
		</display:column>
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>


</div>
<%@ include file="/common/footer_eoms.jsp"%>