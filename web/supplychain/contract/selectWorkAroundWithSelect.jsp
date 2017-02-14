<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%@page import="com.boco.supplychain.util.tree.TreeFactory"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%
	String webapp = request.getContextPath();
	Map company_own_WorkArounds=(Map)request.getSession().getAttribute("company_own_WorkArounds");   //取公司下面维护工作区的集合,用于确定公司下面代维人员能够维护工作区的范围
	System.out.println("Size="+company_own_WorkArounds.size());
	request.setAttribute("company_own_WorkArounds",company_own_WorkArounds);
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>工作区对象树</title>
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
	//下面开始是下拉框添,删元素
	String.prototype.startsWith = function(prefix) {
		return this.substring(0,prefix.length) == prefix;
	}
	function add_obj(){
		 var sel_index=document.forms[0].company_own_WorkArounds.selectedIndex;
		 if(sel_index!=-1){
		 	var workAroundId=document.forms[0].company_own_WorkArounds.options[sel_index].value;
			var workAroundText=document.forms[0].company_own_WorkArounds.options[sel_index].text;
		 	if(isInSelect(workAroundId)){
					alert("<bean:message key="label.alerto"/>");
			}else{
				var option=new Option(workAroundText,workAroundId);
				document.forms[0].selectWorkArounds.options[document.forms[0].selectWorkArounds.options.length]=option
			}
		 }
	}	
	function add_all_obj(){
	  document.forms[0].selectWorkArounds.options.length=0;
	  for(var i=0;i<document.forms[0].company_own_WorkArounds.options.length;i++){
	  		var workAroundId=document.forms[0].company_own_WorkArounds.options[i].value;
			var workAroundText=document.forms[0].company_own_WorkArounds.options[i].text;
	  		var option=new Option(workAroundText,workAroundId);
	  		document.forms[0].selectWorkArounds.options[document.forms[0].selectWorkArounds.options.length]=option
	  }
	}
	function isInSelect(workAroundId){
		for(var i=0;i<document.forms[0].selectWorkArounds.options.length;i++){
			if(document.forms[0].selectWorkArounds.options[i].value==workAroundId){
				return true;
			}
		}
		return false;
	}
	function del_obj(){
	  var sel_sprindex=document.forms[0].selectWorkArounds.selectedIndex;
	  if(sel_sprindex!=-1)
	    document.forms[0].selectWorkArounds.options[sel_sprindex]=null;
	}
	function del_all_obj(){
	  var sel_sprlen=document.forms[0].selectWorkArounds.options.length-1;
	  var j=0;
	  for(j=sel_sprlen;j>=0;j--)
	  {
	    document.forms[0].selectWorkArounds.options[j]=null;
	  }
	}
	/**保存选择的工作区到window.opener(谁打开了这个页面)*/
	function saveWorkArounds(){
	/**	var workAroundIdArr=new Array();
		for(var i=0;i<document.forms[0].selectWorkArounds.options.length;i++){
			workAroundIdArr.push(document.forms[0].selectWorkArounds.options[i].value);
		}
		window.dialogArguments.updateWorkArounds(workAroundIdArr);*/
		window.opener.updateWorkArounds(document.forms[0].selectWorkArounds.options);
		window.close();
	}
	/**初始化这个工作区列表,保持很window.opener的工作区列表一致*/
	function initWorkArounds(){
	/**	var workAroundIdArr=new Array();
		for(var i=0;i<document.forms[0].selectWorkArounds.options.length;i++){
			workAroundIdArr.push(document.forms[0].selectWorkArounds.options[i].value);
		}
		window.dialogArguments.updateWorkArounds(workAroundIdArr);*/
		document.forms[0].selectWorkArounds.options.length=0;
		var length=window.opener.document.forms[0].serviceWorkArounds.options.length;
		for(var i=0;i<length;i++){
			var opt=window.opener.document.forms[0].serviceWorkArounds.options[i];
			var newOpt=new Option(opt.text,opt.value);
			document.forms[0].selectWorkArounds.options[document.forms[0].selectWorkArounds.options.length]=newOpt;
		}
	}
</script>
	</head>
	<body onload="initWorkArounds();">
		<form action="">
			<div id="wrap_div">
				<div id="left_mid_div">
					<div id="left_div" align="left"
						style="width:240;height:expression(document.body.clientHeight-36);overflow:auto;">
						<select size="20" name="company_own_WorkArounds" style="width:180">
							<logic:iterate id="row" name="company_own_WorkArounds">
							<option value='<bean:write name="row" property="key"/>'><bean:write name="row" property="value"/></option>
							</logic:iterate>
						</select>
					</div>
					<div id="mid_div">
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
					<select size="20" name="selectWorkArounds" style="width:180">
					</select>
					<input type="button" value='<bean:message key="label.saveWorkAround" />' onclick="saveWorkArounds();"/>
					<input type="button" value='<bean:message key="label.closeWin" />' onclick="window.close();"/>
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</body>
</html>
