package com.boco.eoms.parter.baseinfo.fee.test.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeeInfoDao;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeInfo;
import com.boco.eoms.partner.baseinfo.util.StatisticMethod;

public class PartnerFeeInfoDaoTest extends ConsoleTestCase {

	// 付费信息
	private PartnerFeeInfoDao partnerFeeInfoDao;



	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		this
				.setTransactionManager((PlatformTransactionManager) applicationContext
						.getBean("transactionManager"));
		this.setDefaultRollback(true);
		super.onSetUpBeforeTransaction();
		this.partnerFeeInfoDao = (PartnerFeeInfoDao) applicationContext
				.getBean("partnerFeeInfoDao");
	}

	public void testGetPartnerFeeInfos() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(new Date());
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		List list = partnerFeeInfoDao.getPartnerFeeInfos();
		assertNotNull(list);
		assertTrue(list.size() > 0);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PartnerFeeInfo pfi = (PartnerFeeInfo) it.next();
			assertNotNull(pfi.getId());
			assertEquals(pfi.getCreateUser(), "admin");
		}
	}

	public void testGetPartnerFeeInfo() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(new Date());
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		flush();
		String id = partnerFeeInfo.getId();
		PartnerFeeInfo pfi = partnerFeeInfoDao.getPartnerFeeInfo(id);
		assertNotNull(pfi);
		assertEquals(id, pfi.getId());
	}

	public void testSavePartnerFeeInfo() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(new Date());
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
	}

	public void testRemovePartnerFeeInfo() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(new Date());
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		flush();
		String id = partnerFeeInfo.getId();
		partnerFeeInfoDao.removePartnerFeeInfo(id);
//		assertNull(partnerFeeInfoDao.getPartnerFeeInfo(id).getId());
	}

	public void testGetPartnerFeeInfosIntegerIntegerString() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(new Date());
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		flush();
		Map map = partnerFeeInfoDao.getPartnerFeeInfos(new Integer(1),
				new Integer(15), "");
		flush();
		List result = (List) map.get("result");
		assertNotNull(result);
		for (Iterator it = result.iterator(); it.hasNext();) {
			PartnerFeeInfo pfi = (PartnerFeeInfo) it.next();
			assertNotNull(pfi.getId());
			assertEquals(pfi.getCollectUnit(), "1");
			assertEquals(pfi.getCompactName(), "1");
		}
	}

	public void testGetPartnerFeeInfosByCollectUnitStringStringString() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);

		List list = partnerFeeInfoDao.getPartnerFeeInfosByCollectUnit("1",
				"2009-9-11 11:21:42", "2009-9-24 11:21:42");
		assertNotNull(list);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PartnerFeeInfo pfi = (PartnerFeeInfo) it.next();
			assertNotNull(pfi.getId());
			assertEquals(pfi.getCollectUnit(), "1");
			assertEquals(pfi.getCompactName(), "1");
		}
	}

	public void testGetPartnerFeeInfosByPayUnitStringStringString() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);

		List list = partnerFeeInfoDao.getPartnerFeeInfosByPayUnit("1",
				"2009-9-11 11:21:42", "2009-9-24 11:21:42");
		assertNotNull(list);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PartnerFeeInfo pfi = (PartnerFeeInfo) it.next();
			assertNotNull(pfi.getId());
			assertEquals(pfi.getCollectUnit(), "1");
			assertEquals(pfi.getCompactName(), "1");
		}
	}

	public void testGetPartnerFeeInfosByCompactNoStringStringString() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		flush();
		List list = partnerFeeInfoDao.getPartnerFeeInfosByCompactNo("1",
				"2009-9-11 11:21:42", "2009-9-24 11:21:42");
		assertNotNull(list);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PartnerFeeInfo pfi = (PartnerFeeInfo) it.next();
			assertNotNull(pfi.getId());
			assertEquals(pfi.getCollectUnit(), "1");
			assertEquals(pfi.getCompactName(), "1");
		}
	}

	public void testGetFeeCollectStatisticsStringStringString() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		flush();
		System.out.println("----------------" + partnerFeeInfo.getPayDate()
				+ "----------------------");
		Map map = partnerFeeInfoDao.getFeeCollectStatistics("",
				"2009-9-11 11:21:42", "2009-9-24 11:21:42");
		assertNotNull(map);
		List result = (List) map.get("result");
		assertEquals(map.get("total").toString(), "1");
		System.out.println("----------------" + map.get("total")
				+ "----------------------");
		for (int i = 0; i < result.size(); i++) {
			Object[] objs = (Object[]) result.get(i);
			// assertEquals(StatisticMethod.objectToString(objs[0]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[0])
					+ "----------------------");
			// assertEquals(StatisticMethod.objectToString(objs[1]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[1])
					+ "----------------------");
		}
	}

	public void testGetFeePayStatisticsStringStringString() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		flush();
		System.out.println("----------------" + partnerFeeInfo.getPayDate()
				+ "----------------------");
		Map map = partnerFeeInfoDao.getFeePayStatistics("",
				"2009-9-11 11:21:42", "2009-9-24 11:21:42");
		assertNotNull(map);
		List result = (List) map.get("result");
		assertEquals(map.get("total").toString(), "1");
		System.out.println("----------------" + map.get("total")
				+ "----------------------");
		for (int i = 0; i < result.size(); i++) {
			Object[] objs = (Object[]) result.get(i);
			// assertEquals(StatisticMethod.objectToString(objs[0]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[0])
					+ "----------------------");
			// assertEquals(StatisticMethod.objectToString(objs[1]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[1])
					+ "----------------------");
		}
	}

	public void testGetFeeCompactStatisticsStringStringString() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		flush();
		System.out.println("----------------" + partnerFeeInfo.getPayDate()
				+ "----------------------");
		
		Map map = partnerFeeInfoDao.getFeeCompactStatistics("",
				"2009-9-11 11:21:42", "2009-9-24 11:21:42");
		assertNotNull(map);
		List result = (List) map.get("result");
		assertEquals(map.get("total").toString(), "1");
		System.out.println("----------------" + map.get("total")
				+ "----------------------");
		for (int i = 0; i < result.size(); i++) {
			Object[] objs = (Object[]) result.get(i);
			// assertEquals(StatisticMethod.objectToString(objs[0]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[0])
					+ "----------------------");
			// assertEquals(StatisticMethod.objectToString(objs[1]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[1])
					+ "----------------------");
		}
	}

	public void testGetPartnerFeeInfosByCollectUnitStringDateDate() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);

		Date startDate = StatisticMethod.stringToDate("2009-9-11 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		Date endDate = StatisticMethod.stringToDate("2009-9-25 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		List list = partnerFeeInfoDao.getPartnerFeeInfosByCollectUnit("1",
				startDate, endDate);
		assertNotNull(list);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PartnerFeeInfo pfi = (PartnerFeeInfo) it.next();
			assertNotNull(pfi.getId());
			assertEquals(pfi.getCollectUnit(), "1");
			assertEquals(pfi.getCompactName(), "1");
		}
	}

	public void testGetPartnerFeeInfosByPayUnitStringDateDate() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);

		Date startDate = StatisticMethod.stringToDate("2009-9-11 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		Date endDate = StatisticMethod.stringToDate("2009-9-25 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		List list = partnerFeeInfoDao.getPartnerFeeInfosByPayUnit("1",
				startDate, endDate);
		assertNotNull(list);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PartnerFeeInfo pfi = (PartnerFeeInfo) it.next();
			assertNotNull(pfi.getId());
			assertEquals(pfi.getCollectUnit(), "1");
			assertEquals(pfi.getCompactName(), "1");
		}
	}

	public void testGetPartnerFeeInfosByCompactNoStringDateDate() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);

		Date startDate = StatisticMethod.stringToDate("2009-9-11 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		Date endDate = StatisticMethod.stringToDate("2009-9-25 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		List list = partnerFeeInfoDao.getPartnerFeeInfosByCompactNo("1",
				startDate, endDate);

		assertNotNull(list);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PartnerFeeInfo pfi = (PartnerFeeInfo) it.next();
			assertNotNull(pfi.getId());
			assertEquals(pfi.getCollectUnit(), "1");
			assertEquals(pfi.getCompactName(), "1");
		}
	}

	public void testGetFeeCollectStatisticsStringDateDate() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		flush();
		Date startDate = StatisticMethod.stringToDate("2009-9-11 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		Date endDate = StatisticMethod.stringToDate("2009-9-25 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		System.out.println("----------------" + partnerFeeInfo.getPayDate()
				+ "----------------------");
		Map map = partnerFeeInfoDao.getFeeCollectStatistics("", startDate,
				endDate);
		assertNotNull(map);
		List result = (List) map.get("result");
		assertEquals(map.get("total").toString(), "1");
		System.out.println("----------------" + map.get("total")
				+ "----------------------");
		for (int i = 0; i < result.size(); i++) {
			Object[] objs = (Object[]) result.get(i);
			// assertEquals(StatisticMethod.objectToString(objs[0]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[0])
					+ "----------------------");
			// assertEquals(StatisticMethod.objectToString(objs[1]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[1])
					+ "----------------------");
		}
	}

	public void testGetFeePayStatisticsStringDateDate() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		flush();
		Date startDate = StatisticMethod.stringToDate("2009-9-11 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		Date endDate = StatisticMethod.stringToDate("2009-9-25 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		System.out.println("----123------------" + partnerFeeInfo.getPayDate()
				+ "----------------------");
		Map map = partnerFeeInfoDao.getFeePayStatistics("", startDate, endDate);
		assertNotNull(map);
		List result = (List) map.get("result");
		assertEquals(map.get("total").toString(), "1");
		System.out.println("-------123---------" + map.get("total")
				+ "----------------------");
		for (int i = 0; i < result.size(); i++) {
			Object[] objs = (Object[]) result.get(i);
			// assertEquals(StatisticMethod.objectToString(objs[0]),"1");
			System.out.println("------123----------"
					+ StatisticMethod.objectToString(objs[0])
					+ "----------------------");
			// assertEquals(StatisticMethod.objectToString(objs[1]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[1])
					+ "----------------------");
		}
	}

	public void testGetFeeCompactStatisticsStringDateDate() {
		PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
		partnerFeeInfo.setCollectUnit("1");
		partnerFeeInfo.setCompactName("1");
		partnerFeeInfo.setCompactNo("1");
		partnerFeeInfo.setCreateDate(new Date());
		partnerFeeInfo.setCreateDept("1");
		partnerFeeInfo.setCreateUser("admin");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPayUser("1");
		partnerFeeInfo.setPayWay("1");
		partnerFeeInfo.setRemark("1123");
		partnerFeeInfo.setFeeType("1");
		partnerFeeInfo.setFileName("1");
		partnerFeeInfo.setIsPlan("1");
		partnerFeeInfo.setName("1");
		partnerFeeInfo.setPayCause("1");
		partnerFeeInfo.setPayDate(StatisticMethod.stringToDate(
				"2009-9-20 11:21:42", "yyyy-MM-dd HH:mm:ss"));
		partnerFeeInfo.setPayFee("1");
		partnerFeeInfo.setPayNo("1");
		partnerFeeInfo.setPayUnit("1");
		partnerFeeInfo.setPlanId("1");
		partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
		flush();
		Date startDate = StatisticMethod.stringToDate("2009-9-11 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		Date endDate = StatisticMethod.stringToDate("2009-9-25 11:21:42",
				"yyyy-MM-dd HH:mm:ss");
		System.out.println("----------------" + partnerFeeInfo.getPayDate()
				+ "----------------------");
		Map map = partnerFeeInfoDao.getFeeCompactStatistics("", startDate,
				endDate);
		assertNotNull(map);
		List result = (List) map.get("result");
		assertEquals(map.get("total").toString(), "1");
		System.out.println("----------------" + map.get("total")
				+ "----------------------");
		for (int i = 0; i < result.size(); i++) {
			Object[] objs = (Object[]) result.get(i);
			// assertEquals(StatisticMethod.objectToString(objs[0]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[0])
					+ "----------------------");
			// assertEquals(StatisticMethod.objectToString(objs[1]),"1");
			System.out.println("----------------"
					+ StatisticMethod.objectToString(objs[1])
					+ "----------------------");
		}
	}
}
