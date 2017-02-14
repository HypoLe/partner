package com.boco.eoms.parter.baseinfo.fee.test.mgr;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeeInfoMgr;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeInfo;
/**
 * 
 * @author WinXPsp3
 *
 */
public class PartnerFeeInfoMgrTest extends ConsoleTestCase {

	/**
	 * 付费信息
	 */
	private PartnerFeeInfoMgr partnerFeeInfoMgr;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
		this.partnerFeeInfoMgr = (PartnerFeeInfoMgr) applicationContext.getBean("partnerFeeInfoMgr");
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
		List list = partnerFeeInfoMgr.getPartnerFeeInfos();
		assertNotNull(list);
		assertTrue(list.size()>0);
		for(Iterator it = list.iterator();it.hasNext();){
			PartnerFeeInfo pfi = (PartnerFeeInfo)it.next();
			assertNotNull(pfi.getId());
			assertEquals(pfi.getCreateUser(),"admin");
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
		String id = partnerFeeInfo.getId();
		PartnerFeeInfo pfi = partnerFeeInfoMgr.getPartnerFeeInfo(id);
		assertNotNull(pfi);
		assertEquals(id,pfi.getId());
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
		String id = partnerFeeInfo.getId();
		partnerFeeInfoMgr.removePartnerFeeInfo(id);
		assertNull(partnerFeeInfoMgr.getPartnerFeeInfo(id).getId());
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
		Map map = partnerFeeInfoMgr.getPartnerFeeInfos(new Integer(1), new Integer(15), "");
		List result = (List)map.get("result");
		assertNotNull(result);
		for(Iterator it = result.iterator();it.hasNext();){
			PartnerFeeInfo pfi = (PartnerFeeInfo)it.next();
			assertNotNull(pfi.getId());
			assertEquals(pfi.getCollectUnit(),"1");
			assertEquals(pfi.getCompactName(),"1");
		}
	}

	public void testGetPartnerFeeInfosByCollectUnit() {
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
	}

	public void testGetPartnerFeeInfosByPayUnit() {
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
		//List list = partnerFeeInfoMgr.getPartnerFeeInfosByPayUnit("1", "2009-09-16", "2009-09-22");
		
	}

	public void testGetPartnerFeeInfosByCompactNo() {
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
	}

	public void testGetFeeCollectStatistics() {
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
	}

	public void testGetFeePayStatistics() {
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
	}

	public void testGetFeeCompactStatistics() {
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
		partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
	}

}
