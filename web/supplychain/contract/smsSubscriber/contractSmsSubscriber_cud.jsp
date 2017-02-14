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
							  // window.alert("������ĺ�ͬ����û�ж�Ӧ��Ӧ�̣�");
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
			  //ѡ���ͬ��
			function selectContractNo(){
			    var contractNo = document.forms[0].contractNo.value;
			   // dWinOrnaments = "status:no;scroll:auto;resizable:no;dialogWidth:550px;dialogHeight:550px";
			    //dWin = window.showModalDialog("/supplychain/contract/selectWorkAround.jsp?workAroundName="+workAroundName, window, dWinOrnaments);
			     var pars="width=500px,height=720px,toolbar=no,menubar=no,resizable=yes,location=no,status=no";
			    dWin= window.open("/supplychain/contract/selectContractNo.jsp?contractNo="+contractNo+"&contractTypeId=0","mywin",pars);  //��ѯ��ͬ��
			}
			//���ú�ͬ��
			function resetContractNo(no){
				document.forms[0].contractNo.value=no;
				updateInfo();
			}
			
			function checkContractNo(be_alert){
			    var contractNo = document.forms[0].contractNo.value;
			    var flag=false;
			    if(contractNo == ""){
			        alert("������д��ͬ��");
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
					           		 alert("�ú�ͬ�Ŵ���,��֤ͨ��!");
					            }
								flag=true;
					        }else if(echo==0){
					        	alert("�ú�ͬ�Ų�����,��ȷ��!");
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
						if(window.confirm("���Ƿ�ɾ���ö���������!")){
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
<title>���ƺ�ͬ����������</title>
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
			����λ�ã�>�����ͬ����->���ƺ�ͬ���ڶ���������
		</p>
</center>
	<html:form
		action="/contract/smsSubscriber?method=add" target="_self">
						<table class="table_show" width="55%" cellspacing="1" 
							cellpadding="1" border="0" id="maintable" align="center">
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									��ͬ��
								</td>
								<td width="35%" height="25">
									<html:text   styleClass="clstext"  maxlength="20" property="contractNo" size="20" title="��ͬ��" onchange="updateInfo();"/>
									<img id="selectContractNoImg" src="<%=webapp%>/images/058.gif"
										style="cursor: hand;" alt="ѡ��" onclick="selectContractNo();">
									<img id="checkContractNoImg" src="<%=webapp%>/images/082.gif"
										style="cursor: hand;" alt="���" onclick="checkContractNo(true);">
								</td>
							</tr>
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									����������
								</td>
								<td width="35%" height="25">
									<html:text  styleClass="clstext" maxlength="20"
										property="smsSubscriberName" size="20" title="��������������"/>
									&nbsp;&nbsp;
								</td>
							</tr>
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									�������ֻ�����
								</td>
								<td width="35%" height="25">
									<html:text  styleClass="clstext" maxlength="20"
										property="smsSubscriberPhone" size="20"  title="�����������ֻ���"/>
									&nbsp;&nbsp;
								</td>
							</tr>
							<tr class="tr_show">
								<td align="right" height="15" colspan="2" >
								<div style="display: none;float: right" id="saveBtnDiv">
									<html:submit styleClass="clsbtn" onclick="return doCommand('add');">���������</html:submit><%--
									<input type="submit" onclick="return doCommand('add');" class="clsbtn" value="���������"/>
									--%><html:reset styleClass="clsbtn2">
										<bean:message key="label.reset" />
									</html:reset>
								</div>
								<div style="display: none;float: right" id="editBtnDiv">
									<html:submit styleClass="clsbtn" onclick="return doCommand('update');">����������</html:submit>
									<html:submit styleClass="clsbtn" onclick="return doCommand('delete');">ɾ��������</html:submit><%--
									<input type="submit" onclick="return doCommand('update');" class="clsbtn" value="����������"/>
									<input type="submit" onclick="return doCommand('delete');" class="clsbtn" value="ɾ��������"/>
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
					<b>��������:</b>
				</td>
			</tr>
			<tr>
				<td>
					<OL>
						<LI>
							���������˶��ƹ��ܵ�ǰ���ǣ�������ͬ�š�����������ȷ�ĺ�ͬ��.���ݺ�ͬ�������ƺ�ͬ�Ĺ���������.
						<LI>
							��ͬ�ſ����ֶ�����,��ͬ��������ɺ�ϵͳ�Զ�ƥ������������˵���Ϣ.���������������ϢΪ��:������������ˡ�������������˴��ڣ�����Ա༭��������Ϣ����ɾ��������.
						<LI>
							���<img src="<%=webapp%>/images/058.gif"	style="cursor: hand;" alt="ѡ��">�򿪺�ͬ�ŵ�ѡ���ҳ��,ѡ�����ͬ�ŷ���.
						<LI>
							���<img id="checkNet" src="<%=webapp%>/images/082.gif" style="cursor: hand;">���ԶԺ�ͬ���Ƿ���ڽ�����֤,���õ���Ӧ����֤�����ʾ.
						<LI>
							��ͬ��������ɺ����������������˵���Ϣ,����ȫ����ɺ���"����"��ť��
					</OL>
				</td>
			</tr>
		</table>
</body>
