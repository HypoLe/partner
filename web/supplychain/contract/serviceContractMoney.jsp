<%@ page contentType="text/html; charset=GB2312"%>
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<html>
	<%
		String webapp = request.getContextPath();
		String xTree = StaticMethod.nullObject2String(request.getSession()
				.getAttribute("xTree"));
	%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>维护合同金额</title>
		<link rel="stylesheet" href="<%=webapp%>/css/table_style.css"
			type="text/css">
		<style>
				.gradeTr {
				background-color: #C0D0EE;
				height: 20px;
				cursor: hand;
				font-weight: bold;
			}
		
  .TREE_BODY {
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


  a {font-size:12px;color:#104CB9;text-decoration:none;mios:expression(this.onclick=changeLinkStyle)}
  a:link {COLOR: #104CB9;font-size: 12px; TEXT-DECORATION: none}
  a:hover {COLOR: #104CB9;font-size: 12px; TEXT-DECORATION: none}
  a:active {COLOR: #37C0FE;font-size: 12px; TEXT-DECORATION: none}
  a.clicked {color: darkblue; font-size: 12; font-weight:bold;TEXT-DECORATION: underline}
</style>

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
		/**
		*加载服务对象选择的页面.
		*/
		function callSubIframeJs(){
		 	serviceObjFrm.initCheckBoxPage();
		}
		var tree=null;
		function selectWorkAround(){
		  	var contractId=<bean:write name="contractId"/>;
		    dWinOrnaments = "status:no;scroll:auto;resizable:no;dialogWidth:550px;dialogHeight:550px";
		   // dWin = window.showModalDialog("/supplychain/contract/selectWorkAroundWithTree.jsp", window, dWinOrnaments);
		   	var pars="width=880px,height=600px,toolbar=no,menubar=no,resizable=yes,location=no,status=no,scroll:auto";
		    dWin= window.open("/supplychain/contract/workAroundSelectView_PageOpen.do?contractId="+contractId,"选择维护对象",pars);
		}
		/**
		得到子窗口传回来的工作区ID的集合,用来更新公司维护工作去列表
		*/
		function updateWorkArounds(options){
			document.forms[0].serviceWorkArounds.options.length=0;
			for(var i=0;i<options.length;i++){
				var myOpt=options[i];
				var option=new Option(myOpt.text,myOpt.value);
				document.forms[0].serviceWorkArounds.options.add(option);
			}
			//暂且不用(直接用子窗口的window.opener实现).
		}
		//下面开始是下拉框添,删元素
		String.prototype.startsWith = function(prefix) {
			return this.substring(0,prefix.length) == prefix;
		}
		/**删除下拉框中该设备类型下面的所有设备(暂时不用)*/
		function del_some_obj_ByDeviceTypeId(deviceTypeId){
		 var sel_sprlen=document.forms[0].selectServiceObjects.options.length-1;
		  var j=0;
		  for(j=sel_sprlen;j>=0;j--)
		  {
		  	if(document.forms[0].serviceWorkArounds.options[j].value.startsWith(deviceTypeId))  //下拉框的ID构造  deviceTypeId_deviceId
		  	  document.forms[0].serviceWorkArounds.options[j]=null;
		  }
		}
		/**从下拉框中取出所有选中的维护设备，这里面的设备ID不是Device中的ID了，而是变成(WorkAroundId/deviceTypeId)*/
		function getDeviceInSelect(){
			var deviceIdArr=new Array();
			for(var i=0;i<document.forms[0].serviceWorkArounds.options.length;i++){
				deviceIdArr.push(document.forms[0].serviceWorkArounds.options[i].value);
			}
			return deviceIdArr;
		}
		  function showServiceFees(deviceIdArr){
		  //这里面的设备ID不是Device中的ID了，而是变成(WorkAroundId/deviceTypeId)
		  	var paramUrl="";
		  	for(var i=0;i<deviceIdArr.length;i++){
		  		paramUrl+=(deviceIdArr[i]+"_");
		  	}
		  	var contractId=<bean:write name="contractId"/>;
		  	if(paramUrl!="")
		  		paramUrl=paramUrl.substring(0,paramUrl.length-1);
		  	document.forms[0].deviceIds.value=paramUrl;
		//  	alert(document.forms[0].deviceIds.value);
		  	var actionUrl="<%=webapp%>/contract/makeServiceFee.do?contractId=" + contractId;
		  	document.forms[0].action=actionUrl;
		  	document.forms[0].target="contractMoneyFrm";
		  	return true;
		//  	document.getElementById("contractMoneyFrm").src=action;
		  }
	</script>

	</head>
	<!---------------------------------------页面主体开始--------------------------------------->
	<body>
		<!------------------标识页面位置------------------>
		<p align="left">
			所在位置->服务合同管理->
			<bean:write name="specialtyName" />
			->
			<bean:write name="contractModelName" />
			－>合同金额录入
		</p>
		<table width="100%" height="100%" border="0" >
			<tr height="40%">
				<td align="center">
					<form  action="" method="post">
					<table>
						<tr><th class="gradeTr">选择的维护对象:</th><th class="gradeTr">操作:</th></tr>
						<tr>
						<td>
							<select size="15" name="serviceWorkArounds" style="width:280">
							</select>
						</td>
						<td>
							<table cellpadding="1" cellspacing="2">
								<tr><td>
									<input type="hidden" name="deviceIds"/>
									<input type="button" class="clsbigbtn" value="<<服务对象配置" onclick="selectWorkAround();"/>
								</td></tr>
								
								<tr><td>
									<input type="submit" class="clsbigbtn" value='保存维护对象' onclick="return showServiceFees(getDeviceInSelect());"/>
								</td></tr>
							</table>
					
						</td>
						</tr>
					</table>
					</form>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<iframe name="contractMoneyFrm"  src="serviceFee.jsp" style="position:absolute;HEIGHT: 100%;WIDTH: 100%; Z-INDEX: 0" frameborder=0 scrolling="auto"></iframe>
				</td>
			</tr>
		</table>


		<!---------------------------------------合同金额录入页面-------------------------------------->
	<div id="myHiddenDiv" onclick="callSubIframeJs();"></div>
	</body>
	<!---------------------------------------页面主体结束--------------------------------------->

</html>
