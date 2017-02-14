<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<html:form action="/pnrAgreementMains.do?method=queryContractDo&target=serach" styleId="ctContentsForm" method="post">
<eoms:xbox id="tree" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="partyAId" handler="partyA"
		textField="partyA"
		checktype="dept" single="true"		
	  ></eoms:xbox>
<eoms:xbox id="tree" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="2" 
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

	<display:table name="pnrFeeInfoMainList" cellspacing="0" cellpadding="0"
		id="pnrFeeInfoMainList" pagesize="${pageSize}"
		class="table pnrFeeInfoMainList" export="false"
		requestURI="${app}/partner/agreement/pnrAgreementMains.do?method=queryContractDo" sort="list"
		partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.contract.contents.displaytag.support.CtContentsDisplaytabDecorator">


		<display:column title="选择 " href="" paramId="id" paramProperty="id"> 
			<!-- 合同ID -->
			<input type="radio" name="ids" id="${pnrFeeInfoMainList.id}" value="${pnrFeeInfoMainList.id}" onclick="getContract(this.value);" /> 
			<!-- 合同编号 -->
			<input type="hidden" name="${pnrFeeInfoMainList.id}_no" id="${pnrFeeInfoMainList.id}_no" value="${pnrFeeInfoMainList.contractNO}"/> 
			<!-- 合同名称 -->
			<input type="hidden" name="${pnrFeeInfoMainList.id}_title" id="${pnrFeeInfoMainList.id}_title" value="${pnrFeeInfoMainList.contractName}"/>
			
		
		</display:column> 
	

		<display:column sortable="true" property="contractName" titleKey="ctContents.contentTitle" 
		    paramId="id" paramProperty="id"
			headerClass="sortable" />
			
			
		<display:column sortable="true" headerClass="sortable" title="合同总额(万元)">
			${pnrFeeInfoMainList.contractAmount}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createUser">
			<eoms:id2nameDB id="${pnrFeeInfoMainList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column sortable="true" property="createTime" titleKey="ctContents.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"
		    paramId="id" paramProperty="id"
			headerClass="sortable" />

		<%--<display:column sortable="true" headerClass="sortable" titleKey="ctContents.contentStatus">
			<eoms:dict key="dict-partner-contract" dictId="contractStatus" itemId="${pnrFeeInfoMainList.contractStatus}" beanId="id2nameXML" />
		</display:column>
		--%><display:column title="查看" headerClass="imageColumn">
			<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=${pnrFeeInfoMainList.id }" target="_blank">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />

	</display:table>
</fmt:bundle>
<script>
    function getContract(contractId){
   		 if(confirm('你确定选择该合同?')){
	    	 var contractNO = document.getElementById(contractId+"_no").value;
	    	 var contractName = document.getElementById(contractId+"_title").value;
	         window.opener.document.all.compactNo.value=contractNO;
	         window.opener.document.all.compactName.value=contractName;
	         window.opener.document.all.contentId.value=contractId;
	         window.opener.setCompactNostr(contractNO);
	         self.close(); 
         }
		
    }
</script>

<%@ include file="/common/footer_eoms.jsp"%>