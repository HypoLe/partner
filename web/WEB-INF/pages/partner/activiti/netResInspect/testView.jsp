<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突

jq(function(){
	var aaName = "a1";//var aaName = ${aaName};
	jq("input[name='"+aaName+"']").attr('disabled',false);
	
	jq("input").not("input[name!='"+aaName+"']").attr('disabled',true); //取消禁用场景模板中除了数量和单价的文本框
	//jq("input[name!='"+aaName+"']").attr('disabled',true);
});

//jq("input[name=1-check]").bind("click", function () {
			//alert("33333");		
		//});
		
jq(document).delegate("input[name$='-check']", "click", function(){
	//	alert("33333");
		var flag=jq(this).attr("checked");
		var val=jq(this).val();
		var nameVal=jq(this).attr("name");
		mainSceneVal=nameVal.substr(0,nameVal.indexOf('-'));
		if(flag==true){
			if(val=="11"){
				jq("#childSceneShow").append("<div id='1-11-outlayer'><table width='880' border='1' cellspacing='0'><tr><td width='117'>对应主场景</td><td width='234'>对应子场景模板(系统内)</td><td width='130'>处理措施</td><td width='54'>单位</td><td width='72'>材料1</td><td width='59'>金额1</td><td width='52'>材料2</td><td width='50'>金额2</td><td width='54'>处理</td></tr><tr><td height='17'>光电缆</td><td>光缆断或备用纤芯断（无需布放）</td><td>施工挖断、人为破坏</td><td>处</td><td>光缆接头盒</td><td>10</td><td>光缆</td><td>20</td><td>删除</td></tr></table></div>");
			}else if(val=="21"){
				jq("#childSceneShow").append("<div id='2-21-outlayer'><table width='880' border='1' cellspacing='0'><tr><td width='117'>对应主场景</td><td width='234'>对应子场景模板(系统内)</td><td width='130'>处理措施</td><td width='54'>单位</td><td width='72'>材料1</td><td width='59'>金额1</td><td width='52'>材料2</td><td width='50'>金额2</td><td width='54'>处理</td></tr><tr><td height='17'>杆路</td><td>电杆断（拆除）</td><td>拆除电杆</td><td>棵</td><td>光缆</td><td>30</td><td>光缆22</td><td>40</td><td>删除</td></tr></table></div>");
			}else if(val=="32"){
				jq("#childSceneShow").append("<div id='3-32-outlayer'><table width='880' border='1' cellspacing='0'><tr><td width='117'>对应主场景</td><td width='234'>对应子场景模板(系统内)</td><td width='130'>处理措施</td><td width='54'>单位</td><td width='72'>材料1</td><td width='59'>金额1</td><td width='52'>材料2</td><td width='50'>金额2</td><td width='54'>处理</td></tr><tr><td height='17'>管道</td><td>人井盖损坏或丢失</td><td>增补、更换井盖</td><td>处</td><td>人井井盖</td><td>50</td><td>光缆33</td><td>60</td><td>删除</td></tr></table></div>");
			}else if(val=="42"){
				jq("#childSceneShow").append("<div id='4-42-outlayer'><table width='880' border='1' cellspacing='0'><tr><td width='117'>对应主场景</td><td width='234'>对应子场景模板(系统内)</td><td width='130'>处理措施</td><td width='54'>单位</td><td width='72'>材料1</td><td width='59'>金额1</td><td width='52'>材料2</td><td width='50'>金额2</td><td width='54'>处理</td></tr><tr><td height='17'>交接箱</td><td>成端内部维修</td><td>光交成端内维修、上盘、整理</td><td>处</td><td>熔纤盘</td><td>70</td><td>尾纤</td><td>80</td><td>删除</td></tr></table></div>");
			}
		}else{
			jq("#"+mainSceneVal+"-"+val+"-outlayer").remove();
			var num=jq("input[name="+nameVal+"]:checked").size();
			//alert(num);
			if(num=='0'){
				jq("#"+mainSceneVal+"-cond").remove(); //移除所有该子场景
				jq("input[name='mainScene'][value="+mainSceneVal+"]").attr("checked",false);//设置对应的主场景未被选择
			}
		}
	}); 
</script>

<!-- 
<div id="main">
		主场景：
		<div id="mainSceneCon">
			<input name="mainScene" type="checkbox" value="1" />光电缆
			<input name="mainScene" type="checkbox" value="2" />杆路 
			<input name="mainScene" type="checkbox" value="3" />管道 
			<input name="mainScene" type="checkbox" value="4" />交接箱
		</div>
		<br /><br /><br />
		子场景：	
		<div id="childSceneCon"></div>
		
			<br />
			<!-- 子场景展示区 
		<div id="childSceneShow"></div>
</div> -->

<input name="a1" value="aaaa" />
<input name="b1" value="bbb" />
<input name="c1" value="ccc" />
<input name="d1" value="dd" />









<%@ include file="/common/footer_eoms.jsp"%>