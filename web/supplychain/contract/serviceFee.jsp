<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.boco.supplychain.util.StaticMethod,com.boco.supplychain.util.StaticVariable" %>
<%
String webapp=request.getContextPath();
 %>
<html>
	<head>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/table_style.css"
			type="text/css">
		<style type="text/css">
		table	{ font-size: 12px;
		margin-top: 0em;
		margin-left: 0em;
		margin-right: 0em;
		margin-bottom: 2em;
		}
		</style>
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/dojo/dojo.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dojo/src/lang.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dojo/src/string.js"></script>
		<script type="text/javascript" src="<%=webapp%>/js/form/checkform.js"></script>
		<script type="text/javascript">
			String.prototype.startsWith=function(prefix){
			 return this.substring(0,prefix.length)==prefix;
			}
			/**计算单个设备类型的价格总和*/
			function calcSubSum(formItem){
				var formItemName=formItem.name;
				var deviceAmount=0;
				var price=0;
				var sum=0;
				var index=formItem.id.substring(formItem.id.length-1);;
				if(formItemName=="deviceAmount"){
					deviceAmount=getNumber(formItem.value);
					var priceId="price"+index;
					var sumId="sum"+index;
					price=getNumber(dojo.byId(priceId).value);
					sum=price*deviceAmount;
					dojo.byId(sumId).value=sum;
				}else if(formItemName=="price"){
					price=getNumber(formItem.value);
					var deviceAmountId="deviceAmount"+index;
					var sumId="sum"+index;
					deviceAmount=getNumber(dojo.byId(deviceAmountId).value);
					sum=price*deviceAmount;
					dojo.byId(sumId).value=sum;
				}
				calcSum();
			}
			function selectAllServiceFee(){
				for(var i=0;i<document.getElementsByName("deviceTypeId").length;i++){
					document.getElementsByName("deviceTypeId").checked=true;
				}
			}
			function getNumber(str){
				var value=0;
				var s=str+"";
				s=dojo.string.trim(s);
				if(s!="" && !isNaN(s))
					value=parseFloat(s);
				return value;
			}
			/**计算总额*/
			function calcSum(){
				if(typeof document.forms[0].sum=="undefined"){  //不存在name=='sum'的表单.元素
				
				}else{
					if(typeof document.forms[0].sum.length=="undefined"){  //只有一个name=='sum'的表单元素
						document.forms[0].contractMoney.value=document.forms[0].sum.value;
					}else{
						var contractMoney=0;
						for(var i=0;i<document.forms[0].sum.length;i++){
							var sum=getNumber(document.forms[0].sum[i].value);
							contractMoney+=sum;
						}
						document.forms[0].contractMoney.value=contractMoney;
					}
				}
			}
			function checkForm(){
				//var  deviceIds_arr=window.parent.getDeviceInSelect();
				
				//验证基站维护合同选择的工作区不能为空.
				
				if(document.forms[0].deviceIds.value.Trim()==""){
					alert("基站维护合同所维护的工作区不能为空,请先从左边工树图中选择地区，在选择对应工作区.");
					return false;
				}
				//验证价格不能为非数值型.
				if(typeof document.forms[0].price=="undefined"){  //不存在name=='price'的表单.元素
					alert("选择的工作区还未生成维护清单,请点击'生成维护清单'!");
					return false;
				}else{
					if(typeof document.forms[0].price.length=="undefined"){  //只有一个name=='price'的表单元素
						if(numCheck(document.forms[0].price,'请为设备维护单价输入数值型!')==false)
							return false;
					}else{
						for(var i=0;i<document.forms[0].price.length;i++){
							if(numCheck(document.forms[0].price[i],'请为设备维护单价输入数值型!')==false)
								return false;
						}
					}
				}
				//var deviceIds="";
			//	for(var i=0;i<deviceIds_arr.length;i++){  //维护设备ID.组合.
			//		deviceIds+=deviceIds_arr[i]+"_";
			//	}
			//	if(deviceIds!=""){
			//		deviceIds=deviceIds.substring(0,deviceIds.length-1);
			//	}
			//	document.forms[0].deviceIds.value=deviceIds;
				return true;
			}
			function init(){
				calcSum();
			}
			dojo.addOnLoad(init);
		</script>
	</head>
	<body>

		<CENTER>
			<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
				<b>服务对象维护清单
				</b>
			</P>
				<html:form action="/contract/serviceContractMoneySave" onsubmit="return checkForm();" target="_parent">
						<table width="100%">
							<tr>
								<td>
									合同金额(元)
								</td>
								<td colspan="5">
									<html:text property="contractMoney" styleClass="clstext"
										maxlength="20" readonly="true"></html:text>
								</td>
							</tr>
							<tr>
								<td class="clsfth">
									设备类型
								</td>
								<td class="clsfth">
									数量
								</td>
								<td class="clsfth">
									单价
								</td>
								<td class="clsfth">
									单位
								</td>
								<td class="clsfth">
									维护总价
								</td>
								<td class="clsfth">
									<input type="button" name="selectAllServiceFee();" value="全选" />
									<%--<input type="button" onclick="addServiceFee();" value="添加" />
									<input type="button" onclick="deleteServiceFee();" value="删除" />
								--%></td>
								</tr>
							<html:hidden property="contractId" />
							<html:hidden property="deviceIds" />
							<logic:notEmpty name="serviceFeeFormList">
								<%int index=1;%>
								<logic:iterate id="serviceFeeForm" name="serviceFeeFormList">
									<tr class="tr_show" id="deviceTypeRow<%=index%>">
										<td>
										<input type="text" name="deviceTypeName"
											 class="clstext"  value='<bean:write name="serviceFeeForm" property="deviceTypeName" />'/>			
										</td>
										<td>
											<input type="text" id="deviceAmount<%=index%>" name="deviceAmount" readonly="readonly"
											 class="clstext" onchange="calcSubSum(this);" value='<bean:write name="serviceFeeForm" property="deviceAmount" />'/>	
										</td>
										<td>
											<input type="text" id="price<%=index%>" onchange="calcSubSum(this);" name="price"
											 class="clstext" value='<bean:write name="serviceFeeForm" property="price" />'  title="维护单价"/>	
										</td>
										<td>
											<input type="text" name="unitName"  readonly="readonly"
											 class="clstext" value='<bean:write name="serviceFeeForm" property="unitName" />'/>	
											<input type="hidden" name="unitId"/>
										</td>
										<td>
											<input type="text" id="sum<%=index%>" onchange="calcSum();" name="sum" readonly="readonly" 
											 class="clstext" value='<bean:write name="serviceFeeForm" property="sum" />'/>											
										</td>
										<td>
											<input type="checkbox"  name="deviceTypeId" checked="checked" value='<bean:write name="serviceFeeForm" property="deviceTypeId"></bean:write>' />
										</td>
									</tr>
									<%index++;%>
								</logic:iterate>
							</logic:notEmpty>
							<logic:empty name="serviceFeeFormList">
								<tr class="tr_show">
									<td align="center" colspan="6">
										<span>费用清单未生成,请先选择服务对象!</span>
									</td>
								</tr>
							</logic:empty>
							<tr>
								<td colspan="6" align="center">
									<html:submit  styleClass="clsbtn2"  value="保存清单" />
									<html:reset styleClass="clsbtn2" value="重置" />
									<button class="clsbtn2" onclick="history.back();">
										返回
									</button>
								</td>
							</tr>
						</table>
					</html:form>
	</body>
</html>
