<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"; 
		%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>		
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
 Ext.onReady(function(){
  		// 初始的时候给区县默认值
		delAllOption("country");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
			url : url ,
			method: 'POST',
			success: function ( result, request ) { 
				res = result.responseText;
				if(res.indexOf("[{")!=0){
							res = "[{"+result.responseText;
				}
				if(res.indexOf("<\/SCRIPT>")>0){
			  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
				}
				
				var json = eval(res);
				
				var countyName = "country";
				var arrOptions = json[0].cb.split(",");
				var obj=$(countyName);
				var i=0;
				var j=0;
				for(i=0;i<arrOptions.length;i++){
					var opt=new Option(arrOptions[i+1],arrOptions[i]);
					obj.options[j]=opt;
					var country = "${country}";
					if(arrOptions[i]==country){
					obj.options[j].selected=true;
					}
					j++;
					i++;
				}
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});//初始的时候给区县默认值结束
		
  });
		// 地区、区县连动
function changeCity(con)
		{    
		    delAllOption("country");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
				url : url ,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("[{")!=0){
								res = "[{"+result.responseText;
					}
					if(res.indexOf("<\/SCRIPT>")>0){
				  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					
					var json = eval(res);
					
					var countyName = "country";
					var arrOptions = json[0].cb.split(",");
					var obj=$(countyName);
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
 function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         }	
         
     }		
     
</script>
<script type="text/javascript">
//重置
		function newReset(){
			document.getElementById("region").value="";	        
			document.getElementById("country").value="";	      
			document.getElementById("resName").value="";	       
			document.getElementById("confirmNum").value="";	        
			document.getElementById("isModify").value="";
			document.getElementById("dataFilter").value="";
			document.getElementById("newProductType").value="";
			document.getElementById("newRoomType").value="";
			document.getElementById("newAntennaHeight").value="";
			document.getElementById("oldProductType").value="";
			document.getElementById("oldRoomType").value="";
			document.getElementById("oldAntennaHeight").value="";
		}
		
		function subgo(str){
			window.location.href="${app}/partner/shortperiod/shortPeriod.do?method=downloadTowerFile&fileName="+str;
		}
		
		function exportTowerList(){
			var tTotal = jq("#tTotal").val();
				if(tTotal == 0){
					alert("没有要导出的数据");
					return;
				}else{
					var tRegion = jq("#tRegion").val();
					var tCountry = jq("#tCountry").val();
					var tResName = jq("#tResName").val();
					var tConfirmNum = jq("#tConfirmNum").val();
					var tIsModify = jq("#tIsModify").val();
					var tDataFilter = jq("#tDataFilter").val();
					var tNewProductType = jq("#tNewProductType").val();
					var tNewRoomType = jq("#tNewRoomType").val();
					var tNewAntennaHeight = jq("#tNewAntennaHeight").val();
					var tOldProductType = jq("#tOldProductType").val();
					var tOldRoomType = jq("#tOldRoomType").val();
					var tOldAntennaHeight = jq("#tOldAntennaHeight").val();
					
					var tUrl = "${app}/partner/shortperiod/shortPeriod.do?method=exportTowerList"
								+"&region="+tRegion+"&country="+tCountry+"&resName="+tResName
								+"&confirmNum="+tConfirmNum+"&isModify="+tIsModify+"&dataFilter="+tDataFilter
								+"&newProductType="+tNewProductType+"&newRoomType="+tNewRoomType+"&newAntennaHeight="+tNewAntennaHeight
								+"&oldProductType="+tOldProductType+"&oldRoomType="+tOldRoomType+"&oldAntennaHeight="+tOldAntennaHeight;
								
					new Ext.form.BasicForm('exportForm').submit({
						timeout:600000,
						method :'post',
						url : tUrl,
					  	waitTitle : '请稍后...',
						waitMsg : '正在导出数据,请稍后...',
					    success : function(form, action) {
							Ext.Msg.alert('提示信息', '导出成功!请点击导出结果进行下载!');
							jq("#resultSpan").append('导出结果为：<a id="errorData1" href="javascript:void(0)" onclick="subgo(&quot;'+action.result.fileName+'&quot;)" ><font color="red">'+action.result.fileName+'</font></a>');
						},
						failure : function(form, action) {
							Ext.Msg.alert('提示信息', '导出失败!');
						}
			  		});
				}
		}
</script>

		<div id="sheetform">
			<html:form action="/shortPeriod.do?method=listBacklog"
				styleId="theform">
				<input type="hidden" id="flag" name="flag" value="1" />
				<input type="hidden" id="tTotal" name="tTotal" value="${total}" />
				<input type="hidden" id="tRegion" name="tRegion" value="${region}" />
				<input type="hidden" id="tCountry" name="tCountry" value="${country}" />
				<input type="hidden" id="tResName" name="tResName" value="${resName}" />
				<input type="hidden" id="tConfirmNum" name="tConfirmNum" value="${confirmNum}" />
				<input type="hidden" id="tIsModify" name="tIsModify" value="${isModify}" />
				<input type="hidden" id="tDataFilter" name="tDataFilter" value="${dataFilter}" />
				
				<input type="hidden" id="tNewProductType" name="tNewProductType" value="${newProductType}" />
				<input type="hidden" id="tNewRoomType" name="tNewRoomType" value="${newRoomType}" />
				<input type="hidden" id="tNewAntennaHeight" name="tNewAntennaHeight" value="${newAntennaHeight}" />
				<input type="hidden" id="tOldProductType" name="tOldProductType" value="${oldProductType}" />
				<input type="hidden" id="tOldRoomType" name="tOldRoomType" value="${oldRoomType}" />
				<input type="hidden" id="tOldAntennaHeight" name="tOldAntennaHeight" value="${oldAntennaHeight}" />
				
				<table class="formTable"  style="width:100%">
                  <tr>
					<td class="label" style="width:10%">地市</td>
					<td class="content" style="width:20%">
						<select name="region"  id="region" class="select"
							alt="allowBlank:false,vtext:'请选择所在地市'"
							onchange="changeCity(0);">
							<option value="">
								--请选择所在地市--
							</option>
							<logic:iterate id="city" name="city">
							<c:if test="${city.areaid ==region}">
								<option value="${city.areaid}" selected="selected" >
									${city.areaname}
								</option>
							</c:if>
							<option value="${city.areaid}">
								${city.areaname}
							</option>
							</logic:iterate>
						</select>
					</td>
					<td class="label" style="width:10%">区县</td>
					<td class="content" style="width:20%">
						<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>
					</td>
					 <td class="label" style="width:10%">站点名称</td>
					  <td class="content">
						 <input type="text" name="resName" class="text" id="resName" value="${resName}" />
					 </td>
				</tr>
				
			<tr>
					<td class="label" style="width:10%">产品业务确认单编号</td>
					<td class="content" style="width:20%">
						<input type="text" name="confirmNum" class="text" id="confirmNum" value="${confirmNum}" />
					</td>
					<td class="label" style="width:10%">是否已修改</td>
					<td class="content" style="width:20%">
						<select id="isModify" name="isModify" class="text" style="width: 150px;">
							<option value="">
								所有
							</option>
							<option value="1" ${isModify == "1" ? 'selected="selected"':'' }>
								是
							</option>
							<option value="0" ${isModify == "0" ? 'selected="selected"':'' }>
								否
							</option>
						</select>
					</td>
					<td class="label" style="width:10%">数据筛选条件</td>
					<td class="content">
						<select id="dataFilter" name="dataFilter" class="text" style="width: 150px;">
							<option value="">无</option>
							<option value="1" ${dataFilter == "1" ? 'selected="selected"':'' }>
								无机房且有用户数
							</option>
							<option value="2" ${dataFilter == "2" ? 'selected="selected"':'' }>
								非RRU拉远且存在铁塔机房
							</option>
							<option value="3" ${dataFilter == "3" ? 'selected="selected"':'' }>
								铁塔站址重复数据
							</option>
							<option value="4" ${dataFilter == "4" ? 'selected="selected"':'' }>
								产品类型为其他
							</option>
							<option value="5" ${dataFilter == "5" ? 'selected="selected"':'' }>
								机房类型为其他
							</option>
							<option value="6" ${dataFilter == "6" ? 'selected="selected"':'' }>
								天线挂高为其他
							</option>
						</select>
					</td>
				</tr>
				
				
				<tr>
					<td class="label" style="width:10%">产品类型（新）</td>
					<td class="content" style="width:20%">
						<eoms:comboBox name="newProductType" id="newProductType" defaultValue="${newProductType}" initDicId="10308"
								alt="allowBlank:false" styleClass="select" />
					</td>
					<td class="label" style="width:10%">机房类型（新）</td>
					<td class="content" style="width:20%">
						<eoms:comboBox name="newRoomType" id="newRoomType" defaultValue="${newRoomType}" initDicId="10307"
								alt="allowBlank:false" styleClass="select" />
					</td>
					<td class="label" style="width:10%">天线挂高（新）</td>
					<td class="content">
						<eoms:comboBox name="newAntennaHeight" id="newAntennaHeight" defaultValue="${newAntennaHeight}" initDicId="10310"
								alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>
				
				<tr>
					<td class="label" style="width:10%">产品类型（旧）</td>
					<td class="content" style="width:20%">
						<eoms:comboBox name="oldProductType" id="oldProductType" defaultValue="${oldProductType}" initDicId="10308"
								alt="allowBlank:false" styleClass="select" />
					</td>
					<td class="label" style="width:10%">机房类型（旧）</td>
					<td class="content" style="width:20%">
						<eoms:comboBox name="oldRoomType" id="oldRoomType" defaultValue="${oldRoomType}" initDicId="10307"
								alt="allowBlank:false" styleClass="select" />
					</td>
					<td class="label" style="width:10%">天线挂高（旧）</td>
					<td class="content">
						<eoms:comboBox name="oldAntennaHeight" id="oldAntennaHeight" defaultValue="${oldAntennaHeight}" initDicId="10310"
								alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>

				</table>
				<!-- buttons -->
				<div class="form-btns">
					<html:submit styleClass="btn" property="method.save"
						styleId="method.save">
                查询
                
                
            </html:submit>
					<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>
					<input type="button" value="导出" class="btn" id="exportQueryResults" name="exportQueryResults"  onclick="exportTowerList()" />
					<c:if test="${flag ne '1'}">
				    	<span><font color="red">请点击查询按钮,查询对应的数据!</font></span>
			        </c:if>
			        <span id="resultSpan"></span><!-- 返回生成的excel文件 -->
				</div>
			</html:form>
		</div>
		
		
		<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="false" requestURI="shortPeriod.do" sort="list"
			size="total" partialList="true">
			<display:column sortable="true" headerClass="sortable" title="产品业务确认单编号">
				<a href="${app}/partner/shortperiod/shortPeriod.do?method=showTowerUpdatePage&productNum=${taskList.productNum}${condition}" title="修改">${taskList.productNum}</a>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="站点名称(原始)" property="resName" />
			<display:column sortable="false" headerClass="sortable" title="地市" property="cityName" />
			<display:column sortable="false" headerClass="sortable" title="区县" property="countryName" />
			<display:column sortable="false" headerClass="sortable" title="站址编码" property="nameId" />
			<display:column sortable="false" headerClass="sortable" title="需求确认单编号" property="nameNumber" />
			<display:column sortable="false" headerClass="sortable" title="产品类型(原始)" property="a2" />
			<display:column sortable="false" headerClass="sortable" title="机房类型(原始)" property="a3" />
			<display:column sortable="false" headerClass="sortable" title="产品单元数1(原始)" property="a4" /> 
			<display:column sortable="false" headerClass="sortable" title="对应实际最高天线挂高1(原始)" property="a5" /> 
			<display:column sortable="false" headerClass="sortable" title="BBU是否放在铁塔公司机房1(原始)" property="a6" /> 
			<display:column sortable="false" headerClass="sortable" title="产品单元数2(原始)" property="a8" /> 
			<display:column sortable="false" headerClass="sortable" title="实际最高天线挂高2(原始)" property="a9" /> 
			<display:column sortable="false" headerClass="sortable" title="是否放在铁塔公司机房2(原始)" property="a10" /> 
			<display:column sortable="false" headerClass="sortable" title="产品单元数3(原始)" property="a12" /> 
			<display:column sortable="false" headerClass="sortable" title="实际最高天线挂高3(原始)" property="a13" /> 
			<display:column sortable="false" headerClass="sortable" title="BBU是否放在铁塔公司机房3(原始)" property="a14" /> 
			<display:column sortable="false" headerClass="sortable" title="期末铁塔共享用户数(原始)" property="a16" /> 
			<display:column sortable="false" headerClass="sortable" title="期末机房共享用户数(原始)" property="a21" /> 
			<display:column sortable="false" headerClass="sortable" title="配套共享用户数(原始)" property="a26" /> 
			<display:column sortable="false" headerClass="sortable" title="场地费共享用户数(原始)" property="a35" /> 
			<display:column sortable="false" headerClass="sortable" title="电力引入费共享用户数(原始)" property="a41" /> 
			<display:column sortable="false" headerClass="sortable" title="维护费共享用户数(原始)" property="a48" /> 
			<display:column sortable="false" headerClass="sortable" title="站点名称(核查)" property="newResName" />
			<display:column sortable="false" headerClass="sortable" title="产品类型(核查)">
				<eoms:id2nameDB id="${taskList.newa2}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="机房类型(核查)">
				<eoms:id2nameDB id="${taskList.newa3}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="产品单元数1(核查)">
				<eoms:id2nameDB id="${taskList.newa4}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="对应实际最高天线挂高1(核查)">
				<eoms:id2nameDB id="${taskList.newa5}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="BBU是否放在铁塔公司机房1(核查)">
				<eoms:id2nameDB id="${taskList.newa6}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="产品单元数2(核查)" property="newa8" />
			<display:column sortable="false" headerClass="sortable" title="实际最高天线挂高2(核查)" property="newa9" />
			<display:column sortable="false" headerClass="sortable" title="BBU是否放在铁塔公司机房2(核查)" property="newa10" />
			<display:column sortable="false" headerClass="sortable" title="产品单元数3(核查)" property="newa12" />
			<display:column sortable="false" headerClass="sortable" title="实际最高天线挂高3(核查)" property="newa13" />
			<display:column sortable="false" headerClass="sortable" title="BBU是否放在铁塔公司机房3(核查)" property="newa14" />
			<display:column sortable="false" headerClass="sortable" title="期末铁塔共享用户数(核查)" property="newa16" />
			<display:column sortable="false" headerClass="sortable" title="期末机房共享用户数(核查)" property="newa21" />
			<display:column sortable="false" headerClass="sortable" title="配套共享用户数(核查)" property="newa26" />
			<display:column sortable="false" headerClass="sortable" title="维护费共享用户数(核查)" property="newa31" />
			<display:column sortable="false" headerClass="sortable" title="场地费共享用户数(核查)" property="newa36" />
			<display:column sortable="false" headerClass="sortable" title="电力引入费共享用户数(核查)" property="newa41" />
			<display:column sortable="true" headerClass="sortable" title="处理">
				<a
					href="${app}/partner/shortperiod/shortPeriod.do?method=showTowerUpdatePage&productNum=${taskList.productNum}${condition}"
					title="修改">修改</a>
			</display:column>
			
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		
	<!-- 	<script type="text/javascript">
			//导出查询结果
			jq(document).delegate("#exportQueryResults","click", function(){
				var tTotal = jq("#tTotal").val();
				if(tTotal == 0){
					alert("没有要导出的数据");
					return;
				}else{
					var tRegion = jq("#tRegion").val();
					var tCountry = jq("#tCountry").val();
					var tResName = jq("#tResName").val();
					var tConfirmNum = jq("#tConfirmNum").val();
					var tIsModify = jq("#tIsModify").val();
					window.location.href="${app}/partner/shortperiod/shortPeriod.do?method=exportTowerList&region="+tRegion+"&country="+tCountry+"&resName="+tResName+"&confirmNum="+tConfirmNum+"&isModify="+tIsModify;
				}
			});
		</script> -->
		
		<form id="exportForm"></form><!-- 导出用 -->
		<%@ include file="/common/footer_eoms.jsp"%>