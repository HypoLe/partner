package com.boco.eoms.sheet.base.service.impl;

import java.util.List;


/**
 *  LinkService实现类
 */
public class LinkServiceImpl extends LinkService {
	@SuppressWarnings("unchecked")
	public List getLinksBycondition(String condition, String linkName) {
		return this.getLinkDAO().getLinksBycondition(condition, linkName);
	}
}
