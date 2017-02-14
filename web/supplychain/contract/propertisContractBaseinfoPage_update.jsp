<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.boco.supplychain.util.StaticMethod,com.boco.supplychain.contract.webapp.form.ContractForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%
	String webapp = request.getContextPath();
	String unSelect_fields_id = StaticMethod.nullObject2String(request
			.getAttribute("unSelect_fields_id"));
	ContractForm contractForm=(ContractForm)request.getAttribute("contractForm");
	String workAroundIds=contractForm.getWorkAroundIds();
	String workAroundNames=contractForm.getWorkAroundNames();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>��ͬ���</title>
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/xtree/xtree.css">
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/table_style.css"
			type="text/css">
		<style type="text/css">
		.clstext_gray {
			border: 1px solid #174EA8;
			background-color: #AEAEAE;
		}
		</style>
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/dojo/dojo.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dojo/src/io.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dojo/src/string.js"></script>
			<script type="text/javascript" src="<%=webapp%>/js/form/checkform.js"></script>
		<script type="text/javascript">
			djConfig={
				isDebug:false,
				bindEncoding:"utf-8"
			};
			
			dojo.require("dojo.io.BrowserIO");
		</script>
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/calendar2.js"></script>
		<script type="text/javascript">
			function init(){
			    initFields();
			}
			function initFields(){
			    var fields_str='<%=unSelect_fields_id%>';
			    var fields_arr=fields_str.split('|');
			    for(var index=0;index<fields_arr.length;index++)
			    {
			        document.getElementsByName(fields_arr[index])[0].disabled="true";
			        document.getElementsByName(fields_arr[index])[0].className="clstext_gray";
			    }
			    
			    //�����ʼ��������option
			    var workAroundIds='<%=workAroundIds%>';
			    var workAroundNames='<%=workAroundNames%>';
			    var workAroundIdArr=workAroundIds.split(",");
			    var workAroundNameArr=workAroundNames.split(",");
			    var select=document.forms[0].selectedWorkAround;
			    select.length=0;
			    for(var index=0;index<workAroundIdArr.length;index++){
			    	select[index]=new Option(workAroundNameArr[index],workAroundIdArr[index]);
			    }
			}
					//��Ӹ���
	
			var index=1;
			function addAccessory(){
				var newTr = accessoryTable.insertRow(); 
				newTr.setAttribute("id","accessories"+index);
				newTr.setAttribute("className","tr_show");
				var newTd2 = newTr.insertCell();
				var newTd3 = newTr.insertCell();
				newTd3.setAttribute("className","clsfth");
				newTd2.innerHTML = "<input type='file' size='56'  name='file"+index+"' title='����ѡ��'/>&nbsp;&nbsp;";
				newTd3.innerHTML="<img src='<%=request.getContextPath()%>/images/bottom/delete.gif' style='cursor: hand;' onclick=delRow('accessories"+index+"')>";
				index++;
			}
			function delRow(rowid){
				var e = document.getElementById(rowid);
				e.removeNode;
				if(null!=e) {
					e.parentNode.removeChild(e); 
				}
				index--;
			}
			function checkForm(){
				var workAroundIdArrStr="";
				var select=document.forms[0].selectedWorkAround;
				if(select.length<1){
					alert("����!��ҵ��ͬ����ѡ���һ��������!");
					select.focus();
					return false;
				}
				for(var index=0;index<select.length;index++){
					if(index==(select.length-1))
						workAroundIdArrStr=workAroundIdArrStr+select.options[index].value;
					else
						workAroundIdArrStr=workAroundIdArrStr+select.options[index].value+",";
				}
				
				document.forms[0].workAroundIds.value=workAroundIdArrStr;
				return (checkContractBaseinfo());	
			}
			function checkContractBaseinfo(){
				//������Ϣ��֤.
				var contractNo=document.forms[0].contractNo;
				var contractName=document.forms[0].contractName;
				var partA=document.forms[0].partA;
				var partB=document.forms[0].partB;
				var strSubscribeDate=document.forms[0].strSubscribeDate;
				var strEndDate=document.forms[0].strEndDate;
				var strCreateDate=document.forms[0].strCreateDate;
				var strEffactDate=document.forms[0].strEffactDate;
				var partBBank=document.forms[0].partBBank;
				var partBFax=document.forms[0].partBFax;
				//var partBGatheringCompany=document.forms[0].partBGatheringCompany;
				var partBAccount=document.forms[0].partBAccount;
				var yearHeadBudget=document.forms[0].yearHeadBudget;
			//	var contractContent=document.forms[0].contractContent;
			//	var remark=document.forms[0].remark;
				var select=document.forms[0].selectedWorkAround;
				var workAroundAddress=document.forms[0].workAroundAddress;
				if(checkLength(contractNo,1,10) && 
				  checkLength(contractName,1,200) &&
				  checkLength(partA,1,200) &&
				  checkLength(partB,1,200) &&
				  checkNotNull(strEffactDate) &&
				  checkNotNull(strSubscribeDate) &&
				  checkNotNull(strEndDate) &&
				  checkNotNull(strCreateDate) &&
				  checkLength(partBBank,1,10) &&
				  checkLength(partBFax,0,20) &&
				 // checkLength(partBGatheringCompany,1,100) &&
				  checkLength(partBAccount,1,25) &&
				  checkNotNull(yearHeadBudget) && numCheck(yearHeadBudget,'��ͬԤ�����Ϊ��ֵ��!') &&
				  checkLength(contractContent,0,2000) &&
				  checkLength(remark,0,2000) &&
				  checkLength(workAroundAddress,0,100) &&
				  checkContractNo(false) ){
				  	return false;
				  }else{
				  	return false;
				  }
			}
			  //ѡ����Ԫ
			function selectWorkAround2(){
			   // dWinOrnaments = "status:no;scroll:auto;resizable:no;dialogWidth:550px;dialogHeight:550px";
			    //dWin = window.showModalDialog("/supplychain/contract/selectWorkAround.jsp?workAroundName="+workAroundName, window, dWinOrnaments);
			     var pars="width=1024px,height=768px,toolbar=no,menubar=no,resizable=yes,location=no,status=no";
			    dWin= window.open("/supplychain/contract/selectWorkAround.jsp","mywin",pars);
			}
			function saveWorkAround(id,name){
				var select=document.forms[0].selectedWorkAround;
				for(var index=0;index<select.length;index++){
					if(select.options[index].value==id){
						return;
					}
				}
				var option=new Option(name,id);
				select.options[select.options.length]=option;
			}
			function deleteWorkAround(){
				var select=document.forms[0].selectedWorkAround;
				if(select.selectedIndex!=-1){
					select.options[select.selectedIndex]=null;
				}else{
					alert("��ѡ��һ������������ɾ��!");
					select.focus();
					
				}
			}
			function checkContractNo(be_alert)
			{
			    var contractNo = document.forms[0].contractNo.value;
			    var flag=false;
			    if(contractNo == ""){
			        alert("������д��ͬ��");
			        document.forms[0].contractNo.focus;
			        flag=false;
			    }
			    else{
			    	var request={
						url:"<%=webapp%>/contract/checkContractNo.do?contractNo="+contractNo,
						mimetype:"text/plain",
						method:"get",
						transport: "XMLHTTPTransport",
						sync:true,
						preventCache:true,
						encoding:"utf-8",
						load: function(type,data,evt){
							var echo=parseInt(data);
							if(echo >0){
					            alert("�ú�ͬ���Ѿ�����,��ȷ��!");
					            document.forms[0].contractNo.value = "";
					   			document.forms[0].contractNo.focus();
								flag=false;
					        }else if(echo==0){
					        	 if(be_alert==true){
					        		alert("�ú�ͬ��û�б�ʹ��,��֤ͨ��!");
					        	 }
					            flag=true;
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
			dojo.addOnLoad(init);
		</script>
	</head>

	<body>
		<p align="left">
			����λ��->�����ͬ����(����)->
			<bean:write name="specialtyName" />
			->
			<bean:write name="contractModelName" />
		</p>
		<html:form action="/contract/propertiesContractUpdate_BaseInfo" method="post"  enctype="multipart/form-data" 
				onsubmit="return checkForm();">
			<P align="center" class="table_title"
				style="margin-bottom: -1em;margin-top: 1em">
				<b>��ͬ������Ϣ</b>
			</P>
			<table class="table_show" width="56%" cellspacing="1" cellpadding="1"
				border="0" id="maintable" align="center">
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						��ͬ���
						<html:hidden property="contractId"/>
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10"
							property="contractNo" size="20" title="��ͬ���" readonly="true"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>

					<td width="15%" height="25" class="clsfth">
						��ͬ����
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="contractName" size="20" title="��ͬ����"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						��ͬ��������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext"
							property="strCreateDate" onfocus="calendar();" title="��������"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						��ͬǩ������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strSubscribeDate" onfocus="calendar();" size="20" title="ǩ������" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						��ͬ��Ч����
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strEffactDate" onfocus="calendar();" size="20" title="��ͬ��Ч����" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						��ͬ��������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strEndDate" onfocus="calendar();" title="��������"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						�׷�
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="partA"
							size="20" title="��ͬ�׷�"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						�ҷ�
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="partB"
							size="20"  title="��ͬ�ҷ�"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr><%--	
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						�׷�������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10" property="partAProxyer" title="��ͬ�׷�������"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						�ҷ�������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10" property="partBProxyer" title="��ͬ�ҷ�������"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						�׷���������ϵ��ʽ
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="partAProxyerPhone" title="��ͬ�׷���������ϵ��ʽ"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						�ҷ���������ϵ��ʽ
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="partBProxyerPhone" title="��ͬ�ҷ���������ϵ��ʽ"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				--%><tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						��ͬԤ��
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="14"
							property="yearHeadBudget" title="��ͬԤ��"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						�ҷ�������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="partBBank" title="������"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						��վ��ַ
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="workAroundAddress"  title="��վ��ַ"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						�ҷ��տ������ʺ�
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="partBAccount" title="�տ������ʺ�"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">

					</td>
					<td width="35%" height="25">
					
					</td>
					<td width="15%" height="25" class="clsfth">
						�ҷ������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partBFax" title="�ҷ������"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						��������
					</td>
					<td width="35%" height="25">
						<html:textarea property="contractContent"  cols="30" rows="3" title="��������">
						</html:textarea>
					</td>
					<td width="15%" height="25" class="clsfth" >
						��ע
					</td>
					<td width="35%" height="25">
						<html:textarea property="remark" cols="30" rows="3" title="��ע">
						</html:textarea>
					</td>
				</tr>

				<!-- ���濪ʼ��չ�ֶ� -->
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						��ϵ��
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contactMan" title="��ϵ��"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						��ϵ��ʽ
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contactPhone" title="��ϵ��ʽ" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						�ҷ�ǩ����
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partASigner" title="�ҷ�ǩ����"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						�Է�Э�鵥λ
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partBCompany" title="�Է�Э�鵥λ"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="taster"  title="������"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						��ͬ����
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contractTerm" title="��ͬ����"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
					</td>
					<td width="35%" height="25">
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						������ѡ��
					</td>
					<td width="35%" height="25">
						<select id="selectedWorkAround" style="width:4cm" size="2" multiple="multiple" >
							
						</select>
						<img id="selectWorkAroundImg" src="<%=webapp%>/images/058.gif"
							style="cursor: hand;" alt="ѡ��" onclick="selectWorkAround2();" width="15" height="15">
						<img id="deleteWorkAroundImg" src="<%=webapp%>/images/bottom/delete.gif"
							style="cursor: hand;" alt="ɾ��ѡ��Ĺ�����" onclick="deleteWorkAround();">
					
							<%--
						<img id="checkNet" src="<%=webapp%>/images/082.gif"
							style="cursor: hand;" alt="���" onclick="checkWorkAroundName(true);">
						--%>
						<html:hidden property="workAroundIds" />
						<html:hidden property="specialtyId"  />
						<html:hidden property="contractModelId"  />
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						��������
					</td>
					<td width="35%" height="25" colspan="3" >
							<table id="accessoryDownLoadTable" cellpadding="0" border="0"  width="100%">
								<logic:notEmpty name="accessoryList">
									<logic:iterate id="accessory"  name="accessoryList">
										<tr >
											<td>
												<a
													href='<%=webapp%>/common/smart_download.jsp?file=<bean:write name="accessory" 
													property="fileUrl"/>&saveAs=<bean:write name="accessory" property="fileName"/>'>
													<bean:write name="accessory" property="fileName" /> </a>
											</td>
											<td>
												<a
													href='<%=webapp%>/common/smart_download.jsp?file=<bean:write name="accessory" 
													property="fileUrl"/>&saveAs=<bean:write name="accessory" property="fileName"/>'>
													���ظ��� </a>
											</td>
										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						�����ϴ�
					</td>
					<td width="35%" height="25" colspan="3">
						<table id="accessoryTable"  width="100%"  border="0" align="center">
							<tr class="tr_show">
								<td width="80%" height="25">
									<input type="file" size="56" name="file0"   />
									<span id="masterdoor"></span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr class="tr_show">
					<td align="right" height="25" colspan="4">
						<input type="button" value="��Ӹ���" class="clsbtn2" onclick="addAccessory()">
						<html:submit styleClass="clsbtn2">
							<bean:message key="label.save" />
						</html:submit>
						<html:reset styleClass="clsbtn2">
							<bean:message key="label.reset" />
						</html:reset>
						<input type="button" value="<bean:message key="label.cancel"/>"
							onclick="history.back()" class="clsbtn2" />
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
