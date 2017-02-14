<%@ page language="java" import="java.util.*" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>     
<%
String webapp = request.getContextPath();
%>
 
  
		<html:form method="post" action="/contract/save" onsubmit="return check();">
			<table border="0" width="100%" cellspacing="0" align="center">
				<tr>
					<td width="100%" class="table_title" align="center">
						<b> <bean:message key="label.add" />合同</b>
					</td>
				</tr>
			</table>
			<table class="table_show" width="100%" cellspacing="1"
				cellpadding="1" border="0" id="maintable" align="center">
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						合同编号
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contractNo" size="20" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						合同名称
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contractName" size="20" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						承办单位
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="partA"
							size="20" />
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						合同乙方
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="partB"
							size="20" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						签订日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="subscribeDate" onfocus="calendar();" size="20" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						结束日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="endDate"
							onfocus="calendar();" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						经办人
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="creator" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						经办日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="createDate" onfocus="calendar();" />
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						内容描述
					</td>
					<td width="35%" height="25">
						<textarea cols="110" rows="5" name="contractContent" type="_moz" />
					
					</td>
					<td width="15%" height="25" class="clsfth">
						备注
					</td>
					<td width="35%" height="25">
						<textarea cols="110" rows="5" name="backup" type="_moz" />
					
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						联系人
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contactMan" />
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						联系方式
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contactPhone" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						对方协议单位
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partBCompany" />
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						我方签署人
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="partASigner" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						审批人
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="taster" />
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						生效日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="effactDate" onfocus="calendar();" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						合同期限
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="contractTerm" />
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						单价
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="price" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						付款日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="payDate" />
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						支付起始日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="payStartDate" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						支付结束日期
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="payEndDate" />
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						消费数
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20"
							property="consumeQuantity" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						付款金额数
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="payMoney" />
						&nbsp;&nbsp;
					</td>
					<td width="15%" height="25" class="clsfth">
						付款经办人
					</td>
					<td width="35%" height="25">
						<html:text styleClass="clstext" maxlength="20" property="opertor" />
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
			<p>
			<table width="100%" cellspacing="1" cellpadding="1" border="0"
				align="center">
				<tr>
					<td align="right">
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
	<table border="0" width="100%" cellspacing="0" cellpadding="0"
		align="center">
		<tr>
			<td>
				<b><bean:message key="label.tip" />
				</b>
			</td>
		</tr>
		<tr>
			<td>
				<ol>
					<li>
						标记(
						<span class="requisite">**</span>)的字段为必填字段;
					</li>
					<li>
						为了便于区分不同的合同名称,请不要输入重复的合同名称;
					</li>
				</ol>
			</td>
		</tr>
	</table>
<%@ include file="/common/footer_eoms_innerpage.jsp"%>
