<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
String webapp = request.getContextPath();
	String contractId = request.getParameter("contractId");
	String accessModelId=request.getParameter("accessModelId");
	String serviceTypeId=request.getParameter("serviceTypeId");
%>
<html>
	<head>
		<title>选择考核模型</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<jsp:include flush="true" page="../access/accessmanager/quote/modaltree.jsp"></jsp:include>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/table_style.css"
			type="text/css">
	<style type="text/css">
			.styles{
	    		font-size:10pt;
	    		font-family: 宋体;
	    	}
	</style>
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
		    function init(){
		    	//初始化考核模型树
		    	
	    		kpiInit(refreshModal);
	    		var accessModelId=<%=accessModelId%>;
	    		var serviceTypeId=<%=serviceTypeId%>;    		
	    		
	    		if(accessModelId!=0){//具有考核模型
	    			openNode(accessModelId,serviceTypeId);
	    			
	    			//alert(1);
	    			//alert(accessModelId+","+serviceTypeId);
	    			//调用modeltree.jsp里面的openNode方法,让节点得到焦点.
	    			
	    		}
	    	}
	    	function refreshModal(id,name){
	    		document.getElementById("modalName").innerText=name;
	    		document.forms[0].modalId.value=id;
	    	}
	    	function checkSave(){
	    		var modalId=document.forms[0].modalId.value;
	    		if(modalId==null || modalId==""){
	    			alert("请选择一个考核模型,再进行保存!");
	    			return;
	    		}else{
	    			var contractId=<%=contractId%>;
	    			ajaxAddAccessModal4Comtract(contractId,modalId);
	    		}
	    	}
	    	
	    	function ajaxAddAccessModal4Comtract(contractId,accessModalId){
				var request={
					url:"<%=webapp%>/contract/ajaxSaveAccessModal.do?accessModalId="+accessModalId+"&contractId="+contractId,
					mimetype:"text/plain",
					method:"get",
					transport: "XMLHTTPTransport",
					sync:false,
					preventCache:true,
					encoding:"utf-8",
					load: function(type,data,evt){
						if(dojo.lang.isString(data)){
							var flag=parseInt(data);
							if(flag==1){//保存成功
								alert("考核模型保存成功!");
								window.opener.location="<%=webapp%>/contract/controller_service.do?contractId=" + contractId+"&view_type=modifyView";
							//	window.dialogArguments.location="<=webapp%>/contract/controller_service.do?contractId=" + contractId;
								window.close();
							}
						}
					},
					error:function(type,error){
						alert(error.message);
					}
				};
				dojo.io.queueBind(request);
			}
			dojo.addOnLoad(init);
	</script>
	</head>
	
	<body>
	<form>
				<table class="table_show" width="100%" cellspacing="1"
							cellpadding="1" border="0">
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									选择考核模型
								</td>
								<td width="35%" height="25">
									<div id="modelTree">
									
									</div>
								</td>
							</tr>
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									被选择的考核模型
								</td>
								<td width="35%" height="25">
									<span id="modalName"></span>
									<input type="hidden" name="modalId"/> 
								</td>

							</tr>
							<tr class="tr_show">
								<td colspan="2">
									<input type="button" value='<bean:message key="label.saveAccessModal" />' onclick="checkSave();"/>
									<input type="button" value='<bean:message key="label.closeWin" />' onclick="window.close();"/>
								</td>
							</tr>
						</table>
		</form>
	</body>
</html>
