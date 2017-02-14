package com.boco.eoms.commons.system.dict.service.impl;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.dao.Name2IDDao;
import com.boco.eoms.commons.system.dict.service.Name2IDService;

public class Name2IDServiceImpl implements Name2IDService {

	public String name2Id(String areaName, String beanId, String filed) {
		String areaId = null;
		try {
			// 通过beanid取bean
			Name2IDDao dao = (Name2IDDao) ApplicationContextHolder
					.getInstance().getBean(beanId);
			// 转换后的name
			areaId = dao.name2Id(areaName, beanId, filed);
		} catch (Exception e) {
			areaId = "";
		}
		if (areaId == null || "".equals(areaId)) {
			areaId = "";
		}
		return areaId;
	}
}
