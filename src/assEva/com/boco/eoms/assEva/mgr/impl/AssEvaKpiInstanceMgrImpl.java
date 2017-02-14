package com.boco.eoms.assEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.assEva.dao.IAssEvaKpiInstanceDao;
import com.boco.eoms.assEva.mgr.IAssEvaKpiInstanceMgr;
import com.boco.eoms.assEva.mgr.IAssEvaOrgMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTemplateMgr;
import com.boco.eoms.assEva.model.AssEvaKpi;
import com.boco.eoms.assEva.model.AssEvaKpiInstance;
import com.boco.eoms.assEva.model.AssEvaOrg;
import com.boco.eoms.assEva.model.AssEvaTemplate;
import com.boco.eoms.assEva.util.DateTimeUtil;
import com.boco.eoms.assEva.util.AssEvaConstants;
import com.boco.eoms.assEva.util.AssEvaStaticMethod;

public class AssEvaKpiInstanceMgrImpl implements IAssEvaKpiInstanceMgr {

	private IAssEvaKpiInstanceDao assEvaKpiInstanceDao;

	public IAssEvaKpiInstanceDao getAssEvaKpiInstanceDao() {
		return assEvaKpiInstanceDao;
	}

	public void setAssEvaKpiInstanceDao(IAssEvaKpiInstanceDao assEvaKpiInstanceDao) {
		this.assEvaKpiInstanceDao = assEvaKpiInstanceDao;
	}

	public AssEvaKpiInstance getKpiInstance(String id) {
		return assEvaKpiInstanceDao.getKpiInstance(id);
	}

	public List listKpiInstanceOfTemplate(String orgId, String date) {
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTemplateMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("IassEvaOrgMgr");
		// 获得任务
		AssEvaOrg org = orgMgr.getAssEvaOrg(orgId);
		// 获得模板
		AssEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = AssEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = AssEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return assEvaKpiInstanceDao.listKpiInstanceOfTemplate(orgId, startDate,
				endDate);
	}

	public void removeKpiInstance(AssEvaKpiInstance kpiInstance) {
		assEvaKpiInstanceDao.removeKpiInstance(kpiInstance);
	}

	public void saveKpiInstance(AssEvaKpiInstance kpiInstance) {
		assEvaKpiInstanceDao.saveKpiInstance(kpiInstance);
	}

	public void genKpiInstance(String orgId, String genOperatorId) {
		// IAssEvaTemplateKpiMgr templateKpiMgr = (IAssEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("IassEvaTemplateKpiMgr");
		// IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("IassEvaOrgMgr");
		// // 获得任务
		// AssEvaOrg org = orgMgr.getAssEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// AssEvaKpiInstance instance = new AssEvaKpiInstance();
		// AssEvaKpi kpi = (AssEvaKpi) kpiIter.next();
		// instance.setTemplateId(org.getTemplateId());
		// instance.setKpiId(kpi.getId());
		// instance.setGenOperator(genOperatorId);
		// instance.setGenTime(DateTimeUtil
		// .getCurrentDateTime(AssEvaConstants.DATE_FORMAT));
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
	public AssEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId){
		return assEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId);
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
		return assEvaKpiInstanceDao.getKpiInstanceListByTimeAndPartner(taskDetailId, partnerId, timeType, startTime, endTime, isPublish);
	}
	
	public AssEvaKpiInstance getKpiInstance(String orgId, String kpiId, String date) {
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTemplateMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("IassEvaOrgMgr");
		// 获得任务
		AssEvaOrg org = orgMgr.getAssEvaOrg(orgId);
		// 获得模板
		AssEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = AssEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = AssEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return assEvaKpiInstanceDao.getKpiInstanceOfTemplate(orgId, kpiId,
				startDate, endDate);
	}

	public List listKpiOfTemplateWithScore(String orgId, String date) {
		// IAssEvaTemplateKpiMgr templateKpiMgr = (IAssEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("IassEvaTemplateKpiMgr");
		// IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("IassEvaOrgMgr");
		// // 获得任务
		// AssEvaOrg org = orgMgr.getAssEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// List list = new ArrayList();
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// AssEvaKpi kpi = (AssEvaKpi) kpiIter.next();
		// AssEvaKpiInstance instance = getKpiInstance(orgId, kpi.getId(), date);
		// // 未生成实例则不加入列表
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// kpi.setAssEvaScore(instance.getKpiScore());
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
			AssEvaKpi kpi = (AssEvaKpi) kpiIter.next();
			if (null != kpi.getAssEvaScore()) {
				totalScore = totalScore + kpi.getAssEvaScore().floatValue();
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
		return assEvaKpiInstanceDao.id2Name(id);
	}
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public AssEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId,final  String city){
		return assEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId,city);
	}
}
