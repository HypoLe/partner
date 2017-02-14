<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<%@page import="com.boco.eoms.commons.statistic.base.config.excel.* ,java.util.*"%>
<%
	Excel excelConfig =(Excel) request.getAttribute("excelConfig");
	if (excelConfig == null) throw new Exception("读取配置统计文件失败!");
	
	String excelConfigURL = String.valueOf(request.getAttribute("excelConfigURL"));
	String findListForward = String.valueOf(request.getAttribute("findListForward"));
	Calendar cld = Calendar.getInstance();
	int year = cld.get(Calendar.YEAR);
	int mondth = cld.get(Calendar.MONTH) + 1;
%>
<script type="text/javascript">
var	userTreeAction='${app}/xtree.do?method=dept';//部门树
var userByDeptTree='${app}/xtree.do?method=userFromDept';//部门用户树
var	roleTree='${app}/xtree.do?method=roleTree'; //角色树
var subroleFromDept='${app}/xtree.do?method=subroleFromDept'; //部门角色树
var	areaTree='${app}/xtree.do?method=areaTree'; //地域树

var v;
Ext.onReady(function(){
	v = new eoms.form.Validation({form:"theform"});

	//选择地域对话框
	/*
		areaTree = new xbox({
		btnId:'areaTreeBtn',dlgId:'dlg-area',
		treeDataUrl:areaTree,treeRootId:'-1',treeRootText:'${eoms:a2u("区域")}',treeChkMode:'',treeChkType:'area',
		showChkFldId:'areaName',saveChkFldId:'areaid' 
	});
	*/
	
	//部门角色树
	 roleTree = new xbox({
		btnId:'subroleFromDeptBtn',dlgId:'dlg-role',
		treeDataUrl:roleTree,treeRootId:'-1',treeRootText:'${eoms:a2u("角色")}',treeChkMode:'',treeChkType:'subrole',
		showChkFldId:'subroleFromDeptName',saveChkFldId:'revRoleId' 
	});
	
	//部门用户树
		userByDeptTree = new xbox({
		btnId:'userByDeptTreeBtn',dlgId:'dlg-user',
		treeDataUrl:userByDeptTree,treeRootId:'-1',treeRootText:'${eoms:a2u("人员")}',treeChkMode:'',treeChkType:'user',
		showChkFldId:'userByDeptName',saveChkFldId:'revUserId' 
	});
})

function displayTR(sel){

	//alert(sel.value);
	//alert('${eoms:a2u("")}');
	if(sel.value=="time")
	{
		eoms.form.enableArea("td2");
		eoms.form.disableArea("td1",true);
		eoms.form.disableArea("td3",true);
	}else if(sel.value=="month")
	{
		eoms.form.enableArea("td1");
		eoms.form.disableArea("td2",true);
		eoms.form.disableArea("td3",true);
	}else if(sel.value=="season")
	{
		eoms.form.enableArea("td3");
		eoms.form.disableArea("td1",true);
		eoms.form.disableArea("td2",true);
	}
	
	else if(sel.value==1)
	{
		eoms.form.disableArea("operateroletd",true);
		eoms.form.enableArea("operateusertd");
	}
	else if(sel.value==0)
	{
		eoms.form.disableArea("operateusertd",true);
		eoms.form.enableArea("operateroletd");
	}
	
	return;
}

	function validateCheck(data)
	{
		alert(data.getElementById("beginTime").value);
		alert(data.getElementById("endTime").value);
	}	
	
		
//alert(${"statspecial"}.value);

 
</script>
	
<html:form action="/stat.do?method=performStatistic" styleId="theform">


 <table class="formTable" >
   <caption>${eoms:a2u("输入条件")}</caption>   
   <tr>
   		<td>
			<input type="hidden" name="excelConfigURL" value="<%=excelConfigURL %>">  
			<input type="hidden" name="findListForward" value="<%=findListForward %>">  			
   		</td> 
   </tr>
   
   <tr>
     <td noWrap class="label">${eoms:a2u("选择报表")}</td>
     <td width="80%">

            	<select name="reportIndex" onchange="displayTR(this)">
            	<%
            	System.out.println(excelConfig.getSheets().length);
								for(int i=0; i<excelConfig.getSheets().length;i++){
									Sheet sheet = excelConfig.getSheets()[i];
							%>		
                 <option value="<%=sheet.getSheetIndex() %>"><%=sheet.getSheetName()%></option> 
							<%}%>
              </select>       		
              </td>
            </tr>
            <tr>
              <td noWrap class="label">
              <!-- <bean:message bundle="statistic" key="statistic.sendtime" /> -->
              ${eoms:a2u("派单季度")}
              <select name="type">
              	<option value="season">${eoms:a2u("季度")}</option>
              </select>
              </td>
              <td id="td3" >
              
              <select name="beginyear">
	              	<%
	              		for(int i=2001; i<= year+1;i++)
	              		{
	              			String select = "";
	              			if(year == i)
	              			{
	              				select = "Selected";
	              			}
	              	 %>
	              		<option value="<%=i%>" <%=select %>><%= i%></option>
	              	<%}%>
	              </select>
	            ${eoms:a2u("年")}
	              
              	<select name="seasonselect">
              		<option value="season_one">${eoms:a2u("第一季度")}</option>
              		<option value="season_two">${eoms:a2u("第二季度")}</option>
              		<option value="season_three">${eoms:a2u("第三季度")}</option>
              		<option value="season_four">${eoms:a2u("第四季度")}</option>
              	</select>
              </td>
              
            </tr>
             <td noWrap class="label">
             	${eoms:a2u("处理类型")}
             </td>
              
  		  <td id=operateusertd style="display:none;"><!-- alt="allowBlank:false" -->
  		  	<input type="button" value="${eoms:a2u('部门人员选择')}" id="userByDeptTreeBtn" class="btn" />
  		  	<input type="text"  name="userByDeptName"  value="" id ="userByDeptName" class="text" />
  		  	<input type="hidden" id="revUserId" value="" name="revUserId" />
  		  </td>
  		  
  		  <td id=operateroletd>
  		  	<!--
  		  	 <input type="button" value="${eoms:a2u('角色选择')}" id="roleTreeBtn" class="btn" />
  		 	 <input type="text"  name="subroleFromDeptName"  value="" id ="subroleFromDeptName" class="text" />
  		 	 <input type="hidden" id="revRoleId" name="revRoleId" />
  		 	   -->
  		 	    
  		 	 <input type="button" value="${eoms:a2u('部门角色选择')}" id="subroleFromDeptBtn" class="btn" />
  		 	 <input type="text"  name="subroleFromDeptName"  value="" id ="subroleFromDeptName" class="text" />
  		 	 <input type="hidden" id="revRoleId" value="" name="revRoleId" />
  		 	
  		  </td>
  		  </tr>
          </table>

  <br/>	
  <!-- buttons -->

     <html:submit styleClass="btn" property="method.save" styleId="method.save">
				<bean:message bundle="statistic" key="button.done"/>
     </html:submit>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>