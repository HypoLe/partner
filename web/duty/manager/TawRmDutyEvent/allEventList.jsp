<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<script type="text/javascript">
Ext.onReady(function() {
	oninit();
});

function nTabs(tabObj,obj){
	var tabList = document.getElementById(tabObj).getElementsByTagName("a");
	
	for(i=0; i <tabList.length; i++) {
		if (tabList[i].id == obj.id) {
			if(document.getElementById(tabObj+"_Title"+(i+1))!=null)
			document.getElementById(tabObj+"_Title"+(i+1)).className = "normal"; 
		}else{
			if(document.getElementById(tabObj+"_Title"+(i+1))!=null)
			document.getElementById(tabObj+"_Title"+(i+1)).className = "active"; 
		}
	} 
}

function fun_goTab(object,roomid,region){	
	document.frames["iframe"].location.href ="${app}/duty/dutyevent.do?method=eventList&roomid="+roomid+"&region="+region;
 }

function oninit() {
	document.getElementById("naTab_Title1").className = "normal"; 
}
</script>
<table width="100%" height="100%">
	<tr>
		<td colspan="2" id="naTab">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<logic:iterate id="REGIONLIST" name="REGIONLIST" type="com.boco.eoms.duty.model.TawRmDutyEventRegion">
				<a id='naTab_Title<bean:write name="REGIONLIST" property="id" scope="page"/>' onclick="nTabs('naTab',this);fun_goTab('tab<bean:write name="REGIONLIST" property="id" scope="page"/>',<bean:write name="REGIONLIST" property="roomId" scope="page"/>,<bean:write name="REGIONLIST" property="id" scope="page"/>);" class="active" style="cursor:hand;">
				<strong><font color="<bean:write name="REGIONLIST" property="color" scope="page"/>" style="cursor:hand;"><bean:write name="REGIONLIST" property="regionName" scope="page" /></font></strong>
				</a>
				</span>&nbsp;
			</logic:iterate>
		</td>
	</tr>
	<tr>
		<td align="center" valign="middle">
			<TABLE cellSpacing="0" cellPadding="0" width="95%" borderColorLight="#709FD5" borderColorDark="#ffffff" align="center" bgColor="#f3f3f3" border="1">
				<TR style="display: inline" id='dispTr'>
					<TD colSpan="6" class="clsfth">
						<IFRAME ID=iframe FRAMEBORDER=0 width="100%" height=8500 SCROLLING=NO SRC='${app}/duty/dutyevent.do?method=eventList&roomid=${roomid}'></IFRAME>
					</TD>
				</TR>
			</TABLE>
		</td>
	</tr>
</table>

<%@ include file="/common/footer_eoms.jsp"%>
