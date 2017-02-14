<%@ page language="java" import="java.util.*,com.boco.eoms.commons.mms.base.config.*,com.boco.eoms.commons.mms.mmsreporttemplate.model.*,com.boco.eoms.base.util.StaticMethod" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
//提交动作
function submitCheck(){
 if(document.forms[0].mmsreport_templateId.value=='0'){
    alert( "请选择彩信报表名称" );
    return false;
  }else if(document.forms[0].receivePerson.value==null||document.forms[0].receivePerson.value==''){
    alert( "请选择接收人");
    return false;  
  }
  document.forms[0].submit();
}  

//预览动作
function submitPreview(){
  //window.open("http://127.0.0.1:8085/eoms"); 
  //alert('${app}/mms/preview/previewMms.jsp' + '?' + 
  //'mmsreport_templateId=' + document.forms[0].mmsreport_templateId.value
  //+'&receivePerson=' + document.forms[0].receivePerson.value
  //+'&receiveTime_hour=' + document.forms[0].receiveTime_hour.value
  //+'&receiveTime_min=' + document.forms[0].receiveTime_min.value
  //);
  
  if(document.forms[0].mmsreport_templateId.value=='0'){
    alert( "请选择彩信报表名称" );
    return false;
  }else if(document.forms[0].receivePerson.value==null||document.forms[0].receivePerson.value==''){
    alert( "请选择接收人");
    return false;  
  }
  
  //打开预览页面
  window.open('${app}/mms/preview/previewMms.jsp' + '?' + 
  'mmsreport_templateId=' + document.forms[0].mmsreport_templateId.value
  +'&receivePerson=' + document.forms[0].receivePerson.value
  +'&receiveTime_hour=' + document.forms[0].receiveTime_hour.value
  +'&receiveTime_min=' + document.forms[0].receiveTime_min.value
  ); 
} 


var userByDeptTree='${app}/xtree.do?method=userFromDept';//部门用户树
var v;
Ext.onReady(function(){
	//v = new eoms.form.Validation({form:"theform"});
	//部门用户树
	userByDeptTree = new xbox({
		btnId:'userByDeptTreeBtn',dlgId:'dlg-user',
		treeDataUrl:userByDeptTree,treeRootId:'-1',treeRootText:'人员',treeChkMode:'',treeChkType:'user',
		showChkFldId:'userByDeptName',saveChkFldId:'receivePerson' 
	});
})
</script>

<%
	List mmsreportTemplateList = (List)request.getAttribute("mmsreportTemplateList");
	String userid = String.valueOf(request.getAttribute("userid"));
%>
<html:form action="/msssubscribes.do?method=save" styleId="msssubscribeForm" method="post"> 

<fmt:bundle basename="config/applicationResources-mms">

<table class="formTable">
	<caption>
		<div class="header center">定阅彩信报</div>
	</caption>

 <tr class="tr_show">

				<td class="label" nowrap="nowrap" width="15%">
					彩信报名称
				</td>
				
				<td noWrap width="85%">
					<select name="mmsreport_templateId" style="width:4.6cm;">
						<option value="0">--请选择彩信报表名称--</option>
					<%
						if (mmsreportTemplateList != null) {
							for (int n = 0; n < mmsreportTemplateList.size(); n++) {
								MmsreportTemplate mmsreportTemplate = (MmsreportTemplate)mmsreportTemplateList.get(n);
								String id = mmsreportTemplate.getId();
								String name = mmsreportTemplate.getMmsName();
					%>
					<option value="<%=id%>">
						<%=name%>
					</option>
					<%
						}
					}
					%>
		
					</select>
				</td>

			</tr>
	<!-- 
	<tr class="tr_show">
         <td class="label" width="15%" nowrap="nowrap" >
             彩信接收人
        </td>
		
		<td width="500" colspan="5">
		  <input type="button" name="clkuser" id="clkuser" value="请选择人员" class="btn"/>
		  <input type="hidden" name="showuser" id="showuser" value="" class="text" alt="allowBlank:false"/>
		  <input type="hidden" name="receivePerson" id="receivePerson"/>
		  <eoms:xbox id="userTree" dataUrl="${app}/xtree.do?method=userFromDept" rootId="-1" rootText="人员树" valueField="receivePerson" handler="clkuser" checktype="user"
			textField="showuser" viewer="true">
	 	 </eoms:xbox>
        </td>
    </tr>
     -->
    
    
    	<%
	 		//只有超级用户才可以查阅其他人订阅的报表
	 		if("admin".equalsIgnoreCase(userid))
	 		{
	 	%>		
 			<tr>
 				<td class="label">
 					彩信接收人
 				</td>
 			
		 		<td><!-- alt="allowBlank:false" -->
		  		  	<input type="button" value="请选择人员" id="userByDeptTreeBtn" class="btn" /><!-- 部门人员选择 -->
		  		  	<input type="text"  name="userByDeptName"  value="" id ="userByDeptName" class="text" />
		  		  	<input type="hidden" id="receivePerson" value="" name="receivePerson" />
	  		  	</td>
		 	</tr>
	 	<%		
	 		}
	 		else
	 		{
	 	 %>
	 	 	<input type="hidden" name="receivePerson" value="<%=userid %>"/>
	 	 <%
			}	 	 
	 	  %>
    
 	<tr class="tr_show">
          <td class="label" nowrap="nowrap"  width="15%">
    	          接收时间
    	   </td>
    	       <td width="500" colspan="5">
	        	<select name="receiveTime_hour">
        				
        				<%
        				String[] hours = {"09","10","11","14","15","16"};
        				for(int i=0;i<hours.length;i++)
        				{
        				%>
        					<option value=<%=hours[i]%>><%=hours[i]%></option>
        				<%
        				}
        				%>	
		    	   	</select>时
		    	   	<select name="receiveTime_min">
		    	   		<%
        				String[] mins = {"05","10","15","20","25","30","35","40","45","50","55"};
        				for(int j=0;j<mins.length;j++)
        				{
        				%>
        					<option value=<%=mins[j]%>><%=mins[j]%></option>
        				<%
        				}
        				%>	
		    	   	</select>分 
	  </td>
    </tr>
    	
	

</table>

</fmt:bundle>
  
<table>
	<tr>
		<td>
			<input type="button" class="btn" value="订阅"  return onclick=submitCheck() />
		</td>
		
		<td>
			<input type="button" class="btn" value="预览"  return onclick=submitPreview() />
		</td>
	</tr>
</table>
<html:hidden property="id" value="${msssubscribeForm.id}" />

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>