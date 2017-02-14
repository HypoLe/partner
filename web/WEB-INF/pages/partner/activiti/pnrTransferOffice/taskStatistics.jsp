<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript"><!--
function alertData(num)
{
if(num==1){
	 isDisplay("trousub","inline-block");
	isDisplay("tasksub","none");
	/* isDisplay("trousub","visible");
	isDisplay("tasksub","hidden");*/
	comm("trousub",true);
	comm("tasksub",false);

}else if(num==2){
	 isDisplay("tasksub","inline-block");
	isDisplay("trousub","none");
	/* isDisplay("tasksub","visible");
	isDisplay("trousub","hidden");*/
	comm("tasksub",true);
	comm("trousub",false);

}else if(num==3){
	isDisplay("tasksub","inline-block");
	isDisplay("trousub","inline-block");
	/*isDisplay("tasksub","visible");
	isDisplay("trousub","visible");*/
	comm("tasksub",true);
	comm("trousub",true);
}

 
}
function comm(id,flag){
	var tasksub = document.getElementById(id);
 	var input = tasksub.getElementsByTagName("input");
	 for(var i = 0; i < input.length; i ++)
           {
               if(input[i].type == "checkbox")
                {                
                input[i].checked=flag;                          
                 
                }
           }
 
}
function isDisplay(id,display){

	 document.getElementById(id).style.display=display;
	// document.getElementById(id).style.visibility=display;
}

--></script>
<div id="sheetform">
	<html:form action="/pnrTransferOffice.do?method=workOrderStatisticstijiao" styleId="theform">
		<table class="formTable">
	
           <tr>
		   <!-- 工单类型 -->
              <td class="label">
                工单类型
              </td>
              <td colspan="3">
				<input type="radio" value="transferOffice" name="taskType" onclick="alertData(1)" />抢修工单&nbsp;&nbsp;
				<input type="radio" value="interface" name="taskType" onclick="alertData(2)"/>预检预修工单&nbsp;&nbsp;
				<input type="radio" value="all" name="taskType" onclick="alertData(3)" checked />全部&nbsp;&nbsp;			
	 		 </td>
       
 		</tr>
           <tr>
		   <!-- 工单子类型 -->
              <td class="label">
                工单子类型
              </td>
              <td colspan="3">
              <span id="trousub">
              <c:forEach items="${transferOfficeList}" var="transferOffice">
              			<input type="checkbox" value="${transferOffice.dictId}" name="subType" checked="true"/>${transferOffice.dictName}&nbsp;&nbsp;
               </c:forEach>
               </span>
               <span id="tasksub">
              <c:forEach items="${transferInterfaceList}" var="task">
                        <input type="checkbox" value="${task.dictId}" name="subType" checked="true"/>${task.dictName}&nbsp;&nbsp;
              </c:forEach>
              </span>
	 		 </td>
       
 		</tr>
			<!--时间 -->
	   <tr>
		  <td class="label">计划开始时间</td>
		  <td class="content">
		    <input type="text" class="text" name="bTime" readonly="readonly" 
				id="bTime" value="${firstDay}" 
				onclick="popUpCalendar(this, this,null,null,null,false,-1)" alt="vtype:'lessThen',link:'eTime',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true"/>  
		  </td>
		  <td class="label">计划结束时间</td>
		  <td class="content">
		    <input type="text" class="text" name="eTime" readonly="readonly" 
				id="eTime" value="${lastDay}" 
				onclick="popUpCalendar(this, this,null,null,null,false,-1)" alt="vtype:'moreThen',link:'bTime',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true"/>   
		  </td>		  
		</tr>
		<tr>
</table>
		<!-- buttons -->
		<div class="form-btns">
		  	<html:submit styleClass="btn" property="method.save" styleId="method.save">
				<fmt:message key="button.done" />
			</html:submit>
		 </div>	 
</html:form>
</div>
<%@ include file="/common/footer_eoms.jsp"%>