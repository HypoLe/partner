package com.boco.eoms.partner.keymanagement.action;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanMainForm;
import com.boco.eoms.partner.keymanagement.form.KeyBorrowRecordForm;
import com.boco.eoms.partner.keymanagement.model.KeyBorrowRecord;
import com.boco.eoms.partner.keymanagement.service.IKeyManagementService;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: huangpeng
 * Date: 13-6-26
 * Time: 下午9:23
 * To change this template use File | Settings | File Templates.
 */
public class KeyManagementAction extends BaseAction {
    public ActionForward toAddKeyFrom(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");


        request.setAttribute("currentData",df.format(new Date()));

        return mapping.findForward("addkey");
    }
    public ActionForward findKeyBorrowRecordList(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception{
        IKeyManagementService keyManagementService = (IKeyManagementService) getBean("keyManagementService");
        Search search = new Search();

        String borrowTimeStart = request.getParameter("borrowTimeStart");//借时间
        String borrowTimeEnd   = request.getParameter("borrowTimeEnd");

        String remandTimeStart = request.getParameter("remandTimeStart");//归还时间
        String remandTimeEnd   = request.getParameter("remandTimeEnd");

        search = CommonUtils.getSqlFromRequestMap(request, search);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotEmpty(borrowTimeStart)){
            search.addFilterGreaterOrEqual("borrowTime",df.parse(borrowTimeStart));
        }
        if (StringUtils.isNotEmpty(borrowTimeEnd)){
            search.addFilterLessOrEqual("borrowTime", df.parse(borrowTimeEnd));
        }

        if (StringUtils.isNotEmpty(remandTimeStart)){
            search.addFilterGreaterOrEqual("remandTime",df.parse(remandTimeStart));
        }
        if (StringUtils.isNotEmpty(remandTimeEnd)){
            search.addFilterLessOrEqual("remandTime",df.parse(remandTimeEnd));
        }

        search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);

        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
        search.setMaxResults(CommonConstants.PAGE_SIZE);

        SearchResult<KeyBorrowRecord> searchResult = keyManagementService.searchAndCount(search);

        request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
        request.setAttribute("list", searchResult.getResult());
        request.setAttribute("size", searchResult.getTotalCount());


        return mapping.findForward("keyBorrowRecordList");
    }

    public ActionForward saveKeyBorrowRecord(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception{
        IKeyManagementService keyManagementService = (IKeyManagementService) getBean("keyManagementService");
        KeyBorrowRecord keyBorrowRecord = new KeyBorrowRecord();
        KeyBorrowRecordForm keyBorrowRecordForm = (KeyBorrowRecordForm)form;
        BeanUtils.copyProperties(keyBorrowRecord, keyBorrowRecordForm);

        keyBorrowRecord.setKeyStatus(123060101);

        keyManagementService.save(keyBorrowRecord);


        return mapping.findForward("success");
    }

    /**
     * 跳转到状态信息修改页面
     * */

    @SuppressWarnings("unchecked")
    public ActionForward gotoUpdateKeyStatusPage(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request, HttpServletResponse response){

        return mapping.findForward("updateKeyStatusWindow");
    }
    public ActionForward updateKeyBorrowRecordStatus(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception{
        IKeyManagementService keyManagementService = (IKeyManagementService) getBean("keyManagementService");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String remand = request.getParameter("remand");
        String remandTime = request.getParameter("remandTime");
        KeyBorrowRecord keyBorrowRecord = keyManagementService.find(request.getParameter("id"));
        keyBorrowRecord.setKeyStatus(123060102);
        keyBorrowRecord.setRemand(remand);
        keyBorrowRecord.setRemandTime(df.parse(remandTime));
        keyManagementService.save(keyBorrowRecord);

        response.getWriter().print("true");

        return null;
    }
    public ActionForward deleteKeyBorrowRecordStatus(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request, HttpServletResponse response) throws Exception{
        IKeyManagementService keyManagementService = (IKeyManagementService) getBean("keyManagementService");
        String recordId = request.getParameter("id");
        KeyBorrowRecord keyBorrowRecord = keyManagementService.find(request.getParameter("id"));
        keyManagementService.removeById(recordId);
        response.getWriter().print("true");

        return null;
    }

}
