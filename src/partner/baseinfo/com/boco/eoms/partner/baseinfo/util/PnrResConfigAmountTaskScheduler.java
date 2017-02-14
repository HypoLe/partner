package com.boco.eoms.partner.baseinfo.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.collections.map.ListOrderedMap;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.interfaceMonitoring.util.interfaceMonitorin;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.partner.baseinfo.mgr.IPnrSourceStandardConfigService;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PnrSourceStandardConfig;

/**  
 * @Title: PnrResConfigAmountS.java
 * @Package com.boco.eoms.partner.baseinfo.util
 * @Description: 资源的标准配置，根据资源配置的标准来获取
 * 一个代维单位应配人员和实际到位人员
 * @author fengguangping 
 * @date Jan 23, 2013  4:28:54 PM  
 */
public class PnrResConfigAmountTaskScheduler implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		BocoLog.info(this, 0, "资源标准配置轮询开始");
		//解决取时间提前8小时的夏令时问题
//		TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8"); 
//		TimeZone.setDefault(tz); 
		Calendar cal=new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH,-1);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		String configType="1240201";//目前只统计人员配置类型
		int saveTimeLongVal=year*10000+month*100+31;
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) 
		ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		StringBuffer sb=new StringBuffer();
		sb.append("select * from ").append("(select rc.EXECUTE_dept,sc.STANDARD_CONFIG,rc.SPECIALTY,count(rc.RES_NAME) as rcCount,")
			.append("sc.CONFIG_DW,rc.COUNTRY")
			.append("  from pnr_res_config rc left join PNR_STANDARD_CONFIG sc")
			.append("	on rc.SPECIALTY=sc.PROFESSIONAL ")
			.append(" where  rc.EXECUTE_dept is not null and sc.isdeleted='0' and sc.config_type='"+configType+"'")
			.append(" group by rc.EXECUTE_dept,sc.STANDARD_CONFIG,rc.SPECIALTY,sc.CONFIG_DW,rc.COUNTRY)  t1 ")
			.append("full join ")
			.append("(select u.DEPT_ID,u.area_id,d.PROFESSIONAL,count(u.NAME) as userCount from pnr_user u,PARTNER_DWINFO d ")
			.append(" where u.USER_ID=d.WORKERID and u.DELETED='0' and d.ISDELETE='0' and u.savetimelongvalue<="+saveTimeLongVal)
			.append(	" group by u.DEPT_ID,d.PROFESSIONAL,u.area_id)  t2 ")
			.append("on ")
			.append(" t1.execute_dept=t2.dept_id and t1.specialty=t2.PROFESSIONAL");
		List<ListOrderedMap> list1=jdbcService.queryForList(sb.toString());
		IPnrSourceStandardConfigService sService=(IPnrSourceStandardConfigService)ApplicationContextHolder.getInstance()
		.getBean("pnrSourceStandardConfigService");
		PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)ApplicationContextHolder.
		getInstance().getBean("partnerDeptMgr");
		ID2NameService service = (ID2NameService) ApplicationContextHolder	.getInstance().getBean("ID2NameGetServiceCatch");
		for (int i = 0; i < list1.size(); i++) {
			ListOrderedMap listOrderedMap=list1.get(i);
			PnrSourceStandardConfig pnrSourceStandardConfig=new PnrSourceStandardConfig();
			pnrSourceStandardConfig.setAddTimeY(year);
			pnrSourceStandardConfig.setAddTimeM(month);
			pnrSourceStandardConfig.setIsdeleted("0");
			pnrSourceStandardConfig.setAddUser("admin");
			pnrSourceStandardConfig.setConfigType(configType);
			pnrSourceStandardConfig.setSaveTime(StatisticMethod.dateToString(new Date(), "yyyy-MM-dd"));
			int j=0;
			for(Object object : listOrderedMap.keySet()) {
				j++;
				String keyString=object.toString().toLowerCase();//兼容Oracle和informix
				String  keyValue=StaticMethod.nullObject2String(listOrderedMap.get(object));
				boolean notNullFlag=!"".equals(keyValue);//是否为空的标志，为空
				if (keyString.equals("usercount")) {
					String keyValue1=keyValue;
					if ("".equals(keyValue)) {
						keyValue1="0";
					}
					pnrSourceStandardConfig.setActualConfig(Integer.parseInt(keyValue1));
				}else if (keyString.equals("rccount")) {
					String keyValue1=keyValue;
					if ("".equals(keyValue)) {
						keyValue1="0";
					}
					pnrSourceStandardConfig.setSourceAmount(Integer.parseInt(keyValue1));
				}else if(keyString.equals("dept_id")&&notNullFlag){
					pnrSourceStandardConfig.setCompanyMagId(keyValue);
				}else if(keyString.equals("execute_dept")&&notNullFlag){
					pnrSourceStandardConfig.setCompanyMagId(keyValue);
				}else if(keyString.equals("dept_name")){
					pnrSourceStandardConfig.setCompanyName(keyValue);
				}else if(keyString.equals("country")&&notNullFlag){
					pnrSourceStandardConfig.setAreaId(keyValue);
					pnrSourceStandardConfig.setAreaName(service.id2Name(keyValue, "tawSystemAreaDao"));
					if (keyValue.length()==2) {
						pnrSourceStandardConfig.setProvinceId(keyValue);
						pnrSourceStandardConfig.setCityId(keyValue);
						pnrSourceStandardConfig.setCountryId(keyValue);
					}else if (keyValue.length()==4) {
						pnrSourceStandardConfig.setProvinceId(keyValue.substring(0, 2));
						pnrSourceStandardConfig.setCityId(keyValue);
						pnrSourceStandardConfig.setCountryId(keyValue);
					}else if (keyValue.length()==6) {
						pnrSourceStandardConfig.setProvinceId(keyValue.substring(0, 2));
						pnrSourceStandardConfig.setCityId(keyValue.substring(0, 4));
						pnrSourceStandardConfig.setCountryId(keyValue);
					}
				}else if(keyString.equals("area_id")&&notNullFlag){
					pnrSourceStandardConfig.setAreaId(keyValue);
					pnrSourceStandardConfig.setAreaName(service.id2Name(keyValue, "tawSystemAreaDao"));
					System.out.print(pnrSourceStandardConfig.getAreaName());
					if (keyValue.length()==2) {
						pnrSourceStandardConfig.setProvinceId(keyValue);
						pnrSourceStandardConfig.setCityId(keyValue);
						pnrSourceStandardConfig.setCountryId(keyValue);
					}else if (keyValue.length()==4) {
						pnrSourceStandardConfig.setProvinceId(keyValue.substring(0, 2));
						pnrSourceStandardConfig.setCityId(keyValue);
						pnrSourceStandardConfig.setCountryId(keyValue);
					}else if (keyValue.length()==6) {
						pnrSourceStandardConfig.setProvinceId(keyValue.substring(0, 2));
						pnrSourceStandardConfig.setCityId(keyValue.substring(0, 4));
						pnrSourceStandardConfig.setCountryId(keyValue);
					}
				}else if(keyString.equals("config_dw")){
					pnrSourceStandardConfig.setConfigDw(keyValue);
				}else if(keyString.equals("professional")&&notNullFlag){
					pnrSourceStandardConfig.setProfessional(keyValue);
				}else if(keyString.equals("specialty")&&notNullFlag){
					pnrSourceStandardConfig.setProfessional(keyValue);
				}else if(keyString.equals("config_type")){
					pnrSourceStandardConfig.setConfigType(keyValue);
				}else if(keyString.equals("standard_config")){
					String keyValue1=keyValue;
					if ("".equals(keyValue)) {
						keyValue1="0";
					}
					pnrSourceStandardConfig.setStandardConfig(Integer.parseInt(keyValue1));
				}
			}
			String whereStr=" and partnerDept.deptMagId='"+pnrSourceStandardConfig.getCompanyMagId()+"'";
			List<PartnerDept> deptList=partnerDeptMgr.getPartnerDepts(whereStr);
			if (deptList!=null&&deptList.size()>0) {
				PartnerDept dept=new PartnerDept();
				dept=deptList.get(0);
				pnrSourceStandardConfig.setCompanyId(StaticMethod.null2String(dept.getId()));
				pnrSourceStandardConfig.setCompanyName(StaticMethod.null2String(dept.getName()));
			}
			int act=pnrSourceStandardConfig.getActualConfig();
			double total=pnrSourceStandardConfig.getSourceAmount();
			int standar=pnrSourceStandardConfig.getStandardConfig();
			int standardAmout=0;
			double rate=0;
			if (standar==0) {
				standardAmout=0;//根据资源数量应该配置的人员
				pnrSourceStandardConfig.setConfigRate(rate);
			}else {
				standardAmout=(int)Math.ceil(total/standar);//根据资源数量应该配置的人员
				rate=act/(double)standardAmout;
				pnrSourceStandardConfig.setConfigRate(rate);
			}
			pnrSourceStandardConfig.setStandardAmout(standardAmout);
			sService.save(pnrSourceStandardConfig);
		}
		BocoLog.info(this, 0, "资源标准配置轮询结束");
	}
}
