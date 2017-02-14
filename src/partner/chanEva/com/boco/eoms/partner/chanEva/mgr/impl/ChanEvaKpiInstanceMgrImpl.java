package com.boco.eoms.partner.chanEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.chanEva.dao.IChanEvaKpiInstanceDao;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiInstanceMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaOrgMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTemplateMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpi;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpiInstance;
import com.boco.eoms.partner.chanEva.model.ChanEvaOrg;
import com.boco.eoms.partner.chanEva.model.ChanEvaTemplate;
import com.boco.eoms.partner.chanEva.util.DateTimeUtil;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;
import com.boco.eoms.partner.chanEva.util.ChanEvaStaticMethod;

public class ChanEvaKpiInstanceMgrImpl implements IChanEvaKpiInstanceMgr {

	private IChanEvaKpiInstanceDao chanEvaKpiInstanceDao;

	public IChanEvaKpiInstanceDao getChanEvaKpiInstanceDao() {
		return chanEvaKpiInstanceDao;
	}

	public void setChanEvaKpiInstanceDao(IChanEvaKpiInstanceDao chanEvaKpiInstanceDao) {
		this.chanEvaKpiInstanceDao = chanEvaKpiInstanceDao;
	}

	public ChanEvaKpiInstance getKpiInstance(String id) {
		return chanEvaKpiInstanceDao.getKpiInstance(id);
	}

	public List listKpiInstanceOfTemplate(String orgId, String date) {
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IchanEvaTemplateMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("IchanEvaOrgMgr");
		// 获得任务
		ChanEvaOrg org = orgMgr.getChanEvaOrg(orgId);
		// 获得模板
		ChanEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = ChanEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = ChanEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return chanEvaKpiInstanceDao.listKpiInstanceOfTemplate(orgId, startDate,
				endDate);
	}

	public void removeKpiInstance(ChanEvaKpiInstance kpiInstance) {
		chanEvaKpiInstanceDao.removeKpiInstance(kpiInstance);
	}

	public void saveKpiInstance(ChanEvaKpiInstance kpiInstance) {
		chanEvaKpiInstanceDao.saveKpiInstance(kpiInstance);
	}

	public void genKpiInstance(String orgId, String genOperatorId) {
		// IChanEvaTemplateKpiMgr templateKpiMgr = (IChanEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("IchanEvaTemplateKpiMgr");
		// IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("IchanEvaOrgMgr");
		// // 获得任务
		// ChanEvaOrg org = orgMgr.getChanEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// ChanEvaKpiInstance instance = new ChanEvaKpiInstance();
		// ChanEvaKpi kpi = (ChanEvaKpi) kpiIter.next();
		// instance.setTemplateId(org.getTemplateId());
		// instance.setKpiId(kpi.getId());
		// instance.setGenOperator(genOperatorId);
		// instance.setGenTime(DateTimeUtil
		// .getCurrentDateTime(ChanEvaConstants.DATE_FORMAT));
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
	public ChanEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId){
		return chanEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId);
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
		return chanEvaKpiInstanceDao.getKpiInstanceListByTimeAndPartner(taskDetailId, partnerId, timeType, startTime, endTime, isPublish);
	}
	
	public ChanEvaKpiInstance getKpiInstance(String orgId, String kpiId, String date) {
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IchanEvaTemplateMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("IchanEvaOrgMgr");
		// 获得任务
		ChanEvaOrg org = orgMgr.getChanEvaOrg(orgId);
		// 获得模板
		ChanEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = ChanEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = ChanEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return chanEvaKpiInstanceDao.getKpiInstanceOfTemplate(orgId, kpiId,
				startDate, endDate);
	}

	public List listKpiOfTemplateWithScore(String orgId, String date) {
		// IChanEvaTemplateKpiMgr templateKpiMgr = (IChanEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("IchanEvaTemplateKpiMgr");
		// IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("IchanEvaOrgMgr");
		// // 获得任务
		// ChanEvaOrg org = orgMgr.getChanEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// List list = new ArrayList();
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// ChanEvaKpi kpi = (ChanEvaKpi) kpiIter.next();
		// ChanEvaKpiInstance instance = getKpiInstance(orgId, kpi.getId(), date);
		// // 未生成实例则不加入列表
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// kpi.setChanEvaScore(instance.getKpiScore());
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
			ChanEvaKpi kpi = (ChanEvaKpi) kpiIter.next();
			if (null != kpi.getChanEvaScore()) {
				totalScore = totalScore + kpi.getChanEvaScore().floatValue();
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
		return chanEvaKpiInstanceDao.id2Name(id);
	}
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public ChanEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId,final  String city){
		return chanEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId,city);
	}
}
