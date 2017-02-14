<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
//common golbal variant
var used_prctmplid;//被选用的模板id 
</script>
<script type="text/javascript">
  //没有如下代码,将报错 ext.js:TypeError: $(this.showChkFldId).update is not a function
  var jq=jQuery.noConflict();
   
  Ext.onReady(function(){  
      
  });
  
  //////////////////////////////////////////////////////////
  function showDetail(obj){
      var prctmplid=obj.parentNode.getElementsByTagName('input')[0].value;
      var url="${app}/partner/feeManage/evaluFee.do?method=evalu_feeprctmpl_view&prctmplid="+prctmplid;
      window.open (url, "evaluprctmpl", "height=450, width=800, top=200, left=200, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no");     
  }   
  
  function  useThePrctmpl(obj){ //现采用跳转到目标页面处理
      used_prctmplid=obj.parentNode.getElementsByTagName('input')[0].value; //设置公共全局js变量used_prctmplid的实时值
      document.getElementById("used_prctmplid").value=used_prctmplid;//设置要传递的值
      //显示费用实例 特定类型之 考核费用实例 的额外信息 
      if(used_prctmplid!=null&& (new String(used_prctmplid)).trim()!=""){
          document.forms['feeEntityForm'].submit();
      } 
  }
  
</script>	

<body>
    <form action="${app}/partner/feeManage/evaluFee.do?method=createFeeEntity" method="post" id="feeEntityForm" name="feeEntityForm">
      <input type="hidden" name="used_prctmplid" id="used_prctmplid" />
      <input type="hidden" name="operType" value="${operType}" />   
      <input type="hidden" name="evalu_entityid" value="${evalu_entityid}" />   
      <input type="hidden" name="mainFeeId" value="${mainFeeId}" />       
      <!-- 分页显示单价模板列表，以供选择 -->
      <!-- 
        标签取得数据的数据源：有四种范围:pageScope requestScope (默认)   sessionScope  applicationScope 。
      display:table name="sessionScope.holder.list"  注意，这里要指定范围，非默认applicationScope 
        
      display:column的property对应List里对象的属性（用getXXX()方法取得），title则对应表格表头里的列名.
      forrest:List里的对象可以是Entity,那么Map对象呢？ 可以(forrest以验证)！！！
      
       通过增加id属性创建隐含的对象.
       -->
      <logic:present name="list" scope="request">
         <display:table name="list" cellspacing="0" cellpadding="0"
		id="curElementInList" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/feeManage/evaluFee.do?method=initateFeeEntity"
		sort="list" partialList="true" size="${total}">
		<display:column property="prctmplnm"  title="模板名称"  sortable="true" headerClass="sortable" />
				
		<display:column   title="专业" >
				<eoms:id2nameDB id="${curElementInList.majorid}" beanId="ItawSystemDictTypeDao" />
		</display:column>					
	
		<display:column  title="地区" >
		   <eoms:id2nameDB id='${curElementInList.areaid}' beanId='tawSystemAreaDao'/>
		</display:column>		
	
		<display:column  title="代维公司" >
			<eoms:id2nameDB id='${curElementInList.compid}' beanId='partnerDeptDao'/>
		</display:column>		
				
		<display:column title="创建人" >
			 <eoms:id2nameDB id="${curElementInList.creatorid}" beanId="tawSystemUserDao" />
		</display:column>
				
		<display:column title="创建时间" property="createdttm"  sortable="true" headerClass="sortable" format="{0,date,yyyy-MM-dd}"/>
		
		<display:column  title="备注" >
			 <textArea >${curElementInList.remark}</textArea>
		</display:column>
				
		<display:column  title="详情">
		     <input type="hidden"  value="${curElementInList.id}"/>
		     <img src="${app}/images/icons/search.gif" onClick="showDetail(this);"/>
    	</display:column>	
    	
    	<display:column  title="操作">
    	     <input type="hidden"  value="${curElementInList.id}"/>
		     <input type="button" value="使用模板" onClick="useThePrctmpl(this);"/>	    
    	</display:column>						
       </display:table>   
              
      </logic:present>
           
   </form>

<%@ include file="/common/footer_eoms.jsp"%>
  
