<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/lines.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<script language=javascript>
<!--
	window.onload = function(){
								//合作伙伴
								var providerName = "${providerBuffer}"; 
								var arrOptionsP=providerName.split(",");
								var objp=$("provider");					
								var m=0;
								var n=0;
								for(m=0;m<arrOptionsP.length;m++){
									var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
									objp.options[n]=optp;
									n++;
									m++;
								}
		}
	Ext.onReady(function() {
	v = new eoms.form.Validation({form:'lineForm'});
	});
		
  function validateForm(){
      var frm = document.forms[0];
      if( frm.beginTime.value == "" && frm.endTime.value != "" ){
          alert("请填写正确的时间段！");
          return false;      
      }
      if( frm.beginTime.value != "" && frm.endTime.value == "" ){
          alert("请填写正确的时间段！");
          return false;      
      }
      
      return true;
  }
  
  	function sub(){
		
		var validate = validateForm();
		
		if(validate){
			if(v.check())
			 $("lineForm").submit();
		}else{
			return false;
			
		}
		
	}		
	
//-->
</script>


<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">



	<html:form action="/pnrStats.do?method=getUseCaseStatReport" styleId="lineForm" method="post" >
	
		<table align="center" class="formTable">
			<caption>
				<div class="header center">合作伙伴使用情况统计</div>
			</caption>
				<!-- 上报的时间段 -->
				<tr>
				  	<td class="label">
						上报时间段：
					</td>
					<td class="content" >
				          <input type="text" size="20" readonly="true" class="text" 
				               name="beginTime" id="beginTime"
				               onclick="popUpCalendar(this,this,null,null,null,true,-1);"
				               alt="vtype:'lessThen',link:'endTime',allowBlank:true,vtext:'结束时间要大于开始时间'"
				               value="${beginTime}"  />
					</td>
					<td class="label">
						至
					</td>
					<td class="content">
				          <input type="text" size="20" readonly="true" class="text" 
			                name="endTime" id="endTime"
			                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
			                alt="vtype:'moreThen',link:'beginTime',allowBlank:true,vtext:'结束时间要大于开始时间'" 
			                value="${endTime}" />
					</td>
				</tr>
	

			<tr>
				<!-- 合作伙伴 -->
				<td class="label">
					<fmt:message key="line.provider" />:
				</td>
				<td class="content" colspan=3>
					<!-- 合作伙伴 -->			
					<select name="provider" id="provider" >
						<option value="">
							--请选择合作伙伴--
						</option>	
															
					</select>
				</td>
			</tr>
			
			
		</table>
				<!-- 提交按钮 -->
				<td>
					<input type="button" class="btn" value="<fmt:message key="button.search"/>" onclick="sub();" />
				</td>
		
	</html:form>






</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>