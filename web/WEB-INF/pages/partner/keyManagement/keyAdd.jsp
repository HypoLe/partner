<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker4.7.2/WdatePicker.js"></script>
<script type="text/javascript">
    Ext.onReady(function() {
        var v = new eoms.form.Validation({form:'resForm'});
    });

</script>
  <body>
 
   <table class="formTable">
	<caption>

	</caption>
  </table>
  <br/>
    <html:form action="keyManagement.do?method=saveKeyBorrowRecord" method="post" styleId="resForm">
   <%--<form action="keyManagement.do" method="post" >--%>
	<table class="formTable" id="sheet">
		<caption>
			<div class="header center">钥匙借用记录添加</div>
		</caption>
		<tr>
			<td class="label">基站名称&nbsp<font color='red'>*</font></td>
			<td class="content">
                <html:text property="btsName"
                           styleClass="text medium" name="btsName"
                           alt="allowBlank:false,vtext:'',maxLength:50" value=""  />
			</td>
			<td class="label">门禁卡号&nbsp<font color='red'>*</font></td>
			<td class="content">
                <html:text property="accessCardNum"
                           styleClass="text medium" name="accessCardNum"
                           alt="allowBlank:false,vtext:'',maxLength:50" value=""  />
			</td>
		</tr>

        <tr>
            <td class="label">事由&nbsp<font color='red'>*</font></td>
            <td class="content">
                <html:text property="subject"
                           styleClass="text medium" name="subject"
                           alt="allowBlank:false,vtext:''" value=""  />
            </td>
            <td class="label">借用日期&nbsp<font color='red'>*</font></td>
            <td class="content">
                <input class="Wdate text" type="text" value=""  name="borrowTime" id="borrowTime"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" />
            </td>
        </tr>
        <tr>
            <td class="label">借用人&nbsp<font color='red'>*</font></td>
            <td class="content">
                <html:text property="borrower"
                           styleClass="text medium" name="borrower"
                           alt="allowBlank:false,vtext:'',maxLength:50" value=""  />
            </td>
            <td class="label">借用人电话&nbsp<font color='red'>*</font></td>
            <td class="content">

                <html:text property="borrowerPhone"
                           styleClass="text medium" name="borrowerPhone"
                           alt="maxLength:50,re:/(^(\d{3,4}-)?\d{7,8})$|(1[0-9]{10})$|(\(\d{3,4}\)?\d{7,8})$/,re_vt:'请输入正确的电话号码'" value="" />
            </td>
        </tr>
        <tr>
            <td class="label">批准主管&nbsp<font color='red'>*</font></td>
            <td class="content">
                <html:text property="approver"
                           styleClass="text medium" name="approver"
                           alt="allowBlank:false,vtext:'',maxLength:50" value=""  />
            </td>
            <td class="label">主管电话&nbsp<font color='red'>*</font></td>
            <td class="content">
                <html:text property="approverPhone"
                           styleClass="text medium" name="approverPhone"
                           alt="maxLength:50,re:/(^(\d{3,4}-)?\d{7,8})$|(1[0-9]{10})$|(\(\d{3,4}\)?\d{7,8})$/,re_vt:'请输入正确的电话号码'" value="" />
            </td>
        </tr>



		<tr>
			<td class="label">备注  </td>
			<td colspan="3">
				<textarea rows="3" name="remark" style="width:98%" alt="maxLength:255" ></textarea>
			</td>
		</tr>
	</table>
	<br/>
	<input type="submit" class="btn" id="btn1" value="保存信息"/>
	<input type="reset" class="btn"  value="重置" />

       <script type="text/javascript">
           var jq=jQuery.noConflict();
           Date.prototype.Format = function (fmt) { //author: meizz
               var o = {
                   "M+": this.getMonth() + 1, //月份
                   "d+": this.getDate(), //日
                   "h+": this.getHours(), //小时
                   "m+": this.getMinutes(), //分
                   "s+": this.getSeconds(), //秒
                   "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                   "S": this.getMilliseconds() //毫秒
               };
               if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
               for (var k in o)
                   if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
               return fmt;
           }
           jq("#borrowTime").val(new Date().Format("yyyy-MM-dd"));
       </script>
       </html:form>
   <%--</form>--%>
    
<%@ include file="/common/footer_eoms.jsp"%>
