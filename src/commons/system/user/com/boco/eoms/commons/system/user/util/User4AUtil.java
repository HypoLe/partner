package com.boco.eoms.commons.system.user.util;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.user.model.TawSystemUser;

public class User4AUtil {

	/**
	 * 解析参数，去掉头尾的标签
	 * 
	 * @param params
	 *            接口传过来的参数
	 * @return 实际要得到的参数
	 */
	public static String getString(String params) {
		String returnString = "";
		if (params != null && !params.equals("")) {
			if (params.indexOf("<userInfos>") != -1) {
				int i = params.indexOf("</userInfos>");
				int j = params.indexOf("<?xml");
				System.out.println(i);
				System.out.println(j);
				returnString = params.substring(j, i);
				System.out
						.println("returnString=====" + params.substring(j, i));
			}
			if (params.indexOf("<userInfos>") == -1) {
				int i = params.indexOf("</params>");
				int j = params.indexOf("<?xml");
				System.out.println(i);
				System.out.println(j);
				returnString = params.substring(j, i);
				System.out
						.println("returnString=====" + params.substring(j, i));
			}
		}
		return returnString;
	}

	/**
	 * 
	 * @param list
	 *            user对象的list集合
	 * @return xml字符串
	 */
	public static String xmlString(List list) {
		String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><accounts>";
		String xmlEnd = "</accounts>";
		String returnString = "";
		String xmlElement = "";
		TawSystemUser tempUser = (TawSystemUser) list.get(0);
		if (tempUser.getId() == null||tempUser.getId().equals("") ) {
			return xmlStart + xmlEnd;
		} else {
			if (list != null && list.size() > 0) {
				returnString = xmlStart;
				for (int i = 0; i < list.size(); i++) {
					TawSystemUser user = (TawSystemUser) list.get(i);
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyyMMddHHmmss");
					Date updateTime = null;
					String modifyTime = "";
					String time = user.getUpdatetime();
					if (time != null && !time.equals("")) {
						try {
							updateTime = dateFormat.parse(StaticMethod
									.null2String(time));
							modifyTime = format.format(updateTime) + "Z";
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					xmlElement += "<account><accId>" + user.getUserid()
							+ "</accId>" + "<name>" + user.getUsername()
							+ "</name>" + "<email>" + user.getEmail()
							+ "</email>" + "<userPassword>"
							+ user.getPassword() + "</userPassword>"
							+ "<mobile>" + user.getMobile() + "</mobile>"
							+ "<gender>" + user.getSex() + "</gender>"
							+ "<employeeNumber>" + "</employeeNumber>" + "<o>"
							+ user.getDeptid() + "</o>" + "<supporterDept>"
							+ user.getDeptname() + "</supporterDept>"
							+ "<facsimileTelephoneNumber>" + user.getFax()
							+ "</facsimileTelephoneNumber>"
							+ "<preferredMobile>" + user.getPhone()
							+ "</preferredMobile>" + "<passwordModifiedDate>"
							+ StaticMethod.null2String(modifyTime)
							+ "</passwordModifiedDate></account>";

				}
				returnString += xmlElement + xmlEnd;
			} else {
				returnString = xmlStart + xmlEnd;
			}
		}
		return returnString;
	}

	/**
	 * 解析xml字符串，新增和修改
	 * 
	 * @param param
	 * @return
	 */
	public static List getXmlString(String param) {
		List list = new ArrayList();
		String returnString = "";
		Accounts accounts = null;
		Digester digester = new Digester();

		try {
			digester.addObjectCreate("accounts", Accounts.class);
			digester.addObjectCreate("accounts/account", TawSystemUser.class);

			digester.addBeanPropertySetter("accounts/account/accId", "userid");
			digester.addBeanPropertySetter("accounts/account/name", "username");
			digester.addBeanPropertySetter("accounts/account/email");
			digester.addBeanPropertySetter("accounts/account/userPassword",
					"password");
			digester.addBeanPropertySetter("accounts/account/gender", "sex");
			digester.addBeanPropertySetter("accounts/account/mobile");
			digester.addBeanPropertySetter("accounts/account/employeeNumber");
			digester.addBeanPropertySetter("accounts/account/o", "deptid");
			digester.addBeanPropertySetter("accounts/account/supporterDept",
					"deptname");
			digester.addBeanPropertySetter(
					"accounts/account/facsimileTelephoneNumber", "fax");
			digester.addBeanPropertySetter("accounts/account/telephoneNumber",
					"phone");
			digester.addBeanPropertySetter(
					"accounts/account/passwordModifiedDate", "updatetime");

			digester.addSetNext("accounts/account", "addAccount");

			accounts = (Accounts) digester.parse(new StringReader(param));
			list = accounts.getAccountList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String saveXml(String userid, String flag) {
		String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><results>";
		String xmlEnd = "</results>";
		String returnString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><results>";
		if (flag.equals("")) {
			returnString = "";
		}

		returnString += xmlStart + "<result returncode=\"1300\"><accId>"
				+ userid + "</accId></result>";
		if (flag.equals("1")) {

		}
		return returnString;
	}

	/**
	 * 解析参数字符串，得到userid的list
	 * 
	 * @param params
	 * @return
	 */
	public static List getUserIds(String params) {
		List list = new ArrayList();
		List returnList = new ArrayList();
		String userId = "";
		Document doc = null;
		if (params != null && !params.equals("")) {
			if (params.indexOf("accounts") == -1) {
				try {
					doc = DocumentHelper.parseText(params);
					Element root = doc.getRootElement();
					Element accIds = root.element("accId");
					userId = accIds.getText();
					System.out.println("userId=======" + userId);
					if (userId != null && !userId.equals("")) {
						returnList.add(userId);
					}
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			} else {
				try {
					doc = DocumentHelper.parseText(params);
					Element root = doc.getRootElement();
					List usersList = root.elements("accId");
					if (usersList != null) {
						for (int i = 0; i < usersList.size(); i++) {
							userId = ((Element) usersList.get(i)).getText();
							returnList.add(userId);
						}
					}
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		}
		return returnList;
	}

	public static Map getPageParameters(String params) {
		Map map = new HashMap();
		Document doc = null;
		if (params != null && !params.equals("")) {
			try {
				doc = DocumentHelper.parseText(params);
				Element root = doc.getRootElement();
				Element pageSize = root.element("pageSize");
				Element pageNum = root.element("pageNum");
				String pageSizeText = pageSize.getText();
				String pageNumText = pageNum.getText();
				map.put("pageSize", pageSizeText);
				map.put("pageNum", pageNumText);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static void main(String args[]) {
		// String time = StaticMethod.getCurrentDateTime("yyyyMMddHHmmss");
		// SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// Date modifytime = null;
		// String ddd = "";
		// try {
		// modifytime = format.parse(time);
		// ddd = format.format(modifytime);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// System.out.println(time);
		// System.out.println(ddd);

		User4AUtil ua = new User4AUtil();
		// ITawSystemUserManager users = (ITawSystemUserManager)
		// ApplicationContextHolder
		// .getInstance().getBean("itawSystemUserManager");
		// List list = new ArrayList();
		// list.add(users.getTawSystemUserByuserid("admin"));
		// System.out.println(list.size() + "44444444444444444444444");
		// String str = ua.xmlString(list);
		// System.out.println(str);
		String param = "<params><userInfos><?xml version=\"1.0\" encoding=\"UTF-8\"?><accounts>"
				+ "<account>"
				+ "<userid>id1</userid>"
				+ "<password>111</password>"
				+ "<username>sam</username>"
				+ "<mail>boco@boco.com.cn</mail>"
				+ "<sex>1</sex>"
				+ "<mobile>134</mobile>"
				+ "<employeeNumber>b1</employeeNumber>"
				+ "<orgid>deptid1</orgid>"
				+ "<orgname>部门名称</orgname>"
				+ "<fax>3333</fax>"
				+ "<phone>134</phone>"
				+ "<modifytime></modifytime>"
				+ "</account>"

				+ "<account>"
				+ "<userid>id2</userid>"
				+ "<password>222</password>"
				+ "<username>adam</username>"
				+ "<mail>boco2@boco</mail>"
				+ "<sex>2</sex>"
				+ "<mobile>2</mobile>"
				+ "<employeeNumber>b2</employeeNumber>"
				+ "<orgid>deptid2</orgid>"
				+ "<orgname>部门名称11</orgname>"
				+ "<fax>7777</fax>"
				+ "<phone>4444</phone>"
				+ "<modifytime></modifytime>"
				+ "</account>"
				+ "</accounts></userInfos></params>";
		// List list = new ArrayList();
		// list = ua.getXmlString(param);
		// System.out.println(list.size() + "aaaaaaaaaaaaaaaaaaaaa");

		// ITawSystemUserMappDept iTawSystemUserMappDept =
		// (ITawSystemUserMappDept) ApplicationContextHolder
		// .getInstance().getBean("iTawSystemUserMappDept");
		// String id = iTawSystemUserMappDept.getLocalDeptId("1");
		// System.out.println(id);

		// ITawSystemUser4AManager iTawSystemUser4AManager =
		// (ITawSystemUser4AManager) ApplicationContextHolder
		// .getInstance().getBean("iTawSystemUserMgr");
		// String aa = iTawSystemUser4AManager.getAllSubAccount("");

		// String delString = "<params><userInfos><?xml version=\"1.0\"
		// encoding=\"UTF-8\"?><accounts>"
		// + "<userid>mengxianyu</userid>"
		// + "<userid>mengxianyu1</userid>"
		// + "<userid>mengxianyu2</userid>"
		// + "</accounts></userInfos></params>";
		// delString = getString(delString);
		// List list = getUserIds(delString);
		// for (int i = 0; i < list.size(); i++) {
		// System.out.println(list.get(i) + "ttttttttttttttttt");
		// }
		// String pageString =
		// "<params><pageSize>15</pageSize><pageNum>1</pageNum></params>";
		// Map map = getPageParameters(pageString);
		// System.out.println(map.get("pageSize") +
		// "dddddddddddddddddddddddddd");
		// System.out.println(map.get("pageNum") +
		// "ssssssssssssssssssssssssssss");
		String singleString = "<params><userInfos><?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<accounts><userid>admin</userid><userid>mengxianyu</userid></accounts></userInfos></params>";
		String fff = getString(singleString);
		System.out.println("111=======" + fff);
		List list = getUserIds(fff);
		for (int j = 0; j < list.size(); j++) {
			System.out.println(list.get(j) + "ttttttttttttttttt");
		}
	}
}
