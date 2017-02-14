<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">

/**
*  打开合作伙伴工单窗口
*/
function openPartnerWindow(){
    //EOMS用户ID(根据实际情况自行获取)
	var userId = 'admin';
	//合作伙伴IP和端口(根据实际情况修改)
	var partnerIp = '127.0.0.1:8080';
	//故障工单URL
	var partnerUrl = 'http://'+partnerIp+'/partner/sheet/pnrfaultdeal/pnrfaultdeal.do?method=showNewSheetPage';
	var url = partnerUrl + '&type=interface&userName='+userId;
    
    //需要从eoms传递到合作伙伴的参数名称(根据实际情况自行获取)
    var keys=['fromEoms','netSortType','title'];
    //需要从eoms传递到合作伙伴的参数值(根据实际情况自行获取)
    var values=['true','1010104020601','aaabbb'];
    
    openWindowWithPost(url,'newwindow',keys,values);  
}
/**
*  变相实现window.open的post提交方式
*/
function openWindowWithPost(url,name,keys,values)  
{  
    var oForm = document.createElement("form");  
    oForm.id="paramsForm";  
    oForm.method="post";  
    oForm.action=url;  
    oForm.target="test";  
    if (keys && values && (keys.length == values.length)){  
        for (var i=0; i < keys.length; i++){  
            var oInput = document.createElement("input");  
            oInput.type="text"; 
            oInput.name=keys[i];  
            oInput.value=values[i];  
            oForm.appendChild(oInput);  
        }  
    }  
    oForm.onSubmit = function(){openSpecfiyWindown(name)};  
    document.body.appendChild(oForm);
    oForm.submit();
    document.body.removeChild(oForm)
}  

function openSpecfiyWindown(name){  
    window.open('about:blank',name);   
}  
	
</script>
<html:html>
<body>

<input type="button" value="代维故障工单" onclick="openPartnerWindow()"/>
</body>
</html:html>