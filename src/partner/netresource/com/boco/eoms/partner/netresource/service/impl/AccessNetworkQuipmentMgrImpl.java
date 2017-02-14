package com.boco.eoms.partner.netresource.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectLink;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectMapping;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectLinkService;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectMappingService;
import com.boco.eoms.partner.netresource.dao.AccessNetworkQuipmentDao;
import com.boco.eoms.partner.netresource.model.bs.AccessNetworkQuipment;
import com.boco.eoms.partner.netresource.model.bs.BsBtQuipment;
import com.boco.eoms.partner.netresource.service.AccessNetworkQuipmentMgr;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 
 * @author:     chenbing 
 * @version:    1.0 
 */ 
public class AccessNetworkQuipmentMgrImpl extends CommonGenericServiceImpl<AccessNetworkQuipment> implements AccessNetworkQuipmentMgr {

	private AccessNetworkQuipmentDao anQuipmentDao;

	
	public AccessNetworkQuipmentDao getAnQuipmentDao() {
		return anQuipmentDao;
	}

	public void setAnQuipmentDao(AccessNetworkQuipmentDao anQuipmentDao) {
		this.anQuipmentDao = anQuipmentDao;
		this.setCommonGenericDao(anQuipmentDao);
	}

	public List<AccessNetworkQuipment> getQuipmentByName(String deviceNumber) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" from AccessNetworkQuipment p where p.deviceNumber='").append(deviceNumber).append("'");
		
		List<AccessNetworkQuipment> list =anQuipmentDao.findByHql(hql.toString());
		
		return list;
	}
	/**
	 * 用于同步功能 设备关联表
	 * 1 索取所有的数据 
	 * 2 mapping 中查看没有关系
	 */
	public void synchronousAnQuitment() throws Exception {
		List<AccessNetworkQuipment> needInsert = new ArrayList<AccessNetworkQuipment>();
		List<AccessNetworkQuipment> needFinal = new ArrayList<AccessNetworkQuipment>();
		PnrInspectMappingService pnrInspectMappingService;
		PnrInspectLinkService pnrInspectLinkService;
		PnrResConfigMgr pnrResService;
		
		StringBuffer hql = new StringBuffer();
		hql.append("select new AccessNetworkQuipment(id,network,deviceType,anrId,deviceNumber) from AccessNetworkQuipment");
		
		List<AccessNetworkQuipment> list =anQuipmentDao.findByHql(hql.toString());
		
		int totalNum = list.size();
		if(totalNum>0){
			pnrInspectMappingService =(PnrInspectMappingService)ApplicationContextHolder.getInstance().getBean("pnrInspectMappingService");
			//找到有网络关系的
			for(int i =0;i<totalNum;i++){
				AccessNetworkQuipment bq = list.get(i);
				List<PnrInspectMapping> pList =pnrInspectMappingService.getPnrInspectMapping(bq.getNetwork(),bq.getDeviceType());
				if(pList.size()>0){
					needInsert.add(bq);
				}
			}
			
			//在关系表中不存在的
			
			int needSize = needInsert.size();
			if(needSize>0){
				
				pnrInspectLinkService=(PnrInspectLinkService)ApplicationContextHolder.getInstance().getBean("pnrInspectLinkService");
				
				for(int j=0 ;j<needSize;j++){
					AccessNetworkQuipment bqneed = needInsert.get(j);
					
					Search search = new Search();
					search.addFilterEqual("netResId", bqneed.getId());
					SearchResult sr =pnrInspectLinkService.searchAndCount(search);
					
					int size = sr.getTotalCount();
					
					if(size==0){
						needFinal.add(bqneed);
					}
					
				}
				
				//开始插入数据
				PnrInspectLink ps;
				int needFinalSize = needFinal.size();
				if(needFinalSize>0){
					
					pnrResService=(PnrResConfigMgr)ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
					PnrInspectMapping pm=null;

					for(int k=0 ;k <needFinalSize;k++){
						AccessNetworkQuipment bqfinal = needFinal.get(k);

						ps = new PnrInspectLink();
						
						List<PnrInspectMapping> p =pnrInspectMappingService.getPnrInspectMapping(bqfinal.getNetwork(),bqfinal.getDeviceType());
						pm = p.get(0);
						
						ps.setId(UUIDHexGenerator.getInstance().getID());
						//通过资源ID 去找巡检资源
						List<PnrResConfig> plist=pnrResService.getPnrResConfigByResId(bqfinal.getAnrId());
						//巡检资源ID
						if(plist.size()>0){					
							ps.setInspectId(plist.get(0).getId());
						}
						//设备所属网络及设备类别 与 专业关联表
						ps.setInspectMappingId(pm.getId());
						//设备ID
						ps.setNetResId(bqfinal.getId());
						//字典值(专业)网络资源分类
						ps.setDeviceSpecialtyName(pm.getDeviceSpecialtyName());
						//所属网络名称
						ps.setDeviceTypeName(pm.getDeviceTypeName());
						//设备表名
						ps.setNetresTableName(pm.getNetresTableName());
//						网络资源名称 --设备的名字
						ps.setNetresName(bqfinal.getAnqName());
//						设备的字段（设备类别）
						ps.setNetresFieldName(pm.getNetresFieldName());
						// 设备类别的字典值
						ps.setNetresFieldValue(pm.getNetresFieldValue());
						
						pnrInspectLinkService.save(ps);
						
					}
					
				}

			}
			
		}
		
		
		
	}

}
