<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>   
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%
	String webapp = request.getContextPath();
	String unSelect_fields_id = StaticMethod.nullObject2String(request
			.getAttribute("unSelect_fields_id"));
	System.out.println(unSelect_fields_id);
%>

 

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
			src="<%=request.getContextPath()%>/js/calendar2.js"></script>
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/xtree/xtree.js"></script>
		<script type="text/javascript" src="<%=webapp%>/js/form/checkform.js"></script><%--
		<script type="text/javascript" src="<%=webapp%>/js/supplychain/accessory.js"></script>
		--%><script type="text/javascript">
			/**页面加载后的初始化*/
			function init(){
			   initFields();
			 
			}
			/**初始化页面合同基本信息表单的状态  如果是模板包含的字段则可以读写,而模板不包含的字段灰色处理*/
			function initFields(){
				var fields_str='<%=unSelect_fields_id%>';
				var fields_arr=fields_str.split('|');
				for(var index=0;index<fields_arr.length;index++){
					document.getElementsByName(fields_arr[index])[0].disabled="true";
					document.getElementsByName(fields_arr[index])[0].className="clstext_gray";
				}
			}
			  //添加附件
			var index=1;
			function addAccessory(){
				var newTr = accessoryTable.insertRow(); 
				newTr.setAttribute("id","accessories"+index);
				newTr.setAttribute("className","tr_show");
				var newTd2 = newTr.insertCell();
				var newTd3 = newTr.insertCell();
				newTd3.setAttribute("className","clsfth");
				newTd2.innerHTML = "<input type='file' size='56'  name='file"+index+"' title='附件选择'/>&nbsp;&nbsp;";
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
				return (checkContractBaseinfo());	
			}
			function checkContractBaseinfo(){
				//基础信息验证.
				var contractNo=document.forms[0].contractNo;
				var contractName=document.forms[0].contractName;
				var partA=document.forms[0].partA;
			//	var partB=document.forms[0].partB;
				var strSubscribeDate=document.forms[0].strSubscribeDate;
				var strEndDate=document.forms[0].strEndDate;
				var strCreateDate=document.forms[0].strCreateDate;
				var strEffactDate=document.forms[0].strEffactDate;
				var partAProxyer=document.forms[0].partAProxyer;
				var partBProxyer=document.forms[0].partBProxyer;
				var partBProxyerPhone=document.forms[0].partBProxyerPhone;
				var partAProxyerPhone=document.forms[0].partAProxyerPhone;
				var partBBank=document.forms[0].partBBank;
			//	var partBGatheringCompany=document.forms[0].partBGatheringCompany;
				var partBAccount=document.forms[0].partBAccount;
				var yearHeadBudget=document.forms[0].yearHeadBudget;
				var contractContent=document.forms[0].contractContent;
				var remark=document.forms[0].remark;
				
				if(checkLength(contractNo,1,10) && 
				  checkLength(contractName,1,200) &&
				  checkLength(partA,1,200) &&
			//	  checkLength(partB,1,200) &&
				
				  checkNotNull(strEffactDate) &&
				  checkNotNull(strSubscribeDate) &&
				  checkNotNull(strEndDate) &&
				  checkNotNull(strCreateDate) &&
				  checkLength(partAProxyer,1,10) &&
				  checkLength(partBProxyer,1,10) &&
				  checkLength(partBProxyerPhone,1,20) &&
				  checkLength(partAProxyerPhone,1,20) &&
				  checkLength(partBBank,1,10) &&
			//	  checkLength(partBGatheringCompany,1,100) &&
				  checkLength(partBAccount,1,25) &&
				  checkNotNull(yearHeadBudget) && numCheck(yearHeadBudget,'合同预算必须为数值型!') &&
				  checkLength(contractContent,0,2000) &&
				  checkLength(remark,0,2000) &&
				  checkContractNo(false)){
				  	return true;
				  }else{
				  	return false;
				  }
			}
			
			
		</script>


 
	<body onload="init();">
		<p align="left">
			${eoms:a2u('服务合同管理(合同修改)')}
			<bean:write name="specialtyName" />
			->
			<bean:write name="contractModelName" />
		</p>
		<html:form action="/contract/update" method="post"
			enctype="multipart/form-data" onsubmit="return checkForm();">
			<P align="center" class="table_title"
				style="margin-bottom: -1em;margin-top: 1em">
				<b>${eoms:a2u('合同基本信息')}</b>
			</P>
			<table class="table_show" width="56%" cellspacing="1" cellpadding="1"
				border="0" id="maintable" align="center">
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同编号')}
					</td>
					<td width="35%" height="25">
						<html:hidden property="contractId"/>
						<html:text styleClass="clstext" maxlength="10"
							property="contractNo" size="20" title="${eoms:a2u('合同编号')}" readonly="true"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>

					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同名称')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="contractName" size="20" title="合同名称" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同创建日期')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext"
							property="strCreateDate" onfocus="calendar();" title="经办日期"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同签订日期')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strSubscribeDate" onfocus="calendar();" size="20" title="签订日期" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同生效日期')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strEffactDate" onfocus="calendar();" size="20" title="合同生效日期" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同结束日期')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strEndDate" onfocus="calendar();" title="结束日期"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('甲方')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="partA"
							size="20" title="合同甲方" readonly="true"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('乙方(代维公司)')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="companyName"
									size="20"  title="合同乙方" readonly="true"/>
					</td>
				</tr>	
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('甲方经办人')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10" property="partAProxyer" title="合同甲方经办人"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('乙方经办人')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10" property="partBProxyer" title="合同乙方经办人"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('甲方经办人联系方式')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="partAProxyerPhone" title="合同甲方经办人联系方式"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('乙方经办人联系方式')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="partBProxyerPhone" title="合同乙方经办人联系方式"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同预算')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="14"
							property="yearHeadBudget" title="合同预算"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('乙方开户行')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="partBBank" title="开户行"/>
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
						${eoms:a2u('乙方收款银行帐号')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="partBAccount" title="收款银行帐号"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('内容描述')}
					</td>
					<td width="35%" height="25">
						<html:textarea property="contractContent"  cols="30" rows="3" title="内容描述">
						</html:textarea>
					</td>
					<td width="15%" height="25" class="clsfth" >
						${eoms:a2u('备注')}
					</td>
					<td width="35%" height="25">
						<html:textarea property="remark" cols="30" rows="3" title="备注">
						</html:textarea>
					</td>
				</tr>

				<!-- 下面开始扩展字段 -->
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('联系人')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contactMan" title="联系人"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('联系方式')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contactPhone" title="联系方式" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('我方签署人')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partASigner" title="我方签署人"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('对方协议单位')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partBCompany" title="对方协议单位"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('审批人')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="taster"  title="审批人"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同期限')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contractTerm" title="合同期限"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('附件下载')}
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
													${eoms:a2u('下载附件')} </a>
											</td>
											<td>
												<a
													href='<%=webapp%>/common/deletefile.do?accessoriesId=<bean:write name="accessory" 
													property="accessoriesId"/>'  onclick="return confirm('${eoms:a2u('您好,附件删除完不能恢复，请确认!');">
													${eoms:a2u('删除附件')} </a>
											</td>
										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('附件上传')}
					</td>
					<td width="35%" height="25" colspan="3">
						<table id="accessoryTable"  width="100%"	cellspacing="1" cellpadding="1" border="0" align="center">
							<tr >
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
						<input id="testBtn" type="button" value="${eoms:a2u('添加附件')}" class="clsbtn2" onclick="addAccessory()">
						<html:submit styleClass="clsbtn2">
							${eoms:a2u('保存')}
						</html:submit>
						<html:reset styleClass="clsbtn2">
							${eoms:a2u('重置')}
						</html:reset>
						<input type="button" value="<bean:message key="label.cancel"/>"
							onclick="history.back()" class="clsbtn2" />
					</td>
				</tr>
			</table>
		</html:form>

<%@ include file="/common/footer_eoms_innerpage.jsp"%>	 
 
