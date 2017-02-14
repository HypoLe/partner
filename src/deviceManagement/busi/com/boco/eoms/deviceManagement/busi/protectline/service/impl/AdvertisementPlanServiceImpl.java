package com.boco.eoms.deviceManagement.busi.protectline.service.impl;

import java.util.Date;
import java.util.List;

import com.boco.eoms.deviceManagement.busi.protectline.dao.AdvertisementPlanDAO;
import com.boco.eoms.deviceManagement.busi.protectline.model.AdvertisementPlan;
import com.boco.eoms.deviceManagement.busi.protectline.dao.ProteclineLinkDao;
import com.boco.eoms.deviceManagement.busi.protectline.model.ProteclineLink;
import com.boco.eoms.deviceManagement.busi.protectline.service.AdvertisementPlanService;
import com.boco.eoms.deviceManagement.busi.protectline.util.ConstructionMap;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class AdvertisementPlanServiceImpl implements AdvertisementPlanService {
	
	private AdvertisementPlanDAO advertisementPlanDAO;
	private ProteclineLinkDao proteclineLinkDao;
	

	public void setAdvertisementPlanDAO(AdvertisementPlanDAO advertisementPlanDAO) {
		this.advertisementPlanDAO = advertisementPlanDAO;
	}
	

	public void setProteclineLinkDao(ProteclineLinkDao proteclineLinkDao) {
		this.proteclineLinkDao = proteclineLinkDao;
	}


	public boolean add(AdvertisementPlan advertisementPlan) {
		return advertisementPlanDAO.save(advertisementPlan);
	}

	

	public boolean delete(String id) {
		return advertisementPlanDAO.removeById(id);
	}

	public void deltetSome(String[] ids) {
		// TODO Auto-generated method stub

	}

	public AdvertisementPlan findById(String id) {
		return advertisementPlanDAO.find(id);
	}

	public SearchResult<AdvertisementPlan> listAll(int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		return advertisementPlanDAO.searchAndCount(search);
	}
	
	public SearchResult<AdvertisementPlan> listAll(String userid, String city, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("createTime", true);
		if(!"".equals(city)) {
			search.addFilterEqual("city", city);
		}
		search.addFilterEqual("createUserId", userid);
		return advertisementPlanDAO.searchAndCount(search);
	}

	public boolean add(String createUserid, String reviewer, String city,
			String generalStone, String detectStone, String advertisement,
			String remarks) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void sumbit(String createUserid, String reviewer, String city, String generalStone, String detectStone, String advertisement,
			String remarks ) {
		AdvertisementPlan advertisementPlan = new AdvertisementPlan();
		advertisementPlan.setCreateUserId(createUserid);
		advertisementPlan.setReviewer(reviewer);
		advertisementPlan.setCity(city);
		advertisementPlan.setGeneralStone(Integer.valueOf(generalStone));
		advertisementPlan.setDetectStone(Integer.valueOf(detectStone));
		advertisementPlan.setAdvertisement(Integer.valueOf(advertisement));
		advertisementPlan.setRemarks(remarks);
		advertisementPlan.setNextOper(reviewer);
		advertisementPlan.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
		advertisementPlan.setStatus(ConstructionMap.STATUS_1);
		advertisementPlanDAO.save(advertisementPlan);
		ProteclineLink proteclineLink = new ProteclineLink();
		proteclineLink.setMainType("2");
		proteclineLink.setMainId(advertisementPlan.getId());
		proteclineLink.setActionSendUser(createUserid);
		proteclineLink.setActionReceiveUser(reviewer);
		proteclineLink.setActionStep("提交");
		proteclineLink.setActionHappenTime(CommonUtils.toEomsStandardDate(new Date()));
		proteclineLinkDao.save(proteclineLink);		
		
	}
	
	public void save(String createUserid, String reviewer, String city, String generalStone, String detectStone, String advertisement,
			String remarks ) {
		AdvertisementPlan advertisementPlan = new AdvertisementPlan();
		advertisementPlan.setCreateUserId(createUserid);
		advertisementPlan.setReviewer(reviewer);
		advertisementPlan.setCity(city);
		//System.out.println("=========g"+generalStone);
		//System.out.println("===========d"+detectStone);
		if(!"".equals(generalStone))
		advertisementPlan.setGeneralStone(Integer.valueOf(generalStone));
		if(!"".equals(detectStone))
		advertisementPlan.setDetectStone(Integer.valueOf(detectStone));
		if(!"".equals(advertisement))
		advertisementPlan.setAdvertisement(Integer.valueOf(advertisement));
		advertisementPlan.setRemarks(remarks);
		advertisementPlan.setNextOper(createUserid);
		advertisementPlan.setStatus(ConstructionMap.STATUS_0);
		advertisementPlan.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
		advertisementPlanDAO.save(advertisementPlan);
		ProteclineLink proteclineLink = new ProteclineLink();
		proteclineLink.setActionSendUser(createUserid);
		proteclineLink.setActionReceiveUser(createUserid);
		proteclineLink.setActionHappenTime(CommonUtils.toEomsStandardDate(new Date()));
		proteclineLinkDao.save(proteclineLink);	
	}


	public void review(String id, String suggestion, String allow) {
		AdvertisementPlan advertisementPlan = advertisementPlanDAO.find(id);
		ProteclineLink proteclineLink = new ProteclineLink();
		if("1".equals(allow)) {
			advertisementPlan.setStatus(ConstructionMap.STATUS_2);
			advertisementPlan.setPassTime(CommonUtils.toEomsStandardDate(new Date()));
			advertisementPlan.setNextOper("");
			proteclineLink.setActionHappenTime(CommonUtils.toEomsStandardDate(new Date()));
			proteclineLink.setActionSendUser(advertisementPlan.getReviewer());
			proteclineLink.setActionStep("通过");
		}else {
			advertisementPlan.setStatus(ConstructionMap.STATUS_3);
			advertisementPlan.setNextOper(advertisementPlan.getCreateUserId());
			proteclineLink.setActionHappenTime(CommonUtils.toEomsStandardDate(new Date()));
			proteclineLink.setActionSendUser(advertisementPlan.getReviewer());
			proteclineLink.setActionReceiveUser(advertisementPlan.getCreateUserId());
			proteclineLink.setActionStepExplain(suggestion);
			proteclineLink.setActionStep("驳回");
		}
		proteclineLink.setMainId(advertisementPlan.getId());
		proteclineLink.setMainType("2");
		advertisementPlanDAO.save(advertisementPlan);
		proteclineLinkDao.save(proteclineLink);
		
	}


	public SearchResult<AdvertisementPlan> listReview(String userid, String city, int first,
			int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("createTime", true);
		search.addFilterEqual("nextOper", userid);
		if(!"".equals(city));
		search.addFilterEqual("city", city);
		search.addFilterEqual("status", ConstructionMap.STATUS_1);
		return advertisementPlanDAO.searchAndCount(search);
	}


	public SearchResult<AdvertisementPlan> listReject(String userid, String city, int first,
			int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("createTime", true);
		search.addFilterEqual("nextOper", userid);
		if(!"".equals(city));
		search.addFilterEqual("city", city);
		search.addFilterEqual("status", ConstructionMap.STATUS_3);
		return advertisementPlanDAO.searchAndCount(search);
	}


	public List<ProteclineLink> listRejectHis(String id) {
		Search search = new Search();
		search.addFilterEqual("mainId", id);
		//search.addFilterEqual("actionStep", "驳回");
		search.addFilterIn("actionStep", "驳回", "通过");
		search.addFilterEqual("mainType", "2");
		search.addSort("actionHappenTime", true);
		return proteclineLinkDao.search(search);
	}


	public void resubmit(String id, String reviewer, String city,
			String generalStone, String detectStone, String advertisement,
			String remarks) {
		AdvertisementPlan advertisementPlan = advertisementPlanDAO.find(id);
		if(!"".equals(city))
		advertisementPlan.setCity(city);
		advertisementPlan.setGeneralStone(Integer.valueOf(generalStone));
		advertisementPlan.setDetectStone(Integer.valueOf(detectStone));
		advertisementPlan.setAdvertisement(Integer.valueOf(advertisement));
		advertisementPlan.setRemarks(remarks);
		if(!"".equals(reviewer)) {
			advertisementPlan.setReviewer(reviewer);
		}
		advertisementPlan.setNextOper(advertisementPlan.getReviewer());
		advertisementPlan.setStatus(ConstructionMap.STATUS_1);
		ProteclineLink proteclineLink = new ProteclineLink();
		proteclineLink.setMainType("2");
		proteclineLink.setMainId(advertisementPlan.getId());
		proteclineLink.setActionSendUser(advertisementPlan.getCreateUserId());
		proteclineLink.setActionReceiveUser(advertisementPlan.getReviewer());
		proteclineLink.setActionStep("提交");
		proteclineLink.setActionHappenTime(CommonUtils.toEomsStandardDate(new Date()));
		advertisementPlanDAO.save(advertisementPlan);
		proteclineLinkDao.save(proteclineLink);	
	}


	public SearchResult<AdvertisementPlan> listPass(String city, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		if(!"".equals(city));
		search.addFilterEqual("city", city);
		search.addFilterEqual("status", ConstructionMap.STATUS_2);
		return advertisementPlanDAO.searchAndCount(search);
	}


	public List<ProteclineLink> listOperHis(String id) {
		Search search = new Search();
		search.addFilterEqual("mainId", id);
		search.addFilterEqual("mainType", "2");
		search.addSort("actionHappenTime", true);
		return proteclineLinkDao.search(search);
	}
	
//	public List<ProteclineLink> listReviewHis(String id) {
//		Search search = new Search();
//		search.addFilterEqual("mainId", id);
//		search.addFilterEqual("mainType", "2");
//		search.addSort("actionHappenTime", true);
//		return proteclineLinkDao.search(search);
//	}
//	
	
	public List state() {
		return advertisementPlanDAO.state();
	}
	
	

}
