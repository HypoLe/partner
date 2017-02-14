<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/supplychain.tld" prefix="supplychain"%>
<%
	String webapp = request.getContextPath();
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
  #wrap_div{ width:100%; height:auto;}
  #left_div{ float:left; width:30%;}
  #right_div{float:right; width:70%;}
  .clear{ clear:both;}
</style>
		<link rel="stylesheet"
			href="<%=webapp%>/css/table_style.css"
			type="text/css">
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
			var checkedflag=false;
			function saveWorkAround(id,name){
				checkedflag=true;
				//window.dialogArguments.resetWorkAround(id,name);
				window.opener.saveWorkAround(id,name);
			}	
			//确定时检查是否已选则了服务对象
			function checkData(){
			  if(!checkedflag){
			    alert("请选择工作区!");
			    return false;
			  }
			  window.close();
			}
			function init(){	
				updateInfo(document.getElementsByName("regionId")[0].value);
				window.forms[0].submit();
			}
			function updateInfo(regionId){
					var request={
						url:"<%=webapp%>/common/listCityByRegionId.do?regionId="+regionId,
						mimetype:"text/plain",
						method:"get",
						transport: "XMLHTTPTransport",
						sync:false,
						preventCache:true,
						encoding:"utf-8",
						load: function(type,data,evt){
							var cityArr=eval(data);	
							var select=document.getElementsByName("cityId")[0];
							select.options.length=0;
							var optionTop=new Option("--县市选择--");
							optionTop.value=0;
							select.options[select.options.length]=optionTop;
							for(var i=0;i<cityArr.length;i++){
								var option=new Option();
								option.text=cityArr[i].cityName;
								option.value=cityArr[i].cityId;
								select.options[select.options.length]=option;
							}
						},
						error:function(type,error){
							alert(error.message);
						}
					};
					dojo.io.bind(request);
		
			}
			dojo.addOnLoad(init);
		</script>
		<supplychain:dictDictRegion type="all"></supplychain:dictDictRegion>
	</head>
	<body>
		<table width="100%" height="100%-32" border="0" cellspacing="1"
			cellpadding="5" bgcolor="#BEDEF8">
			<tr height="30">
				<td nowrap class="small" bgcolor="#E2F1FC" valign="absmiddle">
					&nbsp;请输入查询条件
				</td>
			</tr>
			<tr>
				<td bgcolor="#FFFFFF" valign="top" align="center">
					<html:form action="/baseinfo/exposure/workAround.do?method=list&forward=workaroundlist4propertycontract" target="workAroundTableFrm">
						<table class="table_show" width="100%" cellspacing="1"
							cellpadding="1" border="0">
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									地市选择
								</td>
								<td width="35%" height="25">
									<html:select property="regionId" style="width: 4cm;"
										onchange="updateInfo(this.value);">
										<html:options collection="simpleDictRegionOptions" property="value"
											labelProperty="label" />
									</html:select>
								</td>
								<td width="15%" height="25" class="clsfth">
									县市选择
								</td>
								<td width="35%" height="25">
									<html:select property="cityId" style="width: 4cm;">
										
									</html:select>
								</td>
							</tr>
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									工作区名
								</td>
								<td width="35%" height="25" colspan="3">
									<html:text property="workAroundName" value=""></html:text>
									<script type="text/javascript">
							//	  	 document.getElementsByName("workAroundName")[0].value='';
								  </script>
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3" cellpadding="3">
							<tr>
								<td align="center">
									<a href="javascript://nop/" class="contentLink"
										onclick="document.forms[0].submit();">查询</a>
								</td>
							</tr>
						</table>
					</html:form>
				</td>
			</tr>
			<tr id="workAroundInfoView" height="80%">
				<td bgcolor="#FFFFFF">
					<iframe name="workAroundTableFrm" src="workAroundTable.jsp" width="100%" height="100%"
						scrolling="no" frameborder="0"></iframe>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="buttonBanner" height="30">
					<table cellpadding="5" width="100%">
						<tr>
							<td align="center">
								<input type="button" class="clsbtn2" value="确定"
									onclick="return checkData();">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="clsbtn2" value="取消"
									onclick="window.close();">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
