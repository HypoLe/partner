package com.boco.eoms.partner.property.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.message.service.impl.MsgServiceImpl;
import com.boco.eoms.partner.property.model.PnrElectricityBills;
import com.boco.eoms.partner.property.model.PnrPropertyAgreement;
import com.boco.eoms.partner.property.model.PnrRemindMsg;
import com.boco.eoms.partner.property.model.PnrRentBills;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsService;
import com.boco.eoms.partner.property.service.IPnrPropertyAgreementService;
import com.boco.eoms.partner.property.service.IPnrRemindMsgService;
import com.boco.eoms.partner.property.service.IPnrRentBillsService;

public class PnrPropertyAgreementPayTaskScheduler  implements Job{
	public void execute(JobExecutionContext context) throws JobExecutionException {
		BocoLog.info(this, 0, "合同到期任务提醒开始");
		//解决取时间提前8小时的夏令时问题
//		TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8"); 
//		TimeZone.setDefault(tz); 
		Calendar cal=new GregorianCalendar();
		cal.setTime(new Date());
		 IPnrPropertyAgreementService pnrPropertyAgreementService=(IPnrPropertyAgreementService)ApplicationContextHolder
		.getInstance().getBean("pnrPropertyAgreementService");
		 IPnrRemindMsgService pnrRemindMsgService=(IPnrRemindMsgService)ApplicationContextHolder.getInstance().getBean("pnrRemindMsgService");
		 /**合同到期提醒*/
		 List<PnrPropertyAgreement> list=pnrPropertyAgreementService.findAll();
		 for (PnrPropertyAgreement agreement : list) {
			 Calendar endCal=new GregorianCalendar();
			Date endDate=StaticMethod.nullObject2Timestamp(agreement.getEndDate());
			endCal.setTime(endDate);//结束日期转化为一个calendar类型
			//先判断合同是否为有效合同,避免在修改合同时发生状态切换；
			if (cal.after(endCal)) {//当前时间大于截止时间说明合同已经失效;
				agreement.setAgreementStatus(PnrPropertyAgreementConstant.AGREEMENTUNEFFECTED);
			}else {
				agreement.setAgreementStatus(PnrPropertyAgreementConstant.AGREEMENTEFFECTED);
			}
			//产生提醒
			endCal.add(Calendar.MONTH, -3);
			String status=StaticMethod.null2String(agreement.getAgreementStatus());//合同提前三个月到期提醒&!agreement.getAgreementStatus().equals(PnrPropertyAgreementConstant.AGREEMENTREMIND)
			String expireRemind=StaticMethod.null2String(agreement.getExpireRemind());
			boolean flag=cal.after(endCal)&&status.equals(PnrPropertyAgreementConstant.AGREEMENTEFFECTED)
			&&!expireRemind.equals(PnrPropertyAgreementConstant.AGREEMENTREMIND);
			if (flag) {
				agreement.setExpireRemind(PnrPropertyAgreementConstant.AGREEMENTREMIND);//设置合同需要到期提醒
				PnrRemindMsg pnrRemindMsg=new PnrRemindMsg();
				StringBuffer remindMsg=new StringBuffer();
				remindMsg
				.append(agreement.getSite())
				.append("(站点)物业合同")
				.append("将于")
				.append(new DateTime(StaticMethod.nullObject2Timestamp(agreement.getEndDate())).toString("yyyy-MM-dd"))
				.append("之前到期，请提前关注此合同的重新签订。");
				pnrRemindMsg.setContent(remindMsg.toString());
				Calendar sendCal=new GregorianCalendar();
				sendCal.setTime(new Date());
				sendCal.add(Calendar.HOUR, 10);//凌晨执行，将发送的时间推迟到轮询执行后10个小时；
				pnrRemindMsg.setSendTime(sendCal.getTime());
				agreement.setMsgSendDate(sendCal.getTime());
				pnrRemindMsg.setCreatTime(CommonUtils.toEomsStandardDate(new Date()));
				pnrRemindMsg.setRemark("合同到期提醒");
				pnrRemindMsg.setIsRead("0");
				pnrRemindMsg.setDeletedFlag("0");
				pnrRemindMsg.setReadUser("");
				pnrRemindMsg.setType("agreement");//为合同到期提醒；
				pnrRemindMsgService.save(pnrRemindMsg);
				pnrRemindMsg.setRefId(agreement.getId());
				pnrRemindMsg.setRemindObject(agreement.getRemindObject());
				pnrRemindMsg.setReadTime("");
				pnrRemindMsgService.save(pnrRemindMsg);
				String phones=PnrPropertyAgreementHandler.remindObject2Phones(agreement.getRemindObject());
				MsgServiceImpl msg=new MsgServiceImpl();//发短信提示service
				msg.sendMsg4Mobiles("2c9e9de939dc93dc0139dd9cac3400be", remindMsg.toString(), pnrRemindMsg.getId(),phones, CommonUtils.toEomsStandardDate(sendCal.getTime()));//发送
			}
			pnrPropertyAgreementService.save(agreement);
		 }
		 BocoLog.info(this, 0, "合同到期任务提醒结束");
		 
		BocoLog.info(this, 0, "支付计划到期提醒开始");
		IPnrElectricityBillsService pnrElectricityBillsService=(IPnrElectricityBillsService)ApplicationContextHolder
		.getInstance().getBean("pnrElectricityBillsService");
		IPnrRentBillsService pnrRentBillsService=(IPnrRentBillsService)ApplicationContextHolder
		.getInstance().getBean("pnrRentBillsService");
		/**租金支付提醒*/
		List<PnrRentBills>rentBillsList=pnrRentBillsService.findAll();
		for (PnrRentBills b : rentBillsList) {
			if (b.getPlanPayDate()!=null&&!"".equals(b.getPlanPayDate())) {
				Date payDate=StaticMethod.nullObject2Timestamp(b.getPlanPayDate());
				Calendar payCal=new GregorianCalendar();
				payCal.setTime(payDate);
				payCal.add(Calendar.DATE, -15);
				if(cal.after(payCal)&&StaticMethod.null2String(b.getPayStatus()).equals(PnrPropertyAgreementConstant.UNPAID)){
					b.setPayStatus(PnrPropertyAgreementConstant.WAITPAID);//状态修改为待支付
					PnrRemindMsg pnrRemindMsg=new PnrRemindMsg();
					StringBuffer remindMsg=new StringBuffer();
					remindMsg
					.append(StaticMethod.null2String(b.getRelatedSite()))
					.append("(物业点)租赁合同应于")
					.append(new DateTime(StaticMethod.nullObject2Timestamp(b.getPlanPayDate())).toString("yyyy-MM-dd"))
					.append("之前支付，请提前关注此付款信息。");
					pnrRemindMsg.setContent(remindMsg.toString());
					Calendar sendCal=new GregorianCalendar();
					sendCal.setTime(new Date());
					sendCal.add(Calendar.HOUR, 10);//凌晨执行，将发送的时间推迟到轮询执行后10个小时；
					pnrRemindMsg.setSendTime(sendCal.getTime());
					pnrRemindMsg.setCreatTime(CommonUtils.toEomsStandardDate(new Date()));
					pnrRemindMsg.setRemark("支付提醒");
					pnrRemindMsg.setIsRead("0");
					pnrRemindMsg.setDeletedFlag("0");
					pnrRemindMsg.setReadUser("");
					pnrRemindMsg.setType("rentBills");//为租赁费用支付提醒；
					pnrRentBillsService.save(b);
					pnrRemindMsg.setRefId(b.getId());
					pnrRemindMsg.setRemindObject(b.getRemindObject());
					pnrRemindMsg.setReadTime("");
					pnrRemindMsgService.save(pnrRemindMsg);
					String phones=PnrPropertyAgreementHandler.remindObject2Phones(b.getRemindObject());//接收短信的人员
					MsgServiceImpl msg=new MsgServiceImpl();//发短信提示service
					msg.sendMsg4Mobiles("2c9e9de939dc93dc0139dd9cac3400be", remindMsg.toString(), pnrRemindMsg.getId(), phones, CommonUtils.toEomsStandardDate(sendCal.getTime()));//发送
				}
			}
		}
		/**电费支付提醒*/
		List<PnrElectricityBills>electricityBillsList=pnrElectricityBillsService.findAll();
		for (PnrElectricityBills b : electricityBillsList) {
			if (b.getPlanPayDate()!=null&&!"".equals(b.getPlanPayDate())) {
				Date payDate=StaticMethod.nullObject2Timestamp(b.getPlanPayDate());
				Calendar payCal=new GregorianCalendar();
				payCal.setTime(payDate);
				payCal.add(Calendar.DATE, -15);
				if(cal.after(payCal)&&StaticMethod.null2String(b.getPayStatus()).equals(PnrPropertyAgreementConstant.UNPAID)){
					b.setPayStatus(PnrPropertyAgreementConstant.WAITPAID);//状态修改为待支付
					PnrRemindMsg pnrRemindMsg=new PnrRemindMsg();
					StringBuffer remindMsg=new StringBuffer();
					remindMsg
					.append(StaticMethod.null2String(b.getRelatedSite()))
					.append("(物业点)电费合同应于")
					.append(new DateTime(StaticMethod.nullObject2Timestamp(b.getPlanPayDate())).toString("yyyy-MM-dd"))
					.append("之前支付，请提前关注此付款信息。");
					pnrRemindMsg.setContent(remindMsg.toString());
					Calendar sendCal=new GregorianCalendar();
					sendCal.setTime(new Date());
					sendCal.add(Calendar.HOUR, 10);//凌晨执行，将发送的时间推迟到轮询执行后10个小时；
					pnrRemindMsg.setSendTime(sendCal.getTime());
					pnrRemindMsg.setCreatTime(CommonUtils.toEomsStandardDate(new Date()));
					pnrRemindMsg.setRemark("支付提醒");
					pnrRemindMsg.setIsRead("0");
					pnrRemindMsg.setDeletedFlag("0");
					pnrRemindMsg.setReadUser("");
					pnrRemindMsg.setType("electricityBills");//为电费支付提醒；
					pnrRemindMsgService.save(pnrRemindMsg);
					pnrElectricityBillsService.save(b);
					pnrRemindMsg.setRefId(b.getId());
					pnrRemindMsg.setRemindObject(b.getRemindObject());
					pnrRemindMsg.setReadTime("");
					pnrRemindMsgService.save(pnrRemindMsg);
//				String phones=PnrPropertyAgreementHandler.remindObject2Phones(b.getRemindObject());//接收短信的人员
//				MsgServiceImpl msg=new MsgServiceImpl();//发短信提示service
//				msg.sendMsg4Mobiles("2c9e9de939dc93dc0139dd9cac3400be", remindMsg.toString(), pnrRemindMsg.getId(), phones, CommonUtils.toEomsStandardDate(sendCal.getTime()));//发送
				}
			}
		}
		BocoLog.info(this, 0, "支付计划到期提醒结束");
	}
}
