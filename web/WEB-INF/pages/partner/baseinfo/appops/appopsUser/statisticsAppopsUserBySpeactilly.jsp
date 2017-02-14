<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%    
	String[] company=(String[])request.getAttribute("company");
	String[] userBorth=(String[])request.getAttribute("userBorth");
	String[] workTime=(String[])request.getAttribute("workTime");
	String[] companyTime=(String[])request.getAttribute("companyTime");
	String[] appopsWorkTime=(String[])request.getAttribute("appopsWorkTime");
	String[] highestDegree=(String[])request.getAttribute("highestDegree");
	String[] workSort=(String[])request.getAttribute("workSort");
	String[] jobTitle=(String[])request.getAttribute("jobTitle");
	String[] useSort=(String[])request.getAttribute("useSort");
	String[] jobLevel=(String[])request.getAttribute("jobLevel");
	String[] managerLevel=(String[])request.getAttribute("managerLevel");
	
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryMultiSelect/jquery-1.4.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryMultiSelect/jquery-1.4.3.man.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryMultiSelect/jquery.multiSelect.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryMultiSelect/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryMultiSelect/jquery.multiSelect.js"></script>
<script type="text/javascript">
	var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突	
<!--
	jq(document).ready( function() {
			var fgsDictId1="10208";//分公司字典值
			//var sexDictId1="1120202";//性别
			var highestDegreeDictId1="10201";//最高学历
			var workSortDictId1="10203";//员工类别
			var jobTitleDictId1="10204";//职称
			var useSortDictId1="10205";//用工类别
			var jobLevelDictId1="10207";//岗位职级
			var managerLevelDictId1="10206";//管理层级
			jq.ajax({   
		     url:'<%=request.getContextPath()%>/partner/appops/partnerAppOpsUsers.do?method=getDictValue',   
		     type:'post',   
		     data:{
		     	   fgsDictId:fgsDictId1,
		     	   //sexDictId:sexDictId1,
		     	   highestDegreeDictId:highestDegreeDictId1,
		     	   workSortDictId:workSortDictId1,
		     	   jobTitleDictId:jobTitleDictId1,
		     	   useSortDictId:useSortDictId1,
		     	   jobLevelDictId:jobLevelDictId1,
		     	   managerLevelDictId:managerLevelDictId1},   
		     async : false, //默认为true 异步   
		     dataType:'json',   
		     success:function(data){
		     	if(data.length > 0){
		     		for(var i=0;i<data.length;i++){
		     			if(data[i].key == 'fgs'){
		     				    var str="";
								<%if(company!=null){%>
									<%for(int n=0;n<company.length;n++){%>
										if(data[i].dictId == <%=company[n]%>){
											str="selected";
										}
									<%}%>
								<%}%>
									jq("#company").append("<option value='"+data[i].dictId+"' "+str+">"+data[i].dictName+"</option>");
			
		     			}else if(data[i].key == 'highestDegree'){
		     				var str="";
								<%if(highestDegree!=null){%>
									<%for(int n=0;n<highestDegree.length;n++){%>
										if(data[i].dictId == <%=highestDegree[n]%>){
											str="selected";
										}
									<%}%>
								<%}%>
		     			   jq("#highestDegree").append("<option value='"+data[i].dictId+"' "+str+">"+data[i].dictName+"</option>");	
		     			}else if(data[i].key == 'workSort'){
		     				var str="";
								<%if(workSort!=null){%>
									<%for(int n=0;n<workSort.length;n++){%>
										if(data[i].dictId == <%=workSort[n]%>){
											str="selected";
										}
									<%}%>
								<%}%>
		     			   jq("#workSort").append("<option value='"+data[i].dictId+"' "+str+">"+data[i].dictName+"</option>");	
		     			}else if(data[i].key == 'jobTitle'){
		     				var str="";
								<%if(jobTitle!=null){%>
									<%for(int n=0;n<jobTitle.length;n++){%>
										if(data[i].dictId == <%=jobTitle[n]%>){
											str="selected";
										}
									<%}%>
								<%}%>
		     			   jq("#jobTitle").append("<option value='"+data[i].dictId+"' "+str+">"+data[i].dictName+"</option>");	
		     			}else if(data[i].key == 'useSort'){
		     				var str="";
								<%if(useSort!=null){%>
									<%for(int n=0;n<useSort.length;n++){%>
										if(data[i].dictId == <%=useSort[n]%>){
											str="selected";
										}
									<%}%>
								<%}%>
		     			   jq("#useSort").append("<option value='"+data[i].dictId+"' "+str+">"+data[i].dictName+"</option>");	
		     			}else if(data[i].key == 'jobLevel'){
		     				var str="";
								<%if(jobLevel!=null){%>
									<%for(int n=0;n<jobLevel.length;n++){%>
										if(data[i].dictId == <%=jobLevel[n]%>){
											str="selected";
										}
									<%}%>
								<%}%>
		     			   jq("#jobLevel").append("<option value='"+data[i].dictId+"' "+str+">"+data[i].dictName+"</option>");	
		     			}else if(data[i].key == 'managerLevel'){
		     				var str="";
								<%if(managerLevel!=null){%>
									<%for(int n=0;n<managerLevel.length;n++){%>
										if(data[i].dictId == <%=managerLevel[n]%>){
											str="selected";
										}
									<%}%>
								<%}%>
		     			   jq("#managerLevel").append("<option value='"+data[i].dictId+"' "+str+">"+data[i].dictName+"</option>");	
		     			}else {
		     			    alert('检索条件加载出错,请联系管理员');
		     			}
		     		}
		     	}
		     }
   });
   
   //初始化年份下拉框值
   var currentYear = new Date().getFullYear();
   var select = document.getElementById("userBorth");
   var select1 = document.getElementById("workTime");
   var select2 = document.getElementById("companyTime");
   var select3 = document.getElementById("appopsWorkTime");
     for (var i = 0; i <= 65; i++) {
        //初始化出生年份下拉
        var theOption = document.createElement("option");
        theOption.innerHTML = currentYear-i + "年";
        theOption.value = currentYear-i;
        <%if(userBorth!=null){%>
			<%for(int n=0;n<userBorth.length;n++){%>
				if(currentYear-i == <%=userBorth[n]%>){
					theOption.selected=true;
				}
			<%}%>
		<%}%>
        select.appendChild(theOption);
		
        
        //初始化开始工作年份下拉
        var theOption1 = document.createElement("option");
        theOption1.innerHTML = currentYear-i + "年";
        theOption1.value = currentYear-i;
        <%if(workTime!=null){%>
			<%for(int n=0;n<workTime.length;n++){%>
				if(currentYear-i == <%=workTime[n]%>){
					theOption1.selected=true;
				}
			<%}%>
		<%}%>
        select1.appendChild(theOption1);
        
        //初始化入司年份下拉
        var theOption2 = document.createElement("option");
        theOption2.innerHTML = currentYear-i + "年";
        theOption2.value = currentYear-i;
        <%if(companyTime!=null){%>
			<%for(int n=0;n<companyTime.length;n++){%>
				if(currentYear-i == <%=companyTime[n]%>){
					theOption2.selected=true;
				}
			<%}%>
		<%}%>
        select2.appendChild(theOption2);
        
        //初始化从事运维工作年份下拉
        var theOption3 = document.createElement("option");
        theOption3.innerHTML = currentYear-i + "年";
        theOption3.value = currentYear-i;
        <%if(appopsWorkTime!=null){%>
			<%for(int n=0;n<appopsWorkTime.length;n++){%>
				if(currentYear-i == <%=appopsWorkTime[n]%>){
					theOption3.selected=true;
				}
			<%}%>
		<%}%>
        select3.appendChild(theOption3);
     }
	    	 
   	 //初始化所有多选下拉框
   	 jq("#company").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择' 
        }, function(){   //回调函数   
        
   	 });
   	 
   	 
   	
   	 jq("#userBorth").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择'  
        }, function(){   //回调函数   
       		
   	 });
   	 
   	  jq("#workTime").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择'  
        }, function(){   //回调函数   
       		
   	 });
   	  
   	  jq("#companyTime").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择'  
        }, function(){   //回调函数   
       		
   	 });
   	 
   	 jq("#appopsWorkTime").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择'  
        }, function(){   //回调函数   
       		
   	 });
   	 
   	  jq("#highestDegree").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择'  
        }, function(){   //回调函数   
       		
   	 });
   	 
   	 jq("#workSort").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择'  
        }, function(){   //回调函数   
       		
   	 });
   	 
   	 jq("#jobTitle").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择'  
        }, function(){   //回调函数   
       		
   	 });
   	 
   	 jq("#useSort").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择'  
        }, function(){   //回调函数   
       		
   	 });
   	 
   	 jq("#jobLevel").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择'  
        }, function(){   //回调函数   
       		
   	 });
   	 
   	 
   	 jq("#managerLevel").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择'  
        }, function(){   //回调函数   
       		
   	 });
   	 
   	  jq("#empty").click(function(){
   	  		jq("[name='company_check']").removeAttr("checked");
   	  		jq("[name='userBorth_check']").removeAttr("checked");
   	  		jq("[name='workTime_check']").removeAttr("checked");
   	  		jq("[name='companyTime_check']").removeAttr("checked");
   	  		jq("[name='appopsWorkTime_check']").removeAttr("checked");
   	  		jq("[name='highestDegree_check']").removeAttr("checked");
   	  		jq("[name='workSort_check']").removeAttr("checked");
   	  		jq("[name='jobTitle_check']").removeAttr("checked");
   	  		jq("[name='useSort_check']").removeAttr("checked");
   	  		jq("[name='jobLevel_check']").removeAttr("checked");
   	  		jq("[name='managerLevel_check']").removeAttr("checked");
   	  		jq(".selectAll").removeAttr("checked");
    		jq(".check_span").text("请选择");
    		jq("#userName").val("");
    		jq("#userSex").val("");
    		jq("#phone").val("");
    		jq("#email").val("");
    		jq("#school").val("");
    		jq("#deptName").val("");
    		jq("#organizationNo").val("");
    		jq("#dept").val("");
    		jq("#teamName").val("");
    		jq("#team").val("");
    		jq("#specialty").val("");
    		jq("#specialtySecond").val("");
    		jq("#operatingPostZ option").remove();
    		jq("#operatingPostZ").prepend("<option value=''>等待上一级选择</option>");
    		jq("#operatingPostZ").attr("disabled",true);   
    		jq("#operatingPostJName").val("");
    		jq("#operatingPostJ").val("");
    								
    	}); 
	    	 
	});
//-->
</script>
<script type="text/javascript">

function openImport(){
	var v=jq("#isShowDiv").val();
	if(v == '1'){
		jq("#listQueryObject").animate({height:"1px"});
		jq("#isShowDiv").val('0');
	}else{
		jq("#listQueryObject").animate({height:"100%"});
		jq("#isShowDiv").val('1');
	}
}

//导出Excel
function exportExcel(){
	var workerStateTempValue=document.getElementById("workerStateTemp").value;
	var highAgreeTempValue=document.getElementById("highAgreeTemp").value;
	var zhichengTempValue=document.getElementById("zhichengTemp").value;
	var operatingPostZTempValue=document.getElementById("operatingPostZTemp").value;
	var operatingPostJTempValue=document.getElementById("operatingPostJTemp").value;
	window.location.href="<%=request.getContextPath()%>/partner/appops/partnerAppOpsUsers.do?method=exportStatisticsByCompanyAndDept&workerState="+workerStateTempValue+"&highAgree="+highAgreeTempValue+"&zhicheng="+zhichengTempValue+"&operatingPostZ="+operatingPostZTempValue+"&operatingPostJ="+operatingPostJTempValue;
}
function selectTeam(){
 		var dept_value = document.getElementById("dept").value;
		xbox_teamTree.defaultTree.root.id=dept_value;
		xbox_teamTree.reloadTree();
		xbox_teamTree.show();
	}
	
//验证手机
function checkMobile()
  {
	  var mobile = document.getElementById('phone').value;
	  if(mobile != ''){
	  	  var myreg = /^1[3|4|5|8][0-9]\d{4,8}$/;
	      if(!myreg.test(mobile))
	      {
	          alert('请输入有效的11位手机号码！');
	          document.getElementById('phone').value="";
	          return false;
	      }
	      return true;  
	      
	      
	  }
    
  }	
  
  //判断输入的EMAIL格式是否正确    
function checkEmail()     
{     
        var email = document.getElementById('email').value;    
        if(email!= ''){    
	        reg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;    
	        if(!reg.test(email)){    
	            alert("请输入正确的Email地址!");
	            document.getElementById('email').value="";
	            return false;
	        }
	        return true;   
	        
	        
        }    
} 
 
</script>


<script type="text/javascript">
<!--
function getDeptName(){
			var dept_value = document.getElementById("dept").value;
			if(dept_value!=''){
					Ext.Ajax.request({
	                url: '<%=request.getContextPath()%>/partner/appops/partnerAppOpsUsers.do?method=getDeptName',
	                headers: {
	                    'userHeader': 'userMsg'
	                },
	                params: { deptId: dept_value},
	                method: 'POST',
	                success: function (response, options) {
	                     document.getElementById("deptName").value=response.responseText;
	                },
	                failure: function (response, options) {
	                    Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
	                }
	            });
			}else{
				document.getElementById("deptName").value="";
			}
            
   
}
function changeSpecially(){
	
	
	
}

//-->
</script>
<div align="center"><b>运维人员统计</div><br><br/>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
<html:form action="partnerAppOpsUsers.do?method=statisticsAppopsUserBySpeciallty&flag=1" method="post"  styleId="partnerUserForm">
<c:if test="${in!=null}">
</c:if>
<input type="hidden" name="treeNodeId" value="${treeNodeId }">
<input type="hidden" id="isShowDiv" value="1">
<table class="listTable">
	<tr>
		<td class="label" style="width:10%">
			分公司 
		</td>
		<td class="content" style="width:20%">
			<select id="company"  name="company" multiple="multiple" style="width:60%">
			</select>
		
		</td>
		<td class="label" style="width:10%">
			姓名
		</td>
		<td class="content" style="width:20%">	
			<input type="text" class="text" id="userName" name="userName" value="${userName}";/>
		</td>
		<td class="label" style="width:10%">
			性别 
		</td>
		<td class="content" style="width:20%">	
			<eoms:comboBox name="userSex" id="userSex" initDicId="1120202"
						defaultValue="${userSex}"	alt="allowBlank:false" styleClass="select"/>
		</td>
	</tr>
	<tr>
		<td class="label" style="width:10%">
			出生年月  
		</td>
		<td class="content" style="width:20%">
			<select id="userBorth"  name="userBorth" multiple="multiple" style="width:60%">
			</select>
		</td>
		<td class="label" style="width:10%">
			手机  
		</td>
		<td class="content" style="width:20%">	
			<html:text property="phone" styleId="phone" styleClass="text medium"
					value="${phone}"
					alt="allowBlank:false,vtext:'',vtype:'phonemobile'"
					onblur="checkMobile();"  
					 />
		</td>
		<td class="label" style="width:10%">
			邮箱 
		</td>
		<td class="content" style="width:20%">	
			<html:text property="email" styleId="email"
						styleClass="text medium" onblur="checkEmail();"
						value="${email}" alt="allowBlank:false,vtext:'',vtype:'email'"/>
		</td>
	</tr>
	<tr>
		<td class="label" style="width:10%">
			参加工作时间  
		</td>
		<td class="content" style="width:20%">
			<select id="workTime"  name="workTime" multiple="multiple" style="width:60%">
			</select>
		</td>
		<td class="label" style="width:10%">
			入司时间  
		</td>
		<td class="content" style="width:20%">	
			<select id="companyTime"  name="companyTime" multiple="multiple" style="width:60%">
			</select>
		</td>
		<td class="label" style="width:10%">
			从事维护工作时间 
		</td>
		<td class="content" style="width:20%">	
			<select id="appopsWorkTime"  name="appopsWorkTime" multiple="multiple" style="width:60%">
			</select>
		</td>
	</tr>
	<tr>
		<td class="label" style="width:10%">
			最高学历  
		</td>
		<td class="content" style="width:20%">
			<select id="highestDegree"  name="highestDegree" multiple="multiple" style="width:60%">
			</select>
		</td>
		<td class="label" style="width:10%">
			最高学历毕业院校  
		</td>
		<td class="content" style="width:20%">	
			<input type="text" class="text" id="school" name="school" value="";/>
		</td>
		<td class="label" style="width:10%">
			员工类别 
		</td>
		<td class="content" style="width:20%">	
			<select id="workSort"  name="workSort" multiple="multiple" style="width:60%">
			</select>
		</td>
	</tr>
	<tr>
		<td class="label" style="width:10%">
			职称 
		</td>
		<td class="content" style="width:20%">
			<select id="jobTitle"  name="jobTitle" multiple="multiple" style="width:60%">
			</select>
		</td>
		<td class="label" style="width:10%"> 
			用工类别  
		</td>
		<td class="content" style="width:20%">	
			<select id="useSort"  name="useSort" multiple="multiple" style="width:60%">
			</select>
		</td>
		<td class="label" style="width:10%">
			岗位职级 
		</td>
		<td class="content" style="width:20%">	
			<select id="jobLevel"  name="jobLevel" multiple="multiple" style="width:60%">
			</select>
		</td>
	</tr>
	<tr>
		<td class="label" style="width:10%">
			组织编码
		</td>
		<td class="content" style="width:20%">
			<input type="text" class="text medium" name="organizationNo" id="organizationNo" onblur="getDeptName();"
						value="${organizationNo}"
						alt="allowBlank:false" readonly="readonly" />
					<input name="dept" id="dept" value="${deptId}" type="hidden" />
					<eoms:xbox id="deptTree"
						dataUrl="${app}/xtree.do?method=getPnrDeptNumber&isPartner=${isPartner}"
						rootId="" rootText="组织编码树" valueField="dept" handler="organizationNo"
						textField="organizationNo" checktype="dept"  single="true"  />
		</td>
		<td class="label" style="width:10%">
			所在部门
		</td>
		<td class="content" style="width:20%">	
		<input type="text" class="text medium" name="deptName" id="deptName"
						value="${deptName}"
						alt="allowBlank:false" readonly="readonly" />
		</td>
		<td class="label" style="width:10%">
			所有班组
		</td>
		<td class="content" style="width:20%">	
		<input type="text" class="text medium" name="teamName" id="teamName" 
						value="<eoms:id2nameDB id="${team}" beanId="partnerAppopsDeptDao"/>"
						alt="allowBlank:false" readonly="readonly" onclick="selectTeam()"/>
					<input name="team" id="team" value="${team}" type="hidden" />
					<eoms:xbox id="teamTree"
						dataUrl="${app}/xtree.do?method=getPnrDeptWithRightDW&isPartner=${isPartner}"
						rootId="" rootText="组织结构树" valueField="team" handler="teamName"
						textField="teamName" checktype="dept" single="true" />
		</td>
	</tr>
	<tr>
		<td class="label" style="width:10%">
			专业类别
		</td>
		<td class="content" style="width:20%">
				<eoms:comboBox name="specialty" id="specialty" initDicId="10209"
						defaultValue='${specialty}'	alt="allowBlank:false,vtext:''" sub="operatingPostZ" styleClass="select" onchange="changeSpecially()"/>
					
		</td>
		<td class="label" style="width:10%">
			工作岗位-专职
		</td>
		<td class="content" style="width:20%">	
			  <eoms:comboBox name="operatingPostZ" id="operatingPostZ" initDicId="${specialty}"
						defaultValue='${operatingPostZ}'	alt="allowBlank:false,vtext:''" styleClass="select"/>
			
		</td>
		<td class="label" style="width:10%">
			工作岗位-兼职
		</td>
		<td class="content" style="width:20%">	
				<!-- 		<eoms:comboBox name="operatingPostJ" id="operatingPostJ" initDicId="${iPnrPartnerAppOpsUser.specialty}"
							defaultValue='' alt="allowBlank:false,vtext:''" multiple="true" styleClass="select"/>  -->
							
						<input type="text" class="text medium" name="operatingPostJName" id="operatingPostJName"
						value="${operatingPostJName}"
						alt="allowBlank:false" readonly="readonly" />
							<input name="operatingPostJ" id="operatingPostJ" value="${operatingPostJ}" type="hidden" />
								<eoms:xbox id="provTree"
										dataUrl="${app}/xtree.do?method=dictXbox&level=3"
											rootId="10209" rootText="工作岗位" valueField="operatingPostJ" handler="operatingPostJName"
										textField="operatingPostJName" checktype="dept" />
		</td>
	</tr>
	 
	<tr>
		<td class="label" style="width:10%">
			管理层级 
		</td>
		<td class="content" style="width:20%">
			<select id="managerLevel"  name="managerLevel" multiple="multiple" style="width:60%">
			</select>
		</td>
		<td class="label" style="width:10%">
			工作职责描述
		</td>
		<td class="content" style="width:20%">	
			<input type="text" class="text" id="workDescribe" name="workDescribe" value="${workDescribe}";/>
		</td>
		
	</tr>
</table>
<table >
	<tr>
		<td>
			<input type="submit" class="btn" value="查询" />
			<input type="button" id="empty" class="btn" value="重置" >
			 <input type="hidden" value="tomgr" name = "operationType"/>
		</td>
	</tr>
</table>
</html:form>	
</div>
<br/><br/><br/>
<display:table name="taskList" id="taskList" class="table businessdesignMain" export="true" requestURI="partnerAppOpsUsers.do">
	<display:column title="专业类别" style="text-align:center;">
	<c:if test="${taskList.speciallty eq '合计'}">
	合计
	</c:if>
	<c:if test="${taskList.speciallty ne '合计'}">
	<eoms:id2nameDB id="${taskList.speciallty}" beanId="ItawSystemDictTypeDao"/>
	</c:if>
	</display:column >
	
	<display:column title="年龄<26岁" property="age_one" style="text-align:center;"/>
	<display:column title="年龄26~30" property="age_two" style="text-align:center;"/>
	<display:column title="年龄31~35" property="age_tree" style="text-align:center;"/>
	<display:column title="年龄36~40" property="age_forth" style="text-align:center;"/>
	<display:column title="年龄41~45" property="age_five" style="text-align:center;"/>
	<display:column title="年龄46~50" property="age_sex" style="text-align:center;"/>
	<display:column title="年龄51~55" property="age_seven" style="text-align:center;"/>
	<display:column title="年龄>=56" property="age_ength" style="text-align:center;"/>
	
	<display:column title="博士学位" property="degree_one" style="text-align:center;"/>
	<display:column title="硕士学位" property="degree_two" style="text-align:center;"/>
	<display:column title="本科（含双学位）" property="degree_three" style="text-align:center;"/>
	<display:column title="大专" property="degree_forth" style="text-align:center;"/>
	<display:column title="中专及以下" property="degree_five" style="text-align:center;"/>
	
	<display:column title="省" property="managment_one" style="text-align:center;"/>
	<display:column title="市" property="managment_two" style="text-align:center;"/>
	<display:column title="县" property="managment_three" style="text-align:center;"/>
	<display:column title="市传输局" property="managment_forth" style="text-align:center;"/>
	
	<display:column title="专职" property="workState_zhuan" style="text-align:center;"/>
	<display:column title="兼职" property="workState_jian" style="text-align:center;"/>
	
	<display:column title="合同制" property="useSort_one" style="text-align:center;"/>
	<display:column title="劳务派遣" property="useSort_two" style="text-align:center;"/>
	<display:column title="紧密型外包" property="useSort_three" style="text-align:center;"/>
	<display:column title="经营性外包" property="useSort_forth" style="text-align:center;"/>
	<display:column property="rowSum"  title="合计" style="text-align:center;"/> 
	
		<display:setProperty name="export.rtf" value="false"/>
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
		
	
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>