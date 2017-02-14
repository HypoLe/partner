package com.boco.eoms.parter.baseinfo.fee.test.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeePlanDao;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeePlan;
import com.boco.eoms.partner.baseinfo.util.StatisticMethod;


public class PartnerFeePlanDaoTest extends ConsoleTestCase {

	//付款计划
	private PartnerFeePlanDao partnerFeePlanDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
		this.partnerFeePlanDao = (PartnerFeePlanDao) applicationContext.getBean("partnerFeePlanDao");
	}
	
	public void testGetPartnerFeePlans() {
		PartnerFeePlan partnerFeePlan = new PartnerFeePlan();
		partnerFeePlan.setCollectUnit("1");
		partnerFeePlan.setCompactName("1");
		partnerFeePlan.setCompactNo("1");
		partnerFeePlan.setCreateDate(new Date());
		partnerFeePlan.setCreateDept("1");
		partnerFeePlan.setCreateUser("admin");
		partnerFeePlan.setFactPayDate(new Date());
		partnerFeePlan.setFactPayFee("1");
		partnerFeePlan.setName("1");
		partnerFeePlan.setPayStage("1");
		partnerFeePlan.setPayStatus("1");
		partnerFeePlan.setPayUnit("1");
		partnerFeePlan.setPayUser("1");
		partnerFeePlan.setPayWay("1");
		partnerFeePlan.setPlanPayDate(new Date());
		partnerFeePlan.setPlanPayFee("1");
		partnerFeePlan.setRemark("1123");
		partnerFeePlanDao.savePartnerFeePlan(partnerFeePlan);
		List result = partnerFeePlanDao.getPartnerFeePlans();
		assertNotNull(result);
		for(Iterator it = result.iterator();it.hasNext();){
			PartnerFeePlan pfp = (PartnerFeePlan)it.next();
			assertNotNull(pfp.getId());
			assertEquals(pfp.getCollectUnit(),"1");
			assertEquals(pfp.getCompactName(),"1");
		}
	}

	public void testGetPartnerFeePlan() {
		PartnerFeePlan partnerFeePlan = new PartnerFeePlan();
		partnerFeePlan.setCollectUnit("1");
		partnerFeePlan.setCompactName("1");
		partnerFeePlan.setCompactNo("1");
		partnerFeePlan.setCreateDate(new Date());
		partnerFeePlan.setCreateDept("1");
		partnerFeePlan.setCreateUser("admin");
		partnerFeePlan.setFactPayDate(new Date());
		partnerFeePlan.setFactPayFee("1");
		partnerFeePlan.setName("1");
		partnerFeePlan.setPayStage("1");
		partnerFeePlan.setPayStatus("1");
		partnerFeePlan.setPayUnit("1");
		partnerFeePlan.setPayUser("1");
		partnerFeePlan.setPayWay("1");
		partnerFeePlan.setPlanPayDate(new Date());
		partnerFeePlan.setPlanPayFee("1");
		partnerFeePlan.setRemark("1123");
		partnerFeePlanDao.savePartnerFeePlan(partnerFeePlan);
		String id = partnerFeePlan.getId();
		PartnerFeePlan pfp = partnerFeePlanDao.getPartnerFeePlan(id);
		assertNotNull(pfp);
		assertEquals(id,pfp.getId());
	}
	

	public void testSavePartnerFeePlan() {
		PartnerFeePlan partnerFeePlan = new PartnerFeePlan();
		partnerFeePlan.setCollectUnit("1");
		partnerFeePlan.setCompactName("1");
		partnerFeePlan.setCompactNo("1");
		partnerFeePlan.setCreateDate(new Date());
		partnerFeePlan.setCreateDept("1");
		partnerFeePlan.setCreateUser("admin");
		partnerFeePlan.setFactPayDate(new Date());
		partnerFeePlan.setFactPayFee("1");
		partnerFeePlan.setName("1");
		partnerFeePlan.setPayStage("1");
		partnerFeePlan.setPayStatus("1");
		partnerFeePlan.setPayUnit("1");
		partnerFeePlan.setPayUser("1");
		partnerFeePlan.setPayWay("1");
		partnerFeePlan.setPlanPayDate(new Date());
		partnerFeePlan.setPlanPayFee("1");
		partnerFeePlan.setRemark("1123");
		partnerFeePlanDao.savePartnerFeePlan(partnerFeePlan);
		assertNotNull(partnerFeePlan.getId());
	}

	public void testRemovePartnerFeePlan() {
		PartnerFeePlan partnerFeePlan = new PartnerFeePlan();
		partnerFeePlan.setCollectUnit("1");
		partnerFeePlan.setCompactName("1");
		partnerFeePlan.setCompactNo("1");
		partnerFeePlan.setCreateDate(new Date());
		partnerFeePlan.setCreateDept("1");
		partnerFeePlan.setCreateUser("admin");
		partnerFeePlan.setFactPayDate(new Date());
		partnerFeePlan.setFactPayFee("1");
		partnerFeePlan.setName("1");
		partnerFeePlan.setPayStage("1");
		partnerFeePlan.setPayStatus("1");
		partnerFeePlan.setPayUnit("1");
		partnerFeePlan.setPayUser("1");
		partnerFeePlan.setPayWay("1");
		partnerFeePlan.setPlanPayDate(new Date());
		partnerFeePlan.setPlanPayFee("1");
		partnerFeePlan.setRemark("1123");
		partnerFeePlanDao.savePartnerFeePlan(partnerFeePlan);
		flush();
		String id = partnerFeePlan.getId();
		partnerFeePlanDao.removePartnerFeePlan(id);
//		assertNull(partnerFeePlanDao.getPartnerFeePlan(id).getId());
	}
	
	public void testGetPartnerFeePlansIntegerIntegerString() {
		PartnerFeePlan partnerFeePlan = new PartnerFeePlan();
		partnerFeePlan.setCollectUnit("1");
		partnerFeePlan.setCompactName("1");
		partnerFeePlan.setCompactNo("1");
		partnerFeePlan.setCreateDate(new Date());
		partnerFeePlan.setCreateDept("1");
		partnerFeePlan.setCreateUser("admin");
		partnerFeePlan.setFactPayDate(new Date());
		partnerFeePlan.setFactPayFee("1");
		partnerFeePlan.setName("1");
		partnerFeePlan.setPayStage("1");
		partnerFeePlan.setPayStatus("1");
		partnerFeePlan.setPayUnit("1");
		partnerFeePlan.setPayUser("1");
		partnerFeePlan.setPayWay("1");
		partnerFeePlan.setPlanPayDate(new Date());
		partnerFeePlan.setPlanPayFee("1");
		partnerFeePlan.setRemark("1123");
		partnerFeePlanDao.savePartnerFeePlan(partnerFeePlan);
		Map map = partnerFeePlanDao.getPartnerFeePlans(new Integer(1), new Integer(15), "");
		List result = (List)map.get("result");
		assertNotNull(result);
		for(Iterator it = result.iterator();it.hasNext();){
			PartnerFeePlan pfp = (PartnerFeePlan)it.next();
			assertNotNull(pfp.getId());
			assertEquals(pfp.getCollectUnit(),"1");
			assertEquals(pfp.getCompactName(),"1");
		}
	}


	public void testGetFeePlantStatisticsStringStringString() {
		PartnerFeePlan partnerFeePlan = new PartnerFeePlan();
		partnerFeePlan.setCollectUnit("1");
		partnerFeePlan.setCompactName("1");
		partnerFeePlan.setCompactNo("1");
		partnerFeePlan.setCreateDate(StatisticMethod.stringToDate( "2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		System.out.println("---------------------------------------"+partnerFeePlan.getCreateDate()+"-------------------------------------");
		partnerFeePlan.setCreateDept("1");
		partnerFeePlan.setCreateUser("admin");
		partnerFeePlan.setFactPayDate(new Date());
		partnerFeePlan.setFactPayFee("1");
		partnerFeePlan.setName("1");
		partnerFeePlan.setPayStage("1");
		partnerFeePlan.setPayStatus("1");
		partnerFeePlan.setPayUnit("1");
		partnerFeePlan.setPayUser("1");
		partnerFeePlan.setPayWay("1");
		partnerFeePlan.setPlanPayDate(new Date());
		partnerFeePlan.setPlanPayFee("1");
		partnerFeePlan.setRemark("1123");
		partnerFeePlanDao.savePartnerFeePlan(partnerFeePlan);
		System.out.println("---------------------------------------"+partnerFeePlan.getCreateDate()+"-------------------------------------");
		assertNotNull(partnerFeePlan.getId());
		List result = partnerFeePlanDao.getFeePlantStatistics(partnerFeePlan.getId(), "2009-09-16", "2009-09-22");
//		System.out.println("---------------------------------------"+result.size()+"-------------------------------------");
		assertNotNull(result);
		for(Iterator it = result.iterator();it.hasNext();){
			PartnerFeePlan pfp = (PartnerFeePlan)it.next();
			assertNotNull(pfp.getId());
			assertEquals(pfp.getCollectUnit(),"1");
			assertEquals(pfp.getCompactName(),"1");
		}
	}

	public void testGetFeePlantStatisticsStringDateDate() {
		PartnerFeePlan partnerFeePlan = new PartnerFeePlan();
		partnerFeePlan.setCollectUnit("1");
		partnerFeePlan.setCompactName("1");
		partnerFeePlan.setCompactNo("1");
		partnerFeePlan.setCreateDate(StatisticMethod.stringToDate( "2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		System.out.println("---------------------------------------"+partnerFeePlan.getCreateDate()+"-------------------------------------");
		partnerFeePlan.setCreateDept("1");
		partnerFeePlan.setCreateUser("admin");
		partnerFeePlan.setFactPayDate(new Date());
		partnerFeePlan.setFactPayFee("1");
		partnerFeePlan.setName("1");
		partnerFeePlan.setPayStage("1");
		partnerFeePlan.setPayStatus("1");
		partnerFeePlan.setPayUnit("1");
		partnerFeePlan.setPayUser("1");
		partnerFeePlan.setPayWay("1");
		partnerFeePlan.setPlanPayDate(new Date());
		partnerFeePlan.setPlanPayFee("1");
		partnerFeePlan.setRemark("1123");
		partnerFeePlanDao.savePartnerFeePlan(partnerFeePlan);
		System.out.println("---------------------------------------"+partnerFeePlan.getCreateDate()+"-------------------------------------");
		assertNotNull(partnerFeePlan.getId());
		Date startDate = StatisticMethod.stringToDate( "2009-9-11 11:21:42", "yyyy-MM-dd HH:mm:ss");
		Date endDate = StatisticMethod.stringToDate( "2009-9-25 11:21:42", "yyyy-MM-dd HH:mm:ss");
		List result = partnerFeePlanDao.getFeePlantStatistics(partnerFeePlan.getId(), startDate, endDate);
//		System.out.println("---------------------------------------"+result.size()+"-------------------------------------");
		assertNotNull(result);
		for(Iterator it = result.iterator();it.hasNext();){
			PartnerFeePlan pfp = (PartnerFeePlan)it.next();
			assertNotNull(pfp.getId());
			assertEquals(pfp.getCollectUnit(),"1");
			assertEquals(pfp.getCompactName(),"1");
		}
	}

}
