package com.boco.eoms.partner.siteEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaKpiInstanceDao;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiInstanceMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaOrgMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTemplateMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpi;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpiInstance;
import com.boco.eoms.partner.siteEva.model.SiteEvaOrg;
import com.boco.eoms.partner.siteEva.model.SiteEvaTemplate;
import com.boco.eoms.partner.siteEva.util.DateTimeUtil;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;
import com.boco.eoms.partner.siteEva.util.SiteEvaStaticMethod;

public class SiteEvaKpiInstanceMgrImpl implements ISiteEvaKpiInstanceMgr {

	private ISiteEvaKpiInstanceDao siteEvaKpiInstanceDao;

	public ISiteEvaKpiInstanceDao getSiteEvaKpiInstanceDao() {
		return siteEvaKpiInstanceDao;
	}
 
	public void setSiteEvaKpiInstanceDao(ISiteEvaKpiInstanceDao siteEvaKpiInstanceDao) {
		this.siteEvaKpiInstanceDao = siteEvaKpiInstanceDao;
	}

	public SiteEvaKpiInstance getKpiInstance(String id) {
		return siteEvaKpiInstanceDao.getKpiInstance(id);
	}

	public List listKpiInstanceOfTemplate(String orgId, String date) {
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTemplateMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("IsiteEvaOrgMgr");
		// 获得任务
		SiteEvaOrg org = orgMgr.getSiteEvaOrg(orgId);
		// 获得模板
		SiteEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = SiteEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = SiteEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return siteEvaKpiInstanceDao.listKpiInstanceOfTemplate(orgId, startDate,
				endDate);
	}

	public void removeKpiInstance(SiteEvaKpiInstance kpiInstance) {
		siteEvaKpiInstanceDao.removeKpiInstance(kpiInstance);
	}

	public void saveKpiInstance(SiteEvaKpiInstance kpiInstance) {
		siteEvaKpiInstanceDao.saveKpiInstance(kpiInstance);
	}

	public void genKpiInstance(String orgId, String genOperatorId) {
		// ISiteEvaTemplateKpiMgr templateKpiMgr = (ISiteEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("IsiteEvaTemplateKpiMgr");
		// ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("IsiteEvaOrgMgr");
		// // 获得任务
		// SiteEvaOrg org = orgMgr.getSiteEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// SiteEvaKpiInstance instance = new SiteEvaKpiInstance();
		// SiteEvaKpi kpi = (SiteEvaKpi) kpiIter.next();
		// instance.setTemplateId(org.getTemplateId());
		// instance.setKpiId(kpi.getId());
		// instance.setGenOperator(genOperatorId);
		// instance.setGenTime(DateTimeUtil
		// .getCurrentDateTime(SiteEvaConstants.DATE_FORMAT));
		// instance.setOrgId(orgId);
		// saveKpiInstance(instance);
		//		}
	}

	public boolean isInstanceExistsInTime(String orgId, String date) {
		List instanceList = listKpiInstanceOfTemplate(orgId, date);
		boolean flag = false;
		if (null != instanceList && 0 < instanceList.size()) {
			flag = true;
		}
		return flag;
	}

	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public SiteEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId){
		return siteEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId);
	}
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-22
	 * @param  任务id，合作伙伴ID，时间类型，时间范围，
	 * @return 指标考核实例
	 */
	public List getKpiInstanceListByTimeAndPartner(final String taskDetailId, 
			final String partnerId, final String timeType,
			final String startTime, final String endTime, final String isPublish){
		return siteEvaKpiInstanceDao.getKpiInstanceListByTimeAndPartner(taskDetailId, partnerId, timeType, startTime, endTime, isPublish);
	}
	
	public SiteEvaKpiInstance getKpiInstance(String orgId, String kpiId, String date) {
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTemplateMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("IsiteEvaOrgMgr");
		// 获得任务
		SiteEvaOrg org = orgMgr.getSiteEvaOrg(orgId);
		// 获得模板
		SiteEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = SiteEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = SiteEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return siteEvaKpiInstanceDao.getKpiInstanceOfTemplate(orgId, kpiId,
				startDate, endDate);
	}

	public List listKpiOfTemplateWithScore(String orgId, String date) {
		// ISiteEvaTemplateKpiMgr templateKpiMgr = (ISiteEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("IsiteEvaTemplateKpiMgr");
		// ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("IsiteEvaOrgMgr");
		// // 获得任务
		// SiteEvaOrg org = orgMgr.getSiteEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// List list = new ArrayList();
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// SiteEvaKpi kpi = (SiteEvaKpi) kpiIter.next();
		// SiteEvaKpiInstance instance = getKpiInstance(orgId, kpi.getId(), date);
		// // 未生成实例则不加入列表
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// kpi.setSiteEvaScore(instance.getKpiScore());
		// list.add(kpi);
		//			}
		//		}
		//		return list;
		return new ArrayList();
	}

	public Float getTotalScoreOfTemplate(String orgId, String date) {
		float totalScore = 0;
		List kpiList = listKpiOfTemplateWithScore(orgId, date);
		for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
			SiteEvaKpi kpi = (SiteEvaKpi) kpiIter.next();
			if (null != kpi.getSiteEvaScore()) {
				totalScore = totalScore + kpi.getSiteEvaScore().floatValue();
			}
		}
		return Float.valueOf(totalScore);
	}
	
	/**
	 * 详细任务ID转换为模板名称
	 * 王思轩 09-1-21
	 * @return
	 */
	public String id2Name(String id){
		return siteEvaKpiInstanceDao.id2Name(id);
	}
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public SiteEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId,final  String city){
		return siteEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId,city);
	}
}
