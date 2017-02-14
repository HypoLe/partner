<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>  
<%@page import="com.boco.supplychain.util.StaticVariable"%>
 <%
String webapp = request.getContextPath();
%>
  <script type="text/javascript">
			function toggleCheckBoxs(formElement,checked){//整体改变checkbox组的选择 /checked传入true/false标识选中还是不选中
				for(var i=0;i<formElement.length;i++){  //formElement表示form上的一个元素,像form.baseinfo_field标识form中name为baseinfo_filed的checkbox集合
					formElement[i].checked=checked;
				}
			}
			function init(){	
				toggleCheckBoxs(document.forms[0].base_field_id,true);
				updateInfo(document.getElementsByName("contractType")[0].value);
			}
			function updateInfo(specialtyId){
				if(specialtyId>0){
					var request={
						url:"<%=webapp%>/common/listServiceTypeBySpecialtyId.do?specialtyId="+specialtyId,
						mimetype:"text/plain",
						method:"get",
						transport: "XMLHTTPTransport",
						sync:false,
						preventCache:true,
						encoding:"utf-8",
						load: function(type,data,evt){
							var serviceTypeArr=eval(data);
							var select=document.getElementsByName("serviceTypeId")[0];
							var optionTop=new Option("--代维类型--");
							optionTop.value=0;
							select.options.add(optionTop);
							select.length=0;
							for(var i=0;i<serviceTypeArr.length;i++){
								var option=new Option();
								option.text=serviceTypeArr[i].serviceTypeName;
								option.value=serviceTypeArr[i].serviceTypeId;
								select.options[select.options.length]=option;
							}
						},
						error:function(type,error){
							alert(error.message);
						}
					};
					dojo.io.bind(request);
					
					//下面改变根据需求改变该专业下面的合同类型(无线专业有维护合同和物业合同,而网管专业只有维护合同)
					if(specialtyId==<%=StaticVariable.AVAILABLE_SPECIALTY[0]%>){ //无线专业
							var select=document.getElementsByName("contractType")[0];
							select.length=0;
							var option=new Option('<%=StaticVariable.CONTRACT_NAME_SERVICE%>');
							option.value=<%=StaticVariable.CONTRACT_TYPE_SERVICE%>;
							
							var option2=new Option('<%=StaticVariable.CONTRACT_NAME_PROPERTY%>');
							option2.value=<%=StaticVariable.CONTRACT_TYPE_PROPERTY%>;
							select.options[select.options.length]=option;
							select.options[select.options.length]=option2;
							select.selectedIndex=0;

					}else if(specialtyId==<%=StaticVariable.AVAILABLE_SPECIALTY[1]%>){  //网管专业(维护合同)
							var select=document.getElementsByName("contractType")[0];
							select.length=0;
							var option=new Option('<%=StaticVariable.CONTRACT_NAME_SERVICE%>');
							option.value=<%=StaticVariable.CONTRACT_TYPE_SERVICE%>;
							select.options[select.options.length]=option;
					}
					updateExtendField(document.getElementsByName("contractType")[0].value);  //专业变化显示调用合同类型变化函数.
				}
			}
			function updateCityInfo(regionId){
					var request={
						url:"<%=webapp%>/common/listCityByRegionId.do?regionId="+regionId,
						mimetype:"text/plain",
						method:"get",
						transport: "XMLHTTPTransport",
						sync:false,
						preventCache:true,
						encoding:"utf-8",
						load: function(type,data,evt){
							var cityArr=eval(data);	
							var select=document.getElementsByName("cityId")[0];
							select.options.length=0;
							var optionTop=new Option("--县市选择--");
							optionTop.value=0;
							select.options[select.options.length]=optionTop;
							for(var i=0;i<cityArr.length;i++){
								var option=new Option();
								option.text=cityArr[i].cityName;
								option.value=cityArr[i].cityId;
								select.options[select.options.length]=option;
							}
						},
						error:function(type,error){
							alert(error.message);
						}
					};
					dojo.io.bind(request);
			}
						
			/**表单验证*/
			function checkForm(){
				var modelName = document.forms[0].modelName;
				if(checkLength(modelName,1,100)){	
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
			//	alert($A("base_field_id").length);
				if(contractTypeId==<%=StaticVariable.CONTRACT_TYPE_PROPERTY%>){
					$("cityTr").show();//物业合同按县市区分
					$("serviceContractFieldsTable").hide();
					$("propertyContractFieldsTable").show();//设置标准字段的表格不可见
					if(document.getElementsByName("specialtyId")[0].value==<%=StaticVariable.AVAILABLE_SPECIALTY[0]%>){  //无线专业物业合同有扩展字段.
						dojo.byId("extends_fields").style.visibility="visible";
						return;
					}
				}else{
				    $("cityTr").hide();
					$("serviceContractFieldsTable").show();
					$("propertyContractFieldsTable").hide();//设置标准字段的表格不可见
				}
				
				dojo.byId("extends_fields").style.visibility="hidden";
			}
			dojo.addOnLoad(init);
 </script>
	 

	<!---------------------------------------页面主体开始--------------------------------------->
 
		 

		<!------------------标识页面位置------------------>
		 

		<!---------------------------------------维护合同模板定制--------------------------------------->
		<html:form method="post" action="/contractModel/save" onsubmit="return checkForm();">
			<table>
				  <caption>${eoms:a2u('新增合同模板')}
				  </caption>
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
										<html:text property="modelName" styleClass="clstext" title="合同模板名称" maxlength="100"/>
									</td>
								<tr>
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('专业')}
									</td>
									<td width="60%" height="25">
										<html:select property="specialtyId" style="width:4cm"
											styleClass="clstext" onchange="updateInfo(this.value)">
											<%--<html:options collection="specialtyOptions" property="value"
												labelProperty="label" />
										--%></html:select>
									</td>
								<tr>
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('代维类型')}
									</td>
									<td width="60%" height="25">
										<html:select property="serviceTypeId" style="width:4cm"
											styleClass="clstext">
											<%--<html:options collection="serviceTypeOptions"
												property="value" labelProperty="label" />
										--%></html:select>
									</td>
								<tr>
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('合同类型')}
									</td>
									<td width="60%" height="25">
										<html:select property="contractType" style="width:4cm"
											styleClass="clstext"
											onchange="updateExtendField(this.value);">
											<%--<html:options collection="contractTypeOptions"
												property="value" labelProperty="label" />--%>
										</html:select>
									</td>
								<tr>
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('地市选择')}
									</td>
									<td width="60%" height="25">
									<logic:equal value="true" name="modelRegionFieldReadOnly">
										<html:hidden  property="regionId"/>
										<bean:write name="regionName"/>
									</logic:equal>
									<logic:equal value="false" name="modelRegionFieldReadOnly">
										<html:select property="regionId" onchange="updateCityInfo(this.value);" style="width:4cm"
											styleClass="clstext">
											<%--<html:options collection="simpleDictRegionOptions"
												property="value" labelProperty="label" />
										--%></html:select>
										<html:hidden property="provinceId" />
									</logic:equal>
										<!-- 福建省ID -->
									</td>
								<tr>
								<tr class="tr_show" id="cityTr">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('县市选择')}
									</td>
									<td width="60%" height="25">
									<logic:equal value="true" name="modelCityFieldReadOnly">
										<html:hidden  property="cityId"/>
										<bean:write name="cityName"/>
									</logic:equal>
									<logic:equal value="false" name="modelCityFieldReadOnly">
										<select name="cityId" style="width: 4cm;">
										
										</select>
										<script type="text/javascript">
											updateCityInfo(document.forms[0].regionId.value);
										</script>
									</logic:equal>
										<!-- 福建省ID -->
									</td>
								<tr>
								<tr class="tr_show">
									<td width="40%" height="25" class="clsfth">
										${eoms:a2u('创建时间')}
									</td>
									<td width="60%" height="25">
										<html:text property="strCreateDate" size="20"
											onfocus="calendar()" readonly="true" styleClass="clstext" />
									</td>
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
											</legend>
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
										
										</fieldset>
									
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
						<html:submit styleClass="button" >${eoms:a2u('添加')}
						 
						</html:submit>
						<html:reset styleClass="button">${eoms:a2u('重置')}
							 
						</html:reset>
					</td>
				</tr>
			</table>
		</html:form>
			<table border="0" width="98%" align="center">
			<tr>
				<td>
					<b>${eoms:a2u('友情提醒:')}</b>
				</td>
			</tr>
			<tr>
				<td>
					<OL>
						<LI>
							
						<LI>
							
						<LI>
						
						<LI>
							
					</OL>
				</td>
			</tr>
		</table>
 
	<!---------------------------------------页面主体结束--------------------------------------->

<%@ include file="/common/footer_eoms_innerpage.jsp"%>
