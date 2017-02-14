<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">

var FlagLineNo = true;//线路编号是否已经存在验证
var FlagLineName = true;//线路名称是否已经存在验证

//验证form
Ext.onReady(function() {
    v = new eoms.form.Validation({form:'linesForm'});
    v.custom = function(){
		if(!FlagLineNo){
			alert("【线路编号】 已存在！");
			return false;
		}
		if(!FlagLineName){
			alert("【线路名称】 已存在！");
			return false;
		}
		//经度大于纬度判断
		var beginLong = document.getElementById("beginLong").value;
		var beginLat = document.getElementById("beginLat").value;
		var endLong = document.getElementById("endLong").value;
		var endLat = document.getElementById("endLat").value;
		if(beginLong - beginLat <= 0){
			alert("中国境内,【起点经度】必须要大于【起点纬度】！");
			return false;
		}
		if(endLong - endLat <= 0){
			alert("中国境内,【终点经度】必须要大于【终点纬度】！");
			return false;
		}
      	document.getElementById("submitInput").disabled = true;//锁定提交按钮
      	return true;
   	 }
});

//初始化对象 清空
function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i); //firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }

//getResponseText        
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
		    delAllOption("partner");//地市选择更新后，重新刷新合作伙伴
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/baseinfo/linkage.do?method=changeCity&city="+region;
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
									
									if(con==1){
										var city = '${linesForm.city}';
										if(city!=''){
											document.getElementById("city").value = city;
										}	
									}
									changeGrid(con);
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
										changePartner(con);
										 
									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});
    }
}

//网格 联动 合作伙伴		
function changePartner(con)
		{    
			var grid = document.getElementById("grid").value;
			var url="<%=request.getContextPath()%>/partner/net/sites.do?method=changePartner&gridId="+grid;
			
			Ext.Ajax.request({
							url : url ,
							method: 'POST',
							success: function ( result, request ) { 
								var res = result.responseText;
								if(res.indexOf("<\/SCRIPT>")>0){
							  		res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
								}
								var json = eval(res);
								
								document.getElementById("partner").value = json[0].name;
								document.getElementById('partnerId').value = json[0].id;
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}

//线路编号唯一性验证
function valLineNo(){
	var lineNo = document.getElementById("lineNo").value;
	lineNo = encodeURIComponent(lineNo); //编码转换
	var url = "<%=request.getContextPath()%>/netresource/line/lines.do?method=validationLineInfo&flag=lineNo&lineNo="+lineNo;

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
							if(json[0].message == true){
			     				if(lineNo != ""){
			     					document.getElementById("lineNoMessage").innerHTML = "<font color='blue'>此线路编号可以使用.</font>";
			     					FlagLineNo = true;
			     				}
			     			}else{
			     				document.getElementById("lineNoMessage").innerHTML = "<font color='red'>对不起,此线路编号已存在.</font>";
			     				FlagLineNo = false;
			     			}	
						},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
}

//线路名称唯一性验证
function valLineName(){
	var lineName = document.getElementById("lineName").value;
	lineName = encodeURIComponent(lineName); //编码转换
	var url = "<%=request.getContextPath()%>/netresource/line/lines.do?method=validationLineInfo&flag=lineName&lineName="+lineName;
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
							if(json[0].message == true){
			     				if(lineName != ""){
			     					document.getElementById("lineNameMessage").innerHTML = "<font color='blue'>此线路名称可以使用.</font>";
			     					FlagLineName = true;
			     				}
			     			}else{
			     				document.getElementById("lineNameMessage").innerHTML = "<font color='red'>对不起,此线路名称已存在.</font>";
			     				FlagLineName = false;
			     			}	
						},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
}
	
</script>

<html:form action="/lines.do?method=save" styleId="linesForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/netresource/line/config/ApplicationResources-line">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="line.form.heading"/></div>
    </caption>
    
    <input type=hidden name="partnerId" id="partnerId" value="${linesForm.partner}"/>
    <input type=hidden name="id" id="id" value="${linesForm.id}"/>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.name" />
        </td>
        <td class="content">
            <html:text property="lineName" styleId="lineName"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'线路名称',maxLength:100" value="${linesForm.lineName}" onblur="valLineName()" />
            <span id = "lineNameMessage"></span>
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.no" />
        </td>
        <td class="content">
            <html:text property="lineNo" styleId="lineNo"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'线路编号',maxLength:100" value="${linesForm.lineNo}" onblur="valLineNo()" />
            <span id = "lineNoMessage"></span>
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.remark" />
        </td>
        <td class="content">
            <html:text property="remark" styleId="remark"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'线路信息',maxLength:255" value="${linesForm.remark}"/>
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.maintainType" />
        </td>
        <td class="content">
        	<eoms:dict key="dict-partner-serviceArea" dictId="maintainType" isQuery="false"  alt="allowBlank:false,vtext:''"
				defaultId="${linesForm.maintainType}" selectId="maintainType" beanId="selectXML" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.region" />
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
            <font color='red'> * </font><fmt:message key="line.city" />
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
            <font color='red'> * </font><fmt:message key="line.grid" />
        </td>
        <td class="content">
            <!-- 所属网格 -->			
			<select name="grid" id="grid" 
				alt="allowBlank:true,vtext:'请选择所属网格'" onchange="changePartner(0)">
				<option value="">
					--请选择所属网格--
				</option>				
			</select>
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.partner" />
        </td>
        <td class="content">
            <html:text property="partner" styleId="partner"
								styleClass="text medium" readonly="true"
								alt="allowBlank:true,vtext:''" />
				<font color='blue'> * 系统根据网格信息自动填写.</font>
        </td>
    </tr>
        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.manager" />
        </td>
        <td class="content">
            <html:text property="manager" styleId="manager"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'客户经理',maxLength:100" value="${linesForm.manager}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.managertel" />
        </td>
        <td class="content">
            <html:text property="managerTel" styleId="managerTel"
                        styleClass="text medium"
                        alt="vtype:'number',allowBlank:false,vtext:'客户经理电话',maxLength:100" value="${linesForm.managerTel}" />
        </td>
      </tr>
      
      <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.contacter" />
        </td>
        <td class="content">
            <html:text property="contacter" styleId="contacter"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'联系人',maxLength:100" value="${linesForm.contacter}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.contactertel" />
        </td>
        <td class="content">
            <html:text property="contacterTel" styleId="contacterTel"
                        styleClass="text medium"
                        alt="vtype:'number',allowBlank:false,vtext:'联系人电话',maxLength:100" value="${linesForm.contacterTel}" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.openTime" />
        </td>
        <td class="content">
            <input property="openTime" id="openTime" name="openTime"
                        readonly="readonly" type="text" styleClass="text medium"
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" value="${linesForm.openTime}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.userYear" />(单位:年)
        </td>
        <td class="content">
            <html:text property="userYear" styleId="userYear"
                        styleClass="text medium"
                        alt="vtype:'number',allowBlank:false,vtext:'使用年限',maxLength:4" value="${linesForm.userYear}"/>
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.beginLong" />
        </td>
        <td class="content">
            <html:text property="beginLong" styleId="beginLong"
                        styleClass="text medium" value="${linesForm.beginLong}"
                        alt="re:/^-?\d{0,4}\.{1}\d{0,7}$/,re_vt:'经纬度格式必须为:xxxx.xxxxxx,小数点前最多 4 位,小数点后最多 7 位.'" />
            <span id="beginLongMessage"></span>
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.beginLat" />
        </td>
        <td class="content">
            <html:text property="beginLat" styleId="beginLat"
                        styleClass="text medium" value="${linesForm.beginLat}"
                        alt="re:/^-?\d{0,4}\.{1}\d{0,7}$/,re_vt:'经纬度格式必须为:xxxx.xxxxxx,小数点前最多 4 位,小数点后最多 7 位.'" />
            <span id="beginLatMessage"></span>
        </td>
     </tr>
     
     <tr>   
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.endLong" />
        </td>
        <td class="content">
            <html:text property="endLong" styleId="endLong"
                        styleClass="text medium" value="${linesForm.endLong}"
                        alt="re:/^-?\d{0,4}\.{1}\d{0,7}$/,re_vt:'经纬度格式必须为:xxxx.xxxxxx,小数点前最多 4 位,小数点后最多 7 位.'" />
            <span id="endLongMessage"></span>
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.endLat" />
        </td>
        <td class="content">
            <html:text property="endLat" styleId="endLat"
                        styleClass="text medium" value="${linesForm.endLat}"
                        alt="re:/^-?\d{0,4}\.{1}\d{0,7}$/,re_vt:'经纬度格式必须为:xxxx.xxxxxx,小数点前最多 4 位,小数点后最多 7 位.'" />
            <span id="endLatMessage"></span>
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.level" />
        </td>
        <td class="content">
        	<select name="level" id="level"
				alt="allowBlank:false,vtext:'请选择维护级别'">
				<option value="">
					--请选择维护级别--
				</option>
				<logic:iterate id="PnrInspectCycle" name="PnrInspectCycle">
					<option value="${PnrInspectCycle.checkLevel}">
						${PnrInspectCycle.checkLevel}
					</option>
				</logic:iterate>
			</select>
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.errorScope" />(单位:米)
        </td>
        <td class="content">
            <html:text property="errorScope" styleId="errorScope" value="${linesForm.errorScope}"
                        styleClass="text medium" 
                        alt="re:/^(\d+)(\.\d+)?$/,re_vt:'误差范围必须为整数或小数',maxLength:25" />
        </td>
    </tr>
                                        
</table>
</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.save"/>" id='submitInput' name='submitInput' />
            &nbsp;&nbsp;
            <c:if test="${not empty linesForm.id}">
                <input type="button" class="btn" value="<fmt:message key="button.back"/>" 
                    onclick="javascript:history.back();"    />
            </c:if>
        </td>
    </tr>
</table>

<html:hidden property="id" value="${linesForm.id}" />
<html:hidden property="createTime" value="${linesForm.createTime}" />
<html:hidden property="creator" value="${linesForm.creator}" />
<html:hidden property="isdeleted" value="${linesForm.isdeleted}" />

</html:form>

<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
	var err = '${err}';
    if(err!=''){
    	alert(err);
    }
    var region = '${linesForm.region}';
    var city = '${linesForm.city}';
    var grid = '${linesForm.grid}';
    var level = '${linesForm.level}';
    
	if(region!=''){
 		document.getElementById("region").value = region;
		changeCity(1);
	}
	if(city!=''){
 		document.getElementById("city").value = city;
		changeGrid(1);
	}
	if(grid!=''){
		document.getElementById("grid").value = grid;
		changePartner(1);
	}
	if(level!=''){
		document.getElementById("level").value = level;
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>