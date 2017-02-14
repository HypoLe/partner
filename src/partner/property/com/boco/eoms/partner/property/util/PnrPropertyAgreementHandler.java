package com.boco.eoms.partner.property.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.property.model.PnrElectricityBills;
import com.boco.eoms.partner.property.model.PnrElectricityBillsCount;
import com.boco.eoms.partner.property.model.PnrPropertyAgreement;
import com.boco.eoms.partner.property.model.PnrRentBills;
import com.boco.eoms.partner.property.model.PnrRentBillsCount;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsCountService;
import com.boco.eoms.partner.property.service.IPnrRentBillsCountService;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class PnrPropertyAgreementHandler {
	/**
	 *  生成支付计划列表
	 *fengguangping
	 * Aug 31, 2012-3:02:57 PM
	 * @param <T>
	 * @return
	 */
	public  static List  producePayPlan(PnrPropertyAgreement pnrPropertyAgreement) {
		String type=pnrPropertyAgreement.getAgreementType();
		String payCircle=pnrPropertyAgreement.getPayCycle();
		Date signDate=pnrPropertyAgreement.getSignDate();
		Date endDate=pnrPropertyAgreement.getEndDate();
		Calendar signCal=new GregorianCalendar();
		Calendar endCal=new GregorianCalendar();
		signCal.setTime(signDate);
		endCal.setTime(endDate);
		int startYear=signCal.get(Calendar.YEAR);
		int endYear=endCal.get(Calendar.YEAR);
		produceCountRecord(startYear,endYear,pnrPropertyAgreement);
		List list=new ArrayList();
		int payCircle2Int=payCircleStr2Int(payCircle);
		int i=0;
		if (payCircle2Int!=0) {
			//付款周期不为“其他”时
			while (endCal.after(signCal)&&type.equals("1250101")) {
				PnrElectricityBills eleBills=new PnrElectricityBills();
				eleBills.setRelatedAgreementName(StaticMethod.null2String(pnrPropertyAgreement.getAgreementName()));
				eleBills.setRelatedAgreementNo(StaticMethod.null2String(pnrPropertyAgreement.getAgreementNo()));
				eleBills.setRelatedAgreementType(StaticMethod.null2String(pnrPropertyAgreement.getAgreementType()));
				eleBills.setRelatedDistrict(StaticMethod.null2String(pnrPropertyAgreement.getDistirct()));
				eleBills.setRelatedParta(StaticMethod.null2String(pnrPropertyAgreement.getParta()));
				eleBills.setRelatedPartb(StaticMethod.null2String(pnrPropertyAgreement.getPartb()));
				eleBills.setPayCircle(StaticMethod.null2String(pnrPropertyAgreement.getPayCycle()));
				eleBills.setRefId(StaticMethod.null2String(pnrPropertyAgreement.getId()));
				eleBills.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
				signCal.add(Calendar.MONTH, payCircle2Int);
//				int maxDate=signCal.getActualMaximum(Calendar.DAY_OF_MONTH);//获取当前月的最大天数；
//				signCal.set(Calendar.DAY_OF_MONTH, maxDate);
				eleBills.setPlanPayDate(signCal.getTime());
				eleBills.setPayStatus(PnrPropertyAgreementConstant.UNPAID);
				eleBills.setProvince(StaticMethod.null2String(pnrPropertyAgreement.getProvince()));
				eleBills.setCity(StaticMethod.null2String(pnrPropertyAgreement.getCity()));
				eleBills.setRemindObject(StaticMethod.null2String(pnrPropertyAgreement.getRemindObject()));
				eleBills.setRelatedSite(StaticMethod.null2String(pnrPropertyAgreement.getSite()));
				eleBills.setManager("");
				eleBills.setPayWay("");
				eleBills.setAttachment("");
				eleBills.setSettlementTimeSectStart(new Date());
				eleBills.setSettlementTimeSectEnd(new Date());
				eleBills.setSettlementDate(new Date());
				eleBills.setRemark("");
				list.add(eleBills);
				i++;
			}
			while (endCal.after(signCal)&&type.equals("1250102")) {
				PnrRentBills rentBills=new PnrRentBills();
				rentBills.setRelatedAgreementName(StaticMethod.null2String(pnrPropertyAgreement.getAgreementName()));
				rentBills.setRelatedAgreementNo(StaticMethod.null2String(pnrPropertyAgreement.getAgreementNo()));
				rentBills.setRelatedAgreementType(StaticMethod.null2String(pnrPropertyAgreement.getAgreementType()));
				rentBills.setRelatedDistrict(StaticMethod.null2String(pnrPropertyAgreement.getDistirct()));
				rentBills.setRelatedParta(StaticMethod.null2String(pnrPropertyAgreement.getParta()));
				rentBills.setRelatedPartb(StaticMethod.null2String(pnrPropertyAgreement.getPartb()));
				rentBills.setPayCircle(StaticMethod.null2String(pnrPropertyAgreement.getPayCycle()));
				rentBills.setRefId(StaticMethod.null2String(pnrPropertyAgreement.getId()));
				rentBills.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
				signCal.add(Calendar.MONTH, payCircle2Int);
//				int maxDate=signCal.getActualMaximum(Calendar.DAY_OF_MONTH);//获取当前月的最大天数；
//				signCal.set(Calendar.DAY_OF_MONTH, maxDate);
				rentBills.setPlanPayDate(signCal.getTime());
				rentBills.setPayStatus(PnrPropertyAgreementConstant.UNPAID);
				rentBills.setProvince(StaticMethod.null2String(pnrPropertyAgreement.getProvince()));
				rentBills.setCity(StaticMethod.null2String(pnrPropertyAgreement.getCity()));
				rentBills.setRemark("租赁费用支付计划");
				rentBills.setRemindObject(StaticMethod.null2String(pnrPropertyAgreement.getRemindObject()));
				rentBills.setRelatedSite(StaticMethod.null2String(pnrPropertyAgreement.getSite()));
				rentBills.setSettlementDate(new Date());
				rentBills.setManager("");
				rentBills.setPayWay("");
				rentBills.setAttachment("");
				rentBills.setSettlementTimeSectStart(new Date());
				rentBills.setSettlementTimeSectEnd(new Date());
				list.add(rentBills);
				i++;
			}
		}
		return list;
	}
	
	private static void produceCountRecord(int startYear,int endYear, PnrPropertyAgreement pnrPropertyAgreement) {
		String agreementType=pnrPropertyAgreement.getAgreementType();
		if (agreementType.equals("1250101")) {
			IPnrElectricityBillsCountService pnrElectricityBillsCountService=(IPnrElectricityBillsCountService)ApplicationContextHolder.getInstance().getBean("pnrElectricityBillsCountService");
			for (int i = startYear; i <=endYear; i++) {
				PnrElectricityBillsCount pnrElectricityBillsCount=new PnrElectricityBillsCount();
				String timeYear=i+"";
				pnrElectricityBillsCount.setTimeYear(timeYear);
				pnrElectricityBillsCount.setRelatedAgreementid(StaticMethod.null2String(pnrPropertyAgreement.getId()));
				pnrElectricityBillsCount.setProvince(pnrPropertyAgreement.getProvince());
				pnrElectricityBillsCount.setCity(pnrPropertyAgreement.getCity());
				pnrElectricityBillsCount.setDistrict(pnrPropertyAgreement.getDistirct());
				pnrElectricityBillsCount.setSite(pnrPropertyAgreement.getSite());
				pnrElectricityBillsCountService.save(pnrElectricityBillsCount);
				
			}
		}
		if (agreementType.equals("1250102")) {
			IPnrRentBillsCountService pnrRentBillsCountService=(IPnrRentBillsCountService)ApplicationContextHolder
			.getInstance().getBean("pnrRentBillsCountService");
			for (int i = startYear; i <=endYear; i++) {
				PnrRentBillsCount pnrRentBillsCount=new PnrRentBillsCount();
				String timeYear=i+"";
				startYear++;
				pnrRentBillsCount.setTimeYear(timeYear);
				pnrRentBillsCount.setRelatedAgreementid(StaticMethod.null2String(pnrPropertyAgreement.getId()));
				pnrRentBillsCount.setProvince(pnrPropertyAgreement.getProvince());
				pnrRentBillsCount.setCity(pnrPropertyAgreement.getCity());
				pnrRentBillsCount.setDistrict(pnrPropertyAgreement.getDistirct());
				pnrRentBillsCount.setSite(pnrPropertyAgreement.getSite());
				pnrRentBillsCountService.save(pnrRentBillsCount);
			}
		}
		
	}

	/**
	 * 将支付周期转化为数值
	 *fengguangping
	 * Sep 3, 2012-11:26:53 AM
	 * @param payCircle
	 * @return
	 */
	public static int payCircleStr2Int(String payCircle){
		int payCircle2Int;
		if (payCircle.equals("1250201")) {
			//付款周期为月付
			payCircle2Int=1;
		}else if (payCircle.equals("1250202")) {
			//付款周期为季付
			payCircle2Int=3;
		}else if (payCircle.equals("1250203")) {
			//付款周期为半年付
			payCircle2Int=6;
		}else if (payCircle.equals("1250204")) {
			//付款周期为年付
			payCircle2Int=12;
		}else {
			payCircle2Int=0;//付款周期为其他时
		}
		return payCircle2Int;
	}
	
	public static String getParenetAreaId(String areaid){
		ITawSystemAreaManager areaManager= (ITawSystemAreaManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemAreaManager");
		TawSystemArea area=areaManager.getAreaByAreaId(areaid);
		String cityAreaid=StaticMethod.null2String(area.getParentAreaid());
		return cityAreaid;
	}
	/**
	 * 将前台的用户存用户userid和电话号码的转化为一个String类型；
	 *fengguangping
	 * Sep 19, 2012-11:26:29 AM
	 * @param remindObjects
	 * @return
	 */
	public static  String remindObejct2UserIdAndPhones(String[] remindObjects){
		StringBuffer remindUserIdsAndPhones=new StringBuffer();
		remindUserIdsAndPhones.append("userIds:").append(remindObjects[0]).append(";");
		if (remindObjects.length<2) {
			return remindUserIdsAndPhones.toString();
		}
		remindUserIdsAndPhones.append("phones:");
		for (int i = 1; i < remindObjects.length; i++) {
			remindUserIdsAndPhones.append(remindObjects[i]);
			if (i!=remindObjects.length-1) {
				remindUserIdsAndPhones.append(",");
			}
		}
		remindUserIdsAndPhones.append(";");
		return remindUserIdsAndPhones.toString();
	}
	/**
	 * 统一将提醒对象转化为phones
	 *fengguangping
	 * Sep 19, 2012-10:42:53 AM
	 * @param remindObjects
	 * @return
	 */
	public static String remindObject2Phones(String remindObjects){
		ITawSystemUserManager userMgr = (ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		String remindObjectsPhones="";
		String[] remindUsers=remindObjects.split(";");
		//remindUsers[0]+=",";
		String userStr=remindUsers[0].substring("userIds:".length());
		String[] userIds=userStr.split(",");
		for (int i = 0; i < userIds.length; i++) {
			String id=userIds[i];
			TawSystemUser tawSystemUser= userMgr.getUserByuserid(id);
			String phone=StaticMethod.null2String(tawSystemUser.getMobile());
			if (!"".equals(phone )) {
				if (!"".equals(remindObjectsPhones)) {
					remindObjectsPhones+=",";
				}
				remindObjectsPhones+=phone;
			}
		}
		if (remindUsers.length>1) {
			String phoneStr=remindUsers[1].substring("phones:".length());
			String[] phones=phoneStr.split(",");
			for (int i = 0; i < phones.length; i++) {
				if (!"".equals(phones[i])) {
					if (!"".equals(remindObjectsPhones)) {
						remindObjectsPhones+=",";
					}
					remindObjectsPhones+=phones[i];
				}
				}
			}
		return remindObjectsPhones;//去掉最后一个逗号
	}
	/**
	 * 统一将提醒对象中的userid分离出来得到userid和username；
	 *fengguangping
	 * Sep 19, 2012-10:42:53 AM
	 * @param remindObjects
	 * @return
	 */
	public static JSONArray remindObject2Userids(String remindObjects){
		ITawSystemUserManager userMgr = (ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		StringBuffer remindObjectsPhones=new StringBuffer();
		String[] remindUsers=remindObjects.split(";");
		String userStr=remindUsers[0].substring("userIds:".length());
		String[] userIds=userStr.split(",");
		JSONArray sendUserAndRoles = new JSONArray();
		for (int i = 0; i < userIds.length; i++) {
			String id=userIds[i];
			TawSystemUser tawSystemUser= userMgr.getUserByuserid(id);
			String username=StaticMethod.null2String(tawSystemUser.getUsername());
			String userid = StaticMethod.nullObject2String(tawSystemUser.getUserid());
			if (!"".equals(username) && !"".equals(id)) {
				JSONObject data = new JSONObject();
				try {
					data.put("id", userid);
					data.put("name", username);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				sendUserAndRoles.put(data);
			}
		}
		return sendUserAndRoles;
	}
	/**
	 * 统一将提醒对象中的phones分离出来得到单个的phone；
	 *fengguangping
	 * Sep 19, 2012-10:42:53 AM
	 * @param remindObjects
	 * @return
	 */
	public static List remindObject2phones(String remindObjects){
		StringBuffer remindObjectsPhones=new StringBuffer();
		String[] remindUsers=remindObjects.split(";");
		List<String> phonesList=new ArrayList<String>();
		if (remindUsers.length>1) {
			String phoneStr=remindUsers[1].substring("phones:".length());
			String[] phones=phoneStr.split(",");
			for (int i = 0; i < phones.length; i++) {
				phonesList.add(phones[i]);
			}
		}
		return phonesList;
	}
	/**
	 * 通过日期得到年的数字
	 */
	public static int date2YearInt(Date date){
		int year=0;
		Calendar cal=new GregorianCalendar();
		cal.setTime(date);
		year=cal.get(Calendar.YEAR);
		return year;
	}
	/**
	 * 通过日期得到月的数字
	 */
	public static int date2MonthInt(Date date){
		int month=0;
		Calendar cal=new GregorianCalendar();
		cal.setTime(date);
		month=cal.get(Calendar.MONTH);
		return month+1;
	}
	/**
	 * 通过日期得到天的数字
	 */
	public static int date2DayInt(Date date){
		int day=0;
		Calendar cal=new GregorianCalendar();
		cal.setTime(date);
		day=cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	/**
	 * 将日期与15比较，如果大于等于15返回true，则该次费用计入下一个月，如果小于15费用作为当月费用；
	 */
	public static boolean dayTimeGreatThan15(int day){
		int cut=day/15;
		if (cut<1) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 获取2个时间的月份差
	 */
	public static int  getMonthCut(Date startDate,Date endDate){
		int monthcut=0;
		Calendar sc=new GregorianCalendar();
		Calendar ec=new GregorianCalendar();
		sc.setTime(startDate);
		ec.setTime(endDate);
		return monthcut;
	}
	/**
	 * 将租赁费用记录账单的值填入统计表中
	 */
	public static void setPnrRentBillsValue2PnrRentBillsCount(PnrRentBills pnrRentBills){
		IPnrRentBillsCountService pnrRentBillsCountService=(IPnrRentBillsCountService)ApplicationContextHolder
		.getInstance().getBean("pnrRentBillsCountService");
		String payCircle=pnrRentBills.getPayCircle();
		Date startDate=pnrRentBills.getSettlementTimeSectStart();
		Date endDate=pnrRentBills.getSettlementTimeSectEnd();
		int startYear=date2YearInt(startDate);
		int endYear=date2YearInt(endDate);
		int startMonth=date2MonthInt(startDate);
		int endMonth=date2MonthInt(endDate);
		int monthCut=endMonth+(endYear-startYear)*12-startMonth;
		if (monthCut==0) {
			monthCut=1;
			endMonth+=1;
		}
		int startDay=date2DayInt(startDate);
		boolean greatThanFlag=dayTimeGreatThan15(startDay);
		int yearCut=0;//时间间隔年
		PnrRentBillsCount pnrRentBillsCount=null;
		for (int j =startMonth; j <endMonth+(endYear-startYear)*12; j++) {//按照月份循环
			int monthKey=1;//月份数值，初始化值
			if (greatThanFlag) {
				monthKey=j+1;
			}else {
				monthKey=j;
			}
			if (monthKey>12) {//修正值
				//大于12时填入下年
				yearCut=monthKey/12;
				monthKey=monthKey-12*yearCut;
				if (monthKey%12==0) {//修正值，当月份为24,36,48时出现的情况
					monthKey=12;//为24,36,48时直接为最大月份
					if (!greatThanFlag) {//如果当月大于15日需要填入下一年，如果小于15日，则填入本年；
						yearCut=yearCut-1;
					}
				}
			}
			Search search=new Search();
			search.addFilterLike("timeYear", startYear+yearCut+"");
			search.addFilterLike("relatedAgreementid", pnrRentBills.getRefId());
			SearchResult<PnrRentBillsCount> searchResult2=pnrRentBillsCountService.searchAndCount(search);
			List<PnrRentBillsCount> list2=searchResult2.getResult();
			if(list2!=null&&list2.size()>0){
				pnrRentBillsCount=list2.get(0);
			}else {
				pnrRentBillsCount=new PnrRentBillsCount();
				pnrRentBillsCount.setTimeYear(startYear+yearCut+"");
				pnrRentBillsCount.setProvince(StaticMethod.null2String(pnrRentBills.getProvince()));
				pnrRentBillsCount.setCity(StaticMethod.null2String(pnrRentBills.getCity()));
				pnrRentBillsCount.setDistrict(StaticMethod.null2String(pnrRentBills.getRelatedDistrict()));
				pnrRentBillsCount.setRelatedAgreementid(StaticMethod.null2String(pnrRentBills.getRefId()));
				pnrRentBillsCount.setSite(StaticMethod.null2String(pnrRentBills.getRelatedSite()));
			}
			switch (monthKey) {
				case 1:   pnrRentBillsCount.setJanuaryBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 2:   pnrRentBillsCount.setFebruaryBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 3:   pnrRentBillsCount.setMarchBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 4:   pnrRentBillsCount.setAprilBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 5:   pnrRentBillsCount.setMayBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 6:   pnrRentBillsCount.setJuneBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 7:   pnrRentBillsCount.setJulyBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 8:   pnrRentBillsCount.setAugustBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 9:   pnrRentBillsCount.setSeptemberBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 10:   pnrRentBillsCount.setOctoberBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 11:   pnrRentBillsCount.setNovemberBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				case 12:   pnrRentBillsCount.setDecemberBills(pnrRentBills.getMustPayMoney()/monthCut);
				break;
				default:   pnrRentBillsCount.setTotalBills(getOneYearMustPayTotalBills(pnrRentBillsCount));
				break;
			}
		pnrRentBillsCount.setTotalBills(getOneYearMustPayTotalBills(pnrRentBillsCount));
		pnrRentBillsCount.setPaidTotalBills(getOneYearPaidTotalBills(pnrRentBillsCount,pnrRentBills,monthCut));
		pnrRentBillsCount.setUnpaidTotalBills(getOneYearUnpaidTotalBills(pnrRentBillsCount));
		pnrRentBillsCountService.save(pnrRentBillsCount);
		}
	}
	private static double getOneYearUnpaidTotalBills(	PnrRentBillsCount  pnrRentBillsCount) {
		double oneYearUnpaidTotalBills=0;
		oneYearUnpaidTotalBills=pnrRentBillsCount.getTotalBills()-pnrRentBillsCount.getPaidTotalBills();
		return oneYearUnpaidTotalBills;
	}

	private static double getOneYearPaidTotalBills(	PnrRentBillsCount  pnrRentBillsCount,PnrRentBills  pnrRentBills,int monthCut) {
		double oneYearPaidTotalBills=0;
		oneYearPaidTotalBills=pnrRentBillsCount.getPaidTotalBills()+pnrRentBills.getPaidMoney()/monthCut;
		return oneYearPaidTotalBills;
	}

	/**
	 * 将电费费用记录账单的值填入统计表中
	 */
	public static void setPnrElectricityBillsValue2PnrElectricityBillsCount(PnrElectricityBills pnrElectricityBills){
		IPnrElectricityBillsCountService pnrElectricityBillsCountService=(IPnrElectricityBillsCountService)ApplicationContextHolder
		.getInstance().getBean("pnrElectricityBillsCountService");
		Date startDate=pnrElectricityBills.getSettlementTimeSectStart();
		Date endDate=pnrElectricityBills.getSettlementTimeSectEnd();
		int startYear=date2YearInt(startDate);
		int endYear=date2YearInt(endDate);
		int startMonth=date2MonthInt(startDate);
		int endMonth=date2MonthInt(endDate);
		int monthCut=endMonth+(endYear-startYear)*12-startMonth;
		if (monthCut==0) {
			monthCut=1;
			endMonth+=1;
		}
		int startDay=date2DayInt(startDate);
		boolean greatThanFlag=dayTimeGreatThan15(startDay);
		int yearCut=0;//时间间隔年
		PnrElectricityBillsCount pnrElectricityBillsCount=null;
		for (int j =startMonth; j <endMonth+(endYear-startYear)*12; j++) {//按照月份循环
			int monthKey=1;//月份数值，初始化值
			if (greatThanFlag) {
				monthKey=j+1;
			}else {
				monthKey=j;
			}
			if (monthKey>12) {//修正值
				//大于12时填入下年
				yearCut=monthKey/12;
				monthKey=monthKey-12*yearCut;
				if (monthKey%12==0) {//修正值，当月份为24,36,48时出现的情况
					monthKey=12;//为24,36,48时直接为最大月份
					if (!greatThanFlag) {//如果当月大于15日需要填入下一年，如果小于15日，则填入本年；
						yearCut=yearCut-1;
					}
				}
			}
			Search search=new Search();
			search.addFilterLike("timeYear", startYear+yearCut+"");
			search.addFilterLike("relatedAgreementid", pnrElectricityBills.getRefId());
			SearchResult<PnrElectricityBillsCount> searchResult=pnrElectricityBillsCountService.searchAndCount(search);
			List<PnrElectricityBillsCount> list=searchResult.getResult();
			if(list!=null&&list.size()>0){
				pnrElectricityBillsCount=list.get(0);
			}else {
				pnrElectricityBillsCount=new PnrElectricityBillsCount();
				pnrElectricityBillsCount.setTimeYear(startYear+yearCut+"");
				pnrElectricityBillsCount.setProvince(StaticMethod.null2String(pnrElectricityBills.getProvince()));
				pnrElectricityBillsCount.setCity(StaticMethod.null2String(pnrElectricityBills.getCity()));
				pnrElectricityBillsCount.setDistrict(StaticMethod.null2String(pnrElectricityBills.getRelatedDistrict()));
				pnrElectricityBillsCount.setRelatedAgreementid(StaticMethod.null2String(pnrElectricityBills.getRefId()));
				pnrElectricityBillsCount.setSite(StaticMethod.null2String(pnrElectricityBills.getRelatedSite()));
			}
			switch (monthKey) {
			case 1:   pnrElectricityBillsCount.setJanuaryBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 2:   pnrElectricityBillsCount.setFebruaryBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 3:   pnrElectricityBillsCount.setMarchBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 4:   pnrElectricityBillsCount.setAprilBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 5:   pnrElectricityBillsCount.setMayBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 6:   pnrElectricityBillsCount.setJuneBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 7:   pnrElectricityBillsCount.setJulyBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 8:   pnrElectricityBillsCount.setAugustBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 9:   pnrElectricityBillsCount.setSeptemberBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 10:   pnrElectricityBillsCount.setOctoberBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 11:   pnrElectricityBillsCount.setNovemberBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			case 12:   pnrElectricityBillsCount.setDecemberBills(pnrElectricityBills.getMustPayMoney()/monthCut);
			break;
			default:   pnrElectricityBillsCount.setTotalBills(getOneYearTotal(pnrElectricityBillsCount));
			break;
			}
			pnrElectricityBillsCount.setTotalBills(getOneYearTotal(pnrElectricityBillsCount));
			pnrElectricityBillsCountService.save(pnrElectricityBillsCount);
		}
	}
	/**
	 * 一年的租金费用总和
	 *fengguangping
	 * Oct 8, 2012-2:10:35 PM
	 * @param count
	 * @return
	 */
	private static double getOneYearMustPayTotalBills(PnrRentBillsCount count) {
		double total=0;
		total=count.getJanuaryBills()+count.getFebruaryBills()+count.getMarchBills();
		total=count.getAprilBills()+count.getMayBills()+count.getJuneBills()+total;
		total=count.getJulyBills()+count.getAugustBills()+count.getSeptemberBills()+total;
		total=count.getOctoberBills()+count.getNovemberBills()+count.getDecemberBills()+total;
		return total;
	}
	/**
	 * 一年的电费费用总和
	 *fengguangping
	 * Oct 8, 2012-2:11:02 PM
	 * @param count
	 * @return
	 */
	private static double getOneYearTotal(PnrElectricityBillsCount count){
		double total=0;
		total=count.getJanuaryBills()+count.getFebruaryBills()+count.getMarchBills();
		total=count.getAprilBills()+count.getMayBills()+count.getJuneBills()+total;
		total=count.getJulyBills()+count.getAugustBills()+count.getSeptemberBills()+total;
		total=count.getOctoberBills()+count.getNovemberBills()+count.getDecemberBills()+total;
		return total;
	}
	
}
