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
			  //ѡ����Ԫ
			function selectContractNo(){
			    var contractNo = document.forms[0].contractNo.value;
			   // dWinOrnaments = "status:no;scroll:auto;resizable:no;dialogWidth:550px;dialogHeight:550px";
			    //dWin = window.showModalDialog("/supplychain/contract/selectWorkAround.jsp?workAroundName="+workAroundName, window, dWinOrnaments);
			     var pars="width=500px,height=720px,toolbar=no,menubar=no,resizable=yes,location=no,status=no";
			    dWin= window.open("/supplychain/contract/selectContractNo.jsp?contractNo="+contractNo,"mywin",pars);
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
						url:"<%=webapp%>/contract/checkContractNo.do?contractNo="+contractNo+"&contractTypeId=<%=StaticVariable.CONTRACT_TYPE_SERVICE%>",
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
			function checkForm(){
				var flag=false;
				var contractNo = document.forms[0].contractNo;
				var partB=document.forms[0].partB;
				var strDeductDate=document.forms[0].strDeductDate;
				var deductMoney=document.forms[0].deductMoney;
				var ratioFlag=document.forms[0].payMethodId.value;
				if(ratioFlag=1){		
					if(checkContractNo(false) &&��checkNotNull(partB,"��ѡ��һ����ȷ�ĺ�ͬ�ţ���Ӧ�������Զ�����!") && checkLength(partB,1,100) 
					&& checkNotNull(deductMoney) && numCheck(deductMoney,'�ۿ������Ϊ���֣�') && checkLength(deductMoney,1,12) && checkNotNull(strDeductDate)){
						flag= true;
					}else{
						flag= false;
					}
				}else if(ratioFlag=2){
					if(checkContractNo(false) &&��checkNotNull(partB,"��ѡ��һ����ȷ�ĺ�ͬ�ţ���Ӧ�������Զ�����!") && checkLength(partB,1,100) 
				 	  && checkNotNull(strDeductDate)){
						flag= true;
					}else{
						flag= false;
					}
				}
				return flag;
			}
		</script>
<title>��Ӻ�ͬ����������</title>
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
			����λ�ã�>�����ͬ����->������ͬ���ڶ���������
		</p>
</center>
		<html:form
			action="/contract/contractSmsSubscriber?method=add" onsubmit="return checkForm();">
						<table class="table_show" width="55%" cellspacing="1" 
							cellpadding="1" border="0" id="maintable" align="center">
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									��ͬ��
								</td>
								<td width="35%" height="25">
									<html:text   styleClass="clstext"  maxlength="20" property="contractNo" size="20" title="��ͬ��" />
									<img id="selectContractNo" src="<%=webapp%>/images/058.gif"
										style="cursor: hand;" alt="ѡ��" onclick="selectContractNo();">
									<img id="checkNet" src="<%=webapp%>/images/082.gif"
										style="cursor: hand;" alt="���" onclick="checkContractNo(true);">
								</td>
							</tr>
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									����������
								</td>
								<td width="35%" height="25">
									<html:text  styleClass="clstext" maxlength="20"
										property="smsSubscriberName" size="20" />
									&nbsp;&nbsp;
								</td>
							</tr>
							<tr class="tr_show">
								<td width="15%" height="25" class="clsfth">
									�������ֻ�����
								</td>
								<td width="35%" height="25">
									<html:text  styleClass="clstext" maxlength="20"
										property="smsSubscriberPhone" size="20" />
									&nbsp;&nbsp;
								</td>
							</tr>
							<tr class="tr_show">
								<td align="right" height="15" colspan="2" >
								<html:submit  styleClass="clsbtn2">
									<bean:message key="label.save" />
								</html:submit>
								<html:reset styleClass="clsbtn2">
									<bean:message key="label.reset" />
								</html:reset>
							    <input type="button" class="clsbtn2" value="����" onclick="history.back();"/>
								</td>
							</tr>
						</table>
	</html:form>
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
							��ͬ�ſ����ֶ�����,�ύʱ����ͬ���Ƿ���ڵ���֤.
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
