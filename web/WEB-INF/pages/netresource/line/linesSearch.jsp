<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language="javascript">

Ext.onReady(function() {
    // 设置检索条件部分不显示
	Ext.fly("listQueryObject").setDisplayed(false);
});

    var checkflag = "false";
    function choose() {
        var objs = document.getElementsByName("ids");
        if(checkflag=="false"){
            for(var i=0; i<objs.length; i++) {
                if(objs[i].type.toLowerCase() == "checkbox" )
                    objs[i].checked = true;
                checkflag="true";
            }
        }else if(checkflag=="true"){
            for(var i=0; i<objs.length; i++) {
                if(objs[i].type.toLowerCase() == "checkbox" )
                    objs[i].checked = false;
                checkflag="false"
            }
        }
    }
	
    function ConfirmDel(){
        
        var flag=false;
        var ids;
        
        var objs = document.getElementsByName("ids");
        for(var i=0; i<objs.length; i++) {
            if(objs[i].type.toLowerCase() == "checkbox" )
            if(objs[i].checked){
                flag=true;
                ids=objs[i];
            }
         }
        if(flag){
            if(confirm("您确定要删除您选择的数据？")){
                return true;
            }else{
                return false;
            }
        }else {
            alert("请选择要删除的数据！");
            return false;
        }
    }
    
    function clear() {
        
    }

    function openQuery(){
        // 设置检索条件部分显示
	    Ext.fly("listQueryObject").setDisplayed(true);

        // 设置展开查询的链接部分不显示
	    var openQuery = document.getElementById("openQuery");
	    openQuery.style.display = "none";
	    // 设置关闭查询的链接部分显示
	    var closeQuery = document.getElementById("closeQuery");
	    closeQuery.style.display = "";
   }
   
   function closeQuery(){
        // 设置检索条件部分不显示
        var listQueryObject = document.getElementById("listQueryObject");
        listQueryObject.style.display = "none";
        
        // 设置展开查询的链接部分显示
        var openQuery = document.getElementById("openQuery");
        openQuery.style.display = "";
        
        // 设置关闭查询的链接部分不显示
        var closeQuery = document.getElementById("closeQuery");
        closeQuery.style.display = "none";
   }


function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i); //firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
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
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); 
    xmlhttp.send(null);
    return xmlhttp.responseText;
}

//地市 联动 县区
function changeCity(con){    
		    delAllOption("city");//地市选择更新后，重新刷新县区
		    delAllOption("grid");//网格
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/baseinfo/linkage.do?method=changeCity&city="+region;
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									
									var json = eval(res);
									
									var countyName = "city";
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
									//changeGrid(con);
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}

//地市 区县 联动网格
function changeGrid(con){
    var region = document.getElementById("region").value;
    var city = document.getElementById("city").value;
    if(city==''){
    	delAllOption("grid");
    	var grid = "grid";
		var obj=$(grid);
		var i=0;
		var j=0;
		var opt=new Option("--请选择网格--","");
		obj.options[j]=opt;
    }
    if(region!=''&&city!=''){
		var url="<%=request.getContextPath()%>/partner/net/sites.do?method=changeGrid&areaId="+region+"&city="+city;
		delAllOption("grid");
					Ext.Ajax.request({
									url : url ,
									method: 'POST',
									success: function ( result, request ) {
									res = result.responseText;
									if(res.indexOf("[{")!=0){
					  					res = "[{"+result.responseText;
									}
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
										var json = eval(res);
				     					var countyName = "grid";
										var arrOptions = json[0].gl.split(",");
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
											var grid = '${linesForm.grid}';
											document.getElementById("grid").value = grid;
										}
										//changePartner(con);
										 
									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});
    }
}

</script>

<fmt:bundle basename="com/boco/eoms/netresource/line/config/ApplicationResources-line">

<div class="form-btns" id="form-btns">
  <span id="openQuery">
  	<input type="button" class="btn" onclick="openQuery()" value='展开快速查询'/>
  </span>
  <span id="closeQuery" style="display:none">
    <input type="button" class="btn" onclick="closeQuery()" value='关闭快速查询'/>
  </span>
</div>

<span id="listQueryObject">

<html:form action="/lines.do?method=search" styleId="linesForm" method="post">

<html:hidden property="flag" value="${flag}" />

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="line.search.heading"/></div>
    </caption>
    
    <html:hidden property="flag" value="${flag}" />
    
    <tr>
        <td class="label">
            <fmt:message key="line.name" />
        </td>
        <td class="content">
            <html:text property="lineName" styleId="lineName"
                        styleClass="text medium"
                        value="${linesForm.lineName}" />
        </td>
        
        <td class="label">
            <fmt:message key="line.no" />
        </td>
        <td class="content">
            <html:text property="lineNo" styleId="lineNo"
                        styleClass="text medium"
                        value="${linesForm.lineNo}" />
        </td>
    </tr>
            
    <tr>
        <td class="label">
            <fmt:message key="line.region" />
        </td>
        <td class="content">
            <!-- 地市 -->			
			<select name="region" id="region"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changeCity(0);">
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
        
        <td class="label">
            <fmt:message key="line.city" />
        </td>
        <td class="content">
            <!-- 县区 -->			
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所在县区'"
				onchange="changeGrid(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
        </td>
     </tr>
     
     <tr>   
        <td class="label">
            <fmt:message key="line.grid" />
        </td>
        <td class="content">
            <!-- 所属网格 -->			
			<select name="grid" id="grid" 
				alt="allowBlank:true,vtext:'请选择所属网格'">
				<option value="">
					--请选择所属网格--
				</option>				
			</select>
        </td>
        
        <td class="label">
            <fmt:message key="line.remark" />
        </td>
        <td class="content">
            <html:text property="remark" styleId="remark"
                        styleClass="text medium"
                        value="${linesForm.remark}" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <fmt:message key="line.maintainType" />
        </td>
        <td class="content">
            <html:text property="maintainType" styleId="maintainType"
                        styleClass="text medium"
                        value="${linesForm.maintainType}" />
        </td>
        
        <td class="label">
            <fmt:message key="line.level" />
        </td>
        <td class="content">
            <html:text property="level" styleId="level"
                        styleClass="text medium"
                        value="${linesForm.level}" />
        </td>
    </tr>
    
</table>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.search"/>" />
            <!--<input type="button" class="btn" value="<fmt:message key="button.clear"/>" />-->
        </td>
    </tr>
</table>

</html:form>
</span>


<html:form action="/lines.do?method=removeSel" styleId="linesForm" method="post"> 

<caption>
    <div class="header center"><b><fmt:message key="line.list.heading"/></b></div>
</caption>

<display:table name="linesList" cellspacing="0" cellpadding="0"
    id="linesList" pagesize="${pageSize}" class="table linesList"
    export="false"
    requestURI="${app}/netresource/line/lines.do?method=search"
    sort="list" partialList="true" size="resultSize"
    decorator="com.boco.eoms.netresource.line.util.LinesDecorator">
    
    <logic:present name="linesList" property="id">
    <logic:notEmpty name="linesList" property="id">
        <display:column title="<input type='checkbox' onclick='javascript:choose();'>" >
        	<input type="checkbox" id="${linesList.id}" name="ids" value="${linesList.id}" />
        </display:column>
        <display:setProperty name="css.table" value="0,"/>
    </logic:notEmpty>
    
    <display:column property="lineName" sortable="true" headerClass="sortable"
        titleKey="line.name" paramId="id" paramProperty="id"/>
                    
    <display:column property="lineNo" sortable="true" headerClass="sortable"
        titleKey="line.no" paramId="id" paramProperty="id"/>
        
    <display:column sortable="true" headerClass="sortable" titleKey="line.partner">
        <eoms:id2nameDB id="${linesList.partner}" beanId="partnerDeptDao" />
    </display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="line.region">
        <eoms:id2nameDB id="${linesList.region}" beanId="tawSystemAreaDao" />
    </display:column>
                            
    <display:column sortable="true" headerClass="sortable" titleKey="line.city" >
        <eoms:id2nameDB id="${linesList.city}" beanId="tawSystemAreaDao" />
    </display:column>
                    
    <display:column sortable="true" headerClass="sortable" titleKey="line.grid">
        <eoms:id2nameDB id="${linesList.grid}" beanId="gridDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="line.maintainType">
        <eoms:dict key="dict-partner-serviceArea" dictId="maintainType" itemId="${linesList.maintainType}" beanId="id2nameXML" />
    </display:column>
    
    <display:column property="openTime" sortable="true" headerClass="sortable"
        titleKey="line.openTime" paramId="id" paramProperty="id"/>
    
    <display:column property="userYear" sortable="true" headerClass="sortable"
        titleKey="line.userYear" paramId="id" paramProperty="id"/>
    
    <display:column property="level" sortable="true" headerClass="sortable"
        titleKey="line.level" paramId="id" paramProperty="id"/>
    
    <display:column sortable="true" headerClass="sortable" titleKey="button.detail" >
        <a href="${app}/netresource/line/lines.do?method=detail&id=${linesList.id}" title="明细">
			<img src="${app}/images/icons/search.gif" />
		</a>
    </display:column>

	<!-- 此处添加权限控制 -->
	<% if( null != request.getAttribute("flag") && "manager".equals(request.getAttribute("flag")) ){ %>
    <display:column sortable="true" headerClass="sortable" titleKey="button.edit">
        <a href="${app}/netresource/line/lines.do?method=edit&id=${linesList.id}" title="编辑">
        	<img src="${app}/images/icons/edit.gif" />
        </a>
    </display:column>
            
    <display:column sortable="true" headerClass="sortable" titleKey="button.remove">
        <a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
        {window.location.href = '${app}/netresource/line/lines.do?method=remove&flag=manager&id=${linesList.id}';}" title="删除"/>
        	<img src="${app}/images/icons/icon.gif" />
        </a>
	</display:column>
	<% } %>

    </logic:present>
</display:table>

</html:form>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>