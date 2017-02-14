<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<html:form action="/pnrAgreementMains.do?method=queryContractDoAgr&target=serach" styleId="ctContentsForm" method="post">
<eoms:xbox id="tree" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="partyAId" handler="partyA"
		textField="partyA"
		checktype="dept" single="true"		
	  ></eoms:xbox>
<eoms:xbox id="tree" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="partyBId" handler="partyB"
		textField="partyB"
		checktype="dept" single="true"		
	  ></eoms:xbox>
<table class="formTable">

	<caption>
		<div class="header center">&nbsp;合同查询</div>
	</caption>

	<tr>
		<td class="label">
			合同名称：
		</td>
		<td class="content">	
			<input type="text"   name="contentName" id="contentName" value=""/>
		</td>

		<td class="label">
			合同编号：
		</td>
		<td class="content">								
			<input type="text"   name="contractNO" id="contractNO" value=""/>		
		</td>
	</tr>
	
	<tr>
		<td class="label">
			甲方：
		</td>
		<td class="content">
		    <input type="text" name="partyA" id="partyA" value="" maxLength="100" />		
		    <input type="hidden" name="partyAId" id="partyAId" value="" maxLength="32"/> 
		</td>
		<td class="label">
			乙方
		</td>
		<td class="content">
		    <input type="text" name="partyB" id="partyB" value="" maxLength="100"/>		 
			<input type="hidden" name="partyBId" id="partyBId" value="" maxLength="32"  />	
		</td>
	</tr>	
	<tr>
		<td  colspan="4">
			<input type="submit" class="btn" value="查询" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>
<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">
<html:form action="pnrAgreementMains.do?method=saveContent" styleId="pnrAgreementForm" method="post">
			<!-- 协议id -->
			<input type="hidden" name="id" id="id" value="${idAgr }"/> 
			<!-- 合同id -->
			<input type="hidden" name="contentId" id="contentId" value=""/>
			<!-- 合同编号 -->
			<input type="hidden" name="contractNO1" id="contractNO1" value=""/> 
			<!-- 合同名称 -->
			<input type="hidden" name="contractTitle" id="contractTitle" value=""/>
			



	<display:table name="pnrFeeInfoMainList" cellspacing="0" cellpadding="0"
		id="pnrFeeInfoMainList" pagesize="${pageSize}"
		class="table pnrFeeInfoMainList" export="false"
		requestURI="${app}/partner/agreement/pnrAgreementMains.do?method=queryContractDoAgr" sort="list"
		partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.contract.contents.displaytag.support.CtContentsDisplaytabDecorator">


		<display:column title="选择 " href="" paramId="id" paramProperty="id" > 
			<!-- 合同ID -->
			<input type="radio" name="ids" id="${pnrFeeInfoMainList.id }" value="${pnrFeeInfoMainList.id }" onclick="getContract(this.value);" /> 
			<!-- 合同编号 -->
			<input type="hidden" name="${pnrFeeInfoMainList.id }_no" id="${pnrFeeInfoMainList.id }_no" value="${pnrFeeInfoMainList.contractNO}"/> 
			<!-- 合同名称 -->
			<input type="hidden" name="${pnrFeeInfoMainList.id }_title" id="${pnrFeeInfoMainList.id }_title" value="${pnrFeeInfoMainList.contractName}"/>
			
		
		</display:column> 
	

		<display:column sortable="true" property="contractName" titleKey="ctContents.contentTitle" 
		    paramId="id" paramProperty="id"
			headerClass="sortable" />
			
		<display:column sortable="true" headerClass="sortable" title="合同总额(元)">
			${pnrFeeInfoMainList.contractAmount}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createUser">
			<eoms:id2nameDB id="${pnrFeeInfoMainList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column sortable="true" property="createTime" titleKey="ctContents.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"
		    paramId="id" paramProperty="id"
			headerClass="sortable" />

		<display:column title="查看" headerClass="imageColumn">
			<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=${pnrFeeInfoMainList.id }" target="_blank">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>

	</display:table>
</html:form>
</fmt:bundle>
<script>
    function getContract(contentId){
    	 var contractNO = document.getElementById(contentId+"_no").value;
    	 var contractTitle = document.getElementById(contentId+"_title").value;
    	 document.getElementById("contractNO1").value = contractNO;
    	 document.getElementById("contractTitle").value = contractTitle;
    	 document.getElementById("contentId").value = contentId;
		 if(confirm('你确定选择此合同?')){
		  	document.forms[1].submit();
		 }
    }
</script>

<%@ include file="/common/footer_eoms.jsp"%>