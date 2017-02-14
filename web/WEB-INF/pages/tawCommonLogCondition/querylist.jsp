<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page import="java.util.*,java.lang.*, org.apache.struts.util.*,com.boco.eoms.common.util.StaticMethod"%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker4.7.2/WdatePicker.js"></script>
<% GregorianCalendar cal_start = new GregorianCalendar();
      cal_start.add(cal_start.DATE,-1);
      String str_start = StaticMethod.Cal2String(cal_start);
      str_start = String.valueOf(StaticMethod.getVector(str_start," ").elementAt(0));
%>
<style>
#tabs {
	width:90%;
}
#tabs .x-tabs-item-body {
	display:none;
	padding:10px;
}
</style>
<script type="text/javascript">
var Tabs = {
    init : function(){
        var tabs = new Ext.TabPanel('tabs');
        tabs.addTab('form', '${eoms:a2u('日志查询')}');
        tabs.addTab('info', '${eoms:a2u('帮助')}');
        tabs.activate('form');
     }
}
Ext.onReady(Tabs.init, Tabs, true);
</script>
<div id="tabs">
<div id="form" class="tab-content">
<html:form action="/TawCommonLogCondition/querydo">
<table id="sheet" class="listTable">
       
	<tr >
		<td class="label">${eoms:a2u('用户ID')}</td>
		<td >
		<html:text property="searchbyuser" styleClass="text" title="USERID"/>
		<html:hidden property="searchbyoper" value="${searchbyoper}"/>
	   </td>
		<td class="label">${eoms:a2u('用户IP')}</td>
		<td >
		<input type="text" class="text" name="searchbyremoteip"  title="USERIP">
	   </td>
    </tr>
 <!--  tr >
		<td class="label">MODELID</td>
		<td >
		<html:text property="searchbymodel"  title="MODELID"/>
	   </td>
 </tr>
  <tr >
		<td class="label">OPERID</td>
		<td >
		<html:text property="searchbyoper"  title="OPERID"/>
	   </td>
 </tr>-->
 <tr >
		<td class="label">${eoms:a2u('开始时间')}</td>
		<td >
		<!-- html:text property="searchbystarttime"  title="STARTTIME"/>-->
		<input id="d4311" name="starttime" class="Wdate text" type="text" readonly="readonly" value="${minDay}"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'%y-%M-%d\'}'})"/>
		<!-- 
		<eoms:SelectDate name="searchbyendtime" formName="TawCommonLogConditionForm" flag = "0" value = "<%=str_start%>"/> -->
	   </td>
		<td class="label">${eoms:a2u('结束时间')}</td>
		<td >
		<!--html:text property="searchbyendtime"  title="ENDTIME"/-->
		<input id="d4312" name="endtime" class="Wdate text" readonly="readonly" value="${nowDay}" type="text" onfocus="WdatePicker({maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'d4311\')}'})"/>
	   </td>
 </tr>
 <tr >
	<td class="label">${eoms:a2u('日志类型')}</td>
	<td >
	<eoms:dict key="dict-log" dictId="issucess" isQuery="true" selectId="issucess" beanId="selectXML" cls="select" />
	<!--  select name="issucess">
    <option value="all">ALL</option>
    <option value="sucess">SUCESS</option>
    <option value="error">ERROR</option>
  </select>-->
  </td>
  <td class="label">${eoms:a2u('操作模块')}</td>
		<td >
		<select class="select" name="code">
			<option value="">${eoms:a2u('--请选择--')}</option>
			<c:forEach items="${list}" var="model">
				<option value="${model.code}">${model.name}</option>
			</c:forEach>
		</select>
	   </td>
 </tr>
 <tr>
 	<td class="label">${eoms:a2u('操作时间')}</td>
		<td >
		<input id="oreraDate" name="opreatetime" class="Wdate text" readonly="readonly"  type="text" onfocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	   </td>
 	<td class="label">${eoms:a2u('操作内容')}</td>
		<td >
		<input type="text" class="text" name="notemessage"  >
	   </td>
 </tr>
</table>
<table>
		    <tr>
			    <td>
			    	<html:reset styleClass="btn">
    				${eoms:a2u('重置')}
    				</html:reset>
                    <html:submit property="strutsButton" styleClass="btn">
                     ${eoms:a2u('查询')}
                    </html:submit>
				</td>
			</tr>
		</table>
  
</html:form>
</div>
  <div id="info">
  	<dl>
  		<dt>${eoms:a2u('日志管理')}</dt>
        <dd>${eoms:a2u('系统日志管理提供对系统日志的查询和统计功能。')}</dd>
		<dt>${eoms:a2u('查询')}</dt>
        <dd>${eoms:a2u('输入日志查询条件，查询结果。')}</dd>
    </dl>
  </div>
</div>
</body>
</html>
