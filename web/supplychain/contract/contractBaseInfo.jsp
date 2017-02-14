<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>    
<%@page import="com.boco.supplychain.util.StaticMethod"%>  
<%
	String webapp = request.getContextPath();
	String unSelect_fields_id = StaticMethod.nullObject2String(request
			.getAttribute("unSelect_fields_id"));
	System.out.println(unSelect_fields_id);
%>

 
	 

		 

		<style type="text/css">
		.clstext_gray {
			border: 1px solid #174EA8;
			background-color: #AEAEAE;
		}
		</style>
		<script language="javascript"
			src="<%=webapp%>/js/calendar2.js"></script>
		<script language="javascript"
			src="<%=webapp%>/js/xtree/xtree.js"></script>
		<script type="text/javascript" src="<%=webapp%>/js/form/checkform.js"></script>
		
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
		<%--
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
			
			function checkContractNo(be_alert)
			{
			    var contractNo = document.forms[0].contractNo.value;
			    var flag=false;
			    if(contractNo == ""){
			        alert("请先填写合同号");
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
					            alert("该合同号已经存在,请确认!");
					            document.forms[0].contractNo.value = "";
					   			document.forms[0].contractNo.focus();
								flag=false;
					        }else if(echo==0){
					        	 if(be_alert==true){
					        		alert("该合同号没有被使用,验证通过!");
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
		

 

	 
		<p align="left">
			所在位置->服务合同管理->
			<bean:write name="specialtyName" />
			->
			<bean:write name="contractModelName" />
		</p>
		<html:form action="/contract/save" method="post"
			enctype="multipart/form-data" onsubmit="return checkForm();">
			<P align="center" class="table_title"
				style="margin-bottom: -1em;margin-top: 1em">
				<b>合同基本信息</b>
			</P>
			<table class="table_show" width="56%" cellspacing="1" cellpadding="1"
				border="0" id="maintable" align="center">
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						合同编号
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10"
							property="contractNo" size="20" title="合同编号" />
						<img id="checkNo" src="<%=webapp%>/images/082.gif"
								style="cursor: hand;" alt="检查" onclick="checkContractNo(true);">
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>

					<td width="15%" height="25" class="clsfth">
						合同名称
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="contractName" size="20" title="合同名称"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						合同创建日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext"
							property="strCreateDate" onfocus="calendar();" title="创建日期"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						合同签订日期
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
						合同生效日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strEffactDate" onfocus="calendar();" size="20" title="合同生效日期" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						合同结束日期
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
						甲方
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="partA"
							size="20" title="合同甲方"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						乙方(代维公司)
					</td>
					<td width="35%" height="25">
						<html:select property="companyId" style="width:4cm"
							>
							<html:options collection="companyOptions" property="value"
								labelProperty="label" />
						</html:select>
					
						<%--<html:text styleClass="clstext" maxlength="100" property="partB"
							size="20"  title="合同乙方"/>
					--%></td>
				</tr>	
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						甲方经办人
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10" property="partAProxyer" title="合同甲方经办人"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						乙方经办人
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10" property="partBProxyer" title="合同乙方经办人"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						甲方经办人联系方式
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="partAProxyerPhone" title="合同甲方经办人联系方式"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						乙方经办人联系方式
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="partBProxyerPhone" title="合同乙方经办人联系方式"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						合同预算
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="14"
							property="yearHeadBudget" title="合同预算"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						乙方开户行
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
						乙方收款银行帐号
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
						内容描述
					</td>
					<td width="35%" height="25">
						<html:textarea property="contractContent"  cols="30" rows="3" title="内容描述">
						</html:textarea>
					</td>
					<td width="15%" height="25" class="clsfth" >
					    备注
					</td>						
					<td width="35%" height="25">
						<html:textarea property="remark" cols="30" rows="3" title="备注">
						</html:textarea>
					</td>
				</tr>

				<!-- 下面开始扩展字段 -->
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						联系人
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contactMan" title="联系人"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						联系方式
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contactPhone" title="联系方式" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						我方签署人
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partASigner" title="我方签署人"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						对方协议单位
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partBCompany" title="对方协议单位"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						审批人
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="taster"  title="审批人"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						合同期限
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contractTerm" title="合同期限"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						合同附件
					</td>
					<td width="35%" height="25" colspan="3">
						<table id="accessoryTable" class="table_show" width="100%"	cellspacing="1" cellpadding="1" border="0" align="center">
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
						<input type="button" value="添加附件" class="clsbtn2" onclick="addAccessory()">
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
							有<span class="requisite">**</span>标志的输入框信息不能为空.
						<LI>
							合同号可以手动输入,提交时做合同号是否重复的验证.
						<LI>
							点击<img id="checkNet" src="<%=webapp%>/images/082.gif" style="cursor: hand;">可以对合同号是否已经存在进行验证,并得到相应的验证结果提示.
						<LI>
							合同甲乙两方信息输入框的标签一般都有用"甲方***","乙方***"来标识,公共的信息没有标识.一般甲方信息输入框都放在左方，乙方信息输入框放右边.
					</OL>
				</td>
			</tr>
		</table>
 <%@ include file="/common/footer_eoms_innerpage.jsp"%>	 
