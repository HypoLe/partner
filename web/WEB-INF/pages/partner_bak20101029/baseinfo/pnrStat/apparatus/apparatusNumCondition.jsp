<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/lines.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<script language=javascript>
<!--
	window.onload = function(){
								//合作伙伴
								var providerName = "${providerBuffer}"; 
								var arrOptionsP=providerName.split(",");
								var objp=$("provider");					
								var m=0;
								var n=0;
								for(m=0;m<arrOptionsP.length;m++){
									var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
									objp.options[n]=optp;
									n++;
									m++;
								}
		}
	
	function sub(){
	
	var month =document.getElementById("month").value;
	var year =document.getElementById("year").value;
	
		if(year!="" && month==""){
			alert("请选择要统计的月份！");
		}else if(year=="" && month!=""){
			alert("请选择要统计的年份！");
		}else{
			$("lineForm").submit();
		}
	}
	
//-->
</script>




<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">



	<html:form action="/pnrStats.do?method=getApparatusNumStatReport" styleId="lineForm" method="post" >
	
		<table align="center" class="formTable">
			<caption>
				<div class="header center">仪器仪表统计</div>
			</caption>
			<tr>

				<td class="label">
					年份：
				</td>
				<td class="content">
					<select name="year" id="year" 
						alt="allowBlank:false,vtext:'请选择年份'">
						<option id="0" value="">--请选择年份--</option>
							<c:forEach begin="2008" end="2025" var="num">
									<option id="${num}" value="${num}">${num}年</option>
							</c:forEach>			
					</select>
				</td>
				<td class="label">
					月份：
				</td>
				<td class="content">
					<select name="month" id="month"
						alt="allowBlank:false,vtext:'请选择月份'">
						<option id="0" value="">--请选择月份--</option>
							<c:forEach begin="1" end="12" var="num">
									<option id="${num}" value="${num}">${num}月</option>
							</c:forEach>
												
					</select>
				</td>

			</tr> 


			<tr>
				<!-- 所属地市 -->
				<td class="label">
					<fmt:message key="line.region" />:
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


				<!-- 合作伙伴 -->
				<td class="label">
					<fmt:message key="line.provider" />:
				</td>
				<td class="content">
					<!-- 合作伙伴 -->			
					<select name="provider" id="provider" 
						alt="allowBlank:false,vtext:'请选择合作伙伴'">
						<option value="">
							--请选择合作伙伴--
						</option>	
															
					</select>
				</td>

			</tr> 
<!-- 		
			<tr>
				<td class="label">
					仪器仪表类型：&nbsp;
				</td>
				<td class="content" colspan='3'>
					<eoms:comboBox name="type" id="type" initDicId="11204"
						defaultValue='${tawApparatusForm.type}'
						alt="allowBlank:false,vtext:''" />
					
				</td>
			
			</tr>

 -->		
 
 			<tr>
				<td class="label">
					仪器仪表名称：&nbsp;
				</td>
				<td class="content" colspan='3'>
					<input type="text"   id="apparatusName" name="apparatusName" 
					 class="text"  value="" alt="allowBlank:true"/>
					
				</td>
			
			</tr>
 		
		</table>
				<!-- 提交按钮 -->
				<td>
					<input type="button" class="btn" value="<fmt:message key="button.search"/>" onclick="sub();" />
				</td>
		
	</html:form>






</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>