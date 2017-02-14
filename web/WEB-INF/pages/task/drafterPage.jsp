<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script src="${app}/scripts/widgets/treegrid/GridE.js"> </script>
<script src="${app}/scripts/widgets/treegrid/t.js"></script>
<style type="text/css">
#fields li{float:left;margin-right:3px;}
</style>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'search'});
});

function onFile(taskId, drafter) {
  window.open('${app}/task/accessories.do?taskId=' + taskId + '&drafter=' + drafter,'','width=600,height=300,scrollbars=yes');
}
</script>

<form id="search" method="post">
  <ul id="fields">
    <li>名称:<input type="text" class="text" id="sName" name="sName" value="${requestScope.sName}"/></li>
    <li>负责人:
			<select id="sPrincipal" name="sPrincipal" value="${requestScope.sPrincipal}">
				<option value="">全部</option>
				<logic:iterate id="userList" name="userList">
				<c:choose>
					<c:when test="${userList.userid == requestScope.sPrincipal}">
						<option value="${userList.userid}" selected>${userList.username}</option>
					</c:when>
					<c:otherwise>
						<option value="${userList.userid}">${userList.username}</option>
					</c:otherwise>
				</c:choose>
				</logic:iterate>
			</select>
	</li>
    <li>开始时间:
		<input type="text" class="text" id="sStartTime" name="sStartTime" readonly="readonly" 
			alt="vtype:'lessThen',link:'sEndTime',vtext:'开始时间必须早于结束时间'"
			onclick="popUpCalendar(this, this,null,null,null,false,-1);" value="${requestScope.sStartTime}"/>
	</li>
    <li>结束时间:
		<input type="text" class="text" id="sEndTime" name="sEndTime" readonly="readonly" 
			alt="vtype:'moreThen',link:'sStartTime',vtext:'结束时间必须晚于开始时间'"
			onclick="popUpCalendar(this, this,null,null,null,false,-1);" value="${requestScope.sEndTime}"/>
	</li>
    <li>状态:
			<select id="sStatus" name="sStatus" value="${requestScope.sStatus}">
				<c:choose>
					<c:when test="${requestScope.sStatus == '1'}">
						<option value="">全部</option>
						<option value="1" selected>已完成</option>
						<option value="0">未完成</option>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${requestScope.sStatus == '0'}">
								<option value="">全部</option>
								<option value="1">已完成</option>
								<option value="0" selected>未完成</option>
							</c:when>
							<c:otherwise>
								<option value="" selected>全部</option>
								<option value="1">已完成</option>
								<option value="0">未完成</option>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</select>
	</li>
    <li><input type="submit" value="查询任务" class="submit"/></li>
  </ul>
</form>

<br/><br/><br/>

<form action="?method=saveDrafterChanges">
<input type="hidden" name="grid" id="GridData">
<div>
<treegrid Layout_Data="&lt;Grid>
   &lt;Cfg id='TaskDrafter' Version='4'  MainCol='name'/>
   &lt;Cfg EmptyDate='' />
   &lt;Cfg FullId='1' NumberId='1' IdChars='0123456789'/>
   &lt;Cfg DropFree='1'/>
   &lt;Cfg ResizingMain='3' VarHeight='1' AcceptEnters='1' NoFormatEscape='1' MaxHeight='1' EnumKeys='1'/>
   &lt;Actions ClickButtonAdd='AddRowEnd' />
   &lt;Def>
      &lt;D Name='Task' Expanded='1' Calculated='1' CalcOrder='startTime,endTime,progress,G'/>
      &lt;D Name='Task' startTimeFormula='minimum(min(&quot;startTime&quot;),min(&quot;endTime&quot;))'/>
      &lt;D Name='Task' endTimeFormula='maximum(max(&quot;startTime&quot;),max(&quot;endTime&quot;))'/>
      &lt;D Name='Task' progressFormula='ganttpercent(&quot;startTime&quot;,&quot;endTime&quot;,&quot;d&quot;)'/>
      &lt;D Name='Task' GColor='240,240,240'/>
      &lt;D Name='Task' DButton=''/>
      &lt;D Name='Task' GGanttClass='GanttG' GGanttOptions='2'/>
   &lt;/Def>
   &lt;Panel Copy='7'/>
   &lt;LeftCols>
      &lt;C Name='id' Width='40' Type='Text' CanEdit='0' CanHide='1'/>
      &lt;C Name='name' Width='140' Type='Text' CanHide='1'/>
      &lt;C Name='content' Width='140' Type='Lines' ToolTip='1' CanHide='1'/>
      &lt;C Name='principal' Width='60' Type='Enum' Enum='${requestScope.principalEnum}' EnumKeys='${requestScope.principalEnumKey}' EnumType='1' ToolTip='0' CanHide='1'/>
      &lt;C Name='startTime' Width='80' Type='Date' Format='yyyy-MM-dd' CanHide='1'/>
      &lt;C Name='endTime' Width='80' Type='Date' Format='yyyy-MM-dd' CanHide='1'/>
      &lt;C Name='progress' Width='60' Type='Int' Format='##\%;;0\%' CanHide='1'/>
      &lt;C Name='remark' Width='100' Type='Lines' CanEdit='0' remarkVarHeight='0' ToolTip='1' CanHide='1'/>
      &lt;C Name='remind' Width='60' Type='Bool' CanSort='0' CanHide='1'/>
      <!-- &lt;C Name='nextId' Width='65' Type='Text' CanEdit='0' Button='Defaults' Defaults='|*RowsColid*VariableDef' Range='1'/> -->
      &lt;C Name='accessories' Width='40' Type='Html' CanEdit='0' CanFocus='0' CanSort='0' CanHide='1'/>
      &lt;C Name='taskId' Width='32' Type='Text' Visible='0'/>
   &lt;/LeftCols>
   &lt;Cols>
      &lt;C Name='G' CanSort='0' Type='Gantt' CanEdit='0' CanResize='0' MenuName='甘特图'
         GanttStart='startTime' GanttEnd='endTime' GanttComplete='progress' GanttDependencies='nextId' 
         GanttUnits='d' GanttChartRound='w' GanttOptions='17'
         GanttRight='1' 
         GanttBackground='1/6/2008~1/6/2008 0:01' GanttBackgroundRepeat='w'
         GanttHeader1='w' GanttFormat1='yy年M月d日>'
         GanttHeader2='d' GanttFormat2='ddddd'
         />
   &lt;/Cols>
   &lt;Header id='编号' name='任务' content='描述' principal='执行人' startTime='开始时间' endTime='结束时间' progress='进度' remark='完成情况描述' remind='短信提醒' accessories='附件' G='甘特图' />
   
   &lt;Panel 
      Visible='1' Select='0' Delete='1' Copy='1' CanResize='0' CanMove='0' CanDrag='1'
      />
	&lt;Toolbar Cells='Save,Add,ExpandAll,CollapseAll,Columns,Reload' Tag='' Visible='1' Styles='0' Copy='7'/>

&lt;/Grid>" 
	Data_Data="${taskData}" Upload_Tag="GridData">
</treegrid>
</div>
</form>
<script>

// --- For new rows changes parent row to group task ---
Grids.OnRowAdd = function(G,row){
var P = row.parentNode;
if(P.Def==G.Def.R) G.ChangeDef(P,"Task");
}

// --- When row is moved its new parent is changed to group task and the old parent if does not contain any children to standard task ---
Grids.OnRowMove = function(G,row,par){
var P = row.parentNode;
if(P.Def==G.Def.R) G.ChangeDef(P,"Task");
if(par.Def) { 
   for(var p=par.firstChild;p;p=p.nextSibling) if(p.Visible && !p.Deleted) break;
   if(!p) G.ChangeDef(par,"R");
   }
}

// --- When row is deleted and its parent does not contain any children, the parent is changed to standard task ---
Grids.OnRowDelete = function(G,row){
var par = row.parentNode;
if(par.Def) { 
   for(var p=par.firstChild;p;p=p.nextSibling) if(p.Visible && !p.Deleted) break;
   if(!p) G.ChangeDef(par,"R");
   }  
}

// --- For undeleted rows changes parent row to group task ---
Grids.OnRowUndelete = function(G,row){
var P = row.parentNode;
if(P.Def==G.Def.R) G.ChangeDef(P,"Task");
}

// --- 冲突检查 ---
Grids.OnAfterValueChanged = function(G, row, col) {
if (col == 'principal' || col == 'startTime' || col == 'endTime') { // 冲突检查触发条件列
	var taskId = G.GetString(row, 'taskId'); // 任务主键
	var principal = G.GetString(row, 'principal'); // 负责人，用户名称
	var startTime = G.GetString(row, 'startTime'); // 开始时间
	var endTime = G.GetString(row, 'endTime'); // 结束时间
	
	var principalIndex = G.GetEnumIndex(row, 'principal', principal); // 负责人数组下标
	var principalArray = '${requestScope.principalEnumKey}'; // 负责人Key数组
	
	principalArray = principalArray.split('|');
	
	principal =  principalArray[principalIndex + 1]; // 从Key值中取所选用户的Id
	
	// Ajax 后台冲突检查
	Ext.Ajax.request({
		method:'post',
		url: '${app}/task/tasks.do?method=checkTaskConflict',
		params : "principal=" + principal + "&startTime=" + startTime + "&endTime=" + endTime + "&taskId=" + taskId,
		success: function(x){
			var data = x.responseText;
			if ('' != data) {
				// 检查结果为冲突，弹出dialog提示
				G.ShowDialog(row,col,"<DIV style='background:#ffffaa;border:2px inset #ddaadd; padding:10px;'><i>检测到您安排给【" + G.GetString(row, 'principal') +"】的任务与下列任务有冲突</i><br><br>" + data + "<br><DIV align=center><BUTTON style='width:60;' onclick='Grids[0].CloseDialog();'>确定</BUTTON></DIV></DIV>");
			}
		}
	});	
	
	
}
}
</script>

<script type="text/javascript">
	if(location.hostname.search(/coqsoft\.|treegrid\./i)>=0) setTimeout('location.replace("../../../www/#..*Examples*Html*Gantt*GanttSimple.html"+location.hash.replace("#","\'"));',500);
</script>
<%@ include file="/common/footer_eoms.jsp"%>