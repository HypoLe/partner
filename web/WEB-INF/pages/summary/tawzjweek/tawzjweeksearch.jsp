<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/xtreelibs.jsp"%>
 <%@ page import="java.util.List,com.boco.eoms.summary.model.TawzjWeekSub,com.boco.eoms.summary.model.TawzjWeek,com.boco.eoms.base.util.StaticMethod,com.boco.eoms.commons.system.user.model.TawSystemUser,com.boco.eoms.summary.model.TawzjWeekCheck"%>
 
<script language="javascript">
Ext.onReady(function(){
	var	userTreeAction='${app}/xtree.do?method=userFromDept';
			new xbox({
				btnId:'cruserName',dlgId:'dlg-audit',dlgTitle:'请选择人员',
				treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'所有人员',treeChkMode:'',treeChkType:'user',
				showChkFldId:'cruserName',saveChkFldId:'cruser'
			}); 
	var	userTreeAction2='${app}/xtree.do?method=userFromDept';
			new xbox({
				btnId:'auditerName',dlgId:'dlg-audit',dlgTitle:'请选择人员',
				treeDataUrl:userTreeAction2,treeRootId:'-1',treeRootText:'所有人员',treeChkMode:'',treeChkType:'user',
				showChkFldId:'auditerName',saveChkFldId:'auditer'
			}); 
})
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawzjWeekForm'});
});
//-->

</script>
<html:form action="tawzjweek.do?method=searchList" method="post" styleId="tawzjWeekForm"> 

	<table border="0" width="600" class="formTable">
		<caption>
			查询
		</caption>
		<tr>
			<TD class='label' width="15%">
				年周(从)
			</TD>
			 
			<TD width="18%" colspan='2'>
			  <select name="yearFlag1" class="select">
        <%
          String yyyy =  (String)request.getAttribute("year");
          int year = StaticMethod.null2int(yyyy);
          for (int i = year;i>year-10;i--){
              if(i==year){
        %>
          <option value='<%=i%>' selected><%=i%></option>
        <%    }else{%>
          <option value='<%=i%>'><%=i%></option>
        <%    }
          }
        %>
        </select>
			年<input type="text" name="week1" >周
			</TD>
			<TD class='label' width="15%">
				年周(到)
			</TD>
			<TD width="18%" colspan='2'>
			  <select name="yearFlag2" class="select">
        <%
          String yyyy1 =  (String)request.getAttribute("year");
          int year1 = StaticMethod.null2int(yyyy1);
          for (int i = year1;i>year-10;i--){
              if(i==year1){
        %>
          <option value='<%=i%>' selected><%=i%></option>
        <%    }else{%>
          <option value='<%=i%>'><%=i%></option>
        <%    }
          }
        %>
        </select>
			年<input type="text" name="week2" >周
			</TD>
		</tr>
		<tr>
			<TD class='label' width="15%">
				申请人
			</TD>
			<TD width="15%">
			<html:text property="cruserName" styleId="cruserName"
				styleClass="text medium" readonly="true" /> 
			<html:hidden property="cruser" />
			</TD>
			<TD class='label' width="15%">
				状态
			</TD>
			<TD width="15%">
			<eoms:dict key="dict-summary" dictId="state" beanId="selectXML"  isQuery="false"  selectId="state"/>
			</TD>
			<TD class='label' width="15%">
				审核人
			</TD>
			<TD width="15%">
			<html:text property="auditerName" styleId="auditerName"
				styleClass="text medium" readonly="true" /> 
			<html:hidden property="auditer" />
			</TD>
		</tr>

	</table>
	<br>
	<input type="submit" value="查询" name="B1" class="submit">
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>

