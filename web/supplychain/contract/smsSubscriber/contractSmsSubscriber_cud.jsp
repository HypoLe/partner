<%@ page language="java" pageEncoding="GB2312"%>
<%@page import="com.boco.supplychain.util.StaticVariable" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<%
String webapp=request.getContextPath();
 %>
<script type="text/javascript" src="../js/dojo/dojo.js"></script>
<script type="text/javascript" src="../js/dojo/src/io.js"></script>
<script type="text/javascript">
			djConfig={
				isDebug:false
			};
			
			dojo.require("dojo.io.BrowserIO");
		</script>
<script type="text/javascript">

		function updateInfo(){
				var contractNo=document.forms[0].contractNo.value;
				if(contractNo.Trim()!=""){
					var request={
						url:"<%=request.getContextPath()%>/contract/smsSubscriber.do?method=loadSmsSubscriberByContractNo&contractNo="+contractNo,
						mimetype:"text/plain",
						method:"get",
						transport: "XMLHTTPTransport",
						sync:false,
						preventCache:true,
						encoding:"utf-8",
						load: function(type,data,evt){
							if(data.length==0){
							  // window.alert("您输入的合同号码没有对应供应商！");
								document.forms[0].smsSubscriberName.value='';	
								document.forms[0].smsSubscriberPhone.value='';	
								document.forms[0].contractNo.focus();
								document.getElementById("saveBtnDiv").style.display="block";
								document.getElementById("editBtnDiv").style.display="none";
							}else{
								eval("var smsSubscriberJson="+data);//json String can't use eval expression, like this "var smsSubscriberJson=eval(data);"
								document.forms[0].smsSubscriberName.value=smsSubscriberJson.smsSubscriberName;	
								document.forms[0].smsSubscriberPhone.value=smsSubscriberJson.smsSubscriberPhone;	
								document.getElementById("saveBtnDiv").style.display="none";
								document.getElementById("editBtnDiv").style.display="block";
							}
						},
						error:function(type,error){
							
						}
					};
					dojo.io.bind(request);
				}
			}
			  //选择合同号
			function selectContractNo(){
			    var contractNo = document.forms[0].contractNo.value;
			   // dWinOrnaments = "status:no;scroll:auto;resizable:no;dialogWidth:550px;dialogHeight:550px";
			    //dWin = window.showModalDialog("/supplychain/contract/selectWorkAround.jsp?workAroundName="+workAroundName, window, dWinOrnaments);
			     var pars="width=500px,height=720px,toolbar=no,menubar=no,resizable=yes,location=no,status=no";
			    dWin= window.open("/supplychain/contract/selectContractNo.jsp?contractNo="+contractNo+"&contractTypeId=0","mywin",pars);  //查询合同号
			}
			//重置合同号
			function resetContractNo(no){
				document.forms[0].contractNo.value=no;
				updateInfo();
			}
			
			function checkContractNo(be_alert){
			    var contractNo = document.forms[0].contractNo.value;
			    var flag=false;
			    if(contractNo == ""){
			        alert("请先填写合同号");
			        document.forms[0].contractNo.focus;
			        flag=false;
			    }
			    else{
			    	var request={
						url:"<%=webapp%>/contract/checkContractNo.do?contractNo="+contractNo+"&contractTypeId=0",
						mimetype:"text/plain",
						method:"get",
						transport: "XMLHTTPTransport",
						sync:true,
						preventCache:true,
						encoding:"utf-8",
						load: function(type,data,evt){
							var echo=parseInt(data);
							if(echo >0){
								if(be_alert==true){
					           		 alert("该合同号存在,验证通过!");
					            }
								flag=true;
					        }else if(echo==0){
					        	alert("该合同号不存在,请确认!");
					            flag=false;
					        }
						},
						error:function(type,error){
							alert(error.message);
							flag=false;
						}
					};
					dojo.io.bind(request);
				}
				return flag;
			}

</script>
<script language="javascript"
	src="<%=request.getContextPath()%>/js/calendar2.js">
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/form/checkform.js"></script>
	<script type="text/javascript">
			function doCommand(command){
				if(command=="add"){
					if(checkForm()){
						document.forms[0].action="<%=webapp%>/contract/smsSubscriber.do?method=add";
						//document.forms[0].submit();
						return true;
					}
				}else if(command=="update"){
					if(checkForm()){
						document.forms[0].action="<%=webapp%>/contract/smsSubscriber.do?method=update";
						//document.forms[0].submit();
						return true;
					}
				}else if(command=="delete"){
					if(checkContractNo(false)){
						document.forms[0].action="<%=webapp%>/contract/smsSubscriber.do?method=delete";
						if(window.confirm("您是否删除该短信提醒人!")){
						//	document.forms[0].submit();
							return true;
						}
					}
				
				}
				return false;
			}
			function checkForm(){
				var flag=false;
				var contractNo = document.forms[0].contractNo;
				var smsSubscriberName=document.forms[0].smsSubscriberName;
				var smsSubscriberPhone=document.forms[0].smsSubscriberPhone;
				if(checkContractNo(false)  && checkLength(smsSubscriberName,1,10) && mobileCheck(smsSubscriberPhone)){
					flag= true;
				}else{
					flag= false;
				}
				return flag;
			}
		</script>
<title>定制合同过期提醒人</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/table_style.css"
	type="text/css">
<style type="text/css">
		.clstext_gray {
			border: 1px solid #174EA8;
			background-color: #AEAEAE;
		}
		</style>


<body>
<center>
		<p align="left">
			所在位置－>服务合同管理->定制合同过期短信提醒人
		</p>
</center>
	<html:form
		action="/contract/smsSubscriber?method=add" target="_self">
						<table class="table_show" width="55%" cellspacing="1" 
							cellpadding="1" border="0" id="maintable" align="center">
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									合同号
								</td>
								<td width="35%" height="25">
									<html:text   styleClass="clstext"  maxlength="20" property="contractNo" size="20" title="合同号" onchange="updateInfo();"/>
									<img id="selectContractNoImg" src="<%=webapp%>/images/058.gif"
										style="cursor: hand;" alt="选择" onclick="selectContractNo();">
									<img id="checkContractNoImg" src="<%=webapp%>/images/082.gif"
										style="cursor: hand;" alt="检查" onclick="checkContractNo(true);">
								</td>
							</tr>
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									提醒人名称
								</td>
								<td width="35%" height="25">
									<html:text  styleClass="clstext" maxlength="20"
										property="smsSubscriberName" size="20" title="短信提醒人名字"/>
									&nbsp;&nbsp;
								</td>
							</tr>
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									提醒人手机号码
								</td>
								<td width="35%" height="25">
									<html:text  styleClass="clstext" maxlength="20"
										property="smsSubscriberPhone" size="20"  title="短信提醒人手机号"/>
									&nbsp;&nbsp;
								</td>
							</tr>
							<tr class="tr_show">
								<td align="right" height="15" colspan="2" >
								<div style="display: none;float: right" id="saveBtnDiv">
									<html:submit styleClass="clsbtn" onclick="return doCommand('add');">添加提醒人</html:submit><%--
									<input type="submit" onclick="return doCommand('add');" class="clsbtn" value="添加提醒人"/>
									--%><html:reset styleClass="clsbtn2">
										<bean:message key="label.reset" />
									</html:reset>
								</div>
								<div style="display: none;float: right" id="editBtnDiv">
									<html:submit styleClass="clsbtn" onclick="return doCommand('update');">更新提醒人</html:submit>
									<html:submit styleClass="clsbtn" onclick="return doCommand('delete');">删除提醒人</html:submit><%--
									<input type="submit" onclick="return doCommand('update');" class="clsbtn" value="更新提醒人"/>
									<input type="submit" onclick="return doCommand('delete');" class="clsbtn" value="删除提醒人"/>
									--%><html:reset styleClass="clsbtn2">
										<bean:message key="label.reset" />
									</html:reset>
								</div>
								</td>
							</tr>
						</table>
	</html:form>
		<logic:notEmpty name="message">
			<script type="text/javascript">
				alert('<bean:write name="message"/>');
			</script>
		</logic:notEmpty>
		<table border="0" width="98%" align="center">
			<tr>
				<td>
					<b>友情提醒:</b>
				</td>
			</tr>
			<tr>
				<td>
					<OL>
						<LI>
							短信提醒人定制功能的前提是，往“合同号”框中输入正确的合同号.根据合同号来定制合同的过期提醒人.
						<LI>
							合同号可以手动输入,合同号输入完成后系统自动匹配出短信提醒人的信息.如果短信提醒人信息为空:可以添加提醒人、如果短信提醒人存在：则可以编辑提醒人信息或者删除提醒人.
						<LI>
							点击<img src="<%=webapp%>/images/058.gif"	style="cursor: hand;" alt="选择">打开合同号的选择的页面,选择完合同号返回.
						<LI>
							点击<img id="checkNet" src="<%=webapp%>/images/082.gif" style="cursor: hand;">可以对合同号是否存在进行验证,并得到相应的验证结果提示.
						<LI>
							合同号输入完成后可以输入短信提醒人的信息,输入全部完成后点击"保存"按钮。
					</OL>
				</td>
			</tr>
		</table>
</body>
