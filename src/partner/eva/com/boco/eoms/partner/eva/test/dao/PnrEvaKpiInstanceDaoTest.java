package com.boco.eoms.partner.eva.test.dao;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.partner.eva.dao.IPnrEvaKpiInstanceDao;
import com.boco.eoms.partner.eva.model.PnrEvaKpiInstance;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;

public class PnrEvaKpiInstanceDaoTest extends ConsoleTestCase {
	
	private IPnrEvaKpiInstanceDao iPnrEvaKpiInstanceDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		this.setTransactionManager((PlatformTransactionManager)getBean("transactionManager"));
		this.setDefaultRollback(true);
		super.onSetUpBeforeTransaction();
		this.iPnrEvaKpiInstanceDao = (IPnrEvaKpiInstanceDao)getBean("pnrEvaKpiInstanceDao");
	}
	public final void testGetKpiInstance() {
		PnrEvaKpiInstance pnrEvaKpiInstance = new PnrEvaKpiInstance();
		pnrEvaKpiInstance.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpiInstance.setPartnerId("123");
		pnrEvaKpiInstance.setPartnerName("pname");
		pnrEvaKpiInstance.setRealScore(100);
		pnrEvaKpiInstance.setReduceReason("Reason");
		pnrEvaKpiInstance.setRemark("testMark");
		pnrEvaKpiInstance.setTaskDetailId("222");
		pnrEvaKpiInstance.setTime("200911");
		pnrEvaKpiInstance.setTimeType("月");
		pnrEvaKpiInstance.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
		iPnrEvaKpiInstanceDao.saveObject(pnrEvaKpiInstance);
		PnrEvaKpiInstance newPnrEvaKpiInstance = iPnrEvaKpiInstanceDao.getKpiInstance(pnrEvaKpiInstance.getId());
		assertNotNull(newPnrEvaKpiInstance.getId());
	}

	public final void testRemoveKpiInstance() {
		PnrEvaKpiInstance pnrEvaKpiInstance = new PnrEvaKpiInstance();
		pnrEvaKpiInstance.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpiInstance.setPartnerId("123");
		pnrEvaKpiInstance.setPartnerName("pname");
		pnrEvaKpiInstance.setRealScore(100);
		pnrEvaKpiInstance.setReduceReason("Reason");
		pnrEvaKpiInstance.setRemark("testMark");
		pnrEvaKpiInstance.setTaskDetailId("222");
		pnrEvaKpiInstance.setTime("200911");
		pnrEvaKpiInstance.setTimeType("月");
		pnrEvaKpiInstance.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
		iPnrEvaKpiInstanceDao.saveObject(pnrEvaKpiInstance);
		PnrEvaKpiInstance newPnrEvaKpiInstance = iPnrEvaKpiInstanceDao.getKpiInstance(pnrEvaKpiInstance.getId());
		iPnrEvaKpiInstanceDao.removeKpiInstance(newPnrEvaKpiInstance);
		try {
			PnrEvaKpiInstance lastPnrEvaKpiInstance = iPnrEvaKpiInstanceDao.getKpiInstance(pnrEvaKpiInstance.getId());
		} catch (ObjectRetrievalFailureException e) {
			assertNotNull(e.getMessage());
		}
	}

	public final void testSaveKpiInstance() {
		PnrEvaKpiInstance pnrEvaKpiInstance = new PnrEvaKpiInstance();
		pnrEvaKpiInstance.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpiInstance.setPartnerId("123");
		pnrEvaKpiInstance.setPartnerName("pname");
		pnrEvaKpiInstance.setRealScore(100);
		pnrEvaKpiInstance.setReduceReason("Reason");
		pnrEvaKpiInstance.setRemark("testMark");
		pnrEvaKpiInstance.setTaskDetailId("222");
		pnrEvaKpiInstance.setTime("200911");
		pnrEvaKpiInstance.setTimeType("月");
		pnrEvaKpiInstance.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
		iPnrEvaKpiInstanceDao.saveObject(pnrEvaKpiInstance);
		assertNotNull(pnrEvaKpiInstance.getId());
	}

	public final void testGetKpiInstanceByTimeAndPartner() {
		PnrEvaKpiInstance pnrEvaKpiInstance = new PnrEvaKpiInstance();
		pnrEvaKpiInstance.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpiInstance.setPartnerId("123");
		pnrEvaKpiInstance.setPartnerName("pname");
		pnrEvaKpiInstance.setRealScore(100);
		pnrEvaKpiInstance.setReduceReason("Reason");
		pnrEvaKpiInstance.setRemark("testMark");
		pnrEvaKpiInstance.setTaskDetailId("222");
		pnrEvaKpiInstance.setTime("200911");
		pnrEvaKpiInstance.setTimeType("月");
		pnrEvaKpiInstance.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
		iPnrEvaKpiInstanceDao.saveObject(pnrEvaKpiInstance);
		PnrEvaKpiInstance newPnrEvaKpiInstance = iPnrEvaKpiInstanceDao
		.getKpiInstanceByTimeAndPartner("222","月","200911","123");
		assertNotNull(newPnrEvaKpiInstance.getId());
	}

	public final void testGetKpiInstanceListByTimeAndPartner() {
		PnrEvaKpiInstance pnrEvaKpiInstance1 = new PnrEvaKpiInstance();
		pnrEvaKpiInstance1.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpiInstance1.setPartnerId("123");
		pnrEvaKpiInstance1.setPartnerName("pname");
		pnrEvaKpiInstance1.setRealScore(100);
		pnrEvaKpiInstance1.setReduceReason("Reason");
		pnrEvaKpiInstance1.setRemark("testMark");
		pnrEvaKpiInstance1.setTaskDetailId("222");
		pnrEvaKpiInstance1.setTime("200911");
		pnrEvaKpiInstance1.setTimeType("月");
		pnrEvaKpiInstance1.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
		pnrEvaKpiInstance1.setIsPublish("1");
		iPnrEvaKpiInstanceDao.saveObject(pnrEvaKpiInstance1);
		
		PnrEvaKpiInstance pnrEvaKpiInstance2 = new PnrEvaKpiInstance();
		pnrEvaKpiInstance2.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpiInstance2.setPartnerId("123");
		pnrEvaKpiInstance2.setPartnerName("pname");
		pnrEvaKpiInstance2.setRealScore(100);
		pnrEvaKpiInstance2.setReduceReason("Reason");
		pnrEvaKpiInstance2.setRemark("testMark");
		pnrEvaKpiInstance2.setTaskDetailId("222");
		pnrEvaKpiInstance2.setTime("200911");
		pnrEvaKpiInstance2.setTimeType("月");
		pnrEvaKpiInstance2.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
		pnrEvaKpiInstance2.setIsPublish("1");
		iPnrEvaKpiInstanceDao.saveObject(pnrEvaKpiInstance2);
		List list = iPnrEvaKpiInstanceDao
		.getKpiInstanceListByTimeAndPartner("222","123","月","200911","200911","1");
		assertEquals(2, list.size());
	}

	public final void testGetKpiInstanceByTaskDetailId() {
		PnrEvaKpiInstance pnrEvaKpiInstance = new PnrEvaKpiInstance();
		pnrEvaKpiInstance.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpiInstance.setPartnerId("123");
		pnrEvaKpiInstance.setPartnerName("pname");
		pnrEvaKpiInstance.setRealScore(100);
		pnrEvaKpiInstance.setReduceReason("Reason");
		pnrEvaKpiInstance.setRemark("testMark");
		pnrEvaKpiInstance.setTaskDetailId("222");
		pnrEvaKpiInstance.setTime("200911");
		pnrEvaKpiInstance.setTimeType("月");
		pnrEvaKpiInstance.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
		iPnrEvaKpiInstanceDao.saveObject(pnrEvaKpiInstance);
		PnrEvaKpiInstance newPnrEvaKpiInstance = iPnrEvaKpiInstanceDao.getKpiInstanceByTaskDetailId("222");
		assertNotNull(newPnrEvaKpiInstance.getId());
	}


}
