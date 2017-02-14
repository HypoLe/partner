<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>    
<%@page import="com.boco.supplychain.util.StaticVariable" %>
 
<%
String webapp = request.getContextPath();
String extend_fields_id=(String)request.getAttribute("extend_fields_id");
%>
 
	  
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/calendar2.js"></script>
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/prototype.js"></script>
		<script type="text/javascript" src="../js/dojo/dojo.js"></script>
		<script type="text/javascript" src="../js/dojo/src/io.js"></script>
		<script type="text/javascript" src="<%=webapp%>/js/form/checkform.js"></script>
		 
		<title>维护合同模版更新</title>
		
		
		<script type="text/javascript">
			//add by Lin Wenwang
			function toggleCheckBoxs(formElement,checked){//整体改变checkbox组的选择 /checked传入true/false标识选中还是不选中
				for(var i=0;i<formElement.length;i++){  //formElement表示form上的一个元素,像form.baseinfo_field标识form中name为baseinfo_filed的checkbox集合
					formElement[i].checked=checked;
				}
			}
			function init(){
				toggleCheckBoxs(document.forms[0].base_field_id,true);
				changeSomeCheckBox(document.forms[0].extend_field_id);
				updateExtendField(document.forms[0].contractType.value);
			}

			function changeSomeCheckBox(formElement){
				var fields_str='<%=extend_fields_id%>';
				var fields_arr=fields_str.split('|');
				for(var k=0;k<fields_arr.length;k++){
					for(var i=0;i<formElement.length;i++){  //formElement表示form上的一个元素,像form.baseinfo_field,用来标识form中name为baseinfo_filed的checkbox集合
						if(fields_arr[k]==formElement[i].value){
							formElement[i].checked=true;
							break;
						}
					}
				}
			}
			/**表单验证*/
			function checkForm(){
				var modelName = document.forms[0].modelName;
				if(checkLength(modelName,1,15)){	
					return true;
				}
				else{
					return false;
				}
			}
			/*
			*业务逻辑(无限专业的物业合同有扩展字段定制.)
			*/
			function updateExtendField(contractTypeId){
				if(contractTypeId==<%=StaticVariable.CONTRACT_TYPE_PROPERTY%>){
					$("cityTr").show();//物业合同按县市区分
					$("serviceContractFieldsTable").hide();//设置标准字段的表格不可见
					$("propertyContractFieldsTable").show();
					if(document.getElementsByName("specialtyId")[0].value==<%=StaticVariable.AVAILABLE_SPECIALTY[0]%>){  //无线专业物业合同有扩展字段.
						dojo.byId("extends_fields").style.display="";
						return;
					}
				}else{
					$("cityTr").hide();
					$("serviceContractFieldsTable").show();
					$("propertyContractFieldsTable").hide();
				}
				dojo.byId("extends_fields").style.display="none";
			}
			dojo.addOnLoad(init);
		</script>
 
		 

		<!------------------标识页面位置------------------>
		<p align="left">
			${eoms:a2u('更新合同模板')}
		</p>

		<!---------------------------------------维护合同模板定制--------------------------------------->
		<html:form  method="post" action="/contractModel/edit.do" onsubmit="return checkForm();">
			<table width="70%" height="50%" border="0" cellspacing="1"
				cellpadding="1" align="center">
				<!------------------维护合同模板基本字段定制------------------>
				<tr height="30%">
					<td width="50%" height="100%">
						<fieldset align="center" style="height: 100%">
							<legend>
								${eoms:a2u('合同模板基本信息')}
							</legend>
							<table width="100%" cellspacing="1" cellpadding="1" border="0"
								align="center">
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('合同模板名称')}
									</td>
									<td width="60%" height="25">
										<html:text property="modelName" styleClass="clstext" title="${eoms:a2u('合同模板名称')}" maxlength="15"/>
										<html:hidden property="modelId"/>
									</td>
								<tr>
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('专业')}
										<%--<html:hidden  property="specialtyId"/>
									--%></td>
									<td width="60%" height="25"><%--
										<bean:write name="contractModelForm" property="specialtyName"/>
									--%></td>
								<tr>
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('代维类型')}
										<%--<html:hidden  property="serviceTypeId"/>
									--%></td>
									<td width="60%" height="25">
										<%--<bean:write name="contractModelForm" property="serviceTypeName"/>
									--%></td>
								<tr>
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('地市信息')}
										<html:hidden  property="regionId"/>
									</td>
									<td width="60%" height="25"><%--
										<bean:write name="contractModelForm" property="regionName"/>
								--%>	</td>
								<tr>
								<tr class="tr_show" id="cityTr">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('县市信息')}
										<html:hidden  property="cityId"/>
									</td>
									<td width="60%" height="25"><%--
										<bean:write name="contractModelForm" property="cityName"/>
								--%>	</td>
								<tr>
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('合同类型')}
										<html:hidden  property="contractType"/>
									</td>
									<td width="60%" height="25">
									
									<%--<bean:write name="contractModelForm" property="contractTypeName"/>
									
										<html:select property="contractType" style="width:4cm"
											styleClass="clstext"
											onchange="updateExtendField(this.value);" >
											<html:options collection="contractTypeOptions"
												property="value" labelProperty="label" />
										</html:select>
									--%></td>
								<tr>
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('创建时间')}
									</td>
									<td width="60%" height="25"><%--
										<html:text property="strCreateDate" size="20"
											onfocus="calendar()" readonly="true" styleClass="clstext" />
									--%></td>
								<tr>
							</table>
						</fieldset>
					</td>
					<td align="center" height="100%">
						<fieldset style="height: 100%">
							<legend>
								${eoms:a2u('合同模板基本字段')}
							</legend>
								<table id="serviceContractFieldsTable" width="100%" ellspacing="1" cellpadding="1" border="0"
								align="center" >
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同编号')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同名称')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同创建日期')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同签订日期')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同生效日期')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同结束日期')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('甲方')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('乙方(代维公司)')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('甲方经办人')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('乙方经办人')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('甲方经办人联系方式')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('乙方经办人联系方式')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同预算')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('乙方开户行')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">

									</td>
									<td width="50%" align="left" height="25" >
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('乙方收款银行帐号')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('内容描述')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('备注')}
									</td>
								</tr>
							</table>
							<table id="propertyContractFieldsTable" width="100%" ellspacing="1" cellpadding="1" border="0"
								align="center" >
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同编号')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同名称')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同创建日期')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同签订日期')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同生效日期')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同结束日期')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('甲方')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('乙方')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('合同预算')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('乙方开户行')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('基站地址')}
									</td>
									<td width="50%" align="left" height="25" >
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('乙方收款银行帐号')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">

									</td>
									<td width="50%" align="left" height="25" >
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('乙方传真号')}
									</td>
								</tr>
								<tr class="tr_show">
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('内容描述')}
									</td>
									<td width="50%" align="left" height="25">
										<input type="checkbox" name="base_field_id" checked="checked" disabled="disabled"/>${eoms:a2u('备注')}
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>	
				<tr height="20%" >
					<td width="50%" height="100%">
										<fieldset style="height: 100%">
											<legend>
												${eoms:a2u('合同扩展字段')}
											</legend><%--
										  <table id="extends_fields" width="100%" cellspacing="1" cellpadding="1"
												border="0" align="center">
												<tr class="tr_show">
													<td width="50%" align="left" height="25">
														<html:checkbox property="extend_field_id" value="1">${eoms:a2u('联系人')}</html:checkbox>
													</td>
													<td width="50%" align="left" height="25">
														<html:checkbox property="extend_field_id" value="2">${eoms:a2u('联系方式')}</html:checkbox>
													</td>
												<tr>
												<tr class="tr_show">
													<td width="50%" align="left" height="25">
														<html:checkbox property="extend_field_id" value="3">${eoms:a2u('对方协议单位')}</html:checkbox>
													</td>
													<td width="50%" align="left" height="25">
														<html:checkbox property="extend_field_id" value="4">${eoms:a2u('我方签署人')}</html:checkbox>
													</td>
												<tr>
												<tr class="tr_show">
													<td width="50%" align="left" height="25">
														<html:checkbox property="extend_field_id" value="5">${eoms:a2u('审批人')}</html:checkbox>
													</td>
													<td width="50%" align="left" height="25">
														<html:checkbox property="extend_field_id" value="7">${eoms:a2u('合同期限')}</html:checkbox>
													</td>
												<tr>
											</table> 
										--%></fieldset>
									
								</td>
								<td height="100%">
										<fieldset style="height: 100%">
											<legend>
												${eoms:a2u('合同费用清单选择')}
											</legend>
											<table width="100%" ellspacing="1" cellpadding="1" border="0"
												align="center">
												<tr class="tr_show">
													<td width="50%" align="left" height="25">
			
													</td>
													<td width="50%" align="left" height="25">
												
													</td>
												<tr>
												<tr class="tr_show">
													<td width="50%" align="left" height="25">
													
													</td>
													<td width="50%" align="left" height="25">
													
													</td>
												<tr>
												<tr class="tr_show">
													<td width="50%" align="left" height="25">
												
													</td>
													<td width="50%" align="left" height="25">
														
													</td>
												<tr>
												<tr class="tr_show">
													<td width="50%" align="left" height="25">
												
													</td>
													<td width="50%" align="left" height="25">
														
													</td>

												<tr>
											</table>
										</fieldset>
								</td>
				</tr>
				<!------------------维护合同模板其它定制选项------------------>
				<tr height="10%" class="tr_show">
					<td colspan="2" align="right">
						<html:submit styleClass="button" >
							 ${eoms:a2u('提交')}
						</html:submit>
						<html:reset styleClass="button">
							 ${eoms:a2u('重置')}
						</html:reset>
					</td>
				</tr>
			</table>
		</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
