<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">

	function add(){
		window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/oilEngine.do?method=goToAdd";
	}
	function openImport(){
		var handler = document.getElementById("openQuery");
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
	}

	//地市区县联动
	function changeCity(con){    
		    delAllOption("country");//地市选择更新后，重新刷新县区
			var city = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
			Ext.Ajax.request({
				url : url,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("<\/SCRIPT>")>0){
				  		res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					var json = eval(res);
					var countyName = "country";
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
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}

   function delAllOption(elementid){
        var ddlObj = document.getElementById(elementid);//获取对象
        for(var i=ddlObj.length-1;i>=0;i--){
             ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
        }
    }

	function res(){
		var formElement=document.getElementById("pnrRecordForm")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
	   	 companyId=document.getElementById('city').value='';
	   	 companyId=document.getElementById('country').value='';
	   	 companyId=document.getElementById('site').value='';
	   	 companyId=document.getElementById('speciality').value='';
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
	              inputs[i].value = '';
	         }
     	}
     	 for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	              selects[i].options[0].selected = true;
	         }
     	}
	}
	//地市区县联动
	function changeSite(con){    
		    delAllOption("site");//地市选择更新后，重新刷新县区
			var city = document.getElementById("city").value;
			var country = document.getElementById("country").value;
			var url="<%=request.getContextPath()%>/partner/record/recordAction.do?method=changeSite&city="+city+"&country="+country;
			Ext.Ajax.request({
				url : url,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("<\/SCRIPT>")>0){
				  		res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					var json = eval(res);
					var countyName = "site";
					var arrOptions = json[0].sb.split(",");
					var obj=$(countyName);
					var i=0;
					var j=0;
					for(i=0;i<arrOptions.length;i++){
						var opt=new Option(arrOptions[i+1],arrOptions[i]);
						obj.options[j]=opt;
						j++;
						i++;
					}
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}

</script>

<table class="formTable">
	<caption>
		<div class="header center">档案列表</div>
	</caption>
</table>	
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
	<form action="${app}/partner/record/recordAction.do?method=search" id="pnrRecordForm"	method="post">
		<table id="sheet" class="listTable">
	<tr>
	    <td class="label">
	       主题<font color="red" > *</font>
	    </td>
	    <td colspan="3">
	       <input  type="text" class="text" name="title" id="title" style="width: 90%"
					alt="allowBlank:false,vtext:'标题不能为空或者超过100个汉字！',maxLength:200"
					value=""/>
	    </td>
	</tr>		
		<tr>
 			<td class="label">地市<font color="red" > *</font></td>
			<td class="content" >
				<!-- 地市 -->			
				<select name="city" id="city" class="select" alt="allowBlank:false,vtext:'请选择所在地市'" onchange="changeCity(0);">
					<option value="">
						--请选择所在地市--
					</option>
					<logic:iterate id="city" name="city">
						<c:if test="${sheetMain.mainCity==city.areaid}" var="result">
							<option value="${city.areaid}" selected="selected" >
								${city.areaname}
							</option>
						</c:if>
						<c:if test="${!result}">
							<option value="${city.areaid}" >
								${city.areaname}
							</option>
						</c:if>
					</logic:iterate>
					
				</select>
			</td>
 			<td class="label">区县<font color="red" > *</font></td>
			<td class="content" >
				<!-- 区县 -->			
				<select name="country" id="country" class="select" onchange="changeSite(0);"
					alt="allowBlank:true,vtext:'请选择所在县区'">
					<option value="">
						--请选择所在县区--
					</option>				
				</select>
			</td>
 		</tr>

	<tr>
	    <td class="label">
	       站点<font color="red" > *</font>
	    </td>
	    <td class="content">
				<select name="site" id="site" class="select"
					alt="allowBlank:true,vtext:'请选择所属站点'">
					<option value="">
						--请选择所属站点--
					</option>				
				</select>
	    </td>
	    <td class="label">
	       维护专业<font color="red" > *</font>
	    </td>
			<td class="content">
				<eoms:comboBox name="specialty" id="specialty" defaultValue="${specialty}"
					initDicId="11225" alt="allowBlank:false" styleClass="select"/>
			</td>
	</tr>
	<tr>
               <td class="label">
                   发布时间从
               </td>
               <td>
	            	<input type="text" name="beginTime" id="beginTime" value="" alt="allowBlank:false,vtype:'lessThen',link:'endTime',vtext:'${eoms:a2u("开始时间不能为空或晚于结束时间")}'" readonly="readonly" onclick="popUpCalendar(this, this,null,null,null,true,-1);" class="text"/>
               
               </td>	      
               <td class="label">
                   到
               </td>
               <td> 
               	<input type="text" name="endTime" id="endTime" value="" alt="allowBlank:false,vtype:'moreThen',link:'beginTime',vtext:'${eoms:a2u("结束时间不能为空或早于开始时间")}'" readonly="readonly" onclick="popUpCalendar(this, this,null,null,null,true,-1);" class="text"/>
               
               </td>     
</tr>
		<tr>
				<td colspan='4' class='label'>
					<input type="submit" value="查询">
					<input type="button"  id="reset" value="重置" onclick="res();">
				</td>
		</tr>
	</table>
	</form>
</div>
	<display:table name="pnrRecordList" cellspacing="0" cellpadding="0"
		id="pnrRecordList" pagesize="${pageSize}" class="table pnrRecordList"
		export="false"
		requestURI="../record/recordAction.do?method=search"
		sort="list" partialList="true" size="resultSize">
		
	<display:column property="title" sortable="true"
			headerClass="sortable" title="标题"  paramId="id" paramProperty="id"/>
	
	<display:column sortable="true" headerClass="sortable" title="地市" >
		<eoms:id2nameDB id="${pnrRecordList.city}" beanId="tawSystemAreaDao" />
	</display:column>	

	<display:column sortable="true" headerClass="sortable" title="区县" >
		<eoms:id2nameDB id="${pnrRecordList.country}" beanId="tawSystemAreaDao" />
	</display:column>	
	<display:column sortable="true" headerClass="sortable" title="发布人" >
		<eoms:id2nameDB id="${pnrRecordList.addUser}" beanId="tawSystemUserDao" />
	</display:column>		
	<display:column sortable="true" headerClass="sortable" title="维护专业" >
		<eoms:id2nameDB id="${pnrRecordList.specialty}" beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true" headerClass="sortable" title="档案分类" >
		<eoms:id2nameDB id="${pnrRecordList.recordType}" beanId="ItawSystemDictTypeDao" />
	</display:column>	
	<display:column property="addTime" sortable="true"
			headerClass="sortable" title="发布时间"  paramId="id" paramProperty="id"/>
    <display:column title="下载档案" headerClass="sortable" paramId="id" paramProperty="id">
				<a href='../record/recordAction.do?method=download&id=${pnrRecordList.id}'>
					<img src="${app}/images/icons/save.gif" /> </a>
			</display:column>					
    <display:column title="修改删除" headerClass="sortable" paramId="id" paramProperty="id">
				<a href='../record/recordAction.do?method=edit&id=${pnrRecordList.id}' target='_blank'>
					<img src="${app}/images/icons/edit.gif" /> </a>
			</display:column>
    <display:column title="查看详情" headerClass="sortable" paramId="id" paramProperty="id">
				<a href='../record/recordAction.do?method=detail&id=${pnrRecordList.id}' target='_blank'>
					<img src="${app}/images/icons/table.gif" /> </a>
			</display:column>
	</display:table>	
<%@ include file="/common/footer_eoms.jsp"%>