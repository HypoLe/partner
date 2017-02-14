// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ThreadBrowseHistoryListDisplaytagDecorator.java

package com.boco.eoms.workbench.infopub.displaytag.support;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessories;
import com.boco.eoms.commons.accessories.service.ITawCommonsAccessoriesManager;
import com.boco.eoms.commons.accessories.util.AccessoriesMgrLocator;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictServiceException;
import com.boco.eoms.commons.system.dict.service.IDictService;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.dict.util.Util;
import com.boco.eoms.workbench.infopub.model.ThreadHistory;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import org.displaytag.decorator.TableDecorator;

public class ThreadBrowseHistoryListDisplaytagDecorator extends TableDecorator {

	public ThreadBrowseHistoryListDisplaytagDecorator() {
	}

	public String getUserId() {
		ThreadHistory threadHistory = (ThreadHistory) getCurrentRowObject();
		String name = "";
		try {
			ID2NameDAO forumsDao = (ID2NameDAO) ApplicationContextHolder
					.getInstance().getBean("tawSystemUserDao");
			name = forumsDao.id2Name(threadHistory.getUserId());
		} catch (Exception e) {
			name = Util.idNoName();
		}
		return name;
	}

	public String getReplyresult() {
		ThreadHistory threadHistory = (ThreadHistory) getCurrentRowObject();
		String replyresult = "";
		try {
			replyresult = (String) DictMgrLocator.getDictService().itemId2name(
					Util.constituteDictId("dict-workbench-infopub",
							"replyresult"), threadHistory.getReplyresult());
		} catch (DictServiceException e) {
			replyresult = Util.idNoName();
		}
		return replyresult;
	}

	public String getReplyAccessories() {
		ThreadHistory threadHistory = (ThreadHistory) getCurrentRowObject();
		String html = "";
		String name = threadHistory.getReplyAccessories();
		System.out.println("name++++++=" + name);
		if (name == null || "".equals(name)) {
			System.out.println("11111111111111111");
			return StaticMethod.null2String(html);
		}
		HttpServletRequest request = (HttpServletRequest) getPageContext()
				.getRequest();
		List list = null;
		System.out.println("22222222222222222222");
		list = AccessoriesMgrLocator.getTawCommonsAccessoriesManagerCOS()
				.getNameByDateName(name);
		System.out.println("list's size=====" + list.size());
		if (list.size() != 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				TawCommonsAccessories tawCommonsAccessories = (TawCommonsAccessories) it
						.next();
				tawCommonsAccessories.getId();
				html = html
						+ "<a href ='"
						+ request.getContextPath()
						+ "/accessories/tawCommonsAccessoriesConfigs.do?method=download&id="
						+ tawCommonsAccessories.getId() + "'/>"
						+ tawCommonsAccessories.getAccessoriesCnName()
						+ "</a><br>";
			}
		}

		return StaticMethod.null2String(html);
	}
}
