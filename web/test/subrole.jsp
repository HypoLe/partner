<%@ include file="/common/taglibs.jsp"%>
[
<%
for(int i=0;i<400;i++){
	if(i!=0)out.print(",");
	out.println("{'text':'"+i+"','iconCls':'subrole','nodeType':'subrole','name':'生产任务审核角色(毕节分公司)','id':'8a4282a61ae1a808011ae1db6bf20101'}");
}
%>
]