<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

  Ext.onReady(function()
  {
   var tabConfig = {
		items : [{
			id : 'sheetinfo',
			text : '基本信息'
		]
	};
   
	var tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
	
   }
   );
</script>
<!-- Sheet Tabs Start -->
<div id="sheet-detail-page">
  <!-- Sheet Detail Tab Start -->
  <div id="sheetinfo">
  	<logic:present name="pnrTransferOffice" scope="request">
  	
  	<table class="formTable">
  			<caption>方案制定详情</caption>
  	</table>
  	
  	<table id="sheet" class="formTable"> 	 
	 <tr>
	  <td class="label">制定人</td>
	  <td class="content">	  
			${pnrTransferOffice.theme}
	  </td>
	  <td class="label">
						单位
					</td>
					<td class="content">	  
			${pnrTransferOffice.theme}
	  </td>
	 </tr>
 	<tr>		  
 			<td class="label">电话</td>
			<td class="content">	  
			${pnrTransferOffice.theme}
	  </td>
			<td class="label">
						简要描述
					</td>
					<td class="content">	  
			${pnrTransferOffice.theme}
	  </td>
		
	</tr>
 	<tr>
			<td class="label">附件</td>
		    <td  colspan='3'>
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTransferOffice" 
		             viewFlag="Y"/> 
		    </td>
	  </tr>
</table>
    </logic:present>
   
  </div>
   
</div>
<!-- Sheet Tabs End -->