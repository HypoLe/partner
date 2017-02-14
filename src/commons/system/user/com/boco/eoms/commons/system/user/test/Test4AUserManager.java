package com.boco.eoms.commons.system.user.test;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.boco.eoms.commons.system.user.webClient.EomsPlatFormServiceServiceLocator;
import com.boco.eoms.commons.system.user.webClient.EomsPlatFormService_PortType;

public class Test4AUserManager {

	String returnString = "";
	EomsPlatFormServiceServiceLocator locator = new EomsPlatFormServiceServiceLocator();
	EomsPlatFormService_PortType eomsPlatFormService_PortType = null;

	public String testGetAllSubAccount() {
		String params = "<params></params>";
		try {
			eomsPlatFormService_PortType = locator.getEomsPlatFormService();
			returnString = eomsPlatFormService_PortType.service(
					"BOCO.PARTNER.4A.GETSUMSUBACCOUNT", "", "", "", "", "",
					params);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public String testGetAllSubAccountInfo() {
		String params = "<params></params>";
		try {
			eomsPlatFormService_PortType = locator.getEomsPlatFormService();
			returnString = eomsPlatFormService_PortType.service(
					"BOCO.PARTNER.4A.GETALLSUBACCOUNT", "", "", "", "", "",
					params);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public String testGetAllSubAccountInfoPage() {
		String params = "<params><pageSize>15</pageSize><pageNum>2</pageNum></params>";
		try {
			eomsPlatFormService_PortType = locator.getEomsPlatFormService();
			returnString = eomsPlatFormService_PortType.service(
					"BOCO.PARTNER.4A.GETALLSUBACCPAGE", "", "", "", "", "",
					params);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public String testGetAllSubAccountInfoByAccId() {
		String params = "<params><accId>ceshi</accId></params>";
		try {
			eomsPlatFormService_PortType = locator.getEomsPlatFormService();
			returnString = eomsPlatFormService_PortType
					.service("BOCO.PARTNER.4A.GETSUBACCOUNT", "", "", "", "",
							"", params);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public String testSaveUser() {
		String params = "<params><userInfos><?xml version=\"1.0\" encoding=\"UTF-8\"?><accounts>"
				+ "<account>"
				+ "<accId>lzj111</accId>"
				+ "<userPassword>111444</userPassword>"
				+ "<name>lzj</name>"
				+ "<email>aaaa@boco.com.cn</email>"
				+ "<gender>1</gender>"
				+ "<mobile>13888888888</mobile>"
				+ "<employeeNumber>boco123456</employeeNumber>"
				+ "<o>1</o>"
				+ "<supporterDept>值班管理测试11</supporterDept>"
				+ "<facsimileTelephoneNumber>44444444</facsimileTelephoneNumber>"
				+ "<telephoneNumber>13333333333</telephoneNumber>"
				+ "<passwordModifiedDate></passwordModifiedDate>"
				+ "</account></accounts></userInfos></params>";
		try {
			eomsPlatFormService_PortType = locator.getEomsPlatFormService();
			returnString = eomsPlatFormService_PortType
					.service("BOCO.PARTNER.4A.ADDSUBACCOUNT", "", "", "", "",
							"", params);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public String testUpdateUser() {
		String params = "<params><userInfos><?xml version=\"1.0\" encoding=\"UTF-8\"?><accounts>"
				+ "<account>"
				+ "<accId>lzj111</accId>"
				+ "<userPassword>111444</userPassword>"
				+ "<name>lzj测试</name>"
				+ "<email>sunshengtaiTest@boco.com.cn</email>"
				+ "<gender>1</gender>"
				+ "<mobile>13888888888</mobile>"
				+ "<employeeNumber>boco123456789</employeeNumber>"
				+ "<o>1</o>"
				+ "<supporterDept>省公司</supporterDept>"
				+ "<facsimileTelephoneNumber>44444444</facsimileTelephoneNumber>"
				+ "<telephoneNumber>13333333333</telephoneNumber>"
				+ "<passwordModifiedDate></passwordModifiedDate>"
				+ "</account></accounts></userInfos></params>";
		try {
			eomsPlatFormService_PortType = locator.getEomsPlatFormService();
			returnString = eomsPlatFormService_PortType.service(
					"BOCO.PARTNER.4A.EDITSUBACCOUNT", "", "", "", "", "",
					params);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public String testDelUser() {
		String params = "<params><userInfos><?xml version=\"1.0\" encoding=\"UTF-8\"?><accounts>"
				+ "<accId>lzj111</accId>" + "</accounts></userInfos></params>";
		try {
			eomsPlatFormService_PortType = locator.getEomsPlatFormService();
			returnString = eomsPlatFormService_PortType
					.service("BOCO.PARTNER.4A.DELSUBACCOUNT", "", "", "", "",
							"", params);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public static void main(String args[]) {
		Test4AUserManager test4AUserManager = new Test4AUserManager();
		// 得到所有用户数量
		// System.out.println(test4AUserManager.testGetAllSubAccount());
		// 得到所有用户的信息
		// System.out.println("all User:"
		// + test4AUserManager.testGetAllSubAccountInfo());
		// 通过accId到底用户信息
		System.out.println("user admin:"
				+ test4AUserManager.testGetAllSubAccountInfoByAccId());
		// 得到所有用户的信息分页
		// System.out.println(test4AUserManager.testGetAllSubAccountInfoPage());
		// 新增用户
		// System.out.println(test4AUserManager.testSaveUser());
		// 修改用户
		// System.out.println(test4AUserManager.testUpdateUser());
		// 删除用户
		// System.out.println(test4AUserManager.testDelUser());
	}
}
