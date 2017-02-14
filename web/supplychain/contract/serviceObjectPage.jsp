<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%
	String webapp = request.getContextPath();
	String xTree = StaticMethod.nullObject2String(request.getSession()
			.getAttribute("xTree"));
	System.out.println(xTree);
	if (xTree == null || xTree.equals("")) {
%>
<script type="text/javascript">
  alert("服务对象树生成错误,请重新操作！");
  history.back();
</script>
<%
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>服务对象树</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<style>
  BODY {
  background:#E6F2FC;
  filter = "progid:DXImageTransform.Microsoft.gradient(gradientType=0, startColorStr=#F0F3F7, endColorStr=#96BDC6)" ;
  }
  img{
  border:0px;
  }

  td{
  font-size:12px;
  vertical-align:bottom;
  COLOR:#1F3559;
  }

  div{
  scrollbar-face-color: #F7F7F7;
  scrollbar-shadow-color: #90a0a0;
  scrollbar-highlight-color: #F6F8FD;
  scrollbar-3dlight-color: #FFFFFF;
  scrollbar-darkshadow-color: #FFFFFF;
  scrollbar-track-color: #FFFFFF;
  scrollbar-arrow-color: #90a0a0;
  }
  #wrap_div{ width:100%; height:auto;}
  #left_mid_div{float:left;width:"60%"}
  #left_div{ float:left; width:50%;}
  #mid_div{ float:right; width:10%;}
  #right_div{float:right; width:40%;}
  .clear{ clear:both;}
  a {font-size:12px;color:#104CB9;text-decoration:none;mios:expression(this.onclick=changeLinkStyle)}
  a:link {COLOR: #104CB9;font-size: 12px; TEXT-DECORATION: none}
  a:hover {COLOR: #104CB9;font-size: 12px; TEXT-DECORATION: none}
  a:active {COLOR: #37C0FE;font-size: 12px; TEXT-DECORATION: none}
  a.clicked {color: darkblue; font-size: 12; font-weight:bold;TEXT-DECORATION: underline}
</style>
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/xtree/xtree.css">
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/xtree/xtree.js"></script>
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/dojo/dojo.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dojo/src/io.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dojo/src/string.js"></script>
		<script type="text/javascript">
			djConfig={
				isDebug:false,
				bindEncoding:"utf-8"
			};
			
			dojo.require("dojo.io.BrowserIO");
		</script>
		<script type="text/javascript">
	//用于单击后链接变色
	  var oLinkClicked = null;
	  function changeLinkStyle(){
		var oOnClick = event.srcElement;
		if (oLinkClicked){
			oLinkClicked.className = "";
		}
		oOnClick.className = "clicked";
		oOnClick.blur();
		oLinkClicked = oOnClick;
	  }
	  </script>
	<script type="text/javascript">
		var tree=null;
			function ajaxLoadSomeServiceObj(serviceTypeId,cityId,cityNodeId){
				if(webFXTreeHandler.all[cityNodeId].childNodes.length>0)
					return;
				var cityNode=webFXTreeHandler.all[cityNodeId];
				var loadNode=new WebFXTreeItem("temp_loading_img","Loading...","javascript:void(0);",cityNode,"<%=webapp%>/images/monitor/connect.gif");
				cityNode.expand();
				var request={
					url:"<%=webapp%>/contract/ajaxLoadSomeServiceObj.do?serviceTypeId="+serviceTypeId+"&cityId="+cityId,
					mimetype:"text/plain",
					method:"get",
					transport: "XMLHTTPTransport",
					sync:false,
					preventCache:true,
					encoding:"utf-8",
					load: function(type,data,evt){
						if(dojo.lang.isString(data)){
							var deviceArr=null;
							try{
								deviceArr=eval(data)
							}catch(e){
								
							}
							if(deviceArr!=null && dojo.lang.isArray(deviceArr)){
								for(var i=0;i<deviceArr.length;i++){
									var node=new WebFXTreeItem("device_"+deviceArr[i].deviceId,deviceArr[i].cellId+"/"+deviceArr[i].deviceName,"javascript:void(0);");
									cityNode.add(node);
								}
							}
						}
						loadNode.remove();
					},
					error:function(type,error){
						alert(error.message);
					}
				};
				//dojo.io.bind(request);
				dojo.io.queueBind(request);
			}
	//下面开始是下拉框添,删元素
	String.prototype.startsWith = function(prefix) {
		return this.substring(0,prefix.length) == prefix;
	}
	function add_obj(){
		add_all_obj();
	}	
	function add_all_obj(){
		if(tree.getSelected()!=null){
			var nodeId=tree.getSelected().id;
			if(nodeId.startsWith("city")){
				var childNodes=tree.getSelected().childNodes;
				var message="";
				var flag=false;
				for(var i=0;i<childNodes.length;i++){
					var deviceId=childNodes[i].id.split("_")[1];
					var deviceText=childNodes[i].text;
					if(isInSelect(deviceId)){
						flag=true;
						message+=deviceText+","
					}else{
						var option=new Option(deviceText);
						option.value=deviceId;
						document.forms[0].selectServiceObjects.options[document.forms[0].selectServiceObjects.options.length]=option;
					}
				}
				if(flag){
					alert(message+"<bean:message key="label.alertr"/>");
				}
			}else if(nodeId.startsWith("device")){
				var deviceId=nodeId.split("_")[1];
				var deviceText=tree.getSelected().text;
				if(isInSelect(deviceId)){
					alert("<bean:message key="label.alerto"/>");
				}else{
					var option=new Option(deviceText);
					option.value=deviceId;
					document.forms[0].selectServiceObjects.options[document.forms[0].selectServiceObjects.options.length]=option;
				}
			}
		}
	}
	function isInSelect(deviceId){
		for(var i=0;i<document.forms[0].selectServiceObjects.options.length;i++){
			if(document.forms[0].selectServiceObjects.options[i].value==deviceId){
				return true;
			}
		}
		return false;
	}
	function del_obj(){
	  var sel_sprindex=document.forms[0].selectServiceObjects.selectedIndex;
	  if(sel_sprindex!=-1)
	    document.forms[0].selectServiceObjects.options[sel_sprindex]=null;
	}
	function del_all_obj(){
	  var sel_sprlen=document.forms[0].selectServiceObjects.options.length-1;
	  var j=0;
	  for(j=sel_sprlen;j>=0;j--)
	  {
	    document.forms[0].selectServiceObjects.options[j]=null;
	  }
	}
	function saveServiceObj(){
		var deviceIdArr=new Array();
		for(var i=0;i<document.forms[0].selectServiceObjects.options.length;i++){
			deviceIdArr.push(document.forms[0].selectServiceObjects.options[i].value);
		}
		alert(deviceIdArr.length);
		window.dialogArguments.showServiceObjects(deviceIdArr);
	}

	//下面是编辑XTree的方法 added by Lin Wenwang
	function editNode(id,newName){
		webFXTreeHandler.all[id].select();
		webFXTreeHandler.all[id].text=newName;
		webFXTreeHandler.all[id].updateText(newName);
		
	}
	function delNode(id){
		webFXTreeHandler.all[id].select();
		webFXTreeHandler.all[id].remove();
	}
	function addNode(pid,id,name,action){
		webFXTreeHandler.all[pid].select();
		var addNode=new WebFXTreeItem(id,name,action);
		addNode.target="rightFrame";
		webFXTreeHandler.all[pid].add(addNode);
	}
</script>
	</head>
	<body>
	<a href="javascript:alert(2)">ttt</a>
		<form action="">
			<div id="wrap_div">
				<div id="left_mid_div">
					<div id="left_div" align="left"
						style="width:240;height:expression(document.body.clientHeight-36);overflow:auto;">
						<div id="tree_div" name="tree_div" value="<%=webapp%>">
							<script type="text/javascript">
								webFXTreeConfig.setContextPath('<%=request.getContextPath()%>');
								webFXTreeConfig.setSrcPath('js/xtree');
								<%=xTree%> //构造Tree
								document.write(tree);
							</script>
						</div>
					</div>
					<div id="mid_div">
						<br>
						<br>
						<br>
						<br>
						<br>
						<br>
						<br>
						<br>
						<br>
						<br>
						<br>
						<input type="button" name="btnAddItem"	value='<bean:message key="label.add1"/>' onclick="add_obj();">
						<p>
						<input type="button" name="btnDelItem" value='<bean:message key="label.remove1"/>' onclick="del_obj();">
						<p>
						<input type="button" name="btnAddAll"	value='<bean:message key="label.alladd"/>' onclick="add_all_obj();">
						<p>
						<input type="button" name="btnDelAll" value='<bean:message key="label.alldel"/>' onclick="del_all_obj();">
						<br>						
						<br>
						<br>
	
					</div>
					<div class="clear"></div>
				</div>
				<div id="right_div" align="right">
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>
					<select size="20" name="selectServiceObjects" style="width:180">
					</select>
					<input type="button" value='<bean:message key="label.saveServiceObj" />' onclick="saveServiceObj();"/>
					<input type="button" value='<bean:message key="label.closeWin" />' onclick="window.close();"/>
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</body>
</html>
