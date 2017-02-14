package com.boco.eoms.Bulletin.mgr.impl;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.boco.eoms.Bulletin.mgr.BulletinMgr;
import com.boco.eoms.commons.interfaceMonitoring.mgr.InterfaceMonitoringMgr;
import com.huawei.csp.si.service.BulletinLocator;
import com.huawei.csp.si.service.BulletinPortType;

public class BulletinMgrImpl implements BulletinMgr{

	public String confirmBulletin(Integer in0, Integer in1, String in2,
			String in3, String in4, String in5, String in6, String in7,
			String in8, String in9, String in10, String in11, String in12,
			String in13,String in14) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("CRM====>BulletinMgrImpl====>confirmBulletin");
		BulletinLocator bing = new BulletinLocator();
		try {
			BulletinPortType service = bing.getBulletinHttpPort();
			service.confirmBulletin(in0, in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String newBulletin(Integer in0, Integer in1, String in2, String in3,
			String in4, String in5, String in6, String in7, String in8,
			String in9, String in10, String in11, String in12, String in13,String in14)
			throws RemoteException {
		System.out.println("CRM====>BulletinMgrImpl====>newBulletin");
		BulletinLocator bing = new BulletinLocator();
		try {
			BulletinPortType service = bing.getBulletinHttpPort();
			service.newBulletin(in0, in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
		// TODO Auto-generated catch block

}
