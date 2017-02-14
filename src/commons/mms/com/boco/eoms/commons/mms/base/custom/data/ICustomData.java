package com.boco.eoms.commons.mms.base.custom.data;

import java.util.List;
import java.util.Map;

public interface ICustomData {

	/**
	 * 自定义获取数据
	 * @param param 格式
	 * key：beginTime,endTime
	 * 
	 * @return 格式
	 * List中存放多个map，每个map的key对应着excel样式配置文件的"汇总字段"或"指标字段"，
	 * value是从数据源获取的数据（例如数据库）。
	 */
	public List getCustomData(Map param);
}
