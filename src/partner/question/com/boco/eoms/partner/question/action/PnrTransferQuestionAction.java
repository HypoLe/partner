package com.boco.eoms.partner.question.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.question.model.PnrTransferQuestion;
import com.boco.eoms.partner.question.model.PnrTransferQuestionState;
import com.boco.eoms.partner.question.service.IPnrTransferQuestionService;
import com.boco.eoms.partner.question.service.IPnrTransferQuestionStateService;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;

public class PnrTransferQuestionAction extends SheetAction{
	
	/**
	 * 察看常见问题页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward commonQuestion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonUtils.getCompetenceLimit(request);
		return mapping.findForward("commonQuestion");
	}
	/**
	 * 跳转到添加问题页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newQuestion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		CommonUtils.getCompetenceLimit(request);
		return mapping.findForward("newQuestion");
	}
	/**
	 * 问题跟踪页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectQuestion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		CommonUtils.getCompetenceLimit(request);
		return mapping.findForward("selectQuestion");
	}
	/**
	 * 问题管理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward controlQuestion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		CommonUtils.getCompetenceLimit(request);
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		PnrTransferQuestion pnrTransferQuestion = new PnrTransferQuestion();
		setCheckQuestionNumberPnrTransferQuestion(request,pnrTransferQuestion);
		IPnrTransferQuestionService ipnrTransferQuestionService = (IPnrTransferQuestionService)getBean("pnrTransferQuestionService");
		List<PnrTransferQuestion> list = ipnrTransferQuestionService.getQuestionList(pnrTransferQuestion,firstResult * pageSize, endResult * pageSize);
		int total = 0;
		
		if (list!=null){
			total = list.size();
		}
		
		total = 80;
		request.setAttribute("taskList", list);
		//request.setAttribute("total", total); 
		request.setAttribute("pagesize", pageSize);
		
		return mapping.findForward("controlQuestion");
	}
	/**
	 * 存入问题
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addNewQuestion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		//存入提交问题
		PnrTransferQuestion pnrTransferQuestion = new PnrTransferQuestion();
		setTransferOfficeByRequest(request,pnrTransferQuestion);
		IPnrTransferQuestionService ipnrTransferQuestionService = (IPnrTransferQuestionService)getBean("pnrTransferQuestionService");
		ipnrTransferQuestionService.save(pnrTransferQuestion);
		//存入问题状态
		PnrTransferQuestionState pnrTransferQuestionState = new PnrTransferQuestionState();
		pnrTransferQuestionState.setChangeTime(pnrTransferQuestion.getRaiseTime());
		pnrTransferQuestionState.setQuesitonState(pnrTransferQuestion.getQuestionState());
		pnrTransferQuestionState.setQuestionNumber(pnrTransferQuestion.getQuestionNumber());
		IPnrTransferQuestionStateService ipnrTransferQuestionStateService=(IPnrTransferQuestionStateService)getBean("pnrQuestionStateService");
		ipnrTransferQuestionStateService.save(pnrTransferQuestionState);
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 找回问题编号
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkQuestionNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CommonUtils.getCompetenceLimit(request);
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		PnrTransferQuestion pnrTransferQuestion = new PnrTransferQuestion();
		setCheckQuestionNumberPnrTransferQuestion(request,pnrTransferQuestion);
		IPnrTransferQuestionService ipnrTransferQuestionService = (IPnrTransferQuestionService)getBean("pnrTransferQuestionService");
		List<PnrTransferQuestion> list = ipnrTransferQuestionService.getQuestionNumber(pnrTransferQuestion,firstResult * pageSize, endResult * pageSize);
		int total = 0;
		if (list!=null){
			total = list.size();
		}
		request.setAttribute("taskList", list);
		request.setAttribute("total", total); 
		request.setAttribute("pagesize", pageSize);
		return mapping.findForward("selectQuestion");
	}
	/**
	 * 根据问题编号查询问题进度
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getQuestionByQuestionNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrTransferQuestion pnrTransferQuestion = new PnrTransferQuestion();
		String questionNumber = request.getParameter("questionNumber");
		IPnrTransferQuestionService ipnrTransferQuestionService = (IPnrTransferQuestionService)getBean("pnrTransferQuestionService");
		pnrTransferQuestion = ipnrTransferQuestionService.getQuestionInformationByQuestionNumber(questionNumber);
		IPnrTransferQuestionStateService  questionStateService = (IPnrTransferQuestionStateService)getBean("pnrQuestionStateService");
		List<PnrTransferQuestionState> questionStateList = questionStateService.selectPnrTransferQuestionStateByQuestionNumber(questionNumber);
		request.setAttribute("onePnrTransferQuestion", pnrTransferQuestion);
		request.setAttribute("questionStateList", questionStateList);
		return mapping.findForward("selectQuestion");
	}
	
	/**
	 * 查询问题详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMoreQuestionInformation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		String questionNumber = request.getParameter("number");
		CommonUtils.getCompetenceLimit(request);
		IPnrTransferQuestionService ipnrTransferQuestionService = (IPnrTransferQuestionService)getBean("pnrTransferQuestionService");
		PnrTransferQuestion pnrTransferQuestion = new PnrTransferQuestion();
		pnrTransferQuestion = ipnrTransferQuestionService.getOneQuestion(questionNumber);
		request.setAttribute("pnrTransferQuestion", pnrTransferQuestion);
		
		return mapping.findForward("oneQuestionInformation");
	}
	/**
	 * 管理员修改问题信息和改变问题状态
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeQuestionInformation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CommonUtils.getCompetenceLimit(request);
		//获取修改的属性
		String questionNumber = request.getParameter("questionNumber");
		String questionState = request.getParameter("questionState");
		String questionLevel = request.getParameter("questionLevel");
		//如果修改问题状态，将修改信息和时间存入数据库中
		IPnrTransferQuestionStateService  questionStateService = (IPnrTransferQuestionStateService)getBean("pnrQuestionStateService");
		PnrTransferQuestionState pnrTransferQuetionState = new PnrTransferQuestionState();
		if(questionState!=null&&!"".equals(questionState)&&!"请选择".equals(questionState)){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		pnrTransferQuetionState.setQuesitonState(questionState);
		pnrTransferQuetionState.setQuestionNumber(questionNumber);
		pnrTransferQuetionState.setChangeTime(sdf.parse(sdf.format(new Date())));
		questionStateService.save(pnrTransferQuetionState);
		}
		//根据问题编号修改问题信息
		IPnrTransferQuestionService ipnrTransferQuestionService = (IPnrTransferQuestionService)getBean("pnrTransferQuestionService");
		boolean flag = ipnrTransferQuestionService.ChangeQuestionInformation(questionNumber, questionState, questionLevel);
		if(flag){
			return mapping.findForward("showSuccess");
		}else{
			
		}
		return mapping.findForward("showError");
	}
	
	/**
	 * 根据request得到问题反馈对象
	 * 
	 * @throws ParseException
	 */

	private void setTransferOfficeByRequest(
			HttpServletRequest request,PnrTransferQuestion pnrTransferQuestion) throws ParseException {
	

		// 问题编号
		String questionNumber = StaticMethod.nullObject2String(request
				.getParameter("questionNumber"));
		// 联系人
		String linkman = StaticMethod.nullObject2String(request
				.getParameter("linkman"));
		//联系电话
		String linkmanPhone = StaticMethod.nullObject2String(request
				.getParameter("linkmanPhone"));
		//问题级别 ：0 一般；1 加急 
		String questionLevel = StaticMethod.nullObject2String(request
				.getParameter("questionLevel"));
		//问题详情
		String commonQuestion = StaticMethod.nullObject2String(request
				.getParameter("commonQuestion"));
		//提问人
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String questioner = sessionForm.getUserid();
		//提问时间 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date raiseTime = sdf.parse(sdf.format(new Date()));
		
		//将值存入问题反馈对象中
		pnrTransferQuestion.setQuestioner(questioner);
		pnrTransferQuestion.setQuestionLevel(questionLevel);
		pnrTransferQuestion.setLinkman(linkman);
		pnrTransferQuestion.setLinkmanPhone(linkmanPhone);
		pnrTransferQuestion.setQuestionContent(commonQuestion);
		pnrTransferQuestion.setQuestionNumber(questionNumber);
		pnrTransferQuestion.setRaiseTime(raiseTime);
		//默认存入等待评估状态
		pnrTransferQuestion.setQuestionState("101230301");
	}
	/**
	 *  将找寻问题编号的信息存入实体类对象中
	 * @param request
	 * @param pnrTransferQuestion
	 */
	public void setCheckQuestionNumberPnrTransferQuestion(HttpServletRequest request,PnrTransferQuestion pnrTransferQuestion){
		String linkman = request.getParameter("linkman");
		String linkmanPhone = request.getParameter("linkmanPhone");
		String commonQuestion = request.getParameter("commonQuestion");
		String raiseTime = request.getParameter("raiseTime");
		String questionState = request.getParameter("questionState");
		String questionLevel = request.getParameter("questionLevel");
		
		pnrTransferQuestion.setLinkman(linkman);
		pnrTransferQuestion.setLinkmanPhone(linkmanPhone);
		pnrTransferQuestion.setQuestionContent(commonQuestion);
		pnrTransferQuestion.setUseTime(raiseTime);
		pnrTransferQuestion.setQuestionState(questionState);
		pnrTransferQuestion.setQuestionLevel(questionLevel);
	}

}
