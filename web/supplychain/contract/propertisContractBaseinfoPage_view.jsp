<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%
	String webapp = request.getContextPath();
	String unSelect_fields_id = StaticMethod.nullObject2String(request
			.getAttribute("unSelect_fields_id"));
	System.out.println(unSelect_fields_id);
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>合同信息查看</title>
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
		input.clstext{
			border:0px;
			background:transparent;
		}
		textarea{
			border:0px;
			background:transparent;
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
			    initDeleteField();
			}
			function initFields(){
			    var fields_str='<%=unSelect_fields_id%>';
			    var fields_arr=fields_str.split('|');
			    for(var index=0;index<fields_arr.length;index++)
			    {
			        document.getElementsByName(fields_arr[index])[0].disabled="true";
			        document.getElementsByName(fields_arr[index])[0].className="clstext_gray";
			    }
			}
			dojo.addOnLoad(init);
			
			//删除页面的表单样式调整.
			function initDeleteField(){
			 var items  = document.getElementsByTagName("INPUT")   
			  for   (i=0;i<items.length;i++){   
			 	if(items[i].type=="text"){   
			       // items[i].disabled=true;  
			       items[i].onfocus=function(){
			      	document.getElementById("backBtn").focus();
			      // 	this.blur();
			       }
			  	}   
			  }
			 var textarea_items  = document.getElementsByTagName("TEXTAREA");
			  for   (i=0;i<textarea_items.length;i++){   
			 	if(textarea_items[i].type=="textarea"){   
			       textarea_items[i].onfocus=function(){
			       	document.getElementById("backBtn").focus();
			       }
			  	}   
			  }
			}
			//删除合同
			function confirmDel(){
				if(window.confirm("您真的要删除该物业合同吗?")){
					document.forms[0].submit();
					return true;
				}else{
					return false;
				}
			}
		</script>
	</head>

	<body><%--
		<p align="left">
			所在位置->服务合同管理(查看)->
			<bean:write name="specialtyName" />
			->
			<bean:write name="contractModelName" />
		</p>
		--%><html:form action="/contract/delete" method="post" target="_parent">
			<P align="center" class="table_title"
				style="margin-bottom: -1em;margin-top: 1em">
				<b>合同基本信息</b>
			</P>
			<table class="table_show" width="56%" cellspacing="1" cellpadding="1"
				border="0" id="maintable" align="center">
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						合同编号
						<html:hidden property="contractId"/>
						<html:hidden property="contractType"/>
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10"
							property="contractNo" size="20" title="合同编号" readonly="true"/>
					</td>

					<td width="15%" height="25" class="clsfth">
						合同名称
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="contractName" size="20" title="合同名称"/>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						合同创建日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext"
							property="strCreateDate" onfocus="calendar();" title="经办日期"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						合同签订日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strSubscribeDate" onfocus="calendar();" size="20" title="签订日期" />
						&nbsp;&nbsp;
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
					</td>
					<td width="15%" height="25" class="clsfth">
						合同结束日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strEndDate" onfocus="calendar();" title="结束日期"/>
						&nbsp;&nbsp;
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
					</td>
					<td width="15%" height="25" class="clsfth">
						乙方
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="partB"
							size="20"  title="合同乙方"/>
						&nbsp;&nbsp;
					</td>
				</tr><%--	
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
				--%><tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						合同预算
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="14"
							property="yearHeadBudget" title="合同预算"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						乙方开户行
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="partBBank" title="开户行"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						基站地址
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="workAroundAddress"  title="基站地址"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						乙方收款银行帐号
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="partBAccount" title="收款银行帐号"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">

					</td>
					<td width="35%" height="25">
					
					</td>
					<td width="15%" height="25" class="clsfth">
						乙方传真号
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partBFax" title="乙方传真号"/>
						&nbsp;&nbsp;
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
						
					</td>
					<td width="35%" height="25">
						
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						工作区选择
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" property="workAroundNames" maxlength="64"></html:text>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						附件下载
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
													下载附件 </a>
											</td>
										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
					</td>
				</tr>
				<tr class="tr_show">
					<td align="right" height="25" colspan="4">
					<!-- 本页面为删除页面时显示删除按钮,查看按钮不显示删除按钮. -->
					<logic:equal value="deleteView" name="view_type"> 
						<html:submit styleClass="clsbtn2" onclick="return confirmDel();">
							<bean:message key="label.remove" />
						</html:submit>
					</logic:equal>
						<input type="button" id="backBtn" value="<bean:message key="label.cancel"/>"
							onclick="history.back()" class="clsbtn2" />
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
