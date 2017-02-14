<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript">
	function showSubList(thingId, curObj) {
		if (document.getElementById("subListViewThing" + thingId).innerHTML == "") {
				document.getElementById("subListIframe").src ="${app}/duty/TawRmThing.do?method=listThingNote&thingId=" + thingId;
				curObj.firstChild.src = "${app}/duty/images/nofollow.gif";
		} else {
				document.getElementById("subListViewThing" + thingId).innerHTML = "";
				curObj.firstChild.src = "${app}/duty/images/plus.gif";
		}
 	} 
 	function AddThingAndNote(id){
 		window.navigate("${app}/duty/TawRmThing.do?method=addThingAndNote&thingId=" + id);
 	}
</script>		
		<table class="formTable">
			<caption>
				<div class="header center"><fmt:message key="tawRmThingForm.form.head"/></div>
			</caption>
			<TR class="label">
				<TD align="center" class="label">
					<fmt:message key="tawRmThingForm.thingName"/>
				</TD>
				<TD align="center" class="label">
					<fmt:message key="tawRmThingForm.isForUse"/>
				</TD>
				<TD align="center" class="label">
					<fmt:message key="tawRmThingForm.estate"/>
				</TD>
				<TD align="center" class="label">
					<fmt:message key="tawRmThingForm.thingComment"/>
				</TD>
				<TD align="center" class="label">
					<fmt:message key="tawRmThingForm.operate"/>
				</TD>
			</TR>
			<logic:iterate id="Thinglist" name="Thinglist">
				 <tr class="tr_show">
	    			<td>
	    				<span onclick="showSubList('<bean:write name="Thinglist" property="id"/>',this);">
							<img src="${app}/duty/images/plus.gif">
							<bean:write name="Thinglist" property="thingName" scope="page"/>
						</span>
	    			</td>
     				<td>
     					<logic:equal value="1" name="Thinglist" property="isForUse" scope="page">
     						<fmt:message key="tawRmThingForm.isForUse_yes"/>
     					</logic:equal>
     					<logic:equal value="0" name="Thinglist" property="isForUse" scope="page">
     						<fmt:message key="tawRmThingForm.isForUse_no"/>
     					</logic:equal>			
     				</td>
     				<td>
     					<logic:equal value="0" name="Thinglist" property="estate" scope="page">
     						<fmt:message key="tawRmThingForm.noEstate"/>
     					</logic:equal>
     					<logic:equal value="1" name="Thinglist" property="estate" scope="page">
     						<fmt:message key="tawRmThingForm.isEstate"/>
     					</logic:equal>	
     				</td>
     				<td>
     					<bean:write name="Thinglist" property="thingComment" scope="page"/>
     				</td>
     				<td width="10%">
     					<input type="button" class="button" value='<fmt:message key="tawRmThingForm.manage"/>' onclick="AddThingAndNote('<bean:write name="Thinglist" property="id"/>');"/>
     				</td>
     			</tr>
     			<tr>
					<td colspan="5" id="subListViewThing<bean:write name="Thinglist" property="id" />">
					</td>
				</tr>
			</logic:iterate>
		</table>
		<div style="display: none">
			<iframe name="subListIframe" id="subListIframe" src="" width="400" height="300"></iframe>
		</div>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>