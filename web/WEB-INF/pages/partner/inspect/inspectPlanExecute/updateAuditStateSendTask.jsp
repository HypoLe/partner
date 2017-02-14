<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
    var roleTree;
    var v;
    Ext.onReady(function () {
        v = new eoms.form.Validation({form: 'taskOrderForm'});
    });

    function changeType1() {
        $('operateType').value = "send";
        $('distributionType').value = "distribution";
    }
    function changeType2() {
        $('operateType').value = "jump";
        $('distributionType').value = "distribution";
    }
</script>

<form action="${app}/partner/inspect/inspectPlanExecute.do?method=updateAuditStateSendTask" method="post"
      id="taskOrderForm" name="taskOrderForm">

    <input type="hidden" name="distributionType" id="distributionType" value=""/>
    <input type="hidden" name="operateType" id="operateType" value=""/>
    <input type="hidden" name="resId" id="resId" value="${resId}"/>
    <table class="formTable">
        <tr>
            <td class="label">工单主题*</td>
            <td colspan="3">
                <input type="text" name="title" class="text max" id="title" maxLength="20"
                       value="巡检隐患处理工单－${resName}" alt="allowBlank:false,maxLength:200,vtext:'请输入工单主题，最大长度为20个汉字！'"/>
            </td>
        </tr>

        <tr>
            <td class="label">操作人*</td>
            <td class="content">
                <eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao"/>
                <input type="hidden" name="initiator" value=${userId}>
            </td>

            <td class="label">操作人部门*</td>
            <td class="content"><eoms:id2nameDB id="${deptId}" beanId="tawSystemDeptDao"/></td>
        </tr>

    </table>

    <!-- 工单基本信息 -->
    <table id="sheet" class="formTable">
        <tr>
            <td class="label">工单子类型*</td>


            <td class="content">
                <input class="text" type="text" name="mainFaultNetName"
                       id="mainFaultNetName" value="巡检隐患处理"/>
                <input  name="mainFaultNet"
                       type="hidden" id="mainFaultNet" value="101110105"/>
            </td>


            <td class="label">站点*</td>
            <td class="content">
                <input class="text" type="text" name="mainResName"
                       id="mainResName" value="${resName}"/>
                <input type="hidden" name="mainResId" id="mainResId" value="${resId}"/>
            </td>
        </tr>
        <tr>
            <td class="label">计划开始时间*</td>
            <td class="content">
                <input type="text" class="text" name="sheetAcceptLimit" readonly="readonly"
                       id="sheetAcceptLimit" value=""
                       onclick="popUpCalendar(this, this,null,null,null,true,-1);"
                       alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:false"/>
            </td>
            <td class="label">计划结束时间*</td>
            <td class="content">
                <input type="text" class="text" name="sheetCompleteLimit" readonly="readonly"
                       id="sheetCompleteLimit" value=""
                       onclick="popUpCalendar(this, this)"
                       alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:false"/>
            </td>
        </tr>
        <tr>

            <td class="label">涉及专业*</td>
            <td class="content" colspan=3>
                <eoms:comboBox name="mainSpecialty" id="mainSpecialty" defaultValue="101010806"
                               initDicId="1010108" alt="allowBlank:false" multiple="true" styleClass="select"/>
            </td>
        </tr>
        <tr>
            <td class="label">
                工单内容*
            </td>
            <td class="content" colspan="3">
                <textarea class="textarea max" name="mainRemark"
                          id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入工单内容，最多输入 2000 字符'">${mainRemark}</textarea>
            </td>
        </tr>
    </table>

    <!--接收组-->
    <fieldset>
        <legend>
		 <span id="roleName1">
		 	接收组
		 </span>
        </legend>
        <div class="x-form-item" >
            <eoms:chooser id="sendObject1" panels="[ {text:'部门',dataUrl:'${app}/xtree.do?method=dept',rootId:'${country}'}]"  type="dept" flowId="1103" config="{returnJSON:true,showLeader:true}"
                          category="[{id:'candidateGroup',text:'接收',allowBlank:false,childType:'dept',limit:20,vtext:'请选择处理对象'}]"
                          data="[{id:'${deptId}',nodeType:'dept',categoryId:'candidateGroup'}]" />
        </div>
    </fieldset>
    <p/>
    <!-- buttons -->
    <div class="form-btns" id="btns" style="display:block">

        <html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
            生成
        </html:submit>

        <html:submit styleClass="btn" property="method.save" onclick="v.passing=true;javascript:changeType2();" styleId="method.save">
            跳过
        </html:submit>
    </div>

</form>
<%@ include file="/common/footer_eoms.jsp" %>