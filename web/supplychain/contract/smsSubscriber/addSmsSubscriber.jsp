<%@ page language="java" pageEncoding="GB2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���ú�ͬ��������</title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/table_style.css"
			type="text/css">
		<style type="text/css">
		.clstext_gray {
			border: 1px solid #174EA8;
			background-color: #AEAEAE;
		}
		</style>
	</head>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/form/checkform.js"></script>
	<script type="text/javascript">
			function checkForm(){
				var flag=false;
				var smsSubscriberPhone=document.forms[0].smsSubscriberPhone;
				if( mobileCheck(smsSubscriberPhone)){
					flag= true;
				}else{
					flag= false;
				}
				return flag;
			}
		</script>
	<body>
		<center>
			<p align="left">
				����λ�ã�>�����ͬ����->���ú�ͬ��������
			</p>
		</center>
		<html:form action="/sms/updateSms" onsubmit="return checkForm();">
			<table class="table_show" width="55%" cellspacing="1" cellpadding="1"
				border="0" id="maintable" align="center">
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						�û�ID
					</td>
					<td width="35%" height="25" colspan="2">
						<html:text styleClass="clstext" maxlength="20"
							property="smsSubscriberUser" size="20" readonly="true" />
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						�������ֻ�����
					</td>
					<td width="35%" height="25" colspan="2">
						<html:text styleClass="clstext" maxlength="20"
							property="smsSubscriberPhone" size="20" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="35%" height="25">
						<html:checkbox property="overTime">��ͬ��������</html:checkbox>
					</td>
					<td width="30%" height="25">
						<html:checkbox property="payTime">��������</html:checkbox>
					</td>
					<td width="35%" height="25">
						<html:checkbox property="overWarrant">֤����������</html:checkbox>
					</td>
				</tr>
				<tr class="tr_show">
					<td align="center" height="15" colspan="3">
						<html:submit styleClass="clsbtn2">
							<bean:message key="label.save" />
						</html:submit>
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
							����ʵ�����ֻ����롣
					</OL>
				</td>
			</tr>
		</table>
	</body>
</html>
