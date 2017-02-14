<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>   
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%
	String webapp = request.getContextPath();
	String unSelect_fields_id = StaticMethod.nullObject2String(request
			.getAttribute("unSelect_fields_id"));
	System.out.println(unSelect_fields_id);
%>

 
		<title>${eoms:a2u('合同删除')}</title>

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
			src="<%=webapp%>/js/calendar2.js"></script>
		<script language="javascript"
			src="<%=webapp%>/js/xtree/xtree.js"></script>
		<script type="text/javascript" src="<%=webapp%>/js/form/checkform.js"></script><%--
		<script type="text/javascript" src="<%=webapp%>/js/supplychain/accessory.js"></script>
		--%><script type="text/javascript">
			/**页面加载后的初始化*/
			function init(){
				initFields();
				initDeleteField();
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
			//删除页面的表单样式调整.
			function initDeleteField(){
			 var items  = document.getElementsByTagName("INPUT")   
			  for   (i=0;i<items.length;i++){   
			 	if(items[i].type=="text"){   
			       // items[i].disabled=true;  
			       items[i].onfocus=function(){
			       	document.getElementById("backBtn").focus();
			     //  	this.blur();
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
				if(window.confirm("${eoms:a2u('您真的要删除合同吗?')}")){
					document.forms[0].submit();
					return true;
				}else{
					return false;
				}
			}
		</script>


	 

	<body onload="init();">
	
	<!--  
		<p align="left">
			${eoms:a2u('服务合同管理')}
			logic:equal value="delete" name="view_type"> 
			${eoms:a2u('(删除合同)')}
			/logic:equal>
			logic:equal value="view" name="view_type"> 
			${eoms:a2u('(查看合同)')}
			/logic:equal>
			->
			bean:write name="specialtyName" />
			->
			bean:write name="contractModelName" />
		</p>
	-->	
		<html:form action="/contract/delete" method="post" target="_parent">
			<P align="center" class="table_title"
				style="margin-bottom: -1em;margin-top: 1em">
				<b>${eoms:a2u('合同基本信息')}</b>
			</P>
			<table class="table_show" width="56%" cellspacing="1" cellpadding="1"
				border="0" id="maintable" align="center">
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同编号')}
						<html:hidden property="contractId"/>
						<html:hidden property="contractType"/>
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10"
							property="contractNo" size="20" title="${eoms:a2u('合同编号')}" />
					</td>

					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同名称')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="contractName" size="20" title="${eoms:a2u('合同名称')}"/>	
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同创建日期')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext"
							property="strCreateDate" onfocus="calendar();" title="${eoms:a2u('经办日期')}"/>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同签订日期')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strSubscribeDate" onfocus="calendar();" size="20" title="${eoms:a2u('签订日期')}" />
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同生效日期')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strEffactDate" onfocus="calendar();" size="20" title="${eoms:a2u('合同生效日期')}" />
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同结束日期')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strEndDate" onfocus="calendar();" title="${eoms:a2u('结束日期')}"/>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('甲方')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="partA"
							size="20" title="${eoms:a2u('合同甲方')}"/>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('乙方(代维公司)')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="companyName"
							size="20"  title="${eoms:a2u('合同乙方')}"/></td>
				</tr>	
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('甲方经办人')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10" property="partAProxyer" title="${eoms:a2u('合同甲方经办人')}"/>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('乙方经办人')}
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10" property="partBProxyer" title="${eoms:a2u('合同乙方经办人')"/>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('甲方经办人联系方式')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="partAProxyerPhone" title="${eoms:a2u('合同甲方经办人联系方式')"/>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('乙方经办人联系方式')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="partBProxyerPhone" title="${eoms:a2u('合同乙方经办人联系方式')"/>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同预算')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="14"
							property="yearHeadBudget" title="${eoms:a2u('合同预算')"/>
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('乙方开户行')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="partBBank" title="${eoms:a2u('开户行')"/>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">

					</td>
					<td width="35%" height="25">
					
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('乙方收款银行帐号')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="partBAccount" title="${eoms:a2u('收款银行帐号')"/>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('内容描述')
					</td>
					<td width="35%" height="25">
						<html:textarea property="contractContent"  cols="30" rows="3" title="内容描述">
						</html:textarea>
					</td>
					<td width="15%" height="25" class="clsfth" >
						${eoms:a2u('备注')
					</td>
					<td width="35%" height="25">
						<html:textarea property="remark" cols="30" rows="3" title="${eoms:a2u('备注')">
						</html:textarea>
					</td>
				</tr>

				<!-- 下面开始扩展字段 -->
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('联系人')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contactMan" title="${eoms:a2u('联系人')"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('联系方式')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contactPhone" title="${eoms:a2u('联系方式')" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('我方签署人')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partASigner" title="${eoms:a2u('我方签署人')"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('对方协议单位')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partBCompany" title="${eoms:a2u('对方协议单位')"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('审批人')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="taster"  title="${eoms:a2u('审批人')"/>
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('合同期限')
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contractTerm" title="${eoms:a2u('合同期限')"/>
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						${eoms:a2u('附件下载')
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
													${eoms:a2u('下载附件') </a>
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
							${eoms:a2u('删除')
						</html:submit>
					</logic:equal>
						<input type="button" id="backBtn" value="${eoms:a2u('取消')"
							onclick="history.back()" class="clsbtn2" />
					</td>
				</tr>
			</table>
		</html:form>

<%@ include file="/common/footer_eoms_innerpage.jsp"%>	 
