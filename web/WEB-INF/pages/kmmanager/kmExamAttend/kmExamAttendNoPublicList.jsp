<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">
	<c:set var="chooseAll">
		<input type="button" class="btn" onclick="chooseAll(this)" value="全部选择"/>
		<input type="button" class="btn" onclick="checkForm()" value="批量发布成绩"/>
	</c:set>
	<content tag="heading">
		<center><b><fmt:message key="kmExamAttend.readList" /></b></center>
	</content>
	<div>
		<form action="${app}/kmmanager/kmExamAttends.do?method=searchRead&isPublic=0" method="post">
			<input id="testName" name="testName" class="text" value="${testName }">
			<input type="submit" id="testName" name="testName" class="btn"  value="查找待发布成绩试卷">
			<input type="hidden" id="testId" name="testId" value="${testId }">
		</form> 
	</div>
	<form action="${app}/kmmanager/kmExamAttends.do?method=batchPublic" method="post">
		<display:table name="kmExamAttendList" cellspacing="0" cellpadding="0"
			id="kmExamAttendList" pagesize="${pageSize}" class="table kmExamAttendList"
			export="false"
			requestURI="${app}/kmmanager/kmExamAttends.do?method=searchRead"
			sort="list" partialList="true" size="resultSize">
	
		<display:column  title="选择">
				<input type="checkbox" indexed="true"   id="checkall" name="checkall" value="${kmExamAttendList.id }"/>
		</display:column>
	
		<display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.testId">
			<script>
				var testName = '<eoms:id2nameDB id="${kmExamAttendList.testId}" beanId="kmExamTestDao" />';
				if(testName=='')
					document.write('<eoms:id2nameDB id="${kmExamAttendList.testId}" beanId="kmExamRandomTestDao" />');
				else 
					document.write(testName);
			</script>
		</display:column>
	
	     <display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.attendDept">
				<eoms:id2nameDB id="${kmExamAttendList.attendDept}" beanId="tawSystemDeptDao" />
		</display:column>
		
		 <display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.attendUser">
			<eoms:id2nameDB id="${kmExamAttendList.attendUser}" beanId="tawSystemUserDao" />
		</display:column>
	
		<display:column property="attendTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
				headerClass="sortable" titleKey="kmExamAttend.attendTime"  paramId="id" paramProperty="id"/>
	
		<display:column property="attendOverTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
				headerClass="sortable" titleKey="kmExamAttend.attendOverTime"  paramId="id" paramProperty="id"/>
		
		<display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.readDept">
				<c:if test="${kmExamAttendList.readUser==null}">自动阅卷</c:if>
				<c:if test="${kmExamAttendList.readUser!=null}"><eoms:id2nameDB id="${kmExamAttendList.readDept}" beanId="tawSystemDeptDao" /></c:if>
		</display:column>
		
		 <display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.readUser">
		 	<c:if test="${kmExamAttendList.readUser==null}">自动阅卷</c:if>
			<c:if test="${kmExamAttendList.readUser!=null}"><eoms:id2nameDB id="${kmExamAttendList.readUser}" beanId="tawSystemUserDao" /></c:if>
		</display:column>
		
		<display:column property="score" sortable="true" 
				headerClass="sortable" titleKey="kmExamAttend.score"  paramId="id" paramProperty="id"/>
		
		<display:column sortable="true" headerClass="sortable" titleKey="kmExamAttend.isPublic">
		   <eoms:dict key="dict-kmmanager" dictId="isOrNot" itemId="${kmExamAttendList.isPublic}" beanId="id2nameXML" />		
		</display:column>
		
		<display:column title="查看" headerClass="imageColumn">
			    <a href="javascript:var id = '${kmExamAttendList.id }';
			                        var testID = '${kmExamAttendList.testId}';
			                        var attendUser = '${kmExamAttendList.attendUser}';
			                        var url='${app}/kmmanager/kmExamAttends.do?method=attendRead';
			                        url = url + '&id=' + id + '&testID=' + testID +'&attendUser='+attendUser;
			                        location.href=url">
			       <img src="${app}/images/icons/search.gif"/></a>
			</display:column>
	
			<display:setProperty name="paging.banner.item_name" value="kmExamAttend" />
			<display:setProperty name="paging.banner.items_name" value="kmExamAttends" />
		</display:table>
		<br>
		<c:out value="${chooseAll}" escapeXml="false" />
	</form>

</fmt:bundle>
<script type="text/javascript">
Ext.onReady(function() {
	var config = {
			btnId:'testName',
			treeDataUrl:'${app}/kmmanager/kmExamAttends.do?method=getNoReadTestName&isRead=2',
			treeRootId:'1',
			treeRootText:'考试名称列表',
			treeChkMode:'single',
			treeChkType:'forums',
			showChkFldId:'testName',
			saveChkFldId:'testId'
		}
	tree = new xbox(config);
});
function chooseAll(obj){
   var checks=document.getElementsByName('checkall');
   if(obj.value=="全部选择"){	  
	   for(var i =0; i<checks.length; i++){			   
		   checks[i].checked = true;
	   }
	   obj.value = "全部取消"; 
	}else{
	   for(var i =0; i<checks.length; i++){			   
		   checks[i].checked = false;
	   }
	   obj.value = "全部选择";
	}
 }
 function checkForm()
 {
 	 var flag = false;
 	 var checks=document.getElementsByName('checkall');
 	 for(var i =0; i<checks.length; i++){			   
		   if(checks[i].checked) {flag = true; break;}
	 }
	 if(!flag){alert("请至少选择一项！");return false;}
	 else document.forms[1].submit();
 }
</script>
<%@ include file="/common/footer_eoms.jsp"%>