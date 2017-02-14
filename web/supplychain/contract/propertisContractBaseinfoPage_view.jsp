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
		<title>��ͬ��Ϣ�鿴</title>
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
			
			//ɾ��ҳ��ı���ʽ����.
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
			//ɾ����ͬ
			function confirmDel(){
				if(window.confirm("�����Ҫɾ������ҵ��ͬ��?")){
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
			����λ��->�����ͬ����(�鿴)->
			<bean:write name="specialtyName" />
			->
			<bean:write name="contractModelName" />
		</p>
		--%><html:form action="/contract/delete" method="post" target="_parent">
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
						<html:hidden property="contractType"/>
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="10"
							property="contractNo" size="20" title="��ͬ���" readonly="true"/>
					</td>

					<td width="15%" height="25" class="clsfth">
						��ͬ����
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="contractName" size="20" title="��ͬ����"/>
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
					</td>
					<td width="15%" height="25" class="clsfth">
						��ͬǩ������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strSubscribeDate" onfocus="calendar();" size="20" title="ǩ������" />
						&nbsp;&nbsp;
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
					</td>
					<td width="15%" height="25" class="clsfth">
						��ͬ��������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" 
							property="strEndDate" onfocus="calendar();" title="��������"/>
						&nbsp;&nbsp;
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
					</td>
					<td width="15%" height="25" class="clsfth">
						�ҷ�
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="100" property="partB"
							size="20"  title="��ͬ�ҷ�"/>
						&nbsp;&nbsp;
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
					</td>
					<td width="15%" height="25" class="clsfth">
						�ҷ�������
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="50"
							property="partBBank" title="������"/>
						&nbsp;&nbsp;
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
						<html:text styleClass="clstext" property="workAroundNames" maxlength="64"></html:text>
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
					<td align="right" height="25" colspan="4">
					<!-- ��ҳ��Ϊɾ��ҳ��ʱ��ʾɾ����ť,�鿴��ť����ʾɾ����ť. -->
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
