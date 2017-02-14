<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<script type="text/javascript">
	
	var xmlHttp;
	var moduleType = "${moduleType}";
	var principalType = "${principalType}";
	var principalId = "${principalId}";
	var box;
	var thisGrade;
	
	function createXMLHttpRequest() {
		//表示当前浏览器不是ie,如ns,firefox
		if(window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	
	//更新或者是增加权限操作
	function saveOrUpdateCtAcl(my) {
		box = my;
		var moduleId = my.moduleId;
		var flag = my.checked;
		var permission = my.permission;
		createXMLHttpRequest();
		
		//得到当前选择的合同等级
		var selects = document.getElementById(moduleId);
		//被选上的等级
		var grade;
		for(var i=0;i<selects.length;i++){
			if(selects.options[i].selected==true){
				//得到被选上的等级
				grade = selects.options[i].id;
			}
		} 
		
		var url;
		//对于一模块只要有一个操作权限被选上了  就更新或者是保存权限记录， 如果没有选上的权限操作将删除该权限记录
		if(($(moduleId+"_R").checked)||($(moduleId+"_C").checked)||($(moduleId+"_U").checked)||($(moduleId+"_D").checked)||($(moduleId+"_G").checked)||($(moduleId+"_CA").checked)){
			url = '${app}/partner/contract/ctAcls.do?method=saveOrUpdateCtAcl&moduleType='+moduleType 
					+'&principalType='+principalType +'&principalId='+principalId +'&moduleId='+moduleId
						+ '&flag='+flag + '&permission='+permission +'&grade='+grade;
		}else{
			url ='${app}/partner/contract/ctAcls.do?method=removeCtAcl&moduleType='+moduleType 
					+'&principalType='+principalType +'&principalId='+principalId +'&moduleId='+moduleId;
		}
		xmlHttp.open("POST", url , false);
		xmlHttp.onreadystatechange=callback;
		xmlHttp.send(null);
	}
	
	//回调函数
	function callback() {
	    if (xmlHttp.readyState == 4) {
		  if (xmlHttp.status == 200) {
		  	var xmlText = xmlHttp.responseText;
		  	if(xmlText=='1'){
		  		if(box.checked){
		  			box.checked=false;
		  		}else{
		  			box.checked=true;
		  		}
		  		alert('授权失败！您的权限不足！');
		  	}
		  }
	   }
	}
	
	
	//根据主体所拥有的权限对checkBox进行选上操作初始化
	function initDate(){
		createXMLHttpRequest();
		var url = '${app}/partner/contract/ctAcls.do?method=searchCheck&moduleType='+moduleType 
					+'&principalType='+principalType +'&principalId='+principalId ;
		xmlHttp.open("POST", url , false);
		xmlHttp.onreadystatechange=callback1;
		xmlHttp.send(null);
	}
	
	//回调函数
	function callback1() {
	    if (xmlHttp.readyState == 4) {
		  if (xmlHttp.status == 200) {
		  	var xmlText = xmlHttp.responseText;
		  	var test = eval(xmlText);
		  	var length = test.length;
		  	for(var i=0;i<length;i++){
		  		var M = test[i].M;
		  		//初始化权限
		  		var rState = test[i].rState;
		  		var cState = test[i].cState;
		  		var uState = test[i].uState;
		  		var dState = test[i].dState;
		  		var gState = test[i].gState;
		  		var caState = test[i].caState;
		  		$(M+"_R").checked = rState==0 ? false : true;
		  		$(M+"_C").checked = cState==0 ? false : true;
		  		$(M+"_U").checked = uState==0 ? false : true;
		  		$(M+"_D").checked = dState==0 ? false : true;
		  		$(M+"_G").checked = gState==0 ? false : true;
		  		$(M+"_CA").checked = caState==0 ? false : true;
		  		
		  		//设值初始化等级
		  		var grade = test[i].grade;
		  		$(M).options[grade].selected = true;
		  	}
		  }
	   }
	}
	
	//选择等级时候等到原等级
	function getOption(my2){
		for(var i=0;i<my2.length;i++){
			if(my2.options[i].selected==true){
				//得到被选上的等级
				thisGrade = my2.options[i].id;
			}
		} 
	}
	
	//设值等级
	function changeGrade(my1){
		box = my1;
		var id = my1.id;
		//判断是否已经对该模块授权了  如果授权了就可以进行等级调整 如果没有授权将返回null
		if(($(id+"_R").checked)||($(id+"_C").checked)||($(id+"_U").checked)||($(id+"_D").checked)||($(id+"_G").checked)||($(id+"_CA").checked)){
			//被选上的等级
			var grade;
			for(var i=0;i<my1.length;i++){
				if(my1.options[i].selected==true){
					//得到被选上的等级
					grade = my1.options[i].id;
				}
			} 
			createXMLHttpRequest();
			var url = '${app}/partner/contract/ctAcls.do?method=saveCtAclGrade&moduleType='+moduleType 
					+'&principalType='+principalType +'&principalId='+principalId+'&moduleId='+id +'&grade='+grade;
			xmlHttp.open("POST", url , false);
			xmlHttp.onreadystatechange=callback2;
			xmlHttp.send(null);
		}
		return ;
	}
	
	//回调函数
	function callback2() {
	    if (xmlHttp.readyState == 4) {
		  if (xmlHttp.status == 200) {
		  	var xmlText = xmlHttp.responseText;
		  	if(xmlText=='1'){
		  		box.options[thisGrade].selected = true;
		  		alert('授权失败！您的权限不足！');
		  	}
		  }
	   }
	}
</script>

<html:form action="/ctAcls.do?method=save" styleId="ctAclForm"
	method="post">

	<body onload="initDate()">

		<table class="tableEdit" style="width: 560px;" cellspacing="0"
			border="0" cellpadding="0">
			<br>
			<tr>
				<b>文件管理权限</b>
			</tr>
			<br>
			<tr>
				<td>
					<b>一级模块</b>
				</td>
				<td>
					<b>二级模块</b>
				</td>
				<td>
					<b>&nbsp&nbsp&nbsp操作权限</b>
				</td>
				<td></td>
				<td>
					<b>操作等级</b>
				</td>
			</tr>

			<!-- 模块 -->
			<c:forEach items="${list1}" var="Pnode">
				<tr>
					<td>
						<b>${Pnode.nodeName }</b>
					</td>
					<td>
					</td>

					<td>
						<!-- 
				<input type="checkbox" id="${Pnode.nodeId }_R" onclick="saveOrUpdateCtAcl(this)" moduleId="${Pnode.nodeId}" permission="0">查看
				<input type="checkbox" id="${Pnode.nodeId }_C" onclick="saveOrUpdateCtAcl(this)" moduleId="${Pnode.nodeId}" permission="1" >增加
				<input type="checkbox" id="${Pnode.nodeId }_U" onclick="saveOrUpdateCtAcl(this)" moduleId="${Pnode.nodeId}" permission="2" >修改
				<input type="checkbox" id="${Pnode.nodeId }_D" onclick="saveOrUpdateCtAcl(this)" moduleId="${Pnode.nodeId}" permission="3" >删除
				<input type="checkbox" id="${Pnode.nodeId }_G" onclick="saveOrUpdateCtAcl(this)" moduleId="${Pnode.nodeId}" permission="4" >授予
				<input type="checkbox" id="${Pnode.nodeId }_CA" onclick="saveOrUpdateCtAcl(this)" moduleId="${Pnode.nodeId}" permission="5" >排除
				 -->
					</td>
					<td></td>
					<td>
						<!-- <select id="${Pnode.nodeId}"  onchange="changeGrade(this)">
						 <option id="0">一级</option>
						 <option id="1">二级</option>
						 <option id="2">三级</option>
						 <option id="3">四级</option>
						 <option id="4">五级</option>
						  -->
						</select>
					</td>
				</tr>

				<c:forEach items="${list2}" var="Cnode">
					<c:if test="${Cnode.parentNodeId ==Pnode.nodeId}">
						<tr>

							<td></td>
							<td>
								${Cnode.nodeName }
							</td>
							<td>
								<input type="checkbox" id="${Cnode.nodeId }_R"
									onclick="saveOrUpdateCtAcl(this)" moduleId="${Cnode.nodeId}"
									permission="0">
								查看
								<input type="checkbox" id="${Cnode.nodeId }_C"
									onclick="saveOrUpdateCtAcl(this)" moduleId="${Cnode.nodeId}"
									permission="1">
								增加
								<input type="checkbox" id="${Cnode.nodeId }_U"
									onclick="saveOrUpdateCtAcl(this)" moduleId="${Cnode.nodeId}"
									permission="2">
								修改
								<input type="checkbox" id="${Cnode.nodeId }_D"
									onclick="saveOrUpdateCtAcl(this)" moduleId="${Cnode.nodeId}"
									permission="3">
								删除
								<input type="checkbox" id="${Cnode.nodeId }_G"
									onclick="saveOrUpdateCtAcl(this)" moduleId="${Cnode.nodeId}"
									permission="4">
								授予
								<input type="checkbox" id="${Cnode.nodeId }_CA"
									onclick="saveOrUpdateCtAcl(this)" moduleId="${Cnode.nodeId}"
									permission="5">
								排除
							</td>
							<td></td>
							<td>
								<select id="${Cnode.nodeId}" onchange="changeGrade(this)"
									onclick="getOption(this)">
									<option id="0">
										一级
									</option>
									<option id="1">
										二级
									</option>
									<option id="2">
										三级
									</option>
									<option id="3">
										四级
									</option>
									<option id="4">
										五级
									</option>
								</select>
							</td>
						</tr>
					</c:if>
				</c:forEach>

			</c:forEach>
		</table>
	</body>
	<html:hidden property="id" value="${ctAclForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>