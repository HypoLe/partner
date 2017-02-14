<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language=javascript>
<!--  
	Ext.onReady(function() {
	v = new eoms.form.Validation({form:'checkForm'});
	});

  function validateForm(){
      var frm = document.forms[0];
      if( frm.checkBeginTime.value == "" && frm.checkEndTime.value != "" ){
          alert("请填写正确的时间段！");
          return false;      
      }
      if( frm.checkBeginTime.value != "" && frm.checkEndTime.value == "" ){
          alert("请填写正确的时间段！");
          return false;      
      }
      
      return true;
  }
  
  
   	function sub(){
	
		var validate = validateForm();
		if(validate){
			if(v.check())
			 $("checkForm").submit();
		}else{
			return false;
			
		}
	}
	
	
	
	
 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }

function getResponseText(url) {
    var xmlhttp;
    if(eoms.isIE){
    	try{
    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); 
    	}catch(e){
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");    		
    	}
    }else{
    	xmlhttp = new XMLHttpRequest();
    }
    var method = "post";
    url=url+"&"+new Date();
    xmlhttp.open(method, url, false);
    xmlhttp.setRequestHeader("content-type", "text/html; charset=GBK");
    xmlhttp.send(null);
    return xmlhttp.responseText;
}

//县区联动
function changeCity(con)
		{    
		    delAllOption("county");//地市选择更新后，重新刷新县区
			var city = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
			//var result=getResponseText(url);
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									
									var json = eval(res);
									var countyName = "county";
						
									var arrOptions = json[0].cb.split(",");
									var obj=$(countyName);
											
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									
									
									if(con==1){
										var county = '${problemForm.county}';
										if(county!=''){
											document.getElementById("county").value = county;
										}	
									}	
									changePartner(con);							
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}



	/**
	*选择抽查对象（基站） 可多选
	*/
	var xmlHttp;
	function createXMLHttpRequest() {
		//表示当前浏览器不是ie,如ns,firefox
		if(window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	
	function getRequire(site) {
	
		if (site != "") {
			createXMLHttpRequest();
			var url = '${app}/partner/serviceArea/sites.do?method=getSite&site='+site;
			xmlHttp.open("POST", url , true);
			xmlHttp.onreadystatechange=callback;
			xmlHttp.send(null);
		}
		
			document.getElementById('sampleNo').value = site;	
	}
	
	function callback() {
	    if (xmlHttp.readyState == 4) {
		  if (xmlHttp.status == 200) {
		  	var xmlText = xmlHttp.responseText;
		  	
			document.getElementById('sample').value =  xmlText ;
		   }
	   }
	}

	//选择抽查对象的详情页面（基站名称）可多选
	function openSample(){
		 window.open(eoms.appPath+'/partner/serviceArea/sites.do?method=searchSample');
	}
	
  function newAdd(){

    window.location.href=eoms.appPath+'/partner/maintenance/checks.do?method=add';

}
//-->
</script>

<c:set var="buttons">

		  
		  
		   <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
 
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">



	<html:form action="/checks.do?method=searchX" styleId="checkForm" method="post">
		<table align="center">



			<tr>
				<!-- 抽查对象 -->
				<td>
					<fmt:message key="check.sample" />:
				</td>
<!-- 			<td style="width:85px">
					<input type="text" id="sample" name="sample" class="text" value="${checkForm.sample}" maxlength="32">&nbsp;&nbsp;
				</td>
 -->			
				<!-- 选择抽查对象（可多选）去基站取值 -->
				<td class="content" colspan=3>
					<input type="text" style="width:100%;" id="sample" class="text" onclick="openSample()" name="sample" value="${checkForm.sample}" readonly="true" />
				</td>
					<input type="hidden" name="sampleNo" id="sampleNo" />
				
			</tr>


			<!-- 抽查的时间段 -->
			<tr>
			
				<!-- 地市 -->
				<td>
					<fmt:message key="check.city" />:
				</td>
				<td >
					<!-- 地市 -->			
					<select name="city" id="city"
						alt="allowBlank:true,vtext:'请选择所在地市'"
						onchange="changeCity();">
						<option value="">
							--请选择所在地市--
						</option>
						<logic:iterate id="city" name="city">
							<option value="${city.areaid}">
								${city.areaname}
							</option>
						</logic:iterate>
					</select>
				</td>
				
				<!-- 县区 -->
				<td>
					<fmt:message key="check.county" />:
				</td>
				<td >
					<!-- 县区 -->			
					<select name="county" id="county" 
						alt="allowBlank:true,vtext:'请选择所在县区'">
						<option value="">
							--请选择所在县区--
						</option>				
					</select>
				</td>


			</tr>
			<tr>
			
			  	<td>
					抽查时间段：&nbsp;
				</td>
				<td class="content" style="width:85px">
			          <input type="text" size="20" readonly="true" class="text" 
			               name="checkBeginTime" id="checkBeginTime"
			               onclick="popUpCalendar(this,this,null,null,null,true,-1);"
			               alt=" vtype:'lessThen',link:'checkEndTime', allowBlank:true,vtext:'结束时间要大于开始时间'"
			               value="${checkBeginTime}" />
				</td>
				<td>
					&nbsp;&nbsp;&nbsp;至:
				</td>
				<td class="content" >
			          <input type="text" size="20" readonly="true" class="text" 
		                name="checkEndTime" id="checkEndTime"
		                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
		                alt=" vtype:'moreThen',link:'checkBeginTime', allowBlank:true,vtext:'结束时间要大于开始时间'" 
		                value="${checkEndTime}" />
				</td>

			
			
			</tr>
			<tr>
				<!-- 提交按钮 -->
				<td>
					<input type="button" class="btn" value="<fmt:message key="button.search"/>" onclick="sub();" />
				</td>
			
			</tr>
			
		</table>
	</html:form>






<content tag="heading">
	<fmt:message key="check.list.heading" />
</content>

	<display:table name="checkList" cellspacing="0" cellpadding="0"
		id="checkList" pagesize="${pageSize}" class="table checkList"
		export="false"
		requestURI="${app}/partner/maintenance/checks.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="sample" sortable="true"
			headerClass="sortable" titleKey="check.sample"  paramId="id" paramProperty="id"/>

	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="check.city">
		<eoms:id2nameDB id="${checkList.city}" beanId="tawSystemAreaDao" />
	</display:column>			

	<!-- 所在县区 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="check.county">
		<eoms:id2nameDB id="${checkList.county}" beanId="tawSystemAreaDao" />
	</display:column>			


	<display:column property="checkTime" sortable="true"
			headerClass="sortable" titleKey="check.checkTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"  paramId="id" paramProperty="id"/>

	<!-- 抽查人 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="check.person">
			<eoms:id2nameDB id="${checkList.person}" beanId="tawSystemUserDao" />
	</display:column>


	<c:choose>
		<c:when test="${param.flag!='search'}">
			<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='${app}/partner/maintenance/checks.do?method=detail&id=${checkList.id}' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
			</display:column>
		
		</c:when>
		<c:otherwise>
			<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='${app}/partner/maintenance/checks.do?method=detail&flag=search&id=${checkList.id }' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
			</display:column>
		
		</c:otherwise>
	</c:choose>



		<display:setProperty name="paging.banner.item_name" value="check" />
		<display:setProperty name="paging.banner.items_name" value="checks" />
	</display:table>



<c:if test="${param.flag!='search'}">
	<c:out value="${buttons}" escapeXml="false" />
</c:if>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>