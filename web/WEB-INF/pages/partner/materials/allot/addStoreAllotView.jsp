<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page contentType="text/html;charset=utf-8" import="java.util.*"%>
<%@ page import="com.boco.eoms.materials.model.BillMaterial"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
	String aa = request.getAttribute("username").toString();
	System.out.println(aa);
	String zhidan = "";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	zhidan = sdf.format(new Date());
%>

<%
	List<BillMaterial> list2 = (List<BillMaterial>) request
			.getAttribute("list");
	if (null == list2 || 0 >= list2.size()) {
%>
<script type="text/javascript">
	var aa='0';
	//alert(aa);
</script>
<%
	} else {
%>
<script type="text/javascript">
	var aa='1';
	//alert(aa);
</script>
<%
	}
%>


<html>
	<head>

		<script type="text/javascript">
	
	
	function getParam(method,billId,id){
					var app = document.getElementById("app").value;
					var storeBillingDate = encodeURI(document.getElementById("rktime").value);
					var outStoreSid = encodeURI(document.getElementById("outStoreSid").value);
					var storeCompany =encodeURI(document.getElementById("storeCompany").value);
					var storeDepartment = encodeURI(document.getElementById("storeDepartment").value);
					var storeSid = encodeURI(document.getElementById("storeSid").value);
					var inputStoreSid = encodeURI(document.getElementById("inputStoreSid").value);
					var storeRequisitioner = encodeURI(document.getElementById("storeRequisitioner").value);
					var storeOriginalNum = encodeURI(document.getElementById("storeOriginalNum").value);
					var remark = encodeURI(document.getElementById("remark").value);
					window.location.href=app + "/materials/billMaterial.do?method="+method+"&&flag=0"
					+"&&storeDepartment="+ encodeURI(storeDepartment)
					+"&&storeCompany="+ encodeURI(storeCompany)
					+"&&storeSid="+ encodeURI(storeSid)
					+"&&storeBillingDate="+ encodeURI(storeBillingDate)
					+ "&&storeRequisitioner="	+encodeURI(storeRequisitioner)
					+ "&&outStoreSid="	+encodeURI(outStoreSid)
					+ "&&inputStoreSid="	+encodeURI(inputStoreSid)
					+ "&&storeOriginalNum="	+encodeURI(storeOriginalNum)
					+ "&&remark="	+encodeURI(remark)
					+ "&&billId="	+billId
					+ "&&id="	+encodeURI(id);
	}
	</script>





		<script type="text/javascript">
	function validate(){
	var flag = true;
	var runturnalert = '';
//	alert("kaish");	
		var storeBillingDate = document.getElementById("rktime").value;
		//var storeCompany = document.getElementById("storeCompany").value;
		//var storeDepartment = document.getElementById("storeDepartment").value;
		var storeRequisitioner = document.getElementById("storeRequisitioner").value;
		var outStoreSid =  document.getElementById("outStoreSid").value;
		var inputStoreSid = document.getElementById("inputStoreSid").value;
		
		if(storeBillingDate=="" || storeBillingDate == null){
			flag = false;
			runturnalert=runturnalert+'|开单日期不能为空|';
		}
		if(storeRequisitioner=="" || storeRequisitioner == null){
			flag = false;
			runturnalert=runturnalert+'|经办人不能为空|';
		}
		if(outStoreSid == inputStoreSid ){
			flag = false;
			runturnalert=runturnalert+'|调出仓库与调入仓库相同|';
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
		<p>
		<center>
			<span style="font-size: 20px">新增调拨单</span>
		</center>
		</p>
		<p>
			<span style="color: red">制单人：<%=aa%></span> &nbsp;&nbsp;&nbsp;&nbsp;
			<span style="color: red">制单日期：<%=zhidan%></span>
		</p>
		<br />
		<div style="border-top: 1px solid #000; width: 100%; height: 1px;">
		</div>
		<br />
		<form action="storeAllot.do?method=save" method="post">
			<input type="hidden" name="app" id="app" value="${app}">
			<input type="hidden" name="storeCompany" id="storeCompany" value="">
			<input type="hidden" name="storeDepartment" id="storeDepartment" value="">
			<input type="hidden" name="storeSid" id="storeSid" value="">
			<input type="hidden" value="${billId}" name="billId" id="billId">
				<input type="hidden" value=<%=aa%> name="username" id="username">
				<input type="hidden" value="${storeNum}" name="storeNum" id="storeNum">
			<table class="formTable">
				<tr>
					<td class="label">
						<font color='red'>*</font>开单日期
					</td>
					<td>
						<!--<input type="text" name="storeBillingDate">-->
						<input type="text" class="text" name="storeBillingDate"
							readonly="readonly" id="rktime" value="${storeBillingDate}"
							onclick="popUpCalendar(this,this,null,null,null,true,-1)"
							alt="allowBlank:false,vtext:'请输入需求提出时间'" />
					</td>



					<td class="label">
						原始单号
					</td>
					<td>
						<input type="text" name="storeOriginalNum" id="storeOriginalNum"
							value="${storeOriginalNum}">
					</td>
				</tr>
				<tr>

					<td class="label">
						<font color='red'>*</font>调出仓库
					</td>
					<td>
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
						<font color='red'>*</font>调入仓库
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
						<font color='red'>*</font>经办人
					</td>
					<td>
						<input type="text" name="storeRequisitioner"
							id="storeRequisitioner" value="${storeRequisitioner}">
					</td>
					<td class="label">
						单据类型
					</td>
					<td>
						<select name="" disabled="true" id="">
							<option value="">
								调拨
							</option>
						</select>
					</td>
				</tr>

				<tr>
					<td class="label">
						备注
					</td>
					<td class="content" colspan="3">
						<textarea name="remark" id="remark" class="comments" rows="3"
							cols="100">${remark}</textarea>
					</td>
				</tr>

				
			</table>
			<br />
			<input type="button" style="margin-right: 5px" id="addMaterial"
				value="添加材料" onclick="getParam('view',0,0);" />

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
							String url = "<a onclick=\"getParam('delete','"
											+ list.get(i).getStoreBillId() + "','"
											+ list.get(i).getId() + "')\";>删除</a>";
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
							String amount = "<input type='text' name='materialAmount_"
											+ i + "'>";
									out.print(amount);
						%>
					</td>
					<td>
						<%
							String price = "<input type='text' name='materialPrice_"
											+ i + "'>";
									out.print(price);
						%>
					</td>

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
			<input type="submit" value="保存" onclick="return validate();" />

			<input type="button" value="返回"
				onClick="window.location.href='${app}/materials/storeAllot.do?method=view&fh=1&billId=${billId}'" />

		</form>
		<%@ include file="/common/footer_eoms.jsp"%>
	</body>
</html>