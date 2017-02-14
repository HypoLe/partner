package com.boco.eoms.partner.assess.AssAutoCollection.scheduler;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssKpiConfigMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssSelectedInstanceMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssKpiConfig;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssSelectedInstance;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionConfigMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionQueryJdbcMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionResultMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionTypeMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionConfig;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionResult;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionType;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssKpiInstanceMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskDetailMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskMgr;
import com.boco.eoms.partner.assess.AssExecute.model.AssKpiInstance;
import com.boco.eoms.partner.assess.AssExecute.model.AssTask;
import com.boco.eoms.partner.assess.AssExecute.model.AssTaskDetail;
import com.boco.eoms.partner.assess.AssFactory.mgr.IAssFactoryMgr;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssReportInfoMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;
import com.boco.eoms.partner.assess.AssRight.util.AssRoleIdList;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssKpiMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTemplateMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTreeMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.AssTree.model.AssTemplate;
import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.partner.assess.util.AssStaticMethod;
import com.boco.eoms.partner.assess.util.DateTimeUtil;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;

public class AutoCollectionScheduler implements Job {

	public void execute(JobExecutionContext context)
			throws org.quartz.JobExecutionException {
		System.out.println("测试一下"+StaticMethod.getLocalString());
		
		AssCollectionTypeMgr assCollectionTypeMgr = (AssCollectionTypeMgr) ApplicationContextHolder
		.getInstance().getBean("assCollectionTypeMgr");
		AssCollectionConfigMgr assCollectionConfigMgr = (AssCollectionConfigMgr) ApplicationContextHolder
		.getInstance().getBean("assCollectionConfigMgr");
		AssCollectionQueryJdbcMgr assCollectionQueryJdbcMgr = (AssCollectionQueryJdbcMgr) ApplicationContextHolder
		.getInstance().getBean("assCollectionQueryJdbcMgr");
		AssCollectionResultMgr assCollectionResultMgr = (AssCollectionResultMgr) ApplicationContextHolder
		.getInstance().getBean("assCollectionResultMgr");
		
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) ApplicationContextHolder
		.getInstance().getBean("ItranAssKpiConfigMgr");
		IAssTreeMgr assTreeMgr = (IAssTreeMgr) ApplicationContextHolder
		.getInstance().getBean("ItranAssTreeMgr");
		IAssSelectedInstanceMgr instanceMgr = (IAssSelectedInstanceMgr) ApplicationContextHolder
		.getInstance().getBean("ItranAssSelectedInstanceMgr");
		IAssTemplateMgr assTemplateMgr = (IAssTemplateMgr) ApplicationContextHolder
		.getInstance().getBean("ItranAssTemplateMgr");
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) ApplicationContextHolder
		.getInstance().getBean("ItranAssFactoryMgr");
		IAssTaskMgr taskMgr = (IAssTaskMgr) ApplicationContextHolder
		.getInstance().getBean("ItranAssTaskMgr");
		IAssTaskDetailMgr taskDetailMgr = (IAssTaskDetailMgr) ApplicationContextHolder
		.getInstance().getBean("ItranAssTaskDetailMgr");
		IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) ApplicationContextHolder
		.getInstance().getBean("ItranAssKpiInstanceMgr");
		IAssKpiMgr assKpiMgr = (IAssKpiMgr) ApplicationContextHolder
		.getInstance().getBean("ItranAssKpiMgr");
		IAssReportInfoMgr assReportInfoMgr = (IAssReportInfoMgr) ApplicationContextHolder
		.getInstance().getBean("ItranAssReportInfoMgr");
		List assKpiList = assKpiMgr.getAssKpis(" and isCollection ='collection' ");
//		取得地市
		AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder.getInstance().getBean("lineAssRoleIdList");
		String rootAreaId = roleIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(rootAreaId);
		
		AssSelectedInstance instance = null;
		
		String localTime = StaticMethod.getLocalString();
		String month = localTime.substring(5, 7);
		String year = localTime.substring(0, 4);
		String nowTime = year+month;
		if("01".equals(month)){
			year = String.valueOf(Integer.parseInt(year)-1);
			month = "12";
		}else{
			if(Integer.parseInt(month)>10){
				month = String.valueOf(Integer.parseInt(month)-1);
			}else{
				month = "0"+String.valueOf(Integer.parseInt(month)-1);
			}
		}
		int monthInt = Integer.parseInt(month);
		String quarter = "01";
		if(monthInt<4){
			quarter = "04";
		}else if(monthInt<7){
			quarter = "01";
		}else if(monthInt<10){
			quarter = "02";
		}else if(monthInt<13){
			quarter = "03";
		}
		String save = "yes";
//		循环地市 ，合作伙伴
		for(int k=0;k<city.size();k++){
//			得到地市
			TawSystemArea tawSystemArea = (TawSystemArea)city.get(k);
//			根据地市取得合作伙伴
			List partnerList = assFactoryMgr.getPartnerDepts(tawSystemArea.getAreaid(), "112131101");
			for(int f=0;f<partnerList.size();f++){
				for(int i=0;i<assKpiList.size();i++){
					String time = year ;
					String afterTime = "";
					AssKpi assKpi = (AssKpi)assKpiList.get(i);
					AssTree assTree = assTreeMgr.getNodeByObjId("",assKpi.getId());
					AssTree assTree1 = assTreeMgr.getTreeNodeByNodeId(assTree.getNodeId().substring(0, 7));
					AssTemplate assTemplate = assTemplateMgr.getTemplate(assTree1.getObjectId());
					List assKpiConfigList = assKpiConfigMgr.getAssKpiConfigs(" where kpiId = '"+assKpi.getId()+"' and node_type ='group'");
					List taskList = taskMgr.listTaskOfTpl(assTemplate.getId());
					AssTask assTask = (AssTask)taskList.get(0);
					if("quarter".equals(assTemplate.getCycle())){ 
						time = time + quarter; 
						if("01".equals(quarter)){
							afterTime = String.valueOf(Integer.parseInt(year)-1)+"04";
						}else{
							afterTime = year+"0"+String.valueOf(Integer.parseInt(quarter)-1);
						}
					} else if ("month".equals(assTemplate.getCycle())) {
						time = time + month;
						if("01".equals(month)){
							afterTime = String.valueOf(Integer.parseInt(year)-1)+"12";
						}else {
							if(Integer.valueOf(month)<11){
								afterTime = year+"0"+String.valueOf(Integer.parseInt(month)-1);
							}else {
								afterTime = year+String.valueOf(Integer.parseInt(month)-1);
							}
						}
					}
					float allWeight = 0;
					float kpiWeight = assTree.getWeight();
					AssTaskDetail assTaskDetail = taskDetailMgr.getTaskDetail(" and kpiId = '"+assKpi.getId()+"' and taskId ='"+assTask.getId()+"'");
					AssReportInfo report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), tawSystemArea.getAreaid(), assTemplate.getCycle(), time, String.valueOf(partnerList.get(f)), "1");
//					上月数据以执行 才进行采集
					String deviceNum = "1";
					if(!"".equals(report.getId())&&report.getId()!=null){
						deviceNum = report.getDeviceNum();
					}
					for(int j=0;j<assKpiConfigList.size();j++){
						AssKpiConfig assKpiConfig = (AssKpiConfig)assKpiConfigList.get(j);
						if("collection".equals(assKpiConfig.getIsCollection())){
							AssCollectionType assCollectionType = assCollectionTypeMgr.getAssCollectionTypeByNodeId(assKpiConfig.getCollectionType());
							AssCollectionConfig assCollectionConfig = assCollectionConfigMgr.getAssCollectionConfigByNodeId(assKpiConfig.getCollectionConfig());
	//						进行判断,如果 周期是年并且不是一月不进行保存，如果周期是季度并且不是1月份4月份7月份10月份不进行保存
							if("quarter".equals(assTemplate.getCycle())&&(!"01".equals(month)||!"04".equals(month)||!"07".equals(month)||!"10".equals(month))){
								save = "no";
							} else if ("year".equals(assTemplate.getCycle())&&!"01".equals(month)){
								save = "no";
							}
							if("yes".equals(save)){
								//判断填写值是否在值域范围内
								boolean showGroup = AssStaticMethod.isInBound(deviceNum,assKpiConfig.getNumRelation(),assKpiConfig.getNumConfig());
								if(showGroup||assKpiConfig.getNumConfig()==null){
	//								拼写查询的字符串
									StringBuffer sqlsb = new StringBuffer();
									sqlsb.append(assCollectionConfig.getSql());
									sqlsb.append(" where 1=1 ");
									if(!"".equals(assCollectionConfig.getAreaColumn())){
	//									增加地市查询
										sqlsb.append(" and ");
										sqlsb.append(assCollectionConfig.getAreaColumn());
										sqlsb.append(" = '");
										sqlsb.append(tawSystemArea.getAreaid());
										sqlsb.append("'");
									}
									if(!"".equals(assCollectionConfig.getPartnerColumn())){
	//									增加合作伙伴查询
										sqlsb.append(" and ");
										sqlsb.append(assCollectionConfig.getPartnerColumn());
										sqlsb.append(" = '");
										sqlsb.append(partnerList.get(f));
										sqlsb.append("'");						
									}
									if(!"".equals(assCollectionConfig.getSpecialtyColumn())){
	//									增加设备专业查询
										sqlsb.append(" and ");
										sqlsb.append(assCollectionConfig.getSpecialtyColumn());
										sqlsb.append(" = '");
										sqlsb.append(assTemplate.getDeviceType());
										sqlsb.append("'");						
									}	
									if(!"".equals(assCollectionConfig.getTimeColumn())){
	//									增加时间查询
										sqlsb.append(" and ");
										sqlsb.append(assCollectionConfig.getTimeColumn());
										sqlsb.append(" > '");
	//									获得上一周期的开始时间
										sqlsb.append(DateTimeUtil.getStartTimeByCycleAfter(assTemplate.getCycle()));
										sqlsb.append("' and ");
										sqlsb.append(assCollectionConfig.getTimeColumn() );
										sqlsb.append("< '");
	//									获得上一周期的结束时间
										sqlsb.append(DateTimeUtil.getEndTimeByCycleAfter(assTemplate.getCycle()));
										sqlsb.append("'");
									}					
									float result = assCollectionQueryJdbcMgr.getAssCollectionResult(assCollectionType.getServiceAddr(), assCollectionType.getUserName(),
											assCollectionType.getPassword(), "com.informix.jdbc.IfxDriver", sqlsb.toString());
									//如果是直接折算则不除以设备数，如果是设备折算则除以设备数
									if("2".equals(assKpiConfig.getAlgorithm())){
										result = result/Float.parseFloat(deviceNum);
									}
									AssCollectionResult assCollectionResult = new AssCollectionResult();
									assCollectionResult.setArea(tawSystemArea.getAreaid());
									assCollectionResult.setCycle(assTemplate.getCycle());
									assCollectionResult.setPartner(String.valueOf(partnerList.get(f)));
									assCollectionResult.setResult(String.valueOf(result));
									assCollectionResult.setTime(nowTime);
									assCollectionResult.setTreeNodeId(assKpiConfig.getId());
									assCollectionResult.setType(assTemplate.getDeviceType());
									assCollectionResult.setKpiId(assKpiConfig.getKpiId());
									assCollectionResult.setCreatTime(StaticMethod.getLocalString());
									assCollectionResultMgr.saveAssCollectionResult(assCollectionResult);
									instance = new AssSelectedInstance();
									instance.setKpiId(assKpiConfig.getKpiId());
									instance.setTaskId(assTask.getId());
									instance.setPartnerId(String.valueOf(partnerList.get(f)));
									instance.setTime(nowTime);
									instance.setConfigId(assKpiConfig.getId());
									instance.setWeight(result);
									instance.setAreaId(tawSystemArea.getAreaid());
									instanceMgr.saveAssSelectedInstance(instance);				
									float lastWeight = assKpiConfigMgr.getLastWeightByRange(assKpiConfig.getNodeId(), result);
									allWeight = allWeight + lastWeight*kpiWeight/100;
								}
							}
						}								
					}
						
					AssKpiInstance assKpiInstance = new AssKpiInstance();
					assKpiInstance.setCity(tawSystemArea.getAreaid());
					assKpiInstance.setCreateTime(StaticMethod.getLocalString());
					assKpiInstance.setPartnerId(String.valueOf(partnerList.get(f)));
					assKpiInstance.setTime(nowTime);
					assKpiInstance.setTimeType(assTemplate.getCycle());
					assKpiInstance.setTaskId(assTask.getId());
					assKpiInstance.setTaskDetailId(assTaskDetail.getId());
					ID2NameService mgr = (ID2NameService) ApplicationContextHolder
					.getInstance().getBean("id2nameService");
					assKpiInstance.setPartnerName(mgr.id2Name(String.valueOf(partnerList.get(f)),"partnerDeptDao"));
					assKpiInstance.setRealScore(String.valueOf(allWeight));
					assKpiInstanceMgr.saveKpiInstance(assKpiInstance);						
				}
			}
		}		
	}

}
