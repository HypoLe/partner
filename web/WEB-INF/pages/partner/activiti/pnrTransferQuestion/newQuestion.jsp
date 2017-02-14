<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
	//提示记住问题编号
	function showNumber(){
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		if(month<10){
		month= "0"+month;
		}
		var day =  date.getDate();
		if(day<10){
		day = "0"+day;
		}
		var hour = date.getHours();
		if(hour<10){
			hour = "0"+hour;
		}
		var minute = date.getMinutes();
		if(minute<10){
			minute = "0"+minute;
		}
		var second = date.getSeconds();
		if(second<10){
			second = "0"+second;
		}
		
		var number = "Q"+year+month+day+hour+minute+second;
		alert("请牢记该问题编号："+number);
		var num = document.getElementById("questionNumber");
		num.value=number;
	}
	//判断手机号格式
	function judgePhone(){
		var phone = document.getElementById("linkmanPhone").value;
		var filter=/^1[3|4|5|8][0-9]\d{8}$/;
		var errorPhone = document.getElementById("phone");
		if(phone==null || phone==""){
			errorPhone.innerHTML = "请输入手机号码";
			return false;
		}else if(!filter.test(phone)){
			errorPhone.innerHTML = "请输入正确的手机号码";
			return false;
		}else{
			errorPhone.innerHTML = "";
			return true;
		}
	}
	//联系人onblur
	function showLinkman(){
	var linkman = document.getElementById("linkman").value;
	var errorLinkman = document.getElementById("errorLinkman");
		if(linkman==null || linkman==""){
		errorLinkman.innerHTML = "请填写联系人";
		return false;
		}else{
		errorLinkman.innerHTML = "";
		return true;
		}
	}
	//判断问题详细是否为空
	function showCommonQuestion(){
		var commonQuestion = document.getElementById("commonQuestion").innerHTML;
		var errorSpan = document.getElementById("errorCommonQuestion");
		if(commonQuestion==null || commonQuestion==""){
			errorSpan.innerHTML = "请输入问题的详细信息";
			return false;
		}else{
			errorSpan.innerHTML = "";
			return true;
		}
	}
	function allTrue(){
		judgePhone();
		showLinkman();
		showCommonQuestion();
		var flag = judgePhone()&&showLinkman()&&showCommonQuestion();
		if(flag){
			showNumber();
			return true;
		}else{
			return false;
		}
	}
</script>
<html:form action="/pnrTransferQuestion.do?method=addNewQuestion"
	styleId="theform" onsubmit="return allTrue();">
	<input type="hidden" id="questionNumber" name="questionNumber" />
	<table class="formTable">

		<tr>
			<!-- 工单类型 -->
			<td class="label">
				联系人
			</td>
			<td colspan="3">
				<input type="text" name="linkman" id="linkman"
					onblur="showLinkman()" />
				<span id="errorLinkman" style="color: red"></span>
			</td>

		</tr>
		<tr>

			<td class="label">
				联系电话
			</td>
			<td colspan="3">
				<input type="text" name="linkmanPhone" id="linkmanPhone"
					onblur="judgePhone();" />
				<span id="phone" style="color: red"></span>
			</td>

		</tr>
		<tr>

			<td class="label">
				问题级别
			</td>
			<td colspan="3">
				<select name="questionLevel">
					<option value="0" selected>
						一般
					</option>
					<option value="1">
						加急
					</option>
				</select>
			</td>

		</tr>
		<tr class="label">
			<td>
				问题描述
			</td>
			<td colspan="3">
				<textarea rows="3" cols="80" name="commonQuestion" id="commonQuestion" onblur="showCommonQuestion()"></textarea><span id="errorCommonQuestion" style="color:red"></span>
			</td>
		</tr>

	</table>
	<!-- buttons -->
	<div class="form-btns">
		<html:submit value="提交" ></html:submit>
	</div>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>