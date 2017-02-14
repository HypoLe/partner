<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
});

function changeTo(tabIndex) {
 	if (tabIndex == 0) {
 		document.getElementById("menuTab0").className = "tab1";
 		document.getElementById("menuTab1").className = "tab2";
 		document.getElementById("dispTr0").style.display = "inline";
 		document.getElementById("dispTr1").style.display = "none";
 	} else {
 		document.getElementById("menuTab0").className = "tab2";
 		document.getElementById("menuTab1").className = "tab1";
 		document.getElementById("dispTr1").style.display = "inline";
 		document.getElementById("dispTr0").style.display = "none";
 	}	
 }
</script>

	<table class="formTable">
		<tr>
		<td class="label" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" id="menuTab0" onClick="changeTo(0)" class="tab1">
				<strong>${eoms:a2u('监控事件记录')}</strong>
				</a>&nbsp;&nbsp;
				<a href="#" id="menuTab1" onClick="changeTo(1)" class="tab2">
					<strong>${eoms:a2u('监控纪要')}</strong>
				</a>
			</td>
		</tr>
		<tr>
			<td align="center" valign="middle">
				<table class="formTable">
					<tr style="display: inline" id="dispTr0">
						<td colSpan="6" class="label">
							<iframe ID=IFrame1 FRAMEBORDER=0 width="100%" height=4500 SCROLLING=NO 
							src='${app}/duty/dutyevent.do?method=add'></iframe>
						</td>
					</tr>
					<tr style="display: none" id="dispTr1">
						<td colSpan="6" class="label">
						<iframe ID=IFrame1 FRAMEBORDER=0 width="100%" height=4500 SCROLLING=NO
										src='${app}/duty/TawRmRecord/record.do'>
						</iframe>
						</td>
					</tr>
				</table>
			</td>
		</tr>
</table>

<%@ include file="/common/footer_eoms.jsp"%>
