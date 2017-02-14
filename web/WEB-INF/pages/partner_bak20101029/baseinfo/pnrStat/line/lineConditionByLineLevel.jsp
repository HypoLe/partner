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

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
	
	
function changeRegion()
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/baseinfo/pnrStats.do?method=changeRegion&region="+region;
			
						Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									 if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									var json = eval(res);
									var cityName = "city";
									var arrOptions = json[0].cb.split(",");
									var obj=$(cityName);
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}
	
	
	function sub(){
	
	var month =document.getElementById("month").value;
	var year =document.getElementById("year").value;
	
		if(year!="" && month==""){
			alert("请选择要统计的月份！");
		}else if(year=="" && month!=""){
			alert("请选择要统计的年份！");
		}else{
			$("lineFormByLineLevel").submit();
		}
	      
	       
	}

	
//-->
</script>




<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">


	<html:form action="/pnrStats.do?method=getLineStatReportByLineLevel" styleId="lineFormByLineLevel" method="post" >
		<table align="center" class="formTable">
		 	<caption>
				<div class="header center">线路统计</div>
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
				<td  class="content">
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
						alt="allowBlank:false,vtext:'请选择所在地市'" 
						onchange="changeRegion();" >
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
				
				<!-- 县区 -->
				<td class="label">
					<fmt:message key="line.city" />:
				</td>
				<td class="content">
					<!-- 县区 -->			
					<select name="city" id="city" 
						alt="allowBlank:false,vtext:'请选择所在县区'">
						<option value="">
							--请选择所在县区--
						</option>				
					</select>
				</td>

				

			</tr> 
<tr>
				<!-- 线路等级 -->
				<td class="label">
					<fmt:message key="line.grade" />:
				</td>
				<td class="content" colspan=3>
					<!-- 线路等级 -->			

					<eoms:comboBox name="grade" id="grade" initDicId="1121103" defaultValue=""
					    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
									
											
					
					
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