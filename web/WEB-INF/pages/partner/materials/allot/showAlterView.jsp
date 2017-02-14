<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page contentType="text/html;charset=utf-8" import="java.util.*"%>
<%@ page import="com.boco.eoms.materials.model.BillMaterial"%>
<%@ page import="java.text.SimpleDateFormat"%>


<%

List<BillMaterial> list2 = (List<BillMaterial>) request.getAttribute("list");
if(null == list2 || 0>=list2.size()){
 %>
 <script type="text/javascript" >
	var aa='0';
</script>
<% } else { %>
 <script type="text/javascript" >
	var aa='1';
</script>
<% } %>
<html>
<head>
<script type="text/javascript">
	function getParam(method,storeNum,BillMaterialId){
					var app = document.getElementById("app").value;
					var storeBillingDate = encodeURI(document.getElementById("rktime").value);
					var storeMakeBillDate = encodeURI(document.getElementById("storeMakeBillDate").value);
					var storeMakeBillPeople = encodeURI(document.getElementById("storeMakeBillPeople").value);
					var storeCompany =encodeURI(document.getElementById("storeCompany").value);
					var storeDepartment = encodeURI(document.getElementById("storeDepartment").value);
					var storeSid = encodeURI(document.getElementById("storeSid").value);
					var storeRequisitioner = encodeURI(document.getElementById("storeRequisitioner").value);
					var storeOriginalNum = encodeURI(document.getElementById("storeOriginalNum").value);
					var remark = encodeURI(document.getElementById("remark").value);
					var storeInputId = encodeURI(document.getElementById("storeInputId").value);
					var outStoreSid = encodeURI(document.getElementById("outStoreSid").value);
					var inputStoreSid = encodeURI(document.getElementById("inputStoreSid").value);
					window.location.href=app + "/materials/billMaterial.do?method="+method+"&&flag=0_alter"
					+"&&outStoreSid="+ encodeURI(outStoreSid)
					+"&&storeMakeBillDate="+ encodeURI(storeMakeBillDate)
					+"&&storeMakeBillPeople="+ encodeURI(storeMakeBillPeople)
					+"&&inputStoreSid="+ encodeURI(inputStoreSid)
					+"&&storeBillingDate="+ encodeURI(storeBillingDate)
					+ "&&storeCompany="	+encodeURI(storeCompany)
					+ "&&storeRequisitioner="	+encodeURI(storeRequisitioner)
					+ "&&storeDepartment="	+encodeURI(storeDepartment)
					+ "&&storeSid="	+encodeURI(storeSid)
					+ "&&storeOriginalNum="	+encodeURI(storeOriginalNum)
					+ "&&remark="	+encodeURI(remark)
					+ "&&storeNum="	+storeNum
					+ "&&billId="	+storeNum
					+ "&&storeInputId="	+encodeURI(storeInputId)
					+ "&&id="+BillMaterialId; 
	}
	</script>
	<script type="text/javascript" >
	function validate(){
	var flag = true;
	var runturnalert = '';
//	alert("kaish");	
		var storeBillingDate = document.getElementById("rktime").value;
		var storeCompany = document.getElementById("storeCompany").value;
		var storeDepartment = document.getElementById("storeDepartment").value;
		var storeRequisitioner = document.getElementById("storeRequisitioner").value;
		if(storeBillingDate=="" || storeBillingDate == null){
			flag = false;
			runturnalert=runturnalert+'|开单日期不能为空|';
		}
		
		
		if(storeRequisitioner=="" || storeRequisitioner == null){
			flag = false;
			runturnalert=runturnalert+'|经办人不能为空|';
		}
		if(aa==0){
			flag = false;
			runturnalert=runturnalert+'|没有添加任何材料|';
		}
		
		if(false==flag){
			alert(runturnalert+'请核对后再试.');
		}
		return flag;
	}
</script>
</head>
	<body>
<form action="storeAllot.do?method=alter" method="post">
		<div>
			<p>
			<center>
				<span style="font-size: 20px">修改调拨单</span>
			</center>
			</p>
			<br/>
			<p>
				<span style="color: red">单号：</span>${storeNum}
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span style="color: red">制单人：</span>${storeMakeBillPeople}
					&nbsp;&nbsp;&nbsp;&nbsp;
					
					<span style="color: red">制单日期：</span>	<%=request.getAttribute("storeMakeBillDate")%>
			</p>
		</div>
			<input type="hidden" name="app" id="app" value="${app}">
			<input type="hidden" name="storeSid" id="storeSid" value="${storeSid}">
			<input type="hidden" name="storeDepartment" id="storeDepartment" value="${storeDepartment}">
			<input type="hidden" name="storeNum" id="storeNum" value="${storeNum}">
			<input type="hidden" name="storeMakeBillDate" id="storeMakeBillDate" value="${storeMakeBillDate}">
			<input type="hidden" name="storeSid" id="storeSid" value="${storeSid}">
			<input type="hidden" name="storeMakeBillPeople" id="storeMakeBillPeople" value="${storeMakeBillPeople}">
			<input type="hidden" name="storeCompany" id="storeCompany" value="${storeCompany}">
			<input type="hidden" name="storeInputId" id="storeInputId" value="${storeInputId}">

			
		<table class="formTable">
				<tr>
					<td class="label">
						调拨日期
					</td>
					<td class="content">
						<input type="text" name="storeBillingDate"
							value="${storeBillingDate}" id="rktime"
							onClick="popUpCalendar(this, this,null,null,null,true,-1);" />
					</td>
					<td class="label">
						原始单号
					</td>
					<td class="content">
						<input class="text" name="storeOriginalNum"
							value="${storeOriginalNum}" id="storeOriginalNum"/>
					</td>
				</tr>

				<tr>
					<td class="label">
						调出仓库
					</td>
					<td class="content">
						<select name="outStoreSid" id="outStoreSid">
							<c:if test="${not empty outStoreSid}">
								<option value="${outStoreSid}">
									${outStoreSname}
								</option>
							</c:if>
							<c:forEach var="store" items="${storeList}">
								<option value="${store.id}">
									${store.name}
								</option>
							</c:forEach>
						</select>
					</td>
<td class="label">
						调入仓库
					</td>
					<td>
						<select name="inputStoreSid" id="inputStoreSid">
							<c:if test="${not empty inputStoreSid}">
								<option value="${inputStoreSid}">
									${inputStoreSname}
								</option>
							</c:if>
							<c:forEach var="store" items="${storeList}">
								<option value="${store.id}">
									${store.name}
								</option>
							</c:forEach>
						</select>
					</td>
					
				</tr>

				<tr>
					<td class="label">
						经办人
					</td>
					<td class="content">
						<input class="text" type="text" name="storeRequisitioner" id="storeRequisitioner"
							value="${storeRequisitioner}" />
					</td>

					
				</tr>
				<tr>
					<td class="label">
						备注
					</td>
					<td class="content" colspan="3">
							 <textarea name="remark" id="remark" class="comments" rows="3" cols="125">${remark}</textarea>
					</td>
				</tr>
			</table>
			<br />
<input type="button" style="margin-right: 5px" id="addMaterial"
				value="添加材料" onclick="getParam('view','${storeNum}',0);"/>

			<br />
			<br />

			<table class="table">
				<tr>
					<td class="label">
						操作
					</td>
					<td class="label">
						材料编码
					</td>
					<td class="label">
						材料名称
					</td>
					<td class="label">
						材料规格
					</td>
					<td class="label">
						单位
					</td>
					<td class="label">
						数量
					</td>
					<td class="label">
						单价
					</td>
			  <!-- <td class="label">
						总额
					</td> -->
					<td class="label">
						备注
					</td>
				</tr>

				<tr>
					<%!int i = 0;%>
					<%
						List<BillMaterial> list = (List<BillMaterial>) request
								.getAttribute("list");
						if (list != null) {
							for (i = 0; i < list.size(); i++) {
					%>
				
				<tr>
					<td>
						<%
							

									String url = "<a onclick=\"getParam('delete','"+list.get(i).getStoreBillId()+"','"+list.get(i).getId()+"')\";>删除</a>";
									out.print(url);
									String id = "<input type='hidden' name='id_" + i
											+ "' value='" + list.get(i).getId() + "'>";
									out.print(id);
						%>
					</td>
					<td>
						<%
							out.print(list.get(i).getEncode());
						%>
					</td>
					<td>
						<%
							out.print(list.get(i).getMaterialName());
						%>
					</td>
					<td>
						<%
							out.print(list.get(i).getSpecification());
						%>
					</td>
					<td>
						<%
							out.print(list.get(i).getUnit());
						%>
					</td>
					<td>
						<%
							String amount = "<input type='text' value='"+list.get(i).getMaterialAmount()+"' name='materialAmount_"
											+ i + "'>";
									out.print(amount);
						%>
					</td>
					<td>
						<%
							String price = "<input type='text' value='"+list.get(i).getMaterialPrice()+"' name='materialPrice_"
											+ i + "'>";
									out.print(price);
						%>
					</td>
				<!--  	<td>
					</td> -->
					<td>
						<%
							String remark = "<input type='text' name='remark_'" + i
											+ "'>";
									out.print(remark);
						%>
					</td>
				</tr>
				<%
					}
					}
				%>


			</table>
			<%
				String sum = "<input type='hidden' name='sum' value='" + i + "'>";
				out.print(sum);
			%>
			<br />
			<input type="submit" value="修改" onclick="return validate();"/>
				<input type="button" class="bnt_an" value="返回" onclick="window.location.href='${app}/materials/storeAllot.do?method=view'" />
				</form>

		<%@ include file="/common/footer_eoms.jsp"%>
	</body>
</html>