<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
<!--
Date.prototype.getWeekOfYear = function(weekStart) { // weekStart：每周开始于周几：周日：0，周一：1，周二：2 ...，默认为周日  
    weekStart = (weekStart || 0) - 0;  
    if(isNaN(weekStart) || weekStart > 6)  
        weekStart = 0;    
  
    var year = this.getFullYear();  
    var firstDay = new Date(year, 0, 1);  
    var firstWeekDays = 7 - firstDay.getDay() + weekStart;  
    var dayOfYear = (((new Date(year, this.getMonth(), this.getDate())) - firstDay) / (24 * 3600 * 1000)) + 1;  
    return Math.ceil((dayOfYear - firstWeekDays) / 7) + 1;  
} 

var date = new Date(2013, 4, 7); // 注意：JS 中月的取值范围为 0~11  
var weekOfYear = date.getWeekOfYear(); // 当前日期是本年度第几周
//-->

Ext.onReady(function(){
 	v = new eoms.form.Validation({form:'recordForm'});
 	});

function save(){
   if(v.check()){
       var frm = document.forms["recordForm"];
       frm.submit();
   }
};
function audit(){

   if(v.check()){
       var frm = document.forms["recordForm"];
       frm.action = "../record/recordAction.do?method=save&state=2"
       frm.submit();
   }
};

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

			if(con==1){
                var country = '${pnrRecord.country}';
              	if(country!=''){
             		document.getElementById("country").value = country;
            	}
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
function remove(id){
    location.href= "../record/recordAction.do?method=remove&id="+id;
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
			if(con==1){
                var site = '${pnrRecord.site}';
              	if(site!=''){
             		document.getElementById("site").value = site;
            	}
			}		
					 	
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}

window.onload = function(){
	//修改时，自动加载原来的地市县区显示在修改页面
    var city = '${pnrRecord.city}';
		
	if(city!=''){
 		document.getElementById("city").value = city;
		changeCity(1);
	}

    var country = '${pnrRecord.country}';
	if(country!=''){
 		document.getElementById("country").value = country;
		changeSite(1);
	}
}

</script>

<form action="../record/recordAction.do?method=save" id="recordForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">新增档案管理</div>
	</caption>
	<tr>
	    <td class="label">
	       主题<font color="red" > *</font>
	    </td>
	    <td colspan="3">
	       <input  type="text" class="text" name="title" id="title" style="width: 90%"
					alt="allowBlank:false,vtext:'标题不能为空或者超过100个汉字！',maxLength:200"
					value="${pnrRecord.title} "/>
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
				<select name="country" id="country" class="select"onchange="changeSite(0);"
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
				<eoms:comboBox name="specialty" id="specialty" defaultValue="${pnrRecord.specialty}"
					initDicId="11225" alt="allowBlank:false" styleClass="select"/>
			</td>
	</tr>	
    <tr>
	    <td class="label">
	       档案分类<font color="red" > *</font>
	    </td>
			<td class="content">
				<eoms:comboBox name="recordType" id="recordType" defaultValue="${pnrRecord.recordType}"
					initDicId="127" alt="allowBlank:false" styleClass="select"/>
			</td>    
    </tr>


	<tr>
	    <td class="label">
	       概述<font color="red" > *</font>
	    </td>
	    <td colspan="3">
              <textarea class="textarea max" name="summary" id="summary" value="${pnrRecord.summary}"></textarea>
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       关键词<font color="red" > *</font>
	    </td>
	    <td colspan="3">
	       <input  type="text" class="text" name="keyword" id="keyword"  style="width: 80%"
					alt="allowBlank:false,vtext:'规则名称 不能超出1000个汉字！',maxLength:2000"
					value="${pnrRecord.keyword}"/><font color="red" > 多个关键词用,隔开</font>
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       附件
	    </td>
        <td class="content" colspan="3" height="100px">
               <eoms:attachment scope="request"  name="${pnrRecord.attachment}" idList="${pnrRecord.attachment}"  idField="attachment" property="attachment"  appCode="pnrcontact"  alt="allowBlank:true,vtext:'证书附件'"  />
        </td> 
	</tr>
	
</table>
</form>

<table>
  <tr>
    <td>
      <input type="button" class="btn" value="保存" onclick="save();"/>
      
	  <input type="button" class="btn" value="删除" onclick="remove('${pnrRecord.id }');"/>
    </td>
  </tr>
</table>

<%@ include file="/common/footer_eoms.jsp"%>