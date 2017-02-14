package com.boco.eoms.partner.tranEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.tranEva.dao.ITranEvaKpiInstanceDao;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaKpiInstanceMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaOrgMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTemplateMgr;
import com.boco.eoms.partner.tranEva.model.TranEvaKpi;
import com.boco.eoms.partner.tranEva.model.TranEvaKpiInstance;
import com.boco.eoms.partner.tranEva.model.TranEvaOrg;
import com.boco.eoms.partner.tranEva.model.TranEvaTemplate;
import com.boco.eoms.partner.tranEva.util.DateTimeUtil;
import com.boco.eoms.partner.tranEva.util.TranEvaConstants;
import com.boco.eoms.partner.tranEva.util.TranEvaStaticMethod;

public class TranEvaKpiInstanceMgrImpl implements ITranEvaKpiInstanceMgr {

	private ITranEvaKpiInstanceDao tranEvaKpiInstanceDao;

	public ITranEvaKpiInstanceDao getTranEvaKpiInstanceDao() {
		return tranEvaKpiInstanceDao;
	}

	public void setTranEvaKpiInstanceDao(ITranEvaKpiInstanceDao tranEvaKpiInstanceDao) {
		this.tranEvaKpiInstanceDao = tranEvaKpiInstanceDao;
	}

	public TranEvaKpiInstance getKpiInstance(String id) {
		return tranEvaKpiInstanceDao.getKpiInstance(id);
	}

	public List listKpiInstanceOfTemplate(String orgId, String date) {
		ITranEvaTemplateMgr templateMgr = (ITranEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("ItranEvaTemplateMgr");
		ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("ItranEvaOrgMgr");
		// 获得任务
		TranEvaOrg org = orgMgr.getTranEvaOrg(orgId);
		// 获得模板
		TranEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = TranEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = TranEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return tranEvaKpiInstanceDao.listKpiInstanceOfTemplate(orgId, startDate,
				endDate);
	}

	public void removeKpiInstance(TranEvaKpiInstance kpiInstance) {
		tranEvaKpiInstanceDao.removeKpiInstance(kpiInstance);
	}

	public void saveKpiInstance(TranEvaKpiInstance kpiInstance) {
		tranEvaKpiInstanceDao.saveKpiInstance(kpiInstance);
	}

	public void genKpiInstance(String orgId, String genOperatorId) {
		// ITranEvaTemplateKpiMgr templateKpiMgr = (ITranEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("ItranEvaTemplateKpiMgr");
		// ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("ItranEvaOrgMgr");
		// // 获得任务
		// TranEvaOrg org = orgMgr.getTranEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// TranEvaKpiInstance instance = new TranEvaKpiInstance();
		// TranEvaKpi kpi = (TranEvaKpi) kpiIter.next();
		// instance.setTemplateId(org.getTemplateId());
		// instance.setKpiId(kpi.getId());
		// instance.setGenOperator(genOperatorId);
		// instance.setGenTime(DateTimeUtil
		// .getCurrentDateTime(TranEvaConstants.DATE_FORMAT));
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
	public TranEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId){
		return tranEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId);
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
		return tranEvaKpiInstanceDao.getKpiInstanceListByTimeAndPartner(taskDetailId, partnerId, timeType, startTime, endTime, isPublish);
	}
	
	public TranEvaKpiInstance getKpiInstance(String orgId, String kpiId, String date) {
		ITranEvaTemplateMgr templateMgr = (ITranEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("ItranEvaTemplateMgr");
		ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("ItranEvaOrgMgr");
		// 获得任务
		TranEvaOrg org = orgMgr.getTranEvaOrg(orgId);
		// 获得模板
		TranEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = TranEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = TranEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return tranEvaKpiInstanceDao.getKpiInstanceOfTemplate(orgId, kpiId,
				startDate, endDate);
	}

	public List listKpiOfTemplateWithScore(String orgId, String date) {
		// ITranEvaTemplateKpiMgr templateKpiMgr = (ITranEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("ItranEvaTemplateKpiMgr");
		// ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("ItranEvaOrgMgr");
		// // 获得任务
		// TranEvaOrg org = orgMgr.getTranEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// List list = new ArrayList();
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// TranEvaKpi kpi = (TranEvaKpi) kpiIter.next();
		// TranEvaKpiInstance instance = getKpiInstance(orgId, kpi.getId(), date);
		// // 未生成实例则不加入列表
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// kpi.setTranEvaScore(instance.getKpiScore());
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
			TranEvaKpi kpi = (TranEvaKpi) kpiIter.next();
			if (null != kpi.getTranEvaScore()) {
				totalScore = totalScore + kpi.getTranEvaScore().floatValue();
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
		return tranEvaKpiInstanceDao.id2Name(id);
	}
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public TranEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId,final  String city){
		return tranEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId,city);
	}
}
