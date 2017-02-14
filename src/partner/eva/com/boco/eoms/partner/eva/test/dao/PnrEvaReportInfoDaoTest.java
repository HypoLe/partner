package com.boco.eoms.partner.eva.test.dao;

import java.util.List;
import java.util.Map;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.partner.eva.dao.IPnrEvaReportInfoDao;
import com.boco.eoms.partner.eva.dao.IPnrEvaTaskDao;
import com.boco.eoms.partner.eva.model.PnrEvaReportInfo;
import com.boco.eoms.partner.eva.model.PnrEvaTask;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;

public class PnrEvaReportInfoDaoTest extends ConsoleTestCase {
	
	private IPnrEvaReportInfoDao iPnrEvaReportInfoDao;
	private IPnrEvaTaskDao iPnrEvaTaskDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		this.setTransactionManager((PlatformTransactionManager)getBean("transactionManager"));
		this.setDefaultRollback(true);
		super.onSetUpBeforeTransaction();
		this.iPnrEvaReportInfoDao = (IPnrEvaReportInfoDao)getBean("pnrEvaReportInfoDao");
		this.iPnrEvaTaskDao = (IPnrEvaTaskDao)getBean("pnrEvaTaskDao");
	}
	public final void testGetPnrEvaReportInfo() {
		PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId("333");
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		PnrEvaReportInfo newPnrEvaReportInfo = iPnrEvaReportInfoDao.getPnrEvaReportInfo(pnrEvaReportInfo.getId());
		assertNotNull(newPnrEvaReportInfo.getId());
		
	}

	public final void testSavePnrEvaReportInfo() {
		PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId("333");
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		PnrEvaReportInfo newPnrEvaReportInfo = iPnrEvaReportInfoDao.getPnrEvaReportInfo(pnrEvaReportInfo.getId());
		assertNotNull(newPnrEvaReportInfo.getId());
	}

	public final void testRemovePnrEvaReportInfo() {
		PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId("333");
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		iPnrEvaReportInfoDao.removePnrEvaReportInfo(pnrEvaReportInfo);
		try {
			PnrEvaReportInfo newPnrEvaReportInfo = iPnrEvaReportInfoDao.getPnrEvaReportInfo(pnrEvaReportInfo.getId());
		} catch (ObjectRetrievalFailureException e) {
			assertNotNull(e.getMessage());
		}
	}

	public final void testGetReportInfoByCondition() {
		PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId("333");
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId("333");
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		List list = iPnrEvaReportInfoDao.getReportInfoByCondition(" and taskId='333' ");
		assertEquals(2,list.size());
	}

	public final void testGetReportInfoByTimeAndPartner() {
		PnrEvaTask pnrEvaTask = new PnrEvaTask();
		pnrEvaTask.setOrgId("12");
		pnrEvaTask.setOrgType("area");
		pnrEvaTask.setTemplateId("111111111");
		pnrEvaTask.setCreator("admin");
		pnrEvaTask.setCreateTime(PnrEvaDateUtil.getLocalString());
		//任务表里保存节点信息
		pnrEvaTask.setNodeId("444");
		iPnrEvaTaskDao.saveTask(pnrEvaTask);
		PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId(pnrEvaTask.getId());
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId(pnrEvaTask.getId());
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		List list = iPnrEvaReportInfoDao.getReportInfoByTimeAndPartner("111111111","12","月","200911","111",PnrEvaConstants.TASK_PUBLISHED);
		assertEquals(2,list.size());
	}

	public final void testListReportInfoForPage() {
		PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId("333");
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId("333");
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		Map answersMap = iPnrEvaReportInfoDao.listReportInfoForPage(new Integer(0),new Integer(15)," and taskId='333' ");
		Integer total = (Integer)answersMap.get("total");
		assertEquals(2, total.intValue());
		
	}

	public final void testGetReportInfosByTimeAndAllPartner() {
		PnrEvaTask pnrEvaTask = new PnrEvaTask();
		pnrEvaTask.setOrgId("12");
		pnrEvaTask.setOrgType("area");
		pnrEvaTask.setTemplateId("111111111");
		pnrEvaTask.setCreator("admin");
		pnrEvaTask.setCreateTime(PnrEvaDateUtil.getLocalString());
		//任务表里保存节点信息
		pnrEvaTask.setNodeId("444");
		iPnrEvaTaskDao.saveTask(pnrEvaTask);
		PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId(pnrEvaTask.getId());
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		pnrEvaReportInfo = new PnrEvaReportInfo();
		pnrEvaReportInfo.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司的直接发布
		pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
		pnrEvaReportInfo.setPartnerId("111");
		pnrEvaReportInfo.setPartnerName("222");
		pnrEvaReportInfo.setTaskId(pnrEvaTask.getId());
		pnrEvaReportInfo.setTime("200911");
		pnrEvaReportInfo.setTimeType("月");
		pnrEvaReportInfo.setTotalScore(100);
		pnrEvaReportInfo.setTotalWeight(Float.valueOf(100).toString());
		pnrEvaReportInfo.setArea("12");
		pnrEvaReportInfo.setBelongNode("444");
		iPnrEvaReportInfoDao.savePnrEvaReportInfo(pnrEvaReportInfo);
		List list = iPnrEvaReportInfoDao.getReportInfosByTimeAndAllPartner("111111111","12","月","200911",PnrEvaConstants.TASK_PUBLISHED);
		assertEquals(2,list.size());
	}

}
