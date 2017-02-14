package com.boco.eoms.partner.eva.test.dao;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.partner.eva.dao.IPnrEvaTaskAuditDao;
import com.boco.eoms.partner.eva.model.PnrEvaTaskAudit;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;


public class PnrEvaTaskAuditDaoTest extends ConsoleTestCase {
	private IPnrEvaTaskAuditDao iPnrEvaTaskAuditDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		this.setTransactionManager((PlatformTransactionManager)getBean("transactionManager"));
		this.setDefaultRollback(true);
		super.onSetUpBeforeTransaction();
		this.iPnrEvaTaskAuditDao = (IPnrEvaTaskAuditDao)getBean("pnrEvaTaskAuditDao");
	}
	public final void testGetPnrEvaTaskAuditByTaskId() {
		PnrEvaTaskAudit pnrEvaTaskAudit = new PnrEvaTaskAudit();
		pnrEvaTaskAudit.setTaskId("111");
		pnrEvaTaskAudit.setAuditOrg("222");
		pnrEvaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
		pnrEvaTaskAudit.setAreaId("39");
		pnrEvaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
		pnrEvaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaTaskAudit.setAuditTime("200911");
		pnrEvaTaskAudit.setAuditCycle("月");
		pnrEvaTaskAudit.setTemplateId("333");
		pnrEvaTaskAudit.setPartner("44");
		iPnrEvaTaskAuditDao.savePnrEvaTaskAudit(pnrEvaTaskAudit);	
		pnrEvaTaskAudit = new PnrEvaTaskAudit();
		pnrEvaTaskAudit.setTaskId("111");
		pnrEvaTaskAudit.setAuditOrg("222");
		pnrEvaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
		pnrEvaTaskAudit.setAreaId("39");
		pnrEvaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
		pnrEvaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaTaskAudit.setAuditTime("200911");
		pnrEvaTaskAudit.setAuditCycle("月");
		pnrEvaTaskAudit.setTemplateId("333");
		pnrEvaTaskAudit.setPartner("44");
		iPnrEvaTaskAuditDao.savePnrEvaTaskAudit(pnrEvaTaskAudit);
		List list = iPnrEvaTaskAuditDao.getPnrEvaTaskAuditByTaskId("111","44");
		assertEquals(2,list.size());
	}

	public final void testGetPnrEvaTaskAuditStringStringStringString() {
		PnrEvaTaskAudit pnrEvaTaskAudit = new PnrEvaTaskAudit();
		pnrEvaTaskAudit.setTaskId("111");
		pnrEvaTaskAudit.setAuditOrg("222");
		pnrEvaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
		pnrEvaTaskAudit.setAreaId("39");
		pnrEvaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
		pnrEvaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaTaskAudit.setAuditTime("200911");
		pnrEvaTaskAudit.setAuditCycle("月");
		pnrEvaTaskAudit.setTemplateId("333");
		pnrEvaTaskAudit.setPartner("44");
		iPnrEvaTaskAuditDao.savePnrEvaTaskAudit(pnrEvaTaskAudit);	
		pnrEvaTaskAudit = new PnrEvaTaskAudit();
		pnrEvaTaskAudit.setTaskId("111");
		pnrEvaTaskAudit.setAuditOrg("222");
		pnrEvaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
		pnrEvaTaskAudit.setAreaId("39");
		pnrEvaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
		pnrEvaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaTaskAudit.setAuditTime("200911");
		pnrEvaTaskAudit.setAuditCycle("月");
		pnrEvaTaskAudit.setTemplateId("333");
		pnrEvaTaskAudit.setPartner("44");
		iPnrEvaTaskAuditDao.savePnrEvaTaskAudit(pnrEvaTaskAudit);
		List list = iPnrEvaTaskAuditDao.getPnrEvaTaskAudit("111","200911","月","44");
		assertEquals(2,list.size());
	}

	public final void testGetPnrEvaTaskAuditString() {
		PnrEvaTaskAudit pnrEvaTaskAudit = new PnrEvaTaskAudit();
		pnrEvaTaskAudit.setTaskId("111");
		pnrEvaTaskAudit.setAuditOrg("222");
		pnrEvaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
		pnrEvaTaskAudit.setAreaId("39");
		pnrEvaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
		pnrEvaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaTaskAudit.setAuditTime("200911");
		pnrEvaTaskAudit.setAuditCycle("月");
		pnrEvaTaskAudit.setTemplateId("333");
		pnrEvaTaskAudit.setPartner("44");
		iPnrEvaTaskAuditDao.savePnrEvaTaskAudit(pnrEvaTaskAudit);
		PnrEvaTaskAudit newPnrEvaTaskAudit = iPnrEvaTaskAuditDao.getPnrEvaTaskAudit(pnrEvaTaskAudit.getId());
		assertNotNull(newPnrEvaTaskAudit.getId());
	}

	public final void testGetPnrEvaTaskAuditByOrgType() {
		PnrEvaTaskAudit pnrEvaTaskAudit = new PnrEvaTaskAudit();
		pnrEvaTaskAudit.setTaskId("111");
		pnrEvaTaskAudit.setAuditOrg("222");
		pnrEvaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
		pnrEvaTaskAudit.setAreaId("39");
		pnrEvaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
		pnrEvaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaTaskAudit.setAuditTime("200911");
		pnrEvaTaskAudit.setAuditCycle("月");
		pnrEvaTaskAudit.setTemplateId("333");
		pnrEvaTaskAudit.setPartner("44");
		iPnrEvaTaskAuditDao.savePnrEvaTaskAudit(pnrEvaTaskAudit);	
		pnrEvaTaskAudit = new PnrEvaTaskAudit();
		pnrEvaTaskAudit.setTaskId("111");
		pnrEvaTaskAudit.setAuditOrg("222");
		pnrEvaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
		pnrEvaTaskAudit.setAreaId("39");
		pnrEvaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
		pnrEvaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaTaskAudit.setAuditTime("200911");
		pnrEvaTaskAudit.setAuditCycle("月");
		pnrEvaTaskAudit.setTemplateId("333");
		pnrEvaTaskAudit.setPartner("44");
		iPnrEvaTaskAuditDao.savePnrEvaTaskAudit(pnrEvaTaskAudit);
		Map answersMap = iPnrEvaTaskAuditDao.getPnrEvaTaskAuditByOrgType(new Integer(0),new Integer(15)," where taskId='111' ");
		Integer total = (Integer)answersMap.get("total");
		assertEquals(2, total.intValue());
	}

	public final void testSavePnrEvaTaskAudit() {
		PnrEvaTaskAudit pnrEvaTaskAudit = new PnrEvaTaskAudit();
		pnrEvaTaskAudit.setTaskId("111");
		pnrEvaTaskAudit.setAuditOrg("222");
		pnrEvaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
		pnrEvaTaskAudit.setAreaId("39");
		pnrEvaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
		pnrEvaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaTaskAudit.setAuditTime("200911");
		pnrEvaTaskAudit.setAuditCycle("月");
		pnrEvaTaskAudit.setTemplateId("333");
		pnrEvaTaskAudit.setPartner("44");
		iPnrEvaTaskAuditDao.savePnrEvaTaskAudit(pnrEvaTaskAudit);
		PnrEvaTaskAudit newPnrEvaTaskAudit = iPnrEvaTaskAuditDao.getPnrEvaTaskAudit(pnrEvaTaskAudit.getId());
		assertNotNull(newPnrEvaTaskAudit.getId());
	}

}
