<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/header_eoms_form.jsp" %>
<script type="text/javascript">
    var v;
    Ext.onReady(function () {
        v = new eoms.form.Validation({form: 'theform'});
        
        eoms.ComboBox.doCombo(document.getElementById("1012301"),'mainFaultNet')
    });
</script>

<div id="sheetform">
    <html:form action="/pnrStatistics.do?method=workOrderQuery" styleId="theform">
        <table class="formTable">
            <tr>
                <!-- 工单类型 -->
                <td class="label">
                    工单类型
                </td>
                <td colspan="3">
                   <input type="radio" id="1012301" value="1012301" name="taskType" onclick="javascript:eoms.ComboBox.doCombo(this,'mainFaultNet');" checked/>抢修工单&nbsp;&nbsp;
                   <input type="radio" id="1012306" value="1012306" name="taskType" onclick="javascript:eoms.ComboBox.doCombo(this,'mainFaultNet');" />预检预修工单&nbsp;&nbsp;
                </td>

            </tr>
            <tr>
                <!-- 工单标题 -->
                <td class="label">
                    工单标题
                </td>
                <td>
                    <input type="text" name="title" class="text max" id="title" maxLength="20"
                           value="" alt="allowBlank:true,maxLength:200,vtext:'请输入工单主题，最大长度为20个汉字！'"/>
                </td>
                <td class="label">工单子类型</td>
                <td class="content">
                    <eoms:comboBox name="mainFaultNet" id="mainFaultNet" defaultValue=""
                                   initDicId="" alt="allowBlank:true" styleClass="select"/>
                </td>
            </tr>

            <!--时间 -->
            <tr>
                <td class="label">开始时间</td>
                <td class="content">
                    <input type="text" class="text" name="sheetAcceptLimit" readonly="readonly"
                           id="sheetAcceptLimit" value="${startTime}"
                           onclick="popUpCalendar(this, this,null,null,null,null,-1)"
                           alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true"/>

                </td>
                <td class="label">结束时间</td>
                <td class="content">
                    <input type="text" class="text" name="sheetCompleteLimit" readonly="readonly"
                           id="sheetCompleteLimit" value="${endTime}"
                           onclick="popUpCalendar(this, this,null,null,null,null,-1)"
                           alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true"/>
                </td>
            </tr>
        </table>
        <!-- buttons -->
        <div class="form-btns">
            <html:submit styleClass="btn" property="method.save" styleId="method.save">
                查询
            </html:submit>
        </div>
    </html:form>
</div>
<%@ include file="/common/footer_eoms.jsp" %>