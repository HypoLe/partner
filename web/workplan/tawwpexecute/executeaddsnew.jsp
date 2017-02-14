<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpMonthPlanVO"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpExecuteContentVO"%>
<%@ page import="java.util.*,java.io.File,
                 com.boco.eoms.commons.accessories.service.*,
                 com.boco.eoms.commons.accessories.model.*,
                 com.boco.eoms.base.util.StaticMethod,
                 com.boco.eoms.base.util.ApplicationContextHolder"%>

<style type="text/css">
form {
	padding:20px 0;
}
em {font-weight:bold;color:red;margin:0 10px 0 2px;}
span.success{
	background:url(${app}/images/icons/nodetype/success.gif) no-repeat 0 center;
	padding-left:20px;
	color:green;
	font-weight:bold;
}
</style>
<script type="text/javascript">

function GoBack()
{
  window.navigate( "../tawwpexecute/dailyexecutelistview.do");
}

function onAddons(url)
{
  var _sHeight = 800;
  var _sWidth = 1200;
  var sTop=(window.screen.availHeight-_sHeight)/2;
  var sLeft=(window.screen.availWidth-_sWidth)/2;
  var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
  window.showModalDialog(url,window,sFeatures);
}

function onCopy(monthPlanId,userByStub,date,flag)
{
  if(!confirm("复制上一工作日执行周期为天的执行内容,请确认")){
    return;
  }
  window.navigate( "../tawwpexecute/newprevious.do?monthplanid=" + monthPlanId + "&userbystub=" + userByStub + "&date=" + date + "&flag=" + flag);
}

function onCopyold(monthPlanId,userByStub,date,flag,day)
{ 
  if(day==""){
	confirm("请选择所要复制的日期")
	return;
  }
  if(!confirm("复制"+ day +"日执行周期为天的执行内容,请确认!")){
    return;
  }
  window.navigate( "../tawwpexecute/newpreviousold.do?monthplanid=" + monthPlanId + "&userbystub=" + userByStub + "&date=" + date + "&flag=" + flag+"&day="+day);

}

function onFile(obj1,obj2,obj3,obj4,obj5,obj6,obj7,obj8,obj9)
{	
 
  var object1Name=obj1.name;
  var object1Value=obj1.value;
  var object2Name=obj2.name;
  var object2Value=obj2.value;
  var object3Name=obj3.name;
  var object3Value=obj3.value;
  var object4Name=obj4.name;
  var object4Value=obj4.value;
  var object5Name=obj5.name;
  var object5Value=obj5.value;
  var object6Name=obj6.name;
  var object6Value=obj6.value;
  var strShowSpanId = obj7;
  document.getElementById(obj8).style.display = "none";
  var formName = obj9;

  var _sHeight = 300;
  var _sWidth = 420;
  var sTop=(window.screen.availHeight-_sHeight)/2;
  var sLeft=(window.screen.availWidth-_sWidth)/2;
  var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
  window.showModalDialog('../tawwpexecute/uploadfilenew.do?name='+ object1Name+'&numname='+object2Name+'&resulturl='+object1Value+'&executecontentid='+object3Value+'&executecontentuseridname='+object4Name+'&executecontentuserid='+object4Value+'&userid='+object5Value+'&stubuser='+object6Value+'&action=add&showspanid='+strShowSpanId+'&formname='+formName,window,sFeatures);
}
function addworkflow(i,id) {

   var workplanType=document.getElementById('workplanType'+i).options[document.getElementById('workplanType'+i).selectedIndex].value;
      
   var url = '${app}/workbench/onlineuser/tawWorkbenchOnlineUsers.do';
   	   
   if(workplanType=="1"){
  	   	  url="${app}/sheet/commonfault/commonfault.do?method=showNewSheetPage";
   }else{
  	   	  url="";	
   }
   window.open(url, '', 'channelMode, menubar=no, toolbar=no, location=no, directories=no, status=yes, resizable=yes, scrollbars=yes, width=600, height=400, fullscreen=no');
   document.getElementById("workflow"+i).innerHTML = "编号<input type='text' name='workflowId"+i+"' /><input type='button' value='确定' onclick=submitworkflow('"+i+"','"+id+"')><input type='button' value='取消' onclick=removeDiv('"+i+"')>";
  
}
function submitworkflow(i,id){
    if(document.getElementById("workflowId"+i).value==""){
       alert("请输入工单编号");
    }else {  
      var workflowId=document.getElementById('workflowId'+i).value;
      var workplanType=document.getElementById('workplanType'+i).options[document.getElementById('workplanType'+i).selectedIndex].value;
      window.frames['workflowFrame'+i].document.forms[0].action='../tawwpexecute/workflowAdd.do?workflowId='+workflowId+'&executeId='+id+'&executeType='+workplanType; 
      window.frames['workflowFrame'+i].document.forms[0].submit();
      document.getElementById("workflow"+i).innerHTML="";
    }
} 

function removeDiv(i){
  document.getElementById("workflow"+i).innerHTML="";
}

function OnSubmit(formindex,flag,years,dateTime,size,executeflag,btn){
	
     //var size = document.forms['executeadds_'+formindex+'_'+flag]['maxsize'+formindex+'_'+flag].value;
      
     var fileFlag ='';
     var fileCount = '';
     var count ='';
     var bool = true;
     for(i=0;i<size;i++){
     	fileFlag = document.forms['executeadds_'+formindex+'_'+flag]['fileFlag'+i+'_'+formindex+'_'+flag].value;
     	// 1 是 ，0 否
     	fileCount = document.forms['executeadds_'+formindex+'_'+flag]['count'+i+'_'+formindex+'_'+flag].value;
     	if(fileFlag==1&&fileCount<1){
     	alert('此模板选择了必须上传附件,请上传附件');
        document.forms['executeadds_'+formindex+'_'+flag]['count'+i+'_'+formindex+'_'+flag].focus();
        bool = false;
     	break;
     	}
		if(years!=null&&dateTime!=null&&years<dateTime){
     		reason = document.forms['executeadds_'+formindex+'_'+flag]['reason'+i+'_'+formindex+'_'+flag].value;
     		 
     		if(reason==null||reason==""){
     			alert('请输入补填原因');
     			document.forms['executeadds_'+formindex+'_'+flag]['reason'+i+'_'+formindex+'_'+flag].focus();
     			return false;
     			break;
     		}
     	}
     }
     //  begin 四川 日常执行添加是否保存信息。 v_gongyufeng——20090921
   	 var eflag = document.forms['executeadds_'+formindex+'_'+flag]['executeFlag_'+formindex+'_'+flag].value
   	 //alert(eflag);
     if(executeflag.indexOf("1")>=0 || executeflag.indexOf("2")>=0 || eflag.indexOf("1")>=0){
     if(!confirm("是否覆盖已经执行的信息!")){
     	bool = false;
      }
     }
     
     // end
     
	if(!bool) return false;
	
	//异步提交
	var frm = btn.form;
	
	function success(x){
		Ext.get(frm).unmask();
		var info = {};
		try{info = eoms.JSONDecode(x.responseText);}catch(e){}
		if(info.success===false){
			alert(info.message || "保存中发现错误，请联系管理员");
			return;
		}
		else if(info.success===true){
			Ext.fly('status_'+formindex+'_'+flag).update(info.message || "保存成功").highlight();
			document.forms['executeadds_'+formindex+'_'+flag]['executeFlag_'+formindex+'_'+flag].value='1';
			
		}
	}
	function failure(){
		Ext.get(frm).unmask();
		
		alert("保存中发现错误，请联系管理员");
	}
	
	Ext.get(frm).mask('保存中，请稍候...');
	
	Ext.Ajax.request({
		method : 'Post',
		url : frm.action,
		success : success,
		failure : failure,
		params : Ext.lib.Ajax.serializeForm(frm),
		timeout: 0
		
	});
	return false;
}


 function submitform(formindex,flag,monthPlanId,contentIdStr,sizeStr,executeFlag,btn)
{
   	var frm = btn.form;
    var size;
    size = sizeStr;
	function success(x){
		Ext.get(frm).unmask();
		var info = {};
		try{info = eoms.JSONDecode(x.responseText);}catch(e){}
		if(info.success===false){
			alert(info.message || "保存中发现错误，请联系管理员");
			return;
		}
		else if(info.success===true){
		    var message =  info.message;
		    if(message!=''){
		    var messageArr = message.split("^");
			for(var i =0;i<size;i++){
			if(messageArr[i]!=''){
			var fromArray = messageArr[i].split("$");
			var showSpan = document.getElementById('showLink_'+i+'_'+formindex+'_'+flag);
			if(fromArray.length>0){
			  var fileArray = fromArray[1].split("@");
			  showSpan.innerHTML = "";
			  showSpan.innerHTML = "<li><a target='_blank' href='${app}/workplan/tawwpfile/"+fileArray[0]+"'>"+fileArray[1]+"</a><br>";
			  frm['content'+i+'_'+formindex+'_'+flag].value = fromArray[0];
			  frm['count'+i+'_'+formindex+'_'+flag].value = 1;
			 }
		 	}
		 }
		 	Ext.fly('status_'+formindex+'_'+flag).update("保存成功").highlight();
			alert("保存成功");
			}else{
			alert("没有得到对应的结果。请联系管理员");
			}
			
		
		}
	}
	function failure(){
		Ext.get(frm).unmask();
		alert("保存中发现错误，请联系管理员");
	}
	
	Ext.get(frm).mask('保存中，请稍候...');
	
	Ext.Ajax.request({
		method : 'Post',
		url : "../tawwpexecute/interfacesaves.do?monthplanid='"+monthPlanId+"'&contentidstr="+contentIdStr,
		success : success,
		failure : failure,
		params : Ext.lib.Ajax.serializeForm(frm),
		timeout: 100
	});
	return false;
 
}
</script>
<%
	String flag = (String) request.getAttribute("flag");
	String currUser = (String) request.getAttribute("curruser");
	String date = (String) request.getAttribute("date");
	
	String normalFlag="";

	HashMap monthplanvoHash = (HashMap) request
			.getAttribute("monthplanvoHash");
	HashMap monthplancontentHash = (HashMap) request
			.getAttribute("monthplancontentHash");
	HashMap stubMonthplanvoHash = (HashMap) request
			.getAttribute("stubMonthplanvoHash");
	HashMap stubMonthplancontentHash = (HashMap) request
			.getAttribute("stubMonthplancontentHash");
	
	ITawCommonsAccessoriesManager mgr = (ITawCommonsAccessoriesManager) ApplicationContextHolder.getInstance().getBean("ItawCommonsAccessoriesManager");
%>

  <br/>
  <h1><bean:message key="executeadds.title.formTitle" /></h1>

        <% 
        Set key = monthplanvoHash.keySet();
        Iterator i = key.iterator();
        int formindex = 0;
        int sizeStr = 0;
        while(i.hasNext()){
            
            TawwpMonthPlanVO tawwpmonthplanvo = (TawwpMonthPlanVO)i.next();
            List executeContentVOList = (List)monthplanvoHash.get(tawwpmonthplanvo);
            String contentIdStr = "";
            String executeFlag = "";
            String executeType = tawwpmonthplanvo.getExecuteType();
            for(int m = 0; m<executeContentVOList.size(); m++){
              contentIdStr += (((TawwpExecuteContentVO)executeContentVOList.get(m)).getId() + ",");
              executeFlag  += (((TawwpExecuteContentVO)executeContentVOList.get(m)).getExecuteFlag());
            }
            
            
            
            sizeStr = executeContentVOList.size();
            HashMap contentHash = (HashMap)monthplancontentHash.get(tawwpmonthplanvo);
            Iterator hashKeys = null;
            String stubFlag = "own";
            String tempFormId = "";
            String tempVar = "";
            String formDataId = "";
            TawwpExecuteContentVO tawwpExecuteContentVO = null;
            if(contentHash.size() != 0){
        %>
      <form name="executeadds_<%=formindex %>_<%=stubFlag %>" method="POST" action="../tawwpexecute/executesavesnew.do?contentidstr=<%=contentIdStr.substring(0,contentIdStr.length()-1)%>&flag=<%=flag%>&executetype=<%=executeType%>&formindex=<%=formindex %>&stubFlag=<%=stubFlag %>">
        <div class="skin-blue"><div class="box">
        	<span class="rc-tp"><span></span></span>
        	<div class="bd" >
        	计划：<em><%=tawwpmonthplanvo.getName() %></em>网元:<em><%=tawwpmonthplanvo.getNetName() %></em>
        	<span id="status_<%=formindex %>_<%=stubFlag %>" class="success"></span>
        	</div>    
        	<span class="rc-bt"><span></span></span>
		</div></div>
		<input type="hidden" name="executeFlag_<%=formindex %>_<%=stubFlag %>" value="<%=executeFlag%>">
        <table border="0"  cellspacing="1" cellpadding="1" class="listTable" align=center width="90%">
          <thead>
          <tr>
            <td width="15%">
                作业项目
            </td>
            <!-- 
            <td width="15%">
                业务类型
            </td>
            <td width="15%">
                执行单位级别
            </td>
            <td width="15%">
                适用说明
            </td>
             -->
            <td width="5%" >
                周期
            </td>
            <td width="10%">
                填写时间
            </td>
            <td width="20%">
                执行内容
            </td>
            <td width="6%">
                备注
            </td>
             <td width="6%">
               是否正常
            </td>
            <td width="10%">
                附件
            </td>
            <td width="10%">
                执行表格
            </td>
            <td width="6%">
                同组
            </td>
            <td width="15%"> 
                关联工单
            </td>
            <td width="10%">
                执行帮助
            </td>
            <td width="5%">
                指导文件
            </td>
            <%  String years=(String) request.getAttribute("years");
 				String dateTime=StaticMethod.getCurrentDateTime().substring(0,10);
 				if(dateTime.compareTo(years)>0){           
            %>
            <td width="5%">
                补填原因
            </td>
           <%} %>  
          </tr>
          </thead>
          <tbody>
          <%    
          	  
          	  String commondFlag = "";
              String writeDate = "";
              hashKeys = contentHash.keySet().iterator();
              while (hashKeys.hasNext()) {
                tempFormId = (String)(hashKeys.next());
                tempVar = (String)contentHash.get(tempFormId);
              
                String[] strArray = tempVar.split(",");
                //sizeStr = strArray.length;  
                tawwpExecuteContentVO = (TawwpExecuteContentVO)executeContentVOList.get(Integer.parseInt(strArray[0]));
                if(!"".equals(tawwpExecuteContentVO.getCommand()) && tawwpExecuteContentVO.getCommand() !=null) {
                	commondFlag = "1";
                }
                
                String reason=tawwpExecuteContentVO.getReason();
                if(reason==null||"".equals(reason)){
                	tawwpExecuteContentVO.setReason("");
                }
                if(tawwpExecuteContentVO.getWriteDate()!=null&&!tawwpExecuteContentVO.getWriteDate().equals("")){
                writeDate = tawwpExecuteContentVO.getWriteDate();
               }else{
         	    writeDate = StaticMethod.getCurrentDateTime();
               }
                
          %>
          <tr class="tr_show">
            <td width="15%" class="clsthd2">
              <%=tawwpExecuteContentVO.getName()%>
              <input type="hidden" name="maxsize<%=formindex %>_<%=stubFlag %>" value="<%=strArray.length%>">
              <input type="hidden" name="fileFlag<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getFileFlag()%>">
              <input type="hidden" name="executecontentuserid<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getExecuteContentUserId()%>">
              <input type="hidden" name="executecontentid<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getId()%>">
              <%
                if("1".equals(tawwpExecuteContentVO.getStubFlag())){
              %>
              [<font color="red"><bean:message key="stubuseradd.title.formAgent" /></font>]
              <input type="hidden" name="userid<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getUserByStub()%>">
              <input type="hidden" name="stubuser<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" value="<%=currUser%>">
              <%
                } else {
              %>
              <input type="hidden" name="userid<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" value="<%=currUser%>">
              <input type="hidden" name="stubuser<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" value="">
              <%
                }
              %>
              
          <%if(!"0".equals(tawwpExecuteContentVO.getExecuteFlag())){%>
          	<script type="text/javascript">eoms.$('status_<%=formindex %>_<%=stubFlag %>').update("本计划已执行");</script>
          <%}%>
            </td>
            <!-- 
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getBotype()) %>
	        </td>
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getExecutedeptlevel()) %>
	        </td>
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getAppdesc()) %>
	        </td>
	         -->
            <td width="5%">
              <%=tawwpExecuteContentVO.getCycleName()%>
            </td>
            <td width="10%">
              <input type="hidden" name="writeDate<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" size="20"    class="text" value="<%=StaticMethod.getCurrentDateTime()%>"><%=writeDate %>
            </td>
            <td width="10%">
              <textarea name="content<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>"  rows=3 cols="30" value=""><%=tawwpExecuteContentVO.getContent()%></textarea>
            </td>
            <td width="6%" align=center>
              <textarea name="remark<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>"  rows=3 cols="30" value=""><%=tawwpExecuteContentVO.getRemark()%></textarea>
            </td>
             <td width="6%" align=center>
            	<%normalFlag = "normalFlag"+strArray[0]+"_"+formindex+"_"+stubFlag; %>
       			<eoms:dict key="dict-workplan" dictId="normalFlag" beanId="selectXML" defaultId="<%=tawwpExecuteContentVO.getNormalFlag()%>"  isQuery="false"  selectId="<%=normalFlag%>"  />
      		</td>
            <td width="10%" class="clsthd2" align="left">
              <div id="file<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" style="">
              <%
                if(!"".equals(tawwpExecuteContentVO.getFileName())){
                  String[] tempStr = tawwpExecuteContentVO.getFileName().split(",");
                  for(int j=0; j<tempStr.length; j++){
                    String[] tempArray = tempStr[j].split("@");
              %>
              <a href="<%=tempArray[1]%>"><%=tempArray[0]%></a><br>
              <%
                  }
                }
              %>
              </div>
              <% if(tawwpExecuteContentVO.getFileFlag()!=null&&tawwpExecuteContentVO.getFileFlag().equals("1")&&tawwpExecuteContentVO.getFileCount()<1){%>
              	<font color="red">***</font>
              <%}%>
              <span id="showLink_<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>"></span>
              <input type="hidden" name="fileCount<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getFileCount()%>">
              <input type="hidden" name="filename<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" dataType="null" value="<%=tawwpExecuteContentVO.getFileName()%>">
              <input type="button" class="button filecount" name="count<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" onclick="javascript:onFile(filename<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>,count<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>,executecontentid<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>,executecontentuserid<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>,userid<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>,stubuser<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>,'showLink_<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>','file<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>','executeadds_<%=formindex %>_<%=stubFlag %>');" value="<%=tawwpExecuteContentVO.getFileCount()%>"><bean:message key="executeadds.title.labFile" />
            </td>
      
            <td width="10%" class="clsthd2" rowspan="<%=strArray.length%>">
              <%
                String formDataId_=StaticMethod.null2String(tawwpExecuteContentVO.getFormDataId(),"");
                if(formDataId!=null||"".equals(tawwpExecuteContentVO.getFormDataId())){     
              %>
              <a href="javascript:onAddons('../tawwpaddons/addonsread.do?action=new&window=new&formid=<%=tawwpExecuteContentVO.getFormId()%>&monthid=<%=tawwpmonthplanvo.getId() %>&date=<%=date%>&executeId=<%=tawwpExecuteContentVO.getId() %>&reaction=/tawwpexecute/redirection.jsp');"><%=tawwpExecuteContentVO.getFormName()%></a>
              <input type="hidden" name="formdataid<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" value="<%=formDataId_%>">
              <%} else { %>
              <a href="javascript:onAddons('../tawwpaddons/addonsread.do?action=edit&window=edit&executeId=<%=tawwpExecuteContentVO.getId() %>&reaction=/tawwpexecute/redirection.jsp');"><%=tawwpExecuteContentVO.getFormName()%></a>
              <input type="hidden" name="formdataid<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getFormDataId()%>">
              <%
                }
              %>
            </td>
            <td width="6%" align=center>
              <%
                if("1".equals(executeType)){
              %>
              <a href="../tawwpexecute/sameview.do?executecontentid=<%=tawwpExecuteContentVO.getId()%>" >
              <img src="${app }/images/icons/list.png"> 
              </a>
              <%
                }
              %>
            </td>
            <td width="6%" align=center >
                
		          <select name="workplanType<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" >
			        <option value="1">故障处理工单</option>
                    <option value="2">网优工单</option>
                  </select>
                  &nbsp; 
                  <input type="button" value="添加" class="button"  onclick="javascript:addworkflow('<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>','<%=tawwpExecuteContentVO.getId()%>')" >
                <iframe name="workflowFrame<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" src="../tawwpexecute/workflowList.do?executeId=<%=tawwpExecuteContentVO.getId()%>" frameborder=0></iframe>
                  
                <div id="workflow<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>" > 
                </div>  
            </td> 
            <td width="10%">
              <%=tawwpExecuteContentVO.getExtendremark()%>
            </td>
            <td width="5%">
              <%
                List list1 = null;
                if(!tawwpExecuteContentVO.getAccessories().equals("")){
                  list1 =mgr.getAllFileById(tawwpExecuteContentVO.getAccessories());
                }
                if(list1 != null) {
                  for(int num=0; num<list1.size(); num++){
                  TawCommonsAccessories accessories=(TawCommonsAccessories)list1.get(num);

               %>
               <script language="javascript">
               document.write("<a href=\"${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=<%=accessories.getId()%>&filelist=<%=tawwpExecuteContentVO.getAccessories()%>\">");
               document.write("<img src=\"${app}/images/page.gif\" border=\"0\" align=\"absmiddle\"><%=StaticMethod.nullObject2String(accessories.getAccessoriesCnName())%></a>");
               </script>
               <%
                  } 
                }
               %>
            </td>
             <%years=(String) request.getAttribute("years");
 				dateTime=StaticMethod.getCurrentDateTime().substring(0,10);
 				if(dateTime.compareTo(years)>0){           
            %>
            <td width="5%" align=center>
              <textarea name="reason<%=strArray[0]%>_<%=formindex %>_<%=stubFlag %>"  rows=3 cols="30" value=""><%=tawwpExecuteContentVO.getReason()%></textarea>
            </td>
            <% }%>
          </tr>
        <%
          for(int k=1; k<(strArray.length); k++){
            tawwpExecuteContentVO = (TawwpExecuteContentVO)executeContentVOList.get(Integer.parseInt(strArray[k]));
            String reason1=tawwpExecuteContentVO.getReason();
                if(reason1==null||"".equals(reason1)){
                	tawwpExecuteContentVO.setReason("");
                }
        %>
          <tr class="tr_show">
            <td width="15%" class="clsthd2">
              <%=tawwpExecuteContentVO.getName()%>
              <input type="hidden" name="fileFlag<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getFileFlag()%>">
              <input type="hidden" name="executecontentuserid<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getExecuteContentUserId()%>">
              <input type="hidden" name="executecontentid<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getId()%>">
              <%
                if("1".equals(tawwpExecuteContentVO.getStubFlag())){
              %>
              [<font color="red"><bean:message key="stubuseradd.title.formAgent" /></font>]
              <input type="hidden" name="userid<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getUserByStub()%>">
              <input type="hidden" name="stubuser<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" value="<%=currUser%>">
              <%} else {%>
              <input type="hidden" name="userid<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" value="<%=currUser%>">
              <input type="hidden" name="stubuser<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" value="">
              <%
                }
              %>
            </td>
            <!-- 
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getBotype()) %>
	        </td>
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getExecutedeptlevel()) %>
	        </td>
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getAppdesc()) %>
	        </td>
	         -->
            <td width="5%">
              <%=tawwpExecuteContentVO.getCycleName()%>
            </td>
            <td width="10%">
              <input type="hidden" name="writeDate<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" size="20" class="text" value="<%=StaticMethod.getCurrentDateTime()%>"><%=StaticMethod.getCurrentDateTime() %>
            </td>
            <td width="20%">
              <textarea name="content<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>"  rows=3 cols="30" value=""><%=tawwpExecuteContentVO.getContent()%></textarea>
            </td>
            <td width="6%" align=center>
              <textarea name="remark<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>"  rows=3 cols="30" value=""><%=tawwpExecuteContentVO.getRemark()%></textarea>
            </td>
             <td width="6%" align=center>
             	<%normalFlag = "normalFlag"+strArray[k]+"_"+formindex+"_"+stubFlag; %>
       			<eoms:dict key="dict-workplan" dictId="normalFlag" beanId="selectXML" defaultId="<%=tawwpExecuteContentVO.getNormalFlag()%>"  isQuery="false"  selectId="<%=normalFlag%>"  />
      			 </td>
            <td width="10%" class="clsthd2" align="left">
              <div id="file<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" style="">
              <%
                if(!"".equals(tawwpExecuteContentVO.getFileName())){
                  String[] tempStr = tawwpExecuteContentVO.getFileName().split(",");
                  for(int j=0; j<tempStr.length; j++){
                    String[] tempArray = tempStr[j].split("@");
              %>
                <a href="<%=tempArray[1]%>"><%=tempArray[0]%></a><br>
              <%
                  }
                }
              %>
              <% if(tawwpExecuteContentVO.getFileFlag()!=null&&tawwpExecuteContentVO.getFileFlag().equals("1")&&tawwpExecuteContentVO.getFileCount()<1){%>
              	<font color="red">***</font>
              <%}%>
              </div>
              <span id="showLink_<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>"></span>
              <input type="hidden" name="fileCount<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getFileCount()%>">
              <input type="hidden" name="filename<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" dataType="null" value="<%=tawwpExecuteContentVO.getFileName()%>">
              <input type="button" class="button filecount" name="count<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" onclick="javascript:onFile(filename<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>,count<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>,executecontentid<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>,executecontentuserid<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>,userid<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>,stubuser<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>,'showLink_<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>','file<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>','executeadds_<%=formindex %>_<%=stubFlag %>');" value="<%=tawwpExecuteContentVO.getFileCount()%>"><bean:message key="executeadds.title.labFile" />
            </td>

            <td width="6%" align=center>
            <%
              if("1".equals(executeType)){
            %>
              <a href="../tawwpexecute/sameview.do?executecontentid=<%=tawwpExecuteContentVO.getId()%>" >
              <img src="${app }/images/icons/list.png"> 
              </a>
            <%
              }
            %>
              <input type="hidden" name="formdataid<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" value="<%=formDataId%>">
            </td>
            <td width="6%" align=center >
                 
		          <select name="workplanType<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" >
			        <option value="1">故障处理工单</option>
                    <option value="2">网优工单</option>
                  </select>
                  &nbsp; 
                  <input type="button" value="添加" class="button"  onclick="javascript:addworkflow('<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>','<%=tawwpExecuteContentVO.getId()%>')" >
                <iframe name="workflowFrame<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" src="../tawwpexecute/workflowList.do?executeId=<%=tawwpExecuteContentVO.getId()%>" frameborder=0></iframe>
                
                <div id="workflow<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>" > 
                </div>  
            </td>
            <td width="10%">
              <%=tawwpExecuteContentVO.getExtendremark()%>
            </td>
            <td width="5%">
              <%
                List list2 = null;
                if(!tawwpExecuteContentVO.getAccessories().equals("")){
                  list2 =mgr.getAllFileById(tawwpExecuteContentVO.getAccessories());
                }
                if(list2 != null) {
                  for(int num=0; num<list2.size(); num++){
                  TawCommonsAccessories accessories=(TawCommonsAccessories)list2.get(num);

               %>
               <script language="javascript">
               document.write("<a href=\"${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=<%=accessories.getId()%>&filelist=<%=tawwpExecuteContentVO.getAccessories()%>\">");
               document.write("<img src=\"${app}/images/page.gif\" border=\"0\" align=\"absmiddle\"><%=StaticMethod.nullObject2String(accessories.getAccessoriesCnName())%></a>");
               </script>
               <%
                  } 
                }
               %>
            </td>
             <%years=(String) request.getAttribute("years");
 				dateTime=StaticMethod.getCurrentDateTime().substring(0,10);
 				if(dateTime.compareTo(years)>0){           
            %>
            <td width="5%" align=center>
              <textarea name="reason<%=strArray[k]%>_<%=formindex %>_<%=stubFlag %>"  rows=3 cols="30" value=""><%=tawwpExecuteContentVO.getReason()%></textarea>
            </td>
            <%} %>
          </tr>         
      <%
            }
          } 
      %>
        </tbody>
      </table>
      <br />
        <input type="hidden" value="<%=tawwpmonthplanvo.getId() %>" name="monthplanid">
        <input type="hidden" value="<%=flag %>" name="flag">
        <input type="hidden" value="<%=tawwpmonthplanvo.getUserByStub() %>" name="userbystub">
        <input type="hidden" value="<%=date%>" name="date">
        <input type="hidden" value="<%=years%>" name="years">
        <input type="hidden" value="<%=dateTime%>" name="dateTime">
        <!--input type="submit" value="<bean:message key="executeadds.title.btnSubmit" />" name="B1" class="submit"--> 
        <input type="submit" value="<bean:message key="executeadds.title.btnSubmit" />" name="B1" class="submit" onclick="return OnSubmit('<%=formindex %>','<%=stubFlag %>','<%=years%>','<%=dateTime %>',<%=sizeStr%>,'<%=executeFlag%>',this);"> 
         <%
  		if("1".equals(commondFlag)){  
			%>
    	<input type="button" value="<bean:message key="executeadd.title.btnAutoLog" />" name="B1" onclick="submitform('<%=formindex %>','<%=stubFlag %>','<%=tawwpmonthplanvo.getId()%>','<%=contentIdStr%>',<%=sizeStr%>,'<%=executeFlag%>',this);" class="button">
		<%
  		}
		%>
        <%--<input type="button" value='复制并保存'name="B3" class="button" onclick="javascript:onCopy('<%=tawwpmonthplanvo.getId() %>','<%=tawwpmonthplanvo.getUserByStub() %>');">
        --%><input type="button" value="<bean:message key="executeadds.title.btnBack" />"  name="B2" class="button" onclick="javascript:GoBack();">
        <!-- 
        <input type="text" name="day" size="20" onclick="popUpCalendar(this, this,null,null,null,false,-1);" readonly="true"  class="text">
        <input type="button" value='复制以前日期' name="B4" class="button" onclick="javascript:onCopyold('<%=tawwpmonthplanvo.getId() %>','<%=tawwpmonthplanvo.getUserByStub() %>','<%=date%>','<%=flag %>',this.form.day.value)">
         -->
      </form>
      <%     
           formindex = formindex+1;
           }      
        }
        int stubformindex = 0;
        int sizeStr1 = 0;
        Set stubkey = stubMonthplanvoHash.keySet();
        Iterator stubi = stubkey.iterator();
        while(stubi.hasNext()){
            TawwpMonthPlanVO tawwpmonthplanvo = (TawwpMonthPlanVO)stubi.next();
            List executeContentVOList = (List)stubMonthplanvoHash.get(tawwpmonthplanvo);
            String executeType = tawwpmonthplanvo.getExecuteType();
            String contentIdStr = "";
            for(int m = 0; m<executeContentVOList.size(); m++){
              contentIdStr += (((TawwpExecuteContentVO)executeContentVOList.get(m)).getId() + ",");
            }
            sizeStr1 = executeContentVOList.size();
            HashMap contentHash = (HashMap)stubMonthplancontentHash.get(tawwpmonthplanvo);
            Iterator hashKeys = null;
            String stubFlag = "stub";
            String tempFormId = "";
            String tempVar = "";
            String formDataId = "";
            TawwpExecuteContentVO tawwpExecuteContentVO = null;
            if(contentHash.size() != 0){
        %>
        <form name="executeadds_<%=stubformindex %>_<%=stubFlag %>" method="POST" action="../tawwpexecute/executesavesnew.do?contentidstr=<%=contentIdStr.substring(0,contentIdStr.length()-1)%>&flag=<%=flag%>&executetype=<%=executeType%>&formindex=<%=stubformindex %>&stubFlag=<%=stubFlag %>">

        <table border="0"  cellspacing="1" cellpadding="1" class="listTable" align=center width="90%">
          <div class="skin-blue"><div class="box">
        	<span class="rc-tp"><span></span></span>
        	<div class="bd" >
        	计划：<em><%=tawwpmonthplanvo.getName() %></em>网元:<em><%=tawwpmonthplanvo.getNetName() %></em>
        	<span id="status_<%=stubformindex %>_<%=stubFlag %>" class="success"></span>
        	</div>    
        	<span class="rc-bt"><span></span></span>
			</div></div>
          <thead>
          <tr>
            <td width="15%">
                作业项目
            </td>
            <!--      
            <td width="15%">
                业务类型
            </td>
            <td width="15%">
                执行单位级别
            </td>
            <td width="15%">
                适用说明
            </td>
             -->
            <td width="5%" >
                周期
            </td>
            <td width="10%">
                填写时间
            </td>
            <td width="20%">
                执行内容
            </td>
            <td width="6%">
                备注
            </td>
            <td width="6%">
               是否正常
            </td>
            <td width="10%">
                附件
            </td>
            <td width="10%">
                执行表格
            </td>
            <td width="6%">
                同组
            </td>
            <td width="15%"> 
                关联工单
            </td>
            <td width="10%">
                执行帮助
            </td>
            <td width="5%">
                指导文件
            </td>
             <%String years=(String) request.getAttribute("years");
 				String dateTime=StaticMethod.getCurrentDateTime().substring(0,10);
 				if(dateTime.compareTo(years)>0){           
            %>
            <td width="5%">
              补填原因
            </td>
            <%} %>
          </tr>
          </thead>
          <tbody>
          <%
          	  String commondFlag = "";
          	  String writeDate = "";
              hashKeys = contentHash.keySet().iterator();
              while (hashKeys.hasNext()) {
                tempFormId = (String)(hashKeys.next());
                tempVar = (String)contentHash.get(tempFormId);
                String[] strArray = tempVar.split(",");
                
                tawwpExecuteContentVO = (TawwpExecuteContentVO)executeContentVOList.get(Integer.parseInt(strArray[0]));
                if(!"".equals(tawwpExecuteContentVO.getCommand()) && tawwpExecuteContentVO.getCommand() !=null) {
                	commondFlag = "1";
                }
               
                String reason=tawwpExecuteContentVO.getReason();
                if(reason==null||"".equals(reason)){
                	tawwpExecuteContentVO.setReason("");
                }
                
                if(tawwpExecuteContentVO.getWriteDate()!=null&&!tawwpExecuteContentVO.getWriteDate().equals("")){
                writeDate = tawwpExecuteContentVO.getWriteDate();
               }else{
         	    writeDate = StaticMethod.getCurrentDateTime();
               }
          %>
          <tr class="tr_show">
            <td width="15%" class="clsthd2">
              <%=tawwpExecuteContentVO.getName()%>
              <input type="hidden" name="maxsize<%=stubformindex %>_<%=stubFlag %>" value="<%=strArray.length%>">
              <input type="hidden" name="fileFlag<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getFileFlag()%>">
          
              <input type="hidden" name="executecontentuserid<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getExecuteContentUserId()%>">
              <input type="hidden" name="executecontentid<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getId()%>">
              <%
                if("1".equals(tawwpExecuteContentVO.getStubFlag())){
              %>
              [<font color="red"><bean:message key="stubuseradd.title.formAgent" /></font>]
              <input type="hidden" name="userid<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getUserByStub()%>">
              <input type="hidden" name="stubuser<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=currUser%>">
              <%
                } else {
              %>
              <input type="hidden" name="userid<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=currUser%>">
              <input type="hidden" name="stubuser<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" value="">
              <%
                }
              %>
            </td>
            <!-- 
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getBotype()) %>
	        </td>
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getExecutedeptlevel()) %>
	        </td>
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getAppdesc()) %>
	        </td>
	         -->
	        <%if(!"0".equals(tawwpExecuteContentVO.getExecuteFlag())){%>
          	<script type="text/javascript">eoms.$('status_<%=stubformindex %>_<%=stubFlag %>').update("本计划已执行");</script>
          	<%}%>
            <td width="5%">
              <%=tawwpExecuteContentVO.getCycleName()%>
            </td>
            <td width="10%">
              <input type="hidden" name="writeDate<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" size="20"    class="text" value="<%=StaticMethod.getCurrentDateTime()%>"><%=writeDate %>
            </td>
            <td >
              <textarea name="content<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>"  rows=4 cols="30" value=""><%=tawwpExecuteContentVO.getContent()%></textarea>
            </td>
            <td width="6%" align=center>
              <textarea name="remark<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>"  rows=4 cols="30" value=""><%=tawwpExecuteContentVO.getRemark()%></textarea>
            </td>
            <td width="6%" align=center>
           		<%normalFlag = "normalFlag"+strArray[0]+"_"+stubformindex+"_"+stubFlag; %>
       			<eoms:dict key="dict-workplan" dictId="normalFlag" beanId="selectXML" defaultId="<%=tawwpExecuteContentVO.getNormalFlag()%>"  isQuery="false"  selectId="<%=normalFlag%>"  />
      			 </td>
            <td width="10%" class="clsthd2" align="left">
              <div id="file<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" style="">
              <%
                if(!"".equals(tawwpExecuteContentVO.getFileName())){
                  String[] tempStr = tawwpExecuteContentVO.getFileName().split(",");
                  for(int j=0; j<tempStr.length; j++){
                    String[] tempArray = tempStr[j].split("@");
              %>
              <a href="<%=tempArray[1]%>"><%=tempArray[0]%></a><br>
              <%
                  }
                }
              %>
              </div>
              <% if(tawwpExecuteContentVO.getFileFlag()!=null&&tawwpExecuteContentVO.getFileFlag().equals("1")&&tawwpExecuteContentVO.getFileCount()<1){%>
              	<font color="red">***</font>
              <%}%>
              <span id="showLink_<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>"></span>
              <input type="hidden" name="filename<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" dataType="null" value="<%=tawwpExecuteContentVO.getFileName()%>">
              <input type="button" class="button" name="count<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" onclick="javascript:onFile(filename<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>,count<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>,executecontentid<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>,executecontentuserid<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>,userid<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>,stubuser<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>,'showLink_<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>','file<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>','executeadds_<%=formindex %>_<%=stubFlag %>');" value="<%=tawwpExecuteContentVO.getFileCount()%>"><bean:message key="executeadds.title.labFile" />
            </td>
      
            <td width="10%" class="clsthd2" rowspan="<%=strArray.length%>">
              <%
                String formDataId_=StaticMethod.null2String(tawwpExecuteContentVO.getFormDataId(),"");
                if(formDataId!=null||"".equals(tawwpExecuteContentVO.getFormDataId())){     
              %>
              <a href="javascript:onAddons('../tawwpaddons/addonsread.do?action=new&window=new&formid=<%=tawwpExecuteContentVO.getFormId()%>&monthid=<%=tawwpmonthplanvo.getId() %>&date=<%=date%>&executeId=<%=tawwpExecuteContentVO.getId() %>&reaction=/tawwpexecute/redirection.jsp');"><%=tawwpExecuteContentVO.getFormName()%></a>
              <input type="hidden" name="formdataid<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=formDataId_%>">
              <%} else { %>
              <a href="javascript:onAddons('../tawwpaddons/addonsread.do?action=edit&window=edit&executeId=<%=tawwpExecuteContentVO.getId() %>&reaction=/tawwpexecute/redirection.jsp');"><%=tawwpExecuteContentVO.getFormName()%></a>
              <input type="hidden" name="formdataid<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getFormDataId()%>">
              <%
                }
              %>
            </td>
            <td width="6%" align=center>
              <%
                if("1".equals(executeType)){
              %>
              <a href="../tawwpexecute/sameview.do?executecontentid=<%=tawwpExecuteContentVO.getId()%>" >
              <img src="${app }/images/icons/list.png"> 
              </a>
              <%
                }
              %>
            </td>
            <td width="6%" align=center >
                
              
		          <select name="workplanType<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" >
			        <option value="1">故障处理工单</option>
                    <option value="2">网优工单</option>
                  </select>
                  &nbsp; 
                  <input type="button" value="添加" class="button"  onclick="javascript:addworkflow('<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>','<%=tawwpExecuteContentVO.getId()%>')" >
                <iframe name="workflowFrame<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" src="../tawwpexecute/workflowList.do?executeId=<%=tawwpExecuteContentVO.getId()%>" frameborder=0></iframe>
                <div id="workflow<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>" > 
                </div>  
            </td>
            <td width="10%">
              <%=tawwpExecuteContentVO.getExtendremark()%>
            </td>
            <td width="10%">
              <%
                List list1 = null;
                if(!tawwpExecuteContentVO.getAccessories().equals("")){
                  list1 =mgr.getAllFileById(tawwpExecuteContentVO.getAccessories());
                }
                if(list1 != null) {
                  for(int num=0; num<list1.size(); num++){
                  TawCommonsAccessories accessories=(TawCommonsAccessories)list1.get(num);

               %>
               <script language="javascript">
               document.write("<a href=\"${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=<%=accessories.getId()%>&filelist=<%=tawwpExecuteContentVO.getAccessories()%>\">");
               document.write("<img src=\"${app}/images/page.gif\" border=\"0\" align=\"absmiddle\"><%=StaticMethod.nullObject2String(accessories.getAccessoriesCnName())%></a>");
               </script>
               <%
                  } 
                }
               %>
            </td>
              <%years=(String) request.getAttribute("years");
 				dateTime=StaticMethod.getCurrentDateTime().substring(0,10);
 				if(dateTime.compareTo(years)>0){           
            %>
            <td width="5%" align=center>
              <textarea name="reason<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>"  rows=4 cols="30" value=""><%=tawwpExecuteContentVO.getReason()%></textarea>
            </td>
            <%} %>
          </tr>
        <%
          for(int k=1; k<(strArray.length); k++){
            tawwpExecuteContentVO = (TawwpExecuteContentVO)executeContentVOList.get(Integer.parseInt(strArray[k]));
            String reason2=tawwpExecuteContentVO.getReason();
            if(reason2==null||"".equals(reason2)){
            	tawwpExecuteContentVO.setReason("");
            }
        %>
          <tr class="tr_show">
            <td width="15%" class="clsthd2">
              <%=tawwpExecuteContentVO.getName()%>
              
              <input type="hidden" name="fileFlag<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getFileFlag()%>">
          
              <input type="hidden" name="executecontentuserid<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getExecuteContentUserId()%>">
              <input type="hidden" name="executecontentid<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getId()%>">
              <%
                if("1".equals(tawwpExecuteContentVO.getStubFlag())){
              %>
              [<font color="red"><bean:message key="stubuseradd.title.formAgent" /></font>]
              <input type="hidden" name="userid<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=tawwpExecuteContentVO.getUserByStub()%>">
              <input type="hidden" name="stubuser<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=currUser%>">
              <%} else {%>
              <input type="hidden" name="userid<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=currUser%>">
              <input type="hidden" name="stubuser<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" value="">
              <%
                }
              %>
            </td>
            <!-- 
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getBotype()) %>
	        </td>
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getExecutedeptlevel()) %>
	        </td>
	        <td width="15%">
	          <%=StaticMethod.null2String(tawwpExecuteContentVO.getAppdesc()) %>
	        </td>
	         -->
            <td width="5%">
              <%=tawwpExecuteContentVO.getCycleName()%>
            </td>
            <td width="10%">
              <input type="hidden" name="writeDate<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" size="20" class="text" value="<%=StaticMethod.getCurrentDateTime()%>"><%=StaticMethod.getCurrentDateTime() %>
            </td>
            <td width="6%">
              <textarea name="content<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>"  rows=4 cols="30" value=""><%=tawwpExecuteContentVO.getContent()%></textarea>
            </td>
            <td width="6%" align=center>
              <textarea name="remark<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>"  rows=4 cols="30" value=""><%=tawwpExecuteContentVO.getRemark() %></textarea>
            </td>
            <td width="6%" align=center>
            	<%normalFlag = "normalFlag"+strArray[k]+"_"+stubformindex+"_"+stubFlag; %>
       			<eoms:dict key="dict-workplan" dictId="normalFlag" beanId="selectXML" defaultId="<%=tawwpExecuteContentVO.getNormalFlag()%>"  isQuery="false"  selectId="<%=normalFlag%>"  />
      		</td>
            <td width="10%" class="clsthd2" align="left">
              <div id="file<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" style="">
              <%
                if(!"".equals(tawwpExecuteContentVO.getFileName())){
                  String[] tempStr = tawwpExecuteContentVO.getFileName().split(",");
                  for(int j=0; j<tempStr.length; j++){
                    String[] tempArray = tempStr[j].split("@");
              %>
                <a href="<%=tempArray[1]%>"><%=tempArray[0]%></a><br>
              <%
                  }
                }
              %>
              </div>
              <% if(tawwpExecuteContentVO.getFileFlag()!=null&&tawwpExecuteContentVO.getFileFlag().equals("1")&&tawwpExecuteContentVO.getFileCount()<1){%>
              	<font color="red">***</font>
              <%}%>
              <span id="showLink_<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>"></span>
              <input type="hidden" name="filename<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" dataType="null" value="<%=tawwpExecuteContentVO.getFileName()%>">
              <input type="button" class= "button" name="count<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" onclick="javascript:onFile(filename<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>,count<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>,executecontentid<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>,executecontentuserid<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>,userid<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>,stubuser<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>,'showLink_<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>','file<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>','executeadds_<%=formindex %>_<%=stubFlag %>');" value="<%=tawwpExecuteContentVO.getFileCount()%>"><bean:message key="executeadds.title.labFile" />
            </td>

            <td width="6%" align=center>
            <%
              if("1".equals(executeType)){
            %>
              <a href="../tawwpexecute/sameview.do?executecontentid=<%=tawwpExecuteContentVO.getId()%>" >
              <img src="${app }/images/icons/list.png"> 
              </a>
            <%
              }
            %>
              <input type="hidden" name="formdataid<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" value="<%=formDataId%>">
            </td>
            <td width="6%" align=center >
                
		          <select name="workplanType<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" >
			        <option value="1">故障处理工单</option>
                    <option value="2">网优工单</option>
                  </select>
                  &nbsp; 
                  <input type="button" value="添加" class="button"  onclick="javascript:addworkflow('<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>','<%=tawwpExecuteContentVO.getId()%>')" >
                <iframe name="workflowFrame<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" src="../tawwpexecute/workflowList.do?executeId=<%=tawwpExecuteContentVO.getId()%>" frameborder=0></iframe>
                  
                <div id="workflow<%=strArray[k]%>_<%=stubformindex %>_<%=stubFlag %>" > 
                </div>  
            </td>
            <td width="10%">
              <%=tawwpExecuteContentVO.getExtendremark()%>
            </td>
            <td width="5%">
              <%
                List list2 = null;
                if(!tawwpExecuteContentVO.getAccessories().equals("")){
                  list2 =mgr.getAllFileById(tawwpExecuteContentVO.getAccessories());
                }
                if(list2 != null) {
                  for(int num=0; num<list2.size(); num++){
                  TawCommonsAccessories accessories=(TawCommonsAccessories)list2.get(num);

               %>
               <script language="javascript">
               document.write("<a href=\"${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=<%=accessories.getId()%>&filelist=<%=tawwpExecuteContentVO.getAccessories()%>\">");
               document.write("<img src=\"${app}/images/page.gif\" border=\"0\" align=\"absmiddle\"><%=StaticMethod.nullObject2String(accessories.getAccessoriesCnName())%></a>");
               </script>
               <%
                  } 
                }
               %>
            </td>
             <%years=(String) request.getAttribute("years");
 				dateTime=StaticMethod.getCurrentDateTime().substring(0,10);
 				if(dateTime.compareTo(years)>0){           
            %>
            <td width="5%" align=center>
              <textarea name="reason<%=strArray[0]%>_<%=stubformindex %>_<%=stubFlag %>"  rows=4 cols="30" value=""><%=tawwpExecuteContentVO.getReason()%></textarea>
            </td>
            <%} %>
          </tr>         
      <%
            }
          } 
      %>
          </tbody>
        </table> 
          <input type="hidden" value="<%=tawwpmonthplanvo.getId() %>" name="monthplanid">
          <input type="hidden" value="<%=flag %>" name="flag">
          <input type="hidden" value="<%=tawwpmonthplanvo.getUserByStub() %>" name="userbystub">
          <input type="hidden" value="<%=date%>" name="date">
          <input type="hidden" value="<%=years%>" name="years">
          <input type="hidden" value="<%=dateTime%>" name="dateTime">
		  <input type="submit" value="<bean:message key="executeadds.title.btnSubmit" />" name="B1" class="submit" onclick="return OnSubmit('<%=stubformindex %>','<%=stubFlag %>','<%=years%>','<%=dateTime%>','<%=sizeStr1%>',this);"> 
           <%
  		    if("1".equals(commondFlag)){  
			%>
    		<input type="button" value="<bean:message key="executeadd.title.btnAutoLog" />" name="B1" onclick="submitform('<%=stubformindex %>','<%=stubFlag %>','<%=tawwpmonthplanvo.getId()%>','<%=contentIdStr%>','<%=sizeStr1%>',this);" class="button">
			<%
  			}
			%>
          
          <%--<input type="button" value='复制并保存'name="B3" class="button" onclick="javascript:onCopy('<%=tawwpmonthplanvo.getId() %>','<%=tawwpmonthplanvo.getUserByStub() %>','<%=date%>','<%=flag %>');">
          --%><input type="button" value="<bean:message key="executeadds.title.btnBack" />"  name="B2" class="button" onclick="javascript:GoBack();">
        <br><br><br><br>
        </form>
      <%
          stubformindex = stubformindex+1; 
        }   
      }    
      %>   
            
<%@ include file="/common/footer_eoms.jsp"%>
