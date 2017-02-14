// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Bulletin.java

package com.boco.eoms.Bulletin;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.util.*;
import com.boco.eoms.workbench.infopub.mgr.IThreadManager;
import com.boco.eoms.workbench.infopub.model.*;
import com.boco.eoms.workbench.infopub.model.Thread;

import java.io.PrintStream;
import java.util.*;

public class Bulletin {

	public Bulletin() {
	}

	public String isAlive(String serSupplier, String callTime) {
		System.out.println("收到握手信号");
		String isAliveResult = "0";
		return isAliveResult;
	}

	public String newBulletin(Integer sheetType, Integer serviceType,
			String serialNo, String serSupplier, String serCaller,
			String callerPwd, String callTime, String attachRef,
			String opPerson, String opCorp, String opDepart, String opContact,
			String opTime, String opDetail) throws Exception {
		String errList = "0";
		String xpath = "//opDetail/recordInfo/fieldInfo";
		if (serialNo != null && !"".equals(serialNo))
			try {
				InterfaceUtil doc = new InterfaceUtil();
				System.out.print("getLocalString:" + doc.getSerialNo());
				HashMap sheetMap = new HashMap();
				sheetMap = doc.xmlElements(opDetail, sheetMap, "FieldContent",xpath);
				Thread thread = new Thread();
				thread.setTitle(String.valueOf(sheetMap.get("title")));
				String threadTypeId = StaticMethod.nullObject2String(sheetMap
						.get("urgentDegree"));
				if (threadTypeId.equals("紧急"))
					thread.setThreadTypeId("1");
				else
					thread.setThreadTypeId("2");
				String toDate = String.valueOf(sheetMap.get("availTime"));
				String fromDate = StaticMethod.getCurrentDateTime();
				thread.setValidity(String.valueOf(InterfaceUtil.dateDiff(
						fromDate, toDate)));
				thread.setContent(String.valueOf(sheetMap.get("noticeDesc")));
				thread.setCreateTime(new Date());
				thread.setIsDel("0");
				thread.setEditTime(new Date());
				thread.setForumsId(BulletinMgrLocator.getAttributes()
						.getBulletinForumsId());
				thread.setThreadCount(new Integer(0));
				String reply = StaticMethod.nullObject2String(sheetMap
						.get("isNeedReploy"));
				if (reply.equals("是"))
					thread.setReply("1");
				else
					thread.setReply("2");
				thread.setSerialNo(serialNo);
				thread.setCreaterName(opPerson);
				thread.setCreaterId("admin");
				thread.setIsIncludeSubDept("1");
				InterfaceUtil interfaceUtil = new InterfaceUtil();
				List threadPermimissionOrgs = new ArrayList();
				threadPermimissionOrgs.add(new ThreadPermimissionOrg("1", "1",
						"省公司"));
				IThreadManager msg = (IThreadManager) ApplicationContextHolder
						.getInstance().getBean("IthreadManager");
				thread.setStatus("1");
				if (attachRef != null && !"".equals(attachRef)
						&& !"<attachRef></attachRef>".equals(attachRef)) {
					String fileName = "";
					List list = new ArrayList();
					list = interfaceUtil.getAttachRefFromXml(attachRef);
					if (list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							AttachRef attach = new AttachRef();
							attach = (AttachRef) list.get(i);
							String url = attach.getAttachURL();
							fileName = fileName + "," + attach.getAttachName();
							interfaceUtil.fileDownLoad(url);
						}

					}
					if (fileName.substring(0, 1).equals(","))
						thread.setAccessories(fileName.substring(1, fileName
								.length()));
					else
						thread.setAccessories(fileName);
				}
				msg.saveThread(thread, threadPermimissionOrgs);
			} catch (Exception e) {
				e.printStackTrace();
				errList = e.toString();
			}
		else
			errList = "工单编号不能为空";
		return errList;
	}

	public String confirmBulletin(Integer sheetType, Integer serviceType,
			String serialNo, String serSupplier, String serCaller,
			String callerPwd, String callTime, String attachRef,
			String opPerson, String opCorp, String opDepart, String opContact,
			String opTime, String opDetail) throws Exception {
		String errList = "0";
		String xpath = "//opDetail/recordInfo/fieldInfo";
		if (serialNo != null && !"".equals(serialNo))
			try {
				InterfaceUtil doc = new InterfaceUtil();
				HashMap sheetMap = new HashMap();
				sheetMap = doc.xmlElements(opDetail, sheetMap, "FieldContent",xpath);
				ThreadHistory threadHistory = new ThreadHistory();
				Thread thread = new Thread();
				IThreadManager msg = (IThreadManager) ApplicationContextHolder
						.getInstance().getBean("IthreadManager");
				System.out.println(serialNo);
				thread = msg.getSerialNoToThread(serialNo);
				System.out.println(thread.getId());
				String comments = StaticMethod.nullObject2String(sheetMap
						.get("reployDesc"));
				System.out.println("comments==+++++" + comments);
				threadHistory.setComments(comments);
				String replyresult = StaticMethod.nullObject2String(sheetMap
						.get("reployResult"));
				if (replyresult.equals("同意"))
					threadHistory.setReplyresult("1");
				else
					threadHistory.setReplyresult("2");
				System.out.println("replyresult==&&&&&&" + replyresult);
				threadHistory.setReadTime(new Date());
				threadHistory.setThreadId(thread.getId());
				threadHistory.setUserId("admin");
				threadHistory.setHistoryType("1");
				System.out.println(" start insert");
				msg.saveThreadHistory(thread, threadHistory);
				System.out.println("end insert with no err");
			} catch (Exception e) {
				e.printStackTrace();
				errList = e.toString();
			}
		else
			errList = "工单编号不能为空";
		return errList;
	}
}
