<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.*,com.boco.eoms.common.util.StaticMethod,java.text.SimpleDateFormat"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker4.7.2/WdatePicker.js"></script>

<style type="text/css">
	#qqonline {
		position: absolute;
		top: 320px;
	}
	.formTable{
	   table-layout:fixed;
	}
	.formTable td{
	   width:60px;
	}
	.formTableFor {
	    border-collapse: collapse;
	    font-size: 12px;
	    width: 100%;
	}
	.formTableFor td {
	    background-color: #FFFFFF;
	    border: 1px solid #C9DEFA;
	    padding: 6px;
	}
	.formTableFor td.label {
	    background-color: #EDF5FD;
	    width: 20%;
	}
	.formTableFor td.content {
	    text-align: left;
	}
	table {
	    border-collapse: collapse;
    }
    table,td,th {
	    border: 1px solid #C9DEFA;
    }
    td {
	    padding: 7px;
    }
 </style>


<form id="statisticForm" action="statistic.do?method=showStatisticInfo" method="post">
<input type="hidden" name="sessionDeptId" id="sessionDeptId"  value="${sessionDeptId}"/>
<input type="hidden" name="sessionAreaId" id="sessionAreaId"  value="${sessionAreaId}"/>
<input type="hidden" name="alinkareaid" id="alinkareaid" />
<input type="hidden" name="alinkcompanyId" id="alinkcompanyId"/>
<input type="hidden" name="Excelalinkareaid" id="Excelalinkareaid" value="${alinkareaid}"/>
<input type="hidden" name="ExcelalinkcompanyId" id="ExcelalinkcompanyId" value="${alinkcompanyId}"/>
<input type="hidden" name="excal" id="excal"/>
<input type="hidden" name="alinkcompanyId" id="alinkcompanyId"/>
<fieldset>
  <legend>统计条件</legend>
	<table class="formTableFor">
		<tr>
			<td class="label">派单
				<select id="tagDate" onchange="changeSendTimeSelect(this)">
					<option value="0">时间段</option>
					<option value="1">年月</option>
				</select>
			</td>
			<td name="yearTd" style="display: none;">
			   <select id="year" name="year">
						<option value="">
							请选择年
						</option>
						<c:forEach begin="2012" end="2050" var="year">
							<option value="${year}">${year}年</option>
						</c:forEach>
					</select>
					<select id="month" name="month">
						<option value="">
							请选择月
						</option>
						<c:forEach begin="1" end="12" var="month">
							<option value="${month}">${month}月</option>
						</c:forEach>
					</select>
			</td>
			<td name="timeTd">
			从<input id="startTime" name="startTime" class="Wdate text" type="text" readonly="readonly" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'})" value="${startTime}"/>
			到<input id="endTime" name="endTime" class="Wdate text" readonly="readonly" type="text" onfocus="WdatePicker({maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'startTime\')}'})" value="${endTime}"/>
			</td>
		</tr>
		<tr>
			<td class="label">派单对象</td>
			<td>
			    <select id="sendObject" onchange="changeSendObjSelect(this)">
					<option value="0">用户</option>
					<option value="1">部门</option>
				</select>
					
				<input type="text"  class="text"  name="sendUserName" id="sendUserName" value="${sendUserName}" alt="allowBlank:false" readonly="readonly"/>
			   	<input type="hidden" name="sendUser" id="sendUser"  value="${sendUser}"/>
				<eoms:xbox id="sendUserNamexbox" dataUrl="${app}/xtree.do?method=userFromDept"  
						rootId="" rootText="用户树"  valueField="sendUser" handler="sendUserName" 
						textField="sendUserName" checktype="user" single="true" />
				<input type="text"  class="text"  name="sendDeptName" id="sendDeptName" value="${sendDeptName}" alt="allowBlank:false" readonly="readonly"/>
		   		<input type="hidden" name="sendDept" id="sendDept"  value="${sendDept}"/>
				<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=dept" rootId=""
						rootText='请选择部门' valueField="sendDept" handler="sendDeptName" textField="sendDeptName"
						checktype="dept" single="true" ></eoms:xbox>
			</td>
		</tr>
		<tr>
		    <td class="label">受理对象</td>
			<td>
				<select id="acceptObject" onchange="changeAcceptObjSelect(this)">
					<option value="0">用户</option>
					<option value="1">部门</option>
				</select>
				<input type="text"  class="text"  name="acceptUserName" id="acceptUserName" value="${acceptUserName }" alt="allowBlank:false" readonly="readonly"/>
			   	<input type="hidden" name="acceptUser" id="acceptUser"  value="${acceptUser }"/>
				<eoms:xbox id="acceptUserNamexbox" dataUrl="${app}/xtree.do?method=userFromDept"  
						rootId="" rootText="用户树"  valueField="acceptUser" handler="acceptUserName" 
						textField="acceptUserName" checktype="user" single="true" />						
				<input type="text"  class="text"  name="acceptDeptName" id="acceptDeptName" value="${acceptDeptName }" alt="allowBlank:false" readonly="readonly"/>
		   		<input type="hidden" name="acceptDept" id="acceptDept"  value="${acceptDept }"/>
				<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=dept" rootId=""
						rootText='请选择部门' valueField="acceptDept" handler="acceptDeptName" textField="acceptDeptName"
						checktype="dept" single="true" ></eoms:xbox>
			</td>
		</tr>
		<tr>
			<td class="label">区域</td>
			<td>
				<input type="text" name="area" id="area"  class="text medium"  value="${area}"/>
				<input type="hidden" name="areaIdSearch" id="areaIdSearch"   class="text medium" value="${areaIdSearch}"/>
				<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1" rootText='所属区域' valueField="areaIdSearch" handler="area" 
					textField="area" data="${boxData}" checktype="" single="true"/>
			</td>			
		</tr>
		<tr>
			<td class="label">巡检单位</td>
			<td>
			    <input type="text" name="maintainCompany" class="text"  id="maintainCompany" value="${maintainCompany}"/>
			    <input type="hidden" name="maintainCompanyId" id="maintainCompanyId"  value="${maintainCompanyId}" maxLength="32" 	class="text medium" />
			    <eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=dept" rootId="${rootDeptId}"
					rootText='巡检单位组织' valueField="maintainCompanyId" handler="maintainCompany" textField="maintainCompany"
					checktype="dept"  single="true"/>
			</td>
		</tr>
		<tr>
			<td class="label">巡检专业</td>
			<td>
				<eoms:comboBox name="mainSpecialty" id="mainSpecialty" defaultValue="${sheetMain.mainSpecialty}" 
				initDicId="11225" alt="allowBlank:false" styleClass="select"/>
			</td>				
	    </tr>
	    <tr>
	        <td class="label">任务类型</td>
			<td>
				<eoms:comboBox name="mainTaskType" id="mainTaskType" defaultValue="${sheetMain.mainTaskType}"
				initDicId="1010402" alt="allowBlank:false" styleClass="select"/>
			</td>
	    </tr>
	</table>
</fieldset>
<fieldset>
  <legend>统计项目</legend>
       <table class="formTable">
			<tr>
				<td class="label"><input name="statisticsItem" type="checkbox" checked="checked" value="itemArea"/>区域</td>
				<td class="label"><input name="statisticsItem" type="checkbox" checked="checked" value="itemComp"/>巡检单位</td>
				<td class="label"><input name="statisticsItem" type="checkbox" checked="checked" value="itemStat"/>工单状态</td>
				<td class="label"><input name="statisticsItem" type="checkbox" checked="checked" value="itemHold"/>归档满意度</td>
			</tr>
			<tr>
				<td class="label"><input name="statisticsItem" type="checkbox" checked="checked" value="itemUnaccept"/>未受理工单及时率</td>
				<td class="label"><input name="statisticsItem" type="checkbox" checked="checked" value="itemAccept"/>已受理工单及时率</td> 
				<td class="label"><input name="statisticsItem" type="checkbox" checked="checked" value="itemDeal"/>已回复工单及时率</td>
				<td class="label"></td>
			</tr>
		</table>
		<br>
		<font color="red">除了区域和巡检单位外的统计项必须至少选择一项</font>
</fieldset>
    <br/>
    <input type="button" class="btn" name="统计" value="统计" onclick="statisticSheet()"/>
	<input type="button" class="btn" name="重置" value="重置" onclick="res()" />
</form>

<table id="list" width="100%">
  <tr bgcolor="#EFF6FF">
    <td rowspan="2" name="tabArea"><div align="center">区域</div></td>
    <td rowspan="2" name="tabComp"><div align="center">巡检单位</div></td>
    <td colspan="5" name="tabStat"><div align="center">工单状态</div></td>
    <td colspan="2" name="tabUnac"><div align="center">未接工单</div></td>
    <td colspan="2" name="tabAcce"><div align="center">已受理工单</div></td>
    <td colspan="2" name="tabRepl"><div align="center">已回复工单</div></td>
    <td colspan="3" name="tabHold"><div align="center">归档工单</div></td>
  </tr>
  <tr bgcolor="#EFF6FF">
    <td name="tabStat"><div align="center">运行中</div></td>
    <td name="tabStat"><div align="center">已归档</div></td>
    <td name="tabStat"><div align="center">已作废</div></td>
    <td name="tabStat"><div align="center">强制归档</div></td>
    <td name="tabStat"><div align="center">强制作废</div></td>
    <td name="tabUnac"><div align="center">未超时</div></td>
    <td name="tabUnac"><div align="center">已超时</div></td>
    <td name="tabAcce"><div align="center">未超时</div></td>
    <td name="tabAcce"><div align="center">已超时</div></td>
    <td name="tabRepl"><div align="center">未超时</div></td>
    <td name="tabRepl"><div align="center">已超时</div></td>
    <td name="tabHold"><div align="center">满意</div></td>
    <td name="tabHold"><div align="center">合格</div></td>
    <td name="tabHold"><div align="center">不满意</div></td>
  </tr>
	<c:forEach items="${querylist}" var="tdList">
		<tr>
			<c:forEach items="${tdList}" var="td" varStatus="i">
				<c:if test="${td.show}">	
				   <c:if test="${i.index eq '0' || i.index eq '1' }">
				      <td rowspan="${td.rowspan}">
				        <c:if test="${i.index==0}">  <!-- 区域  -->
				           <c:choose>
				              <c:when test="${tdList[18].name > 0}">   
				                 <a href="#" onclick="selectArea('${tdList[16].name}');">${td.name}</a>
				              </c:when> 
				              <c:otherwise>
				                 ${td.name}
				              </c:otherwise>	
				           </c:choose>		           
				        </c:if> 
				        <c:if test="${i.index==1}">  <!-- 巡检单位  -->
				           <c:choose>
				              <c:when test="${tdList[19].name > 0}">   
				                 <a href="#" onclick="selectComp('${tdList[17].name}');">${td.name}</a>
				              </c:when> 
				              <c:otherwise>
				                 ${td.name}
				              </c:otherwise>	
				           </c:choose>		
				        </c:if> 	
				      </td>
				    </c:if>		
				    <c:choose>
				      <c:when test="${itemStat eq 'YES'}">
				         <c:if test="${i.index eq '2' || i.index eq '3' || i.index eq '4' || i.index eq '5' || i.index eq '6'}">
				            <td rowspan="${td.rowspan}">
				               <c:if test="${i.index==2}">  <!-- 运行中  -->
				                  <c:choose>
				                     <c:when test="${tdList[2].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=0&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>		
				               </c:if> 
				               <c:if test="${i.index==3}">  <!-- 已归档  -->
				                  <c:choose>
				                     <c:when test="${tdList[3].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=1&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}" >${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>	
				               </c:if>
				               <c:if test="${i.index==4}">  <!-- 已作废  -->
				                  <c:choose>
				                     <c:when test="${tdList[4].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=2&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>
				               </c:if>
				               <c:if test="${i.index==5}">  <!-- 强制归档  -->
				                  <c:choose>
				                     <c:when test="${tdList[5].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=3&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                 </c:choose>
				              </c:if>
				              <c:if test="${i.index==6}">  <!-- 强制作废  -->
				                 <c:choose>
				                    <c:when test="${tdList[6].name > 0}">   
				                       <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=4&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                    </c:when> 
				                    <c:otherwise>-</c:otherwise>	
				                 </c:choose>
				              </c:if>
				            </td>
				         </c:if>
				      </c:when>
				   </c:choose>
                   <c:choose>
				      <c:when test="${itemUnaccept eq 'YES'}">
				         <c:if test="${i.index eq '7' || i.index eq '8'}">
				            <td rowspan="${td.rowspan}">
				               <c:if test="${i.index==7}">   <!-- 未接工单 未超时  -->
				                  <c:choose>
				                     <c:when test="${tdList[7].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=5&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>
				               </c:if>
				               <c:if test="${i.index==8}">  <!-- 未接工单 已超时  -->
				                  <c:choose>
				                     <c:when test="${tdList[8].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=6&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>
				               </c:if>
				            </td>
				         </c:if>
				      </c:when>
				   </c:choose>
				   
				   <c:choose>
				      <c:when test="${itemAccept eq 'YES'}">
				         <c:if test="${i.index eq '9' || i.index eq '10'}">
				            <td rowspan="${td.rowspan}">
				               <c:if test="${i.index==9}">   <!-- 已受理工单 未超时  -->
				                  <c:choose>
				                     <c:when test="${tdList[9].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=7&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>
				               </c:if>
				               <c:if test="${i.index==10}">  <!-- 已受理工单 已超时  -->
				                  <c:choose>
				                     <c:when test="${tdList[10].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=8&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>
				               </c:if>
				            </td>
				         </c:if>
				      </c:when>
				   </c:choose>
				   
				   <c:choose>
				      <c:when test="${itemDeal eq 'YES'}">
				         <c:if test="${i.index eq '11' || i.index eq '12'}">
				            <td rowspan="${td.rowspan}">
				               <c:if test="${i.index==11}">   <!-- 已回复工单 未超时  -->
				                  <c:choose>
				                     <c:when test="${tdList[11].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=9&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>
				               </c:if>
				               <c:if test="${i.index==12}">  <!-- 已回复工单 已超时  -->
				                  <c:choose>
				                     <c:when test="${tdList[12].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=10&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>
				               </c:if>
				            </td>
				         </c:if>
				      </c:when>
				   </c:choose>
				   
				   <c:choose>
				      <c:when test="${itemHold eq 'YES'}">
				         <c:if test="${i.index eq '13' || i.index eq '14' || i.index eq '15'}">
				            <td rowspan="${td.rowspan}">
				               <c:if test="${i.index==13}">   <!-- 归档工单 满意  -->
				                  <c:choose>
				                     <c:when test="${tdList[13].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=11&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>
				               </c:if>
				               <c:if test="${i.index==14}">  <!-- 归档工单 合格  -->
				                  <c:choose>
				                     <c:when test="${tdList[14].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=12&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>
				               </c:if>
				               <c:if test="${i.index==15}">  <!-- 归档工单 不满意 -->
				                  <c:choose>
				                     <c:when test="${tdList[15].name > 0}">   
				                        <a target="black" href="${app}/sheet/pnrfaultdeal/statistic.do?method=showIndexList&flag=13&total=${td.name}&year=${year}&month=${month}&startTime=${startTime}&endTime=${endTime}&sendUser=${sendUser}&sendDept=${sendDept}&acceptDept=${acceptDept}&acceptUser=${acceptUser}&companyId=${tdList[17].name}&mainSpecialty=${mainSpecialty}&mainTaskType=${mainTaskType}">${td.name}</a>
				                     </c:when> 
				                     <c:otherwise>-</c:otherwise>	
				                  </c:choose>
				               </c:if>
				            </td>
				         </c:if>
				      </c:when>
				   </c:choose>				
				</c:if>
			</c:forEach>
		</tr>
	</c:forEach>
    
    </table>
    </br>
    <div>导出到: <img src="${app}/images/ico_file_excel.png"/> <a href="#" onclick="excel();">Excal</a></div>

<script type="text/javascript">
	var jq=jQuery.noConflict();
	Ext.onReady(function(){
        var itemStat="${itemStat}";
        var itemHold="${itemHold}";
        var itemUnaccept="${itemUnaccept}";
        var itemAccept="${itemAccept}";
        var itemDeal="${itemDeal}";
        var statisticsItemList = document.getElementsByName('statisticsItem');
        
        if(itemStat=="NO"){
           jq("td[name='tabStat']").hide();
           statisticsItemList[2].checked = false;
        }
        if(itemHold=="NO"){
           jq("td[name='tabHold']").hide();
           statisticsItemList[3].checked = false;
        }
        if(itemUnaccept=="NO"){
           jq("td[name='tabUnac']").hide();
           statisticsItemList[4].checked = false;
        }
        if(itemAccept=="NO"){
           jq("td[name='tabAcce']").hide();
           statisticsItemList[5].checked = false;
        }
        if(itemDeal=="NO"){
           jq("td[name='tabRepl']").hide();
           statisticsItemList[6].checked = false;
        }
	    
	    var yearflag  = "${year}";
	    var monthflag = "${month}";
	    var startTime = "${startTime}";
	    var endTime   = "${endTime}";
	    if(yearflag=="" && monthflag==""){
	      jq("td[name='yearTd']").hide();
	      jq("td[name='timeTd']").show();
	      jq('#tagDate').val('0');
	      jq('#startTime').val(startTime);
		  jq('#endTime').val(endTime);
	    }else{
	      jq("td[name='timeTd']").hide();
		  jq("td[name='yearTd']").show();
		  jq('#tagDate').val('1');
		  jq('#year').val(yearflag);
		  jq('#month').val(monthflag);
	    } 	
	    
	    var sendUser  = "${sendUser}";
	    var sendDept = "${sendDept}";
	    if(sendUser!=""){
	      jq('#sendObject').val('0');
	      jq('#sendDept').val('');
	      jq('#sendDeptName').val('');
	      jq("#sendDeptName").hide();
		  jq("#sendUserName").show();
	    }else if(sendDept!=""){
	      jq('#sendObject').val('1');
	      jq('#sendUser').val('');
	      jq('#sendUserName').val('');
		  jq("#sendUserName").hide();
		  jq("#sendDeptName").show();
	    }else{
	      jq("#sendDeptName").hide();
	    }
	    
	    var acceptUser  = "${acceptUser}";
	    var acceptDept = "${acceptDept}";
	    if(acceptUser!=""){
	      jq('#acceptObject').val('0');
	      jq('#acceptDept').val('');
	      jq('#acceptDeptName').val('');
	      jq("#acceptDeptName").hide();
		  jq("#acceptUserName").show();
	    }else if(acceptDept!=""){
	      jq('#acceptObject').val('1');
	      jq('#acceptUser').val('');
	      jq('#acceptUserName').val('');
		  jq("#acceptUserName").hide();
		  jq("#acceptDeptName").show();
	    }else{
	      jq("#acceptDeptName").hide();
	    }
	    
	    var mainSpecialty = "${mainSpecialty}";
		if(mainSpecialty!=""){
		   jq('#mainSpecialty').val(mainSpecialty);
		}
		
		var mainTaskType = "${mainTaskType}";
	    if(mainTaskType!=""){
		   jq('#mainTaskType').val(mainTaskType);
		}
	});
	

	/**
	* 派单时间段/年月下拉菜单切换触发
	*/
	function changeSendTimeSelect(select){
		if(select.value==0){//显示时间段，把年月清空后隐藏
			jq("td[name='yearTd']").hide();	
			jq('#year').val('');
			jq('#month').val('');
			jq("td[name='timeTd']").show();	
		}else{//显示年月，把时间段清空后隐藏
			jq("td[name='timeTd']").hide();
			jq('#startTime').val('');
			jq('#endTime').val('');
			jq("td[name='yearTd']").show();
		}
	}
	
	/**
	* 派单对象切换触发
	*/
	function changeSendObjSelect(select){
		if(select.value==0){//显示派单对象为用户
			jq("#sendDeptName").hide();
			jq('#sendDept').val('');
	        jq('#sendDeptName').val('');
			jq("#sendUserName").show();
		}else{//显示派单对象为部门
			jq("#sendDeptName").show();
			jq("#sendUserName").hide();
			jq('#sendUser').val('');
	        jq('#sendUserName').val('');
		}
	}
	
	/**
	* 接单对象切换触发
	*/
	function changeAcceptObjSelect(select){
		if(select.value==0){//显示接单对象为用户
			jq("#acceptDeptName").hide();
			jq('#acceptDept').val('');
	        jq('#acceptDeptName').val('');
			jq("#acceptUserName").show();
		}else{//显示接单对象为部门
			jq("#acceptDeptName").show();
			jq("#acceptUserName").hide();
			jq('#acceptUser').val('');
	        jq('#acceptUserName').val('');
		}
	}
	
	/**
	* 点击统计
	*/
	function statisticSheet(){
		var checkedItemValueList = '';
		var statisticsItemList = document.getElementsByName('statisticsItem');
		for (i = 2 ; i < statisticsItemList.length; i ++) {
			if (statisticsItemList[i].checked) {
				var checkedItemValue = statisticsItemList[i].value;
				checkedItemValueList = checkedItemValueList + checkedItemValue;
			}
		}
		if(checkedItemValueList == ''){
			alert('除了区域和巡检单位外的统计项必须至少选择一项');
			return;
		}
		jq('#excal').val('');
		document.getElementById("statisticForm").submit();
	}
	
	function res(){
	    jq('#year').val('');
		jq('#month').val('');
		jq('#startTime').val('');
		jq('#endTime').val('');
		jq('#sendUser').val('');
		jq('#sendUserName').val('');
		jq('#sendDept').val('');
		jq('#sendDeptName').val('');
		jq('#acceptDept').val('');
		jq('#acceptDeptName').val('');
		jq('#acceptUser').val('');
		jq('#acceptUserName').val('');
		jq('#areaIdSearch').val('');
		jq('#area').val('');
		jq('#maintainCompany').val('');
		jq('#maintainCompanyId').val('');
		jq('#mainSpecialty').val('');
		jq('#mainTaskType').val('');
	}
	
	function selectArea(areaid){
	   jq('#alinkareaid').val(areaid); 
	   jq('#excal').val(''); 
	   document.getElementById("statisticForm").submit();
	}
	function selectComp(compid){
	   jq('#alinkcompanyId').val(compid); 
	   jq('#excal').val(''); 
	   document.getElementById("statisticForm").submit();
	}
	function excel(){
	   jq('#excal').val('yes'); 
	   document.getElementById("statisticForm").submit();
	}
</script>

<%@ include file="/common/footer_eoms.jsp"%>