<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<base target="_self" />
		<%@ include file="/common/header_eoms_form.jsp"%>
		<script language="javaScript" type="text/javascript"
			src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
		%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
	var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
</script>
<script type="text/javascript">
	//提交时对查询条件进行校验
	function changeType1(){
		//判断开始时间是否为空		
		var sheetAcceptLimit = jq("#sheetAcceptLimit").val();
		if(sheetAcceptLimit==""){
			alert("开始时间不能为空，请选择！");
 			return false;
		}
		//判断结束时间是否为空
		var sheetCompleteLimit = jq("#sheetCompleteLimit").val();
		if(sheetCompleteLimit==""){
			alert("结束时间不能为空，请选择！");
 			return false;
		}
		
		//判断开始时间是否大于结束时间
		var st=sheetAcceptLimit;
 		st = st.replace(/-/g,"/");
 		var date = new Date(st);
 	
 		var et=sheetCompleteLimit;
 		et = et.replace(/-/g,"/");
 		var date2 = new Date(et);
 	
	 	if(date > date2){
	 		alert("开始日期不能大于结束日期，请重新选择！");
	 		return false;
	 	}
	 	
	 	//判断开始时间和结束时间相差天数，默认查询31天之内的
	 	time = date2.getTime()- date.getTime();
 		days = parseInt(time / (1000 * 60 * 60 * 24));
 		if(days > 31){
	 		alert("统计周期为31天！请重新选择后再查询！");
	 		return false;
 		}		
	}
</script>
		<div id="sheetform">
			<html:form action="/schemeRateStat.do?method=childSceneReports"
				styleId="theform">
				<input type="hidden" id="queryFlay" name="queryFlay" value="1" /><!-- 是否点击查询按钮标识 -->
				
				<input type="hidden" id="flag" name="flag" value="cityQuery" />
				<input type="hidden" id="pId" name="pId" value="28" />
				<input type="hidden" id="queryDate" name="queryDate" value="${sheetAcceptLimit}" />
				<input type="hidden" id="queryEndDate" name="queryEndDate" value="${sheetCompleteLimit}" />
				
				<table class="formTable">
					<!--时间 -->
					<tr>
						<td class="label" style="width:10%">
							开始时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${sheetAcceptLimit}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:false" />

						</td>
						<td class="label" style="width:10%">
							结束时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${sheetCompleteLimit}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:false" />
						</td>
					</tr>
				</table>
				<!-- buttons -->
				<div class="form-btns">
					<html:submit styleClass="btn" property="method.save" styleId="method.save" onclick="return changeType1();">
                查询
                
                
            </html:submit>
            <c:if test="${queryFlay eq '1'}">
            	<input type="button" value="导出查询结果" class="btn" id="exportQueryResults" name="exportQueryResults"  />
			</c:if>
			<c:if test="${queryFlay ne '1'}">
				<span><font color="red">请选择查询条件中的开始日期和结束日期，查询相对应的报表数据!</font></span>
			</c:if>
			
				</div>
			</html:form>
		</div>

		<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="false" requestURI="schemeRateStat.do" sort="list"
			size="total" partialList="true">
			<display:caption media="html">
				<thead>
					<tr>
						<th rowspan="2" align="center">
							市分公司
						</th>
						<th rowspan="2" align="center">
							市分公司预检预修项目总数（项）
						</th>
						<th rowspan="2" align="center">
							市分公司预检预修项目总金额（元）
						</th>
						<th rowspan="2" align="center">
							工费总额
						</th>
						<th rowspan="2" align="center">
							材料费总额
						</th>
						<th rowspan="2" align="center">
							工费占比
						</th>
						<th rowspan="2" align="center">
							材料费占比
						</th>
						<th colspan="4" align="center">
							敷设光缆（不含接续）
						</th>
						<th colspan="6" align="center">
							光缆接头接续
						</th>
						<th colspan="6" align="center">
							光缆成端接续
						</th>
						<th colspan="4" align="center">
							拆除光缆
						</th>
						<th colspan="4" align="center">
							光缆整理
						</th>
						<th colspan="4" align="center">
							光缆断纤、劣化纤处理
						</th>
						<th colspan="4" align="center">
							光交接箱安装或迁移
						</th>
						<th colspan="4" align="center">
							光分路器箱（光分纤箱）安装或迁移
						</th>
						<th colspan="4" align="center">
							障碍物清除
						</th>
						<th colspan="5" align="center">
							立电杆
						</th>
						<th colspan="4" align="center">
							扶正电杆
						</th>
						<th colspan="4" align="center">
							拆电杆
						</th>
						<th colspan="4" align="center">
							增补拉线
						</th>
						<th colspan="4" align="center">
							拆除拉线
						</th>
						<th colspan="4" align="center">
							架设吊线
						</th>
						<th colspan="4" align="center">
							拆除吊线
						</th>
						<th colspan="4" align="center">
							安装吊线保护装置
						</th>
						<th colspan="4" align="center">
							升高吊线
						</th>
						<th colspan="4" align="center">
							增补挂钩
						</th>
						<th colspan="4" align="center">
							障碍物清除
						</th>
						<th colspan="4" align="center">
							管道迁改
						</th>
						<th colspan="4" align="center">
							管道疏通及整修
						</th>
						<th colspan="4" align="center">
							管道积水及杂物清理
						</th>
						<th colspan="4" align="center">
							管道人孔升降、人井上覆的更换
						</th>
						<th colspan="4" align="center">
							障碍物清除
						</th>
						<th colspan="4" align="center">
							线路资源清查
						</th>
						<th colspan="4" align="center">
							充气设备维修
						</th>
						<th colspan="4" align="center">
							标识安装
						</th>
						<th colspan="4" align="center">
							标识粉刷
						</th>
						<th colspan="4" align="center">
							护坡加固
						</th>
						<th colspan="4" align="center">
							架空交接箱站台维修
						</th>
						<th colspan="4" align="center">
							交接分线设备粉刷、编号喷涂
						</th>
						<th colspan="4" align="center">
							障碍物清除
						</th>
						<th colspan="4" align="center">
							光缆（含光交）_其他费用（手工填写）
						</th>
						<th colspan="4" align="center">
							杆路_其他费用（手工填写）
						</th>
						<th colspan="4" align="center">
							管道_其他费用（手工填写）
						</th>
						<th colspan="4" align="center">
							其他项目_其他费用（手工填写）
						</th>
						<th colspan="2" align="center">
							&nbsp;
						</th>


					</tr>
					<tr>
						<td>
							数量（千米）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（头）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							单位接头工费(元)
						</td>
						<td>
							单位接头材料费用（元）
						</td>
						<td>
							接续费用占比
						</td>
						<td>
							数量（芯）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							单位成端工费（元）
						</td>
						<td>
							单位成端材料费（元）
						</td>
						<td>
							接续费用占比
						</td>
						<td>
							数量（千米）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（档）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（头）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（个）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（个）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（棵）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							二次搬运费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（棵）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（棵）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（条）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（条）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（千米）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（千米）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（米）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（杆档）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（米）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（百米）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（米）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（人天）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（个）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（个）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（个）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							数量（次）
						</td>
						<td>
							工费
						</td>
						<td>
							材料费
						</td>
						<td>
							费用占比
						</td>
						<td>
							非标准模板项目（次）
						</td>
						<td>
							非标准模板费用占比
						</td>
					</tr>
				</thead>
			</display:caption>

			<display:column>
				<c:if test="${taskList.areaId eq 'total'}">
						${taskList.areaName}
				</c:if>
				<c:if test="${taskList.areaId eq 'ratio'}">
						${taskList.areaName}
				</c:if>
				<c:if test="${taskList.areaId ne 'total' && taskList.areaId ne 'ratio'}">
					<a
						href="${app}/activiti/statistics/schemeRateStat.do?method=childSceneReports&sheetAcceptLimit=${sheetAcceptLimit}&sheetCompleteLimit=${sheetCompleteLimit}&flag=countyQuery&pId=${taskList.areaId}&queryFlay=1"
						title="查看" target="_blank">${taskList.areaName}</a>
				</c:if>
			</display:column>
			<display:column>${taskList.totalNum}</display:column>
			<display:column><fmt:formatNumber value="${taskList.totalProjectAmount}" pattern="#.##" minFractionDigits="2"></fmt:formatNumber></display:column>
			<display:column><fmt:formatNumber value="${taskList.totalWorkAmout}" pattern="#.##" minFractionDigits="2"></fmt:formatNumber></display:column>
			<display:column><fmt:formatNumber value="${taskList.totalDataAmout}" pattern="#.##" minFractionDigits="2"></fmt:formatNumber></display:column>
			<display:column>${taskList.workRatio}</display:column>
			<display:column>${taskList.dataRatio}</display:column>
			<display:column>${taskList.a1}</display:column>
			<display:column>${taskList.a2}</display:column>
			<display:column>${taskList.a3}</display:column>
			<display:column>${taskList.a4}</display:column>
			<display:column>${taskList.b1}</display:column>
			<display:column>${taskList.b2}</display:column>
			<display:column>${taskList.b3}</display:column>
			<display:column>${taskList.b4}</display:column>
			<display:column>${taskList.b5}</display:column>
			<display:column>${taskList.b6}</display:column>
			<display:column>${taskList.c1}</display:column>
			<display:column>${taskList.c2}</display:column>
			<display:column>${taskList.c3}</display:column>
			<display:column>${taskList.c4}</display:column>
			<display:column>${taskList.c5}</display:column>
			<display:column>${taskList.c6}</display:column>
			<display:column>${taskList.d1}</display:column>
			<display:column>${taskList.d2}</display:column>
			<display:column>${taskList.d3}</display:column>
			<display:column>${taskList.d4}</display:column>
			<display:column>${taskList.e1}</display:column>
			<display:column>${taskList.e2}</display:column>
			<display:column>${taskList.e3}</display:column>
			<display:column>${taskList.e4}</display:column>
			<display:column>${taskList.f1}</display:column>
			<display:column>${taskList.f2}</display:column>
			<display:column>${taskList.f3}</display:column>
			<display:column>${taskList.f4}</display:column>
			<display:column>${taskList.g1}</display:column>
			<display:column>${taskList.g2}</display:column>
			<display:column>${taskList.g3}</display:column>
			<display:column>${taskList.g4}</display:column>
			<display:column>${taskList.h1}</display:column>
			<display:column>${taskList.h2}</display:column>
			<display:column>${taskList.h3}</display:column>
			<display:column>${taskList.h4}</display:column>
			<display:column>${taskList.i1}</display:column>
			<display:column>${taskList.i2}</display:column>
			<display:column>${taskList.i3}</display:column>
			<display:column>${taskList.i4}</display:column>
			<display:column>${taskList.j1}</display:column>
			<display:column>${taskList.j2}</display:column>
			<display:column>${taskList.j3}</display:column>
			<display:column>${taskList.j4}</display:column>
			<display:column>${taskList.j5}</display:column>
			<display:column>${taskList.k1}</display:column>
			<display:column>${taskList.k2}</display:column>
			<display:column>${taskList.k3}</display:column>
			<display:column>${taskList.k4}</display:column>
			<display:column>${taskList.l1}</display:column>
			<display:column>${taskList.l2}</display:column>
			<display:column>${taskList.l3}</display:column>
			<display:column>${taskList.l4}</display:column>
			<display:column>${taskList.m1}</display:column>
			<display:column>${taskList.m2}</display:column>
			<display:column>${taskList.m3}</display:column>
			<display:column>${taskList.m4}</display:column>
			<display:column>${taskList.n1}</display:column>
			<display:column>${taskList.n2}</display:column>
			<display:column>${taskList.n3}</display:column>
			<display:column>${taskList.n4}</display:column>
			<display:column>${taskList.o1}</display:column>
			<display:column>${taskList.o2}</display:column>
			<display:column>${taskList.o3}</display:column>
			<display:column>${taskList.o4}</display:column>
			<display:column>${taskList.p1}</display:column>
			<display:column>${taskList.p2}</display:column>
			<display:column>${taskList.p3}</display:column>
			<display:column>${taskList.p4}</display:column>
			<display:column>${taskList.q1}</display:column>
			<display:column>${taskList.q2}</display:column>
			<display:column>${taskList.q3}</display:column>
			<display:column>${taskList.q4}</display:column>
			<display:column>${taskList.r1}</display:column>
			<display:column>${taskList.r2}</display:column>
			<display:column>${taskList.r3}</display:column>
			<display:column>${taskList.r4}</display:column>
			<display:column>${taskList.s1}</display:column>
			<display:column>${taskList.s2}</display:column>
			<display:column>${taskList.s3}</display:column>
			<display:column>${taskList.s4}</display:column>
			<display:column>${taskList.t1}</display:column>
			<display:column>${taskList.t2}</display:column>
			<display:column>${taskList.t3}</display:column>
			<display:column>${taskList.t4}</display:column>
			<display:column>${taskList.u1}</display:column>
			<display:column>${taskList.u2}</display:column>
			<display:column>${taskList.u3}</display:column>
			<display:column>${taskList.u4}</display:column>
			<display:column>${taskList.v1}</display:column>
			<display:column>${taskList.v2}</display:column>
			<display:column>${taskList.v3}</display:column>
			<display:column>${taskList.v4}</display:column>
			<display:column>${taskList.w1}</display:column>
			<display:column>${taskList.w2}</display:column>
			<display:column>${taskList.w3}</display:column>
			<display:column>${taskList.w4}</display:column>
			<display:column>${taskList.x1}</display:column>
			<display:column>${taskList.x2}</display:column>
			<display:column>${taskList.x3}</display:column>
			<display:column>${taskList.x4}</display:column>
			<display:column>${taskList.y1}</display:column>
			<display:column>${taskList.y2}</display:column>
			<display:column>${taskList.y3}</display:column>
			<display:column>${taskList.y4}</display:column>
			<display:column>${taskList.z1}</display:column>
			<display:column>${taskList.z2}</display:column>
			<display:column>${taskList.z3}</display:column>
			<display:column>${taskList.z4}</display:column>
			<display:column>${taskList.AA1}</display:column>
			<display:column>${taskList.AA2}</display:column>
			<display:column>${taskList.AA3}</display:column>
			<display:column>${taskList.AA4}</display:column>
			<display:column>${taskList.BB1}</display:column>
			<display:column>${taskList.BB2}</display:column>
			<display:column>${taskList.BB3}</display:column>
			<display:column>${taskList.BB4}</display:column>
			<display:column>${taskList.CC1}</display:column>
			<display:column>${taskList.CC2}</display:column>
			<display:column>${taskList.CC3}</display:column>
			<display:column>${taskList.CC4}</display:column>
			<display:column>${taskList.DD1}</display:column>
			<display:column>${taskList.DD2}</display:column>
			<display:column>${taskList.DD3}</display:column>
			<display:column>${taskList.DD4}</display:column>
			<display:column>${taskList.EE1}</display:column>
			<display:column>${taskList.EE2}</display:column>
			<display:column>${taskList.EE3}</display:column>
			<display:column>${taskList.EE4}</display:column>
			<display:column>${taskList.FF1}</display:column>
			<display:column>${taskList.FF2}</display:column>
			<display:column>${taskList.FF3}</display:column>
			<display:column>${taskList.FF4}</display:column>
			<display:column>${taskList.GG1}</display:column>
			<display:column>${taskList.GG2}</display:column>
			<display:column>${taskList.GG3}</display:column>
			<display:column>${taskList.GG4}</display:column>
			<display:column>${taskList.HH1}</display:column>
			<display:column>${taskList.HH2}</display:column>
			<display:column>${taskList.HH3}</display:column>
			<display:column>${taskList.HH4}</display:column>
			<display:column>${taskList.II1}</display:column>
			<display:column>${taskList.II2}</display:column>
			<display:column>${taskList.II3}</display:column>
			<display:column>${taskList.II4}</display:column>
			<display:column>${taskList.JJ1}</display:column>
			<display:column>${taskList.JJ2}</display:column>
			<display:column>${taskList.JJ3}</display:column>
			<display:column>${taskList.JJ4}</display:column>
			<display:column>${taskList.KK1}</display:column>
			<display:column>${taskList.KK2}</display:column>
			<display:column>${taskList.KK3}</display:column>
			<display:column>${taskList.KK4}</display:column>
			<display:column>${taskList.LL1}</display:column>
			<display:column>${taskList.MM1}</display:column>


		</display:table>

<script type="text/javascript">
	//导出详情
	jq(document).delegate("#exportQueryResults","click", function(){
		var flag = jq("#flag").val();
		var pId = jq("#pId").val();
		var sheetAcceptLimit = jq("#queryDate").val();
		var sheetCompleteLimit = jq("#queryEndDate").val();
		window.location.href="${app}/activiti/statistics/schemeRateStat.do?method=exportChildSceneReports&flag="+flag+"&pId="+pId+"&sheetAcceptLimit="+sheetAcceptLimit+"&sheetCompleteLimit="+sheetCompleteLimit;
	});
</script>
<%@ include file="/common/footer_eoms.jsp"%>