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
		<title>�Ӻ�ͬ����</title>
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
  //������
    var option=new Option(document.forms[0].distributeSubContractIds.options[sel_sprindex].text);
    option.value= document.forms[0].distributeSubContractIds.options[sel_sprindex].value;
    document.forms[0].unDistributeSubContractIds.options[document.forms[0].unDistributeSubContractIds.options.length]=option;
    //ɾ��ѡ��
    document.forms[0].distributeSubContractIds.options[sel_sprindex]=null;
  }
}
function del_all_RoomTrue()
{
  var sel_sprlen=document.forms[0].distributeSubContractIds.options.length-1;
  var j=0;
  for(j=sel_sprlen;j>=0;j--)
  {
  //ȡԪ��
    var option=new Option(document.forms[0].distributeSubContractIds.options[j].text);
    option.value= document.forms[0].distributeSubContractIds.options[j].value;
    document.forms[0].unDistributeSubContractIds.options[document.forms[0].unDistributeSubContractIds.options.length]=option;
    //�ƿ�
 	 document.forms[0].distributeSubContractIds.options[j]=null;
  }
}



//var index=1; //�Ӻ�ͬ������(��"α"ɾ��Div����(ɾ��Div������Div��ʾ),)
var index=1;//��ʾ��С���Ӻ�ͬ��������ID
var subContract_Div_idleId=new Array();  //��ҵ��ͬ����(main1,main2,main3,main4)���ڿ���״̬,��ʾû��û��ʾ����,��ûװ���Ӻ�ͬ.��ʼΪ1,2,3,4
										//ÿ����һ����ҵ��ͬȡһ������,ȡ��С��һ��,ÿɾ��һ�������������һ����ʶ
	//ȡ������������С��ʶ��
	function getMin_DivIdleId(){
		if(subContract_Div_idleId.length==1){
			return subContract_Div_idleId[0];
			subContract_Div_idleId.splice(0,1);  //ɾ��min��Ӧ��������. splice(start,count,xxx,xxx); ���js������ǿ��,����ɾ������Ķ���,�滻�������,��.
		}else{
			var	min=subContract_Div_idleId[0];
			var min_index=0;
			for(var i=1;i<subContract_Div_idleId.length;i++){
				if(subContract_Div_idleId[i]<min){
					min=subContract_Div_idleId[i];
					min_index=i;
				}
			}
			subContract_Div_idleId.splice(min_index,1);  //ɾ��min��Ӧ��������.
			return min;
		}
	}
	function initData(){
		var maxSubContractDivCount=<%=subContractTypeCount%>;
		for(var i=0;i<maxSubContractDivCount;i++){
			subContract_Div_idleId.push(i);
		}
	}
	
	/**����ƶ�(�����Ӻ�ͬ�ƶ���)*/
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
	//�Ƿ��Ѿ��������Ӻ�ͬ
	var hasSubContract='<%=request.getAttribute("hasSubContract")%>';
	/**�����Ӻ�ͬ*/
	/**
	*@param isUpdatePage ˵���ǲ��Ǹ���ʱ��ҳ��.
	* ����Ǹ���ʱ��ҳ����ɾ����ҵ��ͬʱҪ�ж���ҵ��ͬ�Ƿ�����ɾ��,�������ɾ����dojo����ɾ����ҵ��ͬ.
	* @param uniteValue_DB ���ݿ�洢��ҵ��ͬ��uniteValue
	*/
	function createSubContract(isUpdatePage,uniteValue_DB){
		var length=document.forms[0].distributeSubContractIds.options.length;
		if(length>0){
			index=getMin_DivIdleId();   //��λ�Ӻ�ͬ����λ��(�±�).
			var contractItems=new Array();
			var uniteValue="";
			
			//ɾ����ͬʱ���ӹ���
			var mainDiv_delete_anchor=document.getElementById("main" + index + "_delete_anchor");
			
			if(typeof(isUpdatePage)=="boolean" && isUpdatePage==true){
				uniteValue=uniteValue_DB;  //uniteValueҪ�������ݿ��uniteValue(��ΪҪͨ��contractId��uniteValueΨһȷ��һ����ҵ��ͬ.)
				var contractItems_DB=uniteValue_DB.split("_");
				for(var i=0;i<contractItems_DB.length;i++){
					contractItems.push(contractItems_DB[i]);
				}
				 //�����ҳ���Ǹ��µ�ҳ��״̬,��Ҫ�ж���ҵ��ͬ�Ƿ����ɾ����ҵ��.
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
			
			//ȡ��ҵ��ͬ��һ����ͬ��
				var firstItem=document.forms[0].distributeSubContractIds.options[0].value; 
				var firstItem_payee=document.getElementById("field"+firstItem+"_"+"payee");
				var firstItem_gatheringAccount=document.getElementById("field"+firstItem+"_"+"gatheringAccount");
				var firstItem_contactMethod=document.getElementById("field"+firstItem+"_"+"contactMethod");
				var firstItem_uniteValue=document.getElementById("field"+firstItem+"_"+"uniteValue");
				//����uniteValue
				firstItem_uniteValue.value=uniteValue;

			if(length>1){//�������Ϻ�ͬ�����һ���Ӻ�ͬ(��Ҫͬ���տ��˵���Ϣ)
				var otherItems_payee=new Array();
				var otherItems_gatheringAccount=new Array();
				var otherItems_contactMethod=new Array();
				for(var i=1;i<length;i++){  //�ӵڶ�����ʼ
					//ȡ���Ӻ�ͬ��������ͬ��
					var otherItem=document.forms[0].distributeSubContractIds.options[i].value;
					var other_payee=document.getElementById("field"+otherItem+"_"+"payee");
					var other_gatheringAccount=document.getElementById("field"+otherItem+"_"+"gatheringAccount");
					var other_contactMethod=document.getElementById("field"+otherItem+"_"+"contactMethod");
					var other_uniteValue=document.getElementById("field"+otherItem+"_"+"uniteValue");
					//����ֻ������
					other_payee.readOnly=true;
					other_gatheringAccount.readOnly=true;
					other_contactMethod.readOnly=true;
					if(hasSubContract=="false"){   //�ж��Ƿ��Ǹո������Ӻ�ͬʱ�����,�����ҳ���Ǹ���ҳ������ԭ����ҵ��ͬ��ֵ
						other_payee.value="���ݵ�һ����ͬ���Զ�����...";
						other_gatheringAccount.value="���ݵ�һ����ͬ���Զ�����...";
						other_contactMethod.value="���ݵ�һ����ͬ���Զ�����...";		
					}
					//����uniteValue
					other_uniteValue.value=uniteValue;
					otherItems_payee.push(other_payee);
					otherItems_gatheringAccount.push(other_gatheringAccount);
					otherItems_contactMethod.push(other_contactMethod);					
				}
				//�������һ����ҵ��ͬ�е�һ����ͬ���onchange�¼�,�õ�һ����ͬ��ͺ���ĺ�ͬ���ֵ����ͬ������.
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
			alert("����ѡ���ͬ��,Ȼ�������Ӻ�ͬ.");
			document.forms[0].unDistributeSubContractIds.focus();
			
		}
		document.forms[0].distributeSubContractIds.options.length=0;
	}
	
	/**
	*ɾ���Ӻ�ͬ�ĺ���(����Ժ����Ҫ���Ajaxʵ��,��̬��ɾ�����ݿ��Ӧ���Ӻ�ͬ�µĺ�ͬ��|��ͬ���ƻ�|��ͬ���ƻ���)
	* @param contractItemDivArr ��ͬ��Div ID������StaticVariable.CONTRACT_ITEM_ROOM_STR
	*/
	function deleteMainDiv(mainDivId,uniteValue,isUpdatePage){
		var mainDiv=document.getElementById("main"+mainDivId);
		var containerDiv=document.getElementById("containerDiv");
		var contractItemIdArr=uniteValue.split("\_");
		
		//����ҳ��ʱɾ������,��ʾ��ҵ��ͬ�Ǵ��������ݿ���
		var allowDelete=false;
		if(typeof(isUpdatePage)=='boolean' && isUpdatePage==true){
			allowDelete=ajaxDeletePropertyContract(uniteValue);
			if(allowDelete==false){
				return;
			}
		}
		
		//�����Ӻ�ͬ�����ĺ�ͬ��.
		for(var i=0;i<contractItemIdArr.length;i++){
			var itemId=contractItemIdArr[i];
			//ȡ��ͬ���Div
			var itemDiv=document.getElementById("field"+itemId+"_Div");
			itemDiv.style.display="none";//��ͬ��div����
			containerDiv.appendChild(itemDiv);  //��ʱ׷�ӵ�containterDiv������(copy����)
			itemDiv.remove;  //ȥ���Ӻ�ͬDiv����ĺ�ͬ��
			
			
			//�����ɾ�����Ӻ�ͬ�����ĺ�ͬ��,��ԭ��δ���ɺ�ͬ�����������.	
			var option=new Option();
			option.value=itemId;
			option.text=document.getElementById('field'+itemId+"_subContractTypeName").innerText;
			
			document.forms[0].unDistributeSubContractIds.options[document.forms[0].unDistributeSubContractIds.options.length]=option;
			
			
			//�����ɾ���Ӻ�ͬ������������,���Ѹ�����ͬ��/�տ��ʺ�ͬ�����¼�ȥ��.��ֻ������ȥ��,unite_value�������
			var item_payee=document.getElementById("field"+itemId+"_"+"payee");
			var item_gatheringAccount=document.getElementById("field"+itemId+"_"+"gatheringAccount");
			var item_contactMethod=document.getElementById("field"+itemId+"_"+"contactMethod");
			var item_uniteValue=document.getElementById("field"+itemId+"_"+"uniteValue");
			//����ֶ�ֵ��uniteValue
					
			item_uniteValue.value="";
			item_payee.value="";
			item_gatheringAccount.value="";
			item_contactMethod.value="";
			//��ԭ��������
			
			item_payee.readOnly=false;
			item_gatheringAccount.readOnly=false;
			item_contactMethod.readOnly=false;
			//��ԭͬ���¼�onchange(��һ��Ԫ�ؾ���onchange�¼�)
			item_payee.onchange=function(){};
			item_gatheringAccount.onchange=function(){};
			item_contactMethod.onchange=function(){};
		}
		mainDiv.style.display="none";
		subContract_Div_idleId.push(mainDivId);  //�Ӻ�ͬ������������һ��.
		
		if(allowDelete==true){
			//����������ɾ����,ִ��ҳ��ɾ����������ʾ.
			 alert("��ҵ��ͬɾ���ɹ�,������µ���ҵ��ͬ!");
		}
		//index--;
	}
	
	/**�Ƿ�����ɾ�������ҵ��ͬ(�ж������ҵ��ͬ�Ƿ����ɾ������.)  contractId��uniteValueΨһȷ��һ����ҵ��ͬ.*/
	//ע������:uniteValueҪ����ҵ��ͬ�����ݿ���ı�ʶһ��(���ܺ�ͬ���ʶ�ߵ�˳��.) . 3_2_4_1��4_1_3_2����ǲ�ͬ�����.
	function ajaxDeletePropertyContract(uniteValue){
	    var contractId = document.forms[0].contractId.value;  //����ɾ����ҵ��ͬ�ı�׼��ʱ���� ��������ͬ������Ϣ����Ч��ʱ��Ϊ׼.
	    var flag=false;
	    if(confirm("��ҵ��ͬɾ�����ָܻ�����ȷ��Ҫɾ����")){
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
			            alert("��ҵ��ͬ����ɾ��,��ҵ��ͬ�Ѿ���Ч!");
			            flag=false;
			        }else if(echo==1){
			           //����������ʾɾ���ɹ�����ʾ,����ҳ���������ʾ.
			           // alert("��ҵ��ͬɾ���ɹ�,������µ���ҵ��ͬ!");
			       		flag=true;
			        }else{
			            alert("��ҵ��ͬɾ��ʱ��������,����ϵ����Ա!");
			        	flag=false;
			        }
				},
				error:function(type,error){
					alert("��ҵ��ͬɾ��ʱ��������:"+error.message+",����ϵ����Ա!");
			        flag=false;
				}
			};
			dojo.io.bind(request);
	    }else{
			flag=false;
		}
		return flag;
	}
	
	
	//��ʼ��ҳ��
	function init(){
		initData();
		initSubContract();
	}	
	//��ҳ�����ڸ���ҳ��ʱ��ʼ��������������ҵ��ͬ�����.
	function initSubContract(){
		if(hasSubContract=="true"){
			<%=request.getAttribute("distinctUniteValueJsArr")%>
			for(var i=0;i<arr.length;i++){
				var contractItemIds=arr[i].split("_");
				for(var k=0;k<contractItemIds.length;k++){
					var option=new Option('',contractItemIds[k]);
					document.forms[0].distributeSubContractIds.options[document.forms[0].distributeSubContractIds.options.length]=option;
				}
				createSubContract(true,arr[i]);  //����arr[i]��ʾ���ݿ�洢����ҵ��ͬUniteValue
			}
		}
	}
	dojo.addOnLoad(init);
	
	function checkData(){
		//��preparePayMode��ֵ,checkbox��ѡ�в����ύ
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
				if(!haveOneSubContract) haveOneSubContract=true;  //˵������һ��UniteValue��Ϊ��.
				//if(checkLength(firstItem_payee,1,15) && checkLength(firstItem_gatheringAccount,1,40) && checkLength(firstItem_smsSubscriberName,1,10) && mobileCheck(firstItem_smsSubscriberPhone) && checkLength(firstItem_contactMethod,1,50) &&
				if(checkLength(firstItem_payee,1,15) && checkLength(firstItem_payDuration,1,5) && checkLength(firstItem_contactMethod,1,50) &&
					checkNotNull(firstItem_budgetUnitName,'���мƷѵ�λ����Ϊ��,��ȷ��!') && numCheckAllowNull(firstItem_payMoney,'�����տ������Ϊ��ֵ��,��ȷ��!')){
					checkPass=true;
				}else{
					checkPass=false;
					return false;
				}
			}
		}
		if(haveOneSubContract==false){
			alert("���ٴ���һ����ҵ��ͬ���Ӻ�ͬ��,���ܱ���!");
			checkPass=false;
			return false;
		}
		return checkPass;
	}
	

//-->
</SCRIPT>
	</head>
	<!---------------------------------------ҳ�����忪ʼ--------------------------------------->
	<body>
		<!------------------��ʶҳ��λ��------------------>
	<p align="left">
		����λ��->�����ͬ����->
		<bean:write name="specialtyName" />
		->
		<bean:write name="contractModelName" />
		->��ҵ��ͬ(������:<bean:write name="workAroundNames" />)
	</p>
		<html:form method="post"
			action="/contract/propertiesContract_SubContract_SaveUpdate" onsubmit="return checkData();">
		<input name="addOrUpdate" type="hidden" />  <!-- ����ҳ�滹�Ǹ���ҳ�� -->
		<!-- �Ӻ�ͬ����ҳ�濪ʼ -->
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
											�����Ӻ�ͬ
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
													<html:button styleClass="clsbtn" property="createSubButton" onclick="createSubContract()">����(��)��ͬ</html:button>
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
		<!---------------------------------------��ͬ�������Ϣ¼��ҳ��-------------------------------------->
		
		
 <div id="mainDivContainer" style="display:block;position:relative;width: 100%;height:500px;" >
 	 <%
 	 	for(int i=0;i<subContractTypeCount;i++){
 	  %>
	 	 <div id="main<%=i%>" style="display:none;position:relative;width: 100%;">
	 	 	<div>	
	 	 		<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
					<b>��ҵ��ͬ��<%=i+1%>��
					</b>
					<span id="main<%=i%>_delete_span"><a id="main<%=i%>_delete_anchor" href="javascript:deleteMainDiv(<%=i%>);">[ɾ��]</a></span>
				</P>
			</div>
	 	 </div>
	 <%} 
	 %>
	 			<table>
				<tr height="10%" class="tr_show">
					<td colspan="4" align="right">
						<html:submit styleClass="clsbtn2" alt="�����ͬ">
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
						�տ���
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_payee' name="payee" class="clstext" maxlength="200" title="�տ���"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						��ϵ��ʽ
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_contactMethod' name="contactMethod" class="clstext" maxlength="200" title="��ϵ��ʽ"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						�տ�����
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_payBank' name="payBank" class="clstext" maxlength="200" title="�տ�����"/>						
					</td>
					<td width="15%" height="25" class="clsfth">
						�տ��ʺ�
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_gatheringAccount' name="gatheringAccount" class="clstext" maxlength="200" title="�տ��ʺ�"/>						
					</td>
					
					
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						������
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_payMoney' name="payMoney" class="clstext" maxlength="20" title="�տ���"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						�Ʒѵ�λ
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_budgetUnitName' name="budgetUnitName" class="clstext" maxlength="20" title="�Ʒѵ�λ"/>
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
						��������
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_payDuration' name="payDuration" class="clstext" maxlength="20" title="��������"/>
						<select style="width: 1cm" name="unitId" id='field<bean:write name="row" property="subContractTypeId"/>_unitId'>
							<logic:iterate id="cycRow" name="dicQuantityList_Cycle">
								<option value='<bean:write name="cycRow" property="value"/>'><bean:write name='cycRow' property="label"/></option>
							</logic:iterate>
						</select>
					</td>
					<td width="15%" height="25" class="clsfth" >
						�Ƿ�Ԥ����
					</td>
					<td width="35%" height="25" >
						<%
						//����߼����ж����ж�checkbox�Ƿ�checked����������Ҫ��Щ�жϣ��������js��ֵ�п�������.����IE��checkboxǶ����div���п��ܻ������Ȼ����checked���Ե���checkboxȴ����ѡ��.
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
						��������������
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_smsSubscriberName' name="smsSubscriberName" class="clstext" maxlength="20" title="��������������"/>
						&nbsp;&nbsp;
						<span class="requisite">**</span>
					</td>
					<td width="15%" height="25" class="clsfth">
						�����������ֻ�
					</td>
					<td width="35%" height="25">
						<input type="text" id='field<bean:write name="row" property="subContractTypeId"/>_smsSubscriberPhone' name="smsSubscriberPhone" class="clstext" maxlength="20" title="�����������ֻ�"/>
						</td>
				</tr>
				--%><tr class="tr_show">
					<td width="15%" height="25" class="clsfth" >
						����
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

					