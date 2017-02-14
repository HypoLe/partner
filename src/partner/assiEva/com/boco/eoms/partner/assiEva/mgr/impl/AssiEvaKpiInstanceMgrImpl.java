package com.boco.eoms.partner.assiEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaKpiInstanceDao;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaKpiInstanceMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaOrgMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTemplateMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaKpi;
import com.boco.eoms.partner.assiEva.model.AssiEvaKpiInstance;
import com.boco.eoms.partner.assiEva.model.AssiEvaOrg;
import com.boco.eoms.partner.assiEva.model.AssiEvaTemplate;
import com.boco.eoms.partner.assiEva.util.DateTimeUtil;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;
import com.boco.eoms.partner.assiEva.util.AssiEvaStaticMethod;

public class AssiEvaKpiInstanceMgrImpl implements IAssiEvaKpiInstanceMgr {

	private IAssiEvaKpiInstanceDao assiEvaKpiInstanceDao;

	public IAssiEvaKpiInstanceDao getAssiEvaKpiInstanceDao() {
		return assiEvaKpiInstanceDao;
	}

	public void setAssiEvaKpiInstanceDao(IAssiEvaKpiInstanceDao assiEvaKpiInstanceDao) {
		this.assiEvaKpiInstanceDao = assiEvaKpiInstanceDao;
	}

	public AssiEvaKpiInstance getKpiInstance(String id) {
		return assiEvaKpiInstanceDao.getKpiInstance(id);
	}

	public List listKpiInstanceOfTemplate(String orgId, String date) {
		IAssiEvaTemplateMgr templateMgr = (IAssiEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IassiEvaTemplateMgr");
		IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("IassiEvaOrgMgr");
		// 获得任务
		AssiEvaOrg org = orgMgr.getAssiEvaOrg(orgId);
		// 获得模板
		AssiEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = AssiEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = AssiEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return assiEvaKpiInstanceDao.listKpiInstanceOfTemplate(orgId, startDate,
				endDate);
	}

	public void removeKpiInstance(AssiEvaKpiInstance kpiInstance) {
		assiEvaKpiInstanceDao.removeKpiInstance(kpiInstance);
	}

	public void saveKpiInstance(AssiEvaKpiInstance kpiInstance) {
		assiEvaKpiInstanceDao.saveKpiInstance(kpiInstance);
	}

	public void genKpiInstance(String orgId, String genOperatorId) {
		// IAssiEvaTemplateKpiMgr templateKpiMgr = (IAssiEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("IassiEvaTemplateKpiMgr");
		// IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("IassiEvaOrgMgr");
		// // 获得任务
		// AssiEvaOrg org = orgMgr.getAssiEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// AssiEvaKpiInstance instance = new AssiEvaKpiInstance();
		// AssiEvaKpi kpi = (AssiEvaKpi) kpiIter.next();
		// instance.setTemplateId(org.getTemplateId());
		// instance.setKpiId(kpi.getId());
		// instance.setGenOperator(genOperatorId);
		// instance.setGenTime(DateTimeUtil
		// .getCurrentDateTime(AssiEvaConstants.DATE_FORMAT));
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
	public AssiEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId){
		return assiEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId);
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
		return assiEvaKpiInstanceDao.getKpiInstanceListByTimeAndPartner(taskDetailId, partnerId, timeType, startTime, endTime, isPublish);
	}
	
	public AssiEvaKpiInstance getKpiInstance(String orgId, String kpiId, String date) {
		IAssiEvaTemplateMgr templateMgr = (IAssiEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IassiEvaTemplateMgr");
		IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr) ApplicationContextHolder.getInstance()
				.getBean("IassiEvaOrgMgr");
		// 获得任务
		AssiEvaOrg org = orgMgr.getAssiEvaOrg(orgId);
		// 获得模板
		AssiEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		// 根据模板周期获得开始时间
		String startDate = AssiEvaStaticMethod.getStartTimeByCycle(template
				.getCycle(), date);
		// 根据模板周期获得结束时间
		String endDate = AssiEvaStaticMethod.getEndTimeByCycle(template.getCycle(),
				date);
		return assiEvaKpiInstanceDao.getKpiInstanceOfTemplate(orgId, kpiId,
				startDate, endDate);
	}

	public List listKpiOfTemplateWithScore(String orgId, String date) {
		// IAssiEvaTemplateKpiMgr templateKpiMgr = (IAssiEvaTemplateKpiMgr)
		// ApplicationContextHolder
		// .getInstance().getBean("IassiEvaTemplateKpiMgr");
		// IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr)
		// ApplicationContextHolder.getInstance()
		// .getBean("IassiEvaOrgMgr");
		// // 获得任务
		// AssiEvaOrg org = orgMgr.getAssiEvaOrg(orgId);
		// List kpiList = templateKpiMgr.listKpiOfTemplate(org.getTemplateId());
		// List list = new ArrayList();
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// AssiEvaKpi kpi = (AssiEvaKpi) kpiIter.next();
		// AssiEvaKpiInstance instance = getKpiInstance(orgId, kpi.getId(), date);
		// // 未生成实例则不加入列表
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// kpi.setAssiEvaScore(instance.getKpiScore());
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
			AssiEvaKpi kpi = (AssiEvaKpi) kpiIter.next();
			if (null != kpi.getAssiEvaScore()) {
				totalScore = totalScore + kpi.getAssiEvaScore().floatValue();
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
		return assiEvaKpiInstanceDao.id2Name(id);
	}
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public AssiEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId,final  String city){
		return assiEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId,city);
	}
}
