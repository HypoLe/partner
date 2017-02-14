package com.boco.eoms.commons.report.network.foot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.commons.mms.base.foot.IFootInfo;

public class RateFootInfo implements IFootInfo {

	/**
	 * 算法得出来的list，在MMSchedulerV35 中设置list初始化值
	 */
	private List list = null;
	
	/**
	 * 所有输入的条件
	 */
	private Map info = null;
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Map getInfo() {
		Map map = new HashMap();
		map.putAll(info);
		map.put("todaytime", "2009-7-6");
		map.put("beginTime", info.get("beginTime"));
		map.put("endTime", info.get("endTime"));
		
		return map;
	}

	public void setInfot(Map info) {
		this.info = info;
	}
}
