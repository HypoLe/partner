<%@ page contentType="text/html; charset=GB2312"%>
<%@ page import="com.boco.supplychain.util.StaticVariable"%>
<%@ page import="com.boco.supplychain.contract.webapp.form.SubContractForm,com.boco.supplychain.contract.domain.SubContractType"%>
<%@ page import="java.util.List,java.util.ArrayList,java.util.Map,java.util.Iterator,java.util.Set,java.util.Enumeration" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/supplychain.tld" prefix="supplychain"%>
<supplychain:dicQuantityUnit  unit_type="<%=StaticVariable.DICT_UNIT_PAY_CYCLE%>"></supplychain:dicQuantityUnit>

		<%
		String webapp = request.getContextPath();
		List subContractTypeList=(List)request.getAttribute("subContractTypeList");
		int subContractTypeCount=subContractTypeList.size();
		%>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>子合同创建</title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/table_style.css"
			type="text/css">
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/calendar2.js"></script>
		<script type="text/javascript" src="<%=webapp%>/js/form/checkform.js"></script>
		<script type="text/javascript" src="../js/dojo/dojo.js"></script>
		<script type="text/javascript" src="../js/dojo/src/io.js"></script>
		
		<script type="text/javascript">
			djConfig={
				isDebug:false,
				bindEncoding:"utf-8"
			};
			
			dojo.require("dojo.io.BrowserIO");
		</script>
		
		<SCRIPT LANGUAGE=javascript>
<!--
function add_RoomTrue()
{
  if (document.forms[0].unDistributeSubContractIds.selectedIndex>-1)
  {
      selected_spr_text=document.forms[0].unDistributeSubContractIds.options[document.forms[0].unDistributeSubContractIds.selectedIndex].text;
      selected_spr_value=document.forms[0].unDistributeSubContractIds.options[document.forms[0].unDistributeSubContractIds.selectedIndex].value;
      var sel_sprlen=document.forms[0].distributeSubContractIds.options.length-1;

      var exist_flag=1;
      var j=0;
      for(j=0;j<=sel_sprlen;j++)
      {
        if(document.forms[0].distributeSubContractIds.options[j].value==selected_spr_value)
        {
          exist_flag=0;
          break;
        }
      }

      if(exist_flag){
        var option=new Option(selected_spr_text,selected_spr_value);
        document.forms[0].distributeSubContractIds.options[document.forms[0].distributeSubContractIds.options.length]=option;
        document.forms[0].unDistributeSubContractIds.options[document.forms[0].unDistributeSubContractIds.selectedIndex]=null;
      }
      else alert("<bean:message key="label.alerto"/>");
  }
}
function add_all_RoomTrue()
{
  var sel_sprlen=document.forms[0].unDistributeSubContractIds.options.length-1;
  //document.forms[0].distributeSubContractIds.options.length=0;
  for(j=0;j<=sel_sprlen;j++)
  {
  	var option=new Option(document.forms[0].unDistributeSubContractIds.options[j].text);
    option.value=document.forms[0].unDistributeSubContractIds.options[j].value;
    document.forms[0].distributeSubContractIds.options[document.forms[0].distributeSubContractIds.options.length] = option;
  }
    document.forms[0].unDistributeSubContractIds.options.length=0;
}
function del_RoomTrue()
{
  var sel_sprindex=document.forms[0].distributeSubContractIds.selectedIndex;
  if(sel_sprindex!=-1){
  //往左移
    var option=new Option(document.forms[0].distributeSubContractIds.options[sel_sprindex].text);
    option.value= document.forms[0].distributeSubContractIds.options[sel_sprindex].value;
    document.forms[0].unDistributeSubContractIds.options[document.forms[0].unDistributeSubContractIds.options.length]=option;
    //删除选中
    document.forms[0].distributeSubContractIds.options[sel_sprindex]=null;
  }
}
function del_all_RoomTrue()
{
  var sel_sprlen=document.forms[0].distributeSubContractIds.options.length-1;
  var j=0;
  for(j=sel_sprlen;j>=0;j--)
  {
  //取元素
    var option=new Option(document.forms[0].distributeSubContractIds.options[j].text);
    option.value= document.forms[0].distributeSubContractIds.options[j].value;
    document.forms[0].unDistributeSubContractIds.options[document.forms[0].unDistributeSubContractIds.options.length]=option;
    //制空
 	 document.forms[0].distributeSubContractIds.options[j]=null;
  }
}



//var index=1; //子合同计数器(有"伪"删除Div操作(删除Div用隐藏Div表示),)
var index=1;//表示最小的子合同空闲容器ID
var subContract_Div_idleId=new Array();  //物业合同容器(main1,main2,main3,main4)处于空闲状态,表示没有没显示出来,还没装载子合同.初始为1,2,3,4
										//每创建一个物业合同取一个容器,取最小的一个,每删除一个则往这里添加一个标识
	//取空闲容器的最小标识号
	function getMin_DivIdleId(){
		if(subContract_Div_idleId.length==1){
			return subContract_Div_idleId[0];
			subContract_Div_idleId.splice(0,1);  //删除min对应的数组项. splice(start,count,xxx,xxx); 这个js方法很强大,可以删除数组的对象,替换数组对象,等.
		}else{
			var	min=subContract_Div_idleId[0];
			var min_index=0;
			for(var i=1;i<subContract_Div_idleId.length;i++){
				if(subContract_Div_idleId[i]<min){
					min=subContract_Div_idleId[i];
					min_index=i;
				}
			}
			subContract_Div_idleId.splice(min_index,1);  //删除min对应的数组项.
			return min;
		}
	}
	function initData(){
		var maxSubContractDivCount=<%=subContractTypeCount%>;
		for(var i=0;i<maxSubContractDivCount;i++){
			subContract_Div_idleId.push(i);
		}
	}
	
	/**层的移动(创建子合同移动层)*/
	function move(arr){
		var mainDiv=document.getElementById("main"+index);
		for(var i=0;i<arr.length;i++){
			var oneTypeDiv=document.getElementById("field"+arr[i]+"_Div");
			oneTypeDiv.style.display="block";
			mainDiv.appendChild(oneTypeDiv);
			oneTypeDiv.remove;
		}
		mainDiv.style.display="block";
		//index++;
	}
	//是否已经创建了子合同
	var hasSubContract='<%=request.getAttribute("hasSubContract")%>';
	/**创建子合同*/
	/**
	*@param isUpdatePage 说明是不是更新时的页面.
	* 如果是更新时的页面在删除物业合同时要判断物业合同是否允许删除,如果允许删除则dojo请求删除物业合同.
	* @param uniteValue_DB 数据库存储物业合同的uniteValue
	*/
	function createSubContract(isUpdatePage,uniteValue_DB){
		var length=document.forms[0].distributeSubContractIds.options.length;
		if(length>0){
			index=getMin_DivIdleId();   //定位子合同容器位置(下标).
			var contractItems=new Array();
			var uniteValue="";
			
			//删除合同时链接构造
			var mainDiv_delete_anchor=document.getElementById("main" + index + "_delete_anchor");
			
			if(typeof(isUpdatePage)=="boolean" && isUpdatePage==true){
				uniteValue=uniteValue_DB;  //uniteValue要保持数据库的uniteValue(因为要通过contractId和uniteValue唯一确定一个物业合同.)
				var contractItems_DB=uniteValue_DB.split("_");
				for(var i=0;i<contractItems_DB.length;i++){
					contractItems.push(contractItems_DB[i]);
				}
				 //如果本页面是更新的页面状态,则要判断物业合同是否可以删除的业务.
				mainDiv_delete_anchor.href="javascript:deleteMainDiv(" + index + ",'" + uniteValue + "',true);"
			}else{
				for(var i=0;i<length;i++){
					contractItems.push(document.forms[0].distributeSubContractIds[i].value);
					uniteValue=uniteValue+document.forms[0].distributeSubContractIds[i].value;
					if(i<length-1)
						uniteValue+="_";
				}
				mainDiv_delete_anchor.href="javascript:deleteMainDiv(" + index + ",'" + uniteValue + "');"
			}
			
			//取物业合同第一个合同项
				var firstItem=document.forms[0].distributeSubContractIds.options[0].value; 
				var firstItem_payee=document.getElementById("field"+firstItem+"_"+"payee");
				var firstItem_gatheringAccount=document.getElementById("field"+firstItem+"_"+"gatheringAccount");
				var firstItem_contactMethod=document.getElementById("field"+firstItem+"_"+"contactMethod");
				var firstItem_uniteValue=document.getElementById("field"+firstItem+"_"+"uniteValue");
				//设置uniteValue
				firstItem_uniteValue.value=uniteValue;

			if(length>1){//两个以上合同项组成一个子合同(需要同步收款人等信息)
				var otherItems_payee=new Array();
				var otherItems_gatheringAccount=new Array();
				var otherItems_contactMethod=new Array();
				for(var i=1;i<length;i++){  //从第二个开始
					//取该子合同的其他合同项
					var otherItem=document.forms[0].distributeSubContractIds.options[i].value;
					var other_payee=document.getElementById("field"+otherItem+"_"+"payee");
					var other_gatheringAccount=document.getElementById("field"+otherItem+"_"+"gatheringAccount");
					var other_contactMethod=document.getElementById("field"+otherItem+"_"+"contactMethod");
					var other_uniteValue=document.getElementById("field"+otherItem+"_"+"uniteValue");
					//设置只读属性
					other_payee.readOnly=true;
					other_gatheringAccount.readOnly=true;
					other_contactMethod.readOnly=true;
					if(hasSubContract=="false"){   //判断是否是刚刚生成子合同时的情况,如果本页面是更新页面则保留原先物业合同的值
						other_payee.value="根据第一个合同项自动生成...";
						other_gatheringAccount.value="根据第一个合同项自动生成...";
						other_contactMethod.value="根据第一个合同项自动生成...";		
					}
					//设置uniteValue
					other_uniteValue.value=uniteValue;
					otherItems_payee.push(other_payee);
					otherItems_gatheringAccount.push(other_gatheringAccount);
					otherItems_contactMethod.push(other_contactMethod);					
				}
				//下面加载一个物业合同中第一个合同项的onchange事件,让第一个合同项和后面的合同项的值保持同步输入.
				firstItem_payee.onchange=function(){
					for(var k=0;k<otherItems_payee.length;k++){
						otherItems_payee[k].value=firstItem_payee.value;
					}
				}
				firstItem_gatheringAccount.onchange=function(){
					for(var k=0;k<otherItems_gatheringAccount.length;k++){
						otherItems_gatheringAccount[k].value=firstItem_gatheringAccount.value;
					}
				}
				firstItem_contactMethod.onchange=function(){
					for(var k=0;k<otherItems_contactMethod.length;k++){
						otherItems_contactMethod[k].value=firstItem_contactMethod.value;
					}
				}
			}
			move(contractItems);
		}else{
			alert("请先选择合同项,然后生成子合同.");
			document.forms[0].unDistributeSubContractIds.focus();
			
		}
		document.forms[0].distributeSubContractIds.options.length=0;
	}
	
	/**
	*删除子合同的函数(这个以后可能要添加Ajax实现,动态的删除数据库对应的子合同下的合同项|合同项付款计划|合同项付款计划项)
	* @param contractItemDivArr 合同项Div ID的数组StaticVariable.CONTRACT_ITEM_ROOM_STR
	*/
	function deleteMainDiv(mainDivId,uniteValue,isUpdatePage){
		var mainDiv=document.getElementById("main"+mainDivId);
		var containerDiv=document.getElementById("containerDiv");
		var contractItemIdArr=uniteValue.split("\_");
		
		//更新页面时删除操作,表示物业合同是存在于数据库中
		var allowDelete=false;
		if(typeof(isUpdatePage)=='boolean' && isUpdatePage==true){
			allowDelete=ajaxDeletePropertyContract(uniteValue);
			if(allowDelete==false){
				return;
			}
		}
		
		//遍历子合同包含的合同项.
		for(var i=0;i<contractItemIdArr.length;i++){
			var itemId=contractItemIdArr[i];
			//取合同项的Div
			var itemDiv=document.getElementById("field"+itemId+"_Div");
			itemDiv.style.display="none";//合同项div隐形
			containerDiv.appendChild(itemDiv);  //暂时追加到containterDiv容器内(copy操作)
			itemDiv.remove;  //去除子合同Div里面的合同项
			
			
			//下面把删除的子合同包含的合同项,还原到未生成合同项的下拉框中.	
			var option=new Option();
			option.value=itemId;
			option.text=document.getElementById('field'+itemId+"_subContractTypeName").innerText;
			
			document.forms[0].unDistributeSubContractIds.options[document.forms[0].unDistributeSubContractIds.options.length]=option;
			
			
			//下面把删除子合同里面的数据清空,并把付款人同步/收款帐号同步的事件去掉.把只读属性去掉,unite_value数据清空
			var item_payee=document.getElementById("field"+itemId+"_"+"payee");
			var item_gatheringAccount=document.getElementById("field"+itemId+"_"+"gatheringAccount");
			var item_contactMethod=document.getElementById("field"+itemId+"_"+"contactMethod");
			var item_uniteValue=document.getElementById("field"+itemId+"_"+"uniteValue");
			//清空字段值和uniteValue
					
			item_uniteValue.value="";
			item_payee.value="";
			item_gatheringAccount.value="";
			item_contactMethod.value="";
			//还原自渎属性
			
			item_payee.readOnly=false;
			item_gatheringAccount.readOnly=false;
			item_contactMethod.readOnly=false;
			//还原同步事件onchange(第一个元素具有onchange事件)
			item_payee.onchange=function(){};
			item_gatheringAccount.onchange=function(){};
			item_contactMethod.onchange=function(){};
		}
		mainDiv.style.display="none";
		subContract_Div_idleId.push(mainDivId);  //子合同空闲容器多了一个.
		
		if(allowDelete==true){
			//真正从数据删除后,执行页面删除操作后提示.
			 alert("物业合同删除成功,请添加新的物业合同!");
		}
		//index--;
	}
	
	/**是否允许删除这个物业合同(判断这个物业合同是否符合删除条件.)  contractId和uniteValue唯一确定一个物业合同.*/
	//注意条件:uniteValue要和物业合同在数据库里的标识一样(不能合同项标识颠倒顺序.) . 3_2_4_1和4_1_3_2这个是不同含义的.
	function ajaxDeletePropertyContract(uniteValue){
	    var contractId = document.forms[0].contractId.value;  //这里删除物业合同的标准暂时现已 工作区合同基本信息中生效的时间为准.
	    var flag=false;
	    if(confirm("物业合同删除后不能恢复，您确认要删除吗？")){
	    	  var request={
				url:"<%=webapp%>/contract/ajaxDeletePropertyContract.do?contractId="+contractId+"&uniteValue="+uniteValue,
				mimetype:"text/plain",
				method:"get",
				transport: "XMLHTTPTransport",
				sync:true,
				preventCache:true,
				encoding:"utf-8",
				load: function(type,data,evt){
					var echo=parseInt(data);
					if(echo == 0){
			            alert("物业合同不能删除,物业合同已经生效!");
			            flag=false;
			        }else if(echo==1){
			           //不再这里显示删除成功的提示,而在页面清除后提示.
			           // alert("物业合同删除成功,请添加新的物业合同!");
			       		flag=true;
			        }else{
			            alert("物业合同删除时发生错误,请联系管理员!");
			        	flag=false;
			        }
				},
				error:function(type,error){
					alert("物业合同删除时发生错误:"+error.message+",请联系管理员!");
			        flag=false;
				}
			};
			dojo.io.bind(request);
	    }else{
			flag=false;
		}
		return flag;
	}
	
	
	//初始化页面
	function init(){
		initData();
		initSubContract();
	}	
	//本页面属于更新页面时初始化工作区所属物业合同的情况.
	function initSubContract(){
		if(hasSubContract=="true"){
			<%=request.getAttribute("distinctUniteValueJsArr")%>
			for(var i=0;i<arr.length;i++){
				var contractItemIds=arr[i].split("_");
				for(var k=0;k<contractItemIds.length;k++){
					var option=new Option('',contractItemIds[k]);
					document.forms[0].distributeSubContractIds.options[document.forms[0].distributeSubContractIds.options.length]=option;
				}
				createSubContract(true,arr[i]);  //这里arr[i]表示数据库存储的物业合同UniteValue
			}
		}
	}
	dojo.addOnLoad(init);
	
	function checkData(){
		//给preparePayMode赋值,checkbox不选中不会提交
		var preparePayMode_chkArr=document.getElementsByName("preparePayMode_chk");
		var preparePayModeArr=document.getElementsByName("preparePayMode");
		for(var j=0;j<preparePayMode_chkArr.length;j++){
			if(preparePayMode_chkArr[j].checked==true){
				preparePayModeArr[j].value=true;
			}else{
				preparePayModeArr[j].value=false;
			}
		}
		var itemArr=document.getElementsByName("subContractTypeId");
		var checkPass=true;
		var haveOneSubContract=false;
		for(var i=0;i<itemArr.length;i++){
			var firstItem=itemArr[i].value;
			var firstItem_payee=document.getElementById("field"+firstItem+"_"+"payee");
			var firstItem_payBank=document.getElementById("field"+firstItem+"_"+"payBank");
			var firstItem_gatheringAccount=document.getElementById("field"+firstItem+"_"+"gatheringAccount");
			var firstItem_contactMethod=document.getElementById("field"+firstItem+"_"+"contactMethod");
			var firstItem_uniteValue=document.getElementById("field"+firstItem+"_"+"uniteValue");
			var firstItem_budgetUnitName=document.getElementById("field"+firstItem+"_"+"budgetUnitName");
			var firstItem_payMoney=document.getElementById("field"+firstItem+"_"+"payMoney");
			var firstItem_payDuration=document.getElementById("field"+firstItem+"_"+"payDuration");
			var firstItem_preparePayMode=document.getElementById("field"+firstItem+"_"+"preparePayMode");
			//var firstItem_smsSubscriberName=document.getElementById("field"+firstItem+"_"+"smsSubscriberName");
			//var firstItem_smsSubscriberPhone=document.getElementById("field"+firstItem+"_"+"smsSubscriberPhone");
			if(firstItem_uniteValue.value!=null && firstItem_uniteValue.value.Trim()!=""){
				if(!haveOneSubContract) haveOneSubContract=true;  //说明具有一个UniteValue不为空.
				//if(checkLength(firstItem_payee,1,15) && checkLength(firstItem_gatheringAccount,1,40) && checkLength(firstItem_smsSubscriberName,1,10) && mobileCheck(firstItem_smsSubscriberPhone) && checkLength(firstItem_contactMethod,1,50) &&
				if(checkLength(firstItem_payee,1,15) && checkLength(firstItem_payDuration,1,5) && checkLength(firstItem_contactMethod,1,50) &&
					checkNotNull(firstItem_budgetUnitName,'所有计费单位不能为空,请确认!') && numCheckAllowNull(firstItem_payMoney,'所有收款金额必须为数值型,请确认!')){
					checkPass=true;
				}else{
					checkPass=false;
					return false;
				}
			}
		}
		if(haveOneSubContract==false){
			alert("至少创建一个物业合同（子合同）,才能保存!");
			checkPass=false;
			return false;
		}
		return checkPass;
	}
	

//-->
</SCRIPT>
	</head>
	<!---------------------------------------页面主体开始--------------------------------------->
	<body>
		<!------------------标识页面位置------------------>
	<p align="left">
		所在位置->服务合同管理->
		<bean:write name="specialtyName" />
		->
		<bean:write name="contractModelName" />
		->物业合同(工作区:<bean:write name="workAroundNames" />)
	</p>
		<html:form method="post"
			action="/contract/propertiesContract_SubContract_SaveUpdate" onsubmit="return checkData();">
		<input name="addOrUpdate" type="hidden" />  <!-- 保存页面还是更新页面 -->
		<!-- 子合同定制页面开始 -->
<table width="52%" height="24"
			border="0" align="center" cellpadding="1" cellspacing="1" class="table_show" id="topTable">
			<tr class="tr_show">
				<td>
					<table  cellpadding="1" cellspacing="0" border="0"
						width="100%" align="center">
						<tr>
							<td>
								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<tr>
										<td bgcolor="#fecc51">
											配置子合同
											<html:hidden property="contractId"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
						  <td >
								<table cellpadding="0" cellspacing="0" border="0" width="50%">
									<tr>
										<td>
											<table bgcolor="#f2f2f2" width="500" cellspacing="0"
												border="0">
												<tr>
													<td width="40%" align="center" rowspan="2">
														<bean:message key="label.left" />
														<select size='4' name="unDistributeSubContractIds"
															style="width:150">
															<logic:iterate id="row" name="unDistinctSubContractTypeList">
																<option
																	value='<bean:write name="row"  property="subContractTypeId"/>'>
																	<bean:write name="row"  property="subContractTypeName"/>
																</option>
															</logic:iterate>
														</select>
													</td>
													<td width="20%" align="center" valign="top" rowspan="2">
														<br>
														<input class="clsbtn2" type="button" name="btnAddItem"
															value="<bean:message key="label.add1"/>"
															onclick=javascript:add_RoomTrue();>
														<p>
															<input class="clsbtn2" type="button" name="btnDelItem"
																value="<bean:message key="label.remove1"/>"
																onclick=javascript:del_RoomTrue();>
														<p>
															<input class="clsbtn2" type="button" name="btnAddAll"
																value="<bean:message key="label.alladd"/>"
																onclick=javascript:add_all_RoomTrue();>
														<p>
															<input class="clsbtn2" type="button" name="btnDelAll"
																value="<bean:message key="label.alldel"/>"
																onclick=javascript:del_all_RoomTrue();>
													</td>
													<td width="40%" align="center">
														<bean:message key="label.rightb" />
														<br>
														<select size="4" name="distributeSubContractIds"
															style="width:150">
														
														</select>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table bgcolor="#f2f2f2" height="30" cellpadding="0"
												cellspacing="0" border="0" width="100%">
												<tr align="center" valign="middle">
													<td> 
													<p align="right">
													<html:button styleClass="clsbtn" property="createSubButton" onclick="createSubContract()">生成(子)合同</html:button>
 														</p>
													</td>

												</tr>
											</table>
										</td>
									</tr>
								</table>
						  </td>
						</tr>
					</table>
				</td>
			</tr>
</table>
		<br>
		<br>
		<!---------------------------------------合同项基本信息录入页面-------------------------------------->
		
		
 <div id="mainDivContainer" style="display:block;position:relative;width: 100%;height:500px;" >
 	 <%
 	 	for(int i=0;i<subContractTypeCount;i++){
 	  %>
	 	 <div id="main<%=i%>" style="display:none;position:relative;width: 100%;">
	 	 	<div>	
	 	 		<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
					<b>物业合同【<%=i+1%>】
					</b>
					<span id="main<%=i%>_delete_span"><a id="main<%=i%>_delete_anchor" href="javascript:deleteMainDiv(<%=i%>);">[删除]</a></span>
				</P>
			</div>
	 	 </div>
	 <%} 
	 %>
	 			<table>
				<tr height="10%" class="tr_show">
					<td colspan="4" align="right">
						<html:submit styleClass="clsbtn2" alt="保存合同">
							<bean:message key="label.save" />
						</html:submit>
						<html:reset styleClass="clsbtn2">
							<bean:message key="label.reset" />
						</html:reset>
					</td>
				</tr>
				</table>
 </div>
<div id="containerDiv" style="display:block;position:relative;width: 100%;" >
<%int index=0; %>
	<logic:iterate id="row" name="subContractTypeList">
		<div id='field<bean:write name="row" property="subContractTypeId"/>_Div' style="display:none;position:relative;width: 100%">
			<table  class="table_show" width="100%" cellspacing="1"	cellpadding="1" border="0" align="center">
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth" colspan="4"
						align="center">
						<span id='field<bean:write name="row" property="subContractTypeId"/>_subContractTypeName'><bean:write name="row" property="subContractTypeName"/></span>
						<input type="hidden" id='field<bean:write name="row" property="subContractTypeId"/>_uniteValue' name="uniteValue"/>
						<input type="hidden" id='field<bean:write name="row" property="subContractTypeId"/>_subContractTypeId' name="subContractTypeId" value='<bean:write name="row" property="subContractTypeId"/>'/>
						&nbsp;
						&nbsp;
						&nbsp;
						&nbsp;
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						收款人
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_payee' name="payee" class="clstext" maxlength="200" title="收款人"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						联系方式
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_contactMethod' name="contactMethod" class="clstext" maxlength="200" title="联系方式"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						收款银行
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_payBank' name="payBank" class="clstext" maxlength="200" title="收款银行"/>						
					</td>
					<td width="15%" height="25" class="clsfth">
						收款帐号
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_gatheringAccount' name="gatheringAccount" class="clstext" maxlength="200" title="收款帐号"/>						
					</td>
					
					
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						付款金额
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_payMoney' name="payMoney" class="clstext" maxlength="20" title="收款金额"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						计费单位
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_budgetUnitName' name="budgetUnitName" class="clstext" maxlength="20" title="计费单位"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
						<%--
						<bean:write name="subContractForm" property="water_budgetUnitName"/>
						<html:hidden property="water_budgetUnitName"/>
						<html:hidden property="water_budgetUnitId"/>
					--%></td>
					
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						付款周期
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_payDuration' name="payDuration" class="clstext" maxlength="20" title="周期数量"/>
						<select style="width: 1cm" name="unitId" id='field<bean:write name="row" property="subContractTypeId"/>_unitId'>
							<logic:iterate id="cycRow" name="dicQuantityList_Cycle">
								<option value='<bean:write name="cycRow" property="value"/>'><bean:write name='cycRow' property="label"/></option>
							</logic:iterate>
						</select>
					</td>
					<td width="15%" height="25" class="clsfth" >
						是否预付款
					</td>
					<td width="35%" height="25" >
						<%
						//这段逻辑的判断是判断checkbox是否checked，本来不需要这些判断，在下面的js赋值中可以做到.但是IE中checkbox嵌套在div中有可能会造成虽然置完checked属性但是checkbox却不会选中.
							SubContractForm subContractForm=(SubContractForm)request.getAttribute("subContractForm");
							String hasSubContract=(String)request.getAttribute("hasSubContract");
							SubContractType subContractType=(SubContractType)subContractTypeList.get(index++);
							String checked="";
						if(hasSubContract.equals("true")){	
							for(int i=0;i<subContractForm.getSubContractTypeId().length;i++){
								if(subContractForm.getSubContractTypeId()[i].equals(subContractType.getSubContractTypeId()) 
									&& subContractType.isIsPayBeforeUse().booleanValue()
								){
									checked="checked='true'";
									break;
								}
							}
						}
						%>
						<input type="checkbox" id='field<bean:write name="row" property="subContractTypeId"/>_preparePayMode_chk' name="preparePayMode_chk" <%=checked%> />

						<input type="hidden" name="preparePayMode" id='field<bean:write name="row" property="subContractTypeId"/>_preparePayMode' />
					</td>
				</tr>
				<%--<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						短信提醒人名字
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_smsSubscriberName' name="smsSubscriberName" class="clstext" maxlength="20" title="短信提醒人名字"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						短信提醒人手机
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_smsSubscriberPhone' name="smsSubscriberPhone" class="clstext" maxlength="20" title="短信提醒人手机"/>
						</td>
				</tr>
				--%><tr class="tr_show">
					<td width="15%" height="25" class="clsfth" >
						描述
					</td>
					<td width="35%" height="25" colspan="3">
						<textarea rows="3" cols="80" name="remark" id='field<bean:write name="row" property="subContractTypeId"/>_remark'></textarea>
					</td>
				</tr>
			</table>
					
		</div>

	</logic:iterate>
</div>
<br>
	</html:form>

			<script type="text/javascript">
				var oneItemId;
				var checkBoxIdArr=new Array();

	<%System.out.println("sdfsdf");
		SubContractForm subContractForm=(SubContractForm)request.getAttribute("subContractForm");
			String hasSubContract=(String)request.getAttribute("hasSubContract");
		if(hasSubContract.equals("true")){	System.out.println(subContractForm.getSubContractTypeId());
			for(int i=0;i<subContractForm.getSubContractTypeId().length;i++){
				int tempId=subContractForm.getSubContractTypeId()[i].intValue();System.out.println(tempId);
				try {
		%>
		
			     oneItemId=<%=tempId%>;
				 document.getElementById("field"+oneItemId+"_"+"payee").value='<%=subContractForm.getPayee()[i]%>';
				 document.getElementById("field"+oneItemId+"_"+"payBank").value='<%=subContractForm.getPayBank()[i]%>';
				 document.getElementById("field"+oneItemId+"_"+"gatheringAccount").value='<%=subContractForm.getGatheringAccount()[i]%>';
				 document.getElementById("field"+oneItemId+"_"+"subContractTypeId").value='<%=subContractForm.getSubContractTypeId()[i]%>';
				 document.getElementById("field"+oneItemId+"_"+"subContractTypeName").innerText='<%=subContractForm.getSubContractTypeName()[i]%>';
				document.getElementById("field"+oneItemId+"_"+"contactMethod").value='<%=subContractForm.getContactMethod()[i]%>';
				 document.getElementById("field"+oneItemId+"_"+"uniteValue").value='<%=subContractForm.getUniteValue()[i]%>';
				 document.getElementById("field"+oneItemId+"_"+"budgetUnitName").value='<%=subContractForm.getBudgetUnitName()[i]%>';
				document.getElementById("field"+oneItemId+"_"+"payMoney").value='<%=subContractForm.getPayMoney()[i]%>';
				document.getElementById("field"+oneItemId+"_"+"payDuration").value='<%=subContractForm.getPayDuration()[i]%>';
				 document.getElementById("field"+oneItemId+"_"+"unitId").value='<%=subContractForm.getUnitId()[i]%>';
				 document.getElementById("field"+oneItemId+"_"+"preparePayMode").value='<%=subContractForm.getPreparePayMode()[i]%>';
				//document.getElementById("field"+oneItemId+"_"+"smsSubscriberName").value='<%--=subContractForm.getSmsSubscriberName()[i]--%>';				
				//document.getElementById("field"+oneItemId+"_"+"smsSubscriberPhone").value='<%--=subContractForm.getSmsSubscriberPhone()[i]--%>';
				document.getElementById("field"+oneItemId+"_"+"remark").value='<%=subContractForm.getRemark()[i]%>';
				 if(document.getElementById("field"+oneItemId+"_"+"preparePayMode").value=="true"){
						document.getElementById("field"+oneItemId+"_"+"preparePayMode_chk").checked=true;
				 }else{
				 
				 }
		<%
			} catch (Exception e) {
				e.printStackTrace();			
			}
			}	
		}

	 %>
	 		</script>
	</body>
</html>

					