<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<div class="successPage">
	<h1 class="header">操作成功！</h1>
	<div class="content">
		您的操作已成功执行。<br/>
	</div>
</div>

<script language="JavaScript">
	
	var modifyType = '${modifyForm.modifyType}';
    var resourceType = '${modifyForm.resourceType}';
    var resourceId = '${modifyForm.resourceId}';
    
    if(modifyType == '2'){//新增
    	if(resourceType!='' && resourceType == '1'){//站点
 		window.open('${app}/partner/net/sites.do?method=edit');
		}else if(resourceType!='' && resourceType == '2'){//线路
			window.open('${app}/netresource/line/lines.do?method=add');
		}else if(resourceType!='' && resourceType == '3'){//标点
			window.open('${app}/netresource/point/points.do?method=add');
		}else if(resourceType!='' && resourceType == '4'){//设备
			window.open('${app}/partner/inspect/pnrInspectDevices.do?method=add');
		}
		
    }else if(modifyType == '1' || modifyType == '3'){//修改
    	if(resourceType!='' && resourceType == '1'){//站点
 		window.open('${app}/partner/net/sites.do?method=search&id='+resourceId);
		}else if(resourceType!='' && resourceType == '2'){//线路
			window.open('${app}/netresource/line/lines.do?method=edit&id='+resourceId);
		}else if(resourceType!='' && resourceType == '3'){//标点
			window.open('${app}/netresource/point/points.do?method=edit&id='+resourceId);
		}else if(resourceType!='' && resourceType == '4'){//设备
			window.open('${app}/partner/inspect/pnrInspectDevices.do?method=edit&id='+resourceId);
		}
		
    }else if(modifyType == '4'){//删除
    	if(resourceType!='' && resourceType == '1'){//站点
 		window.open('${app}/partner/net/sites.do?method=remove&id='+resourceId);
		}else if(resourceType!='' && resourceType == '2'){//线路
			window.open('${app}/netresource/line/lines.do?method=remove&id='+resourceId);
		}else if(resourceType!='' && resourceType == '3'){//标点
			window.open('${app}/netresource/point/points.do?method=remove&id='+resourceId);
		}else if(resourceType!='' && resourceType == '4'){//设备
			window.open('${app}/partner/inspect/pnrInspectDevices.do?method=remove&id='+resourceId);
		}
    }
	
	
</script>

<%@ include file="/common/footer_eoms.jsp"%>