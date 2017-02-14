package com.boco.eoms.deviceManagement.busi.protectline.service;



import java.util.List;

import com.boco.eoms.deviceManagement.busi.protectline.model.AdvertisementPlan;
import com.boco.eoms.deviceManagement.busi.protectline.model.ProteclineLink;
import com.googlecode.genericdao.search.SearchResult;

public interface AdvertisementPlanService {

	public boolean add(AdvertisementPlan advertisementPlan);
	
	public boolean add(String createUserid, String reviewer, String city, String generalStone, String detectStone, String advertisement, String remarks);

	public boolean delete(String id);

	public void deltetSome(String[] ids);

	//public List<AdvertisementPlan> find(String name, String segmentType, int first,
			//int max);

	//public SearchResult<CheckSegment> find2(String name, String segmentType,
	//		int first, int max);

	public AdvertisementPlan findById(String id);

	public SearchResult<AdvertisementPlan> listAll(int first, int max);

	public void sumbit(String createUserid, String reviewer, String city, String generalStone, String detectStone, String advertisement,
			String remarks );
	
	public void save(String createUserid, String reviewer, String city, String generalStone, String detectStone, String advertisement,
			String remarks );
	
	public void review(String id, String suggestion, String allow);
	
	public SearchResult<AdvertisementPlan> listReview(String userid, String city, int first, int max);
	
	public SearchResult<AdvertisementPlan> listReject(String userid, String city, int first, int max);
	
	public List<ProteclineLink> listRejectHis(String id);
	
	public void resubmit(String id, String reviewer, String city, String generalStone, String detectStone, String advertisement, String remarks);
	
	public SearchResult<AdvertisementPlan> listPass(String city, int first, int max);

	public List<ProteclineLink> listOperHis(String id);
	
	public SearchResult<AdvertisementPlan> listAll(String userid, String city, int first, int max);
	
	public List state();
	
	
}
