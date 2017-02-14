package com.boco.eoms.Bulletin.mgr.impl;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.boco.eoms.Bulletin.mgr.BulletinMgr;
import com.huawei.csp.si.service.BulletinServiceLocator;

public class BulletinServiceMgrImpl implements BulletinMgr{

	public String confirmBulletin(Integer in0, Integer in1, String in2,
			String in3, String in4, String in5, String in6, String in7,
			String in8, String in9, String in10, String in11, String in12,
			String in13,String in14) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("CRM====>BulletinServiceMgrImpl====>confirmBulletin");
		BulletinServiceLocator bing = new BulletinServiceLocator();
		try {
			com.huawei.csp.si.service.Bulletin_PortType service = bing.getBulletin();
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
		System.out.println("CRM====>BulletinServiceMgrImpl====>confirmBulletin");
		BulletinServiceLocator bing = new BulletinServiceLocator();
		try {
			com.huawei.csp.si.service.Bulletin_PortType service = bing.getBulletin();
			service.newBulletin(in0, in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
