package com.boco.eoms.sheet.interfaceBase.service.impl;

import java.util.Date;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.sheet.interfaceBase.model.WfInterfaceInfo;
import com.boco.eoms.sheet.interfaceBase.service.IWfInterfaceInfoManager;
import com.boco.eoms.sheet.interfaceBase.service.IWfInterfaceOperateManager;
import com.boco.eoms.sheet.interfaceBase.util.InterfaceConstants;
import com.boco.eoms.sheet.base.model.BaseLink;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.service.ILinkService;
import com.boco.eoms.sheet.base.service.IMainService;

public abstract class WfInterfaceOperateManagerAbstract implements IWfInterfaceOperateManager{
	public boolean sendData(WfInterfaceInfo info) {
		boolean result = false;
		try{
			String mainBeanId = info.getMainBeanId();
			String linkBeanId = info.getLinkBeanId();
			
			IMainService iMainService = (IMainService)ApplicationContextHolder.getInstance().getBean(mainBeanId);
			ILinkService iLinkService = (ILinkService)ApplicationContextHolder.getInstance().getBean(linkBeanId);
			
			BaseMain main = iMainService.getSingleMainPO(info.getSheetKey());
			BaseLink link = iLinkService.getSingleLinkPO(info.getLinkId());
			
			System.out.println("isSended=" + info.getIsSended());
			System.out.println("UN_SEND=" + InterfaceConstants.UN_SEND);
			System.out.println("IS_UNREADY=" + InterfaceConstants.IS_UNREADY);
			if(info.getIsSended()==null || info.getIsSended().equals("") || info.getIsSended().equals(InterfaceConstants.UN_SEND)){
				System.out.println("start sendFlowInterfaceData");
				result = sendFlowInterfaceData(main,link,info.getInterfaceType(),info.getMainBeanId(),info.getServiceType());
			}
			else if(info.getIsSended().equals(InterfaceConstants.IS_UNREADY)){
				System.out.println("start dealUnReadyData");
				result = this.dealUnReadyData(main,link,info.getInterfaceType(),info.getMainBeanId(),info.getServiceType());
			}
			if(result){
				IWfInterfaceInfoManager infoManager = (IWfInterfaceInfoManager) ApplicationContextHolder
				.getInstance().getBean("iWfInterfaceInfoManager");	
				
				info.setIsSended("1");
				info.setSendTime(new Date());
				infoManager.saveOrUpdateWfInterfaceInfo(info);
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		return result;
	}

	public abstract boolean sendFlowInterfaceData(BaseMain main, BaseLink link,String interfaceType,String methodType,String serviceType);
	public boolean dealUnReadyData(BaseMain main, BaseLink link,String interfaceType,String methodType,String serviceType){
		return false;
	}

}
