
<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">
function sub(){
	var month =document.getElementById("month").value;
	var year =document.getElementById("year").value;
	
		if(year==""){
			alert("请选择要统计的年份！");
			return false;
		}
		if(month==""){
			alert("请选择要统计的月份！");
			return false;
		}
	    return true;  
	}
</script>
<html:form action="/pnrStats.do?method=getOilStatByCtiy" styleId="baseinfoReportFrom" method="post" onsubmit="return sub();"> 
<center> 
<div style="width:400px">
<table class="formTable">
	<caption>
		<div class="header center">合作伙伴各功率油机数量统计</div>
	</caption>
	<!-- 区域地市 -->
	<tr>
		<td class="label">
			所属地市：&nbsp;
		</td>
		<td class="content">
					<!-- 地市 -->			
					<select name="region" id="region"
						alt="allowBlank:false,vtext:'请选择所在地市'">
						<option value="">
							--请选择所在地市--
						</option>
						<logic:iterate id="regionBuffer" name="regionBuffer">
							<option value="${regionBuffer.areaid}">
								${regionBuffer.areaname}
							</option>
						</logic:iterate>
						
					</select>
		</td>
	</tr>	
	<!-- 时间 -->
	<tr>
		<td class="label">
			时间：
		</td>
		<td class="content">
			<select id="year" name="year" >
					<option id="0" value="">--请选择年份--</option>
				<c:forEach begin="2008" end="2025" var="num">
						<option id="${num}" value="${num}">${num}年</option>
				</c:forEach>
			</select>
			<select id="month" name="month" >
					<option id="0" value="">--请选择月份--</option>
				<c:forEach begin="1" end="12" var="num">
						<option id="${num}" value="${num}">${num}月</option>
				</c:forEach>
			</select>
		</td>
</tr>

</table>

<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="统计" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</div>
</center>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>