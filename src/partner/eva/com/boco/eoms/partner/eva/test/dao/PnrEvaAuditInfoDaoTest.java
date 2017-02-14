package com.boco.eoms.partner.eva.test.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.partner.eva.dao.IPnrEvaAuditInfoDao;
import com.boco.eoms.partner.eva.model.PnrEvaAuditInfo;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;

public class PnrEvaAuditInfoDaoTest extends ConsoleTestCase {
	
	private IPnrEvaAuditInfoDao iPnrEvaAuditInfoDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		this.setTransactionManager((PlatformTransactionManager)getBean("transactionManager"));
		this.setDefaultRollback(true);
		super.onSetUpBeforeTransaction();
		this.iPnrEvaAuditInfoDao = (IPnrEvaAuditInfoDao)getBean("pnrEvaAuditInfoDao");
	}
	/**
	 * 根据主键id查询模板的审核信息
	 */
	public final void testGetPnrEvaAuditInfo() {
		PnrEvaAuditInfo pnrEvaAuditInfo = new PnrEvaAuditInfo();
		pnrEvaAuditInfo.setTemplateId("111");
		pnrEvaAuditInfo.setCreateTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo.setAuditTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo.setAuditOrg("admin");
		pnrEvaAuditInfo.setAuditOrgType("user");
		pnrEvaAuditInfo.setAuditUser("admin");
		pnrEvaAuditInfo.setAuditType(PnrEvaConstants.AUDIT_NEW);
		pnrEvaAuditInfo.setAuditResult("1");
		pnrEvaAuditInfo.setRemark("remark");
		iPnrEvaAuditInfoDao.savePnrEvaAuditInfo(pnrEvaAuditInfo);
		
		PnrEvaAuditInfo newPnrEvaAuditInfo = iPnrEvaAuditInfoDao.getPnrEvaAuditInfo(pnrEvaAuditInfo.getId());
		assertNotNull(newPnrEvaAuditInfo);
	}
	/**
	 * 保存模板的审核信息
	 */
	public final void testSavePnrEvaAuditInfo() {
		PnrEvaAuditInfo pnrEvaAuditInfo = new PnrEvaAuditInfo();
		pnrEvaAuditInfo.setTemplateId("111");
		pnrEvaAuditInfo.setCreateTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo.setAuditTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo.setAuditOrg("admin");
		pnrEvaAuditInfo.setAuditOrgType("user");
		pnrEvaAuditInfo.setAuditUser("admin");
		pnrEvaAuditInfo.setAuditType(PnrEvaConstants.AUDIT_NEW);
		pnrEvaAuditInfo.setAuditResult("1");
		pnrEvaAuditInfo.setRemark("remark");
		iPnrEvaAuditInfoDao.savePnrEvaAuditInfo(pnrEvaAuditInfo);
		
		assertNotNull(pnrEvaAuditInfo.getId());
	}
	/**
	 * 根据查询条件查询模板的审核信息
	 */
	public final void testGetAuditInfoByCondition() {
		PnrEvaAuditInfo pnrEvaAuditInfo1 = new PnrEvaAuditInfo();
		pnrEvaAuditInfo1.setTemplateId("111");
		pnrEvaAuditInfo1.setAuditTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo1.setCreateTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo1.setAuditOrg("3");
		pnrEvaAuditInfo1.setAuditOrgType("user");
		pnrEvaAuditInfo1.setAuditUser("admin");
		pnrEvaAuditInfo1.setAuditType(PnrEvaConstants.AUDIT_NEW);
		pnrEvaAuditInfo1.setAuditResult("1");
		pnrEvaAuditInfo1.setRemark("test");
		iPnrEvaAuditInfoDao.savePnrEvaAuditInfo(pnrEvaAuditInfo1);
		
		PnrEvaAuditInfo pnrEvaAuditInfo2  = new PnrEvaAuditInfo();
		pnrEvaAuditInfo2.setTemplateId("111");
		pnrEvaAuditInfo2.setCreateTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo2.setAuditTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo2.setAuditOrg("1");
		pnrEvaAuditInfo2.setAuditOrgType("user");
		pnrEvaAuditInfo2.setAuditUser("admin");
		pnrEvaAuditInfo2.setAuditType(PnrEvaConstants.AUDIT_NEW);
		pnrEvaAuditInfo2.setAuditResult("1");
		pnrEvaAuditInfo2.setRemark("test");
		iPnrEvaAuditInfoDao.savePnrEvaAuditInfo(pnrEvaAuditInfo2);
		
		PnrEvaAuditInfo pnrEvaAuditInfo3  = new PnrEvaAuditInfo();
		pnrEvaAuditInfo3.setTemplateId("111");
		pnrEvaAuditInfo3.setCreateTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo3.setAuditTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo3.setAuditOrg("1");
		pnrEvaAuditInfo3.setAuditOrgType("user");
		pnrEvaAuditInfo3.setAuditUser("admin");
		pnrEvaAuditInfo3.setAuditType(PnrEvaConstants.AUDIT_NEW);
		pnrEvaAuditInfo3.setAuditResult("1");
		pnrEvaAuditInfo3.setRemark("test");
		iPnrEvaAuditInfoDao.savePnrEvaAuditInfo(pnrEvaAuditInfo3);
		
		String whereStr = " where templateId='111' and auditUser='admin' and auditResult='1' and remark='test'";
		Map answersMap = iPnrEvaAuditInfoDao.getAuditInfoByCondition(new Integer(0),new Integer(15),whereStr);
		
		Integer total = (Integer)answersMap.get("total");
		
		assertEquals(3, total.intValue());
	}
	/**
	 * 根据模板id查询模板的审核信息
	 */
	public final void testGetPnrEvaAudit() {
		List auditInfoList = iPnrEvaAuditInfoDao.getPnrEvaAudit("111");
		
		PnrEvaAuditInfo pnrEvaAuditInfo = new PnrEvaAuditInfo();
		pnrEvaAuditInfo.setTemplateId("111");
		pnrEvaAuditInfo.setCreateTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo.setAuditTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		pnrEvaAuditInfo.setAuditOrg("admin");
		pnrEvaAuditInfo.setAuditOrgType("user");
		pnrEvaAuditInfo.setAuditUser("admin");
		pnrEvaAuditInfo.setAuditType(PnrEvaConstants.AUDIT_NEW);
		pnrEvaAuditInfo.setAuditResult("1");
		pnrEvaAuditInfo.setRemark("test");
		iPnrEvaAuditInfoDao.savePnrEvaAuditInfo(pnrEvaAuditInfo);
		
		List newAuditInfoList = iPnrEvaAuditInfoDao.getPnrEvaAudit("111");
		assertEquals(auditInfoList.size()+1,newAuditInfoList.size());
	}

}
