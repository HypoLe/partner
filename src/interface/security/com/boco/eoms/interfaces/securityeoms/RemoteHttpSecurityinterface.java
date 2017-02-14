/**
 * RemoteHttpBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.boco.eoms.interfaces.securityeoms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;

public class RemoteHttpSecurityinterface {

	public RemoteUser[] getAccount(java.lang.String flag) throws Exception {

		ITawSystemUserManager mgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("itawSystemUserManager");

		RemoteUser[] remote = {};

		if (null == flag) {
			return null;
		} else if ("".equals(flag)) {
			List allUserList = mgr.getTawSystemUsers(new TawSystemUser());
			remote = new RemoteUser[allUserList.size()];
			for (int i = 0; i < allUserList.size(); i++) {
				TawSystemUser tawTawSystemUser = (TawSystemUser) allUserList
						.get(i);
				RemoteUser remoteUser = new RemoteUser();
				remoteUser.setFax(tawTawSystemUser.getFax());
				remoteUser.setMail(tawTawSystemUser.getEmail());
				remoteUser.setMobile(tawTawSystemUser.getMobile());
				remoteUser.setName(tawTawSystemUser.getUsername());
				remoteUser.setPost("");
				remoteUser.setPwd(tawTawSystemUser.getPassword());
				remoteUser.setSex(tawTawSystemUser.getSex());
				remoteUser.setTelephone(tawTawSystemUser.getPhone());
				remoteUser.setUid(tawTawSystemUser.getUserid());
				remote[i] = remoteUser;
			}
		} else {
			if (null == mgr.getTawSystemUserByuserid(flag)) {
				return null;
			}
			remote = new RemoteUser[1];
			TawSystemUser tawSystemUser = mgr.getTawSystemUserByuserid(flag);
			RemoteUser remoteUser = new RemoteUser();
			remoteUser.setFax(tawSystemUser.getFax());
			remoteUser.setMail(tawSystemUser.getEmail());
			remoteUser.setMobile(tawSystemUser.getMobile());
			remoteUser.setName(tawSystemUser.getUsername());
			remoteUser.setPost("");
			remoteUser.setPwd(tawSystemUser.getPassword());
			remoteUser.setSex(tawSystemUser.getSex());
			remoteUser.setTelephone(tawSystemUser.getPhone());
			remoteUser.setUid(tawSystemUser.getUserid());
			remote[0] = remoteUser;
		}

		return remote;
	}

	public boolean modifypwd(java.lang.String uid, java.lang.String pwd)
			throws java.rmi.RemoteException {
		ITawSystemUserManager mgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("itawSystemUserManager");
		try {
			if (null == mgr.getTawSystemUserByuserid(uid)) {
				return false;
			}
			TawSystemUser tawSystemUser = mgr.getTawSystemUserByuserid(uid);
			mgr.updatePassword(tawSystemUser, pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
