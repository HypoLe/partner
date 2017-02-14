<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
var roleTree;
var v;
  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
   });

   function changeType1(){
    $('distributionType').value = "distribution";
   }
   /**
	*  打开选择资源页面
	*/
	function selectRes(){	
	
		var url = '${app}/partner/res/PnrResConfig.do?method=searchResBySheet&region=${areaId}&executeDept=${executeDept}';
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
	
	/**
	* 设置资源名称和ID
	*/
	function setRes(returnValue){
		
	if(returnValue){
			var index = returnValue.indexOf(',');
			if(index != -1){
				var resId = returnValue.substring(0,index);
	        	var resName = returnValue.substring(index+1);
	        		        	
	        	document.getElementById('mainResId').value = resId;
	        	document.getElementById('mainResName').value = resName;
	        	
			}
        } 
      
		
	}
   /**
	*  打开选择资源页面--多选
	*/
	function selectMulRes(){	
		var url = '${app}/partner/res/PnrResConfig.do?method=searchResBySheet&sel=${multiple}&region=${areaId}&executeDept=${deptId}';
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
	
	/**
	* 设置资源名称和ID --多选
	*/
	function setMulRes(returnValue,returnName){
	if(returnValue!="null"){
    			document.getElementById('mainResId').value = returnValue;
	        	document.getElementById('mainResName').value = returnName;
	}
    			        	
	}
	//控制多选和单选按钮
	function selNet(obj){
	
	   var sig = document.getElementById('sig');
	   var mul = document.getElementById('mul');
		
		var selNet = obj.value;
		if(selNet=="101110104"){
			sig.style.display="none";
			mul.style.display="inline-block";
		}else{
			mul.style.display="none";
			sig.style.display="inline-block";
			document.getElementById('mainResId').value="";
			document.getElementById('mainResName').value="";
		}
	}
</script>

	<html:form action="/pnrTaskTicket.do?method=performAdd" styleId="theform" >
	
			  <input type="hidden" name="distributionType" id="distributionType" value=""/>
			  
<table class="formTable">
	<tr>
	  <td class="label">工单主题*</td>
	  <td colspan="3">
	    <input type="text" name="title" class="text max" id="title"  maxLength="20"
			value="" alt="allowBlank:false,maxLength:200,vtext:'请输入工单主题，最大长度为20个汉字！'"/>
	  </td>
	</tr>

	<tr>
	  <td class="label">操作人*</td>
	  <td class="content">
	  <eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao"/>
	  <input type="hidden" name="initiator" value=${userId}>
	  </td>
	  
	  <td class="label">操作人部门*</td>
	  <td class="content"><eoms:id2nameDB id="${deptId}" beanId="tawSystemDeptDao"/></td>
	</tr>

</table>
		
		 <!-- 工单基本信息 --> 
<table id="sheet" class="formTable" >
		<tr>
			<td class="label">工单子类型*</td>
			
			
			<td class="content">
				<eoms:comboBox name="mainFaultNet" id="mainFaultNet" defaultValue=""
					initDicId="1011101" alt="allowBlank:false" styleClass="select" onchange="selNet(this)"/>
			</td>
		
		  
		  
		  <td class="label">站点*</td>
			<td class="content">
				<input class="text" type="text" name="mainResName" readonly="true" 
					id="mainResName" alt="allowBlank:false" value=""/>
				<input type="hidden" name="mainResId" id="mainResId" value="" />
				<input type="button" class="btn" value="选择" onclick="selectRes()" id="sig"/>
				<input type="button" class="btn" value="选择" onclick="selectMulRes()" id="mul" style="display:none;"/>
			</td>	  
		</tr>
		<tr>
		  <td class="label">计划开始时间*</td>
		  <td class="content">
		    <input type="text" class="text" name="sheetAcceptLimit" readonly="readonly" 
				id="sheetAcceptLimit" value="" 
				onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:false"/>  
		  </td>
		  <td class="label">计划结束时间*</td>
		  <td class="content">
		    <input type="text" class="text" name="sheetCompleteLimit" readonly="readonly" 
				id="sheetCompleteLimit" value="" 
				onclick="popUpCalendar(this, this)" alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:false"/>   
		  </td>		  
		</tr>
		<tr>
 				
			<td class="label">涉及专业*</td>
			<td class="content" colspan=3>
				<eoms:comboBox name="mainSpecialty" id="mainSpecialty" defaultValue=""
					initDicId="1010108" alt="allowBlank:false" multiple="true"  styleClass="select"/>
			</td>
		</tr>
		<tr>
 			<td class="label">
				工单内容*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入工单内容，最多输入 2000 字符'"></textarea>
			</td>
		</tr>
</table>
<!-- 附件-->
<table id="sheet" class="formTable">
	  <tr>
		    <td class="label">
		    	附件
			</td>	
			<td colspan="3">					
		    <eoms:attachment name="sheetMain" property="sheetAccessories" startsWith="ddd"
		            scope="request" idField="sheetAccessories" appCode="pnrActTaskTicket" alt="allowBlank:false;请选择保存的附件"/> 				
		    </td>
	   </tr>			  
</table>
<!-- -->
        <!--接收组-->
        <fieldset>
            <legend>
		 <span id="roleName1">
		 	接收组
		 </span>
            </legend>
            <div class="x-form-item" >
                <eoms:chooser id="sendObject1" panels="[ {text:'部门',dataUrl:'${app}/xtree.do?method=dept',rootId:'${country}'}]"  type="dept" flowId="1103" config="{returnJSON:true,showLeader:true}"
                              category="[{id:'candidateGroup',text:'接收',allowBlank:false,childType:'dept',limit:20,vtext:'请选择处理对象'}]"
                              data="" />
            </div>
        </fieldset>
	    <p/>	
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				生成
			</html:submit>
		</div>
		
	</html:form>
<%@ include file="/common/footer_eoms.jsp"%>