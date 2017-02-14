<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<c:if test="${personCardNo1!=''}">
<%request.setAttribute("personCardNo", request.getAttribute("personCardNo1")); %>
</c:if>
<c:if test="${id!=''}">
<%
String id=(String)request.getAttribute("id");
System.out.println("id          "+id);
request.setAttribute("id", request.getAttribute("id")); 
%>
</c:if>
<c:if test="${personCardNo!=''}">
<%request.setAttribute("personCardNo", request.getAttribute("personCardNo")); %>
</c:if>
<script type="text/javascript">
    var _tabs;
    Ext.onReady(function(){
        var tabConfig = {
            items:[
                {id:'sheet-person',  text:'人力信息', url:eoms.appPath+'/partner/baseinfo/partnerUsers.do?method=detail&nodeId=${nodeId}&personCardNo=${personCardNo}&id=${id}&personCardNo1=${personCardNo1}&type=${type}&isPartner=${isPartner}', isIframe : true},
              {id:'sheet-resume',   text:'人员工作经历', url:eoms.appPath+'/personnel/workExperience.do?method=search&operationType=tomgr&personCardNo=${personCardNo}&personCardNo1=${personCardNo1}&isPartner=${isPartner}', isIframe : true},
       	   {id:'sheet-dwinfo',   text:'人员技能信息', url:eoms.appPath+'/personnel/dwInfo.do?method=search&operationType=tomgr&personCardNo=${personCardNo}&personCardNo1=${personCardNo1}&isPartner=${isPartner}', isIframe : true},
       	   {id:'sheet-certificate',   text:'人员证书信息', url:eoms.appPath+'/personnel/certificate.do?method=search&operationType=tomgr&personCardNo=${personCardNo}&personCardNo1=${personCardNo1}&isPartner=${isPartner}', isIframe : true},
       	   {id:'sheet-reward',   text:'人员奖励信息', url:eoms.appPath+'/personnel/reward.do?method=search&operationType=tomgr&personCardNo=${personCardNo}&personCardNo1=${personCardNo1}&isPartner=${isPartner}', isIframe : true},
       	   {id:'sheet-studyExperience',   text:'人员教育经历', url:eoms.appPath+'/personnel/studyExperience.do?method=search&operationType=tomgr&personCardNo=${personCardNo}&personCardNo1=${personCardNo1}&isPartner=${isPartner}', isIframe : true},
       	{id:'sheet-pxExperience',   text:'人员培训经历', url:eoms.appPath+'/personnel/pxExperience.do?method=search&operationType=tomgr&personCardNo=${personCardNo}&personCardNo1=${personCardNo1}&isPartner=${isPartner}', isIframe : true}
            ]
        };

        _tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
        init();
    });
    
    function doCheck(id,bCheck){
        var item = _tabs.getActiveTab();
        var selfItem = _tabs.getTab(id);
        if(item==selfItem){
            _tabs.activate("sheet-person");
        }
        	
        if(bCheck){
            _tabs.tabPanel.hideTab(id);
        }
        else{
            _tabs.unhideTab(id);
        }
    }

    function disableTab(id){
        _tabs.tabPanel.disableTab(id);
    }
		
    function enableTab(id){
        _tabs.tabPanel.enableTab(id);
    }
		
    function disableAllTab(){
        _tabs.tabPanel.disableTab("sheet-resume");
    }
		
    function enableAllTab(){
        _tabs.tabPanel.enableTab("sheet-resume");		
    }

    function setPersonCardNo(personCardNo){
        window.navigate("${app}/partner/baseinfo/resumes.do?method=newExpert&personCardNo="+personCardNo);
    }
		
    function init(){
        var personCardNo = '${personCardNo}';
        var personCardNo1 = '${personCardNo1}';
        if(personCardNo == ''){
	        if(personCardNo1==''){
	        	disableAllTab();
	        }else{
	        	enableAllTab();
	        }
        }else{
            enableAllTab();
        }
    }
</script>

<div id="sheet-detail-page">
</div>

<br/>
<%@ include file="/common/footer_eoms.jsp"%>
