<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
    <link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
	<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	function synch(id,model){
		Ext.get(document.body).mask('同步中......');
		Ext.Ajax.request({
			method: 'post',
			url: '${app}/netresource/pnrNetResourceAction.do?method=synchNetResToResConfig&id='+id+ '&model='+model,
			success: function(response,options){		
				Ext.get(document.body).unmask();
				if(response.responseText == 'success'){
					alert('同步成功');
				}else{
					alert('同步失败，请联系管理员');
				}
				window.location.reload();
			},
			failure: function(){
				Ext.get(document.body).unmask();
				Ext.Msg.show({
					title: '错误提示',
					msg: '同步发生未知错误',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				});
			}
		});
	}
	
</script>			
    
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list" 
		export="false" requestURI="${app}/netresource/pnrNetResourceAction.do"
		sort="list" partialList="true" size="${resultSize}" 
		decorator="com.boco.eoms.partner.netresource.util.PnrNetResourceListDecorator">
	
		<display:column  title="网络资源类型" >
        	<c:set var="key" value="${list.DATASYNCH_MODEL}"></c:set>
        	${map[key] }
    	</display:column>
		<display:column title="增加数量" property="addcount" />
		<display:column  title="删除数量" property="deletecount" />
		<display:column  title="最新同步时间">
			<bean:write name="list" property="times" format="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column  title="同步到巡检资源" property="flag" />
	</display:table>
	
  </div>
	
<%@ include file="/common/footer_eoms.jsp"%>
