<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突

var v;

   var operatingPostJ = '${iPnrPartnerAppOpsUser.operatingPostJ}';
  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
  
   var defaultUserBorth = '${iPnrPartnerAppOpsUser.userBorth}';
          defaultUserBorth = defaultUserBorth.substr(0,4);
             
   var defaultWorkTime = '${iPnrPartnerAppOpsUser.workTime}';
       defaultWorkTime = defaultWorkTime.substr(0,4);
   var defaultCompanyTime = '${iPnrPartnerAppOpsUser.companyTime}';
       defaultCompanyTime = defaultCompanyTime.substr(0,4);
   
   var defaultAppopsWorkTime = '${iPnrPartnerAppOpsUser.appopsWorkTime}';
       defaultAppopsWorkTime = defaultAppopsWorkTime.substr(0,4);
   
   var userBorth = document.all.userBorth;
  		  for(var i=0;i<userBorth.length;i++) {
  		  	var userBorths = ","+userBorth.options[i].value+",";
            if(userBorths.indexOf(defaultUserBorth)>-1){
                 userBorth.options[i].selected=true;
              }
         }  	
   var workTime = document.all.workTime;
  		  for(var i=0;i<workTime.length;i++) {
  		  	var workTimes = ","+workTime.options[i].value+",";
            if(workTimes.indexOf(defaultWorkTime)>-1){
                 workTime.options[i].selected=true;
              }
         }  	

   var companyTime = document.all.companyTime;
  		  for(var i=0;i<companyTime.length;i++) {
  		  	var companyTimes = ","+companyTime.options[i].value+",";
            if(companyTimes.indexOf(defaultCompanyTime)>-1){
                 companyTime.options[i].selected=true;
              }
         }  
         	
   var appopsWorkTime = document.all.appopsWorkTime;
  		  for(var i=0;i<appopsWorkTime.length;i++) {
  		  	var appopsWorkTimes = ","+appopsWorkTime.options[i].value+",";
            if(appopsWorkTimes.indexOf(defaultAppopsWorkTime)>-1){
                 appopsWorkTime.options[i].selected=true;
              }
         }  
         	
   });

 function setOptionValue(id,strings,split)
	{	
		var valueArray = strings.split(split);
		var arrLength = valueArray.length;
		
		var obj = document.getElementById(id);
		var length = obj.options.length;
		// 下拉列表中所有的项
		for(var i =0;i<length;i++)
		{	//默认选择数组项
			for(var j =0;j<arrLength;j++)
			{
				if(obj.options[i].getAttribute("value")==valueArray[j])
				{
					obj.options[i].selected=true;
				}
			}
		}
	}
</script>
<script language="javascript" for="document" event="onkeydown">
    if (event.keyCode == 13)
    {
        isEmail(document.getElementById('email').value);
    }  
</script>
<script type="text/javascript">
	 function openImport(){
	 	var handler = document.getElementById("openQuery");
		var el = Ext.get('listQueryObject'); 
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开导入界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭导入界面";
		}
	}
	/**设置年龄
	*/
	function setAge(value){
		var userAge = document.getElementById("userAge");
		var d = new Date();
		var nowYear = d.getFullYear();
		userAge.value=parseInt(nowYear)-parseInt(value);
	}
	/**设置工龄
	*/
	function setWorkAge(value){
		var workNumber = document.getElementById("workNumber");
		var d = new Date();
		var nowYear = d.getFullYear();
		workNumber.value=parseInt(nowYear)-parseInt(value);
	}
	/**设置司龄
	*/
	function setCompanyAge(value){
		var companyNumber = document.getElementById("companyNumber");
		var d = new Date();
		var nowYear = d.getFullYear();
		companyNumber.value=parseInt(nowYear)-parseInt(value);
	}
	/**设置维护年限
	*/
	function setMaintainyAge(value){
		var appopsNumber = document.getElementById("appopsNumber");
		var d = new Date();
		var nowYear = d.getFullYear();
		appopsNumber.value=parseInt(nowYear)-parseInt(value);
	}
	
function saveImport() {
	//表单验证
	new Ext.form.BasicForm('importForm').submit({
		method : 'post',
		url : "${app}/partner/appops/partnerAppOpsUsers.do?method=importAppopsUser",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
		},
		failure : function(form, action) {
			alert(action.result.infor);
		}
    });
}
function changeSpecially(){
	
	var name = document.getElementById("specialty").value;
	var second = document.getElementById("specialtySecond");
	second.value=name;
	second.fireEvent("onchange");
	
}

</script>




<script type="text/javascript">
//验证手机号码
function checkMobile()
  {
	  var mobile = document.getElementById('phone').value;
      if(mobile=="")
      {
    	 alert('手机号码不能为空');
    	 document.getElementById('phone').value="";
         return false;
      }
        
      var myreg = /^1[3|4|5|8][0-9]\d{4,8}$/;
      if(!myreg.test(mobile))
      {
          alert('请输入有效的11位手机号码！');
          document.getElementById('phone').value="";
          return false;
      }
      
      //验证手机号码是否已存在，校验唯一性
      var userId=document.getElementById('userId').value;
      var urlStr = "${app}/partner/appops/partnerAppOpsUsers.do?method=checkMobile";
      jq.ajax( {
			type : "POST",
			url : urlStr, 
			data : {"mobile": mobile,"userId":userId},
			success : function(data){ // 回调函数
				if(data==1){
					alert("手机号码已存在！");
					jq("#phone").val("");
					return false;
				}
			}	
		});
      
     
      return true;
  }
  
  function selectTeam(){
 		var dept_value = document.getElementById("dept").value;
		xbox_teamTree.defaultTree.root.id=dept_value;
		xbox_teamTree.reloadTree();
		xbox_teamTree.show();
	}
 
  
</script>


<div align="center"><b>人员信息编辑</div><br><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer">从Excel导入</span>
</div>
<div id="listQueryObject" style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="partnerAppOpsUsers.do?method=importAppopsUser" method="post" 	enctype="multipart/form-data" id="importForm" name="importForm">
				<fieldset>
					<legend>
						从Excel导入
					</legend>
					<table class="formTable">
						<tr>
							<td class="label">
								选择Excel文件
							</td>
							<td width="35%">
								<input id="importFile" type="file" name="importFile"	class="file" alt="allowBlank:false" />
							</td>
						</tr>
						<tr>
							<td class="label">
								模板下载
							</td>
							<td>
								<input type="button" class="btn" value="下载导入文件模板" 
								onclick="javascript:location.href='${app}/partner/appops/partnerAppOpsUsers.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/><br/>
<div align="center"><b>人员信息</div><br><br/>
<html:form action="/partnerAppOpsUsers.do?method=addAppOpsUser" styleId="theform" >
<input type="hidden" value="${iPnrPartnerAppOpsUser.id}" name="id" id="userId" />

<table class="formTable">
		<tr>
				<td class="label">
					分公司&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="company" id="company" initDicId="10208"
						defaultValue="${iPnrPartnerAppOpsUser.company}"	alt="allowBlank:false" styleClass="select"/>
				</td>
				<td class="label">
					员工编号&nbsp;
				</td>
				<td class="content">
					<html:text property="userNumber" styleId="userNumber"
						styleClass="text medium" 
						value="${iPnrPartnerAppOpsUser.userNumber}" />
				</td>
			</tr>
			<tr>
				<td class="label">
					姓名&nbsp;*
				</td>
				<td class="content">
					<html:text property="userName" styleId="userName"
						styleClass="text medium" 
						value="${iPnrPartnerAppOpsUser.userName}" alt="allowBlank:false"/>
				</td>
				<td class="label">
					性别&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="userSex" id="userSex" initDicId="1120202"
						defaultValue="${iPnrPartnerAppOpsUser.userSex}"	alt="allowBlank:false" styleClass="select"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					出生年份&nbsp;*
				</td>
			
				<td class="content">
					  <select id="userBorth" name="userBorth" onChange="setAge(this.value);" class="select" >
		              	 <% int currentYear = StaticMethod.nullObject2int(StaticMethod.getCurrentDateTime("yyyy"));
		              	 for(int i=currentYear;i>=currentYear-100;i--) { 
		              	    if(i==currentYear){
		              	    %>
		               	   		<option value="<%=i%>" selected="selected"><%=i%></option>
		               	   <%} else{%>
		               	   <option value="<%=i%>"><%=i%></option>
		               	   <%}} %>
		               </select>
					
				</td>
				
				
				<td class="label">
					年龄&nbsp;
				</td>
				<td class="content">
					<html:text property="userAge" styleId="userAge" readonly="true"
						styleClass="text medium"
						value="${iPnrPartnerAppOpsUser.userAge}" alt="allowBlank:false"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					固定电话&nbsp;
				</td>
				<td class="content">
					<html:text property="telephone" styleId="telephone"
						styleClass="text medium"
						value="${iPnrPartnerAppOpsUser.telephone}" />
				</td>
				<td class="label">
					手机&nbsp;*
				</td>
				<td class="content">
				<html:text property="phone" styleId="phone" styleClass="text medium"
					value="${iPnrPartnerAppOpsUser.phone}"
					alt="allowBlank:false,vtext:'',vtype:'phonemobile'"
					onblur="checkMobile();"  
					 />
			</td>
			</tr>
			<tr>
				<td class="label">
				邮箱&nbsp;*
				</td>
				<td class="content">
					<html:text property="email" styleId="email"
						styleClass="text medium" 
						value="${iPnrPartnerAppOpsUser.email}" alt="allowBlank:false,vtext:'',vtype:'email'"/>
				</td>

				<td class="label">
					参加工作时间&nbsp;*
				</td>
				<td class="content">
					                    
                        <select id="workTime" name="workTime" onChange="setWorkAge(this.value);" class="select" >
		              	 <% 
		              	 for(int i=currentYear;i>=currentYear-100;i--) { 
		              	    if(i==currentYear){
		              	    %>
		               	   		<option value="<%=i%>" selected="selected"><%=i%></option>
		               	   <%} else{%>
		               	   <option value="<%=i%>"><%=i%></option>
		               	   <%}} %>
		               </select>
				</td>
			</tr>
			<tr>
				<td class="label">
					工龄&nbsp;
				</td>
				<td class="content">
					<html:text property="workNumber" styleId="workNumber" readonly="true"
						styleClass="text medium"
						value="${iPnrPartnerAppOpsUser.workNumber}" alt="allowBlank:false" />
				</td>
				<td class="label">
					入司时间&nbsp;*
				</td>
				<td class="content">
			
				      <select id="companyTime" name="companyTime" onChange="setCompanyAge(this.value);" class="select" >
		              	 <% 
		              	 for(int i=currentYear;i>=currentYear-80;i--) { 
		              	    if(i==currentYear){
		              	    %>
		               	   		<option value="<%=i%>" selected="selected"><%=i%></option>
		               	   <%} else{%>
		               	   <option value="<%=i%>"><%=i%></option>
		               	   <%}} %>
		               </select>
				</td>
			</tr>
			<tr>
				<td class="label">
					司龄&nbsp;
				</td>
				<td class="content">
					<html:text property="companyNumber" styleId="companyNumber" readonly="true"
						styleClass="text medium"
						value="${iPnrPartnerAppOpsUser.companyNumber}" alt="allowBlank:false" />
				</td>
				<td class="label">
					从事维护工作时间&nbsp;*
				</td>
				<td class="content">
			
				      <select id="appopsWorkTime" name="appopsWorkTime" onChange="setMaintainyAge(this.value);" class="select" >
		              	 <% 
		              	 for(int i=currentYear;i>=currentYear-50;i--) { 
		              	    if(i==currentYear){
		              	    %>
		               	   		<option value="<%=i%>" selected="selected"><%=i%></option>
		               	   <%} else{%>
		               	   <option value="<%=i%>"><%=i%></option>
		               	   <%}} %>
		               </select>
				</td>
			</tr>
			<tr>
				<td class="label">
					从事维护工作年限&nbsp;
				</td>
				<td class="content">
					<html:text property="appopsNumber" styleId="appopsNumber"
						styleClass="text medium"
						value="${iPnrPartnerAppOpsUser.appopsNumber}" />
				</td>
				<td class="label">
					最高学历&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="highestDegree" id="highestDegree" initDicId="10201"
						defaultValue='${iPnrPartnerAppOpsUser.highestDegree}'	alt="allowBlank:false,vtext:''" styleClass="select" />
				</td>
			</tr>
			<tr>
				<td class="label">
					最高学历毕业院校&nbsp;*
				</td>
				<td class="content">
					<html:text property="school" styleId="school"
						styleClass="text medium"
						value="${iPnrPartnerAppOpsUser.school}" alt="allowBlank:false,vtext:''" />
				</td>
				<td class="label">
					第一学位&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox name="firstDegree" id="firstDegree" initDicId="10202"
						defaultValue='${iPnrPartnerAppOpsUser.firstDegree}'
						alt="allowBlank:true,vtext:''" styleClass="select"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					第一学位专业&nbsp;
				</td>
				<td class="content">
					<html:text property="firstSpecialty" styleId="firstSpecialty"
						styleClass="text medium" 
						value="${iPnrPartnerAppOpsUser.firstSpecialty}" alt="allowBlank:true,vtext:''"/>
				</td>
				<td class="label">
					第二学位&nbsp;
				</td>
				<td class="content">
					<eoms:comboBox name="secondDegree" id="secondDegree" initDicId="10202"
						defaultValue='${iPnrPartnerAppOpsUser.secondDegree}'	
						alt="allowBlank:true,vtext:''" styleClass="select"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					第二学位专业&nbsp;
				</td>
				<td class="content">
					<html:text property="secondSpecialty" styleId="secondSpecialty"
						styleClass="text medium"
						value="${iPnrPartnerAppOpsUser.secondSpecialty}" alt="allowBlank:true,vtext:''"/>
				</td>
				<td class="label">
					其它证书&nbsp;
				</td>
				<td class="content">
					<html:text property="certificate" styleId="certificate"
						styleClass="text medium"
						value="${iPnrPartnerAppOpsUser.certificate}" alt="allowBlank:true,vtext:''"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					员工类别&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="workSort" id="workSort" initDicId="10203"
						defaultValue='${iPnrPartnerAppOpsUser.workSort}' 
						alt="allowBlank:false,vtext:''" styleClass="select"/>
				</td>
				<td class="label">
					职称&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="jobTitle" id="jobTitle" initDicId="10204"
						defaultValue='${iPnrPartnerAppOpsUser.jobTitle}'	alt="allowBlank:false,vtext:''" styleClass="select"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					用工类别&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="useSort" id="useSort" initDicId="10205"
						defaultValue='${iPnrPartnerAppOpsUser.useSort}'	alt="allowBlank:false,vtext:''" styleClass="select"/>
				</td>
				<td class="label">
					岗位职级&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="jobLevel" id="jobLevel" initDicId="10207"
						defaultValue='${iPnrPartnerAppOpsUser.jobLevel}'	alt="allowBlank:false,vtext:''" styleClass="select"/>
				</td>
			</tr>
			<tr>
				
				<td class="label">
					管理层级&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="managerLevel" id="managerLevel" initDicId="10206"
						defaultValue='${iPnrPartnerAppOpsUser.managerLevel}'	alt="allowBlank:false,vtext:''" styleClass="select"/>

				</td>
			
				<td class="label">
					所在部门&nbsp;*
				</td>
				
				<td class="content">
					<input type="text" class="text medium" name="deptName" id="deptName"
						value="<eoms:id2nameDB id="${iPnrPartnerAppOpsUser.dept}" beanId="partnerAppopsDeptDao"/>"
						alt="allowBlank:false" readonly="readonly" />
					<input name="dept" id="dept" value="${iPnrPartnerAppOpsUser.dept}" type="hidden" />
					<eoms:xbox id="deptTree"
						dataUrl="${app}/xtree.do?method=getPnrDeptWithRightDW&isPartner=${isPartner}"
						rootId="" rootText="组织树" valueField="dept" handler="deptName"
						textField="deptName" checktype="dept" single="true"  />
				</td>
				
				</tr>
			<tr>
				<td class="label">
					所在班组&nbsp;*
				</td>
				
				<td class="content">
					<input type="text" class="text medium" name="teamName" id="teamName" 
						value="<eoms:id2nameDB id="${iPnrPartnerAppOpsUser.team}" beanId="partnerAppopsDeptDao"/>"
						alt="allowBlank:false" readonly="readonly" onclick="selectTeam()"/>
					<input name="team" id="team" value="${iPnrPartnerAppOpsUser.team}" type="hidden" />
					<eoms:xbox id="teamTree"
						dataUrl="${app}/xtree.do?method=getPnrDeptWithRightDW&isPartner=${isPartner}"
						rootId="" rootText="组织树" valueField="team" handler="teamName"
						textField="teamName" checktype="dept" single="true" />
				</td>
				<td class="label">
					专业类别&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="specialty" id="specialty" initDicId="10209"
						defaultValue='${iPnrPartnerAppOpsUser.specialty}'	alt="allowBlank:false,vtext:''" sub="operatingPostZ" styleClass="select" onchange="changeSpecially()"/>
					<div style="display:none">
						<eoms:comboBox name="specialty" id="specialtySecond" initDicId="10209"
						defaultValue='${iPnrPartnerAppOpsUser.specialty}'	alt="allowBlank:false,vtext:''" sub="operatingPostJ" styleClass="select" />
					</div>
				</td>
				</tr>
			<tr>
				<td class="label">
					工作岗位（专职）&nbsp;*
				</td>
				<td class="content">
				    <eoms:comboBox name="operatingPostZ" id="operatingPostZ" initDicId="${iPnrPartnerAppOpsUser.specialty}"
						defaultValue='${iPnrPartnerAppOpsUser.operatingPostZ}'	alt="allowBlank:false,vtext:''" styleClass="select"/>

				</td>
			<td class="label">
					工作岗位（兼职）&nbsp;*
				</td>
				<td class="content">
			<!-- 		<eoms:comboBox name="operatingPostJ" id="operatingPostJ" initDicId="${iPnrPartnerAppOpsUser.specialty}"
							defaultValue='' alt="allowBlank:false,vtext:''" multiple="true" styleClass="select"/>  -->
							
						<input type="text" class="text medium" name="operatingPostJName" id="operatingPostJName"
						value="${iPnrPartnerAppOpsUser.operatingPostJName}"
						alt="allowBlank:false" readonly="readonly" />
							<input name="operatingPostJ" id="operatingPostJ" value="${iPnrPartnerAppOpsUser.operatingPostJ}" type="hidden" />
								<eoms:xbox id="provTree"
										dataUrl="${app}/xtree.do?method=dictXbox&level=3"
											rootId="10209" rootText="工作岗位" valueField="operatingPostJ" handler="operatingPostJName"
										textField="operatingPostJName" checktype="dept" />
				</td>
				
				</tr>
				<tr>
				<td class="label">
					工作职责描述&nbsp;*
				</td>
				<td class="content" colspan="3">
				<textarea class="textarea max" name="workDescribe" 
					id="workDescribe" alt="allowBlank:false,maxLength:60,vtext:'请填入工作职责描述，最多输入 30 字符'">${iPnrPartnerAppOpsUser.workDescribe}</textarea>
				</td>
				</tr>
		    <tr>
		
			<td colspan="4">
					<input type="submit" class="btn" value="保存" />
			</td>
		</tr>
	</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>