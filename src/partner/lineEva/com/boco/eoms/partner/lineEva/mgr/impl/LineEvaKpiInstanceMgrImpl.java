package com.boco.eoms.partner.lineEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.lineEva.dao.ILineEvaKpiInstanceDao;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaKpiInstanceMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaOrgMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTemplateMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaKpi;
import com.boco.eoms.partner.lineEva.model.LineEvaKpiInstance;
import com.boco.eoms.partner.lineEva.model.LineEvaOrg;
import com.boco.eoms.partner.lineEva.model.LineEvaTemplate;
import com.boco.eoms.partner.lineEva.util.DateTimeUtil;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;
import com.boco.eoms.partner.lineEva.util.LineEvaStaticMethod;

public class LineEvaKpiInstanceMgrImpl implements ILineEvaKpiInstanceMgr {

	private ILineEvaKpiInstanceDao lineEvaKpiInstanceDao;

	public ILineEvaKpiInstanceDao getLineEvaKpiInstanceDao() {
		return lineEvaKpiInstanceDao;
	}

	public void setLineEvaKpiInstanceDao(ILineEvaKpiInstanceDao lineEvaKpiInstanceDao) {
		this.lineEvaKpiInstanceDao = lineEvaKpiInstanceDao;
	}

	public LineEvaKpiInstance getKpiInstance(String id) {
		return lineEvaKpiInstanceDao.getKpiInstance(id);
	}

	public List listKpiInstanceOfTemplate(String orgId, String date) {
		ILineEvaTemplateMgr templateMgr = (ILineEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTemplateMgr");
		ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("IlineEvaOrgMgr");
		// 获得任务
		LineEvaOrg org = orgMgr.getLineEvaOrg(orgId);
		// 获得模板
		LineEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = LineEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = LineEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return lineEvaKpiInstanceDao.listKpiInstanceOfTemplate(orgId, startDate,
				endDate);
	}

	public void removeKpiInstance(LineEvaKpiInstance kpiInstance) {
		lineEvaKpiInstanceDao.removeKpiInstance(kpiInstance);
	}

	public void saveKpiInstance(LineEvaKpiInstance kpiInstance) {
		lineEvaKpiInstanceDao.saveKpiInstance(kpiInstance);
	}

	public void genKpiInstance(String orgId, String genOperatorId) {
		// ILineEvaTemplateKpiMgr templateKpiMgr = (ILineEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("IlineEvaTemplateKpiMgr");
		// ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("IlineEvaOrgMgr");
		// // 获得任务
		// LineEvaOrg org = orgMgr.getLineEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// LineEvaKpiInstance instance = new LineEvaKpiInstance();
		// LineEvaKpi kpi = (LineEvaKpi) kpiIter.next();
		// instance.setTemplateId(org.getTemplateId());
		// instance.setKpiId(kpi.getId());
		// instance.setGenOperator(genOperatorId);
		// instance.setGenTime(DateTimeUtil
		// .getCurrentDateTime(LineEvaConstants.DATE_FORMAT));
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
	public LineEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId){
		return lineEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId);
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
		return lineEvaKpiInstanceDao.getKpiInstanceListByTimeAndPartner(taskDetailId, partnerId, timeType, startTime, endTime, isPublish);
	}
	
	public LineEvaKpiInstance getKpiInstance(String orgId, String kpiId, String date) {
		ILineEvaTemplateMgr templateMgr = (ILineEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IlineEvaTemplateMgr");
		ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("IlineEvaOrgMgr");
		// 获得任务
		LineEvaOrg org = orgMgr.getLineEvaOrg(orgId);
		// 获得模板
		LineEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = LineEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = LineEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return lineEvaKpiInstanceDao.getKpiInstanceOfTemplate(orgId, kpiId,
				startDate, endDate);
	}

	public List listKpiOfTemplateWithScore(String orgId, String date) {
		// ILineEvaTemplateKpiMgr templateKpiMgr = (ILineEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("IlineEvaTemplateKpiMgr");
		// ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("IlineEvaOrgMgr");
		// // 获得任务
		// LineEvaOrg org = orgMgr.getLineEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// List list = new ArrayList();
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// LineEvaKpi kpi = (LineEvaKpi) kpiIter.next();
		// LineEvaKpiInstance instance = getKpiInstance(orgId, kpi.getId(), date);
		// // 未生成实例则不加入列表
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// kpi.setLineEvaScore(instance.getKpiScore());
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
			LineEvaKpi kpi = (LineEvaKpi) kpiIter.next();
			if (null != kpi.getLineEvaScore()) {
				totalScore = totalScore + kpi.getLineEvaScore().floatValue();
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
		return lineEvaKpiInstanceDao.id2Name(id);
	}
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public LineEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId,final  String city){
		return lineEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId,city);
	}
}
